package org.gestoresmadrid.oegam2comun.envioData.view.dto;

import java.io.Serializable;
import java.util.Date;

public class EnvioDataDto implements Serializable{

	private static final long serialVersionUID = -3051225552648917305L;
	
	private String proceso; 
	private String fechaEnvio;
	private String correcta;
	private String numExpediente;
	private String respuesta;
	private String cola;
	private Date fechaUltCola;
	
	public String getProceso() {
		return proceso;
	}
	public void setProceso(String proceso) {
		this.proceso = proceso;
	}
	public String getFechaEnvio() {
		return fechaEnvio;
	}
	public void setFechaEnvio(String fechaEnvio) {
		this.fechaEnvio = fechaEnvio;
	}
	public String getCorrecta() {
		return correcta;
	}
	public void setCorrecta(String correcta) {
		this.correcta = correcta;
	}
	public String getNumExpediente() {
		return numExpediente;
	}
	public void setNumExpediente(String numExpediente) {
		this.numExpediente = numExpediente;
	}
	public String getRespuesta() {
		return respuesta;
	}
	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}
	public String getCola() {
		return cola;
	}
	public void setCola(String cola) {
		this.cola = cola;
	}
	public Date getFechaUltCola() {
		return fechaUltCola;
	}
	public void setFechaUltCola(Date fechaUltCola) {
		this.fechaUltCola = fechaUltCola;
	}
	
}
