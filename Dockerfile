FROM tomcat:8-jre8

# Copiar war
COPY target/emisa.war $CATALINA_HOME/webapps/
COPY mysql-connector-j-8.0.31.jar $CATALINA_HOME/lib/

