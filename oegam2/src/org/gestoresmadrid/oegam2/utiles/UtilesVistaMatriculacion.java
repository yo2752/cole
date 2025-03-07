package org.gestoresmadrid.oegam2.utiles;

import java.util.Collections;
import java.util.List;

import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.TipoTarjetaITV;
import org.gestoresmadrid.core.trafico.model.vo.EvolucionTramiteTraficoVO;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.model.service.ServicioGestionImpr;
import org.gestoresmadrid.oegam2comun.trafico.eeff.view.dto.LiberacionEEFFDto;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioEvolucionTramite;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafMatrDto;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioDirectivaCee;
import org.gestoresmadrid.oegam2comun.vehiculo.view.dto.DirectivaCeeDto;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import trafico.utiles.enumerados.TipoTramiteTrafico;

public class UtilesVistaMatriculacion {

	private static UtilesVistaMatriculacion utilesVistaMatriculacion;

	@Autowired
	private ServicioDirectivaCee servicioDirectivaCee;

	@Autowired
	ServicioGestionImpr servicioGestionImpr;

	@Autowired
	ServicioEvolucionTramite servicioEvolucionTramite;

	@Autowired
	private UtilesColegiado utilesColegiado;

	public static UtilesVistaMatriculacion getInstance() {
		if (utilesVistaMatriculacion == null) {
			utilesVistaMatriculacion = new UtilesVistaMatriculacion();
			SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(utilesVistaMatriculacion);
		}
		return utilesVistaMatriculacion;
	}

	public List<DirectivaCeeDto> getListaDirectivasCEE(TramiteTrafMatrDto tramiteTrafMatrDto) {
		if (tramiteTrafMatrDto != null && tramiteTrafMatrDto.getVehiculoDto() != null) {
			List<DirectivaCeeDto> lista = servicioDirectivaCee.listadoDirectivaCee(tramiteTrafMatrDto.getVehiculoDto().getCriterioConstruccion());
			if (lista != null && !lista.isEmpty()) {
				return lista;
			}
		}
		return Collections.emptyList();
	}

	public boolean esFacturableLaTasaMatriculacion(TramiteTrafMatrDto tramiteTrafMatr) {
		if (utilesColegiado.tienePermiso(UtilesColegiado.PERMISO_MANTENIMIENTO_MATRICULACION_MATW)
				&& tramiteTrafMatr.getEstado() != null) {
			if (tramiteTrafMatr.getEstado().equals(EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum()) || tramiteTrafMatr.getEstado().equals(EstadoTramiteTrafico.Finalizado_PDF
					.getValorEnum()) || tramiteTrafMatr.getEstado().equals(EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso.getValorEnum())) {
				return true;
			}
		}
		return false;
	}

	public boolean esLiberableEEFF(TramiteTrafMatrDto tramiteTrafico, LiberacionEEFFDto liberacion) {
		if (liberacion != null && ((liberacion.getRealizado() != null && liberacion.getRealizado()) || (liberacion.getExento() != null && liberacion.getExento()))) {
			return false;
		}
		if (utilesColegiado.tienePermisoEspecial() || !utilesColegiado.tienePermiso(UtilesColegiado.PERMISO_MATRICULACION_TELEMATICA) || !utilesColegiado.tienePermisoLiberarEEFF()) {
			return false;
		} else if (tramiteTrafico.getEstado() != null && EstadoTramiteTrafico.Validado_Telematicamente.getValorEnum().equals(tramiteTrafico.getEstado()) && tramiteTrafico.getVehiculoDto() != null
				&& tramiteTrafico.getVehiculoDto().getNive() != null) {
			return true;
		}
		return false;
	}

	public boolean esConsultableOGuardableMatriculacion(TramiteTrafMatrDto tramite) {
		if (tramite == null || tramite.getEstado() == null) {
			return true;
		}
		if (!utilesColegiado.tienePermisoEspecial()) {
			if (utilesColegiado.tienePermiso(UtilesColegiado.PERMISO_MANTENIMIENTO_MATRICULACION_MATW)) {
				EstadoTramiteTrafico estado = EstadoTramiteTrafico.convertir(tramite.getEstado());
				if (estado == null || estado.equals(EstadoTramiteTrafico.Iniciado) || estado.equals(EstadoTramiteTrafico.TramitadoNRE06) || estado.equals(EstadoTramiteTrafico.Validado_PDF) || estado.equals(EstadoTramiteTrafico.Finalizado_Con_Error)
						|| estado.equals(EstadoTramiteTrafico.Validado_Telematicamente) || estado.equals(EstadoTramiteTrafico.LiberadoEEFF) || estado.equals(EstadoTramiteTrafico.Error_Consulta_EITV)
						|| estado.equals(EstadoTramiteTrafico.Consultado_EITV)) {
					return true;
				}
			}
			return false;
		}
		return false;
	}

	public boolean esFinalizadoTelematicamenteImpreso(TramiteTrafMatrDto tramite) {
		return esFinalizadoTelemImpreso(UtilesColegiado.PERMISO_MANTENIMIENTO_MATRICULACION_MATW, tramite);
	}

	private boolean esFinalizadoTelemImpreso(String permisoMantenimiento, TramiteTrafMatrDto tramite) {
		EstadoTramiteTrafico estado = EstadoTramiteTrafico.convertir(tramite.getEstado());
		return esFinalizadoTelemImpreso(permisoMantenimiento, estado);
	}

	private boolean esFinalizadoTelemImpreso(String permisoMantenimiento, EstadoTramiteTrafico estado) {
		if (!utilesColegiado.tienePermisoEspecial()) {
			if (utilesColegiado.tienePermiso(permisoMantenimiento)) {
				if (estado.equals(EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso) || estado.equals(EstadoTramiteTrafico.Finalizado_Telematicamente) || estado.equals(
						EstadoTramiteTrafico.Finalizado_Telematicamente_Revisado)) {
					return true;
				}
			}
			return false;
		}
		return false;
	}

	public Boolean esSuperTelematicoMatriculacion() {
		Boolean esSuperTelematico = servicioGestionImpr.esColegiadoSuperTelematico(utilesColegiado.getIdContratoSessionBigDecimal());
		if (!esSuperTelematico && !utilesColegiado.tienePermisoEspecial()) {
			if (utilesColegiado.tienePermiso(UtilesColegiado.PERMISO_SUPER_TELEMATICO)) {
				return Boolean.TRUE;
			}
			return Boolean.FALSE;
		}
		return esSuperTelematico;
	}

	public boolean esValidableMatriculacion(TramiteTrafMatrDto tramiteTrafico) {
		if (!utilesColegiado.tienePermisoEspecial()) {
			if (utilesColegiado.tienePermiso(UtilesColegiado.PERMISO_MANTENIMIENTO_MATRICULACION_MATW)) {
				if (tramiteTrafico.getEstado() != null && (EstadoTramiteTrafico.Iniciado.getValorEnum().equals(tramiteTrafico.getEstado()) || EstadoTramiteTrafico.TramitadoNRE06.getValorEnum().equals(tramiteTrafico.getEstado()) || EstadoTramiteTrafico.Consultado_EITV.getValorEnum()
						.equals(tramiteTrafico.getEstado()) || EstadoTramiteTrafico.Error_Consulta_EITV.getValorEnum().equals(tramiteTrafico.getEstado()))) {
					return true;
				}
			}
			return false;
		}
		return false;
	}

	public Boolean esValidoIntroMatriculaManual(TramiteTrafMatrDto tramiteTrafico) {
		if (tramiteTrafico != null && tramiteTrafico.getVehiculoDto() != null && (tramiteTrafico.getVehiculoDto().getMatricula() == null || tramiteTrafico.getVehiculoDto().getMatricula().isEmpty())
				&& tramiteTrafico.getEstado() != null && EstadoTramiteTrafico.Finalizado_PDF.getValorEnum().equals(tramiteTrafico.getEstado())) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	public boolean esClonable(TramiteTrafMatrDto tramiteTrafico) {
		boolean esOk = false;
		if (tramiteTrafico != null && tramiteTrafico.getEstado() != null
				&& (EstadoTramiteTrafico.Iniciado.getValorEnum().equals(tramiteTrafico.getEstado()) || EstadoTramiteTrafico.Consultado_EITV.getValorEnum().equals(
					tramiteTrafico.getEstado()) || EstadoTramiteTrafico.Error_Consulta_EITV.getValorEnum().equals(tramiteTrafico.getEstado()))) {
			esOk = true;
		}
		return esOk;
	}

	public boolean sePuedeObtenerMatricula(TramiteTrafMatrDto tramiteTrafico) {
		if (!utilesColegiado.tienePermisoEspecial()) {
			if (utilesColegiado.tienePermiso(UtilesColegiado.PERMISO_MANTENIMIENTO_MATRICULACION_MATW)
					&& tramiteTrafico.getEstado() != null && EstadoTramiteTrafico.Finalizado_PDF.getValorEnum().equals(tramiteTrafico.getEstado())) {
				return true;
			}
			return false;
		}
		return false;
	}

	public boolean esMatriculableTelematicamente(TramiteTrafMatrDto tramiteTrafico) {
		if (!utilesColegiado.tienePermisoEspecial()) {
			if (utilesColegiado.tienePermiso(UtilesColegiado.PERMISO_MATRICULACION_TELEMATICA)) {
				if (tramiteTrafico.getEstado() != null) {
					if (EstadoTramiteTrafico.Validado_Telematicamente.getValorEnum().equals(tramiteTrafico.getEstado()) || EstadoTramiteTrafico.LiberadoEEFF.getValorEnum().equals(tramiteTrafico
							.getEstado())) {
						return true;
					}
					List<EvolucionTramiteTraficoVO> evolucion = servicioEvolucionTramite.getTramiteFinalizadoErrorAutorizado(tramiteTrafico.getNumExpediente());
					if (evolucion != null && !evolucion.isEmpty()
							&& EstadoTramiteTrafico.Finalizado_Con_Error.getValorEnum().equals(tramiteTrafico.getEstado()) && TipoTramiteTrafico.Matriculacion.getValorEnum().equalsIgnoreCase(tramiteTrafico.getTipoTramite())
							&& EstadoTramiteTrafico.Autorizado.getValorEnum().equalsIgnoreCase(evolucion.get(0).getId().getEstadoAnterior().toString())) {
						return true;
					}
				}
			}
			return false;
		}
		return false;
	}

	public boolean esImprimible(TramiteTrafMatrDto tramiteTrafico) {
		if (!utilesColegiado.tienePermisoEspecial()) {
			if (utilesColegiado.tienePermiso(UtilesColegiado.PERMISO_MANTENIMIENTO_MATRICULACION_MATW) && tramiteTrafico.getEstado() != null) {
				if (EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum().equals(tramiteTrafico.getEstado()) || EstadoTramiteTrafico.Validado_Telematicamente.getValorEnum().equals(
						tramiteTrafico.getEstado()) || EstadoTramiteTrafico.Validado_PDF.getValorEnum().equals(tramiteTrafico.getEstado()) || EstadoTramiteTrafico.LiberadoEEFF.getValorEnum()
								.equals(tramiteTrafico.getEstado())) {
					return true;
				}
			}
			return false;
		}
		return false;
	}
	
	public boolean esFichaA(TramiteTrafMatrDto tramite) {
		if (TipoTarjetaITV.A.getValorEnum().equalsIgnoreCase(tramite.getVehiculoDto().getTipoTarjetaITV())
				|| TipoTarjetaITV.AT.getValorEnum().equalsIgnoreCase(tramite.getVehiculoDto().getTipoTarjetaITV())
				|| TipoTarjetaITV.AR.getValorEnum().equalsIgnoreCase(tramite.getVehiculoDto().getTipoTarjetaITV())
				|| TipoTarjetaITV.AL.getValorEnum().equalsIgnoreCase(tramite.getVehiculoDto().getTipoTarjetaITV())) {
					return true;
				}
			return false;
	}
}