package org.gestoresmadrid.oegam2comun.intervinienteModelos.view.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.oegam2comun.modelo600_601.view.dto.Modelo600_601Dto;
import org.gestoresmadrid.oegam2comun.remesa.view.dto.RemesaDto;
import org.gestoresmadrid.oegamComun.direccion.view.dto.DireccionDto;
import org.gestoresmadrid.oegamComun.persona.view.dto.PersonaDto;

public class IntervinienteModelosDto implements Serializable{
	
	private static final long serialVersionUID = 2923108688508314861L;

	private RemesaDto remesa;
	
	private Modelo600_601Dto modelo600_601;
	
	private PersonaDto persona;
	
	private String tipoInterviniente;
	
	private Long idInterviniente;
	
	private DireccionDto direccion;
	
	private BigDecimal porcentaje;


	public Modelo600_601Dto getModelo600_601() {
		return modelo600_601;
	}

	public void setModelo600_601(Modelo600_601Dto modelo600_601) {
		this.modelo600_601 = modelo600_601;
	}

	public PersonaDto getPersona() {
		return persona;
	}

	public void setPersona(PersonaDto persona) {
		this.persona = persona;
	}

	public RemesaDto getRemesa() {
		return remesa;
	}

	public void setRemesa(RemesaDto remesa) {
		this.remesa = remesa;
	}

	public Long getIdInterviniente() {
		return idInterviniente;
	}

	public void setIdInterviniente(Long idInterviniente) {
		this.idInterviniente = idInterviniente;
	}

	public String getTipoInterviniente() {
		return tipoInterviniente;
	}

	public void setTipoInterviniente(String tipoInterviniente) {
		this.tipoInterviniente = tipoInterviniente;
	}

	public DireccionDto getDireccion() {
		return direccion;
	}

	public void setDireccion(DireccionDto direccion) {
		this.direccion = direccion;
	}

	public BigDecimal getPorcentaje() {
		return porcentaje;
	}

	public void setPorcentaje(BigDecimal porcentaje) {
		this.porcentaje = porcentaje;
	}

}
