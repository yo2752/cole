package org.gestoresmadrid.oegam2comun.atex5.view.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ConsultaVehiculoAtex5Bean implements Serializable {

	private static final long serialVersionUID = -2589739643313071096L;

	private Long idConsultaVehiculoAtex5;

	private BigDecimal numExpediente;

	private String numColegiado;

	private String codigoTasa;

	private String estado;

	private String matricula;

	private String bastidor;

	private String provinciaContrato;

	private Date fechaAlta;

	public Long getIdConsultaVehiculoAtex5() {
		return idConsultaVehiculoAtex5;
	}

	public void setIdConsultaVehiculoAtex5(Long idConsultaVehiculoAtex5) {
		this.idConsultaVehiculoAtex5 = idConsultaVehiculoAtex5;
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

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getProvinciaContrato() {
		return provinciaContrato;
	}

	public void setProvinciaContrato(String provinciaContrato) {
		this.provinciaContrato = provinciaContrato;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public String getBastidor() {
		return bastidor;
	}

	public void setBastidor(String bastidor) {
		this.bastidor = bastidor;
	}
}
