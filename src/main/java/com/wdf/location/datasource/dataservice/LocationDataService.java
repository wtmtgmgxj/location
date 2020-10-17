package com.wdf.location.datasource.dataservice;

import com.wdf.location.datasource.model.Location;
import com.wdf.location.datasource.repository.master.LocationMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocationDataService {

	@Autowired
	private LocationMasterRepository locationMasterRepository;

	public void add(Location location) {
		locationMasterRepository.save(location);
	}

}