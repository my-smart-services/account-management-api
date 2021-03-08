# Account Management API

This project is a sample to show of a simple SpringBoot Project.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

Upcoming:
What things you need to install the software and how to install them

```
Give examples
```

### Installing

Upcoming:
A step by step series of examples that tell you how to get a development env running

Say what the step will be

```
Give the example
```

And repeat

```
until finished
```

End with an example of getting some data out of the system or using it for a little demo

## Running the tests

This project shows of a variety of tests.
All tests are executed if you perform the maven test goal:

```
maven clean test
```

All written Tests are located at

```
src/test
├───java
│   └───com
│       └───mss
│           └───accountmanagementapi
│               └───user
│                   ├───create
│                   ├───data
│                   ├───delete
│                   ├───read
│                   └───update
└───resources
    ├───contracts
    │   └───user
    │       ├───create
    │       ├───delete
    │       ├───read
    │       └───update
    └───sql
```

### Junit Tests

Normally the service layer would be tested as a Unit.
Since this project has a flat 3 Layer architecture, and it's using the JpaRepository, the JpaTests and the actual tests are combined.

To keep the error output consistent meanwhile the tests. The following property is set:

```
spring:
  web:
    locale: en_EN
```

### Generic Testdata

To load Testdata the sql folder contains an SQL File, initializing some Testusers. It can be used by:
```
@Sql("/sql/genericTestData.sql")
```

### Spring Cloud Contract Tests

In order to enable common tests between this API and its consumer, the common Spring Mock MVC Tests are replaced through Spring Cloud Contract Tests.

```
com.mss.accountmanagementapi.ControllerStubTest
```

The Class describes the setup for all contract tests. All Contract Tests can be found under:

```
src/test/resources/contracts
```
Due to the following Build-Plugin, all stubs generate MockMVC Tests.

```
<plugin>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-contract-maven-plugin</artifactId>
    <version>3.0.1</version>
    <extensions>true</extensions>
    <configuration>
        <baseClassForTests>
            com.mss.accountmanagementapi.ControllerStubTest
        </baseClassForTests>
        <testFramework>JUNIT5</testFramework>
    </configuration>
</plugin>
```

Please notice that the ControllerStubTest.class is registered as baseClassForTests. By default the Plugin will publish the generated sources in:

```
target/generated-test-sources
├───contracts
│   └───com
│       └───mss
│           └───accountmanagementapi
│               └───user
└───test-annotations
```

### Spring Rest Doc

Next to the definition of the Stubtests in ControllerStubTest, the Class will also configure the basic setup for the Spring Rest Doc. This setup will generate a bunch of Asscii-Docs, representing all performed contract test calls. All the docs can be found under, after the test goal was executed:

```
target/generated-snippets
```

## Deployment

IF build the application will be available as executable jar.
Each System with a Java installation higher 1.8 and a free 8080 port, will be able to start this application.

## Built With

* [Dropwizard](http://www.dropwizard.io/1.0.2/docs/) - The web framework used
* [Maven](https://maven.apache.org/) - Dependency Management
* [ROME](https://rometools.github.io/rome/) - Used to generate RSS Feeds

## Contributing

TBD

## Versioning

We use [SemVer](http://semver.org/) for versioning. For the versions available, see the [tags on this repository](https://github.com/my-smart-services/account-management-api/tags).

## Authors

* **Tobias Gläßer** - *Initial work* - [PurpleBooth](https://github.com/my-smart-services)

See also the list of [contributors](https://github.com/my-smart-services/account-management-api/graphs/contributors) who participated in this project.

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

## Acknowledgments

* Hat tip to anyone whose code was used
