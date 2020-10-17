package com.wdf.location.httpclient.initializer;

import com.wdf.location.httpclient.constants.REST_TEMPLATE_PROPERTY;
import com.wdf.location.httpclient.store.HttpClientStore;
import com.wdf.location.httpclient.template.properties.RestTemplatePropertiesSpecification;
import com.wdf.location.httpclient.template.properties.builder.RestTemplatePropertiesBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

public abstract class HttpClientInitializer {

	@Autowired
	private HttpClientStore clientStore;

	@PostConstruct
	public void initializeAllHttpClients() {

		String clientsToBeInitializedProperty = REST_TEMPLATE_PROPERTY.CREATE_CLIENTS.getPropertyName();

		String clientsToBeInitializedPropertyValue = providePropertyValue(clientsToBeInitializedProperty);

		if (StringUtils.isNotBlank(clientsToBeInitializedPropertyValue)) {

			String[] clientsToBeInitialized = clientsToBeInitializedPropertyValue.split(",");

			for (String clientName : clientsToBeInitialized) {

				initializeHttpClient(clientName.trim());

			}

		}

	}

	public void initializeHttpClient(String clientName) {

		RestTemplatePropertiesBuilder templatePropertiesBuilder = RestTemplatePropertiesBuilder.createFor(clientName);

		for (REST_TEMPLATE_PROPERTY property : REST_TEMPLATE_PROPERTY.values()) {

			String propertyName = property.getPropertyName().replace("clientName", clientName);

			String propertyValue = providePropertyValue(propertyName);

			templatePropertiesBuilder.setPropertyValue(property, propertyValue);

		}

		RestTemplatePropertiesSpecification templateProperties = templatePropertiesBuilder.build();

		clientStore.updateRestTemplateForClient(clientName, templateProperties);

	}

	public abstract String providePropertyValue(String propertyName);

}
