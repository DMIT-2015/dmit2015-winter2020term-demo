package common.batch.listener;


import javax.batch.api.listener.AbstractJobListener;
import javax.inject.Named;

/**
 * This executes before and after a job execution runs.
 * To apply this listener to a batch job you must define a listener element in the Job Specification Language (JSL) file
 * BEFORE the step element as follows:
 *  
<listeners>
  	<listener ref="batchJobListener" />
</listeners>
 *  
 * @author Sam Wu
 *
 */
@Named
public class BatchJobListener extends AbstractJobListener {

	@Override
	public void beforeJob() throws Exception {
		System.out.println("beforeJob");
	}

	@Override
	public void afterJob() throws Exception {
		System.out.println("afterJob");
	}
	
}
