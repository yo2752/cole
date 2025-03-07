package org.gestoresmadrid.core.trafico.ivtm.model.dao.impl;

import java.math.BigDecimal;

import org.gestoresmadrid.core.ivtmMatriculacion.model.vo.ConsultasIvtmVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.trafico.ivtm.model.dao.ConsultaIvtmDao;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import utilidades.estructuras.Fecha;

@Repository
public class ConsultaIvtmDaoImpl extends GenericDaoImplHibernate<ConsultasIvtmVO> implements ConsultaIvtmDao {
	
	private static final long serialVersionUID = -4693744844234710735L;
	
	private static final String TIPO_ID_PETICION_IVTM = "1";

	@Autowired
	UtilesFecha utilesFecha;

	@Override
	public BigDecimal idPeticionMax(String numColegiado) {
		Criteria criteria = getCurrentSession().createCriteria(ConsultasIvtmVO.class);
		String idPeticion = numColegiado;
		Fecha fecha = utilesFecha.getFechaActual();
		idPeticion += fecha.getDia() + fecha.getMes() + fecha.getAnio().substring(2);
		idPeticion += TIPO_ID_PETICION_IVTM;

		criteria.add(Restrictions.sqlRestriction( " ID_PETICION like " + " '"+idPeticion+"%'"));
		criteria.setProjection(Projections.max("idPeticion"));

		BigDecimal maximoExistente = (BigDecimal) criteria.uniqueResult();
		if (maximoExistente == null) {
			return null;
		}
		return maximoExistente;
	}
}