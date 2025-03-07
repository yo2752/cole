package trafico.beans.daos;

public class BeanPQDireccionViaEquivalente extends BeanPQGeneral{
	private String P_ID_TIPO_VIA;
	private String P_ID_PROVINCIA;
	private String P_ID_MUNICIPIO; 
	private String P_ORGANISMO;
	private String P_VIA_EQUI;
	private String P_DESCRIPCION;
	private String P_PROVINCIA;
	private String P_ID_MUNICIPIO_CAM; 
	private String P_NOMBRE;
	
	public BeanPQDireccionViaEquivalente() {
		super();
	}

	
	public String getP_ID_TIPO_VIA() {
		return P_ID_TIPO_VIA;
	}


	public void setP_ID_TIPO_VIA(String pIDTIPOVIA) {
		P_ID_TIPO_VIA = pIDTIPOVIA;
	}


	public String getP_ID_PROVINCIA() {
		return P_ID_PROVINCIA;
	}


	public void setP_ID_PROVINCIA(String pIDPROVINCIA) {
		P_ID_PROVINCIA = pIDPROVINCIA;
	}


	public String getP_ID_MUNICIPIO() {
		return P_ID_MUNICIPIO;
	}


	public void setP_ID_MUNICIPIO(String pIDMUNICIPIO) {
		P_ID_MUNICIPIO = pIDMUNICIPIO;
	}


	public String getP_ORGANISMO() {
		return P_ORGANISMO;
	}


	public void setP_ORGANISMO(String pORGANISMO) {
		P_ORGANISMO = pORGANISMO;
	}


	public String getP_VIA_EQUI() {
		return P_VIA_EQUI;
	}

	public void setP_VIA_EQUI(String pVIAEQUI) {
		P_VIA_EQUI = pVIAEQUI;
	}
	

	public String getP_DESCRIPCION() {
		return P_DESCRIPCION;
	}


	public void setP_DESCRIPCION(String pDESCRIPCION) {
		P_DESCRIPCION = pDESCRIPCION;
	}


	public String getP_PROVINCIA() {
		return P_PROVINCIA;
	}

	public void setP_PROVINCIA(String pPROVINCIA) {
		P_PROVINCIA = pPROVINCIA;
	}

	public String getP_ID_MUNICIPIO_CAM() {
		return P_ID_MUNICIPIO_CAM;
	}

	public void setP_ID_MUNICIPIO_CAM(String pIDMUNICIPIOCAM) {
		P_ID_MUNICIPIO_CAM = pIDMUNICIPIOCAM;
	}

	public String getP_NOMBRE() {
		return P_NOMBRE;
	}

	public void setP_NOMBRE(String pNOMBRE) {
		P_NOMBRE = pNOMBRE;
	}
	
}
