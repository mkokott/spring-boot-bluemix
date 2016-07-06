/**
 * 
 */
package org.mkko.trainings;

import org.mkko.trainings.cloudant.CloudantClientMgr;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

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

		Database cloudantDb = CloudantClientMgr.getDataBase();
		System.out.println(cloudantDb.info());
	}

	@Bean
	public Database cloudantDatabase(){

		return CloudantClientMgr.getDataBase();
	}
}
