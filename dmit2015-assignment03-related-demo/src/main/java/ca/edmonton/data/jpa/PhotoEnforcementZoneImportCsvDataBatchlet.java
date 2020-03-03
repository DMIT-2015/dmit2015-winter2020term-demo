package ca.edmonton.data.jpa;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.batch.api.AbstractBatchlet;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Named	// Create an object of this named photoEnforcementZoneImportCsvDataBatchlet
public class PhotoEnforcementZoneImportCsvDataBatchlet extends AbstractBatchlet {

	@PersistenceContext(unitName = "mssql-jpa-pu")
	private EntityManager entityManager;
	
	@Transactional
	@Override
	public String process() throws Exception {
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/data-edmonton-ca/All_Photo_Enforcement_Zone_Centre_Points.csv"))))	{			
			String line;
			final String delimiter = ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)";
			// Skip the first line as it is containing column headings
			reader.readLine();
			while ((line = reader.readLine()) != null) {
				String[] values = line.split(delimiter);

				PhotoEnforcementZone data = new PhotoEnforcementZone();
				data.setLocationDescription(values[0]);
				data.setSpeedLimit(Integer.parseInt(values[1]));
				data.setReasonCodes(values[2].replaceAll("[\"()]", ""));
				
				entityManager.persist(data);				
			}			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return "FAILED";
		}
				
		return "COMPLETED";
	}
}
