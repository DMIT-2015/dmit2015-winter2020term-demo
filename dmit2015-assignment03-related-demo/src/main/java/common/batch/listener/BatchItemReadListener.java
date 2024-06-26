package common.batch.listener;

import javax.batch.api.chunk.listener.AbstractItemReadListener;
import javax.inject.Named;

/***
 * This executes before and after an item is read by an item reader
 * To apply this listener to a chunk you must define a listener element in the Job Specification Language (JSL) file
 * INSIDE the step element as follows:
 *  
 <step id="step1" >
    	<listeners>
    		<listener ref="batchItemReadListener" />
   		</listeners>
 </step>  		
 *
 * @author Sam Wu
 *
 */
@Named
public class BatchItemReadListener extends AbstractItemReadListener {

	@Override
	public void afterRead(Object item) throws Exception {
		System.out.println("afterRead: " + item);
	}

	@Override
	public void beforeRead() throws Exception {
		System.out.println("beforeRead");
	}

	@Override
	public void onReadError(Exception ex) throws Exception {
		System.out.println("onReadError: " + ex.getMessage());
	}
	
}
