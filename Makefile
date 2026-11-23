.DEFAULT_GOAL := deploy

ifneq (,$(wildcard ./.env))
    include .env

	# Validate required variables in .env
    $(foreach var, APPLICATION_NAME POSTGRES_USER POSTGRES_DB, \
        $(if $(value $(var)),,$(error $(var) is not defined in .env)))

    export
endif

up:
	docker compose up -d

down:
	docker compose down --rmi local --volumes

down-all:
	docker compose down --rmi all --volumes

build:
	mvn clean package

bash:
	docker compose exec webserver bash

logs:
	docker compose logs -f webserver

sql:
	docker compose exec dbserver psql ${POSTGRES_DB} ${POSTGRES_USER}

ps:
	docker compose ps

stop:
	docker compose stop

deploy: build
	docker compose cp target/${APPLICATION_NAME}.war webserver:/opt/jboss/wildfly/standalone/deployments

monitor: deploy logs

mails:
	docker compose exec --workdir /var/mail mailserver bash
