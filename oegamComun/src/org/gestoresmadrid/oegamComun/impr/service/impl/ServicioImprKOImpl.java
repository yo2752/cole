package org.gestoresmadrid.oegamComun.impr.service.impl;

import java.math.BigDecimal;

import org.gestoresmadrid.core.impr.model.vo.ImprVO;
import org.gestoresmadrid.oegamComun.impr.service.ServicioImprKO;
import org.gestoresmadrid.oegamComun.impr.service.ServicioPersistenciaGestionImpr;
import org.gestoresmadrid.oegamComun.impr.service.ServicioPersistenciaImprKO;
import org.gestoresmadrid.oegamComun.impr.view.bean.ResultadoImprBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioImprKOImpl implements ServicioImprKO{

	private static final long serialVersionUID = -130509170165433326L;
	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioImprKOImpl.class);

	@Autowired
	ServicioPersistenciaImprKO servicioPersistenciaImprKO;
	
	@Autowired
	ServicioPersistenciaGestionImpr servicioPersistenciaGestionImpr;
	
	@Override
	public ResultadoImprBean generarKoTramite(BigDecimal id, Long idUsuario) {
		ResultadoImprBean resultado = new ResultadoImprBean(Boolean.FALSE);
		try {
			ImprVO imprVO = servicioPersistenciaGestionImpr.getImprVO(id.longValue());
			if(imprVO != null) {
				servicioPersistenciaImprKO.crearKo(imprVO,idUsuario);
			}
			
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de generar las solicitudes para obtener los IMPR, error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de generar las solicitudes para obtener los IMPR.");
		}
		return resultado;
	}

}
