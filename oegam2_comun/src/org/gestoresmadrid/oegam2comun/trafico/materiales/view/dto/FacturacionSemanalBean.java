package org.gestoresmadrid.oegam2comun.trafico.materiales.view.dto;

import java.io.Serializable;
import java.util.HashMap;

public class FacturacionSemanalBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3043018505985216241L;
	
	private String[] tipos;
	private Boolean[] documentos;
	private HashMap<String, HashMap<String, Long>> lineasFacturacionSemanalStock;
	private HashMap<String, HashMap<String, HashMap<String, Long>>> lineasFacturacionSemanalImpresion;

	public HashMap<String, HashMap<String, Long>> getLineasFacturacionSemanalStock() {
		return lineasFacturacionSemanalStock;
	}

	public void setLineasFacturacionSemanalStock(HashMap<String, HashMap<String, Long>> lineasFacturacionSemanalStock) {
		this.lineasFacturacionSemanalStock = lineasFacturacionSemanalStock;
	}

	public String[] getTipos() {
		return tipos;
	}

	public void setTipos(String[] tipos) {
		this.tipos = tipos;
	}

	public HashMap<String, HashMap<String, HashMap<String, Long>>> getLineasFacturacionSemanalImpresion() {
		return lineasFacturacionSemanalImpresion;
	}

	public void setLineasFacturacionSemanalImpresion(HashMap<String, HashMap<String, HashMap<String, Long>>> lineasFacturacionSemanalImpresion) {
		this.lineasFacturacionSemanalImpresion = lineasFacturacionSemanalImpresion;
	}

	public Boolean[] getDocumentos() {
		return documentos;
	}

	public void setDocumentos(Boolean[] documentos) {
		this.documentos = documentos;
	}
	
}
