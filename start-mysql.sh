docker run \
--rm \
-d \
--name mysql \
-p 13306:3306 \
-e MYSQL_ALLOW_EMPTY_PASSWORD=yes \
-e MYSQL_DATABASE=tacocloud \
-e MYSQL_USER=tacodb \
-e MYSQL_PASSWORD=tacopassword \
mysql:5.6 \
--character-set-server=utf8 \
--collation-server=utf8_unicode_ci
