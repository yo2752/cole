package org.gestoresmadrid.oegam2.utiles;

import java.util.Arrays;
import java.util.List;

import org.gestoresmadrid.core.model.beans.DatoMaestroBean;
import org.gestoresmadrid.core.trafico.interga.model.enumerados.EstadoTramitesInterga;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioContrato;
import org.gestoresmadrid.oegam2comun.presentacion.jpt.enumerados.JefaturasJPTEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

public class UtilesVistaInterga {

	private static UtilesVistaInterga utilesVistaPermisoInter;

	private EstadoTramitesInterga[] listaEstadosInterga;

	@Autowired
	ServicioContrato servicioContrato;

	public static UtilesVistaInterga getInstance() {
		if (utilesVistaPermisoInter == null) {
			utilesVistaPermisoInter = new UtilesVistaInterga();
			SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(utilesVistaPermisoInter);
		}
		return utilesVistaPermisoInter;
	}

	public EstadoTramitesInterga[] getEstadosInterga() {
		EstadoTramitesInterga[] listaEstadosIntergaAux = null;
		if (listaEstadosInterga == null) {
			listaEstadosIntergaAux = EstadoTramitesInterga.values();
			String[] nombres = new String[listaEstadosIntergaAux.length];
			for (int i = 0; i < listaEstadosIntergaAux.length; i++) {
				nombres[i] = listaEstadosIntergaAux[i].getNombreEnum();
			}
			Arrays.sort(nombres);
			listaEstadosInterga = new EstadoTramitesInterga[listaEstadosIntergaAux.length];
			for (int i = 0; i < nombres.length; i++) {
				for (int j = 0; j < listaEstadosIntergaAux.length; j++) {
					if (nombres[i].equals(listaEstadosIntergaAux[j].getNombreEnum())) {
						listaEstadosInterga[i] = listaEstadosIntergaAux[j];
					}
				}
			}
		}
		return listaEstadosInterga;
	}

	public List<EstadoTramitesInterga> getEstadosTramiInterga() {
		return EstadoTramitesInterga.listaEstadosTramite();
	}
	
	public List<EstadoTramitesInterga> getEstadosTramiIntergaDuplicados() {
		return EstadoTramitesInterga.listaEstadosTramiteDuplicados();
	}

	public List<EstadoTramitesInterga> getEstadosTramiteDocumentacion() {
		return EstadoTramitesInterga.listaEstadosTramiteDocumentacion();
	}

	public JefaturasJPTEnum[] getJefaturasJPTEnum() {
		return JefaturasJPTEnum.values();
	}

	public List<DatoMaestroBean> getComboContratosHabilitados() {
		return servicioContrato.getComboContratosHabilitados();
	}
}