package org.gestoresmadrid.oegamInterga.service.impl;

import java.io.File;
import java.math.BigDecimal;

import org.gestoresmadrid.core.impresion.model.enumerados.TipoDocumentoImpresiones;
import org.gestoresmadrid.core.impresion.model.vo.ImpresionVO;
import org.gestoresmadrid.core.model.enumerados.TipoImpreso;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.core.trafico.interga.model.enumerados.TipoTramiteInterga;
import org.gestoresmadrid.oegamComun.view.bean.ResultadoBean;
import org.gestoresmadrid.oegamComun.view.bean.ResultadoImpresionBean;
import org.gestoresmadrid.oegamImpresion.service.ServicioImpresionDocumentos;
import org.gestoresmadrid.oegamInterga.service.ServicioInterga;
import org.gestoresmadrid.oegamInterga.view.bean.ResultadoIntergaBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioIntergaImpl implements ServicioInterga {

	private static final long serialVersionUID = 5105705845893506940L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioIntergaImpl.class);

	@Autowired
	ServicioImpresionDocumentos servicioImpresionDocumentos;

	@Override
	public ResultadoIntergaBean generarMandato(BigDecimal numExpediente, Long idContrato, Long idUsuario, String tipoTramite) {
		ResultadoIntergaBean result = new ResultadoIntergaBean(Boolean.FALSE);
		try {
			ImpresionVO impresion = servicioImpresionDocumentos.getDatosPorNombreDocumentoSinEliminado(obtenerNombreDocumentoMandato(numExpediente, tipoTramite));
			if (impresion != null) {
				result.setMensaje("Ya existe un mandato generado, si quiere solicitar uno nuevo borre el antiguo.");
				result.setError(Boolean.TRUE);
			} else {
				String[] numExpedientes = new String[1];
				numExpedientes[0] = numExpediente.toString();
				ResultadoImpresionBean resultado = servicioImpresionDocumentos.crearImpresion(numExpedientes, idContrato, idUsuario, TipoImpreso.MatriculacionMandatoEspecifico.toString(), tipoTramite,
						null);
				if (resultado != null && !resultado.getError()) {
					result.setMensaje("Su solicitud de impresión se está procesando. Estará disponible en unos minutos. Acuda a la pestaña Mandato para descargarla.");
				} else {
					result.setMensaje("Existen errores en la solicitud de impresión.");
					result.setError(Boolean.TRUE);
				}
			}
		} catch (Exception e) {
			log.error("Existen errores en la solicitud de impresión para el mandato de interga.", e);
			result.setMensaje("Existen errores en la solicitud de impresión.");
			result.setError(Boolean.TRUE);
		}
		return result;
	}

	@Override
	public ResultadoIntergaBean eliminarMandato(BigDecimal numExpediente, String tipoTramite) {
		ResultadoIntergaBean result = new ResultadoIntergaBean(Boolean.FALSE);
		try {
			ResultadoBean resultado = servicioImpresionDocumentos.eliminarFichero(obtenerNombreDocumentoMandato(numExpediente, tipoTramite));
			if (resultado != null && resultado.getError()) {
				result.setError(Boolean.TRUE);
				result.setMensaje(resultado.getMensaje());
			}
		} catch (Exception e) {
			log.error("Existen errores a la hora de eliminar el mandato de interga.", e);
			result.setMensaje("Existen errores a la hora de eliminar el mandato.");
			result.setError(Boolean.TRUE);
		}
		return result;
	}

	@Override
	public ResultadoIntergaBean descargarFichero(String nombreDocumento, Long idUsuario) {
		ResultadoIntergaBean result = new ResultadoIntergaBean(Boolean.FALSE);
		try {
			File fichero = servicioImpresionDocumentos.descargarFichero(nombreDocumento, idUsuario);
			if (fichero != null) {
				result.setFichero(fichero);
			} else {
				result.setError(Boolean.TRUE);
				result.setMensaje("No se ha podido descargar el fichero.");
			}
		} catch (Throwable e) {
			log.error("Error al descargar el fichero", e);
			result.setError(Boolean.TRUE);
		}
		return result;
	}

	@Override
	public ResultadoIntergaBean descargarMandato(BigDecimal numExpediente, Long idUsuario, String tipoTramite) {
		ResultadoIntergaBean result = new ResultadoIntergaBean(Boolean.FALSE);
		try {
			File fichero = servicioImpresionDocumentos.descargarFichero(obtenerNombreDocumentoMandato(numExpediente, tipoTramite), idUsuario);
			if (fichero != null) {
				result.setFichero(fichero);
			} else {
				result.setError(Boolean.TRUE);
			}
		} catch (Throwable e) {
			log.error("Error al descargar el mandato de interga", e);
			result.setError(Boolean.TRUE);
		}
		return result;
	}

	@Override
	public String obtenerNombreDocumentoMandato(BigDecimal numExpediente, String tipoTramite) {
		String nombreDocumento = TipoDocumentoImpresiones.MandatoEspecifico.getNombreDocumento() + "_";
		if (TipoTramiteTrafico.PermisonInternacional.getValorEnum().equals(tipoTramite)) {
			nombreDocumento += TipoTramiteInterga.Permiso_Internacional.getValorEnum();
		} else if (TipoTramiteTrafico.DuplicadoPermisoConducir.getValorEnum().equals(tipoTramite)) {
			nombreDocumento += TipoTramiteInterga.Duplicado_Permiso_Conducir.getValorEnum();
		}
		nombreDocumento += "_" + numExpediente;
		return nombreDocumento;
	}

}