package org.gestoresmadrid.core.mensajes.procesos.model.dao.impl;

import java.util.Collections;
import java.util.List;

import org.gestoresmadrid.core.mensajes.procesos.model.dao.MensajesProcesosDao;
import org.gestoresmadrid.core.mensajes.procesos.model.vo.MensajesProcesosVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class MensajesProcesosDaoImpl extends GenericDaoImplHibernate<MensajesProcesosVO> implements MensajesProcesosDao {

	private static final long serialVersionUID = 976485743246758837L;

	@Override
	public List<MensajesProcesosVO> getListaMensajesProcesos() {
		Criteria criteria = getCurrentSession().createCriteria(MensajesProcesosVO.class);

		@SuppressWarnings("unchecked")
		List<MensajesProcesosVO> lista = criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista;
		}
		return Collections.emptyList();
	}

	@Override
	public MensajesProcesosVO getMensajeByCodigo(String codigo) {
		Criteria crit = getCurrentSession().createCriteria(MensajesProcesosVO.class);
		crit.add(Restrictions.eq("codigo", codigo));

		return (MensajesProcesosVO) crit.uniqueResult();
	}
}
