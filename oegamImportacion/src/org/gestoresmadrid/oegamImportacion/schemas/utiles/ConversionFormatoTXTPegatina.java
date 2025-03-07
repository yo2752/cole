package org.gestoresmadrid.oegamImportacion.schemas.utiles;

import java.io.File;
import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.oegamImportacion.bean.ResultadoImportacionBean;
import org.gestoresmadrid.oegamImportacion.cet.bean.PegatinaBean;
import org.gestoresmadrid.oegamImportacion.constantes.ConstantesPDF;
import org.gestoresmadrid.oegamImportacion.utiles.BasicText;
import org.gestoresmadrid.oegamImportacion.utiles.UtilidadesFicheroImportacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.excepciones.XmlNoValidoExcepcion;

@Component
public class ConversionFormatoTXTPegatina implements Serializable {

	private static final long serialVersionUID = -2413372601372883675L;

	private static ILoggerOegam log = LoggerOegam.getLogger(ConversionFormatoTXTPegatina.class);

	@Autowired
	UtilidadesFicheroImportacion utilidadesFichero;

	public ResultadoImportacionBean convertirFicheroALineas(File fichero) {
		ResultadoImportacionBean resultado = new ResultadoImportacionBean(Boolean.FALSE);
		try {
			List<String> lineasImportacion = utilidadesFichero.obtenerLineasFicheroTexto(fichero);
			if (lineasImportacion != null && !lineasImportacion.isEmpty()) {
				resultado.setLineasImportPegatinas(lineasImportacion);
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("El fichero no contiene datos que importar.");
			}
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

	public ResultadoImportacionBean convertirLineasToBean(List<String> lineasImportacion) {
		ResultadoImportacionBean resultado = new ResultadoImportacionBean(Boolean.FALSE);
		try {
			String matricula = " ";
			int posicion = 0;
			if (lineasImportacion != null && !lineasImportacion.isEmpty()) {
				for (String lineaImportacion : lineasImportacion) {
					if (!lineaImportacion.contains(";")) {
						resultado.setError(Boolean.TRUE);
						resultado.setMensaje("Error al importar el fichero, el formato no es el adecuado.");
						break;
					} else {
						String[] valores = lineaImportacion.split(";");
						PegatinaBean pegatina = new PegatinaBean();
						if (valores.length > 0) {
							pegatina.setBastidor(valores[0]);
							if (valores.length > 1) {
								pegatina.setMatricula(valores[1]);
							} else {
								pegatina.setMatricula(matricula);
							}
						} else {
							pegatina.setBastidor(BasicText.changeSize("", ConstantesPDF.NUMERO_CARACTERES_BASTIDOR_PEGATINA));
							pegatina.setMatricula(matricula);
						}
						resultado.addListaTramitePegatina(pegatina, posicion);
						posicion++;
					}
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No hay datos para importar.");
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora convertir el fichero, error", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora convertir el fichero.");
		}
		return resultado;
	}
}
