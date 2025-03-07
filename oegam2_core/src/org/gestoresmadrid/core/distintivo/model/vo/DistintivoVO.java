package org.gestoresmadrid.core.distintivo.model.vo;

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

import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.impr.model.vo.DocImprVO;


@Entity
@Table(name="DISTINTIVO")
public class DistintivoVO implements Serializable{

	private static final long serialVersionUID = 3913023853627528358L;

	@Id
	@Column(name = "ID")
	@SequenceGenerator(name = "id_distintivo_secuencia", sequenceName = "ID_DSTV_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "id_distintivo_secuencia")
	Long id;
	
	@Column(name="MATRICULA")
	String matricula;
	
	@Column(name="BASTIDOR")
	String bastidor;
	
	@Column(name="NIVE")
	String nive;
	
	@Column(name="TIPO_DISTINTIVO")
	String tipoDistintivo;
	
	@Column(name="TIPO")
	String tipo;
	
	@Column(name = "FECHA_ALTA")
	Date fechaAlta;
	
	@Column(name="NUM_EXPEDIENTE")
	BigDecimal numExpediente;
	
	@Column(name="ESTADO_SOLICITUD")
	String estadoSolicitud;
	
	@Column(name="ESTADO_IMPRESION")
	String estadoImpresion;
	
	@Column(name="FECHA_IMPRESION")
	Date fechaImpresion;
	
	@Column(name="NIF")
	String nif;
	
	@Column(name="JEFATURA")
	String jefatura;
	
	@Column(name="CARSHARING")
	String carsharing;
	
	@Column(name="DOC_ID")
	Long docId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DOC_ID",referencedColumnName="ID", insertable = false, updatable = false)
	DocImprVO docImpr;
	
	@Column(name="ID_CONTRATO")
	Long idContrato;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_CONTRATO", insertable = false, updatable = false)
	ContratoVO contrato;
	
	@Column(name="RESPUESTA")
	String respuesta;
	
	@Column(name="REF_PROPIA")
	String refPropia;
	
	@Column(name="PRIMERA_IMPRESION")
	String primeraImpresion;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getTipoDistintivo() {
		return tipoDistintivo;
	}

	public void setTipoDistintivo(String tipoDistintivo) {
		this.tipoDistintivo = tipoDistintivo;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
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

	public Long getDocId() {
		return docId;
	}

	public void setDocId(Long docId) {
		this.docId = docId;
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

	public String getBastidor() {
		return bastidor;
	}

	public void setBastidor(String bastidor) {
		this.bastidor = bastidor;
	}

	public String getJefatura() {
		return jefatura;
	}

	public void setJefatura(String jefatura) {
		this.jefatura = jefatura;
	}

	public String getCarsharing() {
		return carsharing;
	}

	public void setCarsharing(String carsharing) {
		this.carsharing = carsharing;
	}

	public String getNive() {
		return nive;
	}

	public void setNive(String nive) {
		this.nive = nive;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public DocImprVO getDocImpr() {
		return docImpr;
	}

	public void setDocImpr(DocImprVO docImpr) {
		this.docImpr = docImpr;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public String getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}

	public Date getFechaImpresion() {
		return fechaImpresion;
	}

	public void setFechaImpresion(Date fechaImpresion) {
		this.fechaImpresion = fechaImpresion;
	}

	public String getRefPropia() {
		return refPropia;
	}

	public void setRefPropia(String refPropia) {
		this.refPropia = refPropia;
	}

	public String getPrimeraImpresion() {
		return primeraImpresion;
	}

	public void setPrimeraImpresion(String primeraImpresion) {
		this.primeraImpresion = primeraImpresion;
	}
	
}
