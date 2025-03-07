package org.gestoresmadrid.core.enumerados;

public enum ProcesosAmEnum {

	CONSULTA_EITV("CONSULTA_EITV_AM", "Consulta Eitv") {
		public String toString() {
			return "CONSULTA_EITV_AM";
		}
	},
	MATW("MATW_AM", "Matriculación") {
		public String toString() {
			return "MATW_AM";
		}
	},
	BTV("BAJAS_BTV_AM", "Proceso para la tramitación telemática de las bajas") {
		public String toString() {
			return "BAJAS_BTV_AM";
		}
	},
	FULLCTIT("FULL_CTIT_AM", "Full CTIT") {
		public String toString() {
			return "FULL_CTIT_AM";
		}
	},
	
	FULL_DUPLICADO("FULL_DUPLICADO_AM", "Full Duplicado") {
		public String toString() {
			return "FULL_DUPLICADO_AM";
		}
	},
	NOTIFICATIONCTIT("NOTIFICATION_CTIT_AM", "Notification CTIT") {
		public String toString() {
			return "NOTIFICATION_CTIT_AM";
		}
	},
	TRADECTIT("TRADE_CTIT_AM", "Trade CTIT") {
		public String toString() {
			return "TRADE_CTIT_AM";
		}
	},
	DSTV("DSTV_AM", "Solicitud de distintivos") {
		public String toString() {
			return "DSTV_AM";
		}
	},
	CHECK_CTIT("CHECK_CTIT_AM", "Check CTIT") {
		public String toString() {
			return "CHECK_CTIT_AM";
		}
	},
	CHECK_BTV("CHECK_BTV_AM", "Check BTV") {
		public String toString() {
			return "CHECK_BTV_AM";
		}
	},
	CHECK_DUPLICADO("CHECK_DUPLICADO_AM", "Check Duplicado") {
		public String toString() {
			return "CHECK_DUPLICADO_AM";
		}
	},
	NOTIFICACIONES_OK_MATW("NOTIF_MATW_OK_AM", "Notifiaciones Matw OK") {
		public String toString() {
			return "NOTIF_MATW_OK_AM";
		}
	},
	NOTIFICACIONES_ERR_MATW("NOTIF_MATW_ERR_AM", "Notifiaciones Matw Error") {
		public String toString() {
			return "NOTIF_MATW_ERR_AM";
		}
	},
	IMPR_NOCTURNO("IMPR_NOCTURNO_AM", "Proceso IMPR Nocturno") {
		public String toString() {
			return "IMPR_NOCTURNO_AM";
		}
	},IMP_PRM_MATW("IMP_PRM_MATW_AM", "Impresion de Permisos de MATW") {
		public String toString() {
			return "IMP_PRM_MATW_AM";
		}
	},
	IMP_PRM_CTIT("IMP_PRM_CTIT_AM", "Impresion de Permisos de CTIT") {
		public String toString() {
			return "IMP_PRM_CTIT_AM";
		}
	},
	IMP_FICHA("IMP_FICHA_AM", "Impresion de Fichas técnicas") {
		public String toString() {
			return "IMP_FICHA_AM";
		}
	},NOTIFICACIONES_IMPRESION_IMPR("NOTIF_IMPR_AM", "Impresion de Fichas técnicas") {
		public String toString() {
			return "NOTIF_IMPR_AM";
		}
	},GESTION_CREDITOS("GESTION_CREDITOS_AM", "Gestion Créditos") {
		public String toString() {
			return "GESTION_CREDITOS_AM";
		}
	};;

	private ProcesosAmEnum(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	private String valorEnum;
	private String nombreEnum;

	public String getNombreEnum() {
		return nombreEnum;
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public static ProcesosAmEnum convertir(String valorEnum) {
		for (ProcesosAmEnum element : ProcesosAmEnum.values()) {
			if (element.valorEnum.equals(valorEnum)) {
				return element;
			}
		}
		return null;
	}

	public static ProcesosAmEnum convertirPorNombreEnum(String nombreEnum) {
		for (ProcesosAmEnum element : ProcesosAmEnum.values()) {
			if (element.nombreEnum.equals(nombreEnum)) {
				return element;
			}
		}
		return null;
	}
}
