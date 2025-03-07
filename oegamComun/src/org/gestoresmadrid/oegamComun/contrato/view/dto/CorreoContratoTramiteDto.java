package org.gestoresmadrid.oegamComun.contrato.view.dto;

import java.io.Serializable;

import org.gestoresmadrid.oegamComun.trafico.view.dto.TipoTramiteDto;

public class CorreoContratoTramiteDto implements Serializable {

	private static final long serialVersionUID = 1565389889644551306L;

	private Long idCorreo;

	private Long idContrato;

	private String idTipoTramite;

	private TipoTramiteDto tipoTramite;

	private String correoElectronico;

	private String enviarCorreoImpresion;

	private String tipoImpresion;

	public CorreoContratoTramiteDto() {}

	public Long getIdCorreo() {
		return idCorreo;
	}

	public void setIdCorreo(Long idCorreo) {
		this.idCorreo = idCorreo;
	}

	public Long getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Long idContrato) {
		this.idContrato = idContrato;
	}

	public String getIdTipoTramite() {
		return idTipoTramite;
	}

	public void setIdTipoTramite(String idTipoTramite) {
		this.idTipoTramite = idTipoTramite;
	}

	public String getCorreoElectronico() {
		return correoElectronico;
	}

	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}

	public TipoTramiteDto getTipoTramite() {
		return tipoTramite;
	}

	public void setTipoTramite(TipoTramiteDto tipoTramite) {
		this.tipoTramite = tipoTramite;
	}

	public String getEnviarCorreoImpresion() {
		return enviarCorreoImpresion;
	}

	public void setEnviarCorreoImpresion(String enviarCorreoImpresion) {
		this.enviarCorreoImpresion = enviarCorreoImpresion;
	}

	public String getTipoImpresion() {
		return tipoImpresion;
	}

	public void setTipoImpresion(String tipoImpresion) {
		this.tipoImpresion = tipoImpresion;
	}

}