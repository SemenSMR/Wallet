
<<<<<<< HEAD


Wallet 
— это приложение для управления кошельками, использующее Spring Boot для создания RESTful API и PostgreSQL для хранения данных. Приложение поддерживает создание, получение и обновление информации о кошельках, включая баланс. Для управления миграциями базы данных используется Liquibase.
=======
Wallet 
— это приложение для управления кошельками, использующее Spring Boot для создания RESTful API и PostgreSQL для хранения данных. Приложение поддерживает создание, получение и обновление информации о кошельках, включая баланс. Для управления миграциями базы данных используется Liquibase.


>>>>>>> ab779a49a480a966ae828dd71decea764658517b
Функциональные возможности


Создание нового кошелька
Получение информации о кошельке по идентификатору
Обновление баланса кошелька
Проверка наличия кошелька и достаточности средств

Запуск с docker

В Docker-compose Запускаем бд

Хост: localhost

Порт: 5432

База данных: postgres

Пользователь: postgres

Пароль: 1111

Создаем images командой docker build -t wallet-app .

Запускаем через Docker-compose приложение на порту 8080

/api/v1/wallet/{id}  Получение информации о кошельке Метод: GET

/api/v1/wallet {
"walletId" : "a4e63d74-45c9-4a59-9a93-349a788f3d12",
"operationType" : "DEPOSIT or WITHDRAW",
"amount" : ""
} - Обновление баланса кошелька метод put


