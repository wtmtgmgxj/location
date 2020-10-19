package com.wdf.location.convert;

import com.wdf.location.datasource.model.Location;
import com.wdf.location.request.AddRequest;
import org.springframework.stereotype.Component;

@Component
public class AddConverter {

	public Location convert(AddRequest request) {
		Location location = new Location();
		location.setLastUpdatedBy(request.getHeaders().get("X-User-ID"));
		// all setters
		return location;
	}

}
