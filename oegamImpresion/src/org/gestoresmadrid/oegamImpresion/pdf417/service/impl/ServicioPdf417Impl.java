package org.gestoresmadrid.oegamImpresion.pdf417.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.impresion.model.vo.ImpresionVO;
import org.gestoresmadrid.core.model.enumerados.TipoImpreso;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.oegamComun.view.bean.ResultadoBean;
import org.gestoresmadrid.oegamComun.view.bean.ResultadoImpresionBean;
import org.gestoresmadrid.oegamImpresion.pdf.service.ServicioPdfMatw;
import org.gestoresmadrid.oegamImpresion.pdf417.service.ServicioPdf417;
import org.gestoresmadrid.oegamImpresion.pdf417.service.ServicioPdf417Baja;
import org.gestoresmadrid.oegamImpresion.pdf417.service.ServicioPdf417Ctit;
import org.gestoresmadrid.oegamImpresion.service.ServicioImpresionDocumentos;
import org.gestoresmadrid.oegamImpresion.service.ServicioImpresionTramiteTrafico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioPdf417Impl implements ServicioPdf417 {

	private static final long serialVersionUID = 7357135600856718740L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioPdf417Impl.class);

	@Autowired
	ServicioImpresionDocumentos servicioImpresion;

	@Autowired
	ServicioImpresionTramiteTrafico servicioImpresionTramiteTrafico;

	@Autowired
	ServicioPdfMatw servicioPdfMatw;

	@Autowired
	ServicioPdf417Ctit servicioPdf417Ctit;

	@Autowired
	ServicioPdf417Baja servicioPdf417Baja;

	@Override
	public ResultadoImpresionBean imprimirPdf417(Long idImpresion) {
		ResultadoImpresionBean resultadoImpr = new ResultadoImpresionBean(Boolean.FALSE);
		ResultadoBean resultado = new ResultadoBean(Boolean.FALSE);
		try {
			ImpresionVO impresion = servicioImpresion.getDatosImpresion(idImpresion);
			if (impresion != null && StringUtils.isNotBlank(impresion.getTipoTramite())) {
				List<BigDecimal> numExpedientes = servicioImpresionTramiteTrafico.obtenerNumExpedientes(idImpresion);
				if (numExpedientes != null && !numExpedientes.isEmpty()) {
					if (TipoTramiteTrafico.Matriculacion.getValorEnum().equals(impresion.getTipoTramite())) {
						resultadoImpr = servicioPdfMatw.imprimirPdf(numExpedientes, false);
					} else if (TipoTramiteTrafico.TransmisionElectronica.getValorEnum().equals(impresion.getTipoTramite())) {
						resultadoImpr = servicioPdf417Ctit.imprimirPdf417(numExpedientes, false);
					} else if (TipoTramiteTrafico.Baja.getValorEnum().equals(impresion.getTipoTramite())) {
						resultadoImpr = servicioPdf417Baja.imprimirPdf417(numExpedientes, false);
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
					if (resultado.getMensaje() == null || resultado.getMensaje().isEmpty()) {
						resultadoImpr.setMensaje("No se ha podido guardar el fichero generado en la impresion");
					} else {
						resultadoImpr.setMensaje(resultado.getMensaje());
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
			log.error("Ha sucedido un error a la hora de imprimir el Pdf 417, error: ", e);
			resultadoImpr.setError(Boolean.TRUE);
			resultadoImpr.setMensaje("Ha sucedido un error a la hora de imprimir el Pdf 417.");
		}
		return resultadoImpr;
	}

	private String getTipoDocumento() {
		return TipoImpreso.MatriculacionPDF417.toString();
	}
}
