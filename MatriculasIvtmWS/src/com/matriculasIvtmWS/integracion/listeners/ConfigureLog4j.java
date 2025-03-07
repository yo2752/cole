package com.matriculasIvtmWS.integracion.listeners;

import java.io.File;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class ConfigureLog4j implements ServletContextListener {
	
	private final String PATH_FILE_LOG4J = "WEB-INF/log4jMatriculasIvtmWS.properties";
	
	static Logger log = Logger.getLogger(ConfigureLog4j.class);

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		try{
			String homeDir = servletContextEvent.getServletContext().getRealPath("/");
	        File propertiesFile = new File(homeDir, PATH_FILE_LOG4J);
	        PropertyConfigurator.configure(propertiesFile.toString());
		}catch(Exception ex){
			log.error(ex);
		}
	}
	@Override
	public void contextDestroyed(ServletContextEvent arg0) { }

}