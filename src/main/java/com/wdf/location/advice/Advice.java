package com.wdf.location.advice;

import com.wdf.location.controller.ExternalController;
import com.wdf.location.exceptions.business.BusinessException;
import com.wdf.location.exceptions.technical.TechnicalException;
import com.wdf.location.response.BaseResponse;
import com.wdf.location.response.builder.ResponseBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = { ExternalController.class })
@Slf4j
public class Advice {

	@Autowired
	ResponseBuilder responseBuilder;

	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler(BusinessException.class)
	public BaseResponse handleException(BusinessException exception) {
		return responseBuilder.baseResponse(exception.code());
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler({ TechnicalException.class, NumberFormatException.class })
	public BaseResponse handleException(TechnicalException exception) {
		return responseBuilder.baseResponse(exception);
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public BaseResponse handleException(Exception exception) {
		return responseBuilder.baseResponse(exception);
	}

}