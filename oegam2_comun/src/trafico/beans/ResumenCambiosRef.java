package trafico.beans;

public class ResumenCambiosRef {

	private String tipoTramite = "";
	private int numCambiosRef = 0;
	private int numFallidos = 0;

	public ResumenCambiosRef() {}

	public ResumenCambiosRef(String tipoTramite) {
		this.tipoTramite = tipoTramite;
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

	public int addCorrecto() {
		this.numCambiosRef++;
		return this.numCambiosRef;
	}

	public int getNumCambiosRef() {
		return numCambiosRef;
	}

	public void setNumCambiosRef(int numCambiosRef) {
		this.numCambiosRef = numCambiosRef;
	}
}