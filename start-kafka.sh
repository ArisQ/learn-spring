### Single
docker-compose -f docker-compose.kafka.yml up -d

### Multiple
#docker-compose -f docker-compose.kafka.yml up -d
#docker-compose -f docker-compose.kafka.yml scale kafka=3
# or
# docker-compose -f docker-compose.kafka.yml up -d --scale kafka=3
