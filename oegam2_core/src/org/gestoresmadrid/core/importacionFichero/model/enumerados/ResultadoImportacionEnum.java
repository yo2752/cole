package org.gestoresmadrid.core.importacionFichero.model.enumerados;

public enum ResultadoImportacionEnum {

	OK("0", "OK", "OK") {
		public String toString() {
			return "0";
		}
	},
	OK_PARCIAL("1", "OK PARCIAL", "PARCIAL") {
		public String toString() {
			return "1";
		}
	},
	Error_Catastrofico("2", "Error Catastrófico", "ERROR") {
		public String toString() {
			return "2";
		}
	},
	Parado("3", "Parado", "PARADO") {
		public String toString() {
			return "3";
		}
	};

	private String codigo;
	private String resultado;
	private String resultadoCorto;

	private ResultadoImportacionEnum(String codigo, String resultado, String resultadoCorto) {
		this.codigo = codigo;
		this.resultado = resultado;
		this.resultadoCorto = resultadoCorto;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getResultado() {
		return resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}

	public String getResultadoCorto() {
		return resultadoCorto;
	}

	public void setResultadoCorto(String resultadoCorto) {
		this.resultadoCorto = resultadoCorto;
	}

	public static ResultadoImportacionEnum convertir(String codigo) {
		for (ResultadoImportacionEnum element : ResultadoImportacionEnum.values()) {
			if (element.codigo.equals(codigo)) {
				return element;
			}
		}
		return null;
	}

	public static String convertirTexto(String codigo) {
		for (ResultadoImportacionEnum element : ResultadoImportacionEnum.values()) {
			if (element.codigo.equals(codigo)) {
				return element.resultado;
			}
		}
		return null;
	}

	public static String convertirResultadoCorto(String codigo) {
		for (ResultadoImportacionEnum element : ResultadoImportacionEnum.values()) {
			if (element.codigo.equals(codigo)) {
				return element.resultadoCorto;
			}
		}
		return null;
	}
}
