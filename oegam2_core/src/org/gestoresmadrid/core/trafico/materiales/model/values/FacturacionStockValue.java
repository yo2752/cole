package org.gestoresmadrid.core.trafico.materiales.model.values;

import java.io.Serializable;
import java.util.HashMap;

public class FacturacionStockValue implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5027380085714103230L;
	
	private Long numColegiado;
	private String nombreColegiado;
	private Long contrato;
	private HashMap<String, Long> distintivos;
	private Long permisos;
	private Long eitv;
	
	
	public Long getNumColegiado() {
		return numColegiado;
	}
	public void setNumColegiado(Long numColegiado) {
		this.numColegiado = numColegiado;
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
	public HashMap<String, Long> getDistintivos() {
		return distintivos;
	}
	public void setDistintivos(HashMap<String, Long> distintivos) {
		this.distintivos = distintivos;
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
		
}
