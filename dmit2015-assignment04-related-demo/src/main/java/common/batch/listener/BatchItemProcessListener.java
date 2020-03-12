package common.batch.listener;

import javax.batch.api.chunk.listener.AbstractItemProcessListener;
import javax.inject.Named;

/***
 * This executes before and after an item is processed by an item processor.
 * To apply this listener to a chunk you must define a listener element in the Job Specification Language (JSL) file
 * INSIDE the step element as follows:
 *  
 <step id="step1" >
    	<listeners>
    		<listener ref="batchItemProcessListener" />
   		</listeners>
 </step>  		
 *
 * @author Sam Wu
 *
 */
@Named
public class BatchItemProcessListener extends AbstractItemProcessListener {

	@Override
	public void beforeProcess(Object item) throws Exception {
		System.out.println("beforeProcess: " + item);
	}

	@Override
	public void afterProcess(Object item, Object result) throws Exception {
		System.out.println("afterProcess with item: " + item + " and result: " + result);
	}

	@Override
	public void onProcessError(Object item, Exception ex) throws Exception {
		System.out.println("onProcessError with item: " + item + " and exception " + ex.getMessage());
	}	
	
}
