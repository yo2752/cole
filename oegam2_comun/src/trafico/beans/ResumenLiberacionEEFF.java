package trafico.beans;

public class ResumenLiberacionEEFF {
	private String tipoTramite = "";
	private int numLiberadosEEFF = 0;
	private int numFallidosLiberadosEEFF = 0;
	

	
	public ResumenLiberacionEEFF() {
	}
	
	public ResumenLiberacionEEFF(String tipoTramite){
		setTipoTramite(tipoTramite);
	}
	
	public String getTipoTramite() {
		return tipoTramite;
	}

	public void setTipoTramite(String tipoTramite) {
		this.tipoTramite = tipoTramite;
	}

	public int getNumLiberadosEEFF() {
		return numLiberadosEEFF;
	}

	public void setNumLiberadosEEFF(int numLiberadosEEFF) {
		this.numLiberadosEEFF = numLiberadosEEFF;
	}

	public int getNumFallidosLiberadosEEFF() {
		return numFallidosLiberadosEEFF;
	}

	public void setNumFallidosLiberadosEEFF(int numFallidosLiberadosEEFF) {
		this.numFallidosLiberadosEEFF = numFallidosLiberadosEEFF;
	}


	public int addLiberadosEEFF() {
		this.numLiberadosEEFF++;
		return this.numLiberadosEEFF;
	}
	
	public int addFallido() {
		this.numFallidosLiberadosEEFF++;
		return this.numFallidosLiberadosEEFF;
	}
}