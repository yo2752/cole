
package org.gestoresmadrid.oegam2comun.licenciasCam.restWS.response.objetos;

import java.io.Serializable;

public class Vial implements Serializable {

	private static final long serialVersionUID = 4114198724610379261L;

	private Integer codVial;

	private Integer codVial5;

	private String nomClase;

	private String nomVial;

	private String nomClaseActual;

	private String nomVialActual;

	private Integer orden;

	public Integer getCodVial() {
		return codVial;
	}

	public void setCodVial(Integer codVial) {
		this.codVial = codVial;
	}

	public Integer getCodVial5() {
		return codVial5;
	}

	public void setCodVial5(Integer codVial5) {
		this.codVial5 = codVial5;
	}

	public String getNomClase() {
		return nomClase;
	}

	public void setNomClase(String nomClase) {
		this.nomClase = nomClase;
	}

	public String getNomVial() {
		return nomVial;
	}

	public void setNomVial(String nomVial) {
		this.nomVial = nomVial;
	}

	public String getNomClaseActual() {
		return nomClaseActual;
	}

	public void setNomClaseActual(String nomClaseActual) {
		this.nomClaseActual = nomClaseActual;
	}

	public String getNomVialActual() {
		return nomVialActual;
	}

	public void setNomVialActual(String nomVialActual) {
		this.nomVialActual = nomVialActual;
	}

	public Integer getOrden() {
		return orden;
	}

	public void setOrden(Integer orden) {
		this.orden = orden;
	}
}
