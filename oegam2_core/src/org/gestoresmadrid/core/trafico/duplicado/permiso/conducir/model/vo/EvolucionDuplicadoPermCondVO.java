package org.gestoresmadrid.core.trafico.duplicado.permiso.conducir.model.vo;

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
@Table(name = "EVOLUCION_DUPLICADO_PERM_COND")
public class EvolucionDuplicadoPermCondVO implements Serializable {

	private static final long serialVersionUID = 8331046748425995004L;

	@Id
	@SequenceGenerator(name = "evolucion_dupl_perm_cond", sequenceName = "EVO_DUPL_PERM_COND_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "evolucion_dupl_perm_cond")
	@Column(name = "ID")
	private Long id;

	@Column(name = "ID_USUARIO")
	private Long idUsuario;

	@Column(name = "ID_DUPLICADO_PERM_COND")
	private Long idDuplicadoPermCond;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_CAMBIO")
	private Date fechaCambio;

	@Column(name = "ESTADO_ANTERIOR")
	private String estadoAnterior;

	@Column(name = "ESTADO_NUEVO")
	private String estadoNuevo;

	@Column(name = "TIPO_ACTUACION")
	private String tipoActuacion;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_USUARIO", insertable = false, updatable = false)
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

	public Long getIdDuplicadoPermCond() {
		return idDuplicadoPermCond;
	}

	public void setIdDuplicadoPermCond(Long idDuplicadoPermCond) {
		this.idDuplicadoPermCond = idDuplicadoPermCond;
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