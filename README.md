# Courier Tracking Service

This project is developed to track the real-time locations of couriers and record their entrances to stores to calculate the daily travel distance of couriers. The project is developed with modern technologies, providing a scalable and flexible solution.

## Summary

This project provides a backend solution for a courier tracking system. It tracks the locations of couriers, finds the nearest store, and records store entrances. Additionally, it calculates the daily travel distance of couriers.

## Technologies and Libraries

- **Java 21**

- **Spring Boot 3**

- **Spring Data JPA**

- **PostgreSQL**

- **PostGIS**

- **Lombok**

- **MapStruct**

- **Aspect-Oriented Programming (AOP)**

- **ApplicationEventPublisher**

- **Custom Annotations for Validation**

- **Exception Handling**
  
- **JUnit & Mockito**

- **Swagger**

- **Log4J**

## Prerequisites
- **Docker Desktop**
- **Java 21** (If you are running the application locally)

## Architecture

The project is built as a **modular monolith**, where different modules are organized as cohesive units within a single codebase. This approach provides the benefits of both monolithic and microservices architectures, allowing for easier development, deployment, and maintenance.

## How It Works?

1. **Courier Location Tracking**:
    - On the server side, there's a REST API endpoint responsible for handling courier location update requests.
    - A service listens to these location update requests and retrieves the latest location of the courier.

2. **Finding the Nearest Store**:
    - Using mathematical calculations and geographic database functions, the system determines the nearest store to the courier's location.

3. **Recording Store Entrances**:
    - The system records instances when a courier enters a store. This data is crucial for determining the entry and exit times of couriers from stores.
    - Store entrance records are managed by a service dedicated to store entrance operations.
    - Details of store entrances (courier, store, entry time) are stored in a database for future reference.

4. **Calculating Total Travel Distance**:
    - The daily travel distance of a courier is computed using the recorded location data and store entrance information.
    - By analyzing courier movements between location updates and store visits, the system calculates the total distance traveled by a courier in a day.
   
## Example Requests

### Create/Update Courier
```http
POST /api/v1/couriers
Content-Type: application/json

{
    "fullName": "Asım Kılıç",
    "identityNumber": "40236654820"
}

fake valid identity numbers: 40236654820, 78919541778, 41036784946
```

### Update Courier Location
```http
POST /api/v1/couriers/location
Content-Type: application/json

{
    "courier": 1,
    "lat": 40.7128,
    "lng": -74.0060,
    "time": 1716165892000
}

```
The `time` field in the requests can be provided in two different formats:

1. **ISO 8601 Format**: For example, `"time": "2024-05-20T12:00:00Z"`
2. **Epoch Milliseconds Format**: For example, `"time": 1716165892000`

Both formats are accepted by the API. You can use either format based on your preference or convenience.

### List Couriers
```http
GET /api/v1/couriers?page=0&size=30
```

### Total Distance Travelled By Courier
```http
GET /api/v1/couriers/total-distances/{courier-id}
```

## Installation and Usage

1. Clone the repo
```powershell
https://github.com/asimkilic/courier-tracking-service.git
```

2. In the project directory
```powershell 
docker compose up --build
```

#### The Dockerfile contains two versions. The uncommented version requires only Docker Desktop, as it will compile and run the code within the container. Using the wait-for-it.sh script, the application will wait for PostgreSQL with PostGIS to be ready before starting the Spring Boot application. The commented version is faster if you have the necessary packages installed on your computer. 
You need to run the 
```powershell
 mvn clean package 

or 

./mvnw clean package 
```
command before executing the **docker compose** command.


# Swagger Documentation

This project uses Swagger for API documentation. You can explore the available endpoints and their 
details using the Swagger UI.

To access the Swagger UI, run the application and navigate to the following URL in your browser:

```powershell
http://localhost:8080/swagger-ui.html
```

# Connect Database In Bash
```powershell
> docker exec -it courier-tracking-service-courier-postgis-1 /bin/bash
> psql -U postgres
> \c courier-tracking
> \dt                                                  Listing-tables
> select * from courier_location;
> \x                                                   Expanded display is on/off

> select * from courier_location;
-[ RECORD 1 ]------------------------------
id          | 1
created_at  | 2024-05-19 03:25:33.317343+00
created_by  | courier-admin
is_deleted  | f
modified_at | 2024-05-19 03:25:33.317358+00
modified_by | courier-admin
version     | 0
latitude    | 39.580544
longitude   | 32.119268
time        | 2024-05-19 03:24:43.716+00
courier_id  | 1
-[ RECORD 2 ]------------------------------
id          | 2
created_at  | 2024-05-19 03:27:13.995854+00
created_by  | courier-admin
is_deleted  | f
modified_at | 2024-05-19 03:27:13.996034+00
modified_by | courier-admin
version     | 0
latitude    | 39.580527
longitude   | 32.122081
time        | 2024-05-19 03:25:43.716+00
courier_id  | 1
```

## Contact

Abdullah Asım KILIÇ - a.asim.kilic@gmail.com

