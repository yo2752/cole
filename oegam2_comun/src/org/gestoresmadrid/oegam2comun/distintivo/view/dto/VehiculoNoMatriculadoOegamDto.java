package org.gestoresmadrid.oegam2comun.distintivo.view.dto;

import java.io.Serializable;

import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;

import utilidades.estructuras.Fecha;

public class VehiculoNoMatriculadoOegamDto implements Serializable {

	private static final long serialVersionUID = 7974291149526843559L;

	private Long id;

	private String matricula;
	
	private String nive;
	
	private String bastidor;
	
	private String numColegiado;
	
	private ContratoDto contrato;
	
	private Fecha fechaSolicitud;

	private Fecha fechaImpresion;
	
	private Fecha fechaAlta;
	
	private String tipoDistintivo;
	
	private Long docDistintivo;
	
	private String estadoSolicitud;
	
	private String estadoImpresion;
	
	private String respuestaSol;
	
	private String primeraImpresion;
	
	public VehiculoNoMatriculadoOegamDto() {
		super();
	}

	public VehiculoNoMatriculadoOegamDto(Long id) {
		super();
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public Fecha getFechaSolicitud() {
		return fechaSolicitud;
	}

	public void setFechaSolicitud(Fecha fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}

	public Fecha getFechaImpresion() {
		return fechaImpresion;
	}

	public void setFechaImpresion(Fecha fechaImpresion) {
		this.fechaImpresion = fechaImpresion;
	}

	public String getEstadoSolicitud() {
		return estadoSolicitud;
	}

	public void setEstadoSolicitud(String estadoSolicitud) {
		this.estadoSolicitud = estadoSolicitud;
	}

	public String getEstadoImpresion() {
		return estadoImpresion;
	}

	public void setEstadoImpresion(String estadoImpresion) {
		this.estadoImpresion = estadoImpresion;
	}

	public ContratoDto getContrato() {
		return contrato;
	}

	public void setContrato(ContratoDto contrato) {
		this.contrato = contrato;
	}

	public String getNive() {
		return nive;
	}

	public void setNive(String nive) {
		this.nive = nive;
	}

	public String getBastidor() {
		return bastidor;
	}

	public void setBastidor(String bastidor) {
		this.bastidor = bastidor;
	}

	public Fecha getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Fecha fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public String getTipoDistintivo() {
		return tipoDistintivo;
	}

	public void setTipoDistintivo(String tipoDistintivo) {
		this.tipoDistintivo = tipoDistintivo;
	}

	public Long getDocDistintivo() {
		return docDistintivo;
	}

	public void setDocDistintivo(Long docDistintivo) {
		this.docDistintivo = docDistintivo;
	}

	public String getRespuestaSol() {
		return respuestaSol;
	}

	public void setRespuestaSol(String respuestaSol) {
		this.respuestaSol = respuestaSol;
	}

	public String getPrimeraImpresion() {
		return primeraImpresion;
	}

	public void setPrimeraImpresion(String primeraImpresion) {
		this.primeraImpresion = primeraImpresion;
	}

}
