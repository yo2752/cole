package org.gestoresmadrid.oegamComun.solicitudNRE06.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultadoSolicitudNRE06Bean implements Serializable{

	private static final long serialVersionUID = -5635162724941511506L;
	
	private Boolean error;
	private String mensaje;
	private String resultado;
	private Integer numOk;
	private Integer numError;
	private List<String> listaOk;
	private List<String> listaError;
	private Boolean esRecuperable;
	private Throwable exception;
	private Map<String, Object>	attachments;
	private String fecha;
	private String hora;
	private String codElect;
	private String nre;
	private String reg;
	
	public static final String TIPO_PDF = "pdf";
	public static final String TIPO_FILE = "file";
	public static final String NOMBRE_FICHERO = "nombreFichero";

	public ResultadoSolicitudNRE06Bean(Boolean error) {
		this.error = error;
		this.listaOk = new ArrayList<String>();
		this.listaError = new ArrayList<String>();
		this.numOk = 0;
		this.numError = 0;
		this.esRecuperable = Boolean.FALSE;
	}
	
	public void addListaOk(String mensaje){
		if(listaOk == null || listaOk.isEmpty()){
			listaOk = new ArrayList<String>();
		}
		listaOk.add(mensaje);
	}
	
	public void addNumOk(){
		numOk++;
	}
	
	public void addListaError(String mensaje){
		if(listaError == null || listaError.isEmpty()){
			listaError = new ArrayList<String>();
		}
		listaError.add(mensaje);
	}
	
	public void addNumError(){
		numError++;
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
	public void addOk(){
		this.numOk++;
	}
	
	public void addError(){
		this.numError++;
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
	public String getResultado() {
		return resultado;
	}
	public void setResultado(String resultado) {
		this.resultado = resultado;
	}
	public Boolean getEsRecuperable() {
		return esRecuperable;
	}
	public void setEsRecuperable(Boolean esRecuperable) {
		this.esRecuperable = esRecuperable;
	}

	public Throwable getException() {
		return exception;
	}

	public void setException(Throwable exception) {
		this.exception = exception;
	}

	public Map<String, Object> getAttachments() {
		return attachments;
	}
	public Object getAttachment(String key) {
		if (null == attachments){
			return null;
		}
		else{
			return attachments.get(key);
		}
	}

	public void setAttachments(Map<String, Object> attachments) {
		this.attachments = attachments;
	}
	
	public void addAttachment(String key, Object value) {
		if (null == attachments){
			attachments = new HashMap<String, Object>();
		}
		attachments.put(key, value);
	}

	public static String getTipoPdf() {
		return TIPO_PDF;
	}

	public static String getTipoFile() {
		return TIPO_FILE;
	}

	public static String getNombreFichero() {
		return NOMBRE_FICHERO;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public String getCodElect() {
		return codElect;
	}

	public void setCodElect(String codElect) {
		this.codElect = codElect;
	}

	public String getNre() {
		return nre;
	}

	public void setNre(String nre) {
		this.nre = nre;
	}

	public String getReg() {
		return reg;
	}

	public void setReg(String reg) {
		this.reg = reg;
	}
}
