package org.gestoresmadrid.oegam2comun.trafico.materiales.view.dto;

import java.io.Serializable;

public class FacturacionStockDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4799239436852437838L;
	
	private String numColegiado;
	private String nombreColegiado;
	private String provincia;
	private String via;
	private Long   contrato;
	private String fechaImpresion;
	private Long   nro0Azul;
	private Long   nroEco;
	private Long   nroCVerde;
	private Long   nroBAmarilla;
	private Long   ceroMoto;
	private Long   ecoMoto;
	private Long   cVerde;
	private Long   bMoto;
	private Long   total;
	private Long   permisos;
	private Long   eitv;
	
	
	public FacturacionStockDto() {
		this.nro0Azul     = 0L;
		this.nroBAmarilla = 0L;
		this.nroCVerde    = 0L;
		this.nroEco       = 0L;
		this.ceroMoto     = 0L;
		this.ceroMoto 	  = 0L;
		this.cVerde       = 0L;
		this.bMoto        = 0L;
		this.permisos     = 0L;
		this.eitv         = 0L;
	}
	
	public String getNombreColegiado() {
		return nombreColegiado;
	}
	public void setNombreColegiado(String nombreColegiado) {
		this.nombreColegiado = nombreColegiado;
	}
	public Long getContrato() {
		return contrato;
	}
	public void setContrato(Long contrato) {
		this.contrato = contrato;
	}
	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}
	public Long getPermisos() {
		return permisos;
	}
	public void setPermisos(Long permisos) {
		this.permisos = permisos;
	}
	public Long getEitv() {
		return eitv;
	}
	public void setEitv(Long eitv) {
		this.eitv = eitv;
	}
	public Long getNro0Azul() {
		return nro0Azul;
	}
	public void setNro0Azul(Long nro0Azul) {
		this.nro0Azul = nro0Azul;
	}
	public Long getMroEco() {
		return nroEco;
	}
	public void setMroEco(Long mroEco) {
		this.nroEco = mroEco;
	}
	public Long getNroCVerde() {
		return nroCVerde;
	}
	public void setNroCVerde(Long nroCVerde) {
		this.nroCVerde = nroCVerde;
	}
	public Long getNroBAmarilla() {
		return nroBAmarilla;
	}
	public void setNroBAmarilla(Long nroBAmarilla) {
		this.nroBAmarilla = nroBAmarilla;
	}
	public String getProvincia() {
		return provincia;
	}
	public void setProvincia(String provincia) {
		this.provincia = provincia;
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

	public String getFechaImpresion() {
		return fechaImpresion;
	}

	public void setFechaImpresion(String fechaImpresion) {
		this.fechaImpresion = fechaImpresion;
	}

	public Long getNroEco() {
		return nroEco;
	}

	public void setNroEco(Long nroEco) {
		this.nroEco = nroEco;
	}

	public Long getCeroMoto() {
		return ceroMoto;
	}

	public void setCeroMoto(Long ceroMoto) {
		this.ceroMoto = ceroMoto;
	}

	public Long getEcoMoto() {
		return ecoMoto;
	}

	public void setEcoMoto(Long ecoMoto) {
		this.ecoMoto = ecoMoto;
	}

	public Long getcVerde() {
		return cVerde;
	}

	public void setcVerde(Long cVerde) {
		this.cVerde = cVerde;
	}

	public Long getbMoto() {
		return bMoto;
	}

	public void setbMoto(Long bMoto) {
		this.bMoto = bMoto;
	}
	
}
