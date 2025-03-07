package org.gestoresmadrid.core.consultasTGate.model.enumerados;

public enum OrigenTGate {

	ImpresionTransmision("IMPRESION TRANSMISION", "Impresi�n Transmisi�n") {
		public String toString() {
			return "IMPRESION TRANSMISION";
		}
	},
	ImpresionBaja("IMPRESION BAJA", "Impresion Baja") {
		public String toString() {
			return "IMPRESION BAJA";
		}
	},
	ProcesoInteve("PROCESO INTEVE", "Proceso Inteve") {
		public String toString() {
			return "PROCESO INTEVE";
		}
	},
	ProcesoNuevoInteve("PROCESO INTEVE NUEVO", "Proceso Inteve Nuevo") {
		public String toString() {
			return "PROCESO INTEVE NUEVO";
		}
	},
	ObtenerMatriculaConsulta("OBTENER MATRICULA CONSULTA", "Obtener Matr�cula Consulta") {
		public String toString() {
			return "OBTENER MATRICULA CONSULTA";
		}
	},
	ObtenerMatricula("OBTENER MATRICULA", "Obtener Matr�cula") {
		public String toString() {
			return "OBTENER MATRICULA";
		}
	},
	ConsultaGest("CONSULTA GEST", "Consulta Gest") {
		public String toString() {
			return "CONSULTA GEST";
		}
	},
	SolicitudAvpo("SOLICITUD AVPO", "Solicitud Avpo") {
		public String toString() {
			return "SOLICITUD AVPO";
		}
	},
	SolicitudGestImpresion("SOLICITUD GEST IMPRESION", "Solicitud Gest Impresion") {
		public String toString() {
			return "SOLICITUD GEST IMPRESION";
		}
	},
	;

	private String valorEnum;
	private String nombreEnum;

	private OrigenTGate(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	public static String convertirTexto(String valorEnum) {
		for (OrigenTGate element : OrigenTGate.values()) {
			if (element.valorEnum.equals(valorEnum)) {
				return element.nombreEnum;
			}
		}
		return null;
	}
}
