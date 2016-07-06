/**
 * 
 */
package org.mkko.trainings.controller;

import org.mkko.trainings.data.WelcomeMessage;
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
	
	@RequestMapping("/iwashere/{name}")
	public String getWelcomeMessage(@PathVariable String name){
		
		boolean returningUser = db.contains(name);
		
		WelcomeMessage welcome = (returningUser) 
				? db.find(WelcomeMessage.class, name)
				: new WelcomeMessage(name);
				
		welcome.setNumberOfVisits(welcome.getNumberOfVisits() + 1);
		
		if (returningUser){
			db.update(welcome);
			return "hello again, " + name + ". you've been here " + welcome.getNumberOfVisits() + " times";
		}
		else{
			db.save(welcome);
			return "hello " + name + "!";
		}
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
