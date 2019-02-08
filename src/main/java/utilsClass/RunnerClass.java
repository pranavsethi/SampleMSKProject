package utilsClass;


import java.io.FileNotFoundException;
import java.io.IOException;

import DemoClusterOperations.clusterOperations;
import demoConsumer.ConsumerClass;
import demoProducer.CreateTopic;
import demoProducer.ProducerClass;

public class RunnerClass {

	


	public static void main(String args[]) throws FileNotFoundException
	{
		
	
		Popertiesfetcher obj = null;
		try {
			obj = new Popertiesfetcher();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		System.out.println(obj.getTopicName());
		
	
		// Calling the cluster Operations.. which would include List Operation and Describe cluster
		System.out.println("LISTING THE CLUSTERS IN THE ACCOUNT.. ");
		
		new clusterOperations().ListClusterOperation();
		new clusterOperations().DescribeClusters();
		
	
		
		//Calling the create topic 
		try {
			new CreateTopic().create();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		// Calling the producer..
		try {
			
		new	ProducerClass().ProduceSampleData();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//Calling the consumer..
		try {
			System.out.println("Calling the consumer");
			new ConsumerClass().Consumer();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	}

}
