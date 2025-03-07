package org.gestoresmadrid.oegam2comun.trafico.view.dto;

import java.io.Serializable;

import org.gestoresmadrid.oegamComun.tasa.view.dto.TasaDto;
import org.gestoresmadrid.oegamComun.vehiculo.view.dto.VehiculoDto;

public class SolicitudInformeVehiculoDto implements Serializable {

	private static final long serialVersionUID = 4389353848296047770L;

	private Long idTramiteSolInfo;

	private TasaDto tasa;

	private String estado;

	private String resultado;

	private String referenciaAtem;

	private String motivoInteve;

	private String tipoInforme;

	private VehiculoDto vehiculo;

	private String matricula;

	private byte[] informe;

	private String bastidor;

	private TramiteTrafSolInfoDto tramiteTrafico;

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

	public String getReferenciaAtem() {
		return referenciaAtem;
	}

	public void setReferenciaAtem(String referenciaAtem) {
		this.referenciaAtem = referenciaAtem;
	}

	public String getMotivoInteve() {
		return motivoInteve;
	}

	public void setMotivoInteve(String motivoInteve) {
		this.motivoInteve = motivoInteve;
	}

	public VehiculoDto getVehiculo() {
		return vehiculo;
	}

	public void setVehiculo(VehiculoDto vehiculo) {
		this.vehiculo = vehiculo;
	}

	public TramiteTrafSolInfoDto getTramiteTrafico() {
		return tramiteTrafico;
	}

	public void setTramiteTraficoSolInfo(TramiteTrafSolInfoDto tramiteTrafico) {
		this.tramiteTrafico = tramiteTrafico;
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

	public String getBastidor() {
		return bastidor;
	}

	public void setBastidor(String bastidor) {
		this.bastidor = bastidor;
	}

	public void setTramiteTrafico(TramiteTrafSolInfoDto tramiteTrafico) {
		this.tramiteTrafico = tramiteTrafico;
	}

	public Long getIdTramiteSolInfo() {
		return idTramiteSolInfo;
	}

	public void setIdTramiteSolInfo(Long idTramiteSolInfo) {
		this.idTramiteSolInfo = idTramiteSolInfo;
	}

	public byte[] getInforme() {
		return informe;
	}

	public void setInforme(byte[] informe) {
		this.informe = informe;
	}

}