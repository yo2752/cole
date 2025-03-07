package procesos.daos;

import trafico.beans.daos.BeanPQGeneral;

public class BeanPQTomarSolicitud extends BeanPQGeneral {

	/*********************************************************************************************************
	 * PROCEDURE: TOMAR_SOLICITUD PARAMETROS:
	 * 
	 * DESCRIPCION:
	 *****************************************************************************************************/
	/*
	 * PROCEDURE TOMAR_SOLICITUD ( P_ID_ENVIO OUT COLA.ID_ENVIO%TYPE, P_PROCESO
	 * IN COLA.PROCESO%TYPE, P_COLA IN COLA.COLA%TYPE, P_FECHA_HORA OUT
	 * COLA.FECHA_HORA%TYPE, P_ESTADO OUT COLA.ESTADO%TYPE, P_N_INTENTO OUT
	 * COLA.N_INTENTO%TYPE, P_TIPO_TRAMITE OUT COLA.TIPO_TRAMITE%TYPE,
	 * P_ID_TRAMITE OUT COLA.ID_TRAMITE%TYPE, P_ID_USUARIO OUT
	 * COLA.ID_USUARIO%TYPE, P_XML_ENVIAR OUT COLA.XML_ENVIAR%TYPE, P_CODE OUT
	 * NUMBER, P_SQLERRM OUT VARCHAR2);
	 */

	private Object P_ID_ENVIO;
	private Object P_PROCESO;
	private Object P_COLA;
	private Object P_FECHA_HORA;
	private Object P_ESTADO;
	private Object P_N_INTENTO;
	private Object P_TIPO_TRAMITE;
	private Object P_ID_TRAMITE;
	private Object P_ID_USUARIO;
	private Object P_XML_ENVIAR;
	private Object P_ID_CONTRATO;
	private String P_NODO;
	
	

	public String getP_NODO() {
		return P_NODO;
	}

	public void setP_NODO(String pNODO) {
		P_NODO = pNODO;
	}

	public Object getP_ID_ENVIO() {
		return P_ID_ENVIO;
	}

	public void setP_ID_ENVIO(Object pIDENVIO) {
		P_ID_ENVIO = pIDENVIO;
	}

	public Object getP_PROCESO() {
		return P_PROCESO;
	}

	public void setP_PROCESO(Object pPROCESO) {
		P_PROCESO = pPROCESO;
	}

	public Object getP_COLA() {
		return P_COLA;
	}

	public void setP_COLA(Object pCOLA) {
		P_COLA = pCOLA;
	}

	public Object getP_FECHA_HORA() {
		return P_FECHA_HORA;
	}

	public void setP_FECHA_HORA(Object pFECHAHORA) {
		P_FECHA_HORA = pFECHAHORA;
	}

	public Object getP_ESTADO() {
		return P_ESTADO;
	}

	public void setP_ESTADO(Object pESTADO) {
		P_ESTADO = pESTADO;
	}

	public Object getP_N_INTENTO() {
		return P_N_INTENTO;
	}

	public void setP_N_INTENTO(Object pNINTENTO) {
		P_N_INTENTO = pNINTENTO;
	}

	public Object getP_TIPO_TRAMITE() {
		return P_TIPO_TRAMITE;
	}

	public void setP_TIPO_TRAMITE(Object pTIPOTRAMITE) {
		P_TIPO_TRAMITE = pTIPOTRAMITE;
	}

	public Object getP_ID_TRAMITE() {
		return P_ID_TRAMITE;
	}

	public void setP_ID_TRAMITE(Object pIDTRAMITE) {
		P_ID_TRAMITE = pIDTRAMITE;
	}

	public Object getP_ID_USUARIO() {
		return P_ID_USUARIO;
	}

	public void setP_ID_USUARIO(Object pIDUSUARIO) {
		P_ID_USUARIO = pIDUSUARIO;
	}

	public Object getP_XML_ENVIAR() {
		return P_XML_ENVIAR;
	}

	public void setP_XML_ENVIAR(Object pXMLENVIAR) {
		P_XML_ENVIAR = pXMLENVIAR;
	}

	public Object getP_ID_CONTRATO() {
		return P_ID_CONTRATO;
	}

	public void setP_ID_CONTRATO(Object p_ID_CONTRATO) {
		P_ID_CONTRATO = p_ID_CONTRATO;
	}
}
