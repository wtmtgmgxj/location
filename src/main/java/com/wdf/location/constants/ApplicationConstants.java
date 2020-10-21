package com.wdf.location.constants;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ApplicationConstants {

	public static final ObjectMapper objectMapper = new ObjectMapper();

	public static final String APPLICATION_NAME = "location";

	public static final String HYPHEN = "-";

	public static final String MDC_KEY = "mdcKey";

	public static final String TRACER = "tracer"; // TODO X-Request-ID used in its place.
													// Remove it if not used later

	public static final String EXTERNAL = "";

	public static final String INTERNAL = "/internal";

}
