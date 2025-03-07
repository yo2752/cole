package org.gestoresmadrid.oegamComun.vehiculo.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.personas.model.vo.EvolucionPersonaVO;
import org.gestoresmadrid.core.vehiculo.model.dao.EvolucionVehiculoDao;
import org.gestoresmadrid.core.vehiculo.model.dao.VehiculoDao;
import org.gestoresmadrid.core.vehiculo.model.dao.VehiculoProcedureDao;
import org.gestoresmadrid.core.vehiculo.model.enumerados.EstadoVehiculo;
import org.gestoresmadrid.core.vehiculo.model.vo.EvolucionVehiculoPK;
import org.gestoresmadrid.core.vehiculo.model.vo.EvolucionVehiculoVO;
import org.gestoresmadrid.core.vehiculo.model.vo.VehiculoVO;
import org.gestoresmadrid.oegamComun.direccion.service.ServicioComunDireccion;
import org.gestoresmadrid.oegamComun.persona.service.ServicioComunPersona;
import org.gestoresmadrid.oegamComun.vehiculo.service.ServicioComunVehiculo;
import org.gestoresmadrid.oegamComun.vehiculo.service.ServicioPersistenciaVehiculo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioPersistenciaVehiculoImpl implements ServicioPersistenciaVehiculo{

	private static final long serialVersionUID = 694761900615807049L;
	
	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioPersistenciaVehiculoImpl.class);

	@Autowired
	VehiculoDao vehiculoDao;
	
	@Autowired
	VehiculoProcedureDao vehiculoProcedureDaoImpl;
	
	@Autowired
	EvolucionVehiculoDao evolucionVehiculoDao;
	
	@Autowired
	ServicioComunDireccion servicioComunDireccion;
	
	@Autowired
	ServicioComunPersona servicioComunPersona;
	
	@Override
	@Transactional(readOnly = true)
	public VehiculoVO getVehiculoVO(Long idVehiculo, String numColegiado, String matricula, String bastidor, String nive, EstadoVehiculo estado) {
		try {
			return vehiculoDao.getVehiculo(idVehiculo, numColegiado, matricula, bastidor, nive, estado);
		} catch (Exception e) {
			log.error("Error el obtener el vehiculo", e);
		}
		return null;
	}
	
	@Override
	@Transactional(readOnly=true)
	public Date calculoItvPQ(VehiculoVO vehiculoVO, Date fechaPresentacion, String tipoTramite) {
		return vehiculoProcedureDaoImpl.calculoItv(vehiculoVO, fechaPresentacion, tipoTramite);
	}
	
	@Override
	@Transactional
	public VehiculoVO guardarVehiculoConEvolucionVO(VehiculoVO vehiculo, EvolucionVehiculoVO evolucionVehiculo, Boolean guardarDir, Boolean guardarIntegrador, EvolucionPersonaVO evolucionIntegrador) {
		if(guardarIntegrador){
			servicioComunPersona.guardarPersonaConEvolucion(vehiculo.getPersona(), evolucionIntegrador);
		}
		if(guardarDir){
			vehiculo.setDireccion(servicioComunDireccion.guardarActualizarDireccion(vehiculo.getDireccion()));
		}
		vehiculo.setActivo(EstadoVehiculo.Activo.getValorEnum());
		vehiculo.setIdVehiculo((Long) vehiculoDao.guardar(vehiculo));
		evolucionVehiculo.getId().setIdVehiculo(vehiculo.getIdVehiculo());
		evolucionVehiculo.getId().setFechaHora(new Date());
		evolucionVehiculoDao.guardar(evolucionVehiculo);
		return vehiculo;
	}
	
	@Override
	@Transactional
	public void actualizarVehiculoConEvolucionVO(VehiculoVO vehiculo, EvolucionVehiculoVO evolucionVehiculo, Boolean guardarDir, Boolean guardarIntegrador,
			EvolucionPersonaVO evolucionIntegrador) {
		if(guardarIntegrador){
			servicioComunPersona.guardarPersonaConEvolucion(vehiculo.getPersona(), evolucionIntegrador);
		}
		if(guardarDir){
			vehiculo.setDireccion(servicioComunDireccion.guardarActualizarDireccion(vehiculo.getDireccion()));
		}
		vehiculo.setActivo(EstadoVehiculo.Activo.getValorEnum());
		vehiculoDao.actualizar(vehiculo);
		evolucionVehiculo.getId().setIdVehiculo(vehiculo.getIdVehiculo());
		evolucionVehiculo.getId().setFechaHora(new Date());
		evolucionVehiculoDao.guardar(evolucionVehiculo);
	}
	
	@Override
	@Transactional
	public VehiculoVO copiaVehiculoDesactivandoAnt(VehiculoVO vehiculoCopia, VehiculoVO vehiculoAnt, EvolucionVehiculoVO evolucionVehiculo, 
			BigDecimal numExpediente, String tipoTramite, Long idUsuario, Boolean guardarDir, Boolean guardarIntegrador, EvolucionPersonaVO evolucionIntegrador) {
		if(guardarIntegrador){
			servicioComunPersona.guardarPersonaConEvolucion(vehiculoCopia.getPersona(), evolucionIntegrador);
		}
		if(guardarDir){
			vehiculoCopia.setDireccion(servicioComunDireccion.guardarActualizarDireccion(vehiculoCopia.getDireccion()));
		}
		Long idVehiculoOrig = vehiculoCopia.getIdVehiculo();
		vehiculoCopia.setIdVehiculo(null);
		vehiculoCopia.setActivo(EstadoVehiculo.Activo.getValorEnum());
		vehiculoCopia.setIdVehiculo((Long) vehiculoDao.guardar(vehiculoCopia));
		evolucionVehiculoDao.guardar(rellenarEvolucion(idVehiculoOrig,vehiculoCopia.getIdVehiculo(), vehiculoCopia.getNumColegiado(), numExpediente, tipoTramite, idUsuario, 
				ServicioComunVehiculo.TIPO_ACTUALIZACION_COP));
		vehiculoAnt.setActivo(EstadoVehiculo.Desactivo.getValorEnum());
		vehiculoDao.guardarOActualizar(vehiculoAnt);
		if(StringUtils.isNotBlank(evolucionVehiculo.getOtros())){
			evolucionVehiculo.setOtros("Vehículo Desactivado. Id nuevo: " + vehiculoCopia.getIdVehiculo() 
				+ " -- " + evolucionVehiculo.getOtros());
		} else {
			evolucionVehiculo.setOtros("Vehículo Desactivado. Id nuevo: " + vehiculoCopia.getIdVehiculo());
		}
		evolucionVehiculo.setTipoActualizacion(ServicioComunVehiculo.TIPO_ACTUALIZACION_MOD);
		evolucionVehiculo.getId().setIdVehiculo(vehiculoAnt.getIdVehiculo());
		evolucionVehiculo.getId().setFechaHora(new Date());
		evolucionVehiculoDao.guardar(evolucionVehiculo);
		return vehiculoCopia;
	}

	private EvolucionVehiculoVO rellenarEvolucion(Long idVehiculoOrig, Long idVehiculoNuevo, String numColegiado,
			BigDecimal numExpediente, String tipoTramite, Long idUsuario, String tipoActualizacion) {
		EvolucionVehiculoVO evolucionVehiculoNue = new EvolucionVehiculoVO();
		evolucionVehiculoNue.setIdVehiculoOrigen(idVehiculoOrig);
		EvolucionVehiculoPK id = new EvolucionVehiculoPK();
		id.setIdVehiculo(idVehiculoNuevo);
		id.setNumColegiado(numColegiado);
		evolucionVehiculoNue.setNumExpediente(numExpediente);
		evolucionVehiculoNue.setTipoTramite(tipoTramite);
		evolucionVehiculoNue.setTipoActualizacion(tipoActualizacion);
		id.setFechaHora(new Date());
		evolucionVehiculoNue.setId(id);
		return evolucionVehiculoNue;
	}
}
