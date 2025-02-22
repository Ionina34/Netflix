#!/bin/bash

# Загрузка init.sql из удаленного источника
FILE_ID="14N20lAnYQMyGOAmAurXfSYEYrtzG_g-Y"
curl -L -o /docker-entrypoint-initdb.d/init.sql "https://drive.google.com/uc?export=download&id=${FILE_ID}"

mysql -u main_user -pmain_password netflix < /docker-entrypoint-initdb.d/init.sql