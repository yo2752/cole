package org.gestoresmadrid.core.tasas.model.enumeration;

public enum FormaPago {
	CCC(0, "Cuenta Bancaria"){
	},
	IBAN(2, "IBAN"){
	},
	TARJETA(1, "Tarjeta"){
	};

	private int codigo;
	private String descripcion;
	
	private FormaPago(int codigo, String descripcion){
		this.codigo = codigo;
		this.descripcion = descripcion;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public static FormaPago convertir(Integer codigo) {
		if (codigo != null) {
			for (FormaPago element : FormaPago.values()) {
				if (element.codigo == codigo.intValue()) {
					return element;
				}
			}
		}
		return null;
	}

	public static FormaPago convertir(String codigo) {
		return codigo != null ? convertir(Integer.parseInt(codigo)) : null;
	}

	
	public static String convertirTexto(Integer codigo){
		if (codigo != null) {
			for (FormaPago element : FormaPago.values()) {
				if (element.codigo == codigo.intValue()) {
					return element.getDescripcion();
				}
			}
		}
		return null;
	}
}
