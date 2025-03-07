package org.gestoresmadrid.oegamInteve.view.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class TramiteTraficoSolInteveDto implements Serializable {

	private static final long serialVersionUID = 8172361693891902059L;

	private Long idTramiteInteve;

	private String codigoTasa;

	private String estado;

	private String tipoInforme;

	private String matricula;

	private String bastidor;

	private String nive;

	private String respuestaDgt;

	private BigDecimal numExpediente;

	private Date fechaPresentacion;

	private boolean aceptacion;

	public Long getIdTramiteInteve() {
		return idTramiteInteve;
	}

	public void setIdTramiteInteve(Long idTramiteInteve) {
		this.idTramiteInteve = idTramiteInteve;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getTipoInforme() {
		return tipoInforme;
	}

	public void setTipoInforme(String tipoInforme) {
		this.tipoInforme = tipoInforme;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getBastidor() {
		return bastidor;
	}

	public void setBastidor(String bastidor) {
		this.bastidor = bastidor;
	}

	public String getNive() {
		return nive;
	}

	public void setNive(String nive) {
		this.nive = nive;
	}

	public String getRespuestaDgt() {
		return respuestaDgt;
	}

	public void setRespuestaDgt(String respuestaDgt) {
		this.respuestaDgt = respuestaDgt;
	}

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

	public boolean isAceptacion() {
		return aceptacion;
	}

	public void setAceptacion(boolean aceptacion) {
		this.aceptacion = aceptacion;
	}

	public Date getFechaPresentacion() {
		return fechaPresentacion;
	}

	public void setFechaPresentacion(Date fecha_presentacion) {
		this.fechaPresentacion = fecha_presentacion;
	}

	public String getCodigoTasa() {
		return codigoTasa;
	}

	public void setCodigoTasa(String codigoTasa) {
		this.codigoTasa = codigoTasa;
	}

}