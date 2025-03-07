package org.gestoresmadrid.oegam2comun.registradores.view.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class FicheroDto implements Serializable{

	private static final long serialVersionUID = -3895765770229482428L;

	private long idFichero;

	private String codigoCsv;

	private Date fecCreacion;

	private String formato;

	private String nombreFichero;

	private BigDecimal ocupacionBytes;

	private String path;

	private String urlCsv;

	private List<MensajeDto> mensajes;

	public long getIdFichero() {
		return idFichero;
	}

	public void setIdFichero(long idFichero) {
		this.idFichero = idFichero;
	}

	public String getCodigoCsv() {
		return codigoCsv;
	}

	public void setCodigoCsv(String codigoCsv) {
		this.codigoCsv = codigoCsv;
	}

	public Date getFecCreacion() {
		return fecCreacion;
	}

	public void setFecCreacion(Date fecCreacion) {
		this.fecCreacion = fecCreacion;
	}

	public String getFormato() {
		return formato;
	}

	public void setFormato(String formato) {
		this.formato = formato;
	}

	public String getNombreFichero() {
		return nombreFichero;
	}

	public void setNombreFichero(String nombreFichero) {
		this.nombreFichero = nombreFichero;
	}

	public BigDecimal getOcupacionBytes() {
		return ocupacionBytes;
	}

	public void setOcupacionBytes(BigDecimal ocupacionBytes) {
		this.ocupacionBytes = ocupacionBytes;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getUrlCsv() {
		return urlCsv;
	}

	public void setUrlCsv(String urlCsv) {
		this.urlCsv = urlCsv;
	}

	public List<MensajeDto> getMensajes() {
		return mensajes;
	}

	public void setMensajes(List<MensajeDto> mensajes) {
		this.mensajes = mensajes;
	}
}
