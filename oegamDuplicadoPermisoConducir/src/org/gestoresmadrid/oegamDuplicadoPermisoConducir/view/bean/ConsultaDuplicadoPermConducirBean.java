package org.gestoresmadrid.oegamDuplicadoPermisoConducir.view.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class ConsultaDuplicadoPermConducirBean implements Serializable {

	private static final long serialVersionUID = 2007428993061656215L;

	private Long idDuplicadoPermCond;
	private BigDecimal numExpediente;
	private String codigoTasa;
	private String estado;
	private String estadoDoc;
	private String doiTitular;
	private String descContrato;
	private String refPropia;

	public Long getIdDuplicadoPermCond() {
		return idDuplicadoPermCond;
	}

	public void setIdDuplicadoPermCond(Long idDuplicadoPermCond) {
		this.idDuplicadoPermCond = idDuplicadoPermCond;
	}

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

	public String getCodigoTasa() {
		return codigoTasa;
	}

	public void setCodigoTasa(String codigoTasa) {
		this.codigoTasa = codigoTasa;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getDoiTitular() {
		return doiTitular;
	}

	public void setDoiTitular(String doiTitular) {
		this.doiTitular = doiTitular;
	}

	public String getDescContrato() {
		return descContrato;
	}

	public void setDescContrato(String descContrato) {
		this.descContrato = descContrato;
	}

	public String getRefPropia() {
		return refPropia;
	}

	public void setRefPropia(String refPropia) {
		this.refPropia = refPropia;
	}

	public String getEstadoDoc() {
		return estadoDoc;
	}

	public void setEstadoDoc(String estadoDoc) {
		this.estadoDoc = estadoDoc;
	}
}
