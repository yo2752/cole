package org.gestoresmadrid.core.docbase.enumerados;

public enum TipoTramiteDocBase {

	GENERACION("D1", "Tramite Generacion Documento Base"){
		public String toString() {
			return "D1";
		}
	},
	IMPRESION("D2", "Tramite Impresion Documento Base"){
		public String toString() {
			return "D2";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private TipoTramiteDocBase(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}
	public String getValorEnum() {
		return valorEnum;
	}
	public void setValor(String valorEnum) {
		this.valorEnum = valorEnum;
	}
	public String getNombreEnum() {
		return nombreEnum;
	}
	public void setNombre(String nombreEnum) {
		this.nombreEnum = nombreEnum;
	}
}
