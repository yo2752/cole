package org.gestoresmadrid.oegamInteve.view.dto;

import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.oegamComun.interviniente.view.dto.IntervinienteTraficoDto;
import org.gestoresmadrid.oegamComun.trafico.view.dto.TramiteTraficoDto;

public class TramiteTraficoInteveDto extends TramiteTraficoDto {

	private static final long serialVersionUID = -2457686106430428086L;

	private IntervinienteTraficoDto solicitante;

	private IntervinienteTraficoDto representante;

	private TramiteTraficoSolInteveDto solicitudInteve;

	private List<TramiteTraficoSolInteveDto> tramitesInteves;

	public void addListaTramitesInteves(TramiteTraficoSolInteveDto tramite) {
		if (tramitesInteves == null || tramitesInteves.isEmpty()) {
			tramitesInteves = new ArrayList<>();
		}
		tramitesInteves.add(tramite);
	}

	public IntervinienteTraficoDto getSolicitante() {
		return solicitante;
	}

	public void setSolicitante(IntervinienteTraficoDto solicitante) {
		this.solicitante = solicitante;
	}

	public List<TramiteTraficoSolInteveDto> getTramitesInteves() {
		return tramitesInteves;
	}

	public void setTramitesInteves(List<TramiteTraficoSolInteveDto> tramitesInteves) {
		this.tramitesInteves = tramitesInteves;
	}

	public TramiteTraficoSolInteveDto getSolicitudInteve() {
		return solicitudInteve;
	}

	public void setSolicitudInteve(TramiteTraficoSolInteveDto solicitudInteve) {
		this.solicitudInteve = solicitudInteve;
	}

	public IntervinienteTraficoDto getRepresentante() {
		return representante;
	}

	public void setRepresentante(IntervinienteTraficoDto representante) {
		this.representante = representante;
	}
}