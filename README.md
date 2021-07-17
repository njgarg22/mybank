# MyBank

A simple Java web-app based on `HTTPServlets` with an embedded `Tomcat` container. `Jackson` library is used here to give our servlet `JSON` capabilities.

## Run the project

1. First, `git clone` the project locally.

1. There’s two ways to run the application:
    * If you have an IDE like `IntelliJ`, import the project in `IntelliJ` and run the main method of `ApplicationLauncher` class.
    * Else, run `mvn package` which will create a `jar` with embedded tomcat &  run `java -jar target/receipts-1.0-SNAPSHOT.jar`.

1. Open http://localhost:8080/ to see the `"Hello World"` welcome page.

1. Using `Postman`, hit the below endpoints.

## GET Endpoints

1. GET http://localhost:8080/transactions : Returns all the transactions

1. GET http://localhost:8080/unregisteredPath : Returns `404 Not Found`.


## POST Endpoints

1. POST http://localhost:8080/transactions?amount=50.24&reference=neeraj : Create a new transaction with the following JSON response

    ```json
    {
        "id": "96d19a2c-b259-428a-b027-fa455a4191f7",
        "amount": 50.24,
        "timestamp": "2021-07-17T22:40+0530",
        "reference": "neeraj",
        "bankSlogan": "Your money is safe with us"
    }
    ```

1. POST http://localhost:8080/unregisteredPath : Returns `404 Not Found`.
