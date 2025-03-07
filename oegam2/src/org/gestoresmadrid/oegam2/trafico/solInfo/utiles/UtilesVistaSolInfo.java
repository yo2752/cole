package org.gestoresmadrid.oegam2.trafico.solInfo.utiles;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.gestoresmadrid.core.model.beans.DatoMaestroBean;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.TipoTasa;
import org.gestoresmadrid.core.trafico.solInfo.model.enumerados.EstadoTramiteSolicitudInformacion;
import org.gestoresmadrid.core.trafico.solInfo.model.enumerados.MotivoInforme;
import org.gestoresmadrid.core.trafico.solInfo.model.enumerados.TipoInforme;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioContrato;
import org.gestoresmadrid.oegam2comun.tasas.model.service.ServicioTasa;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafInteveDto;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafInteveSolicitudesDto;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.oegamComun.tasa.view.dto.TasaDto;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class UtilesVistaSolInfo implements Serializable {

	private static final long serialVersionUID = -7122613282850775292L;

	private static UtilesVistaSolInfo utilesVistaSolInfo;

	private static final ILoggerOegam log = LoggerOegam.getLogger(UtilesVistaSolInfo.class);

	@Autowired
	ServicioContrato servicioContrato;

	@Autowired
	ServicioTasa servicioTasa;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	private UtilesColegiado utilesColegiado;

	public static UtilesVistaSolInfo getInstance() {
		if (utilesVistaSolInfo == null) {
			utilesVistaSolInfo = new UtilesVistaSolInfo();
			SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(utilesVistaSolInfo);
		}
		return utilesVistaSolInfo;
	}

	public Boolean visualizarFacturacion(TramiteTrafInteveDto tramiteInteveDto) {
		if (tramiteInteveDto != null && tramiteInteveDto.getNumExpediente() != null
				&& tramiteInteveDto.getEstado() != null && !tramiteInteveDto.getEstado().isEmpty()
				&& EstadoTramiteSolicitudInformacion.Finalizado_PDF.getValorEnum()
						.equals(tramiteInteveDto.getEstado())) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	public Boolean visualizarResumen(TramiteTrafInteveDto tramiteInteveDto) {
		if (tramiteInteveDto != null && tramiteInteveDto.getNumExpediente() != null
				&& tramiteInteveDto.getEstado() != null && !tramiteInteveDto.getEstado().isEmpty()) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	public Boolean esEstadoGuardable(TramiteTrafInteveDto tramiteInteveDto) {
		if (tramiteInteveDto == null || tramiteInteveDto.getEstado() == null || tramiteInteveDto.getEstado().isEmpty()
				|| EstadoTramiteSolicitudInformacion.Iniciado.getValorEnum().equals(tramiteInteveDto.getEstado())
				|| EstadoTramiteSolicitudInformacion.Finalizado_Con_Error.getValorEnum()
						.equals(tramiteInteveDto.getEstado())
				|| EstadoTramiteSolicitudInformacion.Finalizado_PDF.getValorEnum().equals(tramiteInteveDto.getEstado())
				|| EstadoTramiteSolicitudInformacion.Finalizado_Parcialmente.getValorEnum()
						.equals(tramiteInteveDto.getEstado())) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	public Boolean esEstadoConsultable(TramiteTrafInteveDto tramiteInteveDto) {
		if (tramiteInteveDto != null && tramiteInteveDto.getEstado() != null && !tramiteInteveDto.getEstado().isEmpty()
				&& EstadoTramiteTrafico.Iniciado.getValorEnum().equals(tramiteInteveDto.getEstado())
				&& tramiteInteveDto.getNumExpediente() != null) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	public Boolean esEstadoDescargarXml(TramiteTrafInteveDto tramiteInteveDto) {
		if (tramiteInteveDto != null && tramiteInteveDto.getEstado() != null && !tramiteInteveDto.getEstado().isEmpty()
				&& EstadoTramiteSolicitudInformacion.Pendiente_Respuesta_APP.getValorEnum()
						.equals(tramiteInteveDto.getEstado())) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	public Boolean esEstadoImprimir(TramiteTrafInteveDto tramiteInteveDto) {
		if (tramiteInteveDto != null && tramiteInteveDto.getEstado() != null && !tramiteInteveDto.getEstado().isEmpty()
				&& (EstadoTramiteSolicitudInformacion.Finalizado_PDF.getValorEnum().equals(tramiteInteveDto.getEstado())
						|| EstadoTramiteSolicitudInformacion.Finalizado_Parcialmente.getValorEnum()
								.equals(tramiteInteveDto.getEstado()))) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	public Boolean esEstadoReiniciar(TramiteTrafInteveDto tramiteInteveDto) {
		if (tramiteInteveDto != null && tramiteInteveDto.getEstado() != null && !tramiteInteveDto.getEstado().isEmpty()
				&& EstadoTramiteSolicitudInformacion.Finalizado_Parcialmente.getValorEnum()
						.equals(tramiteInteveDto.getEstado())) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	public EstadoTramiteTrafico[] getEstados() {
		return EstadoTramiteTrafico.values();
	}

	public EstadoTramiteSolicitudInformacion[] getEstadoSolInfoVehiculo() {
		return EstadoTramiteSolicitudInformacion.values();
	}

	public Boolean esAdmin() {
		return utilesColegiado.tienePermisoAdmin();
	}

	public List<DatoMaestroBean> getComboContratosHabilitados() {
		return servicioContrato.getComboContratosHabilitados();
	}

	public List<TasaDto> getListaTasas(TramiteTrafInteveDto tramiteInteveDto) {
		if (tramiteInteveDto != null && tramiteInteveDto.getContrato() != null
				&& tramiteInteveDto.getContrato().getIdContrato() != null) {
			List<TasaDto> listaTasas = servicioTasa.obtenerTasasContrato(
					tramiteInteveDto.getContrato().getIdContrato().longValue(), TipoTasa.CuatroUno.getValorEnum());
			if (listaTasas == null || listaTasas.isEmpty()) {
				listaTasas = new ArrayList<>();
			}
			if ((tramiteInteveDto.getSolicitud() != null && tramiteInteveDto.getSolicitud().getTasa() != null
					&& tramiteInteveDto.getSolicitud().getTasa().getCodigoTasa() != null
					&& !tramiteInteveDto.getSolicitud().getTasa().getCodigoTasa().isEmpty())
					&& !listaTasas.contains(tramiteInteveDto.getSolicitud().getTasa())) {
				listaTasas.add(0, tramiteInteveDto.getSolicitud().getTasa());
			}
			return listaTasas;
		}
		return Collections.emptyList();
	}

	public MotivoInforme[] getMotivoInforme() {
		return MotivoInforme.values();
	}

	public TipoInforme[] getTipoInforme() {
		return TipoInforme.values();
	}

	public String getDescripcionEstado(TramiteTrafInteveDto tramiteTrafSolInfoDto) {
		if (tramiteTrafSolInfoDto != null && tramiteTrafSolInfoDto.getEstado() != null
				&& !tramiteTrafSolInfoDto.getEstado().isEmpty()) {
			return EstadoTramiteSolicitudInformacion.convertirTexto(tramiteTrafSolInfoDto.getEstado());
		}
		return "";
	}

	public String convertirFechaPresentacion(TramiteTrafInteveDto tramiteTrafSolInfoDto) {
		try {
			if (tramiteTrafSolInfoDto != null && tramiteTrafSolInfoDto.getFechaPresentacion() != null
					&& tramiteTrafSolInfoDto.getFechaPresentacion().getFecha() != null) {
				return utilesFecha.formatoFecha(tramiteTrafSolInfoDto.getFechaPresentacion());
			}
		} catch (ParseException e) {
			log.error("Ha sucedido un error a la hora de convertir la fecha de presentacion para la solicitud: "
					+ tramiteTrafSolInfoDto.getNumExpediente() + ", error: ", e);
		}
		return "";
	}

	public String getDescripcionContrato(TramiteTrafInteveDto tramiteTrafSolInfoDto) {
		String descContrato = "";
		if (tramiteTrafSolInfoDto != null && tramiteTrafSolInfoDto.getContrato() != null
				&& tramiteTrafSolInfoDto.getContrato().getColegiadoDto() != null
				&& tramiteTrafSolInfoDto.getContrato().getProvinciaDto() != null) {
			descContrato = tramiteTrafSolInfoDto.getContrato().getColegiadoDto().getNumColegiado() + " - "
					+ tramiteTrafSolInfoDto.getContrato().getProvinciaDto().getNombre() + " - "
					+ tramiteTrafSolInfoDto.getContrato().getVia();
		}
		return descContrato;
	}

	public Boolean existeListaSolInfoVehiculos(TramiteTrafInteveDto tramiteTrafSolInfoDto) {
		if (tramiteTrafSolInfoDto != null && tramiteTrafSolInfoDto.getSolicitudes() != null
				&& !tramiteTrafSolInfoDto.getSolicitudes().isEmpty()) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	public Boolean estadoIniciado(TramiteTrafInteveDto tramiteTrafSolInfoDto) {
		if (tramiteTrafSolInfoDto != null && tramiteTrafSolInfoDto.getSolicitudes() != null
				&& !tramiteTrafSolInfoDto.getSolicitudes().isEmpty() && tramiteTrafSolInfoDto.getEstado()
						.equals(EstadoTramiteSolicitudInformacion.Iniciado.getValorEnum())) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	public Boolean sePuedeDescargarZip(TramiteTrafInteveDto tramiteTrafSolInfoDto) {
		if (tramiteTrafSolInfoDto != null && tramiteTrafSolInfoDto.getSolicitudes() != null
				&& !tramiteTrafSolInfoDto.getSolicitudes().isEmpty()) {
			for (TramiteTrafInteveSolicitudesDto solicitud : tramiteTrafSolInfoDto.getSolicitudes()) {
				if (!solicitud.getEstado().equals(EstadoTramiteSolicitudInformacion.Recibido.getValorEnum())) {
					return Boolean.TRUE;
				}
			}
		}
		return Boolean.FALSE;
	}

}