package org.gestoresmadrid.oegam2comun.licenciasCam.model.service.impl;

import org.gestoresmadrid.core.licencias.model.dao.LcResumenEdificacionDao;
import org.gestoresmadrid.core.licencias.model.vo.LcResumenEdificacionVO;
import org.gestoresmadrid.oegam2comun.licenciasCam.model.service.ServicioLcResumenEdificacion;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.bean.ResultadoLicenciasBean;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.dto.LcResumenEdificacionDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioLcResumenEdificacionImpl implements ServicioLcResumenEdificacion {

	private static final long serialVersionUID = 5994904591390026895L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioLcResumenEdificacionImpl.class);

	@Autowired
	LcResumenEdificacionDao lcResumenEdificacionDao;

	@Autowired
	Conversor conversor;

	@Transactional
	@Override
	public ResultadoLicenciasBean guardarOActualizarResumenEdificacion(LcResumenEdificacionDto resumenEdificacionDto) {
		ResultadoLicenciasBean resultado = new ResultadoLicenciasBean(Boolean.FALSE);
		try {
			LcResumenEdificacionVO resumenEdificacionAltaVO = lcResumenEdificacionDao.guardarOActualizar(conversor.transform(resumenEdificacionDto, LcResumenEdificacionVO.class));

			if (resumenEdificacionAltaVO != null) {
				resultado.setMensaje("Datos de Resumen Edificación Alta actualizados correctamente");
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Error al actualizar los datos de Resumen Edificación Alta");
			}

		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de guardar el Resumen Edificación, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de guardar el Resumen Edificación Alta.");
		}
		if (resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}

		return resultado;
	}
}
