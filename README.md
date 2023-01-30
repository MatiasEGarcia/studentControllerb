#  Spring boot proyect "Student controller"

### What tools/dependencies/etc I used?
    - Spring JDBC
    - Spring devtools
    - Spring started web
    - Lombok
    - Junit
    - Mockito
    - H2 database
    - MySql
    - Eclipse (IDE)
    - MySQL Workbench 
    - JsonPath
    - Swagger

-----------

## TABLES IN DATABASE (ERM)
To have the database filled you can download the data,  in the sql folder are they, and the erm archive will be there too.
#### ERM image
![erm](/readmeFiles/ermImage.png)

-----------

## Communicate with the database
For the dao layer I will be using Spring JDBC ( especially jdbcTemplate with sql queries )
![image with dao example](/readmeFiles/daoExample.png)

I configure the jdbcTemplate datasource in the application.properties :
![application.properties](/readmeFiles/application.properties)

-----------
## TESTING
For testing purposes I will use Junit5 

## Integration testing in dao layer
Testing the dao layer I work with h2 database to persist values and get them from it.

H2 is configured in the application-test.properties :
![application-test.properties](/readmeFiles/application-test.properties)

## Unit testing in service layer
Testing the service layer I don't use Spring boot, that mean I will test in isolation. 
I mock the dao layer with Mockito.
![testServielayer](/readmeFiles/testServiceLayer.png)

## Integration testing in controller layer
Testing the controller layer I work with h2 database to persist values and get them from it.
Using MockMvc and JsonPath I test controller's methods.

## Using endpoints
To access controllers and use the api, you can see the endpoints with swagger. Just run de app and paste this url in your navegator : http://localhost:8080/swagger-ui.html

Video of swagger as example :
![testServielayer](/readmeFiles/swaggerUseExample.gif)