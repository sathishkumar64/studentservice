package com.studentservice.util;

import java.io.IOException;
import java.io.InputStream;
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

@Service
public class GoogleZoneFinder {

	private static final String PROJECT_ID = "sapient-si-dsst-184990";

	Logger logger = LoggerFactory.getLogger(this.getClass());

	public void printInstances() {
		logger.info("GoogleZoneFinder.....................................");
		Compute computeService = null;
		try {

			computeService = createComputeService();

			Compute.InstanceGroups.AggregatedList request = computeService.instanceGroups().aggregatedList(PROJECT_ID);
			logger.info(request.getUriTemplate());
			InstanceGroupAggregatedList response;
			do {
				response = request.execute();
				
				logger.info(response.toPrettyString());
				
				if (response.getItems() == null) {
					continue;
				}
				for (Map.Entry<String, InstanceGroupsScopedList> item : response.getItems().entrySet()) {
					logger.info(item.getKey() + ": " + item.getValue());
				}
				request.setPageToken(response.getNextPageToken());
			} while (response.getNextPageToken() != null);

		} catch (GeneralSecurityException | IOException e) {
			e.printStackTrace();
		}

	}

	public Compute createComputeService() throws GeneralSecurityException, IOException {
		
		
		HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
	    JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();

	  /*  ClassLoader classLoader = getClass().getClassLoader();
		InputStream inputStream = classLoader.getResourceAsStream("sapient-si-dsst-184990-8f4fa5c22ef5.json");
	    GoogleCredential credential = GoogleCredential.fromStream(inputStream);*/
	    
	    GoogleCredential credential = GoogleCredential.getApplicationDefault();
	    
	    if (credential.createScopedRequired()) {
	      credential =
	          credential.createScoped(Arrays.asList("https://www.googleapis.com/auth/cloud-platform"));
	    }
	    
	    logger.info(credential.getServiceAccountId());	
	    logger.info(credential.getServiceAccountPrivateKeyId());

	    return new Compute.Builder(httpTransport, jsonFactory, credential)
	        .setApplicationName("Google-ComputeSample/0.1")
	        .build();

	}
	
	public static void main(String[] args) {
		new GoogleZoneFinder().printInstances();
	}
}
