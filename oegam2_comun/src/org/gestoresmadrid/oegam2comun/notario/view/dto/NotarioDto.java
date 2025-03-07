package org.gestoresmadrid.oegam2comun.notario.view.dto;

import java.io.Serializable;

import org.gestoresmadrid.oegamComun.persona.view.dto.PersonaDto;

public class NotarioDto implements Serializable{

	private static final long serialVersionUID = 7352392681890340369L;

	private String codigoNotario;
	private String codigoNotaria;
	private String nombre;
	private String apellidos;
	private PersonaDto persona;
	
	
	public String getCodigoNotario() {
		return codigoNotario;
	}
	public void setCodigoNotario(String codigoNotario) {
		this.codigoNotario = codigoNotario;
	}
	public String getCodigoNotaria() {
		return codigoNotaria;
	}
	public void setCodigoNotaria(String codigoNotaria) {
		this.codigoNotaria = codigoNotaria;
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return codigoNotario + "_" + codigoNotaria + "_" + nombre + "_" + apellidos;
	}
	public PersonaDto getPersona() {
		return persona;
	}
	public void setPersona(PersonaDto persona) {
		this.persona = persona;
	}
	
}
