package org.gestoresmadrid.oegam2comun.licenciasCam.model.service.impl;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.licencias.model.dao.LcEdificacionDao;
import org.gestoresmadrid.core.licencias.model.enumerados.TipoEdificacionEnum;
import org.gestoresmadrid.core.licencias.model.vo.LcEdificacionVO;
import org.gestoresmadrid.oegam2comun.licenciasCam.model.service.ServicioLcEdificacion;
import org.gestoresmadrid.oegam2comun.licenciasCam.model.service.ServicioLcInfoEdificioAlta;
import org.gestoresmadrid.oegam2comun.licenciasCam.model.service.ServicioLcInfoEdificioBaja;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.bean.ResultadoLicenciasBean;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.dto.LcEdificacionDto;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.dto.LcInfoEdificioAltaDto;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.dto.LcInfoEdificioBajaDto;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.dto.LcResumenEdificacionDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioLcEdificacionImpl implements ServicioLcEdificacion {

	private static final long serialVersionUID = 7305605957238024710L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioLcEdificacionImpl.class);

	@Autowired
	ServicioLcInfoEdificioAlta servicioLcInfoEdificioAlta;

	@Autowired
	ServicioLcInfoEdificioBaja servicioLcInfoEdificioBaja;

	@Autowired
	LcEdificacionDao edificacionDao;

	@Autowired
	Conversor conversor;

	@Transactional
	@Override
	public ResultadoLicenciasBean guardarOActualizarEdificacion(LcEdificacionDto edificacionDto, boolean tipoAlta) {
		ResultadoLicenciasBean resultado = new ResultadoLicenciasBean(Boolean.FALSE);
		try {
			if (tipoAlta) {
				edificacionDto.setTipoEdificacion(TipoEdificacionEnum.Edificacion_Alta.getValorEnum());
			} else {
				edificacionDto.setTipoEdificacion(TipoEdificacionEnum.Edificacion_Baja.getValorEnum());
			}

			LcEdificacionVO edificacionAltaVO = edificacionDao.guardarOActualizar(conversor.transform(edificacionDto, LcEdificacionVO.class));

			if (edificacionAltaVO != null) {
				resultado.setMensaje("Datos de Edificaci�n Alta actualizados correctamente");
				resultado.setObj(edificacionAltaVO.getIdEdificacion());
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Error al actualizar los datos de Edificaci�n Alta");
			}

		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de guardar Edificaci�n Alta, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de guardar Edificaci�n Alta.");
		}
		if (resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}

		return resultado;
	}

	@Override
	public void validarEdificacionAlta(LcEdificacionDto edifAlta, ResultadoLicenciasBean resultado) {
		if (edifAlta.getNumEdificios() == null) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El campo N�mero de Edificios de la Edificaci�n Alta es obligatorio.");
		}
		if (StringUtils.isBlank(edifAlta.getDescripcion())) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El campo Descripci�n de la Edificaci�n Alta es obligatorio.");
		}
		if (StringUtils.isBlank(edifAlta.getTipologia())) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El campo Tipolog�a de la Edificaci�n Alta es obligatorio.");
		}

		if (edifAlta.getLcResumenEdificacionVivienda() != null) {
			if (!validarResumenEdificacion(edifAlta.getLcResumenEdificacionVivienda())) {
				resultado.setError(Boolean.TRUE);
				resultado.addValidacion("Los campos del Resumen Edificaci�n Alta Vivienda son obligatorios.");
			}
		}
		if (edifAlta.getLcResumenEdificacionLocal() != null) {
			if (!validarResumenEdificacion(edifAlta.getLcResumenEdificacionVivienda())) {
				resultado.setError(Boolean.TRUE);
				resultado.addValidacion("Los campos del Resumen Edificaci�n Alta Local son obligatorios.");
			}
		}
		if (edifAlta.getLcResumenEdificacionGaraje() != null) {
			if (!validarResumenEdificacion(edifAlta.getLcResumenEdificacionVivienda())) {
				resultado.setError(Boolean.TRUE);
				resultado.addValidacion("Los campos del Resumen Edificaci�n Alta Garage son obligatorios.");
			}
		}
		if (edifAlta.getLcResumenEdificacionTrastero() != null) {
			if (!validarResumenEdificacion(edifAlta.getLcResumenEdificacionVivienda())) {
				resultado.setError(Boolean.TRUE);
				resultado.addValidacion("Los campos del Resumen Edificaci�n Alta Trastero son obligatorios.");
			}
		}

		if (edifAlta.getLcInfoEdificiosAlta() != null && !edifAlta.getLcInfoEdificiosAlta().isEmpty()) {
			for (LcInfoEdificioAltaDto alta : edifAlta.getLcInfoEdificiosAlta()) {
				servicioLcInfoEdificioAlta.validarInfoEdificacionAlta(alta, resultado);
			}
		} else {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("Debe a�adir un edificio a la Alta de Edificaci�n.");
		}
	}

	@Override
	public void validarEdificacionBaja(LcEdificacionDto edifBaja, ResultadoLicenciasBean resultado) {
		if (StringUtils.isBlank(edifBaja.getTipoDemolicion())) {
			resultado.addValidacion("El campo Tipo de Demolici�n en Edificaci�n Baja es obligatorio.");
			resultado.setError(Boolean.TRUE);
		}
		if (StringUtils.isBlank(edifBaja.getIndustrial())) {
			resultado.addValidacion("El campo Industrial en Edificaci�n Baja es obligatorio.");
			resultado.setError(Boolean.TRUE);
		}
		if (edifBaja.getNumEdificios() == null) {
			resultado.addValidacion("El campo N�mero de Edificios en Edificaci�n Baja es obligatorio.");
			resultado.setError(Boolean.TRUE);
		}
		if (edifBaja.getLcInfoEdificiosBaja() != null && !edifBaja.getLcInfoEdificiosBaja().isEmpty()) {
			for (LcInfoEdificioBajaDto baja : edifBaja.getLcInfoEdificiosBaja()) {
				servicioLcInfoEdificioBaja.validarInfoEdificacionBaja(baja, resultado);
			}
		} else {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("Debe a�adir un edificio a la Baja de Edificaci�n.");
		}
	}

	private boolean validarResumenEdificacion(LcResumenEdificacionDto resumen) {
		if (resumen.getNumUnidadesBajoRasante() == null) {
			return false;
		} else if (resumen.getNumUnidadesSobreRasante() == null) {
			return false;
		} else if (resumen.getSupComputableBajoRasante() == null) {
			return false;
		} else if (resumen.getSupComputableSobreRasante() == null) {
			return false;
		} else if (resumen.getSupConstruidaBajoRasante() == null) {
			return false;
		} else if (resumen.getSupConstruidaSobreRasante() == null) {
			return false;
		}
		return true;
	}
}
