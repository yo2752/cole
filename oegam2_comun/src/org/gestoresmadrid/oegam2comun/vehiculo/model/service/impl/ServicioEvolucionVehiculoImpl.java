package org.gestoresmadrid.oegam2comun.vehiculo.model.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.core.vehiculo.model.dao.EvolucionVehiculoDao;
import org.gestoresmadrid.core.vehiculo.model.vo.EvolucionVehiculoPK;
import org.gestoresmadrid.core.vehiculo.model.vo.EvolucionVehiculoVO;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioEvolucionVehiculo;
import org.gestoresmadrid.oegam2comun.vehiculo.view.dto.EvolucionVehiculoDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioEvolucionVehiculoImpl implements ServicioEvolucionVehiculo {

	private static final long serialVersionUID = -6861820253256409535L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioEvolucionVehiculoImpl.class);

	@Autowired
	private EvolucionVehiculoDao evolucionDao;

	@Autowired
	private Conversor conversor;

	@Autowired
	UtilesFecha utilesFecha;

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	public List<EvolucionVehiculoVO> getEvolucionVehiculo(Long idVehiculo, String numColegiado, ArrayList<String> tipoActualizacion) {
		try {
			List<EvolucionVehiculoVO> lista = evolucionDao.getEvolucionVehiculo(idVehiculo, numColegiado, tipoActualizacion);
			if (lista != null && !lista.isEmpty()) {
				return lista;
			}
		} catch (Exception e) {
			log.error("Error el obtener el vehiculo");
		}
		return null;
	}

	@Override
	@Transactional
	public ResultBean guardarEvolucion(EvolucionVehiculoDto evolucion) {
		ResultBean resultado = new ResultBean();
		try {
			EvolucionVehiculoVO evolucionVO = conversor.transform(evolucion, EvolucionVehiculoVO.class);
			evolucionVO.getId().setFechaHora(utilesFecha.getFechaActualDesfaseBBDD());
			EvolucionVehiculoPK evolucionPK = (EvolucionVehiculoPK) evolucionDao.guardar(evolucionVO);
			resultado.setError(false);
			if (evolucionPK == null) {
				resultado.setError(true);
				resultado.addMensajeALista("Error al guardar la evolución");
				log.error("Error al guardar la evolución");
			}
		} catch (Exception e) {
			resultado.setError(true);
			resultado.addMensajeALista("Error al guardar la evolución");
			log.error("Error al guardar la evolución");
		}
		return resultado;
	}

	@Override
	@Transactional
	public void actualizarEvolucionVehiculoCreacionOCopia(Long idVehiculo, BigDecimal numExpediente) {
		try {
			ArrayList<String> tipoActualizacion = new ArrayList<>();
			tipoActualizacion.add(TIPO_ACTUALIZACION_CRE);
			tipoActualizacion.add(TIPO_ACTUALIZACION_COP);
			List<EvolucionVehiculoVO> lista = getEvolucionVehiculo(idVehiculo, null, tipoActualizacion);
			if (lista != null && !lista.isEmpty()) {
				lista.get(0).setNumExpediente(numExpediente);
				evolucionDao.actualizar(lista.get(0));
			}
		} catch (Exception e) {
			log.error("Error al guardar la evolución");
		}
	}

	@Override
	@Transactional
	public void eliminar(EvolucionVehiculoVO evolucion) {
		try {
			evolucionDao.borrar(evolucion);
		} catch (Exception e) {
			log.error("Error al eliminar la evolución");
		}
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	public String obtenerSinCodig(Long idVehiculo, BigDecimal numExpediente) {
		String sinCodig = "";
		try {
			EvolucionVehiculoVO evolucion = evolucionDao.getEvolucionVehiculoSinCodig(idVehiculo, numExpediente);
			if (evolucion != null) {
				if (evolucion.getOtros() != null) {
					sinCodig += evolucion.getOtros();
				}
				if (evolucion.getMatriculaNue() != null) {
					sinCodig += "MATRICULA,";
				}
				if (evolucion.getBastidorNue() != null) {
					sinCodig += "BASTIDOR,";
				}
				if (evolucion.getTipoVehiculoNue() != null) {
					sinCodig += "TIPO VEHICULO,";
				}
			}
		} catch (Exception e) {
			log.error("Error el obtener el vehiculo");
		}
		return sinCodig;
	}
}
