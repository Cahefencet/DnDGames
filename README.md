# Начало работы

## Создание контейнера с базой данных

Для работы с БД я использовал контейнер podman, mariadb:10.11.7. 
Руководство рассчитано именно на такой способ. 

1. Сначала выполните pull с нужной версией БД mariadb:10.11.7:

    ```bash
    sudo podman pull mariadb:10.11.7
    ```

2. Создайте и запустите контейнер с базой данных указанной версии:

    ```bash
    sudo podman run --name YOUR_CONTAINER_NAME -e MYSQL_ROOT_PASSWORD=YOUR_PASSWORD -d mariadb:10.11.7
    ```

3. Удостоверьтесь, что контейнер запущен:

    ```bash
    sudo podman ps
    ```
   
- `Вывод будет примерно такой:`
    ```
    CONTAINER ID  IMAGE                              COMMAND     CREATED         STATUS         PORTS       NAMES
    55555aaaa  docker.io/library/mariadb:10.11.7     mariadbd 19 seconds ago  Up 19 seconds          YOUR_CONTAINER_NAME
    ```

4. Узнайте IP-адрес для подключения к базе данных:

    ```bash
    sudo podman inspect -f '{{.NetworkSettings.IPAddress}}' YOUR_CONTAINER_NAME
    ```

Команда выведет IP-адрес, по которому мы будем подключаться к бд. Обозначим его как YOUR_IP.

## Создание базы данных

1. Подключитесь к MariaDB:

    ```bash
    mysql -h YOUR_CONTAINER_IP -u root -p
    ```

2. Создайте базу данных:

    ```sql
    CREATE DATABASE YOUR_DATABASE_NAME;
    ```

3. Завершите работу с БД:

    ```sql
    exit;
    ```

## Настройка конфигурационных файлов

В проекте присутствует три конфигурационных файла, 
все располагаются в src/main/resources/ru/uniyar/config/

- `DriverHostPort.env`:

    ```env
    HOST=YOUR_CONTAINER_IP
    DRIVER=com.mysql.cj.jdbc.Driver
    PORT=9000
    ```

DriverHostPort.env отвечает за настройку IP-адреса для подключения 
к базе данных, настройку драйвера для подключения и порта.
Поумолчанию драйвер - com.mysql.cj.jdbc.Driver, порт - 9000.

- `DBAuth.env`:

    ```env
    DB_USER=root
    PASSWORD=YOUR_PASSWORD
    DB_NAME=YOUR_DATABASE_NAME
    ```
DBAuth.env отвечает за настройку аутентификационных данных 
для подключения к базе данных. Значение DB_USER может 
отличаться в зависимости от того, как вы настроили свою 
базу данных, поумолчанию пользователь - user.

- `Secret.env`:

    ```env
    auth.salt=Последовательность1
    secret=Последовательность2
    ```

Secret.env отвечает за соль, добавляемую к паролю для шифрования. 
Вам необходимо сгенерировать достаточно большую последовательность 
символов и вставить её в этот файл.
Ввиду особенностей .env файлов, последовательности НЕ ДОЛЖНЫ 
содержать '#', иначе всё, что следует за '#', будет отброшено.

## Запуск проекта

После завершения конфигураций перед запуском необходимо 
создать в базе первого админа (хотя бы один администратор 
необходим для дальнейшего добавления членов администрации на сайт,
в т.ч. редакторов, модераторов и др. администраторов)

   В `src/main/kotlin/ru/uniyar/utils/DBConnection`:

    insertUser(
        User(
            0,
            "admin",
            Hasher.hashPassword("12345"),
            Role.ADMIN
        )
    )

   Удалите эту операцию после первого запуска.

## Flyway

- Используется Flyway для миграций (класс DBConnection).
- Миграции хранятся в папке `migrations`.
- Вы можете создать файл миграции в формате `Vномер версии__название.sql` в папке `migrations`.
- Поумолчанию используется `V1__Base.sql` с пустой базой.
- Используйте файл `V2__WithData.sql` для примера наполнения базы данных (достаточно убрать .target("1")).
- Пароль для всех пользователей - 12345.
