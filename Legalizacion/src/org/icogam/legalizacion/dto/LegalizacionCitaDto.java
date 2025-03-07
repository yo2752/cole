package org.icogam.legalizacion.dto;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import utilidades.estructuras.Fecha;
import utilidades.estructuras.FechaFraccionada;

@XStreamAlias("Legalizacion")
public class LegalizacionCitaDto {

	private Integer idPeticion;
	private Fecha fecha;
	private Fecha fechaLegalizacion;
	private Fecha fechaDocumentacion;
	private FechaFraccionada fechaFiltradoAlta;
	private FechaFraccionada fechaFiltradoLegalizacion;
	private Boolean ficheroAdjunto;
	private String nombre;
	private String numColegiado;
	private String referencia;
	private Boolean solicitado;
	private String tipoDocumento;
	private Integer estado;
	private Integer estadoPeticion;
	private Integer orden;
	private String claseDocumento;
	private String pais;
	
	public FechaFraccionada getFechaFiltradoAlta() {
		return fechaFiltradoAlta;
	}

	public void setFechaFiltradoAlta(FechaFraccionada fechaFiltradoAlta) {
		this.fechaFiltradoAlta = fechaFiltradoAlta;
	}

	public FechaFraccionada getFechaFiltradoLegalizacion() {
		return fechaFiltradoLegalizacion;
	}

	public void setFechaFiltradoLegalizacion(
			FechaFraccionada fechaFiltradoLegalizacion) {
		this.fechaFiltradoLegalizacion = fechaFiltradoLegalizacion;
	}

	public LegalizacionCitaDto(){
		this.fechaFiltradoLegalizacion = new FechaFraccionada();
	}
	
	public Fecha getFecha() {
		return fecha;
	}
	public void setFecha(Fecha fecha) {
		this.fecha = fecha;
	}
	public Boolean isFicheroAdjunto() {
		return ficheroAdjunto;
	}
	public void setFicheroAdjunto(Boolean ficheroAdjunto) {
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
	public Boolean isSolicitado() {
		return solicitado;
	}
	public void setSolicitado(Boolean solicitado) {
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
