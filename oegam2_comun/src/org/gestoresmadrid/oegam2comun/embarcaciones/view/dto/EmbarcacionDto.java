package org.gestoresmadrid.oegam2comun.embarcaciones.view.dto;

import org.gestoresmadrid.oegamComun.persona.view.dto.PersonaDto;

public class EmbarcacionDto {

	private PersonaDto titular;
	private PersonaDto representante;
	private String hin;
	private String metrosEslora;

	/**
	 * @return the titular
	 */
	public PersonaDto getTitular() {
		return titular;
	}

	/**
	 * @param titular the titular to set
	 */
	public void setTitular(PersonaDto titular) {
		this.titular = titular;
	}
	
	/**
	 * @return the representante
	 */
	public PersonaDto getRepresentante() {
		return representante;
	}
	
	/**
	 * @param representante the representante to set
	 */
	public void setRepresentante(PersonaDto representante) {
		this.representante = representante;
	}
	
	/**
	 * @return the hin
	 */
	public String getHin() {
		return hin;
	}
	
	/**
	 * @param hin the hin to set
	 */
	public void setHin(String hin) {
		this.hin = hin;
	}
	
	/**
	 * @return the metrosEslora
	 */
	public String getMetrosEslora() {
		return metrosEslora;
	}
	
	/**
	 * @param metrosEslora the metrosEslora to set
	 */
	public void setMetrosEslora(String metrosEslora) {
		this.metrosEslora = metrosEslora;
	}
	

}
