package com.wdf.location.exceptions.technical;

import com.wdf.location.exceptions.AbstractException;
import com.wdf.location.response.RespCodes.RespCode;
import lombok.Data;

@Data
public class TechnicalException extends AbstractException {

	private RespCode respCode;

	public TechnicalException(RespCode s) {
		super(s);
		this.respCode = s;
	}

	public String getRespCode() {
		return respCode == null ? null : respCode.name();
	}

	@Override
	public RespCode code() {
		return respCode;
	}

}
