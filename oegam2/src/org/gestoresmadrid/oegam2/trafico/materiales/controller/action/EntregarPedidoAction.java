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

public class EntregarPedidoAction extends ActionBase {

	private static final long serialVersionUID = -2240961842501805619L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(EntregarPedidoAction.class);

	private static final String SUCCESS = "success";

	@Autowired ServicioGuardarPedido servicioGuardarPedido;

	private Long[] idEntregados;
	private String resultado;

	public String entregar() {
		log.info("Solicitar pedidos al consejo");
		log.info("Encolar los pedidos seleccionados");

		ResultBean result = new ResultBean(Boolean.TRUE);

		List<Long> listadoPedidos = Arrays.asList(this.getIdEntregados());
		for(Long item: listadoPedidos) {
			Log.info("Pedido ==> " + item);
			result = servicioGuardarPedido.entregarPedidos(listadoPedidos);
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

	public static ILoggerOegam getLog() {
		return log;
	}

	public Long[] getIdEntregados() {
		return idEntregados;
	}

	public void setIdEntregados(Long[] idEntregados) {
		this.idEntregados = idEntregados;
	}

}