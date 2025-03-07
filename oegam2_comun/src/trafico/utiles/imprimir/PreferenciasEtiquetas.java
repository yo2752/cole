package trafico.utiles.imprimir;

import java.io.File;
import java.math.BigDecimal;

import org.gestoresmadrid.core.springContext.ContextoSpring;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;

import escrituras.beans.ResultBean;
import trafico.beans.utiles.ParametrosPegatinaMatriculacion;
import trafico.utiles.ConstantesPDF;
import utilidades.GestorFicherosConfiguracion;
import utilidades.web.OegamExcepcion;

public class PreferenciasEtiquetas {

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

	/**
	 * Comprobará si hay un fichero de configuración para ese colegiado y en caso de
	 * que no lo tenga se lo crea.
	 * 
	 * @param numColegiado
	 * @return
	 * @throws OegamExcepcion
	 */
	public static ResultBean cargarPreferencias(String numColegiado) throws OegamExcepcion {
		// Para la ruta de los ficheros
		GestorPropiedades gestorPropiedades = ContextoSpring.getInstance().getBean(GestorPropiedades.class);
		String rutaPreferencias = gestorPropiedades.valorPropertie("ficheros.NODO1")
				+ gestorPropiedades.valorPropertie(RUTA_FICHEROS);

		ResultBean resultadoMetodo = new ResultBean();
		ParametrosPegatinaMatriculacion parametros = new ParametrosPegatinaMatriculacion();

		String idContrato = String.valueOf(getUtilesColegiado().getIdContratoSessionBigDecimal());

		File fichero = null;

		/**
		 * Incidencia 1903 . Se va a aniadir que para los colegiados con mas de un
		 * contrato las etiquetas puedan guardarse en funcion del colegio. Para ello
		 * necesito saber el numero de contratos
		 * 
		 * @return
		 */
		if (getUtilesColegiado().getNumContratosColegiado() > 1) {
			fichero = new File(rutaPreferencias + numColegiado + idContrato + EXTENSION);
		} else {
			fichero = new File(rutaPreferencias + numColegiado + EXTENSION);
		}

		if (fichero.exists()) {
			/**
			 * Incidencia 1903 . Se va a aniadir que para los colegiados con mas de un
			 * contrato las etiquetas puedan guardarse en funcion del colegio. Para ello
			 * necesito saber el numero de contratos
			 * 
			 * @return
			 */
			GestorFicherosConfiguracion ficheroColegiado = null;
			if (getUtilesColegiado().getNumContratosColegiado() > 1) {
				// Para la ruta de un fichero en concreto del colegiado.
				ficheroColegiado = new GestorFicherosConfiguracion(
						rutaPreferencias + numColegiado + idContrato + EXTENSION);
			} else {
				ficheroColegiado = new GestorFicherosConfiguracion(rutaPreferencias + numColegiado + EXTENSION);
			}

			parametros.setColumnaPrimer(null != ficheroColegiado.getPropiedad(c_columnaPrimer)
					? Integer.parseInt(ficheroColegiado.getPropiedad(c_columnaPrimer))
					: 0);
			parametros.setEtiquetasColumna(null != ficheroColegiado.getPropiedad(c_etiquetasColumna)
					? Integer.parseInt(ficheroColegiado.getPropiedad(c_etiquetasColumna))
					: 0);
			parametros.setEtiquetasFila(null != ficheroColegiado.getPropiedad(c_etiquetasFila)
					? Integer.parseInt(ficheroColegiado.getPropiedad(c_etiquetasFila))
					: 0);
			parametros.setEtiquetasTramite(null != ficheroColegiado.getPropiedad(c_etiquetasTramite)
					? Integer.parseInt(ficheroColegiado.getPropiedad(c_etiquetasTramite))
					: 0);
			parametros.setFilaPrimer(null != ficheroColegiado.getPropiedad(c_filaPrimer)
					? Integer.parseInt(ficheroColegiado.getPropiedad(c_filaPrimer))
					: 0);
			parametros.setHorizontal(null != ficheroColegiado.getPropiedad(c_horizontal)
					? new BigDecimal(ficheroColegiado.getPropiedad(c_horizontal))
					: null);
			parametros.setMargenDcho(null != ficheroColegiado.getPropiedad(c_margenDcho)
					? new BigDecimal(ficheroColegiado.getPropiedad(c_margenDcho))
					: null);
			parametros.setMargenInf(null != ficheroColegiado.getPropiedad(c_margenInf)
					? new BigDecimal(ficheroColegiado.getPropiedad(c_margenInf))
					: null);
			parametros.setMargenSup(null != ficheroColegiado.getPropiedad(c_margenSup)
					? new BigDecimal(ficheroColegiado.getPropiedad(c_margenSup))
					: null);
			parametros.setMargenIzqd(null != ficheroColegiado.getPropiedad(c_margenIzqd)
					? new BigDecimal(ficheroColegiado.getPropiedad(c_margenIzqd))
					: null);
			parametros.setVertical(null != ficheroColegiado.getPropiedad(c_vertical)
					? new BigDecimal(ficheroColegiado.getPropiedad(c_vertical))
					: null);

			resultadoMetodo.addAttachment(ConstantesPDF.PREFERENCIAS, parametros);
			resultadoMetodo.setError(false);
		} else {
			resultadoMetodo.setError(true);
			resultadoMetodo.setMensaje("No tiene configuración");
		}

		return resultadoMetodo;
	}

	/**
	 * Método que guardará las especificaciones en el fichero de configuración del
	 * colegiado que sea.
	 * 
	 * @param etiquetaParametros
	 * @param numColegiado
	 * @return
	 * @throws OegamExcepcion
	 */
	public static ResultBean guardarPreferencias(ParametrosPegatinaMatriculacion etiquetaParametros,
			String numColegiado) throws OegamExcepcion {
		GestorPropiedades gestorPropiedades = ContextoSpring.getInstance().getBean(GestorPropiedades.class);
		// Para la ruta de los ficheros
		String rutaPreferencias = gestorPropiedades.valorPropertie("ficheros.NODO1")
				+ gestorPropiedades.valorPropertie(RUTA_FICHEROS);

		ResultBean resultadoMetodo = new ResultBean();
		String idContrato = String.valueOf(getUtilesColegiado().getIdContratoSessionBigDecimal());

		GestorFicherosConfiguracion ficheroColegiado = null;

		/**
		 * Incidencia 1903 . Se va a aniadir que para los colegiados con mas de un
		 * contrato las etiquetas puedan guardarse en funcion del colegio. Para ello
		 * necesito saber el numero de contratos
		 * 
		 * @return
		 */
		if (getUtilesColegiado().getNumContratosColegiado() > 1) {
			// Para la ruta de un fichero en concreto del colegiado.
			ficheroColegiado = new GestorFicherosConfiguracion(
					rutaPreferencias + numColegiado + idContrato + EXTENSION);
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

		return resultadoMetodo;
	}

	private static UtilesColegiado getUtilesColegiado() {
		return ContextoSpring.getInstance().getBean(UtilesColegiado.class);
	}

}
