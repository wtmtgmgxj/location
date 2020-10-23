package com.wdf.location.datasource.model;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@DynamicInsert
@DynamicUpdate
public class Location {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; // TODO @DV doc mein uuid h ye : ab59add0-2ba1-470c-a451-8f6eca3a34e9

	@Column(name = "name")
	private String name; // INDIA

	@Column(name = "parent")
	private String parent; // For the topmost, parent will then have a dummy uuid

	@Column(name = "children")
	private JsonNode children; // nullable: {"children": [uuid-1,uuid-2,uuid-3]}

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

	@Column(name = "tags")
	private JsonNode tags; // If I am creating a record for Delhi, Tags will be the
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

	@Column(name = "requests")
	private JsonNode requests; // BOTH KEYS CAN BE PRESENT SIMULTANEOUSLY, UPDATE/REMOVAL
								// : {"requests":[UPDATE,REMOVAL]}

	// Let us suppose
	// Babli adds a location DELLY.
	// You would like to correct this to DELHI.
	// So you would REQUEST_UPDATE(because end user must not be given permission to change
	// a location created by another one, they can only request the same. Admin People
	// will come, look at all requests and act accordingly) .
	// Currently this JSON suports only 1 UPDATE and one REMOVAL. You can discuss before
	// implementing this

}
