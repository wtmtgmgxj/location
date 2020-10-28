package com.wdf.location.service;

import com.wdf.location.convert.AddConverter;
import com.wdf.location.datasource.dataservice.LocationDataService;
import com.wdf.location.datasource.model.Location;
import com.wdf.location.exceptions.business.BusinessException;
import com.wdf.location.request.AddRequest;
import com.wdf.location.response.BaseResponse;
import com.wdf.location.response.PostResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.wdf.location.response.ResponseCodes.NO_PARENT_LOCATION_IN_DB;
import static com.wdf.location.response.ResponseCodes.NO_PARENT_LOCATION_IN_REQUEST;

@Service
public class AddService extends BaseService<PostResponse> {

	@Autowired
	AddConverter addConverter;

	@Autowired
	private LocationDataService locationDataService;

	public BaseResponse add(AddRequest request) {

		if (ObjectUtils.isEmpty(request.getParent()) && request.getLevel() != 0) {
			throw new BusinessException(NO_PARENT_LOCATION_IN_REQUEST);
		}
		else {

			Location parentLocation = locationDataService.findByUid(request.getParent());
			if (parentLocation == null)
				throw new BusinessException(NO_PARENT_LOCATION_IN_DB);

			Location location = locationDataService.save(addConverter.convert(request, parentLocation));
			if (CollectionUtils.isEmpty(parentLocation.getChildren())) {
				List<String> child = new ArrayList();
				Map map = new HashMap<>();
				map.put("children", child);
				parentLocation.setChildren(map);
			}

			((List<String>) parentLocation.getChildren().get("children")).add(location.getUid());
			locationDataService.save(parentLocation);
		}

		return createSuccessResponse();
	}

}