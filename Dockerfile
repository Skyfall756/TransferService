FROM openjdk:17-jdk-slim-buster

EXPOSE 5500

ADD target/MoneyTransferService-0.0.1-SNAPSHOT.jar transferService.jar

CMD ["java", "-jar", "transferService.jar"]