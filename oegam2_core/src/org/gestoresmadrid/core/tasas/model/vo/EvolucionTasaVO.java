package org.gestoresmadrid.core.tasas.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.gestoresmadrid.core.general.model.vo.UsuarioVO;

/**
 * The persistent class for the EVOLUCION_TASA database table.
 */
@Entity
@Table(name = "EVOLUCION_TASA")
public class EvolucionTasaVO implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private EvolucionTasaPK id;

	private String accion;

	@Temporal(TemporalType.DATE)
	@Column(name = "FECHA_VIGENCIA_ANT")
	private Date fechaVigenciaAnt;

	@Temporal(TemporalType.DATE)
	@Column(name = "FECHA_VIGENCIA_NUE")
	private Date fechaVigenciaNue;

	// bi-directional many-to-one association to UsuarioVO
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_USUARIO")
	private UsuarioVO usuario;

	@Column(name = "NUM_EXPEDIENTE")
	private BigDecimal numExpediente;

	@Column(name = "PRECIO_ANT")
	private BigDecimal precioAnt;

	@Column(name = "PRECIO_NUE")
	private String precioNue;

	@Column(name = "MOTIVO_BLOQUEO")
	private String motivoBloqueo;

	// bi-directional many-to-one association to Tasa
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CODIGO_TASA", insertable = false, updatable = false)
	private TasaVO tasa;

	public EvolucionTasaVO() {}

	public EvolucionTasaPK getId() {
		return this.id;
	}

	public void setId(EvolucionTasaPK id) {
		this.id = id;
	}

	public String getAccion() {
		return this.accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}

	public Date getFechaVigenciaAnt() {
		return this.fechaVigenciaAnt;
	}

	public void setFechaVigenciaAnt(Date fechaVigenciaAnt) {
		this.fechaVigenciaAnt = fechaVigenciaAnt;
	}

	public Date getFechaVigenciaNue() {
		return this.fechaVigenciaNue;
	}

	public void setFechaVigenciaNue(Date fechaVigenciaNue) {
		this.fechaVigenciaNue = fechaVigenciaNue;
	}

	public UsuarioVO getUsuario() {
		return this.usuario;
	}

	public void setUsuario(UsuarioVO usuario) {
		this.usuario = usuario;
	}

	public BigDecimal getNumExpediente() {
		return this.numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

	public BigDecimal getPrecioAnt() {
		return this.precioAnt;
	}

	public void setPrecioAnt(BigDecimal precioAnt) {
		this.precioAnt = precioAnt;
	}

	public String getPrecioNue() {
		return this.precioNue;
	}

	public void setPrecioNue(String precioNue) {
		this.precioNue = precioNue;
	}

	public TasaVO getTasa() {
		return this.tasa;
	}

	public void setTasa(TasaVO tasa) {
		this.tasa = tasa;
	}

	public String getMotivoBloqueo() {
		return motivoBloqueo;
	}

	public void setMotivoBloqueo(String motivoBloqueo) {
		this.motivoBloqueo = motivoBloqueo;
	}
}