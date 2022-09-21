# AccessFintech Backend Test

This is a Spring Boot based application

## Prerequisites
- Java 18 jdk
- Redis running on local machine
- Maven

## Build
Run under the project root directory:
```shell 
$ mvn package
```
## Run
Use to run the application
```shell
$ java -jar ./target/accessFintech-0.0.1-SNAPSHOT.jar
```
### Getting data
To get all lowest prices run 
```shell
$ curl -X GET -G 'http://localhost:8888/all'
```
To get the lowest price for a specified stock run
```shell
$ curl -X GET -G 'http://localhost:8888/lowest' -d stockId=<Stock name>
```

## Notes
This is only a test application, so, some  components coded not exactly as I would do them for production. For example, the application implies, that the format of the 
input data cannot be changed. In case of change of input format, we'll have to refactor the code. Also, the application implies, that Redis is installed on local machine.

To change file locations, edit the *application.properties* file

