package org.gestoresmadrid.core.consultaDev.model.enumerados;

import java.math.BigDecimal;

public enum ResultadoErrorConsultaDev {

	ERROR_WS_0001("0001","Error gen�rico WS. El formato de alguno de los campos de la petici�n tiene un formato err�neo."),
	ERROR_WS_0002("0002","Error gen�rico WS. El valor de alguno de los campos de la petici�n tiene un valor err�neo."),
	ERROR_WS_0004("0004","Error gen�rico WS. Par�metro incorrecto. < Se indica el par�metro incorrecto>"),
	ERROR_WS_0005("0005","Error gen�rico WS. Error generando la respuesta."),
	ERROR_WS_0006("0006","Error gen�rico WS. Solicitud con un Identificador de Petici�n repetido."),
	ERROR_WS_0007("0007","Error gen�rico WS. El organismo solicitante no tiene permisos para realizar esta petici�n."),
	ERROR_WS_0008("0008","Error gen�rico WS. La aplicaci�n no pertenece al organismo solicitante."),
	ERROR_WS_0009("0009","Error gen�rico WS. La aplicaci�n no tiene permisos para realizar esta petici�n."),
	ERROR_WS_0011("0011","Error gen�rico WS. Error interno debido a problemas con el gestor de BD, o problemas de red."),
	ERROR_WS_0012("0012","Error datos de entrada. El identificador de usuario es incorrecto incorrectos."),
	ERROR_WS_0056("0056","Error gen�rico WS. No se pueden consultar la suscripci�n para personas f�sicas."),
	OK_WS("0003","OK");
	
	private String valorEnum;
	private String nombreEnum;
	
	private ResultadoErrorConsultaDev(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public void setValorEnum(String valorEnum) {
		this.valorEnum = valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	public void setNombreEnum(String nombreEnum) {
		this.nombreEnum = nombreEnum;
	}
	
	public static ResultadoErrorConsultaDev convertir(String valorEnum) {
		if(valorEnum != null && !valorEnum.isEmpty()){
			for(ResultadoErrorConsultaDev res : ResultadoErrorConsultaDev.values()){
				if(res.getValorEnum().equals(valorEnum)){
					return res;
				}
			}
		}
		return null;
	}
	
	public static String convertirTexto(String valor){
		if(valor != null){
			for(ResultadoErrorConsultaDev res : ResultadoErrorConsultaDev.values()){
				if(res.getValorEnum().equals(valor)){
					return res.getNombreEnum();
				}
			}
		}
		return null;
	}
	
	public static String convertirNombre(String nombre){
		if(nombre != null){
			for(ResultadoErrorConsultaDev res : ResultadoErrorConsultaDev.values()){
				if(res.getNombreEnum().equals(nombre)){
					return res.getValorEnum();
				}
			}
		}
		return null;
	}
	
	public static String convertirTexto(ResultadoErrorConsultaDev result){
		if(result != null){
			for(ResultadoErrorConsultaDev res : ResultadoErrorConsultaDev.values()){
				if(res.getValorEnum() == result.getValorEnum()){
					return res.getNombreEnum();
				}
			}
		}
		return null;
	}
	
	public static String convertirEstadoBigDecimal(BigDecimal estado){
		if(estado != null){
			return convertirTexto(estado.toString());
		}
		return "";
	}
	
}
