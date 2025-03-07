package org.gestoresmadrid.core.historicocreditos.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.general.model.vo.TipoTramiteVO;
import org.gestoresmadrid.core.historicocreditos.model.dao.enumerados.ConceptoCreditoFacturado;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;


/**
 * The persistent class for the CREDITO_FACTURADO database table.
 * 
 */
@Entity
@Table(name="CREDITO_FACTURADO")
public class CreditoFacturadoVO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CREDITO_FACTURADO_IDCREDITOFACTURADO_GENERATOR", sequenceName="CREDITO_FACTURADO_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="CREDITO_FACTURADO_IDCREDITOFACTURADO_GENERATOR")
	@Column(name="ID_CREDITO_FACTURADO")
	private Long idCreditoFacturado;

	@Column(name="CONCEPTO")
	@Enumerated(EnumType.STRING)
	private ConceptoCreditoFacturado concepto;

	@Temporal(TemporalType.DATE)
	@Column(name="FECHA")
	private Date fecha;

	@Column(name="NUMERO_CREDITOS")
	private BigDecimal numeroCreditos;

	@ManyToOne(fetch=FetchType.LAZY, optional = false)
	@JoinColumn(name="TIPO_TRAMITE")
	private TipoTramiteVO tipoTramite;

	//bi-directional many-to-one association to Contrato
	@ManyToOne(fetch=FetchType.LAZY, optional = false)
	@JoinColumn(name="ID_CONTRATO")
	private ContratoVO contrato;

	//bi-directional many-to-one association to CreditoFacturadoTramiteVO
	@OneToMany(mappedBy="creditoFacturado")
	@Cascade({CascadeType.ALL, CascadeType.DELETE_ORPHAN})
	private Set<CreditoFacturadoTramiteVO> creditoFacturadoTramites;

	public CreditoFacturadoVO() {
	}

	public Long getIdCreditoFacturado() {
		return this.idCreditoFacturado;
	}

	public void setIdCreditoFacturado(Long idCreditoFacturado) {
		this.idCreditoFacturado = idCreditoFacturado;
	}

	public ConceptoCreditoFacturado getConcepto() {
		return this.concepto;
	}

	public void setConcepto(ConceptoCreditoFacturado concepto) {
		this.concepto = concepto;
	}

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public BigDecimal getNumeroCreditos() {
		return this.numeroCreditos;
	}

	public void setNumeroCreditos(BigDecimal numeroCreditos) {
		this.numeroCreditos = numeroCreditos;
	}

	public TipoTramiteVO getTipoTramite() {
		return this.tipoTramite;
	}

	public void setTipoTramite(TipoTramiteVO tipoTramite) {
		this.tipoTramite = tipoTramite;
	}

	public ContratoVO getContrato() {
		return this.contrato;
	}

	public void setContrato(ContratoVO contrato) {
		this.contrato = contrato;
	}

	public Set<CreditoFacturadoTramiteVO> getCreditoFacturadoTramites() {
		return this.creditoFacturadoTramites;
	}

	public void setCreditoFacturadoTramites(Set<CreditoFacturadoTramiteVO> creditoFacturadoTramites) {
		this.creditoFacturadoTramites = creditoFacturadoTramites;
	}

	public CreditoFacturadoTramiteVO addCreditoFacturadoTramite(CreditoFacturadoTramiteVO creditoFacturadoTramite) {
		if(getCreditoFacturadoTramites()==null) {
			setCreditoFacturadoTramites(new HashSet<CreditoFacturadoTramiteVO>());
		}
		getCreditoFacturadoTramites().add(creditoFacturadoTramite);
		creditoFacturadoTramite.setCreditoFacturado(this);

		return creditoFacturadoTramite;
	}

	public boolean removeCreditoFacturadoTramite(CreditoFacturadoTramiteVO creditoFacturadoTramite) {
		if(getCreditoFacturadoTramites()!=null && getCreditoFacturadoTramites().remove(creditoFacturadoTramite)) {
			creditoFacturadoTramite.setCreditoFacturado(null);
			return true;
		} else {
			return false;
		}
	}

}