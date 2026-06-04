-- 1. Таблица пользователей
CREATE TABLE portal_user (
    id INT AUTO_INCREMENT PRIMARY KEY,
    password VARCHAR(128) NOT NULL,
    last_login DATETIME NULL,
    is_superuser BOOLEAN NOT NULL DEFAULT FALSE,
    username VARCHAR(150) NOT NULL UNIQUE,
    first_name VARCHAR(150) NOT NULL DEFAULT '',
    last_name VARCHAR(150) NOT NULL DEFAULT '',
    is_staff BOOLEAN NOT NULL DEFAULT FALSE,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    date_joined DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    full_name VARCHAR(150) NOT NULL,
    birth_date DATE NULL,
    phone VARCHAR(20) NOT NULL,
    email VARCHAR(254) NOT NULL UNIQUE,
    role VARCHAR(20) NOT NULL DEFAULT 'USER'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 2. Справочник видов транспорта
CREATE TABLE portal_transporttype (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 3. Таблица заявок
CREATE TABLE portal_application (
    id INT AUTO_INCREMENT PRIMARY KEY,
    start_date DATE NOT NULL,
    payment_method VARCHAR(50) NOT NULL,
    status VARCHAR(30) NOT NULL DEFAULT 'Новая',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    transport_id INT NOT NULL,
    user_id INT NOT NULL,
    
    CONSTRAINT fk_app_transport FOREIGN KEY (transport_id) REFERENCES portal_transporttype(id) ON DELETE RESTRICT,
    CONSTRAINT fk_app_user FOREIGN KEY (user_id) REFERENCES portal_user(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 4. Таблица отзывов (Связь один-к-одному через UNIQUE)
CREATE TABLE portal_review (
    id INT AUTO_INCREMENT PRIMARY KEY,
    text TEXT NOT NULL,
    application_id INT NOT NULL UNIQUE,
    user_id INT NOT NULL,
    
    CONSTRAINT fk_rev_app FOREIGN KEY (application_id) REFERENCES portal_application(id) ON DELETE CASCADE,
    CONSTRAINT fk_rev_user FOREIGN KEY (user_id) REFERENCES portal_user(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;