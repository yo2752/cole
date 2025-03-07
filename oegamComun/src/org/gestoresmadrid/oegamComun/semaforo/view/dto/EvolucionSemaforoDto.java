package org.gestoresmadrid.oegamComun.semaforo.view.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import utilidades.estructuras.Fecha;

/**
 * The persistent class for the EVOLUCION_SEMAFORO database table.
 * 
 */
public class EvolucionSemaforoDto implements Serializable {
	private static final long serialVersionUID = 1L;

	private long id;

	private Fecha fecha;

	private BigDecimal idUsuario;

	private String operacion;

	private SemaforoDto semaforo;

	public EvolucionSemaforoDto() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Fecha getFecha() {
		return this.fecha;
	}

	public void setFecha(Fecha fecha) {
		this.fecha = fecha;
	}

	public BigDecimal getIdUsuario() {
		return this.idUsuario;
	}

	public void setIdUsuario(BigDecimal idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getOperacion() {
		return this.operacion;
	}

	public void setOperacion(String operacion) {
		this.operacion = operacion;
	}

	public SemaforoDto getSemaforo() {
		return this.semaforo;
	}

	public void setSemaforo(SemaforoDto semaforo) {
		this.semaforo = semaforo;
	}

}