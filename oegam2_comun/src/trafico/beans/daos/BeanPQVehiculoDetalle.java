package trafico.beans.daos;

import java.math.BigDecimal;
import java.sql.Timestamp;

/*
 * PROCEDURE DETALLE (P_NUM_COLEGIADO IN OUT VEHICULO.NUM_COLEGIADO%TYPE,
                      P_ID_VEHICULO IN OUT VEHICULO.ID_VEHICULO%TYPE,
                      P_BASTIDOR IN OUT VEHICULO.BASTIDOR%TYPE,
                      P_MATRICULA IN OUT VEHICULO.MATRICULA%TYPE,
                      P_CODIGO_MARCA OUT VEHICULO.CODIGO_MARCA%TYPE,
                      P_MODELO OUT VEHICULO.MODELO%TYPE,
                      P_TIPVEHI OUT VEHICULO.TIPVEHI%TYPE,
                      P_CDMARCA OUT VEHICULO.CDMARCA%TYPE,
                      P_CDMODVEH OUT VEHICULO.CDMODVEH%TYPE,
                      P_FECDESDE OUT VEHICULO.FECDESDE%TYPE,
                      P_TIPO_VEHICULO OUT VEHICULO.TIPO_VEHICULO%TYPE,
                      P_ID_SERVICIO OUT VEHICULO.ID_SERVICIO%TYPE,
                      P_ID_SERVICIO_ANTERIOR OUT VEHICULO.ID_SERVICIO_ANTERIOR%TYPE,
                      P_FECHA_MATRICULACION OUT VEHICULO.FECHA_MATRICULACION%TYPE,
                      P_VEHICULO_AGRICOLA OUT VEHICULO.VEHICULO_AGRICOLA%TYPE,
                      P_VEHICULO_TRANSPORTE OUT VEHICULO.VEHICULO_TRANSPORTE%TYPE,
                      P_ID_COLOR OUT VEHICULO.ID_COLOR%TYPE,
                      P_ID_CARBURANTE OUT VEHICULO.ID_CARBURANTE%TYPE,
                      P_ID_PROCEDENCIA OUT VEHICULO.ID_PROCEDENCIA%TYPE,
                      P_ID_LUGAR_ADQUISICION OUT VEHICULO.ID_LUGAR_ADQUISICION%TYPE,
                      P_ID_CRITERIO_CONSTRUCCION OUT VEHICULO.ID_CRITERIO_CONSTRUCCION%TYPE,
                      P_ID_CRITERIO_UTILIZACION OUT VEHICULO.ID_CRITERIO_UTILIZACION%TYPE,
                      P_ID_DIRECTIVA_CEE OUT VEHICULO.ID_DIRECTIVA_CEE%TYPE,
                      P_DIPLOMATICO OUT VEHICULO.DIPLOMATICO%TYPE,
                      P_PLAZAS OUT VEHICULO.PLAZAS%TYPE,
                      P_NIVE IN OUT VEHICULO.NIVE%TYPE,
                      P_CODITV IN OUT VEHICULO.CODITV%TYPE,
                      P_TIPO_ITV IN OUT VEHICULO.TIPO_ITV%TYPE,
                      P_POTENCIA_FISCAL OUT VEHICULO.POTENCIA_FISCAL%TYPE,
                      P_POTENCIA_NETA OUT VEHICULO.POTENCIA_NETA%TYPE,
                      P_POTENCIA_PESO OUT VEHICULO.POTENCIA_PESO%TYPE,
                      P_CILINDRADA OUT VEHICULO.CILINDRADA%TYPE,
                      P_CO2 OUT VEHICULO.CO2%TYPE,
                      P_TARA OUT VEHICULO.TARA%TYPE,
                      P_PESO_MMA OUT VEHICULO.PESO_MMA%TYPE,
                      P_MTMA_ITV OUT VEHICULO.MTMA_ITV%TYPE,
                      P_VARIANTE OUT VEHICULO.VARIANTE%TYPE,
                      P_VERSION OUT VEHICULO.VERSION%TYPE,
                      P_EXCESO_PESO OUT VEHICULO.EXCESO_PESO%TYPE,
                      P_TIPO_INDUSTRIA OUT VEHICULO.TIPO_INDUSTRIA%TYPE,
                      P_ID_MOTIVO_ITV OUT VEHICULO.ID_MOTIVO_ITV%TYPE,
                      P_FECHA_ITV OUT VEHICULO.FECHA_ITV%TYPE,                      
                      P_MATRICULA_ORGIEN OUT VEHICULO.MATRICULA_ORIGEN%TYPE,                      
                      P_CLASIFICACION_ITV OUT VEHICULO.CLASIFICACION_ITV%TYPE,
                      P_ID_TIPO_TARJETA_ITV OUT VEHICULO.ID_TIPO_TARJETA_ITV%TYPE,
                      P_CONCEPTO_ITV OUT VEHICULO.CONCEPTO_ITV%TYPE,
                      P_ESTACION_ITV OUT VEHICULO.ESTACION_ITV%TYPE,
                      P_FECHA_INSPECCION OUT VEHICULO.FECHA_INSPECCION%TYPE,
                      P_N_PLAZAS_PIE OUT VEHICULO.N_PLAZAS_PIE%TYPE,
                      P_N_HOMOLOGACION OUT VEHICULO.N_HOMOLOGACION%TYPE,
                      P_N_RUEDAS OUT VEHICULO.N_RUEDAS%TYPE,
                      P_N_SERIE OUT VEHICULO.N_SERIE%TYPE,
                      P_ID_EPIGRAFE OUT VEHICULO.ID_EPIGRAFE%TYPE,
                      P_NIF_INTEGRADOR OUT VEHICULO.NIF_INTEGRADOR%TYPE,
                      P_VEHI_USADO OUT VEHICULO.VEHI_USADO%TYPE,
                      P_MATRI_AYUNTAMIENTO OUT VEHICULO.MATRI_AYUNTAMIENTO%TYPE,
                      P_LIMITE_MATR_TURIS OUT VEHICULO.LIMITE_MATR_TURIS%TYPE,
                      P_FECHA_PRIM_MATRI OUT VEHICULO.FECHA_PRIM_MATRI%TYPE,
                      P_ID_LUGAR_MATRICULACION OUT VEHICULO.ID_LUGAR_MATRICULACION%TYPE,
                      P_KM_USO OUT VEHICULO.KM_USO%TYPE,
                      P_HORAS_USO OUT VEHICULO.HORAS_USO%TYPE,
                      P_ID_VEHICULO_PREVER OUT VEHICULO.ID_VEHICULO_PREVER%TYPE,
                      P_N_CILINDROS OUT VEHICULO.N_CILINDROS%TYPE,
                      P_CARACTERISTICAS OUT VEHICULO.CARACTERISTICAS%TYPE,
                      P_ANIO_FABRICA OUT VEHICULO.ANIO_FABRICA%TYPE,
                      -- DATOS DE LA DIRECCION DEL VEHÍCULO
                      P_ID_DIRECCION OUT DIRECCION.ID_DIRECCION%TYPE,
                      P_ID_PROVINCIA OUT DIRECCION.ID_PROVINCIA%TYPE,
                      P_ID_MUNICIPIO OUT DIRECCION.ID_MUNICIPIO%TYPE,
                      P_ID_TIPO_VIA OUT DIRECCION.ID_TIPO_VIA%TYPE,
                      P_NOMBRE_VIA OUT DIRECCION.NOMBRE_VIA%TYPE,
                      P_NUMERO OUT DIRECCION.NUMERO%TYPE,
                      P_COD_POSTAL OUT DIRECCION.COD_POSTAL%TYPE,
                      P_PUEBLO OUT DIRECCION.PUEBLO%TYPE,
                      P_LETRA OUT DIRECCION.LETRA%TYPE,
                      P_ESCALERA OUT DIRECCION.ESCALERA%TYPE,
                      P_BLOQUE OUT DIRECCION.BLOQUE%TYPE,
                      P_PLANTA OUT DIRECCION.PLANTA%TYPE,
                      P_PUERTA OUT DIRECCION.PUERTA%TYPE,
                      P_NUM_LOCAL OUT DIRECCION.NUM_LOCAL%TYPE,
                      P_KM OUT DIRECCION.KM%TYPE,
                      P_HM OUT DIRECCION.HM%TYPE, 
                      -- DATOS DEL VEHICULO PREVER
                      P_PREV_MATRICULA  OUT VEHICULO.MATRICULA%TYPE,
                      P_PREV_BASTIDOR OUT VEHICULO.BASTIDOR%TYPE,
                      P_PREV_CODIGO_MARCA  OUT VEHICULO.CODIGO_MARCA%TYPE,
                      P_PREV_MODELO OUT VEHICULO.MODELO%TYPE,
                      P_PREV_ID_CONSTRUCCION OUT VEHICULO.ID_CRITERIO_CONSTRUCCION%TYPE,
                      P_PREV_ID_UTILIZACION OUT VEHICULO.ID_CRITERIO_UTILIZACION%TYPE,
                      P_PREV_CLASIFICACION_ITV OUT VEHICULO.CLASIFICACION_ITV%TYPE,
                      P_PREV_TIPO_ITV OUT VEHICULO.TIPO_ITV%TYPE,
                      -- DATOS DEL INTEGRADOR
                      P_TIPO_PERSONA_INTE OUT PERSONA.TIPO_PERSONA%TYPE,
                      P_APELLIDO1_RAZON_SOCIAL_INTE OUT PERSONA.APELLIDO1_RAZON_SOCIAL%TYPE,
                      P_APELLIDO2_INTE OUT PERSONA.APELLIDO2%TYPE,
                      P_NOMBRE_INTE OUT PERSONA.NOMBRE%TYPE,
                      -- MATE 2.5
                      P_IMPORTADO IN OUT VEHICULO.IMPORTADO%TYPE,
                      P_SUBASTADO IN OUT VEHICULO.SUBASTADO%TYPE,
                      P_FABRICACION IN OUT VEHICULO.FABRICACION%TYPE,
                      P_PAIS_IMPORTACION IN OUT VEHICULO.PAIS_IMPORTACION%TYPE,
                      P_FABRICANTE IN OUT VEHICULO.FABRICANTE%TYPE,
                      P_CARROCERIA IN OUT VEHICULO.CARROCERIA%TYPE,
                      P_CONSUMO IN OUT VEHICULO.CONSUMO%TYPE,
                      P_DIST_EJES IN OUT VEHICULO.DISTANCIA_EJES%TYPE,
                      P_VIA_ANT IN OUT VEHICULO.VIA_ANTERIOR%TYPE,
                      P_VIA_POS IN OUT VEHICULO.VIA_POSTERIOR%TYPE,
                      P_ALIMENTACION IN OUT VEHICULO.TIPO_ALIMENTACION%TYPE,
                      P_EMISIONES IN OUT VEHICULO.NIVEL_EMISIONES%TYPE,
                      P_ECO_INNOVACION IN OUT VEHICULO.ECO_INNOVACION%TYPE,
                      P_REDUCCION_ECO IN OUT VEHICULO.REDUCCION_ECO%TYPE,
                      P_CODIGO_ECO IN OUT VEHICULO.CODIGO_ECO%TYPE,
                      P_MOM IN OUT VEHICULO.MOM%TYPE,
                      P_CODITV_ORIGINAL IN OUT VEHICULO.CODITV_ORIGINAL%TYPE,
                      ----
                      P_CODE OUT NUMBER,
                      P_SQLERRM OUT VARCHAR2);
 */

public class BeanPQVehiculoDetalle extends BeanPQGeneral{

	private String P_NUM_COLEGIADO;
	private BigDecimal P_ID_VEHICULO;
	private String P_BASTIDOR;
	private String P_MATRICULA;
	private Object P_CODIGO_MARCA; 
	private Object P_MODELO;
	private Object P_TIPVEHI;
	private Object P_CDMARCA;
	private Object P_CDMODVEH;
	private Object P_FECDESDE;
	private Object P_TIPO_VEHICULO;
	private Object P_ID_SERVICIO;
	private Object P_ID_SERVICIO_ANTERIOR;
	private Object P_FECHA_MATRICULACION;
	private Object P_VEHICULO_AGRICOLA;
	private Object P_VEHICULO_TRANSPORTE;
	private Object P_ID_COLOR;
	private Object P_ID_CARBURANTE; 
	private Object P_ID_PROCEDENCIA;
	private Object P_ID_LUGAR_ADQUISICION;
	private Object P_ID_CRITERIO_CONSTRUCCION;
	private Object P_ID_CRITERIO_UTILIZACION;
	private Object P_ID_DIRECTIVA_CEE;
	private Object P_DIPLOMATICO;
	private Object P_PLAZAS;
	private Object P_CODITV;
	private Object P_CODITV_ORIGINAL;
	private Object P_POTENCIA_FISCAL;
	private Object P_POTENCIA_NETA;
	private Object P_POTENCIA_PESO;
	private Object P_CILINDRADA;
	private Object P_CO2;
	private Object P_MTMA_ITV;
	private Object P_VARIANTE;
	private Object P_VERSION;
	private Object P_TARA;
	private Object P_PESO_MMA;
	private Object P_PROVINCIA_PRIMERA_MATRICULA;
	private Object P_EXCESO_PESO;
	private Object P_TIPO_INDUSTRIA;
	private Object P_ID_MOTIVO_ITV;
	private Object P_FECHA_ITV;
	//private Object P_FECHA_INICIAL_ITV;
	private Object P_MATRICULA_ORIGEN;
	//private Object P_ID_PROVINCIA_ITV;
	private Object P_CLASIFICACION_ITV;
	private Object P_ID_TIPO_TARJETA_ITV;
	private Object P_CONCEPTO_ITV;
	private Object P_ESTACION_ITV;
	private Object P_FECHA_INSPECCION;
	private Object P_N_PLAZAS_PIE;
	private Object P_N_HOMOLOGACION;
	private Object P_N_RUEDAS;
	private Object P_N_SERIE;
	private Object P_ID_EPIGRAFE;
	private Object P_NIF_INTEGRADOR;
	private Object P_VEHI_USADO;
	private Object P_MATRI_AYUNTAMIENTO;
	private Object P_LIMITE_MATR_TURIS;
	private Object P_FECHA_PRIM_MATRI;
	private Object P_ID_LUGAR_MATRICULACION;
	private Object P_KM_USO;
	private Object P_HORAS_USO;
	private Object P_ID_VEHICULO_PREVER;
	private Object P_N_CILINDROS;
	private Object P_CARACTERISTICAS;
	private Object P_ANIO_FABRICA;
	private String P_NIVE;

	// DATOS DE LA DIRECCION DEL VEHÍCULO
	private Object P_ID_DIRECCION;
	private Object P_ID_PROVINCIA;
	private Object P_ID_MUNICIPIO;
	private Object P_ID_TIPO_VIA;
	private Object P_NOMBRE_VIA;
	private Object P_NUMERO;
	private Object P_COD_POSTAL;
	private Object P_PUEBLO_CORREOS;
	private Object P_COD_POSTAL_CORREOS;
	private Object P_PUEBLO;
	private Object P_LETRA;
	private Object P_ESCALERA;
	private Object P_BLOQUE;
	private Object P_PLANTA;
	private Object P_PUERTA;
	private Object P_NUM_LOCAL;
	private Object P_KM;
	private Object P_HM; 

	// DATOS DEL VEHICULO PREVER
	private Object P_PREV_MATRICULA;
	private Object P_PREV_BASTIDOR;
	private Object P_PREV_CODIGO_MARCA;
	private Object P_PREV_MODELO;
	private Object P_PREV_CLASIFICACION_ITV;
	private Object P_PREV_TIPO_ITV;
	private Object P_PREV_ID_CONSTRUCCION;
	private Object P_PREV_ID_UTILIZACION;
	// DATOS DEL INTEGRADOR
	private Object P_TIPO_PERSONA_INTE;
	private Object P_APELLIDO1_RAZON_SOCIAL_INTE;
	private Object P_APELLIDO2_INTE;
	private Object P_NOMBRE_INTE;
	private Object P_TIPO_ITV;
	
	// MATE 2.5
	private Object P_IMPORTADO;
	private Object P_SUBASTADO;
	private Object P_FABRICACION;
	private Object P_PAIS_IMPORTACION;
	private Object P_FABRICANTE;
	private Object P_CARROCERIA;
	private Object P_CONSUMO;
	private Object P_DIST_EJES;
	private Object P_VIA_ANT;
	private Object P_VIA_POS;
	private Object P_ALIMENTACION;
	private Object P_EMISIONES;
	private Object P_ECO_INNOVACION;
	private Object P_REDUCCION_ECO;
	private Object P_CODIGO_ECO;
	private Object P_MOM;
	
	private String P_CHECK_FECHA_CADUCIDAD_ITV;
	private String P_PAIS_FABRICACION;
	private String P_PROCEDENCIA;
	private Object P_MATRICULA_ORIG_EXTR;

	// VEHICULOS MULTIFASICOS
	private BigDecimal P_CODIGO_MARCA_BASE;
	private String P_FABRICANTE_BASE;
	private String P_TIPO_BASE; 
	private String P_VARIANTE_BASE;
	private String P_VERSION_BASE;
	private String P_N_HOMOLOGACION_BASE;
	private BigDecimal P_MOM_BASE;
	private String P_CATEGORIA_ELECTRICA;
	private BigDecimal P_AUTONOMIA_ELECTRICA;

	private Object P_BASTIDOR_MATRICULADO;
	
	private BigDecimal P_FICHA_TECNICA_RD750;
	
	private Timestamp P_FECHA_LECTURA_KM;	
	private String P_DOI_RESPONSABLE_LECTURA_KM;

	public BeanPQVehiculoDetalle() { }
	
	// ----------------------- GET & SET -----------------------

	public Object getP_MTMA_ITV() {
		return P_MTMA_ITV;
	}
	public void setP_MTMA_ITV(Object pMTMAITV) {
		P_MTMA_ITV = pMTMAITV;
	}
	public Object getP_VARIANTE() {
		return P_VARIANTE;
	}
	public void setP_VARIANTE(Object pVARIANTE) {
		P_VARIANTE = pVARIANTE;
	}
	public Object getP_VERSION() {
		return P_VERSION;
	}
	public Object getP_POTENCIA_PESO() {
		return P_POTENCIA_PESO;
	}
	public void setP_POTENCIA_PESO(Object pPOTENCIAPESO) {
		P_POTENCIA_PESO = pPOTENCIAPESO;
	}
	public void setP_VERSION(Object pVERSION) {
		P_VERSION = pVERSION;
	}
	public String getP_NUM_COLEGIADO() {
		return P_NUM_COLEGIADO;
	}
	public void setP_NUM_COLEGIADO(String pNUMCOLEGIADO) {
		P_NUM_COLEGIADO = pNUMCOLEGIADO;
	}
	public Object getP_PREV_ID_CONSTRUCCION() {
		return P_PREV_ID_CONSTRUCCION;
	}
	public void setP_PREV_ID_CONSTRUCCION(Object pPREVIDCONSTRUCCION) {
		P_PREV_ID_CONSTRUCCION = pPREVIDCONSTRUCCION;
	}
	public Object getP_PREV_ID_UTILIZACION() {
		return P_PREV_ID_UTILIZACION;
	}
	public void setP_PREV_ID_UTILIZACION(Object pPREVIDUTILIZACION) {
		P_PREV_ID_UTILIZACION = pPREVIDUTILIZACION;
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
	public Object getP_CODIGO_MARCA() {
		return P_CODIGO_MARCA;
	}
	public void setP_CODIGO_MARCA(Object pCODIGOMARCA) {
		P_CODIGO_MARCA = pCODIGOMARCA;
	}
	public Object getP_MODELO() {
		return P_MODELO;
	}
	public void setP_MODELO(Object pMODELO) {
		P_MODELO = pMODELO;
	}
	public Object getP_TIPVEHI() {
		return P_TIPVEHI;
	}
	public void setP_TIPVEHI(Object pTIPVEHI) {
		P_TIPVEHI = pTIPVEHI;
	}
	public Object getP_CDMARCA() {
		return P_CDMARCA;
	}
	public void setP_CDMARCA(Object pCDMARCA) {
		P_CDMARCA = pCDMARCA;
	}
	public Object getP_CDMODVEH() {
		return P_CDMODVEH;
	}
	public void setP_CDMODVEH(Object pCDMODVEH) {
		P_CDMODVEH = pCDMODVEH;
	}
	public Object getP_FECDESDE() {
		return P_FECDESDE;
	}
	public void setP_FECDESDE(Object pFECDESDE) {
		P_FECDESDE = pFECDESDE;
	}
	public Object getP_TIPO_VEHICULO() {
		return P_TIPO_VEHICULO;
	}
	public void setP_TIPO_VEHICULO(Object pTIPOVEHICULO) {
		P_TIPO_VEHICULO = pTIPOVEHICULO;
	}
	public Object getP_ID_SERVICIO() {
		return P_ID_SERVICIO;
	}
	public void setP_ID_SERVICIO(Object pIDSERVICIO) {
		P_ID_SERVICIO = pIDSERVICIO;
	}
	public Object getP_ID_SERVICIO_ANTERIOR() {
		return P_ID_SERVICIO_ANTERIOR;
	}
	public void setP_ID_SERVICIO_ANTERIOR(Object pIDSERVICIOANTERIOR) {
		P_ID_SERVICIO_ANTERIOR = pIDSERVICIOANTERIOR;
	}
	public Object getP_VEHICULO_AGRICOLA() {
		return P_VEHICULO_AGRICOLA;
	}
	public void setP_VEHICULO_AGRICOLA(Object pVEHICULOAGRICOLA) {
		P_VEHICULO_AGRICOLA = pVEHICULOAGRICOLA;
	}
	public Object getP_VEHICULO_TRANSPORTE() {
		return P_VEHICULO_TRANSPORTE;
	}
	public void setP_VEHICULO_TRANSPORTE(Object pVEHICULOTRANSPORTE) {
		P_VEHICULO_TRANSPORTE = pVEHICULOTRANSPORTE;
	}
	public Object getP_ID_COLOR() {
		return P_ID_COLOR;
	}
	public void setP_ID_COLOR(Object pIDCOLOR) {
		P_ID_COLOR = pIDCOLOR;
	}
	public Object getP_ID_CARBURANTE() {
		return P_ID_CARBURANTE;
	}
	public void setP_ID_CARBURANTE(Object pIDCARBURANTE) {
		P_ID_CARBURANTE = pIDCARBURANTE;
	}
	public Object getP_ID_PROCEDENCIA() {
		return P_ID_PROCEDENCIA;
	}
	public void setP_ID_PROCEDENCIA(Object pIDPROCEDENCIA) {
		P_ID_PROCEDENCIA = pIDPROCEDENCIA;
	}
	public Object getP_ID_LUGAR_ADQUISICION() {
		return P_ID_LUGAR_ADQUISICION;
	}
	public void setP_ID_LUGAR_ADQUISICION(Object pIDLUGARADQUISICION) {
		P_ID_LUGAR_ADQUISICION = pIDLUGARADQUISICION;
	}
	public Object getP_ID_CRITERIO_CONSTRUCCION() {
		return P_ID_CRITERIO_CONSTRUCCION;
	}
	public void setP_ID_CRITERIO_CONSTRUCCION(Object pIDCRITERIOCONSTRUCCION) {
		P_ID_CRITERIO_CONSTRUCCION = pIDCRITERIOCONSTRUCCION;
	}
	public Object getP_ID_CRITERIO_UTILIZACION() {
		return P_ID_CRITERIO_UTILIZACION;
	}
	public void setP_ID_CRITERIO_UTILIZACION(Object pIDCRITERIOUTILIZACION) {
		P_ID_CRITERIO_UTILIZACION = pIDCRITERIOUTILIZACION;
	}
	public Object getP_ID_DIRECTIVA_CEE() {
		return P_ID_DIRECTIVA_CEE;
	}
	public void setP_ID_DIRECTIVA_CEE(Object pIDDIRECTIVACEE) {
		P_ID_DIRECTIVA_CEE = pIDDIRECTIVACEE;
	}
	public Object getP_DIPLOMATICO() {
		return P_DIPLOMATICO;
	}
	public void setP_DIPLOMATICO(Object pDIPLOMATICO) {
		P_DIPLOMATICO = pDIPLOMATICO;
	}
	public Object getP_PLAZAS() {
		return P_PLAZAS;
	}
	public void setP_PLAZAS(Object pPLAZAS) {
		P_PLAZAS = pPLAZAS;
	}
	public Object getP_CODITV() {
		return P_CODITV;
	}
	public void setP_CODITV(Object pCODITV) {
		P_CODITV = pCODITV;
	}
	public Object getP_CODITV_ORIGINAL() {
		return P_CODITV_ORIGINAL;
	}
	public void setP_CODITV_ORIGINAL(Object pCODITV_ORIGINAL) {
		P_CODITV_ORIGINAL = pCODITV_ORIGINAL;
	}
	public Object getP_POTENCIA_FISCAL() {
		return P_POTENCIA_FISCAL;
	}
	public void setP_POTENCIA_FISCAL(Object pPOTENCIAFISCAL) {
		P_POTENCIA_FISCAL = pPOTENCIAFISCAL;
	}
	public Object getP_POTENCIA_NETA() {
		return P_POTENCIA_NETA;
	}
	public void setP_POTENCIA_NETA(Object pPOTENCIANETA) {
		P_POTENCIA_NETA = pPOTENCIANETA;
	}
	public Object getP_CILINDRADA() {
		return P_CILINDRADA;
	}
	public void setP_CILINDRADA(Object pCILINDRADA) {
		P_CILINDRADA = pCILINDRADA;
	}
	public Object getP_CO2() {
		return P_CO2;
	}
	public void setP_CO2(Object pCO2) {
		P_CO2 = pCO2;
	}
	public Object getP_TARA() {
		return P_TARA;
	}
	public void setP_TARA(Object pTARA) {
		P_TARA = pTARA;
	}
	public Object getP_PESO_MMA() {
		return P_PESO_MMA;
	}
	public void setP_PESO_MMA(Object pPESOMMA) {
		P_PESO_MMA = pPESOMMA;
	}
	public Object getP_EXCESO_PESO() {
		return P_EXCESO_PESO;
	}
	public void setP_EXCESO_PESO(Object pEXCESOPESO) {
		P_EXCESO_PESO = pEXCESOPESO;
	}
	public Object getP_TIPO_INDUSTRIA() {
		return P_TIPO_INDUSTRIA;
	}
	public void setP_TIPO_INDUSTRIA(Object pTIPOINDUSTRIA) {
		P_TIPO_INDUSTRIA = pTIPOINDUSTRIA;
	}
	public Object getP_ID_MOTIVO_ITV() {
		return P_ID_MOTIVO_ITV;
	}
	public void setP_ID_MOTIVO_ITV(Object pIDMOTIVOITV) {
		P_ID_MOTIVO_ITV = pIDMOTIVOITV;
	}
	public Object getP_FECHA_ITV() {
		return P_FECHA_ITV;
	}
	public void setP_FECHA_ITV(Object pFECHAITV) {
		P_FECHA_ITV = pFECHAITV;
	}
	public Object getP_CLASIFICACION_ITV() {
		return P_CLASIFICACION_ITV;
	}
	public void setP_CLASIFICACION_ITV(Object pCLASIFICACIONITV) {
		P_CLASIFICACION_ITV = pCLASIFICACIONITV;
	}
	public Object getP_ID_TIPO_TARJETA_ITV() {
		return P_ID_TIPO_TARJETA_ITV;
	}
	public void setP_ID_TIPO_TARJETA_ITV(Object pIDTIPOTARJETAITV) {
		P_ID_TIPO_TARJETA_ITV = pIDTIPOTARJETAITV;
	}
	public Object getP_CONCEPTO_ITV() {
		return P_CONCEPTO_ITV;
	}
	public void setP_CONCEPTO_ITV(Object pCONCEPTOITV) {
		P_CONCEPTO_ITV = pCONCEPTOITV;
	}
	public Object getP_ESTACION_ITV() {
		return P_ESTACION_ITV;
	}
	public void setP_ESTACION_ITV(Object pESTACIONITV) {
		P_ESTACION_ITV = pESTACIONITV;
	}
	public Object getP_FECHA_INSPECCION() {
		return P_FECHA_INSPECCION;
	}
	public void setP_FECHA_INSPECCION(Object pFECHAINSPECCION) {
		P_FECHA_INSPECCION = pFECHAINSPECCION;
	}
	public Object getP_N_PLAZAS_PIE() {
		return P_N_PLAZAS_PIE;
	}
	public void setP_N_PLAZAS_PIE(Object pNPLAZASPIE) {
		P_N_PLAZAS_PIE = pNPLAZASPIE;
	}
	public Object getP_N_HOMOLOGACION() {
		return P_N_HOMOLOGACION;
	}
	public void setP_N_HOMOLOGACION(Object pNHOMOLOGACION) {
		P_N_HOMOLOGACION = pNHOMOLOGACION;
	}
	public Object getP_N_RUEDAS() {
		return P_N_RUEDAS;
	}
	public void setP_N_RUEDAS(Object pNRUEDAS) {
		P_N_RUEDAS = pNRUEDAS;
	}
	public Object getP_N_SERIE() {
		return P_N_SERIE;
	}
	public void setP_N_SERIE(Object pNSERIE) {
		P_N_SERIE = pNSERIE;
	}
	public Object getP_ID_EPIGRAFE() {
		return P_ID_EPIGRAFE;
	}
	public void setP_ID_EPIGRAFE(Object pIDEPIGRAFE) {
		P_ID_EPIGRAFE = pIDEPIGRAFE;
	}
	public Object getP_NIF_INTEGRADOR() {
		return P_NIF_INTEGRADOR;
	}
	public void setP_NIF_INTEGRADOR(Object pNIFINTEGRADOR) {
		P_NIF_INTEGRADOR = pNIFINTEGRADOR;
	}
	public Object getP_VEHI_USADO() {
		return P_VEHI_USADO;
	}
	public void setP_VEHI_USADO(Object pVEHIUSADO) {
		P_VEHI_USADO = pVEHIUSADO;
	}
	public Object getP_MATRI_AYUNTAMIENTO() {
		return P_MATRI_AYUNTAMIENTO;
	}
	public void setP_MATRI_AYUNTAMIENTO(Object pMATRIAYUNTAMIENTO) {
		P_MATRI_AYUNTAMIENTO = pMATRIAYUNTAMIENTO;
	}
	public Object getP_LIMITE_MATR_TURIS() {
		return P_LIMITE_MATR_TURIS;
	}
	public void setP_LIMITE_MATR_TURIS(Object pLIMITEMATRTURIS) {
		P_LIMITE_MATR_TURIS = pLIMITEMATRTURIS;
	}
	public Object getP_FECHA_PRIM_MATRI() {
		return P_FECHA_PRIM_MATRI;
	}
	public void setP_FECHA_PRIM_MATRI(Object pFECHAPRIMMATRI) {
		P_FECHA_PRIM_MATRI = pFECHAPRIMMATRI;
	}
	public Object getP_ID_LUGAR_MATRICULACION() {
		return P_ID_LUGAR_MATRICULACION;
	}
	public void setP_ID_LUGAR_MATRICULACION(Object pIDLUGARMATRICULACION) {
		P_ID_LUGAR_MATRICULACION = pIDLUGARMATRICULACION;
	}
	public Object getP_KM_USO() {
		return P_KM_USO;
	}
	public void setP_KM_USO(Object pKMUSO) {
		P_KM_USO = pKMUSO;
	}
	public Object getP_HORAS_USO() {
		return P_HORAS_USO;
	}
	public void setP_HORAS_USO(Object pHORASUSO) {
		P_HORAS_USO = pHORASUSO;
	}
	public Object getP_ID_VEHICULO_PREVER() {
		return P_ID_VEHICULO_PREVER;
	}
	public void setP_ID_VEHICULO_PREVER(Object pIDVEHICULOPREVER) {
		P_ID_VEHICULO_PREVER = pIDVEHICULOPREVER;
	}
	public Object getP_N_CILINDROS() {
		return P_N_CILINDROS;
	}
	public void setP_N_CILINDROS(Object pNCILINDROS) {
		P_N_CILINDROS = pNCILINDROS;
	}
	public Object getP_CARACTERISTICAS() {
		return P_CARACTERISTICAS;
	}
	public void setP_CARACTERISTICAS(Object pCARACTERISTICAS) {
		P_CARACTERISTICAS = pCARACTERISTICAS;
	}
	public Object getP_ANIO_FABRICA() {
		return P_ANIO_FABRICA;
	}
	public void setP_ANIO_FABRICA(Object pANIOFABRICA) {
		P_ANIO_FABRICA = pANIOFABRICA;
	}
	public Object getP_ID_DIRECCION() {
		return P_ID_DIRECCION;
	}
	public void setP_ID_DIRECCION(Object pIDDIRECCION) {
		P_ID_DIRECCION = pIDDIRECCION;
	}
	public Object getP_ID_PROVINCIA() {
		return P_ID_PROVINCIA;
	}
	public void setP_ID_PROVINCIA(Object pIDPROVINCIA) {
		P_ID_PROVINCIA = pIDPROVINCIA;
	}
	public Object getP_ID_MUNICIPIO() {
		return P_ID_MUNICIPIO;
	}
	public void setP_ID_MUNICIPIO(Object pIDMUNICIPIO) {
		P_ID_MUNICIPIO = pIDMUNICIPIO;
	}
	public Object getP_ID_TIPO_VIA() {
		return P_ID_TIPO_VIA;
	}
	public void setP_ID_TIPO_VIA(Object pIDTIPOVIA) {
		P_ID_TIPO_VIA = pIDTIPOVIA;
	}
	public Object getP_NOMBRE_VIA() {
		return P_NOMBRE_VIA;
	}
	public void setP_NOMBRE_VIA(Object pNOMBREVIA) {
		P_NOMBRE_VIA = pNOMBREVIA;
	}
	public Object getP_NUMERO() {
		return P_NUMERO;
	}
	public void setP_NUMERO(Object pNUMERO) {
		P_NUMERO = pNUMERO;
	}
	public Object getP_COD_POSTAL() {
		return P_COD_POSTAL;
	}
	public void setP_COD_POSTAL(Object pCODPOSTAL) {
		P_COD_POSTAL = pCODPOSTAL;
	}
	public Object getP_PUEBLO() {
		return P_PUEBLO;
	}
	public void setP_PUEBLO(Object pPUEBLO) {
		P_PUEBLO = pPUEBLO;
	}
	public Object getP_PUEBLO_CORREOS() {
		return P_PUEBLO_CORREOS;
	}
	public void setP_PUEBLO_CORREOS(Object p_PUEBLO_CORREOS) {
		P_PUEBLO_CORREOS = p_PUEBLO_CORREOS;
	}
	public Object getP_COD_POSTAL_CORREOS() {
		return P_COD_POSTAL_CORREOS;
	}
	public void setP_COD_POSTAL_CORREOS(Object p_COD_POSTAL_CORREOS) {
		P_COD_POSTAL_CORREOS = p_COD_POSTAL_CORREOS;
	}
	public Object getP_LETRA() {
		return P_LETRA;
	}
	public void setP_LETRA(Object pLETRA) {
		P_LETRA = pLETRA;
	}
	public Object getP_ESCALERA() {
		return P_ESCALERA;
	}
	public void setP_ESCALERA(Object pESCALERA) {
		P_ESCALERA = pESCALERA;
	}
	public Object getP_BLOQUE() {
		return P_BLOQUE;
	}
	public void setP_BLOQUE(Object pBLOQUE) {
		P_BLOQUE = pBLOQUE;
	}
	public Object getP_PLANTA() {
		return P_PLANTA;
	}
	public void setP_PLANTA(Object pPLANTA) {
		P_PLANTA = pPLANTA;
	}
	public Object getP_PUERTA() {
		return P_PUERTA;
	}
	public void setP_PUERTA(Object pPUERTA) {
		P_PUERTA = pPUERTA;
	}
	public Object getP_NUM_LOCAL() {
		return P_NUM_LOCAL;
	}
	public void setP_NUM_LOCAL(Object pNUMLOCAL) {
		P_NUM_LOCAL = pNUMLOCAL;
	}
	public Object getP_KM() {
		return P_KM;
	}
	public void setP_KM(Object pKM) {
		P_KM = pKM;
	}
	public Object getP_HM() {
		return P_HM;
	}
	public void setP_HM(Object pHM) {
		P_HM = pHM;
	}
	public Object getP_PREV_MATRICULA() {
		return P_PREV_MATRICULA;
	}
	public void setP_PREV_MATRICULA(Object pPREVMATRICULA) {
		P_PREV_MATRICULA = pPREVMATRICULA;
	}
	public Object getP_PREV_BASTIDOR() {
		return P_PREV_BASTIDOR;
	}
	public void setP_PREV_BASTIDOR(Object pPREVBASTIDOR) {
		P_PREV_BASTIDOR = pPREVBASTIDOR;
	}
	public Object getP_PREV_CODIGO_MARCA() {
		return P_PREV_CODIGO_MARCA;
	}
	public void setP_PREV_CODIGO_MARCA(Object pPREVCODIGOMARCA) {
		P_PREV_CODIGO_MARCA = pPREVCODIGOMARCA;
	}
	public Object getP_PREV_MODELO() {
		return P_PREV_MODELO;
	}
	public void setP_PREV_MODELO(Object pPREVMODELO) {
		P_PREV_MODELO = pPREVMODELO;
	}
	public Object getP_PREV_CLASIFICACION_ITV() {
		return P_PREV_CLASIFICACION_ITV;
	}
	public void setP_PREV_CLASIFICACION_ITV(Object pPREVCLASIFICACIONITV) {
		P_PREV_CLASIFICACION_ITV = pPREVCLASIFICACIONITV;
	}
	public Object getP_PREV_TIPO_ITV() {
		return P_PREV_TIPO_ITV;
	}
	public void setP_PREV_TIPO_ITV(Object pPREVTIPOITV) {
		P_PREV_TIPO_ITV = pPREVTIPOITV;
	}
	public Object getP_TIPO_PERSONA_INTE() {
		return P_TIPO_PERSONA_INTE;
	}
	public void setP_TIPO_PERSONA_INTE(Object pTIPOPERSONAINTE) {
		P_TIPO_PERSONA_INTE = pTIPOPERSONAINTE;
	}
	public Object getP_APELLIDO1_RAZON_SOCIAL_INTE() {
		return P_APELLIDO1_RAZON_SOCIAL_INTE;
	}
	public void setP_APELLIDO1_RAZON_SOCIAL_INTE(Object pAPELLIDO1RAZONSOCIALINTE) {
		P_APELLIDO1_RAZON_SOCIAL_INTE = pAPELLIDO1RAZONSOCIALINTE;
	}
	public Object getP_APELLIDO2_INTE() {
		return P_APELLIDO2_INTE;
	}
	public void setP_APELLIDO2_INTE(Object pAPELLIDO2INTE) {
		P_APELLIDO2_INTE = pAPELLIDO2INTE;
	}
	public Object getP_NOMBRE_INTE() {
		return P_NOMBRE_INTE;
	}
	public void setP_NOMBRE_INTE(Object pNOMBREINTE) {
		P_NOMBRE_INTE = pNOMBREINTE;
	}
	public Object getP_FECHA_MATRICULACION() {
		return P_FECHA_MATRICULACION;
	}
	public void setP_FECHA_MATRICULACION(Object pFECHAMATRICULACION) {
		P_FECHA_MATRICULACION = pFECHAMATRICULACION;
	}
	public Object getP_TIPO_ITV() {
		return P_TIPO_ITV;
	}
	public void setP_TIPO_ITV(Object pTIPOITV) {
		P_TIPO_ITV = pTIPOITV;
	}
	public String getP_NIVE() {
		return P_NIVE;
	}
	public void setP_NIVE(String pNIVE) {
		P_NIVE = pNIVE;
	}
	public Object getP_IMPORTADO() {
		return P_IMPORTADO;
	}
	public void setP_IMPORTADO(Object pIMPORTADO) {
		P_IMPORTADO = pIMPORTADO;
	}
	public Object getP_SUBASTADO() {
		return P_SUBASTADO;
	}
	public void setP_SUBASTADO(Object pSUBASTADO) {
		P_SUBASTADO = pSUBASTADO;
	}
	public Object getP_FABRICACION() {
		return P_FABRICACION;
	}
	public void setP_FABRICACION(Object pFABRICACION) {
		P_FABRICACION = pFABRICACION;
	}
	public Object getP_PAIS_IMPORTACION() {
		return P_PAIS_IMPORTACION;
	}
	public void setP_PAIS_IMPORTACION(Object pPAISIMPORTACION) {
		P_PAIS_IMPORTACION = pPAISIMPORTACION;
	}
	public Object getP_FABRICANTE() {
		return P_FABRICANTE;
	}
	public void setP_FABRICANTE(Object pFABRICANTE) {
		P_FABRICANTE = pFABRICANTE;
	}
	public Object getP_CARROCERIA() {
		return P_CARROCERIA;
	}
	public void setP_CARROCERIA(Object pCARROCERIA) {
		P_CARROCERIA = pCARROCERIA;
	}
	public Object getP_CONSUMO() {
		return P_CONSUMO;
	}
	public void setP_CONSUMO(Object pCONSUMO) {
		P_CONSUMO = pCONSUMO;
	}
	public Object getP_DIST_EJES() {
		return P_DIST_EJES;
	}
	public void setP_DIST_EJES(Object pDISTEJES) {
		P_DIST_EJES = pDISTEJES;
	}
	public Object getP_VIA_ANT() {
		return P_VIA_ANT;
	}
	public void setP_VIA_ANT(Object pVIAANT) {
		P_VIA_ANT = pVIAANT;
	}
	public Object getP_VIA_POS() {
		return P_VIA_POS;
	}
	public void setP_VIA_POS(Object pVIAPOS) {
		P_VIA_POS = pVIAPOS;
	}
	public Object getP_ALIMENTACION() {
		return P_ALIMENTACION;
	}
	public void setP_ALIMENTACION(Object pALIMENTACION) {
		P_ALIMENTACION = pALIMENTACION;
	}
	public Object getP_EMISIONES() {
		return P_EMISIONES;
	}
	public void setP_EMISIONES(Object pEMISIONES) {
		P_EMISIONES = pEMISIONES;
	}
	public Object getP_ECO_INNOVACION() {
		return P_ECO_INNOVACION;
	}
	public void setP_ECO_INNOVACION(Object pECOINNOVACION) {
		P_ECO_INNOVACION = pECOINNOVACION;
	}
	public Object getP_REDUCCION_ECO() {
		return P_REDUCCION_ECO;
	}
	public void setP_REDUCCION_ECO(Object pREDUCCIONECO) {
		P_REDUCCION_ECO = pREDUCCIONECO;
	}
	public Object getP_CODIGO_ECO() {
		return P_CODIGO_ECO;
	}
	public void setP_CODIGO_ECO(Object pCODIGOECO) {
		P_CODIGO_ECO = pCODIGOECO;
	}
	public Object getP_MOM() {
		return P_MOM;
	}
	public void setP_MOM(Object pMOM) {
		P_MOM = pMOM;
	}
	public Object getP_PROVINCIA_PRIMERA_MATRICULA() {
		return P_PROVINCIA_PRIMERA_MATRICULA;
	}
	public void setP_PROVINCIA_PRIMERA_MATRICULA(Object p_PROVINCIA_PRIMERA_MATRICULA) {
		P_PROVINCIA_PRIMERA_MATRICULA = p_PROVINCIA_PRIMERA_MATRICULA;
	}
	public String getP_CHECK_FECHA_CADUCIDAD_ITV() {
		return P_CHECK_FECHA_CADUCIDAD_ITV;
	}
	public void setP_CHECK_FECHA_CADUCIDAD_ITV(String pCHECKFECHACADUCIDADITV) {
		P_CHECK_FECHA_CADUCIDAD_ITV = pCHECKFECHACADUCIDADITV;
	}

	/*public Object getP_FECHA_INICIAL_ITV() {
		return P_FECHA_INICIAL_ITV;
	}

	public void setP_FECHA_INICIAL_ITV(Object p_FECHA_INICIAL_ITV) {
		P_FECHA_INICIAL_ITV = p_FECHA_INICIAL_ITV;
	}*/

	public Object getP_MATRICULA_ORIGEN() {
		return P_MATRICULA_ORIGEN;
	}

	public void setP_MATRICULA_ORIGEN(Object p_MATRICULA_ORIGEN) {
		P_MATRICULA_ORIGEN = p_MATRICULA_ORIGEN;
	}

	/*public Object getP_ID_PROVINCIA_ITV() {
		return P_ID_PROVINCIA_ITV;
	}

	public void setP_ID_PROVINCIA_ITV(Object p_ID_PROVINCIA_ITV) {
		P_ID_PROVINCIA_ITV = p_ID_PROVINCIA_ITV;
	}*/

	public String getP_PAIS_FABRICACION() {
		return P_PAIS_FABRICACION;
	}

	public void setP_PAIS_FABRICACION(String p_PAIS_FABRICACION) {
		P_PAIS_FABRICACION = p_PAIS_FABRICACION;
	}

	public String getP_PROCEDENCIA() {
		return P_PROCEDENCIA;
	}

	public void setP_PROCEDENCIA(String p_PROCEDENCIA) {
		P_PROCEDENCIA = p_PROCEDENCIA;
	}

	public Object getP_BASTIDOR_MATRICULADO() {
		return P_BASTIDOR_MATRICULADO;
	}

	public void setP_BASTIDOR_MATRICULADO(Object p_BASTIDOR_MATRICULADO) {
		P_BASTIDOR_MATRICULADO = p_BASTIDOR_MATRICULADO;
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

	public Object getP_MATRICULA_ORIG_EXTR() {
		return P_MATRICULA_ORIG_EXTR;
	}

	public void setP_MATRICULA_ORIG_EXTR(Object p_MATRICULA_ORIG_EXTR) {
		P_MATRICULA_ORIG_EXTR = p_MATRICULA_ORIG_EXTR;
	}
	
	public Timestamp getP_FECHA_LECTURA_KM() {
		return P_FECHA_LECTURA_KM;
	}

	public void setP_FECHA_LECTURA_KM(Timestamp p_FECHA_LECTURA_KM) {
		P_FECHA_LECTURA_KM = p_FECHA_LECTURA_KM;
	}

	public String getP_DOI_RESPONSABLE_LECTURA_KM() {
		return P_DOI_RESPONSABLE_LECTURA_KM;
	}

	public void setP_DOI_RESPONSABLE_LECTURA_KM(String p_DOI_RESPONSABLE_LECTURA_KM) {
		P_DOI_RESPONSABLE_LECTURA_KM = p_DOI_RESPONSABLE_LECTURA_KM;
	}
}