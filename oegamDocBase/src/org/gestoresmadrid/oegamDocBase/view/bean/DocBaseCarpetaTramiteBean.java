package org.gestoresmadrid.oegamDocBase.view.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class DocBaseCarpetaTramiteBean implements Serializable {

	private static final long serialVersionUID = -6038168328325684092L;

	private BigDecimal numExpediente;

	private Long id;

	private String idDocBase;

	private String tipoCarpeta;

	private String tipoCarpetaDocBase;

	private String motivo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

	public String getTipoCarpeta() {
		return tipoCarpeta;
	}

	public void setTipoCarpeta(String tipoCarpeta) {
		this.tipoCarpeta = tipoCarpeta;
	}

	public String getTipoCarpetaDocBase() {
		return tipoCarpetaDocBase;
	}

	public void setTipoCarpetaDocBase(String tipoCarpetaDocBase) {
		this.tipoCarpetaDocBase = tipoCarpetaDocBase;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public String getIdDocBase() {
		return idDocBase;
	}

	public void setIdDocBase(String idDocBase) {
		this.idDocBase = idDocBase;
	}
}
