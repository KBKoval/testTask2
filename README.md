## Spring Boot  version 3.2.1
## JAVA 17

# Сборка
  # Maven :
   mvn clean install
  # Gradle:
   gradlew build

# Run
   java -jar task2.jar  
# Swagger:  
    http://localhost:8080/swagger-ui/index.html 
  # Сборка Docker: 
      docker compose build  
  # Run Docker: 
     docker compose up -d (как сервиса)
  # Копирование файла: 
     docker cp goodreads_top100_from1980to2023_final.csv task-container:/usr/local/data

# Bat file для выполнения всех задач: 
    rundocker.bat