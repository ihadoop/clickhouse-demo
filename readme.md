# install clickhouse with docker
- docker run -d --name clickhouse-server --ulimit nofile=262144:262144 -p 8123:8123 -p 9000:9000 yandex/clickhouse-server
- docker exec -it clickhouse-server /bin/bash

#  write data to clickhouse
 - see jdbc program
