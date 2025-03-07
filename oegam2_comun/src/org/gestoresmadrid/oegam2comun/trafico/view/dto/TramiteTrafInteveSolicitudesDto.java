package org.gestoresmadrid.oegam2comun.trafico.view.dto;

import java.io.Serializable;

import org.gestoresmadrid.oegamComun.tasa.view.dto.TasaDto;

public class TramiteTrafInteveSolicitudesDto implements Serializable {

	private static final long serialVersionUID = -2801977614143689415L;

	private Long idTramiteSolInfo;

	private TasaDto tasa;

	private String estado;

	private String resultado;

	private String referencia;

	private String motivoInforme;

	private String tipoInforme;

	private String matricula;

	private byte[] informe;

	private String bastidor;

	private String xml;

	private Long idContrato;

	private TramiteTrafInteveDto tramiteTraficoInteve;

	public Long getIdTramiteSolInfo() {
		return idTramiteSolInfo;
	}

	public void setIdTramiteSolInfo(Long idTramiteSolInfo) {
		this.idTramiteSolInfo = idTramiteSolInfo;
	}

	public TasaDto getTasa() {
		return tasa;
	}

	public void setTasa(TasaDto tasa) {
		this.tasa = tasa;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getResultado() {
		return resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public String getMotivoInforme() {
		return motivoInforme;
	}

	public void setMotivoInforme(String motivoInforme) {
		this.motivoInforme = motivoInforme;
	}

	public String getTipoInforme() {
		return tipoInforme;
	}

	public void setTipoInforme(String tipoInforme) {
		this.tipoInforme = tipoInforme;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public byte[] getInforme() {
		return informe;
	}

	public void setInforme(byte[] informe) {
		this.informe = informe;
	}

	public String getBastidor() {
		return bastidor;
	}

	public void setBastidor(String bastidor) {
		this.bastidor = bastidor;
	}

	public TramiteTrafInteveDto getTramiteTraficoInteve() {
		return tramiteTraficoInteve;
	}

	public void setTramiteTraficoInteve(TramiteTrafInteveDto tramiteTraficoInteve) {
		this.tramiteTraficoInteve = tramiteTraficoInteve;
	}

	public String getXml() {
		return xml;
	}

	public void setXml(String xml) {
		this.xml = xml;
	}

	public Long getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Long idContrato) {
		this.idContrato = idContrato;
	}

}