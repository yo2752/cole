package org.gestoresmadrid.oegamComun.impr.model.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DocDGTImprDTO implements Serializable {

	private static final long serialVersionUID = 872183853751455139L;

	private Long idDocPermDistItv;
	private String docIdPerm;
	private String tipo;
	private String tipoDistintivo;
	private Date fechaImpresion;
	private Long idContrato;
	private String numColegiado;
	private String jefatura;
	private Date fechaAlta;
	private BigDecimal estado;
	private String nomEstado;

	public Long getIdDocPermDistItv() {
		return idDocPermDistItv;
	}

	public void setIdDocPermDistItv(Long idDocPermDistItv) {
		this.idDocPermDistItv = idDocPermDistItv;
	}

	public String getDocIdPerm() {
		return docIdPerm;
	}

	public void setDocIdPerm(String docIdPerm) {
		this.docIdPerm = docIdPerm;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getTipoDistintivo() {
		return tipoDistintivo;
	}

	public void setTipoDistintivo(String tipoDistintivo) {
		this.tipoDistintivo = tipoDistintivo;
	}

	public Date getFechaImpresion() {
		return fechaImpresion;
	}
	
	public String getFechaImpresionStr() {
		return fechaImpresion != null ? new SimpleDateFormat("dd/MM/yyyy").format(fechaImpresion) : "";
	}

	public void setFechaImpresion(Date fechaImpresion) {
		this.fechaImpresion = fechaImpresion;
	}

	public Long getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Long idContrato) {
		this.idContrato = idContrato;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
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
	
	public String getFechaAltaStr() {
		return fechaAlta != null ? new SimpleDateFormat("dd/MM/yyyy").format(fechaAlta) : "";
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public BigDecimal getEstado() {
		return estado;
	}

	public void setEstado(BigDecimal estado) {
		this.estado = estado;
	}

	public String getNomEstado() {
		return nomEstado;
	}

	public void setNomEstado(String nomEstado) {
		this.nomEstado = nomEstado;
	}
}