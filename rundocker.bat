echo on
call gradle clean build
call docker compose build
call docker compose up -d
call docker cp goodreads_top100_from1980to2023_final.csv task-container:/usr/local/data