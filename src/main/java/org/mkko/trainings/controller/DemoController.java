/**
 * 
 */
package org.mkko.trainings.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mkko.trainings.data.SensorReading;
import org.mkko.trainings.data.SensorValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cloudant.client.api.Database;
import com.cloudant.client.api.views.ViewRequest;
import com.cloudant.client.api.views.ViewResponse;
import com.cloudant.client.api.views.ViewResponse.Row;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author mkokott
 *
 */
@RestController
public class DemoController {

	@Autowired
	private Database db;
	
	private Long startTime = 1468225931215L;

	@RequestMapping("/")
	public String index(){

		return "Greetings from Spring Boot!";
	}

	@RequestMapping("/cloudant-metrics/{metric}")
	public String getDatabaseMetrics(@PathVariable String metric){

		switch (metric.toLowerCase()){

		case "doccount":
			return "" + db.info().getDocCount();
		case "mock-query":
			try {
				ViewResponse<String, SensorReading> results = db.getViewRequestBuilder("jarvis", "byTimestamp")
						.newRequest(com.cloudant.client.api.views.Key.Type.STRING, SensorReading.class)
						.startKey("1468225931215")
						.endKey("1468229931215")
						.includeDocs(true)
						.build()
						.getResponse();
				for (Row row : results.getRows()){
					
					System.out.println((String)row.getKey());
					SensorReading values = (SensorReading) row.getValue();
					
					System.out.println(values.getPayload());
				}
				return "" + results.getDocs().size();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				return e.getMessage();
			}

		default:
			return "metric " + metric + " not available";
		}
	}
	
	public String getSensorReadingsForTimePeriode(String startTime, String endTime){
		
		try {
			ViewResponse<String, SensorReading> results = db.getViewRequestBuilder("jarvis", "byTimestamp")
					.newRequest(com.cloudant.client.api.views.Key.Type.STRING, SensorReading.class)
					.startKey(startTime)
					.endKey(endTime)
					.includeDocs(true)
					.build()
					.getResponse();
			return "" + results.getDocs().size();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return e.getMessage();
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/injectAll")
	public void run() throws InterruptedException, IOException {
		
		try {
			ViewRequest<String, SensorValues> request = db.getViewRequestBuilder("jarvis", "byTimestamp")
					.newPaginatedRequest(com.cloudant.client.api.views.Key.Type.STRING, SensorValues.class)
					.includeDocs(true)
					.build();
			
			ViewResponse<String,SensorValues> response = request.getResponse();
			String nextPageToken = response.getNextPageToken();
			
			while (nextPageToken != null){
				
				response = request.getResponse(nextPageToken);
				nextPageToken = response.getNextPageToken();
				
				List<Map>documents = response.getDocsAs(Map.class);
				
				for (Map doc : documents){
					
					Map<String, String> payload = new HashMap<String, String>();
					
					try{
						payload = (Map<String, String>) doc.get("payload");
					}
					catch(Exception e){
						// just for docs without payload...
					}
					
					System.out.println(doc.get("payload"));
					
					if (payload != null)
						this.sendSensorData(payload);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
	}
	
	
//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	@RequestMapping("/injectAllTweets")
//	public void twitterIngress() throws InterruptedException, IOException {
//		
//		try {
//			ViewRequest<String, SensorValues> request = db.getViewRequestBuilder("jarvis", "byTimestamp")
//					.newPaginatedRequest(com.cloudant.client.api.views.Key.Type.STRING, SensorValues.class)
//					.includeDocs(true)
//					.build();
//			
//			ViewResponse<String,SensorValues> response = request.getResponse();
//			String nextPageToken = response.getNextPageToken();
//			
//			while (nextPageToken != null){
//				
//				response = request.getResponse(nextPageToken);
//				nextPageToken = response.getNextPageToken();
//				
//				List<Map>documents = response.getDocsAs(Map.class);
//				
//				for (Map doc : documents){
//					
//					Map<String, String> payload = new HashMap<String, String>();
//					
//					try{
//						payload = (Map<String, String>) doc.get("payload");
//					}
//					catch(Exception e){
//						// just for docs without payload...
//					}
//					
//					System.out.println(doc.get("payload"));
//					
//					if (payload != null)
//						this.sendSensorData(payload);
//				}
//			}
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			System.out.println(e.getMessage());
//		}
//	}
	
	
	
	private void sendSensorData(Map<String, String> payload){
		
		try {
	        URL url = new URL("http://team-jarvis-node-red.mybluemix.net/sensorIngress");
	        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	        String json = new ObjectMapper().writeValueAsString(payload);

	        connection.setDoInput(true);
	        connection.setDoOutput(true);
	        connection.setRequestMethod("POST");
	        connection.setRequestProperty("Accept", "application/json");
	        connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
	        OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
	        writer.write(json);
	        writer.close();
	        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	        StringBuffer jsonString = new StringBuffer();
	        String line;
	        while ((line = br.readLine()) != null) {
	                jsonString.append(line);
	        }
	        br.close();
	        connection.disconnect();
	    } catch (Exception e) {
	            throw new RuntimeException(e.getMessage());
	    }
	}
	
	@RequestMapping("/doNothing")
	public void doNothing(){
		
		
	}
}
