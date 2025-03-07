package org.gestoresmadrid.oegam2comun.trafico.view.dto;

import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.oegamComun.interviniente.view.dto.IntervinienteTraficoDto;

import trafico.utiles.enumerados.TipoTramiteTrafico;

public class TramiteTrafInteveDto extends TramiteTrafDto {

	private static final long serialVersionUID = 4836965871615829275L;

	public TramiteTrafInteveDto(){
		super();
		setTipoTramite(TipoTramiteTrafico.Solicitud_Inteve.getValorEnum());
	}
	private IntervinienteTraficoDto solicitante;

	private List<TramiteTrafInteveSolicitudesDto> solicitudes;

	private TramiteTrafInteveSolicitudesDto solicitud;

	public List<TramiteTrafInteveSolicitudesDto> getSolicitudes() {
		return solicitudes;
	}

	public void setSolicitudes(List<TramiteTrafInteveSolicitudesDto> solicitudes) {
		this.solicitudes = solicitudes;
	}

	public TramiteTrafInteveSolicitudesDto getSolicitud() {
		return solicitud;
	}

	public void setSolicitud(TramiteTrafInteveSolicitudesDto solicitud) {
		this.solicitud = solicitud;
	}

	public void addSolicitud(TramiteTrafInteveSolicitudesDto solicitud) {
		if (solicitudes == null) {
			solicitudes = new ArrayList<TramiteTrafInteveSolicitudesDto>();
		}
		solicitudes.add(solicitud);
		solicitud.setTramiteTraficoInteve(this);
	}

	public IntervinienteTraficoDto getSolicitante() {
		return solicitante;
	}

	public void setSolicitante(IntervinienteTraficoDto solicitante) {
		this.solicitante = solicitante;
	}

}