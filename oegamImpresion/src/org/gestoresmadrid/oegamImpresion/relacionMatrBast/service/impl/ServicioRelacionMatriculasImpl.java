package org.gestoresmadrid.oegamImpresion.relacionMatrBast.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.impresion.model.vo.ImpresionVO;
import org.gestoresmadrid.core.model.enumerados.TipoImpreso;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.oegamComun.view.bean.ResultadoBean;
import org.gestoresmadrid.oegamComun.view.bean.ResultadoImpresionBean;
import org.gestoresmadrid.oegamImpresion.relacionMatrBast.service.ServicioRelacionMatriculas;
import org.gestoresmadrid.oegamImpresion.relacionMatrBast.service.ServicioRelacionMatriculasBaja;
import org.gestoresmadrid.oegamImpresion.relacionMatrBast.service.ServicioRelacionMatriculasCtit;
import org.gestoresmadrid.oegamImpresion.relacionMatrBast.service.ServicioRelacionMatriculasDuplicado;
import org.gestoresmadrid.oegamImpresion.relacionMatrBast.service.ServicioRelacionMatriculasMatw;
import org.gestoresmadrid.oegamImpresion.service.ServicioImpresionDocumentos;
import org.gestoresmadrid.oegamImpresion.service.ServicioImpresionTramiteTrafico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioRelacionMatriculasImpl implements ServicioRelacionMatriculas {

	private static final long serialVersionUID = 3032591337108421684L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioRelacionMatriculasImpl.class);

	@Autowired
	ServicioImpresionDocumentos servicioImpresion;

	@Autowired
	ServicioImpresionTramiteTrafico servicioImpresionTramiteTrafico;

	@Autowired
	ServicioRelacionMatriculasMatw servicioRelacionMatriculasMatw;

	@Autowired
	ServicioRelacionMatriculasCtit servicioRelacionMatriculasCtit;

	@Autowired
	ServicioRelacionMatriculasBaja servicioRelacionMatriculasBaja;

	@Autowired
	ServicioRelacionMatriculasDuplicado servicioRelacionMatriculasDuplicado;

	@Override
	public ResultadoImpresionBean imprimirRelacionMatrBast(Long idImpresion) {
		ResultadoImpresionBean resultadoImpr = new ResultadoImpresionBean(Boolean.FALSE);
		ResultadoBean resultado = new ResultadoBean(Boolean.FALSE);
		try {
			ImpresionVO impresion = servicioImpresion.getDatosImpresion(idImpresion);
			if (impresion != null && StringUtils.isNotBlank(impresion.getTipoTramite())) {
				List<BigDecimal> numExpedientes = servicioImpresionTramiteTrafico.obtenerNumExpedientes(idImpresion);
				if (numExpedientes != null && !numExpedientes.isEmpty()) {
					if (TipoTramiteTrafico.Matriculacion.getValorEnum().equals(impresion.getTipoTramite())) {
						resultadoImpr = servicioRelacionMatriculasMatw.imprimirRelacionMatricula(numExpedientes);
					} else if (TipoTramiteTrafico.TransmisionElectronica.getValorEnum().equals(impresion.getTipoTramite())) {
						resultadoImpr = servicioRelacionMatriculasCtit.imprimirRelacionMatricula(numExpedientes);
					} else if (TipoTramiteTrafico.Baja.getValorEnum().equals(impresion.getTipoTramite())) {
						resultadoImpr = servicioRelacionMatriculasBaja.imprimirRelacionMatricula(numExpedientes);
					} else if (TipoTramiteTrafico.Duplicado.getValorEnum().equals(impresion.getTipoTramite())) {
						resultadoImpr = servicioRelacionMatriculasDuplicado.imprimirRelacionMatricula(numExpedientes);
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
			log.error("Ha sucedido un error a la hora de imprimir la relacion matricula / bastidor, error: ", e);
			resultadoImpr.setError(Boolean.TRUE);
			resultadoImpr.setMensaje("Ha sucedido un error a la hora de imprimir la relacion matricula / bastidor.");
		}
		return resultadoImpr;
	}

	private String getTipoDocumento() {
		return TipoImpreso.MatriculacionListadoBastidores.toString();
	}
}
