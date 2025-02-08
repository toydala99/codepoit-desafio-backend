# Etapa 1: Usar uma imagem com Maven e OpenJDK 21 para compilar o projeto
FROM maven:3.9.0-openjdk-21-slim AS build

# Definir o diretório de trabalho para a construção
WORKDIR /app

# Copiar o arquivo pom.xml para baixar as dependências do Maven
COPY pom.xml .

# Baixar as dependências do Maven (cache para otimizar builds futuros)
RUN mvn dependency:go-offline

# Copiar o código-fonte da aplicação para o contêiner
COPY src ./src

# Compilar o projeto e gerar o arquivo .jar, ignorando os testes
RUN mvn clean package -DskipTests

# Etapa 2: Usar uma imagem do Ubuntu para rodar a aplicação com OpenJDK 21
FROM ubuntu:22.04

# Instalar o OpenJDK 21 no contêiner
RUN apt-get update && apt-get install -y \
    openjdk-21-jdk \
    && apt-get clean

# Definir as variáveis de ambiente para o Java
ENV JAVA_HOME=/usr/lib/jvm/java-21-openjdk-amd64
ENV PATH=$JAVA_HOME/bin:$PATH

# Definir o diretório de trabalho no contêiner
WORKDIR /app

# Copiar o arquivo .jar gerado da etapa de build para o contêiner
COPY --from=build /app/target/*.jar app.jar

# Expor a porta 8080 (padrão da aplicação Spring Boot)
EXPOSE 8080

# Comando para rodar a aplicação Spring Boot
CMD ["java", "-jar", "app.jar"]
