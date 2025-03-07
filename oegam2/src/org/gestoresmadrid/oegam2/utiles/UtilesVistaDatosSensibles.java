package org.gestoresmadrid.oegam2.utiles;

import java.util.List;

import org.gestoresmadrid.core.model.beans.DatoMaestroBean;
import org.gestoresmadrid.core.model.enumerados.EstadoBastidor;
import org.gestoresmadrid.core.model.enumerados.TipoBastidor;
import org.gestoresmadrid.oegam2comun.grupo.model.service.ServicioGrupo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import trafico.datosSensibles.utiles.enumerados.TiempoBajaDatosSensibles;
import trafico.datosSensibles.utiles.enumerados.TiposDatosSensibles;

public class UtilesVistaDatosSensibles {

	private static UtilesVistaDatosSensibles utilesVistaDatosSensibles;

	@Autowired
	private ServicioGrupo servicioGrupo;

	private UtilesVistaDatosSensibles() {
		super();
	}

	public static UtilesVistaDatosSensibles getInstance() {
		if (utilesVistaDatosSensibles == null) {
			utilesVistaDatosSensibles = new UtilesVistaDatosSensibles();
			SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(utilesVistaDatosSensibles);
		}
		return utilesVistaDatosSensibles;
	}

	public TiposDatosSensibles[] getComboDatosSensibles() {
		return TiposDatosSensibles.values();
	}

	public TiempoBajaDatosSensibles[] getComboBajaDatosSensibles() {
		return TiempoBajaDatosSensibles.values();
	}

	public List<DatoMaestroBean> getComboGrupoUsuarios() {
		return servicioGrupo.getComboGrupos();
	}

	public String getDescripcionGrupo(String idGrupo) {
		return servicioGrupo.getDescripcionGrupo(idGrupo);
	}

	public TipoBastidor[] getComboTipoControlDatoSensible() {
		return TipoBastidor.values();
	}

	public EstadoBastidor[] getComboEstadoDatoSensible() {
		return EstadoBastidor.values();
	}
}