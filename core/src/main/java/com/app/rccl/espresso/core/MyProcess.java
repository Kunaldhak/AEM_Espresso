package com.app.rccl.espresso.core;
import com.day.cq.workflow.WorkflowException;
import com.day.cq.workflow.WorkflowSession;
import com.day.cq.workflow.exec.WorkItem;
import com.day.cq.workflow.exec.WorkflowData;
import com.day.cq.workflow.exec.WorkflowProcess;
import com.day.cq.workflow.metadata.MetaDataMap;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.osgi.framework.Constants;
import javax.jcr.Node;
import javax.jcr.RepositoryException;

@Component
@Service
@Properties({ @Property(name = Constants.SERVICE_DESCRIPTION, value = "A sample workflow process implementation."),
		@Property(name = Constants.SERVICE_VENDOR, value = "Adobe"),
		@Property(name = "process.label", value = "My Sample Workflow Process") })
public class MyProcess implements WorkflowProcess {

	private static final String TYPE_JCR_PATH = "JCR_PATH";

	public void execute(WorkItem item, WorkflowSession session, MetaDataMap args) throws WorkflowException {
		WorkflowData workflowData = item.getWorkflowData();
		if (workflowData.getPayloadType().equals(TYPE_JCR_PATH)) {
			String path = workflowData.getPayload().toString() + "/jcr:content";
			try {
				Node node = (Node) session.getSession().getItem(path);
				if (node != null) {
					node.setProperty("approved", readArgument(args));
					session.getSession().save();
				}
			} catch (RepositoryException e) {
				throw new WorkflowException(e.getMessage(), e);
			}
		}
	}

	private boolean readArgument(MetaDataMap args) {
		String argument = args.get("PROCESS_ARGS", "false");
		return argument.equalsIgnoreCase("true");
	}
}
