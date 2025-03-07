package org.gestoresmadrid.oegam2comun.evolucionCayc.model.service.impl;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.evolucionCayc.model.dao.EvolucionCaycDao;
import org.gestoresmadrid.core.evolucionCayc.model.vo.EvolucionCaycVO;
import org.gestoresmadrid.oegam2comun.evolucionCayc.model.service.ServicioEvolucionCayc;
import org.gestoresmadrid.oegam2comun.evolucionCayc.view.dto.EvolucionCaycDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioEvolucionCaycImpl implements ServicioEvolucionCayc {

	private static final long serialVersionUID = -7295182370055333949L;
	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioEvolucionCaycImpl.class);
	
	@Autowired
	Conversor conversor;
	
	@Autowired
	EvolucionCaycDao evolucionCaycDao;
	
	@Override
	public List<EvolucionCaycDto> convertirListaVoToDto(List<EvolucionCaycVO> lista) {
		if (lista != null && !lista.isEmpty()) {
			return conversor.transform(lista, EvolucionCaycDto.class);
		}
		return Collections.emptyList();
	}

	@Override
	public void guardarEvolucion(BigDecimal numExpediente, Long idUsuario, Date fecha, BigDecimal estadoAnt,
			BigDecimal estadoNuevo, String tipoActuacion) {
		EvolucionCaycVO evolucionCaycVO = rellenarEvolucion(numExpediente, idUsuario, fecha, estadoAnt, estadoNuevo, tipoActuacion);
		if (evolucionCaycVO != null) {
			evolucionCaycDao.guardar(evolucionCaycVO);
		} else {
			log.error("No se puede guardar una evolucion de una consulta de arrendatarios, si el objeto esta vacio.");
		}
		
	}

	private EvolucionCaycVO rellenarEvolucion(BigDecimal numExpediente, Long idUsuario, Date fecha,
			BigDecimal estadoAnt, BigDecimal estadoNuevo, String tipoActuacion) {
		
		EvolucionCaycVO evolucionCaycVO = new EvolucionCaycVO();
		evolucionCaycVO.setNumExpediente(numExpediente);
		evolucionCaycVO.setFechaCambio(fecha);
		evolucionCaycVO.setIdUsuario(idUsuario);
		evolucionCaycVO.setEstadoAnt(estadoAnt);
		evolucionCaycVO.setEstadoNuevo(estadoNuevo);
		evolucionCaycVO.setTipoActuacion(tipoActuacion);
		return evolucionCaycVO;
	}
	
	

}
