package org.gestoresmadrid.oegamCreditos.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.core.historicocreditos.model.vo.CreditoFacturadoTramiteVO;
import org.gestoresmadrid.core.historicocreditos.model.vo.CreditoFacturadoVO;
import org.gestoresmadrid.oegamCreditos.service.ServicioGastoMensualCreditos;
import org.gestoresmadrid.oegamCreditos.service.ServicioPersistenciaCreditos;
import org.gestoresmadrid.oegamCreditos.view.bean.GastoMensualCreditosBean;
import org.gestoresmadrid.oegamCreditos.view.bean.ResultCreditosBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioGastoMensualCreditosImpl implements ServicioGastoMensualCreditos {

	private static final long serialVersionUID = -6508460558085159875L;

	private static ILoggerOegam log = LoggerOegam.getLogger(ServicioGastoMensualCreditosImpl.class);

	@Autowired
	private ServicioPersistenciaCreditos servicioPersistenciaCreditos;

	@Override
	public CreditoFacturadoVO getCreditoFacturadoVO(Long id, String... initialized) {
		return servicioPersistenciaCreditos.getCreditoFacturadoVO(id, initialized);
	}

	@Override
	public ResultCreditosBean listarTablaCompleta(GastoMensualCreditosBean filtro) {

		ResultCreditosBean resultado = new ResultCreditosBean();

		try {
			List<CreditoFacturadoVO> lista = servicioPersistenciaCreditos.getListaExportarHistroricoFacturado(filtro.getConcepto(), filtro.getIdContrato(), filtro.getTipoCredito(), filtro
					.getTramite(), filtro.getFechaCreditos());
			ArrayList<String> lineasExport = new ArrayList<String>();

			if (lista != null) {
				// Recorremos toda la lista de creditosFacturados
				for (CreditoFacturadoVO creditoFacturado : lista) {
					String lineaExport = "";
					lineaExport += null != creditoFacturado.getFecha() ? creditoFacturado.getFecha() : "";
					lineaExport += ";";
					lineaExport += null != creditoFacturado.getTipoTramite() && null != creditoFacturado.getTipoTramite().getTipoCredito() && null != creditoFacturado.getTipoTramite().getTipoCredito().getTipoCredito()? creditoFacturado.getTipoTramite().getTipoCredito().getTipoCredito() : "";
					lineaExport += ";";
					lineaExport += null != creditoFacturado.getTipoTramite() &&  null != creditoFacturado.getTipoTramite().getDescripcion()? creditoFacturado.getTipoTramite().getDescripcion() : "";
					lineaExport += ";";
					lineaExport += null != creditoFacturado.getNumeroCreditos() ? creditoFacturado.getNumeroCreditos() : "";
					lineaExport += ";";
					lineaExport += null != creditoFacturado.getConcepto() ? creditoFacturado.getConcepto() : "";
					lineaExport += ";";

					// Conjunto de tramites por cada creditoFactura
					for (CreditoFacturadoTramiteVO creditoFacturadoTramite : creditoFacturado.getCreditoFacturadoTramites()) {
						lineaExport += null != creditoFacturadoTramite.getIdTramite() ? creditoFacturadoTramite.getIdTramite() : "";
						lineaExport += " | ";
					}
					// Quitamos el ultimo separador de cada registro de expedientes
					lineaExport = lineaExport.substring(0, lineaExport.length() - 3);

					lineaExport += ";";
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

}
