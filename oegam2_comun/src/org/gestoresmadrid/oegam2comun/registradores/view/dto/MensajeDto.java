package org.gestoresmadrid.oegam2comun.registradores.view.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class MensajeDto implements Serializable {

	private static final long serialVersionUID = 4933863232956280030L;

	private long idMensaje;

	private String acuseRecibido;

	private Date fecAcuse;

	private Date fecNotificacion;

	private String tipoMensaje;

	private List<FicheroDto> ficheros;

	private TramiteRegRbmDto dossier;

	public long getIdMensaje() {
		return idMensaje;
	}

	public void setIdMensaje(long idMensaje) {
		this.idMensaje = idMensaje;
	}

	public String getAcuseRecibido() {
		return acuseRecibido;
	}

	public void setAcuseRecibido(String acuseRecibido) {
		this.acuseRecibido = acuseRecibido;
	}

	public Date getFecAcuse() {
		return fecAcuse;
	}

	public void setFecAcuse(Date fecAcuse) {
		this.fecAcuse = fecAcuse;
	}

	public Date getFecNotificacion() {
		return fecNotificacion;
	}

	public void setFecNotificacion(Date fecNotificacion) {
		this.fecNotificacion = fecNotificacion;
	}

	public String getTipoMensaje() {
		return tipoMensaje;
	}

	public void setTipoMensaje(String tipoMensaje) {
		this.tipoMensaje = tipoMensaje;
	}

	public List<FicheroDto> getFicheros() {
		return ficheros;
	}

	public void setFicheros(List<FicheroDto> ficheros) {
		this.ficheros = ficheros;
	}

	public TramiteRegRbmDto getDossier() {
		return dossier;
	}

	public void setDossier(TramiteRegRbmDto dossier) {
		this.dossier = dossier;
	}
}
