echo on
cd .\back
call build
cd ..\
call docker compose build
call docker compose up -d