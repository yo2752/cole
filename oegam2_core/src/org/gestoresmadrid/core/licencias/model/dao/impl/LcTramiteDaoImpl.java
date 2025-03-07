package org.gestoresmadrid.core.licencias.model.dao.impl;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import org.gestoresmadrid.core.licencias.model.dao.LcTramiteDao;
import org.gestoresmadrid.core.licencias.model.vo.LcTramiteVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import utilidades.estructuras.Fecha;

@Repository
public class LcTramiteDaoImpl extends GenericDaoImplHibernate<LcTramiteVO> implements LcTramiteDao {

	private static final long serialVersionUID = -6531629929974359646L;

	private static final String horaFInDia = "23:59";
	private static final int N_SEGUNDOS = 59;

	@Autowired
	UtilesFecha utilesFecha;

	@Override
	public BigDecimal generarNumExpediente(String numColegiado) throws Exception {
		String textNumExpediente = numColegiado;
		Fecha fecha = utilesFecha.getFechaActual();
		Date fin = new Date();
		utilesFecha.setHoraEnDate(fin, horaFInDia);
		utilesFecha.setSegundosEnDate(fin, N_SEGUNDOS);
		textNumExpediente += fecha.getDia() + fecha.getMes() + fecha.getAnio().substring(2);
		Criteria criteria = getCurrentSession().createCriteria(LcTramiteVO.class);
		criteria.add(Restrictions.eq("numColegiado", numColegiado));
		criteria.add(Restrictions.between("fechaAlta", fecha.getFecha(), fin));
		criteria.setProjection(Projections.max("numExpediente"));
		BigDecimal maximoExistente = (BigDecimal) criteria.uniqueResult();
		if (maximoExistente == null) {
			maximoExistente = new BigDecimal(textNumExpediente + "00000");
		}
		return new BigDecimal(maximoExistente.longValue() + 1);
	}

	@Override
	public LcTramiteVO getTramiteLc(BigDecimal numExpediente, boolean completo) {
		Criteria criteria = getCurrentSession().createCriteria(LcTramiteVO.class);
		if (numExpediente != null) {
			criteria.add(Restrictions.eq("numExpediente", numExpediente));
		}

		if (completo) {
			criteria.createAlias("lcDatosSuministros", "lcDatosSuministros", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("lcIdDirEmplazamiento", "lcIdDirEmplazamiento", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("lcActuacion", "lcActuacion", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("lcInfoLocal", "lcInfoLocal", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("lcInfoLocal.lcDirInfoLocal", "idLcInfoLocalLcDirInfoLocal", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("lcObra", "lcObra", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("lcObra.partesAutonomas", "idLcObraLcpartesAutonomas", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("lcEdificaciones", "lcEdificaciones", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("lcEdificaciones.lcResumenEdificacion", "idLcEdificacionesLcResumen", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("lcEdificaciones.lcInfoEdificiosAlta", "idLcEdificacionesLcInfoAlta", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("lcEdificaciones.lcInfoEdificiosAlta.lcDirEdificacionAlta", "idLcEdificacionesLcInfoAltaLcDirEdificacionAlta", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("lcEdificaciones.lcInfoEdificiosAlta.lcDatosPortalesAlta", "idLcEdificacionesLcInfoAltaLcDatosPortalesAlta", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("lcEdificaciones.lcInfoEdificiosAlta.lcDatosPortalesAlta.lcDatosPlantasAlta", "idLcEdificacionesLcInfoAltaLcDatosPortalesAltaLcDatosPlantasAlta", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("lcEdificaciones.lcInfoEdificiosAlta.lcDatosPortalesAlta.lcDatosPlantasAlta.lcSupNoComputablesPlanta", "idLcEdificacionesLcInfoAltaLcDatosPortalesAltaLcDatosPlantasAltaLcSupNoComputablesPlanta", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("lcEdificaciones.lcInfoEdificiosBaja", "idLcEdificacionesLcInfoBaja", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("lcEdificaciones.lcInfoEdificiosBaja.lcDirEdificacion", "idLcEdificacionesLcInfoBajaLcDirEdificacion", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("lcEdificaciones.lcInfoEdificiosBaja.lcDatosPlantasBaja", "idLcEdificacionesLcInfoBajaLcDatosPlantasBaja", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("lcInfoLocal.lcEpigrafes", "idLcInfoLocalLcEpigrafes", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("lcIntervinientes.lcDireccion", "idLcIntervinientesLcDireccion", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("lcIntervinientes.lcPersona", "idLcIntervinientesLcPersona", CriteriaSpecification.LEFT_JOIN);

			criteria.setFetchMode("lcIntervinientes", FetchMode.JOIN);
		}
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return (LcTramiteVO) criteria.uniqueResult();
	}

	@Override
	public LcTramiteVO cambiarEstado(BigDecimal numExpediente, BigDecimal estadoNuevo) {
		LcTramiteVO tramite = getTramiteLc(numExpediente, Boolean.FALSE);
		tramite.setEstado(estadoNuevo);
		tramite = guardarOActualizar(tramite);
		if (tramite != null) {
			return tramite;
		}
		return null;
	}

	@Override
	public Integer getCountTramitesConIdSolicitud() {
		Criteria criteria = getCurrentSession().createCriteria(LcTramiteVO.class);

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		int anio = calendar.get(Calendar.YEAR);

		Calendar calendarInicio = Calendar.getInstance();
		calendarInicio.set(anio, 0, 1, 0, 0, 0);
		Calendar calendarFin = Calendar.getInstance();
		calendarFin.set(anio + 1, 0, 1, 0, 0, 0);

		criteria.add(Restrictions.between("fechaAlta", calendarInicio.getTime(), calendarFin.getTime()));
		criteria.add(Restrictions.isNotNull("idSolicitud"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setProjection(Projections.rowCount());
		return (Integer) criteria.uniqueResult();
	}
}
