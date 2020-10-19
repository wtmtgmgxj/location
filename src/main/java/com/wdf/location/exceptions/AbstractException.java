package com.wdf.location.exceptions;

import com.wdf.location.response.ResponseCodes;
import lombok.Data;

@Data
public abstract class AbstractException extends RuntimeException {

	public AbstractException(ResponseCodes s) {
		super(s.name());
	}

	public abstract ResponseCodes code();

}
