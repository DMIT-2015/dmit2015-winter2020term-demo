package ca.edmonton.data.jpa;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

import lombok.Data;

@Entity		// This class maps to a database table with the same name
@Data		// Generate getters/setters, toString, equals
public class PhotoEnforcementZone implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id					// This data field matches to the primary key column in the database table
	@GeneratedValue(strategy = GenerationType.IDENTITY)		// The primary key is auto-generated by the database
	private Long id;

	/** A  description of where along the Road Name that the enforcement zone is located */
	private String locationDescription;
	
	/** A speed limit that is being monitored and enforced (km/h) */
	private int speedLimit;
	
	/** A reason code for why this location was chosen for photo enforcement: 
	 * 	a) Areas or intersections where conventional enforcement is unsafe or ineffective; 
	 * 	b) Areas or intersections with an identifiable, documented history of collisions; 
	 * 	c) Areas or intersections with an identifiable, documented history of speeding problems; 
	 * 	d) Intersections with an identifiable, documented history of offences; 
	 * 	e) Intersections near schools, post-secondary institutions, or other areas with high pedestrian volumes; 
	 * 	f) School and playground zones or areas; 
	 * 	g) Construction zones; or 
	 * 	h) Areas where the public or a community has expressed concerns related to speeding.
	 * */
	private String reasonCodes;
	
	@Version
	private Integer version;
}
