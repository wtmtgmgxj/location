package com.wdf.location.datasource.model;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
@DynamicInsert
@DynamicUpdate
public class Location {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; // TODO @DV doc mein uuid h ye : ab59add0-2ba1-470c-a451-8f6eca3a34e9

	private String name; // INDIA

	private String parent; // For the topmost, parent will then have a dummy uuid

	private JsonNode children; // nullable: {"list": [uuid-1,uuid-2,uuid-3]}

	private int level;

	private String lastUpdatedBy; // X-User-ID header

	private String type;

	private JsonNode tags; // If I am creating a record for Delhi, Tags will be the
							// information of all the parents of Delhi,

	// So tags will contain all of WORLD,ASIA,INDIA,DELHI
	// So you have to fetch the Immediate Parent and copy its tag and add to it

	private String imageUrl;

	private String geoLocation; // nullable

	private int reportCount; // default 0;

	private String status; // eligible values: [ACTIVE,INACTIVE]

	private Date createdOn;

	private Date updatedOn;

	private JsonNode requests; // BOTH KEYS CAN BE PRESENT SIMULTANEOUSLY, UPDATE/REMOVAL
								// mtlb??

	// Let us suppose
	// Babli adds a location DELLY.
	// You would like to correct this to DELHI.
	// So you would REQUEST_UPDATE(because end user must not be given permission to change
	// a location created by another one, they can only request the same. Admin People
	// will come, look at all requests and act accordingly) .
	// Currently this JSON suports only 1 UPDATE and one REMOVAL. You can discuss before
	// implementing this

}
