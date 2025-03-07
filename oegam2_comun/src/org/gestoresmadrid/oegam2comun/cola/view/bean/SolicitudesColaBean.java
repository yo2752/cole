package org.gestoresmadrid.oegam2comun.cola.view.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class SolicitudesColaBean implements Serializable{

	private static final long serialVersionUID = 4732104243335146363L;

	private String idEnvio;
	private String proceso;
	private String idTramite;
	private String bastidor;
	private String matricula;
	private String estado;
	private BigDecimal nIntento;
	private String cola;
	private String fechaHora;
	private String respuesta;
	
	public String getIdEnvio() {
		return idEnvio;
	}
	public void setIdEnvio(String idEnvio) {
		this.idEnvio = idEnvio;
	}
	public String getProceso() {
		return proceso;
	}
	public void setProceso(String proceso) {
		this.proceso = proceso;
	}
	public String getIdTramite() {
		return idTramite;
	}
	public void setIdTramite(String idTramite) {
		this.idTramite = idTramite;
	}
	public String getBastidor() {
		return bastidor;
	}
	public void setBastidor(String bastidor) {
		this.bastidor = bastidor;
	}
	public String getMatricula() {
		return matricula;
	}
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getCola() {
		return cola;
	}
	public void setCola(String cola) {
		this.cola = cola;
	}
	public String getFechaHora() {
		return fechaHora;
	}
	public void setFechaHora(String fechaHora) {
		this.fechaHora = fechaHora;
	}
	public String getRespuesta() {
		return respuesta;
	}
	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}
	public BigDecimal getnIntento() {
		return nIntento;
	}
	public void setnIntento(BigDecimal nIntento) {
		this.nIntento = nIntento;
	}
	
}
