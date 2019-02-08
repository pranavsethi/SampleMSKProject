package demoProducer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import com.amazonaws.services.glue.model.GetClassifierRequest;

import DemoClientCreation.Test;

public class TEST {
	
	public static void main(String args[]) throws IOException
	{
		
		Properties prop = new Properties();
		OutputStream output = null;
		InputStream input = null;
		
		input = new FileInputStream("src/main/java/DemoClientCreation/config.properties");
		prop.load(input);
		System.out.println(prop.getProperty("TopicName"));
		
		input.close();



//		output = new FileOutputStream("src/main/resources/config.properties");
//
//		// set the properties value
//		prop.setProperty("database", "localhost");
//		prop.setProperty("dbuser", "mkyong");
//		prop.setProperty("dbpassword", "password");
//
//		// save properties to project root folder
//		prop.store(output, null);



	
  }

}
