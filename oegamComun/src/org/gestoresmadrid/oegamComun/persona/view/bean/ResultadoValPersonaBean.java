package org.gestoresmadrid.oegamComun.persona.view.bean;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

public class ResultadoValPersonaBean implements Serializable{

	private static final long serialVersionUID = -1626530249584279241L;
	
	Boolean esModificada;
	Boolean error;
	String mensaje;
	String textoModificado;
	String apellidoAnt;
	String apellidoNue;
	String apellido2Ant;
	String apellido2Nue;
	String nombreAnt;
	String nombreNue;
	Date fechaNacimientoAnt;
	Date fechaNacimientoNue;
	
	
	public void addTextoModificado(String texto){
		if(StringUtils.isBlank(textoModificado)){
			textoModificado = "";
		}
		textoModificado += texto;
	}

	public ResultadoValPersonaBean(Boolean error) {
		super();
		this.error = error;
		this.esModificada = Boolean.FALSE;
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

	public String getTextoModificado() {
		return textoModificado;
	}

	public void setTextoModificado(String textoModificado) {
		this.textoModificado = textoModificado;
	}

	public Boolean getEsModificada() {
		return esModificada;
	}

	public void setEsModificada(Boolean esModificada) {
		this.esModificada = esModificada;
	}

	public String getApellidoAnt() {
		return apellidoAnt;
	}

	public void setApellidoAnt(String apellidoAnt) {
		this.apellidoAnt = apellidoAnt;
	}

	public String getApellidoNue() {
		return apellidoNue;
	}

	public void setApellidoNue(String apellidoNue) {
		this.apellidoNue = apellidoNue;
	}

	public String getApellido2Ant() {
		return apellido2Ant;
	}

	public void setApellido2Ant(String apellido2Ant) {
		this.apellido2Ant = apellido2Ant;
	}

	public String getApellido2Nue() {
		return apellido2Nue;
	}

	public void setApellido2Nue(String apellido2Nue) {
		this.apellido2Nue = apellido2Nue;
	}

	public Date getFechaNacimientoAnt() {
		return fechaNacimientoAnt;
	}

	public void setFechaNacimientoAnt(Date fechaNacimientoAnt) {
		this.fechaNacimientoAnt = fechaNacimientoAnt;
	}

	public Date getFechaNacimientoNue() {
		return fechaNacimientoNue;
	}

	public void setFechaNacimientoNue(Date fechaNacimientoNue) {
		this.fechaNacimientoNue = fechaNacimientoNue;
	}

	public String getNombreAnt() {
		return nombreAnt;
	}

	public void setNombreAnt(String nombreAnt) {
		this.nombreAnt = nombreAnt;
	}

	public String getNombreNue() {
		return nombreNue;
	}

	public void setNombreNue(String nombreNue) {
		this.nombreNue = nombreNue;
	}
}
