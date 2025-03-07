package org.gestoresmadrid.oegam2comun.participacion.view.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.oegam2comun.bien.view.dto.BienDto;
import org.gestoresmadrid.oegam2comun.intervinienteModelos.view.dto.IntervinienteModelosDto;
import org.gestoresmadrid.oegam2comun.remesa.view.dto.RemesaDto;

public class ParticipacionDto implements Serializable{

	private static final long serialVersionUID = 2519894982746394137L;

	private RemesaDto remesa;
	
	private BienDto bien;
	
	private IntervinienteModelosDto intervinienteModelos;
	
	private BigDecimal porcentaje;
	
	private Long idParticipacion;

	public RemesaDto getRemesa() {
		return remesa;
	}

	public void setRemesa(RemesaDto remesa) {
		this.remesa = remesa;
	}

	public BienDto getBien() {
		return bien;
	}

	public void setBien(BienDto bien) {
		this.bien = bien;
	}

	public BigDecimal getPorcentaje() {
		return porcentaje;
	}

	public void setPorcentaje(BigDecimal porcentaje) {
		this.porcentaje = porcentaje;
	}

	public IntervinienteModelosDto getIntervinienteModelos() {
		return intervinienteModelos;
	}

	public void setIntervinienteModelos(IntervinienteModelosDto intervinienteModelos) {
		this.intervinienteModelos = intervinienteModelos;
	}

	public Long getIdParticipacion() {
		return idParticipacion;
	}

	public void setIdParticipacion(Long idParticipacion) {
		this.idParticipacion = idParticipacion;
	}
	
}
