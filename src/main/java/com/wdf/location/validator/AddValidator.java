package com.wdf.location.validator;

import com.wdf.location.constants.Status;
import com.wdf.location.exceptions.business.BusinessException;
import com.wdf.location.request.AddRequest;
import com.wdf.location.response.ResponseCodes;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class AddValidator {

	public void validate(AddRequest request) {
		// throw new RuntimeException();
		validateNonNullableFields(request);
		validateEnums(request);
		validateGeoLocation(request.getGeoLocation());

	}

	private void validateEnums(AddRequest request) {
		// status has [ACTIVE,INACTIVE] as eligible values
		// throws IllegalArgumentException -> runtimeException if not a valid enum.

		try {
			Status.valueOf(request.getStatus());
		}
		catch (IllegalArgumentException ex) {
			throw new BusinessException(ResponseCodes.ILLEGAL_STATUS_VALUE);
		}
	}

	private void validateNonNullableFields(AddRequest request) {
		// except CHILDREN,IMAGE URL, GEOLOCATION rest are non nullable.

		if (StringUtils.isEmpty(request.getName())) {
			throw new BusinessException(ResponseCodes.BLANK_NAME);
		}
		if (!(StringUtils.isEmpty(request.getParent()) && request.getLevel() == 0)) {
			// not parent.
			throw new BusinessException(ResponseCodes.INVALID_LEVEL_FOR_A_CHILD);
		}
		if (StringUtils.isEmpty(request.getType())) {
			throw new BusinessException(ResponseCodes.BLANK_TYPE);
		}
		if (CollectionUtils.isEmpty(request.getTags())) {
			throw new BusinessException(ResponseCodes.EMPTY_TAG_LIST);
		}
		if (StringUtils.isEmpty(request.getStatus())) {
			throw new BusinessException(ResponseCodes.BLANK_STATUS);
		}
	}

	private void validateGeoLocation(String geoLocation) {
		// TODO Google api se check or simple type check??
	}

}
