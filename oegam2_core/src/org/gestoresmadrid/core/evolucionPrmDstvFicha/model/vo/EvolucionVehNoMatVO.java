package org.gestoresmadrid.core.evolucionPrmDstvFicha.model.vo;

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

import org.gestoresmadrid.core.docPermDistItv.model.vo.VehNoMatOegamVO;
import org.gestoresmadrid.core.general.model.vo.UsuarioVO;

@Entity
@Table(name = "EVOLUCION_VEH_NO_MAT_OEGAM")
public class EvolucionVehNoMatVO implements Serializable {

	private static final long serialVersionUID = -2035043676557639037L;

	@Id
	@SequenceGenerator(name = "EvoVehiculoNoMatr_secuencia", sequenceName = "EVO_VEH_NO_MATR_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "EvoVehiculoNoMatr_secuencia")
	@Column(name = "ID")
	private Long id;

	@Column(name = "FECHA_CAMBIO")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaCambio;

	@Column(name = "ID_USUARIO")
	private Long idUsuario;

	@Column(name = "MATRICULA")
	private String matricula;

	@Column(name = "TIPO_ACTUACION")
	private String tipoActuacion;

	@Column(name = "DESCRIPCION")
	private String descripcion;
	
	@Column(name = "ESTADO_ANT")
	private String estadoAnt;
	
	@Column(name = "ESTADO_NUEVO")
	private String estadoNuevo;
	
	@Column(name="DOC_ID")
	private String docId; 
	
	@Column(name="ID_VEH_NO_MAT_OEGAM")
	private Long idVehNoMatOegam;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_VEH_NO_MAT_OEGAM", referencedColumnName="ID", insertable = false, updatable = false)
	private VehNoMatOegamVO VehNoMatOegam;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_USUARIO", insertable = false, updatable = false)
	private UsuarioVO usuario;

	public String getTipoActuacion() {
		return tipoActuacion;
	}

	public void setTipoActuacion(String tipoActuacion) {
		this.tipoActuacion = tipoActuacion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public UsuarioVO getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioVO usuario) {
		this.usuario = usuario;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getFechaCambio() {
		return fechaCambio;
	}

	public void setFechaCambio(Date fechaCambio) {
		this.fechaCambio = fechaCambio;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getEstadoAnt() {
		return estadoAnt;
	}

	public void setEstadoAnt(String estadoAnt) {
		this.estadoAnt = estadoAnt;
	}

	public String getEstadoNuevo() {
		return estadoNuevo;
	}

	public void setEstadoNuevo(String estadoNuevo) {
		this.estadoNuevo = estadoNuevo;
	}

	public String getDocId() {
		return docId;
	}

	public void setDocId(String docId) {
		this.docId = docId;
	}

	public Long getIdVehNoMatOegam() {
		return idVehNoMatOegam;
	}

	public void setIdVehNoMatOegam(Long idVehNoMatOegam) {
		this.idVehNoMatOegam = idVehNoMatOegam;
	}

	public VehNoMatOegamVO getVehNoMatOegam() {
		return VehNoMatOegam;
	}

	public void setVehNoMatOegam(VehNoMatOegamVO vehNoMatOegam) {
		VehNoMatOegam = vehNoMatOegam;
	}
}
