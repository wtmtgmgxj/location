package com.wdf.location.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.wdf.location.constants.ApplicationConstants;
import com.wdf.location.constants.Flow;
import com.wdf.location.datasource.dataservice.LocationDataService;
import com.wdf.location.datasource.model.Location;
import com.wdf.location.exceptions.business.BusinessException;
import com.wdf.location.response.BaseResponse;
import com.wdf.location.response.PostResponse;
import com.wdf.location.response.ResponseCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.*;

import static com.wdf.location.datasource.LocationDBConstants.STATUS.INACTIVE;

@Service
public class UpdateService extends BaseService<PostResponse> {

	@Autowired
	private LocationDataService locationDataService;

	public BaseResponse updateReportCount(String user, String id) {
		Location location = locationDataService.findByUid(id);
		location.setReportCount(location.getReportCount() + 1);
		location.setLastUpdatedBy(user);
		locationDataService.save(location);

		return createSuccessResponse();
	}

	public BaseResponse requestUpdate(final String userId, final String fieldName, String newValue,
													final Flow type, final String id) {

		Location location = locationDataService.findByUid(id);
		Map<String, Map<String, String>> requestMap = ApplicationConstants.objectMapper.convertValue(location.getRequests(), HashMap.class);

		String values = null;
		String toSetType = null;
		// only 1 update or remove type allowed.
		if(Flow.UPDATE.equals(type)){
			toSetType = "UPDATE";
		} else if(Flow.DELETE.equals(type)){
			toSetType = "REMOVAL";
		}

		// {"requests":{"UPDATE":{"NAME":"AYUSHI"},"REMOVAL":{"NAME":"AYUSHI"}}
		values = requestMap.get("requests").get(toSetType);
		if(StringUtils.isEmpty(values)){
			Map<String, String> newMap = new HashMap<>();
			newMap.put(fieldName,newValue);
			requestMap.put("requests",newMap);
		}else{
			throw new BusinessException(ResponseCodes.PREVIOUS_REQUEST_PENDING);
		}

		location.setRequests(ApplicationConstants.objectMapper.convertValue(requestMap,JsonNode.class));
		location.setLastUpdatedBy(userId);
		locationDataService.save(location);
		return createSuccessResponse();
	}



	public BaseResponse club(String userID, String idA, String idB) {

		Location locationA = locationDataService.findByUid(idA);
		Location locationB = locationDataService.findByUid(idB);

		Map<String, List<String>> mapA = ApplicationConstants.objectMapper.convertValue(locationA.getTags(),
				HashMap.class);
		Map<String, List<String>> mapB = ApplicationConstants.objectMapper.convertValue(locationB.getTags(),
				HashMap.class);

		List<String> tagsA = mapA.get("tags");
		List<String> tagsB = mapB.get("tags");
		tagsA.addAll(tagsB);
		mapA.put("tags", tagsA);

		locationA.setTags(ApplicationConstants.objectMapper.convertValue(mapA, JsonNode.class));
		locationB.setStatus(INACTIVE.name());
		List<Location> allLocation = new ArrayList<>();
		allLocation.add(locationA);
		allLocation.add(locationB);

		allLocation.stream().forEach(x -> x.setLastUpdatedBy(userID));

		locationDataService.saveAll(allLocation);

		return createSuccessResponse();
	}

	public BaseResponse changeParent(String userID, String child, String newParent) {
		// remove parent from child n child from parent.set child in new parent. level
		// will be new parent level +1
		List<String> allIds = new ArrayList<>();
		allIds.add(child);
		allIds.add(newParent);
		List<Location> bothLocations = locationDataService.findAllByUidIn(allIds);
		Optional<Location> childLocation = bothLocations.stream().filter(x -> x.getUid().equalsIgnoreCase(child))
				.findFirst();
		Optional<Location> newParentLocation = bothLocations.stream()
				.filter(x -> x.getUid().equalsIgnoreCase(newParent)).findFirst();

		if (ObjectUtils.isEmpty(childLocation))
			throw new BusinessException(ResponseCodes.NO_CHILD_LOCATION_IN_DB);

		if (ObjectUtils.isEmpty(newParentLocation))
			throw new BusinessException(ResponseCodes.NO_PARENT_LOCATION_IN_DB);

		List<Location> locationsToBeSaved = new ArrayList<>();
		if (!StringUtils.isEmpty(newParentLocation)) {
			Location oldParent = locationDataService.findByUid(childLocation.get().getParent());
			if (!ObjectUtils.isEmpty(oldParent)) {
				locationsToBeSaved.add(setChildren(oldParent, childLocation.get().getUid(), Flow.DELETE));
			}
		}
		childLocation.get().setParent(newParent);
		childLocation.get().setLevel(newParentLocation.get().getLevel() + 1);
		locationsToBeSaved.add(childLocation.get());
		locationsToBeSaved.add(setChildren(newParentLocation.get(), childLocation.get().getUid(), Flow.ADD));
		locationsToBeSaved.stream().forEach(x -> x.setLastUpdatedBy(userID));

		locationDataService.saveAll(locationsToBeSaved);
		return createSuccessResponse();
	}

	public BaseResponse removeLocation(String userID, String id) {
		// remove location from its parents row also. then delete it

		List<Location> locationsToBeSaved = new ArrayList<>();

		Location location = locationDataService.findByUid(id);
		Location parentLocation = locationDataService.findByUid(location.getParent());
		location.setStatus(INACTIVE.name());
		locationsToBeSaved.add(location);
		locationsToBeSaved.add(setChildren(parentLocation, location.getUid(), Flow.DELETE));

		locationsToBeSaved.stream().forEach(x -> x.setLastUpdatedBy(userID));

		locationDataService.saveAll(locationsToBeSaved);
		return createSuccessResponse();
	}

	public BaseResponse discardRequests(String userID, List<String> locationIdList) {

		List<Location> locationsToBeSaved = locationDataService.findAllByUidIn(locationIdList);
		locationsToBeSaved.stream().forEach(x -> {
			x.setLastUpdatedBy(userID);
			x.setRequests(null);
			x.setReportCount(0);
		});

		locationDataService.saveAll(locationsToBeSaved);
		return createSuccessResponse();

	}

	public BaseResponse updateLocation(String userID, String id, String name, String tag, String imageUrl,
			String geoLocation, final String type) {
		Location location = locationDataService.findByUid(id);

		if (!StringUtils.isEmpty(name)) {
			location.setName(name);
		}
		if (!StringUtils.isEmpty(type)) {
			location.setType(type);
		}
		if (!StringUtils.isEmpty(imageUrl)) {
			location.setImageUrl(imageUrl);
		}
		if (!StringUtils.isEmpty(geoLocation)) {
			location.setGeoLocation(geoLocation);
		}
		if (!StringUtils.isEmpty(tag)) {
			Map<String, List<String>> map = ApplicationConstants.objectMapper.convertValue(location.getTags(),
					HashMap.class);
			List<String> existingTags = map.get("tags");
			if (!existingTags.stream().filter(x -> x.toUpperCase().equalsIgnoreCase(tag.toUpperCase())).findAny()
					.isPresent()) {
				existingTags.add(tag);
				map.put("tags", existingTags);
				location.setTags(ApplicationConstants.objectMapper.convertValue(map, JsonNode.class));
			}
		}
		location.setLastUpdatedBy(userID);
		locationDataService.save(location);
		return createSuccessResponse();
	}

}
