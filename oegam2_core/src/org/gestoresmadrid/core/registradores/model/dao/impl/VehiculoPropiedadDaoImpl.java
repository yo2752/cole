package org.gestoresmadrid.core.registradores.model.dao.impl;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.registradores.model.dao.VehiculoPropiedadDao;
import org.gestoresmadrid.core.registradores.model.vo.VehiculoPropiedadVO;
import org.springframework.stereotype.Repository;

@Repository
public class VehiculoPropiedadDaoImpl extends GenericDaoImplHibernate<VehiculoPropiedadVO> implements VehiculoPropiedadDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1912608794443517956L;
}