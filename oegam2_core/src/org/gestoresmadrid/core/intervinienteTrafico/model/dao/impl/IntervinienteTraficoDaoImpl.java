package org.gestoresmadrid.core.intervinienteTrafico.model.dao.impl;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.intervinienteTrafico.model.dao.IntervinienteTraficoDao;
import org.gestoresmadrid.core.intervinienteTrafico.model.vo.IntervinienteTraficoVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.hibernate.Criteria;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class IntervinienteTraficoDaoImpl extends GenericDaoImplHibernate<IntervinienteTraficoVO> implements IntervinienteTraficoDao {

	private static final long serialVersionUID = 6155598259740629671L;

	@Override
	public IntervinienteTraficoVO getIntervinienteTrafico(BigDecimal numExpediente, String tipoInterviniente, String nif, String numColegiado) {
		Criteria criteria = getCurrentSession().createCriteria(IntervinienteTraficoVO.class);
		if (numExpediente != null) {
			criteria.add(Restrictions.eq("id.numExpediente", numExpediente));
		}
		if (tipoInterviniente != null && !tipoInterviniente.isEmpty()) {
			criteria.add(Restrictions.eq("id.tipoInterviniente", tipoInterviniente));
		}
		if (nif != null && !nif.isEmpty()) {
			criteria.add(Restrictions.eq("id.nif", nif));
		}
		if (numColegiado != null && !numColegiado.isEmpty()) {
			criteria.add(Restrictions.eq("id.numColegiado", numColegiado));
		}

		criteria.createAlias("direccion", "direccion", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("persona", "persona", CriteriaSpecification.LEFT_JOIN);

		@SuppressWarnings("unchecked")
		List<IntervinienteTraficoVO> lista = criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<IntervinienteTraficoVO> getListaIntervinientesTramite(BigDecimal numExpediente) {
		Criteria criteria = getCurrentSession().createCriteria(IntervinienteTraficoVO.class);
		criteria.add(Restrictions.eq("id.numExpediente", numExpediente));
		criteria.createAlias("tramiteTrafico", "tramiteTrafico", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("persona", "persona", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("direccion", "direccion", CriteriaSpecification.LEFT_JOIN);
		return criteria.list();
	}

	@Override
	public Integer comprobarcomprobarTramitesEmpresaST(Long idContrato, Date fecha, String nifInterviniente, String codigoPostal,
			String idMunicipio, String idProvincia, String tipoTramiteSolicitud) {
		Criteria criteria = getCurrentSession().createCriteria(IntervinienteTraficoVO.class);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 0, 0, 0);
		Date fecMin = calendar.getTime();
		calendar.setTime(fecha);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 23, 59, 59);
		Date fecMax = calendar.getTime();
		Conjunction and = Restrictions.conjunction();
		and.add(Restrictions.ge("tramiteTrafico.fechaPresentacion", fecMin));
		and.add(Restrictions.lt("tramiteTrafico.fechaPresentacion", fecMax));
		criteria.add(and);
		criteria.add(Restrictions.in("tramiteTrafico.estado", new BigDecimal[]{new BigDecimal(12), new BigDecimal(14)}));
		criteria.add(Restrictions.eq("tramiteTraficoContrato.idContrato", idContrato.longValue()));
		criteria.add(Restrictions.eq("tramiteTrafico.tipoTramite", TipoTramiteTrafico.Matriculacion.getValorEnum()));
		criteria.add(Restrictions.eq("id.tipoInterviniente", TipoInterviniente.Titular.getValorEnum()));
		criteria.add(Restrictions.eq("id.nif",nifInterviniente));
		criteria.add(Restrictions.eq("direccion.codPostalCorreos",codigoPostal));
		criteria.add(Restrictions.eq("direccion.idMunicipio",idMunicipio));
		criteria.add(Restrictions.eq("direccion.idProvincia",idProvincia));
		if (TipoTramiteTrafico.Solicitud_Permiso.getValorEnum().equals(tipoTramiteSolicitud)) {
			criteria.add(Restrictions.isNull("tramiteTrafico.docPermiso"));
		} else if (TipoTramiteTrafico.Solicitud_Ficha_Tecnica.getValorEnum().equals(tipoTramiteSolicitud)) {
			criteria.add(Restrictions.isNull("tramiteTrafico.docFichaTecnica"));
			criteria.add(Restrictions.isNotNull("tramiteTraficoVehiculo.nive"));
		}
		criteria.createAlias("tramiteTrafico", "tramiteTrafico", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("tramiteTrafico.vehiculo", "tramiteTraficoVehiculo", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("persona", "persona", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("direccion", "direccion", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("tramiteTrafico.contrato", "tramiteTraficoContrato", CriteriaSpecification.LEFT_JOIN);
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		criteria.setProjection(Projections.rowCount());
		return (Integer) criteria.uniqueResult();
	}

	@Override
	public List<IntervinienteTraficoVO> getListaTramitesPorIntervieniente(String nif, BigDecimal idContrato,
			String tipoInterviniente, Date fecha, String tipoTramite, String codigoPostal, String idMunicipio,
			String idProvincia, String tipoTramiteSolicitud) {
		Criteria criteria = getCurrentSession().createCriteria(IntervinienteTraficoVO.class);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 0, 0, 0);
		Date fecMin = calendar.getTime();
		calendar.setTime(fecha);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 23, 59, 59);
		Date fecMax = calendar.getTime();
		Conjunction and = Restrictions.conjunction();
		and.add(Restrictions.ge("tramiteTrafico.fechaPresentacion", fecMin));
		and.add(Restrictions.lt("tramiteTrafico.fechaPresentacion", fecMax));
		criteria.add(and);
		if (TipoTramiteTrafico.Solicitud_Permiso.getValorEnum().equals(tipoTramiteSolicitud)) {
			criteria.add(Restrictions.isNull("tramiteTrafico.docPermiso"));
		} else if (TipoTramiteTrafico.Solicitud_Ficha_Tecnica.getValorEnum().equals(tipoTramiteSolicitud)){
			criteria.add(Restrictions.isNull("tramiteTrafico.docFichaTecnica"));
			criteria.add(Restrictions.isNotNull("tramiteTraficoVehiculo.nive"));
		}
		criteria.add(Restrictions.in("tramiteTrafico.estado", new BigDecimal[]{new BigDecimal(12), new BigDecimal(14)}));
		criteria.add(Restrictions.eq("tramiteTraficoContrato.idContrato", idContrato.longValue()));
		criteria.add(Restrictions.eq("tramiteTrafico.tipoTramite", tipoTramite));
		criteria.add(Restrictions.eq("id.tipoInterviniente", TipoInterviniente.Titular.getValorEnum()));
		criteria.add(Restrictions.eq("id.nif",nif));
		criteria.add(Restrictions.eq("direccion.codPostalCorreos",codigoPostal));
		criteria.add(Restrictions.eq("direccion.idMunicipio",idMunicipio));
		criteria.add(Restrictions.eq("direccion.idProvincia",idProvincia));
		criteria.createAlias("tramiteTrafico", "tramiteTrafico", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("tramiteTrafico.vehiculo", "tramiteTraficoVehiculo", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("persona", "persona", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("direccion", "direccion", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("tramiteTrafico.contrato", "tramiteTraficoContrato", CriteriaSpecification.LEFT_JOIN);
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	@Override
	public List<IntervinienteTraficoVO> getExpedientesDireccion(String nif, String numColegiado, Long idDireccion) {
		Criteria criteria = getCurrentSession().createCriteria(IntervinienteTraficoVO.class);
		if (nif != null && !nif.isEmpty()) {
			criteria.add(Restrictions.eq("id.nif", nif));
		}
		if (numColegiado != null && !numColegiado.isEmpty()) {
			criteria.add(Restrictions.eq("id.numColegiado", numColegiado));
		}
		if (idDireccion != null) {
			criteria.add(Restrictions.eq("idDireccion", idDireccion));
		}

		criteria.createAlias("direccion", "direccion", CriteriaSpecification.LEFT_JOIN);

		@SuppressWarnings("unchecked")
		List<IntervinienteTraficoVO> lista = criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista;
		}
		return Collections.emptyList();
	}

	@Override
	public IntervinienteTraficoVO getIntervinientePorNifColegiadoExpedienteYTipo(String nif, String numColegiado, BigDecimal numExpediente, String tipoInterviniente) {
		Criteria criteria = getCurrentSession().createCriteria(IntervinienteTraficoVO.class);
		criteria.add(Restrictions.eq("id.nif", nif));
		criteria.add(Restrictions.eq("id.numColegiado", numColegiado));
		criteria.add(Restrictions.eq("id.numExpediente", numExpediente));
		criteria.add(Restrictions.eq("id.tipoInterviniente", tipoInterviniente));
		criteria.createAlias("tramiteTrafico", "tramiteTrafico", CriteriaSpecification.LEFT_JOIN);
		return (IntervinienteTraficoVO) criteria.uniqueResult();
	}
}