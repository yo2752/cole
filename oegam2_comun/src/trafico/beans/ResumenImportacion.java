package trafico.beans;

public class ResumenImportacion {
	private String tipoTramite = "";
	private String mensajeError="";
	private int guardadosBien = 0;
	private int guardadosMal = 0;
	private Boolean error;
	
	public ResumenImportacion(){		
	}
	
	public ResumenImportacion(String tipoTramite){
		this.tipoTramite=tipoTramite;
	}

	public String getTipoTramite() {
		return tipoTramite;
	}

	public void setTipoTramite(String tipoTramite) {
		this.tipoTramite = tipoTramite;
	}

	public int getGuardadosBien() {
		return guardadosBien;
	}

	public void setGuardadosBien(int guardadosBien) {
		this.guardadosBien = guardadosBien;
	}

	public int getGuardadosMal() {
		return guardadosMal;
	}

	public void setGuardadosMal(int guardadosMal) {
		this.guardadosMal = guardadosMal;
	}
	
	public int addCorrecto() {
		this.guardadosBien++;
		return this.guardadosBien;
	}
	
	public int addIncorrecto() {
		this.guardadosMal++;
		return this.guardadosMal;
	}

	public Boolean getError() {
		return error;
	}

	public void setError(Boolean error) {
		this.error = error;
	}

	public String getMensajeError() {
		return mensajeError;
	}

	public void setMensajeError(String mensajeError) {
		this.mensajeError = mensajeError;
	}
}
