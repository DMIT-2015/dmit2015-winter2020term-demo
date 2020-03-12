package ca.edmonton.data.batch;

import javax.batch.api.chunk.ItemProcessor;
import javax.inject.Named;
import javax.json.JsonObject;

@Named
public class PhotoEnforcementZoneJsonToCsvBatchItemProcessor implements ItemProcessor {

	@Override
	public JsonObject processItem(Object item) throws Exception {
		JsonObject jsonItem = (JsonObject) item;		
		return jsonItem;
	}

}
