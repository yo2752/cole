package trafico.beans;

public class ResumenTramitacionTelematica {
	private String tipoTramite = "";
	private int numTramitadosTelematicamente = 0;
	private int numFallidosTelematicamente = 0;
	

	
	public ResumenTramitacionTelematica() {

	}

	public ResumenTramitacionTelematica(String tipoTramite){
		setTipoTramite(tipoTramite);
	}

	public String getTipoTramite() {
		return tipoTramite;
	}

	public void setTipoTramite(String tipoTramite) {
		this.tipoTramite = tipoTramite;
	}	

	public int getNumTramitadosTelematicamente() {
		return numTramitadosTelematicamente;
	}

	public void setNumTramitadosTelematicamente(int numTramitadosTelematicamente) {
		this.numTramitadosTelematicamente = numTramitadosTelematicamente;
	}

	public int getNumFallidosTelematicamente() {
		return numFallidosTelematicamente;
	}

	public void setNumFallidosTelematicamente(int numFallidosTelematicamente) {
		this.numFallidosTelematicamente = numFallidosTelematicamente;
	}

	public int addTramitadosTelematicamente() {
		this.numTramitadosTelematicamente++;
		return this.numTramitadosTelematicamente;
	}
	
	public int addFallido() {
		this.numFallidosTelematicamente++;
		return this.numFallidosTelematicamente;
	}
}