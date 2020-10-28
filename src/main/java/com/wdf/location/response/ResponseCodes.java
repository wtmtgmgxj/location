package com.wdf.location.response;

import lombok.Getter;
import lombok.AllArgsConstructor;

import java.util.Arrays;
import java.util.Optional;

@AllArgsConstructor
@Getter
public enum ResponseCodes {

	OK(200), INTERNAL_SERVER_ERROR(500), INVALID_LEVEL_FOR_A_CHILD(101), BLANK_NAME(102), BLANK_TYPE(
			103), EMPTY_TAG_LIST(104), BLANK_STATUS(105), ILLEGAL_STATUS_VALUE(106), NO_CHILD_LOCATION_IN_DB(
					107), NO_PARENT_LOCATION_IN_DB(108), PREVIOUS_REQUEST_PENDING(
							109), HEADER_NOT_PRESENT(400), NO_PARENT_LOCATION_IN_REQUEST(110);

	private int code;

	public static int getCodeFromResponseMessage(String response) {
		Optional<ResponseCodes> responseCode = Arrays.asList(ResponseCodes.values()).stream()
				.filter(x -> x.name().equalsIgnoreCase(response)).findFirst();
		if (responseCode.isPresent())
			return responseCode.get().getCode();
		else
			return INTERNAL_SERVER_ERROR.getCode();
	}

}
