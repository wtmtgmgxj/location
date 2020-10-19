package com.wdf.location.service;

import com.wdf.location.convert.AddConverter;
import com.wdf.location.datasource.dataservice.LocationDataService;
import com.wdf.location.request.AddRequest;
import com.wdf.location.response.AddResponse;
import com.wdf.location.response.BaseResponse;
import com.wdf.location.response.ResponseCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddService {

	@Autowired
	AddConverter addConverter;

	@Autowired
	private LocationDataService locationDataService;

	public AddResponse add(AddRequest request) {

		locationDataService.add(addConverter.convert(request));

		return createSuccessResponse();
	}

	private AddResponse createSuccessResponse() {
		BaseResponse response = new BaseResponse();
		response.setRespCode(ResponseCodes.OK.name());
		return new AddResponse();
	}

}