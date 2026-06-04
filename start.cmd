@echo off
cd /d "%~dp0"
echo Запуск бэкенда и фронтенда на http://localhost:8045
call mvnw.cmd spring-boot:run
