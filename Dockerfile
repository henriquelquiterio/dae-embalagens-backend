FROM quay.io/wildfly/wildfly:41.0.1.Final-jdk21

ARG POSTGRES_DRIVER_VERSION
ARG WILDFLY_ADMIN_USER
ARG WILDFLY_ADMIN_PASSWORD

ENV POSTGRES_DRIVER_VERSION=${POSTGRES_DRIVER_VERSION}
ENV WILDFLY_HOME=/opt/jboss/wildfly
ENV DEPLOYMENTS_DIR=${WILDFLY_HOME}/standalone/deployments

WORKDIR $WILDFLY_HOME

# Sets Administrator Password
RUN bin/add-user.sh ${WILDFLY_ADMIN_USER} ${WILDFLY_ADMIN_PASSWORD} --silent

# Downloads the script that starts the wildfly server
ADD --chown=jboss:root --chmod=771 scripts/start-wildfly.sh bin

# Downloads PostgreSQL Driver
ADD --chown=jboss:root --chmod=664 https://jdbc.postgresql.org/download/postgresql-${POSTGRES_DRIVER_VERSION}.jar bin

# Starts the Wildfly Web Server
ENTRYPOINT ["bin/start-wildfly.sh"]
