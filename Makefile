
dev: ## springboot run
	mvn spring-boot:run

db:
	docker-compose up --build

build:
	mvn clean install

update:
	scp docker-compose-release.yml perso:/tmp
	ssh perso -C "docker stack deploy -c /tmp/docker-compose-release.yml dofus"


help:           ## Show this help.
	@fgrep -h "##" $(MAKEFILE_LIST) | fgrep -v fgrep | sed -e 's/\\$$//' | sed -e 's/##//'