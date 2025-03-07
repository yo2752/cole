package org.icogam.sanciones.DTO;

import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import general.beans.BeanCriteriosSkeletonPaginatedList;
import utilidades.estructuras.FechaFraccionada;


public class BeanCriteriosSancion implements BeanCriteriosSkeletonPaginatedList{


	private FechaFraccionada fechaFiltradoPresentacion;
	private String nombre;
	private String apellidos;
	private String dni;
	private String numColegiado;
	private Integer motivo;
	private Integer estadoSancion;
	
	@Autowired
	UtilesFecha utilesFecha;

	public BeanCriteriosSancion() {
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}
	
	public FechaFraccionada getFechaFiltradoPresentacion() {
		return fechaFiltradoPresentacion;
	}
	
	public void setFechaFiltradoPresentacion(
			FechaFraccionada fechaFiltradoPresentacion) {
		this.fechaFiltradoPresentacion = fechaFiltradoPresentacion;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getApellidos() {
		return apellidos;
	}
	
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	
	public Integer getMotivo() {
		return motivo;
	}
	
	public void setMotivo(Integer motivo) {
		this.motivo = motivo;
	}
	
	public Integer getEstadoSancion() {
		return estadoSancion;
	}
	
	public void setEstadoSancion(Integer estadoSancion) {
		this.estadoSancion = estadoSancion;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	@Override
	public BeanCriteriosSkeletonPaginatedList iniciarBean() {
		fechaFiltradoPresentacion = utilesFecha.getFechaFracionadaActual();
		return this;
	}
}
