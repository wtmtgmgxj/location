package com.wdf.location.controller;

import com.wdf.location.constants.Flow;
import com.wdf.location.request.AddRequest;
import com.wdf.location.response.PostResponse;
import com.wdf.location.response.BaseResponse;
import com.wdf.location.response.GetResponse;
import com.wdf.location.service.AddService;
import com.wdf.location.service.GetService;
import com.wdf.location.service.UpdateService;
import com.wdf.location.validator.CommonValidator;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.wdf.location.constants.ApplicationConstants.APPLICATION_NAME;
import static com.wdf.location.constants.ApplicationConstants.EXTERNAL;
import static com.wdf.location.constants.RequestHeader.*;

@Slf4j
@RestController("/" + APPLICATION_NAME + EXTERNAL)
public class ExternalController {

	@Autowired
	private AddService addService;

	@Autowired
	private CommonValidator validator;

	@Autowired
	private UpdateService updateService;

	@Autowired
	private GetService getService;

	@RequestMapping(value = { "add" }, method = { RequestMethod.PUT }, consumes = { "application/json" })
	@ApiOperation(value = "Add location", notes = "This api adds location.")
	public @ResponseBody BaseResponse<PostResponse> add(@Validated @RequestBody AddRequest request,
			@RequestHeader Map<String, String> headers) {

		request.setHeaders(headers);
		request.setTracer(headers.get(USERID));
		request.setRequestTimestamp(
				Long.valueOf(headers.get(com.wdf.location.constants.RequestHeader.DATE.getValue())));

		validator.validate(request, headers, Flow.ADD);

		return addService.add(request);

	}

	@ApiOperation(value = "Get location details",
			notes = "This api will give details of the parent as well as all the immediate children.")
	@RequestMapping(value = { "get/{id}" }, method = { RequestMethod.GET }, consumes = { "application/json" })
	public @ResponseBody BaseResponse<GetResponse> get(@PathVariable String id, @RequestParam Long requestTimestamp,
			@RequestHeader Map<String, String> headers) {

		validator.validate(null, headers, Flow.NONE);

		return getService.get(headers.get(USERID), id);

	}

	@ApiOperation(value = "increase report count", notes = "This api will give increase report count")
	@RequestMapping(value = { "report/{id}" }, method = { RequestMethod.POST }, consumes = { "application/json" })
	public @ResponseBody BaseResponse<PostResponse> reportCountUpdate(@PathVariable String id,
			@RequestParam Long requestTimestamp, @RequestHeader Map<String, String> headers) {

		validator.validate(null, headers, Flow.NONE);

		return updateService.updateReportCount(headers.get(USERID), id);

	}

	@ApiOperation(value = "update request", notes = "This api will give update request")
	@RequestMapping(value = { "request/update/{fieldName}/{newValue}/{id}" }, method = { RequestMethod.POST },
			consumes = { "application/json" })
	public @ResponseBody BaseResponse<PostResponse> requestUpdate(@PathVariable String fieldName,
			@PathVariable String newValue, @PathVariable String id, @RequestParam Long requestTimestamp,
			@RequestHeader Map<String, String> headers) {

		validator.validate(null, headers, Flow.NONE);

		return updateService.requestUpdate(headers.get(USERID), fieldName, newValue, Flow.UPDATE, id);

	}

	@ApiOperation(value = "remove request", notes = "This api will give remove request")
	@RequestMapping(value = { "request/remove/{fieldName}/{newValue}/{id}" }, method = { RequestMethod.POST },
			consumes = { "application/json" })
	public @ResponseBody BaseResponse<PostResponse> requestRemove(@PathVariable String fieldName,
			@PathVariable String newValue, @PathVariable String id, @RequestParam Long requestTimestamp,
			@RequestHeader Map<String, String> headers) {

		validator.validate(null, headers, Flow.NONE);

		return updateService.requestUpdate(headers.get(USERID), fieldName, newValue, Flow.DELETE, id);

	}

}
