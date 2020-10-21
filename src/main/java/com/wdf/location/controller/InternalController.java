package com.wdf.location.controller;

import com.wdf.location.response.BaseResponse;
import com.wdf.location.response.GetResponse;
import com.wdf.location.response.PostResponse;
import com.wdf.location.service.AddService;
import com.wdf.location.service.GetService;
import com.wdf.location.service.UpdateService;
import com.wdf.location.validator.AddValidator;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.wdf.location.constants.ApplicationConstants.APPLICATION_NAME;
import static com.wdf.location.constants.ApplicationConstants.INTERNAL;

@Slf4j
@RestController(INTERNAL)
public class InternalController {

	@Autowired
	private AddService addService;

	@Autowired
	private AddValidator addValidator;

	@Autowired
	private UpdateService updateService;

	@Autowired
	private GetService getService;

	@RequestMapping(value = { "/" + APPLICATION_NAME + "/club/{idA}/{idB}" }, method = { RequestMethod.PUT },
			consumes = { "application/json" })
	@ApiOperation(value = "Clubs location", notes = "This api clubs location.")
	public @ResponseBody BaseResponse<PostResponse> club(@PathVariable String idA, @PathVariable String idB,
			@RequestParam Long requestTimestamp, @RequestHeader Map<String, String> headers) {

		// headers: X-User-ID; X-Request-ID; Date; X-Client-ID
		return updateService.club(headers.get("X-User-ID"), idA, idB);

	}

	@ApiOperation(value = "change parent", notes = "This api will change parent")
	@RequestMapping(value = { "/" + APPLICATION_NAME + "/changeparent/{child}/{newParent}" },
			method = { RequestMethod.PUT }, consumes = { "application/json" })
	public @ResponseBody BaseResponse<GetResponse> changeParent(@PathVariable String child,
			@PathVariable String newParent, @RequestParam Long requestTimestamp,
			@RequestHeader Map<String, String> headers) {

		// headers: X-User-ID; X-Request-ID; Date; X-Client-ID

		return updateService.changeParent(headers.get("X-User-ID"), child, newParent);

	}

	@ApiOperation(value = "remove location", notes = "This api will give remove location")
	@RequestMapping(value = { "/" + APPLICATION_NAME + "/{id}" }, method = { RequestMethod.DELETE },
			consumes = { "application/json" })
	public @ResponseBody BaseResponse<PostResponse> removeLocation(@PathVariable String id,
			@RequestParam Long requestTimestamp, @RequestHeader Map<String, String> headers) {

		// headers: X-User-ID; X-Request-ID; Date; X-Client-ID

		// remove location from its parents row also. then delete it
		return updateService.removeLocation(headers.get("X-User-ID"), id);

	}

	@ApiOperation(value = "update location", notes = "This api will give update location")
	@RequestMapping(value = { "/" + APPLICATION_NAME + "/{id}" }, method = { RequestMethod.PATCH },
			consumes = { "application/json" })
	public @ResponseBody BaseResponse updateLocation(@PathVariable String id,
			@RequestParam(required = false) String name, @RequestParam(required = false) String tag,
			@RequestParam(required = false) String imageUrl,@RequestParam(required = false) String geoLocation,
			 @RequestParam(required = false) String type,
			@RequestParam Long requestTimestamp, @RequestHeader Map<String, String> headers) {

		 return updateService.updateLocation(headers.get("X-User-ID"),id,name,tag,imageUrl,geoLocation,type);

	}

	@ApiOperation(value = "get requests", notes = "This api will give get requests")
	@RequestMapping(value = { "/" + APPLICATION_NAME + "/requests/{n}" }, method = { RequestMethod.GET },
			consumes = { "application/json" })
	public @ResponseBody BaseResponse<List<String>> getRequests(@PathVariable String x,
			@RequestParam Long requestTimestamp, @RequestHeader Map<String, String> headers) {

		// headers: X-User-ID; X-Request-ID; Date; X-Client-ID

		return getService.getRequests(headers.get("X-User-ID"), x);

	}

	@ApiOperation(value = "discard requests", notes = "This api will give discard requests")
	@RequestMapping(value = { "/" + APPLICATION_NAME + "/{id}" }, method = { RequestMethod.PATCH },
			consumes = { "application/json" })
	public @ResponseBody BaseResponse discardRequests(@RequestBody List<String> locationIdList,
			@RequestParam Long requestTimestamp, @RequestHeader Map<String, String> headers) {

		// headers: X-User-ID; X-Request-ID; Date; X-Client-ID

		return updateService.discardRequests(headers.get("X-User-ID"), locationIdList);

	}

}
