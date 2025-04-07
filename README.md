# BackendEksamen2025
# SkiLesson Planner API

https://github.com/MickeJDengaard/BackendEksamen2025

Et RESTful API-baseret backend-projekt bygget med Javalin og JPA (Hibernate), som gør det muligt at oprette, hente, opdatere og slette skiundervisningstimer og instruktører.

##  Projektbeskrivelse

Dette projekt er en del af en eksamensopgave, hvor formålet er at demonstrere evner inden for:
- Javalin (web framework)
- JPA/Hibernate (ORM og databaseintegration)
- DAO/DTO arkitektur
- REST API design
- Brug af enums og relationer (OneToMany)

API'et giver funktionalitet til at:
- Håndtere skiundervisningstimer (`SkiLesson`)
- Håndtere skiinstruktører (`Instructor`)
- Tilknytte instruktører til lektioner
- Finde alle lektioner for en bestemt instruktør

##  Teknologier

- Java 17+
- Javalin
- Hibernate (JPA)
- PostgreSQL 
- Maven
- Jakarta Persistence API
- Lombok


Jeg har startet projektet med de nødvendige konfigurationer til at kunne arbejde med Javalin, JPA
og Hibernate.

Jeg har valgt at designe DTO klasserne således, at de inkluderer konstruktører som modtager en entitet
som argument og mapper felterne direkte. Jeg overvejede om det var hensigtsmæssigt at flytte konverteringen
til DAO laget, men jeg vurderede at det i dette projekt ville være unødvendig kompleksitet, så jeg
fortsøgte at holde det simpelt. 

Jeg har implementeret to interfaces, IDAO<T> og ISkiLessonInstructorDAO<T>. IDAO er et generisk interface
med standard CRUD funktioner, dette implementeres af instructorDAO. ISkiLessonInstructorDAO<T>, 
implementeres af SkiLessonDAO, da skilektioner kræver mere funktionalitet, som tilknytning af instruktører,
og filtering af lektioner.


### Hent alle ski-lektioner
GET http://localhost:7070/api/ski-lessons
Accept: application/json

[
{
"id": 8,
"name": "Begynderlektion",
"startTime": [
2025,
4,
8,
12,
25,
13,
38207000
],
"endTime": [
2025,
4,
8,
14,
25,
13,
38227000
],
"latitude": 55.6761,
"longitude": 12.5683,
"price": 300.0,
"level": "BEGINNER",
"instructor": {
"id": 7,
"firstname": "Anna",
"lastname": "Skiguide",
"email": "anna@ski.com",
"phone": "12345678",
"yearsOfExperience": 5
},
"instructions": null
},



### Hent en specifik ski-lektion
GET http://localhost:7070/api/ski-lessons/9
Accept: application/json

{
"id": 9,
"name": "Ekspert-session",
"startTime": [
2025,
4,
9,
12,
25,
13,
38239000
],
"endTime": [
2025,
4,
9,
15,
25,
13,
38242000
],
"latitude": 46.8182,
"longitude": 8.2275,
"price": 600.0,
"level": "ADVANCED",
"instructor": {
"id": 8,
"firstname": "Lars",
"lastname": "Snowman",
"email": "lars@snow.com",
"phone": "87654321",
"yearsOfExperience": 10
},
"instructions": []
}



