package org.gestoresmadrid.oegam2comun.licenciasCam.model.service.impl;

import org.gestoresmadrid.core.licencias.model.dao.LcEpigrafeDao;
import org.gestoresmadrid.core.licencias.model.vo.LcEpigrafeVO;
import org.gestoresmadrid.oegam2comun.licenciasCam.model.service.ServicioLcEpigrafe;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.bean.ResultadoLicenciasBean;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.dto.LcEpigrafeDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioLcEpigrafeImpl implements ServicioLcEpigrafe {

	private static final long serialVersionUID = 7305605957238024710L;
	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioLcEpigrafeImpl.class);

	@Autowired
	LcEpigrafeDao epigrafeDao;

	@Autowired
	Conversor conversor;

	@Transactional
	@Override
	public ResultadoLicenciasBean getEpigrafe(long identificador) {
		ResultadoLicenciasBean result = new ResultadoLicenciasBean(Boolean.FALSE);
		LcEpigrafeDto epigrafeDto = conversor.transform(epigrafeDao.getEpigrafe(identificador), LcEpigrafeDto.class);

		if (null != epigrafeDto) {
			result.setObj(epigrafeDto);
		} else {
			result.setError(Boolean.TRUE);
			result.setMensaje("Error al obtener el Epígrafe");
		}

		return result;
	}

	@Transactional
	@Override
	public ResultadoLicenciasBean guardarOActualizarEpigrafe(LcEpigrafeDto epigrafeDto) {
		ResultadoLicenciasBean resultado = new ResultadoLicenciasBean(Boolean.FALSE);
		try {
			LcEpigrafeVO epigrafeVO = epigrafeDao.guardarOActualizar(conversor.transform(epigrafeDto, LcEpigrafeVO.class));
			if (null != epigrafeVO) {
				resultado.setMensaje("Datos de Epígrafe actualizados correctamente");
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Error al actualizar los datos de Epígrafe");
			}

		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de guardar los datos de Epígrafe, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de guardar los datos de Epígrafe.");
		}

		return resultado;
	}

	@Override
	@Transactional
	public ResultadoLicenciasBean borrarEpigrafe(long id) {
		ResultadoLicenciasBean result = new ResultadoLicenciasBean(Boolean.FALSE);

		if (epigrafeDao.borrar(epigrafeDao.getEpigrafe(id)))
			result.setMensaje("Epígrafe borrado correctamente");
		else {
			result.setError(Boolean.TRUE);
			result.setMensaje("Error borrando Epígrafe");
		}
		return result;
	}
}
