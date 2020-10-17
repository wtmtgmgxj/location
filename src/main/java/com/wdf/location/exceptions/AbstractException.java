package com.wdf.location.exceptions;

import com.wdf.location.response.RespCodes.RespCode;
import lombok.Data;

@Data
public abstract class AbstractException extends RuntimeException {

	public AbstractException(RespCode s) {
		super(s.name());
	}

	public abstract RespCode code();

}
