package org.gestoresmadrid.oegam2.docBase.controller.action;

import org.gestoresmadrid.oegam2comun.notificacionpreferencias.service.ServicioContratoPreferencias;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoPreferenciaDto;
import org.springframework.beans.factory.annotation.Autowired;

import general.acciones.ActionBase;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class PreferenciasDocBaseAction extends ActionBase {

	private static final long serialVersionUID = -3621367830009512246L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(PreferenciasDocBaseAction.class);

	ContratoPreferenciaDto preferencias;

	@Autowired
	ServicioContratoPreferencias servicioContratoPreferencias;

	@Autowired
	UtilesColegiado utilesColegiado;

	public String inicio() {
		preferencias = servicioContratoPreferencias.getContratoPreferencias(utilesColegiado.getIdContratoSession());
		if (preferencias == null) {
			preferencias = new ContratoPreferenciaDto();
			preferencias.setIdContrato(utilesColegiado.getIdContratoSession());
		}
		return SUCCESS;
	}

	public String guardar() {
		try {
			servicioContratoPreferencias.guardar(preferencias);
			addActionMessage("Preferencias guardadas correctamente.");
		} catch (Exception e) {
			log.error("Error al guarda el contrato preferencias contrato.", e);
		}
		return SUCCESS;
	}

	public ContratoPreferenciaDto getPreferencias() {
		return preferencias;
	}

	public void setPreferencias(ContratoPreferenciaDto preferencias) {
		this.preferencias = preferencias;
	}
}
