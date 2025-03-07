package org.gestoresmadrid.core.evolucionConsultaKo.model.vo;

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

import org.gestoresmadrid.core.consultaKo.model.vo.ConsultaKoVO;
import org.gestoresmadrid.core.general.model.vo.UsuarioVO;

@Entity
@Table(name="EVOLUCION_CONSULTA_KO")
public class EvolucionConsultaKoVO implements Serializable{

	private static final long serialVersionUID = -2176817897308676635L;

	@Id
	@SequenceGenerator(name = "EvoConsultaKo_secuencia", sequenceName = "ID_EV_CONSULTA_KO_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "EvoConsultaKo_secuencia")
	@Column(name="ID")
	private Long id;

	@Column(name="TIPO_TRAMITE")
	private String tipoTramite;

	@Column(name="TIPO")
	private String tipo;

	@Column(name="OPERACION")
	private String operacion;

	@Column(name="MATRICULA")
	private String matricula;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="FECHA_CAMBIO")
	private Date fechaCambio;

	@Column(name="ESTADO_ANT")
	private BigDecimal estadoAnterior;

	@Column(name="ESTADO_NUEVO")
	private BigDecimal estadoNuevo;

	@Column(name="ID_USUARIO")
	private BigDecimal idUsuario;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_USUARIO",insertable=false,updatable=false)
	private UsuarioVO usuario;

	@Column(name="ID_CONSULTA_KO")
	private Long idConsultaKo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_CONSULTA_KO", referencedColumnName="ID", insertable = false, updatable = false)
	private ConsultaKoVO consultaKo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTipoTramite() {
		return tipoTramite;
	}

	public void setTipoTramite(String tipoTramite) {
		this.tipoTramite = tipoTramite;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getOperacion() {
		return operacion;
	}

	public void setOperacion(String operacion) {
		this.operacion = operacion;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
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

	public BigDecimal getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(BigDecimal idUsuario) {
		this.idUsuario = idUsuario;
	}

	public UsuarioVO getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioVO usuario) {
		this.usuario = usuario;
	}

	public Long getIdConsultaKo() {
		return idConsultaKo;
	}

	public void setIdConsultaKo(Long idConsultaKo) {
		this.idConsultaKo = idConsultaKo;
	}

	public ConsultaKoVO getConsultaKo() {
		return consultaKo;
	}

	public void setConsultaKo(ConsultaKoVO consultaKo) {
		this.consultaKo = consultaKo;
	}

}