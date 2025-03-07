package org.gestoresmadrid.core.consultaDev.model.enumerados;

import java.math.BigDecimal;

public enum ResultadoErrorConsultaDev {

	ERROR_WS_0001("0001","Error genérico WS. El formato de alguno de los campos de la petición tiene un formato erróneo."),
	ERROR_WS_0002("0002","Error genérico WS. El valor de alguno de los campos de la petición tiene un valor erróneo."),
	ERROR_WS_0004("0004","Error genérico WS. Parámetro incorrecto. < Se indica el parámetro incorrecto>"),
	ERROR_WS_0005("0005","Error genérico WS. Error generando la respuesta."),
	ERROR_WS_0006("0006","Error genérico WS. Solicitud con un Identificador de Petición repetido."),
	ERROR_WS_0007("0007","Error genérico WS. El organismo solicitante no tiene permisos para realizar esta petición."),
	ERROR_WS_0008("0008","Error genérico WS. La aplicación no pertenece al organismo solicitante."),
	ERROR_WS_0009("0009","Error genérico WS. La aplicación no tiene permisos para realizar esta petición."),
	ERROR_WS_0011("0011","Error genérico WS. Error interno debido a problemas con el gestor de BD, o problemas de red."),
	ERROR_WS_0012("0012","Error datos de entrada. El identificador de usuario es incorrecto incorrectos."),
	ERROR_WS_0056("0056","Error genérico WS. No se pueden consultar la suscripción para personas físicas."),
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
