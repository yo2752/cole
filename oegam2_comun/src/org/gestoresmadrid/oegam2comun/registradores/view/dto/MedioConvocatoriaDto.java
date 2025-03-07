package org.gestoresmadrid.oegam2comun.registradores.view.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import utilidades.estructuras.Fecha;

public class MedioConvocatoriaDto implements Serializable {

	private static final long serialVersionUID = -7959259920599612878L;

	private Long idMedio;

	private BigDecimal idTramiteRegistro;

	private Long idReunion;

	private Fecha fechaPublicacion;

	private MedioDto medio;

	public Long getIdMedio() {
		return idMedio;
	}

	public void setIdMedio(Long idMedio) {
		this.idMedio = idMedio;
	}

	public BigDecimal getIdTramiteRegistro() {
		return idTramiteRegistro;
	}

	public void setIdTramiteRegistro(BigDecimal idTramiteRegistro) {
		this.idTramiteRegistro = idTramiteRegistro;
	}

	public Long getIdReunion() {
		return idReunion;
	}

	public void setIdReunion(Long idReunion) {
		this.idReunion = idReunion;
	}

	public Fecha getFechaPublicacion() {
		return fechaPublicacion;
	}

	public void setFechaPublicacion(Fecha fechaPublicacion) {
		this.fechaPublicacion = fechaPublicacion;
	}

	public MedioDto getMedio() {
		return medio;
	}

	public void setMedio(MedioDto medio) {
		this.medio = medio;
	}
}