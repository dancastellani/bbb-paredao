default: 
	mvn clean package
	rm -rf apache-tomcat-6.0.37/webapps/bbb
	rm -f apache-tomcat-6.0.37/webapps/bbb.war

	cp target/bbb.war apache-tomcat-6.0.37/webapps/

	./apache-tomcat-6.0.37/bin/startup.sh
	echo "---------------------------------"
	echo "Acesse: "
	echo "	Votar - http://localhost:8080 "
	echo "	Resumo da Votação - http://localhost:8080/resumo.xhtml"
	echo "---------------------------------"
