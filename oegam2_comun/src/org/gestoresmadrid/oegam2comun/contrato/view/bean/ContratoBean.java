package org.gestoresmadrid.oegam2comun.contrato.view.bean;

import java.io.Serializable;

import org.gestoresmadrid.core.contrato.model.enumerados.EstadoContrato;
import org.gestoresmadrid.core.contrato.model.enumerados.EstadoMobileGest;

public class ContratoBean implements Serializable{

	private static final long serialVersionUID = -111212176707712609L;
	
	private String cif;
	private String provincia;
	private Long idContrato;
	private String razonSocial;
	private String via;
	private String numColegiado;
	private String alias;
	private EstadoContrato estado;
	private String fechaCaducidad;
	private EstadoMobileGest mobileGest;
	
	
	public String getCif() {
		return cif;
	}
	public void setCif(String cif) {
		this.cif = cif;
	}
	public String getProvincia() {
		return provincia;
	}
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	public Long getIdContrato() {
		return idContrato;
	}
	public void setIdContrato(Long idContrato) {
		this.idContrato = idContrato;
	}
	public String getRazonSocial() {
		return razonSocial;
	}
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
	public String getVia() {
		return via;
	}
	public void setVia(String via) {
		this.via = via;
	}
	public String getNumColegiado() {
		return numColegiado;
	}
	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public EstadoContrato getEstado() {
		return estado;
	}
	public void setEstado(EstadoContrato estado) {
		this.estado = estado;
	}
	public String getFechaCaducidad() {
		return fechaCaducidad;
	}
	public void setFechaCaducidad(String fechaCaducidad) {
		this.fechaCaducidad = fechaCaducidad;
	}
	public EstadoMobileGest getMobileGest() {
		return mobileGest;
	}
	public void setMobileGest(EstadoMobileGest mobileGest) {
		this.mobileGest = mobileGest;
	}
	
}
