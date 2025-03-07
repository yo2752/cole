package com.integracionsiga.listeners.utils;

import general.utiles.Constantes;
import hibernate.dao.general.PropertyDaoImpl;
import hibernate.entities.general.Property;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

public class PropertiesUtils {

	public HashMap<String, String> refresh() throws Throwable{
		PropertiesUtils propiedades = new PropertiesUtils();
		HashMap<String, String> mapaPropiedades = new HashMap<String, String>();
		mapaPropiedades = propiedades.getMap();
		return mapaPropiedades;
	}

	private void cargarPreferentes(HashMap<String,String> mapaPropiedades) throws IOException{
		Properties oegamProperties = new Properties();
		oegamProperties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(Constantes.OEGAM_PROPERTIES));
		@SuppressWarnings("unchecked")
		Enumeration<String> propiedadesFichero = (Enumeration<String>) oegamProperties.propertyNames();
		while(propiedadesFichero.hasMoreElements()){
			String propiedadFichero = (String)propiedadesFichero.nextElement();
			mapaPropiedades.put(propiedadFichero, oegamProperties.getProperty(propiedadFichero)); 
		}
	}

	private HashMap<String,String> getMap() throws Throwable{
		PropertyDaoImpl propertyDao = new PropertyDaoImpl();
		List<Property> propiedades = propertyDao.load(getEntorno());
		HashMap<String,String> mapaPropiedades = getMapPropiedades(propiedades);
		cargarPreferentes(mapaPropiedades);
		return mapaPropiedades;
	}

	private HashMap<String,String> getMapPropiedades(List<Property> propiedades){
		HashMap<String,String> mapaPropiedades = new HashMap<String,String>();
		for(Property propiedad : propiedades){
			mapaPropiedades.put(propiedad.getNombre(), propiedad.getValor());
		}
		return mapaPropiedades;
	}

	public String getEntorno() throws IOException{
		Properties oegamProperties = new Properties();
		oegamProperties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(Constantes.OEGAM_PROPERTIES));
		return oegamProperties.getProperty(Constantes.ENTORNO).toUpperCase();
	}

}
