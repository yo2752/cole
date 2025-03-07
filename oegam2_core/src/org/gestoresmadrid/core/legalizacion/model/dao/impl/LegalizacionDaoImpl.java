package org.gestoresmadrid.core.legalizacion.model.dao.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.legalizacion.constantes.ConstantesLegalizacion;
import org.gestoresmadrid.core.legalizacion.model.dao.LegalizacionDao;
import org.gestoresmadrid.core.legalizacion.model.vo.LegalizacionCitaVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Repository
public class LegalizacionDaoImpl extends GenericDaoImplHibernate<LegalizacionCitaVO> implements LegalizacionDao {

	private static final long serialVersionUID = 1L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(LegalizacionDaoImpl.class);

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	private UtilesFecha utilesFecha;

	@Override
	public LegalizacionCitaVO getLegalizacionId(Integer idPeticion) {
		LegalizacionCitaVO leg = null;

		Criteria criteria = getCurrentSession().createCriteria(LegalizacionCitaVO.class);
		criteria.add(Restrictions.eq("idPeticion", idPeticion));
		criteria.createAlias("contrato", "contrato", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.correosTramites", "contratoCorreosTramites", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.correosTramites.tipoTramite", "contratoCorreosTramitesTipoTramite", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.correosTramites.tipoTramite.aplicacion", "contratoCorreosTramitesTipoTramiteAplicacion", CriteriaSpecification.LEFT_JOIN);
		leg = (LegalizacionCitaVO) criteria.uniqueResult();

		return leg;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LegalizacionCitaVO> listadoDiario(String numColegiado, Date fechaLegalizacion) {

		List<LegalizacionCitaVO> listaLeg = null;

		Criteria criteria = getCurrentSession().createCriteria(LegalizacionCitaVO.class);
		if (StringUtils.isNotBlank(numColegiado)) {
			criteria.add(Restrictions.eq("numColegiado", numColegiado));
		}
		criteria.add(Restrictions.eq("fechaLegalizacion", fechaLegalizacion));
		criteria.add(Restrictions.eq("estado", 1));
		criteria.addOrder(Order.asc("numColegiado"));
		criteria.addOrder(Order.asc("orden"));
		criteria.addOrder(Order.asc("nombre"));

		listaLeg = criteria.list();

		return listaLeg;
	}

	@Override
	public boolean esPosiblePeticion(String numColegiado) {

		try {
			// Comprueba que no se supera el límite de peticiones por día ni por colegiado.
			Criteria criteria = getCurrentSession().createCriteria(LegalizacionCitaVO.class);
			criteria.add(Restrictions.eq("numColegiado", numColegiado));
			criteria.add(Restrictions.eq("fechaLegalizacion", utilesFecha.getFechaActual().getFecha()));
			Integer totalColegiado = ((Number) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();

			if (totalColegiado < numMaximoColegiado()) {
				Criteria criteria2 = getCurrentSession().createCriteria(LegalizacionCitaVO.class);
				criteria2.add(Restrictions.eq("fechaLegalizacion", utilesFecha.getFechaActual().getFecha()));
				Integer totalDia = ((Number) criteria2.setProjection(Projections.rowCount()).uniqueResult()).intValue();
				if (totalDia < numMaximoDia()) {
					return true;
				}
			}
		} catch (Exception e) {
			log.error(e);
		}

		return false;
	}

	private Integer numMaximoColegiado() {
		return Integer.parseInt(gestorPropiedades.valorPropertie(ConstantesLegalizacion.MAXIMO_GESTOR));
	}

	private Integer numMaximoDia() {
		return Integer.parseInt(gestorPropiedades.valorPropertie(ConstantesLegalizacion.MAXIMO_DIA));
	}

}
