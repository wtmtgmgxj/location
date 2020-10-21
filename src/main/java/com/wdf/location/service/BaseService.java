package com.wdf.location.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.wdf.location.constants.ApplicationConstants;
import com.wdf.location.constants.Flow;
import com.wdf.location.datasource.model.Location;
import com.wdf.location.response.BaseResponse;
import com.wdf.location.response.ResponseCodes;

import java.util.HashMap;
import java.util.List;

public abstract class BaseService<T> {

	BaseResponse<T> createSuccessResponse() {
		BaseResponse response = new BaseResponse();
		response.setRespCode(ResponseCodes.OK.name());
		return new BaseResponse();
	}

	protected List<String> getChildren(Location parentLocation) {
		return (List<String>) ApplicationConstants.objectMapper
				.convertValue(parentLocation.getChildren(), HashMap.class).get("children");
	}

	protected Location setChildren(Location parentLocation, String childUid, Flow flowtype) {
		List<String> children = getChildren(parentLocation);
		if (Flow.ADD.equals(flowtype))
			children.add(childUid);
		if (Flow.DELETE.equals(flowtype))
			children.remove(childUid);

		parentLocation.setChildren(ApplicationConstants.objectMapper.convertValue(children, JsonNode.class));
		return parentLocation;
	}

}
