package web;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import constants.Keys;

@WebListener
public class ModelInitialiser implements ServletContextListener {

	public void contextInitialized(ServletContextEvent sce) {
		ServletContext appScope = sce.getServletContext();
		
		// open games
		final Map<String, List<Subscriber>> clients = Collections.synchronizedMap(new HashMap<String, List<Subscriber>>());
		appScope.setAttribute(Keys.CLIENTS, clients);
	}

	public void contextDestroyed(ServletContextEvent sce) {
	}
}
