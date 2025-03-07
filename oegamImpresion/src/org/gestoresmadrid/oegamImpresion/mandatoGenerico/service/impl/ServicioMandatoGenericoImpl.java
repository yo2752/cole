package org.gestoresmadrid.oegamImpresion.mandatoGenerico.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.impresion.model.vo.ImpresionVO;
import org.gestoresmadrid.core.model.enumerados.TipoImpreso;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.oegamComun.view.bean.ResultadoBean;
import org.gestoresmadrid.oegamComun.view.bean.ResultadoImpresionBean;
import org.gestoresmadrid.oegamImpresion.mandatoGenerico.service.ServicioMandatoGenerico;
import org.gestoresmadrid.oegamImpresion.mandatoGenerico.service.ServicioMandatoGenericoBaja;
import org.gestoresmadrid.oegamImpresion.mandatoGenerico.service.ServicioMandatoGenericoCtit;
import org.gestoresmadrid.oegamImpresion.mandatoGenerico.service.ServicioMandatoGenericoDuplPermCond;
import org.gestoresmadrid.oegamImpresion.mandatoGenerico.service.ServicioMandatoGenericoDuplicado;
import org.gestoresmadrid.oegamImpresion.mandatoGenerico.service.ServicioMandatoGenericoMatw;
import org.gestoresmadrid.oegamImpresion.mandatoGenerico.service.ServicioMandatoGenericoPermInter;
import org.gestoresmadrid.oegamImpresion.mandatoGenerico.service.ServicioMandatoGenericoSolicitud;
import org.gestoresmadrid.oegamImpresion.service.ServicioImpresionDocumentos;
import org.gestoresmadrid.oegamImpresion.service.ServicioImpresionTramiteTrafico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioMandatoGenericoImpl implements ServicioMandatoGenerico {

	private static final long serialVersionUID = 3144359806833679612L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioMandatoGenericoImpl.class);

	@Autowired
	ServicioImpresionDocumentos servicioImpresion;

	@Autowired
	ServicioImpresionTramiteTrafico servicioImpresionTramiteTrafico;

	@Autowired
	ServicioMandatoGenericoMatw servicioMandatoGenericoMatw;

	@Autowired
	ServicioMandatoGenericoCtit servicioMandatoGenericoCtit;

	@Autowired
	ServicioMandatoGenericoBaja servicioMandatoGenericoBaja;

	@Autowired
	ServicioMandatoGenericoDuplicado servicioMandatoGenericoDuplicado;

	@Autowired
	ServicioMandatoGenericoSolicitud servicioMandatoGenericoSolicitud;

	@Autowired
	ServicioMandatoGenericoPermInter servicioMandatoGenericoPermInter;

	@Autowired
	ServicioMandatoGenericoDuplPermCond servicioMandatoGenericoDuplPermCond;

	@Override
	public ResultadoImpresionBean imprimirMandatoGenerico(Long idImpresion) {
		ResultadoImpresionBean resultadoImpr = new ResultadoImpresionBean(Boolean.FALSE);
		ResultadoBean resultado = new ResultadoBean(Boolean.FALSE);
		try {
			ImpresionVO impresion = servicioImpresion.getDatosImpresion(idImpresion);
			if (impresion != null && StringUtils.isNotBlank(impresion.getTipoTramite())) {
				List<BigDecimal> numExpedientes = servicioImpresionTramiteTrafico.obtenerNumExpedientes(idImpresion);
				if (numExpedientes != null && !numExpedientes.isEmpty()) {
					if (TipoTramiteTrafico.Matriculacion.getValorEnum().equals(impresion.getTipoTramite())) {
						resultadoImpr = servicioMandatoGenericoMatw.imprimirMandatoGenerico(numExpedientes);
					} else if (TipoTramiteTrafico.TransmisionElectronica.getValorEnum().equals(impresion.getTipoTramite())) {
						resultadoImpr = servicioMandatoGenericoCtit.imprimirMandatoGenerico(numExpedientes, impresion.getTipoInterviniente());
					} else if (TipoTramiteTrafico.Baja.getValorEnum().equals(impresion.getTipoTramite())) {
						resultadoImpr = servicioMandatoGenericoBaja.imprimirMandatoGenerico(numExpedientes);
					} else if (TipoTramiteTrafico.Duplicado.getValorEnum().equals(impresion.getTipoTramite())) {
						resultadoImpr = servicioMandatoGenericoDuplicado.imprimirMandatoGenerico(numExpedientes);
					} else if (TipoTramiteTrafico.Solicitud.getValorEnum().equals(impresion.getTipoTramite())) {
						resultadoImpr = servicioMandatoGenericoSolicitud.imprimirMandatoGenerico(numExpedientes);
					} else if (TipoTramiteTrafico.PermisonInternacional.getValorEnum().equals(impresion.getTipoTramite())) {
						resultadoImpr = servicioMandatoGenericoPermInter.imprimirMandatoGenerico(numExpedientes);
					} else if (TipoTramiteTrafico.DuplicadoPermisoConducir.getValorEnum().equals(impresion.getTipoTramite())) {
						resultadoImpr = servicioMandatoGenericoDuplPermCond.imprimirMandatoGenerico(numExpedientes);
					} else {
						resultadoImpr.setError(Boolean.TRUE);
						resultadoImpr.setMensaje("El tipo tramite para la impresion no es el correcto");
					}
					resultadoImpr.setListaTramites(numExpedientes);
					if (resultadoImpr != null && !resultadoImpr.getError()) {
						resultado = servicioImpresion.prepararFinalizarImpresion(numExpedientes, resultadoImpr, getTipoDocumento(), impresion.getContrato(), impresion.getTipoTramite(), impresion
								.getNombreDocumento(), false);
						if (resultadoImpr != null && !resultadoImpr.getError()) {
							resultado = servicioImpresion.finalizarImpresion(idImpresion, resultadoImpr, getTipoDocumento(), impresion.getIdUsuario(), impresion.getContrato(), impresion
									.getTipoTramite(), impresion.getNombreDocumento());
						}
					}
				} else {
					resultadoImpr.setError(Boolean.TRUE);
					resultadoImpr.setMensaje("No existe numeros de expedientes a imprimir");
				}
				if (resultado != null && resultado.getError()) {
					resultadoImpr.setError(Boolean.TRUE);
					resultadoImpr.setMensaje("No se ha podido guardar el fichero generado en la impresion");
				}
				resultadoImpr.setTipoTramite(impresion.getTipoTramite());
				resultadoImpr.setTipoDocumento(impresion.getTipoDocumento());
				resultadoImpr.setNombreDocumento(impresion.getNombreDocumento());
			} else {
				resultadoImpr.setError(Boolean.TRUE);
				resultadoImpr.setMensaje("No se han encontrado datos de la impresion o no tiene tipo de tramite");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de imprimir el mandato generico, error: ", e);
			resultadoImpr.setError(Boolean.TRUE);
			resultadoImpr.setMensaje("Ha sucedido un error a la hora de imprimir el mandato generico.");
		}
		return resultadoImpr;
	}

	private String getTipoDocumento() {
		return TipoImpreso.MatriculacionMandatoGenerico.toString();
	}
}
