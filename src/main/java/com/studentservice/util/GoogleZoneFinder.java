package com.studentservice.util;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.compute.Compute;
import com.google.api.services.compute.model.InstanceGroupAggregatedList;
import com.google.api.services.compute.model.InstanceGroupsScopedList;
import com.google.auth.oauth2.ComputeEngineCredentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.api.gax.paging.Page;


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
		GoogleCredentials credentials = null;
		
		try {
			
			httpTransport = GoogleNetHttpTransport.newTrustedTransport();
	
			 credentials = ComputeEngineCredentials.create();
			if (credentials.createScopedRequired()) {
				credentials.createScoped(Arrays.asList("https://www.googleapis.com/auth/cloud-platform"));
			}
			
			Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();

		    System.out.println("Buckets:");
		    Page<Bucket> buckets = storage.list();
		    for (Bucket bucket : buckets.iterateAll()) {
		      System.out.println(bucket.toString());
		    }
		
		
		} catch (GeneralSecurityException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		Compute compute = new Compute.Builder(httpTransport, JSON_FACTORY, (HttpRequestInitializer) credentials).setApplicationName(APPLICATION_NAME).build();
		return compute;
	}

}
