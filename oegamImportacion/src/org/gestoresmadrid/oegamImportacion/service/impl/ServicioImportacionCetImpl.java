package org.gestoresmadrid.oegamImportacion.service.impl;

import java.io.File;

import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.oegamImportacion.bean.ResultadoBean;
import org.gestoresmadrid.oegamImportacion.bean.ResultadoImportacionBean;
import org.gestoresmadrid.oegamImportacion.bean.ResultadoValidacionImporBean;
import org.gestoresmadrid.oegamImportacion.cet.bean.AutoliquidacionBean;
import org.gestoresmadrid.oegamImportacion.schemas.utiles.ConversionFormatoImpGACet;
import org.gestoresmadrid.oegamImportacion.service.ServicioImportacionCet;
import org.gestoresmadrid.oegamImportacion.service.ServicioValidacionesImportacion;
import org.gestoresmadrid.oegamImportacion.trafico.service.ServicioTramiteTraficoTransmisionImportacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioImportacionCetImpl implements ServicioImportacionCet {

	private static final long serialVersionUID = -1638600305126950373L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioImportacionCetImpl.class);

	@Autowired
	ConversionFormatoImpGACet conversionFormatoGACet;

	@Autowired
	ServicioTramiteTraficoTransmisionImportacion servicioTramiteTraficoTransmision;

	@Autowired
	ServicioValidacionesImportacion servicioValidaciones;

	@Override
	public ResultadoImportacionBean convertirFicheroImportacion(File fichero) {
		ResultadoImportacionBean resultado = new ResultadoImportacionBean(Boolean.FALSE);
		try {
			resultado = conversionFormatoGACet.convertirFicheroFormatoGA(fichero);
			if (resultado != null && !resultado.getError()) {
				resultado = conversionFormatoGACet.convertirFormatoGAToParams(resultado.getFormatoOegam2Cet());
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de tratar el fichero de importacion cet, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de tratar el fichero de importacion cet.");
		}
		return resultado;
	}

	@Override
	public ResultadoValidacionImporBean validarAutoliquidacion(AutoliquidacionBean autoliquidacion) {
		ResultadoValidacionImporBean resultado = new ResultadoValidacionImporBean(Boolean.FALSE);
		try {
			resultado = servicioValidaciones.validarAutoliquidacion(autoliquidacion);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de validar el tramite, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de validar el tramite.");
		}
		return resultado;
	}

	@Override
	public ResultadoImportacionBean autoliquidacion(AutoliquidacionBean autoliquidacion, ContratoVO contratoBBDD) {
		ResultadoImportacionBean resultadoImportacion = new ResultadoImportacionBean(Boolean.FALSE);
		try {
			ResultadoBean resultado = servicioTramiteTraficoTransmision.autoliquidacion(autoliquidacion, contratoBBDD.getIdContrato());
			if (resultado != null && resultado.getError()) {
				resultadoImportacion.setError(Boolean.TRUE);
				resultadoImportacion.setMensaje(resultado.getMensaje());
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de validar el tramite, error: ", e);
			resultadoImportacion.setError(Boolean.TRUE);
			resultadoImportacion.setMensaje("Ha sucedido un error a la hora de validar el tramite.");
		}
		return resultadoImportacion;
	}
}
