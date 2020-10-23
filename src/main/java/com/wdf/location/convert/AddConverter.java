package com.wdf.location.convert;

import com.fasterxml.jackson.databind.JsonNode;
import com.wdf.location.constants.ApplicationConstants;
import com.wdf.location.datasource.model.Location;
import com.wdf.location.request.AddRequest;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.wdf.location.datasource.LocationDBConstants.STATUS.ACTIVE;

@Component
public class AddConverter {

	public Location convert(AddRequest request, Location parentLocation) {

		// TODO You forgot to set the ID , ChangeID to UUID
		Location location = new Location();
		location.setLastUpdatedBy(request.getHeaders().get("X-User-ID"));
		location.setChildren(null);
		location.setUid(UUID.randomUUID().toString());
		location.setGeoLocation(request.getGeoLocation());
		location.setImageUrl(request.getImageUrl());
		location.setLastUpdatedBy(request.getHeaders().get("X-User-ID"));
		location.setLevel(parentLocation.getLevel() + 1); // parent +1

		Map<String, List<String>> map = ApplicationConstants.objectMapper.convertValue(parentLocation.getTags(),
				HashMap.class);
		map.get("tags").add(request.getName());
		location.setTags(ApplicationConstants.objectMapper.convertValue(map, JsonNode.class));

		location.setName(request.getName());
		location.setParent(request.getParent());
		location.setReportCount(0);
		location.setRequests(null);
		location.setStatus(ACTIVE.name());

		location.setType(request.getType());
		return location;
	}

}
