package com.app.rccl.espresso.core.servlets;

import java.io.IOException;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.jcr.ValueFactory;
import javax.jcr.query.qom.Constraint;
import javax.jcr.query.qom.QueryObjectModel;
import javax.jcr.query.qom.QueryObjectModelFactory;
import javax.jcr.query.qom.Selector;
import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.commons.json.JSONArray;
import org.apache.sling.commons.json.JSONObject;
import org.osgi.service.component.annotations.Component;

import com.day.cq.wcm.api.PageManager;
@Component(service=Servlet.class,property = {

		"sling.servlet.selectors=search",
		"sling.servlet.methods=get",
		"sling.servlet.resourceTypes=geometrixx/components/homepage" })

public class SearchServlet extends SlingSafeMethodsServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3169795937693969416L;

	@Override
	public final void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
		throws ServletException, IOException {
		response.setHeader("Content-Type", "application/json");
		JSONObject jsonObject = new JSONObject();
		JSONArray resultArray = new JSONArray();
		
		try {
			// this is the current node that is requested, in case of a page that is the jcr:content node
			Node currentNode = request.getResource().adaptTo(Node.class);
			PageManager pageManager = request.getResource().getResourceResolver().adaptTo(PageManager.class);
			// node that is the cq:Page containing the requested node
			Node queryRoot = pageManager.getContainingPage(currentNode.getPath()).adaptTo(Node.class);
			
			String queryTerm = request.getParameter("q");
			if (queryTerm != null) {
				NodeIterator searchResults = performSearch(queryRoot, queryTerm);
				while (searchResults.hasNext()) resultArray.put(searchResults.nextNode().getPath());
				jsonObject.put("results", resultArray);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
//		response.getWriter().print("SQL VERSION");
		response.getWriter().print(jsonObject.toString());
		response.getWriter().close();
	}

	private NodeIterator performSearch(Node queryRoot, String queryTerm) throws RepositoryException {
		
		// JQOM infrastructure
		QueryObjectModelFactory qf = queryRoot.getSession().getWorkspace().getQueryManager().getQOMFactory();
		ValueFactory vf = queryRoot.getSession().getValueFactory();
		
		final String SELECTOR_NAME = "all results";
		final String SELECTOR_NT_UNSTRUCTURED = "nt:unstructured";
		// select all unstructured nodes
		Selector selector = qf.selector(SELECTOR_NT_UNSTRUCTURED, SELECTOR_NAME);
		
		// full text constraint
		Constraint constraint = qf.fullTextSearch(SELECTOR_NAME, null, qf.literal(vf.createValue(queryTerm)));
		// path constraint
		constraint = qf.and(constraint, qf.descendantNode(SELECTOR_NAME, queryRoot.getPath()));
		
		// execute the query without explicit order and columns
		QueryObjectModel query = qf.createQuery(selector, constraint, null, null);
		return query.execute().getNodes();

	}

}
