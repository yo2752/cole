package org.gestoresmadrid.oegam2comun.trafico.solInfo.view.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ConsultaInteveDto implements Serializable{

	private static final long serialVersionUID = 3178621444459518386L;
	
	

	private BigDecimal numExpediente;
	
	private String numColegiado;
	
	private String nombreContrato;
	
	private String bastidor[];
	
	private String matricula[];
	
	private String tasa[];
	
	private Date fechaAlta;
	
	private String estado;
	
	private Long idContrato;
	

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

	public String[] getBastidor() {
		return bastidor;
	}

	public void setBastidor(String[] bastidor) {
		this.bastidor = bastidor;
	}

	public String[] getMatricula() {
		return matricula;
	}

	public void setMatricula(String[] matricula) {
		this.matricula = matricula;
	}

	public String[] getTasa() {
		return tasa;
	}

	public void setTasa(String[] tasa) {
		this.tasa = tasa;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getNombreContrato() {
		return nombreContrato;
	}

	public void setNombreContrato(String nombreContrato) {
		this.nombreContrato = nombreContrato;
	}

	public Long getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Long idContrato) {
		this.idContrato = idContrato;
	}



}
