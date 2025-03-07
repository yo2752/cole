package org.gestoresmadrid.oegam2.transporte.utiles;

import java.util.List;

import org.gestoresmadrid.core.model.beans.DatoMaestroBean;
import org.gestoresmadrid.core.transporte.model.enumeration.EstadosNotificacionesTransporte;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioContrato;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioMunicipio;
import org.gestoresmadrid.oegamComun.direccion.view.dto.MunicipioDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

public class UtilesVistaNotificacionTransporte {
	
	private static UtilesVistaNotificacionTransporte utilesVistaNotificacionTransporte;
	
	@Autowired
	ServicioContrato servicioContrato;
	
	@Autowired
	ServicioMunicipio servicioMunicipio;
	
	public static UtilesVistaNotificacionTransporte getInstance() {
		if (utilesVistaNotificacionTransporte == null) {
			utilesVistaNotificacionTransporte = new UtilesVistaNotificacionTransporte();
			SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(utilesVistaNotificacionTransporte);
		}
		return utilesVistaNotificacionTransporte;
	}
	
	public List<DatoMaestroBean> getComboContratosHabilitados() {
		return servicioContrato.getComboContratosHabilitados();
	}
	
	public EstadosNotificacionesTransporte[] getEstadosNotificaciones(){
		return EstadosNotificacionesTransporte.values();
	}
	
	public List<MunicipioDto> getComboMunicipios(){
		return servicioMunicipio.listaMunicipios("28");
	}
}
