package com.app.rccl.espresso.core;

import java.util.HashMap;
import java.util.Map;

import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.event.jobs.Job;
import org.apache.sling.event.jobs.consumer.JobConsumer;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;

@Component(service = JobConsumer.class, immediate=true, 
property = {
		JobConsumer.PROPERTY_TOPICS + "=com/app/rccl/espresso/core/replicationjob"
		

})
public class ReplicationLogger implements JobConsumer {

	private static final Logger LOGGER = LoggerFactory.getLogger(ReplicationLogger.class);
	public static final String TOPIC = "com/app/rccl/espresso/core/replicationjob";
	static final String myTopic = JobConsumer.PROPERTY_TOPICS;

	@Reference
	private ResourceResolverFactory resourceResolverFactory;

	@Override
	public JobResult process(final Job job) {
		final String pagePath = job.getProperty("PAGE_PATH").toString();
		ResourceResolver resourceResolver = null;
		Map<String, Object> serviceParams = new HashMap<String, Object>();
		serviceParams.put(ResourceResolverFactory.SUBSERVICE, "espresso");
		try {
			resourceResolver = resourceResolverFactory.getServiceResourceResolver(serviceParams);
		} catch (org.apache.sling.api.resource.LoginException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Create a Page object to log its title
		final PageManager pm = resourceResolver.adaptTo(PageManager.class);
		final Page page = pm.getContainingPage(pagePath);
		if (page != null) {
			LOGGER.info("******************* ACTIVATION OF PAGE : {}", page.getTitle());
		}
		return JobConsumer.JobResult.OK;
	}
}
