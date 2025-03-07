package org.gestoresmadrid.oegam2comun.registradores.view.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import utilidades.estructuras.Fecha;

public class MinutaRegistroDto implements Serializable{

	private static final long serialVersionUID = 6583857638744508949L;

	private long idMinuta;
	
	private String aceptada;
	
	private String numeroMinuta;

	private Date fechaMinuta;

	private Fecha fechaMinutaRegistro;

	private BigDecimal idTramiteRegistro;

	public long getIdMinuta() {
		return idMinuta;
	}

	public void setIdMinuta(long idMinuta) {
		this.idMinuta = idMinuta;
	}

	public String getAceptada() {
		return aceptada;
	}

	public void setAceptada(String aceptada) {
		this.aceptada = aceptada;
	}

	public String getNumeroMinuta() {
		return numeroMinuta;
	}

	public void setNumeroMinuta(String numeroMinuta) {
		this.numeroMinuta = numeroMinuta;
	}

	public Date getFechaMinuta() {
		return fechaMinuta;
	}

	public void setFechaMinuta(Date fechaMinuta) {
		this.fechaMinuta = fechaMinuta;
	}

	public Fecha getFechaMinutaRegistro() {
		return fechaMinutaRegistro;
	}

	public void setFechaMinutaRegistro(Fecha fechaMinutaRegistro) {
		this.fechaMinutaRegistro = fechaMinutaRegistro;
	}

	public BigDecimal getIdTramiteRegistro() {
		return idTramiteRegistro;
	}

	public void setIdTramiteRegistro(BigDecimal idTramiteRegistro) {
		this.idTramiteRegistro = idTramiteRegistro;
	}


}
