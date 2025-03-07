package org.gestoresmadrid.core.registradores.model.dao.impl;

import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.registradores.model.dao.RegistroFueraSecuenciaDao;
import org.gestoresmadrid.core.registradores.model.vo.RegistroFueraSecuenciaVO;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class RegistroFueraSecuenciaDaoImpl extends GenericDaoImplHibernate<RegistroFueraSecuenciaVO> implements RegistroFueraSecuenciaDao {

	private static final long serialVersionUID = 4271669570342947723L;

	@Override
	public List<RegistroFueraSecuenciaVO> getRegistrosFueraSecuencia(BigDecimal idTramiteRegistro) {
		Criteria criteria = getCurrentSession().createCriteria(RegistroFueraSecuenciaVO.class);
		if (idTramiteRegistro != null) {
			criteria.add(Restrictions.eq("idTramiteRegistro", idTramiteRegistro));
		}
		criteria.add(Restrictions.isNull("fechaEnvio"));
		@SuppressWarnings("unchecked")
		List<RegistroFueraSecuenciaVO> lista = criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista;
		}
		return null;
	}
	
	@Override
	public RegistroFueraSecuenciaVO getRegistroFueraSecuencia(Long idRegistroFueraSecuencia) {
		Criteria criteria = getCurrentSession().createCriteria(RegistroFueraSecuenciaVO.class);
		if (idRegistroFueraSecuencia != null) {
			criteria.add(Restrictions.eq("idRegistroFueraSecuencia", idRegistroFueraSecuencia));
		}
		@SuppressWarnings("unchecked")
		List<RegistroFueraSecuenciaVO> lista = criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista.get(0);
		}
		return null;
	}
}
