package trafico.beans.daos;


/**********************************************************************************************************
PROCEDURE: GUARDAR
PARAMETROS:
DESCRIPCON:
********************************************************************************************************
PROCEDURE GUARDAR (P_CODIGO_ITV IN OUT DGT_CODIGO_ITV.CODIGO_ITV%TYPE,
                         P_BASTIDOR IN OUT DGT_CODIGO_ITV.BASTIDOR%TYPE,
                         P_TIPO_VEHICULO_INDUSTRIA IN OUT DGT_CODIGO_ITV.TIPO_VEHICULO_INDUSTRIA%TYPE,
                         P_TIPO_VEHICULO_TRAFICO IN OUT DGT_CODIGO_ITV.TIPO_VEHICULO_TRAFICO%TYPE,
                         P_MARCA IN OUT DGT_CODIGO_ITV.MARCA%TYPE,
                         P_MODELO IN OUT DGT_CODIGO_ITV.MODELO%TYPE,
                         P_TARA IN OUT DGT_CODIGO_ITV.TARA%TYPE,
                         P_MMA IN OUT DGT_CODIGO_ITV.MMA%TYPE,
                         P_PLAZAS IN OUT DGT_CODIGO_ITV.PLAZAS%TYPE,
                         P_CARBURANTE IN OUT DGT_CODIGO_ITV.CARBURANTE%TYPE,
                         P_CILINDRADA IN OUT DGT_CODIGO_ITV.CILINDRADA%TYPE,
                         P_POTENCIA_FISCAL IN OUT DGT_CODIGO_ITV.POTENCIA_FISCAL%TYPE,
                         P_POTENCIA_REAL IN OUT DGT_CODIGO_ITV.POTENCIA_REAL%TYPE,
                         P_CO2 IN OUT DGT_CODIGO_ITV.CO2%TYPE,
                        -----           
                         P_CODE OUT NUMBER,
                         P_SQLERRM OUT VARCHAR2);
                   
                   **/
/**
 * Clase BeanPQ para guardar un codigo ITV.
 */
public class BeanPQCodigosITV extends BeanPQGeneral{

	private Object P_CODIGO_ITV;
	private Object P_BASTIDOR; 
	private Object P_TIPO_VEHICULO_INDUSTRIA;
	private Object P_TIPO_VEHICULO_TRAFICO;
	private Object P_MARCA;
	private Object P_MODELO;
	private Object P_TARA;
	private Object P_MMA;
	private Object P_PLAZAS;
	private Object P_CARBURANTE;
	private Object P_CILINDRADA;
	private Object P_POTENCIA_FISCAL;
	private Object P_POTENCIA_REAL;
	private Object P_CO2;

	public BeanPQCodigosITV() {
		super();
	}

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
}
