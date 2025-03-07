package org.gestoresmadrid.core.arrendatarios.model.dao.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.gestoresmadrid.core.arrendatarios.model.dao.ArrendatarioDao;
import org.gestoresmadrid.core.arrendatarios.model.vo.ArrendatarioVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import utilidades.estructuras.Fecha;

@Repository
public class ArrendatarioDaoImpl extends GenericDaoImplHibernate<ArrendatarioVO>  implements ArrendatarioDao {

	private static final long serialVersionUID = 5652114744878490740L;
	private static final String horaFInDia = "23:59";
	private static final int N_SEGUNDOS = 59;

	@Autowired
	UtilesFecha utilesFecha;

	@Override
	public BigDecimal generarNumExpediente(String numColegiado,String tipoOperacion) throws Exception {
		
		String textNumExpediente = numColegiado;
		Fecha fecha = utilesFecha.getFechaActual();
		Date fin = new Date();
		utilesFecha.setHoraEnDate(fin, horaFInDia);
		utilesFecha.setSegundosEnDate(fin, N_SEGUNDOS);
		textNumExpediente += fecha.getDia() + fecha.getMes() + fecha.getAnio().substring(2) + tipoOperacion /*TipoOperacionCaycEnum.Alta_Arrendatario.getTipo()*/	;

		Criteria criteria = getCurrentSession().createCriteria(ArrendatarioVO.class);
		criteria.add(Restrictions.eq("numColegiado", numColegiado));
		criteria.add(Restrictions.between("fechaAlta", fecha.getFecha(), fin));
		criteria.setProjection(Projections.max("numExpediente"));

		BigDecimal maximoExistente = (BigDecimal) criteria.uniqueResult();
		if (maximoExistente == null) {
			maximoExistente = new BigDecimal(textNumExpediente + "0000");
		}
		return new BigDecimal(maximoExistente.longValue() + 1);
	}

	@Override
	public ArrendatarioVO getArrendatarioPorExpediente(BigDecimal numExpediente, Boolean tramiteCompleto) {
		Criteria criteria = getCurrentSession().createCriteria(ArrendatarioVO.class);
		criteria.add(Restrictions.eq("numExpediente", numExpediente));
		if (tramiteCompleto) {
			criteria.createAlias("contrato", "contrato", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("contrato.colegiado", "contrato.colegiado", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("contrato.colegiado.usuario", "contrato.colegiado.usuario", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("contrato.colegio", "contrato.colegio", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("persona", "persona", CriteriaSpecification.LEFT_JOIN);
			//criteria.createAlias("persona.personaDireccion", "persona.personaDireccion", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("direccion", "direccion", CriteriaSpecification.LEFT_JOIN);
		}

		return (ArrendatarioVO) criteria.uniqueResult();
	}

	@Override
	public ArrendatarioVO getArrendatarioPorId(Long idArrendatario, Boolean tramiteCompleto) {
		Criteria criteria = getCurrentSession().createCriteria(ArrendatarioVO.class);		
		criteria.add(Restrictions.eq("idArrendatario", idArrendatario));		
		if (tramiteCompleto){
			criteria.createAlias("contrato", "contrato", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("contrato.colegiado", "contrato.colegiado", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("contrato.colegiado.usuario", "contrato.colegiado.usuario", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("contrato.colegio", "contrato.colegio", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("persona", "persona", CriteriaSpecification.LEFT_JOIN);
			//criteria.createAlias("persona.personaDireccion", "persona.personaDireccion", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("direccion", "direccion", CriteriaSpecification.LEFT_JOIN);
		}		
		return (ArrendatarioVO) criteria.uniqueResult();
	}
	
	
}
