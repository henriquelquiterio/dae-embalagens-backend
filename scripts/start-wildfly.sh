#!/usr/bin/env bash

set -euo pipefail

readonly WILDFLY_CLI="$WILDFLY_HOME/bin/jboss-cli.sh"
readonly WILDFLY_SERVER="$WILDFLY_HOME/bin/standalone.sh"
readonly DEPLOYMENTS_TMP="/tmp/deployments"
readonly STARTED_FILE="wildfly.started"

wait_for_server() {
    printf "Waiting for WildFly to become available..."

    until "$WILDFLY_CLI" --connect ":read-attribute(name=server-state)" \
        2>/dev/null | grep -q '"result" => "running"'
    do
        sleep 1
    done
}

cleanup_deployments() {
    rm -rf "$DEPLOYMENTS_TMP"
    mkdir -p "$DEPLOYMENTS_TMP"

    if compgen -G "$DEPLOYMENTS_DIR/*" > /dev/null; then
        mv "$DEPLOYMENTS_DIR"/* "$DEPLOYMENTS_TMP"/
    fi
}

restore_deployments() {
    if compgen -G "$DEPLOYMENTS_TMP/*" > /dev/null; then
        mv "$DEPLOYMENTS_TMP"/* "$DEPLOYMENTS_DIR"/
    fi

    rm -rf "$DEPLOYMENTS_TMP"
}

initialize_wildfly() {
    cleanup_deployments

    printf "\n\n************************* >>> Starting WildFly <<< *************************\n\n"

    "$WILDFLY_SERVER" \
        -c standalone.xml \
        >/dev/null 2>&1 &

    local wildfly_pid=$!

    wait_for_server

    printf "\n\n************************* >>> Setup Datasource <<< *************************\n\n"

    "$WILDFLY_CLI" --connect <<EOF
batch

module add \
  --name=org.postgresql \
  --resources="$WILDFLY_HOME/bin/postgresql-$POSTGRES_DRIVER_VERSION.jar" \
  --dependencies=javax.api,javax.transaction.api

/subsystem=datasources/jdbc-driver=postgres:add(driver-name="postgres",driver-module-name="org.postgresql",driver-class-name="org.postgresql.Driver")

data-source add \
  --jndi-name=$DATASOURCE_JNDI \
  --name=$DATASOURCE_NAME \
  --connection-url=jdbc:postgresql://$POSTGRES_HOST:$POSTGRES_PORT/$POSTGRES_DB \
  --driver-name=postgres \
  --user-name=$POSTGRES_USER \
  --password=$POSTGRES_PASSWORD \
  --check-valid-connection-sql="SELECT 1" \
  --background-validation=true \
  --background-validation-millis=60000 \
  --flush-strategy=IdleConnections \
  --min-pool-size=10 \
  --max-pool-size=100 \
  --pool-prefill=false

run-batch
EOF

    printf "\n\n************************* >>> Setup Mail Session (fakeSMTP) <<< *************************\n\n"

    "$WILDFLY_CLI" --connect <<EOF
batch
# Creates the custom fakeSMTP mail session
/subsystem=mail/mail-session=fakeSMTP:add(jndi-name=java:/jboss/mail/fakeSMTP)

# Configures the custom SMTP socket binding groups
/socket-binding-group=standard-sockets/remote-destination-outbound-socket-binding=my-mail-smtp:add(host=host.docker.internal,port=2525)

# Adds the custom socket binding groups to the custom fakeSMTP mail session
/subsystem=mail/mail-session=fakeSMTP/server=smtp:add(outbound-socket-binding-ref=my-mail-smtp)

# Runs the batch commands
run-batch

# Reloads the server configuration
reload
EOF

    printf "\n\n************************* >>> Shutting down WildFly <<< *************************\n\n"

    "$WILDFLY_CLI" --connect ":shutdown" || true

    wait "$wildfly_pid" 2>/dev/null || true

    restore_deployments

    touch "$STARTED_FILE"
}

if [[ ! -f "$STARTED_FILE" ]]; then
    initialize_wildfly
fi

printf "\n\n************************* >>> Starting WildFly Web Server <<< *************************\n\n"

exec "$WILDFLY_SERVER" \
    -c standalone.xml \
    -b 0.0.0.0 \
    -bmanagement 0.0.0.0
