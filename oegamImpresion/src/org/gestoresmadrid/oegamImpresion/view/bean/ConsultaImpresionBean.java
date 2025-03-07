package org.gestoresmadrid.oegamImpresion.view.bean;

import java.io.Serializable;
import java.util.Date;

public class ConsultaImpresionBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long idImpresion;

	private String tipoDocumento;

	private String tipoTramite;

	private Date fechaCreacion;

	private Long idContrato;

	private String nombreDocumento;

	private String estado;

	private Date fechaGenerado;

	private String numColegiado;

	private String mensaje;

	private int indice;

	public Long getIdImpresion() {
		return idImpresion;
	}

	public void setIdImpresion(Long idImpresion) {
		this.idImpresion = idImpresion;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getTipoTramite() {
		return tipoTramite;
	}

	public void setTipoTramite(String tipoTramite) {
		this.tipoTramite = tipoTramite;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Long getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Long idContrato) {
		this.idContrato = idContrato;
	}

	public String getNombreDocumento() {
		return nombreDocumento;
	}

	public void setNombreDocumento(String nombreDocumento) {
		this.nombreDocumento = nombreDocumento;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Date getFechaGenerado() {
		return fechaGenerado;
	}

	public void setFechaGenerado(Date fechaGenerado) {
		this.fechaGenerado = fechaGenerado;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public int getIndice() {
		return indice;
	}

	public void setIndice(int indice) {
		this.indice = indice;
	}
}