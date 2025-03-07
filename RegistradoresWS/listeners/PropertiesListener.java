package com.integracionsigaWS.listeners;

import general.utiles.Constantes;

import java.util.HashMap;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.propiedades.PropertiesReader;

import com.integracionsigaWS.listeners.utils.PropertiesUtils;

public class PropertiesListener implements ServletContextListener {

	private static final ILoggerOegam log = LoggerOegam.getLogger(PropertiesListener.class);

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		try {
			arg0.getServletContext().removeAttribute(Constantes.PROPIEDADES);
		} catch(Throwable t) {
			log.error(t);
		}
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		try{
			HashMap<String, String> mapaPropiedades = new HashMap<String, String>();
			PropertiesReader.setServletContext(arg0);
			PropertiesUtils propertiesUtils = new PropertiesUtils();
			mapaPropiedades = propertiesUtils.refresh();
			arg0.getServletContext().setAttribute(Constantes.PROPIEDADES, mapaPropiedades);
		} catch(Throwable t){
			log.error(t);
		}
	}
}
