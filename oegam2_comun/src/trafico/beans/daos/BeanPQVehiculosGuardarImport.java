package trafico.beans.daos;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class BeanPQVehiculosGuardarImport extends BeanPQGeneral {
	private BigDecimal P_ID_USUARIO;
	private BigDecimal P_ID_CONTRATO_SESSION;
	private BigDecimal P_NUM_EXPEDIENTE;
	private BigDecimal P_ID_CONTRATO;
	private String P_TIPO_TRAMITE;

	private String P_NUM_COLEGIADO;
	private BigDecimal P_ID_VEHICULO;
	private String P_BASTIDOR;
	private String P_MATRICULA;
	private String P_NIVE;
	private BigDecimal P_CODIGO_MARCA;
	private String P_MODELO;
	private String P_TIPVEHI;
	private String P_CDMARCA;
	private String P_CDMODVEH;
	private Timestamp P_FECDESDE;
	private String P_TIPO_VEHICULO;
	private String P_ID_SERVICIO;
	private String P_ID_SERVICIO_ANTERIOR;
	private Timestamp P_FECHA_MATRICULACION;
	private String P_VEHICULO_AGRICOLA;
	private String P_VEHICULO_TRANSPORTE;
	private String P_ID_COLOR;
	private String P_ID_CARBURANTE;
	private String P_PROCEDENCIA;
	private String P_ID_PROCEDENCIA;
	private String P_ID_LUGAR_ADQUISICION;

	private String P_ID_CRITERIO_CONSTRUCCION;
	private String P_ID_CRITERIO_UTILIZACION;
	private String P_ID_DIRECTIVA_CEE;
	private String P_DIPLOMATICO;
	private BigDecimal P_PLAZAS;
	private String P_CODITV;
	private BigDecimal P_POTENCIA_FISCAL;
	private BigDecimal P_POTENCIA_NETA;
	private BigDecimal P_POTENCIA_PESO;
	private String P_CILINDRADA;
	private String P_CO2;
	private String P_TARA;
	private String P_PESO_MMA;
	private String P_KILOMETRAJE;
	private String P_MTMA_ITV;
	private String P_VERSION;
	private String P_VARIANTE;
	private String P_EXCESO_PESO;
	private String P_TIPO_INDUSTRIA;
	private String P_ID_MOTIVO_ITV;
	private Timestamp P_FECHA_ITV;
	private String P_CLASIFICACION_ITV;
	private String P_ID_TIPO_TARJETA_ITV;
	private String P_CONCEPTO_ITV;
	private String P_ESTACION_ITV;
	private Timestamp P_FECHA_INSPECCION;
	private BigDecimal P_N_PLAZAS_PIE;
	private String P_N_HOMOLOGACION;

	private BigDecimal P_N_RUEDAS;
	private String P_N_SERIE;
	private String P_ID_EPIGRAFE;
	private String P_NIF_INTEGRADOR;
	private String P_VEHI_USADO;
	private String P_MATRI_AYUNTAMIENTO;
	private Timestamp P_LIMITE_MATR_TURIS;
	private Timestamp P_FECHA_PRIM_MATRI;
	private String P_ID_LUGAR_MATRICULACION;
	private BigDecimal P_KM_USO;
	private BigDecimal P_HORAS_USO;
	
	private BigDecimal P_ID_VEHICULO_PREVER;
	private BigDecimal P_N_CILINDROS;
	private String P_CARACTERISTICAS;
	private String P_ANIO_FABRICA; 
	private BigDecimal P_PROVINCIA_PRIMERA_MATRICULA;

	// DATOS DE LA DIRECCIÓN DEL VEHÍCULO
	private BigDecimal P_ID_DIRECCION;
	private String P_ID_PROVINCIA;
	private String P_ID_MUNICIPIO;
	private String P_ID_TIPO_VIA;
	private String P_NOMBRE_VIA;
	private String P_NUMERO;
	private String P_COD_POSTAL;
	private String P_PUEBLO;
	private String P_LETRA;
	private String P_ESCALERA;
	private String P_BLOQUE;
	private String P_PLANTA;
	private String P_PUERTA;
	private BigDecimal P_NUM_LOCAL;
	private BigDecimal P_KM;
	private BigDecimal P_HM;

	// DATOS DEL VEHÍCULO PREVER
	private String P_PREV_MATRICULA;
	private String P_PREV_BASTIDOR;
	private BigDecimal P_PREV_CODIGO_MARCA;
	private String P_PREV_MODELO;
	private String P_PREV_CLASIFICACION_ITV;
	private String P_PREV_TIPO_ITV;
	private String P_PREV_ID_CONSTRUCCION;
	private String P_PREV_ID_UTILIZACION;
	private String P_PREV_ID_TIPO_TARJETA_ITV;

	// Información adicional para las importaciones
	private String P_ID_TIPO_DGT;
	private String P_MUNICIPIO;
	private String P_PUEBLO_LIT;
	private String P_VIA;
	private String P_MARCA_SIN_EDITAR;
	private String P_MARCA;

	// DATOS DEL INTEGRADOR
	private String P_TIPO_PERSONA_INTE;
	private String P_APELLIDO1_RAZON_SOCIAL_INTE;
	private String P_APELLIDO2_INTE;
	private String P_NOMBRE_INTE;
	private String P_TIPO_ITV;

	// Información devuelta
	private String P_INFORMACION;

	// MATE 2.5
	private String P_IMPORTADO;
	private String P_SUBASTADO;
	private String P_FABRICACION;
	private String P_PAIS_IMPORTACION;
	private String P_FABRICANTE;
	private String P_CARROCERIA;
	private BigDecimal P_CONSUMO;
	private BigDecimal P_DIST_EJES;
	private BigDecimal P_VIA_ANT;
	private BigDecimal P_VIA_POS;
	private String P_ALIMENTACION;
	private String P_EMISIONES;
	private String P_ECO_INNOVACION;
	private BigDecimal P_REDUCCION_ECO;
	private String P_CODIGO_ECO;
	private BigDecimal P_MOM;
	private String P_CHECK_FECHA_CADUCIDAD_ITV;
	private Integer P_DIFERENCIA_ANYO;

	// Nuevos Campos Matw
	//private Timestamp P_FECHA_INICIAL_ITV;
	private String P_MATRICULA_ORIGEN;
	//private String P_ID_PROVINCIA_ITV;
	private String P_PAIS_FABRICACION;
	private BigDecimal P_FICHA_TECNICA_RD750;
	private String P_MATRICULA_ORIG_EXTR;
	// VEHÍCULOS MULTIFÁSICOS
	private BigDecimal P_CODIGO_MARCA_BASE;
	private String P_FABRICANTE_BASE;
	private String P_TIPO_BASE; 
	private String P_VARIANTE_BASE;
	private String P_VERSION_BASE;
	private String P_N_HOMOLOGACION_BASE;
	private BigDecimal P_MOM_BASE;
	private String P_CATEGORIA_ELECTRICA;
	private BigDecimal P_AUTONOMIA_ELECTRICA;
	private String P_FECHA_LECTURA_KM;
	private BigDecimal P_DOI_RESPONSABLE_LECTURA_KM;
	//DVV
	private Boolean P_ES_SINIESTRO;
	private BigDecimal P_VELOCIDAD_MAXIMA;

	// --------- GET & SET ---------
	public BigDecimal getP_ID_USUARIO() {
		return P_ID_USUARIO;
	}
	public void setP_ID_USUARIO(BigDecimal pIDUSUARIO) {
		P_ID_USUARIO = pIDUSUARIO;
	}
	public BigDecimal getP_ID_CONTRATO_SESSION() {
		return P_ID_CONTRATO_SESSION;
	}
	public void setP_ID_CONTRATO_SESSION(BigDecimal pIDCONTRATOSESSION) {
		P_ID_CONTRATO_SESSION = pIDCONTRATOSESSION;
	}
	public BigDecimal getP_NUM_EXPEDIENTE() {
		return P_NUM_EXPEDIENTE;
	}
	public BigDecimal getP_POTENCIA_PESO() {
		return P_POTENCIA_PESO;
	}
	public void setP_POTENCIA_PESO(BigDecimal pPOTENCIAPESO) {
		P_POTENCIA_PESO = pPOTENCIAPESO;
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
	public String getP_TIPO_TRAMITE() {
		return P_TIPO_TRAMITE;
	}
	public void setP_TIPO_TRAMITE(String pTIPOTRAMITE) {
		P_TIPO_TRAMITE = pTIPOTRAMITE;
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
	public String getP_BASTIDOR() {
		return P_BASTIDOR;
	}
	public void setP_BASTIDOR(String pBASTIDOR) {
		P_BASTIDOR = pBASTIDOR;
	}
	public String getP_MATRICULA() {
		return P_MATRICULA;
	}
	public void setP_MATRICULA(String pMATRICULA) {
		P_MATRICULA = pMATRICULA;
	}
	public String getP_NIVE() {
		return P_NIVE;
	}
	public void setP_NIVE(String pNIVE) {
		P_NIVE = pNIVE;
	}
	public BigDecimal getP_CODIGO_MARCA() {
		return P_CODIGO_MARCA;
	}
	public void setP_CODIGO_MARCA(BigDecimal pCODIGOMARCA) {
		P_CODIGO_MARCA = pCODIGOMARCA;
	}
	public String getP_MODELO() {
		return P_MODELO;
	}
	public void setP_MODELO(String pMODELO) {
		P_MODELO = pMODELO;
	}
	public String getP_TIPVEHI() {
		return P_TIPVEHI;
	}
	public void setP_TIPVEHI(String pTIPVEHI) {
		P_TIPVEHI = pTIPVEHI;
	}
	public String getP_CDMARCA() {
		return P_CDMARCA;
	}
	public void setP_CDMARCA(String pCDMARCA) {
		P_CDMARCA = pCDMARCA;
	}
	public String getP_CDMODVEH() {
		return P_CDMODVEH;
	}
	public void setP_CDMODVEH(String pCDMODVEH) {
		P_CDMODVEH = pCDMODVEH;
	}
	public Timestamp getP_FECDESDE() {
		return P_FECDESDE;
	}
	public void setP_FECDESDE(Timestamp pFECDESDE) {
		P_FECDESDE = pFECDESDE;
	}
	public String getP_TIPO_VEHICULO() {
		return P_TIPO_VEHICULO;
	}
	public void setP_TIPO_VEHICULO(String pTIPOVEHICULO) {
		P_TIPO_VEHICULO = pTIPOVEHICULO;
	}
	public String getP_ID_SERVICIO() {
		return P_ID_SERVICIO;
	}
	public void setP_ID_SERVICIO(String pIDSERVICIO) {
		P_ID_SERVICIO = pIDSERVICIO;
	}
	public String getP_ID_SERVICIO_ANTERIOR() {
		return P_ID_SERVICIO_ANTERIOR;
	}
	public void setP_ID_SERVICIO_ANTERIOR(String pIDSERVICIOANTERIOR) {
		P_ID_SERVICIO_ANTERIOR = pIDSERVICIOANTERIOR;
	}	
	public Timestamp getP_FECHA_MATRICULACION() {
		return P_FECHA_MATRICULACION;
	}
	public void setP_FECHA_MATRICULACION(Timestamp pFECHAMATRICULACION) {
		P_FECHA_MATRICULACION = pFECHAMATRICULACION;
	}
	public String getP_VEHICULO_AGRICOLA() {
		return P_VEHICULO_AGRICOLA;
	}
	public void setP_VEHICULO_AGRICOLA(String pVEHICULOAGRICOLA) {
		P_VEHICULO_AGRICOLA = pVEHICULOAGRICOLA;
	}
	public String getP_VEHICULO_TRANSPORTE() {
		return P_VEHICULO_TRANSPORTE;
	}
	public void setP_VEHICULO_TRANSPORTE(String pVEHICULOTRANSPORTE) {
		P_VEHICULO_TRANSPORTE = pVEHICULOTRANSPORTE;
	}
	public String getP_ID_COLOR() {
		return P_ID_COLOR;
	}
	public void setP_ID_COLOR(String pIDCOLOR) {
		P_ID_COLOR = pIDCOLOR;
	}
	public String getP_ID_CARBURANTE() {
		return P_ID_CARBURANTE;
	}
	public void setP_ID_CARBURANTE(String pIDCARBURANTE) {
		P_ID_CARBURANTE = pIDCARBURANTE;
	}
	public String getP_PROCEDENCIA() {
		return P_PROCEDENCIA;
	}
	public void setP_PROCEDENCIA(String pPROCEDENCIA) {
		P_PROCEDENCIA = pPROCEDENCIA;
	}
	public String getP_ID_LUGAR_ADQUISICION() {
		return P_ID_LUGAR_ADQUISICION;
	}
	public void setP_ID_LUGAR_ADQUISICION(String pIDLUGARADQUISICION) {
		P_ID_LUGAR_ADQUISICION = pIDLUGARADQUISICION;
	}
	public String getP_ID_CRITERIO_CONSTRUCCION() {
		return P_ID_CRITERIO_CONSTRUCCION;
	}
	public void setP_ID_CRITERIO_CONSTRUCCION(String pIDCRITERIOCONSTRUCCION) {
		P_ID_CRITERIO_CONSTRUCCION = pIDCRITERIOCONSTRUCCION;
	}
	public String getP_ID_CRITERIO_UTILIZACION() {
		return P_ID_CRITERIO_UTILIZACION;
	}
	public void setP_ID_CRITERIO_UTILIZACION(String pIDCRITERIOUTILIZACION) {
		P_ID_CRITERIO_UTILIZACION = pIDCRITERIOUTILIZACION;
	}
	public String getP_ID_DIRECTIVA_CEE() {
		return P_ID_DIRECTIVA_CEE;
	}
	public void setP_ID_DIRECTIVA_CEE(String pIDDIRECTIVACEE) {
		P_ID_DIRECTIVA_CEE = pIDDIRECTIVACEE;
	}
	public String getP_DIPLOMATICO() {
		return P_DIPLOMATICO;
	}
	public void setP_DIPLOMATICO(String pDIPLOMATICO) {
		P_DIPLOMATICO = pDIPLOMATICO;
	}
	public BigDecimal getP_PLAZAS() {
		return P_PLAZAS;
	}
	public void setP_PLAZAS(BigDecimal pPLAZAS) {
		P_PLAZAS = pPLAZAS;
	}
	public String getP_CODITV() {
		return P_CODITV;
	}
	public void setP_CODITV(String pCODITV) {
		P_CODITV = pCODITV;
	}
	public BigDecimal getP_POTENCIA_FISCAL() {
		return P_POTENCIA_FISCAL;
	}
	public void setP_POTENCIA_FISCAL(BigDecimal pPOTENCIAFISCAL) {
		P_POTENCIA_FISCAL = pPOTENCIAFISCAL;
	}
	public BigDecimal getP_POTENCIA_NETA() {
		return P_POTENCIA_NETA;
	}
	public void setP_POTENCIA_NETA(BigDecimal pPOTENCIANETA) {
		P_POTENCIA_NETA = pPOTENCIANETA;
	}
	public String getP_CILINDRADA() {
		return P_CILINDRADA;
	}
	public void setP_CILINDRADA(String pCILINDRADA) {
		P_CILINDRADA = pCILINDRADA;
	}
	public String getP_CO2() {
		return P_CO2;
	}
	public void setP_CO2(String pCO2) {
		P_CO2 = pCO2;
	}
	public String getP_TARA() {
		return P_TARA;
	}
	public void setP_TARA(String pTARA) {
		P_TARA = pTARA;
	}
	public String getP_PESO_MMA() {
		return P_PESO_MMA;
	}
	public void setP_PESO_MMA(String pPESOMMA) {
		P_PESO_MMA = pPESOMMA;
	}
	public String getP_EXCESO_PESO() {
		return P_EXCESO_PESO;
	}
	public void setP_EXCESO_PESO(String pEXCESOPESO) {
		P_EXCESO_PESO = pEXCESOPESO;
	}
	public String getP_TIPO_INDUSTRIA() {
		return P_TIPO_INDUSTRIA;
	}
	public void setP_TIPO_INDUSTRIA(String pTIPOINDUSTRIA) {
		P_TIPO_INDUSTRIA = pTIPOINDUSTRIA;
	}
	public String getP_ID_MOTIVO_ITV() {
		return P_ID_MOTIVO_ITV;
	}
	public void setP_ID_MOTIVO_ITV(String pIDMOTIVOITV) {
		P_ID_MOTIVO_ITV = pIDMOTIVOITV;
	}
	public Timestamp getP_FECHA_ITV() {
		return P_FECHA_ITV;
	}
	public void setP_FECHA_ITV(Timestamp pFECHAITV) {
		P_FECHA_ITV = pFECHAITV;
	}
	public String getP_CLASIFICACION_ITV() {
		return P_CLASIFICACION_ITV;
	}
	public void setP_CLASIFICACION_ITV(String pCLASIFICACIONITV) {
		P_CLASIFICACION_ITV = pCLASIFICACIONITV;
	}
	public String getP_ID_TIPO_TARJETA_ITV() {
		return P_ID_TIPO_TARJETA_ITV;
	}
	public void setP_ID_TIPO_TARJETA_ITV(String pIDTIPOTARJETAITV) {
		P_ID_TIPO_TARJETA_ITV = pIDTIPOTARJETAITV;
	}
	public String getP_CONCEPTO_ITV() {
		return P_CONCEPTO_ITV;
	}
	public void setP_CONCEPTO_ITV(String pCONCEPTOITV) {
		P_CONCEPTO_ITV = pCONCEPTOITV;
	}
	public String getP_ESTACION_ITV() {
		return P_ESTACION_ITV;
	}
	public void setP_ESTACION_ITV(String pESTACIONITV) {
		P_ESTACION_ITV = pESTACIONITV;
	}
	public Timestamp getP_FECHA_INSPECCION() {
		return P_FECHA_INSPECCION;
	}
	public void setP_FECHA_INSPECCION(Timestamp pFECHAINSPECCION) {
		P_FECHA_INSPECCION = pFECHAINSPECCION;
	}
	public BigDecimal getP_N_PLAZAS_PIE() {
		return P_N_PLAZAS_PIE;
	}
	public void setP_N_PLAZAS_PIE(BigDecimal pNPLAZASPIE) {
		P_N_PLAZAS_PIE = pNPLAZASPIE;
	}
	public String getP_N_HOMOLOGACION() {
		return P_N_HOMOLOGACION;
	}
	public void setP_N_HOMOLOGACION(String pNHOMOLOGACION) {
		P_N_HOMOLOGACION = pNHOMOLOGACION;
	}
	public BigDecimal getP_N_RUEDAS() {
		return P_N_RUEDAS;
	}
	public void setP_N_RUEDAS(BigDecimal pNRUEDAS) {
		P_N_RUEDAS = pNRUEDAS;
	}
	public String getP_N_SERIE() {
		return P_N_SERIE;
	}
	public void setP_N_SERIE(String pNSERIE) {
		P_N_SERIE = pNSERIE;
	}
	public String getP_ID_EPIGRAFE() {
		return P_ID_EPIGRAFE;
	}
	public void setP_ID_EPIGRAFE(String pIDEPIGRAFE) {
		P_ID_EPIGRAFE = pIDEPIGRAFE;
	}
	public String getP_NIF_INTEGRADOR() {
		return P_NIF_INTEGRADOR;
	}
	public void setP_NIF_INTEGRADOR(String pNIFINTEGRADOR) {
		P_NIF_INTEGRADOR = pNIFINTEGRADOR;
	}
	public String getP_VEHI_USADO() {
		return P_VEHI_USADO;
	}
	public void setP_VEHI_USADO(String pVEHIUSADO) {
		P_VEHI_USADO = pVEHIUSADO;
	}
	public String getP_MATRI_AYUNTAMIENTO() {
		return P_MATRI_AYUNTAMIENTO;
	}
	public void setP_MATRI_AYUNTAMIENTO(String pMATRIAYUNTAMIENTO) {
		P_MATRI_AYUNTAMIENTO = pMATRIAYUNTAMIENTO;
	}
	public Timestamp getP_LIMITE_MATR_TURIS() {
		return P_LIMITE_MATR_TURIS;
	}
	public void setP_LIMITE_MATR_TURIS(Timestamp pLIMITEMATRTURIS) {
		P_LIMITE_MATR_TURIS = pLIMITEMATRTURIS;
	}
	public Timestamp getP_FECHA_PRIM_MATRI() {
		return P_FECHA_PRIM_MATRI;
	}
	public void setP_FECHA_PRIM_MATRI(Timestamp pFECHAPRIMMATRI) {
		P_FECHA_PRIM_MATRI = pFECHAPRIMMATRI;
	}
	public String getP_ID_LUGAR_MATRICULACION() {
		return P_ID_LUGAR_MATRICULACION;
	}
	public void setP_ID_LUGAR_MATRICULACION(String pIDLUGARMATRICULACION) {
		P_ID_LUGAR_MATRICULACION = pIDLUGARMATRICULACION;
	}
	public BigDecimal getP_KM_USO() {
		return P_KM_USO;
	}
	public void setP_KM_USO(BigDecimal pKMUSO) {
		P_KM_USO = pKMUSO;
	}
	public BigDecimal getP_HORAS_USO() {
		return P_HORAS_USO;
	}
	public void setP_HORAS_USO(BigDecimal pHORASUSO) {
		P_HORAS_USO = pHORASUSO;
	}
	public BigDecimal getP_ID_VEHICULO_PREVER() {
		return P_ID_VEHICULO_PREVER;
	}
	public void setP_ID_VEHICULO_PREVER(BigDecimal pIDVEHICULOPREVER) {
		P_ID_VEHICULO_PREVER = pIDVEHICULOPREVER;
	}
	public BigDecimal getP_N_CILINDROS() {
		return P_N_CILINDROS;
	}
	public void setP_N_CILINDROS(BigDecimal pNCILINDROS) {
		P_N_CILINDROS = pNCILINDROS;
	}
	public String getP_CARACTERISTICAS() {
		return P_CARACTERISTICAS;
	}
	public void setP_CARACTERISTICAS(String pCARACTERISTICAS) {
		P_CARACTERISTICAS = pCARACTERISTICAS;
	}
	public String getP_ANIO_FABRICA() {
		return P_ANIO_FABRICA;
	}
	public void setP_ANIO_FABRICA(String pANIOFABRICA) {
		P_ANIO_FABRICA = pANIOFABRICA;
	}
	public BigDecimal getP_ID_DIRECCION() {
		return P_ID_DIRECCION;
	}
	public void setP_ID_DIRECCION(BigDecimal pIDDIRECCION) {
		P_ID_DIRECCION = pIDDIRECCION;
	}
	public String getP_ID_PROVINCIA() {
		return P_ID_PROVINCIA;
	}
	public void setP_ID_PROVINCIA(String pIDPROVINCIA) {
		P_ID_PROVINCIA = pIDPROVINCIA;
	}
	public String getP_ID_MUNICIPIO() {
		return P_ID_MUNICIPIO;
	}
	public void setP_ID_MUNICIPIO(String pIDMUNICIPIO) {
		P_ID_MUNICIPIO = pIDMUNICIPIO;
	}
	public String getP_ID_TIPO_VIA() {
		return P_ID_TIPO_VIA;
	}
	public void setP_ID_TIPO_VIA(String pIDTIPOVIA) {
		P_ID_TIPO_VIA = pIDTIPOVIA;
	}
	public String getP_NOMBRE_VIA() {
		return P_NOMBRE_VIA;
	}
	public void setP_NOMBRE_VIA(String pNOMBREVIA) {
		P_NOMBRE_VIA = pNOMBREVIA;
	}
	public String getP_NUMERO() {
		return P_NUMERO;
	}
	public void setP_NUMERO(String pNUMERO) {
		P_NUMERO = pNUMERO;
	}
	public String getP_COD_POSTAL() {
		return P_COD_POSTAL;
	}
	public void setP_COD_POSTAL(String pCODPOSTAL) {
		P_COD_POSTAL = pCODPOSTAL;
	}
	public String getP_PUEBLO() {
		return P_PUEBLO;
	}
	public void setP_PUEBLO(String pPUEBLO) {
		P_PUEBLO = pPUEBLO;
	}
	public String getP_LETRA() {
		return P_LETRA;
	}
	public void setP_LETRA(String pLETRA) {
		P_LETRA = pLETRA;
	}
	public String getP_ESCALERA() {
		return P_ESCALERA;
	}
	public void setP_ESCALERA(String pESCALERA) {
		P_ESCALERA = pESCALERA;
	}
	public String getP_BLOQUE() {
		return P_BLOQUE;
	}
	public void setP_BLOQUE(String pBLOQUE) {
		P_BLOQUE = pBLOQUE;
	}
	public String getP_PLANTA() {
		return P_PLANTA;
	}
	public void setP_PLANTA(String pPLANTA) {
		P_PLANTA = pPLANTA;
	}
	public String getP_PUERTA() {
		return P_PUERTA;
	}
	public void setP_PUERTA(String pPUERTA) {
		P_PUERTA = pPUERTA;
	}
	public BigDecimal getP_NUM_LOCAL() {
		return P_NUM_LOCAL;
	}
	public void setP_NUM_LOCAL(BigDecimal pNUMLOCAL) {
		P_NUM_LOCAL = pNUMLOCAL;
	}
	public BigDecimal getP_KM() {
		return P_KM;
	}
	public void setP_KM(BigDecimal pKM) {
		P_KM = pKM;
	}
	public BigDecimal getP_HM() {
		return P_HM;
	}
	public void setP_HM(BigDecimal pHM) {
		P_HM = pHM;
	}	
	public String getP_PREV_MATRICULA() {
		return P_PREV_MATRICULA;
	}
	public void setP_PREV_MATRICULA(String pPREVMATRICULA) {
		P_PREV_MATRICULA = pPREVMATRICULA;
	}
	public String getP_PREV_BASTIDOR() {
		return P_PREV_BASTIDOR;
	}
	public void setP_PREV_BASTIDOR(String pPREVBASTIDOR) {
		P_PREV_BASTIDOR = pPREVBASTIDOR;
	}
	public BigDecimal getP_PREV_CODIGO_MARCA() {
		return P_PREV_CODIGO_MARCA;
	}
	public void setP_PREV_CODIGO_MARCA(BigDecimal pPREVCODIGOMARCA) {
		P_PREV_CODIGO_MARCA = pPREVCODIGOMARCA;
	}
	public String getP_PREV_MODELO() {
		return P_PREV_MODELO;
	}
	public void setP_PREV_MODELO(String pPREVMODELO) {
		P_PREV_MODELO = pPREVMODELO;
	}
	public String getP_PREV_CLASIFICACION_ITV() {
		return P_PREV_CLASIFICACION_ITV;
	}
	public void setP_PREV_CLASIFICACION_ITV(String pPREVCLASIFICACIONITV) {
		P_PREV_CLASIFICACION_ITV = pPREVCLASIFICACIONITV;
	}
	public String getP_ID_TIPO_DGT() {
		return P_ID_TIPO_DGT;
	}
	public void setP_ID_TIPO_DGT(String pIDTIPODGT) {
		P_ID_TIPO_DGT = pIDTIPODGT;
	}
	public String getP_MUNICIPIO() {
		return P_MUNICIPIO;
	}
	public void setP_MUNICIPIO(String pMUNICIPIO) {
		P_MUNICIPIO = pMUNICIPIO;
	}
	public String getP_PUEBLO_LIT() {
		return P_PUEBLO_LIT;
	}
	public void setP_PUEBLO_LIT(String pPUEBLOLIT) {
		P_PUEBLO_LIT = pPUEBLOLIT;
	}
	public String getP_VIA() {
		return P_VIA;
	}
	public void setP_VIA(String pVIA) {
		P_VIA = pVIA;
	}
	public String getP_MARCA_SIN_EDITAR() {
		return P_MARCA_SIN_EDITAR;
	}
	public void setP_MARCA_SIN_EDITAR(String pMARCASINEDITAR) {
		P_MARCA_SIN_EDITAR = pMARCASINEDITAR;
	}
	public String getP_TIPO_PERSONA_INTE() {
		return P_TIPO_PERSONA_INTE;
	}
	public void setP_TIPO_PERSONA_INTE(String pTIPOPERSONAINTE) {
		P_TIPO_PERSONA_INTE = pTIPOPERSONAINTE;
	}
	public String getP_APELLIDO1_RAZON_SOCIAL_INTE() {
		return P_APELLIDO1_RAZON_SOCIAL_INTE;
	}
	public void setP_APELLIDO1_RAZON_SOCIAL_INTE(String pAPELLIDO1RAZONSOCIALINTE) {
		P_APELLIDO1_RAZON_SOCIAL_INTE = pAPELLIDO1RAZONSOCIALINTE;
	}
	public String getP_APELLIDO2_INTE() {
		return P_APELLIDO2_INTE;
	}
	public void setP_APELLIDO2_INTE(String pAPELLIDO2INTE) {
		P_APELLIDO2_INTE = pAPELLIDO2INTE;
	}
	public String getP_NOMBRE_INTE() {
		return P_NOMBRE_INTE;
	}
	public void setP_NOMBRE_INTE(String pNOMBREINTE) {
		P_NOMBRE_INTE = pNOMBREINTE;
	}
	public String getP_INFORMACION() {
		return P_INFORMACION;
	}
	public void setP_INFORMACION(String pInformacion) {
		P_INFORMACION = pInformacion;
	}
	public String getP_MTMA_ITV() {
		return P_MTMA_ITV;
	}
	public void setP_MTMA_ITV(String pMTMAITV) {
		P_MTMA_ITV = pMTMAITV;
	}
	public String getP_VERSION() {
		return P_VERSION;
	}
	public void setP_VERSION(String pVERSION) {
		P_VERSION = pVERSION;
	}
	public String getP_VARIANTE() {
		return P_VARIANTE;
	}
	public void setP_VARIANTE(String pVARIANTE) {
		P_VARIANTE = pVARIANTE;
	}
	public String getP_TIPO_ITV() {
		return P_TIPO_ITV;
	}
	public void setP_TIPO_ITV(String pTIPOITV) {
		P_TIPO_ITV = pTIPOITV;
	}
	public String getP_IMPORTADO() {
		return P_IMPORTADO;
	}
	public void setP_IMPORTADO(String pIMPORTADO) {
		P_IMPORTADO = pIMPORTADO;
	}
	public String getP_SUBASTADO() {
		return P_SUBASTADO;
	}
	public void setP_SUBASTADO(String pSUBASTADO) {
		P_SUBASTADO = pSUBASTADO;
	}
	public String getP_FABRICACION() {
		return P_FABRICACION;
	}
	public void setP_FABRICACION(String pFABRICACION) {
		P_FABRICACION = pFABRICACION;
	}
	public String getP_PAIS_IMPORTACION() {
		return P_PAIS_IMPORTACION;
	}
	public void setP_PAIS_IMPORTACION(String pPAISIMPORTACION) {
		P_PAIS_IMPORTACION = pPAISIMPORTACION;
	}
	public String getP_FABRICANTE() {
		return P_FABRICANTE;
	}
	public void setP_FABRICANTE(String pFABRICANTE) {
		P_FABRICANTE = pFABRICANTE;
	}
	public String getP_CARROCERIA() {
		return P_CARROCERIA;
	}
	public void setP_CARROCERIA(String pCARROCERIA) {
		P_CARROCERIA = pCARROCERIA;
	}
	public BigDecimal getP_CONSUMO() {
		return P_CONSUMO;
	}
	public void setP_CONSUMO(BigDecimal pCONSUMO) {
		P_CONSUMO = pCONSUMO;
	}
	public BigDecimal getP_DIST_EJES() {
		return P_DIST_EJES;
	}
	public void setP_DIST_EJES(BigDecimal pDISTEJES) {
		P_DIST_EJES = pDISTEJES;
	}
	public BigDecimal getP_VIA_ANT() {
		return P_VIA_ANT;
	}
	public void setP_VIA_ANT(BigDecimal pVIAANT) {
		P_VIA_ANT = pVIAANT;
	}
	public BigDecimal getP_VIA_POS() {
		return P_VIA_POS;
	}
	public void setP_VIA_POS(BigDecimal pVIAPOS) {
		P_VIA_POS = pVIAPOS;
	}
	public String getP_ALIMENTACION() {
		return P_ALIMENTACION;
	}
	public void setP_ALIMENTACION(String pALIMENTACION) {
		P_ALIMENTACION = pALIMENTACION;
	}
	public String getP_EMISIONES() {
		return P_EMISIONES;
	}
	public void setP_EMISIONES(String pEMISIONES) {
		P_EMISIONES = pEMISIONES;
	}
	public String getP_ECO_INNOVACION() {
		return P_ECO_INNOVACION;
	}
	public void setP_ECO_INNOVACION(String pECOINNOVACION) {
		P_ECO_INNOVACION = pECOINNOVACION;
	}
	public BigDecimal getP_REDUCCION_ECO() {
		return P_REDUCCION_ECO;
	}
	public void setP_REDUCCION_ECO(BigDecimal pREDUCCIONECO) {
		P_REDUCCION_ECO = pREDUCCIONECO;
	}
	public String getP_CODIGO_ECO() {
		return P_CODIGO_ECO;
	}
	public void setP_CODIGO_ECO(String pCODIGOECO) {
		P_CODIGO_ECO = pCODIGOECO;
	}
	public BigDecimal getP_MOM() {
		return P_MOM;
	}
	public void setP_MOM(BigDecimal pMOM) {
		P_MOM = pMOM;
	}
	public String getP_PREV_TIPO_ITV() {
		return P_PREV_TIPO_ITV;
	}
	public void setP_PREV_TIPO_ITV(String pPREVTIPOITV) {
		P_PREV_TIPO_ITV = pPREVTIPOITV;
	}
	public String getP_PREV_ID_CONSTRUCCION() {
		return P_PREV_ID_CONSTRUCCION;
	}
	public void setP_PREV_ID_CONSTRUCCION(
			String pPREVIDCONSTRUCCION) {
		P_PREV_ID_CONSTRUCCION = pPREVIDCONSTRUCCION;
	}
	public String getP_PREV_ID_UTILIZACION() {
		return P_PREV_ID_UTILIZACION;
	}
	public void setP_PREV_ID_UTILIZACION(String pPREVIDUTILIZACION) {
		P_PREV_ID_UTILIZACION = pPREVIDUTILIZACION;
	}
	public String getP_PREV_ID_TIPO_TARJETA_ITV() {
		return P_PREV_ID_TIPO_TARJETA_ITV;
	}
	public void setP_PREV_ID_TIPO_TARJETA_ITV(String pPREVIDTIPOTARJETAITV) {
		P_PREV_ID_TIPO_TARJETA_ITV = pPREVIDTIPOTARJETAITV;
	}
	public String getP_KILOMETRAJE() {
		return P_KILOMETRAJE;
	}
	public void setP_KILOMETRAJE(String pKILOMETRAJE) {
		P_KILOMETRAJE = pKILOMETRAJE;
	}
	public String getP_CHECK_FECHA_CADUCIDAD_ITV() {
		return P_CHECK_FECHA_CADUCIDAD_ITV;
	}
	public void setP_CHECK_FECHA_CADUCIDAD_ITV(String pCHECKFECHACADUCIDADITV) {
		P_CHECK_FECHA_CADUCIDAD_ITV = pCHECKFECHACADUCIDADITV;
	}
	public BigDecimal getP_PROVINCIA_PRIMERA_MATRICULA() {
		return P_PROVINCIA_PRIMERA_MATRICULA;
	}
	public void setP_PROVINCIA_PRIMERA_MATRICULA(
			BigDecimal p_PROVINCIA_PRIMERA_MATRICULA) {
		P_PROVINCIA_PRIMERA_MATRICULA = p_PROVINCIA_PRIMERA_MATRICULA;
	}
	public Integer getP_DIFERENCIA_ANYO() {
		return P_DIFERENCIA_ANYO;
	}
	public void setP_DIFERENCIA_ANYO(Integer pDIFERENCIAANYO) {
		P_DIFERENCIA_ANYO = pDIFERENCIAANYO;
	}
	public String getP_PAIS_FABRICACION() {
		return P_PAIS_FABRICACION;
	}
	public void setP_PAIS_FABRICACION(String p_PAIS_FABRICACION) {
		P_PAIS_FABRICACION = p_PAIS_FABRICACION;
	}
	public String getP_MATRICULA_ORIGEN() {
		return P_MATRICULA_ORIGEN;
	}
	public void setP_MATRICULA_ORIGEN(String p_MATRICULA_ORIGEN) {
		P_MATRICULA_ORIGEN = p_MATRICULA_ORIGEN;
	}
	public String getP_ID_PROCEDENCIA() {
		return P_ID_PROCEDENCIA;
	}
	public void setP_ID_PROCEDENCIA(String p_ID_PROCEDENCIA) {
		P_ID_PROCEDENCIA = p_ID_PROCEDENCIA;
	}
	public BigDecimal getP_FICHA_TECNICA_RD750() {
		return P_FICHA_TECNICA_RD750;
	}
	public void setP_FICHA_TECNICA_RD750(BigDecimal p_FICHA_TECNICA_RD750) {
		P_FICHA_TECNICA_RD750 = p_FICHA_TECNICA_RD750;
	}
	public BigDecimal getP_CODIGO_MARCA_BASE() {
		return P_CODIGO_MARCA_BASE;
	}
	public void setP_CODIGO_MARCA_BASE(BigDecimal p_CODIGO_MARCA_BASE) {
		P_CODIGO_MARCA_BASE = p_CODIGO_MARCA_BASE;
	}
	public String getP_FABRICANTE_BASE() {
		return P_FABRICANTE_BASE;
	}
	public void setP_FABRICANTE_BASE(String p_FABRICANTE_BASE) {
		P_FABRICANTE_BASE = p_FABRICANTE_BASE;
	}
	public String getP_TIPO_BASE() {
		return P_TIPO_BASE;
	}
	public void setP_TIPO_BASE(String p_TIPO_BASE) {
		P_TIPO_BASE = p_TIPO_BASE;
	}
	public String getP_VARIANTE_BASE() {
		return P_VARIANTE_BASE;
	}
	public void setP_VARIANTE_BASE(String p_VARIANTE_BASE) {
		P_VARIANTE_BASE = p_VARIANTE_BASE;
	}
	public String getP_VERSION_BASE() {
		return P_VERSION_BASE;
	}
	public void setP_VERSION_BASE(String p_VERSION_BASE) {
		P_VERSION_BASE = p_VERSION_BASE;
	}
	public String getP_N_HOMOLOGACION_BASE() {
		return P_N_HOMOLOGACION_BASE;
	}
	public void setP_N_HOMOLOGACION_BASE(String p_N_HOMOLOGACION_BASE) {
		P_N_HOMOLOGACION_BASE = p_N_HOMOLOGACION_BASE;
	}
	public BigDecimal getP_MOM_BASE() {
		return P_MOM_BASE;
	}
	public void setP_MOM_BASE(BigDecimal p_MOM_BASE) {
		P_MOM_BASE = p_MOM_BASE;
	}
	public String getP_CATEGORIA_ELECTRICA() {
		return P_CATEGORIA_ELECTRICA;
	}
	public void setP_CATEGORIA_ELECTRICA(String p_CATEGORIA_ELECTRICA) {
		P_CATEGORIA_ELECTRICA = p_CATEGORIA_ELECTRICA;
	}
	public BigDecimal getP_AUTONOMIA_ELECTRICA() {
		return P_AUTONOMIA_ELECTRICA;
	}
	public void setP_AUTONOMIA_ELECTRICA(BigDecimal p_AUTONOMIA_ELECTRICA) {
		P_AUTONOMIA_ELECTRICA = p_AUTONOMIA_ELECTRICA;
	}
	public String getP_MATRICULA_ORIG_EXTR() {
		return P_MATRICULA_ORIG_EXTR;
	}
	public void setP_MATRICULA_ORIG_EXTR(String p_MATRICULA_ORIG_EXTR) {
		P_MATRICULA_ORIG_EXTR = p_MATRICULA_ORIG_EXTR;
	}
	public String getP_MARCA() {
		return P_MARCA;
	}
	public void setP_MARCA(String p_MARCA) {
		P_MARCA = p_MARCA;
	}
	public String getP_FECHA_LECTURA_KM() {
		return P_FECHA_LECTURA_KM;
	}
	public void setP_FECHA_LECTURA_KM(String p_FECHA_LECTURA_KM) {
		P_FECHA_LECTURA_KM = p_FECHA_LECTURA_KM;
	}
	public BigDecimal getP_DOI_RESPONSABLE_LECTURA_KM() {
		return P_DOI_RESPONSABLE_LECTURA_KM;
	}
	public void setP_DOI_RESPONSABLE_LECTURA_KM(BigDecimal p_DOI_RESPONSABLE_LECTURA_KM) {
		P_DOI_RESPONSABLE_LECTURA_KM = p_DOI_RESPONSABLE_LECTURA_KM;
	}
	public Boolean getP_ES_SINIESTRO() {
		return P_ES_SINIESTRO;
	}
	public void setP_ES_SINIESTRO(Boolean p_ES_SINIESTRO) {
		P_ES_SINIESTRO = p_ES_SINIESTRO;
	}
	public BigDecimal getP_VELOCIDAD_MAXIMA() {
		return P_VELOCIDAD_MAXIMA;
	}
	public void setP_VELOCIDAD_MAXIMA(BigDecimal p_VELOCIDAD_MAXIMA) {
		P_VELOCIDAD_MAXIMA = p_VELOCIDAD_MAXIMA;
	}

}