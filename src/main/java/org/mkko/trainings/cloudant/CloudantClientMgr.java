/**
 * 
 */
package org.mkko.trainings.cloudant;

import java.net.MalformedURLException;
import java.net.URL;
/**
 * @author mkokott
 *
 */
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.cloudant.client.api.ClientBuilder;
import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Component
public class CloudantClientMgr {

	private static CloudantClient cloudant = null;
	private static Database db = null;
	
	private String databaseName;
	private String user;
	private String password;
	private String url;

	@Autowired
	public CloudantClientMgr(@Value("${app.cloudant.url:http://127.0.0.1:5984}") String url,
			@Value("${app.cloudant.user:}") String user,
			@Value("${app.cloudant.password:}") String password,
			@Value("${app.cloudant.database:sample_nosql_db}") String database){
		
		this.url = url;
		this.user = user;
		this.password = password;
		this.databaseName = database;
		
		if (isRunningInCloudFoundry())
			this.setCloudFoundryEnvVars();
		
		this.initializeCloudantClient();
		this.establishDbConnection();
	}
	
	public static Database getDataBase(){
		
		return db;
	}
	
	private void establishDbConnection() {
		
		try {
			db = cloudant.database(databaseName, true);
		} catch (Exception e) {
			throw new RuntimeException("DB Not found", e);
		}
	}

	private void initializeCloudantClient() {
		
		try {
			cloudant = (isRunningInCloudFoundry()) 
					? ClientBuilder.account(user)
							.username(user)
							.password(password)
							.build() 
					:  ClientBuilder.url(new URL(url))
							.username(user)
							.password(password)
							.build();
		} catch (MalformedURLException e) {
			throw new RuntimeException("couldn't connect to database", e);
		}
	}

	private void setCloudFoundryEnvVars() {
		
		JsonObject obj = (JsonObject) new JsonParser().parse(System.getenv("VCAP_SERVICES"));
		Entry<String, JsonElement> dbEntry = null;
		Set<Entry<String, JsonElement>> entries = obj.entrySet();
		// Look for the VCAP key that holds the cloudant no sql db information
		for (Entry<String, JsonElement> eachEntry : entries) {
			if (eachEntry.getKey().toLowerCase().contains("cloudant")) {
				dbEntry = eachEntry;
				break;
			}
		}
		if (dbEntry == null) {
			throw new RuntimeException("Could not find cloudantNoSQLDB key in VCAP_SERVICES env variable");
		}

		obj = (JsonObject) ((JsonArray) dbEntry.getValue()).get(0);
		String serviceName = (String) dbEntry.getKey();
		System.out.println("Service Name - " + serviceName);

		obj = (JsonObject) obj.get("credentials");

		this.user = obj.get("username").getAsString();
		this.password = obj.get("password").getAsString();
	}
	
	private static boolean isRunningInCloudFoundry(){
		
		return System.getenv("VCAP_SERVICES") != null;
	}
}