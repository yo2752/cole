package org.gestoresmadrid.core.model.enumerados;

import java.util.ArrayList;
import java.util.List;

public enum MotivoBaja {

	TempV("1", "Temporal voluntaria.", "BAJA TEMPORAL VOLUNTARIA") {
		public String toString() {
			return "1";
		}
	},
	TempS("2", "Temporal sustracción.", "BAJA TEMPORAL SUSTRACCIÓN") {
		public String toString() {
			return "2";
		}
	},
	TempFCA("3", "Temporal finalizado contrato arrendamiento.", "") {
		public String toString() {
			return "3";
		}
	},
	TempT("4", "Temporal transferencia.", "") {
		public String toString() {
			return "4";
		}
	},
	DefV("5", "Definitiva voluntaria.", "BAJA DEFINITIVA VOLUNTARIA") {
		public String toString() {
			return "5"; // Exento de tasa si tiene más de 15 años
		}
	},
	DefRP("6", "Definitiva renovación parque.", "") {
		public String toString() {
			return "6";
		}
	},
	DefE("7", "Definitiva exportación.", "BAJA DEFINITIVA EXPORTACION") {
		public String toString() {
			return "7"; // Exento de tasa si tiene más de 15 años
		}
	},
	DefTC("8", "Definitiva tránsito comunitario.", "BAJA DEFINITIVA TRANSITO COMUNITARIO") {
		public String toString() {
			return "8"; // Exento de tasa si tiene más de 15 años
		}
	},
	DefA("9", "Alta baja voluntaria.", "ALTA BAJA VOLUNTARIA") {
		public String toString() {
			return "9"; // Exento de tasa si tiene más de 15 años
		}
	},
	TranCTIT("10", "Tránsito posterior CTIT.", "BAJA TRANSITO POSTERIOR CTIT") {
		public String toString() {
			return "10";
		}
	},
	ExpCTIT("11", "Exportación posterior CTIT.", "BAJA EXPORTACION POSTERIOR CTIT") {
		public String toString() {
			return "11";
		}
	},
	DefCT("12", "Definitiva carta DGT.", "BAJA DEFINITIVA CARTA DGT") {
		public String toString() {
			return "12"; // Exento de tasa si tiene carta DGT
		}
	},
	TempR("13", "Renovación baja temporal.", "RENOVACIÓN BAJA TEMP") {
		public String toString() {
			return "13";
		}
	};

	private String valorEnum;
	private String nombreEnum;
	private String descExcel;

	private MotivoBaja(String valorEnum, String nombreEnum, String descExcel) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
		this.descExcel = descExcel;
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	public String getDescExcel() {
		return descExcel;
	}

	public static List<MotivoBaja> getMotivosSinDefinitivaVoluntaria() {
		List<MotivoBaja> motivoBajas = new ArrayList<>();
		int i = 0;
		for (MotivoBaja motivo : MotivoBaja.values()) {
			if (!motivo.getValorEnum().equals(MotivoBaja.DefV.getValorEnum())) {
				motivoBajas.add(i++, motivo);
			}
		}
		return motivoBajas;
	}

	public static List<MotivoBaja> getMotivosSinPosteriorCtit() {
		List<MotivoBaja> motivoBajas = new ArrayList<>();
		int i = 0;
		for (MotivoBaja motivo : MotivoBaja.values()) {
			if (!motivo.getValorEnum().equals(MotivoBaja.TranCTIT.getValorEnum())
					&& !motivo.getValorEnum().equals(MotivoBaja.ExpCTIT.getValorEnum())) {
				motivoBajas.add(i++, motivo);
			}
		}
		return motivoBajas;
	}

	// valueOf
	public static MotivoBaja convertir(String valorEnum) {
		if (valorEnum != null && !valorEnum.isEmpty()) {
			for (MotivoBaja motivo : MotivoBaja.values()) {
				if (motivo.getValorEnum().equals(valorEnum)) {
					return motivo;
				}
			}
		}
		return null;
	}

	public static String convertirNombre(String nombreEnum) {
		if (nombreEnum != null && !nombreEnum.isEmpty()) {
			for (MotivoBaja motivo : MotivoBaja.values()) {
				if (motivo.getNombreEnum().equals(nombreEnum)) {
					return motivo.getValorEnum();
				}
			}
		}
		return null;
	}

	public static String convertirTexto(String valorEnum) {
		if (valorEnum != null && !valorEnum.isEmpty()) {
			for (MotivoBaja motivo : MotivoBaja.values()) {
				if (motivo.getValorEnum().equals(valorEnum)) {
					return motivo.getNombreEnum();
				}
			}
		}
		return null;
	}

	public static String convertirMotivosBtvXml(String motivo) {
		if (MotivoBaja.DefE.getValorEnum().equals(motivo)) {
			return "1";
		} else if (MotivoBaja.DefTC.getValorEnum().equals(motivo)) {
			return "2";
		}
		return null;
	}

	public static String getDescBajaPorTipo(String tipoBaja) {
		String descripcion = "";
		if (tipoBaja != null && !tipoBaja.isEmpty()) {
			for (MotivoBaja motivobaja : MotivoBaja.values()) {
				if (motivobaja.getValorEnum().equals(tipoBaja)) {
					descripcion = motivobaja.getDescExcel();
					break;
				}
			}
			if (descripcion != null && descripcion.isEmpty()) {
				return MotivoBaja.DefA.descExcel;
			}
		}
		return descripcion;
	}

	public static Boolean esMotivoBajaCorrecto(String motivoBaja) {
		if (convertir(motivoBaja) != null) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	public static String tipoTemporalDefinitiva(String motivoBaja) {
		if (MotivoBaja.TempV.getValorEnum().equals(motivoBaja) || MotivoBaja.TempS.getValorEnum().equals(motivoBaja)
				|| MotivoBaja.TempFCA.getValorEnum().equals(motivoBaja)
				|| MotivoBaja.TempT.getValorEnum().equals(motivoBaja)
				|| MotivoBaja.TempR.getValorEnum().equals(motivoBaja)) {
			return "Temporal";
		} else {
			return "Definitiva";
		}
	}

}