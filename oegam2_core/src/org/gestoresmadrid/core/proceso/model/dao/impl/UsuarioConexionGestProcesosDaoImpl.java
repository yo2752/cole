package org.gestoresmadrid.core.proceso.model.dao.impl;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.proceso.model.dao.UsuarioConexionGestProcesosDao;
import org.gestoresmadrid.core.proceso.model.vo.UsuarioConexionGestProcesosVO;
import org.springframework.stereotype.Repository;

@Repository
public class UsuarioConexionGestProcesosDaoImpl extends GenericDaoImplHibernate<UsuarioConexionGestProcesosVO> implements UsuarioConexionGestProcesosDao {

	private static final long serialVersionUID = 5542540041397170311L;
}
