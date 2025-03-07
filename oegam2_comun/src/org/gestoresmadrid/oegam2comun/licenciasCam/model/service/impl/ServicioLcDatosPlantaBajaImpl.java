package org.gestoresmadrid.oegam2comun.licenciasCam.model.service.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.licencias.model.dao.LcDatosPlantaBajaDao;
import org.gestoresmadrid.core.licencias.model.vo.LcDatosPlantaBajaVO;
import org.gestoresmadrid.oegam2comun.licenciasCam.model.service.ServicioLcDatosPlantaBaja;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.bean.ResultadoLicenciasBean;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.dto.LcDatosPlantaBajaDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioLcDatosPlantaBajaImpl implements ServicioLcDatosPlantaBaja {

	private static final long serialVersionUID = 7305605957238024710L;
	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioLcDatosPlantaBajaImpl.class);

	@Autowired
	LcDatosPlantaBajaDao datosPlantaBajaDao;

	@Autowired
	Conversor conversor;

	@Transactional
	@Override
	public ResultadoLicenciasBean getDatosPlantaBaja(long identificador) {
		ResultadoLicenciasBean result = new ResultadoLicenciasBean(Boolean.FALSE);

		LcDatosPlantaBajaDto datosPlantaBajaDto = conversor.transform(datosPlantaBajaDao.getDatosPlantaBaja(identificador), LcDatosPlantaBajaDto.class);

		if (null != datosPlantaBajaDto) {
			result.setObj(datosPlantaBajaDto);
		} else {
			result.setError(Boolean.TRUE);
			result.setMensaje("Error al obtener los datos de la planta baja");
		}

		return result;
	}

	@Override
	@Transactional
	public List<LcDatosPlantaBajaDto> getPlantasBaja(long id) {
		List<LcDatosPlantaBajaDto> resultado = conversor.transform(datosPlantaBajaDao.getPlantasBaja(id), LcDatosPlantaBajaDto.class);
		if (null != resultado && !resultado.isEmpty()) {
			Collections.sort(resultado, new ComparadorPlantasBaja());
		}
		return resultado;
	}

	@Transactional
	@Override
	public ResultadoLicenciasBean guardarOActualizarDatosPlantaBaja(LcDatosPlantaBajaDto datosPlantaBajaDto) {
		ResultadoLicenciasBean resultado = new ResultadoLicenciasBean(Boolean.FALSE);
		try {
			resultado = validarDatosPlantaBaja(datosPlantaBajaDto);
			if (resultado.getError())
				return resultado;

			LcDatosPlantaBajaVO datosPlantaBajaVO = datosPlantaBajaDao.guardarOActualizar(conversor.transform(datosPlantaBajaDto, LcDatosPlantaBajaVO.class));

			if (null != datosPlantaBajaVO) {
				resultado.setMensaje("Datos de Planta Baja actualizados correctamente");
				resultado.setObj(datosPlantaBajaVO.getIdDatosPlantaBaja());
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Error al actualizar los datos de la planta baja");
			}

		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de guardar los datos de la planta baja, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de guardar los datos de la planta baja.");
		}

		return resultado;
	}

	@Override
	public ResultadoLicenciasBean validarDatosPlantaBaja(LcDatosPlantaBajaDto datosPlantaBajaDto) {
		ResultadoLicenciasBean resultado = new ResultadoLicenciasBean(Boolean.FALSE);

		if (StringUtils.isBlank(datosPlantaBajaDto.getTipoPlanta())) {
			resultado.setError(Boolean.TRUE);
			resultado.addMensaje(" El tipo de planta de la Planta Baja no puede ir vacío.<br/><br/>");
		}

		if (null == datosPlantaBajaDto.getSupConstruida()) {
			resultado.setError(Boolean.TRUE);
			resultado.addMensaje(" Superficie construida de la Planta Baja no puede ir vacío.<br/><br/>");
		}

		return resultado;
	}

	@Transactional
	@Override
	public ResultadoLicenciasBean borrarDatosPlantaBaja(long id) {
		ResultadoLicenciasBean result = new ResultadoLicenciasBean(Boolean.FALSE);
		if (datosPlantaBajaDao.borrar(datosPlantaBajaDao.getDatosPlantaBaja(id))) {
			result.addMensaje("Datos de la planta baja eliminados correctamente");
		} else {
			result.setError(Boolean.TRUE);
			result.addMensaje("Error eliminando los datos de la planta baja");
		}
		return result;
	}

	private class ComparadorPlantasBaja implements Comparator<LcDatosPlantaBajaDto> {
		@Override
		public int compare(LcDatosPlantaBajaDto o1, LcDatosPlantaBajaDto o2) {
			return ((Long) o1.getIdDatosPlantaBaja()).compareTo(o2.getIdDatosPlantaBaja());
		}
	}
}
