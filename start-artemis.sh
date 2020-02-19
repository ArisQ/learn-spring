docker run \
--rm \
-d \
--name artemis \
-p 8161:8161 \
-p 61616:61616 \
-e ARTEMIS_USERNAME=tacoweb \
-e ARTEMIS_PASSWORD=13tm31n \
vromero/activemq-artemis:2.11.0-alpine
