default: 
	mvn clean jetty:run-war
	#rm -rf apache-tomcat-6.0.37/webapps/bbb
	#rm -f apache-tomcat-6.0.37/webapps/bbb.war

	#cp target/bbb.war tomcat6/webapps/

	#./tomcat6/bin/startup.sh
	#@echo "---------------------------------"
	#@echo "Votação BBB - Online "
	#@echo "Acesse: "
	#@echo "	Votar - http://localhost:8080/bbb/ "
	#@echo "	Resumo da Votação - http://localhost:8080/bbb/resumo.xhtml"
	#@echo "---------------------------------"
