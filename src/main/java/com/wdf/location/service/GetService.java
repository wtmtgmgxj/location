package com.wdf.location.service;

import com.wdf.location.datasource.dataservice.LocationDataService;
import com.wdf.location.response.BaseResponse;
import com.wdf.location.response.GetResponse;
import com.wdf.location.response.ResponseCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static com.wdf.location.constants.ApplicationConstants.APPLICATION_NAME;
import static com.wdf.location.constants.ApplicationConstants.HYPHEN;

@Service
public class GetService extends BaseService {

	@Autowired
	private LocationDataService locationDataService;

	public BaseResponse<GetResponse> get(String userId, String id) {

		return createSuccessResponse(locationDataService.get(id, userId));
	}

	private BaseResponse<GetResponse> createSuccessResponse(GetResponse getResponse) {
		BaseResponse<GetResponse> response = new BaseResponse();
		response.setRespCode(
				APPLICATION_NAME + HYPHEN + ResponseCodes.getCodeFromResponseMessage(ResponseCodes.OK.name()));
		response.setData(getResponse);
		return response;
	}

	public BaseResponse<List<Map<String, Map<String, String>>>> getRequests(String userId, String x) {
		BaseResponse<List<Map<String, Map<String, String>>>> response = new BaseResponse();
		response.setRespCode(
				APPLICATION_NAME + HYPHEN + ResponseCodes.getCodeFromResponseMessage(ResponseCodes.OK.name()));
		response.setData(locationDataService.fetchFirstXNonNullRequests(x));
		return response;
	}

}