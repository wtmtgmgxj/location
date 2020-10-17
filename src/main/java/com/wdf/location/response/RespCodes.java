package com.wdf.location.response;

import lombok.Getter;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.HashMap;

@Component
public class RespCodes {

	private HashMap<String, RespCode> respCodes = new HashMap<>();

	@PostConstruct
	HashMap<String, RespCode> createMap() {
		Arrays.stream(RespCode.values()).forEach(x -> {
			if (x == null || x.getDownstreamCodes() == null || x.getDownstreamCodes().length == 0)
				return;
			for (String singleDownstreamCode : x.getDownstreamCodes()) {
				if (respCodes.containsKey(singleDownstreamCode))
					throw new RuntimeException("Duplicate Key in DownstreamCodes: " + singleDownstreamCode);
				respCodes.put(singleDownstreamCode, x);
			}

		});

		return respCodes;
	}

	public RespCode get(String downstreamCode) {
		return respCodes.get(downstreamCode);
	}

	@Getter
	public enum RespCode {

		OK("ALL_OK", new String[] { "DOWNSTREAM_OK", "DOWNSTREAM_SUCCESS", "DOWNSTREAM2_SCS" }), BAD_REQUEST(
				"BAD_THINGS", new String[] { "DOWNSTREAM2_BR" });

		private final String desc;

		private final String[] downstreamCodes;

		RespCode(String desc, String[] downstreamCodes) {
			this.downstreamCodes = downstreamCodes;
			this.desc = desc;
		}

	}

}
