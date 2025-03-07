package org.gestoresmadrid.core.proceso.model.enumerados;

public enum RolUsuarioGestProceso {

	ROL_ADMIN("ROL_ADMIN", "ROL ADMIN") {
		public String toString() {
			return "TODOS";
		}
	},
	ROL_USER("ROL_USER", "ROL USUARIO") {
		public String toString() {
			return "TRÁFICO";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private RolUsuarioGestProceso(String valorEnum, String nombreEnum) {
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

	public static RolUsuarioGestProceso convertir(String valorEnum) {
		if (valorEnum != null && !valorEnum.isEmpty()) {
			for (RolUsuarioGestProceso patron : RolUsuarioGestProceso.values()) {
				if (patron.getValorEnum().equals(valorEnum)) {
					return patron;
				}
			}
		}
		return null;
	}

	public static String convertirTexto(String valor) {
		if (valor != null) {
			for (RolUsuarioGestProceso patron : RolUsuarioGestProceso.values()) {
				if (patron.getValorEnum().equals(valor)) {
					return patron.getNombreEnum();
				}
			}
		}
		return null;
	}

	public static String convertirNombre(String nombre) {
		if (nombre != null) {
			for (RolUsuarioGestProceso patron : RolUsuarioGestProceso.values()) {
				if (patron.getNombreEnum().equals(nombre)) {
					return patron.getValorEnum();
				}
			}
		}
		return null;
	}

	public static String convertirTexto(RolUsuarioGestProceso patronPro) {
		if (patronPro != null) {
			for (RolUsuarioGestProceso patron : RolUsuarioGestProceso.values()) {
				if (patron.getValorEnum().equals(patronPro.getValorEnum())) {
					return patron.getNombreEnum();
				}
			}
		}
		return null;
	}
}
