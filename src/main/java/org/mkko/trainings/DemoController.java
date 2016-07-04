/**
 * 
 */
package org.mkko.trainings;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mkokott
 *
 */
@RestController
public class DemoController {

	@RequestMapping("/")
	public String index(){
		
		return "Greetings from Spring Boot!";
	}
}
