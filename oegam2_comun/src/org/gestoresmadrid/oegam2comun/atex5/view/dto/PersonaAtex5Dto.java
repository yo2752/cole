package org.gestoresmadrid.oegam2comun.atex5.view.dto;

import java.io.Serializable;
import java.util.List;

public class PersonaAtex5Dto implements Serializable{

	private static final long serialVersionUID = -8511160897535328830L;
	
	private DatosPersonaAtex5Dto personaFisicaDto;
	private DatosPersonaAtex5Dto personaJuridicaDto;
	private List<DatosPermisoAtex5Dto> listaPermisos;
	private List<DatosMatriculaPersonaAtex5Dto> listaMatriculas;
	private List<DatosSancionPersonaAtex5Dto> listaSanciones;
	
	public DatosPersonaAtex5Dto getPersonaFisicaDto() {
		return personaFisicaDto;
	}
	public void setPersonaFisicaDto(DatosPersonaAtex5Dto personaFisicaDto) {
		this.personaFisicaDto = personaFisicaDto;
	}
	public DatosPersonaAtex5Dto getPersonaJuridicaDto() {
		return personaJuridicaDto;
	}
	public void setPersonaJuridicaDto(DatosPersonaAtex5Dto personaJuridicaDto) {
		this.personaJuridicaDto = personaJuridicaDto;
	}
	public List<DatosPermisoAtex5Dto> getListaPermisos() {
		return listaPermisos;
	}
	public void setListaPermisos(List<DatosPermisoAtex5Dto> listaPermisos) {
		this.listaPermisos = listaPermisos;
	}
	public List<DatosMatriculaPersonaAtex5Dto> getListaMatriculas() {
		return listaMatriculas;
	}
	public void setListaMatriculas(List<DatosMatriculaPersonaAtex5Dto> listaMatriculas) {
		this.listaMatriculas = listaMatriculas;
	}
	public List<DatosSancionPersonaAtex5Dto> getListaSanciones() {
		return listaSanciones;
	}
	public void setListaSanciones(List<DatosSancionPersonaAtex5Dto> listaSanciones) {
		this.listaSanciones = listaSanciones;
	}
	
}
