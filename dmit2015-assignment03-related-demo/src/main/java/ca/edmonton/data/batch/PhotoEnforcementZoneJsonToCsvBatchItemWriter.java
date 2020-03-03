package ca.edmonton.data.batch;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;

import javax.batch.api.chunk.AbstractItemWriter;
import javax.batch.runtime.context.JobContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.json.JsonObject;

@Named
public class PhotoEnforcementZoneJsonToCsvBatchItemWriter extends AbstractItemWriter {

	@Inject
	private JobContext jobContext;
	
	@Override
	public void writeItems(List<Object> items) throws Exception {
		String outputFile = (String) jobContext.getProperties().get("output_file");
		
		try (PrintWriter writer = new PrintWriter(new FileWriter(outputFile,true))) {
			for (Object singleItem : items) {
				JsonObject model = (JsonObject) singleItem;				
				String reasonCodes = model.getString("reasonCodes");
				// If reasonsCodes contains commas the enclose value with double quotes
				if (reasonCodes.contains(",")) {
					reasonCodes = String.format("\"%s\"", reasonCodes);
				}
				String line = String.format("%s, %d, %s", 
					model.getString("locationDescription"),
					model.getInt("speedLimit"),
					reasonCodes);
				writer.println(line);
			}
			writer.flush();
		} catch(Exception e) {
			System.err.println("Error: " + e.getMessage());
			throw new Exception(e);
		}		

	}

}
