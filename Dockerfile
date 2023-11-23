# Definição de build para a imagem do Spring boot
FROM openjdk as build

WORKDIR /app



COPY target/algafood-api-0.0.1-SNAPSHOT.jar /app/algafood-api.jar

ENTRYPOINT [ "java", "-jar", "algafood-api.jar"]