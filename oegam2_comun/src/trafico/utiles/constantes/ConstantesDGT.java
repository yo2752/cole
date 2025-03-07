package trafico.utiles.constantes;

import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;

public class ConstantesDGT {

	//TIPOS DE REGISTROS DE LOS TRÁMITES DEL FICHERO DGT
	public static String REGISTRO_SOLICITUD = "010";
	public static String REGISTRO_BAJA = "020";
	public static String REGISTRO_TRANSMISION = "030";
	public static String REGISTRO_MATRICULACION = "040";
	public static String REGISTRO_PDF = "PDF";

	//TIPOS DE TRÁMITES
	public static String TRAMITE_SOLICITUD = "T4";
	public static String TRAMITE_BAJA = "T3";
	public static String TRAMITE_TRANSMISION = "T2";
	public static String TRAMITE_MATRICULACION = "T1";
	public static String TRAMITE_INTEVE3= "T40";

	//ERRORES
	public static String ERROR_IMPORTAR_FICHERO = "Error al importar el fichero";
	public static String ERROR_FICHERO_SIN_TRAMITES = "Error, el fichero no contiene trámites";
	public static String ERROR_PERMISOS_IMPORTAR_DGT = "El usuario no tiene permisos para importar el fichero DGT";
	public static String ERROR_FICHERO_SIN_SOLICUTUD = "Error, el fichero no contiene lineas con solicitud de datos";
	public static String ERROR_FORMATO_FICHERO = "Error al importar el fichero, el formato no es el adecuado";
	public static String ERROR_FICHERO_MATRICULACION = "Desda la actualización del proceso de matriculación por parte de la DGT en Octubre de 2013 no está permitida la " +
			"importación de trámites de matriculación en ficheros del tipo .dgt.";

	//TAMAÑOS DEL REGISTRO DE LOS TRÁMITES DEL FICHERO DGT
	public static int TAM_REG_TRAMITE = 3;

	//***********************************
	//* TAMAÑO DE LAS LÍNEAS DE FICHERO	*
	//***********************************
	public static final int TAM_LINEA_PDF_SIN_BLANCOS = 58;
	public static final int TAM_LINEA_PDF_CON_BLANCOS = 73;
	public static final int TAM_LINEA_MATRICULACION = 1850;
	public static final int TAM_LINEA_TRANSMISION = 1610;
	public static final int TAM_LINEA_BAJA_MENOR = 1131;
	public static final int TAM_LINEA_BAJA_MAYOR = 1344;
	public static final int TAM_LINEA_SOLICITUD = 757;
	public static final int TAM_LINEA_AEATLOG576 = 111;

	public static final int TAM_LINEA_BAJA_MENOR_BTV = 1155;
	public static final int TAM_LINEA_BAJA_MAYOR_BTV = 1368;

	//*********************************************************
	//* TAMAÑO DE VALORES DE CAMPOS	DEL TRÁMITE MATRICULACIÓN *
	//*********************************************************
	public static final int TAM_TIPO_REG = 3;
	public static final int TAM_TASA = 12;
	public static final int TAM_FECHA_PRESENTACION = 8;
	public static final int TAM_JEFATURA_TRAFICO = 2;
	public static final int TAM_MATRICULA = 9;
	public static final int TAM_FECHA_MATRICULACION = 8;
	public static final int TAM_FECHA_ITV = 8;
	public static final int TAM_DNI_TITULAR = 9;
	public static final int TAM_PRIMER_APELLIDO = 70;
	public static final int TAM_SEGUNDO_APELLIDO = 26;
	public static final int TAM_NOMBRE_TITULAR = 18;
	public static final int TAM_DOMICILIO_TITULAR = 26;
	public static final int TAM_DOMICILIO_TITULAR_MATE = 50;
	public static final int TAM_MUNICIPIO_TITULAR = 24;
	public static final int TAM_PUEBLO_TITULAR = 24;
	public static final int TAM_PROVINCIA_TITULAR = 2;
	public static final int TAM_CP_TITULAR = 5;
	public static final int TAM_SEXO_TITULAR = 1;
	public static final int TAM_FECHA_NACIMIENTO = 8;
	public static final int TAM_NUM_DOCUMENTO = 30;
	public static final int TAM_ACRED_REPRESENTANTE = 70;
	public static final int TAM_REPRESENTANTE = 70;
	public static final int TAM_DNI_REPRESENTANTE = 9;
	public static final int TAM_CONCEPTO_REPR = 70;
	public static final int TAM_MARCA = 23;
	public static final int TAM_MODELO = 22;
	public static final int TAM_BASTIDOR = 21;
	public static final int TAM_PROCEDENCIA = 1;
	public static final int TAM_COLOR = 2;
	public static final int TAM_TIPO_VEHICULO = 2;
	public static final int TAM_CARBURANTE = 2;
	public static final int TAM_POTENCIA = 5;
	public static final int TAM_CILINDRADA = 5;
	public static final int TAM_TARA = 6;
	public static final int TAM_MASA = 6;
	public static final int TAM_PLAZAS = 3;
	public static final int TAM_MATRICULA_AYTO = 12;
	public static final int TAM_EXCESO_PESO = 2;
	public static final int TAM_FECHA_LIMITE = 8;
	public static final int TAM_FECHA_PRIMERA_MATR = 8;
	public static final int TAM_SERVICIO = 1;
	public static final int TAM_SERVICIO_MATE = 3;
	public static final int TAM_TUTELA_MATRICULACION = 2;
	public static final int TAM_MATRICULA_DIPL = 2;
	public static final int TAM_TIPO_LIMITACION = 1;
	public static final int TAM_FECHA_LIMITACION = 8;
	public static final int TAM_CAMPO_BLANCO = 10;
	public static final int TAM_FINANCIERA_LIMITACION = 30;
	public static final int TAM_CALLE_VEHICULO = 26;
	public static final int TAM_CALLE_VEHICULO_MATE = 50;
	public static final int TAM_PROVINCIA_VEHICULO = 2;
	public static final int TAM_MUNICIPIO_VEHICULO = 24;
	public static final int TAM_PUEBLO_VEHICULO = 24;
	public static final int TAM_CP_VEHICULO = 5;
	public static final int TAM_NUM_PROFESIONAL = 5;
	public static final int TAM_ULT_CIFRAS_DOCUMENTO = 6;
	public static final int TAM_FECHA_CREACION = 8;
	public static final int TAM_CODIGO_ITV = 8;
	public static final int TAM_OBSERVACIONES = 255;
	public static final int TAM_TIPO_VIA_TITULAR = 3;
	public static final int TAM_NUM_DOMICILIO_TITULAR = 10;
	public static final int TAM_LETRA_DOMICILIO_TITULAR = 10;
	public static final int TAM_ESCALERA_DOMICILIO_TITULAR = 10;
	public static final int TAM_PISO_DOMICILIO_TITULAR = 10;
	public static final int TAM_PUERTA_DOMICILIO_TITULAR = 10;
	public static final int TAM_KM_DOMICILIO_TITULAR = 10;
	public static final int TAM_BLOQUE_DOMICILIO_TITULAR = 10;
	public static final int TAM_TIPO_TASA = 2;
	public static final int TAM_TIPO_ITV = 25;
	public static final int TAM_VARIANTE = 25;
	public static final int TAM_VERSION = 35;
	public static final int TAM_NUM_HOMOLOGACION = 25;
	public static final int TAM_MASA_MAX = 6;
	public static final int TAM_MASA_SERVICIO = 6;
	public static final int TAM_POTENCIA_MAX = 5;
	public static final int TAM_RELACION_POTENCIA_PESO = 7;
	public static final int TAM_PLAZAS_DE_PIE = 3;
	public static final int TAM_CEM = 8;
	public static final int TAM_NUM_COTITULARES = 1;
	public static final int TAM_DNI_TITULAR_2 = 9;
	public static final int TAM_PRIMER_APELLIDO_TITULAR_2 = 70;
	public static final int TAM_SEGUNDO_APELLIDO_TITULAR_2 = 26;
	public static final int TAM_NOMBRE_TITULAR_2 = 18;
	public static final int TAM_DOMICILIO_TITULAR_2 = 26;
	public static final int TAM_RESERVADO_1 = 73;
	public static final int TAM_MUNICIPIO_TITULAR_2 = 24;
	public static final int TAM_PUEBLO_TITULAR_2 = 24;
	public static final int TAM_PROVINCIA_TITULAR_2 = 2;
	public static final int TAM_CP_TITULAR_2 = 5;
	public static final int TAM_SEXO_TITULAR_2 = 1;
	public static final int TAM_FECHA_NACIMIENTO_TITULAR_2 = 8;
	public static final int TAM_DNI_TITULAR_3 = 9;
	public static final int TAM_PRIMER_APELLIDO_TITULAR_3 = 70;
	public static final int TAM_SEGUNDO_APELLIDO_TITULAR_3 = 26;
	public static final int TAM_NOMBRE_TITULAR_3 = 18;
	public static final int TAM_DOMICILIO_TITULAR_3 = 26;
	public static final int TAM_RESERVADO_2 = 73;
	public static final int TAM_MUNICIPIO_TITULAR_3 = 24;
	public static final int TAM_PUEBLO_TITULAR_3 = 24;
	public static final int TAM_PROVINCIA_TITULAR_3 = 2;
	public static final int TAM_CP_TITULAR_3 = 5;
	public static final int TAM_SEXO_TITULAR_3 = 1;
	public static final int TAM_FECHA_NACIMIENTO_TITULAR_3 = 8;
	public static final int TAM_NOMBRE_PROFESIONAL = 50;

	public static final int TAM_TOTAL_TITULAR_2 = 286;
	public static final int TAM_TOTAL_TITULAR_3 = 286;

	public static final int TAM_TIPO_TARJETA_ITV = 1;

	public static final int TAM_PAIS_BAJA = 3;

	public static final String VALOR_TIPO_LIMITACION = "E";

	/*
	 * 
	 * Añadido para la TRANSMISION
	 * 
	 */
	public static final int TAM_DNI_TRANSMITENTE = 9;
	public static final int TAM_DNI_ADQUIRENTE = 9;
	public static final int TAM_PRIMER_APELLIDO_ADQUIRENTE = 70;
	public static final int TAM_SEGUNDO_APELLIDO_ADQUIRENTE = 26;
	public static final int TAM_NOMBRE_ADQUIRENTE = 18;
	public static final int TAM_DOMICILIO_ADQUIRENTE = 26;
	public static final int TAM_MUNICIPIO_ADQUIRENTE = 24;
	public static final int TAM_PUEBLO_ADQUIRENTE = 24;
	public static final int TAM_PROVINCIA_ADQUIRENTE = 2;
	public static final int TAM_CP_ADQUIRENTE = 5;
	public static final int TAM_SEXO_ADQUIRENTE = 1;
	public static final int TAM_FECHA_NACIMIENTO_ADQUIRENTE = 8;
	public static final int TAM_TIPO_TRANSFERENCIA = 1;
	public static final int TAM_MOTIVO_ITV = 1;
	public static final int TAM_NOMBRE_TRANSMITENTE = 70;
	public static final int TAM_ACRED_REPR_ADQUIRENTE = 70;
	public static final int TAM_REPRESENTANTE_ADQUIRENTE = 70;
	public static final int TAM_REPRESENTANTE_TRANSMITENTE = 70;
	public static final int TAM_DNI_REPRESENTANTE_ADQUIRENTE = 9;
	public static final int TAM_DNI_REPRESENTANTE_TRANSMITENTE = 9;
	public static final int TAM_CONCEPTO_REPR_ADQUIRENTE = 70;
	// DRC@03-10-2012 Incidencia: 1801
	public static final int TAM_ARRENDADOR = 50;
	public static final int TAM_DNI_ARRENDADOR = 9;
	public static final int TAM_REPRESENTANTE_ARRENDADOR = 50;
	public static final int TAM_DNI_REPRESENTANTE_ARRENDADOR = 9;
	public static final int TAM_COMPRAVENTA = 50;
	public static final int TAM_DNI_COMPRAVENTA = 9;
	public static final int TAM_REPRESENTANTE_COMPRAVENTA = 50;
	public static final int TAM_DNI_REPRESENTANTE_COMPRAVENTA = 9;

	public static final int TAM_LOCALIDAD_VEHICULO = 50;
	public static final int TAM_NOMBRE_PROVINCIA_VEHICULO = 20;
	public static final int TAM_NOMBRE_CALLE_VEHICULO = 50;
	public static final int TAM_NUM_CALLE_VEHICULO = 10;
	public static final int TAM_MODO_ADJUDICACION = 1;
	public static final int TAM_PRIMER_APELLIDO_TRANSMITENTE = 70;
	public static final int TAM_SEGUNDO_APELLIDO_TRANSMITENTE = 26;
	public static final int TAM_DOMICILIO_TRANSMITENTE = 26;
	public static final int TAM_MUNICIPIO_TRANSMITENTE = 24;
	public static final int TAM_PUEBLO_TRANSMITENTE = 24;
	public static final int TAM_PROVINCIA_TRANSMITENTE = 2;
	public static final int TAM_CP_TRANSMITENTE = 5;
	public static final int TAM_SEXO_TRANSMITENTE = 1;
	public static final int TAM_ACRED_REPR_TRANSMITENTE = 70;
	public static final int TAM_TIPO_VIA_TRANSMITENTE = 3;
	public static final int TAM_NUM_DOMICILIO_TRANSMITENTE = 10;
	public static final int TAM_LETRA_DOMICILIO_TRANSMITENTE = 10;
	public static final int TAM_ESCALERA_DOMICILIO_TRANSMITENTE = 10;
	public static final int TAM_PISO_DOMICILIO_TRANSMITENTE = 10;
	public static final int TAM_PUERTA_DOMICILIO_TRANSMITENTE = 10;
	public static final int TAM_TIPO_VIA_ADQUIRENTE = 3;
	public static final int TAM_NUM_DOMICILIO_ADQUIRENTE = 10;
	public static final int TAM_LETRA_DOMICILIO_ADQUIRENTE = 10;
	public static final int TAM_ESCALERA_DOMICILIO_ADQUIRENTE = 10;
	public static final int TAM_PISO_DOMICILIO_ADQUIRENTE = 10;
	public static final int TAM_PUERTA_DOMICILIO_ADQUIRENTE = 10;
	public static final int TAM_CET = 8;
	public static final int TAM_TUTELA_TRANSMISION = 1;

	/*
	 * 
	 * Añadido para la BAJA
	 * 
	 */
	public static final int TAM_MOTIVO_BAJA = 1;
	public static final int TAM_NOMBRE_PROVINCIA_TITULAR = 20;
	public static final int TAM_NOMBRE_JEFATURA_TRAFICO = 20;
	public static final int TAM_DOCUMENTOS_ACOMPAÑANTES = 70;
	public static final int TAM_CONCEPTO_SOLICITANTE = 40;
	public static final int TAM_RESERVADO = 73;
	public static final int TAM_REPRESENTANTE_BAJA = 50;
	public static final int TAM_LOCALIDAD_VEHICULO_BAJA = 70;
	public static final int TAM_NOMBRE_CALLE_VEHICULO_BAJA = 70;
	public static final int TAM_ACRED_REPRESENTANTE_BAJA = 50;

	/*
	 * 
	 * Añadido para la SOLICITUD DE DATOS
	 * 
	 */
	public static final int TAM_NUM_SOLICITUDES = 2;
	public static final int TAM_MATRICULA_DNI = 9;
	public static final int TAM_PRIMER_DATO_SOLICITADO = 25;
	public static final int TAM_SEGUNDO_DATO_SOLICITADO = 25;
	public static final int TAM_TERCER_DATO_SOLICITADO = 25;
	public static final int TAM_DNI_SOLICITANTE = 9;
	public static final int TAM_PRIMER_APELLIDO_SOLICITANTE = 26;
	public static final int TAM_SEGUNDO_APELLIDO_SOLICITANTE = 26;
	public static final int TAM_NOMBRE_SOLICITANTE = 18;

	/*****************************
	 * Nombres bean contenedores *
	 * **************************/

	public static final String DTO_BAJA = "DtoBaja";
	public static final String BEAN_PQ_ALTA_MATRICULACION = "BeanPQAltaMatriculacion";
	public static final String BEAN_PQ_ALTA_MATRICULACION_IMPORT = "BeanPQAltaMatriculacionImport";
	public static final String BEAN_PQ_TRANSMISION = "BeanPQTransmision";
	public static final String BEAN_PDF = "ImportacionDGTPDFBean";

	/*****************************
	 * TIPOS INTERVINIENTE *******
	 */
	public static final String TIPO_INTERVINIENTE_SUJETO_PASIVO = TipoInterviniente.SujetoPasivo.getValorEnum(); // "001"
	public static final String TIPO_INTERVINIENTE_PRESENTADOR = TipoInterviniente.Presentador.getValorEnum(); // "002"
	public static final String TIPO_INTERVINIENTE_TRANSMITENTE = TipoInterviniente.Transmitente.getValorEnum(); // "003"
	public static final String TIPO_INTERVINIENTE_TITULAR = TipoInterviniente.Titular.getValorEnum(); //Titular '004'
	public static final String TIPO_INTERVINIENTE_ADQUIRIENTE =  TipoInterviniente.Adquiriente.getValorEnum(); //Adquiriente '005'
	public static final String TIPO_INTERVINIENTE_TRANSMITENTE_TRAFICO = TipoInterviniente.TransmitenteTrafico.getValorEnum(); // "006"
	public static final String TIPO_INTERVINIENTE_PRESENTADOR_TRAFICO = TipoInterviniente.PresentadorTrafico.getValorEnum(); // "007"
	public static final String TIPO_INTERVINIENTE_REPRESENTANTE_ADQUIRIENTE  = TipoInterviniente.RepresentanteAdquiriente.getValorEnum(); // "008"
	public static final String TIPO_INTERVINIENTE_REPRESENTANTE_TRANSMITENTE = TipoInterviniente.RepresentanteTransmitente.getValorEnum(); // "009"
	public static final String TIPO_INTERVINIENTE_REPRESENTANTE_TITULAR = TipoInterviniente.RepresentanteTitular.getValorEnum(); //Representante del titular '010'
	public static final String TIPO_INTERVINIENTE_CONDUCTOR_HABITUAL = TipoInterviniente.ConductorHabitual.getValorEnum(); // "011"
	public static final String TIPO_INTERVINIENTE_ARRENDADOR = TipoInterviniente.Compraventa.getValorEnum(); // "012"
	public static final String TIPO_INTERVINIENTE_REPRESENTANTE_ARRENDADOR = TipoInterviniente.RepresentanteCompraventa.getValorEnum(); // "013"
	public static final String TIPO_INTERVINIENTE_ARRENDATARIO = TipoInterviniente.Arrendatario.getValorEnum(); // "014"
	public static final String TIPO_INTERVINIENTE_REPRESENTANTE_ARRENDATARIO = TipoInterviniente.RepresentanteArrendatario.getValorEnum(); // "015"
	public static final String TIPO_INTERVINIENTE_COTITULAR_TRANSMISION = TipoInterviniente.CotitularTransmision.getValorEnum(); // "016"
	public static final String TIPO_INTERVINIENTE_SOLICITANTE = TipoInterviniente.Solicitante.getValorEnum(); // "017"
	public static final String TIPO_INTERVINIENTE_COMPRAVENTA = TipoInterviniente.Compraventa.getValorEnum(); // "018"
}