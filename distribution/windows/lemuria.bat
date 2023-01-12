@echo off
where /q java
IF ERRORLEVEL 1 (
    ECHO Lemuria requires an installed Java environment.
    ECHO   It does not seem as if Java is installed.
    ECHO   Please check and make sure it is, and that java.exe is in your path.
    EXIT /B
)

java -jar .\lemuria %*