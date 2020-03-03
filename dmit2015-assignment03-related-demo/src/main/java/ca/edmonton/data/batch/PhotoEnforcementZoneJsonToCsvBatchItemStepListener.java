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
  	<listener ref="photoEnforcementZoneJsonToCsvBatchItemStepListener" />
</listeners>
 *  
 * @author Sam Wu
 *
 */
@Named
public class PhotoEnforcementZoneJsonToCsvBatchItemStepListener extends AbstractStepListener {

	@Inject
	private JobContext jobContext;
	private long startTime;
	
	@Override
	public void beforeStep() throws Exception {
		System.out.println("beforeStep");
		startTime = System.currentTimeMillis();
		
		// Write a list of column names to the first line of the CSV file
		String outputFile = (String) jobContext.getProperties().get("output_file");
		try (PrintWriter writer = new PrintWriter(new FileWriter(outputFile,false))) {
			writer.println("Location Description,Speed Limit,Reason Code(s)");
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
	}
}
