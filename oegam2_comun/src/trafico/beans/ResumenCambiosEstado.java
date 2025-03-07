package trafico.beans;

public class ResumenCambiosEstado {

	private String tipoTramite = "";
	private int numCambiosEstado = 0;
	private int numFallidos = 0;

	public ResumenCambiosEstado() {}

	public ResumenCambiosEstado(String tipoTramite) {
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
		this.numCambiosEstado++;
		return this.numCambiosEstado;
	}

	public int getNumCambiosEstado() {
		return numCambiosEstado;
	}

	public void setNumCambiosEstado(int numCambiosEstado) {
		this.numCambiosEstado = numCambiosEstado;
	}
}