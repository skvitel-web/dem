CREATE DATABASE driver-rf;

-- 1. Таблица пользователей (с полями для авторизации Django)
CREATE TABLE portal_user (
    id SERIAL PRIMARY KEY,
    password VARCHAR(128) NOT NULL,
    last_login TIMESTAMP WITH TIME ZONE,
    is_superuser BOOLEAN NOT NULL DEFAULT FALSE,
    username VARCHAR(150) NOT NULL UNIQUE,
    first_name VARCHAR(150) NOT NULL,
    last_name VARCHAR(150) NOT NULL,
    is_staff BOOLEAN NOT NULL DEFAULT FALSE,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    date_joined TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    full_name VARCHAR(150) NOT NULL,
    birth_date DATE,
    phone VARCHAR(20) NOT NULL,
    email VARCHAR(254) NOT NULL UNIQUE,
    role VARCHAR(20) NOT NULL DEFAULT 'USER'
);

-- 2. Справочник видов транспорта
CREATE TABLE portal_transporttype (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

-- 3. Таблица заявок
CREATE TABLE portal_application (
    id SERIAL PRIMARY KEY,
    start_date DATE NOT NULL,
    payment_method VARCHAR(50) NOT NULL,
    status VARCHAR(30) NOT NULL DEFAULT 'Новая',
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    transport_id INT NOT NULL,
    user_id INT NOT NULL,
    
    CONSTRAINT fk_app_transport FOREIGN KEY (transport_id) REFERENCES portal_transporttype(id) ON DELETE RESTRICT,
    CONSTRAINT fk_app_user FOREIGN KEY (user_id) REFERENCES portal_user(id) ON DELETE CASCADE
);

-- 4. Таблица отзывов (связь один-к-одному через UNIQUE)
CREATE TABLE portal_review (
    id SERIAL PRIMARY KEY,
    text TEXT NOT NULL,
    application_id INT NOT NULL UNIQUE,
    user_id INT NOT NULL,
    
    CONSTRAINT fk_rev_app FOREIGN KEY (application_id) REFERENCES portal_application(id) ON DELETE CASCADE,
    CONSTRAINT fk_rev_user FOREIGN KEY (user_id) REFERENCES portal_user(id) ON DELETE CASCADE
);