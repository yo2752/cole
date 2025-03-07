package org.gestoresmadrid.oegam2comun.licenciasCam.model.service.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.gestoresmadrid.core.licencias.model.dao.LcDatosPortalAltaDao;
import org.gestoresmadrid.core.licencias.model.vo.LcDatosPortalAltaVO;
import org.gestoresmadrid.oegam2comun.licenciasCam.model.service.ServicioLcDatosPlantaAlta;
import org.gestoresmadrid.oegam2comun.licenciasCam.model.service.ServicioLcDatosPortalAlta;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.bean.ResultadoLicenciasBean;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.dto.LcDatosPortalAltaDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioLcDatosPortalAltaImpl implements ServicioLcDatosPortalAlta {

	private static final long serialVersionUID = 7305605957238024710L;
	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioLcDatosPortalAltaImpl.class);

	@Autowired
	LcDatosPortalAltaDao datosPortalAltaDao;

	@Autowired
	ServicioLcDatosPlantaAlta servicioLcDatosPlantaAlta;

	@Autowired
	Conversor conversor;

	@Transactional
	@Override
	public ResultadoLicenciasBean getDatosPortalAlta(long identificador) {
		ResultadoLicenciasBean result = new ResultadoLicenciasBean(Boolean.FALSE);

		LcDatosPortalAltaDto datosPortalAltaDto = conversor.transform(datosPortalAltaDao.getDatosPortalAlta(identificador), LcDatosPortalAltaDto.class);

		if (null != datosPortalAltaDto) {
			result.setObj(datosPortalAltaDto);
		} else {
			result.setError(Boolean.TRUE);
			result.setMensaje("Error al obtener los datos de portal alta");
		}

		return result;
	}

	@Override
	@Transactional
	public List<LcDatosPortalAltaDto> getPortalesAlta(long id) {
		List<LcDatosPortalAltaDto> resultado = conversor.transform(datosPortalAltaDao.getPortalesAlta(id), LcDatosPortalAltaDto.class);
		if (null != resultado && !resultado.isEmpty()) {
			Collections.sort(resultado, new ComparadorDatosPortalAlta());
		}
		return resultado;
	}

	@Transactional
	@Override
	public ResultadoLicenciasBean guardarOActualizarDatosPortalAlta(LcDatosPortalAltaDto datosPortalAltaDto) {
		ResultadoLicenciasBean resultado = new ResultadoLicenciasBean(Boolean.FALSE);
		try {
			resultado = validarDatosPortalAlta(datosPortalAltaDto);
			if (resultado.getError())
				return resultado;

			LcDatosPortalAltaVO datosPortalAltaVO = datosPortalAltaDao.guardarOActualizar(conversor.transform(datosPortalAltaDto, LcDatosPortalAltaVO.class));

			if (null != datosPortalAltaVO) {
				resultado.setMensaje("Datos de Portal Alta actualizados correctamente");
				resultado.setObj(datosPortalAltaVO.getIdDatosPortalAlta());
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Error al actualizar los datos de portal alta");
			}

		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de guardar los datos de portal alta, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de guardar los datos de portal alta.");
		}
		if (resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}

		return resultado;
	}

	@Override
	public ResultadoLicenciasBean validarDatosPortalAlta(LcDatosPortalAltaDto datosPortalAltaDto) {
		ResultadoLicenciasBean resultado = new ResultadoLicenciasBean(Boolean.FALSE);

		if (null == datosPortalAltaDto.getUnidadesSbRasante()) {
			resultado.setError(Boolean.TRUE);
			resultado.addMensaje(" Sobre Rasante Unidades de Datos Portal Alta no puede ir vacío.<br/><br/>");
		}

		if (null == datosPortalAltaDto.getSuperficieConstruidaSbRasante()) {
			resultado.setError(Boolean.TRUE);
			resultado.addMensaje(" Sobre Rasante Superficie Construida de Datos Portal Alta no puede ir vacío.<br/><br/>");
		}

		if (null == datosPortalAltaDto.getSuperficieComputableSbRasante()) {
			resultado.setError(Boolean.TRUE);
			resultado.addMensaje(" Sobre Rasante Superficie Computable de Datos Portal Alta no puede ir vacío.<br/><br/>");
		}

		return resultado;
	}

	@Transactional
	@Override
	public ResultadoLicenciasBean borrarDatosPortalAlta(long id) {
		ResultadoLicenciasBean result = new ResultadoLicenciasBean(Boolean.FALSE);

		LcDatosPortalAltaVO datosPortalAltaVO = datosPortalAltaDao.getDatosPortalAlta(id);
		if (null == datosPortalAltaVO) {
			result.setError(Boolean.TRUE);
			result.setMensaje("Error al obtener los datos de portal alta");
			return result;
		}

		LcDatosPortalAltaDto datosPortalAltaDto = conversor.transform(datosPortalAltaVO, LcDatosPortalAltaDto.class);

		// Borramos las plantas altas (si existen)
		if (null != datosPortalAltaDto.getLcDatosPlantasAlta() && !datosPortalAltaDto.getLcDatosPlantasAlta().isEmpty()) {
			int numPlantasAlta = datosPortalAltaDto.getLcDatosPlantasAlta().size();

			for (int i = 0; i < numPlantasAlta; i++) {
				servicioLcDatosPlantaAlta.borrarDatosPlantaAlta(datosPortalAltaDto.getLcDatosPlantasAlta().get(i).getIdDatosPlantaAlta());
			}
		}

		// Borramos el portal alta
		if (datosPortalAltaDao.borrar(datosPortalAltaVO)) {
			result.addMensaje("Datos de portal alta eliminados correctamente");
		} else {
			result.setError(Boolean.TRUE);
			result.addMensaje("Error eliminando los datos de portal alta");
		}

		return result;
	}

	private class ComparadorDatosPortalAlta implements Comparator<LcDatosPortalAltaDto> {
		@Override
		public int compare(LcDatosPortalAltaDto o1, LcDatosPortalAltaDto o2) {
			return ((Long) o1.getIdDatosPortalAlta()).compareTo(o2.getIdDatosPortalAlta());
		}
	}
}
