package org.gestoresmadrid.oegam2comun.presentacion.jpt.enumerados;

public enum JefaturasJPTNuevoEnum {

	MADRID("M", "MADRID") {
		public String toString() {
			return "Madrid";
		}
	},
	ALCORCON("M1", "ALCORCON") {
		public String toString() {
			return "Alcorcon";
		}
	},
	SEGOVIA("SG", "SEGOVIA") {
		public String toString() {
			return "Segovia";
		}
	},
	AVILA("AV", "AVILA") {
		public String toString() {
			return "Avila";
		}
	},
	CUENCA("CU", "CUENCA") {
		public String toString() {
			return "Cuenca";
		}
	},
	CIUDADREAL("CR", "CIUDAD REAL") {
		public String toString() {
			return "Ciudad Real";
		}
	},
	GUADALAJARA("GU", "GUADALAJARA") {
		public String toString() {
			return "Guadalajara";
		}
	};

	private JefaturasJPTNuevoEnum(String jefatura, String descripcion) {
		this.jefatura = jefatura;
		this.descripcion = descripcion;
	}

	private String jefatura;
	private String descripcion;

	public String getJefatura() {
		return jefatura;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public static String convertirJefatura(String codigo) {
		String jefatura = null;
		if (codigo == null) {
			return null;
		} else if (codigo.equals("M")) {
			jefatura = JefaturasJPTEnum.MADRID.getDescripcion();
		} else if (codigo.equals("M1")) {
			jefatura = JefaturasJPTEnum.ALCORCON.getDescripcion();
		} else if (codigo.equals("SG")) {
			jefatura = JefaturasJPTEnum.SEGOVIA.getDescripcion();
		} else if (codigo.equals("AV")) {
			jefatura = JefaturasJPTEnum.AVILA.getDescripcion();
		} else if (codigo.equals("CU")) {
			jefatura = JefaturasJPTEnum.CUENCA.getDescripcion();
		} else if (codigo.equals("CR")) {
			jefatura = JefaturasJPTEnum.CIUDADREAL.getDescripcion();
		} else if (codigo.equals("GU")) {
			jefatura = JefaturasJPTEnum.GUADALAJARA.getDescripcion();
		}

		return jefatura;
	}
}
