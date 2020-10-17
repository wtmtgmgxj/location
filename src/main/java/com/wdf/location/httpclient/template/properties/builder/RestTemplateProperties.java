package com.wdf.location.httpclient.template.properties.builder;

import com.wdf.location.httpclient.template.properties.RestTemplatePropertiesSpecification;
import lombok.Data;

@Data
class RestTemplateProperties implements RestTemplatePropertiesSpecification {

	private int connectionRequestTimeout = 1000;

	private int connectionTimeout = 1000;

	private int readTimeout = 5000;

	private int maxTotalConnection = 50;

	private int maxPerChannel = 50;

	private int staleConnectionCheckAfterInactivityPeriod = 2000;

	private String proxyScheme;

	private String proxyHost;

	private int proxyPort;

	private String clientName;

	RestTemplateProperties() {

	}

	/*
	 * Auto generated Using eclipse to compare if equals these objects.
	 */
	@Override
	public boolean ifEquals(RestTemplatePropertiesSpecification other) {

		if (this == other) {

			return true;

		}

		if (other == null) {

			return false;

		}

		if (connectionRequestTimeout != other.getConnectionRequestTimeout()) {

			return false;

		}

		if (connectionTimeout != other.getConnectionTimeout()) {

			return false;

		}

		if (maxPerChannel != other.getMaxPerChannel()) {

			return false;

		}

		if (maxTotalConnection != other.getMaxTotalConnection()) {

			return false;

		}

		if (readTimeout != other.getReadTimeout()) {

			return false;

		}

		if (staleConnectionCheckAfterInactivityPeriod != other.getStaleConnectionCheckAfterInactivityPeriod()) {

			return false;

		}

		if (isProxySettingSame(other)) {

			return false;

		}

		return isClientNameSame(other.getClientName());

	}

	private boolean isClientNameSame(String otherClientName) {

		if (clientName == null && otherClientName != null) {

			return false;

		}
		else if (clientName == null && otherClientName == null) {

			return true;

		}
		else {

			return clientName.equalsIgnoreCase(otherClientName);

		}

	}

	private boolean isProxySettingSame(RestTemplatePropertiesSpecification other) {

		return isProxyHostSame(other.getProxyHost()) && isProxyPortSame(other.getProxyPort())
				&& isProxySchemeSame(other.getProxyScheme());

	}

	private boolean isProxyHostSame(String otherProxyHost) {

		if (this.proxyHost == null && otherProxyHost != null) {

			return false;

		}

		if (this.proxyHost != null && otherProxyHost == null) {

			return false;

		}

		if (this.proxyHost != null) {

			return this.proxyHost.equalsIgnoreCase(otherProxyHost);

		}

		return true;

	}

	private boolean isProxySchemeSame(String otherProxyScheme) {

		if (this.proxyScheme == null && otherProxyScheme != null) {

			return false;

		}

		if (this.proxyScheme != null && otherProxyScheme == null) {

			return false;

		}

		if (this.proxyScheme != null) {

			return this.proxyScheme.equalsIgnoreCase(otherProxyScheme);

		}

		return true;

	}

	private boolean isProxyPortSame(int otherProxyPort) {

		return otherProxyPort >= 0 && this.proxyPort == otherProxyPort;

	}

}
