# Daily Haiku Service

## Overview

A service for managing haikus.


## Quick start

The following guide will take you through the necessary steps to run the application for development purposes.

The REST API is documented using Mulesoft RAML API Console. To view the documentation start the application and go to http://localhost:8080/api

To view the Dropwizard administration page, start the application and go to http://localhost:8081

### IDE Requirements

#### Eclipse

Project is imported via "Import" -> "Existing Maven Projects"  

#### IntelliJ IDEA

Project is opened via File -> Open... (select root pom.xml)  

### Installing dependencies

#### Java
From [Oracle](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
JDK 8 is required to run application

#### Maven
[Apache Maven 3.x](http://maven.apache.org/download.cgi)

#### Postgres
Requires PostgreSQL 9.4+

```
psql postgres
DROP DATABASE TODO;
CREATE DATABASE TODO WITH encoding 'UTF8';
```

### Running application

Application runs as a standard Java program with a main method. Jetty is embedded so there's no need to run as a WAR.

module: dailyhaiku-app

main class: com.agentcoon.dailyhaiku.app.dropwizard.HaikuApplication

parameters: server ${workspace_loc:/dailyhaiku-configuration}/src/main/resources/local/dailyhaiku.yml

## Build

Using Maven from top level of project
```
mvn clean package verify
```