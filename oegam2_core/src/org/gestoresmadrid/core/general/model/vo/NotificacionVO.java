package org.gestoresmadrid.core.general.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.gestoresmadrid.core.trafico.model.vo.TramiteTraficoVO;

/**
 * The persistent class for the NOTIFICACION database table.
 */
@Entity(name = "NOTIFICACION")
public class NotificacionVO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "notificacion_secuencia", sequenceName = "ID_NOTIFICACION_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "notificacion_secuencia")
	@Column(name = "ID_NOTIFICACION")
	private long idNotificacion;

	private String descripcion;

	@Column(name = "ESTADO_ANT")
	private BigDecimal estadoAnt;

	@Column(name = "ESTADO_NUE")
	private BigDecimal estadoNue;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_HORA")
	private Date fechaHora;

	@Column(name = "ID_TRAMITE")
	private BigDecimal idTramite;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_TRAMITE", referencedColumnName = "NUM_EXPEDIENTE", insertable = false, updatable = false)
	private TramiteTraficoVO tramiteTrafico;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TIPO_TRAMITE")
	private TipoTramiteVO tipoTramite;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_USUARIO")
	private UsuarioVO usuario;

	public NotificacionVO() {}

	public long getIdNotificacion() {
		return this.idNotificacion;
	}

	public void setIdNotificacion(long idNotificacion) {
		this.idNotificacion = idNotificacion;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public BigDecimal getEstadoAnt() {
		return this.estadoAnt;
	}

	public void setEstadoAnt(BigDecimal estadoAnt) {
		this.estadoAnt = estadoAnt;
	}

	public BigDecimal getEstadoNue() {
		return this.estadoNue;
	}

	public void setEstadoNue(BigDecimal estadoNue) {
		this.estadoNue = estadoNue;
	}

	public Date getFechaHora() {
		return this.fechaHora;
	}

	public void setFechaHora(Date fechaHora) {
		this.fechaHora = fechaHora;
	}

	public BigDecimal getIdTramite() {
		return this.idTramite;
	}

	public void setIdTramite(BigDecimal idTramite) {
		this.idTramite = idTramite;
	}

	public TramiteTraficoVO getTramiteTrafico() {
		return tramiteTrafico;
	}

	public void setTramiteTrafico(TramiteTraficoVO tramiteTrafico) {
		this.tramiteTrafico = tramiteTrafico;
	}

	public TipoTramiteVO getTipoTramite() {
		return this.tipoTramite;
	}

	public void setTipoTramite(TipoTramiteVO tipoTramite) {
		this.tipoTramite = tipoTramite;
	}

	public UsuarioVO getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioVO usuario) {
		this.usuario = usuario;
	}
}