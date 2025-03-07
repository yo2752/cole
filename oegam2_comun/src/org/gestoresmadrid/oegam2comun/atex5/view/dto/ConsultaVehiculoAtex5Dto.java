package org.gestoresmadrid.oegam2comun.atex5.view.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;
import org.gestoresmadrid.oegamComun.tasa.view.dto.TasaDto;

public class ConsultaVehiculoAtex5Dto implements Serializable {

	private static final long serialVersionUID = -65942914465706387L;

	private Long idConsultaVehiculoAtex5;

	private String numColegiado;

	private BigDecimal numExpediente;

	private String matricula;

	private String bastidor;

	private String estado;

	private Date fechaAlta;

	private String respuesta;

	private ContratoDto contrato;

	private TasaDto tasa;

	private VehiculoAtex5Dto vehiculoAtex5;
	
	public Long getIdConsultaVehiculoAtex5() {
		return idConsultaVehiculoAtex5;
	}

	public void setIdConsultaVehiculoAtex5(Long idConsultaVehiculoAtex5) {
		this.idConsultaVehiculoAtex5 = idConsultaVehiculoAtex5;
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

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public String getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}

	public ContratoDto getContrato() {
		return contrato;
	}

	public void setContrato(ContratoDto contrato) {
		this.contrato = contrato;
	}

	public TasaDto getTasa() {
		return tasa;
	}

	public void setTasa(TasaDto tasa) {
		this.tasa = tasa;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

	public VehiculoAtex5Dto getVehiculoAtex5() {
		return vehiculoAtex5;
	}

	public void setVehiculoAtex5(VehiculoAtex5Dto vehiculoAtex5) {
		this.vehiculoAtex5 = vehiculoAtex5;
	}

}
