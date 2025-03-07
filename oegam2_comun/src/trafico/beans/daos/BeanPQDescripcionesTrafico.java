package trafico.beans.daos;

import java.math.BigDecimal;

/**********************************************************************************************************
PROCEDURE: DESCRIPCIONES
PARAMETROS:
DESCRIPCON:
********************************************************************************************************
PROCEDURE DESCRIPCIONES (ROWID_TRAMITE IN OUT UROWID,
                 -- VALORES PROPIOS DEL TRAMITE GENERAL
                   P_NUM_EXPEDIENTE IN OUT TRAMITE_TRAFICO.NUM_EXPEDIENTE%TYPE,
                   P_CODIGO_TASA OUT TRAMITE_TRAFICO.CODIGO_TASA%TYPE,
                   P_TIPO_TASA OUT TASA.TIPO_TASA%TYPE,
                   P_JEFATURA_PROVINCIAL OUT TRAMITE_TRAFICO.JEFATURA_PROVINCIAL%TYPE,
                   P_DESC_JEFATURA OUT JEFATURA_TRAFICO.DESCRIPCION%TYPE,
                  -- VALORES PROPIOS DEL VEHICULO 
                   P_ID_VEHICULO OUT TRAMITE_TRAFICO.ID_VEHICULO%TYPE,
                   P_CODIGO_MARCA OUT VEHICULO.CODIGO_MARCA%TYPE,
                   P_MARCA OUT MARCA_DGT.MARCA%TYPE,
                   P_TIPO_VEHICULO OUT VEHICULO.TIPO_VEHICULO%TYPE,
                   P_DESC_TIPO_VEHI OUT TIPO_VEHICULO.DESCRIPCION%TYPE,
                   P_ID_SERVICIO OUT VEHICULO.ID_SERVICIO%TYPE,
                   P_DESC_SERVICIO OUT SERVICIO_TRAFICO.DESCRIPCION%TYPE,
                   P_ID_CRITERIO_CONSTRUCCION OUT VEHICULO.ID_CRITERIO_CONSTRUCCION%TYPE,
                   P_DESC_CRITER_CONSTRUCCION OUT CRITERIO_CONSTRUCCION.DESCRIPCION%TYPE,
                   P_ID_CRITERIO_UTILIZACION OUT VEHICULO.ID_CRITERIO_UTILIZACION%TYPE,                      
                   P_DESC_CRITER_UTILIZACION OUT CRITERIO_UTILIZACION.DESCRIPCION%TYPE,
                   P_ID_DIRECTIVA_CEE OUT VEHICULO.ID_DIRECTIVA_CEE%TYPE,
                   P_DESC_DIRECTIVA_CEE OUT DIRECTIVA_CEE.DESCRIPCION%TYPE,
                   P_ID_MOTIVO_ITV OUT VEHICULO.ID_MOTIVO_ITV%TYPE,
                   P_DESC_MOTIVO_ITV OUT MOTIVO_ITV.DESCRIPCION%TYPE,
                   P_ESTACION_ITV OUT VEHICULO.ESTACION_ITV%TYPE,
                   P_PROV_ESTACION_ITV OUT ESTACION_ITV.PROVINCIA%TYPE,
                   P_MUNI_ESTACION_ITV OUT ESTACION_ITV.MUNICIPIO%TYPE,
                   P_OTROS_SINCODIG OUT EVOLUCION_VEHICULO.OTROS%TYPE,
                 -- VALORES PROPIOS DE LA TRANSMISION
                   P_CODIGO_TASA_INF OUT TRAMITE_TRAF_TRAN.CODIGO_TASA_INF%TYPE,
                   P_TIPO_TASA_INF OUT TASA.TIPO_TASA%TYPE,
                   P_CODIGO_TASA_CAMSER OUT TRAMITE_TRAF_TRAN.CODIGO_TASA_CAMSER%TYPE,
                   P_TIPO_TASA_CAMSER OUT TASA.TIPO_TASA%TYPE,
                 -----
                   P_CODE OUT NUMBER,
                   P_SQLERRM OUT VARCHAR2);
                   
                   **/
/**
 * Clase BeanPQ para las descripciones de todos los campos que se necesitan para las impresiones.
 */
public class BeanPQDescripcionesTrafico extends BeanPQGeneral{

	private BigDecimal P_NUM_EXPEDIENTE;
	
	private Object P_CODIGO_TASA; 
	private Object P_TIPO_TASA;
	private Object P_JEFATURA_PROVINCIAL;
	private Object P_JEFATURA_PROV;
	private Object P_DESC_JEFATURA;
	private Object P_DESC_SUCURSAL;
	private Object P_ID_VEHICULO;
	private Object P_CODIGO_MARCA;
	private Object P_MARCA;
	private Object P_TIPO_VEHICULO;
	private Object P_DESC_TIPO_VEHI;
	private Object P_ID_SERVICIO;
	private Object P_DESC_SERVICIO;
	private Object P_ID_CRITERIO_CONSTRUCCION;
	private Object P_DESC_CRITER_CONSTRUCCION;
	private Object P_ID_CRITERIO_UTILIZACION;
	private Object P_DESC_CRITER_UTILIZACION;
	private Object P_ID_DIRECTIVA_CEE;
	private Object P_DESC_DIRECTIVA_CEE;
	private Object P_ID_MOTIVO_ITV;
	private Object P_DESC_MOTIVO_ITV;
	private Object P_ESTACION_ITV;
	private Object P_PROV_ESTACION_ITV;
	private Object P_MUNI_ESTACION_ITV;
	private Object P_OTROS_SINCODIG;

	private Object P_CODIGO_TASA_INF;
	private Object P_TIPO_TASA_INF;
	private Object P_CODIGO_TASA_CAMSER;
	private Object P_TIPO_TASA_CAMSER;
	
	private String P_CIF;
	private String P_RAZON_SOCIAL;
	private String P_MARCA_SIN_EDITAR;

	private Object P_CODIGO_MARCA_BASE;
	private Object P_MARCA_BASE;
	private String P_MARCA_SIN_EDITAR_BASE;

	public BeanPQDescripcionesTrafico() {
		super();
	}	

	// --------------- GET & SET --------------- 
	public Object getP_DESC_SUCURSAL() {
		return P_DESC_SUCURSAL;
	}
	public void setP_DESC_SUCURSAL(Object pDESCSUCURSAL) {
		P_DESC_SUCURSAL = pDESCSUCURSAL;
	}
	public BigDecimal getP_NUM_EXPEDIENTE() {
		return P_NUM_EXPEDIENTE;
	}
	public void setP_NUM_EXPEDIENTE(BigDecimal pNUMEXPEDIENTE) {
		P_NUM_EXPEDIENTE = pNUMEXPEDIENTE;
	}
	public Object getP_CODIGO_TASA() {
		return P_CODIGO_TASA;
	}
	public void setP_CODIGO_TASA(Object pCODIGOTASA) {
		P_CODIGO_TASA = pCODIGOTASA;
	}
	public Object getP_TIPO_TASA() {
		return P_TIPO_TASA;
	}
	public void setP_TIPO_TASA(Object pTIPOTASA) {
		P_TIPO_TASA = pTIPOTASA;
	}
	public Object getP_JEFATURA_PROVINCIAL() {
		return P_JEFATURA_PROVINCIAL;
	}
	public void setP_JEFATURA_PROVINCIAL(Object pJEFATURA_PROVINCIAL) {
		P_JEFATURA_PROVINCIAL = pJEFATURA_PROVINCIAL;
	}
	public Object getP_JEFATURA_PROV() {
		return P_JEFATURA_PROV;
	}
	public void setP_JEFATURA_PROV(Object pJEFATURAPROV) {
		P_JEFATURA_PROV = pJEFATURAPROV;
	}
	public Object getP_DESC_JEFATURA() {
		return P_DESC_JEFATURA;
	}
	public void setP_DESC_JEFATURA(Object pDESCJEFATURA) {
		P_DESC_JEFATURA = pDESCJEFATURA;
	}
	public Object getP_ID_VEHICULO() {
		return P_ID_VEHICULO;
	}
	public void setP_ID_VEHICULO(Object pIDVEHICULO) {
		P_ID_VEHICULO = pIDVEHICULO;
	}
	public Object getP_CODIGO_MARCA() {
		return P_CODIGO_MARCA;
	}
	public void setP_CODIGO_MARCA(Object pCODIGOMARCA) {
		P_CODIGO_MARCA = pCODIGOMARCA;
	}
	public Object getP_MARCA() {
		return P_MARCA;
	}
	public void setP_MARCA(Object pMARCA) {
		P_MARCA = pMARCA;
	}
	public Object getP_TIPO_VEHICULO() {
		return P_TIPO_VEHICULO;
	}
	public void setP_TIPO_VEHICULO(Object pTIPOVEHICULO) {
		P_TIPO_VEHICULO = pTIPOVEHICULO;
	}
	public Object getP_DESC_TIPO_VEHI() {
		return P_DESC_TIPO_VEHI;
	}
	public void setP_DESC_TIPO_VEHI(Object pDESCTIPOVEHI) {
		P_DESC_TIPO_VEHI = pDESCTIPOVEHI;
	}
	public Object getP_ID_SERVICIO() {
		return P_ID_SERVICIO;
	}
	public void setP_ID_SERVICIO(Object pIDSERVICIO) {
		P_ID_SERVICIO = pIDSERVICIO;
	}
	public Object getP_DESC_SERVICIO() {
		return P_DESC_SERVICIO;
	}
	public void setP_DESC_SERVICIO(Object pDESCSERVICIO) {
		P_DESC_SERVICIO = pDESCSERVICIO;
	}
	public Object getP_ID_CRITERIO_CONSTRUCCION() {
		return P_ID_CRITERIO_CONSTRUCCION;
	}
	public void setP_ID_CRITERIO_CONSTRUCCION(Object pIDCRITERIOCONSTRUCCION) {
		P_ID_CRITERIO_CONSTRUCCION = pIDCRITERIOCONSTRUCCION;
	}
	public Object getP_DESC_CRITER_CONSTRUCCION() {
		return P_DESC_CRITER_CONSTRUCCION;
	}
	public void setP_DESC_CRITER_CONSTRUCCION(Object pDESCCRITERCONSTRUCCION) {
		P_DESC_CRITER_CONSTRUCCION = pDESCCRITERCONSTRUCCION;
	}
	public Object getP_ID_CRITERIO_UTILIZACION() {
		return P_ID_CRITERIO_UTILIZACION;
	}
	public void setP_ID_CRITERIO_UTILIZACION(Object pIDCRITERIOUTILIZACION) {
		P_ID_CRITERIO_UTILIZACION = pIDCRITERIOUTILIZACION;
	}
	public Object getP_DESC_CRITER_UTILIZACION() {
		return P_DESC_CRITER_UTILIZACION;
	}
	public void setP_DESC_CRITER_UTILIZACION(Object pDESCCRITERUTILIZACION) {
		P_DESC_CRITER_UTILIZACION = pDESCCRITERUTILIZACION;
	}
	public Object getP_ID_DIRECTIVA_CEE() {
		return P_ID_DIRECTIVA_CEE;
	}
	public void setP_ID_DIRECTIVA_CEE(Object pIDDIRECTIVACEE) {
		P_ID_DIRECTIVA_CEE = pIDDIRECTIVACEE;
	}
	public Object getP_DESC_DIRECTIVA_CEE() {
		return P_DESC_DIRECTIVA_CEE;
	}
	public void setP_DESC_DIRECTIVA_CEE(Object pDESCDIRECTIVACEE) {
		P_DESC_DIRECTIVA_CEE = pDESCDIRECTIVACEE;
	}
	public Object getP_ID_MOTIVO_ITV() {
		return P_ID_MOTIVO_ITV;
	}
	public void setP_ID_MOTIVO_ITV(Object pIDMOTIVOITV) {
		P_ID_MOTIVO_ITV = pIDMOTIVOITV;
	}
	public Object getP_DESC_MOTIVO_ITV() {
		return P_DESC_MOTIVO_ITV;
	}
	public void setP_DESC_MOTIVO_ITV(Object pDESCMOTIVOITV) {
		P_DESC_MOTIVO_ITV = pDESCMOTIVOITV;
	}
	public Object getP_ESTACION_ITV() {
		return P_ESTACION_ITV;
	}
	public void setP_ESTACION_ITV(Object pESTACIONITV) {
		P_ESTACION_ITV = pESTACIONITV;
	}
	public Object getP_PROV_ESTACION_ITV() {
		return P_PROV_ESTACION_ITV;
	}
	public void setP_PROV_ESTACION_ITV(Object pPROVESTACIONITV) {
		P_PROV_ESTACION_ITV = pPROVESTACIONITV;
	}
	public Object getP_MUNI_ESTACION_ITV() {
		return P_MUNI_ESTACION_ITV;
	}
	public void setP_MUNI_ESTACION_ITV(Object pMUNIESTACIONITV) {
		P_MUNI_ESTACION_ITV = pMUNIESTACIONITV;
	}	
	public Object getP_OTROS_SINCODIG() {
		return P_OTROS_SINCODIG;
	}
	public void setP_OTROS_SINCODIG(Object pOTROSSINCODIG) {
		P_OTROS_SINCODIG = pOTROSSINCODIG;
	}
	public Object getP_CODIGO_TASA_INF() {
		return P_CODIGO_TASA_INF;
	}
	public void setP_CODIGO_TASA_INF(Object pCODIGOTASAINF) {
		P_CODIGO_TASA_INF = pCODIGOTASAINF;
	}
	public Object getP_TIPO_TASA_INF() {
		return P_TIPO_TASA_INF;
	}
	public void setP_TIPO_TASA_INF(Object pTIPOTASAINF) {
		P_TIPO_TASA_INF = pTIPOTASAINF;
	}
	public Object getP_CODIGO_TASA_CAMSER() {
		return P_CODIGO_TASA_CAMSER;
	}
	public void setP_CODIGO_TASA_CAMSER(Object pCODIGOTASACAMSER) {
		P_CODIGO_TASA_CAMSER = pCODIGOTASACAMSER;
	}
	public Object getP_TIPO_TASA_CAMSER() {
		return P_TIPO_TASA_CAMSER;
	}
	public void setP_TIPO_TASA_CAMSER(Object pTIPOTASACAMSER) {
		P_TIPO_TASA_CAMSER = pTIPOTASACAMSER;
	}
	public String getP_CIF() {
		return P_CIF;
	}
	public void setP_CIF(String pCIF) {
		P_CIF = pCIF;
	}
	public String getP_RAZON_SOCIAL() {
		return P_RAZON_SOCIAL;
	}
	public void setP_RAZON_SOCIAL(String pRAZONSOCIAL) {
		P_RAZON_SOCIAL = pRAZONSOCIAL;
	}
	public String getP_MARCA_SIN_EDITAR() {
		return P_MARCA_SIN_EDITAR;
	}
	public void setP_MARCA_SIN_EDITAR(String pMARCASINEDITAR) {
		P_MARCA_SIN_EDITAR = pMARCASINEDITAR;
	}

	public Object getP_CODIGO_MARCA_BASE() {
		return P_CODIGO_MARCA_BASE;
	}

	public void setP_CODIGO_MARCA_BASE(Object p_CODIGO_MARCA_BASE) {
		P_CODIGO_MARCA_BASE = p_CODIGO_MARCA_BASE;
	}

	public Object getP_MARCA_BASE() {
		return P_MARCA_BASE;
	}

	public void setP_MARCA_BASE(Object p_MARCA_BASE) {
		P_MARCA_BASE = p_MARCA_BASE;
	}

	public String getP_MARCA_SIN_EDITAR_BASE() {
		return P_MARCA_SIN_EDITAR_BASE;
	}

	public void setP_MARCA_SIN_EDITAR_BASE(String p_MARCA_SIN_EDITAR_BASE) {
		P_MARCA_SIN_EDITAR_BASE = p_MARCA_SIN_EDITAR_BASE;
	}
	
}