package org.gestoresmadrid.oegam2comun.licenciasCam.model.service.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.licencias.model.dao.LcDatosPlantaAltaDao;
import org.gestoresmadrid.core.licencias.model.vo.LcDatosPlantaAltaVO;
import org.gestoresmadrid.oegam2comun.licenciasCam.model.service.ServicioLcDatosPlantaAlta;
import org.gestoresmadrid.oegam2comun.licenciasCam.model.service.ServicioLcSupNoComputablePlanta;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.bean.ResultadoLicenciasBean;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.dto.LcDatosPlantaAltaDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioLcDatosPlantaAltaImpl implements ServicioLcDatosPlantaAlta {

	private static final long serialVersionUID = 7305605957238024710L;
	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioLcDatosPlantaAltaImpl.class);

	@Autowired
	LcDatosPlantaAltaDao datosPlantaAltaDao;

	@Autowired
	ServicioLcSupNoComputablePlanta servicioLcSupNoComputablePlanta;

	@Autowired
	Conversor conversor;

	@Transactional
	@Override
	public ResultadoLicenciasBean getDatosPlantaAlta(long identificador) {
		ResultadoLicenciasBean result = new ResultadoLicenciasBean(Boolean.FALSE);

		LcDatosPlantaAltaDto datosPlantaAltaDto = conversor.transform(datosPlantaAltaDao.getDatosPlantaAlta(identificador), LcDatosPlantaAltaDto.class);

		if (null != datosPlantaAltaDto) {
			result.setObj(datosPlantaAltaDto);
		} else {
			result.setError(Boolean.TRUE);
			result.setMensaje("Error al obtener los datos de la planta Alta");
		}

		return result;
	}

	@Override
	@Transactional
	public List<LcDatosPlantaAltaDto> getPlantasAlta(long id) {
		List<LcDatosPlantaAltaDto> resultado = conversor.transform(datosPlantaAltaDao.getPlantasAlta(id), LcDatosPlantaAltaDto.class);
		if (null != resultado && !resultado.isEmpty()) {
			Collections.sort(resultado, new ComparadorPlantasAlta());
		}
		return resultado;
	}

	@Transactional
	@Override
	public ResultadoLicenciasBean guardarOActualizarDatosPlantaAlta(LcDatosPlantaAltaDto datosPlantaAltaDto) {
		ResultadoLicenciasBean resultado = new ResultadoLicenciasBean(Boolean.FALSE);
		try {
			resultado = validarDatosPlantaAlta(datosPlantaAltaDto);
			if (resultado.getError())
				return resultado;

			LcDatosPlantaAltaVO datosPlantaAltaVO = datosPlantaAltaDao.guardarOActualizar(conversor.transform(datosPlantaAltaDto, LcDatosPlantaAltaVO.class));

			if (null != datosPlantaAltaVO) {
				resultado.setMensaje("Datos de Planta Alta actualizados correctamente");
				resultado.setObj(datosPlantaAltaVO.getIdDatosPlantaAlta());
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Error al actualizar los datos de la planta alta");
			}

		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de guardar los datos de la planta alta, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de guardar los datos de la planta alta.");
		}

		return resultado;
	}

	@Override
	public ResultadoLicenciasBean validarDatosPlantaAlta(LcDatosPlantaAltaDto datosPlantaAltaDto) {
		ResultadoLicenciasBean resultado = new ResultadoLicenciasBean(Boolean.FALSE);

		if (StringUtils.isBlank(datosPlantaAltaDto.getTipoPlanta())) {
			resultado.setError(Boolean.TRUE);
			resultado.addMensaje(" El tipo de planta de la Planta Alta no puede ir vacío.<br/><br/>");
		}

		if (null == datosPlantaAltaDto.getNumPlanta()) {
			resultado.setError(Boolean.TRUE);
			resultado.addMensaje(" El número de planta de la Planta Alta no puede ir vacío.<br/><br/>");
		}

		if (null == datosPlantaAltaDto.getAlturaLibre()) {
			resultado.setError(Boolean.TRUE);
			resultado.addMensaje(" La altura libre de la Planta Alta no puede ir vacía.<br/><br/>");
		}

		if (null == datosPlantaAltaDto.getAlturaPiso()) {
			resultado.setError(Boolean.TRUE);
			resultado.addMensaje(" La altura piso de la Planta Alta no puede ir vacía.<br/><br/>");
		}

		if (StringUtils.isBlank(datosPlantaAltaDto.getUsoPlanta())) {
			resultado.setError(Boolean.TRUE);
			resultado.addMensaje(" Uso planta de la Planta Alta no puede ir vacío.<br/><br/>");
		}

		if (null == datosPlantaAltaDto.getNumUnidades()) {
			resultado.setError(Boolean.TRUE);
			resultado.addMensaje(" Número de unidades de la Planta Alta no puede ir vacío.<br/><br/>");
		}

		if (null == datosPlantaAltaDto.getSupConstruida()) {
			resultado.setError(Boolean.TRUE);
			resultado.addMensaje(" Superficie construída de la Planta Alta no puede ir vacío.<br/><br/>");
		}

		if (null == datosPlantaAltaDto.getSupComputable()) {
			resultado.setError(Boolean.TRUE);
			resultado.addMensaje(" Superficie computable de la Planta Alta no puede ir vacío.<br/><br/>");
		}

		return resultado;
	}

	@Transactional
	@Override
	public ResultadoLicenciasBean borrarDatosPlantaAlta(long id) {
		ResultadoLicenciasBean result = new ResultadoLicenciasBean(Boolean.FALSE);

		LcDatosPlantaAltaVO datosPlantaAltaVO = datosPlantaAltaDao.getDatosPlantaAlta(id);
		if (null == datosPlantaAltaVO) {
			result.setError(Boolean.TRUE);
			result.setMensaje("Error al obtener los datos de planta alta");
			return result;
		}

		LcDatosPlantaAltaDto datosPlantaAltaDto = conversor.transform(datosPlantaAltaVO, LcDatosPlantaAltaDto.class);

		// Borramos las superficies no computables planta (si existen)
		if (null != datosPlantaAltaDto.getLcSupNoComputablesPlanta() && !datosPlantaAltaDto.getLcSupNoComputablesPlanta().isEmpty()) {
			int numSupNoComputables = datosPlantaAltaDto.getLcSupNoComputablesPlanta().size();

			for (int i = 0; i < numSupNoComputables; i++) {
				servicioLcSupNoComputablePlanta.borrarSupNoComputablePlanta(datosPlantaAltaDto.getLcSupNoComputablesPlanta().get(i).getIdSupNoComputablePlanta());
			}
		}

		// Borramos la planta alta
		if (datosPlantaAltaDao.borrar(datosPlantaAltaVO)) {
			result.addMensaje("Datos de la planta alta eliminados correctamente");
		} else {
			result.setError(Boolean.TRUE);
			result.addMensaje("Error eliminando los datos de la planta alta");
		}
		return result;
	}

	private class ComparadorPlantasAlta implements Comparator<LcDatosPlantaAltaDto> {
		@Override
		public int compare(LcDatosPlantaAltaDto o1, LcDatosPlantaAltaDto o2) {
			return ((Long) o1.getIdDatosPlantaAlta()).compareTo(o2.getIdDatosPlantaAlta());
		}
	}
}
