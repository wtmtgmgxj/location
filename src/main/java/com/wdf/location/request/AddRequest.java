package com.wdf.location.request;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

import java.util.List;

@Data
public class AddRequest extends BaseRequest {

	private String name; // INDIA

	private String parent; // Id of the parent, topmost will then have a null value

	private JsonNode children; // id of children nullable: {"list":[uuid-1,uuid-2,uuid-3]}

	private int level;

	private String type;

	private List<String> tags;

	private String imageUrl;

	private String geoLocation; // nullable

	private int reportCount; // default 0;

	private String status; // eligible values: [ACTIVE,INACTIVE]

	private JsonNode requests; // BOTH KEYS CAN BE PRESENT SIMULTANEOUSLY, UPDATE/REMOVAL
								// or null

}