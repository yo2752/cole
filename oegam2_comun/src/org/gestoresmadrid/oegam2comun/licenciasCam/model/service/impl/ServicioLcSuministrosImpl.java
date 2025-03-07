package org.gestoresmadrid.oegam2comun.licenciasCam.model.service.impl;

import org.gestoresmadrid.core.licencias.model.dao.LcDatosSuministrosDao;
import org.gestoresmadrid.core.licencias.model.vo.LcDatosSuministrosVO;
import org.gestoresmadrid.oegam2comun.licenciasCam.model.service.ServicioLcSuministros;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.bean.ResultadoLicenciasBean;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.dto.LcDatosSuministrosDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioLcSuministrosImpl implements ServicioLcSuministros {

	private static final long serialVersionUID = -2397012986462541456L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioLcSuministrosImpl.class);

	@Autowired
	LcDatosSuministrosDao lcDatosSuministrosDao;

	@Autowired
	Conversor conversor;

	@Transactional
	@Override
	public ResultadoLicenciasBean guardarOActualizarSuministro(LcDatosSuministrosDto suministrosDto) {
		ResultadoLicenciasBean resultado = new ResultadoLicenciasBean(Boolean.FALSE);
		try {
			LcDatosSuministrosVO suministrosVO = lcDatosSuministrosDao.guardarOActualizar(conversor.transform(suministrosDto, LcDatosSuministrosVO.class));
			if (suministrosVO != null) {
				resultado.setMensaje("Datos de los suministros actualizados correctamente");
				resultado.setObj(suministrosVO.getIdDatosSuministros());
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.addMensaje("Error al actualizar los datos de los suministros");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de guardar los datos de los suministros, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.addMensaje("Ha sucedido un error a la hora de guardar los datos de los suministros.");
		}
		return resultado;
	}
}
