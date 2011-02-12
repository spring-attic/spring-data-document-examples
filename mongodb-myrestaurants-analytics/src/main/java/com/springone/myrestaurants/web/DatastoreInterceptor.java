package com.springone.myrestaurants.web;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.document.analytics.MvcEvent;
import org.springframework.data.document.analytics.Parameters;
import org.springframework.data.document.mongodb.MongoTemplate;
import org.springframework.data.document.web.servlet.ActionExecutedContext;
import org.springframework.data.document.web.servlet.ActionExecutingContext;
import org.springframework.data.document.web.servlet.ActionInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.mongodb.BasicDBObject;
import com.mongodb.WriteResult;

@Component
public class DatastoreInterceptor implements ActionInterceptor {

	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Override
	public boolean preHandle(ActionExecutingContext actionExecutingContext) {
		System.out.println(actionExecutingContext);
		return true;
	}

	@Override
	public void postHandle(ActionExecutedContext actionExecutedContext) {
		System.out.println(actionExecutedContext);

	}

	@Override
	public void afterCompletion(ActionExecutedContext actionExecutedContext) {
		
		printDebug(actionExecutedContext);
		
		MvcEvent event = createMvcEvent(actionExecutedContext);
		
		mongoTemplate.save(event);

		storeCounterData(actionExecutedContext.getHandler().getClass().getSimpleName(), 
						 actionExecutedContext.getHandlerMethod().getName());
		
		
	}
	
	public void storeCounterData(String controllerName, String methodName) {
		BasicDBObject query = new BasicDBObject("name", controllerName);
		
		BasicDBObject changes = new BasicDBObject();
		changes.put("$set", new BasicDBObject("name", controllerName));
		changes.put("$inc", new BasicDBObject("count", 1));
		
		WriteResult r = mongoTemplate.getCollection("counters").update(query, changes, true,false);
		System.out.println(r);
				
		changes = new BasicDBObject("$inc", new BasicDBObject("methods." + methodName, 1));
		r = mongoTemplate.getCollection("counters").update(query, changes, true, false);
		System.out.println(r);
	}


	private void printDebug(ActionExecutedContext actionExecutedContext) {
		//System.out.println(actionExecutedContext);
		HttpServletRequest request = actionExecutedContext.getHttpServletRequest();
		System.out.println("Handler Class = " + actionExecutedContext.getHandler().getClass());
		System.out.println("Request URI = " + actionExecutedContext.getHttpServletRequest().getRequestURI());
		System.out.println("Remote Address = " + actionExecutedContext.getHttpServletRequest().getRemoteAddr());
		HttpSession session = actionExecutedContext.getHttpServletRequest().getSession(false);
		if (session != null ) {
			System.out.println("Session ID = " + session.getId());
		}
		String remoteUser = request.getRemoteUser();
		if (StringUtils.hasLength(remoteUser)) {
			System.out.println("Remote User = " + remoteUser);
		}		
	}

	private MvcEvent createMvcEvent(
			ActionExecutedContext actionExecutedContext) {
		MvcEvent event = new MvcEvent();
		event.setAction(actionExecutedContext.getHandlerMethod().getName());
		event.setController(actionExecutedContext.getHandler().getClass().getSimpleName());
		event.setDate(new Date());
		Parameters parameters = new Parameters();
		Object[] args = actionExecutedContext.getHandlerParameters();
		for (int i = 0; i < args.length; i++) {
			if (i==0) {
				if (args[i] != null) parameters.setP1(args[i].toString());
			}
			if (i==1) {
				if (args[i] != null) parameters.setP2(args[i].toString());
			}
			if (i==2) {
				if (args[i] != null) parameters.setP3(args[i].toString());			
			}
		}
		event.setParameters(parameters);
		event.setRemoteUser(actionExecutedContext.getHttpServletRequest().getRemoteUser());
		event.setRequestAddress(actionExecutedContext.getHttpServletRequest().getRemoteAddr());
		event.setRequestUri(actionExecutedContext.getHttpServletRequest().getRequestURI());
		return event;
	}

}
