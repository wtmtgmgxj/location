package com.wdf.location.datasource.dataservice;

import com.wdf.location.constants.ApplicationConstants;
import com.wdf.location.datasource.model.Location;
import com.wdf.location.datasource.repository.master.LocationMasterRepository;
import com.wdf.location.response.GetResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.HashMap;
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

		if (ObjectUtils.isEmpty(location)) {
			return null;
		}
		response.setLocation(location);

		List<String> ids = new ArrayList<>();
		ids.add(location.getParent());
		if (!ObjectUtils.isEmpty(location.getChildren()))
			ids.addAll(ApplicationConstants.objectMapper.convertValue(location.getChildren().get("list"), List.class));

		List<Location> locations = locationMasterRepository.findAllByUid(ids);

		if (!CollectionUtils.isEmpty(locations)) {
			if (location.getParent() != null) {
				response.setParent(locations.stream().filter(x -> x.getUid().equalsIgnoreCase(location.getParent()))
						.findAny().get());
				response.setChildren(locations.stream().filter(x -> !x.getUid().equalsIgnoreCase(location.getParent()))
						.collect(Collectors.toList()));
			}
			else {

			}
		}

		return response;
	}

	public void removeLocation(String location) {
		locationMasterRepository.removeLocation(location);
	}

	public List<Map<String, Map<String, String>>> fetchFirstXNonNullRequests(String x) {
		Map requestMap = new HashMap<String, Map<String, Map<String, String>>>();
		List<Location> list = locationMasterRepository.findXNonNullReports(Integer.parseInt(x));
		List<Map<String, Map<String, String>>> listToSend = new ArrayList<>();

		for (final int[] i = { 0 }; i[0] < Integer.parseInt(x);) {

			if (i[0] > list.size()) {
				break;
			}

			Map<String, Map<String, String>> values = ApplicationConstants.objectMapper
					.convertValue(list.get(i[0]).getRequests().get("requests"), HashMap.class);

			Map<String, Map<String, String>> mapToSend = new HashMap<>();

			values.keySet().forEach(key -> {
				if (mapToSend.size() + 1 <= Integer.parseInt(x)) {
					mapToSend.put(key, values.get(key));
					i[0] = i[0] + 1;
				}
			});

			listToSend.add(mapToSend);
		}

		return listToSend;
	}

}