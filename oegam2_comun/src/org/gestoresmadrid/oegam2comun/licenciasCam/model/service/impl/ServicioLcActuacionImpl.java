package org.gestoresmadrid.oegam2comun.licenciasCam.model.service.impl;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.licencias.model.dao.LcActuacionDao;
import org.gestoresmadrid.core.licencias.model.vo.LcActuacionVO;
import org.gestoresmadrid.oegam2comun.licenciasCam.model.service.ServicioLcActuacion;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.bean.ResultadoLicenciasBean;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.dto.LcActuacionDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioLcActuacionImpl implements ServicioLcActuacion {

	private static final long serialVersionUID = 7305605957238024710L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioLcActuacionImpl.class);

	@Autowired
	LcActuacionDao actuacionDao;

	@Autowired
	Conversor conversor;

	@Transactional
	@Override
	public ResultadoLicenciasBean guardarOActualizarActuacion(LcActuacionDto actuacionDto) {
		ResultadoLicenciasBean resultado = new ResultadoLicenciasBean(Boolean.FALSE);
		try {
			LcActuacionVO actuacionVO = actuacionDao.guardarOActualizar(conversor.transform(actuacionDto, LcActuacionVO.class));

			if (actuacionVO != null) {
				resultado.setMensaje("Datos de actuación actualizados correctamente");
				resultado.setObj(actuacionVO.getIdActuacion());
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Error al actualizar los datos de actuación");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de guardar la actuación, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de guardar la actuación.");
		}

		return resultado;
	}

	@Override
	public void validarDatosActuacion(LcActuacionDto actuacion, ResultadoLicenciasBean resultado) {
		if (actuacion != null) {
			if (StringUtils.isBlank(actuacion.getNormaZonalFiguraOrdenacion())) {
				resultado.setError(Boolean.TRUE);
				resultado.addValidacion("El campo Norma Zonal de la Actuación es obligatorio.");
			}
			if (StringUtils.isBlank(actuacion.getNivelProteccion())) {
				resultado.setError(Boolean.TRUE);
				resultado.addValidacion("El campo Nivel Protección de la Actuación es obligatorio.");
			}
			if (StringUtils.isBlank(actuacion.getDotacionalTransporte())) {
				resultado.setError(Boolean.TRUE);
				resultado.addValidacion("El campo Dotacional Transporte de la Actuación es obligatorio.");
			}
		}
	}
}
