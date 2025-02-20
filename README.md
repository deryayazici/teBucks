# TEBucks
TEBucks is a simple online payment service that lets users transfer "TE bucks" to friends.

## Features
**User Registration & Login**: Users can create an account and securely log in.
    
**View Balance**: Users can check their current balance.

**Send TE Bucks**: Allows users to transfer money to others.

**Transaction History**: Users can track past transactions.



## Tech Stack

- **Backend**: Java, Spring Boot, JUnit

- **Database**: PostgreSQL

- **Tools**: IntelliJ IDEA, Git


## API Endpoints

- **Register**: POST/register

- **Login**: POST/login (returns JWT token)

- **View balance**: GET/balance (requires authentication)

- **Send Money**: POST/transfer

- **Transaction History**: GET/transactions


## Getting started

**1. Clone the repo:**

   To clone this repository to your local machine, run the following command:

   ```bash
   git clone https://github.com/deryayazici/teBucks.git
   cd teBucks
```
**2. Set up the PostgreSQL database:**

- Install PostgreSQL if it's not already installed.

- Create new database named ```tebucks```.

- Update the database configuration in
  
  ```src/main/resource/application.properties```

   with your PostgreSQL credentials.

**3. Build and run the application:**

```bash
./mvnw clean install
./mvnw spring-boot:run
```

**4. Access the frontend at:** [teBucks Frontend](https://tebucks.netlify.app/)


## Testing

Run tests with:

```
bash
./mvnw test
```

