package org.gestoresmadrid.core.vehiculo.model.dao.impl;

import java.util.List;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.vehiculo.model.dao.CambioServPermitidoDao;
import org.gestoresmadrid.core.vehiculo.model.vo.CambioServPermitidoVO;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class CambioServPermitidoDaoImpl extends GenericDaoImplHibernate<CambioServPermitidoVO> implements CambioServPermitidoDao {

	private static final long serialVersionUID = -8832171758081722682L;

	private static final Logger log = LoggerFactory.getLogger(CambioServPermitidoDaoImpl.class);

	@Override
	public List<CambioServPermitidoVO> getCambioPermitido(String idServicioNuevo, String idServicioAnterior) {
		Criteria criteria = getCurrentSession().createCriteria(CambioServPermitidoVO.class);
		criteria.add(Restrictions.eq("id.idServicioTraficoNuevo", idServicioNuevo));
		criteria.add(Restrictions.eq("id.idServicioTraficoActual", idServicioAnterior));
		@SuppressWarnings("unchecked")
		List<CambioServPermitidoVO> lista = criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista;
		}
		return null;
	}
}