package org.gestoresmadrid.oegam2comun.licenciasCam.model.service.impl;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.licencias.model.dao.LcInfoLocalDao;
import org.gestoresmadrid.core.licencias.model.vo.LcInfoLocalVO;
import org.gestoresmadrid.oegam2comun.licenciasCam.model.service.ServicioLcDireccion;
import org.gestoresmadrid.oegam2comun.licenciasCam.model.service.ServicioLcInfoLocal;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.bean.ResultadoLicenciasBean;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.dto.LcInfoLocalDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioLcInfoLocalImpl implements ServicioLcInfoLocal {

	private static final long serialVersionUID = 7305605957238024710L;
	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioLcInfoLocalImpl.class);

	@Autowired
	LcInfoLocalDao infoLocalDao;

	@Autowired
	ServicioLcDireccion servicioLcDireccion;

	@Autowired
	Conversor conversor;

	@Transactional
	@Override
	public ResultadoLicenciasBean guardarOActualizarInfoLocal(LcInfoLocalDto infoLocalDto) {
		ResultadoLicenciasBean resultado = new ResultadoLicenciasBean(Boolean.FALSE);
		try {
			resultado = validarInfoLocalGuardado(infoLocalDto);
			if (resultado.getError()) {
				return resultado;
			}

			if (infoLocalDto.getLcDirInfoLocal() != null) {
				resultado = servicioLcDireccion.guardarOActualizarDireccion(infoLocalDto.getLcDirInfoLocal());
				if (resultado.getError()) {
					return resultado;
				} else {
					infoLocalDto.setIdLcDirInfoLocal((Long) resultado.getObj());
				}
			} else {
				infoLocalDto.setLcDirInfoLocal(null);
			}

			LcInfoLocalVO infoLocalVO = infoLocalDao.guardarOActualizar(conversor.transform(infoLocalDto, LcInfoLocalVO.class));
			if (infoLocalVO != null) {
				resultado.setMensaje("Datos de informaci�n local actualizados correctamente");
				resultado.setObj(infoLocalVO.getIdInfoLocal());
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.addMensaje("Error al actualizar los datos de informaci�n local");
			}

		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de guardar la informaci�n local, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.addMensaje("Ha sucedido un error a la hora de guardar la informaci�n local.");
		}
		if (resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}

		return resultado;
	}

	private ResultadoLicenciasBean validarInfoLocalGuardado(LcInfoLocalDto infoLocalDto) {
		ResultadoLicenciasBean resultado = new ResultadoLicenciasBean(Boolean.FALSE);
		if (StringUtils.isBlank(infoLocalDto.getDescripcionActividad())) {
			resultado.setError(Boolean.TRUE);
			resultado.addMensaje(" La descripci�n de actividad del bloque Informaci�n Local no puede ir vac�o.<br/><br/>");
		}
		if (infoLocalDto.getSuperficieUtilLocal() == null) {
			resultado.setError(Boolean.TRUE);
			resultado.addMensaje(" La superficie util del bloque Informaci�n Local no puede ir vac�o.<br/><br/>");
		}
		if (infoLocalDto.getSuperficieUtilUsoPublico() == null) {
			resultado.setError(Boolean.TRUE);
			resultado.addMensaje(" La superficie uso p�blico del bloque Informaci�n Local no puede ir vac�o.<br/><br/>");
		}
		if (infoLocalDto.getLocalizacion() == null) {
			resultado.setError(Boolean.TRUE);
			resultado.addMensaje(" La localizaci�n del bloque Informaci�n Local no puede ir vac�o.<br/><br/>");
		}
		return resultado;
	}

	@Override
	public void validarInfoLocal(LcInfoLocalDto infoLocal, ResultadoLicenciasBean resultado) {
		if (StringUtils.isBlank(infoLocal.getLocalizacion())) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El campo Localizaci�n de la informaci�n del local es obligatorio.");
		}
		if (infoLocal.getSuperficieUtilLocal() == null) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El campo �til Local de la informaci�n del local es obligatorio.");
		}
		if (infoLocal.getSuperficieUtilUsoPublico() == null) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El campo �til Uso P�blico de la informaci�n del local es obligatorio.");
		}
		if (StringUtils.isBlank(infoLocal.getDescripcionActividad())) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El campo Localizaci�n de la informaci�n del local es obligatorio.");
		}
		if ("N".equals(infoLocal.getAccesoPrincipalIgual())) {
			if (infoLocal.getLcDirInfoLocal() != null && infoLocal.getLcDirInfoLocal().getIdDireccion() != null) {
				servicioLcDireccion.validarDireccion(infoLocal.getLcDirInfoLocal(), resultado, "Local", false, false);
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.addValidacion("Si el acceso principal no va marcado, debe rellenar la direcci�n del local.");
			}
		}
	}
}
