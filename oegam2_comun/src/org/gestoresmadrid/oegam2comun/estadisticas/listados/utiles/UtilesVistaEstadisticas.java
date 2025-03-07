package org.gestoresmadrid.oegam2comun.estadisticas.listados.utiles;

import java.util.List;

import org.gestoresmadrid.oegamComun.usuarioLogin.ServicioPersistenciaUsuarioLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import estadisticas.utiles.enumerados.Agrupacion;
import estadisticas.utiles.enumerados.AgrupacionVehiculos;

public class UtilesVistaEstadisticas {

	private static UtilesVistaEstadisticas utilesVistaEstadisticas;

	private List<Integer> listaFrontales;

	@Autowired
	private ServicioPersistenciaUsuarioLogin servicioPersistenciaUsuarioLogin;

	private UtilesVistaEstadisticas() {
		super();
	}

	public static UtilesVistaEstadisticas getInstance() {
		if (utilesVistaEstadisticas == null) {
			utilesVistaEstadisticas = new UtilesVistaEstadisticas();
			SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(utilesVistaEstadisticas);
		}
		return utilesVistaEstadisticas;
	}

	// Devuelve una lista con los frontales activos
	public List<Integer> getListaFrontalesActivos() {
		if (listaFrontales == null) {
			listaFrontales = servicioPersistenciaUsuarioLogin.getFrontalesActivos();
		}
		return listaFrontales;

	}
	
	public Agrupacion[] getComboAgrupacion() {
		return Agrupacion.values();
	}

	public AgrupacionVehiculos[] getComboAgrupacionVehiculos() {
		return AgrupacionVehiculos.values();
	}


}
