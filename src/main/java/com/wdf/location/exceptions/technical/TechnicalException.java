package com.wdf.location.exceptions.technical;

import com.wdf.location.exceptions.AbstractException;
import com.wdf.location.response.ResponseCodes;

public class TechnicalException extends AbstractException {

	private ResponseCodes respCode;

	public TechnicalException(ResponseCodes s) {
		super(s);
		this.respCode = s;
	}

	public String getRespCode() {
		return respCode == null ? null : respCode.name();
	}

	@Override
	public ResponseCodes code() {
		return respCode;
	}

}
