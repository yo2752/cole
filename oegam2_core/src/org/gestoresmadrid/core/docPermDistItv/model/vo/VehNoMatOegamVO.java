package org.gestoresmadrid.core.docPermDistItv.model.vo;

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

@Entity
@Table(name = "VEHICULO_NO_MATRICULADO_OEGAM")
public class VehNoMatOegamVO implements Serializable {

	private static final long serialVersionUID = -1597574563103980531L;

	@Id
	@Column(name = "ID")
	@SequenceGenerator(name = "vehiculo_no_matriculado_oegam_secuencia", sequenceName = "ID_VEHICULO_NO_MATRICULADO_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "vehiculo_no_matriculado_oegam_secuencia")
	private Long id;
	
	@Column(name = "NUM_COLEGIADO")
	private String numColegiado;
	
	@Column(name = "ID_CONTRATO")
	private Long idContrato;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_CONTRATO", insertable = false, updatable = false)
	private ContratoVO contrato;
	
	@Column(name = "ESTADO_SOLICITUD")
	private String estadoSolicitud;
	
	@Column(name = "ESTADO_IMPRESION")
	private String estadoImpresion;
	
	@Column(name = "FECHA_SOLICITUD")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaSolicitud;
	
	@Column(name = "FECHA_IMPRESION")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaImpresion;
	
	@Column(name = "FECHA_ALTA")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaAlta;
	
	@Column(name = "MATRICULA")
	private String matricula;
	
	@Column(name = "NIVE")
	private String nive;
	
	@Column(name = "BASTIDOR")
	private String bastidor;
	
	@Column(name="TIPO_DSTV")
	private String tipoDistintivo;
	
	@Column(name = "DOC_DISTINTIVO")
	private Long docDistintivo;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DOC_DISTINTIVO", referencedColumnName="ID", insertable = false, updatable = false)
	private DocPermDistItvVO docDistintivoVO;

	@Column(name="RESP_SOLICITUD")
	private String respuestaSol;
	
	@Column(name = "PRIMERA_IMPRESION")
	private String primeraImpresion;
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public Long getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Long idContrato) {
		this.idContrato = idContrato;
	}


	public String getEstadoSolicitud() {
		return estadoSolicitud;
	}

	public void setEstadoSolicitud(String estadoSolicitud) {
		this.estadoSolicitud = estadoSolicitud;
	}

	public String getEstadoImpresion() {
		return estadoImpresion;
	}

	public void setEstadoImpresion(String estadoImpresion) {
		this.estadoImpresion = estadoImpresion;
	}

	public Date getFechaSolicitud() {
		return fechaSolicitud;
	}

	public void setFechaSolicitud(Date fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}

	public Date getFechaImpresion() {
		return fechaImpresion;
	}

	public void setFechaImpresion(Date fechaImpresion) {
		this.fechaImpresion = fechaImpresion;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getNive() {
		return nive;
	}

	public void setNive(String nive) {
		this.nive = nive;
	}

	public String getBastidor() {
		return bastidor;
	}

	public void setBastidor(String bastidor) {
		this.bastidor = bastidor;
	}

	public ContratoVO getContrato() {
		return contrato;
	}

	public void setContrato(ContratoVO contrato) {
		this.contrato = contrato;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public String getTipoDistintivo() {
		return tipoDistintivo;
	}

	public void setTipoDistintivo(String tipoDistintivo) {
		this.tipoDistintivo = tipoDistintivo;
	}

	public Long getDocDistintivo() {
		return docDistintivo;
	}

	public void setDocDistintivo(Long docDistintivo) {
		this.docDistintivo = docDistintivo;
	}

	public DocPermDistItvVO getDocDistintivoVO() {
		return docDistintivoVO;
	}

	public void setDocDistintivoVO(DocPermDistItvVO docDistintivoVO) {
		this.docDistintivoVO = docDistintivoVO;
	}

	public String getRespuestaSol() {
		return respuestaSol;
	}

	public void setRespuestaSol(String respuestaSol) {
		this.respuestaSol = respuestaSol;
	}

	public String getPrimeraImpresion() {
		return primeraImpresion;
	}

	public void setPrimeraImpresion(String primeraImpresion) {
		this.primeraImpresion = primeraImpresion;
	}

}
