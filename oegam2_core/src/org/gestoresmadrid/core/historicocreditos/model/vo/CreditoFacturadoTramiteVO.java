package org.gestoresmadrid.core.historicocreditos.model.vo;

import java.io.Serializable;

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


/**
 * The persistent class for the CREDITO_FACTURADO_TRAMITE database table.
 * 
 */
@Entity
@Table(name="CREDITO_FACTURADO_TRAMITE")
public class CreditoFacturadoTramiteVO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CREDITO_FACTURADO_TRAMITE_GENERATOR", sequenceName="CREDITO_FACTURADO_TRAMITE_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="CREDITO_FACTURADO_TRAMITE_GENERATOR")
	@Column(name="ID_CREDITO_FACTURADO_TRAMITE")
	private Long idCreditoFacturadoTramite;

	@Column(name="ID_TRAMITE")
	private String idTramite;

	//bi-directional many-to-one association to CreditoFacturadoVO
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name="ID_CREDITO_FACTURADO")
	private CreditoFacturadoVO creditoFacturado;

	public CreditoFacturadoTramiteVO() {
	}

	public Long getIdCreditoFacturadoTramite() {
		return this.idCreditoFacturadoTramite;
	}

	public void setIdCreditoFacturadoTramite(Long idCreditoFacturadoTramite) {
		this.idCreditoFacturadoTramite = idCreditoFacturadoTramite;
	}

	public String getIdTramite() {
		return this.idTramite;
	}

	public void setIdTramite(String idTramite) {
		this.idTramite = idTramite;
	}

	public CreditoFacturadoVO getCreditoFacturado() {
		return this.creditoFacturado;
	}

	public void setCreditoFacturado(CreditoFacturadoVO creditoFacturado) {
		this.creditoFacturado = creditoFacturado;
	}

}