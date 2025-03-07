package trafico.datosSensibles.utiles;

import hibernate.entities.general.Grupo;

/**
 * Bean que almacena datos de la tabla ESCRITURA
 *
 */
public class DatosSensiblesAgrupados {

	private String tipo;
	private String campo;
	private String fecha;
	private String apellidosNombre;
	private Grupo grupoUsuarios;

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

	public Grupo getGrupoUsuarios() {
		return grupoUsuarios;
	}

	public void setGrupoUsuarios(Grupo grupoUsuarios) {
		this.grupoUsuarios = grupoUsuarios;
	}

}