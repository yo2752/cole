package org.gestoresmadrid.oegam2comun.atex5.view.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;
import org.gestoresmadrid.oegamComun.tasa.view.dto.TasaDto;

import utilidades.estructuras.Fecha;

public class ConsultaPersonaAtex5Dto implements Serializable{

	private static final long serialVersionUID = -2168994077198517989L;

	private Long idConsultaPersonaAtex5;
	
	private String nif;
	
	private String numColegiado;
	
	private BigDecimal numExpediente;
	
	private String apellido1;
	
	private String razonSocial;
	
	private String apellido2;
	
	private String nombre;
	
	private String anioNacimiento;
	
	private Fecha fechaNacimiento;
	
	private String estado;
	
	private Date fechaAlta;
	
	private String respuesta;

	private ContratoDto contrato;
	
	private TasaDto tasa;
	
	private PersonaAtex5Dto personaAtex5;
	
	public Long getIdConsultaPersonaAtex5() {
		return idConsultaPersonaAtex5;
	}

	public void setIdConsultaPersonaAtex5(Long idConsultaPersonaAtex5) {
		this.idConsultaPersonaAtex5 = idConsultaPersonaAtex5;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public String getApellido1() {
		return apellido1;
	}

	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public String getApellido2() {
		return apellido2;
	}

	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getAnioNacimiento() {
		return anioNacimiento;
	}

	public void setAnioNacimiento(String anioNacimiento) {
		this.anioNacimiento = anioNacimiento;
	}

	public Fecha getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Fecha fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
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

	public PersonaAtex5Dto getPersonaAtex5() {
		return personaAtex5;
	}

	public void setPersonaAtex5(PersonaAtex5Dto personaAtex5) {
		this.personaAtex5 = personaAtex5;
	}

}
