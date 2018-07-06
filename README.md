# Banking Application

An application that simulates simple banking transactions.  
All interaction with the application is done through the console. Customers can request to create an account with the bank and manage an existing account to make a withdrawal, deposit, or transfer. Employees can view all customer information, including account information. Admins have the same capability of employees, as well as the ability to approve/deny customer account requests, perform transactions on any account, and cancel accounts.  
The application is setup to store and retrieve data from a database.  
Due to time constraints password hashing was not implemented.  

## Database Setup

```sql
CREATE TABLE IF NOT EXISTS bank.requests (
 	id SERIAL PRIMARY KEY,
 	request_id INTEGER NOT NULL,
 	username VARCHAR(30) NOT NULL UNIQUE,
 	hash VARCHAR(30) NOT NULL,
 	date_created TIMESTAMP DEFAULT now()
);

CREATE TABLE IF NOT EXISTS bank.users (
 	id SERIAL PRIMARY KEY,
 	username VARCHAR(30) NOT NULL UNIQUE,
 	hash VARCHAR(60) NOT NULL,
 	role INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS bank.accounts(
 	id SERIAL PRIMARY KEY,
 	balance DECIMAL(18,2)
);

CREATE TABLE IF NOT EXISTS bank.user_accounts(
	user_id INTEGER REFERENCES bank.users(id) ON DELETE CASCADE,
    account_id INTEGER REFERENCES bank.accounts(id) ON DELETE CASCADE,
	PRIMARY KEY (user_id, account_id)
);

INSERT INTO bank.users (username, hash, role) VALUES ('admin', 'adminpass', '2');
INSERT INTO bank.users (username, hash, role) VALUES ('employee', 'employeepass', '1');
INSERT INTO bank.accounts (balance) VALUES (0.0);
INSERT INTO bank.user_accounts (user_id, account_id) VALUES (1, 1);
```

## Built With

Spring Tool Suite - Project Management  
Java 8 - Core Application  
J-Unit - Unit Tests  
Log4J - Transaction Logging  
Maven - Dependency Management  

## Additional Dependencies

AWS - Server Host  
PostgreSQL - Database Management  
