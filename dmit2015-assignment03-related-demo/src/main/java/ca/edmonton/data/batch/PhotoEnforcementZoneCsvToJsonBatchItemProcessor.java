package ca.edmonton.data.batch;

import javax.batch.api.chunk.ItemProcessor;
import javax.batch.runtime.context.JobContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

import ca.edmonton.data.jpa.PhotoEnforcementZone;

@Named
public class PhotoEnforcementZoneCsvToJsonBatchItemProcessor implements ItemProcessor {

	@Inject
	private JobContext jobContext;
	
	@Override
	public String processItem(Object item) throws Exception {
		String line = (String) item;
		final String delimiter = ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)";
		String[] values = line.split(delimiter);

		PhotoEnforcementZone model = new PhotoEnforcementZone();
		model.setLocationDescription(values[0]);
		model.setSpeedLimit(Integer.parseInt(values[1]));
		model.setReasonCodes(values[2].replaceAll("[\"()]", ""));
				
		Jsonb jsonb = JsonbBuilder.create();
		String jsonString = jsonb.toJson(model);
		long itemsProcessed = (long) jobContext.getTransientUserData();
		System.out.println("Processing item " + itemsProcessed);
		if (itemsProcessed > 1) {
			jsonString = "," + jsonString;
		}
		
		return jsonString;
	}

}
