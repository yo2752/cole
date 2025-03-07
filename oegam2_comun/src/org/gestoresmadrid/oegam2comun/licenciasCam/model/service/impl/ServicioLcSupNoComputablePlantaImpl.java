package org.gestoresmadrid.oegam2comun.licenciasCam.model.service.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.gestoresmadrid.core.licencias.model.dao.LcSupNoComputablePlantaDao;
import org.gestoresmadrid.core.licencias.model.vo.LcSupNoComputablePlantaVO;
import org.gestoresmadrid.oegam2comun.licenciasCam.model.service.ServicioLcSupNoComputablePlanta;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.bean.ResultadoLicenciasBean;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.dto.LcSupNoComputablePlantaDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioLcSupNoComputablePlantaImpl implements ServicioLcSupNoComputablePlanta {

	private static final long serialVersionUID = 7305605957238024710L;
	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioLcSupNoComputablePlantaImpl.class);

	@Autowired
	LcSupNoComputablePlantaDao supNoComputablePlantaDao;

	@Autowired
	Conversor conversor;

	@Transactional
	@Override
	public ResultadoLicenciasBean getSupNoComputablePlanta(long identificador) {
		ResultadoLicenciasBean result = new ResultadoLicenciasBean(Boolean.FALSE);

		LcSupNoComputablePlantaDto supNoComputablePlantaDto = conversor.transform(supNoComputablePlantaDao.getSupNoComputablePlanta(identificador), LcSupNoComputablePlantaDto.class);

		if (null != supNoComputablePlantaDto) {
			result.setObj(supNoComputablePlantaDto);
		} else {
			result.setError(Boolean.TRUE);
			result.setMensaje("Error al obtener los datos de superficie no computable planta");
		}

		return result;
	}

	@Override
	@Transactional
	public List<LcSupNoComputablePlantaDto> getSupNoComputablesPlanta(long id) {
		List<LcSupNoComputablePlantaDto> resultado = conversor.transform(supNoComputablePlantaDao.getSupNoComputablesPlanta(id), LcSupNoComputablePlantaDto.class);
		if (null != resultado && !resultado.isEmpty()) {
			Collections.sort(resultado, new ComparadorSupNoComputablePlanta());
		}
		return resultado;
	}

	@Transactional
	@Override
	public ResultadoLicenciasBean guardarOActualizarSupNoComputablesPlanta(LcSupNoComputablePlantaDto supNoComputablePlantaDto) {
		ResultadoLicenciasBean resultado = new ResultadoLicenciasBean(Boolean.FALSE);
		try {
			resultado = validarSupNoComputablePlantaDto(supNoComputablePlantaDto);
			if (resultado.getError())
				return resultado;

			LcSupNoComputablePlantaVO supNoComputablePlantaVO = supNoComputablePlantaDao.guardarOActualizar(conversor.transform(supNoComputablePlantaDto, LcSupNoComputablePlantaVO.class));

			if (null != supNoComputablePlantaVO) {
				resultado.setMensaje("Datos de superficie no computable planta actualizados correctamente");
				resultado.setObj(supNoComputablePlantaVO.getIdSupNoComputablePlanta());
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Error al actualizar los datos de superficie no computable planta");
			}

		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de guardar los datos de superficie no computable planta, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de guardar los datos de superficie no computable planta.");
		}

		return resultado;
	}

	@Override
	public ResultadoLicenciasBean validarSupNoComputablePlantaDto(LcSupNoComputablePlantaDto supNoComputablePlantaDto) {
		ResultadoLicenciasBean resultado = new ResultadoLicenciasBean(Boolean.FALSE);

		if (null == supNoComputablePlantaDto.getTipo()) {
			resultado.setError(Boolean.TRUE);
			resultado.addMensaje(" El tipo de superficie no computable planta no puede ir vacío.<br/><br/>");
		}

		if (null == supNoComputablePlantaDto.getTamanio()) {
			resultado.setError(Boolean.TRUE);
			resultado.addMensaje(" El tamaño de superficie no computable planta no puede ir vacío.<br/><br/>");
		}

		return resultado;
	}

	@Transactional
	@Override
	public ResultadoLicenciasBean borrarSupNoComputablePlanta(long id) {
		ResultadoLicenciasBean result = new ResultadoLicenciasBean(Boolean.FALSE);
		if (supNoComputablePlantaDao.borrar(supNoComputablePlantaDao.getSupNoComputablePlanta(id))) {
			result.addMensaje("Datos de superficie no computable planta eliminados correctamente");
		} else {
			result.setError(Boolean.TRUE);
			result.addMensaje("Error eliminando los datos de superficie no computable planta");
		}
		return result;
	}

	private class ComparadorSupNoComputablePlanta implements Comparator<LcSupNoComputablePlantaDto> {
		@Override
		public int compare(LcSupNoComputablePlantaDto o1, LcSupNoComputablePlantaDto o2) {
			return ((Long) o1.getIdSupNoComputablePlanta()).compareTo(o2.getIdSupNoComputablePlanta());
		}
	}
}
