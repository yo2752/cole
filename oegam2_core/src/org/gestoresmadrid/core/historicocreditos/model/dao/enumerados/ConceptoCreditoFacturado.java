package org.gestoresmadrid.core.historicocreditos.model.dao.enumerados;

/**
 * Enumerado que contiene las operaciones (y su descripcion) que conllevan gasto de creditos.
 */
public enum ConceptoCreditoFacturado {

	AVPO("AVPO", "Solicitud de AVPO") {},
	DAVP("DAVP", "Devolución Solicitud de AVPO") {},
	SJP("SJP", "Solicitar justificante profesional") {},
	ITB("ITB", "Imprimir trámites de baja") {},
	ITT("ITT", "Imprimir trámites de transmisión") {},
	ITM("ITM", "Imprimir trámites de matriculación") {},
	GOM("GOM", "Obtener matricula") {},
	DGOM("DGOM", "Devolver Obtener matricula") {},
	GEST("GEST", "Solicitud de Gest") {},
	DGET("DGET", "Devolver Solicitud de Gest") {},
	EBB("EEB", "Envio Excel Bajas") {},
	EBD("EED", "Envio Excel Duplicados") {},
	TMT("TMT", "Tramitar matriculación telemáticamente") {},
	TMAM("TMAM", "Tramitar matriculacion telematicamente AM") {},
	TTAM("TTAM", "Tramitar transmisión telematicamente AM") {},
	TBAM("TBAM", "Tramitar baja telemáticamente AM") {},
	TDAM("TDAM", "Tramitar Duplicado AM") {},
	LEG("LEG", "Alta de petición de legalización") {},
	SAN("SAN", "Alta de petición de sanciones") {},
	SPL("SPL", "Solicitud placas de matricula") {},
	ATE("ATE", "Modelo Atem") {},
	TTT("TTT", "Trámite de transmisión telemática") {},
	INF("INF", "Solicitud de informe desde tramite de transmisión") {},
	EFC("EFC", "Consulta entidades financieras") {},
	EFL("EFL", "Liberación entidades financieras") {},
	TBT("TBT", "Tramitar baja telemáticamente") {},
	TCT("TCT", "Tramitar compra de tasas") {},
	PMD("PMD", "Presentacion Modelo 600/601") {},
	CDEV("CDEV", "Consulta Dev") {},
	PTX5("PTX5", "Consulta Persona Atex5") {},
	VTX5("VTX5", "Consulta Vehiculo Atex5") {},
	DTX5("DTX5", "Consulta Documento Atex5") {},
	ETX5("ETX5", "Consulta Eucaris Atex5") {},
	ATX5("ATX5", "Consulta Vehiculo Aplicación Atex5") {},
	MTX5("MTX5", "Consulta Masiva de Personas o Vehículos Atex5") {},
	DTMT("DTMT", "Devolución Tramitar matriculación telemáticamente") {},
	DEBB("DEEB", "Devolución Envio Excel Bajas") {},
	DEBD("DEBD", "Devolución Envio Excel Duplicados") {},
	DLEG("DLEG", "Devolución Alta de petición de legalización") {},
	DITB("DITB", "Devolución Imprimir trámites de baja") {},
	DITT("DITT", "Devolución Imprimir trámites de transmisión") {},
	DITM("DITM", "Devolución Imprimir trámites de matriculación") {},
	DSAN("DSAN", "Devolución Alta de petición de sanciones") {},
	DEFC("DEFC", "Devolución consulta entidades financieras") {},
	DEFL("DEFL", "Devolución liberación entidades financieras") {},
	DSJP("DSJP", "Devolución Solicitar justificante profesional") {},
	DTCT("DTCT", "Devolucion tramitar compra de tasas") {},
	DTBT("DTBT", "Devolucion tramitar baja telematicamente") {},
	ESC("ESC", "Presentación de formularios 600/601") {},
	SSCN("SSCN", "Consulta notificaciones de la Seguridad Social") {},
	WREG("WREG", "Registro Propiedad") {},
	DWREG("DWREG", "Devolver Registro Propiedad") {},
	DPMD("DPMD", "Devolución Presentacion Modelo 600/601") {},
	DDEV("DDEV", "Devolución Consulta Dev") {},
	DPX5("PTX5", "Devolución Consulta Persona Atex5") {},
	DVX5("VTX5", "Devolución Consulta Vehiculo Atex5") {},
	DDX5("DTX5", "Devolución Consulta Documento Atex5") {},
	DEX5("ETX5", "Devolución Consulta Eucaris Atex5") {},
	DAX5("ATX5", "Devolución Consulta Vehiculo Aplicación Atex5") {},
	DMX5("MTX5", "Devolución Consulta Masiva de Personas o Vehículos Atex5") {},
	TRA("TRA", "Consulta Testra: Notificación de Sanción ") {},
	PRI("PRI", "Permiso Internacional") {},
	DTMA("DTMA", "Devolución trámitar matriculación telematicamente AM") {},
	DTTA("DTTA", "Devolución trámitar transmisión telematicamente AM") {},
	DTBA("DTBA", "Devolución trámitar baja telematicamente AM") {},
	DTDA("DTDA", "Devolución Duplicado AM") {},
	DTIA("DTBA", "Devolución trámitar Inteve telematicamente AM") {},
	DJPA("DJPA", "Devolución Solicitar justificante profesional AM") {},
	TDT("TDT", "Tramitar Duplicado Telemático") {},
	TCAN("TCAN", "Imprimir Listado Canjes") {};

	private String valorEnum;
	private String nombreEnum;

	private ConceptoCreditoFacturado(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public static ConceptoCreditoFacturado convertir(String valorEnum) {
		if (valorEnum != null) {
			for (ConceptoCreditoFacturado element : ConceptoCreditoFacturado.values()) {
				if (element.valorEnum.equals(valorEnum)) {
					return element;
				}
			}
		}
		return null;
	}

	public static String convertirTexto(String valorEnum) {
		ConceptoCreditoFacturado conceptoCreditoFacturado = convertir(valorEnum);
		return conceptoCreditoFacturado != null ? conceptoCreditoFacturado.nombreEnum : null;
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	public String toString() {
		return nombreEnum;
	}
}
