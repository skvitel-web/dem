-- Администратор для Spring Boot (логин Admin26, пароль Demo20, BCrypt)
-- Выполните в psql, если в БД ещё нет админа или пароль в формате Django (не BCrypt).

INSERT INTO portal_transporttype (name)
SELECT 'Катера' WHERE NOT EXISTS (SELECT 1 FROM portal_transporttype WHERE name = 'Катера');
INSERT INTO portal_transporttype (name)
SELECT 'Круизные лайнеры' WHERE NOT EXISTS (SELECT 1 FROM portal_transporttype WHERE name = 'Круизные лайнеры');
INSERT INTO portal_transporttype (name)
SELECT 'Яхты' WHERE NOT EXISTS (SELECT 1 FROM portal_transporttype WHERE name = 'Яхты');

INSERT INTO portal_user (
    password, username, first_name, last_name, full_name, birth_date, phone, email,
    role, is_superuser, is_staff, is_active
) VALUES (
    '$2b$10$iPySKtJWNpK7RaodMhBJi.i6c6f4grbzWVeXW4ADAuG1EQkAPKAT6',
    'Admin26',
    'Администратор',
    'системы',
    'Администратор системы',
    '1980-01-26',
    '+79000000026',
    'admin26@training.local',
    'ADMIN',
    TRUE,
    TRUE,
    TRUE
)
ON CONFLICT (username) DO UPDATE SET
    password = EXCLUDED.password,
    role = 'ADMIN',
    is_superuser = TRUE,
    is_staff = TRUE;
