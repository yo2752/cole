package org.gestoresmadrid.oegam2.circulares.utiles;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.circular.model.enumerados.EstadoCircular;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioContrato;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioMunicipio;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioProvincia;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;


public class UtilesVistaCircular implements Serializable{
	
	private static final long serialVersionUID = 8733965188112679324L;
	private static UtilesVistaCircular utilesVistaCircular;
	
	@Autowired
	ServicioContrato servicioContrato;
	
	@Autowired
	ServicioMunicipio servicioMunicipio;
	
	@Autowired
	ServicioProvincia servicioProvincia;
	
	@Autowired
	UtilesColegiado utilesColegiado;

	public static UtilesVistaCircular getInstance(){
		if(utilesVistaCircular == null){
			utilesVistaCircular = new UtilesVistaCircular();
			SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(utilesVistaCircular);
		}
		return utilesVistaCircular;
	}

	public EstadoCircular[] getEstados(){
		return EstadoCircular.values();
	}
	public List<EstadoCircular> getEstadoCircular(){
		return EstadoCircular.getEstadoCircular();
	}
	

	public Boolean esAdmin(){
		return utilesColegiado.tienePermisoAdmin();
	}
	


}
