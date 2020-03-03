package common.batch;

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
  	<listener ref="batchItemStepListener" />
</listeners>
 *  
 * @author Sam Wu
 *
 */
@Named
public class BatchItemStepListener extends AbstractStepListener {

	@Inject
	private JobContext jobContext;
	private long startTime;
	
	@Override
	public void beforeStep() throws Exception {
		System.out.println("beforeStep");
		startTime = System.currentTimeMillis();		
	}

	@Override
	public void afterStep() throws Exception {
		System.out.println("afterStep");
		long endTime = System.currentTimeMillis();
		long duration = endTime - startTime;
		System.out.println(jobContext.getJobName() + " completed in " + duration + " milliseconds");
	}
	
}
