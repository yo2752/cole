package org.gestoresmadrid.core.impr.model.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.distintivo.model.vo.DistintivoVO;


@Entity
@Table(name="DOC_IMPR")
public class DocImprVO implements Serializable{

	private static final long serialVersionUID = 5487684898367675324L;

	@Id
	@SequenceGenerator(name = "docImpr_secuencia", sequenceName = "ID_DOC_IMPR_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "docImpr_secuencia")
	@Column(name="ID")
	Long id;

	@Column(name="DOC_IMPR")
	String docImpr;
	
	@Column(name = "ID_CONTRATO")
	Long idContrato;
	
	@Column(name = "ESTADO")
	String estado;
	
	@Column(name = "TIPO")
	String tipo;
	
	@Column(name = "TIPO_TRAMITE")
	String tipoTramite;
	
	@Column(name = "TIPO_DISTINTIVO")
	String tipoDstv;
	
	@Column(name= "JEFATURA")
	String jefatura;
	
	@Column(name="FECHA_ALTA")
	Date fechaAlta;
	
	@Column(name="FECHA_IMPRESION")
	Date fechaImpresion;
	
	@Column(name="FECHA_DOCUMENTO")
	Date fechaDocumento;
	
	@Column(name="TIPO_IMPRESION")
	String tipoImpresion;
	
	@Column(name="DEMANDA")
	String demanda;
	
	@Column(name="CARPETA")
	String carpeta;
	
	@Column(name="REF_DOCUMENTO")
	String refDocumento;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_CONTRATO",referencedColumnName="ID_CONTRATO",insertable=false,updatable=false)
	ContratoVO contrato;
	
	@OneToMany(mappedBy="docImpr",fetch= FetchType.LAZY)
	Set<ImprVO> listaImpr;
	
	@OneToMany(mappedBy="docImpr",fetch= FetchType.LAZY)
	Set<DistintivoVO> listaDistintivos;

	public List<ImprVO> getImprAsList() {
		List<ImprVO> lista;
		if (listaImpr != null) {
			// Map from Set to List
			lista =  new ArrayList<ImprVO>(listaImpr);
		} else {
			lista = Collections.emptyList();
		}
		return lista;
	}
	
	public List<DistintivoVO> getDstvAsList() {
		List<DistintivoVO> lista;
		if (listaDistintivos != null) {
			// Map from Set to List
			lista =  new ArrayList<DistintivoVO>(listaDistintivos);
		} else {
			lista = Collections.emptyList();
		}
		return lista;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDocImpr() {
		return docImpr;
	}

	public void setDocImpr(String docImpr) {
		this.docImpr = docImpr;
	}

	public Long getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Long idContrato) {
		this.idContrato = idContrato;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getJefatura() {
		return jefatura;
	}

	public void setJefatura(String jefatura) {
		this.jefatura = jefatura;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Date getFechaImpresion() {
		return fechaImpresion;
	}

	public void setFechaImpresion(Date fechaImpresion) {
		this.fechaImpresion = fechaImpresion;
	}

	public ContratoVO getContrato() {
		return contrato;
	}

	public void setContrato(ContratoVO contrato) {
		this.contrato = contrato;
	}

	public Set<ImprVO> getListaImpr() {
		return listaImpr;
	}

	public void setListaImpr(Set<ImprVO> listaImpr) {
		this.listaImpr = listaImpr;
	}

	public String getTipoTramite() {
		return tipoTramite;
	}

	public void setTipoTramite(String tipoTramite) {
		this.tipoTramite = tipoTramite;
	}

	public String getDemanda() {
		return demanda;
	}

	public void setDemanda(String demanda) {
		this.demanda = demanda;
	}

	public String getTipoDstv() {
		return tipoDstv;
	}

	public void setTipoDstv(String tipoDstv) {
		this.tipoDstv = tipoDstv;
	}

	public Set<DistintivoVO> getListaDistintivos() {
		return listaDistintivos;
	}

	public void setListaDistintivos(Set<DistintivoVO> listaDistintivos) {
		this.listaDistintivos = listaDistintivos;
	}

	public Date getFechaDocumento() {
		return fechaDocumento;
	}

	public void setFechaDocumento(Date fechaDocumento) {
		this.fechaDocumento = fechaDocumento;
	}

	public String getTipoImpresion() {
		return tipoImpresion;
	}

	public void setTipoImpresion(String tipoImpresion) {
		this.tipoImpresion = tipoImpresion;
	}

	public String getCarpeta() {
		return carpeta;
	}

	public void setCarpeta(String carpeta) {
		this.carpeta = carpeta;
	}

	public String getRefDocumento() {
		return refDocumento;
	}

	public void setRefDocumento(String refDocumento) {
		this.refDocumento = refDocumento;
	}
	
}

