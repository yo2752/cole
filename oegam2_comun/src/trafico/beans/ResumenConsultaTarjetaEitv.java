package trafico.beans;

public class ResumenConsultaTarjetaEitv {
	
	private String tipoTramite = "";
	private int consultasEitvEnCola = 0;
	private int numFallidos = 0;
	
	public ResumenConsultaTarjetaEitv() {

	}

	public ResumenConsultaTarjetaEitv(String tipoTramite){
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

	public int getConsultasEitvEnCola() {
		return consultasEitvEnCola;
	}

	public void setConsultasEitvEnCola(int consultasEitvEnCola) {
		this.consultasEitvEnCola = consultasEitvEnCola;
	}
	
	
}