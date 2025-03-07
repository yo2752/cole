package org.gestoresmadrid.oegamComun.persona.view.bean;

import java.io.Serializable;

import org.gestoresmadrid.core.direccion.model.vo.DireccionVO;
import org.gestoresmadrid.core.personas.model.vo.EvolucionPersonaVO;
import org.gestoresmadrid.core.personas.model.vo.PersonaDireccionVO;
import org.gestoresmadrid.core.personas.model.vo.PersonaVO;

public class ResultadoPersonaBean implements Serializable{

	public ResultadoPersonaBean(Boolean error) {
		super();
		this.error = error;
		this.guardarDir = Boolean.FALSE;
		this.nuevaDireccion = Boolean.FALSE;
	}
	private static final long serialVersionUID = -4861735387849480765L;
	
	Boolean error;
	String mensaje;
	Boolean guardarDir;
	EvolucionPersonaVO evolucionPersona;
	DireccionVO direccion;
	PersonaDireccionVO personaDirPrincipalAnt;
	PersonaDireccionVO personaDirPrincipalNue;
	PersonaVO persona;
	PersonaDireccionVO personaDireccion;
	Boolean nuevaDireccion;
	
	public Boolean getError() {
		return error;
	}
	public void setError(Boolean error) {
		this.error = error;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public EvolucionPersonaVO getEvolucionPersona() {
		return evolucionPersona;
	}
	public void setEvolucionPersona(EvolucionPersonaVO evolucionPersona) {
		this.evolucionPersona = evolucionPersona;
	}
	public DireccionVO getDireccion() {
		return direccion;
	}
	public void setDireccion(DireccionVO direccion) {
		this.direccion = direccion;
	}
	public PersonaDireccionVO getPersonaDirPrincipalAnt() {
		return personaDirPrincipalAnt;
	}
	public void setPersonaDirPrincipalAnt(PersonaDireccionVO personaDirPrincipalAnt) {
		this.personaDirPrincipalAnt = personaDirPrincipalAnt;
	}
	public PersonaDireccionVO getPersonaDirPrincipalNue() {
		return personaDirPrincipalNue;
	}
	public void setPersonaDirPrincipalNue(PersonaDireccionVO personaDirPrincipalNue) {
		this.personaDirPrincipalNue = personaDirPrincipalNue;
	}
	public PersonaVO getPersona() {
		return persona;
	}
	public void setPersona(PersonaVO persona) {
		this.persona = persona;
	}
	public Boolean getGuardarDir() {
		return guardarDir;
	}
	public void setGuardarDir(Boolean guardarDir) {
		this.guardarDir = guardarDir;
	}
	public PersonaDireccionVO getPersonaDireccion() {
		return personaDireccion;
	}
	public void setPersonaDireccion(PersonaDireccionVO personaDireccion) {
		this.personaDireccion = personaDireccion;
	}
	public Boolean getNuevaDireccion() {
		return nuevaDireccion;
	}
	public void setNuevaDireccion(Boolean nuevaDireccion) {
		this.nuevaDireccion = nuevaDireccion;
	}
}
