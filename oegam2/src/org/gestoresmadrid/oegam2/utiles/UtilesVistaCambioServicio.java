package org.gestoresmadrid.oegam2.utiles;

import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafCambioServicioDto;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class UtilesVistaCambioServicio {

	private static ILoggerOegam log = LoggerOegam.getLogger(UtilesVistaCambioServicio.class);

	private static UtilesVistaCambioServicio utilesVistaCambioServicio;

	@Autowired
	private UtilesColegiado utilesColegiado;

	public static UtilesVistaCambioServicio getInstance() {
		if (utilesVistaCambioServicio == null) {
			utilesVistaCambioServicio = new UtilesVistaCambioServicio();
			SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(utilesVistaCambioServicio);
		}
		return utilesVistaCambioServicio;
	}

	public boolean esConsultableOGuardableCambioServicio(TramiteTrafCambioServicioDto tramiteCambServ) {
		if (!utilesColegiado.tienePermisoEspecial()) {
			return (tramiteCambServ.getEstado() == null || tramiteCambServ.getEstado().isEmpty() || tramiteCambServ.getEstado().equals(EstadoTramiteTrafico.Iniciado.getValorEnum())
					|| tramiteCambServ.getEstado().equals(EstadoTramiteTrafico.No_Tramitable.getValorEnum())
					|| tramiteCambServ.getEstado().equals(EstadoTramiteTrafico.Tramitable_Incidencias.getValorEnum())
					|| tramiteCambServ.getEstado().equals(EstadoTramiteTrafico.Tramitable.getValorEnum())
					|| tramiteCambServ.getEstado().equals(EstadoTramiteTrafico.Validado_Telematicamente.getValorEnum())
					|| tramiteCambServ.getEstado().equals(EstadoTramiteTrafico.Validado_PDF.getValorEnum())
					|| tramiteCambServ.getEstado().equals(EstadoTramiteTrafico.Finalizado_Con_Error.getValorEnum())
					|| tramiteCambServ.getEstado().equals(EstadoTramiteTrafico.Finalizado_Excel_Incidencia.getValorEnum()));
		}
		return false;
	}

	public boolean esValidable(TramiteTrafCambioServicioDto tramiteCambServ) {
		return (!utilesColegiado.tienePermisoEspecial() && tramiteCambServ.getEstado() != null
				&& !tramiteCambServ.getEstado().equals("")
				&& (tramiteCambServ.getEstado().equals(EstadoTramiteTrafico.Iniciado.getValorEnum()) || tramiteCambServ.getEstado().equals(
						EstadoTramiteTrafico.Finalizado_Con_Error.getValorEnum())));
	}

	public boolean esEnvioExcelCambioServicio(TramiteTrafCambioServicioDto tramiteCambServ) {
		if (!utilesColegiado.tienePermisoEspecial()) {
			if (EstadoTramiteTrafico.Validado_PDF.getValorEnum().equals(tramiteCambServ.getEstado())) {
				return utilesColegiado.esColegiadoEnvioExcel();
			}
			return false;
		}
		return false;
	}
}