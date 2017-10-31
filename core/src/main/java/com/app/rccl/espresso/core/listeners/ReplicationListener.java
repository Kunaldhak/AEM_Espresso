package com.app.rccl.espresso.core.listeners;

import java.util.HashMap;

import org.apache.sling.event.jobs.JobManager;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.replication.ReplicationAction;
import com.day.cq.replication.ReplicationActionType;

@Component(service = EventHandler.class, immediate = true, property = {
		"event.topics =" + "ReplicationAction.EVENT_TOPIC"

})
public class ReplicationListener implements EventHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(ReplicationListener.class);
	private static final String TOPIC = "com/app/rccl/espresso/core/replicationjob";

	@Reference
	private JobManager jobManager;

	@Override
	public void handleEvent(final Event event) {
		ReplicationAction action = ReplicationAction.fromEvent(event);
		if (action.getType().equals(ReplicationActionType.ACTIVATE)) {
			if (action.getPath() != null) {
				try {
					// Create a properties map that contains things we want to pass through the job
					HashMap<String, Object> jobprops = new HashMap<String, Object>();
					jobprops.put("PAGE_PATH", action.getPath());
					// Add the job
					jobManager.addJob(TOPIC, jobprops);
				} catch (Exception e) {
					LOGGER.error("********************************* ERROR CREATING JOB : NO PAYLOAD WAS DEFINED");
					e.printStackTrace();
				}
			}
		}
	}
}