package com.wdf.location.controller;

import com.wdf.location.constants.Flow;
import com.wdf.location.response.BaseResponse;
import com.wdf.location.response.GetResponse;
import com.wdf.location.response.PostResponse;
import com.wdf.location.service.AddService;
import com.wdf.location.service.GetService;
import com.wdf.location.service.UpdateService;
import com.wdf.location.validator.CommonValidator;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.wdf.location.constants.RequestHeader.USERID;

@Slf4j
@RestController
public class InternalController {

	@Autowired
	private AddService addService;

	@Autowired
	private CommonValidator addValidator;

	@Autowired
	private UpdateService updateService;

	@Autowired
	private GetService getService;

	@Autowired
	private CommonValidator validator;

	@RequestMapping(value = { "/location/internal/club/{idA}/{idB}" }, method = { RequestMethod.PUT },
			consumes = { "application/json" })
	@ApiOperation(value = "Clubs location", notes = "This api clubs location.")
	public @ResponseBody BaseResponse<PostResponse> club(@PathVariable String idA, @PathVariable String idB,
			@RequestHeader Map<String, String> headers) {

		validator.validate(null, headers, Flow.NONE);
		return updateService.club(headers.get(USERID.getValue()), idA, idB);

	}

	@ApiOperation(value = "change parent", notes = "This api will change parent")
	@RequestMapping(value = { "/location/internal/changeparent/{child}/{newParent}" }, method = { RequestMethod.PUT },
			consumes = { "application/json" })
	public @ResponseBody BaseResponse<GetResponse> changeParent(@PathVariable String child,
			@PathVariable String newParent, @RequestHeader Map<String, String> headers) {

		validator.validate(null, headers, Flow.NONE);

		return updateService.changeParent(headers.get(USERID.getValue()), child, newParent);

	}

	@ApiOperation(value = "remove location", notes = "This api will give remove location")
	@RequestMapping(value = { "/location/internal/{id}" }, method = { RequestMethod.DELETE },
			consumes = { "application/json" })
	public @ResponseBody BaseResponse<PostResponse> removeLocation(@PathVariable String id,
			@RequestHeader Map<String, String> headers) {

		validator.validate(null, headers, Flow.NONE);

		return updateService.removeLocation(headers.get(USERID.getValue()), id);

	}

	@ApiOperation(value = "update location", notes = "This api will give update location")
	@RequestMapping(value = { "/location/internal/{id}" }, method = { RequestMethod.PATCH },
			consumes = { "application/json" })
	public @ResponseBody BaseResponse updateLocation(@PathVariable String id,
			@RequestParam(required = false) String name, @RequestParam(required = false) String tag,
			@RequestParam(required = false) String imageUrl, @RequestParam(required = false) String geoLocation,
			@RequestParam(required = false) String type, @RequestHeader Map<String, String> headers) {

		validator.validate(null, headers, Flow.NONE);

		return updateService.updateLocation(headers.get(USERID.getValue()), id, name, tag, imageUrl, geoLocation, type);

	}

	@ApiOperation(value = "get requests", notes = "This api will give get requests")
	@RequestMapping(value = { "/location/internal/requests/{x}" }, method = { RequestMethod.GET },
			consumes = { "application/json" })
	public @ResponseBody BaseResponse<List<Map<String, Map<String, String>>>> getRequests(@PathVariable String x,
			@RequestHeader Map<String, String> headers) {

		validator.validate(null, headers, Flow.NONE);

		return getService.getRequests(headers.get(USERID.getValue()), x);

	}

	@ApiOperation(value = "discard requests", notes = "This api will give discard requests")
	@RequestMapping(value = { "/location/internal/discard" }, method = { RequestMethod.PATCH },
			consumes = { "application/json" })
	public @ResponseBody BaseResponse discardRequests(@RequestBody List<String> locationIdList,
			@RequestHeader Map<String, String> headers) {

		validator.validate(null, headers, Flow.NONE);

		return updateService.discardRequests(headers.get(USERID.getValue()), locationIdList);

	}

}
