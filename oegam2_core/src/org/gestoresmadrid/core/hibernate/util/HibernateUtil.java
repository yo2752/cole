package org.gestoresmadrid.core.hibernate.util;


import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

import utilidades.mensajes.GestorFicherosPropiedades;

public class HibernateUtil {
	
	private static SessionFactory sessionFactory = buildSessionFactory();

	private static SessionFactory buildSessionFactory() {
		
		try {
			GestorFicherosPropiedades gestor = new GestorFicherosPropiedades("hibernate.properties");
			
			if (gestor.getMensaje("connection.type").equals("jndi")){
				return new AnnotationConfiguration()
				.setProperty("hibernate.connection.driver_class", gestor.getMensaje("hibernate.connection.driver_class"))
				.setProperty("hibernate.jndi.url", gestor.getMensaje("hibernate.jndi.url"))
				.setProperty("hibernate.dialect", gestor.getMensaje("hibernate.dialect"))
				.setProperty("hibernate.current_session_context_class", gestor.getMensaje("hibernate.current_session_context_class"))
				.setProperty("hibernate.show_sql", gestor.getMensaje("hibernate.show.sql"))
				.setProperty("hibernate.connection.datasource", gestor.getMensaje("hibernate.connection.datasource"))
				.configure(gestor.getMensaje("hibernate.configXml")).buildSessionFactory();
			}else{
				return new AnnotationConfiguration()
				.setProperty("hibernate.connection.driver_class", gestor.getMensaje("hibernate.connection.driver_class"))
				.setProperty("hibernate.connection.url", gestor.getMensaje("hibernate.connection.url"))
				.setProperty("hibernate.connection.username", gestor.getMensaje("hibernate.connection.user"))
				.setProperty("hibernate.connection.password", gestor.getMensaje("hibernate.connection.password"))
				.setProperty("hibernate.dialect", gestor.getMensaje("hibernate.dialect"))
				.setProperty("hibernate.current_session_context_class", gestor.getMensaje("hibernate.current_session_context_class"))
				.setProperty("hibernate.show_sql", gestor.getMensaje("hibernate.show.sql"))
				.configure(gestor.getMensaje("hibernate.configXml")).buildSessionFactory();
			}
		} catch (Throwable e) {
			throw new ExceptionInInitializerError(e);
		} 
	}
	
	public static SessionFactory getSessionFactory(){
		return sessionFactory;
	}
	
	public static Session createSession(){
		
		// Conecto a la base de datos con hibernate
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		return session;
		
	}
	
	public static HibernateBean createCriteria(Class<?> hibernateClass){
		
		HibernateBean hibernateBean = new HibernateBean();
		
		// Conecto a la base de datos con hibernate
		Session session = createSession();
		
		// Creo el objeto criteria
		Criteria criteria = session.createCriteria(hibernateClass);
		
		// Creo el objeto projections
		//ProjectionList projections = Projections.projectionList();
		
		// Seteo el bean de Hibernate
		hibernateBean.setSession(session);
		hibernateBean.setCriteria(criteria);
		//hibernateBean.getCriteria().setProjection(projections);
		
		// Seteo las projections en el bean
		//hibernateBean.setProjections(projections);
		
		return hibernateBean;
		
	}
	
	/**
	 * 
	 * Para evitar los lazzys, buscamos los getters de los objetos indicados
	 * para recuperarlo. Si el getter perteneze a una colleción, realizamos
	 * Hibernate.initialize para traernos todos sin tener que recorrer la lista.
	 * 
	 * @param _class
	 * @param _object
	 * @param _param
	 * @return
	 */
	public static Object initialize(Object _object, String _param) {
		if (_object != null) {
			if (_param.isEmpty()) {
				return _object;
			}
			if (_param.contains(".")) {
				Object o = initialize(_object, _param.substring(0, _param.lastIndexOf(".")));
				if (o != null) {
					return initialize(o, _param.substring(_param.lastIndexOf(".") + 1));
				}
			}
			String formated = _param.substring(0, 1).toUpperCase() + _param.substring(1);
			try {
				Object o = _object.getClass().getMethod("get" + formated).invoke(_object);
				// Si es una lista, se inicializa
				Hibernate.initialize(o);
				return o;
			} catch (Exception e) {
				try {
					// Asumiendo buenas prácticas... si es un isXXX tiene
					// que devolver un booleano, no una colección.
					return _object.getClass().getMethod("is" + formated).invoke(_object);
				} catch (Exception e2) {
				}
			}
		}
		return _object;
	}

}
