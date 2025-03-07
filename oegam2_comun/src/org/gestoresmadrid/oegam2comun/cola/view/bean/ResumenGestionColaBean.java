package org.gestoresmadrid.oegam2comun.cola.view.bean;

import java.io.Serializable;
import java.util.List;

public class ResumenGestionColaBean implements Serializable {

	private static final long serialVersionUID = 2937466649091616116L;

	private Integer numOk;
	private Integer numError;
	private List<String> listaOk;
	private List<String> listaErrores;
	private Boolean esEliminarCola;
	private Boolean esFinalizarErrorServ;
	private Boolean esFinalizarConError;
	private Boolean esReactivar;
	private Boolean esEstablecerMaxPrio;

	public Integer getNumOk() {
		return numOk;
	}

	public void setNumOk(Integer numOk) {
		this.numOk = numOk;
	}

	public Integer getNumError() {
		return numError;
	}

	public void setNumError(Integer numError) {
		this.numError = numError;
	}

	public List<String> getListaOk() {
		return listaOk;
	}

	public void setListaOk(List<String> listaOk) {
		this.listaOk = listaOk;
	}

	public List<String> getListaErrores() {
		return listaErrores;
	}

	public void setListaErrores(List<String> listaErrores) {
		this.listaErrores = listaErrores;
	}

	public Boolean getEsEliminarCola() {
		return esEliminarCola;
	}

	public void setEsEliminarCola(Boolean esEliminarCola) {
		this.esEliminarCola = esEliminarCola;
	}

	public Boolean getEsFinalizarErrorServ() {
		return esFinalizarErrorServ;
	}

	public void setEsFinalizarErrorServ(Boolean esFinalizarErrorServ) {
		this.esFinalizarErrorServ = esFinalizarErrorServ;
	}

	public Boolean getEsFinalizarConError() {
		return esFinalizarConError;
	}

	public void setEsFinalizarConError(Boolean esFinalizarConError) {
		this.esFinalizarConError = esFinalizarConError;
	}

	public Boolean getEsReactivar() {
		return esReactivar;
	}

	public void setEsReactivar(Boolean esReactivar) {
		this.esReactivar = esReactivar;
	}

	public Boolean getEsEstablecerMaxPrio() {
		return esEstablecerMaxPrio;
	}

	public void setEsEstablecerMaxPrio(Boolean esEstablecerMaxPrio) {
		this.esEstablecerMaxPrio = esEstablecerMaxPrio;
	}
}
