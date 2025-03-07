package org.gestoresmadrid.oegam2comun.licenciasCam.model.service.impl;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.licencias.model.dao.LcParteAutonomaDao;
import org.gestoresmadrid.core.licencias.model.vo.LcParteAutonomaVO;
import org.gestoresmadrid.oegam2comun.licenciasCam.model.service.ServicioLcParteAutonoma;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.bean.ResultadoLicenciasBean;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.dto.LcParteAutonomaDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioLcParteAutonomaImpl implements ServicioLcParteAutonoma {

	private static final long serialVersionUID = 7305605957238024710L;
	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioLcParteAutonomaImpl.class);

	@Autowired
	LcParteAutonomaDao parteAutonomaDao;

	@Autowired
	Conversor conversor;

	@Transactional
	@Override
	public ResultadoLicenciasBean getParteAutonoma(long identificador) {
		ResultadoLicenciasBean result = new ResultadoLicenciasBean(Boolean.FALSE);

		LcParteAutonomaDto parteAutonomaDto = conversor.transform(parteAutonomaDao.getParteAutonoma(identificador), LcParteAutonomaDto.class);

		if (null != parteAutonomaDto) {
			result.setObj(parteAutonomaDto);
		} else {
			result.setError(Boolean.TRUE);
			result.setMensaje("Error al obtener la Parte Autónoma");
		}

		return result;
	}

	@Transactional
	@Override
	public ResultadoLicenciasBean guardarOActualizarParteAutonoma(LcParteAutonomaDto parteAutonomaDto) {
		ResultadoLicenciasBean resultado = new ResultadoLicenciasBean(Boolean.FALSE);
		try {
			resultado = validarParteAutonoma(parteAutonomaDto);
			if (resultado.getError()) {
				return resultado;
			}

			LcParteAutonomaVO parteAutonomaVO = parteAutonomaDao.guardarOActualizar(conversor.transform(parteAutonomaDto, LcParteAutonomaVO.class));

			if (null != parteAutonomaVO) {
				resultado.setMensaje("Datos de Parte Autónoma actualizados correctamente");
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Error al actualizar la Parte Autónoma");
			}

		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de guardar los datos de Parte Autónoma, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de guardar los datos de Parte Autónoma.");
		}

		return resultado;
	}

	@Override
	@Transactional
	public ResultadoLicenciasBean borrarParteAutonoma(long id) {
		ResultadoLicenciasBean result = new ResultadoLicenciasBean(Boolean.FALSE);

		if (parteAutonomaDao.borrar(parteAutonomaDao.getParteAutonoma(id)))
			result.setMensaje("Parte Autónoma borrado correctamente");
		else {
			result.setError(Boolean.TRUE);
			result.setMensaje("Error borrando Parte Autónoma");
		}
		return result;
	}

	private ResultadoLicenciasBean validarParteAutonoma(LcParteAutonomaDto parteAutonomaDto) {
		ResultadoLicenciasBean resultado = new ResultadoLicenciasBean(Boolean.FALSE);
		if (parteAutonomaDto.getNumero() == null) {
			resultado.setError(Boolean.TRUE);
			resultado.addMensaje(" El número de la parte autónoma no puede ir vacío.<br/><br/>");
		}
		if (StringUtils.isBlank(parteAutonomaDto.getDescripcion())) {
			resultado.setError(Boolean.TRUE);
			resultado.addMensaje(" La descripción de la parte autónoma no puede ir vacía.<br/><br/>");
		}
		return resultado;
	}
}
