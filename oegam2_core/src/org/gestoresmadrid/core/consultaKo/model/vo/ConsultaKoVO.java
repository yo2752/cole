package org.gestoresmadrid.core.consultaKo.model.vo;

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

import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.general.model.vo.UsuarioVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTraficoVO;

@Entity
@Table(name = "CONSULTA_KO")
public class ConsultaKoVO implements Serializable {

	private static final long serialVersionUID = -2344713675325589919L;

	@Id
	@Column(name = "ID")
	@SequenceGenerator(name = "consulta_ko_secuencia", sequenceName = "ID_CONSULTA_KO_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "consulta_ko_secuencia")
	private Long id;
	
	@Column(name = "ID_CONTRATO")
	private Long idContrato;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_CONTRATO", insertable = false, updatable = false)
	private ContratoVO contrato;
	
	@Column(name = "ID_USUARIO")
	private Long idUsuario;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_USUARIO", insertable = false, updatable = false)
	private UsuarioVO usuario;
	
	@Column(name = "MATRICULA")
	private String matricula;
	
	@Column(name = "TIPO_TRAMITE")
	private String tipoTramite;
	
	@Column(name = "TIPO")
	private String tipo;
	
	@Column(name = "ESTADO")
	private String estado;
	
	@Column(name = "FECHA")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fecha;
	
	@Column(name = "FECHA_EXCEL")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaGenExcel;
	
	@Column(name="NOMBRE_FICHERO")
	private String nombreFichero;
	
	@Column(name= "JEFATURA")
	private String jefatura;
	
	@Column(name = "NUM_EXPEDIENTE")
	private Long numExpediente;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "NUM_EXPEDIENTE",referencedColumnName="NUM_EXPEDIENTE", insertable = false, updatable = false)
	private TramiteTraficoVO tramiteTrafico;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Long idContrato) {
		this.idContrato = idContrato;
	}

	public ContratoVO getContrato() {
		return contrato;
	}

	public void setContrato(ContratoVO contrato) {
		this.contrato = contrato;
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

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
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

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getJefatura() {
		return jefatura;
	}

	public void setJefatura(String jefatura) {
		this.jefatura = jefatura;
	}

	public TramiteTraficoVO getTramiteTrafico() {
		return tramiteTrafico;
	}

	public void setTramiteTrafico(TramiteTraficoVO tramiteTrafico) {
		this.tramiteTrafico = tramiteTrafico;
	}

	public Long getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(Long numExpediente) {
		this.numExpediente = numExpediente;
	}

	public Date getFechaGenExcel() {
		return fechaGenExcel;
	}

	public void setFechaGenExcel(Date fechaGenExcel) {
		this.fechaGenExcel = fechaGenExcel;
	}

	public String getNombreFichero() {
		return nombreFichero;
	}

	public void setNombreFichero(String nombreFichero) {
		this.nombreFichero = nombreFichero;
	}
	
	
	

}
