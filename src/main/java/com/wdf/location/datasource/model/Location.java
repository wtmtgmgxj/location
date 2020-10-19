package com.wdf.location.datasource.model;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;
import lombok.Setter;
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

	private JsonNode tags;

	private String imageUrl;

	private String geoLocation; // TODO @DV why is this allowed to be nullable?

	private int reportCount; // default 0;

	private String status; // eligible values: [ACTIVE,INACTIVE]

	private Date createdOn;

	private Date updatedOn;

	private JsonNode requests; // TODO @DV what is this for? BOTH KEYS CAN BE PRESENT
								// SIMULTANEOUSLY, UPDATE/REMOVAL mtlb??

}
