package org.gestoresmadrid.oegam2comun.licenciasCam.model.service.impl;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.licencias.model.dao.LcInfoEdificioBajaDao;
import org.gestoresmadrid.core.licencias.model.vo.LcInfoEdificioBajaVO;
import org.gestoresmadrid.oegam2comun.licenciasCam.model.service.ServicioLcDatosPlantaBaja;
import org.gestoresmadrid.oegam2comun.licenciasCam.model.service.ServicioLcDireccion;
import org.gestoresmadrid.oegam2comun.licenciasCam.model.service.ServicioLcInfoEdificioBaja;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.bean.ResultadoLicenciasBean;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.dto.LcDatosPlantaBajaDto;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.dto.LcInfoEdificioBajaDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioLcInfoEdificioBajaImpl implements ServicioLcInfoEdificioBaja {

	private static final long serialVersionUID = 7305605957238024710L;
	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioLcInfoEdificioBajaImpl.class);

	@Autowired
	LcInfoEdificioBajaDao infoEdificioBajaDao;

	@Autowired
	ServicioLcDireccion servicioLcDireccion;

	@Autowired
	ServicioLcDatosPlantaBaja servicioLcDatosPlantaBaja;

	@Autowired
	Conversor conversor;

	@Transactional
	@Override
	public ResultadoLicenciasBean getDatosInfoEdificioBaja(long identificador) {
		ResultadoLicenciasBean result = new ResultadoLicenciasBean(Boolean.FALSE);

		LcInfoEdificioBajaDto infoEdificioBajaDto = conversor.transform(infoEdificioBajaDao.getInfoEdificioBaja(identificador), LcInfoEdificioBajaDto.class);

		if (infoEdificioBajaDto != null) {
			result.setObj(infoEdificioBajaDto);
		} else {
			result.setError(Boolean.TRUE);
			result.setMensaje("Error al obtener la información del Edificio Baja");
		}

		return result;
	}

	@Transactional
	@Override
	public ResultadoLicenciasBean guardarOActualizarInfoEdificioBaja(LcInfoEdificioBajaDto infoEdificioBajaDto) {
		ResultadoLicenciasBean resultado = new ResultadoLicenciasBean(Boolean.FALSE);
		try {
			resultado = validarInfoEdificioBajaGuardado(infoEdificioBajaDto);
			if (resultado.getError())
				return resultado;

			resultado = servicioLcDireccion.guardarOActualizarDireccion(infoEdificioBajaDto.getLcDirEdificacion());
			if (resultado.getError()) {
				return resultado;
			} else {
				infoEdificioBajaDto.setIdLcDirEdificacion((Long) resultado.getObj());
			}

			LcInfoEdificioBajaVO infoEdificioBajaVO = infoEdificioBajaDao.guardarOActualizar(conversor.transform(infoEdificioBajaDto, LcInfoEdificioBajaVO.class));

			if (null != infoEdificioBajaVO) {
				resultado.setMensaje("Información del Edificio Baja actualizada correctamente");
				resultado.setObj(infoEdificioBajaVO.getIdInfoEdificioBaja());
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Error al actualizar información del Edificio Baja");
			}

		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de guardar Información del Edificio Baja, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de guardar Información del Edificio Baja.");
		}
		if (resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}

		return resultado;
	}

	private ResultadoLicenciasBean validarInfoEdificioBajaGuardado(LcInfoEdificioBajaDto infoEdificioBajaDto) {
		ResultadoLicenciasBean resultado = new ResultadoLicenciasBean(Boolean.FALSE);
		if (infoEdificioBajaDto.getNumEdificio() == null) {
			resultado.setError(Boolean.TRUE);
			resultado.addMensaje(" El número de edificio de la información del Edificio Baja no puede ir vacío.<br/><br/>");
		}
		if (infoEdificioBajaDto.getIdLcDirEdificacion() != null) {
			resultado.setError(Boolean.TRUE);
			resultado.addMensaje(" El dirección de edificio de la información del Edificio Baja es obligatoria.<br/><br/>");
		}
		return resultado;
	}

	@Transactional
	@Override
	public ResultadoLicenciasBean borrarInfoEdificioBaja(long id) {
		ResultadoLicenciasBean result = new ResultadoLicenciasBean(Boolean.FALSE);

		LcInfoEdificioBajaVO infoEdificioBajaVO = infoEdificioBajaDao.getInfoEdificioBaja(id);
		if (null == infoEdificioBajaVO) {
			result.setError(Boolean.TRUE);
			result.setMensaje("Error al obtener la información del Edificio Baja");
			return result;
		}

		LcInfoEdificioBajaDto infoEdificioBajaDto = conversor.transform(infoEdificioBajaVO, LcInfoEdificioBajaDto.class);
		// Borramos las plantas (si existen)
		if (infoEdificioBajaDto.getLcDatosPlantasBaja() != null && !infoEdificioBajaDto.getLcDatosPlantasBaja().isEmpty()) {
			int numPlantas = infoEdificioBajaDto.getLcDatosPlantasBaja().size();

			for (int i = 0; i < numPlantas; i++) {
				servicioLcDatosPlantaBaja.borrarDatosPlantaBaja(infoEdificioBajaDto.getLcDatosPlantasBaja().get(i).getIdDatosPlantaBaja());
			}
		}

		// Borramos el edificio
		if (infoEdificioBajaDao.borrar(infoEdificioBajaVO)) {
			result.addMensaje("Información del Edificio Baja eliminada correctamente");
		} else {
			result.setError(Boolean.TRUE);
			result.addMensaje("Error eliminando información del Edificio Baja");
		}

		// Borramos su dirección
		servicioLcDireccion.borrarDireccion(infoEdificioBajaDto.getLcDirEdificacion().getIdDireccion());
		return result;
	}

	@Override
	public void validarInfoEdificacionBaja(LcInfoEdificioBajaDto baja, ResultadoLicenciasBean resultado) {
		if (baja.getNumEdificio() == null) {
			resultado.addValidacion("El campo Número de Edificio de unos de los edificios en Edificación Baja es obligatorio.");
			resultado.setError(Boolean.TRUE);
		}
		if (baja.getLcDirEdificacion() != null) {
			servicioLcDireccion.validarDireccion(baja.getLcDirEdificacion(), resultado, "Edificación Baha de uno de los edificios", false, false);
			if (resultado != null && !resultado.getError()) {
				if (baja.getLcDatosPlantasBaja() != null && !baja.getLcDatosPlantasBaja().isEmpty()) {
					for (LcDatosPlantaBajaDto planta : baja.getLcDatosPlantasBaja()) {
						if (planta.getSupConstruida() == null || StringUtils.isBlank(planta.getTipoPlanta())) {
							resultado.setError(Boolean.TRUE);
							resultado.addValidacion("Toda la información de una de las plantas en Edificación Baja debe ir rellena.");
						}
					}
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.addValidacion("Debe añadir alguna planta en uno de los edificios en Edificación Baja.");
				}
			}
		} else {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("La dirección de uno de los edificios en Edificación Baja debe estar rellena.");
		}
	}

}
