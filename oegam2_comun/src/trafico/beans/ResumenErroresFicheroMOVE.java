package trafico.beans;

public class ResumenErroresFicheroMOVE {

	private String numExpedienteError;
	private String descripcionError;
	private boolean error;
	private String linea;
	
	
	public boolean isError() {
		return error;
	}
	public void setError(boolean error) {
		this.error = error;
	}
	public String getNumExpedienteError() {
		return numExpedienteError;
	}
	public void setNumExpedienteError(String numExpedienteError) {
		this.numExpedienteError = numExpedienteError;
	}
	public String getDescripcionError() {
		return descripcionError;
	}
	public void setDescripcionError(String descripcionError) {
		this.descripcionError = descripcionError;
	}
	public String getLinea() {
		return linea;
	}
	public void setLinea(String linea) {
		this.linea = linea;
	}
	
	
}
