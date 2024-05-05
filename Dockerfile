FROM openjdk:17
EXPOSE 8081
ADD target/authorityservice-onemorerep.jar authorityservice-onemorerep.jar
ENTRYPOINT ["java","-jar","/authorityservice-onemorerep.jar"]