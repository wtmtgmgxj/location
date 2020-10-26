package com.wdf.location.datasource.dataservice;

import com.fasterxml.jackson.databind.JsonNode;
import com.wdf.location.constants.ApplicationConstants;
import com.wdf.location.datasource.model.Location;
import com.wdf.location.datasource.repository.master.LocationMasterRepository;
import com.wdf.location.response.GetResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class LocationDataService {

	@Autowired
	private LocationMasterRepository locationMasterRepository;

	public void save(Location location) {
		locationMasterRepository.save(location);
	}

	public void saveAll(List<Location> locationList) {
		locationMasterRepository.saveAll(locationList);
	}

	public Location findByUid(String uid) {
		return locationMasterRepository.findByUid(uid);
	}

	public List<Location> findAllByUidIn(List<String> uidList) {
		return locationMasterRepository.findAllByUidIn(uidList);
	}

	// TODO GET RESPONSE IS BEING CALLED DIRECTLY FROM DATA SERVICE? WHATS THE USE OF
	// CONVERTERS THEN? : yaha pe parent n all bhi to set krne h. isliy direct usi mein
	// set krdiya.
	public GetResponse get(String id, String userId) {

		GetResponse response = new GetResponse();
		Location location = locationMasterRepository.findByUid(id);

		response.setLocation(location);

		List<String> ids = new ArrayList<>();
		ids.add(location.getParent());
		ids.addAll(location.getChildren().findValuesAsText("list"));

		List<Location> locations = locationMasterRepository.findAllByUid(ids);

		response.setParent(
				locations.stream().filter(x -> x.getUid().equalsIgnoreCase(location.getParent())).findAny().get());
		response.setChildren(locations.stream().filter(x -> !x.getUid().equalsIgnoreCase(location.getParent()))
				.collect(Collectors.toList()));

		return response;
	}

	public void removeLocation(String location) {
		locationMasterRepository.removeLocation(location);
	}

	public List<Map<String, String>> fetchFirstXNonNullRequests(String x) {
		List<JsonNode> requestList = locationMasterRepository.findXNonNullReports(x);
		List<Map<String, Map<String, String>>> mapList = requestList.stream()
				.map(y -> ApplicationConstants.objectMapper.convertValue(y, Map.class)).collect(Collectors.toList());

		return mapList.stream().map(y -> ApplicationConstants.objectMapper.convertValue(y.get("requests"), Map.class))
				.collect(Collectors.toList());

	}

}