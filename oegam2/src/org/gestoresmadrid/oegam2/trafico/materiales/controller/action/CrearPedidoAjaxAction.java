package org.gestoresmadrid.oegam2.trafico.materiales.controller.action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.ServicioConsultaPedidos;
import org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.ServicioGuardarPedido;
import org.springframework.beans.factory.annotation.Autowired;

import escrituras.beans.ResultBean;
import general.acciones.ActionBase;

public class CrearPedidoAjaxAction extends ActionBase {

	private static final long serialVersionUID = 8462868808267731008L;

	@Autowired ServicioConsultaPedidos servicioConsultaPedidos;
	@Autowired ServicioGuardarPedido   servicioGuardarPedido;

	private InputStream inputStream;

	private String jefatura;
	private String material;

	private Long   pedido;
	private String prefijo;
	private String serialIni;
	private String serialFin;

	public String validarMaterial() {
		Long pedido = servicioConsultaPedidos.validadMaterial(jefatura, new Long(material));
		String cadenaToSend = (pedido == null)? "valido": pedido.toString();
		inputStream = new ByteArrayInputStream(
				cadenaToSend.getBytes(StandardCharsets.UTF_8));
		return SUCCESS;		
	}

	public String actualizarSeriales() {
		String cadenaToSend = "";

		try {
			String codigoInicial	= String.format("%s-%s", prefijo, serialIni);
			String codigoFinal		= String.format("%s-%s", prefijo, serialFin);
			ResultBean resultado	= servicioGuardarPedido.actualizarSeriales(pedido, codigoInicial, codigoFinal);
			if (!resultado.getError() ) {
				cadenaToSend = String.format("Seriales actualizados correctamente para el pedido %d", pedido);
			} else {
				cadenaToSend = String.format("Se ha producido un error pedido %d", pedido);
			}
		} catch (Exception ex) {
			cadenaToSend = String.format("Se ha producido un error pedido %d", pedido);
		}

		inputStream = new ByteArrayInputStream(
				cadenaToSend.getBytes(StandardCharsets.UTF_8));
		return SUCCESS;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getJefatura() {
		return jefatura;
	}

	public void setJefatura(String jefatura) {
		this.jefatura = jefatura;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public Long getPedido() {
		return pedido;
	}

	public void setPedido(Long pedido) {
		this.pedido = pedido;
	}

	public String getPrefijo() {
		return prefijo;
	}

	public void setPrefijo(String prefijo) {
		this.prefijo = prefijo;
	}

	public String getSerialIni() {
		return serialIni;
	}

	public void setSerialIni(String serialIni) {
		this.serialIni = serialIni;
	}

	public String getSerialFin() {
		return serialFin;
	}

	public void setSerialFin(String serialFin) {
		this.serialFin = serialFin;
	}
}