package org.gestoresmadrid.oegamImportacion.schemas.utiles;

import java.io.File;
import java.io.Serializable;

import org.gestoresmadrid.oegamConversiones.jaxb.cet.OptiCET620;
import org.gestoresmadrid.oegamConversiones.jaxb.cet.OptiCET620.Autoliquidacion;
import org.gestoresmadrid.oegamConversiones.schemas.utiles.XMLCetFactory;
import org.gestoresmadrid.oegamImportacion.bean.ResultadoImportacionBean;
import org.gestoresmadrid.oegamImportacion.cet.bean.AutoliquidacionBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.excepciones.XmlNoValidoExcepcion;

@Component
public class ConversionFormatoImpGACet implements Serializable {

	private static final long serialVersionUID = 4854599595624618514L;

	private static ILoggerOegam log = LoggerOegam.getLogger(ConversionFormatoImpGACet.class);

	@Autowired
	XMLCetFactory xMLCetFactory;

	public ResultadoImportacionBean convertirFicheroFormatoGA(File fichero) {
		ResultadoImportacionBean resultado = new ResultadoImportacionBean(Boolean.FALSE);
		try {
			xMLCetFactory.validarXMLFORMATOGACet(fichero);
			OptiCET620 ficheroCet = xMLCetFactory.getFormatoCet(fichero);
			resultado.setFormatoOegam2Cet(ficheroCet);
		} catch (XmlNoValidoExcepcion e) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No se puede importar el fichero por los siguientes errores: " + e.getMensajeError1());
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora convertir el fichero, error", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora convertir el fichero.");
		}
		return resultado;
	}

	public ResultadoImportacionBean convertirFormatoGAToParams(OptiCET620 formatoOegam2Cet) {
		ResultadoImportacionBean resultado = new ResultadoImportacionBean(Boolean.FALSE);
		try {
			int posicion = 0;
			for (Autoliquidacion cetImportar : formatoOegam2Cet.getAutoliquidacion()) {
				AutoliquidacionBean autoliquidacionBean = new AutoliquidacionBean();
				autoliquidacionBean.setCet(cetImportar.getCET());
				autoliquidacionBean.setMatricula(cetImportar.getMatricula());
				autoliquidacionBean.setCodigo(cetImportar.getCodigo());
				resultado.addListaCets(autoliquidacionBean, posicion);
				posicion++;
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora convertir el fichero, error", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora convertir el fichero.");
		}
		return resultado;
	}
}
