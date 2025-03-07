package org.gestoresmadrid.core.empresaDIRe.model.dao.impl;

import java.math.BigDecimal;

import org.gestoresmadrid.core.empresaDIRe.model.dao.EmpresaDIRe_AdministradorDao;
import org.gestoresmadrid.core.empresaDIRe.model.vo.EmpresaDIRe_AdministradorVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.springframework.stereotype.Repository;

@Repository
public class EmpresaDIRe_AdministradorDaoImpl extends GenericDaoImplHibernate<EmpresaDIRe_AdministradorVO> implements EmpresaDIRe_AdministradorDao {

	private static final long serialVersionUID = 5652114744878490740L;
	private static final String horaFInDia = "23:59";
	private static final int N_SEGUNDOS = 59;

	public int Guardar_Administrador(EmpresaDIRe_AdministradorVO objeto) {
		Criteria criteria = getCurrentSession().createCriteria(EmpresaDIRe_AdministradorVO.class);
		criteria.setProjection(Projections.max("id"));
		BigDecimal maximoExistente = (BigDecimal) criteria.uniqueResult();
		int temporal = maximoExistente.intValue();
		temporal++;
		maximoExistente = new BigDecimal(temporal);
		objeto.setId(maximoExistente);
		guardarOActualizar(objeto);
		return 0;
	}

}