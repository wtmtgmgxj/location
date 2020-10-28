package com.wdf.location.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RequestHeader {

	USERID("x-user-id"), REQUESTID("x-request-id"), CLIENTID("x-client-id"), DATE("date");

	private String value;

}
