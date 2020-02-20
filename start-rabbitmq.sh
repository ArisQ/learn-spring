docker run \
--rm \
-d \
--name rabbitmq \
-p 5672:5672 \
-p 15672:15672 \
-e RABBITMQ_DEFAULT_USER=tacoweb \
-e RABBITMQ_DEFAULT_PASS=13tm31n \
rabbitmq:3.8-management-alpine
