package northwind.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the Region database table.
 * 
 */
@Entity
@NamedQuery(name="Region.findAll", query="SELECT r FROM Region r")
public class Region implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="RegionID")
	private int regionID;

	@Column(name="RegionDescription")
	private String regionDescription;

	//bi-directional many-to-one association to Territory
	@OneToMany(mappedBy="region")
	private List<Territory> territories;

	public Region() {
	}

	public int getRegionID() {
		return this.regionID;
	}

	public void setRegionID(int regionID) {
		this.regionID = regionID;
	}

	public String getRegionDescription() {
		return this.regionDescription;
	}

	public void setRegionDescription(String regionDescription) {
		this.regionDescription = regionDescription;
	}

	public List<Territory> getTerritories() {
		return this.territories;
	}

	public void setTerritories(List<Territory> territories) {
		this.territories = territories;
	}

	public Territory addTerritory(Territory territory) {
		getTerritories().add(territory);
		territory.setRegion(this);

		return territory;
	}

	public Territory removeTerritory(Territory territory) {
		getTerritories().remove(territory);
		territory.setRegion(null);

		return territory;
	}

}