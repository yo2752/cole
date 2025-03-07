package org.gestoresmadrid.oegam2comun.datosSensibles.views.dto;


public class ImportarBastidorDto {

	private String tipoImportado;
	private String tipoRegistro;
	private String fechaEnvio;
	private String bastidor;
	
	public String getTipoImportado() {
		return tipoImportado;
	}
	public void setTipoImportado(String tipoImportado) {
		this.tipoImportado = tipoImportado;
	}
	public String getTipoRegistro() {
		return tipoRegistro;
	}
	public void setTipoRegistro(String tipoRegistro) {
		this.tipoRegistro = tipoRegistro;
	}
	public String getFechaEnvio() {
		return fechaEnvio;
	}
	public void setFechaEnvio(String fechaEnvio) {
		this.fechaEnvio = fechaEnvio;
	}
	public String getBastidor() {
		return bastidor;
	}
	public void setBastidor(String bastidor) {
		this.bastidor = bastidor;
	}
}
