package org.gestoresmadrid.oegam2comun.atex5.view.dto;

import java.io.Serializable;
import java.util.Date;

public class DatosIncidenciaAtex5Dto implements Serializable{

	private static final long serialVersionUID = 6608382662900814278L;
	
	private Date fecha;
    private String kms;
    private String importancias;
    private String tipo;
    private String concepto;
    private String descripcion;
    private String anotador;
    private Date fechaLegalizacion;
    private String pieza;
    private String tipoIncidenciaTaller;
    private String elementoIncidenciaTaller;
    
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public String getKms() {
		return kms;
	}
	public void setKms(String kms) {
		this.kms = kms;
	}
	public String getImportancias() {
		return importancias;
	}
	public void setImportancias(String importancias) {
		this.importancias = importancias;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getConcepto() {
		return concepto;
	}
	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getAnotador() {
		return anotador;
	}
	public void setAnotador(String anotador) {
		this.anotador = anotador;
	}
	public Date getFechaLegalizacion() {
		return fechaLegalizacion;
	}
	public void setFechaLegalizacion(Date fechaLegalizacion) {
		this.fechaLegalizacion = fechaLegalizacion;
	}
	public String getPieza() {
		return pieza;
	}
	public void setPieza(String pieza) {
		this.pieza = pieza;
	}
	public String getTipoIncidenciaTaller() {
		return tipoIncidenciaTaller;
	}
	public void setTipoIncidenciaTaller(String tipoIncidenciaTaller) {
		this.tipoIncidenciaTaller = tipoIncidenciaTaller;
	}
	public String getElementoIncidenciaTaller() {
		return elementoIncidenciaTaller;
	}
	public void setElementoIncidenciaTaller(String elementoIncidenciaTaller) {
		this.elementoIncidenciaTaller = elementoIncidenciaTaller;
	}
    
}
