package org.gestoresmadrid.oegam2.utiles;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafDuplicadoDto;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import trafico.utiles.enumerados.MotivoDuplicado;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class UtilesVistaDuplicado {

	private static ILoggerOegam log = LoggerOegam.getLogger(UtilesVistaDuplicado.class);

	private static UtilesVistaDuplicado utilesVistaDuplicado;

	@Autowired
	private UtilesColegiado utilesColegiado;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	public static UtilesVistaDuplicado getInstance() {
		if (utilesVistaDuplicado == null) {
			utilesVistaDuplicado = new UtilesVistaDuplicado();
			SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(utilesVistaDuplicado);
		}
		return utilesVistaDuplicado;
	}

	public boolean esFacturableLaTasaDuplicado(TramiteTrafDuplicadoDto tramiteDuplicado) {
		return (utilesColegiado.tienePermiso(UtilesColegiado.PERMISO_MANTENIMIENTO_DUPLICADOS)
				&& tramiteDuplicado.getEstado() != null
				&& (tramiteDuplicado.getEstado().equals(EstadoTramiteTrafico.Finalizado_Excel.getValorEnum())
						|| tramiteDuplicado.getEstado()
								.equals(EstadoTramiteTrafico.Finalizado_Excel_Impreso.getValorEnum())
						|| tramiteDuplicado.getEstado().equals(EstadoTramiteTrafico.Finalizado_PDF.getValorEnum())));
	}

	// 190117 Mantis 0025205. Se añade Finalizado_Excel_Incidencia para que muestre
	// botón de guardar
	public boolean esConsultableOGuardableDuplicado(TramiteTrafDuplicadoDto tramiteDuplicado) {
		if (!utilesColegiado.tienePermisoEspecial()) {
			return (utilesColegiado.tienePermiso(UtilesColegiado.PERMISO_MANTENIMIENTO_DUPLICADOS)
					&& (tramiteDuplicado.getEstado() == null || tramiteDuplicado.getEstado().isEmpty()
					|| tramiteDuplicado.getEstado().equals(EstadoTramiteTrafico.Iniciado.getValorEnum())
					|| tramiteDuplicado.getEstado().equals(EstadoTramiteTrafico.No_Tramitable.getValorEnum())
					|| tramiteDuplicado.getEstado().equals(EstadoTramiteTrafico.Tramitable_Incidencias.getValorEnum())
					|| tramiteDuplicado.getEstado().equals(EstadoTramiteTrafico.Tramitable.getValorEnum())
					|| tramiteDuplicado.getEstado().equals(EstadoTramiteTrafico.Validado_Telematicamente.getValorEnum())
					|| tramiteDuplicado.getEstado().equals(EstadoTramiteTrafico.Validado_PDF.getValorEnum())
					|| tramiteDuplicado.getEstado().equals(EstadoTramiteTrafico.Finalizado_Con_Error.getValorEnum())
					|| tramiteDuplicado.getEstado().equals(EstadoTramiteTrafico.Finalizado_Excel_Incidencia.getValorEnum())));
		}
		return false;
	}

	public boolean esValidable(TramiteTrafDuplicadoDto tramiteDuplicado) {
		if (!utilesColegiado.tienePermisoEspecial()
				&& utilesColegiado.tienePermiso(UtilesColegiado.PERMISO_MANTENIMIENTO_DUPLICADOS)
				&& StringUtils.isNotBlank(tramiteDuplicado.getEstado())) {
			if (!esUsuarioSive()) {
				if ((tramiteDuplicado.getEstado().equals(EstadoTramiteTrafico.Iniciado.getValorEnum())
						|| tramiteDuplicado.getEstado()
								.equals(EstadoTramiteTrafico.Finalizado_Con_Error.getValorEnum()))) {
					return true;
				}
			} else if ((tramiteDuplicado.getEstado().equals(EstadoTramiteTrafico.Tramitable.getValorEnum())
					|| tramiteDuplicado.getEstado().equals(EstadoTramiteTrafico.No_Tramitable.getValorEnum()))
					|| ((tramiteDuplicado.getMotivoDuplicado().equals(MotivoDuplicado.CambD.getValorEnum())
							|| tramiteDuplicado.getMotivoDuplicado().equals(MotivoDuplicado.CambDCond.getValorEnum()))
							&& (tramiteDuplicado.getEstado().equals(EstadoTramiteTrafico.Iniciado.getValorEnum())
									|| tramiteDuplicado.getEstado()
											.equals(EstadoTramiteTrafico.Finalizado_Con_Error.getValorEnum())))) {
				return true;
			}
		}
		return false;
	}

	public boolean esComprobable(TramiteTrafDuplicadoDto tramiteDuplicado) {
		return (!utilesColegiado.tienePermisoEspecial()
				&& utilesColegiado.tienePermiso(UtilesColegiado.PERMISO_MANTENIMIENTO_DUPLICADOS) && esUsuarioSive()
				&& !MotivoDuplicado.CambD.getValorEnum().endsWith(tramiteDuplicado.getMotivoDuplicado())
				&& !MotivoDuplicado.CambDCond.getValorEnum().endsWith(tramiteDuplicado.getMotivoDuplicado())
				&& tramiteDuplicado.getEstado() != null && !tramiteDuplicado.getEstado().equals("")
				&& (tramiteDuplicado.getEstado().equals(EstadoTramiteTrafico.Iniciado.getValorEnum())
						|| tramiteDuplicado.getEstado()
								.equals(EstadoTramiteTrafico.Finalizado_Con_Error.getValorEnum())));
	}

//	Se comenta método tras comprobar que lleva tiempo sin que se ejecute el JavaScript del botón que lleva a la impresión.
//	public boolean esImprimible(TramiteTrafDuplicadoDto tramiteDuplicado) {
//		if (!utilesColegiado.tienePermisoEspecial()) {
//			if (utilesColegiado.tienePermiso(UtilesColegiado.PERMISO_MANTENIMIENTO_DUPLICADOS)) {
//				if (tramiteDuplicado.getEstado() != null) {
//					if (tramiteDuplicado.getEstado().equals(EstadoTramiteTrafico.Validado_PDF.getValorEnum())
//							|| tramiteDuplicado.getEstado().equals(EstadoTramiteTrafico.Finalizado_PDF.getValorEnum())) {
//						return true;
//					}
//				}
//			}
//			return false;
//		}
//		return false;
//	}

	public boolean esEnvioExcelDuplicados(TramiteTrafDuplicadoDto tramiteDuplicado) {
		if (!utilesColegiado.tienePermisoEspecial()) {
			if (utilesColegiado.tienePermiso(UtilesColegiado.PERMISO_MANTENIMIENTO_DUPLICADOS)
					&& EstadoTramiteTrafico.Validado_PDF.getValorEnum().equals(tramiteDuplicado.getEstado())) {
				return utilesColegiado.esColegiadoEnvioExcel();
			}
			return false;
		}
		return false;
	}

	public boolean esTramitableDuplicados(TramiteTrafDuplicadoDto tramiteDuplicado) {
		if (!utilesColegiado.tienePermisoEspecial()) {
			return (utilesColegiado.tienePermiso(UtilesColegiado.PERMISO_MANTENIMIENTO_DUPLICADOS)
					&& EstadoTramiteTrafico.Validado_Telematicamente.getValorEnum().equals(tramiteDuplicado.getEstado()));
		}
		return false;
	}

	public Boolean esUsuarioSive() {
		String forzarSive = gestorPropiedades.valorPropertie("forzar.sive");
		String colegiado = utilesColegiado.getNumColegiadoSession();
		if ("SI".equalsIgnoreCase(forzarSive) || esAutorizadoSive(colegiado)) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	private boolean esAutorizadoSive(String colegiado) {
		String[] usuariosSive = gestorPropiedades.valorPropertie("usuarios.sive").split(",");
		for (String elemento : usuariosSive) {
			if (elemento.equals(colegiado)) {
				return Boolean.TRUE;
			}
		}
		return false;
	}

}