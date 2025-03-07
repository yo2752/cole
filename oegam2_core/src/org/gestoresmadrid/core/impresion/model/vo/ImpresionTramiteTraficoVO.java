package org.gestoresmadrid.core.impresion.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;

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
 * The persistent class for the IMPRESION database table.
 */
@Entity
@Table(name = "IMPRESION_TRAMITE_TRAFICO")
public class ImpresionTramiteTraficoVO implements Serializable {

	private static final long serialVersionUID = -285077798057871653L;

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEC_IMPRESION_TRAMITE_TRAFICO")
	@SequenceGenerator(name = "SEC_IMPRESION_TRAMITE_TRAFICO", sequenceName = "IMPRESION_TRAMITE_TRAFICO_SEQ")
	private Long id;

	@Column(name = "ID_IMPRESION")
	private Long idImpresion;

	@Column(name = "NUM_EXPEDIENTE")
	private BigDecimal numExpediente;

	@Column(name = "TIPO_IMPRESION")
	private String tipoImpresion;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_IMPRESION", insertable = false, updatable = false)
	private ImpresionVO impresion;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdImpresion() {
		return idImpresion;
	}

	public void setIdImpresion(Long idImpresion) {
		this.idImpresion = idImpresion;
	}

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

	public String getTipoImpresion() {
		return tipoImpresion;
	}

	public void setTipoImpresion(String tipoImpresion) {
		this.tipoImpresion = tipoImpresion;
	}

	public ImpresionVO getImpresion() {
		return impresion;
	}

	public void setImpresion(ImpresionVO impresion) {
		this.impresion = impresion;
	}
}