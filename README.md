# Начало работы

## Создание контейнера с базой данных

1. Сначала выполните pull с нужной версией БД mariadb:10.11.7:

    ```bash
    sudo podman pull mariadb:10.11.7
    ```

2. Создайте и запустите контейнер с базой данных указанной версии:

    ```bash
    sudo podman run --name [YOUR_CONTAINER_NAME] -e MYSQL_ROOT_PASSWORD=[YOUR_PASSWORD] -d mariadb:10.11.7
    ```

3. Удостоверьтесь, что контейнер запущен:

    ```bash
    sudo podman ps
    ```

4. Узнайте IP-адрес для подключения к базе данных:

    ```bash
    sudo podman inspect -f '{{.NetworkSettings.IPAddress}}' [YOUR_CONTAINER_NAME]
    ```

## Создание базы данных

1. Подключитесь к MariaDB:

    ```bash
    mysql -h [YOUR_IP] -u root -p
    ```

2. Создайте базу данных:

    ```sql
    CREATE DATABASE [YOUR_DATABASE_NAME];
    ```

3. Завершите работу с БД:

    ```sql
    exit;
    ```

## Настройка конфигурационных файлов

- `DriverHostPort.env`:

    ```env
    HOST=[YOUR_IP]
    DRIVER=com.mysql.cj.jdbc.Driver
    PORT=9000
    ```

- `DBAuth.env`:

    ```env
    DB_USER=root
    PASSWORD=[YOUR_PASSWORD]
    DB_NAME=[YOUR_DATABASE_NAME]
    ```

- `Secret.env`:
~~~~
    ```env
    auth.salt=[Последовательность1]
    secret=[Последовательность2]
    ```

## Запуск проекта

1. Перед запуском создайте первого админа в базе данных:

   В `ru.uniyar.utils.DBConnection`:

    ```java
    insertUser(
        User(
            0,
            "admin",
            Hasher.hashPassword("12345"),
            Role.ADMIN
        )
    );
    ```

   Удалите эту операцию после первого запуска.

## Flyway

- Используется Flyway для миграций.
- Миграции хранятся в папке `migrations`.
- Вы можете создать файл миграции в формате `V[номер версии]__[название]` в папке `migrations`.
- Используйте файл `V2__WithData.sql` для примера наполнения базы данных.
- Пароль для всех пользователей - 12345.
