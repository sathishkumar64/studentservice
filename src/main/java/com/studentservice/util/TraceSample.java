package com.studentservice.util;

import java.io.IOException;

import org.springframework.stereotype.Component;

import io.opencensus.common.Scope;
import io.opencensus.exporter.trace.stackdriver.StackdriverTraceConfiguration;
import io.opencensus.exporter.trace.stackdriver.StackdriverTraceExporter;
import io.opencensus.trace.Tracer;
import io.opencensus.trace.Tracing;
import io.opencensus.trace.samplers.Samplers;

@Component
public class TraceSample {
	
	private static final Tracer tracer = Tracing.getTracer();
	
	private static final String PROJECT_ID = "sapient-si-dsst-184990";


	public static void doWork() {
		// Create a child Span of the current Span.
		try (Scope ss = tracer.spanBuilder("MyChildWorkSpan").startScopedSpan()) {
			doInitialWork();
			tracer.getCurrentSpan().addAnnotation("Finished initial work");
			doFinalWork();
		}
	}

	private static void doInitialWork() {
		// ...
		tracer.getCurrentSpan().addAnnotation("Doing initial work");
		// ...
	}

	private static void doFinalWork() {
		// ...
		tracer.getCurrentSpan().addAnnotation("Hello Sathish!");
		// ...
	}
	// [END trace_setup_java_custom_span]

	// [START trace_setup_java_full_sampling]
	public static void doWorkFullSampled() {
		try (Scope ss = tracer.spanBuilder("MyChildWorkSpan").setSampler(Samplers.alwaysSample()).startScopedSpan()) {
			doInitialWork();
			tracer.getCurrentSpan().addAnnotation("Finished initial work");
			doFinalWork();
		}
	}
	// [END trace_setup_java_full_sampling]

	// [START trace_setup_java_create_and_register]
	public static void createAndRegister() throws IOException {
		StackdriverTraceExporter.createAndRegister(StackdriverTraceConfiguration.builder().build());
	}
	// [END trace_setup_java_create_and_register]

	
	// [START trace_setup_java_register_exporter]
	public static void createAndRegisterGoogleCloudPlatform(String projectId) throws IOException {
		StackdriverTraceExporter
				.createAndRegister(StackdriverTraceConfiguration.builder().setProjectId(PROJECT_ID).build());
	}
}
