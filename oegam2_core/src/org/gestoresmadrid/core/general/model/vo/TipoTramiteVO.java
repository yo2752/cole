package org.gestoresmadrid.core.general.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * The persistent class for the TIPO_TRAMITE database table.
 */
@Entity
@Table(name = "TIPO_TRAMITE")
public class TipoTramiteVO implements Serializable {

	private static final long serialVersionUID = 7296022345376802083L;

	@Id
	@Column(name = "TIPO_TRAMITE")
	private String tipoTramite;

	@Column(name = "ACTIVO")
	private BigDecimal activo;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CODIGO_APLICACION")
	private AplicacionVO aplicacion;

	@Column(name = "DESCRIPCION")
	private String descripcion;

	// bi-directional many-to-one association to Notificacion
	@OneToMany(mappedBy = "tipoTramite", fetch = FetchType.LAZY)
	private List<NotificacionVO> notificacions;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinTable(name = "TIPO_CREDITO_TRAMITE", catalog = "oegam2", joinColumns = { @JoinColumn(name = "TIPO_TRAMITE", nullable = false, updatable = false, insertable = false) }, inverseJoinColumns = {
			@JoinColumn(name = "TIPO_CREDITO", nullable = false, updatable = false, insertable = false) })
	private TipoCreditoVO tipoCredito;

	public TipoTramiteVO() {}

	public String getTipoTramite() {
		return this.tipoTramite;
	}

	public void setTipoTramite(String tipoTramite) {
		this.tipoTramite = tipoTramite;
	}

	public BigDecimal getActivo() {
		return this.activo;
	}

	public void setActivo(BigDecimal activo) {
		this.activo = activo;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public List<NotificacionVO> getNotificacions() {
		return this.notificacions;
	}

	public void setNotificacions(List<NotificacionVO> notificacions) {
		this.notificacions = notificacions;
	}

	public TipoCreditoVO getTipoCredito() {
		return tipoCredito;
	}

	public void setTipoCredito(TipoCreditoVO tipoCredito) {
		this.tipoCredito = tipoCredito;
	}

	public AplicacionVO getAplicacion() {
		return aplicacion;
	}

	public void setAplicacion(AplicacionVO aplicacion) {
		this.aplicacion = aplicacion;
	}

}