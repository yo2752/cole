package org.gestoresmadrid.oegam2comun.tasas.model.service;

import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.model.beans.ColaBean;
import org.gestoresmadrid.oegam2comun.tasas.model.dto.RespuestaTasas;
import org.gestoresmadrid.oegam2comun.tasas.view.beans.GeneracionEtiquetasBean;
import org.gestoresmadrid.oegam2comun.tasas.view.beans.Tasa;

import utilidades.web.OegamExcepcion;
import es.globaltms.gestorDocumentos.bean.FicheroBean;
import escrituras.beans.ResultBean;

public interface ServicioGeneracionEtiquetasTasas {
	
	public static String FORMA_PAGO_TASAS = "CC";
	public static String JEFATURA_TASAS = "WEB";
	public static String destinatarioCorreoBastidoresOculto = "destinatarioOculto.administrador.tasasPegatinas";
	
	// Constantes del fichero a guardar. Se pasaran por properties
	public final String NOMBRE_DOCUMENTO = "ETIQUETAS_";
	public final String NOMBRE_DOCUMENTO_TEMP = "TEMP_ETQ_";
	// Constantes para la generacion del informe en pdf. Se pasaran por properties
	public final String RUTA_CARPETA = "RUTA_JASPER_ETIQUETAS_TASAS";
	public final String NOMBRE_INFORME = "NOMBRE_JASPER_ETIQUETAS_TASAS";
	public final String FORMATO = "pdf";
	public final String TAG_CABECERA = "tasasCompradas";
	public static final String horaFInDia = "23:59";
	public static final int N_SEGUNDOS = 59;
	
	public final static String NUM_COLEGIADO_ADMINISTRADOR   = "2056";		

	RespuestaTasas generarInformePdf(List<GeneracionEtiquetasBean> listaEtiquetas, String numColegiado, ColaBean cola);

	FicheroBean guardarDocumento(byte[] byteFinal, String numColegiado, BigDecimal idContrato) throws OegamExcepcion;

	List<GeneracionEtiquetasBean> obtenerListaTempEtiquetasBean(String nombreFich);

	RespuestaTasas generarTasasPegatinasProceso(ColaBean colaBean);

	ResultBean generarListadoEtiquetas(String[] numTasas);

	GeneracionEtiquetasBean generarEtiquetasBean(Tasa t);

	ResultBean crearDocumentoSolicitudColaPegatinas(List<GeneracionEtiquetasBean> listaGeneEtiquetasBean, BigDecimal contrato, BigDecimal idUsuario);

	ResultBean comprobarDatosObligatoriosGeneracionTasasEtiquetas(String idCodigoTasaElectronica, String idCodigoTasaPegatina);

	void borrarDocTasasPegatinas(String nombreFich, String tipo, String subTipo);

}
