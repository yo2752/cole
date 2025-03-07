package org.gestoresmadrid.oegam2comun.trafico.testra.view.beans;

public enum TipoConsultaTestraEnum {

	M("M", "Matrícula"),
	C("C", "CIF/NIF");

	private TipoConsultaTestraEnum(String clave, String descripcion) {
		this.clave = clave;
		this.descripcion = descripcion;
	}

	private String clave;
	private String descripcion;

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public static TipoConsultaTestraEnum getTipoConsulta(String clave) {
		for (TipoConsultaTestraEnum e : TipoConsultaTestraEnum.values()) {
			if (e.getClave().equals(clave))
				return e;
		}
		return null;
	}

	public static String convertirTexto(String clave) {
		TipoConsultaTestraEnum e = getTipoConsulta(clave);
		return e != null ? e.getDescripcion() : null;
	}

}
