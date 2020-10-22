package com.wdf.location.controller;

import com.wdf.location.request.AddRequest;
import com.wdf.location.response.PostResponse;
import com.wdf.location.response.BaseResponse;
import com.wdf.location.response.GetResponse;
import com.wdf.location.service.AddService;
import com.wdf.location.service.GetService;
import com.wdf.location.service.UpdateService;
import com.wdf.location.validator.AddValidator;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.wdf.location.constants.ApplicationConstants.APPLICATION_NAME;
import static com.wdf.location.constants.ApplicationConstants.EXTERNAL;

@Slf4j
@RestController(EXTERNAL)
public class ExternalController {

	@Autowired
	private AddService addService;

	@Autowired
	private AddValidator addValidator;

	@Autowired
	private UpdateService updateService;

	@Autowired
	private GetService getService;

	@RequestMapping(value = { "/" + APPLICATION_NAME + "/add" }, method = { RequestMethod.PUT },
			consumes = { "application/json" })
	@ApiOperation(value = "Add location", notes = "This api adds location.")
	//TODO explain why the BASE RESPONSE IS USING GENERICS HERE
	public @ResponseBody BaseResponse<PostResponse> add(@Validated @RequestBody AddRequest request,
			@RequestHeader Map<String, String> headers) {

		// headers: X-User-ID; X-Request-ID; Date; X-Client-ID

		request.setHeaders(headers);
		request.setTracer(headers.get("X-Request-ID"));
//		request.setRequestTimestamp(requestTimestamp);
		//TODO Request timestamp is now coming from HEADER named DATE

		addValidator.validate(request);

		return addService.add(request);

	}

	@ApiOperation(value = "Get location details",
			notes = "This api will give details of the parent as well as all the immediate children.")
	@RequestMapping(value = { "/" + APPLICATION_NAME + "/get/{id}" }, method = { RequestMethod.GET },
			consumes = { "application/json" })
	public @ResponseBody BaseResponse<GetResponse> get(@PathVariable String id, @RequestParam Long requestTimestamp,
			@RequestHeader Map<String, String> headers) {

		// headers: X-User-ID; X-Request-ID; Date; X-Client-ID

		return getService.get(headers.get("X-User-ID"), id);

	}

	@ApiOperation(value = "increase report count", notes = "This api will give increase report count")
	@RequestMapping(value = { "/" + APPLICATION_NAME + "/report/{id}" }, method = { RequestMethod.POST },
			consumes = { "application/json" })
	public @ResponseBody BaseResponse<PostResponse> reportCountUpdate(@PathVariable String id,
			@RequestParam Long requestTimestamp, @RequestHeader Map<String, String> headers) {

		// headers: X-User-ID; X-Request-ID; Date; X-Client-ID

		return updateService.updateReportCount(headers.get("X-User-ID"), id);

	}

	@ApiOperation(value = "update request", notes = "This api will give update request")
	@RequestMapping(value = { "/" + APPLICATION_NAME + "/request/update" }, method = { RequestMethod.POST },
			consumes = { "application/json" })
	public @ResponseBody BaseResponse<PostResponse> requestUpdate(@RequestParam Long requestTimestamp,
			@RequestHeader Map<String, String> headers) {

		// headers: X-User-ID; X-Request-ID; Date; X-Client-ID

		return updateService.requestUpdate(headers.get("X-User-ID"));

	}

	@ApiOperation(value = "remove request", notes = "This api will give remove request")
	@RequestMapping(value = { "/" + APPLICATION_NAME + "/request/remove" }, method = { RequestMethod.POST },
			consumes = { "application/json" })
	public @ResponseBody BaseResponse<PostResponse> requestRemove(@RequestParam Long requestTimestamp,
			@RequestHeader Map<String, String> headers) {

		// headers: X-User-ID; X-Request-ID; Date; X-Client-ID

		return updateService.requestRemove(headers.get("X-User-ID"));

	}

}
