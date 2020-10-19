package com.wdf.location.convert;

import com.wdf.location.datasource.dataservice.LocationDataService;
import com.wdf.location.datasource.model.Location;
import com.wdf.location.request.AddRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AddConverter {

	@Autowired
	private LocationDataService locationDataService;

	public Location convert(AddRequest request) {
		Location location = new Location();
		location.setLastUpdatedBy(request.getHeaders().get("X-User-ID"));
		location.setChildren(null);
		location.setGeoLocation(request.getGeoLocation());
		location.setImageUrl(request.getImageUrl());
		location.setLastUpdatedBy(request.getHeaders().get("X-User-ID"));

		Location parentLocation = locationDataService.findByUid(request.getParent());

		location.setLevel(parentLocation.getLevel() + 1); // parent +1
		location.setTags(parentLocation.getTags());// fetch tags of the parents and set
													// them here

		location.setName(request.getName());
		location.setParent(request.getParent());
		location.setReportCount(request.getReportCount());
		location.setRequests(request.getRequests());
		location.setStatus(request.getStatus());

		location.setType(request.getType());
		return location;
	}

}
