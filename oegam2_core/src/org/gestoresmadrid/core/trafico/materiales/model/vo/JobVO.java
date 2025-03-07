package org.gestoresmadrid.core.trafico.materiales.model.vo;

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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.docPermDistItv.model.vo.DocPermDistItvVO;
import org.gestoresmadrid.core.general.model.vo.UsuarioVO;
import org.gestoresmadrid.core.trafico.model.vo.JefaturaTraficoVO;

@Entity
@Table(name = "GID_JOB")
public class JobVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 358900141207940378L;

	@Id
	@SequenceGenerator(name = "job_secuencia", sequenceName = "GID_ID_JOB_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "job_secuencia")
	@Column(name = "JOB_ID")
	private Long jobId;
	
	@Column(name = "DOC_DISTINTIVO")
	private Long docDistintivo;
	
	@Column(name = "TIPO_DOCUMENTO")
	private String tipoDocumento;
	
	@Column(name = "TIPO_DISTINTIVO")
	private String tipoDistintivo;
	
	@Column(name = "ESTADO")
	private Long estado;
	
	@Column(name = "RESPUESTA")
	private String respuesta;
	
	@Column(name = "JEFATURA_PROVINCIAL")
	private String jefatura;

	@ManyToOne
	@JoinColumn(name="JEFATURA_PROVINCIAL",insertable=false,updatable=false)
	private JefaturaTraficoVO jefaturaProvincial;

	@Column(name="FECHA_DOCUMENTO")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaDocumento;
	
	@Column(name = "ID_CONTRATO")
	private Long idContrato;

	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_CONTRATO",referencedColumnName="ID_CONTRATO",insertable=false,updatable=false)
	private ContratoVO contrato;

	@Column(name="DOC_PERMDSTVEITV_ID")
	private String docIdPerm;

	@Column(name = "SUBTIPO")
	private String subtipo;
	
	@Column(name = "ID_USUARIO")
	private Long idUsuario;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_USUARIO",insertable=false,updatable=false)
	private UsuarioVO usuario;
	
	@Column(name = "COLEGIO")
	private String colegio;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="DOC_DISTINTIVO", referencedColumnName="ID", insertable=false,updatable=false)
	private DocPermDistItvVO docPermDstv;
	
	@Override
	public String toString() {
		return "JobVO [jobId=" + jobId + ", docDistintivo=" + docDistintivo + ", tipoDocumento=" + tipoDocumento
				+ ", tipoDistintivo=" + tipoDistintivo + ", estado=" + estado + ", respuesta=" + respuesta
				+ ", jefatura=" + jefatura + ", fechaDocumento=" + fechaDocumento + ", idContrato=" + idContrato
				+ ", docIdPerm=" + docIdPerm + ", subtipo=" + subtipo + ", idUsuario=" + idUsuario + ", usuario="
				+ usuario + ", colegio=" + colegio + "]";
	}

	public Long getJobId() {
		return jobId;
	}

	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}

	public Long getDocDistintivo() {
		return docDistintivo;
	}

	public void setDocDistintivo(Long docDistintivo) {
		this.docDistintivo = docDistintivo;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getTipoDistintivo() {
		return tipoDistintivo;
	}

	public void setTipoDistintivo(String tipoDistintivo) {
		this.tipoDistintivo = tipoDistintivo;
	}

	public Long getEstado() {
		return estado;
	}

	public void setEstado(Long estado) {
		this.estado = estado;
	}

	public JefaturaTraficoVO getJefaturaProvincial() {
		return jefaturaProvincial;
	}

	public void setJefaturaProvincial(JefaturaTraficoVO jefaturaProvincial) {
		this.jefaturaProvincial = jefaturaProvincial;
	}

	public Date getFechaDocumento() {
		return fechaDocumento;
	}

	public void setFechaDocumento(Date fechaDocumento) {
		this.fechaDocumento = fechaDocumento;
	}

	public ContratoVO getContrato() {
		return contrato;
	}

	public void setContrato(ContratoVO contrato) {
		this.contrato = contrato;
	}

	public String getDocIdPerm() {
		return docIdPerm;
	}

	public void setDocIdPerm(String docIdPerm) {
		this.docIdPerm = docIdPerm;
	}

	public String getSubtipo() {
		return subtipo;
	}

	public void setSubtipo(String subtipo) {
		this.subtipo = subtipo;
	}

	public UsuarioVO getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioVO usuario) {
		this.usuario = usuario;
	}

	public Long getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Long idContrato) {
		this.idContrato = idContrato;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}

	public String getJefatura() {
		return jefatura;
	}

	public void setJefatura(String jefatura) {
		this.jefatura = jefatura;
	}

	public String getColegio() {
		return colegio;
	}

	public void setColegio(String colegio) {
		this.colegio = colegio;
	}

	public DocPermDistItvVO getDocPermDstv() {
		return docPermDstv;
	}

	public void setDocPermDstv(DocPermDistItvVO docPermDstv) {
		this.docPermDstv = docPermDstv;
	}
	
}
