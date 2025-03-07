package org.gestoresmadrid.oegam2comun.evolucionDocPrmDstvFicha.model.view.bean;

import java.io.Serializable;

public class EvolucionDocPrmDstvFichaBean implements Serializable{

	private static final long serialVersionUID = 2269145460796454467L;

	private String documento;
	private String fecha;
	private String operacion;
	private String usuario;
	private String docId;
	private String estadoAnterior;
	private String estadoNuevo;
	
	public String getDocumento() {
		return documento;
	}
	public void setDocumento(String documento) {
		this.documento = documento;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getOperacion() {
		return operacion;
	}
	public void setOperacion(String operacion) {
		this.operacion = operacion;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getDocId() {
		return docId;
	}
	public void setDocId(String docId) {
		this.docId = docId;
	}
	public String getEstadoAnterior() {
		return estadoAnterior;
	}
	public void setEstadoAnterior(String estadoAnterior) {
		this.estadoAnterior = estadoAnterior;
	}
	public String getEstadoNuevo() {
		return estadoNuevo;
	}
	public void setEstadoNuevo(String estadoNuevo) {
		this.estadoNuevo = estadoNuevo;
	}
}
