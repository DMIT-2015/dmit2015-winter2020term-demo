package common.batch;

import java.io.Serializable;

import javax.batch.api.chunk.AbstractItemReader;
import javax.inject.Named;

/**
 * The sequence for a batch chunk step are: ItemReader --> ItemProcessor --> ItemWriter
 * 
 * @author Sam Wu
 *
 */
@Named
public class BatchItemReader extends AbstractItemReader {
	
	/**
	 * The open method is used to open a data source for reading.
	 */
	@Override
	public void open(Serializable checkpoint) throws Exception {

	}

	/**
	 * Read from the data source one item at a time.
	 * Return null to trigger the end of the file.
	 */
	@Override
	public Object readItem() throws Exception {

		return null;
	}

}
