package org.gestoresmadrid.oegam2comun.trafico.materiales.view.dto;

import java.io.Serializable;
import java.util.Date;

public class PedPaqueteDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1876162033285473522L;
	
	private Long   pedPaqueteId;
    private Long   pedidoId;
    private String numSerieIncial;
	private String numSerieFin;
	private String numSerieActual;
	private Long   estado;
	private Date   fechaBaja;
	private Long   usuarioBaja;
	
	public Long getPedPaqueteId() {
		return pedPaqueteId;
	}
	public void setPedPaqueteId(Long pedPaqueteId) {
		this.pedPaqueteId = pedPaqueteId;
	}
	public Long getPedidoId() {
		return pedidoId;
	}
	public void setPedidoId(Long pedidoId) {
		this.pedidoId = pedidoId;
	}
	public String getNumSerieIncial() {
		return numSerieIncial;
	}
	public void setNumSerieIncial(String numSerieIncial) {
		this.numSerieIncial = numSerieIncial;
	}
	public String getNumSerieFin() {
		return numSerieFin;
	}
	public void setNumSerieFin(String numSerieFin) {
		this.numSerieFin = numSerieFin;
	}
	public String getNumSerieActual() {
		return numSerieActual;
	}
	public void setNumSerieActual(String numSerieActual) {
		this.numSerieActual = numSerieActual;
	}
	public Date getFechaBaja() {
		return fechaBaja;
	}
	public void setFechaBaja(Date fechaBaja) {
		this.fechaBaja = fechaBaja;
	}
	public Long getUsuarioBaja() {
		return usuarioBaja;
	}
	public void setUsuarioBaja(Long usuarioBaja) {
		this.usuarioBaja = usuarioBaja;
	}
	public Long getEstado() {
		return estado;
	}
	public void setEstado(Long estado) {
		this.estado = estado;
	}
	
}
