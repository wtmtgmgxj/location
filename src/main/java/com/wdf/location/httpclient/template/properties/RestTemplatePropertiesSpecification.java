package com.wdf.location.httpclient.template.properties;

public interface RestTemplatePropertiesSpecification {

	public int getConnectionRequestTimeout();

	public int getConnectionTimeout();

	public int getReadTimeout();

	public int getMaxTotalConnection();

	public int getMaxPerChannel();

	public int getStaleConnectionCheckAfterInactivityPeriod();

	public String getClientName();

	public String getProxyScheme();

	public String getProxyHost();

	public int getProxyPort();

	public boolean ifEquals(RestTemplatePropertiesSpecification properties);

}
