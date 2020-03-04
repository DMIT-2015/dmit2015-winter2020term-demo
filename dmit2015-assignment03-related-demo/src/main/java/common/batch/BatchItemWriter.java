package common.batch;

import java.util.List;

import javax.batch.api.chunk.AbstractItemWriter;
import javax.inject.Named;

/**
 * An ItemWriter is executed after an ItemProcessor has executed.
 * 
 * @author Sam Wu
 *
 */
@Named
public class BatchItemWriter extends AbstractItemWriter {

	/**
	 * Write a list of items returned from the ItemProcessor to a destination data source..
	 */
	@Override
	public void writeItems(List<Object> items) throws Exception {

	}

}
