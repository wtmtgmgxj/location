package com.wdf.location.validator;

import com.wdf.location.exceptions.business.BusinessException;
import com.wdf.location.request.AddRequest;
import com.wdf.location.response.ResponseCodes;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class AddValidator {

	public void validate(AddRequest request) {
		//TODO Each Request must BREAK WITH 4XX if the required headers are not present
		validateNonNullableFields(request);
		validateGeoLocation(request.getGeoLocation());
	}



	private void validateNonNullableFields(AddRequest request) {

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
//		if (CollectionUtils.isEmpty(request.getTags())) {
//			throw new BusinessException(ResponseCodes.EMPTY_TAG_LIST);
//		}
		//TODO : REQUEST CANNOT HAVE TAGS, Wrong validation


//		if (StringUtils.isEmpty(request.getStatus())) {
//			throw new BusinessException(ResponseCodes.BLANK_STATUS);
//		}
		//TODO : REQUEST CANNOT HAVE STATUS DUDE, Wrong validation
	}

	private void validateGeoLocation(String geoLocation) {
		// TODO Google api se check or simple type check??
		// NOT NECESSARY TO DO IT , let it be as is for now.
	}

}
