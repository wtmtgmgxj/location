package com.wdf.location.convert;

import com.wdf.location.datasource.model.Location;
import com.wdf.location.request.AddRequest;
import org.springframework.stereotype.Component;

@Component
public class AddConverter {

	public Location convert(AddRequest request) {
		return new Location();
	}

}
