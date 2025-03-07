package org.gestoresmadrid.oegamImportacion.trafico.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.gestoresmadrid.core.general.model.vo.UsuarioVO;
import org.gestoresmadrid.core.trafico.model.dao.EvolucionTramiteTraficoDao;
import org.gestoresmadrid.core.trafico.model.vo.EvolucionTramiteTraficoPK;
import org.gestoresmadrid.core.trafico.model.vo.EvolucionTramiteTraficoVO;
import org.gestoresmadrid.oegamImportacion.trafico.service.ServicioEvolucionTramiteImportacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioEvolucionTramiteImportacionImpl implements ServicioEvolucionTramiteImportacion {

	private static final long serialVersionUID = 4365670917727175205L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioEvolucionTramiteImportacionImpl.class);

	@Autowired
	EvolucionTramiteTraficoDao evolucionTramiteTraficoDao;

	
	@Override
	@Transactional
	public void guardarEvolucion(BigDecimal numExpediente, BigDecimal estado, Long idUsuario, Date fecha) {
		try {
			EvolucionTramiteTraficoVO evolucion = new EvolucionTramiteTraficoVO();
			EvolucionTramiteTraficoPK id = new EvolucionTramiteTraficoPK();
			id.setEstadoAnterior(BigDecimal.ZERO);
			id.setEstadoNuevo(estado);
			id.setFechaCambio(fecha);
			id.setNumExpediente(numExpediente);
			evolucion.setId(id);
			UsuarioVO usuario = new UsuarioVO();
			usuario.setIdUsuario(idUsuario);
			evolucion.setUsuario(usuario);
			evolucionTramiteTraficoDao.guardar(evolucion);
		} catch (Exception e) {
			log.error("Has sucedido un error a la hora de guardar la evolucion del tramite: " + numExpediente + ", error: ",e);
		}
		
	}
}
