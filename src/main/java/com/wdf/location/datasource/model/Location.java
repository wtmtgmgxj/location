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

	private JsonNode children; // nullable: {"children": [uuid-1,uuid-2,uuid-3]}

	private String uid; // unique uuid

	private int level;

	private String lastUpdatedBy; // X-User-ID header

	private String type;

	private JsonNode tags; // If I am creating a record for Delhi, Tags will be the
							// information of all the parents of Delhi, {"tags":
							// [tag-1,tag-2,tag-3]}

	// So tags will contain all of WORLD,ASIA,INDIA,DELHI
	// So you have to fetch the Immediate Parent and copy its tag and add to it

	private String imageUrl;

	private String geoLocation; // nullable

	private int reportCount; // default 0;

	private String status; // eligible values: [ACTIVE,INACTIVE]

	private Date createdOn;

	private Date updatedOn;

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
//
// CREATE TABLE `location` (
// `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
// `name` varchar(2000) NOT NULL,
// `parent` varchar(50) NOT NULL DEFAULT '0',
// `children` json NULL DEFAULT NULL,
// `doc_code` varchar(50) DEFAULT NULL,
// `reason` varchar(100) DEFAULT NULL,
// `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
// `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
// PRIMARY KEY (`id`),
// UNIQUE KEY `unique_cust_id_doc_code` (`cust_id`,`doc_code`),
// KEY `idx_cust_id` (`cust_id`),
// KEY `idx_reason` (`reason`),
// KEY `idx_expiry` (`doc_expiry_date`)
// );
