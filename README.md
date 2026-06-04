<<<<<<< HEAD
# Обучение вождению речного транспорта

Spring Boot 3 + PostgreSQL **`driver_rf`** + HTML/CSS/JS.

## Схема БД (ваша)

Бэкенд работает с таблицами из `initPo.sql`:

| Таблица | Назначение |
|---------|------------|
| `portal_user` | Пользователи |
| `portal_transporttype` | Виды транспорта (катера, лайнеры, яхты) |
| `portal_application` | Заявки |
| `portal_review` | Отзывы |

При запуске **таблицы не создаются и не перезаписываются** (`spring.sql.init.mode=never`).

---

## Запуск одной командой (бэкенд + фронтенд)

Фронтенд встроен в Spring Boot (`static/`), отдельный сервер не нужен.

```powershell
cd C:\demo-1v
.\start.ps1
```

Или двойной клик по **`start.cmd`**. Сайт: http://localhost:8045/

---

## Как запустить всё вместе

### 1. PostgreSQL

Убедитесь, что сервер PostgreSQL запущен и база **`driver_rf`** существует (как в pgAdmin / DBeaver).

Если базы ещё нет:

```sql
CREATE DATABASE driver_rf;
```

Схема таблиц — из файла `initPo.sql` (если таблицы ещё не созданы).

Администратор **Admin26** / **Demo20** создаётся автоматически при каждом запуске приложения (класс `AdminBootstrap`). Ручной SQL: `sql/seed-admin-bcrypt.sql` — по желанию.

### 2. Настройки подключения

Откройте `src/main/resources/application.properties` и укажите **свой** логин и пароль PostgreSQL:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/driver_rf
spring.datasource.username=postgres
spring.datasource.password=ВАШ_ПАРОЛЬ
```

Удобнее: скопируйте `application-local.properties.example` → `application-local.properties` и пропишите пароль там (файл не попадает в git).

### 3. Запуск

```powershell
.\start.ps1
```

Дождитесь: `Started DriverTrainingApplication`.

### 4. Открыть сайт

В браузере:

| Страница | URL |
|----------|-----|
| Главная (слайдер) | http://localhost:8045/ |
| Вход | http://localhost:8045/login.html |
| Регистрация | http://localhost:8045/register.html |
| Личный кабинет | http://localhost:8045/profile.html |
| Заявка | http://localhost:8045/order.html |
| Админ | http://localhost:8045/admin.html |

Фронтенд отдаётся тем же Spring Boot (папка `src/main/resources/static`). Отдельный веб-сервер не нужен.

### 5. Проверка

1. **Пользователь:** регистрация → вход → заявка на `/order.html` → заявки в `/profile.html`.
2. **Админ:** вход **Admin26** / **Demo20** → `/admin.html` → смена статусов заявок.

---

## Сборка JAR

```powershell
.\mvnw.cmd package -DskipTests
java -jar target\driver-training-1.0.0.jar
```

## Демо без PostgreSQL (H2 в памяти)

```powershell
.\mvnw.cmd spring-boot:run "-Dspring-boot.run.profiles=h2"
```

---

## API

- `POST /api/auth/register` — регистрация  
- `POST /api/auth/login` — вход (сессия), логин: **e-mail** или **username** (например `Admin26`)  
- `GET/POST /api/applications` — заявки пользователя  
- `POST /api/applications/{id}/review` — отзыв  
- `GET /api/admin/applications` — все заявки (админ)  
- `PUT /api/admin/applications/{id}/status` — смена статуса  
- `GET /api/courses` — виды транспорта из `portal_transporttype`
=======

>>>>>>> e8a1ef6c386030d425be128af23660712c88ae81
