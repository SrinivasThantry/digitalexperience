package com.apple.demo.core.workflow;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.ValueFormatException;
import javax.jcr.lock.LockException;
import javax.jcr.nodetype.ConstraintViolationException;
import javax.jcr.version.VersionException;

import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowProcess;
import com.adobe.granite.workflow.metadata.MetaDataMap;
import com.apple.demo.core.TodoService;
import com.day.cq.commons.jcr.JcrConstants;
import com.google.gson.JsonObject;

@Component(property = { Constants.SERVICE_DESCRIPTION + "= Add custom page properties on page creation",
		Constants.SERVICE_VENDOR + "=Adobe Systems", "process.label" + "=Add Custom page properties" })
public class AddCustomPageProperties implements WorkflowProcess {

	private static final Logger log = LoggerFactory.getLogger(AddCustomPageProperties.class);
	
	@Reference
	TodoService todoService;
	
	
	@Override
	public void execute(WorkItem workItem, WorkflowSession workflowSession, MetaDataMap processArguments)
			throws WorkflowException {
		String payloadPath = workItem.getWorkflowData().getPayload().toString();
		ResourceResolver resourceResolver = workflowSession.adaptTo(ResourceResolver.class);
		Node node = resourceResolver.getResource(payloadPath + "/" + JcrConstants.JCR_CONTENT).adaptTo(Node.class);
		log.info("payloadPath:"+payloadPath+"::"+node);
		try {
			JsonObject todoobj = todoService.getTodo();
			log.info("todoobj:"+todoobj+"::");
			String userid = todoobj.get("userId").getAsString();
			String id = todoobj.get("id").getAsString();
			String title = todoobj.get("title").getAsString();
			boolean iscompleted = todoobj.get("completed").getAsBoolean();
			node.setProperty("userid", userid);
			node.setProperty("id", id);
			node.setProperty("title", title);
			node.setProperty("iscompleted", iscompleted);
		} catch (ValueFormatException e) {
			e.printStackTrace();
		} catch (VersionException e) {
			e.printStackTrace();
		} catch (LockException e) {
			e.printStackTrace();
		} catch (ConstraintViolationException e) {
			e.printStackTrace();
		} catch (RepositoryException e) {
			e.printStackTrace();
		}
		
	}

}
