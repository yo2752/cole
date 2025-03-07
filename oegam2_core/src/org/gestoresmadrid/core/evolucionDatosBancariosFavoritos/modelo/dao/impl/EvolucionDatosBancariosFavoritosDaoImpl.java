package org.gestoresmadrid.core.evolucionDatosBancariosFavoritos.modelo.dao.impl;

import org.gestoresmadrid.core.evolucionDatosBancariosFavoritos.modelo.dao.EvolucionDatosBancariosFavoritosDao;
import org.gestoresmadrid.core.evolucionDatosBancariosFavoritos.modelo.vo.EvolucionDatosBancariosFavoritosVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.springframework.stereotype.Repository;

@Repository
public class EvolucionDatosBancariosFavoritosDaoImpl extends GenericDaoImplHibernate<EvolucionDatosBancariosFavoritosVO> implements EvolucionDatosBancariosFavoritosDao {

	private static final long serialVersionUID = -2886736310277650014L;

}
