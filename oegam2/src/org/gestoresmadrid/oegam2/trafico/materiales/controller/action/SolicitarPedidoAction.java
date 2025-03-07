package org.gestoresmadrid.oegam2.trafico.materiales.controller.action;

import java.util.Arrays;
import java.util.List;

import org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.ServicioGuardarPedido;
import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Autowired;

import escrituras.beans.ResultBean;
import general.acciones.ActionBase;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class SolicitarPedidoAction extends ActionBase {

	private static final long serialVersionUID = -2240961842501805619L;

	private static final String SUCCESS = "success";

	private static final ILoggerOegam log = LoggerOegam.getLogger(SolicitarPedidoAction.class);

	@Autowired ServicioGuardarPedido servicioGuardarPedido;

	private Long[] idSolicitudes;
	private String resultado;

	public String solicitar() {
		log.info("Solicitar pedidos al consejo");
		log.info("Encolar los pedidos seleccionados");

		ResultBean result = new ResultBean(Boolean.TRUE);

		List<Long> listadoPedidos = Arrays.asList(this.getIdSolicitudes());
		for(Long item: listadoPedidos) {
			Log.info("Pedido ==> " + item);
			result = servicioGuardarPedido.solicitarPedidos(listadoPedidos);
		}

		if (result.getError()) {
			this.setResultado("Las solicitudes no se han procesado.");
		} else {
			this.setResultado("Las solicitudes se han procesado correctamente.");
		}

		return SUCCESS;
	}

	public String getResultado() {
		return resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}

	public Long[] getIdSolicitudes() {
		return idSolicitudes;
	}

	public void setIdSolicitudes(Long[] idSolicitudes) {
		this.idSolicitudes = idSolicitudes;
	}

}