package escrituras.utiles.beans;

public class ExpedientesPersonaBean {
	
	private Long numExpediente;
	private String tipoInterviniente;
	private String descTipoInterv;
	private String nif;
	private String idDireccion;
	
	public Long getNumExpediente() {
		return numExpediente;
	}
	public void setNumExpediente(Long numExpediente) {
		this.numExpediente = numExpediente;
	}
	public String getTipoInterviniente() {
		return tipoInterviniente;
	}
	public void setTipoInterviniente(String tipoInterviniente) {
		this.tipoInterviniente = tipoInterviniente;
	}
	public String getNif() {
		return nif;
	}
	public void setNif(String nif) {
		this.nif = nif;
	}
	public String getIdDireccion() {
		return idDireccion;
	}
	public void setIdDireccion(String idDireccion) {
		this.idDireccion = idDireccion;
	}
	public String getDescTipoInterv() {
		return descTipoInterv;
	}
	public void setDescTipoInterv(String descTipoInterv) {
		this.descTipoInterv = descTipoInterv;
	}
	
}
