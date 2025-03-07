package org.gestoresmadrid.core.trafico.permiso.internacional.model.vo;

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
@Table(name = "EVOLUCION_PERMISO_INTER")
public class EvolucionPermisoInterVO implements Serializable {

	private static final long serialVersionUID = 7004364697536235480L;

	@Id
	@SequenceGenerator(name = "evolucion_perm_inter", sequenceName = "EVOLUCION_PERM_INTER_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "evolucion_perm_inter")
	@Column(name = "ID")
	private Long id;

	@Column(name = "ID_USUARIO")
	private Long idUsuario;

	@Column(name = "ID_PERMISO")
	private Long idPermiso;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_CAMBIO")
	private Date fechaCambio;

	@Column(name = "ESTADO_ANTERIOR")
	private String estadoAnterior;

	@Column(name = "ESTADO_NUEVO")
	private String estadoNuevo;

	@Column(name = "TIPO_ACTUACION")
	private String tipoActuacion;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_USUARIO",insertable=false,updatable=false)
	private UsuarioVO usuario;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Long getIdPermiso() {
		return idPermiso;
	}

	public void setIdPermiso(Long idPermiso) {
		this.idPermiso = idPermiso;
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

	public String getTipoActuacion() {
		return tipoActuacion;
	}

	public void setTipoActuacion(String tipoActuacion) {
		this.tipoActuacion = tipoActuacion;
	}

	public UsuarioVO getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioVO usuario) {
		this.usuario = usuario;
	}
}