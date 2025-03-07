package org.gestoresmadrid.core.licencias.model.vo;

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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.gestoresmadrid.core.general.model.vo.UsuarioVO;

/**
 * The persistent class for the LC_EVOLUCION_TRAMITE database table.
 */
@Entity
@Table(name = "LC_EVOLUCION_TRAMITE")
public class LcEvolucionTramiteVO implements Serializable {

	private static final long serialVersionUID = 559827897747231900L;

	
	@Id
	@SequenceGenerator(name = "lc_evo_lc_tramite", sequenceName = "LC_EVO_LC_TRAMITE_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "lc_evo_lc_tramite")
	@Column(name = "ID_EVOLUCION")
	private Long idEvolucion;
	
	@Column(name = "NUM_EXPEDIENTE")
	private BigDecimal numExpediente;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_CAMBIO")
	private Date fechaCambio;

	@Column(name = "ESTADO_ANTERIOR")
	private BigDecimal estadoAnterior;

	@Column(name = "ESTADO_NUEVO")
	private BigDecimal estadoNuevo;
	
	@Column(name="ID_USUARIO")
	private Long idUsuario;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_USUARIO", insertable = false, updatable = false)
	private UsuarioVO usuario;

	public Long getIdEvolucion() {
		return idEvolucion;
	}

	public void setIdEvolucion(Long idEvolucion) {
		this.idEvolucion = idEvolucion;
	}

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

	public Date getFechaCambio() {
		return fechaCambio;
	}

	public void setFechaCambio(Date fechaCambio) {
		this.fechaCambio = fechaCambio;
	}

	public BigDecimal getEstadoAnterior() {
		return estadoAnterior;
	}

	public void setEstadoAnterior(BigDecimal estadoAnterior) {
		this.estadoAnterior = estadoAnterior;
	}

	public BigDecimal getEstadoNuevo() {
		return estadoNuevo;
	}

	public void setEstadoNuevo(BigDecimal estadoNuevo) {
		this.estadoNuevo = estadoNuevo;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public UsuarioVO getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioVO usuario) {
		this.usuario = usuario;
	}
}