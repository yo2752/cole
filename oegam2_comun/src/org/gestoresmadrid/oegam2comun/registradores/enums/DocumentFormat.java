package org.gestoresmadrid.oegam2comun.registradores.enums;

/**
 * Tipos de comisión
 */
public enum DocumentFormat {

	UNKNOWN("Desconocido"),
	PDF("pdf"),
	DOC("doc"),
	RTF("rtf"),
	XLS("xls"),
	TIF("tif"),
	XML("xml"),
	DOCX("docx"),
	XLSX("xlsx"),
	TXT("txt"),
	PKCS7("pkcs7"),
	ASC("asc"),
	ZIP("zip");

	private String nombreEnum;

	private DocumentFormat(String nombreEnum) {
		this.nombreEnum = nombreEnum;
	}

	@Override
	public String toString() {
		return nombreEnum;
	}

}
