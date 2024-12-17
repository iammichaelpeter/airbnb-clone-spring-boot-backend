# Airbnb clone (fullstack project) Spring boot 3, Angular 17, PrimeNG, PostgreSQL, Auth0 (2024) (Backend)

Spring boot backend of the airbnb clone

⚠️ **IMPORTANT NOTICE**
This project is my implementation of a YouTube tutorial by [C0de-cake](https://github.com/C0de-cake).

The TUTORIAL_NOTES.md file contains my personal documentation of the learning process.

[Video tutorial](https://youtu.be/XriUV06Hkow)

[Angular Frontend](https://github.com/C0de-cake/airbnb-clone-frontend)

### Key Features:
- 📅 Booking management for travelers
- 🏠 Landlord reservation management
- 🔍 Search for houses by criteria (location, date, guests, beds, etc)
- 🔐 Authentication and Authorization (Role management) with Auth0 (OAuth2)
- 🏢 Domain-driven design

## Usage
### Prerequisites
- [JDK 21](https://adoptium.net/temurin/releases/)
- [PostgreSQL](https://www.postgresql.org/download/)
- IDE ([VSCode](https://code.visualstudio.com/download), [IntelliJ](https://www.jetbrains.com/idea/download/))

### Clone the repository
``git clone https://github.com/C0de-cake/airbnb-clone-back``

### Launch
#### Maven
``./mvnw spring-boot:run  -Dspring-boot.run.arguments="--AUTH0_CLIENT_ID=<client-id> --AUTH0_CLIENT_SECRET=<client-secret>"``

#### IntelliJ
Go in IntelliJ add the environment variables and then run it.