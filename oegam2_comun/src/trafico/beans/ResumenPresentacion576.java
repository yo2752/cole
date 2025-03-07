package trafico.beans;

public class ResumenPresentacion576 {
	
	private String tipoTramite = "";
	private int numTasasCertificado = 0;
	private int numFallidos = 0;
	
	public ResumenPresentacion576() {

	}

	public ResumenPresentacion576(String tipoTramite){
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

	public int getNumTasasCertificado() {
		return numTasasCertificado;
	}

	public void setNumTasasCertificado(int numTasasCertificado) {
		this.numTasasCertificado = numTasasCertificado;
	}
	
	
}