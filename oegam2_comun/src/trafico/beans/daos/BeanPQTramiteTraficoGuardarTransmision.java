package trafico.beans.daos;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class BeanPQTramiteTraficoGuardarTransmision extends BeanPQGeneral {
	private String P_ELECTRONICA;
	// Valores propios del trámite general
	private BigDecimal P_ID_USUARIO;
	private BigDecimal P_NUM_EXPEDIENTE;
	private BigDecimal P_ID_CONTRATO;
	private BigDecimal  P_ID_CONTRATO_SESSION;
	private String P_NUM_COLEGIADO;
	private BigDecimal P_ID_VEHICULO;
	private String P_CODIGO_TASA;
	private String P_TIPO_TASA;
	private BigDecimal P_ESTADO;
	private String P_REF_PROPIA;
	private String P_TIPO_TRAMITE;
	private Timestamp P_FECHA_ALTA;
	private Timestamp P_FECHA_PRESENTACION;
	private Timestamp P_FECHA_ULT_MODIF;
	private Timestamp P_FECHA_IMPRESION;
	private String P_JEFATURA_PROVINCIAL;
	private String P_ANOTACIONES;
	private String P_RENTING;
	private String P_CAMBIO_DOMICILIO;
	private String P_IEDTM;
	private String P_MODELO_IEDTM;
	private Timestamp P_FECHA_IEDTM;
	private String P_N_REG_IEDTM;
	private String P_FINANCIERA_IEDTM;
	private String P_EXENTO_IEDTM;
	private String P_NO_SUJECION_IEDTM;
	//Mantis 25415
	private BigDecimal P_VALOR_REAL;
	private String P_CEM;
	private String P_RESPUESTA;
	private String P_RES_CHECK_CTIT;
	//Valores propios de la Transmisión
	private String P_MODO_ADJUDICACION;
	private String P_TIPO_TRANSFERENCIA;
	private String P_ACEPTACION_RESPONS_ITV;
	private String P_CAMBIO_SERVICIO;
	private BigDecimal P_ESTADO_620;
	private String P_MODELO_ITP;
	private String P_ID_PROVINCIA;
	private String P_OFICINA_LIQUIDADORA;
	private String P_FUNDAMENTO_EXENCION;
	private String P_EXENTO_ITP;
	private String P_NO_SUJETO_ITP;
	private String P_FUNDAMENTO_NO_SUJETO_ITP;
	private Timestamp P_FECHA_DEVENGO_ITP;
	private BigDecimal P_P_REDUCCION_ANUAL;
	private BigDecimal P_VALOR_DECLARADO;
	private BigDecimal P_P_ADQUISICION;
	private BigDecimal P_BASE_IMPONIBLE;
	private BigDecimal P_TIPO_GRAVAMEN;
	private BigDecimal P_CUOTA_TRIBUTARIA;
	private String P_CODIGO_TASA_INF;
	private String P_CODIGO_TASA_CAMSER;
	private String P_IMPR_PERMISO_CIRCU;
	private String P_CONSENTIMIENTO;
	private String P_CONTRATO_FACTURA;
	private String P_FACTURA;
	private String P_ACTA_SUBASTA;
	private String P_SENTENCIA_JUDICIAL;
	private String P_ACREDITA_HERENCIA_DONACION;
	private String P_CET_ITP;
	private String P_NUM_AUTO_ITP;
	private String P_MODELO_ISD;
	private String P_NUM_AUTO_ISD;
	private String P_EXENCION_ISD;
	private String P_NO_SUJETO_ISD;
	private String P_DUA;
	private String P_ALTA_IVTM;
	private String P_EXENTO_CEM;
	private String P_CEMA;
	private String P_ID_REDUCCION_05;
	private String P_ID_NO_SUJECCION_06;
	private String P_ID_PROVINCIA_CET;
	private String P_ID_PROVINCIA_CEM;
	private BigDecimal P_NUM_TITULARES;
	private String P_CONSENTIMIENTO_CAMBIO;
	private BigDecimal P_ID_TIPO_CREACION;
	private BigDecimal P_SIMULTANEA;

	private Timestamp P_FECHA_FACTURA;
	private Timestamp P_FECHA_CONTRATO;

	private String P_CHECK_VALOR_MANUAL_620;

	private String P_TIPO_MOTOR;
	private String P_SUBASTA;
	private String P_TIPO_REDUCCION;
	private BigDecimal P_REDUCCION_PORCENTAJE;
	private String P_PROCEDENCIA_620;

	//DVV
//	private BigDecimal P_CAMBIO_SOCIETARIO;
//	private BigDecimal P_MODIFICACION_NO_CONSTANTE;
	private BigDecimal P_ES_SINIESTRO;
//	private BigDecimal P_TIENE_CARGA_FINANCIERA;
	private String P_ACREDITACION_PAGO;

	//CONSTRUCTORES
	public BeanPQTramiteTraficoGuardarTransmision() {
	}

	//SETTERS & GETTERS

	public String getP_ELECTRONICA() {
		return P_ELECTRONICA;
	}

	public void setP_ELECTRONICA(String pELECTRONICA) {
		P_ELECTRONICA = pELECTRONICA;
	}

	public String getP_TIPO_TASA() {
		return P_TIPO_TASA;
	}

	public void setP_TIPO_TASA(String pTIPOTASA) {
		P_TIPO_TASA = pTIPOTASA;
	}

	public String getP_ID_REDUCCION_05() {
		return P_ID_REDUCCION_05;
	}

	public void setP_ID_REDUCCION_05(String pIDREDUCCION_05) {
		P_ID_REDUCCION_05 = pIDREDUCCION_05;
	}

	public String getP_ID_NO_SUJECCION_06() {
		return P_ID_NO_SUJECCION_06;
	}

	public void setP_ID_NO_SUJECCION_06(String pIDNOSUJECCION_06) {
		P_ID_NO_SUJECCION_06 = pIDNOSUJECCION_06;
	}

	public BigDecimal getP_ID_USUARIO() {
		return P_ID_USUARIO;
	}

	public void setP_ID_USUARIO(BigDecimal pIDUSUARIO) {
		P_ID_USUARIO = pIDUSUARIO;
	}

	public BigDecimal getP_NUM_EXPEDIENTE() {
		return P_NUM_EXPEDIENTE;
	}

	public void setP_NUM_EXPEDIENTE(BigDecimal pNUMEXPEDIENTE) {
		P_NUM_EXPEDIENTE = pNUMEXPEDIENTE;
	}

	public BigDecimal getP_ID_CONTRATO() {
		return P_ID_CONTRATO;
	}

	public void setP_ID_CONTRATO(BigDecimal pIDCONTRATO) {
		P_ID_CONTRATO = pIDCONTRATO;
	}

	public BigDecimal getP_ID_CONTRATO_SESSION() {
		return P_ID_CONTRATO_SESSION;
	}

	public void setP_ID_CONTRATO_SESSION(BigDecimal pIDCONTRATOSESSION) {
		P_ID_CONTRATO_SESSION = pIDCONTRATOSESSION;
	}

	public String getP_NUM_COLEGIADO() {
		return P_NUM_COLEGIADO;
	}

	public void setP_NUM_COLEGIADO(String pNUMCOLEGIADO) {
		P_NUM_COLEGIADO = pNUMCOLEGIADO;
	}

	public BigDecimal getP_ID_VEHICULO() {
		return P_ID_VEHICULO;
	}

	public void setP_ID_VEHICULO(BigDecimal pIDVEHICULO) {
		P_ID_VEHICULO = pIDVEHICULO;
	}

	public String getP_CODIGO_TASA() {
		return P_CODIGO_TASA;
	}

	public void setP_CODIGO_TASA(String pCODIGOTASA) {
		P_CODIGO_TASA = pCODIGOTASA;
	}

	public BigDecimal getP_ESTADO() {
		return P_ESTADO;
	}

	public void setP_ESTADO(BigDecimal pESTADO) {
		P_ESTADO = pESTADO;
	}

	public String getP_REF_PROPIA() {
		return P_REF_PROPIA;
	}

	public void setP_REF_PROPIA(String pREFPROPIA) {
		P_REF_PROPIA = pREFPROPIA;
	}
	
	public String getP_TIPO_TRAMITE() {
		return P_TIPO_TRAMITE;
	}

	public void setP_TIPO_TRAMITE(String pTIPOTRAMITE) {
		P_TIPO_TRAMITE = pTIPOTRAMITE;
	}

	public Timestamp getP_FECHA_ALTA() {
		return P_FECHA_ALTA;
	}

	public void setP_FECHA_ALTA(Timestamp pFECHAALTA) {
		P_FECHA_ALTA = pFECHAALTA;
	}

	public Timestamp getP_FECHA_PRESENTACION() {
		return P_FECHA_PRESENTACION;
	}

	public void setP_FECHA_PRESENTACION(Timestamp pFECHAPRESENTACION) {
		P_FECHA_PRESENTACION = pFECHAPRESENTACION;
	}

	public Timestamp getP_FECHA_ULT_MODIF() {
		return P_FECHA_ULT_MODIF;
	}

	public void setP_FECHA_ULT_MODIF(Timestamp pFECHAULTMODIF) {
		P_FECHA_ULT_MODIF = pFECHAULTMODIF;
	}

	public Timestamp getP_FECHA_IMPRESION() {
		return P_FECHA_IMPRESION;
	}

	public void setP_FECHA_IMPRESION(Timestamp pFECHAIMPRESION) {
		P_FECHA_IMPRESION = pFECHAIMPRESION;
	}

	public String getP_JEFATURA_PROVINCIAL() {
		return P_JEFATURA_PROVINCIAL;
	}

	public void setP_JEFATURA_PROVINCIAL(String pJEFATURA_PROVINCIAL) {
		P_JEFATURA_PROVINCIAL = pJEFATURA_PROVINCIAL;
	}

	public String getP_ANOTACIONES() {
		return P_ANOTACIONES;
	}

	public void setP_ANOTACIONES(String pANOTACIONES) {
		P_ANOTACIONES = pANOTACIONES;
	}

	public String getP_RENTING() {
		return P_RENTING;
	}

	public void setP_RENTING(String pRENTING) {
		P_RENTING = pRENTING;
	}

	public String getP_CAMBIO_DOMICILIO() {
		return P_CAMBIO_DOMICILIO;
	}

	public void setP_CAMBIO_DOMICILIO(String pCAMBIODOMICILIO) {
		P_CAMBIO_DOMICILIO = pCAMBIODOMICILIO;
	}

	public String getP_IEDTM() {
		return P_IEDTM;
	}

	public void setP_IEDTM(String pIEDTM) {
		P_IEDTM = pIEDTM;
	}

	public String getP_MODELO_IEDTM() {
		return P_MODELO_IEDTM;
	}

	public void setP_MODELO_IEDTM(String pMODELOIEDTM) {
		P_MODELO_IEDTM = pMODELOIEDTM;
	}

	public Timestamp getP_FECHA_IEDTM() {
		return P_FECHA_IEDTM;
	}

	public void setP_FECHA_IEDTM(Timestamp pFECHAIEDTM) {
		P_FECHA_IEDTM = pFECHAIEDTM;
	}

	public String getP_N_REG_IEDTM() {
		return P_N_REG_IEDTM;
	}

	public void setP_N_REG_IEDTM(String pNREGIEDTM) {
		P_N_REG_IEDTM = pNREGIEDTM;
	}

	public String getP_FINANCIERA_IEDTM() {
		return P_FINANCIERA_IEDTM;
	}

	public void setP_FINANCIERA_IEDTM(String pFINANCIERAIEDTM) {
		P_FINANCIERA_IEDTM = pFINANCIERAIEDTM;
	}

	public String getP_EXENTO_IEDTM() {
		return P_EXENTO_IEDTM;
	}

	public void setP_EXENTO_IEDTM(String pEXENTOIEDTM) {
		P_EXENTO_IEDTM = pEXENTOIEDTM;
	}

	public String getP_NO_SUJECION_IEDTM() {
		return P_NO_SUJECION_IEDTM;
	}

	public void setP_NO_SUJECION_IEDTM(String pNOSUJECIONIEDTM) {
		P_NO_SUJECION_IEDTM = pNOSUJECIONIEDTM;
	}

	public String getP_CEM() {
		return P_CEM;
	}

	public void setP_CEM(String pCEM) {
		P_CEM = pCEM;
	}

	public String getP_RESPUESTA() {
		return P_RESPUESTA;
	}

	public void setP_RESPUESTA(String pRESPUESTA) {
		P_RESPUESTA = pRESPUESTA;
	}

	public String getP_RES_CHECK_CTIT() {
		return P_RES_CHECK_CTIT;
	}

	public void setP_RES_CHECK_CTIT(String pRESCHECKCTIT) {
		P_RES_CHECK_CTIT = pRESCHECKCTIT;
	}

	public String getP_MODO_ADJUDICACION() {
		return P_MODO_ADJUDICACION;
	}

	public void setP_MODO_ADJUDICACION(String pMODOADJUDICACION) {
		P_MODO_ADJUDICACION = pMODOADJUDICACION;
	}

	public String getP_TIPO_TRANSFERENCIA() {
		return P_TIPO_TRANSFERENCIA;
	}

	public void setP_TIPO_TRANSFERENCIA(String pTIPOTRANSFERENCIA) {
		P_TIPO_TRANSFERENCIA = pTIPOTRANSFERENCIA;
	}

	public String getP_ACEPTACION_RESPONS_ITV() {
		return P_ACEPTACION_RESPONS_ITV;
	}

	public void setP_ACEPTACION_RESPONS_ITV(String pACEPTACIONRESPONSITV) {
		P_ACEPTACION_RESPONS_ITV = pACEPTACIONRESPONSITV;
	}

	// incidencia 3038 P_CONSENTIMIENTO_CAMBIO
	public String getP_CONSENTIMIENTO_CAMBIO() {
		return P_CONSENTIMIENTO_CAMBIO;
	}

	public void setP_CONSENTIMIENTO_CAMBIO(String pCONSENTIMIENTOCAMBIO) {
		P_CONSENTIMIENTO_CAMBIO = pCONSENTIMIENTOCAMBIO;
	}
	// Fin 3038

	public String getP_CAMBIO_SERVICIO() {
		return P_CAMBIO_SERVICIO;
	}

	public void setP_CAMBIO_SERVICIO(String pCAMBIOSERVICIO) {
		P_CAMBIO_SERVICIO = pCAMBIOSERVICIO;
	}

	public BigDecimal getP_ESTADO_620() {
		return P_ESTADO_620;
	}

	public void setP_ESTADO_620(BigDecimal pESTADO_620) {
		P_ESTADO_620 = pESTADO_620;
	}

	public String getP_MODELO_ITP() {
		return P_MODELO_ITP;
	}

	public void setP_MODELO_ITP(String pMODELOITP) {
		P_MODELO_ITP = pMODELOITP;
	}

	public String getP_ID_PROVINCIA() {
		return P_ID_PROVINCIA;
	}

	public void setP_ID_PROVINCIA(String pIDPROVINCIA) {
		P_ID_PROVINCIA = pIDPROVINCIA;
	}

	public String getP_OFICINA_LIQUIDADORA() {
		return P_OFICINA_LIQUIDADORA;
	}

	public void setP_OFICINA_LIQUIDADORA(String pOFICINALIQUIDADORA) {
		P_OFICINA_LIQUIDADORA = pOFICINALIQUIDADORA;
	}

	public String getP_FUNDAMENTO_EXENCION() {
		return P_FUNDAMENTO_EXENCION;
	}

	public void setP_FUNDAMENTO_EXENCION(String pFUNDAMENTOEXENCION) {
		P_FUNDAMENTO_EXENCION = pFUNDAMENTOEXENCION;
	}

	public String getP_EXENTO_ITP() {
		return P_EXENTO_ITP;
	}

	public void setP_EXENTO_ITP(String pEXENTOITP) {
		P_EXENTO_ITP = pEXENTOITP;
	}

	public String getP_NO_SUJETO_ITP() {
		return P_NO_SUJETO_ITP;
	}

	public void setP_NO_SUJETO_ITP(String pNOSUJETOITP) {
		P_NO_SUJETO_ITP = pNOSUJETOITP;
	}

	public BigDecimal getP_P_REDUCCION_ANUAL() {
		return P_P_REDUCCION_ANUAL;
	}

	public void setP_P_REDUCCION_ANUAL(BigDecimal pPREDUCCIONANUAL) {
		P_P_REDUCCION_ANUAL = pPREDUCCIONANUAL;
	}

	public BigDecimal getP_VALOR_DECLARADO() {
		return P_VALOR_DECLARADO;
	}

	public void setP_VALOR_DECLARADO(BigDecimal pVALORDECLARADO) {
		P_VALOR_DECLARADO = pVALORDECLARADO;
	}

	public BigDecimal getP_P_ADQUISICION() {
		return P_P_ADQUISICION;
	}

	public void setP_P_ADQUISICION(BigDecimal pPADQUISICION) {
		P_P_ADQUISICION = pPADQUISICION;
	}

	public BigDecimal getP_BASE_IMPONIBLE() {
		return P_BASE_IMPONIBLE;
	}

	public void setP_BASE_IMPONIBLE(BigDecimal pBASEIMPONIBLE) {
		P_BASE_IMPONIBLE = pBASEIMPONIBLE;
	}

	public BigDecimal getP_TIPO_GRAVAMEN() {
		return P_TIPO_GRAVAMEN;
	}

	public void setP_TIPO_GRAVAMEN(BigDecimal pTIPOGRAVAMEN) {
		P_TIPO_GRAVAMEN = pTIPOGRAVAMEN;
	}

	public BigDecimal getP_CUOTA_TRIBUTARIA() {
		return P_CUOTA_TRIBUTARIA;
	}

	public void setP_CUOTA_TRIBUTARIA(BigDecimal pCUOTATRIBUTARIA) {
		P_CUOTA_TRIBUTARIA = pCUOTATRIBUTARIA;
	}

	public String getP_CODIGO_TASA_INF() {
		return P_CODIGO_TASA_INF;
	}

	public void setP_CODIGO_TASA_INF(String pCODIGOTASAINF) {
		P_CODIGO_TASA_INF = pCODIGOTASAINF;
	}

	public String getP_CODIGO_TASA_CAMSER() {
		return P_CODIGO_TASA_CAMSER;
	}

	public void setP_CODIGO_TASA_CAMSER(String pCODIGOTASACAMSER) {
		P_CODIGO_TASA_CAMSER = pCODIGOTASACAMSER;
	}

	public String getP_IMPR_PERMISO_CIRCU() {
		return P_IMPR_PERMISO_CIRCU;
	}

	public void setP_IMPR_PERMISO_CIRCU(String pIMPRPERMISOCIRCU) {
		P_IMPR_PERMISO_CIRCU = pIMPRPERMISOCIRCU;
	}

	public String getP_CONSENTIMIENTO() {
		return P_CONSENTIMIENTO;
	}

	public void setP_CONSENTIMIENTO(String pCONSENTIMIENTO) {
		P_CONSENTIMIENTO = pCONSENTIMIENTO;
	}

	public String getP_CONTRATO_FACTURA() {
		return P_CONTRATO_FACTURA;
	}

	public void setP_CONTRATO_FACTURA(String pCONTRATOFACTURA) {
		P_CONTRATO_FACTURA = pCONTRATOFACTURA;
	}

	public String getP_FACTURA() {
		return P_FACTURA;
	}

	public void setP_FACTURA(String pFACTURA) {
		P_FACTURA = pFACTURA;
	}

	public String getP_ACTA_SUBASTA() {
		return P_ACTA_SUBASTA;
	}

	public void setP_ACTA_SUBASTA(String pACTASUBASTA) {
		P_ACTA_SUBASTA = pACTASUBASTA;
	}

	public String getP_SENTENCIA_JUDICIAL() {
		return P_SENTENCIA_JUDICIAL;
	}

	public void setP_SENTENCIA_JUDICIAL(String pSENTENCIAJUDICIAL) {
		P_SENTENCIA_JUDICIAL = pSENTENCIAJUDICIAL;
	}

	public String getP_ACREDITA_HERENCIA_DONACION() {
		return P_ACREDITA_HERENCIA_DONACION;
	}

	public void setP_ACREDITA_HERENCIA_DONACION(String pACREDITAHERENCIADONACION) {
		P_ACREDITA_HERENCIA_DONACION = pACREDITAHERENCIADONACION;
	}

	public String getP_CET_ITP() {
		return P_CET_ITP;
	}

	public void setP_CET_ITP(String pCETITP) {
		P_CET_ITP = pCETITP;
	}

	public String getP_NUM_AUTO_ITP() {
		return P_NUM_AUTO_ITP;
	}

	public void setP_NUM_AUTO_ITP(String pNUMAUTOITP) {
		P_NUM_AUTO_ITP = pNUMAUTOITP;
	}

	public String getP_MODELO_ISD() {
		return P_MODELO_ISD;
	}

	public void setP_MODELO_ISD(String pMODELOISD) {
		P_MODELO_ISD = pMODELOISD;
	}

	public String getP_NUM_AUTO_ISD() {
		return P_NUM_AUTO_ISD;
	}

	public void setP_NUM_AUTO_ISD(String pNUMAUTOISD) {
		P_NUM_AUTO_ISD = pNUMAUTOISD;
	}

	public String getP_EXENCION_ISD() {
		return P_EXENCION_ISD;
	}

	public void setP_EXENCION_ISD(String pEXENCIONISD) {
		P_EXENCION_ISD = pEXENCIONISD;
	}

	public String getP_NO_SUJETO_ISD() {
		return P_NO_SUJETO_ISD;
	}

	public void setP_NO_SUJETO_ISD(String pNOSUJETOISD) {
		P_NO_SUJETO_ISD = pNOSUJETOISD;
	}

	public String getP_DUA() {
		return P_DUA;
	}

	public void setP_DUA(String pDUA) {
		P_DUA = pDUA;
	}

	public String getP_ALTA_IVTM() {
		return P_ALTA_IVTM;
	}

	public void setP_ALTA_IVTM(String pALTAIVTM) {
		P_ALTA_IVTM = pALTAIVTM;
	}

	public Timestamp getP_FECHA_DEVENGO_ITP() {
		return P_FECHA_DEVENGO_ITP;
	}

	public void setP_FECHA_DEVENGO_ITP(Timestamp pFECHADEVENGOITP) {
		P_FECHA_DEVENGO_ITP = pFECHADEVENGOITP;
	}

	public String getP_FUNDAMENTO_NO_SUJETO_ITP() {
		return P_FUNDAMENTO_NO_SUJETO_ITP;
	}

	public void setP_FUNDAMENTO_NO_SUJETO_ITP(String pFUNDAMENTONOSUJETOITP) {
		P_FUNDAMENTO_NO_SUJETO_ITP = pFUNDAMENTONOSUJETOITP;
	}

	public String getP_EXENTO_CEM() {
		return P_EXENTO_CEM;
	}

	public void setP_EXENTO_CEM(String pEXENTOCEM) {
		P_EXENTO_CEM = pEXENTOCEM;
	}

	public String getP_CEMA() {
		return P_CEMA;
	}

	public void setP_CEMA(String pCEMA) {
		P_CEMA = pCEMA;
	}

	public String getP_ID_PROVINCIA_CET() {
		return P_ID_PROVINCIA_CET;
	}

	public void setP_ID_PROVINCIA_CET(String pIDPROVINCIACET) {
		P_ID_PROVINCIA_CET = pIDPROVINCIACET;
	}

	public BigDecimal getP_NUM_TITULARES() {
		return P_NUM_TITULARES;
	}

	public void setP_NUM_TITULARES(BigDecimal p_NUM_TITULARES) {
		P_NUM_TITULARES = p_NUM_TITULARES;
	}

	public String getP_ID_PROVINCIA_CEM() {
		return P_ID_PROVINCIA_CEM;
	}

	public void setP_ID_PROVINCIA_CEM(String pIDPROVINCIACEM) {
		P_ID_PROVINCIA_CEM = pIDPROVINCIACEM;
	}

	public BigDecimal getP_ID_TIPO_CREACION() {
		return P_ID_TIPO_CREACION;
	}

	public void setP_ID_TIPO_CREACION(BigDecimal pIDTIPOCREACION) {
		P_ID_TIPO_CREACION = pIDTIPOCREACION;
	}

	public BigDecimal getP_SIMULTANEA() {
		return P_SIMULTANEA;
	}

	public void setP_SIMULTANEA(BigDecimal p_SIMULTANEA) {
		P_SIMULTANEA = p_SIMULTANEA;
	}

	public Timestamp getP_FECHA_FACTURA() {
		return P_FECHA_FACTURA;
	}

	public void setP_FECHA_FACTURA(Timestamp p_FECHA_FACTURA) {
		P_FECHA_FACTURA = p_FECHA_FACTURA;
	}

	public Timestamp getP_FECHA_CONTRATO() {
		return P_FECHA_CONTRATO;
	}

	public void setP_FECHA_CONTRATO(Timestamp p_FECHA_CONTRATO) {
		P_FECHA_CONTRATO = p_FECHA_CONTRATO;
	}
	//Mantis 25415
	public BigDecimal getP_VALOR_REAL() {
		return P_VALOR_REAL;
	}
	//Mantis 25415
	public void setP_VALOR_REAL(BigDecimal p_VALOR_REAL) {
		P_VALOR_REAL = p_VALOR_REAL;
	}

	public String getP_CHECK_VALOR_MANUAL_620() {
		return P_CHECK_VALOR_MANUAL_620;
	}

	public void setP_CHECK_VALOR_MANUAL_620(String p_CHECK_VALOR_MANUAL_620) {
		P_CHECK_VALOR_MANUAL_620 = p_CHECK_VALOR_MANUAL_620;
	}

	public String getP_TIPO_MOTOR() {
		return P_TIPO_MOTOR;
	}

	public void setP_TIPO_MOTOR(String p_TIPO_MOTOR) {
		P_TIPO_MOTOR = p_TIPO_MOTOR;
	}

	public String getP_SUBASTA() {
		return P_SUBASTA;
	}

	public void setP_SUBASTA(String p_SUBASTA) {
		P_SUBASTA = p_SUBASTA;
	}

	public String getP_TIPO_REDUCCION() {
		return P_TIPO_REDUCCION;
	}

	public void setP_TIPO_REDUCCION(String p_REDUCCION_CODIGO) {
		P_TIPO_REDUCCION = p_REDUCCION_CODIGO;
	}

	public BigDecimal getP_REDUCCION_PORCENTAJE() {
		return P_REDUCCION_PORCENTAJE;
	}

	public void setP_REDUCCION_PORCENTAJE(BigDecimal p_REDUCCION_PORCENTAJE) {
		P_REDUCCION_PORCENTAJE = p_REDUCCION_PORCENTAJE;
	}

	public String getP_PROCEDENCIA_620() {
		return P_PROCEDENCIA_620;
	}

	public void setP_PROCEDENCIA_620(String p_PROCEDENCIA_620) {
		P_PROCEDENCIA_620 = p_PROCEDENCIA_620;
	}

//	public BigDecimal getP_CAMBIO_SOCIETARIO() {
//		return P_CAMBIO_SOCIETARIO;
//	}

//	public void setP_CAMBIO_SOCIETARIO(BigDecimal p_CAMBIO_SOCIETARIO) {
//		P_CAMBIO_SOCIETARIO = p_CAMBIO_SOCIETARIO;
//	}

//	public BigDecimal getP_MODIFICACION_NO_CONSTANTE() {
//		return P_MODIFICACION_NO_CONSTANTE;
//	}

//	public void setP_MODIFICACION_NO_CONSTANTE(BigDecimal p_MODIFICACION_NO_CONSTANTE) {
//		P_MODIFICACION_NO_CONSTANTE = p_MODIFICACION_NO_CONSTANTE;
//	}

	public BigDecimal getP_ES_SINIESTRO() {
		return P_ES_SINIESTRO;
	}

	public void setP_ES_SINIESTRO(BigDecimal p_ES_SINIESTRO) {
		P_ES_SINIESTRO = p_ES_SINIESTRO;
	}

	public String getP_ACREDITACION_PAGO() {
		return P_ACREDITACION_PAGO;
	}

	public void setP_ACREDITACION_PAGO(String p_ACREDITACION_PAGO) {
		P_ACREDITACION_PAGO = p_ACREDITACION_PAGO;
	}

//	public BigDecimal getP_TIENE_CARGA_FINANCIERA() {
//		return P_TIENE_CARGA_FINANCIERA;
//	}

//	public void setP_TIENE_CARGA_FINANCIERA(BigDecimal p_TIENE_CARGA_FINANCIERA) {
//		P_TIENE_CARGA_FINANCIERA = p_TIENE_CARGA_FINANCIERA;
//	}

//	public BigDecimal getP_ACREDITACION_PAGO() {
//		return P_ACREDITACION_PAGO;
//	}

//	public void setP_ACREDITACION_PAGO(BigDecimal p_ACREDITACION_PAGO) {
//		P_ACREDITACION_PAGO = p_ACREDITACION_PAGO;
//	}

}