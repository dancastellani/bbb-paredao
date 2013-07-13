default: 
	mvn clean compile flyway:migrate jetty:run
