package com.wdf.location.exceptions.business;

import com.wdf.location.exceptions.AbstractException;
import com.wdf.location.response.RespCodes;

public class BusinessException extends AbstractException {

	private RespCodes.RespCode respCode;

	public BusinessException(RespCodes.RespCode s) {
		super(s);
		this.respCode = s;
	}

	public String getRespCode() {
		return respCode == null ? null : respCode.name();
	}

	@Override
	public RespCodes.RespCode code() {
		return respCode;
	}

}
