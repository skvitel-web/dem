-- Предзаполнение: курсы, администратор Admin26 / Demo20
-- Дубликат: src/main/resources/data.sql

INSERT INTO courses (name, description)
SELECT 'Катера', 'Обучение управлению моторными катерами на внутренних водных путях'
WHERE NOT EXISTS (SELECT 1 FROM courses WHERE name = 'Катера');

INSERT INTO courses (name, description)
SELECT 'Круизные лайнеры', 'Подготовка к управлению пассажирскими речными лайнерами'
WHERE NOT EXISTS (SELECT 1 FROM courses WHERE name = 'Круизные лайнеры');

INSERT INTO courses (name, description)
SELECT 'Яхты', 'Практика и теория управления парусными и моторными яхтами'
WHERE NOT EXISTS (SELECT 1 FROM courses WHERE name = 'Яхты');

INSERT INTO users (full_name, email, password_hash, birth_date, phone, role)
SELECT 'Администратор системы', 'Admin26@training.local',
       '$2b$10$iPySKtJWNpK7RaodMhBJi.i6c6f4grbzWVeXW4ADAuG1EQkAPKAT6',
       DATE '1980-01-26', '+79000000026', 'ROLE_ADMIN'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'Admin26@training.local');
