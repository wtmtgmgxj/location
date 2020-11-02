package com.wdf.location.service;

import com.wdf.location.constants.ApplicationConstants;
import com.wdf.location.constants.Flow;
import com.wdf.location.datasource.model.Location;
import com.wdf.location.response.BaseResponse;
import com.wdf.location.response.ResponseCodes;
import org.springframework.util.CollectionUtils;

import java.util.*;

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

		if (CollectionUtils.isEmpty(children))
			children = new ArrayList<>();

		if (Flow.ADD.equals(flowtype))
			children.add(childUid);
		if (Flow.DELETE.equals(flowtype))
			children.remove(childUid);

		Map<String, Object> map = new HashMap<>();
		map.put("children", children);
		parentLocation.setChildren(ApplicationConstants.objectMapper.convertValue(map, HashMap.class));
		return parentLocation;
	}

	// add or remove tags of B into/from tags of A depending on flow type.
	protected void setTags(Location locationA, final Optional<Location> locationB, final Flow flowtype) {
		if (locationA == null)
			return;

		if (!locationB.isPresent())
			return;

		Map<String, List<String>> mapA = ApplicationConstants.objectMapper.convertValue(locationA.getTags(),
				HashMap.class);
		Map<String, List<String>> mapB = ApplicationConstants.objectMapper.convertValue(locationB.get().getTags(),
				HashMap.class);

		List<String> tagsA = mapA.get("tags");
		List<String> tagsB = mapB.get("tags");

		if (Flow.ADD.equals(flowtype)) {
			tagsA.addAll(tagsB);
			tagsB.add(tagsB.size(), locationA.getName());
		}
		else if (Flow.DELETE.equals(flowtype)) {
			tagsA.removeAll(tagsB);
			tagsB.remove(locationA.getName());
			if (!tagsA.contains(locationA.getName()))
				tagsA.add(0, locationA.getName());
		}

		mapA.put("tags", tagsA);
		mapB.put("tags", tagsB);
		locationA.setTags(ApplicationConstants.objectMapper.convertValue(mapA, HashMap.class));

		return;

	}

}
