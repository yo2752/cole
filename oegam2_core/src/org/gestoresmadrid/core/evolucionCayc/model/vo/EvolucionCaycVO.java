package org.gestoresmadrid.core.evolucionCayc.model.vo;

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

@Entity
@Table(name="EVOLUCION_CAYC")
public class EvolucionCaycVO implements Serializable{

	private static final long serialVersionUID = -1173060467616201677L;
	
	@Id
	@SequenceGenerator(name = "evolucion_cayc_secuencia", sequenceName = "EVOLUCION_CAYC_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "evolucion_cayc_secuencia")
	@Column(name="ID")
	private long id;

	@Column(name="NUM_EXPEDIENTE")
	private BigDecimal numExpediente;
	
	@Column(name="FECHA_CAMBIO")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaCambio;
	
	@Column(name="ID_USUARIO")
	private Long idUsuario;

	@Column(name="TIPO_ACTUACION")
	private String tipoActuacion;
	
	@Column(name="ESTADO_ANT")
	private BigDecimal estadoAnt;
	
	@Column(name="ESTADO_NUEVO")
	private BigDecimal estadoNuevo;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_USUARIO",insertable=false,updatable=false)
	private UsuarioVO usuario;

	public String getTipoActuacion() {
		return tipoActuacion;
	}

	public void setTipoActuacion(String tipoActuacion) {
		this.tipoActuacion = tipoActuacion;
	}

	public BigDecimal getEstadoAnt() {
		return estadoAnt;
	}

	public void setEstadoAnt(BigDecimal estadoAnt) {
		this.estadoAnt = estadoAnt;
	}

	public BigDecimal getEstadoNuevo() {
		return estadoNuevo;
	}

	public void setEstadoNuevo(BigDecimal estadoNuevo) {
		this.estadoNuevo = estadoNuevo;
	}

	public UsuarioVO getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioVO usuario) {
		this.usuario = usuario;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the numExpediente
	 */
	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	/**
	 * @param numExpediente the numExpediente to set
	 */
	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

	/**
	 * @return the fechaCambio
	 */
	public Date getFechaCambio() {
		return fechaCambio;
	}

	/**
	 * @param fechaCambio the fechaCambio to set
	 */
	public void setFechaCambio(Date fechaCambio) {
		this.fechaCambio = fechaCambio;
	}

	/**
	 * @return the idUsuario
	 */
	public Long getIdUsuario() {
		return idUsuario;
	}

	/**
	 * @param idUsuario the idUsuario to set
	 */
	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}
	
}
