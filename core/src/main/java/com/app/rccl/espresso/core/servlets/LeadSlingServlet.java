package com.app.rccl.espresso.core.servlets;

import java.io.IOException;

import javax.jcr.Repository;
import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;


@Component(service=Servlet.class,
property={
        Constants.SERVICE_DESCRIPTION + "=Simple Servlet Demo Testing",
        "sling.servlet.methods=" + HttpConstants.METHOD_GET,
        "sling.servlet.paths=" + "/bin/rcl/titleservlet1" ,
        "sling.servlet.resourceTypes="+ "apps/geometrixx/components/lead",
        "sling.servlet.selectors = "+ "lead-sling-servlet",
        "sling.servlet.extensions=" + "html"
})
public class LeadSlingServlet extends SlingSafeMethodsServlet {

	@Reference
	private Repository repository;

	/**
	 * 
	 */
	private static final long serialVersionUID = 2819632804074304452L;

	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
		response.setHeader("Content-Type", "text/html");
		String[] keys = repository.getDescriptorKeys();
		response.getWriter().print("<br><table border='1'><th>Repository Descriptor</th><th>Value</th>");
		for (int i = 0; i < keys.length; i++) {
			try {
				response.getWriter()
						.print("<tr><td>" + keys[i] + "</td><td>" + repository.getDescriptor(keys[i]) + "</td></tr>");
			} finally {
			}
		}
		response.getWriter().print("</table>");
	}

}
