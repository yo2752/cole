package org.gestoresmadrid.oegam2comun.consulta.tramite.view.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class ConsultaTramiteTraficoResultBean implements Serializable {

	private static final long serialVersionUID = 3835704307809585323L;

	private BigDecimal numExpediente;

	private String refPropia;

	private String bastidor;

	private String matricula;

	private String tipoTasa;

	private String codigoTasa;

	private String tipoTramite;

	private String estado;

	private String presentadoJPT;
	
	private String respuesta;

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

	public String getRefPropia() {
		return refPropia;
	}

	public void setRefPropia(String refPropia) {
		this.refPropia = refPropia;
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

	public String getTipoTasa() {
		return tipoTasa;
	}

	public void setTipoTasa(String tipoTasa) {
		this.tipoTasa = tipoTasa;
	}

	public String getCodigoTasa() {
		return codigoTasa;
	}

	public void setCodigoTasa(String codigoTasa) {
		this.codigoTasa = codigoTasa;
	}

	public String getTipoTramite() {
		return tipoTramite;
	}

	public void setTipoTramite(String tipoTramite) {
		this.tipoTramite = tipoTramite;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getPresentadoJPT() {
		return presentadoJPT;
	}

	public void setPresentadoJPT(String presentadoJPT) {
		this.presentadoJPT = presentadoJPT;
	}

	public String getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}
}
