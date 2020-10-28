package com.wdf.location.datasource.model;

import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.Date;
import java.util.Map;

@Entity
@Data
@DynamicInsert
@DynamicUpdate
@TypeDef(name = "json", typeClass = JsonStringType.class)
public class Location {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; // TODO @DV doc mein uuid h ye : ab59add0-2ba1-470c-a451-8f6eca3a34e9

	@Column(name = "name")
	private String name; // INDIA

	@Column(name = "parent")
	private String parent; // For the topmost, parent will then have a dummy uuid

	@Type(type = "json")
	@Column(columnDefinition = "json", name = "children")
	private Map<String, Object> children; // nullable: {"children":
											// [uuid-1,uuid-2,uuid-3]}

	@Column(name = "uid")
	private String uid; // unique uuid

	@Column(name = "level")
	private int level;

	@Column(name = "last_updated_by")
	private String lastUpdatedBy; // X-User-ID header

	@Column(name = "created_by")
	private String createdBy; // X-User-ID header

	@Column(name = "type")
	private String type;

	@Type(type = "json")
	@Column(columnDefinition = "json", name = "tags")
	private Map<String, Object> tags; // If I am creating a record for Delhi, Tags will be
										// the

	// information of all the parents of Delhi, {"tags":
	// [tag-1,tag-2,tag-3]} + Delhi itself

	// So tags will contain all of WORLD,ASIA,INDIA,DELHI
	// So you have to fetch the Immediate Parent and copy its tag and add to it

	@Column(name = "image_url")
	private String imageUrl;

	@Column(name = "geo_location")
	private String geoLocation; // nullable

	@Column(name = "report_count")
	private int reportCount; // default 0;

	@Column(name = "status")
	private String status; // eligible values: [ACTIVE,INACTIVE]

	@Column(name = "created_on")
	private Date createdOn;

	@Column(name = "updated_on")
	private Date updatedOn;

	@Type(type = "json")
	@Column(columnDefinition = "json", name = "requests")
	private Map<String, Object> requests; // BOTH KEYS CAN BE PRESENT SIMULTANEOUSLY,
											// UPDATE/REMOVAL

	// : {"requests":[UPDATE,REMOVAL]}

	// {"requests":{"UPDATE":{"NAME":"AYUSHI"},"REMOVAL":{"NAME":"AYUSHI"}}

	// Let us suppose
	// Babli adds a location DELLY.
	// You would like to correct this to DELHI.
	// So you would REQUEST_UPDATE(because end user must not be given permission to change
	// a location created by another one, they can only request the same. Admin People
	// will come, look at all requests and act accordingly) .
	// Currently this JSON suports only 1 UPDATE and one REMOVAL. You can discuss before
	// implementing this

	// fields to be updated are name , type and geolocation only

}
