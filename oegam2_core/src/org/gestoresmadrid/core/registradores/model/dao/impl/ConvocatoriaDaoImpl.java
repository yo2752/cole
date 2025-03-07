package org.gestoresmadrid.core.registradores.model.dao.impl;

import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.registradores.model.dao.ConvocatoriaDao;
import org.gestoresmadrid.core.registradores.model.vo.ConvocatoriaVO;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class ConvocatoriaDaoImpl extends GenericDaoImplHibernate<ConvocatoriaVO> implements ConvocatoriaDao {

	private static final long serialVersionUID = 3387204041268348176L;
	
	@Override
	public ConvocatoriaVO getConvocatoria(BigDecimal idTramiteRegistro, Long idReunion) {
		Criteria criteria = getCurrentSession().createCriteria(ConvocatoriaVO.class);
		if (idTramiteRegistro != null) {
			criteria.add(Restrictions.eq("id.idTramiteRegistro", idTramiteRegistro));
		}
		if (idReunion != null) {
			criteria.add(Restrictions.eq("id.idReunion", idReunion));
		}
		@SuppressWarnings("unchecked")
		List<ConvocatoriaVO> lista = criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista.get(0);
		}
		return null;
	}
	
}
