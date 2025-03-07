package org.gestoresmadrid.oegam2comun.circular.model.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.gestoresmadrid.core.circular.model.dao.EvolucionCircularDao;
import org.gestoresmadrid.core.circular.model.enumerados.OperacionCirculares;
import org.gestoresmadrid.core.circular.model.vo.EvolucionCircularVO;
import org.gestoresmadrid.oegam2comun.circular.model.service.ServicioEvolucionCircular;
import org.gestoresmadrid.oegam2comun.circular.view.bean.ResultadoCircularBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioEvolucionCircularImpl implements ServicioEvolucionCircular {

	private static final long serialVersionUID = -8082907272018830267L;

	@Autowired
	EvolucionCircularDao evolucionCircularDao;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioEvolucionCircularImpl.class);

	@Override
	@Transactional
	public ResultadoCircularBean guardarEvolucion(Long idCircular, BigDecimal idUsuario, Date fecha, String estadoAnt,
			String estadoNuevo,OperacionCirculares operacion) {
		ResultadoCircularBean resultado = new ResultadoCircularBean(Boolean.FALSE);
		try {
			EvolucionCircularVO evolucionCircularVO = new EvolucionCircularVO();
			evolucionCircularVO.setIdCircular(idCircular);
			evolucionCircularVO.setIdUsuario(idUsuario.longValue());
			evolucionCircularVO.setFechaCambio(fecha);
			evolucionCircularVO.setEstadoAnterior(estadoAnt);
			evolucionCircularVO.setEstadoNuevo(estadoNuevo);
			evolucionCircularVO.setOperacion(operacion.getValorEnum());
			evolucionCircularDao.guardar(evolucionCircularVO);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de guardar la evolucion de la circular, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de guardar la evolución de la circular.");
		}
		return resultado;
	}

}
