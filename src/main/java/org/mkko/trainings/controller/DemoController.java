/**
 * 
 */
package org.mkko.trainings.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cloudant.client.api.Database;

/**
 * @author mkokott
 *
 */
@RestController
public class DemoController {
	
	@Autowired
	private Database db;
	
	@RequestMapping("/")
	public String index(){

		return "Greetings from Spring Boot!";
	}
	
	@RequestMapping("/cloudant-metrics/{metric}")
	public String getDatabaseMetrics(@PathVariable String metric){
		
		switch (metric.toLowerCase()){
		
		case "doccount":
			return "" + db.info().getDocCount();
		default:
			return "metric " + metric + " not available";
		}
	}
	
}
