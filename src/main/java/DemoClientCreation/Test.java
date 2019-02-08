package DemoClientCreation;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.utils.Time;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.kafka.AWSKafka;
import com.amazonaws.services.kafka.AWSKafkaClientBuilder;
import com.amazonaws.services.kafka.model.DescribeClusterRequest;
import com.amazonaws.services.kafka.model.DescribeClusterResult;
import com.amazonaws.services.kafka.model.ListClustersRequest;
import com.amazonaws.services.kafka.model.ListClustersResult;

import kafka.zk.AdminZkClient;
import kafka.zk.KafkaZkClient;
import utilsClass.DemoDataGeneration;
import utilsClass.RandomDataGenerator;


public class Test {

	public static void main(String args[]) throws IOException
	{
		
		final AWSKafka kf = AWSKafkaClientBuilder.defaultClient();
		
		final AWSKafka kf1= AWSKafkaClientBuilder.standard().withRegion(Regions.US_EAST_1).withCredentials(new ProfileCredentialsProvider("default")).build();
		
		
		ListClustersRequest list_cluster_request = new ListClustersRequest();
		ListClustersResult  list_cluster_result = kf1.listClusters(list_cluster_request);
		
		int num_clusters= list_cluster_result.getClusterInfoList().size();
		System.out.println("This Account has " + num_clusters+" Clusters\n\n");
		System.out.println("The Name and ARN of Clusters are");
		
		
		for(int i=0; i<num_clusters;i++)
		{
			System.out.println((i+1) +"  "+ list_cluster_result.getClusterInfoList().get(i).getClusterName()+"   "+ list_cluster_result.getClusterInfoList().get(i).getClusterArn() );
		}

		
		
		
		System.out.println("\n\nDescribing Cluster one at a time ");
		
		for(int i=0;i<num_clusters;i++)
		{
			DescribeClusterRequest request = new DescribeClusterRequest().withClusterArn(list_cluster_result.getClusterInfoList().get(i).getClusterArn());
			DescribeClusterResult result= kf1.describeCluster(request);
			
			System.out.println("THE Cluster description for cluster "+ (i+1) + " is: "+ result.getClusterInfo().toString()+"\n");
		}
		
	//	Properties topicConfiguration = new Properties();
		// Creating Producer... 
		
		String zookeeperHost = "10.0.0.163:2181,10.0.2.134:2181,10.0.1.65:2181"; 
		Boolean isSucre = false;
		int sessionTimeoutMs = 200000;
		int connectionTimeoutMs = 15000;
		int maxInFlightRequests = 10;
		Time time = Time.SYSTEM;
		String metricGroup = "myGroup";
		String metricType = "myType";

		
	//	ZkUtils zkClient = new ZkClient("localhost:2181", 10000, 10000, ZKStringSerializer$.MODULE$);
		
		
		KafkaZkClient zkClient = KafkaZkClient.apply(zookeeperHost,isSucre,sessionTimeoutMs,
		                connectionTimeoutMs,maxInFlightRequests,time,metricGroup,metricType);

		AdminZkClient adminZkClient = new AdminZkClient(zkClient);

		String topicName1 = "myTopic";
		int partitions = 3;
		int replication = 1;
		Properties topicConfig = new Properties();

	//	adminZkClient.createTopic(topicName1,partitions,replication,
	//	            topicConfig,RackAwareMode.Disabled$.MODULE$);
		
		
		 final String TOPIC1 = "myTopic";
		    final String BOOTSTRAP_SERVERS1 =
		            "10.0.1.203:9092,10.0.0.236:9092,10.0.2.196:9092";
		    
		    
		    Properties props = new Properties();
		    props.put("bootstrap.servers", "10.0.1.203:9092,10.0.0.236:9092,10.0.2.196:9092");
		    props.put("acks", "all");
		    props.put("retries", 0);
		    props.put("batch.size", 16384);
		    props.put("linger.ms", 1);
		    props.put("buffer.memory", 33554432);
		    props.put("key.serializer", "org.apache.kafka.common.serialization.IntegerSerializer");
		    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		    
		    DemoDataGeneration obj = new DemoDataGeneration();
		    Properties prop = new Properties();
			
			InputStream input = null;
			
			input = new FileInputStream("src/main/java/DemoClientCreation/config.properties");
			prop.load(input);
			System.out.println(prop.getProperty("TopicName"));
			
			input.close();
		    Producer<Integer, String> producer = new KafkaProducer<Integer,String>(props);
		    for(int i = 0; i < 200; i++)
		    {
		    	int key_gen=new RandomDataGenerator().getRandomNumberInRange(1, 5);
		    	
		    	String comp = obj.DataGeneration(key_gen);
		        producer.send(new ProducerRecord<Integer, String>(prop.getProperty("TopicName"),key_gen,comp));
		        	//	Integer.toString(i), Integer.toString(i)));
		        System.out.println("Indput data my-topic"+key_gen+" "+comp+" " );
		        
		    }
		    producer.close();
		    
		    Properties consumerConfig = new Properties();
		    consumerConfig.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, 
		    		"10.0.1.203:9092,10.0.0.236:9092,10.0.2.196:9092");
		       consumerConfig.put(ConsumerConfig.GROUP_ID_CONFIG, "my-group");
		       consumerConfig.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		       consumerConfig.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, 
		    		   "org.apache.kafka.common.serialization.StringDeserializer");
		       consumerConfig.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
		    
		       KafkaConsumer<byte[], byte[]> consumer = new KafkaConsumer<byte[], byte[]>(consumerConfig);
		     //  consumer.subscribe(Collections.singletonList("test-topic"), ConsumerRebalanceListener);
		       
		       consumer.subscribe(Collections.singleton("my-topic"));
		       while (true) {
		           ConsumerRecords<byte[], byte[]> records = consumer.poll(1000);
		           for (ConsumerRecord<byte[], byte[]> record : records) {
		               System.out.printf("Received Message topic =%s, partition =%s, offset = %d, key = %s, value = %s\n", record.topic(), record.partition(), record.offset(), record.key(), record.value());
		           }

		           consumer.commitSync();
		       }
		       
	}
	
}
