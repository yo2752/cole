package org.gestoresmadrid.oegam2comun.notario.view.beans;

import java.io.Serializable;

import org.gestoresmadrid.core.annotations.FilterSimpleExpression;
import org.gestoresmadrid.core.annotations.enumeration.FilterSimpleExpressionRestriction;

public class NotarioFilterBean implements Serializable{

	private static final long serialVersionUID = 5559057162774567329L;
	
	@FilterSimpleExpression(name="codigoNotario")
	private String codigoNotario;
	
	@FilterSimpleExpression(name="codigoNotaria")
	private String codigoNotaria;
	
	@FilterSimpleExpression(name="nombre", restriction = FilterSimpleExpressionRestriction.LIKE)
	private String nombre;
	
	@FilterSimpleExpression(name="apellidos", restriction = FilterSimpleExpressionRestriction.LIKE)
	private String apellidos;

	public String getCodigoNotario() {
		return codigoNotario;
	}

	public void setCodigoNotario(String codigoNotario) {
		this.codigoNotario = codigoNotario;
	}

	public String getCodigoNotaria() {
		return codigoNotaria;
	}

	public void setCodigoNotaria(String codigoNotaria) {
		this.codigoNotaria = codigoNotaria;
	}

	public String getNombre() {
		if(nombre != null && !nombre.isEmpty()){
			if(nombre.contains("%")) {
				nombre = nombre.replace("%", "");
			}
		}
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		if(apellidos != null && !apellidos.isEmpty()){
			if(apellidos.contains("%")) {
				apellidos = apellidos.replace("%", "");
			}
		}
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	
	
}
