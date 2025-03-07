package org.gestoresmadrid.oegamImportacion.trafico.service.impl;

import java.math.BigDecimal;

import org.gestoresmadrid.core.trafico.model.dao.TramiteTraficoDuplicadosDao;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafDuplicadoVO;
import org.gestoresmadrid.oegamImportacion.trafico.service.ServicioTramiteTraficoDuplicadoImportacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioTramiteTraficoDuplicadoImportacionImpl implements ServicioTramiteTraficoDuplicadoImportacion {

	private static final long serialVersionUID = 1063146071761231208L;

	private ILoggerOegam log = LoggerOegam.getLogger(ServicioTramiteTraficoDuplicadoImportacionImpl.class);

	@Autowired
	TramiteTraficoDuplicadosDao tramiteTraficoDuplicadosDao;

	@Override
	@Transactional(readOnly = true)
	public TramiteTrafDuplicadoVO getTramiteDuplicado(BigDecimal numExpediente, boolean tramiteCompleto) {
		log.debug("Recuperar el trámite duplicado: " + numExpediente);
		TramiteTrafDuplicadoVO result = null;
		try {
			TramiteTrafDuplicadoVO tramite = tramiteTraficoDuplicadosDao.getTramiteDuplicado(numExpediente, tramiteCompleto);
			if (tramite != null) {
				result = tramite;
			}
		} catch (Exception e) {
			log.error("Error al recuperar el trámite de duplicado: " + numExpediente, e, numExpediente.toString());
		}
		return result;
	}

}
