package org.gestoresmadrid.oegamImpresion.solicitudes.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.impresion.model.vo.ImpresionVO;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.oegamComun.view.bean.ResultadoBean;
import org.gestoresmadrid.oegamComun.view.bean.ResultadoImpresionBean;
import org.gestoresmadrid.oegamImpresion.service.ServicioImpresionDocumentos;
import org.gestoresmadrid.oegamImpresion.service.ServicioImpresionTramiteTrafico;
import org.gestoresmadrid.oegamImpresion.solicitudes.service.ServicioSolicitudes;
import org.gestoresmadrid.oegamImpresion.solicitudes.service.ServicioSolicitudesDPC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioSolicitudesImpl implements ServicioSolicitudes {

	private static final long serialVersionUID = -8887087421089720489L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioSolicitudesImpl.class);

	@Autowired
	ServicioImpresionDocumentos servicioImpresion;

	@Autowired
	ServicioImpresionTramiteTrafico servicioImpresionTramiteTrafico;

	@Autowired
	ServicioSolicitudesDPC servicioSolicitudesDPC;

	@Override
	public ResultadoImpresionBean imprimirSolicitud(Long idImpresion) {
		ResultadoImpresionBean resultadoImpr = new ResultadoImpresionBean(Boolean.FALSE);
		ResultadoBean resultado = new ResultadoBean(Boolean.FALSE);
		try {
			ImpresionVO impresion = servicioImpresion.getDatosImpresion(idImpresion);
			if (impresion != null && StringUtils.isNotBlank(impresion.getTipoTramite())) {
				List<BigDecimal> numExpedientes = servicioImpresionTramiteTrafico.obtenerNumExpedientes(idImpresion);
				if (numExpedientes != null && !numExpedientes.isEmpty()) {
					if (TipoTramiteTrafico.DuplicadoPermisoConducir.getValorEnum().equals(impresion.getTipoTramite())) {
						resultadoImpr = servicioSolicitudesDPC.imprimirSolicitudDPC(numExpedientes);
					} else {
						resultadoImpr.setError(Boolean.TRUE);
						resultadoImpr.setMensaje("El tipo tramite para la impresion no es el correcto");
					}
					resultadoImpr.setListaTramites(numExpedientes);
					if (resultadoImpr != null && !resultadoImpr.getError()) {
						resultado = servicioImpresion.prepararFinalizarImpresion(numExpedientes, resultadoImpr, impresion.getTipoDocumento(), impresion.getContrato(), impresion.getTipoTramite(),
								impresion.getNombreDocumento(), true);
						if (resultadoImpr != null && !resultadoImpr.getError()) {
							resultado = servicioImpresion.finalizarImpresion(idImpresion, resultadoImpr, impresion.getTipoDocumento(), impresion.getIdUsuario(), impresion.getContrato(), impresion
									.getTipoTramite(), impresion.getNombreDocumento());
						}
					}
				} else {
					resultadoImpr.setError(Boolean.TRUE);
					resultadoImpr.setMensaje("No existe numeros de expedientes a imprimir");
				}
				if (resultado != null && resultado.getError()) {
					resultadoImpr.setError(Boolean.TRUE);
					resultadoImpr.setMensaje("Existe algún problema al enviar la solicitud");
				}
				resultadoImpr.setTipoTramite(impresion.getTipoTramite());
				resultadoImpr.setTipoDocumento(impresion.getTipoDocumento());
				resultadoImpr.setNombreDocumento(impresion.getNombreDocumento());
			} else {
				resultadoImpr.setError(Boolean.TRUE);
				resultadoImpr.setMensaje("No se han encontrado datos de la impresion o no tiene tipo de tramite");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de imprimir la solicitud, error: ", e);
			resultadoImpr.setError(Boolean.TRUE);
			resultadoImpr.setMensaje("Ha sucedido un error a la hora de imprimirla solicitud.");
		}
		return resultadoImpr;
	}
}
