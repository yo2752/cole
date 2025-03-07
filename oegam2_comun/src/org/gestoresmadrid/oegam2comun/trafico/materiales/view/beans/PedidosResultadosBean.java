package org.gestoresmadrid.oegam2comun.trafico.materiales.view.beans;

import java.io.Serializable;
import java.util.Date;

public class PedidosResultadosBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7830554299753616334L;

	private Long    pedidoId;
	private String  inventarioId;
	private String  observaciones;
	private String  codigoInicial;
	private String  codigoFinal;
	private String  jefaturaProvincial;
	private String  materialVO;
	private String  unidades;
	private String  pedidoDgt;
	private String  estado;
	private Date    fecha;
	private boolean pedidoPermisosEntregado; 
	private boolean pedidoEntregado;
	private boolean solicitarPedido;
	private boolean entregarPedido;

	public boolean isEntregarPedido() {
		return entregarPedido;
	}

	public void setEntregarPedido(boolean entregarPedido) {
		this.entregarPedido = entregarPedido;
	}

	public Long getPedidoId() {
		return pedidoId;
	}

	public void setPedidoId(Long pedidoId) {
		this.pedidoId = pedidoId;
	}

	public String getInventarioId() {
		return inventarioId;
	}

	public void setInventarioId(String inventarioId) {
		this.inventarioId = inventarioId;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public String getCodigoInicial() {
		return codigoInicial;
	}

	public void setCodigoInicial(String codigoInicial) {
		this.codigoInicial = codigoInicial;
	}

	public String getCodigoFinal() {
		return codigoFinal;
	}

	public void setCodigoFinal(String codigoFinal) {
		this.codigoFinal = codigoFinal;
	}

	public String getJefaturaProvincial() {
		return jefaturaProvincial;
	}

	public void setJefaturaProvincial(String jefaturaProvincial) {
		this.jefaturaProvincial = jefaturaProvincial;
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

	public String getPedidoDgt() {
		return pedidoDgt;
	}

	public void setPedidoDgt(String pedidoDgt) {
		this.pedidoDgt = pedidoDgt;
	}

	public String getMaterialVO() {
		return materialVO;
	}

	public void setMaterialVO(String materialVO) {
		this.materialVO = materialVO;
	}

	public String getUnidades() {
		return unidades;
	}

	public void setUnidades(String unidades) {
		this.unidades = unidades;
	}

	public boolean isPedidoPermisosEntregado() {
		return pedidoPermisosEntregado;
	}

	public void setPedidoPermisosEntregado(boolean pedidoPermisosEntregado) {
		this.pedidoPermisosEntregado = pedidoPermisosEntregado;
	}

	public boolean isPedidoEntregado() {
		return pedidoEntregado;
	}

	public void setPedidoEntregado(boolean pedidoEntregado) {
		this.pedidoEntregado = pedidoEntregado;
	}

	public boolean isSolicitarPedido() {
		return solicitarPedido;
	}

	public void setSolicitarPedido(boolean solicitarPedido) {
		this.solicitarPedido = solicitarPedido;
	}

}
