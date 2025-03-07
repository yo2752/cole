package org.gestoresmadrid.oegam2comun.model.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.gestoresmadrid.core.cola.model.vo.ColaVO;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.TipoTransferencia;
import org.gestoresmadrid.oegam2comun.cola.model.service.ServicioCola;
import org.gestoresmadrid.oegam2comun.model.service.ServicioMonitor;
import org.gestoresmadrid.oegam2comun.modelos.view.dto.ChartDataDto;
import org.gestoresmadrid.oegam2comun.modelos.view.dto.ChartDataDto.CharDataset;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTrafico;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import colas.beans.ProcesoMonitorizado;
import colas.daos.EjecucionesProcesosDAO;
import trafico.beans.EnvioDataPantalla;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioMonitorImpl implements ServicioMonitor {

	private static final ILoggerOegam LOG = LoggerOegam.getLogger(ServicioMonitorImpl.class);

	private static final String LABEL_PENDIENTES = "Pendientes";
	private static final String LABEL_ERROR_SERVICIO = "Error servicio";

	private static final String LABEL_FINALIZADO_OK = "Finalizado Ok";
	private static final String LABEL_FINALIZADO_ERROR = "Finalizado Error";

	private static final String YELLOW_BACK_GROUND_COLOR = "rgba(255, 206, 86, 0.2)";
	private static final String YELLOW_BORDER_COLOR = "rgba(255, 206, 86, 1)";
	private static final String ORANGE_BACK_GROUND_COLOR = "rgba(255, 159, 64, 0.2)";
	private static final String ORANGE_BORDER_COLOR = "rgba(255, 159, 64, 1)";

	private static final String RED_BACK_GROUND_COLOR = "rgba(255, 99, 132, 0.2)";
	private static final String RED_BORDER_COLOR = "rgba(255,99,132,1)";
	private static final String GREEN_BACK_GROUND_COLOR = "rgba(75, 192, 192, 0.2)";
	private static final String GREEN_BORDER_COLOR = "rgba(75, 192, 192, 1)";

	@Autowired
	private ServicioCola servicioCola;

	@Autowired
	private ServicioTramiteTrafico servicioTramiteTrafico;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	private EjecucionesProcesosDAO ejecucionesProcesosDAO;

	// Recupera todos los procesos de Tráfico
	@Override
	@Transactional(readOnly = true)
	public List<ProcesoMonitorizado> monitorizaProcesosTrafico() {
		List<ProcesoMonitorizado> listadoProcesos = new ArrayList<>();
		for (String aux : listaDeProcedimientosTrafico()) {
			listadoProcesos.add(recuperaDatosProceso(aux));
		}
		return listadoProcesos;
	}

	// Recupera todos los procesos de Gestion Documental
	@Override
	@Transactional(readOnly = true)
	public List<ProcesoMonitorizado> monitorizaProcesosGDoc() {
		List<ProcesoMonitorizado> listadoProcesos = new ArrayList<>();
		for (String aux : listaDeProcedimientosGDoc()) {
			listadoProcesos.add(recuperaDatosProceso(aux));
		}
		return listadoProcesos;
	}

	@Override
	public ChartDataDto listarSolicitudes(String proceso) {
		ChartDataDto result = null;
		List<Object[]> datos = servicioCola.solicitudesAgrupadasPorHilos(proceso);
		if (datos != null) {
			result = new ChartDataDto(LABEL_PENDIENTES, LABEL_ERROR_SERVICIO);
			int totalPendientes = 0;
			int totalError = 0;
			for (Object[] dato : datos) {
				if (dato == null || dato.length < 3 || dato[0] == null || dato[1] == null || dato[2] == null) {
					continue;
				}
				int pendientes = ((BigDecimal) dato[1]).intValue();
				int errores = ((BigDecimal) dato[2]).intValue();
				result.getLabels().add("Hilo " + dato[0].toString());
				result.addData(LABEL_PENDIENTES, pendientes, YELLOW_BACK_GROUND_COLOR, YELLOW_BORDER_COLOR);
				result.addData(LABEL_ERROR_SERVICIO, errores, RED_BACK_GROUND_COLOR, RED_BORDER_COLOR);
				totalPendientes += pendientes;
				totalError += errores;
			}
			if (result.getLabels().size() > 1) {
				result.getLabels().add("Total");
				result.addData(LABEL_PENDIENTES, totalPendientes, ORANGE_BACK_GROUND_COLOR, ORANGE_BORDER_COLOR);
				result.addData(LABEL_ERROR_SERVICIO, totalError, RED_BACK_GROUND_COLOR, RED_BORDER_COLOR);
			}
		}
		return result;
	}

	@Override
	@Transactional(readOnly = true)
	public ChartDataDto listarFinalizaciones(String proceso) {
		ChartDataDto result = null;
		ProcesosEnum procesoEnum = ProcesosEnum.convertirPorNombreEnum(proceso);
		TipoTramiteTrafico tipoTramite = getTipoTramite(procesoEnum);
		if (tipoTramite != null) {
			List<BigDecimal> estadosOk = getEstadosFinalizacionOk(procesoEnum);
			List<BigDecimal> estadosKo = getEstadosFinalizacionKo(procesoEnum);
			List<String> tipoTransferencia = getTipoTransferencia(procesoEnum);

			Object[] datos1minuto = servicioTramiteTrafico.listarTramitesFinalizadosPorTiempo(tipoTramite.getValorEnum(), estadosOk, estadosKo, tipoTransferencia, 60);
			Object[] datos5minuto = servicioTramiteTrafico.listarTramitesFinalizadosPorTiempo(tipoTramite.getValorEnum(), estadosOk, estadosKo, tipoTransferencia, 300);
			Object[] datos15minuto = servicioTramiteTrafico.listarTramitesFinalizadosPorTiempo(tipoTramite.getValorEnum(), estadosOk, estadosKo, tipoTransferencia, 900);
			Object[] datos30minuto = servicioTramiteTrafico.listarTramitesFinalizadosPorTiempo(tipoTramite.getValorEnum(), estadosOk, estadosKo, tipoTransferencia, 1800);
			Object[] datos60minuto = servicioTramiteTrafico.listarTramitesFinalizadosPorTiempo(tipoTramite.getValorEnum(), estadosOk, estadosKo, tipoTransferencia, 3600);

			result = new ChartDataDto(LABEL_FINALIZADO_OK, LABEL_FINALIZADO_ERROR);

			if (datos1minuto != null && datos1minuto.length > 1) {
				result.getLabels().add("Último minuto");
				result.addData(LABEL_FINALIZADO_OK, ((BigDecimal) datos1minuto[0]).intValue(), GREEN_BACK_GROUND_COLOR, GREEN_BORDER_COLOR);
				result.addData(LABEL_FINALIZADO_ERROR, ((BigDecimal) datos1minuto[1]).intValue(), RED_BACK_GROUND_COLOR, RED_BORDER_COLOR);
			}

			if (datos5minuto != null && datos5minuto.length > 1) {
				result.getLabels().add("Cinco minutos");
				result.addData(LABEL_FINALIZADO_OK, ((BigDecimal) datos5minuto[0]).intValue(), GREEN_BACK_GROUND_COLOR, GREEN_BORDER_COLOR);
				result.addData(LABEL_FINALIZADO_ERROR, ((BigDecimal) datos5minuto[1]).intValue(), RED_BACK_GROUND_COLOR, RED_BORDER_COLOR);
			}

			if (datos15minuto != null && datos15minuto.length > 1) {
				result.getLabels().add("Quince minutos");
				result.addData(LABEL_FINALIZADO_OK, ((BigDecimal) datos15minuto[0]).intValue(), GREEN_BACK_GROUND_COLOR, GREEN_BORDER_COLOR);
				result.addData(LABEL_FINALIZADO_ERROR, ((BigDecimal) datos15minuto[1]).intValue(), RED_BACK_GROUND_COLOR, RED_BORDER_COLOR);
			}

			if (datos30minuto != null && datos30minuto.length > 1) {
				result.getLabels().add("Treinta minutos");
				result.addData(LABEL_FINALIZADO_OK, ((BigDecimal) datos30minuto[0]).intValue(), GREEN_BACK_GROUND_COLOR, GREEN_BORDER_COLOR);
				result.addData(LABEL_FINALIZADO_ERROR, ((BigDecimal) datos30minuto[1]).intValue(), RED_BACK_GROUND_COLOR, RED_BORDER_COLOR);
			}

			if (datos60minuto != null && datos60minuto.length > 1) {
				result.getLabels().add("Última hora");
				result.addData(LABEL_FINALIZADO_OK, ((BigDecimal) datos60minuto[0]).intValue(), GREEN_BACK_GROUND_COLOR, GREEN_BORDER_COLOR);
				result.addData(LABEL_FINALIZADO_ERROR, ((BigDecimal) datos60minuto[1]).intValue(), RED_BACK_GROUND_COLOR, RED_BORDER_COLOR);
			}

		}
		return result;
	}

	@Override
	@Transactional(readOnly = true)
	public ChartDataDto peticionesTotales() {
		ChartDataDto chart = null;
		List<Object[]> datos = servicioCola.solicitudesAgrupadasPorHilos();
		if (datos != null && !datos.isEmpty()) {
			chart = new ChartDataDto();
			CharDataset dataset = chart.new CharDataset(null);
			dataset.setBorderWidth(0);
			dataset.setBorderColor(null);
			chart.getDatasets().add(dataset);
			for (Object[] dato : datos) {
				if (dato != null && dato.length > 1) {
					String proceso = (String) dato[0];
					chart.getLabels().add(proceso);
					dataset.getData().add((Number) dato[1]);
					dataset.getBackgroundColor().add(generateColor(proceso));
				}
			}
		}
		return chart;
	}

	private String generateColor(String valor) {
		StringBuilder sb = new StringBuilder("rgba(")
				.append(valor.hashCode() % 255)
				.append(", ")
				.append((valor.length() * 48 )% 255)
				.append(", ")
				.append((valor.length() * valor.hashCode()) % 255)
				.append(", 0.3)");
		return sb.toString();
	}

	private TipoTramiteTrafico getTipoTramite(ProcesosEnum proceso) {
		TipoTramiteTrafico tipoTramite;
		if (proceso == null) {
			tipoTramite = null;
		} else {
			switch (proceso) {
				case MATW:
					tipoTramite = TipoTramiteTrafico.Matriculacion;
					break;
				case CHECKCTIT:
				case TRADECTIT:
				case NOTIFICATIONCTIT:
				case FULLCTIT:
					tipoTramite = TipoTramiteTrafico.TransmisionElectronica;
					break;
				default:
					tipoTramite = null;
					break;
			}
		}
		return tipoTramite;
	}

	private List<BigDecimal> getEstadosFinalizacionOk(ProcesosEnum proceso) {
		List<BigDecimal> estados;
		if (proceso == null) {
			estados = Collections.emptyList();
		} else {
			switch (proceso) {
				case MATW:
				case FULLCTIT:
				case TRADECTIT:
				case NOTIFICATIONCTIT:
					estados = Arrays.asList(BigDecimal.valueOf(Long.parseLong(EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum())), BigDecimal.valueOf(Long.parseLong(
							EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso.getValorEnum())));
					break;
				case CHECKCTIT:
					estados = Arrays.asList(BigDecimal.valueOf(Long.parseLong(EstadoTramiteTrafico.Tramitable.getValorEnum())), BigDecimal.valueOf(Long.parseLong(
							EstadoTramiteTrafico.Tramitable_Incidencias.getValorEnum())), BigDecimal.valueOf(Long.parseLong(EstadoTramiteTrafico.Tramitable_Jefatura.getValorEnum())));
					break;
				default:
					estados = Collections.emptyList();
					break;
			}
		}
		return estados;
	}

	private List<String> getTipoTransferencia(ProcesosEnum proceso) {
		List<String> tipos;
		if (proceso == null) {
			tipos = Collections.emptyList();
		} else {
			switch (proceso) {
				case FULLCTIT:
					tipos = Arrays.asList(TipoTransferencia.tipo1.getValorEnum(), TipoTransferencia.tipo2.getValorEnum(), TipoTransferencia.tipo3.getValorEnum());
					break;
				case TRADECTIT:
					tipos = Arrays.asList(TipoTransferencia.tipo5.getValorEnum());
					break;
				case NOTIFICATIONCTIT:
					tipos = Arrays.asList(TipoTransferencia.tipo4.getValorEnum());
					break;
				default:
					tipos = Collections.emptyList();
					break;
			}
		}
		return tipos;
	}

	private List<BigDecimal> getEstadosFinalizacionKo(ProcesosEnum proceso) {
		List<BigDecimal> estados;
		if (proceso == null) {
			estados = Collections.emptyList();
		} else {
			switch (proceso) {
				case MATW:
				case FULLCTIT:
				case TRADECTIT:
				case NOTIFICATIONCTIT:
					estados = Arrays.asList(BigDecimal.valueOf(Long.parseLong(EstadoTramiteTrafico.Finalizado_Con_Error.getValorEnum())));
					break;
				case CHECKCTIT:
					estados = Arrays.asList(BigDecimal.valueOf(Long.parseLong(EstadoTramiteTrafico.No_Tramitable.getValorEnum())));
					break;
				default:
					estados = Collections.emptyList();
					break;
			}
		}
		return estados;
	}

	private ProcesoMonitorizado recuperaDatosProceso(String proceso) {
		ProcesoMonitorizado resultado = new ProcesoMonitorizado();
		resultado.setNombre(proceso);
		try {
			// Últimas ejecuciones
			ArrayList<EnvioDataPantalla> listaUltimasEjecuciones = ejecucionesProcesosDAO.recuperarUltimasEjecucionesProceso(proceso);

			// Posición 0 = Última correcta, Posición 1 = Úlima Error, Posición 2 = Última excepción
			// En algunos casos puede que no hayan últimas ejecuciones
			if (listaUltimasEjecuciones != null && !listaUltimasEjecuciones.isEmpty())
				resultado.setUltimaOK(listaUltimasEjecuciones.get(0).getFechaEnvio());
			if (listaUltimasEjecuciones != null && listaUltimasEjecuciones.size() > 1)
				resultado.setUltimaKO(listaUltimasEjecuciones.get(1).getFechaEnvio());
			if (listaUltimasEjecuciones != null && listaUltimasEjecuciones.size() > 2)
				resultado.setUltimaExcepcion(listaUltimasEjecuciones.get(2).getFechaEnvio());

			// Consulto peticiones pendientes.
			List<ColaVO> listaConsultaPeticionesPendientes = servicioCola.getSolicitudesColaMonitorizacion(proceso, null, utilesFecha.getFechaFracionadaActual());
			if (listaConsultaPeticionesPendientes != null && !listaConsultaPeticionesPendientes.isEmpty())
				resultado.setSolicitudesCola(listaConsultaPeticionesPendientes.size());

		} catch (Throwable ex) {
			LOG.error(ex);
		}

		return resultado;
	}

	// Esto es un listado con todos los procedimientos de TRAFICO
	// Desde aqui se pueden añadir o quitar los que se mostraran
	private List<String> listaDeProcedimientosTrafico() {
		List<String> procedimientos = new ArrayList<String>();

		procedimientos.add("COITV");
		procedimientos.add("MARCADGT");
		procedimientos.add("INFOWS");
		procedimientos.add("MATW");
		procedimientos.add("MATEEITV");
		procedimientos.add("FULLCTIT");
		procedimientos.add("NOTIFICATIONCTIT");
		procedimientos.add("TRADECTIT");
		procedimientos.add("CHECKCTIT");
		procedimientos.add("ISSUEPROPROOF");
		procedimientos.add("VERIFYPROPROOF");
		procedimientos.add("ANUNTISM");
		procedimientos.add("ANUNTIST");
		procedimientos.add("INTEVE");
		procedimientos.add("INTEVE4ANUNTIS");
		procedimientos.add("MATEEITV");
		procedimientos.add("576");

		return procedimientos;
	}

	// Esto es un listado con todos los procedimientos de Gestión Documental
	// Desde aquí se pueden añadir o quitar los que se mostrarán
	private List<String> listaDeProcedimientosGDoc() {
		List<String> procedimientos = new ArrayList<>();

		procedimientos.add("YERBABUENAM");
		procedimientos.add("YERBABUENAN");
		procedimientos.add("YERBABUENA30PDF");

		return procedimientos;
	}

}