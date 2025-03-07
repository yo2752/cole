package org.gestoresmadrid.oegam2comun.trafico.materiales.view.beans;

import java.io.File;
import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.docPermDistItv.model.beans.FacturacionDocBean;

public class ResultadoFacturacionStockBean implements Serializable{

	private static final long serialVersionUID = -6743741498701521524L;

	private Boolean error;
	private String mensaje;
	private FacturacionStockBean facturacionStock;
	private File excel;
	private String nombreFichero;
	private List<FacturacionDocBean> listaFacturacion;
	
	public ResultadoFacturacionStockBean(Boolean error) {
		super();
		this.error = error;
	}
	
	public Boolean getError() {
		return error;
	}
	public void setError(Boolean error) {
		this.error = error;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public FacturacionStockBean getFacturacionStock() {
		return facturacionStock;
	}
	public void setFacturacionStock(FacturacionStockBean facturacionStock) {
		this.facturacionStock = facturacionStock;
	}
	public File getExcel() {
		return excel;
	}
	public void setExcel(File excel) {
		this.excel = excel;
	}

	public String getNombreFichero() {
		return nombreFichero;
	}

	public void setNombreFichero(String nombreFichero) {
		this.nombreFichero = nombreFichero;
	}

	public List<FacturacionDocBean> getListaFacturacion() {
		return listaFacturacion;
	}

	public void setListaFacturacion(List<FacturacionDocBean> listaFacturacion) {
		this.listaFacturacion = listaFacturacion;
	}
	
}
