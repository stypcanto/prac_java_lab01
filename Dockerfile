# Usa Tomcat 10.1 que sí soporta Jakarta EE 10

# ✅ Use dependencias compatibles con Jakarta EE 10
# ✅ No empaquete jakarta.servlet (porque lo provee Tomcat)

FROM tomcat:10.1

# Limpia la carpeta webapps por defecto
RUN rm -rf /usr/local/tomcat/webapps/*

# Copia tu WAR generado por Maven a la carpeta de despliegue de Tomcat
COPY target/Lab01.war /usr/local/tomcat/webapps/ROOT.war




# Para que funcione este proyecto hay que actualizar el pom.xml
# Hay que definir la version java 17
# Para ejecutar colocar el nombre del proeycto del Build:   <finalName>template</finalName>

#Para ejecutar el Docker en donde esta el Tomcat
# mvn clean package
# docker-compose up --build --force-recreate -d