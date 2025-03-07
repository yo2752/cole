package trafico.beans;

public class ResumenPendienteExcel {
	private String tipoTramite = "";
	private int numPendientesExcel = 0;
	private int numFallidos = 0;
	

	
	public ResumenPendienteExcel() {

	}

	public ResumenPendienteExcel(String tipoTramite){
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
	
	public int getNumPendientesExcel() {
		return numPendientesExcel;
	}

	public void setNumPendientesExcel(int numPendientesExcel) {
		this.numPendientesExcel = numPendientesExcel;
	}
	
	public int addPendientesExcel() {
	this.numPendientesExcel++;
		return this.numPendientesExcel;
	}
}