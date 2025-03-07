package org.gestoresmadrid.oegam2comun.tasas.view.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ConsultaTasaNuevaBean implements Serializable {


	private static final long serialVersionUID = 7354182411783475371L;

	
	private BigDecimal numExpediente;
	
	private String numColegiado;
	
	private String codigoTasa;
	
	private String formato;
	
	private String descContrato;
	
	private Date fechaAlta;
	
	private Date fechaAsignacion;
	
	private Date fechaFinVigencia;
	
	private String tipoTasa;
	
	private String precio;
	
	private String contrato;
	
	private String comentarios;
	
	private BigDecimal importadoIcogam;
	
	private String asignada;
	
	

	
	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public String getDescContrato() {
		return descContrato;
	}

	public void setDescContrato(String descContrato) {
		this.descContrato = descContrato;
	}

	public Date getFechaAsignacion() {
		return fechaAsignacion;
	}

	public void setFechaAsignacion(Date fechaAsignacion) {
		this.fechaAsignacion = fechaAsignacion;
	}

	public String getTipoTasa() {
		return tipoTasa;
	}

	public void setTipoTasa(String tipoTasa) {
		this.tipoTasa = tipoTasa;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public String getFormato() {
		return formato;
	}

	public void setFormato(String formato) {
		this.formato = formato;
	}

	public Date getFechaFinVigencia() {
		return fechaFinVigencia;
	}

	public void setFechaFinVigencia(Date fechaFinVigencia) {
		this.fechaFinVigencia = fechaFinVigencia;
	}

	public String getPrecio() {
		return precio;
	}

	public void setPrecio(String precio) {
		this.precio = precio;
	}

	public String getContrato() {
		return contrato;
	}

	public void setContrato(String contrato) {
		this.contrato = contrato;
	}

	public String getComentarios() {
		return comentarios;
	}

	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}

	public BigDecimal getImportadoIcogam() {
		return importadoIcogam;
	}

	public void setImportadoIcogam(BigDecimal importadoIcogam) {
		this.importadoIcogam = importadoIcogam;
	}

	public String getCodigoTasa() {
		return codigoTasa;
	}

	public void setCodigoTasa(String codigoTasa) {
		this.codigoTasa = codigoTasa;
	}

	public String getAsignada() {
		return asignada;
	}

	public void setAsignada(String asignada) {
		this.asignada = asignada;
	}


	
}
