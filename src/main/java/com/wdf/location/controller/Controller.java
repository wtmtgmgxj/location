package com.wdf.location.controller;

import com.wdf.location.request.AddRequest;
import com.wdf.location.response.AddResponse;
import com.wdf.location.service.AddService;
import com.wdf.location.service.UpdateService;
import com.wdf.location.validator.AddValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.wdf.location.constants.ApplicationConstants.*;

@Slf4j
@RestController
public class Controller {

	@Autowired
	private AddService addService;

	@Autowired
	private AddValidator addValidator;

	@Autowired
	private UpdateService updateService;

	@RequestMapping(value = { "/" + APPLICATION_NAME + "/add" }, method = { RequestMethod.PUT },
			consumes = { "application/json" })
	public @ResponseBody AddResponse add(@Validated @RequestBody AddRequest request,
			@RequestParam Long requestTimestamp, @RequestHeader Map<String, String> headers) {

		// headers: X-User-ID; X-Request-ID; Date; X-Client-ID

		// TODO @DV what is the use of tracer?

		request.setHeaders(headers);
		request.setTracer(headers.get("X-Request-ID"));
		request.setRequestTimestamp(requestTimestamp);

		addValidator.validate(request);

		request.setRequestTimestamp(requestTimestamp);
		return addService.add(request);

	}

}
