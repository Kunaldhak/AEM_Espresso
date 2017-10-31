package com.app.rccl.espresso.core.servlets;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.app.rccl.espresso.core.models.MyModel;

@Component(service = Servlet.class, property = {

		"sling.servlet.selectors=hotel",
		"sling.servlet.paths=/bin/foo",
		"sling.servlet.methods=get",
		"sling.servlet.resourceTypes=geometrixx/components/homepage" })
public class SlingModelServlet extends SlingAllMethodsServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Logger logger = LoggerFactory.getLogger(this.getClass());
	ResourceResolver resourceResolver;

	public void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
		logger.info("inside sling model test servlet");
		response.setContentType("text/html");
		try {
			// Get the resource (a node in the JCR) using ResourceResolver from the request
			resourceResolver = request.getResourceResolver();
			String nodePath = "/content/espresso/training/slingmodel";
			Resource resource = resourceResolver.getResource(nodePath);
			// Adapt resource properties to variables using ValueMap, and log their values
			ValueMap valueMap = resource.adaptTo(ValueMap.class);
			logger.info("Output from ValueMap is : " + valueMap.get("firstName").toString() + " "
					+ valueMap.get("lastName").toString());
			// Adapt the resource to our model
			logger.info("HERE HERE");
			MyModel mymodel=resource.adaptTo(MyModel.class);
			logger.info("Output message from MyModel is : " + mymodel.message());
			// Print the response in the browser
			response.getWriter().print("My name is : " + mymodel.message());
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
}