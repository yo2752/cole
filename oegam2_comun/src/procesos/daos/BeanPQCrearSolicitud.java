package procesos.daos;

import trafico.beans.daos.BeanPQGeneral;

public class BeanPQCrearSolicitud extends BeanPQGeneral {

	/*********************************************************************************************************
	 * PROCEDURE:CREAR_SOLICITUD PARAMETROS: DESCRIPCION:
	 *****************************************************************************************************/

	private Object P_ID_ENVIO;
	private Object P_PROCESO;
	private Object P_COLA;
	private Object P_FECHA_HORA;
	private Object P_TIPO_TRAMITE;
	private Object P_ID_TRAMITE;
	private Object P_ID_USUARIO;
	private Object P_XML_ENVIAR;
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

	
	
	
}
