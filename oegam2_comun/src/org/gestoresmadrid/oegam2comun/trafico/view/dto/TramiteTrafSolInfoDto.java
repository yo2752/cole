package org.gestoresmadrid.oegam2comun.trafico.view.dto;

import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.oegamComun.interviniente.view.dto.IntervinienteTraficoDto;

import trafico.utiles.enumerados.TipoTramiteTrafico;

public class TramiteTrafSolInfoDto extends TramiteTrafDto {

	private static final long serialVersionUID = 4516417902364546962L;

	private IntervinienteTraficoDto solicitante;

	private SolicitudInformeVehiculoDto solInfoVehiculo;

	private List<SolicitudInformeVehiculoDto> solicitudes;

	public TramiteTrafSolInfoDto() {
		super();
		setTipoTramite(TipoTramiteTrafico.Solicitud_Inteve.getValorEnum());
	}

	public IntervinienteTraficoDto getSolicitante() {
		return solicitante;
	}

	public void setSolicitante(IntervinienteTraficoDto solicitante) {
		this.solicitante = solicitante;
	}

	public List<SolicitudInformeVehiculoDto> getSolicitudes() {
		return solicitudes;
	}

	public void setSolicitudes(List<SolicitudInformeVehiculoDto> solicitudes) {
		this.solicitudes = solicitudes;
	}

	public void addSolicitud(SolicitudInformeVehiculoDto solicitud) {
		if (solicitudes == null) {
			solicitudes = new ArrayList<SolicitudInformeVehiculoDto>();
		}
		solicitudes.add(solicitud);
		solicitud.setTramiteTraficoSolInfo(this);
	}

	public SolicitudInformeVehiculoDto getSolInfoVehiculo() {
		return solInfoVehiculo;
	}

	public void setSolInfoVehiculo(SolicitudInformeVehiculoDto solInfoVehiculo) {
		this.solInfoVehiculo = solInfoVehiculo;
	}

}