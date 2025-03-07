package org.gestoresmadrid.core.model.enumerados;


public enum TipoFicheroImportacionBastidores {
	TXT(0, ".txt"){
	},
	EXCEL(1, ".xls"){
	},
	DAT(2, ".dat"){
	};

	private int codigo;
	private String descripcion;
	
	private TipoFicheroImportacionBastidores(int codigo, String descripcion){
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

	public static TipoFicheroImportacionBastidores convertir(Integer codigo) {
		if (codigo != null) {
			for (TipoFicheroImportacionBastidores element : TipoFicheroImportacionBastidores.values()) {
				if (element.codigo == codigo.intValue()) {
					return element;
				}
			}
		}
		return null;
	}

	public static TipoFicheroImportacionBastidores convertir(String codigo) {
		return codigo != null ? convertir(Integer.parseInt(codigo)) : null;
	}
	
	public static TipoFicheroImportacionBastidores convertirTexto(String descripcion) {
		for (TipoFicheroImportacionBastidores element : TipoFicheroImportacionBastidores.values()) {
			if (element.descripcion.equals(descripcion)) {
				return element;
			}
		}
		return null;
	}

}
