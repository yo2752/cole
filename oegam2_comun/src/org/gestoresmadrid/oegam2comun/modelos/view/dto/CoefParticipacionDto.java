package org.gestoresmadrid.oegam2comun.modelos.view.dto;

import java.io.Serializable;

import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;
import org.gestoresmadrid.oegam2comun.bien.view.dto.BienDto;
import org.gestoresmadrid.oegamComun.persona.view.dto.PersonaDto;

public class CoefParticipacionDto implements Serializable{

	private static final long serialVersionUID = -1960979692649125946L;
	
	private BienDto bien;
	private Float porcentaje;
	private PersonaDto persona;
	private TipoInterviniente tipoInterviniente;
	
	public BienDto getBien() {
		return bien;
	}
	public void setBien(BienDto bien) {
		this.bien = bien;
	}
	public Float getPorcentaje() {
		return porcentaje;
	}
	public void setPorcentaje(Float porcentaje) {
		this.porcentaje = porcentaje;
	}
	public PersonaDto getPersona() {
		return persona;
	}
	public void setPersona(PersonaDto persona) {
		this.persona = persona;
	}
	public TipoInterviniente getTipoInterviniente() {
		return tipoInterviniente;
	}
	public void setTipoInterviniente(TipoInterviniente tipoInterviniente) {
		this.tipoInterviniente = tipoInterviniente;
	}
	
	
}
