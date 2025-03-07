package org.gestoresmadrid.core.hibernate.util;

import org.hibernate.Criteria;
import org.hibernate.Session;

public class HibernateBean {
	
	private Session session;
	private Criteria criteria;
	
	private void HibernateBean(){
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public Criteria getCriteria() {
		return criteria;
	}

	public void setCriteria(Criteria criteria) {
		this.criteria = criteria;
	}

}
