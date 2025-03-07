package org.gestoresmadrid.oegam2comun.trafico.view.dto;

import org.gestoresmadrid.oegamComun.interviniente.view.dto.IntervinienteTraficoDto;

public class TramiteTrafCambioServicioDto extends TramiteTrafDto {

	private static final long serialVersionUID = 7741858492378986728L;

	private String motivoCambioServicio;

	private Boolean imprPermisoCirculacion;

	private IntervinienteTraficoDto titular;

	private IntervinienteTraficoDto representanteTitular;

	public TramiteTrafCambioServicioDto() {
	}

	public TramiteTrafCambioServicioDto(TramiteTrafDto tramite) {
		setRefPropia(tramite.getRefPropia());
		setVehiculoDto(tramite.getVehiculoDto());
		setJefaturaTraficoDto(tramite.getJefaturaTraficoDto());
		setNumColegiado(tramite.getNumColegiado());
		setNumExpediente(tramite.getNumExpediente());
	}

	public IntervinienteTraficoDto getTitular() {
		return titular;
	}

	public void setTitular(IntervinienteTraficoDto titular) {
		this.titular = titular;
	}

	public IntervinienteTraficoDto getRepresentanteTitular() {
		return representanteTitular;
	}

	public void setRepresentanteTitular(IntervinienteTraficoDto representanteTitular) {
		this.representanteTitular = representanteTitular;
	}

	public String getMotivoCambioServicio() {
		return motivoCambioServicio;
	}

	public void setMotivoCambioServicio(String motivoCambioServicio) {
		this.motivoCambioServicio = motivoCambioServicio;
	}

	public Boolean getImprPermisoCirculacion() {
		return imprPermisoCirculacion;
	}

	public void setImprPermisoCirculacion(Boolean imprPermisoCirculacion) {
		this.imprPermisoCirculacion = imprPermisoCirculacion;
	}

}