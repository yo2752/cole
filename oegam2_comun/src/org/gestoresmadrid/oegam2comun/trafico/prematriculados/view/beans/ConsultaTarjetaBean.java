package org.gestoresmadrid.oegam2comun.trafico.prematriculados.view.beans;

import org.gestoresmadrid.core.annotations.FilterSimpleExpression;
import org.gestoresmadrid.core.annotations.enumeration.FilterSimpleExpressionRestriction;

import utilidades.estructuras.FechaFraccionada;

public class ConsultaTarjetaBean {

	private Long id;
	
	@FilterSimpleExpression
	private String numColegiado;
	
	@FilterSimpleExpression
	private String nive;
	
	@FilterSimpleExpression
	private String bastidor;
	
	private String respuesta;
	
	@FilterSimpleExpression
	private Integer estadoCaracteristicas;

	@FilterSimpleExpression
	private String respuestaCaracteristicas;

	@FilterSimpleExpression
	private Integer estadoFichaTecnica;

	
	private String respuestaFichaTecnica;
	
	@FilterSimpleExpression(name="fechaAlta", restriction=FilterSimpleExpressionRestriction.BETWEEN)
	private FechaFraccionada fecha;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public String getNive() {
		return nive;
	}

	public void setNive(String nive) {
		this.nive = nive;
	}

	public String getBastidor() {
		return bastidor;
	}

	public void setBastidor(String bastidor) {
		this.bastidor = bastidor;
	}

	public String getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}

	public Integer getEstadoCaracteristicas() {
		return estadoCaracteristicas;
	}

	public void setEstadoCaracteristicas(Integer estadoCaracteristicas) {
		this.estadoCaracteristicas = estadoCaracteristicas;
	}

	public String getRespuestaCaracteristicas() {
		return respuestaCaracteristicas;
	}

	public void setRespuestaCaracteristicas(String respuestaCaracteristicas) {
		this.respuestaCaracteristicas = respuestaCaracteristicas;
	}

	public Integer getEstadoFichaTecnica() {
		return estadoFichaTecnica;
	}

	public void setEstadoFichaTecnica(Integer estadoFichaTecnica) {
		this.estadoFichaTecnica = estadoFichaTecnica;
	}

	public String getRespuestaFichaTecnica() {
		return respuestaFichaTecnica;
	}

	public void setRespuestaFichaTecnica(String respuestaFichaTecnica) {
		this.respuestaFichaTecnica = respuestaFichaTecnica;
	}

	public FechaFraccionada getFecha() {
		return fecha;
	}

	public void setFecha(FechaFraccionada fecha) {
		this.fecha = fecha;
	}
	
}
