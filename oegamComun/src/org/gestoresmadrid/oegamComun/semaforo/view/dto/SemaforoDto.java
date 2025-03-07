package org.gestoresmadrid.oegamComun.semaforo.view.dto;

import java.io.Serializable;

public class SemaforoDto implements Serializable {

	private static final long serialVersionUID = -2645755573907721059L;

	private Long idSemaforo;

	private Integer estado;

	private String nodo;

	private String proceso;

	private EvolucionSemaforoDto evolucionSemaforoDto;

	public SemaforoDto() {
	}

	public Long getIdSemaforo() {
		return this.idSemaforo;
	}

	public void setIdSemaforo(Long idSemaforo) {
		this.idSemaforo = idSemaforo;
	}

	public Integer getEstado() {
		return this.estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	public String getNodo() {
		return this.nodo;
	}

	public void setNodo(String nodo) {
		this.nodo = nodo;
	}

	public String getProceso() {
		return this.proceso;
	}

	public void setProceso(String proceso) {
		this.proceso = proceso;
	}

	public EvolucionSemaforoDto getEvolucionSemaforoDto() {
		return evolucionSemaforoDto;
	}

	public void setEvolucionSemaforoDto(EvolucionSemaforoDto evolucionSemaforoDto) {
		this.evolucionSemaforoDto = evolucionSemaforoDto;
	}

}