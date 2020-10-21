package com.wdf.location.service;

import com.wdf.location.convert.AddConverter;
import com.wdf.location.datasource.dataservice.LocationDataService;
import com.wdf.location.request.AddRequest;
import com.wdf.location.response.PostResponse;
import com.wdf.location.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddService extends BaseService<PostResponse> {

	@Autowired
	AddConverter addConverter;

	@Autowired
	private LocationDataService locationDataService;

	public BaseResponse add(AddRequest request) {

		locationDataService.save(addConverter.convert(request));

		return createSuccessResponse();
	}

}