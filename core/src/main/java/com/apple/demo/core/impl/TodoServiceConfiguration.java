package com.apple.demo.core.impl;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(name = "My Service Configuration", description = "Service Configuration")
public @interface TodoServiceConfiguration {
	
	@AttributeDefinition(name = "Config Value", description = "Configuration value")
	String configValue() default "https://jsonplaceholder.typicode.com/todos/1";
	

}