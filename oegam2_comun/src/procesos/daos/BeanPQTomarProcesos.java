package procesos.daos;

import trafico.beans.daos.BeanPQGeneral;

public class BeanPQTomarProcesos extends BeanPQGeneral {

	private String P_NODO;
	private ProcesosCursor C_PROCESOS;

	public String getP_NODO() {
		return P_NODO;
	}

	public void setP_NODO(String pNODO) {
		P_NODO = pNODO;
	}

	public ProcesosCursor getC_PROCESOS() {
		return C_PROCESOS;
	}

	public void setC_PROCESOS(ProcesosCursor cPROCESOS) {
		C_PROCESOS = cPROCESOS;
	}

}
