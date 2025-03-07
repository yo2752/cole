package org.gestoresmadrid.oegam2.estacionITV.controller.action;

import org.gestoresmadrid.oegam2comun.administracion.service.ServicioRecargaCache;
import org.gestoresmadrid.oegam2comun.estacionITV.model.service.ServicioEstacionITV;
import org.gestoresmadrid.oegam2comun.estacionITV.view.bean.EstacionITV;
import org.springframework.beans.factory.annotation.Autowired;

import escrituras.beans.ResultBean;
import general.acciones.ActionBase;

public class EstacionITVAction extends ActionBase{

	private static final long serialVersionUID = 1L;

	public EstacionITV estacionITV;

	@Autowired
	private ServicioEstacionITV estacionITVService;

	@Autowired
	private ServicioRecargaCache servicioRecargaCache;

	public String execute(){
		return SUCCESS;
	}

	public String alta(){
		ResultBean resultadoAlta = new ResultBean();
		if (estacionValida()) {

			resultadoAlta = estacionITVService.altaEstacionITV(estacionITV);

			if (resultadoAlta.getError()) {
				addActionError(resultadoAlta.getMensaje());
			} else {
				addActionMessage("Estacion guardada correctamente.");
				servicioRecargaCache.registrarPeticionRecargaCombos();
				addActionMessage("Se ha registrado una peticion para refrescar los combos automaticamente.");
			}

		}
		return SUCCESS;
	}

	private Boolean estacionValida() {
		Boolean esValida = true;

		if (estacionITV == null) {
			esValida = false;
			addActionError("Ha ocurrido un error al recuperar la Estacion ITV");
		} else {
			if ("".equals(estacionITV.getId()) || !(estacionITV.getId().matches("\\d+"))) {
				esValida = false;
				addActionError("El ID de la estación no puede estar vacío o no es válido.");
			}

			if ("".equals(estacionITV.getProvincia())) {
				esValida = false;
				addActionError("La provincia de la estación no puede estar vacía.");
			}

			if ("".equals(estacionITV.getMunicipio())) {
				esValida = false;
				addActionError("El municipio de la estación no puede estar vacío.");
			}
		}
		return esValida;
	}

	/* GETTERS y SETTERS */

	public EstacionITV getEstacionITV() {
		return estacionITV;
	}

	public void setEstacionITV(EstacionITV estacionITV) {
		this.estacionITV = estacionITV;
	}

}