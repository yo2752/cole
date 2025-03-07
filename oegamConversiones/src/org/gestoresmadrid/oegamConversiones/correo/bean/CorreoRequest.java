package org.gestoresmadrid.oegamConversiones.correo.bean;
import java.io.Serializable;
import java.util.List;

public class CorreoRequest implements Serializable{

	private static final long serialVersionUID = 8739460495743011008L;
	
	private String texto;
	private Boolean textoPlano;
	private String from;
	private String subject;
	private String recipient;
	private String copia;
	private String direccionesOcultas;
	private List<FicheroAdjunto> adjuntos;
	private String origen;
	
	/**
	 * @return the texto
	 */
	public String getTexto() {
		return texto;
	}
	/**
	 * @param texto the texto to set
	 */
	public void setTexto(String texto) {
		this.texto = texto;
	}
	/**
	 * @return the textoPlano
	 */
	public Boolean getTextoPlano() {
		return textoPlano;
	}
	/**
	 * @param textoPlano the textoPlano to set
	 */
	public void setTextoPlano(Boolean textoPlano) {
		this.textoPlano = textoPlano;
	}
	/**
	 * @return the from
	 */
	public String getFrom() {
		return from;
	}
	/**
	 * @param from the from to set
	 */
	public void setFrom(String from) {
		this.from = from;
	}
	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}
	/**
	 * @param subject the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}
	/**
	 * @return the recipient
	 */
	public String getRecipient() {
		return recipient;
	}
	/**
	 * @param recipient the recipient to set
	 */
	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}
	/**
	 * @return the copia
	 */
	public String getCopia() {
		return copia;
	}
	/**
	 * @param copia the copia to set
	 */
	public void setCopia(String copia) {
		this.copia = copia;
	}
	/**
	 * @return the direccionesOcultas
	 */
	public String getDireccionesOcultas() {
		return direccionesOcultas;
	}
	/**
	 * @param direccionesOcultas the direccionesOcultas to set
	 */
	public void setDireccionesOcultas(String direccionesOcultas) {
		this.direccionesOcultas = direccionesOcultas;
	}
	/**
	 * @return the adjuntos
	 */
	public List<FicheroAdjunto> getAdjuntos() {
		return adjuntos;
	}
	/**
	 * @param adjuntos the adjuntos to set
	 */
	public void setAdjuntos(List<FicheroAdjunto> adjuntos) {
		this.adjuntos = adjuntos;
	}
	/**
	 * @return the origen
	 */
	public String getOrigen() {
		return origen;
	}
	/**
	 * @param origen the origen to set
	 */
	public void setOrigen(String origen) {
		this.origen = origen;
	}
	
}
