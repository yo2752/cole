package procesos.daos;

import trafico.beans.daos.BeanPQGeneral;

public class BeanPQTomarITV extends BeanPQGeneral {

	/*********************************************************************************************************
	 * PROCEDURE: TOMAR:
	 * 
	 * DESCRIPCION:
	 *****************************************************************************************************/
	/* PROCEDURE TOMAR (P_CODIGO_ITV IN OUT DGT_CODIGO_ITV.CODIGO_ITV%TYPE,
                   P_BASTIDOR OUT DGT_CODIGO_ITV.BASTIDOR%TYPE,
                   P_TIPO_VEHICULO_INDUSTRIA OUT DGT_CODIGO_ITV.TIPO_VEHICULO_INDUSTRIA%TYPE,
                   P_TIPO_VEHICULO_TRAFICO OUT DGT_CODIGO_ITV.TIPO_VEHICULO_TRAFICO%TYPE,
                   P_CODIGO_MARCA OUT VEHICULO.CODIGO_MARCA%TYPE,
                   P_MARCA OUT DGT_CODIGO_ITV.MARCA%TYPE,
                   P_MODELO OUT DGT_CODIGO_ITV.MODELO%TYPE,
                   P_TARA OUT DGT_CODIGO_ITV.TARA%TYPE,
                   P_MMA OUT DGT_CODIGO_ITV.MMA%TYPE,
                   P_PLAZAS OUT DGT_CODIGO_ITV.PLAZAS%TYPE,
                   P_CARBURANTE OUT DGT_CODIGO_ITV.CARBURANTE%TYPE,
                   P_CILINDRADA OUT DGT_CODIGO_ITV.CILINDRADA%TYPE,
                   P_POTENCIA_FISCAL OUT DGT_CODIGO_ITV.POTENCIA_FISCAL%TYPE,
                   P_POTENCIA_REAL OUT DGT_CODIGO_ITV.POTENCIA_REAL%TYPE,
                   P_CO2 OUT DGT_CODIGO_ITV.CO2%TYPE,
                   
                   --nuevos campos mate 2.5
                   P_FABRICANTE IN OUT DGT_CODIGO_ITV.FABRICANTE%TYPE,
                   P_TIPO IN OUT DGT_CODIGO_ITV.TIPO%TYPE,
                   P_VARIANTE IN OUT DGT_CODIGO_ITV.VARIANTE%TYPE,
                   P_VERSION_35 IN OUT DGT_CODIGO_ITV.VERSION_35%TYPE,
                   P_CAT_HOMOLOGACION IN OUT DGT_CODIGO_ITV.CAT_HOMOLOGACION%TYPE,
                   P_CONSUMO IN OUT DGT_CODIGO_ITV.CONSUMO%TYPE,
                   P_MASA_MOM IN OUT DGT_CODIGO_ITV.MASA_MOM%TYPE,
                   P_PROCEDENCIA IN OUT DGT_CODIGO_ITV.PROCEDENCIA%TYPE,
                   P_DISTANCIA_EJES IN OUT DGT_CODIGO_ITV.DISTANCIA_EJES%TYPE,
                   P_VIA_ANTERIOR IN OUT DGT_CODIGO_ITV.VIA_ANTERIOR%TYPE,
                   P_VIA_POSTERIOR IN OUT DGT_CODIGO_ITV.VIA_POSTERIOR%TYPE,
                   P_TIPO_ALIMENTACION IN OUT DGT_CODIGO_ITV.TIPO_ALIMENTACION%TYPE,
                   P_CONTRASENIA_HOMOLOGACION IN OUT DGT_CODIGO_ITV.CONTRASENIA_HOMOLOGACION%TYPE,
                   P_NIVEL_EMISION IN OUT DGT_CODIGO_ITV.NIVEL_EMISION%TYPE,
                   P_ECO_INNOVACION IN OUT DGT_CODIGO_ITV.ECO_INNOVACION%TYPE,
                   P_REDUCCION_ECO IN OUT DGT_CODIGO_ITV.REDUCCION_ECO%TYPE,
                   P_CODIGO_ECO IN OUT DGT_CODIGO_ITV.CODIGO_ECO%TYPE,
                   -----           
                   P_CODE OUT NUMBER,
                   P_SQLERRM OUT VARCHAR2);
*/
	private Object P_CODIGO_ITV;
	private Object P_BASTIDOR;
	private Object P_TIPO_VEHICULO_INDUSTRIA; //Equivale a la CLASIFICACION en el nuevo documento MATE 2.5
	private Object P_TIPO_VEHICULO_TRAFICO; //No hay equivalencia con tabla DGT_CODIGO_ITV
	private Object P_MARCA;
	private Object P_CODIGO_MARCA;
	private Object P_MODELO;
	private Object P_TARA;
	private Object P_MMA;
	private Object P_PLAZAS;
	private Object P_CARBURANTE;
	private Object P_CILINDRADA;
	private Object P_POTENCIA_FISCAL;
	private Object P_POTENCIA_REAL;
	private Object P_CO2;
	private Object P_NIVE; //No hay equivalencia con tabla DGT_CODIGO_ITV
	private Object P_ID_VEHICULO; //No hay equivalencia con tabla DGT_CODIGO_ITV
	
	//Nuevos campos MATE 2.5
	private Object P_FABRICANTE;
	private Object P_TIPO;
	private Object P_VARIANTE;
	private Object P_VERSION;
	private Object P_CAT_HOMOLOGACION;
	private Object P_CONSUMO;
	private Object P_MASA_MOM;
	private Object P_PROCEDENCIA;
	private Object P_DISTANCIA_EJES;
	private Object P_VIA_ANTERIOR;
	private Object P_VIA_POSTERIOR;
	private Object P_TIPO_ALIMENTACION;
	private Object P_CONTRASENIA_HOMOLOGACION;
	private Object P_NIVEL_EMISION;
	private Object P_ECO_INNOVACION;
	private Object P_REDUCCION_ECO;
	private Object P_CODIGO_ECO;
	
	private String P_VERSION_35;
	private String P_MMC;
	private String P_POTENCIA_PESO;
	private String P_PLAZAS_PIE;
	private String P_CARROCERIA;	
	
	// ---------------------- GET & SET ---------------------- 
	public Object getP_CODIGO_ITV() {
		return P_CODIGO_ITV;
	}
	public void setP_CODIGO_ITV(Object pCODIGOITV) {
		P_CODIGO_ITV = pCODIGOITV;
	}
	public Object getP_BASTIDOR() {
		return P_BASTIDOR;
	}
	public void setP_BASTIDOR(Object pBASTIDOR) {
		P_BASTIDOR = pBASTIDOR;
	}
	public Object getP_TIPO_VEHICULO_INDUSTRIA() {
		return P_TIPO_VEHICULO_INDUSTRIA;
	}
	public void setP_TIPO_VEHICULO_INDUSTRIA(Object pTIPOVEHICULOINDUSTRIA) {
		P_TIPO_VEHICULO_INDUSTRIA = pTIPOVEHICULOINDUSTRIA;
	}
	public Object getP_TIPO_VEHICULO_TRAFICO() {
		return P_TIPO_VEHICULO_TRAFICO;
	}
	public void setP_TIPO_VEHICULO_TRAFICO(Object pTIPOVEHICULOTRAFICO) {
		P_TIPO_VEHICULO_TRAFICO = pTIPOVEHICULOTRAFICO;
	}
	public Object getP_MARCA() {
		return P_MARCA;
	}
	public void setP_MARCA(Object pMARCA) {
		P_MARCA = pMARCA;
	}
	public Object getP_MODELO() {
		return P_MODELO;
	}
	public void setP_MODELO(Object pMODELO) {
		P_MODELO = pMODELO;
	}
	public Object getP_TARA() {
		return P_TARA;
	}
	public void setP_TARA(Object pTARA) {
		P_TARA = pTARA;
	}
	public Object getP_MMA() {
		return P_MMA;
	}
	public void setP_MMA(Object pMMA) {
		P_MMA = pMMA;
	}
	public Object getP_PLAZAS() {
		return P_PLAZAS;
	}
	public Object getP_NIVE() {
		return P_NIVE;
	}
	public void setP_NIVE(Object pNIVE) {
		P_NIVE = pNIVE;
	}
	public void setP_PLAZAS(Object pPLAZAS) {
		P_PLAZAS = pPLAZAS;
	}
	public Object getP_CARBURANTE() {
		return P_CARBURANTE;
	}
	public void setP_CARBURANTE(Object pCARBURANTE) {
		P_CARBURANTE = pCARBURANTE;
	}
	public Object getP_CILINDRADA() {
		return P_CILINDRADA;
	}
	public void setP_CILINDRADA(Object pCILINDRADA) {
		P_CILINDRADA = pCILINDRADA;
	}
	public Object getP_POTENCIA_FISCAL() {
		return P_POTENCIA_FISCAL;
	}
	public void setP_POTENCIA_FISCAL(Object pPOTENCIAFISCAL) {
		P_POTENCIA_FISCAL = pPOTENCIAFISCAL;
	}
	public Object getP_POTENCIA_REAL() {
		return P_POTENCIA_REAL;
	}
	public void setP_POTENCIA_REAL(Object pPOTENCIAREAL) {
		P_POTENCIA_REAL = pPOTENCIAREAL;
	}
	public Object getP_CO2() {
		return P_CO2;
	}
	public void setP_CO2(Object pCO2) {
		P_CO2 = pCO2;
	}
	public Object getP_CODIGO_MARCA() {
		return P_CODIGO_MARCA;
	}
	public void setP_CODIGO_MARCA(Object pCODIGOMARCA) {
		P_CODIGO_MARCA = pCODIGOMARCA;
	}
	public Object getP_ID_VEHICULO() {
		return P_ID_VEHICULO;
	}
	public void setP_ID_VEHICULO(Object pIDVEHICULO) {
		P_ID_VEHICULO = pIDVEHICULO;
	}
	public Object getP_FABRICANTE() {
		return P_FABRICANTE;
	}
	public void setP_FABRICANTE(Object pFABRICANTE) {
		P_FABRICANTE = pFABRICANTE;
	}
	public Object getP_TIPO() {
		return P_TIPO;
	}
	public void setP_TIPO(Object pTIPO) {
		P_TIPO = pTIPO;
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
	public void setP_VERSION(Object pVERSION) {
		P_VERSION = pVERSION;
	}
	public Object getP_CAT_HOMOLOGACION() {
		return P_CAT_HOMOLOGACION;
	}
	public void setP_CAT_HOMOLOGACION(Object pCATHOMOLOGACION) {
		P_CAT_HOMOLOGACION = pCATHOMOLOGACION;
	}
	public Object getP_CONSUMO() {
		return P_CONSUMO;
	}
	public void setP_CONSUMO(Object pCONSUMO) {
		P_CONSUMO = pCONSUMO;
	}
	public Object getP_MASA_MOM() {
		return P_MASA_MOM;
	}
	public void setP_MASA_MOM(Object pMASAMOM) {
		P_MASA_MOM = pMASAMOM;
	}
	public Object getP_PROCEDENCIA() {
		return P_PROCEDENCIA;
	}
	public void setP_PROCEDENCIA(Object pPROCEDENCIA) {
		P_PROCEDENCIA = pPROCEDENCIA;
	}
	public Object getP_DISTANCIA_EJES() {
		return P_DISTANCIA_EJES;
	}
	public void setP_DISTANCIA_EJES(Object pDISTANCIAEJES) {
		P_DISTANCIA_EJES = pDISTANCIAEJES;
	}
	public Object getP_VIA_ANTERIOR() {
		return P_VIA_ANTERIOR;
	}
	public void setP_VIA_ANTERIOR(Object pVIAANTERIOR) {
		P_VIA_ANTERIOR = pVIAANTERIOR;
	}
	public Object getP_VIA_POSTERIOR() {
		return P_VIA_POSTERIOR;
	}
	public void setP_VIA_POSTERIOR(Object pVIAPOSTERIOR) {
		P_VIA_POSTERIOR = pVIAPOSTERIOR;
	}
	public Object getP_TIPO_ALIMENTACION() {
		return P_TIPO_ALIMENTACION;
	}
	public void setP_TIPO_ALIMENTACION(Object pTIPOALIMENTACION) {
		P_TIPO_ALIMENTACION = pTIPOALIMENTACION;
	}
	public Object getP_CONTRASENIA_HOMOLOGACION() {
		return P_CONTRASENIA_HOMOLOGACION;
	}
	public void setP_CONTRASENIA_HOMOLOGACION(Object pCONTRASENIAHOMOLOGACION) {
		P_CONTRASENIA_HOMOLOGACION = pCONTRASENIAHOMOLOGACION;
	}
	public Object getP_NIVEL_EMISION() {
		return P_NIVEL_EMISION;
	}
	public void setP_NIVEL_EMISION(Object pNIVELEMISION) {
		P_NIVEL_EMISION = pNIVELEMISION;
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
	public String getP_VERSION_35() {
		return P_VERSION_35;
	}
	public void setP_VERSION_35(String pVERSION_35) {
		P_VERSION_35 = pVERSION_35;
	}
	public String getP_MMC() {
		return P_MMC;
	}
	public void setP_MMC(String pMMC) {
		P_MMC = pMMC;
	}
	public String getP_POTENCIA_PESO() {
		return P_POTENCIA_PESO;
	}
	public void setP_POTENCIA_PESO(String pPOTENCIAPESO) {
		P_POTENCIA_PESO = pPOTENCIAPESO;
	}
	public String getP_PLAZAS_PIE() {
		return P_PLAZAS_PIE;
	}
	public void setP_PLAZAS_PIE(String pPLAZASPIE) {
		P_PLAZAS_PIE = pPLAZASPIE;
	}
	public String getP_CARROCERIA() {
		return P_CARROCERIA;
	}
	public void setP_CARROCERIA(String pCARROCERIA) {
		P_CARROCERIA = pCARROCERIA;
	}
}