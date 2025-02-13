package org.oegam.gestorcorreos.beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 * Bean que encapsula el resultado de una operacion
 * @author TB_Solutions
 */
public class ResultBean {
	
	public static final String TIPO_PDF = "pdf";
	public static final String TIPO_FILE = "file";
	public static final String NOMBRE_FICHERO = "nombreFichero";
	
	private Boolean 			error;
	private String 				mensaje;
	private List<String>		listaMensajes;
	private Map<String, Object>	attachments;
	
	public ResultBean(){
		init();
	}
	
	public ResultBean(Boolean error) {
		this();
		setError(error);
	}
	
	/**
	 * El mensaje se anade a la lista, no al atributo mensaje
	 * @param error
	 * @param mensaje
	 */
	public ResultBean(Boolean error, String mensaje){
		this();
		setError(error);
		if (mensaje!=null){
			addMensajeALista(mensaje);
		}
	}
	
	

	public void init() {
		setError(Boolean.FALSE);
		setListaMensajes(new ArrayList<String>());
	}

	/**
	 * Devuelve TRUE si hay error, en caso contrario, devuelve FALSE.
	 * Equivalente a isError()
	 * @return boolean
	 */
	public Boolean getError() {
		return this.error;
	}

	/**
	 * Establece esta variable a TRUE si ha ocurrido un error, en caso contrario, deberia ser FALSE
	 * @param error
	 */
	public void setError(Boolean error) {
		this.error = error;
	}
	
	public String getMensaje(){
		return this.mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
		if(this.listaMensajes == null){
			listaMensajes = new ArrayList<String>();
		}
		this.listaMensajes.add(mensaje);
	}
	
	public List<String> getListaMensajes() {
		return this.listaMensajes;
	}

	public void setListaMensajes(List<String> listaMensajes) {
		this.listaMensajes = listaMensajes;
	}
	
	public void addMensajeALista(String mensaje){
		if (listaMensajes== null){
			listaMensajes = new ArrayList<String>();
		}
		listaMensajes.add(mensaje);
	}

	/**
	 * Add an attachment
	 * @param key
	 * @param value
	 */
	public void addAttachment(String key, Object value) {
		if (null == attachments){
			attachments = new HashMap<String, Object>();
		}
		attachments.put(key, value);
	}

	/**
	 * @return an attachment
	 */
	public Object getAttachment(String key) {
		if (null == attachments){
			return null;
		}
		else{
			return attachments.get(key);
		}
	}

	/**
	 * Anadimos un mensaje a la lista
	 * @param listaMensajes
	 */
	public void addListaMensajes(List<String> listaMensajes){
		for (String msg : listaMensajes){
			this.listaMensajes.add(msg);
		}
	}
	
	public void addError(String error){
		this.error=Boolean.TRUE;
		addMensajeALista(error);
	}
}
