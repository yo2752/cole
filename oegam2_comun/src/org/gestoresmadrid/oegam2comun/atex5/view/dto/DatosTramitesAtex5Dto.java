package org.gestoresmadrid.oegam2comun.atex5.view.dto;

import java.io.Serializable;
import java.util.Date;

public class DatosTramitesAtex5Dto implements Serializable{
	
	private static final long serialVersionUID = -129463842277997532L;
	
	/**Comunes a todos los tramites***/
    private Date fechaFin;
    private Date fechaInicio;
    private String jefatura;
    private String sucursal;
    private String anotacion;
	
    /****Baja***/
	private String causaBaja;
    private String tipoBaja;
    
    
    /****Duplicados***/
    private Date fechaDuplicado;
    private String razonDuplicado;
    
    /****Prorrogas***/
    private Date fechaFinProrroga;
    	
    /***Rematriculaciones**/
    private Date fechaRematriculacion;
    private String razonRematriculacion;
    
    /***Transferencia**/
    private Date fechaTransferencia;
    private String idDocumentoAnterior;
    
    /***MatriculacionTemporal***/
    private Date fechaMatriculacionTemporal;
    private String matriculaAnterior;
    
	public Date getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}
	public Date getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public String getJefatura() {
		return jefatura;
	}
	public void setJefatura(String jefatura) {
		this.jefatura = jefatura;
	}
	public String getSucursal() {
		return sucursal;
	}
	public void setSucursal(String sucursal) {
		this.sucursal = sucursal;
	}
	public String getAnotacion() {
		return anotacion;
	}
	public void setAnotacion(String anotacion) {
		this.anotacion = anotacion;
	}
	public String getCausaBaja() {
		return causaBaja;
	}
	public void setCausaBaja(String causaBaja) {
		this.causaBaja = causaBaja;
	}
	public String getTipoBaja() {
		return tipoBaja;
	}
	public void setTipoBaja(String tipoBaja) {
		this.tipoBaja = tipoBaja;
	}
	public Date getFechaDuplicado() {
		return fechaDuplicado;
	}
	public void setFechaDuplicado(Date fechaDuplicado) {
		this.fechaDuplicado = fechaDuplicado;
	}
	public String getRazonDuplicado() {
		return razonDuplicado;
	}
	public void setRazonDuplicado(String razonDuplicado) {
		this.razonDuplicado = razonDuplicado;
	}
	public Date getFechaFinProrroga() {
		return fechaFinProrroga;
	}
	public void setFechaFinProrroga(Date fechaFinProrroga) {
		this.fechaFinProrroga = fechaFinProrroga;
	}
	public Date getFechaRematriculacion() {
		return fechaRematriculacion;
	}
	public void setFechaRematriculacion(Date fechaRematriculacion) {
		this.fechaRematriculacion = fechaRematriculacion;
	}
	public String getRazonRematriculacion() {
		return razonRematriculacion;
	}
	public void setRazonRematriculacion(String razonRematriculacion) {
		this.razonRematriculacion = razonRematriculacion;
	}
	public Date getFechaTransferencia() {
		return fechaTransferencia;
	}
	public void setFechaTransferencia(Date fechaTransferencia) {
		this.fechaTransferencia = fechaTransferencia;
	}
	public String getIdDocumentoAnterior() {
		return idDocumentoAnterior;
	}
	public void setIdDocumentoAnterior(String idDocumentoAnterior) {
		this.idDocumentoAnterior = idDocumentoAnterior;
	}
	public Date getFechaMatriculacionTemporal() {
		return fechaMatriculacionTemporal;
	}
	public void setFechaMatriculacionTemporal(Date fechaMatriculacionTemporal) {
		this.fechaMatriculacionTemporal = fechaMatriculacionTemporal;
	}
	public String getMatriculaAnterior() {
		return matriculaAnterior;
	}
	public void setMatriculaAnterior(String matriculaAnterior) {
		this.matriculaAnterior = matriculaAnterior;
	}
    
}
