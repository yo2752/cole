package org.gestoresmadrid.core.trafico.solInfo.model.enumerados;

import java.math.BigDecimal;

public enum MotivoInforme {

	POSIBLE_ADQUISICION("0", "Posible adquisici�n del veh�culo") {
		public String toString() {
			return "Posible adquisici�n del veh�culo";
		}
	},
	CERTIFICADO_NO_TITULARIDAD("1", "Certificado de no titularidad del veh�culo para otras administraciones y entidades") {
		public String toString() {
			return "Certificado de no titularidad del veh�culo para otras administraciones y entidades";
		}
	},
	VERIFICACION_DATOS_TECNICOS("2", "Verificaci�n de datos t�cnicos y administrativos del veh�culo") {
		public String toString() {
			return "Verificaci�n de datos t�cnicos y administrativos del veh�culo";
		}
	},
	INVESTIGACION_SINIESTRO_COLISION("3", "Investigaci�n de veh�culos implicados en siniestro o colisi�n") {
		public String toString() {
			return "Investigaci�n de veh�culos implicados en siniestro o colisi�n";
		}

	},
	ABANDONO_VIA_PUBLICA("4", "Abandono del veh�culo en la v�a p�blica") {
		public String toString() {
			return "Abandono del veh�culo en la v�a p�blica";
		}

	};

	private String nombreEnum;
	private String valorEnum;

	private MotivoInforme(String valorEnum, String nombreEnum) {
		this.nombreEnum = nombreEnum;
		this.valorEnum = valorEnum;
	}

	public static MotivoInforme convertir(String valorEnum) {
		if(valorEnum != null && !valorEnum.isEmpty()){
			for(MotivoInforme motivo : MotivoInforme.values()){
				if(motivo.getValorEnum().equals(valorEnum)){
					return motivo;
				}
			}
		}
		return null;
	}

	public static String convertirTexto(String valor){
		if(valor != null){
			for(MotivoInforme motivo : MotivoInforme.values()){
				if(motivo.getValorEnum().equals(valor)){
					return motivo.getNombreEnum();
				}
			}
		}
		return null;
	}

	public static String convertirNombre(String nombre){
		if(nombre != null){
			for(MotivoInforme motivo : MotivoInforme.values()){
				if(motivo.getNombreEnum().equals(nombre)){
					return motivo.getValorEnum();
				}
			}
		}
		return null;
	}

	public static String convertirTexto(MotivoInforme motivoInforme){
		if(motivoInforme != null){
			for(MotivoInforme motivo : MotivoInforme.values()){
				if(motivo.getValorEnum() == motivoInforme.getValorEnum()){
					return motivo.getNombreEnum();
				}
			}
		}
		return null;
	}

	public static String convertirEstadoBigDecimal(BigDecimal motivo){
		if(motivo != null){
			return convertirTexto(motivo.toString());
		}
		return "";
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	public void setNombreEnum(String nombreEnum) {
		this.nombreEnum = nombreEnum;
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public void setValorEnum(String valorEnum) {
		this.valorEnum = valorEnum;
	}
}