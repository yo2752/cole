package org.gestoresmadrid.oegam2comun.atex5.model.service.impl;

import java.io.Serializable;

import org.gestoresmadrid.oegam2comun.atex5.view.dto.DatosDomicilioAtex5Dto;
import org.gestoresmadrid.oegam2comun.atex5.view.dto.DatosMatriculaPersonaAtex5Dto;
import org.gestoresmadrid.oegam2comun.atex5.view.dto.DatosPermisoAtex5Dto;
import org.gestoresmadrid.oegam2comun.atex5.view.dto.DatosPersonaAtex5Dto;
import org.gestoresmadrid.oegam2comun.atex5.view.dto.DatosSancionPersonaAtex5Dto;
import org.gestoresmadrid.oegam2comun.atex5.view.dto.PersonaAtex5Dto;
import org.gestoresmadrid.oegam2comun.atex5.xml.persona.Persona;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BuildPersonaAtex5 implements Serializable{

	private static final long serialVersionUID = -2655061848254329065L;

	@Autowired
	Conversor conversor;
	
	public PersonaAtex5Dto convertirPersonaXmlToPersonaAtex5Dto(Persona personaAtex5) {
		PersonaAtex5Dto personaAtex5Dto = new PersonaAtex5Dto();
		rellenarDatosPersona(personaAtex5Dto, personaAtex5);
		rellenarListaPermisos(personaAtex5Dto, personaAtex5);
		rellenarListaMatriculas(personaAtex5Dto, personaAtex5);
		rellenarListaSanciones(personaAtex5Dto,personaAtex5);
		return personaAtex5Dto;
	}
	
	private void rellenarListaSanciones(PersonaAtex5Dto personaAtex5Dto, Persona personaAtex5) {
		if(personaAtex5.getListaSanciones() != null && personaAtex5.getListaSanciones().getDatosSanciones() != null
			&& !personaAtex5.getListaSanciones().getDatosSanciones().isEmpty()){
			personaAtex5Dto.setListaSanciones(conversor.transform(personaAtex5.getListaSanciones().getDatosSanciones(), DatosSancionPersonaAtex5Dto.class));
		}
	}

	private void rellenarListaMatriculas(PersonaAtex5Dto personaAtex5Dto, Persona personaAtex5) {
		if(personaAtex5.getDatosGenerales() != null && personaAtex5.getDatosGenerales().getListaVehiculos() != null
			&& personaAtex5.getDatosGenerales().getListaVehiculos().getValue() != null
			&& personaAtex5.getDatosGenerales().getListaVehiculos().getValue().getMatricula() != null 
			&& !personaAtex5.getDatosGenerales().getListaVehiculos().getValue().getMatricula().isEmpty()){
			personaAtex5Dto.setListaMatriculas(conversor.transform(personaAtex5.getDatosGenerales().getListaVehiculos().getValue().getMatricula(), DatosMatriculaPersonaAtex5Dto.class));
		}
	}

	private void rellenarDatosPersona(PersonaAtex5Dto personaAtex5Dto, Persona personaAtex5) {
		if(personaAtex5.getDatosPersona() != null){
			if(personaAtex5.getDatosPersona().getPersonaFisica() != null 
					&& personaAtex5.getDatosPersona().getPersonaFisica().getIdDocumento() != null 
					&& !personaAtex5.getDatosPersona().getPersonaFisica().getIdDocumento().isEmpty()){
				personaAtex5Dto.setPersonaFisicaDto(conversor.transform(personaAtex5.getDatosPersona().getPersonaFisica(), DatosPersonaAtex5Dto.class));
				if(personaAtex5Dto.getPersonaFisicaDto() != null && personaAtex5Dto.getPersonaFisicaDto().getNif() != null
					&& !personaAtex5Dto.getPersonaFisicaDto().getNif().isEmpty()){
					if(personaAtex5.getDatosGenerales() != null){
						if(personaAtex5.getDatosGenerales().getDomicilio() != null){
							personaAtex5Dto.getPersonaFisicaDto().setDomicilio(conversor.transform(personaAtex5.getDatosGenerales().getDomicilio(), DatosDomicilioAtex5Dto.class));
						}
						if (personaAtex5.getDatosGenerales().getDomicilioIne() != null) {
							personaAtex5Dto.getPersonaFisicaDto().setDomicilioIne(conversor.transform(personaAtex5.getDatosGenerales().getDomicilioIne(), DatosDomicilioAtex5Dto.class));
						}
					}
				} 
			} else if(personaAtex5.getDatosPersona().getPersonaJuridica() != null 
				&& personaAtex5.getDatosPersona().getPersonaJuridica().getCif() != null
				&& !personaAtex5.getDatosPersona().getPersonaJuridica().getCif().isEmpty()) {
				personaAtex5Dto.setPersonaJuridicaDto(conversor.transform(personaAtex5.getDatosPersona().getPersonaJuridica(), DatosPersonaAtex5Dto.class));
				if(personaAtex5Dto.getPersonaJuridicaDto() != null && personaAtex5Dto.getPersonaJuridicaDto().getCif() != null
					&& !personaAtex5Dto.getPersonaJuridicaDto().getCif().isEmpty()){
					if(personaAtex5.getDatosGenerales() != null){
						if(personaAtex5.getDatosGenerales().getDomicilio() != null){
							personaAtex5Dto.getPersonaJuridicaDto().setDomicilio(conversor.transform(personaAtex5.getDatosGenerales().getDomicilio(), DatosDomicilioAtex5Dto.class));
						}
						if (personaAtex5.getDatosGenerales().getDomicilioIne() != null) {
							personaAtex5Dto.getPersonaJuridicaDto().setDomicilioIne(conversor.transform(personaAtex5.getDatosGenerales().getDomicilioIne(), DatosDomicilioAtex5Dto.class));
						}
					}
				}
			}
		}
	}

	private void rellenarListaPermisos(PersonaAtex5Dto personaAtex5Dto, Persona personaAtex5) {
		if(personaAtex5.getDatosGenerales() != null && personaAtex5.getDatosGenerales().getListaPermisosVigentes() != null
			&& personaAtex5.getDatosGenerales().getListaPermisosVigentes().getValue() != null
			&& personaAtex5.getDatosGenerales().getListaPermisosVigentes().getValue().getPermiso() != null 
			&& !personaAtex5.getDatosGenerales().getListaPermisosVigentes().getValue().getPermiso().isEmpty()){
			personaAtex5Dto.setListaPermisos(conversor.transform(personaAtex5.getDatosGenerales().getListaPermisosVigentes().getValue().getPermiso(), DatosPermisoAtex5Dto.class));
		}
	}
}
