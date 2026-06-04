Set-Location $PSScriptRoot
Write-Host "Запуск бэкенда и фронтенда: http://localhost:8045" -ForegroundColor Cyan
& .\mvnw.cmd spring-boot:run
