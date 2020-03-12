package common.batch;

import javax.batch.api.chunk.ItemProcessor;
import javax.inject.Named;

/**
 * An ItemProcessor is executed after an ItemReader has finished.
 * 
 * @author Sam Wu
 *
 */
@Named
public class BatchItemProcessor implements ItemProcessor {

	/**
	 * Change the return type of this method to the type of object (JsonOject, String, etc) you are processing
	 * Process one item returned from an ItemReader
	 */
	@Override
	public Object processItem(Object item) throws Exception {

		return null;
	}

}
