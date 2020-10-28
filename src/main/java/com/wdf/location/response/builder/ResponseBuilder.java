package com.wdf.location.response.builder;

import com.wdf.location.exceptions.AbstractException;
import com.wdf.location.response.BaseResponse;
import com.wdf.location.response.ResponseCodes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.wdf.location.constants.ApplicationConstants.APPLICATION_NAME;
import static com.wdf.location.constants.ApplicationConstants.HYPHEN;

@Component
@Slf4j
public class ResponseBuilder {

	public BaseResponse baseResponse(AbstractException e) {
		publishErrorStats(e);
		return new BaseResponse(APPLICATION_NAME + HYPHEN
				+ ResponseCodes.getCodeFromResponseMessage(ResponseCodes.INTERNAL_SERVER_ERROR.name()));
	}

	public BaseResponse baseResponse(Exception e) {
		publishErrorStats(e);
		return new BaseResponse(APPLICATION_NAME + HYPHEN
				+ ResponseCodes.getCodeFromResponseMessage(ResponseCodes.INTERNAL_SERVER_ERROR.name()));
	}

	public BaseResponse baseResponse(ResponseCodes e) {
		publishErrorStats(e);
		return new BaseResponse(APPLICATION_NAME + HYPHEN + ResponseCodes.getCodeFromResponseMessage(e.name()));
	}

	private void publishErrorStats(Exception e) {
		log.error("error ", e);
	}

	private void publishErrorStats(ResponseCodes e) {
		log.error("error ", e);
	}

}