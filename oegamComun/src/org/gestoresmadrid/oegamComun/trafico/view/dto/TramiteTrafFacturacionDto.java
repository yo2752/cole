package org.gestoresmadrid.oegamComun.trafico.view.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.oegamComun.direccion.view.dto.DireccionDto;
import org.gestoresmadrid.oegamComun.persona.view.dto.PersonaDto;

import utilidades.estructuras.Fecha;

public class TramiteTrafFacturacionDto implements Serializable {

	private static final long serialVersionUID = -6635100150972910082L;

	private BigDecimal numExpediente;

	private String nif;

	private String codTasa;

	private String tipoTasa;

	private String tipoTramite;

	private String matricula;

	private String bastidor;

	private Boolean generado;

	private PersonaDto persona;

	private DireccionDto direccion;

	private String numColegiado;

	private Fecha fechaAplicacion;

	private Long idContrato;

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public String getCodTasa() {
		return codTasa;
	}

	public void setCodTasa(String codTasa) {
		this.codTasa = codTasa;
	}

	public String getTipoTramite() {
		return tipoTramite;
	}

	public void setTipoTramite(String tipoTramite) {
		this.tipoTramite = tipoTramite;
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

	public Boolean getGenerado() {
		return generado;
	}

	public void setGenerado(Boolean generado) {
		this.generado = generado;
	}

	public PersonaDto getPersona() {
		return persona;
	}

	public void setPersona(PersonaDto persona) {
		this.persona = persona;
	}

	public DireccionDto getDireccion() {
		return direccion;
	}

	public void setDireccion(DireccionDto direccion) {
		this.direccion = direccion;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public Fecha getFechaAplicacion() {
		return fechaAplicacion;
	}

	public void setFechaAplicacion(Fecha fechaAplicacion) {
		this.fechaAplicacion = fechaAplicacion;
	}

	public Long getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Long idContrato) {
		this.idContrato = idContrato;
	}

	public String getTipoTasa() {
		return tipoTasa;
	}

	public void setTipoTasa(String tipoTasa) {
		this.tipoTasa = tipoTasa;
	}
}
