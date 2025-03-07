package org.gestoresmadrid.core.tasas.model.enumeration;

public enum FormatoTasa {
	ELECTRONICO(0, "Electrónico"),
	PEGATINA(1, "Pegatina");

	private int codigo;
	private String descripcion;

	private FormatoTasa(int codigo, String descripcion) {
		this.codigo = codigo;
		this.descripcion = descripcion;
	}

	public int getCodigo() {
		return codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public static FormatoTasa convertir(Integer codigo) {
		if (codigo != null) {
			for (FormatoTasa element : FormatoTasa.values()) {
				if (element.codigo == codigo.intValue()) {
					return element;
				}
			}
		}
		return null;
	}

	public static FormatoTasa convertir(String codigo) {
		return codigo != null ? convertir(Integer.parseInt(codigo)) : null;
	}

}
