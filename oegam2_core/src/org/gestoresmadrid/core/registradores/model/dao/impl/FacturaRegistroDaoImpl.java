package org.gestoresmadrid.core.registradores.model.dao.impl;

import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.registradores.model.dao.FacturaRegistroDao;
import org.gestoresmadrid.core.registradores.model.vo.FacturaRegistroVO;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class FacturaRegistroDaoImpl extends GenericDaoImplHibernate<FacturaRegistroVO> implements FacturaRegistroDao {

	private static final long serialVersionUID = -8799674911360008270L;

	@Override
	public FacturaRegistroVO getFacturaRegistro(Long id) {
		Criteria criteria = getCurrentSession().createCriteria(FacturaRegistroVO.class);
		criteria.add(Restrictions.eq("idFactura", id));
		return (FacturaRegistroVO) criteria.uniqueResult();
	}

	@Override
	public List<FacturaRegistroVO> getFacturasRegistroPorTramite(BigDecimal idTramiteRegistro) {
		Criteria criteria = getCurrentSession().createCriteria(FacturaRegistroVO.class);
		if (null != idTramiteRegistro) {
			criteria.add(Restrictions.eq("idTramiteRegistro", idTramiteRegistro));
		}
		@SuppressWarnings("unchecked")
		List<FacturaRegistroVO> lista = criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista;
		}
		return null;
	}

}
