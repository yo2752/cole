package org.gestoresmadrid.oegamImpresion.mandatoEspecifico.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.impresion.model.vo.ImpresionVO;
import org.gestoresmadrid.core.model.enumerados.TipoImpreso;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.oegamComun.view.bean.ResultadoBean;
import org.gestoresmadrid.oegamComun.view.bean.ResultadoImpresionBean;
import org.gestoresmadrid.oegamImpresion.mandatoEspecifico.service.ServicioMandatoEspecifico;
import org.gestoresmadrid.oegamImpresion.mandatoEspecifico.service.ServicioMandatoEspecificoBaja;
import org.gestoresmadrid.oegamImpresion.mandatoEspecifico.service.ServicioMandatoEspecificoCtit;
import org.gestoresmadrid.oegamImpresion.mandatoEspecifico.service.ServicioMandatoEspecificoDuplPermCond;
import org.gestoresmadrid.oegamImpresion.mandatoEspecifico.service.ServicioMandatoEspecificoDuplicado;
import org.gestoresmadrid.oegamImpresion.mandatoEspecifico.service.ServicioMandatoEspecificoMatw;
import org.gestoresmadrid.oegamImpresion.mandatoEspecifico.service.ServicioMandatoEspecificoPermInter;
import org.gestoresmadrid.oegamImpresion.mandatoEspecifico.service.ServicioMandatoEspecificoSolicitud;
import org.gestoresmadrid.oegamImpresion.service.ServicioImpresionDocumentos;
import org.gestoresmadrid.oegamImpresion.service.ServicioImpresionTramiteTrafico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioMandatoEspecificoImpl implements ServicioMandatoEspecifico {

	private static final long serialVersionUID = -2540067766124243234L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioMandatoEspecificoImpl.class);

	@Autowired
	ServicioImpresionDocumentos servicioImpresion;

	@Autowired
	ServicioImpresionTramiteTrafico servicioImpresionTramiteTrafico;

	@Autowired
	ServicioMandatoEspecificoMatw servicioMandatoEspecificoMatw;

	@Autowired
	ServicioMandatoEspecificoCtit servicioMandatoEspecificoCtit;

	@Autowired
	ServicioMandatoEspecificoBaja servicioMandatoEspecificoBaja;

	@Autowired
	ServicioMandatoEspecificoDuplicado servicioMandatoEspecificoDuplicado;

	@Autowired
	ServicioMandatoEspecificoSolicitud servicioMandatoEspecificoSolicitud;

	@Autowired
	ServicioMandatoEspecificoPermInter servicioMandatoEspecificoPermInter;

	@Autowired
	ServicioMandatoEspecificoDuplPermCond servicioMandatoEspecificoDuplPermCond;

	@Override
	public ResultadoImpresionBean imprimirMandatoEspecifico(Long idImpresion) {
		ResultadoImpresionBean resultadoImpr = new ResultadoImpresionBean(Boolean.FALSE);
		ResultadoBean resultado = new ResultadoBean(Boolean.FALSE);
		try {
			ImpresionVO impresion = servicioImpresion.getDatosImpresion(idImpresion);
			if (impresion != null && StringUtils.isNotBlank(impresion.getTipoTramite())) {
				List<BigDecimal> numExpedientes = servicioImpresionTramiteTrafico.obtenerNumExpedientes(idImpresion);
				if (numExpedientes != null && !numExpedientes.isEmpty()) {
					if (TipoTramiteTrafico.Matriculacion.getValorEnum().equals(impresion.getTipoTramite())) {
						resultadoImpr = servicioMandatoEspecificoMatw.imprimirMandatoEspecifico(numExpedientes);
					} else if (TipoTramiteTrafico.TransmisionElectronica.getValorEnum().equals(impresion.getTipoTramite())) {
						resultadoImpr = servicioMandatoEspecificoCtit.imprimirMandatoEspecifico(numExpedientes, impresion.getTipoInterviniente());
					} else if (TipoTramiteTrafico.Baja.getValorEnum().equals(impresion.getTipoTramite())) {
						resultadoImpr = servicioMandatoEspecificoBaja.imprimirMandatoEspecifico(numExpedientes);
					} else if (TipoTramiteTrafico.Duplicado.getValorEnum().equals(impresion.getTipoTramite())) {
						resultadoImpr = servicioMandatoEspecificoDuplicado.imprimirMandatoEspecifico(numExpedientes);
					} else if (TipoTramiteTrafico.Solicitud.getValorEnum().equals(impresion.getTipoTramite())) {
						resultadoImpr = servicioMandatoEspecificoSolicitud.imprimirMandatoEspecifico(numExpedientes);
					} else if (TipoTramiteTrafico.PermisonInternacional.getValorEnum().equals(impresion.getTipoTramite())) {
						resultadoImpr = servicioMandatoEspecificoPermInter.imprimirMandatoEspecifico(numExpedientes);
					} else if (TipoTramiteTrafico.DuplicadoPermisoConducir.getValorEnum().equals(impresion.getTipoTramite())) {
						resultadoImpr = servicioMandatoEspecificoDuplPermCond.imprimirMandatoEspecifico(numExpedientes);
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
			log.error("Ha sucedido un error a la hora de imprimir el mandato especifico, error: ", e);
			resultadoImpr.setError(Boolean.TRUE);
			resultadoImpr.setMensaje("Ha sucedido un error a la hora de imprimir el mandato especifico.");
		}
		return resultadoImpr;
	}

	private String getTipoDocumento() {
		return TipoImpreso.MatriculacionMandatoEspecifico.toString();
	}
}
