package trafico.beans;

public class ResumenValidacion {
	private String tipoTramite = "";
	private int numValidadosPDF = 0;
	private int numValidadosTele = 0;
	private int numFallidos = 0;
	

	
	public ResumenValidacion() {

	}

	public ResumenValidacion(String tipoTramite){
		this.tipoTramite=tipoTramite;
	}

	public String getTipoTramite() {
		return tipoTramite;
	}

	public void setTipoTramite(String tipoTramite) {
		this.tipoTramite = tipoTramite;
	}

	public int getNumValidadosPDF() {
		return numValidadosPDF;
	}

	public void setNumValidadosPDF(int numValidadosPDF) {
		this.numValidadosPDF = numValidadosPDF;
	}

	public int getNumValidadosTele() {
		return numValidadosTele;
	}

	public void setNumValidadosTele(int numValidadosTele) {
		this.numValidadosTele = numValidadosTele;
	}
	
	public int getNumFallidos() {
		return numFallidos;
	}

	public void setNumFallidos(int numFallidos) {
		this.numFallidos = numFallidos;
	}

	public int addValidadoPDF() {
		this.numValidadosPDF++;
		return this.numValidadosPDF;
	}
	
	public int addValidadoTelematicamente() {
		this.numValidadosTele++;
		return this.numValidadosTele;
	}
	
	public int addFallido() {
		this.numFallidos++;
		return this.numFallidos;
	}
}