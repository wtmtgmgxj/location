package com.wdf.location.service;

import com.wdf.location.constants.ApplicationConstants;
import com.wdf.location.constants.Flow;
import com.wdf.location.datasource.model.Location;
import com.wdf.location.response.BaseResponse;
import com.wdf.location.response.ResponseCodes;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.wdf.location.constants.ApplicationConstants.APPLICATION_NAME;
import static com.wdf.location.constants.ApplicationConstants.HYPHEN;

public abstract class BaseService<T> {

	BaseResponse<T> createSuccessResponse() {
		BaseResponse response = new BaseResponse();
		response.setRespCode(
				APPLICATION_NAME + HYPHEN + ResponseCodes.getCodeFromResponseMessage(ResponseCodes.OK.name()));
		return response;
	}

	protected List<String> getChildren(Location parentLocation) {
		Map childrenMap = ApplicationConstants.objectMapper.convertValue(parentLocation.getChildren(), HashMap.class);

		if (CollectionUtils.isEmpty(childrenMap))
			return null;
		return (List<String>) childrenMap.get("children");
	}

	protected Location setChildren(Location parentLocation, String childUid, Flow flowtype) {

		if (parentLocation == null)
			return null;

		List<String> children = getChildren(parentLocation);

		if (Flow.ADD.equals(flowtype))
			children.add(childUid);
		if (Flow.DELETE.equals(flowtype))
			children.remove(childUid);

		Map<String, Object> map = new HashMap<>();
		map.put("children", children);
		parentLocation.setChildren(ApplicationConstants.objectMapper.convertValue(map, HashMap.class));
		return parentLocation;
	}

}
