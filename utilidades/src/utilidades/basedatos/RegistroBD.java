package utilidades.basedatos;

public class RegistroBD {
	
	private String CIF_CONTRATO="";
	
	private String RAZON_SOCIAL_CONTRATO="";
	public String getCIF_CONTRATO() {
		return CIF_CONTRATO;
	}
	public void setCIF_CONTRATO(String cIFCONTRATO) {
		CIF_CONTRATO = cIFCONTRATO;
	}
	public String getRAZON_SOCIAL() {
		return RAZON_SOCIAL_CONTRATO;
	}
	public void setRAZON_SOCIAL_CONTRATO(String rAZONSOCIAL) {
		RAZON_SOCIAL_CONTRATO = rAZONSOCIAL;
	}

	public void setRegistro(RegistroBD regBD) {
		return;
	}
}
