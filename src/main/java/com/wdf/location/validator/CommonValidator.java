package com.wdf.location.validator;

import com.wdf.location.constants.Flow;
import com.wdf.location.constants.RequestHeader;
import com.wdf.location.exceptions.business.BusinessException;
import com.wdf.location.request.AddRequest;
import com.wdf.location.response.ResponseCodes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Arrays;
import java.util.Map;

@Slf4j
@Component
public class CommonValidator {

	public void validate(AddRequest request, Map<String, String> headers, Flow flow) {

		validateAllHeaders(headers);

		if (flow.equals(Flow.ADD)) {
			validateNonNullableFields(request);
			validateGeoLocation(request.getGeoLocation());
		}

	}

	private void validateAllHeaders(Map<String, String> headers) {

		try {
			Arrays.stream(RequestHeader.values()).forEach(header -> {
				if (StringUtils.isEmpty(headers.get(header.getValue()))) {
					log.error("exception header not present: {}", header.getValue());
					throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
				}
			});
		}
		catch (Exception e) {
			log.error("exception", e);
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
		}

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
		// if (CollectionUtils.isEmpty(request.getTags())) {
		// throw new BusinessException(ResponseCodes.EMPTY_TAG_LIST);
		// }
		// TODO : REQUEST CANNOT HAVE TAGS, Wrong validation

		// if (StringUtils.isEmpty(request.getStatus())) {
		// throw new BusinessException(ResponseCodes.BLANK_STATUS);
		// }
		// TODO : REQUEST CANNOT HAVE STATUS DUDE, Wrong validation
	}

	private void validateGeoLocation(String geoLocation) {
		// TODO Google api se check or simple type check??
		// NOT NECESSARY TO DO IT , let it be as is for now.
	}

}