package trafico.beans;

public class ResumenAsignacionMasivaTasas {

	private String tipoTramite = "";
	private int numTasas = 0;
	private int numFallidos = 0;
	private int numCorrectos = 0;

	public ResumenAsignacionMasivaTasas() {}

	public ResumenAsignacionMasivaTasas(String tipoTramite) {
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
		this.numCorrectos++;
		return this.numCorrectos;
	}

	public int getNumTasas() {
		return numTasas;
	}

	public void setNumTasas(int numTasas) {
		this.numTasas = numTasas;
	}

	public int getNumCorrectos() {
		return numCorrectos;
	}

	public void setNumCorrectos(int numCorrectos) {
		this.numCorrectos = numCorrectos;
	}
}