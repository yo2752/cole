package org.gestoresmadrid.core.vehiculo.model.dao.impl;

import java.util.List;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.vehiculo.model.dao.ServicioTraficoDao;
import org.gestoresmadrid.core.vehiculo.model.vo.ServicioTraficoVO;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class ServicioTraficoDaoImpl extends GenericDaoImplHibernate<ServicioTraficoVO> implements ServicioTraficoDao {

	private static final long serialVersionUID = 4161997464355864441L;

	@Override
	public ServicioTraficoVO getServicioTrafico(String idServicio) {
		Criteria criteria = getCurrentSession().createCriteria(ServicioTraficoVO.class);
		criteria.add(Restrictions.eq("idServicio", idServicio));
		return (ServicioTraficoVO) criteria.uniqueResult();
	}

	@Override
	public List<ServicioTraficoVO> getServicioTraficoPorTipoTramite(String tipoTramite) {
		Criteria criteria = getCurrentSession().createCriteria(ServicioTraficoVO.class);
		criteria.add(Restrictions.or(Restrictions.eq("tipoTramite", tipoTramite), Restrictions.isNull("tipoTramite")));

		@SuppressWarnings("unchecked")
		List<ServicioTraficoVO> lista = criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista;
		}
		return null;
	}
}
