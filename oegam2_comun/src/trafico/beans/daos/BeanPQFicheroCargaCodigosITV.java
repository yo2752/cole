package trafico.beans.daos;


/**********************************************************************************************************
PROCEDURE: CARGA
PARAMETROS:
DESCRIPCON:
********************************************************************************************************
PROCEDURE CARGA (P_NOMITV IN VARCHAR2);
**/

/**
 * Clase BeanPQ para guardar un fichero de codigos ITV.
 */
public class BeanPQFicheroCargaCodigosITV extends BeanPQGeneral{

	private Object P_NOMITV;
	
	public BeanPQFicheroCargaCodigosITV() {
		super();
	}

	public Object getP_NOMITV() {
		return P_NOMITV;
	}

	public void setP_NOMITV(Object pNOMITV) {
		P_NOMITV = pNOMITV;
	}
}
