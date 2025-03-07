package org.gestoresmadrid.oegam2comun.trafico.canjes.view.beans;

import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.gestoresmadrid.oegam2comun.canjes.view.dto.CanjesDto;


public class ResultadoCanjesBean implements Serializable {


	private static final long serialVersionUID = 2613342057648327886L;

	
	public static final String TIPO_PDF = "pdf";
	public static final String TIPO_FILE = "file";
	public static final String NOMBRE_FICHERO = "nombreFichero";
	private Boolean error;
	private Exception excepcion;
	private String mensaje;
	private Integer numOk;
	private Integer numError;
	private List<String> listaOk;
	private List<String> listaError;
	private List<String> listaMensajes;
	private Map<String, Object>	attachments;
	private List<CanjesDto> listaCanjes;
	
	private BigDecimal totalSumado;
	private BigDecimal totalRestado;
	private BigDecimal creditosContrato;
	private String tipoCredito;
	
	private File fichero;
	private String nombreFichero;
	
	private String nombreArchivoDescarga;
	
	private byte[] pdf;

	public ResultadoCanjesBean(Boolean error) {
		super();
		this.error = error;
	}
	
	public ResultadoCanjesBean() {
		super();
		this.error = Boolean.FALSE;
	}
	
	public void addListaCanjes(CanjesDto canjes){
		if(listaCanjes == null){
			listaCanjes = new ArrayList<CanjesDto>();
		}
		listaCanjes.add(canjes);
	}

	
	public Boolean getError() {
		return error;
	}

	public void setError(Boolean error) {
		this.error = error;
	}

	public Exception getExcepcion() {
		return excepcion;
	}

	public void setExcepcion(Exception excepcion) {
		this.excepcion = excepcion;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

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

	public List<String> getListaError() {
		return listaError;
	}

	public void setListaError(List<String> listaError) {
		this.listaError = listaError;
	}

	public List<String> getListaMensajes() {
		return listaMensajes;
	}

	public void setListaMensajes(List<String> listaMensajes) {
		this.listaMensajes = listaMensajes;
	}

	public List<CanjesDto> getListaCanjes() {
		return listaCanjes;
	}

	public void setListaCanjes(List<CanjesDto> listaCanjes) {
		this.listaCanjes = listaCanjes;
	}

	public BigDecimal getTotalSumado() {
		return totalSumado;
	}

	public void setTotalSumado(BigDecimal totalSumado) {
		this.totalSumado = totalSumado;
	}

	public BigDecimal getTotalRestado() {
		return totalRestado;
	}

	public void setTotalRestado(BigDecimal totalRestado) {
		this.totalRestado = totalRestado;
	}

	public BigDecimal getCreditosContrato() {
		return creditosContrato;
	}

	public void setCreditosContrato(BigDecimal creditosContrato) {
		this.creditosContrato = creditosContrato;
	}

	public String getTipoCredito() {
		return tipoCredito;
	}

	public void setTipoCredito(String tipoCredito) {
		this.tipoCredito = tipoCredito;
	}

	public byte[] getPdf() {
		return pdf;
	}

	public void setPdf(byte[] pdf) {
		this.pdf = pdf;
	}

	public Map<String, Object> getAttachments() {
		return attachments;
	}
	
	public void addAttachment(String key, Object value) {
		if (null == attachments){
			attachments = new HashMap<String, Object>();
		}
		attachments.put(key, value);
	}


	public void setAttachments(Map<String, Object> attachments) {
		this.attachments = attachments;
	}

	public Object getAttachment(String key) {
		if (null == attachments){
			return null;
		}
		else{
			return attachments.get(key);
		}
	}
	
	public void addMensajeALista(String mensaje){
		if (listaMensajes== null){
			listaMensajes = new ArrayList<String>();
		}
		listaMensajes.add(mensaje);
	}

	public File getFichero() {
		return fichero;
	}

	public void setFichero(File fichero) {
		this.fichero = fichero;
	}

	public String getNombreFichero() {
		return nombreFichero;
	}

	public void setNombreFichero(String nombreFichero) {
		this.nombreFichero = nombreFichero;
	}

	public String getNombreArchivoDescarga() {
		return nombreArchivoDescarga;
	}

	public void setNombreArchivoDescarga(String nombreArchivoDescarga) {
		this.nombreArchivoDescarga = nombreArchivoDescarga;
	}

	
}
