package org.gestoresmadrid.core.evolucionDatosSensibles.model.vo;

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

import org.gestoresmadrid.core.general.model.vo.GrupoVO;
import org.gestoresmadrid.core.general.model.vo.UsuarioVO;

/**
 * The persistent class for the DATOS_SENSIBLES_MATRICULA database table.
 */
@Entity
@Table(name = "EVOLUCION_DS_MATRICULA")
public class EvolucionDatosSensiblesMatriculaVO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="SEC_ID_DS_MATRICULA")
	@SequenceGenerator(name="SEC_ID_DS_MATRICULA", sequenceName="ID_DS_MATRICULA_SEQ")
	@Column(name = "ID")
	private Long id;

	@Column(name = "ID_USUARIO")
	private BigDecimal idUsuario;

	@Column(name="ID_GRUPO")
	private String idGrupo;

	@Column(name = "TIPO_CAMBIO")
	private String tipoCambio;

	@Column(name = "ESTADO_ANTERIOR")
	private BigDecimal estadoAnterior;

	@Column(name = "ESTADO_NUEVO")
	private BigDecimal estadoNuevo;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_CAMBIO")
	private Date fechaCambio;

	@Column(name = "MATRICULA")
	private String matricula;

	@Column(name = "ORIGEN")
	private String origen;

	// bi-directional many-to-one association to GrupoVO
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_GRUPO", insertable = false, updatable = false)
	private GrupoVO grupo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_USUARIO", insertable = false, updatable = false)
	private UsuarioVO usuario;

	public EvolucionDatosSensiblesMatriculaVO() {}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the idUsuario
	 */
	public BigDecimal getIdUsuario() {
		return idUsuario;
	}

	/**
	 * @param idUsuario the idUsuario to set
	 */
	public void setIdUsuario(BigDecimal idUsuario) {
		this.idUsuario = idUsuario;
	}

	/**
	 * @return the tipoCambio
	 */
	public String getTipoCambio() {
		return tipoCambio;
	}

	/**
	 * @param tipoCambio the tipoCambio to set
	 */
	public void setTipoCambio(String tipoCambio) {
		this.tipoCambio = tipoCambio;
	}

	/**
	 * @return the estadoAnterior
	 */
	public BigDecimal getEstadoAnterior() {
		return estadoAnterior;
	}

	/**
	 * @param estadoAnterior the estadoAnterior to set
	 */
	public void setEstadoAnterior(BigDecimal estadoAnterior) {
		this.estadoAnterior = estadoAnterior;
	}

	/**
	 * @return the estadoNuevo
	 */
	public BigDecimal getEstadoNuevo() {
		return estadoNuevo;
	}

	/**
	 * @param estadoNuevo the estadoNuevo to set
	 */
	public void setEstadoNuevo(BigDecimal estadoNuevo) {
		this.estadoNuevo = estadoNuevo;
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
	 * @return the grupo
	 */
	public GrupoVO getGrupo() {
		return grupo;
	}

	/**
	 * @param grupo the grupo to set
	 */
	public void setGrupo(GrupoVO grupo) {
		this.grupo = grupo;
	}

	/**
	 * @return the matricula
	 */
	public String getMatricula() {
		return matricula;
	}

	/**
	 * @param matricula the matricula to set
	 */
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	/**
	 * @return the idGrupo
	 */
	public String getIdGrupo() {
		return idGrupo;
	}

	/**
	 * @param idGrupo the idGrupo to set
	 */
	public void setIdGrupo(String idGrupo) {
		this.idGrupo = idGrupo;
	}

	/**
	 * @return the usuario
	 */
	public UsuarioVO getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(UsuarioVO usuario) {
		this.usuario = usuario;
	}

	/**
	 * @return the origen
	 */
	public String getOrigen() {
		return origen;
	}

	/**
	 * @param origen the origen to set
	 */
	public void setOrigen(String origen) {
		this.origen = origen;
	}


}