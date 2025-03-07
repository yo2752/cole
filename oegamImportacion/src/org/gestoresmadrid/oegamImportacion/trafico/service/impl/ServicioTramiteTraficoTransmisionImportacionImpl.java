package org.gestoresmadrid.oegamImportacion.trafico.service.impl;

import java.math.BigDecimal;

import org.gestoresmadrid.core.trafico.model.dao.TramiteTraficoTransDao;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafTranVO;
import org.gestoresmadrid.oegamImportacion.bean.ResultadoBean;
import org.gestoresmadrid.oegamImportacion.cet.bean.AutoliquidacionBean;
import org.gestoresmadrid.oegamImportacion.trafico.service.ServicioTramiteTraficoTransmisionImportacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioTramiteTraficoTransmisionImportacionImpl implements ServicioTramiteTraficoTransmisionImportacion {

	private static final long serialVersionUID = 1746929335259030496L;

	private ILoggerOegam log = LoggerOegam.getLogger(ServicioTramiteTraficoTransmisionImportacionImpl.class);

	@Autowired
	private TramiteTraficoTransDao tramiteTraficoTransDao;

	@Override
	@Transactional
	public TramiteTrafTranVO getTramiteTransmisionVO(BigDecimal numExpediente, boolean tramiteCompleto) {
		try {
			return tramiteTraficoTransDao.getTramiteTransmision(numExpediente, tramiteCompleto);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener el trámite de Transmisión ,error: ", e, numExpediente.toString());
		}
		return null;
	}

	@Override
	@Transactional
	public ResultadoBean autoliquidacion(AutoliquidacionBean autoliquidacion, Long idContrato) {
		ResultadoBean resultado = new ResultadoBean(Boolean.FALSE);
		try {
			TramiteTrafTranVO tramiteVO = tramiteTraficoTransDao.getTramiteAutoliquidarCet(autoliquidacion.getMatricula(), idContrato);
			if (tramiteVO != null) {
				tramiteVO.setCetItp(autoliquidacion.getCet());
				tramiteTraficoTransDao.actualizar(tramiteVO);
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No hay ningun tramite susceptible de ser cargado el CET para la matricula aportada");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de actualizar el CET ,error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de actualizar el CET");
		}
		return resultado;
	}

}