package org.gestoresmadrid.core.historicocreditos.model.dao.enumerados;

/**
 * Enumerado que contiene las operaciones (y su descripcion) que conllevan gasto de creditos.
 */
public enum ConceptoCreditoFacturado {

	AVPO("AVPO", "Solicitud de AVPO") {},
	DAVP("DAVP", "Devoluci�n Solicitud de AVPO") {},
	SJP("SJP", "Solicitar justificante profesional") {},
	ITB("ITB", "Imprimir tr�mites de baja") {},
	ITT("ITT", "Imprimir tr�mites de transmisi�n") {},
	ITM("ITM", "Imprimir tr�mites de matriculaci�n") {},
	GOM("GOM", "Obtener matricula") {},
	DGOM("DGOM", "Devolver Obtener matricula") {},
	GEST("GEST", "Solicitud de Gest") {},
	DGET("DGET", "Devolver Solicitud de Gest") {},
	EBB("EEB", "Envio Excel Bajas") {},
	EBD("EED", "Envio Excel Duplicados") {},
	TMT("TMT", "Tramitar matriculaci�n telem�ticamente") {},
	TMAM("TMAM", "Tramitar matriculacion telematicamente AM") {},
	TTAM("TTAM", "Tramitar transmisi�n telematicamente AM") {},
	TBAM("TBAM", "Tramitar baja telem�ticamente AM") {},
	TDAM("TDAM", "Tramitar Duplicado AM") {},
	LEG("LEG", "Alta de petici�n de legalizaci�n") {},
	SAN("SAN", "Alta de petici�n de sanciones") {},
	SPL("SPL", "Solicitud placas de matricula") {},
	ATE("ATE", "Modelo Atem") {},
	TTT("TTT", "Tr�mite de transmisi�n telem�tica") {},
	INF("INF", "Solicitud de informe desde tramite de transmisi�n") {},
	EFC("EFC", "Consulta entidades financieras") {},
	EFL("EFL", "Liberaci�n entidades financieras") {},
	TBT("TBT", "Tramitar baja telem�ticamente") {},
	TCT("TCT", "Tramitar compra de tasas") {},
	PMD("PMD", "Presentacion Modelo 600/601") {},
	CDEV("CDEV", "Consulta Dev") {},
	PTX5("PTX5", "Consulta Persona Atex5") {},
	VTX5("VTX5", "Consulta Vehiculo Atex5") {},
	DTX5("DTX5", "Consulta Documento Atex5") {},
	ETX5("ETX5", "Consulta Eucaris Atex5") {},
	ATX5("ATX5", "Consulta Vehiculo Aplicaci�n Atex5") {},
	MTX5("MTX5", "Consulta Masiva de Personas o Veh�culos Atex5") {},
	DTMT("DTMT", "Devoluci�n Tramitar matriculaci�n telem�ticamente") {},
	DEBB("DEEB", "Devoluci�n Envio Excel Bajas") {},
	DEBD("DEBD", "Devoluci�n Envio Excel Duplicados") {},
	DLEG("DLEG", "Devoluci�n Alta de petici�n de legalizaci�n") {},
	DITB("DITB", "Devoluci�n Imprimir tr�mites de baja") {},
	DITT("DITT", "Devoluci�n Imprimir tr�mites de transmisi�n") {},
	DITM("DITM", "Devoluci�n Imprimir tr�mites de matriculaci�n") {},
	DSAN("DSAN", "Devoluci�n Alta de petici�n de sanciones") {},
	DEFC("DEFC", "Devoluci�n consulta entidades financieras") {},
	DEFL("DEFL", "Devoluci�n liberaci�n entidades financieras") {},
	DSJP("DSJP", "Devoluci�n Solicitar justificante profesional") {},
	DTCT("DTCT", "Devolucion tramitar compra de tasas") {},
	DTBT("DTBT", "Devolucion tramitar baja telematicamente") {},
	ESC("ESC", "Presentaci�n de formularios 600/601") {},
	SSCN("SSCN", "Consulta notificaciones de la Seguridad Social") {},
	WREG("WREG", "Registro Propiedad") {},
	DWREG("DWREG", "Devolver Registro Propiedad") {},
	DPMD("DPMD", "Devoluci�n Presentacion Modelo 600/601") {},
	DDEV("DDEV", "Devoluci�n Consulta Dev") {},
	DPX5("PTX5", "Devoluci�n Consulta Persona Atex5") {},
	DVX5("VTX5", "Devoluci�n Consulta Vehiculo Atex5") {},
	DDX5("DTX5", "Devoluci�n Consulta Documento Atex5") {},
	DEX5("ETX5", "Devoluci�n Consulta Eucaris Atex5") {},
	DAX5("ATX5", "Devoluci�n Consulta Vehiculo Aplicaci�n Atex5") {},
	DMX5("MTX5", "Devoluci�n Consulta Masiva de Personas o Veh�culos Atex5") {},
	TRA("TRA", "Consulta Testra: Notificaci�n de Sanci�n ") {},
	PRI("PRI", "Permiso Internacional") {},
	DTMA("DTMA", "Devoluci�n tr�mitar matriculaci�n telematicamente AM") {},
	DTTA("DTTA", "Devoluci�n tr�mitar transmisi�n telematicamente AM") {},
	DTBA("DTBA", "Devoluci�n tr�mitar baja telematicamente AM") {},
	DTDA("DTDA", "Devoluci�n Duplicado AM") {},
	DTIA("DTBA", "Devoluci�n tr�mitar Inteve telematicamente AM") {},
	DJPA("DJPA", "Devoluci�n Solicitar justificante profesional AM") {},
	TDT("TDT", "Tramitar Duplicado Telem�tico") {},
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
