package org.gestoresmadrid.oegam2.seguridadsocial.controller.action;

import java.math.BigDecimal;

import org.gestoresmadrid.core.general.model.vo.ContratoPreferenciaVO;
import org.gestoresmadrid.oegam2comun.notificacionpreferencias.service.ServicioContratoPreferencias;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.springframework.beans.factory.annotation.Autowired;

import general.acciones.ActionBase;

public class PreferenciasSeguridadSocialAction extends ActionBase{
	
	private static final long serialVersionUID = -6846287086578475013L;

	@Autowired
	private ServicioContratoPreferencias servicioContratoPreferencias;
	
	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	@Autowired
	private UtilesColegiado utilesColegiado;
	@Autowired
	private Utiles utiles;
	
	private ContratoPreferenciaVO contratoPreferencias;

	public String inicio(){
		if(utiles.esUsuarioOegam3(utilesColegiado.getIdUsuarioSessionBigDecimal().toString())) {
			return "redireccionOegam3";
		}else {
			if (!utilesColegiado.tienePermiso("SS2")) {
				if ("SI".equals(gestorPropiedades.valorPropertie("seguridad.social.forzar.aceptacion"))) {
					return "aceptacion";
				} else {
					return "forbidden";
				}
			} else {
				BigDecimal idContrato = utilesColegiado.getIdContratoSessionBigDecimal();
				contratoPreferencias = servicioContratoPreferencias.obtenerContratoPreferenciaByIdContrato(idContrato.longValue());
				if (contratoPreferencias == null){
					contratoPreferencias = new ContratoPreferenciaVO();
					contratoPreferencias.setIdContrato(utilesColegiado.getIdContratoSession());
				}
				return "success";
			}
		}
	}

	public String guardarDestinatario(){
		Object result = servicioContratoPreferencias.insertOrUpdate(contratoPreferencias);
		if (result==null){
			addActionError("Se ha producido un error al los nuevos destinatarios de notificaciones de la Seguridad Social");
		}else{
			addActionMessage("Se han guardado los nuevos destinatarios de notificaciones de la Seguridad Social");
		}
		return "success";
	}
	
	public String eliminarDestinatario(){
		contratoPreferencias.setOtrosDestinatariosSS(null);
		Object result = servicioContratoPreferencias.insertOrUpdate(contratoPreferencias);
		if (result==null){
			addActionError("Se ha producido un error al eliminar los destinatarios correctamente");
		}else{
			addActionMessage("Se han eliminado los destinatarios correctamente");
		}
		return "success";
	}

	public ContratoPreferenciaVO getContratoPreferencias() {
		return contratoPreferencias;
	}

	public void setContratoPreferencias(ContratoPreferenciaVO contratoPreferencias) {
		this.contratoPreferencias = contratoPreferencias;
	}

}
