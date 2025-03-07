package org.gestoresmadrid.oegam2comun.registradores.utiles;

public class Firmable {
	private String nombre; // Nombre del fichero a firmar
	private String formato; // Formato del fichero a firmar
	private String contenido; // Contenido del fichero a firmar
	private String sign; // Firma
	private String timestamp; // Timestamp

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getFormato() {
		return formato;
	}

	public void setFormato(String formato) {
		this.formato = formato;
	}

	public String getContenido() {
		return contenido;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

}
