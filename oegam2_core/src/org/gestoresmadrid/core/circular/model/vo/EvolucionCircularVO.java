package org.gestoresmadrid.core.circular.model.vo;

import java.io.Serializable;
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
@Table(name="EVOLUCION_CIRCULAR")
public class EvolucionCircularVO implements Serializable{

	private static final long serialVersionUID = -1756477222035849807L;

	@Id
	@SequenceGenerator(name = "evo_circular_secuencia", sequenceName = "ID_CIRCULAR_EVOL_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "evo_circular_secuencia")
	@Column(name = "ID_EVOLUCION")
	private Long idEvolucion;
	
	@Column(name="ID_CIRCULAR")
	private Long idCircular;
	
	@Column(name="ID_USUARIO")
	private Long idUsuario;
	 
	@Column(name="FECHA_CAMBIO")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaCambio;
	
	@Column(name="ESTADO_ANT")
	private String estadoAnterior;
	
	@Column(name="ESTADO_NUEVO")
	private String estadoNuevo;
	
	@Column(name="OPERACION")
	private String operacion;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_CIRCULAR",insertable=false,updatable=false)
	private CircularVO circular;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_USUARIO",insertable=false,updatable=false)
	private UsuarioVO usuario;

	public Long getIdEvolucion() {
		return idEvolucion;
	}

	public void setIdEvolucion(Long idEvolucion) {
		this.idEvolucion = idEvolucion;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Date getFechaCambio() {
		return fechaCambio;
	}

	public void setFechaCambio(Date fechaCambio) {
		this.fechaCambio = fechaCambio;
	}

	public String getEstadoAnterior() {
		return estadoAnterior;
	}

	public void setEstadoAnterior(String estadoAnterior) {
		this.estadoAnterior = estadoAnterior;
	}

	public String getEstadoNuevo() {
		return estadoNuevo;
	}

	public void setEstadoNuevo(String estadoNuevo) {
		this.estadoNuevo = estadoNuevo;
	}

	public Long getIdCircular() {
		return idCircular;
	}

	public void setIdCircular(Long idCircular) {
		this.idCircular = idCircular;
	}

	public CircularVO getCircular() {
		return circular;
	}

	public void setCircular(CircularVO circular) {
		this.circular = circular;
	}

	public UsuarioVO getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioVO usuario) {
		this.usuario = usuario;
	}

	public String getOperacion() {
		return operacion;
	}

	public void setOperacion(String operacion) {
		this.operacion = operacion;
	}
	 
}
