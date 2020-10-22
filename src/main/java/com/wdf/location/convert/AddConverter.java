package com.wdf.location.convert;

import com.wdf.location.datasource.LocationDBConstants;
import com.wdf.location.datasource.dataservice.LocationDataService;
import com.wdf.location.datasource.model.Location;
import com.wdf.location.request.AddRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.wdf.location.datasource.LocationDBConstants.STATUS.ACTIVE;

@Component
public class AddConverter {

	public Location convert(AddRequest request, Location parentLocation) {

		//TODO You forgot to set the ID , ChangeID to UUID
		Location location = new Location();
		location.setLastUpdatedBy(request.getHeaders().get("X-User-ID"));
		location.setChildren(null);
		location.setGeoLocation(request.getGeoLocation());
		location.setImageUrl(request.getImageUrl());
		location.setLastUpdatedBy(request.getHeaders().get("X-User-ID"));

		//TODO NO DATABASE CALLS FROM CONVERTER, they shuld be from SERVICE
//		Location parentLocation = locationDataService.findByUid(request.getParent());
		//TODO : BUSINESS VALIDATION MISSING if parent==null throw BusinessException

		location.setLevel(parentLocation.getLevel() + 1); // parent +1
		location.setTags(parentLocation.getTags());
		//TODO : location Tags=(TAGS of parent+ Current Location's name)

		location.setName(request.getName());
		location.setParent(request.getParent());
//		location.setReportCount(request.getReportCount());
//		location.setRequests(request.getRequests());
		//TODO WHEN WE ARE CREATING A NEW LOCATION WE WILL CREATE IT WITH DEFAULT REPORT COUNT=0 and DEFAULT REQUESTS=null
		location.setStatus(ACTIVE.name());

		location.setType(request.getType());
		return location;
	}

}
