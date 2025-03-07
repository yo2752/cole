package org.gestoresmadrid.oegam2.trafico.prematriculados.controller.action;

import org.gestoresmadrid.oegam2comun.trafico.prematriculados.model.service.ServicioVehiculosPrematriculados;
import org.gestoresmadrid.oegam2comun.trafico.prematriculados.view.dto.VehiculoPrematriculadoDto;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.springframework.beans.factory.annotation.Autowired;

import escrituras.beans.ResultBean;
import general.acciones.ActionBase;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ConsultaTarjetaAction extends ActionBase {

	private static final long serialVersionUID = 1101259432306893562L;
	private static ILoggerOegam log = LoggerOegam.getLogger(ConsultaTarjetaAction.class);

	@Autowired
	private ServicioVehiculosPrematriculados servicioVehiculosPrematriculados;

	@Autowired
	private UtilesColegiado utilesColegiado;

	private VehiculoPrematriculadoDto vehiculoPrematriculado;

	public String nuevo(){
		return SUCCESS;
	}

	public String consultar(){
		if (!utilesColegiado.tienePermisoAdmin() && !utilesColegiado.tienePermisoConsultaEITV()) {
			addActionError("No tiene permisos para consultar las tarjetas eITV");
			return SUCCESS;
		}
		try {
			ResultBean rs = servicioVehiculosPrematriculados.validarConsulta(vehiculoPrematriculado);
			if (rs.getError()){
				for (String error: rs.getListaMensajes()){
					addActionError(error);
				}
				return SUCCESS;
			}
			vehiculoPrematriculado.setNumColegiado(utilesColegiado.getNumColegiadoSession());
			rs = servicioVehiculosPrematriculados.consultarEitv(vehiculoPrematriculado);
			if (rs.getError()){
				for (String error: rs.getListaMensajes()){
					addActionError(error);
				}
				return SUCCESS;
			}
			addActionMessage("Solicitud de consulta de tarjeta EITV creada correctamente con el identificador: " + 
				vehiculoPrematriculado.getId()  + " Recibirá notificación tras el envío.");
		} catch (Exception ex) {
			log.error(ex.toString(), ex);
			addActionError(ex.toString());
		}
		return SUCCESS;
	}

	public VehiculoPrematriculadoDto getVehiculoPrematriculado() {
		return vehiculoPrematriculado;
	}

	public void setVehiculoPrematriculado(
			VehiculoPrematriculadoDto vehiculoPrematriculado) {
		this.vehiculoPrematriculado = vehiculoPrematriculado;
	}

	public ServicioVehiculosPrematriculados getServicioVehiculosPrematriculados() {
		return servicioVehiculosPrematriculados;
	}

	public void setServicioVehiculosPrematriculados(ServicioVehiculosPrematriculados servicioVehiculosPrematriculados) {
		this.servicioVehiculosPrematriculados = servicioVehiculosPrematriculados;
	}

}