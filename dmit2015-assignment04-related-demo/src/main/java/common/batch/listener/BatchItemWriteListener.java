package common.batch.listener;

import java.util.List;

import javax.batch.api.chunk.listener.AbstractItemWriteListener;
import javax.inject.Named;

/**
 * This executes before and after an items is written by an item writer
 * To apply this listener to a chunk you must define a listener element in the Job Specification Language (JSL) file
 * INSIDE the step element as follows:
 *  
 <step id="step1" >
    	<listeners>
    		<listener ref="batchItemWriteListener" />
    		
   		</listeners>
 </step>  	
 *	
 * @author Sam Wu
 *
 */
@Named
public class BatchItemWriteListener extends AbstractItemWriteListener {

	@Override
	public void beforeWrite(List<Object> items) throws Exception {
		System.out.println("beforeWrite:" + items);
	}

	@Override
	public void afterWrite(List<Object> items) throws Exception {
		System.out.println("afterWrite: " + items);
	}

	@Override
	public void onWriteError(List<Object> items, Exception ex) throws Exception {
		System.out.println("onWriteError with items: " + items + " and exception " + ex.getMessage());
	}
		
}
