package procesos.daos;

import trafico.beans.daos.BeanPQGeneral;

public class BeanPQBorrarSolicitud extends BeanPQGeneral {

	/*********************************************************************************************************
	 * PROCEDURE: SOLICITUD_CORRECTA PARAMETROS:
	 * 
	 * DESCRIPCION:
	 *****************************************************************************************************/
	/*
	 * PROCEDURE SOLICITUD_CORRECTA ( P_ID_ENVIO IN NUMBER,
                                  P_CODE OUT NUMBER,
                                  P_SQLERRM OUT VARCHAR2); 
	 */

	private Object P_ID_ENVIO;
	private String P_NODO;
	private String P_RESPUESTA;
	

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

	public String getP_RESPUESTA() {
		return P_RESPUESTA;
	}

	public void setP_RESPUESTA(String pRESPUESTA) {
		P_RESPUESTA = pRESPUESTA;
	}
	
	

}
