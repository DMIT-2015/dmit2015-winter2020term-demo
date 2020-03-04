package common.batch;

import javax.batch.api.AbstractBatchlet;
import javax.inject.Named;
import javax.transaction.Transactional;

/**
 * Batchlets are task oriented step that is called once.
 * It either succeeds or fails. If it fails, it CAN be restarted and it runs again.
 * 
 * @author Sam Wu
 *
 */
@Named
public class TaskOrientedBatchlet extends AbstractBatchlet {
	
	/**
	 * Perform a task and return "COMPLETED" if the job has successfully completed
	 * otherwise return "FAILED" to indicate the job failed to complete.
	 */
	@Transactional
	@Override
	public String process() throws Exception {
//			return "FAILED";	// The job has failed to complete
				
		return "COMPLETED";		// The job has successfully completed
	}
}
