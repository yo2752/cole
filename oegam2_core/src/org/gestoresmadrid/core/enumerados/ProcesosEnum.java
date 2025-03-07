package org.gestoresmadrid.core.enumerados;

public enum ProcesosEnum {

	CHECKCTIT("CHECKCTIT", "Check CTIT") {
		public String toString() {
			return "CHECKCTIT";
		}
	},
	ESTADISTICAS("ESTADISTICAS", "Envío estadisticas CTIT y MATE") {
		public String toString() {
			return "ESTADISTICAS";
		}
	},
	ESTADISTICAS_NUEVO("ESTADISTICAS_NUEVO", "Envío estadisticas CTIT y MATE Nuevo") {
		public String toString() {
			return "ESTADISTICAS_NUEVO";
		}
	},
	Excel_Bajas_Madrid("EXCELBAJAMADRID", "EXCEL BAJAS MADRID") {
		public String toString() {
			return "EXCELBAJAMADRID";
		}
	},
	Excel_Duplicados_Madrid("EXCELDUPLICADOMADRID", "EXCEL DUPLICADOS MADRID") {
		public String toString() {
			return "EXCELDUPLICADOMADRID";
		}
	},
	Excel_Bajas_Alcorcon("EXCELBAJAALCORCON", "EXCEL BAJAS ALCORCON") {
		public String toString() {
			return "EXCELBAJAALCORCON";
		}
	},
	Excel_Duplicados_Alcorcon("EXCELDUPLICADOALCORCON", "EXCEL DUPLICADOS ALCORCON") {
		public String toString() {
			return "EXCELDUPLICADOALCORCON";
		}
	},
	Excel_Bajas_Alcala("EXCELBAJAALCALA", "EXCEL BAJAS ALCALA") {
		public String toString() {
			return "EXCELBAJAALCALA";
		}
	},
	Excel_Duplicados_Alcala("EXCELDUPLICADOALCALA", "EXCEL DUPLICADOS ALCALA") {
		public String toString() {
			return "EXCELDUPLICADOALCALA";
		}
	},
	Excel_Bajas_Avila("EXCELBAJAAVILA", "EXCEL BAJAS AVILA") {
		public String toString() {
			return "EXCELBAJAAVILA";
		}
	},
	Excel_Duplicados_Avila("EXCELDUPLICADOAVILA", "EXCEL DUPLICADOS AVILA") {
		public String toString() {
			return "EXCELDUPLICADOAVILA";
		}
	},
	Excel_Bajas_Ciudad_Real("EXCELBAJACIUDADREAL", "EXCEL BAJAS CIUDAD REAL") {
		public String toString() {
			return "EXCELBAJACIUDADREAL";
		}
	},
	Excel_Duplicados_Ciudad_Real("EXCELDUPLICADOCIUDADREAL", "EXCEL DUPLICADOS CIUDAD REAL") {
		public String toString() {
			return "EXCELDUPLICADOCIUDADREAL";
		}
	},
	Excel_Bajas_Cuenca("EXCELBAJACUENCA", "EXCEL BAJAS CUENCA") {
		public String toString() {
			return "EXCELBAJACUENCA";
		}
	},
	Excel_Duplicados_Cuenca("EXCELDUPLICADOCUENCA", "EXCEL DUPLICADOS CUENCA") {
		public String toString() {
			return "EXCELDUPLICADOCUENCA";
		}
	},
	Excel_Bajas_Guadalajara("EXCELBAJAGUADALAJARA", "EXCEL BAJAS GUADALAJARA") {
		public String toString() {
			return "EXCELBAJAGUADALAJARA";
		}
	},
	Excel_Duplicados_Guadalajara("EXCELDUPLICADOGUADALAJARA", "EXCEL DUPLICADOS GUADALAJARA") {
		public String toString() {
			return "EXCELDUPLICADOGUADALAJARA";
		}
	},
	Excel_Bajas_Segovia("EXCELBAJASEGOVIA", "EXCEL BAJAS SEGOVIA") {
		public String toString() {
			return "EXCELBAJASEGOVIA";
		}
	},
	Excel_Duplicados_Segovia("EXCELDUPLICADOSEGOVIA", "EXCEL DUPLICADOS SEGOVIA") {
		public String toString() {
			return "EXCELDUPLICADOSEGOVIA";
		}
	},
	FIRSTMATENUEVO("FIRSTMATENUEVO", "Envío primera matrícula Nuevo") {
		public String toString() {
			return "FIRSTMATENUEVO";
		}
	},
	FULLCTIT("FULLCTIT", "Full CTIT") {
		public String toString() {
			return "FULLCTIT";
		}
	},
	ISSUEPROPROOF("ISSUEPROPROOF", "Emisión de justificantes profesionales") {
		public String toString() {
			return "ISSUEPROPROOF";
		}
	},
	JUSTIFICANTES_SEGA("JUSTIFICANTESSEGA", "Emisión de justificantes profesionales de Sega") {
		public String toString() {
			return "JUSTIFICANTESSEGA";
		}
	},
	NOTIFICATIONCTIT("NOTIFICATIONCTIT", "Notification CTIT") {
		public String toString() {
			return "NOTIFICATIONCTIT";
		}
	},
	TRADECTIT("TRADECTIT", "Trade CTIT") {
		public String toString() {
			return "TRADECTIT";
		}
	},
	ULTMATENUEVO("ULTMATENUEVO", "Envío última matrícula Nuevo") {
		public String toString() {
			return "ULTMATENUEVO";
		}
	},
	VERIFYPROPROOF("VERIFYPROPROOF", "Verifica la validez de un Justificante Profesional") {
		public String toString() {
			return "VERIFYPROPROOF";
		}
	},
	INTEVE3("INTEVE3", "Proceso INTEVE Tres") {
		public String toString() {
			return "INTEVE3";
		}
	},
	IVTM("IVTM", "Proceso IVTM Madrid") {
		public String toString() {
			return "IVTM";
		}
	},
	IVTM_MODIFICACION("IVTM_MODIFICACION", "Proceso Modificacion IVTM Madrid") {
		public String toString() {
			return "IVTM_MODIFICACION";
		}
	},
	IVTM_CONSULTA("CONSULTA_IVTM", "Proceso Consulta IVTM Madrid") {
		public String toString() {
			return "CONSULTA_IVTM";
		}
	},
	IVTM_ALTA("ALTA_IVTM", "Proceso Alta IVTM Madrid") {
		public String toString() {
			return "IVTM_ALTA";
		}
	},
	MATW("MATW", "Conexión Matriculación") {
		public String toString() {
			return "MATRICULACION";
		}
	},
	PROCESO_576("576", "Proceso 576") {
		public String toString() {
			return "576";
		}
	},
	ATEM("ATEM", "Proceso ATEM") {
		public String toString() {
			return "ATEM";
		}
	},
	CADUCIDAD_CERTIFICADOS("CADCERTS", "Alerta dias validez certificados") {
		public String toString() {
			return "CADCERTS";
		}
	},
	IMPRIMIRTRAMITES("IMPRIMIRTRAMITES", "Imprimir Tramites Masivos") {
		public String toString() {
			return "IMPRIMIRTRAMITES";
		}
	},
	WREG("WREG", "Conexión con Registro") {
		public String toString() {
			return "WREG";
		}
	},
	VEHICULO_PREMATICULADO_DATOS_EITV("DATOS_EITV", "Consulta datos técnicos EITV de Vehiculos prematriculados") {
		public String toString() {
			return "DATOS_EITV";
		}
	},
	COMPRA_TASAS_DGT("COMPRATASAS", "Servicio de compra de tasas de la DGT") {
		public String toString() {
			return "COMPRATASAS";
		}
	},
	IMPORTACIONMASIVA("IMPORTACIONMASIVA", "Importación Masiva") {
		public String toString() {
			return "IMPORTACIONMASIVA";
		}
	},
	EEFF("EEFF", "Entidades Financieras") {
		public String toString() {
			return "EEFF";
		}
	},
	PROCESO_EEFF("PROCESO_EEFF", "Proceso EEFF") {
		public String toString() {
			return "PROCESO_EEFF";
		}
	},
	PRESENTACION_JPT("PRESENTACIONJPT", "Presentacion Jefatura Provincial de Tráfico") {
		public String toString() {
			return "PRESENTACIONJPT";
		}
	},
	SEA_ENVIO_DS("SEA_ENVIO_DS", "Envio de datos sensibles SEA") { // Mantis 18007
		public String toString() {
			return "SEA_ENVIO_DS";
		}
	},
	VPDATOSEITV("VPDATOSEITV", "MATE EITV Prematriculados") { // Mantis 14748
		public String toString() {
			return "PREMATRICULADOS DATOS EITV";
		}
	},
	VPFICHATECNICA("VPFICHATECNICA", "Vehiculos Prematriculados Ficha Tecnica") { // Mantis 14748
		public String toString() {
			return "PREMATRICULADOS FICHA TECNICA";
		}
	},
	RECARGACACHE("RECARGA CACHE", "Proceso para la recarga de cache") {
		public String toString() {
			return "RECARGA CACHE";
		}
	},
	EMISIONJPROF("EMISIONJPROF", "Servicio de emisión de justificantes profesionales") {
		public String toString() {
			return "EMISIONJPROF";
		}
	},
	SANTANDER("IMPORTACION DATOS SENSIBLES", "Proceso para la importacion de Santander Consumer") {
		public String toString() {
			return "IMPORTACION DATOS SENSIBLES";
		}
	},
	VERIFICACIONJPROF("VERIFICACIONJPROF", "Servicio de verificacion de justificantes profesionales") {
		public String toString() {
			return "VERIFICACIONJPROF";
		}
	},
	VERIFICACIONJPROFSEGA("VERIFIJPROF_SEGA", "Servicio de verificacion de justificantes profesionales") {
		public String toString() {
			return "VERIFIJPROF_SEGA";
		}
	},
	EMISIONTASAETIQUETAS("EMISIONTASAETIQUETAS", "Proceso para la generación de tasas de etiquetas") {
		public String toString() {
			return "EMISIONTASAETIQUETAS";
		}
	},
	CONSULTA_TARJETA_EITV("CONSULTA_EITV", "Proceso para la consulta de trajetas EITV") {
		public String toString() {
			return "CONSULTA_EITV";
		}
	},
	CONSULTA_TARJETA_EITV_SEGA("CONSULTA_EITV_SEGA", "Proceso para la consulta de trajetas EITV") {
		public String toString() {
			return "CONSULTA_EITV_SEGA";
		}
	},
	ESTADISTICAS_TRANS_N("ESTADISTICAS_TRANS_N", "Proceso para la generacion de las estadisticas de tramites de CTIT Nuevo") {
		public String toString() {
			return "ESTADISTICAS_TRANS_N";
		}
	},
	NOTIFICACIONES_SS("NOTIFICACIONES_SS", "Proceso para descargar las notificaciones de la Seguridad Social") {
		public String toString() {
			return "NOTIFICACIONES_SS";
		}
	},
	SS_ENVIO_EMAIL("SS_ENVIO_EMAIL", "Notificaciones y envio email Seg.Social") {
		public String toString() {
			return "SS_ENVIO_EMAIL";
		}
	},
	MODELO_600_601("MODELO_600_601", "Proceso para presentar los formulario 600/601 de la Comunidad de Madrid") {
		public String toString() {
			return "MODELO_600_601";
		}
	},
	CONSULTABTV("CONSULTABTV", "Proceso para la comprobación ConsultaBTV") {
		public String toString() {
			return "CONSULTABTV";
		}
	},
	CONSULTABTV_SEGA("CONSULTABTVSEGA", "Proceso para la comprobación ConsultaBTV") {
		public String toString() {
			return "CONSULTABTVSEGA";
		}
	},
	BTV("BTV", "Proceso para la tramitación telemática de las bajas") {
		public String toString() {
			return "BTV";
		}
	},
	BTV_SEGA("BTVSEGA", "Proceso para la tramitación telemática de las bajas") {
		public String toString() {
			return "BTVSEGA";
		}
	},
	LECTURA_CORREO_LIBERACION("CORREOSANTANDER", "Lectura Correo Liberacion Bastidores") {
		public String toString() {
			return "CORREOSANTANDER";
		}
	},
	RECARGA_STATUS_OEGAM("RECARGAESTATUSOEGAM", "Recarga Status Oegam") {
		public String toString() {
			return "RECARGAESTATUSOEGAM";
		}
	},
	CONSULTA_TESTRA("CONSULTATESTRA", "Consulta Testra") {
		public String toString() {
			return "CONSULTATESTRA";
		}
	},
	PEGATINAS("PEGATINAS", "Proceso para la gestion de stock de pegatinas") {
		public String toString() {
			return "PEGATINAS";
		}
	},
	MATERIALES("MATERIALES", "Proceso para la gestion de stock de materiales") {
		public String toString() {
			return "MATERIALES";
		}
	},
	PEGATINAS_NOTIFICAR("PEGATINAS_NOTIFICAR", "Proceso para la notificacion diaria") {
		public String toString() {
			return "PEGATINAS_NOTIFICAR";
		}
	},
	MATERIALES_NOTIFICAR("MATERIALES_NOTIFICAR", "Proceso para la notificacion diaria de materiales") {
		public String toString() {
			return "MATERIALES_NOTIFICAR";
		}
	},
	STOCK_SEMANAL("STOCK_SEMANAL", "Servicio de informar stock y impresión de materiales") {
		public String toString() {
			return "STOCK_SEMANAL";
		}
	},
	STOCK_NOTIFICAR("STOCK_NOTIFICAR", "Proceso para la notificacion de estadisticas diaria de consumo materiales") {
		public String toString() {
			return "STOCK_NOTIFICAR";
		}
	},
	CONSULTA_DEV("CONSULTA_DEV", "Consulta Dev") {
		public String toString() {
			return "CONSULTA_DEV";
		}
	},
	CONSULTA_PERSONA_ATEX5("CONSULTA_PERS_ATEX5", "Consulta Persona Atex5") {
		public String toString() {
			return "CONSULTA_PERS_ATEX5";
		}
	},
	CONSULTA_VEHICULO_ATEX5("CONSULTA_VEH_ATEX5", "Consulta Vehiculo Atex5") {
		public String toString() {
			return "CONSULTA_VEH_ATEX5";
		}
	},
	JUSTIFICANTE_PROFESIONAL("JUSTIFICANTE_PROF", "Proceso Justificante Profesional") {
		public String toString() {
			return "JUSTIFICANTE_PROF";
		}
	},
	MATW_SEGA("MATW_SEGA", "Proceso Matw Sega") {
		public String toString() {
			return "MATW_SEGA";
		}
	},
	CHECK_CTIT_SEGA("CHECK_CTIT_SEGA", "Proceso CheckCTIT Sega") {
		public String toString() {
			return "CHECK_CTIT_SEGA";
		}
	},
	FULL_CTIT_SEGA("FULL_CTIT_SEGA", "Proceso FullCTIT Sega") {
		public String toString() {
			return "FULL_CTIT_SEGA";
		}
	},
	TRADE_CTIT_SEGA("TRADE_CTIT_SEGA", "Proceso TradeCTIT Sega") {
		public String toString() {
			return "TRADE_CTIT_SEGA";
		}
	},
	NOTIFICACION_CTIT_SEGA("NOTIF_CTIT_SEGA", "Proceso NotificacionCTIT Sega") {
		public String toString() {
			return "NOTIFICACION_CTIT_SEGA";
		}
	},
	CONSULTA_ESTADISTICA_LEGALIZACION("CONSULTA_ESTADISTICA_LEGALIZACION", "Consulta Estadistica Legalizacion") {
		public String toString() {
			return "CONSULTA_ESTADISTICA_LEGALIZACION";
		}
	},
	ERROR_SERVICIO("ERROR_SERVICIO", "Proceso mensaje error servicio") {
		public String toString() {
			return "ERROR_SERVICIO";
		}
	},
	DSTV("DSTV", "Solicitud de distintivos") {
		public String toString() {
			return "DSTV";
		}
	},
	IMP_DSTV("IMP_DSTV", "Impresion de distintivo") {
		public String toString() {
			return "IMP_DSTV";
		}
	},
	IMP_PRM_MATW("IMP_PRM_MATW", "Impresion de Permisos de MATW") {
		public String toString() {
			return "IMP_PRM_MATW";
		}
	},
	IMP_PRM_CTIT("IMP_PRM_CTIT", "Impresion de Permisos de CTIT") {
		public String toString() {
			return "IMP_PRM_CTIT";
		}
	},
	IMP_FICHA("IMP_FICHA", "Impresion de Fichas técnicas") {
		public String toString() {
			return "IMP_FICHA";
		}
	},
	ARRENDATARIOS("ARRENDATARIOS", "Proceso para alta y modificación de arrendatarios") {
		public String toString() {
			return "ARRENDATARIOS";
		}
	},
	CONDUCTOR_HABITUAL("CONDUCTOR_HABITUAL", "Proceso para alta y modificación de conductores habituales") {
		public String toString() {
			return "CONDUCTOR_HABITUAL";
		}
	},
	EXCEL_620("EXCEL_620", "Proceso para generar las liquidaciones 620") {
		public String toString() {
			return "EXCEL_620";
		}
	},
	IMPRESION_DOC_NOCHE("IMPRESION_DOC_NOCHE", "Proceso para imprimir los distintivos, permisos y fichas por la noche") {
		public String toString() {
			return "IMPRESION_DOC_NOCHE";
		}
	},
	GESTION_COLAS_IMPR("GESTION_COLAS_IMPR", "Proceso para la Gestión de colas para IMPR") {
		public String toString() {
			return "GESTION_COLAS_IMPR";
		}
	},
	IMPR_DEMANDA("IMPR_DEMANDA", "Proceso IMPR Demanda") {
		public String toString() {
			return "IMPR_DEMANDA";
		}
	},
	IMPR_NOCTURNO("IMPR_NOCTURNO", "Proceso IMPR Nocturno") {
		public String toString() {
			return "IMPR_NOCTURNO";
		}
	},
	EXCEL_IMPR_KO("EXCEL_IMPR_KO", "Proceso para la generacion del excel con los KO de IMPR") {
		public String toString() {
			return "EXCEL_IMPR_KO";
		}
	},
	INCIDENCIAS_BTV("INCIDENCIAS_BTV", "Proceso de Incidencias de BTV") {
		public String toString() {
			return "INCIDENCIAS_BTV";
		}
	},
	INCD_BTV_MADRID("INCD_BTV_MADRID", "Proceso de Incidencias de BTV Madrid") {
		public String toString() {
			return "INCD_BTV_MADRID";
		}
	},
	INCD_BTV_ALCORCON("INCD_BTV_ALCORCON", "Proceso de Incidencias de BTV Alcorcon") {
		public String toString() {
			return "INCD_BTV_ALCORCON";
		}
	},
	INCD_BTV_ALCALA("INCD_BTV_ALCALA", "Proceso de Incidencias de BTV Alcala") {
		public String toString() {
			return "INCD_BTV_ALCALA";
		}
	},
	INCD_BTV_AVILA("INCD_BTV_AVILA", "Proceso de Incidencias de BTV Avila") {
		public String toString() {
			return "INCD_BTV_AVILA";
		}
	},
	INCD_BTV_CIUDAD_REAL("INCD_BTV_CIUDAD_REAL", "Proceso de Incidencias de BTV Ciudad Real") {
		public String toString() {
			return "INCD_BTV_CIUDAD_REAL";
		}
	},
	INCD_BTV_CUENCA("INCD_BTV_CUENCA", "Proceso de Incidencias de BTV Cuenca") {
		public String toString() {
			return "INCD_BTV_CUENCA";
		}
	},
	INCD_BTV_SEGOVIA("INCD_BTV_SEGOVIA", "Proceso de Incidencias de BTV Segovia") {
		public String toString() {
			return "INCD_BTV_SEGOVIA";
		}
	},
	INCD_BTV_GUADALAJARA("INCD_BTV_GUADALAJARA", "Proceso de Incidencias de BTV Guadalajara") {
		public String toString() {
			return "INCD_BTV_GUADALAJARA";
		}
	},
	ENVIO_STOCK_PRM_DST("ENVIO_STOCK_PRM_DST", "Envío Stock Permisos y Distintivos") {
		public String toString() {
			return "ENVIO_STOCK_PRM_DST";
		}
	},
	JUSTIFICANTES_NO_FINALIZADOS("JUSTIF_NO_FIN", "Justificantes No Finalizados") {
		public String toString() {
			return "JUSTIF_NO_FIN";
		}
	},
	GESTION_CIRCULARES("GESTION_CIRCULARES", "Circulares Oegam") {
		public String toString() {
			return "GESTION_CIRCULARES";
		}
	},
	GENERAR_DOC_BASE("GEN_DOC_BASE", "Generar Doc.Base") {
		public String toString() {
			return "GEN_DOC_BASE";
		}
	},
	DOC_BASE_NOCTURNO("DOC_BASE_NOCTURNO", "Generar Doc.Base Nocturno") {
		public String toString() {
			return "DOC_BASE_NOCTURNO";
		}
	},
	IMPRIMIR_DOC_BASE("IMP_DOC_BASE", "Imprimir Doc.Base") {
		public String toString() {
			return "IMP_DOC_BASE";
		}
	},
	INTEVE_COMPLETO_WS("INTEVE_COMPLETO_WS", "Proceso InteveCompletoWS") {
		public String toString() {
			return "INTEVE_COMPLETO_WS";
		}
	},
	IMP_PRESENTACION_TELEMATICA("IMP_PRES_TELEMATICA", "Impresión Presentación Telemática") {
		public String toString() {
			return "IMP_PRESENTACION_TELEMATICA";
		}
	},
	IMP_PDF_417("IMP_PDF_417", "Impresión PDF 417") {
		public String toString() {
			return "IMP_PDF_417";
		}
	},
	IMP_BORRADOR_PDF_417("IMP_BORRADOR_PDF_417", "Impresión Borrador PDF 417") {
		public String toString() {
			return "IMP_BORRADOR_PDF_417";
		}
	},
	IMP_RELACION_MATRICULAS("IMP_REL_MATRICULAS", "Impresión Relacion Matriculas") {
		public String toString() {
			return "IMP_RELACION_MATRICULAS";
		}
	},
	IMP_MANDATO_GENERICO("IMP_MANDATO_GENERICO", "Impresión Mandato Generico") {
		public String toString() {
			return "IMP_MANDATO_GENERICO";
		}
	},
	IMP_MANDATO_ESPECIFICO("IMP_MANDATO_ESPECIF", "Impresión Mandato Especifico") {
		public String toString() {
			return "IMP_MANDATO_ESPECIFICO";
		}
	},
	IMP_MODELO_430("IMP_MODELO_430", "Impresión Modelo 430") {
		public String toString() {
			return "IMP_MODELO_430";
		}
	},
	IMP_DECLARACIONES("IMP_DECLARACIONES", "Impresión Declaraciones") {
		public String toString() {
			return "IMP_DECLARACIONES";
		}
	},
	IMP_SOLICITUDES("IMP_SOLICITUDES", "Impresión Solicitudes") {
		public String toString() {
			return "IMP_SOLICITUDES";
		}
	},
	LIC_REGISTRAR_SOLICITUD("LIC_REGISTRAR_SOL", "Licencias Registrar Solicitud") {
		public String toString() {
			return "LIC_REGISTRAR_SOL";
		}
	},
	LIC_ENVIAR_SOLICITUD("LIC_ENVIAR_SOL", "Licencias Enviar Solicitud") {
		public String toString() {
			return "LIC_ENVIAR_SOL";
		}
	},
	LIC_ENVIAR_DOCUMENTACION("LIC_ENVIAR_DOCU", "Licencias Enviar Documentacion") {
		public String toString() {
			return "LIC_ENVIAR_DOCU";
		}
	},
	LIC_VALIDAR_SOLICITUD("LIC_VALIDAR_SOL", "Licencias Validar Solicitud") {
		public String toString() {
			return "LIC_VALIDAR_SOL";
		}
	},
	ENVIO_PERMISO_INTER("ENVIO_PERMISO_INTER", "Envio Permiso Internacional") {
		public String toString() {
			return "ENVIO_PERMISO_INTER";
		}
	},
	ENVIO_DUPL_PERM_COND("ENVIO_DUPL_PERM_COND", "Envio Duplicado Permiso Conducir") {
		public String toString() {
			return "ENVIO_DUPL_PERM_COND";
		}
	},
	GESTION_INTERGA("GESTION_INTERGA", "Gestion Tramites Interga") {
		public String toString() {
			return "GESTION_INTERGA";
		}
	},
	GEN_EXCEL_KO_IMPR("GEN_EXCEL_KO_IMPR", "Generar Excel KO Demanda") {
		public String toString() {
			return "GEN_EXCEL_KO_IMPR";
		}
	},
	GEN_EXCEL_KO_IMPR_NOCTURNO("GEN_KO_IMPR_NOCTURNO", "Generar Excel KO Nocturno") {
		public String toString() {
			return "GEN_KO_IMPR_NOCTURNO";
		}
	},
	GEN_NRE06("GEN_NRE06", "Generar NRE06") {
		public String toString() {
			return "GEN_NRE06";
		}
	},
	ENVIO_RESUMEN_NRE06("ENVIO_RESUMEN_NRE06", "Envio resumen NRE06") {
		public String toString() {
			return "ENVIO_RESUMEN_NRE06";
		}
	},
	IMP_NRE("IMP_NRE", "Impresion Txt NRE") {
		public String toString() {
			return "IMP_NRE";
		}
	};

	private ProcesosEnum(String nombreEnum, String valorEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	private String valorEnum;
	private String nombreEnum;

	public String getNombreEnum() {
		return nombreEnum;
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public static ProcesosEnum convertir(String valorEnum) {
		for (ProcesosEnum element : ProcesosEnum.values()) {
			if (element.valorEnum.equals(valorEnum)) {
				return element;
			}
		}
		return null;
	}

	public static ProcesosEnum convertirPorNombreEnum(String nombreEnum) {
		for (ProcesosEnum element : ProcesosEnum.values()) {
			if (element.nombreEnum.equals(nombreEnum)) {
				return element;
			}
		}
		return null;
	}
}
