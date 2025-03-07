package org.gestoresmadrid.oegam2comun.trafico.materiales.view.beans;

import java.io.Serializable;
import java.util.Date;

public class EvolucionPedidoResultadosBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8933686095278624056L;
	
	private Long evolucionPedidoId;
	private Long pedidoId;
	private Long inventarioId;
	private String estadoInicial;
	private String estado;
	private Date fecha;
	
	public Long getEvolucionPedidoId() {
		return evolucionPedidoId;
	}
	public void setEvolucionPedidoId(Long evolucionPedidoId) {
		this.evolucionPedidoId = evolucionPedidoId;
	}
	public Long getPedidoId() {
		return pedidoId;
	}
	public void setPedidoId(Long pedidoId) {
		this.pedidoId = pedidoId;
	}
	public Long getInventarioId() {
		return inventarioId;
	}
	public void setInventarioId(Long inventarioId) {
		this.inventarioId = inventarioId;
	}
	public String getEstadoInicial() {
		return estadoInicial;
	}
	public void setEstadoInicial(String estadoInicial) {
		this.estadoInicial = estadoInicial;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
}
