package org.gestoresmadrid.oegam2comun.registradores.enums;


/**
 * Posibles tipos de otros importes
 */
public enum TipoOtrosImportes {

	SEGURO_VIDA_CREDITO("1", "Seguro de vida/crédito") {
		@Override
		public String toString() {
			return "Seguro de vida/crédito";
		}
	},
	SEGURO_VEHICULO("2", "Seguro del vehículo") {
		@Override
		public String toString() {
			return "Seguro del vehículo";
		}
	},
	ESTUDIO_CONCESION_CREDITO("3", "Estudio Concesión del Crédito") {
		@Override
		public String toString() {
			return "Estudio Concesión del Crédito";
		}
	},
	SINIESTRO("4", "Siniestro") {
		@Override
		public String toString() {
			return "Siniestro";
		}
	},
	DESEMPLEO("5", "Desempleo") {
		@Override
		public String toString() {
			return "Desempleo";
		}
	},
	RECLAMACION_DEUDA_IMPAGADA("6", "Reclamación de deuda impagada") {
		@Override
		public String toString() {
			return "Comisión por Gastos de Reclamación de Posiciones Deudoras";
		}
	},
	SEGURO_PAGO_PROTEGIDO("7", "Seguro de pago protegido") {
		@Override
		public String toString() {
			return "Seguro de pago protegido";
		}
	},
	SEGURO_PERDIDA_TOTAL("8", "Seguro de pérdida total") {
		@Override
		public String toString() {
			return "Seguro de pérdida total";
		}
	},
	SEGURO_RETIRADA_CARNET("9", "Seguro de retirada de carnet") {
		@Override
		public String toString() {
			return "Seguro de retirada de carnet";
		}
	},
	SEGURO_AVERIAS("10", "Seguro de averías") {
		@Override
		public String toString() {
			return "Seguro de averías";
		}
	},
	EXTENSION_GARANTIA_MANTENIMIENTO("11",
			"Extensión de garantía y mantenimiento") {
		@Override
		public String toString() {
			return "Extensión de garantía y mantenimiento";
		}
	},
	;

	private String valorEnum;
	private String nombreEnum;

	private TipoOtrosImportes(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	public String getKey() {
		// name()
		return valorEnum;
	}

	@Override
	public String toString() {
		return nombreEnum;
	}

	public static String convertirTexto(String valorEnum) {
		for (TipoOtrosImportes element : TipoOtrosImportes.values()) {
			if (element.valorEnum.equals(valorEnum)) {
				return element.nombreEnum;
			}
		}
		return null;
	}
}
