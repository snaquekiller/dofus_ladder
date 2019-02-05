

dev: ## springboot run
	mvn spring-boot:run


db:
	docker-compose up --build
build:
    mvn clean install