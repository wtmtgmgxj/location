package com.wdf.location.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RequestHeader {

	USERID("X-User-ID"), REQUESTID("X-Request-ID"), CLIENTID("X-Client-ID"), DATE("Date");

	private String value;

}
