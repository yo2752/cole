package org.gestoresmadrid.oegamImpresion.view.dto;

import java.io.Serializable;

import utilidades.estructuras.Fecha;

public class ImpresionDto implements Serializable {

	private static final long serialVersionUID = 6438257582122554122L;

	private Long idImpresion;

	private String tipoDocumento;

	private String tipoTramite;

	private Fecha fechaCreacion;

	private Long idContrato;

	private Long idUsuario;

	private String nombreDocumento;

	private String tipoInterviniente;

	private String tipoCarpeta;

	private String estado;

	private Fecha fechaModificacion;

	private Fecha fechaGenerado;

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

	public Fecha getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Fecha fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Long getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Long idContrato) {
		this.idContrato = idContrato;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNombreDocumento() {
		return nombreDocumento;
	}

	public void setNombreDocumento(String nombreDocumento) {
		this.nombreDocumento = nombreDocumento;
	}

	public String getTipoInterviniente() {
		return tipoInterviniente;
	}

	public void setTipoInterviniente(String tipoInterviniente) {
		this.tipoInterviniente = tipoInterviniente;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Fecha getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(Fecha fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public Fecha getFechaGenerado() {
		return fechaGenerado;
	}

	public void setFechaGenerado(Fecha fechaGenerado) {
		this.fechaGenerado = fechaGenerado;
	}

	public String getTipoCarpeta() {
		return tipoCarpeta;
	}

	public void setTipoCarpeta(String tipoCarpeta) {
		this.tipoCarpeta = tipoCarpeta;
	}

}