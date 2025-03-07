package org.gestoresmadrid.oegam2comun.trafico.materiales.view.beans.rest;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PedidoRest implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3773605046658817000L;
	
	private String pedidoDgt;
	private String observaciones;
	private String codInicial;
	private String codFinal;
	private String delegacion_id;
	
	public String getPedidoDgt() {
		return pedidoDgt;
	}
	public void setPedidoDgt(String pedidoDgt) {
		this.pedidoDgt = pedidoDgt;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	public String getCodInicial() {
		return codInicial;
	}
	public void setCodInicial(String codInicial) {
		this.codInicial = codInicial;
	}
	public String getCodFinal() {
		return codFinal;
	}
	public void setCodFinal(String codFinal) {
		this.codFinal = codFinal;
	}
	public String getDelegacion_id() {
		return delegacion_id;
	}
	public void setDelegacion_id(String delegacion_id) {
		this.delegacion_id = delegacion_id;
	}
	
	
}
