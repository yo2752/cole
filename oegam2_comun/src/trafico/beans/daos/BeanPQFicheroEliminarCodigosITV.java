package trafico.beans.daos;


/**********************************************************************************************************
PROCEDURE: ELIMINAR
PARAMETROS:
DESCRIPCON:
********************************************************************************************************
PROCEDURE ELIMINAR (P_NOMITV IN VARCHAR2);
**/

/**
 * Clase BeanPQ para guardar un fichero de codigos ITV.
 */
public class BeanPQFicheroEliminarCodigosITV extends BeanPQGeneral{

	private Object P_NOMITV;
	
	public BeanPQFicheroEliminarCodigosITV() {
		super();
	}

	public Object getP_NOMITV() {
		return P_NOMITV;
	}

	public void setP_NOMITV(Object pNOMITV) {
		P_NOMITV = pNOMITV;
	}
}
