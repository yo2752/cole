package org.gestoresmadrid.core.licencias.model.vo;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;

/**
 * The persistent class for the LC_DOCUMENTO_LICENCIA database table.
 */
@Entity
@Table(name = "LC_DOCUMENTO_LICENCIA")
public class LcDocumentoLicenciaVO implements Serializable {

	private static final long serialVersionUID = -1055997329349519357L;

	@Id
	@SequenceGenerator(name = "lc_documento_licencia", sequenceName = "LC_DOCUMENTO_LICENCIA_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "lc_documento_licencia")
	@Column(name = "ID_DOCUMENTO_LICENCIA")
	private Long idDocumentoLicencia;

	@Column(name = "NUM_EXPEDIENTE")
	private BigDecimal numExpediente;

	@Column(name = "TIPO_DOCUMENTO")
	private String tipoDocumento;

	@Column(name = "ID_CONTRATO")
	private Long idContrato;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_CONTRATO", insertable = false, updatable = false)
	private ContratoVO contrato;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_ALTA")
	private Date fechaAlta;

	public Long getIdDocumentoLicencia() {
		return idDocumentoLicencia;
	}

	public void setIdDocumentoLicencia(Long idDocumentoLicencia) {
		this.idDocumentoLicencia = idDocumentoLicencia;
	}

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
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

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}
}