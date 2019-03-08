package com.studentservice.util;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.api.services.compute.Compute;
import com.google.api.services.compute.ComputeScopes;
import com.google.api.services.compute.model.InstanceGroupAggregatedList;
import com.google.api.services.compute.model.InstanceGroupsScopedList;
import com.google.auth.Credentials;
import com.google.auth.oauth2.ComputeEngineCredentials;
import com.google.auth.oauth2.GoogleCredentials;	

@Service
public class GoogleZoneFinder {

	 private static final String PROJECT_ID = "sapient-si-dsst-184990";
	 private static final String APPLICATION_NAME = "";
	 private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	
	
	 Logger logger = LoggerFactory.getLogger(this.getClass());
	
	 
	 public void printInstances() {		
		 logger.info("GoogleZoneFinder.....................................");
		 	Compute computeService=null;
			try {
				try {
					computeService = createComputeService();
				} catch (GeneralSecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				 Credentials myCredentials = GoogleCredentials.getApplicationDefault();
				 FixedCredentialsProvider.create(myCredentials);
				
				Compute.InstanceGroups.AggregatedList request =computeService.instanceGroups().aggregatedList(PROJECT_ID);
				InstanceGroupAggregatedList response;
			    do {
			      response = request.execute();
			      if (response.getItems() == null) {
			        continue;
			      }
			      for (Map.Entry<String, InstanceGroupsScopedList> item : response.getItems().entrySet()) {			     
			    	  logger.info(item.getKey() + ": " + item.getValue());
			      }
			      request.setPageToken(response.getNextPageToken());
			    } while (response.getNextPageToken() != null);
				  
			} catch (IOException e) {			
				e.printStackTrace();
			}
		   
		  
		   
		  }
	 
	public static Compute createComputeService() throws IOException, GeneralSecurityException{
		    HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
		     
		    GoogleCredentials credentials = ComputeEngineCredentials.create();		    
		    if (credentials.createScopedRequired()) {
		    	credentials.createScoped(Arrays.asList("https://www.googleapis.com/auth/cloud-platform"));
		    }
		    		    
		    GoogleCredential credential = GoogleCredential.getApplicationDefault();
		      if (credential.createScopedRequired()) {
		        List<String> scopes = new ArrayList<>();
		   
		        scopes.add(ComputeScopes.COMPUTE);
		        credential = credential.createScoped(scopes);
		      }		    
		    Compute compute = new Compute.Builder(httpTransport, JSON_FACTORY, credential)
		            .setApplicationName(APPLICATION_NAME)
		            .build();
		    
		   return new Compute.Builder(httpTransport, JSON_FACTORY,credential).setApplicationName(APPLICATION_NAME).build();
		 }
	 
	 
	

}
