/**
 * 
 */
package org.mkko.trainings;

import org.mkko.trainings.cloudant.CloudantClientMgr;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.cloudant.client.api.Database;


/**
 * @author mkokott
 *
 */
@SpringBootApplication
public class DemoApp {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		SpringApplication.run(DemoApp.class, args);
		
		Database cloudantClient = CloudantClientMgr.getDataBase();
		System.out.println(cloudantClient.info());
	}
}
