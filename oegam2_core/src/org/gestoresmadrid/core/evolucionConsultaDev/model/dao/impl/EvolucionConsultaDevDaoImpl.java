package org.gestoresmadrid.core.evolucionConsultaDev.model.dao.impl;

import java.math.BigDecimal;

import org.gestoresmadrid.core.consultaDev.model.enumerados.EstadoConsultaDev;
import org.gestoresmadrid.core.evolucionConsultaDev.model.dao.EvolucionConsultaDevDao;
import org.gestoresmadrid.core.evolucionConsultaDev.model.vo.EvolucionConsultaDevVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class EvolucionConsultaDevDaoImpl extends GenericDaoImplHibernate<EvolucionConsultaDevVO> implements EvolucionConsultaDevDao{

	private static final long serialVersionUID = -386144378826174849L;

	
	@Override
	public int getNumPeticionesWS(Long idConsultaDev) {
		Criteria criteria = getCurrentSession().createCriteria(EvolucionConsultaDevVO.class);
		criteria.add(Restrictions.eq("id.idConsultaDev", idConsultaDev));
		criteria.add(Restrictions.eq("estadoNuevo", new BigDecimal(EstadoConsultaDev.Pdte_Consulta_Dev.getValorEnum())));
		return (Integer) criteria.setProjection(Projections.rowCount()).uniqueResult();
	}
}
