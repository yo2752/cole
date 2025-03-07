package org.gestoresmadrid.oegam2comun.licenciasCam.restWS.response.objetos;

import java.io.Serializable;

public class BloqueNumero implements Serializable {

	private static final long serialVersionUID = 4873605119846234071L;

	private Integer codDireccion;

	private Integer clsDireccion;

	private String nomApp;

	private String numApp;

	private String calApp;

	private Integer codDistrito;

	private String distrito;

	private Integer codBarrio;

	private String barrio;

	private String seccionCensal;

	private String codPostal;

	private String seccionCarteria;

	private Integer zonaora;

	private Integer coordx;

	private Integer coordy;

	private Object bloqueLocal;

	public Integer getCodDireccion() {
		return codDireccion;
	}

	public void setCodDireccion(Integer codDireccion) {
		this.codDireccion = codDireccion;
	}

	public Integer getClsDireccion() {
		return clsDireccion;
	}

	public void setClsDireccion(Integer clsDireccion) {
		this.clsDireccion = clsDireccion;
	}

	public String getNomApp() {
		return nomApp;
	}

	public void setNomApp(String nomApp) {
		this.nomApp = nomApp;
	}

	public String getNumApp() {
		return numApp;
	}

	public void setNumApp(String numApp) {
		this.numApp = numApp;
	}

	public String getCalApp() {
		return calApp;
	}

	public void setCalApp(String calApp) {
		this.calApp = calApp;
	}

	public Integer getCodDistrito() {
		return codDistrito;
	}

	public void setCodDistrito(Integer codDistrito) {
		this.codDistrito = codDistrito;
	}

	public String getDistrito() {
		return distrito;
	}

	public void setDistrito(String distrito) {
		this.distrito = distrito;
	}

	public Integer getCodBarrio() {
		return codBarrio;
	}

	public void setCodBarrio(Integer codBarrio) {
		this.codBarrio = codBarrio;
	}

	public String getBarrio() {
		return barrio;
	}

	public void setBarrio(String barrio) {
		this.barrio = barrio;
	}

	public String getSeccionCensal() {
		return seccionCensal;
	}

	public void setSeccionCensal(String seccionCensal) {
		this.seccionCensal = seccionCensal;
	}

	public String getCodPostal() {
		return codPostal;
	}

	public void setCodPostal(String codPostal) {
		this.codPostal = codPostal;
	}

	public String getSeccionCarteria() {
		return seccionCarteria;
	}

	public void setSeccionCarteria(String seccionCarteria) {
		this.seccionCarteria = seccionCarteria;
	}

	public Integer getZonaora() {
		return zonaora;
	}

	public void setZonaora(Integer zonaora) {
		this.zonaora = zonaora;
	}

	public Integer getCoordx() {
		return coordx;
	}

	public void setCoordx(Integer coordx) {
		this.coordx = coordx;
	}

	public Integer getCoordy() {
		return coordy;
	}

	public void setCoordy(Integer coordy) {
		this.coordy = coordy;
	}

	public Object getBloqueLocal() {
		return bloqueLocal;
	}

	public void setBloqueLocal(Object bloqueLocal) {
		this.bloqueLocal = bloqueLocal;
	}
}
