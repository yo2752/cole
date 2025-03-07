package org.gestoresmadrid.oegam2.trafico.materiales.controller.action;

import java.util.Arrays;
import java.util.List;

import org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.ServicioConsultaStock;
import org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.ServicioCrearSolicitudesPedido;
import org.springframework.beans.factory.annotation.Autowired;

import escrituras.beans.ResultBean;
import general.acciones.ActionBase;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ActualizarInformacionAction extends ActionBase {

	private static final long serialVersionUID = -4450960067950692287L;

	private static final String SUCCESS = "success";

	private static final ILoggerOegam log = LoggerOegam.getLogger(ActualizarInformacionAction.class);

	@Autowired ServicioCrearSolicitudesPedido	servicioCrearSolicitudesPedido;
	@Autowired ServicioConsultaStock			servicioConsultaStock;

	private Long[] idActualizaciones;
	private String resultado;

	public String sincronizarPedidos() {
		log.info("Encolar los pedidos seleccionados");
		List<Long> listadoPedidos = Arrays.asList(this.getIdActualizaciones()); 
		ResultBean result = servicioCrearSolicitudesPedido.crearSolicitudInformacionPedido(listadoPedidos);
		if (result.getError()) {
			this.setResultado("Las solicitudes no se han podido encolar");
		} else {
			this.setResultado("Solicitudes encoladas correctamente");
		}
		return SUCCESS;
	}

	public String sincronizarStock() {
		log.info("Encolar los stocks seleccionados");
		
		List<Long> listadoStocks = Arrays.asList(this.getIdActualizaciones());
		ResultBean result = servicioCrearSolicitudesPedido.crearSolicitudInformacionStock(listadoStocks, null);
		if (result.getError()) {
			this.setResultado("Las solicitudes no se han podido encolar");
		} else {
			this.setResultado("Solicitudes encoladas correctamente");
		}
		return SUCCESS;
	}

	// Getters & Setters
	public Long[] getIdActualizaciones() {
		return idActualizaciones;
	}

	public void setIdActualizaciones(Long[] idActualizaciones) {
		this.idActualizaciones = idActualizaciones;
	}

	public String getResultado() {
		return resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}

}