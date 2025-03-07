package org.gestoresmadrid.core.registradores.model.dao.impl;

import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.registradores.model.dao.MedioConvocatoriaDao;
import org.gestoresmadrid.core.registradores.model.vo.MedioConvocatoriaVO;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class MedioConvocatoriaDaoImpl extends GenericDaoImplHibernate<MedioConvocatoriaVO> implements MedioConvocatoriaDao {

	private static final long serialVersionUID = 7856411721242630553L;
	
	@Override
	public MedioConvocatoriaVO getMediosConvocatoria(Long idMedio, BigDecimal idTramiteRegistro, Long idReunion) {
		Criteria criteria = getCurrentSession().createCriteria(MedioConvocatoriaVO.class);
		if (idMedio != null) {
			criteria.add(Restrictions.eq("id.idMedio", idMedio));
		}
		if (idTramiteRegistro != null) {
			criteria.add(Restrictions.eq("id.idTramiteRegistro", idTramiteRegistro));
		}
		if (idReunion != null) {
			criteria.add(Restrictions.eq("id.idReunion", idReunion));
		}
		criteria.createAlias("medio", "medio");
		@SuppressWarnings("unchecked")
		List<MedioConvocatoriaVO> lista = criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista.get(0);
		}
		return null;
	}

	@Override
	public List<MedioConvocatoriaVO> getMediosConvocatorias(BigDecimal idTramiteRegistro) {
		Criteria criteria = getCurrentSession().createCriteria(MedioConvocatoriaVO.class);
		if (idTramiteRegistro != null) {
			criteria.add(Restrictions.eq("id.idTramiteRegistro", idTramiteRegistro));
		}
		criteria.createAlias("medio", "medio");
		@SuppressWarnings("unchecked")
		List<MedioConvocatoriaVO> lista = criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista;
		}
		return null;
	}
}
