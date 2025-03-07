package org.icogam.legalizacion.beans.paginacion;

import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import general.beans.BeanCriteriosSkeletonPaginatedList;
import utilidades.estructuras.FechaFraccionada;

public class LegalizacionBean implements BeanCriteriosSkeletonPaginatedList{

	private FechaFraccionada fechaFiltradoLegalizacion;
	private String ficheroAdjunto;
	private String nombre;
	private String numColegiado;
	private String referencia;
	private String solicitado;
	private String tipoDocumento;
	private Integer estadoPeticion;
	private String estado;
	private String claseDocumento;
	private String pais;
	
	@Autowired
	private UtilesFecha utilesFecha;

	public LegalizacionBean() {
		super();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	@Override
	public BeanCriteriosSkeletonPaginatedList iniciarBean() {
		fechaFiltradoLegalizacion = utilesFecha.getFechaFracionadaActual();
		return this;
	}



	public FechaFraccionada getFechaFiltradoLegalizacion() {
		return fechaFiltradoLegalizacion;
	}



	public void setFechaFiltradoLegalizacion(
			FechaFraccionada fechaFiltradoLegalizacion) {
		this.fechaFiltradoLegalizacion = fechaFiltradoLegalizacion;
	}



	public String getFicheroAdjunto() {
		return ficheroAdjunto;
	}



	public void setFicheroAdjunto(String ficheroAdjunto) {
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



	public String getSolicitado() {
		return solicitado;
	}



	public void setSolicitado(String solicitado) {
		this.solicitado = solicitado;
	}



	public String getTipoDocumento() {
		return tipoDocumento;
	}



	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}



	public Integer getEstadoPeticion() {
		return estadoPeticion;
	}



	public void setEstadoPeticion(Integer estadoPeticion) {
		this.estadoPeticion = estadoPeticion;
	}



	public String getEstado() {
		return estado;
	}



	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getClaseDocumento() {
		return claseDocumento;
	}

	public void setClaseDocumento(String claseDocumento) {
		this.claseDocumento = claseDocumento;
	}

}
