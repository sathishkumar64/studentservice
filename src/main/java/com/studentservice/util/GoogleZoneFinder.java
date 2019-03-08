package com.studentservice.util;

import java.io.IOException;
import java.security.GeneralSecurityException;
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
import com.google.api.services.compute.Compute;
import com.google.api.services.compute.ComputeScopes;
import com.google.api.services.compute.model.InstanceGroupAggregatedList;
import com.google.api.services.compute.model.InstanceGroupsScopedList;


@Service
public class GoogleZoneFinder {

	 private static final String PROJECT_ID = "sapient-si-dsst-184990";
	 private static final String APPLICATION_NAME = "";
	 private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	 private static final List<String> SCOPES = Arrays.asList(ComputeScopes.COMPUTE_READONLY);
	
	 Logger logger = LoggerFactory.getLogger(this.getClass());
	
	 
	 public void printInstances() {		
		 logger.info("GoogleZoneFinder.....................................");
		 	Compute computeService;
			try {
				computeService = createComputeService();
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
				  
			} catch (GeneralSecurityException |IOException e) {			
				e.printStackTrace();
			}
		   
		  
		   
		  }
	 
	public static Compute createComputeService() throws IOException, GeneralSecurityException{
		    HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();		   
		    GoogleCredential credential = GoogleCredential.getApplicationDefault();
		    if (credential.createScopedRequired()) {
		      credential =credential.createScoped(SCOPES);
		    }
		    return new Compute.Builder(httpTransport, JSON_FACTORY, credential).setApplicationName(APPLICATION_NAME).build();
		 }
	 
	 
	

}
