package ca.edmonton.data.jpa;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.batch.api.AbstractBatchlet;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Named
public class ScheduledPhotoEnforcementZoneCentrePointImportCsvDataBatchlet extends AbstractBatchlet {

	@PersistenceContext(unitName = "mssql-jpa-pu")
	private EntityManager entityManager;
	
	@Transactional
	@Override
	public String process() throws Exception {
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/data-edmonton-ca/Scheduled_Photo_Enforcement_Zone_Centre_Points.csv"))))	{			
			String line;
			final String delimiter = ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)";
			// Skip the first line as it is containing column headings
			reader.readLine();
			while ((line = reader.readLine()) != null) {
				String[] values = line.split(delimiter);

				ScheduledPhotoEnforcementZoneCentrePoint model = new ScheduledPhotoEnforcementZoneCentrePoint();
				model.setSiteId(Short.parseShort(values[0]));
				model.setLocationDescription(values[1]);
				model.setSpeedLimit(Short.parseShort(values[2]));
				model.setReasonCodes(values[3].replaceAll("[\"()]", ""));
				model.setLatitude(Double.valueOf(values[4]));
				model.setLongitude(Double.valueOf(values[5]));
				
				entityManager.persist(model);				
			}			
		} catch (Exception ex) {
			ex.printStackTrace();
			return "FAILED";
		}
				
		return "COMPLETED";
	}
}
