package org.gestoresmadrid.oegamCreditos.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.general.model.vo.CreditoVO;
import org.gestoresmadrid.core.general.model.vo.HistoricoCreditoVO;
import org.gestoresmadrid.core.general.model.vo.TipoCreditoVO;
import org.gestoresmadrid.core.model.beans.AliasQueryBean;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.oegamCreditos.resultTransform.HistoricoCreditosResultTransform;
import org.gestoresmadrid.oegamCreditos.service.ServicioPersistenciaCreditos;
import org.gestoresmadrid.oegamCreditos.service.ServicioResumenCargaCreditos;
import org.gestoresmadrid.oegamCreditos.view.bean.CreditosDisponiblesBean;
import org.gestoresmadrid.oegamCreditos.view.bean.ResultCreditosBean;
import org.gestoresmadrid.oegamCreditos.view.dto.HistoricoCreditoDto;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thoughtworks.xstream.XStream;

import utilidades.estructuras.FechaFraccionada;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioResumenCargaCreditosImpl implements ServicioResumenCargaCreditos {

	private static final long serialVersionUID = 3838085371911847195L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioResumenCargaCreditosImpl.class);

	private static final String horaFinDia = "23:59";
	private static final int N_SEGUNDOS = 59;

	@Autowired
	private ServicioPersistenciaCreditos servicioPersistenciaCreditos;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	Conversor conversor;

	@Autowired
	Utiles utiles;

	@Override
	public ResultCreditosBean cantidadesTotalesResumen(Long idContrato, String tipoCredito, Date fechaDesde, Date fechaHasta) throws Exception {
		ResultCreditosBean result = new ResultCreditosBean();
		List<BigDecimal[]> listaCantidades = servicioPersistenciaCreditos.cantidadesTotalesResumen(idContrato, tipoCredito, fechaDesde, fechaHasta);

		if (listaCantidades != null && listaCantidades.size() == 1) {
			Object[] cantidades = listaCantidades.get(0);
			if (cantidades != null && cantidades.length == 2) {
				result.addAttachment(CANTIDAD_SUMADA_TOTAL, cantidades[0]);
				result.addAttachment(CANTIDAD_RESTADA_TOTAL, cantidades[1]);
			}
		} else {
			result.setError(true);
		}

		return result;
	}

	@Override
	public int numeroElementos(Long idContrato, String tipoCredito, Date fechaDesde, Date fechaHasta) throws Exception {
		return servicioPersistenciaCreditos.numeroElementosResumenCargaCreditos(idContrato, tipoCredito, fechaDesde, fechaHasta);
	}

	@Override
	public String obtenerCreditosGBJA(FechaFraccionada fechaAlta) {

		String[] sTiposCreditos = { "CT9", "CT10" };

		List<HistoricoCreditosResultTransform> listadoSuma = new ArrayList<HistoricoCreditosResultTransform>();

		for (String tipo : sTiposCreditos) {
			List<Criterion> criterion = new ArrayList<Criterion>();
			criterion.add(Restrictions.eq("creditotipoCreditoVO.tipoCredito", tipo));
			anadirCriterioFecha(criterion, fechaAlta, "fecha");
			ProjectionList listaProyecciones = Projections.projectionList();
			listaProyecciones.add(Projections.groupProperty("creditotipoCreditoVO.descripcion"));
			listaProyecciones.add(Projections.sum("cantidadSumada"));
			listaProyecciones.add(Projections.sum("cantidadRestada"));

			HistoricoCreditosResultTransform resultTransform = new HistoricoCreditosResultTransform();
			List<AliasQueryBean> alias = new ArrayList<AliasQueryBean>();
			alias.add(new AliasQueryBean(HistoricoCreditoVO.class, "credito", "credito", CriteriaSpecification.INNER_JOIN));
			alias.add(new AliasQueryBean(HistoricoCreditoVO.class, "credito.tipoCreditoVO", "creditotipoCreditoVO", CriteriaSpecification.INNER_JOIN));

			@SuppressWarnings("rawtypes")
			List listCT = servicioPersistenciaCreditos.buscarPorCriteria(criterion, alias, listaProyecciones, resultTransform);
			if (listCT.size() > 0) {
				listadoSuma.add((HistoricoCreditosResultTransform) listCT.get(0));
			} else {
				HistoricoCreditosResultTransform histCre = new HistoricoCreditosResultTransform();
				TipoCreditoVO tipoCredito = servicioPersistenciaCreditos.getTipoCredito(tipo);
				histCre.setTipoTramite(tipoCredito.getDescripcion());
				histCre.setCreditosRestados(new BigDecimal(0));
				histCre.setCreditosSumados(new BigDecimal(0));
				listadoSuma.add(histCre);
			}
		}

		XStream xstream = new XStream();
		xstream.processAnnotations(HistoricoCreditosResultTransform.class);
		String xmlXStream = xstream.toXML(listadoSuma);

		return xmlXStream;
	}

	@Override
	public ResultCreditosBean exportarTablaCompleta(Long idContrato, String tipoCredito, Date fechaDesde, Date fechaHasta) {

		ResultCreditosBean resultado = new ResultCreditosBean();

		try {
			List<Object[]> lista = servicioPersistenciaCreditos.getListaExportarTablaResumenCargaCreditos(idContrato, tipoCredito, fechaDesde, fechaHasta);
			ArrayList<String> lineasExport = new ArrayList<String>();

			if (lista != null && !lista.isEmpty()) {
				// Recorremos toda la lista de objetos
				for (Object[] tramite : (List<Object[]>) lista) {
					String lineaExport = "";
					// Columna Razón Social
					lineaExport += null != (String) tramite[4] ? (String) tramite[4] : "";
					lineaExport += ";";
					// Columna Núm. Colegiado
					if (null != (String) tramite[3]) {
						String numColegiado = utiles.rellenarCeros((String) tramite[3], 4);
						lineaExport += numColegiado;
					} else {
						lineaExport += "";
					}

					lineaExport += ";";
					// Columna Descripción Tipo Crédito
					lineaExport += null != (String) tramite[5] ? (String) tramite[5] : "";
					lineaExport += ";";
					// Columna Cantidad sumada
					lineaExport += null != (BigDecimal) tramite[7] ? (BigDecimal) tramite[7] : "";
					lineaExport += ";";

					// añadimos la linea a la lista
					lineasExport.add(lineaExport);
				}
			}
			resultado.addAttachment("contenidoFichero", lineasExport);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de exportar la tabla, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de exportar la tabla.");
		}

		return resultado;

	}

	@Override
	public ResultCreditosBean exportarTablaCompletaMes(Long idContrato, String tipoCredito, Date fechaDesde, Date fechaHasta, BigDecimal precioCredito) {

		ResultCreditosBean resultado = new ResultCreditosBean();

		try {
			List<Object[]> lista = servicioPersistenciaCreditos.getListaExportarTablaResumenCargaCreditos(idContrato, tipoCredito, fechaDesde, fechaHasta);
			ArrayList<String> lineasExport = new ArrayList<String>();

			// Se agregan los títulos de las cabeceras de la tabla
			String lineaExportCol = "";
			lineaExportCol += "Nombre Colegiado";
			lineaExportCol += ";";
			lineaExportCol += "Dirección Colegiado";
			lineaExportCol += ";";
			lineaExportCol += "Número Colegiado";
			lineaExportCol += ";";
			lineaExportCol += "Tipo Crédito";
			lineaExportCol += ";";
			lineaExportCol += "Número Créditos Gastados";
			lineaExportCol += ";";
			lineaExportCol += "Precio Total";
			lineaExportCol += ";";
			lineasExport.add(0, lineaExportCol);

			if (lista != null && !lista.isEmpty()) {
				// Recorremos toda la lista de objetos
				for (Object[] tramite : (List<Object[]>) lista) {
					String lineaExport = "";
					// Columna Razón Social
					lineaExport += null != (String) tramite[4] ? (String) tramite[4] : "";
					lineaExport += ";";
					// Columna Dirección Colegiado (Vía)
					lineaExport += null != (String) tramite[2] ? (String) tramite[2] : "";
					lineaExport += ";";
					// Columna Núm. Colegiado
					if (null != (String) tramite[3]) {
						String numColegiado = utiles.rellenarCeros((String) tramite[3], 4);
						lineaExport += numColegiado;
					} else {
						lineaExport += "";
					}

					lineaExport += ";";
					// Columna Descripción Tipo Crédito
					lineaExport += null != (String) tramite[5] ? (String) tramite[5] : "";
					lineaExport += ";";
					// Columna Cantidad sumada
					lineaExport += null != (BigDecimal) tramite[7] ? (BigDecimal) tramite[7] : "";
					lineaExport += ";";

					// Si la cantidad sumada no es null, la multiplicamos por el coste del crédito para obtener el Precio Total
					if ((BigDecimal) tramite[7] != null) {

						BigDecimal precioTotal = BigDecimal.valueOf(((BigDecimal) tramite[7]).doubleValue()).multiply(precioCredito);

						lineaExport += null != precioTotal ? precioTotal : "";
					} else {
						lineaExport += ";";
					}
					lineaExport += ";";

					// añadimos la linea a la lista
					lineasExport.add(1, lineaExport);
				}
			}
			resultado.addAttachment("contenidoFichero", lineasExport);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de exportar la tabla, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de exportar la tabla.");
		}

		return resultado;

	}

	protected void anadirCriterioFecha(List<Criterion> criterionList, FechaFraccionada fecha, String tipo) {
		if (fecha != null) {
			Date fechaFin = null;
			if (!fecha.isfechaFinNula()) {
				fechaFin = fecha.getFechaFin();
				try {
					utilesFecha.setHoraEnDate(fechaFin, horaFinDia);
					utilesFecha.setSegundosEnDate(fechaFin, N_SEGUNDOS);
				} catch (Throwable e) {
					log.error("Se ha producido un error al hacer una búsqueda por criterios con la interfaz PaginatedList, setHora en " + tipo);
					log.error(e.getMessage());
				}
			}
			if (!fecha.isfechaInicioNula() && !fecha.isfechaFinNula()) {
				Criterion rest2 = Restrictions.between(tipo, fecha.getFechaInicio(), fechaFin);
				criterionList.add(rest2);
			} else if (!fecha.isfechaInicioNula()) {
				Criterion rest2 = Restrictions.ge(tipo, fecha.getFechaInicio());
				criterionList.add(rest2);
			} else if (!fecha.isfechaFinNula()) {
				Criterion rest2 = Restrictions.le(tipo, fechaFin);
				criterionList.add(rest2);
			}
		}
	}

	@Override
	public List<CreditosDisponiblesBean> creditosDisponiblesPorColegiado(Long idContrato) {
		List<CreditosDisponiblesBean> listaCreditosDisponiblesBean = new ArrayList<CreditosDisponiblesBean>();

		List<CreditoVO> listaCreditos = servicioPersistenciaCreditos.consultaCreditosDisponiblesPorColegiado(idContrato);

		List<Object[]> listaCreditosBloqueados = servicioPersistenciaCreditos.consultaCreditosBloqueadosPorContrato(new BigDecimal(idContrato));

		if (listaCreditos != null && !listaCreditos.isEmpty()) {
			for (CreditoVO element : listaCreditos) {
				CreditosDisponiblesBean creditosDisponiblesBean = new CreditosDisponiblesBean();
				creditosDisponiblesBean.setCreditos(element.getCreditos() != null ? element.getCreditos() : BigDecimal.ZERO);
				creditosDisponiblesBean.setTipoCredito(element.getTipoCreditoVO().getTipoCredito());
				creditosDisponiblesBean.setDescripcionTipoCredito(element.getTipoCreditoVO().getTipoCredito() + " - " + element.getTipoCreditoVO().getDescripcion());

				Integer creditosBloqueados = 0;

				if (listaCreditosBloqueados != null && !listaCreditosBloqueados.isEmpty()) {
					for (Object[] creditoBloqueado : listaCreditosBloqueados) {
						if (null != creditoBloqueado[0] && creditoBloqueado[0].equals(creditosDisponiblesBean.getTipoCredito())) {
							creditosBloqueados = (Integer) creditoBloqueado[1];
						}
					}

					creditosDisponiblesBean.setCreditosBloqueados(creditosBloqueados != null ? creditosBloqueados : 0);

					creditosDisponiblesBean.setCreditosDisponibles(creditosDisponiblesBean.getCreditos().intValue() - creditosDisponiblesBean.getCreditosBloqueados().intValue());

				} else {
					creditosDisponiblesBean.setCreditosBloqueados(0);
					creditosDisponiblesBean.setCreditosDisponibles(creditosDisponiblesBean.getCreditos().intValue());
				}

				listaCreditosDisponiblesBean.add(creditosDisponiblesBean);
			}

		}

		return listaCreditosDisponiblesBean;
	}

	@Override
	public List<HistoricoCreditoDto> consultaCreditosAcumuladosMesPorColegiado(Long idContrato) {
		List<HistoricoCreditoVO> lista = servicioPersistenciaCreditos.consultaCreditosAcumuladosMesPorColegiado(idContrato);
		if (lista != null && !lista.isEmpty()) {
			return conversor.transform(lista, HistoricoCreditoDto.class);
		}
		return Collections.emptyList();
	}
}