FROM openjdk
EXPOSE 8080
ADD target/bacxiu-cafe.jar bacxiu-cafe.jar
ENTRYPOINT ["java", "-jar", "/bacxiu-cafe.jar"]