package org.gestoresmadrid.core.model.dao.impl;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.engine.SessionImplementor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Repository
public abstract class GenericProcedureDaoImplHibernate implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8958303128940815572L;

	protected static final ILoggerOegam log = LoggerOegam.getLogger(GenericProcedureDaoImplHibernate.class);

	@Autowired
	SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	/**
	 * Crea el elemento callable
	 * @param callQuery
	 * @return
	 * @throws SQLException
	 */
	protected CallableStatement prepareCall(String callQuery) throws SQLException {
		Connection conn = ((SessionImplementor) getCurrentSession()).connection();
		return conn.prepareCall(callQuery);
	}

}
