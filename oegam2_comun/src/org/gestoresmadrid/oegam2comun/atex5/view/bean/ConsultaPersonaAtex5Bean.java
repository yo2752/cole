package org.gestoresmadrid.oegam2comun.atex5.view.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ConsultaPersonaAtex5Bean implements Serializable{

	private static final long serialVersionUID = 3653669636993870590L;
	
	private Long idConsultaPersonaAtex5;
	
	private BigDecimal numExpediente;
	
	private String numColegiado;
	
	private String codigoTasa;
	
	private String estado;
	
	private String descContrato;
	
	private Date fechaAlta;

	public Long getIdConsultaPersonaAtex5() {
		return idConsultaPersonaAtex5;
	}

	public void setIdConsultaPersonaAtex5(Long idConsultaPersonaAtex5) {
		this.idConsultaPersonaAtex5 = idConsultaPersonaAtex5;
	}

	public String getCodigoTasa() {
		return codigoTasa;
	}

	public void setCodigoTasa(String codigoTasa) {
		this.codigoTasa = codigoTasa;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getDescContrato() {
		return descContrato;
	}

	public void setDescContrato(String descContrato) {
		this.descContrato = descContrato;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

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
	
}
