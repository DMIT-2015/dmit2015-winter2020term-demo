package ca.edmonton.data.batch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.Properties;

import javax.batch.api.chunk.AbstractItemReader;
import javax.batch.runtime.context.JobContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named	// Create a CDI managed bean that is accessible from the JSL definition file 
		// with the default named "photoEnforcementZoneBatchItemReader"
public class PhotoEnforcementZoneCsvToJsonBatchItemReader extends AbstractItemReader {

	@Inject
	private JobContext jobContext;
	
	private BufferedReader reader;
	
	/**
	 * The open method is used to initiate the connection toward the dataset.
	 * In this case it is point toe the input file
	 */
	@Override
	public void open(Serializable checkpoint) throws Exception {
		Properties jobParametes = jobContext.getProperties();
		String inputFile = jobParametes.getProperty("input_file");
		reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(inputFile)));
		// Skip the first line as it contains field name headers
		reader.readLine();
	}

	/**
	 * Read lines of data and store each into a String object.
	 * Once all lines have been read then return null to trigger the end of the file.
	 */
	@Override
	public Object readItem() throws Exception {
		try {
			String line = reader.readLine();
			long itemsProcessed = (long) jobContext.getTransientUserData();
			itemsProcessed += 1L;
			jobContext.setTransientUserData(itemsProcessed);
			return line;
		} catch(IOException ex) {
			ex.printStackTrace();
		}
		return null;
	}

}
