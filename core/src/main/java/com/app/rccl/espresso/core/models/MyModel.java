package com.app.rccl.espresso.core.models;

import javax.inject.Inject;
import javax.inject.Named;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Model(adaptables = Resource.class)
public class MyModel {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	@Inject
	private String firstName;
	@Inject
	@Named("lastName")
	@Default(values = "No name defined")
	protected String name;

	public String message() {
		logger.info("Name Is : " + name);
		return firstName + " " + name;
	}
}