package com.studentservice.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Arrays;
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
import com.google.api.services.compute.model.InstanceGroupAggregatedList;
import com.google.api.services.compute.model.InstanceGroupsScopedList;
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
		Compute computeService = null;
		try {

			computeService = createComputeService();

			Compute.InstanceGroups.AggregatedList request = computeService.instanceGroups().aggregatedList(PROJECT_ID);
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

	public Compute createComputeService() {
		HttpTransport httpTransport = null;		
		GoogleCredential credential =new GoogleCredential();	
		 Compute compute=null;
		try {	
			 httpTransport = GoogleNetHttpTransport.newTrustedTransport();	
			 ClassLoader classLoader = getClass().getClassLoader();
			 InputStream inputStream = classLoader.getResourceAsStream("sapient-si-dsst-184990-9c9f03224da8.json");
			 GoogleCredentials credentials = ComputeEngineCredentials.fromStream(inputStream);
			 logger.info(credentials.getAuthenticationType());		
			 logger.info(credentials.getAccessToken().getTokenValue());				
			 credential.setAccessToken(credentials.getAccessToken().getTokenValue());			 
			 logger.info(credential.getAccessToken());	
			 if (credential.createScopedRequired()) {
				credential.createScoped(Arrays.asList("https://www.googleapis.com/auth/cloud-platform"));
			 }
			 compute = new Compute.Builder(httpTransport, JSON_FACTORY,credential).build();
		} catch (GeneralSecurityException | IOException e) {		
			e.printStackTrace();
		}		
		return compute;
	}
	
}
