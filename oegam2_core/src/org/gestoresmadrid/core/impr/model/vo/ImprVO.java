package org.gestoresmadrid.core.impr.model.vo;

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


@Entity
@Table(name="IMPR")
public class ImprVO implements Serializable{

	private static final long serialVersionUID = 1236787717748280206L;

	@Id
	@Column(name = "ID")
	@SequenceGenerator(name = "impr_secuencia", sequenceName = "ID_IMPR_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "impr_secuencia")
	Long id;
	
	@Column(name="MATRICULA")
	String matricula;
	
	@Column(name="BASTIDOR")
	String bastidor;
	
	@Column(name="NIVE")
	String nive;
	
	@Column(name="TIPO_IMPR")
	String tipoImpr;
	
	@Column(name="TIPO_VEHICULO_IMPR")
	String tipoVehiculoImpr;
	
	@Column(name="TIPO_TRAMITE")
	String tipoTramite;
	
	@Column(name="NIF_TITULAR")
	String nifTitular;
	
	@Column(name="NUM_EXPEDIENTE")
	BigDecimal numExpediente;
	
	@Column(name="ESTADO_SOLICITUD")
	String estadoSolicitud;
	
	@Column(name="ESTADO_IMPRESION")
	String estadoImpresion;
	
	@Column(name="FECHA_IMPRESION")
	Date fechaImpresion;
	
	@Column(name="FECHA_PRESENTACION")
	Date fechaPresentacion;
	
	@Column(name="FECHA_ALTA")
	Date fechaAlta;
	
	@Column(name="JEFATURA")
	String jefatura;
	
	@Column(name="DOC_ID")
	Long docId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DOC_ID",referencedColumnName="ID", insertable = false, updatable = false)
	DocImprVO docImpr;
	
	@Column(name="ID_KO")
	Long idKO;
	
	@Column(name="ID_CONTRATO")
	Long idContrato;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_CONTRATO", insertable = false, updatable = false)
	ContratoVO contrato;
	
	@Column(name="NUM_DESCARGA")
	Long numDescarga;
	
	@Column(name="CARPETA")
	String carpeta;
	
	@Column(name="ESTADO_IMPR")
	String estadoImpr;
	
	@Column(name="REF_PROPIA")
	String refPropia;

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

	public String getBastidor() {
		return bastidor;
	}

	public void setBastidor(String bastidor) {
		this.bastidor = bastidor;
	}

	public String getNive() {
		return nive;
	}

	public void setNive(String nive) {
		this.nive = nive;
	}

	public String getTipoImpr() {
		return tipoImpr;
	}

	public void setTipoImpr(String tipoImpr) {
		this.tipoImpr = tipoImpr;
	}

	public String getTipoTramite() {
		return tipoTramite;
	}

	public void setTipoTramite(String tipoTramite) {
		this.tipoTramite = tipoTramite;
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

	public Long getIdKO() {
		return idKO;
	}

	public void setIdKO(Long idKO) {
		this.idKO = idKO;
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

	public Date getFechaPresentacion() {
		return fechaPresentacion;
	}

	public void setFechaPresentacion(Date fechaPresentacion) {
		this.fechaPresentacion = fechaPresentacion;
	}

	public String getJefatura() {
		return jefatura;
	}

	public void setJefatura(String jefatura) {
		this.jefatura = jefatura;
	}

	public Long getNumDescarga() {
		return numDescarga;
	}

	public void setNumDescarga(Long numDescarga) {
		this.numDescarga = numDescarga;
	}

	public DocImprVO getDocImpr() {
		return docImpr;
	}

	public void setDocImpr(DocImprVO docImpr) {
		this.docImpr = docImpr;
	}

	public String getNifTitular() {
		return nifTitular;
	}

	public void setNifTitular(String nifTitular) {
		this.nifTitular = nifTitular;
	}

	public Date getFechaImpresion() {
		return fechaImpresion;
	}

	public void setFechaImpresion(Date fechaImpresion) {
		this.fechaImpresion = fechaImpresion;
	}

	public String getTipoVehiculoImpr() {
		return tipoVehiculoImpr;
	}

	public void setTipoVehiculoImpr(String tipoVehiculoImpr) {
		this.tipoVehiculoImpr = tipoVehiculoImpr;
	}

	public String getCarpeta() {
		return carpeta;
	}

	public void setCarpeta(String carpeta) {
		this.carpeta = carpeta;
	}

	public String getEstadoImpr() {
		return estadoImpr;
	}

	public void setEstadoImpr(String estadoImpr) {
		this.estadoImpr = estadoImpr;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public String getRefPropia() {
		return refPropia;
	}

	public void setRefPropia(String refPropia) {
		this.refPropia = refPropia;
	}
}
