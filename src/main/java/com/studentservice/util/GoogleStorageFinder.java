package com.studentservice.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.common.collect.Lists;

@Service
public class GoogleStorageFinder {
	
	private static final String BUCKET_NAME = "student_image";		

	Logger logger = LoggerFactory.getLogger(this.getClass());	


	
	public void getObject(String studentName) throws FileNotFoundException, IOException {		
		
		  GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream("D:\\COE_Repo\\studentservice\\src\\main\\resources\\sapient-si-dsst-184990-c5ac3eeaa81d.json"))
			        .createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));
			  Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();

	//	Storage storage = StorageOptions.getDefaultInstance().getService();		
		
		Blob blob = null;
			  
		if(studentName!=null) {			
			 blob = storage.get(BlobId.of(BUCKET_NAME, "Capture.PNG"));				
			 logger.info("Student image info {}.....",blob);			 
			 Path destFilePath = Paths.get("tmp/"+blob.getName());				
			 blob.downloadTo(destFilePath);			
		}
	}
	
	
	public static void main(String[] args) {
		
		try {
			new GoogleStorageFinder().getObject("name");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
