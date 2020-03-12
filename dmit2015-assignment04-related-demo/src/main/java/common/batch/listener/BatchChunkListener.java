package common.batch.listener;

import javax.batch.api.chunk.listener.AbstractChunkListener;
import javax.inject.Named;

/***
 * This executes at the beginning and end of chunk processing.
 * To apply this listener to a chunk you must define a listener element in the Job Specification Language (JSL) file
 * INSIDE the step element as follows:
 *  
 <step id="step1" >
    	<listeners>
    		<listener ref="batchChunkListener" />
   		</listeners>
 </step>  		
 * 
 * @author Sam Wu
 *
 */
@Named
public class BatchChunkListener extends AbstractChunkListener {

	@Override
	public void beforeChunk() throws Exception {
		System.out.println("beforeChunk");
	}

	@Override
	public void onError(Exception ex) throws Exception {
		System.out.println("onError: " + ex.getMessage());	
	}

	@Override
	public void afterChunk() throws Exception {
		System.out.println("afterChunk");
	}
}
