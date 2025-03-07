package org.gestoresmadrid.core.vehiculo.model.enumerados;

import java.math.BigDecimal;

public enum VehiculoEITVEnum {

	Importado("1", "Importado"){
		public String toString() {
			return "1";
		}
	},Activado("2", "Activado"){
		public String toString() {
			return "2";
		}
	},Pdt_Resultado("3", "Pendiente de Resultado"){
		public String toString() {
			return "3";
		}
	},Finalizado_OK("4", "Finalizado OK"){
		public String toString() {
			return "4";
		}
	},Finalizado_ERROR("5", "Finalizado con Error"){
		public String toString() {
			return "3";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private VehiculoEITVEnum(String valorEnum, String nombreEnum) {
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

	public static VehiculoEITVEnum convertir(String valorEnum) {
		if(valorEnum != null && !valorEnum.isEmpty()){
			for(VehiculoEITVEnum estado : VehiculoEITVEnum.values()){
				if(estado.getValorEnum().equals(valorEnum)){
					return estado;
				}
			}
		}
		return null;
	}

	public static String convertirTexto(String valor){
		if(valor != null){
			for(VehiculoEITVEnum estado : VehiculoEITVEnum.values()){
				if(estado.getValorEnum().equals(valor)){
					return estado.getNombreEnum();
				}
			}
		}
		return null;
	}
	
	public static String convertirNombre(String nombre){
		if(nombre != null){
			for(VehiculoEITVEnum estado : VehiculoEITVEnum.values()){
				if(estado.getNombreEnum().equals(nombre)){
					return estado.getValorEnum();
				}
			}
		}
		return null;
	}

	public static String convertirTexto(VehiculoEITVEnum estadoDev){
		if(estadoDev != null){
			for(VehiculoEITVEnum estado : VehiculoEITVEnum.values()){
				if(estado.getValorEnum() == estadoDev.getValorEnum()){
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