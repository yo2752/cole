package org.gestoresmadrid.core.consultaDev.model.enumerados;

import java.math.BigDecimal;

public enum EstadoSuscripcion {

	SUSCRITO("A", "Usuario suscrito"),
	NO_SUSCRITO("B","Usuario no suscrito");
	
	private String valorEnum;
	private String nombreEnum;
	
	private EstadoSuscripcion(String valorEnum, String nombreEnum) {
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
	
	public static EstadoSuscripcion convertir(String valorEnum) {
		if(valorEnum != null && !valorEnum.isEmpty()){
			for(EstadoSuscripcion estado : EstadoSuscripcion.values()){
				if(estado.getValorEnum().equals(valorEnum)){
					return estado;
				}
			}
		}
		return null;
	}
	
	public static String convertirTexto(String valor){
		if(valor != null){
			for(EstadoSuscripcion estado : EstadoSuscripcion.values()){
				if(estado.getValorEnum().equals(valor)){
					return estado.getNombreEnum();
				}
			}
		}
		return null;
	}
	
	public static String convertirNombre(String nombre){
		if(nombre != null){
			for(EstadoSuscripcion estado : EstadoSuscripcion.values()){
				if(estado.getNombreEnum().equals(nombre)){
					return estado.getValorEnum();
				}
			}
		}
		return null;
	}
	
	public static String convertirTexto(EstadoSuscripcion est){
		if(est != null){
			for(EstadoSuscripcion estado : EstadoSuscripcion.values()){
				if(estado.getValorEnum() == est.getValorEnum()){
					return estado.getNombreEnum();
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
