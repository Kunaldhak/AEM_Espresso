package com.app.rccl.espresso.core.servlets;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;

@Component(service = Servlet.class,
property = { Constants.SERVICE_DESCRIPTION + "=Simple My DEMO Servlet",
		"sling.servlet.methods=" + HttpConstants.METHOD_GET, 
		"sling.servlet.paths=" + "/bin/rcl/another",
		 "sling.servlet.extensions=" + "html",
		"sling.servlet.resourceTypes= "+ "/apps/espresso/components/content/text" })

public class Titleservlet2 extends SlingSafeMethodsServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -829705735198725758L;

	@Override
    protected void doGet(final SlingHttpServletRequest req,
            final SlingHttpServletResponse resp) throws ServletException, IOException {
        final Resource resource = req.getResource();
        resp.setContentType("text/plain");
        resp.getWriter().write("My Custom Title = " + resource.adaptTo(ValueMap.class).get("jcr:title"));
    }
}