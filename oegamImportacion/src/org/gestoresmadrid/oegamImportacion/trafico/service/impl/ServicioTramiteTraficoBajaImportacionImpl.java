package org.gestoresmadrid.oegamImportacion.trafico.service.impl;

import java.math.BigDecimal;

import org.gestoresmadrid.core.trafico.model.dao.TramiteTraficoBajaDao;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafBajaVO;
import org.gestoresmadrid.oegamImportacion.trafico.service.ServicioTramiteTraficoBajaImportacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioTramiteTraficoBajaImportacionImpl implements ServicioTramiteTraficoBajaImportacion {

	private static final long serialVersionUID = -4761366319089212968L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioTramiteTraficoBajaImportacionImpl.class);

	@Autowired
	private TramiteTraficoBajaDao tramiteTraficoBajaDao;

	@Override
	@Transactional(readOnly = true)
	public TramiteTrafBajaVO getTramiteBajaVO(BigDecimal numExpediente, boolean tramiteCompleto) {
		TramiteTrafBajaVO tramiteBajaVO = null;
		try {
			if (numExpediente != null) {
				tramiteBajaVO = tramiteTraficoBajaDao.getTramiteBaja(numExpediente, tramiteCompleto);
			}
		} catch (Exception e) {
			log.error("Error al recuperar el trámite de baja: " + numExpediente, e, numExpediente.toString());
		}
		return tramiteBajaVO;
	}

	public TramiteTraficoBajaDao getTramiteTraficoBajaDao() {
		return tramiteTraficoBajaDao;
	}

	public void setTramiteTraficoBajaDao(TramiteTraficoBajaDao tramiteTraficoBajaDao) {
		this.tramiteTraficoBajaDao = tramiteTraficoBajaDao;
	}


}
