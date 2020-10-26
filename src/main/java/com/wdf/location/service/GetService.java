package com.wdf.location.service;

import com.wdf.location.datasource.dataservice.LocationDataService;
import com.wdf.location.response.BaseResponse;
import com.wdf.location.response.GetResponse;
import com.wdf.location.response.ResponseCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class GetService extends BaseService {

	@Autowired
	private LocationDataService locationDataService;

	public BaseResponse<GetResponse> get(final String id, String userId) {

		return createSuccessResponse(locationDataService.get(id, userId));
	}

	private BaseResponse<GetResponse> createSuccessResponse(GetResponse getResponse) {
		BaseResponse<GetResponse> response = new BaseResponse();
		response.setRespCode(ResponseCodes.OK.name());
		response.setData(getResponse);
		return response;
	}

	public BaseResponse<List<Map<String, String>>> getRequests(String userId, String x) {
		BaseResponse<List<Map<String, String>>> response = new BaseResponse();
		response.setRespCode(ResponseCodes.OK.name());
		response.setData(locationDataService.fetchFirstXNonNullRequests(x));
		return response;
	}

}