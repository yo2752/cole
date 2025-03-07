package org.oegam.gestor.distintivos.enumerates;

public enum EstadoJob {
	Iniciado("0", "Iniciado"),
	Descargado("1", "Descargado"),
	Impreso("2", "Impreso"),
	Erroneo("3", "Erroneo");

	private String valorEnum;
	private String nombreEnum;

	private EstadoJob(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public static EstadoJob convertir(Long valorEnum) {
		if (valorEnum != null) {
			for (EstadoJob estado : EstadoJob.values()) {
				if (Integer.parseInt(estado.getValorEnum()) == valorEnum.intValue()) {
					return estado;
				}
			}
		}
		return null;
	}

	public static String convertirTexto(Long valor) {
		if (valor != null) {
			for (EstadoJob estado : EstadoJob.values()) {
				boolean igual = true;
				try {
					igual = (Integer.parseInt(estado.getValorEnum()) == valor.intValue());
					if (igual) return estado.getNombreEnum();
				} catch (Exception ex) {
					// TODO poner log
					//log.debug("No se ha podido parsear");
				}
			}
		}
		return null;
	}

	public static Long convertirNombre(String nombre) {
		if (nombre != null) {
			for (EstadoJob estado : EstadoJob.values()) {
				if (estado.getNombreEnum().equals(nombre)) {
					return new Long(estado.getValorEnum());
				}
			}
		}
		return null;
	}

	public static String convertirTexto(EstadoJob estadoJob) {
		if (estadoJob != null) {
			for (EstadoJob estado : EstadoJob.values()) {
				if (estado.getValorEnum() == estadoJob.getValorEnum()) {
					return estado.getNombreEnum();
				}
			}
		}
		return null;
	}

	public static String convertirEstadoLong(Long estado) {
		if (estado != null) {
			return convertirTexto(estado);
		}
		return "";
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

}