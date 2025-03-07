package org.gestoresmadrid.oegamImportacion.vehiculo.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.gestoresmadrid.core.general.model.vo.UsuarioVO;
import org.gestoresmadrid.core.vehiculo.model.dao.EvolucionVehiculoDao;
import org.gestoresmadrid.core.vehiculo.model.vo.EvolucionVehiculoPK;
import org.gestoresmadrid.core.vehiculo.model.vo.EvolucionVehiculoVO;
import org.gestoresmadrid.oegamImportacion.vehiculo.service.ServicioEvolucionVehiculoImportacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioEvolucionVehiculoImportacionImpl implements ServicioEvolucionVehiculoImportacion {

	private static final long serialVersionUID = 7308533172441704984L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioEvolucionVehiculoImportacionImpl.class);

	@Autowired
	EvolucionVehiculoDao evolucionVehiculoDao;

	@Override
	@Transactional
	public void guardarEvolucion(Long idVehiculo, String numColegiado, BigDecimal numExpediente, String tipoTramite, String tipoActualizacion, Long idUsuario, Long idVehiculoOrigen) {
		try {
			EvolucionVehiculoVO evolucion = new EvolucionVehiculoVO();
			EvolucionVehiculoPK id = new EvolucionVehiculoPK();
			id.setFechaHora(new Date());
			id.setIdVehiculo(idVehiculo);
			id.setNumColegiado(numColegiado);
			evolucion.setId(id);
			evolucion.setNumExpediente(numExpediente);
			evolucion.setTipoTramite(tipoTramite);
			evolucion.setTipoActualizacion(tipoActualizacion);
			evolucion.setIdVehiculoOrigen(idVehiculoOrigen);
			UsuarioVO usuario = new UsuarioVO();
			usuario.setIdUsuario(idUsuario);
			evolucion.setUsuario(usuario);
			evolucionVehiculoDao.guardar(evolucion);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de guardar la evolucion del vehiculo con id: " + idVehiculo + ", error: ", e);
		}
	}

	@Override
	@Transactional
	public void guardarEvolucionVO(EvolucionVehiculoVO evolucion, Long idVehiculo, String numColegiado, BigDecimal numExpediente, String tipoTramite, String tipoActualizacion, Long idUsuario) {
		try {
			EvolucionVehiculoPK id = new EvolucionVehiculoPK();
			id.setFechaHora(new Date());
			id.setIdVehiculo(idVehiculo);
			id.setNumColegiado(numColegiado);
			evolucion.setId(id);
			evolucion.setNumExpediente(numExpediente);
			evolucion.setTipoTramite(tipoTramite);
			evolucion.setTipoActualizacion(tipoActualizacion);
			UsuarioVO usuario = new UsuarioVO();
			usuario.setIdUsuario(idUsuario);
			evolucion.setUsuario(usuario);
			evolucionVehiculoDao.guardar(evolucion);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de guardar la evolucion del vehiculo con id: " + idVehiculo + ", error: ", e);
		}
	}
}
