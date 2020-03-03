package ca.edmonton.data.batch;

import java.io.Serializable;
import java.util.Properties;

import javax.batch.api.chunk.AbstractItemReader;
import javax.batch.runtime.context.JobContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

@Named	// Create a CDI managed bean that is accessible from the JSL definition file 
		// with the default named "photoEnforcementZoneJsonToCsvBatchItemReader"
public class PhotoEnforcementZoneJsonToCsvBatchItemReader extends AbstractItemReader {

	@Inject
	private JobContext jobContext;

	private JsonArray jsonArray;	// JSON file contains an JsonArray 
	private int index = 0;			// number of items read
	
	/**
	 * The open method is used to initiate the connection toward the dataset.
	 * In this case it is point to the input file
	 */
	@Override
	public void open(Serializable checkpoint) throws Exception {
		Properties jobParametes = jobContext.getProperties();
		String inputFile = jobParametes.getProperty("input_file");
//		JsonReader jsonReader = Json.createReader(new FileReader(inputFile));
		JsonReader jsonReader = Json.createReader(getClass().getResourceAsStream(inputFile));
		
		jsonArray = jsonReader.readArray();
	}

	/**
	 * Read all JsonObject from the JsonArray.
	 * Once all JsonObject have been read then return null to trigger the end of the file.
	 */
	@Override
	public Object readItem() throws Exception {
		try {
			JsonObject currentItem = jsonArray.getJsonObject(index);
			index += 1;
			return currentItem;
		} catch(Exception ex) {
			ex.printStackTrace();
			System.out.println("no more data to read");
		}
		return null;
	}

}
