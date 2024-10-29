# EtherscanTransactions - API Documentation

## Description

The Etherscan Transactions API is a service designed to retrieve and manage transactions on the Ethereum network. This API enables interaction with transaction information using data provided by the Etherscan API, offering functionality to get all transactions by address.

## Main Features

- **Recovery of all transactions**
- **Recovery of only new transactions**
- **Saving key operations in logs**

## Prerequisites

- **Java 17**
- **Maven** link to install https://maven.apache.org/install.html
- **Postman** to test the API
- **Postgres 15**

## Configure the database

To save all the data on the database the only thing to do is to change the credentials on the **EtherscanTransactions\src\main\resources\application.properties**
On that file are written the postgreSQL credentials, the fields to fill are:

- spring.datasource.username
- spring.datasource.password

Obviously the fields must be filled with the username and password of the postgreSQL server installed on your pc

Then, open command prompt and type:

  ```
  bash
  psql -U <your_postgres_username>
  ```
Insert the required password and then type
  ```
  bash
  CREATE DATABASE etherscantransactions
  ```

## How to run the application

### Build and execution
- **Build**: to generate `.jar` using Maven
    ```
    bash
    mvn clean install
    ```
- **Execution**: Once the `.jar` file is generated, run the application:
    ```
    bash
    java -jar target/EtherscanTranscations-0.0.1-SNAPSHOT.jar
    ```
## Test the API using Postman

The main functionality is to retrieve all the transactions. To test the functionality using Postman there are two main URL. Both of two have in common the behaviour: the EtherscanTransactions API perform a request to the etherscan API, and requests only the new transactions, if there are it saves all on the database.

#### The first GET request is
http://localhost:8080/api/v1/transactions/getall/0x0063ec2896db3d25194667c9b5ccb69bd860b928

Then, all the transaction on the database are provided

#### The second GET request is
http://localhost:8080/api/v1/transactions/getnew/0x0063ec2896db3d25194667c9b5ccb69bd860b928

Then, only the new transactions are provided

#### GET request to get paginated transactions

With this request all the transactions are provided paginated with the given offset and number of the page. This functionality saves on the db only the transactions that aren't yet present

http://localhost:8080/api/v1/transactions/getall/page/{page}/size/{size}/0x0063ec2896db3d25194667c9b5ccb69bd860b928


#### GET request to retrieve all the LOGS

http://localhost:8080/api/v1/log/getall