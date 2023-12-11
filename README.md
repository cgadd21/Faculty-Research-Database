# Faculty Research Database

Done by Cooper Gadd, George Gabro, John D'Angelo, Rayhan Kader, Aathif Khan, and Jon Wong

The goal of the Research Database is to pair guests, students, and faculty with interested parties for research projects.

## Configuration

In order to configure the database properly, follow these steps:

1. Source the Database file in: `Configuration\Database\Project.SQL`
2. Source a driver in the classpath via: `Configuration\Driver\mysql-connector-j-8.1.0.jar`
3. Run the `program.java` file.

## Program

To run the program, follow these steps:

1. Start by running the `program.java` file.
2. If an account exists, press login. If not, select sign up to create a new account.

   - **Sign Up (1A):** Select user type between Faculty, Student, or Guest.
     - After pressing next, fill out all the fields, then select all interests and abstracts you want.
     - If a new abstract or interest is needed, press new on the field and then done.

   - **LogIn (1B):** Enter Username and Password, and then press next.
     - Options between Account, Search, or Logout are available.

       - **Account (2B):** Allows the user to update users' information. New interests and abstracts can be made or added to the user.
       - **Search (3B):** Allows the user to search all users for interests and abstracts that are available.
       - **Logout (4B):** Logs the user out from the account.

## Test Accounts

- **Jimhab:** FacultyPass
- **jmd4173:** StudentPass
- **Wegmans:** GuestPass
- **abc1234:** ExamplePass
