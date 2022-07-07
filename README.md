Проект написан с использованием: Spring Boot, Security, MVC, data JPA, liquibase, PostgreSQL.

1. Регистрация (шифрование пароля bcrypt), авторизация (basic auth).
2. Добавление/получение/редактирование информации о своих питомцах, получение всех питомцев в базе данных


POST "/api/v1/auth/registration" (String name, String password) -> HTTPStatus, если пользователь существует -> exception

GET "/api/v1/animals" -> List со всеми питомцами в базе данных

GET "/api/v1/animals/{id}" (Integer id) -> питомец с данным id

GET "/api/v1/animals/my" List с питомцами по ключу id

POST "/api/v1/animals/" добавление питомца в базу данных (id владельца исходя из авторизированных данных)

PUT "/api/v1/animals/{id}" изменение данных по id питомца

DELETE "/api/v1/animals/{id}" удаление питомца по id
