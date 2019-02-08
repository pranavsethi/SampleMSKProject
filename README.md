# SampleMSKProject
This Project declares how to use MSK(Managed Streaming Kafka) APIs and Kafka API to produce sample data and consume that



 This project once run, does following :
 
 1. List all the clusters in your MSK account
 2. One by one describe all the clusters
 3. Then call the create topic, if topic does not exists
 4. Then generate random data which is <company name, stock price>
 5. Once the number of records specified by you is produced to kafka, It will consume all those records.
 

How To use :
Easiest way : load the file in your IDEs and then after making changes according to your requirement 
              Once done export it as runnable jar
              
              Need to create an MSK cluster
              Launch a client EC2 in the same VPC as MSK
              Create a folder in home directory
              /home/ec2-user/MSK_Project
              Scp the project( which is in the form of runnable jar) to this folder in specified pr
              
              Once you have the project, run it 
              example : java -cp MSK_SAMPLE_JAVA.jar utilsClass.RunnerClass 
              
              Please make sure to create a properties file with the name of config1.properties 
              This file will have all the properties such as topic name, number of records to publish and consume and other 
              kafka related configurations such as Partition, replication, key/value serializer
              
              
****** SAMPLE PROPERTIES ***
 TopicName=MSK_SAMPLE_TOPIC
NUM_OF_RECORDS=500
bootstrap.servers=XXXXXX\:9092,XXXXX\:9092,XXXXX\:9092
zookeeperHost=XXXXX\:2181,XXXXXX\:2181,XXXXX\:2181
isSucre=false
sessionTimeoutMs=200000
connectionTimeoutMs=15000
maxInFlightRequests=2
time=Time.SYSTEM
metricGroup=myGroup
metricType=myType
value.serializer=org.apache.kafka.common.serialization.IntegerSerializer
buffer.memory=33554432
retries=0
linger.ms=0
key.serializer=org.apache.kafka.common.serialization.StringSerializer
batch.size=1634
acks=0
GROUP_ID_CONFIG=my-group
AUTO_OFFSET_RESET_CONFIG=earliest
VALUE_DESERIALIZER_CLASS_CONFIG=org.apache.kafka.common.serialization.IntegerDeserializer
KEY_DESERIALIZER_CLASS_CONFIG=org.apache.kafka.common.serialization.StringDeserializer
Partition=1
replication=3

****************************
 
 
 
 
 
              
   
