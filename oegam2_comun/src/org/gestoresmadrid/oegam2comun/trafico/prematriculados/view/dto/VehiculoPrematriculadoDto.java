package org.gestoresmadrid.oegam2comun.trafico.prematriculados.view.dto;

import java.util.Date;

public class VehiculoPrematriculadoDto {
	
	private Long id;
	private String numColegiado;
	private String nive;
	private String bastidor;
	private Date fechaAlta;
	private Integer estadoCaracteristicas;
	private String respuestaCaracteristicas;
	private Integer estadoFichaTecnica;
	private String respuestaFichaTecnica;
	
	private boolean datosTecnicos;
	private boolean fichaTecnica;
	
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
	public Date getFechaAlta() {
		return fechaAlta;
	}
	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
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
	public boolean isDatosTecnicos() {
		return datosTecnicos;
	}
	public void setDatosTecnicos(boolean datosTecnicos) {
		this.datosTecnicos = datosTecnicos;
	}
	public boolean isFichaTecnica() {
		return fichaTecnica;
	}
	public void setFichaTecnica(boolean fichaTecnica) {
		this.fichaTecnica = fichaTecnica;
	}
	
	
}
