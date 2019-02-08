package DemoClientCreation;

import java.util.List;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.kafka.AWSKafka;
import com.amazonaws.services.kafka.AWSKafkaClientBuilder;
import com.amazonaws.services.kafka.model.DescribeClusterRequest;
import com.amazonaws.services.kafka.model.DescribeClusterResult;
import com.amazonaws.services.kafka.model.ListClustersRequest;
import com.amazonaws.services.kafka.model.ListClustersResult;

import kafka.zk.KafkaZkClient;

/**
 * 
 * Cluster Operations method 1. List Clusters 2. Describe Clusters
 * 
 * @author swetavk
 *
 */

public class clusterOperations {

	// public static AWSKafka kf = AWSKafkaClientBuilder.defaultClient();
	public  AWSKafka kf1 = AWSKafkaClientBuilder.standard().withRegion(Regions.US_EAST_1)
			.withCredentials(new ProfileCredentialsProvider("default")).build();
	

	private final  ListClustersRequest list_cluster_request = new ListClustersRequest();
	private final  ListClustersResult list_cluster_result = kf1.listClusters(list_cluster_request);
	private int num_clusters =list_cluster_result.getClusterInfoList().size();;

	/**
	 * Getting the list of clusters on your account..
	 * 
	 * @return
	 */
	public  ListClustersResult ListClusterOperation() {
		num_clusters = list_cluster_result.getClusterInfoList().size();
		System.out.println("This Account has " + num_clusters + " Clusters\n\n");
		System.out.println("The Name and ARN of Clusters are");

		for (int i = 0; i < num_clusters; i++) {
			System.out.println((i + 1) + "  " + list_cluster_result.getClusterInfoList().get(i).getClusterName() + "   "
					+ list_cluster_result.getClusterInfoList().get(i).getClusterArn());
		}

		return list_cluster_result;

	}

	/**
	 * Method which takes the listcluster result And calls describe one cluster at a
	 * time..
	 */
	public void DescribeClusters() {
		System.out.println("\n\nDescribing Cluster one at a time "+num_clusters);

		for (int i = 0; i < num_clusters; i++) {
			// new clusterOperations();
			System.out.println("Inside Describe cluster API..");

			DescribeClusterRequest request  = new DescribeClusterRequest()
					.withClusterArn(list_cluster_result.getClusterInfoList().get(i).getClusterArn());
			DescribeClusterResult result = kf1.describeCluster(request);
			System.out.println("THE Cluster description for cluster " + (i + 1) + " is: "
					+ result.getClusterInfo().toString() + "\n");
		}
	}

}
