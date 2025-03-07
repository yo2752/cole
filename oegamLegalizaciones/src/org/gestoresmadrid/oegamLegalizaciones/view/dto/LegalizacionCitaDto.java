package org.gestoresmadrid.oegamLegalizaciones.view.dto;

import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import utilidades.estructuras.Fecha;

@XStreamAlias("Legalizacion")
public class LegalizacionCitaDto {

	private Integer idPeticion;
	private Fecha fecha;
	private Fecha fechaLegalizacion;
	private Fecha fechaDocumentacion;
	private int ficheroAdjunto;
	private String nombre;
	private String numColegiado;
	private String referencia;
	private int solicitado;
	private String tipoDocumento;
	private Integer estado;
	private Integer estadoPeticion;
	private Integer orden;
	private String claseDocumento;
	private String pais;
	private ContratoDto contrato;
	private Integer idContrato;

	public Fecha getFecha() {
		return fecha;
	}

	public void setFecha(Fecha fecha) {
		this.fecha = fecha;
	}

	public int getFicheroAdjunto() {
		return ficheroAdjunto;
	}

	public void setFicheroAdjunto(int ficheroAdjunto) {
		this.ficheroAdjunto = ficheroAdjunto;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public int getSolicitado() {
		return solicitado;
	}

	public void setSolicitado(int solicitado) {
		this.solicitado = solicitado;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public Integer getIdPeticion() {
		return idPeticion;
	}

	public void setIdPeticion(Integer idPeticion) {
		this.idPeticion = idPeticion;
	}

	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	public Fecha getFechaLegalizacion() {
		return fechaLegalizacion;
	}

	public void setFechaLegalizacion(Fecha fechaLegalizacion) {
		this.fechaLegalizacion = fechaLegalizacion;
	}

	public Fecha getFechaDocumentacion() {
		return fechaDocumentacion;
	}

	public void setFechaDocumentacion(Fecha fechaDocumentacion) {
		this.fechaDocumentacion = fechaDocumentacion;

	}

	public Integer getEstadoPeticion() {
		return estadoPeticion;
	}

	public void setEstadoPeticion(Integer estadoPeticion) {
		this.estadoPeticion = estadoPeticion;
	}

	public Integer getOrden() {
		return orden;
	}

	public void setOrden(Integer orden) {
		this.orden = orden;
	}

	public ContratoDto getContrato() {
		return contrato;
	}

	public void setContrato(ContratoDto contrato) {
		this.contrato = contrato;
	}

	public Integer getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Integer idContrato) {
		this.idContrato = idContrato;
	}

	public String getClaseDocumento() {
		return claseDocumento;
	}

	public void setClaseDocumento(String claseDocumento) {
		this.claseDocumento = claseDocumento;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

}
