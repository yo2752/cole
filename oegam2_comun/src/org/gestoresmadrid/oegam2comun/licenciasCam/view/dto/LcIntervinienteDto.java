package org.gestoresmadrid.oegam2comun.licenciasCam.view.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class LcIntervinienteDto implements Serializable {

	private static final long serialVersionUID = -7578587022973784123L;

	private Long idInterviniente;

	private String tipoInterviniente;

	private String numColegiado;

	private BigDecimal numExpediente;

	private Long idDireccion;

	private Long idPersona;

	private LcPersonaDto lcPersona;

	private LcDireccionDto lcDireccion;

	public LcIntervinienteDto() {}

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

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

	public Long getIdDireccion() {
		return idDireccion;
	}

	public void setIdDireccion(Long idDireccion) {
		this.idDireccion = idDireccion;
	}

	public Long getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(Long idPersona) {
		this.idPersona = idPersona;
	}

	public LcPersonaDto getLcPersona() {
		return lcPersona;
	}

	public void setLcPersona(LcPersonaDto lcPersona) {
		this.lcPersona = lcPersona;
	}

	public LcDireccionDto getLcDireccion() {
		return lcDireccion;
	}

	public void setLcDireccion(LcDireccionDto lcDireccion) {
		this.lcDireccion = lcDireccion;
	}

}