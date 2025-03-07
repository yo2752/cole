package org.gestoresmadrid.oegam2.trafico.materiales.controller.action;

import java.util.LinkedHashMap;

import org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.ServicioConsultaMateriales;
import org.springframework.beans.factory.annotation.Autowired;

import general.acciones.ActionBase;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class TipoDistintivoAjaxAction extends ActionBase {

	private static final long serialVersionUID = -2448682514468899591L;

	private static final String SUCCESS = "success";

	@Autowired ServicioConsultaMateriales servicioConsultaMateriales;

	@SuppressWarnings("unused")
	private static final ILoggerOegam log = LoggerOegam.getLogger(TipoDistintivoAjaxAction.class);

	private Long tipoDocumento;
	private LinkedHashMap<Long, String> tipoDistintivo = new LinkedHashMap<>();

	public String comboValues() {

		switch (this.getTipoDocumento().intValue()) {
			case 2:
				tipoDistintivo = servicioConsultaMateriales.getPermisoConducir();
				break;
			case 1:
				tipoDistintivo = servicioConsultaMateriales.getTipoDistintivo();
				break;
			case 3:
				tipoDistintivo.put(1L, "Ficha Técnica");
				break;
		}

		return SUCCESS;
	}

	public Long getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(Long tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public LinkedHashMap<Long, String> getTipoDistintivo() {
		return tipoDistintivo;
	}

	public void setTipoDistintivo(LinkedHashMap<Long, String> tipoDistintivo) {
		this.tipoDistintivo = tipoDistintivo;
	}

}