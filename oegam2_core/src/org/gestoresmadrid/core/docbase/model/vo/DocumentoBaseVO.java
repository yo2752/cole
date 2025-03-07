package org.gestoresmadrid.core.docbase.model.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTraficoVO;

@Entity
@Table(name = "DOC_BASE")
@NamedQueries({ @NamedQuery(name = "DocumentoBaseVO.findAll", query = "SELECT d FROM DocumentoBaseVO d"),
	@NamedQuery(name = DocumentoBaseVO.REVERTIR_DOC_BASE, query = DocumentoBaseVO.REVERTIR_DOC_BASE_QUERY) })
public class DocumentoBaseVO implements Serializable{

	private static final long serialVersionUID = -6947658153254785298L;

	public static final String REVERTIR_DOC_BASE = "DocumentoBaseVO.revertir";
	static final String REVERTIR_DOC_BASE_QUERY = "delete from DocumentoBaseVO d where d.docId in (:listaDocBase)";
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="doc_base_seq_gen")
	@SequenceGenerator(name="doc_base_seq_gen", sequenceName="DOC_BASE_SEQ")
	private Long id;

	@Column(name="CARPETA")
	private String carpeta;

	@Column(name="DOC_ID")
	private String docId;

	@Column(name="ESTADO")
	private String estado;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="FECHA_CREACION")
	private Date fechaCreacion;

	@Temporal(TemporalType.DATE)
	@Column(name="FECHA_PRESENTACION")
	private Date fechaPresentacion;

	@Column(name="QR_CODE")
	private String qrCode;
	
	//bi-directional many-to-one association to TramiteTrafico
	@OneToMany(mappedBy="documentoBase", fetch=FetchType.LAZY)
	private Set<TramiteTraficoVO> tramitesTrafico;

	//bi-directional many-to-one association to Contrato
	//@ManyToOne
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="NUM_CONTRATO", insertable=false, updatable=false)
	private ContratoVO contrato;	
	
	@Column(name="NUM_CONTRATO")
	private Long idContrato;

	@Column(name="IND_DOC_YB")
	private Boolean indDocYb;
	
	@Column(name="ORDEN_DOCBASE_YB")
	private Long ordenDocbase;

	public List<TramiteTraficoVO> getTramitesTraficoAsList() {
		List<TramiteTraficoVO> lista = new ArrayList<TramiteTraficoVO>();
		if(tramitesTrafico != null && !tramitesTrafico.isEmpty()){
			lista.addAll(tramitesTrafico);
		}
		return lista;
	}
	
	public void setTramitesTrafico(List<TramiteTraficoVO> tramitesTrafico) {
		if (tramitesTrafico == null) {
			this.tramitesTrafico = null;
		} else {
			// Map from List to Set
			Set<TramiteTraficoVO> set = new HashSet<TramiteTraficoVO>();
			set.addAll(tramitesTrafico);
			this.tramitesTrafico = set;
		}
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCarpeta() {
		return carpeta;
	}

	public void setCarpeta(String carpeta) {
		this.carpeta = carpeta;
	}

	public String getDocId() {
		return docId;
	}

	public void setDocId(String docId) {
		this.docId = docId;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Date getFechaPresentacion() {
		return fechaPresentacion;
	}

	public void setFechaPresentacion(Date fechaPresentacion) {
		this.fechaPresentacion = fechaPresentacion;
	}

	public String getQrCode() {
		return qrCode;
	}

	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}

	public Set<TramiteTraficoVO> getTramitesTrafico() {
		return tramitesTrafico;
	}

	public void setTramitesTrafico(Set<TramiteTraficoVO> tramitesTrafico) {
		this.tramitesTrafico = tramitesTrafico;
	}

	public ContratoVO getContrato() {
		return contrato;
	}

	public void setContrato(ContratoVO contrato) {
		this.contrato = contrato;
	}

	public Long getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Long idContrato) {
		this.idContrato = idContrato;
	}

	public Boolean getIndDocYb() {
		return indDocYb;
	}

	public void setIndDocYb(Boolean indDocYb) {
		this.indDocYb = indDocYb;
	}

	public Long getOrdenDocbase() {
		return ordenDocbase;
	}

	public void setOrdenDocbase(Long ordenDocbase) {
		this.ordenDocbase = ordenDocbase;
	}
}
