package org.gestoresmadrid.oegam2comun.impresion.model.service;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;

import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.oegam2comun.impresion.view.dto.ImprimirTramiteTraficoDto;

import trafico.beans.utiles.ParametrosPegatinaMatriculacion;
import trafico.servicio.implementacion.CriteriosImprimirTramiteTraficoBean;
import utilidades.web.OegamExcepcion;
import escrituras.beans.ResultBean;
import escrituras.beans.ResultValidarTramitesImprimir;

public interface ServicioImpresion extends Serializable {

	ParametrosPegatinaMatriculacion getEtiquetasParametros(TipoTramiteTrafico tipoTramiteTrafico, String numColegiado);

	ResultBean generarSituacionCreditos(ImprimirTramiteTraficoDto beanCriterios, BigDecimal idContrato);

	Boolean esTransmisionElectronica(TipoTramiteTrafico tipoTramite);

	ResultBean comprobarDatosObligatorios(String listaChecksConsultaTramite, String impreso);

	ResultValidarTramitesImprimir validarTramitesPrevioAPermitirImprimir(String[] codSeleccionados, BigDecimal idContrato, Boolean tienePermisoAdmin);

	ResultBean comprobarImpresion(String impreso, ImprimirTramiteTraficoDto imprimirTramiteTraficoDto, String[] numsExpedientes, BigDecimal idContrato, BigDecimal idUsuario);

	CriteriosImprimirTramiteTraficoBean generarCriteriosImprimirTramite(String impreso, ImprimirTramiteTraficoDto imprimirTramiteTraficoDto, ParametrosPegatinaMatriculacion etiquetaParametros,
			String tipoRepresentacion);

	boolean impresionMasiva(int length) throws OegamExcepcion;

	byte[] encriptarPDF(byte[] byte1);

	boolean guardarFichero(byte[] archivo, String fileName, String tipoImpreso);

	ResultBean impresionPDF417(String[] numsExpedientes, ImprimirTramiteTraficoDto imprimirTramiteTraficoDto, BigDecimal idContrato, BigDecimal idUsuario, String impreso) throws OegamExcepcion;

	Boolean borrarDocumentoGuardado(String fileName, String idSession);

	void crearAccionSiNecesaria(boolean terminadoCorrectamente, String[] numsExpedientes, String impreso, BigDecimal idUsuario);

	File recuperarFichero(String fileName);

	byte[] concatenarPDF(ArrayList<byte[]> listaByte);

	ResultBean recuperarDocumentosOficiales(String[] numExpedientes, String tipo, String tipoImpresion);

	ResultBean generarResultBeanConArrayByteFinal(ArrayList<byte[]> listaByte, String nombreFichero);

	ResultBean tratarResultSiNulo(ResultBean result);

	ResultBean generarResultBeanConByteFinal(byte[] byte1, String string);

	ResultBean generarResultBeanByteProceso(ArrayList<byte[]> listaByte);

	File guardarFicheroProceso(byte[] bytesFichero, String nombreFichero, String tipoDocumento, String subTipo, String extension) throws Exception, IOException, OegamExcepcion;

	ResultValidarTramitesImprimir validarImpresion(String[] numExpedientes, boolean admin);

	ResultBean imprimirPdfSolicitudNRE06(String[] numsExpedientes, String tipo, String tipoImpreso);
}
