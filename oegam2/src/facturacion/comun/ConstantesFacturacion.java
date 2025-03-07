package facturacion.comun;

import java.text.SimpleDateFormat;

public class ConstantesFacturacion {
	public static final String RESULT_ALTA_FACTURACION						= "altaFactura";
	public static final String RESULT_CONSULTA_FACTURACION					= "consultaFactura";
	public static final String RESULT_CONSULTA_FACTURACION_RECTIFICATIVA	= "consultaFacturaRectificativa";
	public static final String RESULT_CONSULTA_FACTURACION_ABONO			= "consultaFacturaAbono";
	public static final String RESULT_DESCARGAR_PDF_FACTURACION				= "descargarFacturaPDF";
	public static final String RESULT_DESCARGAR_XML_EXPORTACION_SIGA		= "descargarXMLSIGA";
	public static final String RESULT_CONSULTA_CONCEPTO						= "consultaConcepto";
	public static final String RESULT_CONSULTA_ANULADA						= "consultaAnulada";
	public static final String CLAVE_DEFECTO								= "00000";

	public static final String ESTADO_PDF_INICIAL				= "INI";
	public static final String ESTADO_PDF_OK					= "OOK";
	public static final String ESTADO_PDF_KO					= "KOO";
	public static final String ESTADO_PDF_PDF					= "PDF";
	public static final String ESTADO_PDF_FIN					= "FIN";

	public static final String ESTADO_XML_INICIAL				= "INI";
	public static final String ESTADO_XML_OK					= "OOK";
	public static final String ESTADO_XML_XML					= "XML";
	public static final String ESTADO_XML_FIN					= "FIN";

	public static final String FACTURACION_SEPARADOR			= "\\";
	public static final String FACTURACION_SEPARADOR_CONCEPTO	= "*";

	public static final String RESULT_CONSUTA_TASA				= "consultaTasa";

	public static final String TITULAR							= "TITULAR";
	public static final String COMPRADOR						= "COMPRADOR";
	public static final String TRANSMITENTE						= "TRANSMITENTE";
	public static final String COMPRAVENTA						= "COMPRAVENTA";

	public static final int IVA_DEFECTO			= 21;
	public static final int IRPF_DEFECTO		= 0;
	public static final String DECIMALES_FACT	= "2";
	// Mantis 13737 
	public static final String NUM_COLEGIADO_ADMINISTRADOR   = "2056";

	// MANTIS 22033
	public static final String NUM_DECIMALES = "2";
	public static final String ERROR_NUMFACTURA = "El campo Numero de Factura es obligatorio";
	public static final String ERROR_FECHAFACTURA = "El campo Fecha de Factura es obligatorio";
	public static final String ERROR_NIF = "El campo NIF/CIF es obligatorio";
	public static final String ERROR_PRIMERAPELLIDO = "El campo Primer Apellido / Razon Social es obligatorio";
	public static final String ERROR_NOMBRE = "El campo Nombre es obligatorio";
	public static final String ERROR_PROVINCIA = "El campo Provincia es obligatorio";
	public static final String ERROR_MUNICIPIO = "El campo Municipio es obligatorio";
	public static final String ERROR_CODPOSTAL = "El campo Codigo Postal es obligatorio";
	public static final String ERROR_TIPOVIA = "El campo Tipo de Via es obligatorio";
	public static final String ERROR_NOMBREVIA = "El campo Nombre de Via es obligatorio";
	public static final String ERROR_NUMEROVIA = "El campo Numero de Via es obligatorio";
	public static final String ERROR_CONCEPTO_NUMCOLEGIADO = "El campo Numero de Colegiado es obligatorio";
	public static final String ERROR_CONCEPTO_ID = "El campo Identificador es obligatorio";
	public static final String ERROR_CONCEPTO_NOMBRE = "El campo Nombre es obligatorio";
	public static final String ALERT_MENSAJE_BORRADOR = "Desea generar el borrador de la factura seleccionada?";
	public static final String ALERT_MENSAJE_PDF = "Desea generar en formato pdf la factura seleccionada, sabiendo que ya no podra modificarla, y en caso de querer hacerlo sera rectificativa?";
	public static final String ALERT_MENSAJE_XML = "Desea generar en formato xml la factura seleccionada, sabiendo que ya no podra modificarla, y en caso de querer hacerlo sera rectificativa?";
	public static final String ALERT_MENSAJE_IMPRIMIR = "Desea generar en formato pdf la factura seleccionada?";
	public static final String ALERT_MENSAJE_MODFICAR = "Si desea modificar una factura ya generada, debe introducir la clave?";
	public static final String MENSAJE_COLEGIADO_CONCEPTO_ERROR_UNO = "Se ha producido un error al actualizar los conceptos";
	public static final String MENSAJE_COLEGIADO_CONCEPTO_OK_UNO = "Los conceptos han sido actualizado correctmente";
	public static final String MENSAJE_OK_FACTURA = "La Factura con numero";
	public static final String MENSAJE_OK = "se ha guardado correctamente";
	public static final String MENSAJE_ERROR_FACTURA = "Se ha producido un error el Factura con el numero";
	public static final String MENSAJE_ERROR = "y no se ha guardado correctamente";
	public static final String MENSAJE_ERROR_ELIMINAR_UNO = "La factura";
	public static final String MENSAJE_ERROR_ELIMINAR_DOS = "ha sido eliminada satisfactoriamente";
	public static final String MENSAJE_ERROR_CREAR_RECTIFICATIVA_DOS = "ha sido anulada satisfactoriamente";
	public static final String MENSAJE_ERROR_CREAR_RECTIFICATIVA_TRES = "ha sido creada satisfactoriamente";
	public static final String MENSAJE_ERROR_CREAR_ABONO_UNO = "El abono";
	public static final String MENSAJE_ERROR_CREAR_ABONO_DOS = "ha sido creado satisfactoriamente";
	public static final String PDF_ABONO_MENSAJE_TRES = "No se ha podido crear el abono de la factura";
	public static final String PDF_ABONO_MENSAJE_CUATRO = "porque no se encuentra en el estado FACTURA GENERADA";
	public static final String PDF_RECTIFICATIVA_MENSAJE_CERO = "No se ha encontrado la factura solicitada";
	public static final String PDF_RECTIFICATIVA_MENSAJE_UNO = "La factura rectificativa";
	public static final String PDF_RECTIFICATIVA_MENSAJE_DOS = "anula la factura";
	public static final String PDF_RECTIFICATIVA_MENSAJE_TRES = "No se ha podido crear la factura rectificativa para la factura";
	public static final String PDF_RECTIFICATIVA_MENSAJE_CUATRO = "porque no se encuentra en el estado FACTURA GENERADA";
	public static final String MENSAJE_ERROR_CREAR_RECTIFICATIVA_UNO = "La factura";
	public static final String FACTURA_ABONO_DEFECTO = "ABON";
	public static final String FACTURA_SERIE_DEFECTO = "FACT";
	public static final String FACTURA_RECTIFICATIVA_DEFECTO = "RECT";
	public static final String ERROR_EMISOR = "El emisor es obligatorio";

	public ConstantesFacturacion() {}
	SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
}