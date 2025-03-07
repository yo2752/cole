package org.gestoresmadrid.oegam2comun.evolucionAtex5.model.service.impl;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.evolucionAtex5.model.dao.EvolucionAtex5Dao;
import org.gestoresmadrid.core.evolucionAtex5.model.vo.EvolucionAtex5PK;
import org.gestoresmadrid.core.evolucionAtex5.model.vo.EvolucionAtex5VO;
import org.gestoresmadrid.oegam2comun.evolucionAtex5.model.service.ServicioEvolucionAtex5;
import org.gestoresmadrid.oegam2comun.evolucionAtex5.view.dto.EvolucionAtex5Dto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioEvolucionAtex5Impl implements ServicioEvolucionAtex5 {

	private static final long serialVersionUID = 3354398665024274559L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioEvolucionAtex5Impl.class);

	@Autowired
	Conversor conversor;

	@Autowired
	EvolucionAtex5Dao evolucionAtex5Dao;

	@Override
	public List<EvolucionAtex5Dto> convertirListaVoToDto(List<EvolucionAtex5VO> lista) {
		if (lista != null && !lista.isEmpty()) {
			return conversor.transform(lista, EvolucionAtex5Dto.class);
		}
		return Collections.emptyList();
	}

	@Override
	public void guardarEvolucion(BigDecimal numExpediente, Long idUsuario, Date fecha, BigDecimal estadoAnt, BigDecimal estadoNuevo, String tipoActuacion) {
		EvolucionAtex5VO evolucionAtex5VO = rellenarEvolucion(numExpediente, idUsuario, fecha, estadoAnt, estadoNuevo, tipoActuacion);
		if (evolucionAtex5VO != null) {
			evolucionAtex5Dao.guardar(evolucionAtex5VO);
		} else {
			log.error("No se puede guardar una evolucion de una consulta de atex5, si el objeto esta vacio.");
		}

	}

	private EvolucionAtex5VO rellenarEvolucion(BigDecimal numExpediente, Long idUsuario, Date fecha, BigDecimal estadoAnt, BigDecimal estadoNuevo, String tipoActuacion) {
		EvolucionAtex5VO evolucionDocumentoAtex5VO = new EvolucionAtex5VO();
		EvolucionAtex5PK id = new EvolucionAtex5PK();
		id.setNumExpediente(numExpediente);
		id.setFechaCambio(fecha);
		id.setIdUsuario(idUsuario);
		evolucionDocumentoAtex5VO.setId(id);
		evolucionDocumentoAtex5VO.setEstadoAnt(estadoAnt);
		evolucionDocumentoAtex5VO.setEstadoNuevo(estadoNuevo);
		evolucionDocumentoAtex5VO.setTipoActuacion(tipoActuacion);
		return evolucionDocumentoAtex5VO;
	}

	@Override
	public ResultBean eliminarEvolucionConsulta(BigDecimal numExpediente) {
		ResultBean resultBean = new ResultBean();
		try {
			List<EvolucionAtex5VO> listaEvoluciones = evolucionAtex5Dao.getListaEvolucionesPorNumExp(numExpediente);
			if (listaEvoluciones != null && !listaEvoluciones.isEmpty()) {
				for (EvolucionAtex5VO evolucionAtex5VO : listaEvoluciones) {
					evolucionAtex5Dao.borrar(evolucionAtex5VO);
				}
			}
		} catch (Exception e) {
			Log.error("Ha sucedido un error a la hora de eliminar las evoluciones de las consultas de atex5, error: ", e);
			resultBean.setError(Boolean.TRUE);
			resultBean.setMensaje("Ha sucedido un error a la hora de eliminar las evoluciones de las consultas.");
		}
		return resultBean;
	}
}
