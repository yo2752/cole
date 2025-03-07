package trafico.beans;

public class ResumenCambiosNive {
	private String tipoTramite = "";
	private int numCambiosNive = 0;
	private int numFallidos = 0;
	
	public ResumenCambiosNive() {

	}

	public ResumenCambiosNive(String tipoTramite){
		this.tipoTramite=tipoTramite;
	}

	public String getTipoTramite() {
		return tipoTramite;
	}

	public void setTipoTramite(String tipoTramite) {
		this.tipoTramite = tipoTramite;
	}
	
	public int getNumFallidos() {
		return numFallidos;
	}

	public void setNumFallidos(int numFallidos) {
		this.numFallidos = numFallidos;
	}

	
	public int addFallido() {
		this.numFallidos++;
		return this.numFallidos;
	}

	public int getNumCambiosNive() {
		return numCambiosNive;
	}

	public void setNumCambiosNive(int numCambiosNive) {
		this.numCambiosNive = numCambiosNive;
	}	
	
}


