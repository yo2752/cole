package org.gestoresmadrid.oegamImpresion.declaraciones.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.impresion.model.vo.ImpresionVO;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.oegamComun.view.bean.ResultadoBean;
import org.gestoresmadrid.oegamComun.view.bean.ResultadoImpresionBean;
import org.gestoresmadrid.oegamImpresion.declaracion.responsable.service.ServicioDeclaracionRespDPC;
import org.gestoresmadrid.oegamImpresion.declaracion.responsable.service.ServicioDeclaracionRespPermInter;
import org.gestoresmadrid.oegamImpresion.declaraciones.service.ServicioDeclaraciones;
import org.gestoresmadrid.oegamImpresion.declaraciones.service.ServicioDeclaracionesBaja;
import org.gestoresmadrid.oegamImpresion.declaraciones.service.ServicioDeclaracionesCtit;
import org.gestoresmadrid.oegamImpresion.service.ServicioImpresionDocumentos;
import org.gestoresmadrid.oegamImpresion.service.ServicioImpresionTramiteTrafico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioDeclaracionesImpl implements ServicioDeclaraciones {

	private static final long serialVersionUID = 4874511328734656889L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioDeclaracionesImpl.class);

	@Autowired
	ServicioImpresionDocumentos servicioImpresion;

	@Autowired
	ServicioImpresionTramiteTrafico servicioImpresionTramiteTrafico;

	@Autowired
	ServicioDeclaracionesCtit servicioDeclaracionesCtit;

	@Autowired
	ServicioDeclaracionesBaja servicioDeclaracionesBaja;

	@Autowired
	ServicioDeclaracionRespPermInter servicioDeclaracionRespPermInter;

	@Autowired
	ServicioDeclaracionRespDPC servicioDeclaracionRespDPC;

	@Override
	public ResultadoImpresionBean imprimirDeclaracion(Long idImpresion) {
		ResultadoImpresionBean resultadoImpr = new ResultadoImpresionBean(Boolean.FALSE);
		ResultadoBean resultado = new ResultadoBean(Boolean.FALSE);
		try {
			ImpresionVO impresion = servicioImpresion.getDatosImpresion(idImpresion);
			if (impresion != null && StringUtils.isNotBlank(impresion.getTipoTramite())) {
				boolean firmarDocumento = false;
				List<BigDecimal> numExpedientes = servicioImpresionTramiteTrafico.obtenerNumExpedientes(idImpresion);
				if (numExpedientes != null && !numExpedientes.isEmpty()) {
					if (TipoTramiteTrafico.TransmisionElectronica.getValorEnum().equals(impresion.getTipoTramite())) {
						resultadoImpr = servicioDeclaracionesCtit.imprimirDeclaracion(numExpedientes, impresion.getTipoInterviniente(), impresion.getTipoDocumento());
					} else if (TipoTramiteTrafico.Baja.getValorEnum().equals(impresion.getTipoTramite())) {
						resultadoImpr = servicioDeclaracionesBaja.imprimirDeclaracion(numExpedientes, impresion.getTipoInterviniente(), impresion.getTipoDocumento());
					} else if (TipoTramiteTrafico.PermisonInternacional.getValorEnum().equals(impresion.getTipoTramite())) {
						resultadoImpr = servicioDeclaracionRespPermInter.imprimirDeclaracionRespColegiado(numExpedientes);
						firmarDocumento = true;
					} else if (TipoTramiteTrafico.DuplicadoPermisoConducir.getValorEnum().equals(impresion.getTipoTramite())) {
						resultadoImpr = servicioDeclaracionRespDPC.imprimirDeclaracionRespColegiado(numExpedientes);
						firmarDocumento = true;
					} else {
						resultadoImpr.setError(Boolean.TRUE);
						resultadoImpr.setMensaje("El tipo tramite para la impresion no es el correcto");
					}
					resultadoImpr.setListaTramites(numExpedientes);
					if (resultadoImpr != null && !resultadoImpr.getError()) {
						resultado = servicioImpresion.prepararFinalizarImpresion(numExpedientes, resultadoImpr, impresion.getTipoDocumento(), impresion.getContrato(), impresion.getTipoTramite(),
								impresion.getNombreDocumento(), firmarDocumento);
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
					if (TipoTramiteTrafico.PermisonInternacional.getValorEnum().equals(impresion.getTipoTramite()) || TipoTramiteTrafico.DuplicadoPermisoConducir.getValorEnum().equals(impresion
							.getTipoTramite())) {
						resultadoImpr.setMensaje("Existe algún problema al enviar la documentación de declaración de responsabilidad");
					} else {
						resultadoImpr.setMensaje("No se ha podido guardar el fichero generado en la impresion");
					}
				}
				resultadoImpr.setTipoTramite(impresion.getTipoTramite());
				resultadoImpr.setTipoDocumento(impresion.getTipoDocumento());
				resultadoImpr.setNombreDocumento(impresion.getNombreDocumento());
			} else {
				resultadoImpr.setError(Boolean.TRUE);
				resultadoImpr.setMensaje("No se han encontrado datos de la impresion o no tiene tipo de tramite");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de imprimir la declaración, error: ", e);
			resultadoImpr.setError(Boolean.TRUE);
			resultadoImpr.setMensaje("Ha sucedido un error a la hora de imprimirla declaración.");
		}
		return resultadoImpr;
	}
}
