package org.mkko.trainings.workers;

import java.io.IOException;

import org.mkko.trainings.data.SensorReading;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.tokens.StreamStartToken;

import com.cloudant.client.api.Database;
import com.cloudant.client.api.views.ViewResponse;

@Component
public class SensorReadingsFetcher {

	@Autowired
	private Database db;
	
	private static String DESIGN_DOC = "jarvis";
	private static String TIMESTAMP_VIEW = "byTimestamp";
	
	private static String TIMESTAMP_SEARCH_INDEX = "timestampSearch";
	private Long startTimestamp = 1468225931215L;
	
	
	
	public void run() throws IOException, InterruptedException{
		
		while (true){
			
			String startKey = "" + startTimestamp;
			String endKey = "" + (startTimestamp + 10000);
			
			ViewResponse<String, SensorReading> results = db.getViewRequestBuilder(DESIGN_DOC, TIMESTAMP_VIEW)
				.newRequest(com.cloudant.client.api.views.Key.Type.STRING, SensorReading.class)
				.startKey(startKey)
				.endKey(endKey)
				.build()
				.getResponse();
			
			
			System.out.println(results.getDocs().size());
			
			this.updateTimestamp();
			Thread.sleep(10000);
		}
	}
	
	private void updateTimestamp(){
		
		startTimestamp += 10001;
	}
}
