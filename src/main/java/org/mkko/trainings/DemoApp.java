/**
 * 
 */
package org.mkko.trainings;

import java.io.IOException;

import org.mkko.trainings.cloudant.CloudantClientMgr;
import org.mkko.trainings.controller.DemoController;
import org.mkko.trainings.workers.SensorReadingsFetcher;
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
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException, InterruptedException {

		SpringApplication.run(DemoApp.class, args);
	}

	@Bean
	public Database cloudantDatabase(){

		return CloudantClientMgr.getDataBase();
	}
}
