package org.gestoresmadrid.oegam2comun.datosSensibles.views.beans;



/**
 * Bean que almacena datos de la tabla ESCRITURA
 *
 */

public class DatosSensiblesAgrupados{

	private String tipo;
	private String campo;
	private String fecha;
	private String apellidosNombre;
	private String descGrupo;
	private String idGrupo;
	private String tiempoRestauracion;
	private String tipoControl;
	private String estado;
	
	public DatosSensiblesAgrupados() {
		
	}
	
	public String getCampo() {
		return campo;
	}

	public void setCampo(String campo) {
		this.campo = campo;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}	

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public String getApellidosNombre() {
		return apellidosNombre;
	}

	public void setApellidosNombre(String apellidosNombre) {
		this.apellidosNombre = apellidosNombre;
	}

	public String getDescGrupo() {
		return descGrupo;
	}

	public void setDescGrupo(String descGrupo) {
		this.descGrupo = descGrupo;
	}

	public String getTiempoRestauracion() {
		return tiempoRestauracion;
	}

	public void setTiempoRestauracion(String tiempoRestauracion) {
		this.tiempoRestauracion = tiempoRestauracion;
	}

	public String getIdGrupo() {
		return idGrupo;
	}

	public void setIdGrupo(String idGrupo) {
		this.idGrupo = idGrupo;
	}

	public String getTipoControl() {
		return tipoControl;
	}

	public void setTipoControl(String tipoControl) {
		this.tipoControl = tipoControl;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
}
