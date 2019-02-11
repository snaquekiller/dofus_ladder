
dev: ## springboot run
	mvn spring-boot:run

db:
	docker-compose up --build

build:
	mvn clean install

update:
	scp docker-compose-release.yml perso:/tmp
	ssh perso -C "docker stack deploy -c /tmp/docker-compose-release.yml dofus"
