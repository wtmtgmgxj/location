package com.wdf.location.convert;

import com.wdf.location.constants.ApplicationConstants;
import com.wdf.location.constants.RequestHeader;
import com.wdf.location.datasource.model.Location;
import com.wdf.location.request.AddRequest;
import org.springframework.stereotype.Component;

import java.util.*;

import static com.wdf.location.datasource.LocationDBConstants.STATUS.ACTIVE;

@Component
public class AddConverter {

	public Location convert(AddRequest request, Location parentLocation) {

		Location location = new Location();
		location.setCreatedBy(request.getHeaders().get(RequestHeader.USERID.getValue()));
		location.setChildren(null);
		location.setUid(UUID.randomUUID().toString());
		location.setGeoLocation(request.getGeoLocation());
		location.setImageUrl(request.getImageUrl());
		location.setLastUpdatedBy(request.getHeaders().get(RequestHeader.USERID.getValue()));

		Map<String, List<String>> map = null;

		if (request.getParent() != null) {
			location.setLevel(parentLocation.getLevel() + 1); // parent +1

			map = ApplicationConstants.objectMapper.convertValue(parentLocation.getTags(), HashMap.class);
			map.get("tags").add(request.getName());
			location.setTags(ApplicationConstants.objectMapper.convertValue(map, HashMap.class));
		}
		else {
			map = new HashMap<>();
			List<String> tags = new ArrayList<>();
			tags.add(request.getName());
			map.put("tags", tags);

			location.setLevel(1);
			location.setTags(ApplicationConstants.objectMapper.convertValue(map, HashMap.class));
		}

		location.setName(request.getName());
		location.setParent(request.getParent());
		location.setReportCount(0);
		location.setRequests(null);
		location.setStatus(ACTIVE.name());

		location.setType(request.getType());
		return location;
	}

}
