package procesos.daos;

import trafico.beans.daos.BeanPQGeneral;

public class BeanPQReasignarSolicitud extends BeanPQGeneral {

	/*********************************************************************************************************
	 * PROCEDURE: SOLICITUD_ERRONEA PARAMETROS:
	 * 
	 * DESCRIPCION:
	 *****************************************************************************************************/
	/*
	 * PROCEDURE SOLICITUD_ERRONEA(P_ID_ENVIO IN NUMBER,
                      P_RESPUESTA IN VARCHAR2,
                      P_CODE OUT NUMBER,
                      P_SQLERRM OUT VARCHAR2); 
	 */

	private Object P_ID_ENVIO;
	private Object P_RESPUESTA;
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

	public Object getP_RESPUESTA() {
		return P_RESPUESTA;
	}

	public void setP_RESPUESTA(Object pRESPUESTA) {
		P_RESPUESTA = pRESPUESTA;
	}

	
	
}
