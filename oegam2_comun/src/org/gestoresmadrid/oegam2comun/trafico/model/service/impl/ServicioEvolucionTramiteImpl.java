package org.gestoresmadrid.oegam2comun.trafico.model.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.trafico.model.dao.EvolucionTramiteTraficoDao;
import org.gestoresmadrid.core.trafico.model.vo.EvolucionTramiteTraficoVO;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioEvolucionTramite;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.EvolucionTramiteTraficoDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioEvolucionTramiteImpl implements ServicioEvolucionTramite {

	private static final long serialVersionUID = 3324734789784008459L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioEvolucionTramiteImpl.class);

	@Autowired
	private EvolucionTramiteTraficoDao evolucionTramiteDao;

	@Autowired
	private Conversor conversor;

	@Autowired
	UtilesFecha utilesFecha;

	@Override
	@Transactional
	public void guardar(EvolucionTramiteTraficoDto evolucion) {
		try {
			EvolucionTramiteTraficoVO evolucionVO = conversor.transform(evolucion, EvolucionTramiteTraficoVO.class);
			evolucionVO.getId().setFechaCambio(utilesFecha.getFechaActualDesfaseBBDD());
			evolucionTramiteDao.guardarOActualizar(evolucionVO);
		} catch (Exception e) {
			log.error("Error al guardar la evolución del trámite: " + evolucion.getNumExpediente(), e, evolucion.getNumExpediente().toString());
		}
	}

	@Override
	@Transactional
	public void guardar(EvolucionTramiteTraficoVO evolucion) {
		try {
			evolucion.getId().setFechaCambio(utilesFecha.getFechaActualDesfaseBBDD());
			evolucionTramiteDao.guardarOActualizar(evolucion);
		} catch (Exception e) {
			log.error("Error al guardar la evolución del trámite: " + evolucion.getId().getNumExpediente(), e, evolucion.getId().getNumExpediente().toString());
		}
	}

	@Override
	@Transactional(readOnly = true)
	public int getNumeroFinalizacionesConError(BigDecimal numExpediente) {
		return numExpediente != null ? evolucionTramiteDao.getNumeroFinalizacionesConError(numExpediente) : -1;
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<EvolucionTramiteTraficoVO> getTramiteFinalizadoErrorAutorizado(BigDecimal numExpediente) {
		
		return evolucionTramiteDao.getTramiteFinalizadoErrorAutorizado(numExpediente);
	}

}
