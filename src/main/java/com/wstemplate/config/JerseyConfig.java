package com.wstemplate.config;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;
import org.springframework.stereotype.Component;

import com.wstemplate.errorhandling.ResourceNotFoundExceptionMapper;
import com.wstemplate.resources.ClientResource;

@Component
public class JerseyConfig extends ResourceConfig {
	public JerseyConfig() {
		register(RequestContextFilter.class);
        register(ClientResource.class);
        register(JacksonFeature.class);
        register(CORSFilter.class);
        register(ResourceNotFoundExceptionMapper.class);
	}
}
