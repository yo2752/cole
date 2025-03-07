package org.icogam.sanciones.DTO;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import utilidades.estructuras.Fecha;



@XStreamAlias("Sancion")
public class SancionDto{

	private Integer idSancion;
	private Fecha fechaPresentacion;
	private Fecha fechaAlta;
	private String nombre;
	private String apellidos;
	private String dni;
	private String numColegiado;
	private Integer motivo;
	private Integer estado;
	private Integer estadoSancion;
	private String motivoDescripcion;
	
	public SancionDto() {
	}
	
	public Integer getIdSancion() {
		return idSancion;
	}
	
	public void setIdSancion(Integer idSancion) {
		this.idSancion = idSancion;
	}
	
	public Fecha getFechaPresentacion() {
		return fechaPresentacion;
	}
	
	public void setFechaPresentacion(Fecha fechaPresentacion) {
		this.fechaPresentacion = fechaPresentacion;
	}
	
	public Fecha getFechaAlta() {
		return fechaAlta;
	}
	
	public void setFechaAlta(Fecha fechaAlta) {
		this.fechaAlta = fechaAlta;
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
	
	public Integer getEstado() {
		return estado;
	}
	
	public void setEstado(Integer estado) {
		this.estado = estado;
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

	public String getMotivoDescripcion() {
		return motivoDescripcion;
	}

	public void setMotivoDescripcion(String motivoDescripcion) {
		this.motivoDescripcion = motivoDescripcion;
	}

}
