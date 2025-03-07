package org.gestoresmadrid.oegamImportacion.utiles;

import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.oegamConversiones.jaxb.pegatina.ParametrosPegatinaMatriculacion;
import org.gestoresmadrid.oegamImportacion.bean.ResultadoBean;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import utilidades.GestorFicherosConfiguracion;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Component
public class PreferenciasEtiquetasImportacion implements Serializable {

	private static final long serialVersionUID = -2021405585385345797L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(PreferenciasEtiquetasImportacion.class);

	public static String RUTA_FICHEROS = "FicherosConfiguracion";
	public static String EXTENSION = ".properties";

	public static String c_columnaPrimer = "etiquetaPreferencias.columnaPrimer";
	public static String c_etiquetasColumna = "etiquetasColumna";
	public static String c_etiquetasFila = "etiquetasFila";
	public static String c_etiquetasTramite = "etiquetasTramite";
	public static String c_filaPrimer = "filaPrimer";
	public static String c_horizontal = "horizontal";
	public static String c_margenDcho = "margenDcho";
	public static String c_margenInf = "margenInf";
	public static String c_margenSup = "margenSup";
	public static String c_margenIzqd = "margenIzqd";
	public static String c_vertical = "vertical";

	@Autowired
	GestorPropiedades gestorPropiedades;
	
	public void guardarPreferencias(ParametrosPegatinaMatriculacion etiquetaParametros, String numColegiado, String idContrato, int numContratosPorColegiado) {
		GestorFicherosConfiguracion ficheroColegiado = null;
		try {
			String rutaPreferencias = gestorPropiedades.valorPropertie("ficheros.NODO1") + gestorPropiedades.valorPropertie(RUTA_FICHEROS);

			if (numContratosPorColegiado > 1) {
				ficheroColegiado = new GestorFicherosConfiguracion(rutaPreferencias + numColegiado + idContrato + EXTENSION);
			} else {
				ficheroColegiado = new GestorFicherosConfiguracion(rutaPreferencias + numColegiado + EXTENSION);
			}

			ficheroColegiado.setPropiedad(c_columnaPrimer, etiquetaParametros.getColumnaPrimer().toString());

			ficheroColegiado.setPropiedad(c_etiquetasColumna, etiquetaParametros.getEtiquetasColumna().toString());

			ficheroColegiado.setPropiedad(c_etiquetasFila, etiquetaParametros.getEtiquetasFila().toString());

			ficheroColegiado.setPropiedad(c_etiquetasTramite, etiquetaParametros.getEtiquetasTramite().toString());

			ficheroColegiado.setPropiedad(c_filaPrimer, etiquetaParametros.getFilaPrimer().toString());

			ficheroColegiado.setPropiedad(c_horizontal, etiquetaParametros.getHorizontal().toString());

			ficheroColegiado.setPropiedad(c_margenDcho, etiquetaParametros.getMargenDcho().toString());

			ficheroColegiado.setPropiedad(c_margenInf, etiquetaParametros.getMargenInf().toString());

			ficheroColegiado.setPropiedad(c_margenIzqd, etiquetaParametros.getMargenIzqd().toString());

			ficheroColegiado.setPropiedad(c_margenSup, etiquetaParametros.getMargenSup().toString());

			ficheroColegiado.setPropiedad(c_vertical, etiquetaParametros.getVertical().toString());
		} catch (Throwable e) {
			log.error("Error al guardar los parametros de las etiquetas", e);
		}
	}

	public ResultadoBean cargarPreferencias(String numColegiado, String idContrato, int numContratosPorColegiado) {
		ResultadoBean resultadoMetodo = new ResultadoBean(Boolean.FALSE);
		File fichero = null;
		try {
			String rutaPreferencias = gestorPropiedades.valorPropertie("ficheros.NODO1") + gestorPropiedades.valorPropertie(RUTA_FICHEROS);

			if (numContratosPorColegiado > 1) {
				fichero = new File(rutaPreferencias + numColegiado + idContrato + EXTENSION);
			} else {
				fichero = new File(rutaPreferencias + numColegiado + EXTENSION);
			}

			if (fichero.exists()) {
				ParametrosPegatinaMatriculacion parametros = new ParametrosPegatinaMatriculacion();
				GestorFicherosConfiguracion ficheroColegiado = null;
				if (numContratosPorColegiado > 1) {
					ficheroColegiado = new GestorFicherosConfiguracion(rutaPreferencias + numColegiado + idContrato + EXTENSION);
				} else {
					ficheroColegiado = new GestorFicherosConfiguracion(rutaPreferencias + numColegiado + EXTENSION);
				}

				parametros.setColumnaPrimer(null != ficheroColegiado.getPropiedad(c_columnaPrimer) ? Integer.parseInt(ficheroColegiado.getPropiedad(c_columnaPrimer)) : 0);
				parametros.setEtiquetasColumna(null != ficheroColegiado.getPropiedad(c_etiquetasColumna) ? Integer.parseInt(ficheroColegiado.getPropiedad(c_etiquetasColumna)) : 0);
				parametros.setEtiquetasFila(null != ficheroColegiado.getPropiedad(c_etiquetasFila) ? Integer.parseInt(ficheroColegiado.getPropiedad(c_etiquetasFila)) : 0);
				parametros.setEtiquetasTramite(null != ficheroColegiado.getPropiedad(c_etiquetasTramite) ? Integer.parseInt(ficheroColegiado.getPropiedad(c_etiquetasTramite)) : 0);
				parametros.setFilaPrimer(null != ficheroColegiado.getPropiedad(c_filaPrimer) ? Integer.parseInt(ficheroColegiado.getPropiedad(c_filaPrimer)) : 0);
				parametros.setHorizontal(null != ficheroColegiado.getPropiedad(c_horizontal) ? new BigDecimal(ficheroColegiado.getPropiedad(c_horizontal)) : null);
				parametros.setMargenDcho(null != ficheroColegiado.getPropiedad(c_margenDcho) ? new BigDecimal(ficheroColegiado.getPropiedad(c_margenDcho)) : null);
				parametros.setMargenInf(null != ficheroColegiado.getPropiedad(c_margenInf) ? new BigDecimal(ficheroColegiado.getPropiedad(c_margenInf)) : null);
				parametros.setMargenSup(null != ficheroColegiado.getPropiedad(c_margenSup) ? new BigDecimal(ficheroColegiado.getPropiedad(c_margenSup)) : null);
				parametros.setMargenIzqd(null != ficheroColegiado.getPropiedad(c_margenIzqd) ? new BigDecimal(ficheroColegiado.getPropiedad(c_margenIzqd)) : null);
				parametros.setVertical(null != ficheroColegiado.getPropiedad(c_vertical) ? new BigDecimal(ficheroColegiado.getPropiedad(c_vertical)) : null);

				resultadoMetodo.setParametros(parametros);
				resultadoMetodo.setError(false);
			} else {
				resultadoMetodo.setError(true);
				resultadoMetodo.setMensaje("No tiene configuración");
			}
		} catch (Throwable e) {
			log.error("Error al guardar los parametros de las etiquetas", e);
			resultadoMetodo.setError(Boolean.TRUE);
			resultadoMetodo.setMensaje("Error al guardar los parametros de las etiquetas");
		}

		return resultadoMetodo;
	}
}
