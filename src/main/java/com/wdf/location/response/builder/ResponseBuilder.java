package com.wdf.location.response.builder;

import com.wdf.location.exceptions.AbstractException;
import com.wdf.location.response.BaseResponse;
import com.wdf.location.response.ResponseCodes;
import org.springframework.stereotype.Component;

import static com.wdf.location.constants.ApplicationConstants.APPLICATION_NAME;
import static com.wdf.location.constants.ApplicationConstants.HYPHEN;

@Component
public class ResponseBuilder {

	public BaseResponse baseResponse(AbstractException e) {
		return new BaseResponse(APPLICATION_NAME + HYPHEN + e.getMessage());
	}

	public BaseResponse baseResponse(Exception e) {
		return new BaseResponse(APPLICATION_NAME + HYPHEN + e.getMessage());
	}

	public BaseResponse baseResponse(ResponseCodes e) {
		return new BaseResponse(APPLICATION_NAME + HYPHEN + ResponseCodes.getCodeFromResponseMessage(e.name()));
	}

}