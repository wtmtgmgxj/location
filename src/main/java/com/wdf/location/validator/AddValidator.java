package com.wdf.location.validator;

import com.wdf.location.request.AddRequest;
import org.springframework.stereotype.Component;

@Component
public class AddValidator {

	public void validate(AddRequest request) {
		// throw new RuntimeException();

		validateGeoLocation();
		validateNonNullableFields();
		validateEnums();
	}

	private void validateEnums() {
		// status has [ACTIVE,INACTIVE] as eligible values
	}

	private void validateNonNullableFields() {
		// except CHILDREN,IMAGE URL, GEOLOCATION rest are non nullable.

	}

	private void validateGeoLocation() {
		// TODO Google api se check or simple type check??
	}

}
