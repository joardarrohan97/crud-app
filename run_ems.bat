@echo off
echo Starting PostgreSQL...
net start postgresql-x64-17

echo Starting EMS App...
cd /d "%~dp0"
java -jar target\crud-app-1.0.0.jar

timeout /t 5 >nul
start http://localhost:8080
