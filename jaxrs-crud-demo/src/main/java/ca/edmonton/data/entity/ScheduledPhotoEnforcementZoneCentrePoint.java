package ca.edmonton.data.entity;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

/**
 * This dataset represents the centre points of each Zone or Operational Area where Photo Enforcement is "scheduled" to be conducted.
 * An enforcement unit can be found anywhere along the area of the Zone.
 * An enforcement unit may not be able to operate at the specified zone and subsequently move to a zone that is not scheduled for enforcement.
 * The centre points are extracted for mapping purposes only and are not intended to imply or suggest that is where the Photo Enforcement is being conducted.
 * Designated Zones are continuously reviewed, revised, added, removed and otherwise updated in accordance to the guidelines for establishing a Zone for photo enforcement.
 * Automated enforcement is expected to be operating at the locations indicated.
 * Please be advised that automated enforcement may be used at other locations within Edmonton as well.
 * Locations selected for enforcement may be removed or added as determined by weather, road conditions, roadway closures or construction, equipment issues or other unforeseen circumstances.
 * Each enforcement site has one or more reasons for why enforcement is taking place. The list of reasons are:
 * a) Areas or intersections where conventional enforcement is unsafe or ineffective;
 * b) Areas or intersections with an identifiable, documented history of collisions;
 * c) Areas or intersections with an identifiable, documented history of speeding problems;
 * d) Intersections with an identifiable, documented history of offences;
 * e) Intersections near schools, post-secondary institutions, or other areas with high pedestrian volumes;
 * f) School and playground zones or areas;
 * g) Construction zones; or
 * h) Areas where the public or a community has expressed concerns related to speeding.
 *
 * Please refer to the FAQ the City has available in regards to Photo Enforcement.
 * https://www.edmonton.ca/transportation/Mobile_Photo_Enforcement_FAQ.pdf
 *
 * @author City of Edmonton
 *
 */
@Entity
@Table(name = "edmonton_scheduled_photo_enforcement_zone_centre_point")
@Getter @Setter
public class ScheduledPhotoEnforcementZoneCentrePoint implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * A system generated number assigned to any newly created enforcement zone
	 */
	@NotNull(message = "SiteID is required")
	@Id
	@Column(name = "site_id", nullable = false, unique = true)
	private Short siteId;

	/**
	 * A description of where along the Road Name that the enforcement zone is located
	 */
	@NotBlank(message = "Location Description is required")
	@Column(name = "location_description", nullable = false, length = 128)
	private String locationDescription;

	/**
	 * A speed limit that is being monitored and enforced (km/h)
	 */
	@NotNull(message = "Speed Limit is required")
	@Column(name = "speed_limit", nullable = false)
	private Short speedLimit;

	/**
	 * A reason code for why this location was chosen for photo enforcement:
	 * a) Areas or intersections where conventional enforcement is unsafe or ineffective;
	 * b) Areas or intersections with an identifiable, documented history of collisions;
	 * c) Areas or intersections with an identifiable, documented history of speeding problems;
	 * d) Intersections with an identifiable, documented history of offences;
	 * e) Intersections near schools, post-secondary institutions, or other areas with high pedestrian volumes;
	 * f) School and playground zones or areas;
	 * g) Construction zones; or
	 * h) Areas where the public or a community has expressed concerns related to speeding.
	 */
	@NotBlank(message = "Reason Code(s) is required")
	@Column(name = "reason_code_s_", length = 32)
	private String reasonCodes;

	/**
	 * The latitude value for the centre of the enforcement zone
	 */
	@NotNull(message = "Latitude is required")
	private Double latitude;


	/**
	 * The longitude value for the centre of the enforcement zone
	 */
	@NotNull(message = "Longitude is required")
	private Double longitude;

	@Version
	private Integer version;

	/**
	 * https://vladmihalcea.com/the-best-way-to-implement-equals-hashcode-and-tostring-with-jpa-and-hibernate/
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;

		if (!(o instanceof ScheduledPhotoEnforcementZoneCentrePoint))
			return false;

		ScheduledPhotoEnforcementZoneCentrePoint other = (ScheduledPhotoEnforcementZoneCentrePoint) o;
		return Objects.equals(getSiteId(), other.getSiteId());
	}

	/**
	 * https://vladmihalcea.com/the-best-way-to-implement-equals-hashcode-and-tostring-with-jpa-and-hibernate/
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getSiteId());
	}

}