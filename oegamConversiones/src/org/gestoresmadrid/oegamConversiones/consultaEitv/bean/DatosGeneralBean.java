package org.gestoresmadrid.oegamConversiones.consultaEitv.bean;

import java.io.Serializable;

public class DatosGeneralBean implements Serializable{

	private static final long serialVersionUID = -3792651524711112551L;
    
	private String nive;
    private String tipodoc;
    private String docfabricante;
    private String nomfabricante;
    private String dirfabricante;
    private String nomfabvehicomp;
    private String dirfabvehicomp;
    private String emitida;
    private String fechaemitida;
    private String serieindustria;
    private String tipotarjeta;
    private String estado;
    private String fechaestado;
    private String validez;
    private String fechavalidez;
	
    public DatosGeneralBean() {
		super();
	}

	public String getNive() {
		return nive;
	}

	public void setNive(String nive) {
		this.nive = nive;
	}

	public String getTipodoc() {
		return tipodoc;
	}

	public void setTipodoc(String tipodoc) {
		this.tipodoc = tipodoc;
	}

	public String getDocfabricante() {
		return docfabricante;
	}

	public void setDocfabricante(String docfabricante) {
		this.docfabricante = docfabricante;
	}

	public String getNomfabricante() {
		return nomfabricante;
	}

	public void setNomfabricante(String nomfabricante) {
		this.nomfabricante = nomfabricante;
	}

	public String getDirfabricante() {
		return dirfabricante;
	}

	public void setDirfabricante(String dirfabricante) {
		this.dirfabricante = dirfabricante;
	}

	public String getNomfabvehicomp() {
		return nomfabvehicomp;
	}

	public void setNomfabvehicomp(String nomfabvehicomp) {
		this.nomfabvehicomp = nomfabvehicomp;
	}

	public String getDirfabvehicomp() {
		return dirfabvehicomp;
	}

	public void setDirfabvehicomp(String dirfabvehicomp) {
		this.dirfabvehicomp = dirfabvehicomp;
	}

	public String getEmitida() {
		return emitida;
	}

	public void setEmitida(String emitida) {
		this.emitida = emitida;
	}

	public String getFechaemitida() {
		return fechaemitida;
	}

	public void setFechaemitida(String fechaemitida) {
		this.fechaemitida = fechaemitida;
	}

	public String getSerieindustria() {
		return serieindustria;
	}

	public void setSerieindustria(String serieindustria) {
		this.serieindustria = serieindustria;
	}

	public String getTipotarjeta() {
		return tipotarjeta;
	}

	public void setTipotarjeta(String tipotarjeta) {
		this.tipotarjeta = tipotarjeta;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getFechaestado() {
		return fechaestado;
	}

	public void setFechaestado(String fechaestado) {
		this.fechaestado = fechaestado;
	}

	public String getValidez() {
		return validez;
	}

	public void setValidez(String validez) {
		this.validez = validez;
	}

	public String getFechavalidez() {
		return fechavalidez;
	}

	public void setFechavalidez(String fechavalidez) {
		this.fechavalidez = fechavalidez;
	}
    
}
