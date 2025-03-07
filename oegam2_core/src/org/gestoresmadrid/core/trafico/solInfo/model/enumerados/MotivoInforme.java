package org.gestoresmadrid.core.trafico.solInfo.model.enumerados;

import java.math.BigDecimal;

public enum MotivoInforme {

	POSIBLE_ADQUISICION("0", "Posible adquisición del vehículo") {
		public String toString() {
			return "Posible adquisición del vehículo";
		}
	},
	CERTIFICADO_NO_TITULARIDAD("1", "Certificado de no titularidad del vehículo para otras administraciones y entidades") {
		public String toString() {
			return "Certificado de no titularidad del vehículo para otras administraciones y entidades";
		}
	},
	VERIFICACION_DATOS_TECNICOS("2", "Verificación de datos técnicos y administrativos del vehículo") {
		public String toString() {
			return "Verificación de datos técnicos y administrativos del vehículo";
		}
	},
	INVESTIGACION_SINIESTRO_COLISION("3", "Investigación de vehículos implicados en siniestro o colisión") {
		public String toString() {
			return "Investigación de vehículos implicados en siniestro o colisión";
		}

	},
	ABANDONO_VIA_PUBLICA("4", "Abandono del vehículo en la vía pública") {
		public String toString() {
			return "Abandono del vehículo en la vía pública";
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