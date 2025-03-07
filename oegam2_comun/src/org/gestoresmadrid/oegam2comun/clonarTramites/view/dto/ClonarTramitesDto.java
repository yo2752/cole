package org.gestoresmadrid.oegam2comun.clonarTramites.view.dto;

public class ClonarTramitesDto {

	private String numExpediente;
	private String tipoTramite;
	private String estado;
	private String bastidor;
	private ResultClonarTramites result;

	public String getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(String numExpediente) {
		this.numExpediente = numExpediente;
	}

	public String getTipoTramite() {
		return tipoTramite;
	}

	public void setTipoTramite(String tipoTramite) {
		this.tipoTramite = tipoTramite;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public ResultClonarTramites getResult() {
		return result;
	}

	public void setResult(ResultClonarTramites result) {
		this.result = result;
	}

	public String getBastidor() {
		return bastidor;
	}

	public void setBastidor(String bastidor) {
		this.bastidor = bastidor;
	}
}