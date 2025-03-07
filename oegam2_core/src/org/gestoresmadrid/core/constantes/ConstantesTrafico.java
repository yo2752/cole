package org.gestoresmadrid.core.constantes;

import java.math.BigDecimal;

import org.gestoresmadrid.core.trafico.justificante.profesional.model.enumerados.DocumentosJustificante;
import org.gestoresmadrid.core.trafico.justificante.profesional.model.enumerados.MotivoJustificante;

public class ConstantesTrafico {

	public static final String ID_PREFIX = "ID_";
	public static final String ND = "ND";// Valor no disponible (Mate 2.5)
	public static final String TEMPORAL = "TEMPORAL";
	public static final String DEFINITIVA = "DEFINITIVA";
	public static final String AVISO_INE_DIRECCION = "AVISO: DIRECCIÓN NO VALIDADA POR EL INE";
	public static final String AVISO_PROVINCIA_TITULAR= "PROVINCIA DEL TITULAR DISTINTA A LA DEL CONTRATO";	 
	public static final String AVISO_NIF_PATRON_NO_VALIDO ="cvc-pattern-valid";
	public static final String AVISO_DISTINTIVO_MEDIOAMBIENTAL = "distintivo medioambiental";
	
	public static final String AVISO_MAXIMA_LONGITUD_DIRECCION_VEHICULO = "26";
	public static final String AVISO_VALIDACION_WMI = "WMI";
	public static final String PREFIJO_ID_FUERZAS_ARMADAS = "FA";

	public static final String CONSULTA_AVPO_BSTI_GEST_DESACTIVAR = "avpo.bsti.gest.consulta.desactivar";
	public static final String CONSULTA_AVPO_BSTI_GEST_DESACTIVADA_MENSAJE = "Solicitud de información a DGT, temporalmente inhabilitada.";
	public static final String CONSULTA_AVPO_BSTI_GEST_MENSAJE_X_DEFECTO = "Solicitud de información a DGT, temporalmente inhabilitada.";
	public static final String MENSAJE_VALIDACION_PATRON_NIF="El Nif introducido no es válido.";

	public static final String REF_PROP_NOMBRE_SERVIDOR_INFORME = "nombre.servidor.informe.datos.tecnicos.vehiculo";
	public static final String REF_PROP_ALMACEN_INFORMES_TECNICOS_VEHICULO = "avpo.server";
	public static final String INTEVE_MASIVO_URL = "atem.service.inteve.url";
	public static final String INTEVE_MASIVO_PLANTILLAS = "atem.service.inteve.plantillas";
	public static final String INTEVE_ATEM_MASIVO_ACTIVO = "atem.service.masivo.activo";
	public static final String INTEVE_MASIVO_PLANTILLAS_NOMBRE = "atem.service.inteve.plantillas.nombre";
	public static final String INTEVE_MASIVO_DESVIO_USO_INTERNO = "atem.service.uso.interno.inteve";
	public static final String INTEVE_GUARDAR_COPIA_XML = "atem.guardar.copia.xml";
	
	public static final int POS_IN_NUBE2=112; // indice del array en el que comienza la nube 2 (Si se añade o se elmina algun campo en el array es necesario
	                                               // modificar este valor)
	public static final int POS_FIN_NUBE2=159;
	
	public static final int PRIMER_ANYO_EJERCICIO_DEVENGO = 2005;

	// *******************************
	// * IDENTIFICADORES DE CAMPOS *
	// *******************************

	/**
	 * Numero de Expediente
	 */
	public static final String ID_NUM_EXPEDIENTE = ID_PREFIX + "NUM_EXPEDIENTE";

	/**
	 * Tipo de registro. (Primer campo)
	 * 
	 * @see #TAM_TIPO_REG
	 * @see #VALOR_TIPO_REG_MATRICULACION
	 * @see #VALOR_TIPO_REG_BAJA
	 * @see #VALOR_TIPO_REG_TRANSMISION
	 * @see #VALOR_TIPO_REG_PDF
	 */
	public static final String ID_TIPO_REG = ID_PREFIX + "TIPO_REG";
	/**
	 * Numero de tasa
	 * 
	 * @see #TAM_TASA
	 */
	public static final String ID_TASA = ID_PREFIX + "TASA";
	/**
	 * Numero de tasa
	 * 
	 * @see #TAM_TASA
	 */
	public static final String ID_TASA_CAMBIO = ID_PREFIX + "TASA_CAMBIO";
	/**
	 * Numero de tasa
	 * 
	 * @see #TAM_TASA
	 */
	public static final String ID_TASA_INFORME = ID_PREFIX + "TASA_INFORME";
	/**
	 * Fecha de presentacion
	 * 
	 * @see #TAM_FECHA_PRESENTACION
	 * @see #FORMATO_FECHA_PRESENTACION
	 */
	public static final String ID_FECHA_PRESENTACION = ID_PREFIX
			+ "FECHA_PRESENTACION";
	
	/**
	 * Fecha de contrato
	 * 
	 * @see #TAM_FECHA_CONTRATO
	 * @see #FORMATO_FECHA_CONTRATO
	 */
	public static final String ID_FECHA_CONTRATO430 = ID_PREFIX
			+ "FECHA_CONTRATO430";
	
	/**
	 * Siglas de la Jefatura de Trafico.
	 * 
	 * @see #TAM_JEFATURA_TRAFICO
	 * @see #TAM_NOMBRE_JEFATURA_TRAFICO
	 */
	public static final String ID_JEFATURA_TRAFICO = ID_PREFIX
			+ "JEFATURA_TRAFICO";
	/**
	 * Matricula.
	 * 
	 * @see #TAM_MATRICULA
	 */
	public static final String ID_MATRICULA = ID_PREFIX + "MATRICULA";
	/**
	 * Fecha de matriculacion
	 * 
	 * @see #TAM_FECHA_MATRICULACION
	 * @see #FORMATO_FECHA_MATRICULACION
	 */

	public static final String ID_MATRI = ID_PREFIX + "MATRI";
	/**
	 * @see #TAM_FECHA_MATRICULACION
	 * @see #FORMATO_FECHA_MATRICULACION
	 */
	public static final String ID_FECHA_MATRICULACION = ID_PREFIX
			+ "FECHA_MATRICULACION";
	/**
	 * Fecha de matriculacion
	 * 
	 * @see #TAM_FECHA_1_MATRICULACION
	 * @see #FORMATO_FECHA_1_MATRICULACION
	 */
	public static final String ID_FECHA_MATRI = ID_PREFIX + "FECHA_MATRI";
	/**
	 * Fecha de matriculacion
	 * 
	 * @see #TAM_FECHA_1_MATRICULACION
	 * @see #FORMATO_FECHA_1_MATRICULACION
	 */
	public static final String ID_FECHA_1_MATRICULACION = ID_PREFIX
			+ "FECHA_1_MATRICULACION";
	/**
	 * Fecha de ITV
	 * 
	 * @see #TAM_FECHA_ITV
	 * @see #FORMATO_FECHA_ITV
	 */
	public static final String ID_FECHA_ITV = ID_PREFIX + "FECHA_ITV";
	/**
	 * DNI Titular
	 * 
	 * @see #TAM_DNI_TITULAR
	 */
	public static final String ID_DNI_TITULAR = ID_PREFIX + "DNI_TITULAR";
	/**
	 * Primer Apellido, razon social o denominacion del titular
	 * 
	 * @see #TAM_PRIMER_APELLIDO
	 */
	public static final String ID_PRIMER_APELLIDO = ID_PREFIX
			+ "PRIMER_APELLIDO";
	/**
	 * Segundo Apellido
	 * 
	 * @see #TAM_SEGUNDO_APELLIDO
	 */
	public static final String ID_SEGUNDO_APELLIDO = ID_PREFIX
			+ "SEGUNDO_APELLIDO";
	/**
	 * Nombre del titular
	 * 
	 * @see #TAM_NOMBRE
	 */
	public static final String ID_NOMBRE = ID_PREFIX + "NOMBRE";
	/**
	 * Domicilio del titular
	 * 
	 * @see #TAM_DOMICILIO_TITULAR
	 */
	public static final String ID_NOMBRE_TITULAR = ID_PREFIX + "NOMBRE_TITULAR";
	/**
	 * Domicilio del titular
	 * 
	 * @see #TAM_DOMICILIO_TITULAR
	 */
	public static final String ID_DOMICILIO_TITULAR = ID_PREFIX
			+ "DOMICILIO_TITULAR";
	/**
	 * Municipio del titular
	 * 
	 * @see #TAM_MUNICIPIO_TITULAR
	 */
	public static final String ID_MUNICIPIO_TITULAR = ID_PREFIX
			+ "MUNICIPIO_TITULAR";
	/**
	 * Pueblo del titular
	 * 
	 * @see #TAM_PUEBLO_TITULAR
	 */
	public static final String ID_PUEBLO_TITULAR = ID_PREFIX + "PUEBLO_TITULAR";
	/**
	 * Siglas de la provincia del titular
	 * 
	 * @see #TAM_PROVINCIA_TITULAR
	 * @see #TAM_NOMBRE_PROVINCIA_TITULAR
	 */
	public static final String ID_PROVINCIA_TITULAR = ID_PREFIX
			+ "PROVINCIA_TITULAR";
	/**
	 * Codigo postal del titular
	 * 
	 * @see #TAM_CP_TITULAR
	 */
	public static final String ID_CP_TITULAR = ID_PREFIX + "CP_TITULAR";
	/**
	 * Sexo del titular
	 * 
	 * @see #TAM_SEXO_TITULAR
	 */
	public static final String ID_SEXO_TITULAR = ID_PREFIX + "SEXO_TITULAR";
	/**
	 * Fecha de nacimiento del titular
	 * 
	 * @see #TAM_FECHA_NACIMIENTO
	 * @see #FORMATO_FECHA_NACIMIENTO
	 */
	public static final String ID_FECHA_NACIMIENTO = ID_PREFIX
			+ "FECHA_NACIMIENTO";
	/**
	 * Numero del documento
	 * 
	 * @see #TAM_NUM_DOCUMENTO
	 */
	public static final String ID_NUM_DOCUMENTO = ID_PREFIX + "NUM_DOCUMENTO";
	/**
	 * Documento que acredita al representante del titular
	 * 
	 * @see #TAM_ACRED_REPRESENTANTE
	 */
	public static final String ID_ACRED_REPRESENTANTE = ID_PREFIX
			+ "ACRED_REPRESENTANTE";
	/**
	 * Nombre y apellidos del representante del titular
	 * 
	 * @see #TAM_REPRESENTANTE
	 * @see #TAM_REPRESENTANTE_BAJA
	 */
	public static final String ID_REPRESENTANTE = ID_PREFIX + "REPRESENTANTE";
	/**
	 * DNI del representante del titular
	 * 
	 * @see #TAM_DNI_REPRESENTANTE
	 */
	public static final String ID_DNI_REPRESENTANTE = ID_PREFIX
			+ "DNI_REPRESENTANTE";
	/**
	 * Concepto en que representa al titular
	 * 
	 * @see #TAM_CONCEPTO_REPR
	 */
	public static final String ID_CONCEPTO_REPR = ID_PREFIX + "CONCEPTO_REPR";
	/**
	 * Domicilio del representante
	 */
	public static final String ID_DOMICILIO_REPR = ID_PREFIX + "DOMICILIO_REPR";
	/**
	 * Provincia del representante
	 */
	public static final String ID_PROVINCIA_REPR = ID_PREFIX + "DOMICILIO_REPR";
	/**
	 * Municipio del representante
	 */
	public static final String ID_MUNICIPIO_REPR = ID_PREFIX + "MUNICIPIO_REPR";
	/**
	 * Código postal del representante
	 */
	public static final String ID_CP_REPR = ID_PREFIX + "CP_REPR";
	/**
	 * Marca del vehiculo
	 * 
	 * @see #TAM_MARCA
	 */
	public static final String ID_MARCA = ID_PREFIX + "MARCA";
	/**
	 * Modelo del vehiculo
	 * 
	 * @see #TAM_MODELO
	 */
	public static final String ID_MODELO = ID_PREFIX + "MODELO";
	/**
	 * Numero de bastidor
	 * 
	 * @see #TAM_BASTIDOR
	 */
	public static final String ID_BASTIDOR = ID_PREFIX + "BASTIDOR";
	/**
	 * Procedencia
	 * 
	 * @see #TAM_PROCEDENCIA
	 */
	public static final String ID_PROCEDENCIA = ID_PREFIX + "PROCEDENCIA";
	/**
	 * Color
	 * 
	 * @see #TAM_COLOR
	 */
	public static final String ID_COLOR = ID_PREFIX + "COLOR";
	/**
	 * Tipo de vehiculo
	 * 
	 * @see #TAM_TIPO_VEHICULO
	 */
	public static final String ID_TIPO_VEHICULO = ID_PREFIX + "TIPO_VEHICULO";
	/**
	 * Carburante
	 * 
	 * @see #TAM_CARBURANTE
	 */
	public static final String ID_CARBURANTE = ID_PREFIX + "CARBURANTE";
	/**
	 * Potencia
	 * 
	 * @see #TAM_POTENCIA
	 */
	public static final String ID_POTENCIA = ID_PREFIX + "POTENCIA";
	/**
	 * Cilindrada
	 * 
	 * @see #TAM_CILINDRADA
	 */
	public static final String ID_CILINDRADA = ID_PREFIX + "CILINDRADA";
	/**
	 * CO2
	 */
	public static final String ID_CO2 = ID_PREFIX + "CO2";
	/**
	 * Tara
	 * 
	 * @see #TAM_TARA
	 */
	public static final String ID_TARA = ID_PREFIX + "TARA";
	/**
	 * Masa
	 * 
	 * @see #TAM_MASA
	 */
	public static final String ID_MASA = ID_PREFIX + "MASA";
	/**
	 * Plazas
	 * 
	 * @see #TAM_PLAZAS
	 */
	public static final String ID_PLAZAS = ID_PREFIX + "PLAZAS";
	/**
	 * Matricula del ayuntamiento
	 * 
	 * @see #TAM_MATRICULA_AYTO
	 */
	public static final String ID_MATRICULA_AYTO = ID_PREFIX + "MATRICULA_AYTO";
	/**
	 * Exceso de peso
	 * 
	 * @see #TAM_EXCESO_PESO
	 */
	public static final String ID_EXCESO_PESO = ID_PREFIX + "EXCESO_PESO";
	/**
	 * Fecha limite de validez de matricula turistica
	 * 
	 * @see #TAM_FECHA_LIMITE
	 * @see #FORMATO_FECHA_LIMITE
	 */
	public static final String ID_FECHA_LIMITE = ID_PREFIX + "FECHA_LIMITE";
	/**
	 * Fecha de la primera matriculacion
	 * 
	 * @see #TAM_FECHA_PRIMERA_MATR
	 * @see #FORMATO_FECHA_PRIMERA_MATR
	 */
	public static final String ID_FECHA_PRIMERA_MATR = ID_PREFIX
			+ "FECHA_PRIMERA_MATR";
	/**
	 * Servicio al que se destina
	 * 
	 * @see #TAM_SERVICIO
	 */
	public static final String ID_SERVICIO = ID_PREFIX + "SERVICIO";

	// Servicio Anterior
	public static final String ID_CAMBIO_SERVICIO = ID_PREFIX
			+ "CAMBIO_SERVICIO";
	/**
	 * Servicio al que se destina en el nuevo formato DGT
	 * 
	 * @see #TAM_SERVICIO
	 */
	public static final String ID_SERVICIO_MATE = ID_PREFIX + "SERVICIO_MATE";
	/**
	 * Tutela o patria potestad
	 * 
	 * @see #TAM_TUTELA_MATRICULACION
	 * @see #TAM_TUTELA_TRANSMISION
	 */
	public static final String ID_TUTELA = ID_PREFIX + "TUTELA";
	/**
	 * Matricula diplomatica
	 * 
	 * @see #TAM_MATRICULA_DIPL
	 */
	public static final String ID_MATRICULA_DIPL = ID_PREFIX + "MATRICULA_DIPL";
	/**
	 * Tipo de limitacion de disposicion
	 * 
	 * @see #TAM_TIPO_LIMITACION
	 */
	public static final String ID_TIPO_LIMITACION = ID_PREFIX
			+ "TIPO_LIMITACION";
	/**
	 * Fecha de limitacion de disposicion
	 * 
	 * @see #TAM_FECHA_LIMITACION
	 * @see #FORMATO_FECHA_LIMITACION
	 */
	public static final String ID_FECHA_LIMITACION = ID_PREFIX
			+ "FECHA_LIMITACION";
	/**
	 * Campo que ha dejado de ser útil
	 * 
	 * @see #TAM_CAMPO_BLANCO
	 */
	public static final String ID_CAMPO_BLANCO = ID_PREFIX + "CAMPO_BLANCO";
	/**
	 * Financiera de limitacion
	 * 
	 * @see #TAM_FINANCIERA_LIMITACION
	 */
	public static final String ID_FINANCIERA_LIMITACION = ID_PREFIX
			+ "FINANCIERA_LIMITACION";
	/**
	 * Calle donde está el vehiculo
	 * 
	 * @see #TAM_CALLE_VEHICULO
	 * @see #TAM_NOMBRE_CALLE_VEHICULO
	 */
	public static final String ID_CALLE_VEHICULO = ID_PREFIX + "CALLE_VEHICULO";
	/**
	 * Provincia donde está el vehiculo
	 * 
	 * @see #TAM_PROVINCIA_VEHICULO
	 * @see #TAM_NOMBRE_PROVINCIA_VEHICULO
	 */
	public static final String ID_PROVINCIA_VEHICULO = ID_PREFIX
			+ "PROVINCIA_VEHICULO";
	/**
	 * Municipio donde está el vehiculo
	 * 
	 * @see #TAM_MUNICIPIO_VEHICULO
	 */
	public static final String ID_MUNICIPIO_VEHICULO = ID_PREFIX
			+ "MUNICIPIO_VEHICULO";
	/**
	 * Pueblo donde está el vehiculo
	 * 
	 * @see #TAM_PUEBLO_VEHICULO
	 */
	public static final String ID_PUEBLO_VEHICULO = ID_PREFIX
			+ "PUEBLO_VEHICULO";
	/**
	 * Codigo postal donde está el vehiculo
	 * 
	 * @see #TAM_CP_VEHICULO
	 */
	public static final String ID_CP_VEHICULO = ID_PREFIX + "CP_VEHICULO";
	/**
	 * Matrícula del vehiculo PREVER
	 */
	public static final String ID_MATRICULA_PREVER = ID_PREFIX
			+ "MATRICULA_PREVER";
	/**
	 * Marca del vehiculo PREVER
	 */
	public static final String ID_MARCA_PREVER = ID_PREFIX + "MARCA_PREVER";
	/**
	 * Numero del profesional
	 * 
	 * @see #TAM_NUM_PROFESIONAL
	 */
	public static final String ID_NUM_PROFESIONAL = ID_PREFIX
			+ "NUM_PROFESIONAL";
	/**
	 * 6 ultimas cifras del documento
	 * 
	 * @see #TAM_ULT_CIFRAS_DOCUMENTO
	 */
	public static final String ID_ULT_CIFRAS_DOCUMENTO = ID_PREFIX
			+ "ULT_CIFRAS_DOCUMENTO";
	/**
	 * Fecha de creacion del documento
	 * 
	 * @see #TAM_FECHA_CREACION
	 * @see #FORMATO_FECHA_CREACION
	 */
	public static final String ID_FECHA_CREACION = ID_PREFIX + "FECHA_CREACION";
	/**
	 * Codigo ITV
	 * 
	 * @see #TAM_CODIGO_ITV
	 */
	public static final String ID_CODIGO_ITV = ID_PREFIX + "CODIGO_ITV";
	/**
	 * Observaciones
	 * 
	 * @see #TAM_OBSERVACIONES
	 */
	public static final String ID_OBSERVACIONES = ID_PREFIX + "OBSERVACIONES";
	/**
	 * DNI del representante del adquiriente
	 * 
	 * @see #ID_DNI_REPRESENTANTE_ADQUIRIENTE
	 */
	public static final String ID_DNI_REPRESENTANTE_ADQUIRIENTE = ID_PREFIX
			+ "DNI_REPRESENTANTE_ADQUIRIENTE";
	/**
	 * Codigo tipo via domicilio titular
	 * 
	 * @see #TAM_TIPO_VIA_TITULAR
	 */
	public static final String ID_TIPO_VIA_TITULAR = ID_PREFIX
			+ "TIPO_VIA_TITULAR";
	/**
	 * Numero domicilio titular
	 * 
	 * @see #TAM_NUM_DOMICILIO_TITULAR
	 */
	public static final String ID_NUM_DOMICILIO_TITULAR = ID_PREFIX
			+ "NUM_DOMICILIO_TITULAR";
	/**
	 * Letra domicilio titular
	 * 
	 * @see #TAM_LETRA_DOMICILIO_TITULAR
	 */
	public static final String ID_LETRA_DOMICILIO_TITULAR = ID_PREFIX
			+ "LETRA_DOMICILIO_TITULAR";
	/**
	 * Escalera domicilio titular
	 * 
	 * @see #TAM_ESCALERA_DOMICILIO_TITULAR
	 */
	public static final String ID_ESCALERA_DOMICILIO_TITULAR = ID_PREFIX
			+ "ESCALERA_DOMICILIO_TITULAR";
	/**
	 * Piso domicilio titular
	 * 
	 * @see #TAM_PISO_DOMICILIO_TITULAR
	 */
	public static final String ID_PISO_DOMICILIO_TITULAR = ID_PREFIX
			+ "PISO_DOMICILIO_TITULAR";
	/**
	 * Puerta domicilio titular
	 * 
	 * @see #TAM_PUERTA_DOMICILIO_TITULAR
	 */
	public static final String ID_PUERTA_DOMICILIO_TITULAR = ID_PREFIX
			+ "PUERTA_DOMICILIO_TITULAR";
	/**
	 * Km domicilio titular
	 * 
	 * @see #TAM_KM_DOMICILIO_TITULAR
	 */
	public static final String ID_KM_DOMICILIO_TITULAR = ID_PREFIX
			+ "KM_DOMICILIO_TITULAR";
	/**
	 * Hm domicilio titular
	 * 
	 * @see #TAM_HM_DOMICILIO_TITULAR
	 */
	public static final String ID_HM_DOMICILIO_TITULAR = ID_PREFIX
			+ "HM_DOMICILIO_TITULAR";
	/**
	 * Bloque domicilio titular
	 * 
	 * @see #TAM_BLOQUE_DOMICILIO_TITULAR
	 */
	public static final String ID_BLOQUE_DOMICILIO_TITULAR = ID_PREFIX
			+ "BLOQUE_DOMICILIO_TITULAR";
	/**
	 * Tipo tasa
	 * 
	 * @see #TAM_TIPO_TASA
	 */
	public static final String ID_TIPO_TASA = ID_PREFIX + "TIPO_TASA";
	/**
	 * Tipo de la tarjeta ITV
	 * 
	 * @see #TAM_TIPO_ITV
	 */
	public static final String ID_TIPO_ITV = ID_PREFIX + "TIPO_ITV";
	/**
	 * Variante
	 * 
	 * @see #TAM_VARIANTE
	 */
	public static final String ID_VARIANTE = ID_PREFIX + "VARIANTE";
	/**
	 * Versión
	 * 
	 * @see #TAM_VERSION
	 */
	public static final String ID_VERSION = ID_PREFIX + "VERSION";
	/**
	 * Número de homologación
	 * 
	 * @see #TAM_NUM_HOMOLOGACION
	 */
	public static final String ID_NUM_HOMOLOGACION = ID_PREFIX
			+ "NUM_HOMOLOGACION";
	/**
	 * Masa máxima en carga técnicamente admisible
	 * 
	 * @see #TAM_MASA_MAX
	 */
	public static final String ID_MASA_MAX = ID_PREFIX + "MASA_MAX";
	/**
	 * Masa del vehículo en servicio
	 * 
	 * @see #TAM_MASA_SERVICIO
	 */
	public static final String ID_MASA_SERVICIO = ID_PREFIX + "MASA_SERVICIO";
	/**
	 * Potencia neta máxima
	 * 
	 * @see #TAM_POTENCIA_MAX
	 */
	public static final String ID_POTENCIA_MAX = ID_PREFIX + "POTENCIA_MAX";
	/**
	 * Relacion Potencia/Peso
	 * 
	 * @see #TAM_RELACION_POTENCIA_PESO
	 */
	public static final String ID_RELACION_POTENCIA_PESO = ID_PREFIX
			+ "RELACION_POTENCIA_PESO";
	/**
	 * Número de plazas de pie
	 * 
	 * @see #TAM_PLAZAS_DE_PIE
	 */
	public static final String ID_PLAZAS_DE_PIE = ID_PREFIX + "PLAZAS_DE_PIE";
	/**
	 * Código electrónico de Matriculación
	 * 
	 * @see #TAM_CEM
	 */
	public static final String ID_CEM = ID_PREFIX + "CEM";
	/**
	 * Código electrónico de Matriculación para Vehículos Especiales
	 * 
	 * @see #TAM_CEM
	 */
	public static final String ID_CEMA = ID_PREFIX + "CEMA";
	/**
	 * Número de cotitulares
	 * 
	 * @see #TAM_NUM_COTITULARES
	 */
	public static final String ID_NUM_COTITULARES = ID_PREFIX + "COTITULARES";
	/**
	 * DNI del 2º Titular
	 * 
	 * @see #TAM_DNI_TITULAR_2
	 */
	public static final String ID_DNI_TITULAR_2 = ID_PREFIX + "DNI_TITULAR_2";
	/**
	 * Primer Apellido, Razón Social o Denominación del 2º Titular
	 * 
	 * @see #TAM_PRIMER_APELLIDO_TITULAR_2
	 */
	public static final String ID_PRIMER_APELLIDO_TITULAR_2 = ID_PREFIX
			+ "PRIMER_APELLIDO_TITULAR_2";
	/**
	 * Segundo Apellido del 2º Titular
	 * 
	 * @see #TAM_SEGUNDO_APELLIDO_TITULAR_2
	 */
	public static final String ID_SEGUNDO_APELLIDO_TITULAR_2 = ID_PREFIX
			+ "SEGUNDO_APELLIDO_TITULAR_2";
	/**
	 * Nombre del 2º Titular
	 * 
	 * @see #TAM_NOMBRE_TITULAR_2
	 */
	public static final String ID_NOMBRE_TITULAR_2 = ID_PREFIX
			+ "NOMBRE_TITULAR_2";
	/**
	 * Domicilio completo del 2º Titular
	 * 
	 * @see #TAM_DOMICILIO_TITULAR_2
	 */
	public static final String ID_DOMICILIO_TITULAR_2 = ID_PREFIX
			+ "DOMICILIO_TITULAR_2";
	/**
	 * Reservado (1)
	 * 
	 * @see #TAM_RESERVADO_1
	 */
	public static final String ID_RESERVADO_1 = ID_PREFIX + "RESERVADO_1";

	/*
	 * Constantes para los datos de los cotitulares
	 */
	// ID_NOMBRE_APELLIDOS_PRIMER_COTITULAR
	public static final String ID_NOMBRE_APELLIDOS_PRIMER_COTITULAR = ID_PREFIX
			+ "NOMBRE_APELLIDOS_PRIMER_COTITULAR";

	// ID_DNI_PRIMER_COTITULAR
	public static final String ID_DNI_PRIMER_COTITULAR = ID_PREFIX
			+ "DNI_PRIMER_COTITULAR";

	// ID_FECHA_NACIMIENTO_PRIMER_COTITULAR
	public static final String ID_FECHA_NACIMIENTO_PRIMER_COTITULAR = ID_PREFIX
			+ "FECHA_NACIMIENTO_PRIMER_COTITULAR";

	// ID_SEXO_PRIMER_COTITULAR
	public static final String ID_SEXO_PRIMER_COTITULAR = ID_PREFIX
			+ "SEXO_PRIMER_COTITULAR";

	// ID_NOMBRE_APELLIDOS_SEGUNDO_COTITULAR
	public static final String ID_NOMBRE_APELLIDOS_SEGUNDO_COTITULAR = ID_PREFIX
			+ "NOMBRE_APELLIDOS_SEGUNDO_COTITULAR";

	// ID_DNI_SEGUNDO_COTITULAR
	public static final String ID_DNI_SEGUNDO_COTITULAR = ID_PREFIX
			+ "DNI_SEGUNDO_COTITULAR";

	// ID_FECHA_NACIMIENTO_SEGUNDO_COTITULAR
	public static final String ID_FECHA_NACIMIENTO_SEGUNDO_COTITULAR = ID_PREFIX
			+ "FECHA_NACIMIENTO_SEGUNDO_COTITULAR";

	// ID_SEXO_SEGUNDO_COTITULAR
	public static final String ID_SEXO_SEGUNDO_COTITULAR = ID_PREFIX
			+ "SEXO_SEGUNDO_COTITULAR";

	/**
	 * Nombre de la gestoria o del profesional
	 * 
	 * @see #TAM_NOMBRE_PROFESIONAL
	 */
	public static final String ID_NOMBRE_PROFESIONAL = ID_PREFIX
			+ "NOMBRE_PROFESIONAL";

	/**
	 * Campo para insertar la nube de puntos
	 */
	public static final String ID_NUBE_PUNTOS = ID_PREFIX + "NUBE_PUNTOS";

	/**
	 * Campo para insertar la nube de puntos segunda de matriculación
	 */
	public static final String ID_NUBE_PUNTOS_2 = ID_PREFIX + "NUBE_PUNTOS_2";

	/**
	 * Campo para insertar la fecha de devolucion
	 */
	public static final String ID_FECHA_DEVOLUCION = ID_PREFIX
			+ "FECHA_DEVOLUCION";

	/**
	 * Campo para insertar las siglas del tipo de via del titular 2
	 */
	public static final String ID_TIPO_VIA_TITULAR_2 = ID_PREFIX
			+ "TIPO_VIA_TITULAR_2";

	/**
	 * Campo para insertar las siglas del tipo de via del titular 3
	 */
	public static final String ID_TIPO_VIA_TITULAR_3 = ID_PREFIX
			+ "TIPO_VIA_TITULAR_3";

	/**
	 * Campo para insertar el DNI del vendedor
	 */
	public static final String ID_DNI_VENDEDOR = ID_PREFIX + "DNI_VENDEDOR";

	/**
	 * Campo para insertar la Fecha de Nacimiento del vendedor
	 */
	public static final String ID_FECHA_NACIMIENTO_VENDEDOR = ID_PREFIX
			+ "FECHA_NACIMIENTO_VENDEDOR";

	/**
	 * Campo para insertar el sexo del vendedor
	 */
	public static final String ID_SEXO_VENDEDOR = ID_PREFIX + "SEXO_VENDEDOR";

	/**
	 * Campo para insertar la provincia del vendedor
	 */
	public static final String ID_PROVINCIA_VENDEDOR = ID_PREFIX
			+ "PROVINCIA_VENDEDOR";

	/**
	 * Campo para insertar el municipio del vendedor
	 */
	public static final String ID_MUNICIPIO_VENDEDOR = ID_PREFIX
			+ "MUNICIPIO_VENDEDOR";

	/**
	 * Campo para insertar codigo postal del vendedor
	 */
	public static final String ID_CP_VENDEDOR = ID_PREFIX + "CP_VENDEDOR";

	/**
	 * Campo para insertar tipo via del vehiculo
	 */
	public static final String ID_TIPO_VIA_VEHICULO = ID_PREFIX
			+ "TIPO_VIA_VEHICULO";

	/**
	 * Campo para insertar pago efectivo impuestos
	 */
	public static final String ID_PAGO_EFECTIVO_IMPUESTOS = ID_PREFIX
			+ "PAGO_EFECTIVO_IMPUESTOS";

	/**
	 * Campo para insertar pago cuenta impuestos
	 */
	public static final String ID_PAGO_CUENTA_IMPUESTOS = ID_PREFIX
			+ "PAGO_CUENTA_IMPUESTOS";

	/**
	 * Campo para insertar el numero de cuenta impuestos
	 */
	public static final String ID_NUMERO_CUENTA_IMPUESTOS = ID_PREFIX
			+ "NUMERO_CUENTA_IMPUESTOS";

	/**
	 * Campo para insertar la fecha de presentacion de impuestos
	 */
	public static final String ID_FECHA_PRESENTACION_IMPUESTOS = ID_PREFIX
			+ "FECHA_PRESENTACION_IMPUESTOS";

	/**
	 * Campo para insertar el organismo IVMT
	 */
	public static final String ID_ORGANISMO_IVMT = ID_PREFIX + "ORGANISMO_IVMT";

	/**
	 * Campo para insertar la fecha de alta de autoliquidacion IVMT
	 */
	public static final String ID_FECHA_ALTA_AUTOLIQ_IVMT = ID_PREFIX
			+ "FECHA_ALTA_AUTOLIQ_IVMT";

	/**
	 * Campo para insertar el código de beneficios fiscales IVMT
	 */
	public static final String ID_CODIGO_BENEF_FISCALES_IVTM = ID_PREFIX
			+ "CODIGO_BENEF_FISCALES_IVTM";

	/**
	 * Campo para insertar la cuota anual IVMT
	 */
	public static final String ID_CUOTA_ANUAL_IVTM = ID_PREFIX
			+ "CUOTA_ANUAL_IVTM";

	/**
	 * Campo para insertar la cuota periodo IVMT
	 */
	public static final String ID_CUOTA_PERIODO_IVTM = ID_PREFIX
			+ "CUOTA_PERIODO_IVTM";

	/**
	 * Campo para insertar la cuota a pagar IVMT
	 */
	public static final String ID_CUOTA_PAGAR_IVTM = ID_PREFIX
			+ "CUOTA_PAGAR_IVTM";

	/**
	 * Campo para insertar la forma de pago IVMT
	 */
	public static final String ID_FORMA_PAGO_IVTM = ID_PREFIX
			+ "FORMA_PAGO_IVTM";

	/**
	 * Campo para insertar la cuenta IVMT
	 */
	public static final String ID_CUENTA_IVTM = ID_PREFIX + "CUENTA_IVTM";

	/**
	 * Campo para insertar el DNI del titular de la cuenta IVMT
	 */
	public static final String ID_DNI_TITULAR_CUENTA_IVTM = ID_PREFIX
			+ "DNI_TITULAR_CUENTA_IVTM";

	/**
	 * Campo para insertar la fecha de caducidad de la tarjeta IVMT
	 */
	public static final String ID_FECHA_CADUCIDAD_TARJETA_IVTM = ID_PREFIX
			+ "FECHA_CADUCIDAD_TARJETA_IVTM";

	/*
	 * 576
	 */
	public static final String ID_NUM_DECLARACION_COMP_576 = ID_PREFIX
			+ "NUM_DECLARACION_COMP_576";
	public static final String ID_BASE_IMPONIBLE_576 = ID_PREFIX
			+ "BASE_IMPONIBLE_576";
	public static final String ID_BASE_IMPONIBLE_REDUC_576 = ID_PREFIX
			+ "BASE_IMPONIBLE_REDUC_576";
	public static final String ID_TIPO_GRAVEMENT_576 = ID_PREFIX
			+ "TIPO_GRAVAMEN_576";
	public static final String ID_CUOTA_A_INGRESAR_576 = ID_PREFIX
			+ "CUOTA_A_INGRESAR_576";
	public static final String ID_IMPORTE_TOTAL_576 = ID_PREFIX
			+ "IMPORTE_TOTAL_576";
	public static final String ID_CUOTA_576 = ID_PREFIX + "CUOTA_576";
	public static final String ID_CANT_A_DEDUCIR_576 = ID_PREFIX
			+ "CANT_A_DEDUCIR_576";
	public static final String ID_DEDUCCION_LINEAL_576 = ID_PREFIX
			+ "DEDUCCION_LINEAL_576";
	public static final String ID_NUM_REG_LIMITACION = ID_PREFIX
			+ "NUM_REG_LIMITACION";
	public static final String ID_NRC_576 = ID_PREFIX + "NRC_576";
	public static final String ID_CCC_576 = ID_PREFIX + "CCC_576";
	public static final String ID_FECHA_PAGO_576 = ID_PREFIX + "FECHA_PAGO_576";
	public static final String ID_IMPORTE_PAGO_IVTM = ID_PREFIX
			+ "IMPORTE_PAGO_IVTM";
	public static final String ID_FECHA_PAGO_IVTM = ID_PREFIX
			+ "FECHA_PAGO_IVTM";
	public static final String ID_CCC_PAGO_IVTM = ID_PREFIX + "CCC_PAGO_IVTM";
	public static final String ID_NRC_PAGO_IVTM = ID_PREFIX + "NRC_PAGO_IVTM";
	public static final String ID_NJC_IVTM = ID_PREFIX + "NJC_IVTM";
	public static final String ID_CAUSA_HECHO_IMPONIBLE = ID_PREFIX
			+ "CAUSA_HECHO_IMPONIBLE";
	public static final String ID_OBSERVACIONES_AEAT = ID_PREFIX
			+ "OBSERVACIONES_AEAT";
	public static final String ID_CLASIFICACION_CONSTRUCCION = ID_PREFIX
			+ "CLASIFICACION_CONSTRUCCION";
	public static final String ID_CLASIFICACION_UTILIZACION = ID_PREFIX
			+ "CLASIFICACION_UTILIZACION";
	public static final String ID_CLASIFICACION_DIRECTIVA_CEE = ID_PREFIX
			+ "CLASIFICACION_DIRECTIVA_CEE";
	public static final String ID_TIPO_TARJETA_ITV = ID_PREFIX
			+ "TIPO_TARJETA_ITV";
	public static final String ID_NUM_SERIE_ITV = ID_PREFIX + "NUM_SERIE_ITV";
	public static final String ID_EPIGRAFE = ID_PREFIX + "EPIGRAFE";
	public static final String ID_NUM_KM_USO = ID_PREFIX + "NUM_KM_USO";
	public static final String ID_DNI_INTRODUCTOR_VEHICULO = ID_PREFIX
			+ "DNI_INTRODUCTOR_VEHICULO";
	public static final String ID_INTRODUCTOR_VEHICULO = ID_PREFIX
			+ "INTRODUCTOR_VEHICULO";
	public static final String ID_CLASIFICACION_ITV = ID_PREFIX
			+ "CLASIFICACION_ITV";
	public static final String ID_NO_SUJECCION_06 = ID_PREFIX
			+ "NO_SUJECCION_06";
	public static final String ID_REDUCCION_05 = ID_PREFIX + "REDUCCION_05";
	public static final String ID_REDUCCION_576 = ID_PREFIX + "REDUCCION_576";
	public static final String ID_FUNDAMENTO_576 = ID_PREFIX + "FUNDAMENTO_576";

	/*
	 * Log 576
	 */
	public static final String ID_CEP = ID_PREFIX + "CEP";
	public static final String ID_BASTIDOR_LOG576 = ID_PREFIX
			+ "BASTIDOR_LOG576";
	public static final String ID_FECHA_PRESENTACION_LOG576 = ID_PREFIX
			+ "FECHA_PRESENTACION_LOG576";
	public static final String ID_HORA_PRESENTACION_LOG576 = ID_PREFIX
			+ "HORA_PRESENTACION_LOG576";

	public static final String ID_ANAGRAMA_TITULAR = ID_PREFIX
			+ "ANAGRAMA_TITULAR";
	public static final String ID_ANAGRAMA_REPRESENTANTE = ID_PREFIX
			+ "ANAGRAMA_REPRESENTANTE";

	/*
	 * 
	 * 
	 * Agregados para TRANSMISION
	 */
	/**
	 * DNI Transmitente
	 * 
	 * @see #TAM_DNI_TRANSMITENTE
	 */
	public static final String ID_DNI_TRANSMITENTE = ID_PREFIX
			+ "DNI_TRANSMITENTE";
	/**
	 * DNI Adquirente
	 * 
	 * @see #TAM_DNI_ADQUIRENTE
	 */
	public static final String ID_DNI_ADQUIRENTE = ID_PREFIX + "DNI_ADQUIRENTE";
	/**
	 * Primer Apellido Adquirente
	 * 
	 * @see #TAM_PRIMER_APELLIDO_ADQUIRENTE
	 */
	public static final String ID_PRIMER_APELLIDO_ADQUIRENTE = ID_PREFIX
			+ "PRIMER_APELLIDO_ADQUIRENTE";
	/**
	 * Segundo Apellido Adquirente
	 * 
	 * @see #TAM_SEGUNDO_APELLIDO_ADQUIRENTE
	 */
	public static final String ID_SEGUNDO_APELLIDO_ADQUIRENTE = ID_PREFIX
			+ "SEGUNDO_APELLIDO_ADQUIRENTE";
	/**
	 * Nombre Adquirente
	 * 
	 * @see #TAM_NOMBRE_ADQUIRENTE
	 */
	public static final String ID_NOMBRE_ADQUIRENTE = ID_PREFIX
			+ "NOMBRE_ADQUIRENTE";
	/**
	 * Domicilio Adquirente
	 * 
	 * @see #TAM_DOMICILIO_ADQUIRENTE
	 */
	public static final String ID_DOMICILIO_ADQUIRENTE = ID_PREFIX
			+ "DOMICILIO_ADQUIRENTE";
	/**
	 * Municipio Adquirente
	 * 
	 * @see #TAM_MUNICIPIO_ADQUIRENTE
	 */
	public static final String ID_MUNICIPIO_ADQUIRENTE = ID_PREFIX
			+ "MUNICIPIO_ADQUIRENTE";
	/**
	 * Pueblo Adquirente
	 * 
	 * @see #TAM_PUEBLO_ADQUIRENTE
	 */
	public static final String ID_PUEBLO_ADQUIRENTE = ID_PREFIX
			+ "PUEBLO_ADQUIRENTE";
	/**
	 * Siglas de la Provincia Adquirente
	 * 
	 * @see #TAM_PROVINCIA_ADQUIRENTE
	 */
	public static final String ID_PROVINCIA_ADQUIRENTE = ID_PREFIX
			+ "PROVINCIA_ADQUIRENTE";

	// 620
	public static final String ID_PROVINCIA_ADQUIRENTE_DESC = ID_PREFIX
			+ "PROVINCIA_ADQUIRENTE_DESC";

	/**
	 * Codigo Postal Adquirente
	 * 
	 * @see #TAM_CP_ADQUIRENTE
	 */
	public static final String ID_CP_ADQUIRENTE = ID_PREFIX + "CP_ADQUIRENTE";
	/**
	 * Sexo Adquirente
	 * 
	 * @see #TAM_SEXO_ADQUIRENTE
	 */
	public static final String ID_SEXO_ADQUIRENTE = ID_PREFIX
			+ "SEXO_ADQUIRENTE";
	/**
	 * Fecha Nacimiento Adquirente
	 * 
	 * @see #TAM_FECHA_NACIMIENTO_ADQUIRENTE
	 */
	public static final String ID_FECHA_NACIMIENTO_ADQUIRENTE = ID_PREFIX
			+ "FECHA_NACIMIENTO_ADQUIRENTE";
	/**
	 * Tipo de Transferencia
	 * 
	 * @see #TAM_TIPO_TRANSFERENCIA
	 */
	public static final String ID_TIPO_TRANSFERENCIA = ID_PREFIX
			+ "TIPO_TRANSFERENCIA";
	/**
	 * Motivo ITV
	 * 
	 * @see #TAM_MOTIVO_ITV
	 */
	public static final String ID_MOTIVO_ITV = ID_PREFIX + "MOTIVO_ITV";

	/**
	 * Resultado ITV
	 */
	public static final String ID_RESULTADO_ITV = ID_PREFIX + "RESULTADO_ITV";
	/**
	 * Nombre Transmitente
	 * 
	 * @see #TAM_NOMBRE_TRANSMITENTE
	 */
	public static final String ID_NOMBRE_TRANSMITENTE = ID_PREFIX
			+ "NOMBRE_TRANSMITENTE";
	/**
	 * Documento que acredita al Representante del Adquirente
	 * 
	 * @see #TAM_ACRED_REPR_ADQUIRENTE
	 */
	public static final String ID_ACRED_REPR_ADQUIRENTE = ID_PREFIX
			+ "ACRED_REPR_ADQUIRENTE";
	/**
	 * Nombre y Apellidos del Representante del Adquirente
	 * 
	 * @see #TAM_REPRESENTANTE_ADQUIRENTE
	 */
	public static final String ID_REPRESENTANTE_ADQUIRENTE = ID_PREFIX
			+ "REPRESENTANTE_ADQUIRENTE";
	/**
	 * Nombre y Apellidos del Representante del Transmitente
	 * 
	 * @see #TAM_REPRESENTANTE_TRANSMITENTE
	 */
	public static final String ID_REPRESENTANTE_TRANSMITENTE = ID_PREFIX
			+ "REPRESENTANTE_TRANSMITENTE";
	/**
	 * DNI del Representante del Adquirente
	 * 
	 * @see #TAM_DNI_REPRESENTANTE_ADQUIRENTE
	 */
	public static final String ID_DNI_REPRESENTANTE_ADQUIRENTE = ID_PREFIX
			+ "DNI_REPRESENTANTE_ADQUIRENTE";
	/**
	 * DNI del Representante del Transmitente
	 * 
	 * @see #TAM_DNI_REPRESENTANTE_TRANSMITENTE
	 */
	public static final String ID_DNI_REPRESENTANTE_TRANSMITENTE = ID_PREFIX
			+ "DNI_REPRESENTANTE_TRANSMITENTE";
	/**
	 * Concepto en que representa al Adquirente
	 * 
	 * @see #TAM_CONCEPTO_REPR_ADQUIRENTE
	 */
	public static final String ID_CONCEPTO_REPR_ADQUIRENTE = ID_PREFIX
			+ "CONCEPTO_REPR_ADQUIRENTE";
	/**
	 * Arrendador
	 * 
	 * @see #TAM_ARRENDADOR
	 */
	public static final String ID_ARRENDADOR = ID_PREFIX + "ARRENDADOR";
	/**
	 * DNI Arrendador
	 * 
	 * @see #TAM_DNI_ARRENDADOR
	 */
	public static final String ID_DNI_ARRENDADOR = ID_PREFIX + "DNI_ARRENDADOR";
	/**
	 * Representante Arrendador
	 * 
	 * @see #TAM_REPRESENTANTE_ARRENDADOR
	 */
	public static final String ID_REPRESENTANTE_ARRENDADOR = ID_PREFIX
			+ "REPRESENTANTE_ARRENDADOR";
	/**
	 * DNI Representante Arrendador
	 * 
	 * @see #TAM_DNI_REPRESENTANTE_ARRENDADOR
	 */
	public static final String ID_DNI_REPRESENTANTE_ARRENDADOR = ID_PREFIX
			+ "DNI_REPRESENTANTE_ARRENDADOR";
	/**
	 * Numero de la calle donde está el vehiculo
	 * 
	 * @see #TAM_NUM_CALLE_VEHICULO
	 */
	public static final String ID_NUM_CALLE_VEHICULO = ID_PREFIX
			+ "NUM_CALLE_VEHICULO";
	/**
	 * Modo de adjudicacion
	 * 
	 * @see #TAM_MODO_ADJUDICACION
	 */
	public static final String ID_MODO_ADJUDICACION = ID_PREFIX
			+ "MODO_ADJUDICACION";
	/**
	 * Primer Apellido Transmitente
	 * 
	 * @see #TAM_PRIMER_APELLIDO_TRANSMITENTE
	 */
	public static final String ID_PRIMER_APELLIDO_TRANSMITENTE = ID_PREFIX
			+ "PRIMER_APELLIDO_TRANSMITENTE";
	/**
	 * Segundo Apellido Transmitente
	 * 
	 * @see #TAM_SEGUNDO_APELLIDO_TRANSMITENTE
	 */
	public static final String ID_SEGUNDO_APELLIDO_TRANSMITENTE = ID_PREFIX
			+ "SEGUNDO_APELLIDO_TRANSMITENTE";
	/**
	 * Domicilio Transmitente
	 * 
	 * @see #TAM_DOMICILIO_TRANSMITENTE
	 */
	public static final String ID_DOMICILIO_TRANSMITENTE = ID_PREFIX
			+ "DOMICILIO_TRANSMITENTE";
	/**
	 * Municipio Transmitente
	 * 
	 * @see #TAM_MUNICIPIO_TRANSMITENTE
	 */
	public static final String ID_MUNICIPIO_TRANSMITENTE = ID_PREFIX
			+ "MUNICIPIO_TRANSMITENTE";
	/**
	 * Pueblo Transmitente
	 * 
	 * @see #TAM_PUEBLO_TRANSMITENTE
	 */
	public static final String ID_PUEBLO_TRANSMITENTE = ID_PREFIX
			+ "PUEBLO_TRANSMITENTE";
	/**
	 * Siglas de la Provincia Transmitente
	 * 
	 * @see #TAM_PROVINCIA_TRANSMITENTE
	 */
	public static final String ID_PROVINCIA_TRANSMITENTE = ID_PREFIX
			+ "PROVINCIA_TRANSMITENTE";

	// 620
	public static final String ID_PROVINCIA_TRANSMITENTE_DESC = ID_PREFIX
			+ "PROVINCIA_TRANSMITENTE_DESC";

	/**
	 * Codigo Postal Transmitente
	 * 
	 * @see #TAM_CP_TRANSMITENTE
	 */
	public static final String ID_CP_TRANSMITENTE = ID_PREFIX
			+ "CP_TRANSMITENTE";
	/**
	 * Sexo Transmitente
	 * 
	 * @see #TAM_SEXO_TRANSMITENTE
	 */
	public static final String ID_SEXO_TRANSMITENTE = ID_PREFIX
			+ "SEXO_TRANSMITENTE";
	/**
	 * Documento que acredita al Representante del Transmitente
	 * 
	 * @see #TAM_ACRED_REPR_TRANSMITENTE
	 */
	public static final String ID_ACRED_REPR_TRANSMITENTE = ID_PREFIX
			+ "ACRED_REPR_TRANSMITENTE";
	/**
	 * Codigo tipo via domicilio Transmitente
	 * 
	 * @see #TAM_TIPO_VIA_TRANSMITENTE
	 */
	public static final String ID_TIPO_VIA_TRANSMITENTE = ID_PREFIX
			+ "TIPO_VIA_TRANSMITENTE";
	/**
	 * Numero domicilio Transmitente
	 * 
	 * @see #TAM_NUM_DOMICILIO_TRANSMITENTE
	 */
	public static final String ID_NUM_DOMICILIO_TRANSMITENTE = ID_PREFIX
			+ "NUM_DOMICILIO_TRANSMITENTE";
	/**
	 * Letra domicilio Transmitente
	 * 
	 * @see #TAM_LETRA_DOMICILIO_TRANSMITENTE
	 */
	public static final String ID_LETRA_DOMICILIO_TRANSMITENTE = ID_PREFIX
			+ "LETRA_DOMICILIO_TRANSMITENTE";
	/**
	 * Escalera domicilio Transmitente
	 * 
	 * @see #TAM_ESCALERA_DOMICILIO_TRANSMITENTE
	 */
	public static final String ID_ESCALERA_DOMICILIO_TRANSMITENTE = ID_PREFIX
			+ "ESCALERA_DOMICILIO_TRANSMITENTE";
	/**
	 * Piso domicilio Transmitente
	 * 
	 * @see #TAM_PISO_DOMICILIO_TRANSMITENTE
	 */
	public static final String ID_PISO_DOMICILIO_TRANSMITENTE = ID_PREFIX
			+ "PISO_DOMICILIO_TRANSMITENTE";
	/**
	 * Puerta domicilio Transmitente
	 * 
	 * @see #TAM_PUERTA_DOMICILIO_TRANSMITENTE
	 */
	public static final String ID_PUERTA_DOMICILIO_TRANSMITENTE = ID_PREFIX
			+ "PUERTA_DOMICILIO_TRANSMITENTE";
	/**
	 * Codigo tipo via domicilio Adquirente
	 * 
	 * @see #TAM_TIPO_VIA_ADQUIRENTE
	 */
	public static final String ID_TIPO_VIA_ADQUIRENTE = ID_PREFIX
			+ "TIPO_VIA_ADQUIRENTE";
	/**
	 * Numero domicilio Adquirente
	 * 
	 * @see #TAM_NUM_DOMICILIO_ADQUIRENTE
	 */
	public static final String ID_NUM_DOMICILIO_ADQUIRENTE = ID_PREFIX
			+ "NUM_DOMICILIO_ADQUIRENTE";
	/**
	 * Letra domicilio Adquirente
	 * 
	 * @see #TAM_LETRA_DOMICILIO_ADQUIRENTE
	 */
	public static final String ID_LETRA_DOMICILIO_ADQUIRENTE = ID_PREFIX
			+ "LETRA_DOMICILIO_ADQUIRENTE";
	/**
	 * Escalera domicilio Adquirente
	 * 
	 * @see #TAM_ESCALERA_DOMICILIO_ADQUIRENTE
	 */
	public static final String ID_ESCALERA_DOMICILIO_ADQUIRENTE = ID_PREFIX
			+ "ESCALERA_DOMICILIO_ADQUIRENTE";
	/**
	 * Piso domicilio Adquirente
	 * 
	 * @see #TAM_PISO_DOMICILIO_ADQUIRENTE
	 */
	public static final String ID_PISO_DOMICILIO_ADQUIRENTE = ID_PREFIX
			+ "PISO_DOMICILIO_ADQUIRENTE";
	/**
	 * Puerta domicilio Adquirente
	 * 
	 * @see #TAM_PUERTA_DOMICILIO_ADQUIRENTE
	 */
	public static final String ID_PUERTA_DOMICILIO_ADQUIRENTE = ID_PREFIX
			+ "PUERTA_DOMICILIO_ADQUIRENTE";
	/**
	 * Codigo Electronico de Transferencia
	 * 
	 * @see #TAM_CET
	 */
	public static final String ID_CET = ID_PREFIX + "CET";
	/**
	 * DNI Arrendador
	 * 
	 * @see #TAM_DNI_PRESENTADOR
	 */
	public static final String ID_DNI_PRESENTADOR = ID_PREFIX
			+ "DNI_PRESENTADOR";

	/**
	 * DNI Arrendador
	 * 
	 * @see #TAM_DNI_PRESENTADOR
	 */
	public static final String ID_DOMICILIO_PRESENTADOR = ID_PREFIX
			+ "DOMICILIO_PRESENTADOR";

	/**
	 * DNI Arrendador
	 * 
	 * @see #TAM_DNI_PRESENTADOR
	 */
	public static final String ID_FECHA_DEVENGO = ID_PREFIX + "FECHA_DEVENGO";

	/**
	 * DNI Arrendador
	 * 
	 * @see #TAM_DNI_PRESENTADOR
	 */
	public static final String ID_NUM_CILINDROS = ID_PREFIX + "NUM_CILINDROS";

	/**
	 * DNI Arrendador
	 * 
	 * @see #TAM_DNI_PRESENTADOR
	 */
	public static final String ID_NOMBRE_PRESENTADOR = ID_PREFIX
			+ "NOMBRE_PRESENTADOR";

	/**
	 * DNI Arrendador
	 * 
	 * @see #TAM_DNI_PRESENTADOR
	 */
	public static final String ID_PRIMER_APELLIDO_PRESENTADOR = ID_PREFIX
			+ "PRIMER_APELLIDO_PRESENTADOR";

	/**
	 * DNI Arrendador
	 * 
	 * @see #TAM_DNI_PRESENTADOR
	 */
	public static final String ID_SEGUNDO_APELLIDO_PRESENTADOR = ID_PREFIX
			+ "SEGUNDO_APELLIDO_PRESENTADOR";

	/**
	 * DNI Arrendador
	 * 
	 * @see #TAM_DNI_PRESENTADOR
	 */
	public static final String ID_PROVINCIA_PRESENTADOR = ID_PREFIX
			+ "PROVINCIA_PRESENTADOR";

	// 620
	public static final String ID_PROVINCIA_PRESENTADOR_DESC = ID_PREFIX
			+ "PROVINCIA_PRESENTADOR_DESC";

	/**
	 * DNI Arrendador
	 * 
	 * @see #TAM_DNI_PRESENTADOR
	 */
	public static final String ID_MUNICIPIO_PRESENTADOR = ID_PREFIX
			+ "MUNICIPIO_PRESENTADOR";

	/**
	 * DNI Arrendador
	 * 
	 * @see #TAM_DNI_PRESENTADOR
	 */
	public static final String ID_CP_PRESENTADOR = ID_PREFIX + "CP_PRESENTADOR";

	/**
	 * DNI Arrendador
	 * 
	 * @see #TAM_DNI_PRESENTADOR
	 */
	public static final String ID_TIPO_VIA_PRESENTADOR = ID_PREFIX
			+ "TIPO_VIA_PRESENTADOR";

	/**
	 * DNI Arrendador
	 * 
	 * @see #TAM_DNI_PRESENTADOR
	 */
	public static final String ID_CARACTERISTICAS_VEHICULO = ID_PREFIX
			+ "CARACTERISTICAS_VEHICULO";

	/**
	 * DNI Arrendador
	 * 
	 * @see #TAM_DNI_PRESENTADOR
	 */
	public static final String ID_DNI_REPR_ADQUIRENTE = ID_PREFIX
			+ "DNI_REPR_ADQUIRENTE";

	/**
	 * DNI Arrendador
	 * 
	 * @see #TAM_DNI_PRESENTADOR
	 */
	public static final String ID_DNI_REPR_TRANSMITENTE = ID_PREFIX
			+ "DNI_REPR_TRANSMITENTE";

	/**
	 * DNI Arrendador
	 * 
	 * @see #TAM_DNI_PRESENTADOR
	 */
	public static final String ID_DNI_REPR_ARRENDADOR = ID_PREFIX
			+ "DNI_REPR_ARRENDADOR";

	public static final String ID_CCC_620 = ID_PREFIX + "CCC_620";
	/**
	 * DNI Arrendador
	 * 
	 * @see #TAM_DNI_PRESENTADOR
	 */
	public static final String ID_NIF_TITULAR_CCC = ID_PREFIX
			+ "NIF_TITULAR_CCC";

	public static final String ID_PORCENTAJE_REDUCCION_620 = ID_PREFIX
			+ "PORCENTAJE_REDUCCION_620";
	public static final String ID_VALOR_FISCAL_1ER_AÑO_620 = ID_PREFIX
			+ "VALOR_FISCAL_1ER_AÑO_620";
	public static final String ID_PORCENTAJE_REDUCCION_ANUAL_620 = ID_PREFIX
			+ "PORCENTAJE_REDUCCION_ANUAL_620";
	public static final String ID_VALOR_CONTRATO_620 = ID_PREFIX
			+ "VALOR_DECLARADO_620";
	public static final String ID_PORCENTAJE_ADQUISICION_620 = ID_PREFIX
			+ "PORCENTAJE_ADQUISICION_620";
	public static final String ID_BASE_IMPONIBLE_620 = ID_PREFIX
			+ "BASE_IMPONIBLE_620";
	public static final String ID_TIPO_GRAVAMEN_620 = ID_PREFIX
			+ "TIPO_GRAVAMEN_620";
	public static final String ID_CUOTA_TRIBUTARIA_620 = ID_PREFIX
			+ "CUOTA_TRIBUTARIA_620";
	public static final String ID_SANCION_620 = ID_PREFIX + "SANCION_620";
	public static final String ID_INTERES_DEMORA_620 = ID_PREFIX
			+ "INTERES_DEMORA_620";
	public static final String ID_TOTAL_INGRESAR_620 = ID_PREFIX
			+ "TOTAL_INGRESAR_620";
	public static final String ID_MARCA_620 = ID_PREFIX + "MARCA_620";
	public static final String ID_MODELO_620 = ID_PREFIX + "MODELO_620";
	public static final String ID_NUM_FACTURA = ID_PREFIX + "NUM_FACTURA";
	public static final String ID_RESULTADO_TRAMITABILIDAD = ID_PREFIX + "RESULTADO_TRAMITABILIDAD";
	public static final String ID_RESULT_BTV =  ID_PREFIX + "RESULT_BTV";
	public static final String ID_RESULTADO_CTIT = "ID_RESULTADO_CTIT";
	public static final String ID_COMPRAVENTA_ADQUIRIENTE = "ID_COMPRAVENTA_ADQUIRIENTE";
	public static final String ID_COMPRAVENTA_TRANSMITENTE = "ID_COMPRAVENTA_TRANSMITENTE";
	public static final String ID_TELEMATICA = "ID_TELEMATICA";
	public static final String ID_EXP_TELEMATICO = "EXPEDIENTE TRAMITABLE TELEMATICAMENTE";
	public static final String ID_EXP_NO_TELEMATICO = "EXPEDIENTE NO TRAMITABLE TELEMATICAMENTE";

	/*
	 * 
	 * 
	 * Agregados para BAJA
	 */

	/**
	 * Motivo de baja
	 * 
	 * @see #TAM_MOTIVO_BAJA
	 */
	public static final String ID_MOTIVO_BAJA = ID_PREFIX + "MOTIVO_BAJA";

	public static final String ID_TIPO_BAJA = ID_PREFIX + "TIPO_BAJA";
	/**
	 * Documentos que se acompañan
	 * 
	 * @see #TAM_DOCUMENTOS_ACOMPAÑANTES
	 */
	public static final String ID_DOCUMENTOS_ACOMPAÑANTES = ID_PREFIX
			+ "DOCUMENTOS_ACOMPAÑANTES";
	/**
	 * Concepto en que actúa el solicitante
	 * 
	 * @see #TAM_CONCEPTO_SOLICITANTE
	 */
	public static final String ID_CONCEPTO_SOLICITANTE = ID_PREFIX
			+ "CONCEPTO_SOLICITANTE";
	/**
	 * Reservado
	 * 
	 * @see #TAM_RESERVADO
	 */
	public static final String ID_RESERVADO = ID_PREFIX + "RESERVADO";

	/*
	 * 
	 * 
	 * Agregados para SOLICITUD DE DATOS
	 */

	/**
	 * Número de matrículas o DNI que se solicitan
	 * 
	 * @see #TAM_NUM_SOLICITUDES
	 */
	public static final String ID_NUM_SOLICITUDES = ID_PREFIX
			+ "NUM_SOLICITUDES";
	/**
	 * 1ª Matrícula o DNI
	 * 
	 * @see #TAM_MATRICULA_DNI
	 */
	public static final String ID_MATRICULA_DNI_1 = ID_PREFIX
			+ "MATRICULA_DNI_1";
	/**
	 * 2ª Matrícula o DNI
	 * 
	 * @see #TAM_MATRICULA_DNI
	 */
	public static final String ID_MATRICULA_DNI_2 = ID_PREFIX
			+ "MATRICULA_DNI_2";
	/**
	 * 3ª Matrícula o DNI
	 * 
	 * @see #TAM_MATRICULA_DNI
	 */
	public static final String ID_MATRICULA_DNI_3 = ID_PREFIX
			+ "MATRICULA_DNI_3";
	/**
	 * 4ª Matrícula o DNI
	 * 
	 * @see #TAM_MATRICULA_DNI
	 */
	public static final String ID_MATRICULA_DNI_4 = ID_PREFIX
			+ "MATRICULA_DNI_4";
	/**
	 * 5ª Matrícula o DNI
	 * 
	 * @see #TAM_MATRICULA_DNI
	 */
	public static final String ID_MATRICULA_DNI_5 = ID_PREFIX
			+ "MATRICULA_DNI_5";
	/**
	 * 6ª Matrícula o DNI
	 * 
	 * @see #TAM_MATRICULA_DNI
	 */
	public static final String ID_MATRICULA_DNI_6 = ID_PREFIX
			+ "MATRICULA_DNI_6";
	/**
	 * 7ª Matrícula o DNI
	 * 
	 * @see #TAM_MATRICULA_DNI
	 */
	public static final String ID_MATRICULA_DNI_7 = ID_PREFIX
			+ "MATRICULA_DNI_7";
	/**
	 * 8ª Matrícula o DNI
	 * 
	 * @see #TAM_MATRICULA_DNI
	 */
	public static final String ID_MATRICULA_DNI_8 = ID_PREFIX
			+ "MATRICULA_DNI_8";
	/**
	 * 9ª Matrícula o DNI
	 * 
	 * @see #TAM_MATRICULA_DNI
	 */
	public static final String ID_MATRICULA_DNI_9 = ID_PREFIX
			+ "MATRICULA_DNI_9";
	/**
	 * 10ª Matrícula o DNI
	 * 
	 * @see #TAM_MATRICULA_DNI
	 */
	public static final String ID_MATRICULA_DNI_10 = ID_PREFIX
			+ "MATRICULA_DNI_10";
	/**
	 * 11ª Matrícula o DNI
	 * 
	 * @see #TAM_MATRICULA_DNI
	 */
	public static final String ID_MATRICULA_DNI_11 = ID_PREFIX
			+ "MATRICULA_DNI_11";
	/**
	 * 12ª Matrícula o DNI
	 * 
	 * @see #TAM_MATRICULA_DNI
	 */
	public static final String ID_MATRICULA_DNI_12 = ID_PREFIX
			+ "MATRICULA_DNI_12";
	/**
	 * 13ª Matrícula o DNI
	 * 
	 * @see #TAM_MATRICULA_DNI
	 */
	public static final String ID_MATRICULA_DNI_13 = ID_PREFIX
			+ "MATRICULA_DNI_13";
	/**
	 * 14ª Matrícula o DNI
	 * 
	 * @see #TAM_MATRICULA_DNI
	 */
	public static final String ID_MATRICULA_DNI_14 = ID_PREFIX
			+ "MATRICULA_DNI_14";
	/**
	 * 15ª Matrícula o DNI
	 * 
	 * @see #TAM_MATRICULA_DNI
	 */
	public static final String ID_MATRICULA_DNI_15 = ID_PREFIX
			+ "MATRICULA_DNI_15";
	/**
	 * 16ª Matrícula o DNI
	 * 
	 * @see #TAM_MATRICULA_DNI
	 */
	public static final String ID_MATRICULA_DNI_16 = ID_PREFIX
			+ "MATRICULA_DNI_16";
	/**
	 * 17ª Matrícula o DNI
	 * 
	 * @see #TAM_MATRICULA_DNI
	 */
	public static final String ID_MATRICULA_DNI_17 = ID_PREFIX
			+ "MATRICULA_DNI_17";
	/**
	 * 18ª Matrícula o DNI
	 * 
	 * @see #TAM_MATRICULA_DNI
	 */
	public static final String ID_MATRICULA_DNI_18 = ID_PREFIX
			+ "MATRICULA_DNI_18";
	/**
	 * 19ª Matrícula o DNI
	 * 
	 * @see #TAM_MATRICULA_DNI
	 */
	public static final String ID_MATRICULA_DNI_19 = ID_PREFIX
			+ "MATRICULA_DNI_19";
	/**
	 * 20ª Matrícula o DNI
	 * 
	 * @see #TAM_MATRICULA_DNI
	 */
	public static final String ID_MATRICULA_DNI_20 = ID_PREFIX
			+ "MATRICULA_DNI_20";
	/**
	 * 21ª Matrícula o DNI
	 * 
	 * @see #TAM_MATRICULA_DNI
	 */
	public static final String ID_MATRICULA_DNI_21 = ID_PREFIX
			+ "MATRICULA_DNI_21";
	/**
	 * 22ª Matrícula o DNI
	 * 
	 * @see #TAM_MATRICULA_DNI
	 */
	public static final String ID_MATRICULA_DNI_22 = ID_PREFIX
			+ "MATRICULA_DNI_22";
	/**
	 * 23ª Matrícula o DNI
	 * 
	 * @see #TAM_MATRICULA_DNI
	 */
	public static final String ID_MATRICULA_DNI_23 = ID_PREFIX
			+ "MATRICULA_DNI_23";
	/**
	 * 24ª Matrícula o DNI
	 * 
	 * @see #TAM_MATRICULA_DNI
	 */
	public static final String ID_MATRICULA_DNI_24 = ID_PREFIX
			+ "MATRICULA_DNI_24";
	/**
	 * 25ª Matrícula o DNI
	 * 
	 * @see #TAM_MATRICULA_DNI
	 */
	public static final String ID_MATRICULA_DNI_25 = ID_PREFIX
			+ "MATRICULA_DNI_25";
	/**
	 * 26ª Matrícula o DNI
	 * 
	 * @see #TAM_MATRICULA_DNI
	 */
	public static final String ID_MATRICULA_DNI_26 = ID_PREFIX
			+ "MATRICULA_DNI_26";
	/**
	 * 27ª Matrícula o DNI
	 * 
	 * @see #TAM_MATRICULA_DNI
	 */
	public static final String ID_MATRICULA_DNI_27 = ID_PREFIX
			+ "MATRICULA_DNI_27";
	/**
	 * 28ª Matrícula o DNI
	 * 
	 * @see #TAM_MATRICULA_DNI
	 */
	public static final String ID_MATRICULA_DNI_28 = ID_PREFIX
			+ "MATRICULA_DNI_28";
	/**
	 * 29ª Matrícula o DNI
	 * 
	 * @see #TAM_MATRICULA_DNI
	 */
	public static final String ID_MATRICULA_DNI_29 = ID_PREFIX
			+ "MATRICULA_DNI_29";
	/**
	 * 30ª Matrícula o DNI
	 * 
	 * @see #TAM_MATRICULA_DNI
	 */
	public static final String ID_MATRICULA_DNI_30 = ID_PREFIX
			+ "MATRICULA_DNI_30";
	/**
	 * Primer dato solicitado
	 * 
	 * @see #TAM_PRIMER_DATO_SOLICITADO
	 */
	public static final String ID_PRIMER_DATO_SOLICITADO = ID_PREFIX
			+ "PRIMER_DATO_SOLICITADO";
	/**
	 * Segundo dato solicitado
	 * 
	 * @see #TAM_SEGUNDO_DATO_SOLICITADO
	 */
	public static final String ID_SEGUNDO_DATO_SOLICITADO = ID_PREFIX
			+ "SEGUNDO_DATO_SOLICITADO";
	/**
	 * Tercer dato solicitado
	 * 
	 * @see #TAM_TERCER_DATO_SOLICITADO
	 */
	public static final String ID_TERCER_DATO_SOLICITADO = ID_PREFIX
			+ "TERCER_DATO_SOLICITADO";
	/**
	 * DNI Solicitante
	 * 
	 * @see #TAM_DNI_SOLICITANTE
	 */
	public static final String ID_DNI_SOLICITANTE = ID_PREFIX
			+ "DNI_SOLICITANTE";
	/**
	 * Primer Apellido Solicitante
	 * 
	 * @see #TAM_PRIMER_APELLIDO_SOLICITANTE
	 */
	public static final String ID_PRIMER_APELLIDO_SOLICITANTE = ID_PREFIX
			+ "PRIMER_APELLIDO_SOLICITANTE";
	/**
	 * Segundo Apellido Solicitante
	 * 
	 * @see #TAM_SEGUNDO_APELLIDO_SOLICITANTE
	 */
	public static final String ID_SEGUNDO_APELLIDO_SOLICITANTE = ID_PREFIX
			+ "SEGUNDO_APELLIDO_SOLICITANTE";
	/**
	 * Nombre Solicitante
	 * 
	 * @see #TAM_NOMBRE_SOLICITANTE
	 */
	public static final String ID_NOMBRE_SOLICITANTE = ID_PREFIX
			+ "NOMBRE_SOLICITANTE";

	// ***********************************
	// * IDENTIFICADORES DE CAMPOS AEAT *
	// ***********************************
	/**
	 * Tipo declaracion
	 * 
	 * @see #TAM_TIPO_DECLARACION
	 */
	public static final String ID_TIPO_DECLARACION = ID_PREFIX
			+ "TIPO_DECLARACION";
	/**
	 * NIF (Reservado para AEAT)
	 * 
	 * @see #TAM_NIF
	 */
	public static final String ID_NIF = ID_PREFIX + "NIF";
	/**
	 * Ejercicio (Reservado para AEAT)
	 * 
	 * @see #TAM_EJERCICIO
	 */
	public static final String ID_EJERCICIO = ID_PREFIX + "EJERCICIO";
	/**
	 * Tipo de transporte
	 * 
	 * @see #TAM_TIPO_TRANSPORTE
	 */
	public static final String ID_TIPO_TRANSPORTE = ID_PREFIX
			+ "TIPO_TRANSPORTE";
	/**
	 * C.M.T. Lugar de adquisicion
	 * 
	 * @see #TAM_CMT_ADQUISICION
	 */
	public static final String ID_LUGAR_DE_ADQUISICION = ID_PREFIX
			+ "LUGAR_ADQUISICION";
	/**
	 * C.M.T. M. Transp. Us.
	 * 
	 * @see #TAM_CMT_M_TRANSP_US
	 */
	public static final String ID_M_TRANSP_US = ID_PREFIX + "M_TRANSP_US";
	/**
	 * C.M.T. Fabricante o importador Embarcacion
	 * 
	 * @see #TAM_CMT_FABRICANTE_EMBARCACION
	 */
	public static final String ID_CMT_FABRICANTE_EMBARCACION = ID_PREFIX
			+ "CMT_FABRICANTE_EMBARCACION";
	/**
	 * C.M.T. Modelo Embarcacion
	 * 
	 * @see #TAM_CMT_MODELO_EMBARCACION
	 */
	public static final String ID_CMT_MODELO_EMBARCACION = ID_PREFIX
			+ "CMT_MODELO_EMBARCACION";
	/**
	 * C.M.T. Identificacion (Nº construccion) Embarcacion
	 * 
	 * @see #TAM_CMT_IDENTIFICACION_EMBARCACION
	 */
	public static final String ID_CMT_IDENTIFICACION_EMBARCACION = ID_PREFIX
			+ "CMT_IDENTIFICACION_EMBARCACION";
	/**
	 * C.M.T. Eslora maxima (en metros) Embarcacion
	 * 
	 * @see #TAM_CMT_ESLORA_MAX_EMBARCACION
	 */
	public static final String ID_CMT_ESLORA_MAX_EMBARCACION = ID_PREFIX
			+ "CMT_ESLORA_MAX_EMBARCACION";
	/**
	 * C.M.T. Fabricante Aeronave
	 * 
	 * @see #TAM_CMT_FABRICANTE_AERONAVE
	 */
	public static final String ID_CMT_FABRICANTE_AERONAVE = ID_PREFIX
			+ "CMT_FABRICANTE_AERONAVE";
	/**
	 * C.M.T. Modelo Aeronave
	 * 
	 * @see #TAM_CMT_MODELO_AERONAVE
	 */
	public static final String ID_CMT_MODELO_AERONAVE = ID_PREFIX
			+ "CMT_MODELO_AERONAVE";
	/**
	 * C.M.T. Nº Serie Aeronave
	 * 
	 * @see #TAM_CMT_NUM_SERIE_AERONAVE
	 */
	public static final String ID_CMT_NUM_SERIE_AERONAVE = ID_PREFIX
			+ "CMT_NUM_SERIE_AERONAVE";
	/**
	 * C.M.T. Año de Fabricacion Aeronave
	 * 
	 * @see #TAM_CMT_ANIO_FABRICACION_AERONAVE
	 */
	public static final String ID_CMT_ANIO_FABRICACION_AERONAVE = ID_PREFIX
			+ "CMT_ANIO_FABRICACION_AERONAVE";
	/**
	 * C.M.T. Peso maximo despegue (en Kg) Aeronave
	 * 
	 * @see #TAM_CMT_PESO_MAX_AERONAVE
	 */
	public static final String ID_CMT_PESO_MAX_AERONAVE = ID_PREFIX
			+ "CMT_PESO_MAX_AERONAVE";
	/**
	 * Declaracion complementaria. Nº de justificante de la declaracion anterior
	 * 
	 * @see #TAM_NUM_DECLARACION_ANTERIOR
	 */
	public static final String ID_NUM_DECLARACION_ANTERIOR = ID_PREFIX
			+ "NUM_DECLARACION_ANTERIOR";
	/**
	 * 006 (Reservado para la AEAT)
	 * 
	 * @see #TAM_006
	 */
	public static final String ID_006 = ID_PREFIX + "006";
	/**
	 * Expediente AEAT (Reservado para AEAT)
	 * 
	 * @see #TAM_EXPEDIENTE_AEAT
	 */
	public static final String ID_EXPEDIENTE_AEAT = ID_PREFIX
			+ "EXPEDIENTE_AEAT";
	/**
	 * Reservado para AEAT
	 * 
	 * @see #TAM_RESERVADO_AEAT
	 */
	public static final String ID_RESERVADO_AEAT = ID_PREFIX + "RESERVADO_AEAT";
	/**
	 * Ejercicio fiscal
	 * 
	 * @see #TAM_EJERCICIO_FISCAL
	 */
	public static final String ID_EJERCICIO_FISCAL = ID_PREFIX
			+ "EJERCICIO_FISCAL";
	/**
	 * Periodo
	 * 
	 * @see #TAM_PERIODO
	 */
	public static final String ID_PERIODO = ID_PREFIX + "PERIODO";
	/**
	 * Tipo de moneda de la declaracion
	 * 
	 * @see #TAM_TIPO_MONEDA
	 */
	public static final String ID_TIPO_MONEDA = ID_PREFIX + "TIPO_MONEDA";
	/**
	 * Tipo de autoliquidacion
	 * 
	 * @see #TAM_TIPO_AUTOLIQUIDACION
	 */
	public static final String ID_TIPO_AUTOLIQUIDACION = ID_PREFIX
			+ "TIPO_AUTOLIQUIDACION";
	/**
	 * Letras de etiquetas en personas fisicas
	 * 
	 * @see #TAM_LETRAS_ETIQUETA
	 */
	public static final String ID_LETRAS_ETIQUETA = ID_PREFIX
			+ "LETRAS_ETIQUETA";
	/**
	 * Codigo de administracion
	 * 
	 * @see #TAM_CODIGO_ADMON
	 */
	public static final String ID_CODIGO_ADMON = ID_PREFIX + "CODIGO_ADMON";
	/**
	 * Opcion de fraccionamiento segunnormativa
	 * 
	 * @see #TAM_FRACCIONAMIENTO
	 */
	public static final String ID_FRACCIONAMIENTO = ID_PREFIX
			+ "FRACCIONAMIENTO";
	/**
	 * Fecha de la operacion
	 * 
	 * @see #TAM_FECHA_OPERACION
	 */
	public static final String ID_FECHA_OPERACION = ID_PREFIX
			+ "FECHA_OPERACION";
	/**
	 * Fecha de caducidad de la tarjeta
	 * 
	 * @see #TAM_FECHA_CADUCIDAD
	 */
	public static final String ID_FECHA_CADUCIDAD = ID_PREFIX
			+ "FECHA_CADUCIDAD";
	/**
	 * Libre
	 * 
	 * @see #TAM_CAMPO_LIBRE
	 */
	public static final String ID_CAMPO_LIBRE = ID_PREFIX + "CAMPO_LIBRE";
	/**
	 * Vehículo usado
	 * 
	 * @see #TAM_VEHICULO_USADO
	 */
	public static final String ID_VEHICULO_USADO = ID_PREFIX + "VEHICULO_USADO";

	// PDF_TRANSMISION

	public static final String ID_CONCEPTO_ITV = ID_PREFIX + "CONCEPTO_ITV";
	public static final String ID_FECHA_INSPECCION_ITV = ID_PREFIX
			+ "FECHA_INSPECCION_ITV";
	public static final String ID_ESTACION_ITV = ID_PREFIX + "ESTACION_ITV";

	/**
	 * Nombre completo del adquiriente
	 */
	public static final String ID_NOMBRE_APELLIDOS_ADQUIRENTE = ID_PREFIX
			+ "NOMBRE_APELLIDOS_ADQUIRENTE";

	/**
	 * Nombre completo del transmitente
	 */
	public static final String ID_NOMBRE_APELLIDOS_TRANSMITENTE = ID_PREFIX
			+ "NOMBRE_APELLIDOS_TRANSMITENTE";

	/**
	 * Domicilio completo del adquiriente
	 */
	public static final String ID_DOMICILIO_COMPLETO_ADQUIRENTE = ID_PREFIX
			+ "DOMICILIO_COMPLETO_ADQUIRENTE";

	/**
	 * Domicilio completo del transmitente
	 */
	public static final String ID_DOMICILIO_COMPLETO_TRANSMITENTE = ID_PREFIX
			+ "DOMICILIO_COMPLETO_TRANSMITENTE";

	/**
	 * Domicilio completo del arrendador
	 */
	public static final String ID_DOMICILIO_COMPLETO_ARRENDADOR = ID_PREFIX
			+ "DOMICILIO_COMPLETO_ARRENDADOR";

	/**
	 * Codigo tipo via domicilio Arrendador
	 */
	public static final String ID_TIPO_VIA_ARRENDADOR = ID_PREFIX
			+ "TIPO_VIA_ARRENDADOR";
	/**
	 * Numero domicilio Arrendador
	 */
	public static final String ID_NUM_DOMICILIO_ARRENDADOR = ID_PREFIX
			+ "NUM_DOMICILIO_ARRENDADOR";
	/**
	 * Letra domicilio Arrendador
	 */
	public static final String ID_LETRA_DOMICILIO_ARRENDADOR = ID_PREFIX
			+ "LETRA_DOMICILIO_ARRENDADOR";
	/**
	 * Escalera domicilio Arrendador
	 */
	public static final String ID_ESCALERA_DOMICILIO_ARRENDADOR = ID_PREFIX
			+ "ESCALERA_DOMICILIO_ARRENDADOR";
	/**
	 * Piso domicilio Arrendador
	 */
	public static final String ID_PISO_DOMICILIO_ARRENDADOR = ID_PREFIX
			+ "PISO_DOMICILIO_ARRENDADOR";
	/**
	 * Puerta domicilio Arrendador
	 */
	public static final String ID_PUERTA_DOMICILIO_ARRENDADOR = ID_PREFIX
			+ "PUERTA_DOMICILIO_ARRENDADOR";
	/**
	 * Domicilio Arrendador
	 */
	public static final String ID_DOMICILIO_ARRENDADOR = ID_PREFIX
			+ "DOMICILIO_ARRENDADOR";
	/**
	 * Municipio Arrendador
	 */
	public static final String ID_MUNICIPIO_ARRENDADOR = ID_PREFIX
			+ "MUNICIPIO_ARRENDADOR";
	/**
	 * Pueblo Arrendador
	 */
	public static final String ID_PUEBLO_ARRENDADOR = ID_PREFIX
			+ "PUEBLO_ARRENDADOR";
	/**
	 * Siglas de la Provincia Arrendador
	 */
	public static final String ID_PROVINCIA_ARRENDADOR = ID_PREFIX
			+ "PROVINCIA_ARRENDADOR";
	/**
	 * Codigo Postal Arrendador
	 */
	public static final String ID_CP_ARRENDADOR = ID_PREFIX + "CP_ARRENDADOR";

	/*
	 * 
	 * 
	 * Agregados para MATE
	 */
	/**
	 * Numero domicilio completio del vehiculo
	 */
	public static final String ID_DOMICILIO_COMPLETO_VEHICULO = ID_PREFIX
			+ "DOMICILIO_COMPLETO_VEHICULO";
	/**
	 * Numero domicilio vehiculo
	 */
	public static final String ID_NUM_DOMICILIO_VEHICULO = ID_PREFIX
			+ "NUM_DOMICILIO_VEHICULO";
	/**
	 * Letra domicilio vehiculo
	 */
	public static final String ID_LETRA_DOMICILIO_VEHICULO = ID_PREFIX
			+ "LETRA_DOMICILIO_VEHICULO";
	/**
	 * Escalera domicilio vehiculo
	 */
	public static final String ID_ESCALERA_DOMICILIO_VEHICULO = ID_PREFIX
			+ "ESCALERA_DOMICILIO_VEHICULO";
	/**
	 * Piso domicilio vehiculo
	 */
	public static final String ID_PISO_DOMICILIO_VEHICULO = ID_PREFIX
			+ "PISO_DOMICILIO_VEHICULO";
	/**
	 * Puerta domicilio vehiculo
	 */
	public static final String ID_PUERTA_DOMICILIO_VEHICULO = ID_PREFIX
			+ "PUERTA_DOMICILIO_VEHICULO";
	/**
	 * Km domicilio vehiculo
	 */
	public static final String ID_KM_DOMICILIO_VEHICULO = ID_PREFIX
			+ "KM_DOMICILIO_VEHICULO";
	/**
	 * Hm domicilio vehiculo
	 */
	public static final String ID_HM_DOMICILIO_VEHICULO = ID_PREFIX
			+ "HM_DOMICILIO_VEHICULO";
	/**
	 * Bloque domicilio vehiculo
	 */
	public static final String ID_BLOQUE_DOMICILIO_VEHICULO = ID_PREFIX
			+ "BLOQUE_DOMICILIO_VEHICULO";
	/**
	 * Fecha de Inicio de Tutela
	 */
	public static final String ID_FECHA_INICIO_TUTELA = ID_PREFIX
			+ "FECHA_INICIO_TUTELA";
	/**
	 * Código de Tramite de Tutela
	 */
	public static final String ID_TRAMITE_TUTELA = ID_PREFIX + "TRAMITE_TUTELA";
	/**
	 * Tipo de Tutela
	 */
	public static final String ID_TIPO_TUTELA = ID_PREFIX + "TIPO_TUTELA";
	/**
	 * Tipo de Matrícula de Tutela
	 */
	public static final String ID_TIPO_MATRICULA = ID_PREFIX + "TIPO_MATRICULA";
	/**
	 * DNI de conductor habitual
	 */
	public static final String ID_DNI_CONDUCTOR_HABITUAL = ID_PREFIX
			+ "DNI_CONDUCTOR_HABITUAL";
	/**
	 * Fecha de Inicio de Conductor Habitual
	 */
	public static final String ID_FECHA_INICIO_CONDUCTOR_HABITUAL = ID_PREFIX
			+ "FECHA_INICIO_CONDUCTOR_HABITUAL";
	/**
	 * Fecha de Fin de Conductor Habitual
	 */
	public static final String ID_FECHA_FIN_CONDUCTOR_HABITUAL = ID_PREFIX
			+ "FECHA_FIN_CONDUCTOR_HABITUAL";
	/**
	 * Concepto de tutela
	 */
	public static final String ID_CONCEPTO_TUTELA = ID_PREFIX
			+ "CONCEPTO_TUTELA";
	/**
	 * DNI de tutor
	 */
	public static final String ID_DNI_TUTOR = ID_PREFIX + "DNI_TUTOR";
	/**
	 * Flag autónomo
	 */
	public static final String ID_FLAG_AUTONOMO = ID_PREFIX + "FLAG_AUTONOMO";
	/**
	 * Código IAE
	 * 
	 * @see #TAM_AUTONOMOS
	 */
	public static final String ID_CODIGO_IAE = ID_PREFIX + "CODIGO_IAE";
	/**
	 * Código CEMU
	 * 
	 * @see #TAM_CEMU
	 */
	public static final String ID_CODIGO_CEMU = ID_PREFIX + "CODIGO_CEMU";
	/**
	 * Horas de Uso de un vehículo usadi
	 * 
	 * @see #TAM_CEMU
	 */
	public static final String ID_HORAS_USO = ID_PREFIX + "HORAS_USO";
	/**
	 * DNI Arrendatario
	 * 
	 * @see #TAM_DNI_ARRENDATARIO
	 */
	public static final String ID_DNI_ARRENDATARIO = ID_PREFIX
			+ "DNI_ARRENDATARIO";
	/**
	 * Primer Apellido Arrendatario
	 * 
	 * @see #TAM_PRIMER_APELLIDO_ARRENDATARIO
	 */
	public static final String ID_PRIMER_APELLIDO_ARRENDATARIO = ID_PREFIX
			+ "PRIMER_APELLIDO_ARRENDATARIO";
	/**
	 * Segundo Apellido Arrendatario
	 * 
	 * @see #TAM_SEGUNDO_APELLIDO_ARRENDATARIO
	 */
	public static final String ID_SEGUNDO_APELLIDO_ARRENDATARIO = ID_PREFIX
			+ "SEGUNDO_APELLIDO_ARRENDATARIO";
	/**
	 * Nombre Arrendatario
	 * 
	 * @see #TAM_NOMBRE_ARRENDATARIO
	 */
	public static final String ID_NOMBRE_ARRENDATARIO = ID_PREFIX
			+ "NOMBRE_ARRENDATARIO";
	/**
	 * Domicilio Arrendatario
	 * 
	 * @see #TAM_DOMICILIO_ARRENDATARIO
	 */
	public static final String ID_TIPO_VIA_ARRENDATARIO = ID_PREFIX
			+ "TIPO_VIA_ARRENDATARIO";
	/**
	 * Domicilio Arrendatario
	 * 
	 * @see #TAM_DOMICILIO_ARRENDATARIO
	 */
	public static final String ID_DOMICILIO_ARRENDATARIO = ID_PREFIX
			+ "DOMICILIO_ARRENDATARIO";
	/**
	 * Municipio Arrendatario
	 * 
	 * @see #TAM_MUNICIPIO_ARRENDATARIO
	 */
	public static final String ID_MUNICIPIO_ARRENDATARIO = ID_PREFIX
			+ "MUNICIPIO_ARRENDATARIO";
	/**
	 * Pueblo Arrendatario
	 * 
	 * @see #TAM_PUEBLO_ARRENDATARIO
	 */
	public static final String ID_PUEBLO_ARRENDATARIO = ID_PREFIX
			+ "PUEBLO_ARRENDATARIO";
	/**
	 * Siglas de la Provincia Arrendatario
	 * 
	 * @see #TAM_PROVINCIA_ARRENDATARIO
	 */
	public static final String ID_PROVINCIA_ARRENDATARIO = ID_PREFIX
			+ "PROVINCIA_ARRENDATARIO";
	/**
	 * Codigo Postal Arrendatario
	 * 
	 * @see #TAM_CP_ARRENDATARIO
	 */
	public static final String ID_CP_ARRENDATARIO = ID_PREFIX
			+ "CP_ARRENDATARIO";
	/**
	 * Numero domicilio arrendatario
	 */
	public static final String ID_NUM_DOMICILIO_ARRENDATARIO = ID_PREFIX
			+ "NUM_DOMICILIO_ARRENDATARIO";
	/**
	 * Letra domicilio arrendatario
	 */
	public static final String ID_LETRA_DOMICILIO_ARRENDATARIO = ID_PREFIX
			+ "LETRA_DOMICILIO_ARRENDATARIO";
	/**
	 * Escalera domicilio arrendatario
	 */
	public static final String ID_ESCALERA_DOMICILIO_ARRENDATARIO = ID_PREFIX
			+ "ESCALERA_DOMICILIO_ARRENDATARIO";
	/**
	 * Piso domicilio arrendatario
	 */
	public static final String ID_PISO_DOMICILIO_ARRENDATARIO = ID_PREFIX
			+ "PISO_DOMICILIO_ARRENDATARIO";
	/**
	 * Puerta domicilio arrendatario
	 */
	public static final String ID_PUERTA_DOMICILIO_ARRENDATARIO = ID_PREFIX
			+ "PUERTA_DOMICILIO_ARRENDATARIO";
	/**
	 * Hm domicilio arrendatario
	 */
	public static final String ID_HM_DOMICILIO_ARRENDATARIO = ID_PREFIX
			+ "HM_DOMICILIO_ARRENDATARIO";
	/**
	 * Km domicilio arrendatario
	 */
	public static final String ID_KM_DOMICILIO_ARRENDATARIO = ID_PREFIX
			+ "KM_DOMICILIO_ARRENDATARIO";
	/**
	 * Bloque domicilio arrendatario
	 */
	public static final String ID_BLOQUE_DOMICILIO_ARRENDATARIO = ID_PREFIX
			+ "BLOQUE_DOMICILIO_ARRENDATARIO";
	/**
	 * Sexo Arrendatario
	 * 
	 * @see #TAM_SEXO_ARRENDATARIO
	 */
	public static final String ID_SEXO_ARRENDATARIO = ID_PREFIX
			+ "SEXO_ARRENDATARIO";
	/**
	 * Fecha Nacimiento Arrendatario
	 * 
	 * @see #TAM_FECHA_NACIMIENTO_ARRENDATARIO
	 */
	public static final String ID_FECHA_NACIMIENTO_ARRENDATARIO = ID_PREFIX
			+ "FECHA_NACIMIENTO_ARRENDATARIO";
	/**
	 * Fecha de Inicio de Renting
	 */
	public static final String ID_FECHA_INICIO = ID_PREFIX + "FECHA_INICIO";
	/**
	 * Hora de Inicio de Renting
	 */
	public static final String ID_HORA_INICIO = ID_PREFIX + "HORA_INICIO";
	/**
	 * Fecha de Fin de Renting
	 */
	public static final String ID_FECHA_FIN = ID_PREFIX + "FECHA_FIN";
	/**
	 * Hora de Fin de Renting
	 */
	public static final String ID_HORA_FIN = ID_PREFIX + "HORA_FIN";
	/**
	 * Tipo de Carga
	 */
	public static final String ID_TIPO_CARGA = ID_PREFIX + "TIPO_CARGA";
	/**
	 * Fecha de Carga financiera
	 */
	public static final String ID_FECHA_CARGA = ID_PREFIX + "FECHA_CARGA";
	/**
	 * Número de Registro de Carga
	 */
	public static final String ID_NUMERO_REGISTRO_CARGA = ID_PREFIX
			+ "NUMERO_REGISTRO_CARGA";
	/**
	 * Hora de Fin de Renting
	 */
	public static final String ID_FINANCIERA_DE_CARGA = ID_PREFIX
			+ "FINANCIERA_DE_CARGA";
	/**
	 * Masa del vehículo en servicio
	 * 
	 * @see #TAM_MASA_EN_SERVICIO
	 */
	public static final String ID_MASA_EN_SERVICIO = ID_PREFIX
			+ "MASA_EN_SERVICIO";
	/**
	 * Número de ruedas del vehículo
	 * 
	 * @see #TAM_NUMERO_RUEDAS
	 */
	public static final String ID_NUMERO_RUEDAS = ID_PREFIX + "NUMERO_RUEDAS";
	/**
	 * Campo para insertar las siglas del tipo de via ine del titular
	 */
	public static final String ID_TIPO_VIA_TITULAR_INE = ID_PREFIX
			+ "TIPO_VIA_TITULAR_INE";
	/**
	 * Campo para insertar las siglas del tipo de via ine del vehiculo
	 */
	public static final String ID_TIPO_VIA_VEHICULO_INE = ID_PREFIX
			+ "TIPO_VIA_VEHICULO_INE";
	/**
	 * Campo para insertar las siglas del tipo de via ine del arrendatario
	 */
	public static final String ID_TIPO_VIA_ARRENDATARIO_INE = ID_PREFIX
			+ "TIPO_VIA_ARRENDATARIO_INE";
	/**
	 * Campo para insertar El resultado de la matriculación telématica
	 */
	public static final String ID_RESULTADO_TELEMATICO = ID_PREFIX
			+ "RESULTADO_TELEMATICO";
	/**
	 * Campo para insertar información adicional
	 */
	public static final String ID_INFORMACION = ID_PREFIX + "INFORMACION";
	/**
	 * Campo para insertar el flag de cambio de domicilio
	 */
	public static final String ID_CAMBIO_DOMICILIO = ID_PREFIX
			+ "CAMBIO_DOMICILIO";
	/**
	 * Campo para la Jefatura Local (Sucursal)
	 */
	public static final String ID_SUCURSAL_JEFATURA = ID_PREFIX
			+ "SUCURSAL_JEFATURA";
	/**
	 * Campo para insertar el flag de renting
	 */
	public static final String ID_RENTING = ID_PREFIX + "RENTING";
	/**
	 * Campo para insertar el flag de cargas
	 */
	public static final String ID_CARGA_EEFF = ID_PREFIX + "CARGA_EEFF";
	/**
	 * Campo para insertar los Errores de Matege
	 */
	public static final String ID_ERROR_MATEGE = ID_PREFIX
			+ "ERROR_MATEGE_DESCRIPCION";
	/**
	 * Campo para insertar las siglas de la provincia ine del titular
	 */
	public static final String ID_PROVINCIA_TITULAR_INE = ID_PREFIX
			+ "PROVINCIA_TITULAR_INE";
	/**
	 * Campo para insertar las siglas de la municipio ine del titular
	 */
	public static final String ID_MUNICIPIO_TITULAR_INE = ID_PREFIX
			+ "MUNICIPIO_TITULAR_INE";
	/*
	 * Campo para insertar las siglas de la provincia ine del arrendatario
	 */
	public static final String ID_PROVINCIA_ARRENDATARIO_INE = ID_PREFIX
			+ "PROVINCIA_ARRENDATARIO_INE";
	/**
	 * Campo para insertar las siglas del municipio ine del arrendatario
	 */
	public static final String ID_MUNICIPIO_ARRENDATARIO_INE = ID_PREFIX
			+ "MUNICIPIO_ARRENDATARIO_INE";
	/*
	 * Campo para insertar las siglas de la provincia ine del arrendatario
	 */
	public static final String ID_PROVINCIA_VEHICULO_INE = ID_PREFIX
			+ "PROVINCIA_VEHICULO_INE";
	/**
	 * Campo para insertar las siglas del municipio ine del arrendatario
	 */
	public static final String ID_MUNICIPIO_VEHICULO_INE = ID_PREFIX
			+ "MUNICIPIO_VEHICULO_INE";

	// MODELO430

	/**
	 * Fecha de Devengo-Presentador
	 */
	public static final String ID_DIA_PRESENTACION = ID_PREFIX
			+ "DIA_PRESENTACION";
	public static final String ID_MES_PRESENTACION = ID_PREFIX
			+ "MES_PRESENTACION";
	public static final String ID_ANIO_PRESENTACION = ID_PREFIX
			+ "ANIO_PRESENTACION";

	public static final String ID_DIA_CONTRATO430 = ID_PREFIX
			+ "DIA_CONTRATO430";
	public static final String ID_MES_CONTRATO430 = ID_PREFIX
			+ "MES_CONTRATO430";
	public static final String ID_ANIO_CONTRATO430 = ID_PREFIX
			+ "ANIO_CONTRATO430";
	
	/**
	 * Nuevos campos MATE 2.5
	 * 
	 * @incidencias 0001635
	 * */
	public static final String ID_CATEGORIA_EU = ID_PREFIX + "CATEGORIA_EU";
	public static final String ID_CARROCERIA = ID_PREFIX + "CARROCERIA";
	public static final String ID_DIST_EJES = ID_PREFIX + "DIST_EJES";
	public static final String ID_VIA_ANT = ID_PREFIX + "VIA_ANT";
	public static final String ID_VIA_POST = ID_PREFIX + "VIA_POST";
	public static final String ID_CONSUMO = ID_PREFIX + "CONSUMO";
	public static final String ID_FABRICACION = ID_PREFIX + "FABRICACION";
	public static final String ID_TIPO_ALIMENTACION = ID_PREFIX
			+ "TIPO_ALIMENTACION";
	public static final String ID_NIVEL_EMISIONES = ID_PREFIX
			+ "NIVEL_EMISIONES";
	public static final String ID_FABRICANTE = ID_PREFIX + "FABRICANTE";
	public static final String ID_IMPORTADO = ID_PREFIX + "IMPORTADO";
	public static final String ID_PAIS_IMPORT = ID_PREFIX + "PAIS_IMPORT";
	public static final String ID_ECO_INNOVA = ID_PREFIX + "ECO_INNOVA";
	public static final String ID_REDUCCION_EC = ID_PREFIX + "REDUCCION_EC";
	public static final String ID_CODIGO_ECO = ID_PREFIX + "CODIGO_ECO";
	public static final String ID_SUBASTA = ID_PREFIX + "SUBASTA";

	// IMPRESION PDF PLANTILLA MATEW
	public static final String ID_TIPO_TRAMITE = "Tipo Trámite";
	public static final String ID_BASTIDOR_VIN = "Bastidor  VIN";
	public static final String ID_NUME_EXPEDIENTE = "Nº Expediente";
	public static final String ID_TASA_MATW = "Tasa";
	public static final String ID_NIVE = "NIVE";
	public static final String ID_CEM_MATEW = "CEM";
	public static final String ID_IVTM_CEMU_MTW = "IVTMCEMU";
	public static final String ID_CEMA_MTW = "CEMA";
	public static final String ID_MOTIVO_EXENCION = "Motivo Exención";
	public static final String ID_INFORMACION_MTW = "INFORMACIÓN ADICIONAL";
	//public static final String ID_SATISFECHO_CET = "Satisfecho CET";
	public static final String ID_FECHA_PRESENTACION_MTW = "Fecha Presentación";
	public static final String ID_EXENCION_IVTM_MTW = "Exento IVTM";
	public static final String ID_JEFATURA_TRAFICO_MTW = "Jefatura Provincial";
	public static final String ID_SUCURSAL_JEFATURA_MTW = "Jefatura Local";
	public static final String ID_RESULTADO_MATW = "RESULTADO MATRÍCULA TELEMÁTICA";
	// DATOS REPRESENTANTE PROFESIONAL
	public static final String ID_GESTOR_MTW = "Gestor";

	public static final String ID_NOMBRE_PROFESIONAL_MTW = "Nombre Profesional";
	public static final String ID_GESTORIA_MTW = "Gestoría";

	// DATOS TITULAR;
	public static final String ID_CAMBIO_DOMICILIO_MTW = "Cambio Domicilio";
	public static final String ID_FLAG_AUTONOMO_TITULAR_MTW = "Autónomo";
	public static final String ID_NOMBRE_APELLIDOS_TITULAR_MTW = "Nombre y Apellidos Razón Social";
	public static final String ID_FECHA_NACIMIENTO_TITULAR_MTW = "Fecha Nacimiento";
	public static final String ID_SEXO_TITULAR_MTW = "Sexo";
	public static final String ID_DNI_TITULAR_MTW = "DOI";
	public static final String ID_DNI_REPRESENTANTE_TITULAR_MTW = "DOI Representante";
	public static final String DENOMINACION_MTW = "Denominación:";
	public static final String ID_CODIGO_IAE_TITULAR_MTW = "IAE";
	public static final String ID_TUTELA_TITULAR_MTW = "Tutela";
	public static final String ID_TIPO_TUTELA_TITULAR_MTW = "Tipo Tutela";
	public static final String ID_FECHA_INICIO_TUTELA_TITULAR_MTW = "Fecha Inicio Tutela";
	public static final String ID_NACIONALIDAD_TITULAR_MTW = "Nacionalidad";
	public static final String ID_DOMICILIO_COMPLETO_TITULAR_MTW = "Domicilio INE Titular";

	// DATOS VEHÍCULO;
	public static final String ID_SUBASTA_MTW = "Subasta";
	public static final String ID_USADO_MTW = "Usado";
	public static final String ID_IMPORTADO_MTW = "Importado";
	public static final String ID_PAIS_IMPORT_MTW = "País Importación";
	public static final String ID_CODIGO_ITV_MTW = "Código ITV";
	public static final String ID_TIPO_TARJETA_ITV_MTW = "Tipo Tarjeta ITV";
	public static final String ID_TIPO_VEHICULO_MTW = "Tipo Vehículo DGT";
	public static final String ID_TIPO_VEHICULO_IND_MTW = "Tipo Vehículo Industria";
	public static final String ID_CATEGORIA_EU_MTW = "Categoría EU";
	public static final String ID_CARROCERIA_MTW = "Carrocería";
	public static final String FECHA_CADUCIDAD_ITV = "Fecha Caducidad ITV";
	public static final String ID_MARCA_MTW = "Marca";
	public static final String ID_MARCA_BASE_MTW = "Marca Base";
	public static final String ID_MODELO_MTW = "Modelo";
	public static final String ID_FABRICANTE_MTW = "Fabricante";
	public static final String ID_FABRICANTE_BASE_MTW = "Fabricante Base";
	public static final String ID_TIPO_ITV_MTW = "Tipo";
	public static final String ID_VARIANTE_MTW = "Variante";
	public static final String ID_VERSION_MTW = "Versión";
	public static final String ID_TIPO_VARIANTE_VERSION_MTW = "Tipo/Variante/Version";
	public static final String ID_TIPO_ITV_BASE_MTW = "Tipo Base";
	public static final String ID_VARIANTE_BASE_MTW = "Variante Base";
	public static final String ID_VERSION_BASE_MTW = "Versión Base";
	public static final String ID_TIPO_VARIANTE_VERSION_BASE_MTW = "Tipo/Variante/Version Base";
	public static final String ID_TARA_MTW = "Tara";
	public static final String ID_MASA_SERVICIO_MTW = "MOM";
	public static final String ID_MASA_SERVICIO_BASE_MTW = "MOM Base";
	public static final String ID_MASA_MTW = "Masa Máxima Auto";
	public static final String ID_MASA_MAX_MTW = "MTMA";
	public static final String ID_NUMERO_PLAZAS_MTW = "Nº de plazas";
	public static final String ID_PLAZAS_PIE_MTW = "Nº de plazas de pie";
	public static final String ID_DIST_EJES_MTW = "Distancia entre ejes";
	public static final String ID_VIA_ANT_MTW = "Vía anterior";
	public static final String ID_VIA_POS_MTW = "Vía posterior";
	public static final String ID_NUMERO_RUEDAS_MTW = "Neumáticos Nª Ruedas";
	public static final String ID_NUM_HOMOLOGACION_MTW = "Nº Homologación";
	public static final String ID_NUM_HOMOLOGACION_BASE_MTW = "Nº Homologación Base";
	public static final String ID_SERVICIO_MTW = "Servicio Destino";
	public static final String ID_CILINDRADA_MTW = "Cilindrada";
	public static final String ID_POTENCIA_MTW = "Potencia Fiscal";
	public static final String ID_POTENCIA_MAX_MTW = "Potencia Real";
	public static final String ID_RELACION_POTENCIA_PESO_MTW = "Relación PotenciaPeso";
	public static final String ID_CONSUMO_MTW = "Consumo WhKm";
	public static final String ID_FABRICACION_MTW = "Fabricación";
	public static final String ID_CARBURANTE_MTW = "Carburante";
	public static final String ID_TIPO_ALIMENTACION_MTW = "Tipo Alimentación";
	public static final String ID_CO2_MTW = "CO2";
	public static final String ID_NIVEL_EMISIONES_MTW = "Nivel de Emisiones";
	public static final String ID_COLOR_MTW = "Color";
	public static final String ID_FECHA_PRIMERA_MATR_MTW = "Fecha Primera Matriculación";
	public static final String ID_DOMICILIO_COMPLETO_MTW_VEHICULO = "Domicilio INE Vehículo";
	public static final String ID_CATEGORIA_ELECTRICA_MTW = "Categoría Eléctrica";
	public static final String ID_AUTONOMIA_ELECTRICA_MTW = "Autonomía Eléctrica";
	public static final String ID_CODIGO_ECO_MTW = "Código Eco";
	public static final String ID_REDUCCION_ECO_MTW = "Reducción Eco";
	public static final String ID_ECO_INNOVACION_MTW = "Eco Innovación";

	// DATOS ITV;
	public static final String MATRICULA_ORIGEN_MTW = "Matrícula Origen";
	public static final String TIPO_INSPECCION_ITV = "Tipo inspección";
	public static final String ID_ESTACION_ITV_MTW = "Estación ITV";
	public static final String PROVINCIA_INSPECCION_ITV = "Provincia ITV";
	public static final String ID_FECHA_ITV_MTW = "Fecha Inicial ITV";
	public static final String KM_ITV_MTW = "Km ITV";;
	public static final String HORAS_ITV_MTW = "Horas ITV";

	// DATOS ARRENDATARIO;
	public static final String ID_CAMBIO_DOMICILIO_ARRENDATARIO_MTW = "Cambio Domicilio_2";
	public static final String ID_NOMBRE_APELLIDOS_ARRENDATARIO_MTW = "Nombre y ApellidosRazón Social_2";
	public static final String ID_FECHA_NACIMIENTO_ARRENDATARIO_MTW = "Fecha Nacimiento_2";
	public static final String ID_SEXO_ARRENDATARIO_MTW = "Sexo_2";
	public static final String ID_DNI_ARRENDATARIO_MTW = "DOI_2";
	public static final String ID_DNI_REPRESENTANTE_ARRENDATARIO = "DOI Representante_2";
	public static final String ID_FECHA_INICIO_ARRENDATARIO_MTW = "Fecha Inicio Renting";
	public static final String ID_FECHA_FIN_ARRENDATARIO_MTW = "Fecha Fin Renting";
	public static final String ID_HORA_INICIO_ARRENDATARIO_MTW = "Hora Inicio Renting";
	public static final String ID_HORA_FIN_ARRENDATARIO_MTW = "Hora Fin Renting";
	public static final String ID_FECHA_HORA_INICIO_ARRENDATARIO_MTW = "Fecha/Hora Inicio Renting";
	public static final String ID_FECHA_HORA_FIN_ARRENDATARIO_MTW = "Fecha/Hora Fin Renting";
	public static final String ID_DOMICILIO_COMPLETO_ARRENDATARIO_MTW = "Domicilio INE Arrendatario";
	public static final String ID_NACIONALIDAD_ARRENDATARIO_MTW = "Nacionalidad_2";

	// DATOS CONDUCTOR HABITUAL";
	public static final String ID_CAMBIO_DOMICILIO_CONDUCTOR_HABITUAL_MTW = "Cambio Domicilio_3";
	public static final String ID_NOMBRE_APELLIDOS_CONDUCTOR_HABITUAL_MTW = "Nombre y ApellidosRazón Social_3";
	public static final String ID_FECHA_NACIMIENTO_CONDUCTOR_HABITUAL_MTW = "Fecha Nacimiento_3";
	public static final String ID_DNI_CONDUCTOR_HABITUAL_MTW = "DOI_3";
	public static final String ID_DNI_REPRESENTANTE_CONDUCTOR_HABITUAL_MTW = "DOI Representante_3";
	public static final String ID_DOMICILIO_COMPLETO_CONDUCTOR_HABITUAL_MTW = "Domicilio INE Conductor Habitual";
	public static final String ID_SEXO_CONDUCTOR_HABITUAL_MTW = "Sexo_3";
	public static final String ID_NACIONALIDAD_CONDUCTOR_HABITUAL_MTW = "Nacionalidad_3";
	public static final String ID_FECHA_INICIO_CONDUCTOR_HABITUAL_MTW = "Fecha Inicio";
	public static final String ID_FECHA_FIN_CONDUCTOR_HABITUAL_MTW = "Fecha Fin";
	public static final String ID_HORA_FIN_CONDUCTOR_HABITUAL_MTW = "Hora Fin";
	public static final String ID_FECHA_HORA_FIN_CONDUCTOR_HABITUAL_MTW = "Fecha/Hora Fin";
	
	
	
	public static final String ID_DOMICILIO_INE = "ID_DOMICILIO_INE";
	public static final String ID_NACIONALIDAD = "ID_NACIONALIDAD";
	public static final String ID_NOMBRE_GESTOR_MATW= "ID_NOMBRE_GESTOR";
	public static final String ID_FECHA_ACTUAL_MATW = "ID_FECHA_ACTUAL_MATW";
	public static final String ID_FIRMA_GESTOR_POSICION= "ID_FIRMA_GESTOR_POSICION";
	public static final String ID_FIRMA_COLEGIO_POSICION="ID_FIRMA_COLEGIO_POSICION";

	// FIN CAMPOS MATEW

	// *******************************
	// * TAMAÑO DE VALORES DE CAMPOS *
	// *******************************

	/**
	 * Mantis: 0001694 Author: Carlos García Se añaden campos para la nueva
	 * cadena
	 */
	public static final int TAM_NUM_EXPEDIENTE = 25;
	public static final int TAM_FACTURA = 25;
	public static final int TAM_CAMBIO_SERVICIO = 2;
	public static final String VALOR_TIPO_TRANSF_CTI = "CTI";
	public static final String VALOR_TIPO_TRANSF_NOT = "NOT";
	public static final String VALOR_TIPO_TRANSF_ENT = "ENT";

	public static final int TAM_TIPO_REG = 3;
	public static final int TAM_TASA = 12;
	public static final int TAM_FECHA_PRESENTACION = 8;
	public static final int TAM_FECHA_CONTRATO430 = 8;
	public static final int TAM_JEFATURA_TRAFICO = 2;
	public static final int TAM_MATRICULA = 9;
	public static final int TAM_TIPO_VIA_VEHICULO = 5;
	public static final int TAM_VIA_VEHICULO = 50;
	public static final int TAM_KM_VEHICULO = 3;
	public static final int TAM_HM_VEHICULO = 2;

	public static final int TAM_NUM_VIA_VEHICULO = 10;
	public static final int TAM_BLOQUE_DOMICILIO_VEHICULO = 10;
	public static final int TAM_PORTAL_DOMICILIO_VEHICULO = 10;
	public static final int TAM_ESCALERA_DOMICILIO_VEHICULO = 10;
	public static final int TAM_PLANTA_DOMICILIO_VEHICULO = 10;
	public static final int TAM_PUERTA_DOMICILIO_VEHICULO = 4;
	public static final int TAM_MUNICIPO_DOMICILIO_VEHICULO = 24;

	public static final int TAM_PROVINCIA_DOMICILIO_VEHICULO = 2;

	public static final int TAM_PUEBLO_DOMICILIO_VEHICULO = 24;
	public static final int TAM_CP_DOMICILIO_VEHICULO = 5;

	public static final int TAM_FECHA_MATRICULACION = 8;
	public static final int CODIGO_ITV = 9;// Se cambia de 8 a 9, según nuevas
											// especificaciones de MATE 2.5
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
	public static final int TAM_BASTIDOR_MATE = 17;
	public static final int TAM_BASTIDOR = 21; // Se cambia de 17 a 21 según las
												// nuevas especificaciones de
												// MATEW
	public static final int TAM_PROCEDENCIA = 3;// Se cambia de 1 a 3, según las
												// nuevas especificaciones de
												// MATE 2.5
	public static final int TAM_COLOR = 2;
	public static final int TAM_TIPO_VEHICULO = 2;
	public static final int TAM_CARBURANTE = 3;// Se cambia de 2 a 3, según las
												// nuevas especificaciones de
												// MATE 2.5
	public static final int TAM_POTENCIA = 5;
	public static final int TAM_CILINDRADA = 5;
	public static final int TAM_TARA = 6;
	public static final int TAM_MASA = 6;
	public static final int TAM_PLAZAS = 3;
	public static final int TAM_MATRICULA_AYTO = 12;
	public static final int TAM_EXCESO_PESO = 1;
	public static final int TAM_FECHA_LIMITE = 8;
	public static final int TAM_FECHA_PRIMERA_MATR = 8;
	public static final int TAM_SERVICIO = 1;
	public static final int TAM_SERVICIO_MATE = 3;
	public static final int TAM_TUTELA_MATRICULACION = 1;
	public static final int TAM_MATRICULA_DIPL = 1;
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
	public static final int TAM_CODIGO_ITV = 9;// Se cambia de 8 a 9, según MATE
												// 2.5
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
	public static final int TAM_NUM_HOMOLOGACION = 50; // Se cambia de 25 a 50
														// por especificación de
														// MATE 2.5
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

	/*
	 * 
	 * 
	 * Añadido para la TRANSMISION
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
	public static final int TAM_PROVINCIA_CET = 2;
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
	public static final int TAM_ARRENDADOR = 50;
	public static final int TAM_DNI_ARRENDADOR = 9;
	public static final int TAM_REPRESENTANTE_ARRENDADOR = 50;
	public static final int TAM_DNI_REPRESENTANTE_ARRENDADOR = 9;
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
	public static final int TAM_PUERTA_DOMICILIO_ADQUIRENTE = 4;
	public static final int TAM_CET = 8;
	public static final int TAM_TUTELA_TRANSMITENTE = 1;
	public static final int TAM_NOMBRE_VIA_ADQUIRIENTE = 50;
	public static final int TAM_TIPO_VIA_ADQUIRIENTE = 5;
	public static final int TAM_NUM_VIA_ADQUIRIENTE = 10;
	public static final int TAM_KILOMETRO_ADQUIRENTE = 3;
	public static final int TAM_HECTOMETRO_ADQUIRENTE = 2;
	public static final int TAM_BLOQUE_DOMICILIO_ADQUIRENTEE = 10;
	public static final int TAM_PORTAL_DOMICILIO_ADQUIRENTEE = 10;
	public static final int TAM_PLANTA_DOMICILIO_ADQUIRENTE = 10;
	public static final int TAM_PROVINCIA_DOMICILIO_ADQUIRENTE = 2;
	public static final int TAM_MUNICIPO_DOMICILIO_ADQUIRENTE = 24;
	public static final int TAM_PUEBLO_DOMICILIO_ADQUIRENTE = 24;
	public static final int TAM_CP_DOMICILIO_ADQUIRENTE = 5;

	public static final String LA_PROVINCIA_DEL_CET_DEBERIA_SER_INFORMADA = "LA PROVINCIA DEL CET DEBERÍA SER INFORMADA";

	public static final String XML_VALIDO = "CORRECTO";
	public static final String _XML = ".xml";
	/*
	 * 
	 * 
	 * Añadido para la BAJA
	 */
	public static final int TAM_MOTIVO_BAJA = 1;
	public static final int TAM_CEMA = 8;
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
	 * 
	 * Añadido para la SOLICITUD DE DATOS
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

	/*
	 * 
	 * 
	 * Añadido para la AEAT
	 */
	public static final int TAM_TIPO_DECLARACION = 1;
	public static final int TAM_NIF = 9;
	public static final int TAM_EJERCICIO = 4;
	public static final int TAM_TIPO_TRANSPORTE = 1;
	public static final int TAM_APELLIDOS_NOMBRE_TITULAR = 40;
	public static final int TAM_TIPO_VIA_AEAT = 10;
	public static final int TAM_NOMBRE_VIA_AEAT = 25;
	public static final int TAM_NUM_DOMICILIO_AEAT = 10;
	public static final int TAM_ESCALERA_AEAT = 5;
	public static final int TAM_PISO_AEAT = 5;
	public static final int TAM_PUERTA_AEAT = 5;
	public static final int TAM_TELEFONO_AEAT = 10;
	public static final int TAM_CP_AEAT = 5;
	public static final int TAM_MUNICIPIO_AEAT = 25;
	public static final int TAM_PROVINCIA_AEAT = 15;
	public static final int TAM_REPRESENTANTE_AEAT = 40;
	public static final int TAM_ADQUISICION = 1;
	public static final int TAM_M_TRANSP_US = 1;
	public static final int TAM_MARCA_VEHICULO = 40;
	public static final int TAM_MODELO_VEHICULO = 80;
	public static final int TAM_CLASIFICACION_VEHICULO = 40;
	public static final int TAM_MOTOR_VEHICULO = 1;
	public static final int TAM_FABRICANTE_EMBARCACION = 40;
	public static final int TAM_MODELO_EMBARCACION = 80;
	public static final int TAM_IDENTIFICACION_EMBARCACION = 22;
	public static final int TAM_ESLORA_MAX_EMBARCACION = 5;
	public static final int TAM_FABRICANTE_AERONAVE = 40;
	public static final int TAM_MODELO_AERONAVE = 80;
	public static final int TAM_NUM_SERIE_AERONAVE = 22;
	public static final int TAM_ANIO_FABRICACION_AERONAVE = 4;
	public static final int TAM_PESO_MAX_AERONAVE = 10;
	public static final int TAM_PREVER_MARCA = 40;
	public static final int TAM_PREVER_TIPO = 40;
	public static final int TAM_PREVER_MODELO = 80;
	public static final int TAM_PREVER_BASTIDOR = 22;
	public static final int TAM_PREVER_CLASIFICACION = 40;
	public static final int TAM_PREVER_MATRICULA = 10;
	public static final int TAM_BASE_IMPONIBLE = 13;
	public static final int TAM_BASE_IMPONIBLE_RED = 13;
	public static final int TAM_TIPO_GRAVAMEN = 5;
	public static final int TAM_CUOTA = 13;
	public static final int TAM_DEDUCCION_LINEAL = 13;
	public static final int TAM_CUOTA_INGRESO = 13;
	public static final int TAM_DEDUCCION = 13;
	public static final int TAM_RESULTADO_LIQUIDACION = 13;
	public static final int TAM_NUM_DECLARACION_ANTERIOR = 13;
	public static final int TAM_006 = 46;
	public static final int TAM_EXPEDIENTE_AEAT = 30;
	public static final int TAM_RESERVADO_AEAT = 169;
	public static final int TAM_EJERCICIO_FISCAL = 2;
	public static final int TAM_PERIODO = 2;
	public static final int TAM_TIPO_MONEDA = 1;
	public static final int TAM_TIPO_AUTOLIQUIDACION = 1;
	public static final int TAM_LETRAS_ETIQUETA = 4;
	public static final int TAM_CODIGO_ADMON = 5;
	public static final int TAM_FRACCIONAMIENTO = 1;
	public static final int TAM_FECHA_CADUCIDAD = 4;
	public static final int TAM_CAMPO_LIBRE = 20;
	public static final int TAM_IMPORTE_NRC = 12;
	public static final int TAM_CCC = 20;
	public static final int TAM_FECHA_PAGO_576 = 8;
	public static final int TAM_NRC = 22;
	public static final int TAM_DECLARACION_EXENCION = 4;
	public static final int TAM_DELEGACION = 12;
	public static final int TAM_ADMINISTRACION = 25;
	public static final int TAM_MODELO_05 = 40;
	public static final int TAM_TIPO_05 = 40;
	public static final int TAM_BASTIDOR_AEAT = 22;
	public static final int TAM_VEHICULO_USADO = 1;
	public static final int TAM_CLASIFICACION_CONSTRUCCION = 2;
	public static final int TAM_CLASIFICACION_UTILIZACION = 2;
	public static final int TAM_CLASIFICACION_DIRECTIVA = 2;
	public static final int TAM_EMISIONES_CO2 = 5;
	public static final int TAM_EPIGRAFE = 2;
	public static final int TAM_KM_USO = 6;
	public static final int TAM_NUM_SERIE_ITV = 12;
	public static final int TAM_TIPO_TARJETA_ITV = 1;
	public static final int TAM_CAUSA_HECHO_IMPONIBLE = 1;
	public static final int TAM_FECHA_DEVENGO = 8;
	public static final int TAM_DNI_INTRODUCTOR_VEHICULO = 9;
	public static final int TAM_INTRODUCTOR_VEHICULO = 40;
	public static final int TAM_DIPUTACIONES = 79;
	public static final int TAM_CENTRO_GRABACION = 5;
	public static final int TAM_OBSERVACIONES_VEHICULO = 2;
	public static final int TAM_OBSERVACIONES_EMBARCACION = 2;
	public static final int TAM_RESERVADO_23 = 23;

	// Log 576
	public static final int TAM_CEP = 16;
	public static final int TAM_BASTIDOR_LOG576 = 17;
	public static final int TAM_ESPACIOSENBLANCO_LOG576 = 6;
	public static final int TAM_FECHA_PRESENTACION_LOG576 = 10;
	public static final int TAM_HORA_PRESENTACION_LOG576 = 8;

	// MATE
	public static final int TAM_CODIGO_IAE = 6;
	public static final int TAM_FLAG_IAE = 1;
	public static final int TAM_FLAG_CONDUCTOR = 1;
	public static final int TAM_DNI_CONDUCTOR_HABITUAL = 9;
	public static final int TAM_FECHA_INICIO_CONDUCTOR = 8;
	public static final int TAM_FECHA_FIN_CONDUCTOR = 8;
	public static final int TAM_HORA_INICIO_CONDUCTOR = 4;
	public static final int TAM_HORA_FIN_CONDUCTOR = 4;
	public static final int TAM_DNI_ARRENDATARIO = 9;
	public static final int TAM_PRIMER_APELLIDO_ARRENDATARIO = 26;
	public static final int TAM_SEGUNDO_APELLIDO_ARRENDATARIO = 26;
	public static final int TAM_NOMBRE_ARRENDATARIO = 18;
	public static final int TAM_DOMICILIO_ARRENDATARIO = 50;
	public static final int TAM_MUNICIPIO_ARRENDATARIO = 24;
	public static final int TAM_PUEBLO_ARRENDATARIO = 24;
	public static final int TAM_PROVINCIA_ARRENDATARIO = 2;
	public static final int TAM_CP_ARRENDATARIO = 5;
	public static final int TAM_SEXO_ARRENDATARIO = 1;
	public static final int TAM_FECHA_NACIMIENTO_ARRENDATARIO = 8;
	public static final int TAM_FECHA_INICIO_RENTING = 8;
	public static final int TAM_HORA_INICIO_RENTING = 4;
	public static final int TAM_FECHA_FIN_RENTING = 8;
	public static final int TAM_HORA_FIN_RENTING = 4;
	public static final int TAM_TIPO_CARGA = 1;
	public static final int TAM_FECHA_CARGA = 8;
	public static final int TAM_REGISTRO_CARGA = 25;
	public static final int TAM_FINANCIERA_CARGA = 4;
	public static final int TAM_MASA_EN_SERVICIO = 6;
	public static final int TAM_NUMERO_RUEDAS = 2;
	public static final int TAM_CAMBIO_DOMICILIO = 1;
	public static final int TAM_CODIGO_CEMU = 24;
	public static final int TAM_RENTING = 1;
	public static final int TAM_CARGAS = 1;
	public static final int TAM_KM_ITV = 7;
	public static final int TAM_HORA_ITV = 7;
	public static final int TAM_CO2 = 6;// Cambia por MATE 2.5, de 3 a 6 (3
										// enteros, 3 decimales);
	public static final int TAM_FECHA_INICIO_TUTELA = 8;
	public static final int TAM_TRAMITE_TUTELA = 4;
	public static final int TAM_TIPO_TUTELA = 1;
	public static final int TAM_TIPO_MATRICULA = 9;
	public static final int TAM_ARRENDATARIO_COMPLETO = 281;
	public static final int TAM_CONDUCTOR_COMPLETO = 25;
	public static final int TAM_CONDUCTOR_HABITUAL = 33;

	public static final int TAM_TIPO_VIA_MATE = 5;
	public static final int TAM_NUM_DOMICILIO_MATE = 10;
	public static final int TAM_LETRA_DOMICILIO_MATE = 10;
	public static final int TAM_ESCALERA_DOMICILIO_MATE = 10;
	public static final int TAM_PISO_DOMICILIO_MATE = 10;
	public static final int TAM_PUERTA_DOMICILIO_MATE = 4;
	public static final int TAM_KM_DOMICILIO_MATE = 3;
	public static final int TAM_HEC_DOMICILIO_MATE = 2;
	public static final int TAM_BLOQUE_DOMICILIO_MATE = 10;

	public static final int TAM_NUM_DOMICILIO_MATE_XML = 4;
	public static final int TAM_BLOQUE_DOMICILIO_MATE_XML = 2;
	public static final int TAM_LETRA_DOMICILIO_MATE_XML = 2;
	public static final int TAM_ESCALERA_DOMICILIO_MATE_XML = 2;
	public static final int TAM_PISO_DOMICILIO_MATE_XML = 2;
	public static final int TAM_PUERTA_DOMICILIO_MATE_XML = 4;
	public static final int TAM_KM_DOMICILIO_MATE_XML = 3;
	public static final int TAM_HEC_DOMICILIO_MATE_XML = 2;

	/* TAMAÑOS NUEVAS VARIABLES MATE 2.5 */
	public static final int TAM_CATEGORIA_EU = 4;
	public static final int TAM_CARROCERIA = 4;
	public static final int TAM_DIST_EJES = 4;
	public static final int TAM_VIA_ANT = 4;
	public static final int TAM_VIA_POST = 4;
	public static final int TAM_CONSUMO = 4;
	public static final int TAM_FABRICACION = 3;
	public static final int TAM_TIPO_ALIMENTACION = 1;
	public static final int TAM_NIVEL_EMISIONES = 8;
	public static final int TAM_FABRICANTE = 70;
	public static final int TAM_IMPORTADO = 1;
	public static final int TAM_PAIS_IMPORT = 3;
	public static final int TAM_ECO_INNOVA = 1;
	public static final int TAM_REDUCCION_EC = 4;
	public static final int TAM_CODIGO_ECO = 10;
	public static final int TAM_SUBASTA = 1;
	public static final int TAM_NIVE = 32;
	/* FIN TAMAÑOS MATE 2.5 */

	/* TAMAÑOS NUEVAS VARIABLES MATEW */
	public static final int TAM_IDEXPEDIENTE = 20;
	public static final int TAM_ESTACION_ITV = 5;
	public static final int TAM_PROVINCIA_ESTACION_ITV = 2;
	public static final int TAM_FECHA_INICIAL_ITV = 8;
	public static final int TAM_FECHA_CADUCIDAD_ITV = 8;
	public static final int TAM_SUCURSAL = 1;
	public static final int TAM_MAT_ANOTADOR = 32;
	public static final int TAM_TIPO_TRAMITE = 2;
	public static final int TAM_NUM_EXPEDIENTE_COL = 100;
	public static final int TAM_MATRICULA_ORIGEN = 9;
	public static final int TAM_TIPO_INSPECCION = 1;
	public static final int TAM_RAZON_SOCIAL = 70;
	public static final int TAM_CAMBIO_DOMICILIO_MATEW = 1;
	public static final int TAM_DIST_EJES_MATEW = 4;
	public static final int TAM_VIA_ANT_MATEW = 4;
	public static final int TAM_NUM_HOMOLOGACION_MATEW = 50;
	public static final int TAM_CONSUMO_MATEW = 4;
	public static final int TAM_CARROCERIA_MATW = 5;
	public static final int TAM_NUM_VIA = 4;
	public static final int TAM_BLOQUE_DOMICILIO = 5;
	public static final int TAM_PORTAL_DOMICILIO = 5;
	public static final int TAM_ESCALERA_DOMICILIO = 2;
	public static final int TAM_PLANTA_DOMICILIO = 2;
	public static final int TAM_PUERTA_DOMICILIO = 2;
	public static final int TAM_MUNICIPIO_DOMICILIO = 5;
	public static final int TAM_PAIS_DOMICILIO = 5;
	public static final int TAM_NACIONALIDAD_TITULAR = 32;
	public static final int TAM_EXENCION_IVTM = 1;
	public static final int TAM_TIPO_ALIMENTACION_MATEW = 1;
	public static final int TAM_ECO_INNOVA_MATEW = 1;
	public static final int TAM_REDUCCION_EC_MATEW = 4;
	public static final int TAM_CODIGO_ECO_MATEW = 10;
	public static final int TAM_NIVEL_EMISIONES_MATEW = 15;
	public static final int TAM_CATEGORIA_EU_MATEW = 5;
	public static final int TAM_PAIS_FABRICACION = 3;
	public static final int TAM_CO2_MATEW = 7;
	public static final int TAM_ITVIND = 4;
	public static final int TAM_EXENCION_CEM = 10;
	//public static final int TAM_SATISFECHO_CET = 1;
	public static final int TAM_NUMHOR_USO = 6;
	public static final int TAM_FLAG_IAE_MATEW = 1;
	public static final int TAM_CODIGO_IAE_MATEW = 4;
	public static final int TAM_CARBURANTE_MATEW = 3;
	public static final int TAM_KM_ITV_MATW = 6;
	public static final int TAM_NOMBRE_TITULAR_MATW = 20;
	public static final int TAM_PRIMER_APELLIDO_MATW=20;
	public static final int TAM_DOMICILIO_TITULAR_MATW = 114;
	public static final int TAM_NUM_VIA_MATW=5;
	public static final int TAM_HEC_DOMICILIO_MATW=7;
	public static final int TAM_TIPO_VEHICULO_MATW=4;
    public static final int TAM_POTENCIA_MATW = 13;
    public static final int TAM_CILINDRADA_MATW=8;
    public static final int TAM_TARA_MATW=7;
    public static final int TAM_MASA_SERVICIO_MATW=7;
    public static final int TAM_POTENCIA_MAX_MATW=13;
    public static final int TAM_PROCEDENCIA_MATW=1;
    public static final int TAM_ARRENDATARIO_COMPLETO_MATW = 316;
    public static final int TAM_JUSTIFICADO_INVTM=1;
   
	/* FIN MATEW */

	// ***********************
	// * VALORES DE CAMPOS *
	// ***********************
	/**
	 * Valor para tipo de registro: PDF
	 */
	public static final String VALOR_TIPO_REG_PDF = "PDF";
	/**
	 * Valor para tipo de registro: BAJA
	 */
	public static final String VALOR_TIPO_REG_BAJA = "020";
	/**
	 * Valor para tipo de registro: TRANSMISION
	 */
	public static final String VALOR_TIPO_REG_TRANSMISION = "030";
	/**
	 * Valor para tipo de registro: MATRICULACION
	 */
	public static final String VALOR_TIPO_REG_MATRICULACION = "040";

	// ***********************************
	// * FORMATOS PARA VALORES DE CAMPOS *
	// ***********************************
	public static final String FORMATO_FECHA_PRESENTACION = "yyyyMMdd";
	public static final String FORMATO_FECHA_CONTRATO430 = "yyyyMMdd";
	public static final String FORMATO_FECHA_MATRICULACION = "yyyyMMdd";
	public static final String FORMATO_FECHA_ITV = "yyyyMMdd";
	public static final String FORMATO_FECHA_NACIMIENTO = "yyyyMMdd";
	public static final String FORMATO_FECHA_LIMITE = "yyyyMMdd";
	public static final String FORMATO_FECHA_PRIMERA_MATR = "yyyyMMdd";
	public static final String FORMATO_FECHA_LIMITACION = "yyyyMMdd";
	public static final String FORMATO_FECHA_CREACION = "yyyyMMdd";
	public static final String FORMATO_FECHA_NACIMIENTO_TITULAR_2 = "yyyyMMdd";
	public static final String FORMATO_FECHA_NACIMIENTO_TITULAR_3 = "yyyyMMdd";
	public static final String FORMATO_FECHA_NACIMIENTO_ADQUIRENTE = "yyyyMMdd";
	public static final String FORMATO_FECHA_PAGO_576 = "yyyyMMdd";
	// Nuevos campos de MATE
	public static final String FORMATO_FECHA_INICIO_TUTELA = "yyyyMMdd";
	public static final String FORMATO_FECHA_CONDUCTOR_HABITUAL = "yyyyMMdd";
	public static final String FORMATO_FECHA_RENTING = "yyyyMMdd";
	public static final String FORMATO_HORA_RENTING = "HHmm";
	public static final String FORMATO_FECHA_NACIMIENTO_ARRENDATARIO = "yyyyMMdd";
	public static final String FORMATO_FECHA_CARGA = "yyyyMMdd";

	// ***********************************
	// * TAMAÑO DE LAS LINEAS DE FICHERO *
	// ***********************************
	public static final int TAM_LINEA_PDF_SIN_BLANCOS = 58;
	public static final int TAM_LINEA_PDF_CON_BLANCOS = 73;
	public static final int TAM_LINEA_MATRICULACION = 1850;
	public static final int TAM_LINEA_TRANSMISION = 1610;
	public static final int TAM_LINEA_BAJA_MENOR = 1131;
	public static final int TAM_LINEA_BAJA_MAYOR = 1344;
	public static final int TAM_LINEA_SOLICITUD = 757;
	public static final int TAM_LINEA_AEATLOG576 = 111;

	// ***************************
	// * OTROS VALORES DE CAMPOS *
	// ***************************
	public static final int POSICION_MOTIVO_BAJA = 49;
	public static final String MOTIVO_BAJA_4 = "4";
	public static final String VALOR_TIPO_DECLARACION_576_I = "I";
	public static final String VALOR_TIPO_DECLARACION_576_N = "N";
	public static final String VALOR_TIPO_DECLARACION_05_06 = " ";
	public static final String VALOR_TIPO_AUTOLIQUIDACION_NRC_I = "I";
	public static final String VALOR_TIPO_AUTOLIQUIDACION_NRC_D = "D";
	public static final String VALOR_PERIODO = "0A";
	public static final String VALOR_TIPO_MONEDA = "E";
	public static final String VALOR_TIPO_TRANSPORTE_VEHICULO = "V";
	public static final String VALOR_ADQUIRIDO_UE_DISTINTO_SPA = "2";
	public static final String VALOR_VEHICULO_USADO_TRUE = "true";
	public static final String VALOR_VEHICULO_USADO = "2";
	public static final String VALOR_VEHICULO_NO_USADO_2008 = "1";
	public static final String VALOR_VEHICULO_ADQUIRIDO_NO_UE = "3";
	public static final String VALOR_TIPO_TARJETA_ITV_A = "A";
	public static final String VALOR_DECLARACION_EXENCION_05 = "ER3";
	public static final String VALOR_TIPO_LIMITACION = "E";

	// VALORES DE CAMPOS MATEW
	public static final String VALOR_PAIS = "ESP";

	public static final Integer ANIO_LIMITE_DEVENGO = 2008;

	// La aplicación SIGA exporta la esta fecha cuando la fecha está vacía.
	public static final String FECHA_DEFECTO_SIGA = "18991230";
	public static final int TAM_FECHA_DEFECTO_SIGA = 8;

	// public static final CasillasTramiteTrafico[]
	// CASILLAS_FECHAS_MATRICULACION = {CASILLA_FECHA_PRESENTACION,
	// CASILLA_FECHA_MATRICULACION,
	// CASILLA_FECHA_ITV, CASILLA_FECHA_NACIMIENTO, CASILLA_FECHA_LIMITE,
	// CASILLA_FECHA_PRIMERA_MATR, CASILLA_FECHA_CREACION,
	// CASILLA_FECHA_LIMITACION, CASILLA_FECHA_NACIMIENTO_TITULAR_2,
	// CASILLA_FECHA_NACIMIENTO_TITULAR_3};
	//
	// public static final CasillasTramiteTrafico[] CASILLAS_FECHAS_TRANSMISION
	// = {CASILLA_FECHA_PRESENTACION, CASILLA_FECHA_MATRICULACION,
	// CASILLA_FECHA_ITV, CASILLA_FECHA_NACIMIENTO_ADQUIRENTE,
	// CASILLA_FECHA_LIMITACION, CASILLA_FECHA_CREACION};
	//
	// public static final CasillasTramiteTrafico[] CASILLAS_FECHAS_BAJA =
	// {CASILLA_FECHA_PRESENTACION, CASILLA_FECHA_MATRICULACION,
	// CASILLA_FECHA_NACIMIENTO_ADQUIRENTE, CASILLA_FECHA_CREACION};
	//
	// public static final CasillasTramiteTrafico[] CASILLAS_FECHAS_SOLICITUD =
	// {CASILLA_FECHA_PRESENTACION, CASILLA_FECHA_CREACION};

	// CONSTANTES_VALIDACION

	public static final String SIN_CODIG = "SINCODIG";
	public static final String SIN_CODIGO = "SINCODIGO";
	public static final String SIN_CODIGO_MATW = "SINCODIGOITV";
	public static final String SIN_CODIGO_1 = "SINCDG";
	public static final String VALOR_CET_PREDETERMINADO = "00000000";

	// Genéricos de direcciones
	public static final String ID_TIPO_VIA = "ID_TIPO_VIA";
	public static final String ID_CALLE = "ID_CALLE";
	public static final String ID_NUM_DOMICILIO = "ID_NUM_DOMICILIO";
	public static final String ID_KM_DOMICILIO = "ID_KM_DOMICILIO";
	public static final String ID_HM_DOMICILIO = "ID_HM_DOMICILIO";
	public static final String ID_BLOQUE_DOMICILIO = "ID_BLOQUE_DOMICILIO";
	public static final String ID_LETRA_DOMICILIO = "ID_LETRA_DOMICILIO";
	public static final String ID_ESCALERA_DOMICILIO = "ID_ESCALERA_DOMICILIO";
	public static final String ID_PISO_DOMICILIO = "ID_PISO_DOMICILIO";
	public static final String ID_PUERTA_DOMICILIO = "ID_PUERTA_DOMICILIO";
	public static final String ID_MUNICIPIO = "ID_MUNICIPIO";
	public static final String ID_PUEBLO = "ID_PUEBLO";
	public static final String ID_PROVINCIA = "ID_PROVINCIA";
	public static final String ID_CP = "ID_CP";
	public static final String ID_DOMICILIO_COMPLETO = "ID_DOMICILIO_COMPLETO";
	public static final String ID_DOMICILIO_DIRECCION = "ID_DOMICILIO_DIRECCION";
	public static final String ID_EXENCION = "ID_EXENCION";

	// Bajas Exportacion o transito comunitario
	public static final String ID_PAIS_DESTINO = "ID_PAIS_DESTINO";
	public static final String CHECK_ACREDITA = "CHECK_ACREDITA";
	
	// Genéricos de personas
	public static final String ID_NOMBRE_APELLIDOS = "ID_NOMBRE_APELLIDOS";
	public static final String ID_NOMBRE_APELLIDOS_ADQUIRIENTE = "ID_NOMBRE_APELLIDOS_ADQUIRIENTE";
	public static final String ID_NOMBRE_APELLIDOS_REPR_ADQUIRIENTE = "ID_NOMBRE_APELLIDOS_REPR_ADQUIRIENTE";
	public static final String ID_DNI = "ID_DNI";
	public static final String ID_DNI_ADQUIRIENTE = "ID_DNI_ADQUIRIENTE";
	public static final String ID_DNI_REPR_ADQUIRIENTE = "ID_DNI_REPR_ADQUIRIENTE";
	public static final String ID_SEXO = "ID_SEXO";

	// Constantes para vpo
	/**
	 * Avpo
	 */
	public static final String ID_ERROR = ID_PREFIX + "ERROR";
	public static final String ID_NUM_COLEGIADO = ID_PREFIX + "NUM_COLEGIADO";
	public static final String ID_COLEGIADO = ID_PREFIX + "COLEGIADO";

	// OTROS
	public static final String FICHEROXML = "FicheroXML";
	public static final String BYTESXML = "BytesXML";
	public static final String NOMBREXML = "NombreXML";
	
	// OTROS
	public static final String FICHEROAEAT = "FicheroAEAT";
	public static final String BYTESAEAT = "BytesAEAT";
	public static final String NOMBREAEAT = "NombreAEAT";

	// OTROS
	public static final String FICHEROBM = "FicheroBM";
	public static final String BYTESBM = "BytesBM";
	public static final String NOMBREBM = "NombreBM";

	public static final String PREFERENCIAS = "preferencias";

	//EEFF. 
	public static final String CONSTANTE_TITULAR = "TITULAR";
	public static final String CONSTANTE_VEHICULO= "VEHICULO";
	
	// JUSTIFICANTES

	// public static final Integer DIAS_VALIDEZ_POR_DEFECTO = 60;
	public static final Integer DIAS_VALIDEZ_POR_DEFECTO = 30; // Incidencia
																// 4359 atc
	public static final DocumentosJustificante DOCUMENTOS_POR_DEFECTO = DocumentosJustificante.PermisoCirculacionYFichaTecnica;
	public static final MotivoJustificante MOTIVO_TRANSMISION_POR_DEFECTO = MotivoJustificante.TransferenciaCambioTitularidadCtit;
	public static final MotivoJustificante MOTIVO_DUPLICADO_POR_DEFECTO = MotivoJustificante.DuplicadoCambioDatos;

	// ASIGNACIÓN MASIVA DE TASAS

	public static final String LISTA_CORRECTOS = "LISTA_CORRECTOS";
	public static final String LISTA_ERRORES = "LISTA_ERRORES";
	public static final String INICIO_ASIGNACION_MASIVA_DE_TASAS = "INICIO ASIGNACIÓN MASIVA DE TASAS";
	public static final String FIN_ASIGNACION_MASIVA_DE_TASAS = "FIN ASIGNACIÓN MASIVA DE TASAS";
	public static final String ERROR_AL_OBTENER_LA_LISTA_DE_TRAMITES = "Error al obtener la lista de trámites";
	public static final String NO_SE_HA_ENCONTRADO_EL_EXPEDIENTE_DE_NUMERO = "No se ha encontrado el expediente de número: ";
	public static final String NO_SE_PUEDE_RECUPERAR_EL_TIPO_DE_VEHICULO_PARA_EL_EXPEDIENTE = "No se puede recuperar el tipo de vehículo para el expediente ";
	public static final String NO_SE_PUEDE_RECUPERAR_EL_TIPO_DE_TRANSFERENCIA_PARA_EL_EXPEDIENTE = "No se puede recuperar el tipo de transferencia para el expediente ";
	public static final String NO_SE_PUEDE_RECUPERAR_EL_TIPO_DE_TRAMITE_PARA_EL_EXPEDIENTE = "No se puede recuperar el tipo de trámite para el expediente ";
	public static final String NO_SE_HA_RECUPERADO_EL_IDENTIFICADOR_DE_LAS_TASAS_IMPORTADAS_DESDE_EL_ICOGAM = "No se ha recuperado el identificador de las tasas importadas desde el ICOGAM";
	public static final String ESTA_FUNCIONALIDAD_SOLO_ESTA_DISPONIBLE_PARA_TRAMITES_EN_ESTADO_INICIADO = "Esta funcionalidad sólo está disponible para trámites en estado iniciado";
	public static final String NO_DISPONE_DE_PERMISO_PARA_REALIZAR_ESTA_ACCION = "No dispone de permiso para realizar esta acción";

	// CONSTANTES PARA EL GESTOR DE FICHEROS
//	public static final String TIPO_GF_SOLICITUD_INFORMACION = "SOLINFO";
//	public static final String TIPO_GF_ANUNTIS = "ANUNTIS";
//	public static final String SUBTIPO_GF_ATEM = "ATEM";
//	public static final String SUBTIPO_GF_ATEM_XML = "ATEM_XMLS";
	
	public static final BigDecimal MARCA_DGT_MATE = new BigDecimal("0");
	public static final BigDecimal MARCA_DGT_MATW = new BigDecimal("1");
	public static final BigDecimal MARCA_DGT_COMUNES_MATE_MATW = new BigDecimal("2");
	public static final String MARCAS_DGT_MATE = "MATE";
	public static final String MARCAS_DGT_MATW = "MATW";
	
	/* INICIO MANTIS 0012591 */
	// CONSTANTES PARA LAS SOLICITUDES DE INFORMACION
	public static final String LIMITACION_NUMERO_SOLICITUDES = "maximo.numero.solicitudes.tramite";
	public static final int NUM_MAXIMO_SOLICITUDES_POR_DEFECTO = 10;
	/* FIN MANTIS 0012591 */
	
	public ConstantesTrafico() {
	}
}
