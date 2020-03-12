package ca.edmonton.data.batch;

import java.io.FileWriter;
import java.io.PrintWriter;

import javax.batch.api.listener.AbstractStepListener;
import javax.batch.runtime.context.JobContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * This executes before and after a step execution runs.
 * To apply this listener to a batch job you must define a listener element in the Job Specification Language (JSL) file
 * BEFORE the step element as follows:
 *  
<listeners>
  	<listener ref="photoEnforcementZoneCsvToJsonBatchItemStepListener" />
</listeners>
 *  
 * @author Sam Wu
 *
 */
@Named
public class PhotoEnforcementZoneCsvToJsonBatchItemStepListener extends AbstractStepListener {

	@Inject
	private JobContext jobContext;
	private long startTime;
	private long itemsProcessed = 0L;
	
	@Override
	public void beforeStep() throws Exception {
		System.out.println("beforeStep");
		startTime = System.currentTimeMillis();
		
		jobContext.setTransientUserData(itemsProcessed);
		
		// Insert the [ character at the beginning of the file to indicate the start of an JSON array
		String outputFile = (String) jobContext.getProperties().get("output_file");
		try (PrintWriter writer = new PrintWriter(new FileWriter(outputFile,false))) {
			writer.write("[");
			writer.flush();
		} catch(Exception e) {
			System.err.println("Error: " + e.getMessage());
			throw new Exception(e);
		}	
	}

	@Override
	public void afterStep() throws Exception {
		System.out.println("afterStep");
		long endTime = System.currentTimeMillis();
		long duration = endTime - startTime;
		System.out.println(jobContext.getJobName() + " completed in " + duration + " milliseconds");
		startTime = System.currentTimeMillis();
		
		// Insert the ] at the end of the file to indicate the end of an JSON array
		String outputFile = (String) jobContext.getProperties().get("output_file");
		try (PrintWriter writer = new PrintWriter(new FileWriter(outputFile,true))) {
			writer.write("]");
			writer.flush();
		} catch(Exception e) {
			System.err.println("Error: " + e.getMessage());
			throw new Exception(e);
		}	
	}
	
}
