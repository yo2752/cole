package org.gestoresmadrid.oegam2comun.modeloCAYC.model.service;

import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.oegam2comun.modeloCAYC.view.bean.ResultBeanTotal;

public interface ServicioModeloCAYC extends Serializable{

	public static String TIPO_DOC_PUBLICO = "PUB";
	public static String CODIGO_REMESA = "00001";
	public static int TAM_CODIGO_DECLARACION = 13;
	public static int TAM_CODIGO_REMESA = 5;
	public static String ENCODING_XML_ISO = "ISO-8859-1";
	public static String ENCODING_XML_UTF8 = "UTF-8";
	public static String NOMBRE_FICHERO = "Liquidacion_";
	public static String NOMBRE_ESCRITURA = "Escritura_";
	public static String cobrarCreditos = "cobrar.creditos.cam";
	public static final String RUTA_XSD_MODELO_600 = "/org/gestoresmadrid/oegam2/modelo600601/schemas/600_V1.2.xsd";
	public static final String RUTA_XSD_MODELO_601 = "/org/gestoresmadrid/oegam2/modelo600601/schemas/601_V1.2.xsd";
	// Cambiar estas líneas por las de arriba cuando se proceda al desarrollo de los Modelos 600 y 601
//	public static final String RUTA_XSD_MODELO_600 = "/org/gestoresmadrid/oegam2/modelo600601/schemas/600_V1.3.xsd";
//	public static final String RUTA_XSD_MODELO_601 = "/org/gestoresmadrid/oegam2/modelo600601/schemas/601_V1.3.xsd";

	public ResultBeanTotal importarModeloCAYC(File fichero, String idContrato, BigDecimal idUsuarioDeSesion, String Tipo);

}