package org.gestoresmadrid.oegamImpresion.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.impresion.model.dao.EvolucionImpresionDao;
import org.gestoresmadrid.core.impresion.model.vo.EvolucionImpresionVO;
import org.gestoresmadrid.oegamImpresion.service.ServicioEvolucionImpresion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioEvolucionImpresionImpl implements ServicioEvolucionImpresion {

	private static final long serialVersionUID = -1358607348647425824L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioEvolucionImpresionImpl.class);

	@Autowired
	EvolucionImpresionDao evolucionImpresionDao;

	@Override
	@Transactional
	public void guardarEvolucion(Long idImpresion, String tipoActuacion, String nombreDocumento, List<BigDecimal> listaTramitesErroneos, Long idUsuario) {
		try {
			EvolucionImpresionVO evolucionImpresionVO = new EvolucionImpresionVO();
			evolucionImpresionVO.setIdUsuario(idUsuario);
			evolucionImpresionVO.setIdImpresion(idImpresion);
			evolucionImpresionVO.setFechaCambio(new Date());
			evolucionImpresionVO.setNombreDocumento(nombreDocumento);
			evolucionImpresionVO.setTramiterErroneos(rellenarTramiteErroneos(listaTramitesErroneos));
			evolucionImpresionVO.setTipoActuacion(tipoActuacion);
		} catch (Exception e) {
			log.error("Error a la hora de guardar la evolucion de impresion", e);
		}
	}

	private String rellenarTramiteErroneos(List<BigDecimal> listaTramitesErroneos) {
		String errores = null;
		if (listaTramitesErroneos != null && !listaTramitesErroneos.isEmpty()) {
			for (BigDecimal numExpediente : listaTramitesErroneos) {
				if (errores == null) {
					errores = numExpediente.toString();
				} else {
					errores += ", " + numExpediente.toString();
				}
			}
		}
		return errores;
	}
}
