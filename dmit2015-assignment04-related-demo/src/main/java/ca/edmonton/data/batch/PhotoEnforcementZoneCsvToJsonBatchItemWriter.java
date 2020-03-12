package ca.edmonton.data.batch;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;

import javax.batch.api.chunk.AbstractItemWriter;
import javax.batch.runtime.context.JobContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named
public class PhotoEnforcementZoneCsvToJsonBatchItemWriter extends AbstractItemWriter {

	@Inject
	private JobContext jobContext;
	
	@Override
	public void writeItems(List<Object> items) throws Exception {
		String outputFile = (String) jobContext.getProperties().get("output_file");
	
		try (PrintWriter writer = new PrintWriter(new FileWriter(outputFile,true))) {
			for (Object singleItem : items) {
				String line = (String) singleItem;
				writer.println(line);					
			}
			writer.flush();
		} catch(Exception e) {
			System.err.println("Error: " + e.getMessage());
			throw new Exception(e);
		}		

	}

}
