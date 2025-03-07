package trafico.beans.daos;


/**********************************************************************************************************
PROCEDURE: DESCRIPCIONES
PARAMETROS:
DESCRIPCON:
********************************************************************************************************
PROCEDURE DESCRIPCIONES (P_ID_DIRECCION IN DIRECCION.ID_DIRECCION%TYPE,
                             P_ID_PROVINCIA OUT DIRECCION.ID_PROVINCIA%TYPE,
                             P_PROV_NOMBRE OUT PROVINCIA.NOMBRE%TYPE,
                             P_ID_MUNICIPIO OUT DIRECCION.ID_MUNICIPIO%TYPE,
                             P_MUNI_NOMBRE OUT MUNICIPIO.NOMBRE%TYPE,
                             P_ID_TIPO_VIA OUT DIRECCION.ID_TIPO_VIA%TYPE,
                             P_TIP_VIA_NOMBRE OUT TIPO_VIA.NOMBRE%TYPE,
                             P_CODE OUT NUMBER,
                             P_SQLERRM OUT VARCHAR2);
                   
                   **/
/**
 * Clase BeanPQ para las descripciones de todos los campos que se necesitan para las impresiones.
 */
public class BeanPQDescripcionesDireccion extends BeanPQGeneral{
	private Object P_ID_DIRECCION; 
	private Object P_ID_PROVINCIA;
	private Object P_PROV_NOMBRE;
	private Object P_ID_MUNICIPIO;
	private Object P_MUNI_NOMBRE;
	private Object P_ID_TIPO_VIA;
	private Object P_TIP_VIA_NOMBRE;
	
	public BeanPQDescripcionesDireccion() {
		super();
	}

	public Object getP_ID_DIRECCION() {
		return P_ID_DIRECCION;
	}

	public void setP_ID_DIRECCION(Object pIDDIRECCION) {
		P_ID_DIRECCION = pIDDIRECCION;
	}

	public Object getP_ID_PROVINCIA() {
		return P_ID_PROVINCIA;
	}

	public void setP_ID_PROVINCIA(Object pIDPROVINCIA) {
		P_ID_PROVINCIA = pIDPROVINCIA;
	}

	public Object getP_PROV_NOMBRE() {
		return P_PROV_NOMBRE;
	}

	public void setP_PROV_NOMBRE(Object pPROVNOMBRE) {
		P_PROV_NOMBRE = pPROVNOMBRE;
	}

	public Object getP_ID_MUNICIPIO() {
		return P_ID_MUNICIPIO;
	}

	public void setP_ID_MUNICIPIO(Object pIDMUNICIPIO) {
		P_ID_MUNICIPIO = pIDMUNICIPIO;
	}

	public Object getP_MUNI_NOMBRE() {
		return P_MUNI_NOMBRE;
	}

	public void setP_MUNI_NOMBRE(Object pMUNINOMBRE) {
		P_MUNI_NOMBRE = pMUNINOMBRE;
	}

	public Object getP_ID_TIPO_VIA() {
		return P_ID_TIPO_VIA;
	}

	public void setP_ID_TIPO_VIA(Object pIDTIPOVIA) {
		P_ID_TIPO_VIA = pIDTIPOVIA;
	}

	public Object getP_TIP_VIA_NOMBRE() {
		return P_TIP_VIA_NOMBRE;
	}

	public void setP_TIP_VIA_NOMBRE(Object pTIPVIANOMBRE) {
		P_TIP_VIA_NOMBRE = pTIPVIANOMBRE;
	}
}
