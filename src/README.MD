# 启动Kafka
bin/kafka-server-start.sh config/server.properties &
# 停止Kafka
bin/kafka-server-stop.sh

# 创建topic
bin/kafka-topics.sh --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic pzhu

# 查看已经创建的Topic信息

bin/kafka-topics.sh --list  --bootstrap-server localhost:9092

# 发送消息
bin/kafka-console-producer.sh --broker-list localhost:9092 --topic zp


# 接收消息
bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic zp --from-beginning