package org.gestoresmadrid.oegam2comun.impresion.excel.duplicados;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.direccion.model.vo.MunicipioSitesVO;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.MotivoDuplicado;
import org.gestoresmadrid.core.model.enumerados.TextoNotificacion;
import org.gestoresmadrid.oegam2comun.correo.model.service.ServicioCorreo;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioDireccion;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioMunicipio;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioMunicipioSites;
import org.gestoresmadrid.oegam2comun.presentacion.jpt.enumerados.JefaturasJPTEnum;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTrafico;
import org.gestoresmadrid.oegam2comun.trafico.view.beans.ResultadoImpresionExcel;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafDuplicadoDto;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;

import es.globaltms.gestorDocumentos.bean.FicheroBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import escrituras.beans.ResultBean;
import jxl.CellView;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import trafico.utiles.enumerados.TipoImpreso;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

@Component
public class ImpresionExcelDuplicados {

	private static ILoggerOegam log = LoggerOegam.getLogger(ImpresionExcelDuplicados.class);

	// Constantes hoja Excel
	public static final String SOLICITUDDUPLICADOGESTORES = "SolicitudDuplicadosGestores";
	public static final Integer SHETSOLICITUDDUPLICADOGESTORES = 0;

	// Constantes Cabeceras
	public static final String NGESTORIA = " Nº Gestoria ";
	public static final Integer COL_NGESTORIA = 0;
	public static final String NREFERENCIA = " Nº Referencia ";
	public static final Integer COL_NREFERENCIA = 1;
	public static final String TIPODUPLICADO = " Tipo Duplicado ";
	public static final Integer COL_TIPODUPLICADO = 2;
	public static final String NTASADUPLICADO = " Nº TASA  ";
	public static final Integer COL_NTASADUPLICADO = 3;
	public static final String DOCTIPO = " DOC_TIPO ";
	public static final Integer COL_DOCTIPO = 4;
	public static final String DNITITULAR = " DNI TITULAR ";
	public static final Integer COL_DNITITULAR = 5;
	public static final String DNICOTITULAR = " DNI COTITULAR     ";
	public static final Integer COL_DNICOTITULAR = 6;

	/*** NUEVOS CAMPOS DUPLICADOS SITEX */
	public static final String TIPOVIA = " Tipo vía ";
	public static final Integer COL_TIPOVIA = 7;
	public static final String NOMBREVIA = " Nombre Vía ";
	public static final Integer COL_NOMBREVIA = 8;
	public static final String NUMERO = " Nº ";
	public static final Integer COL_NUMERO = 9;
	public static final String BLOQUE = " Bloque ";
	public static final Integer COL_BLOQUE = 10;
	public static final String PORTAL = " Portal ";
	public static final Integer COL_PORTAL = 11;
	public static final String ESCALERA = " Escalera ";
	public static final Integer COL_ESCALERA = 12;
	public static final String PLANTA = " Planta ";
	public static final Integer COL_PLANTA = 13;
	public static final String PUERTA = " Puerta ";
	public static final Integer COL_PUERTA = 14;
	public static final String KM = " Km. ";
	public static final Integer COL_KM = 15;
	public static final String HM = " Hm. ";
	public static final Integer COL_HM = 16;
	public static final String LOCALIDAD = " Localidad ";
	public static final Integer COL_LOCALIDAD = 17;

	public static final String CODIGO_POSTAL = " CODIGO POSTAL     ";
	public static final Integer COL_CODIGOPOSTAL = 18;
	public static final String MUNICIPIO = " MUNICIPIO     ";
	public static final Integer COL_MUNICIPIO = 19;
	public static final String PROVINCIA = " PROVINCIA     ";
	public static final Integer COL_PROVINCIA = 20;
	public static final String MATRICULA = " MATRICULA  ";
	public static final Integer COL_MATRICULA = 21;
	public static final String FECHAMATRICULACION = " FECHA_MATRICULACION     ";
	public static final Integer COL_FECHAMATRICULACION = 22;
	public static final String FECHAITV = " FECHA ITV  ";
	public static final Integer COL_FECHAITV = 23;
	public static final String JUSTIFICANTEAIMPRIMIR = " IMPRESIÓN       ";
	public static final Integer COL_JUSTIFICANTEAIMPRIMIR = 24;
	public static final String IMPORTACION = " ¿Vehículo Importación?     ";
	public static final Integer COL_IMPORTACION = 25;
	public static final String TASAIMPORTACION = " Tasa Anotación Importación ";
	public static final Integer COL_TASAIMPORTACION = 26;
	public static final String OBSERVACIONES = " OBSERVACIONES     ";
	public static final Integer COL_OBSERVACIONES = 27;
	public static final String FECHAPRESENTACION = " FECHA_PRESENTACION     "; // MODIFICACION SITEX 20/11/2012
	public static final Integer COL_FECHA_PRESENTACION = 28;
	public static final String FECHARESOLUCION = " FECHA_RESOLUCION     ";
	public static final Integer COL_FECHARESOLUCION = 29;
	public static final String RESOLUCION = " RESOLUCION  ";
	public static final Integer COL_RESOLUCION = 30;
	public static final String DESCRIPCION = " DESCRIPCION  ";
	public static final Integer COL_DESCRIPCION = 31;

	private static final String VALORSI = "SI";
	private static final String VALORNO = "NO";

	private static final int NUM_DUPLICADOS_POR_EXCEL = 4000;

	@Autowired
	ServicioTramiteTrafico servicioTramiteTrafico;

	@Autowired
	ServicioDireccion servicioDireccion;

	@Autowired
	ServicioMunicipio servicioMunicipio;
	
	@Autowired
	ServicioMunicipioSites servicioMunicipioSites;

	@Autowired
	GestorDocumentos gestorDocumentos;

	@Autowired
	ServicioCorreo servicioCorreo;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	UtilesFecha utilesFecha;

	public void impresionExcelDuplicados(List<TramiteTrafDuplicadoDto> listaDuplicados, String jefatura, ResultadoImpresionExcel resultado) {
		ResultadoImpresionExcel resultImpresion = new ResultadoImpresionExcel(Boolean.FALSE);
		try {
			resultImpresion = generarExcel(listaDuplicados, jefatura);
			if (!resultImpresion.getError() && "SI".equals(gestorPropiedades.valorPropertie("envio.mail.duplicados.excel"))) {
				enviarCorreoExcel(resultImpresion, jefatura);
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un eror a la hora de generar el excel de duplicados para la jefatura: " + jefatura + ", error: ", e);
			resultImpresion.setError(Boolean.TRUE);
			resultImpresion.setMensajeError("Ha sucedido un eror a la hora de generar el excel de duplicados para la jefatura: " + jefatura);
		}
		rellenarResultadoFinal(resultado, resultImpresion, jefatura);
	}

	private void enviarCorreoExcel(ResultadoImpresionExcel resultImpresion, String jefatura) {
		try {
			if (resultImpresion.getFicheros() != null && !resultImpresion.getFicheros().isEmpty()) {
				String to = getToCorreo(jefatura);
				String bcc = gestorPropiedades.valorPropertie("direccion.excel.duplicados.mail.destinatario2");
				String from = gestorPropiedades.valorPropertie("direccion.excel.duplicados.mail.usuario");
				String subject = gestorPropiedades.valorPropertie("direccion.excel.duplicados.mail.subject");

				String texto = "<br><span style=\"font-size:15pt;font-family:Tunga;margin-left:20px;\"><br>Se solicita desde la Oficina Electrónica de Gestión Administrativa (OEGAM), el envío del siguiente fichero Excel con los duplicados<br></span>";

				FicheroBean[] adjuntos = new FicheroBean[resultImpresion.getFicheros().size()];
				for (int i = 0; i < resultImpresion.getFicheros().size(); i++) {
					FicheroBean ficheroBean = new FicheroBean();
					ficheroBean.setFichero(resultImpresion.getFicheros().get(i));
					ficheroBean.setNombreDocumento(resultImpresion.getFicheros().get(i).getName());
					adjuntos[i] = ficheroBean;
				}

				ResultBean resultEnvio = servicioCorreo.enviarCorreoDuplicados(texto, Boolean.FALSE, from, subject, to, null, bcc, "duplicados", adjuntos);

				if (resultEnvio.getError()) {
					resultImpresion.setError(Boolean.TRUE);
					resultImpresion.setMensajeError(resultEnvio.getMensaje());
				}
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de enviar el correo de duplicados a la jefatura: " + jefatura + ",error: ", e);
			resultImpresion.setError(Boolean.TRUE);
			resultImpresion.setMensajeError("Ha sucedido un error a la hora de enviar el correo de duplicados a la jefatura: " + jefatura);
		}

	}

	private void rellenarResultadoFinal(ResultadoImpresionExcel resultado, ResultadoImpresionExcel resultImpresion, String jefatura) {
		if (JefaturasJPTEnum.MADRID.getDescripcion().equals(jefatura)) {
			if (resultImpresion.getError()) {
				resultado.setErrorMadrid(Boolean.TRUE);
				resultado.setMensajeErrorMadrid(resultImpresion.getMensajeError());
			} else {
				resultado.setNumExcelMadrid(resultImpresion.getNumOK());
				resultado.setNumExcelErrorMadrid(resultImpresion.getNumError());
				resultado.setListaResumenMadrid(resultImpresion.getListaErrores());
			}
		} else if (JefaturasJPTEnum.ALCORCON.getDescripcion().equals(jefatura)) {
			if (resultImpresion.getError()) {
				resultado.setErrorAlcorcon(Boolean.TRUE);
				resultado.setMensajeErrorAlcorcon(resultImpresion.getMensajeError());
			} else {
				resultado.setNumExcelAlcorcon(resultImpresion.getNumOK());
				resultado.setNumExcelErrorAlcorcon(resultImpresion.getNumError());
				resultado.setListaResumenAlcorcon(resultImpresion.getListaErrores());
			}
		} else if ("ALCALA".equals(jefatura)) {
			if (resultImpresion.getError()) {
				resultado.setErrorAlcala(Boolean.TRUE);
				resultado.setMensajeErrorAlcala(resultImpresion.getMensajeError());
			} else {
				resultado.setNumExcelAlcala(resultImpresion.getNumOK());
				resultado.setNumExcelErrorAlcala(resultImpresion.getNumError());
				resultado.setListaResumenAlcala(resultImpresion.getListaErrores());
			}
		} else if (JefaturasJPTEnum.AVILA.getDescripcion().equals(jefatura)) {
			if (resultImpresion.getError()) {
				resultado.setErrorAvila(Boolean.TRUE);
				resultado.setMensajeErrorAvila(resultImpresion.getMensajeError());
			} else {
				resultado.setNumExcelAvila(resultImpresion.getNumOK());
				resultado.setNumExcelErrorAvila(resultImpresion.getNumError());
				resultado.setListaResumenAvila(resultImpresion.getListaErrores());
			}
		} else if ("CIUDADREAL".equals(jefatura)) {
			if (resultImpresion.getError()) {
				resultado.setErrorCiudadReal(Boolean.TRUE);
				resultado.setMensajeErrorCiudadReal(resultImpresion.getMensajeError());
			} else {
				resultado.setNumExcelCiudadReal(resultImpresion.getNumOK());
				resultado.setNumExcelErrorCiudadReal(resultImpresion.getNumError());
				resultado.setListaResumenCiudadReal(resultImpresion.getListaErrores());
			}
		} else if (JefaturasJPTEnum.CUENCA.getDescripcion().equals(jefatura)) {
			if (resultImpresion.getError()) {
				resultado.setErrorCuenca(Boolean.TRUE);
				resultado.setMensajeErrorCuenca(resultImpresion.getMensajeError());
			} else {
				resultado.setNumExcelCuenca(resultImpresion.getNumOK());
				resultado.setNumExcelErrorCuenca(resultImpresion.getNumError());
				resultado.setListaResumenCuenca(resultImpresion.getListaErrores());
			}
		} else if (JefaturasJPTEnum.GUADALAJARA.getDescripcion().equals(jefatura)) {
			if (resultImpresion.getError()) {
				resultado.setErrorGuadalajara(Boolean.TRUE);
				resultado.setMensajeErrorGuadalajara(resultImpresion.getMensajeError());
			} else {
				resultado.setNumExcelGuadalajara(resultImpresion.getNumOK());
				resultado.setNumExcelErrorGuadalajara(resultImpresion.getNumError());
				resultado.setListaResumenGuadalajara(resultImpresion.getListaErrores());
			}
		} else if (JefaturasJPTEnum.SEGOVIA.getDescripcion().equals(jefatura)) {
			if (resultImpresion.getError()) {
				resultado.setErrorSegovia(Boolean.TRUE);
				resultado.setMensajeErrorSegovia(resultImpresion.getMensajeError());
			} else {
				resultado.setNumExcelSegovia(resultImpresion.getNumOK());
				resultado.setNumExcelErrorSegovia(resultImpresion.getNumError());
				resultado.setListaResumenSegovia(resultImpresion.getListaErrores());
			}
		}

	}

	private ResultadoImpresionExcel generarExcel(List<TramiteTrafDuplicadoDto> listaDuplicados, String jefatura) throws OegamExcepcion {
		ResultadoImpresionExcel resultadoMetodo = new ResultadoImpresionExcel(Boolean.FALSE);
		int contadorFicheros = 1;
		final List<List<TramiteTrafDuplicadoDto>> auxListas = Lists.partition(listaDuplicados, NUM_DUPLICADOS_POR_EXCEL);
		for(List<TramiteTrafDuplicadoDto> listaTramites : auxListas) {
			ResultadoImpresionExcel resulGenExcel = generarExcelDuplicadoLista(listaTramites,jefatura,contadorFicheros);
			if (!resulGenExcel.getError()) {
				resultadoMetodo.addFicheros(resulGenExcel.getFichero());
				resultadoMetodo.setNumOK(resulGenExcel.getNumOK());
				contadorFicheros++;
			} else {
				if (StringUtils.isNotBlank(resulGenExcel.getMensajeError())) {
					resultadoMetodo.addMensajeError(resulGenExcel.getMensajeError());
				}
				if (resulGenExcel.getListaErrores() != null && !resulGenExcel.getListaErrores().isEmpty()) {
					resultadoMetodo.addListaMensajeError(resulGenExcel.getListaErrores());
				}
			}
		}
		return resultadoMetodo;
	}

	private ResultadoImpresionExcel generarExcelDuplicadoLista(List<TramiteTrafDuplicadoDto> listaTramites, String jefatura, int contadorFicheros) {
		ResultadoImpresionExcel resultadoMetodo = new ResultadoImpresionExcel(Boolean.FALSE);
		FicheroBean fichero = new FicheroBean();
		String nombreFichero = null;
		File archivo = null;
		if (contadorFicheros == 1) {
			nombreFichero = TipoImpreso.DuplicadosExcel.getNombreEnum() + "_ICOGAM_" + jefatura + "_" + utilesFecha.getCadenaFecha('_');
		} else {
			nombreFichero = TipoImpreso.DuplicadosExcel.getNombreEnum() + "_ICOGAM_" + jefatura + "_" + utilesFecha.getCadenaFecha('_') + "_" + contadorFicheros;
		}
		try {
			fichero.setExtension(".xls");
			fichero.setNombreDocumento(nombreFichero);
			fichero.setTipoDocumento(ConstantesGestorFicheros.EXCELS);
			fichero.setSubTipo(ConstantesGestorFicheros.DUPLICADOSENVIO);
			fichero.setFecha(utilesFecha.getFechaActual());
			fichero.setSobreescribir(true);
			fichero.setFichero(new File(nombreFichero));
			archivo = gestorDocumentos.guardarFichero(fichero);
		} catch (OegamExcepcion e1) {
			log.error("Ha sucedido un error a la hora de guardar el fichero " + nombreFichero + ",error: ", e1);
			resultadoMetodo.setError(Boolean.TRUE);
			resultadoMetodo.setMensajeError("Ha sucedido un error a la hora de guardar el fichero " + nombreFichero);
		}

		WritableWorkbook copyWorkbook = null;
		WritableSheet sheet;
		try {
			copyWorkbook = Workbook.createWorkbook(archivo);
			sheet = copyWorkbook.createSheet(SOLICITUDDUPLICADOGESTORES, 0);
			CellView vistaCeldas = new CellView();
			vistaCeldas.setAutosize(true);
			for (int i = 0; i < 32; i++) {
				sheet.setColumnView(i, vistaCeldas);
			}
			WritableFont fuente = new WritableFont(WritableFont.createFont("MS Sans Serif"), 10, WritableFont.BOLD, false);
			fuente.setColour(Colour.BLACK);
			WritableCellFormat formato = new WritableCellFormat(fuente);

			formato.setBackground(Colour.GRAY_25);
			formato.setBorder(Border.LEFT, BorderLineStyle.THIN);
			formato.setBorder(Border.RIGHT, BorderLineStyle.THIN);

			vistaCeldas.setFormat(formato);
			for (int i = 29; i < 32; i++) {
				sheet.setColumnView(i, vistaCeldas);
			}
			// Formato de las celdas de cabecera
			WritableFont fuenteCabecera = new WritableFont(WritableFont.createFont("MS Sans Serif"), 10, WritableFont.BOLD, false);
			fuenteCabecera.setColour(Colour.BLACK);
			WritableCellFormat formatoCabecera = new WritableCellFormat(fuenteCabecera);

			formatoCabecera.setBackground(Colour.PALE_BLUE);
			formatoCabecera.setAlignment(Alignment.LEFT);
			formatoCabecera.setBorder(Border.ALL, BorderLineStyle.THIN);

			// Formato de las celdas de datos
			WritableFont fuenteDatos = new WritableFont(WritableFont.createFont("MS Sans Serif"), 10, WritableFont.NO_BOLD, false);
			fuenteDatos.setColour(Colour.BLACK);
			WritableCellFormat formatoDatos = new WritableCellFormat(fuenteDatos);

			formatoDatos.setAlignment(Alignment.LEFT);
			Label label = null;
			try {
				label = new Label(COL_NGESTORIA, 0, NGESTORIA, formatoCabecera);
				sheet.addCell(label);
				label = new Label(COL_NREFERENCIA, 0, NREFERENCIA, formatoCabecera);
				sheet.addCell(label);
				label = new Label(COL_TIPODUPLICADO, 0, TIPODUPLICADO, formatoCabecera);
				sheet.addCell(label);
				label = new Label(COL_NTASADUPLICADO, 0, NTASADUPLICADO, formatoCabecera);
				sheet.addCell(label);
				label = new Label(COL_DOCTIPO, 0, DOCTIPO, formatoCabecera);
				sheet.addCell(label);
				label = new Label(COL_DNITITULAR, 0, DNITITULAR, formatoCabecera);
				sheet.addCell(label);
				label = new Label(COL_DNICOTITULAR, 0, DNICOTITULAR, formatoCabecera);
				sheet.addCell(label);

				label = new Label(COL_TIPOVIA, 0, TIPOVIA, formatoCabecera);
				sheet.addCell(label);
				label = new Label(COL_NOMBREVIA, 0, NOMBREVIA, formatoCabecera);
				sheet.addCell(label);
				label = new Label(COL_NUMERO, 0, NUMERO, formatoCabecera);
				sheet.addCell(label);
				label = new Label(COL_BLOQUE, 0, BLOQUE, formatoCabecera);
				sheet.addCell(label);
				label = new Label(COL_PORTAL, 0, PORTAL, formatoCabecera);
				sheet.addCell(label);
				label = new Label(COL_ESCALERA, 0, ESCALERA, formatoCabecera);
				sheet.addCell(label);
				label = new Label(COL_PLANTA, 0, PLANTA, formatoCabecera);
				sheet.addCell(label);
				label = new Label(COL_ESCALERA, 0, ESCALERA, formatoCabecera);
				sheet.addCell(label);
				label = new Label(COL_PUERTA, 0, PUERTA, formatoCabecera);
				sheet.addCell(label);
				label = new Label(COL_KM, 0, KM, formatoCabecera);
				sheet.addCell(label);
				label = new Label(COL_HM, 0, HM, formatoCabecera);
				sheet.addCell(label);
				label = new Label(COL_LOCALIDAD, 0, LOCALIDAD, formatoCabecera);
				sheet.addCell(label);

				label = new Label(COL_CODIGOPOSTAL, 0, CODIGO_POSTAL, formatoCabecera);
				sheet.addCell(label);
				label = new Label(COL_MUNICIPIO, 0, MUNICIPIO, formatoCabecera);
				sheet.addCell(label);
				label = new Label(COL_PROVINCIA, 0, PROVINCIA, formatoCabecera);
				sheet.addCell(label);
				label = new Label(COL_MATRICULA, 0, MATRICULA, formatoCabecera);
				sheet.addCell(label);
				label = new Label(COL_FECHAMATRICULACION, 0, FECHAMATRICULACION, formatoCabecera);
				sheet.addCell(label);
				label = new Label(COL_FECHAITV, 0, FECHAITV, formatoCabecera);
				sheet.addCell(label);
				label = new Label(COL_JUSTIFICANTEAIMPRIMIR, 0, JUSTIFICANTEAIMPRIMIR, formatoCabecera);
				sheet.addCell(label);
				label = new Label(COL_IMPORTACION, 0, IMPORTACION, formatoCabecera);
				sheet.addCell(label);
				label = new Label(COL_TASAIMPORTACION, 0, TASAIMPORTACION, formatoCabecera);
				sheet.addCell(label);
				label = new Label(COL_OBSERVACIONES, 0, OBSERVACIONES, formatoCabecera);
				sheet.addCell(label);
				label = new Label(COL_FECHA_PRESENTACION, 0, FECHAPRESENTACION, formatoCabecera);
				sheet.addCell(label);
				label = new Label(COL_FECHARESOLUCION, 0, FECHARESOLUCION, formatoCabecera);
				sheet.addCell(label);
				label = new Label(COL_RESOLUCION, 0, RESOLUCION, formatoCabecera);
				sheet.addCell(label);
				label = new Label(COL_DESCRIPCION, 0, DESCRIPCION, formatoCabecera);
				sheet.addCell(label);

				Integer i = 1;
				for (TramiteTrafDuplicadoDto tramiteTrafDuplicadoDto : listaTramites) {
					try {
						ResultBean resultadoCambio = servicioTramiteTrafico.cambiarEstadoConEvolucion(tramiteTrafDuplicadoDto.getNumExpediente(), EstadoTramiteTrafico.Pendiente_Envio_Excel,
								EstadoTramiteTrafico.Pendiente_Respuesta_Jefatura, true, TextoNotificacion.Cambio_Estado.getNombreEnum(), tramiteTrafDuplicadoDto.getUsuarioDto().getIdUsuario());
						if (resultadoCambio.getError()) {
							resultadoMetodo.addMensajeError("Error al cambiar el estado del trámite " + tramiteTrafDuplicadoDto.getNumExpediente());
							resultadoMetodo.addNumError();
						} else {
							label = new Label(COL_NGESTORIA, i, tramiteTrafDuplicadoDto.getNumColegiado(), formatoDatos);
							sheet.addCell(label);

							label = new Label(COL_NREFERENCIA, i, tramiteTrafDuplicadoDto.getNumExpediente().toString(), formatoDatos);
							sheet.addCell(label);

							label = new Label(COL_TIPODUPLICADO, i, null != tramiteTrafDuplicadoDto.getMotivoDuplicado() ? MotivoDuplicado.convertirTexto(tramiteTrafDuplicadoDto.getMotivoDuplicado()).replace(".", "")
									.toUpperCase() : "", formatoDatos);
							sheet.addCell(label);

							if (tramiteTrafDuplicadoDto.getTasa() != null) {
								label = new Label(COL_NTASADUPLICADO, i, tramiteTrafDuplicadoDto.getTasa().getCodigoTasa(), formatoDatos);
							} else {
								label = new Label(COL_NTASADUPLICADO, i, "", formatoDatos);
							}
							sheet.addCell(label);

							if (tramiteTrafDuplicadoDto.getTitular() != null) {
								if (tramiteTrafDuplicadoDto.getTitular() != null && tramiteTrafDuplicadoDto.getTitular().getPersona().getTipoPersona().equals("JURIDICA")) {
									label = new Label(COL_DOCTIPO, i, "2", formatoDatos);
								} else {
									if (tramiteTrafDuplicadoDto.getTitular().getNifInterviniente().substring(0, 1).equals("X") || tramiteTrafDuplicadoDto.getTitular().getNifInterviniente().substring(0, 1).equals(
											"Y")) {
										label = new Label(COL_DOCTIPO, i, "3", formatoDatos);
									} else {
										label = new Label(COL_DOCTIPO, i, "1", formatoDatos);
									}
								}
								sheet.addCell(label);
								label = new Label(COL_DNITITULAR, i, tramiteTrafDuplicadoDto.getTitular().getNifInterviniente(), formatoDatos);
								sheet.addCell(label);
							} else {
								label = new Label(COL_DOCTIPO, i, "", formatoDatos);
								sheet.addCell(label);
								label = new Label(COL_DNITITULAR, i, "", formatoDatos);
								sheet.addCell(label);
							}

							if (tramiteTrafDuplicadoDto.getCotitular() != null) {
								label = new Label(COL_DNICOTITULAR, i, tramiteTrafDuplicadoDto.getCotitular().getNifInterviniente(), formatoDatos);
							} else {
								label = new Label(COL_DNICOTITULAR, i, "", formatoDatos);
							}
							sheet.addCell(label);

							if (tramiteTrafDuplicadoDto.getTitular() != null && tramiteTrafDuplicadoDto.getTitular().getDireccion() != null) {
								label = new Label(COL_TIPOVIA, i, tramiteTrafDuplicadoDto.getTitular().getDireccion().getIdTipoVia(), formatoDatos);
								sheet.addCell(label);
								label = new Label(COL_NOMBREVIA, i, tramiteTrafDuplicadoDto.getTitular().getDireccion().getNombreVia(), formatoDatos);
								sheet.addCell(label);
								label = new Label(COL_NUMERO, i, null != tramiteTrafDuplicadoDto.getTitular().getDireccion().getNumero() ? tramiteTrafDuplicadoDto.getTitular().getDireccion().getNumero() : "SN",
										formatoDatos); // (sitex:si no existe el poner ND)
								sheet.addCell(label);
								label = new Label(COL_BLOQUE, i, tramiteTrafDuplicadoDto.getTitular().getDireccion().getBloque(), formatoDatos);
								sheet.addCell(label);
								label = new Label(COL_PORTAL, i, tramiteTrafDuplicadoDto.getTitular().getDireccion().getLetra(), formatoDatos);
								sheet.addCell(label);
								label = new Label(COL_ESCALERA, i, tramiteTrafDuplicadoDto.getTitular().getDireccion().getEscalera(), formatoDatos);
								sheet.addCell(label);
								label = new Label(COL_PLANTA, i, tramiteTrafDuplicadoDto.getTitular().getDireccion().getPlanta(), formatoDatos);
								sheet.addCell(label);
								label = new Label(COL_PUERTA, i, tramiteTrafDuplicadoDto.getTitular().getDireccion().getPuerta(), formatoDatos);
								sheet.addCell(label);

								if (tramiteTrafDuplicadoDto.getTitular().getDireccion().getKm() != null) {
									label = new Label(COL_KM, i, tramiteTrafDuplicadoDto.getTitular().getDireccion().getKm().toString(), formatoDatos);
								} else {
									label = new Label(COL_KM, i, "", formatoDatos);
								}
								sheet.addCell(label);

								if (tramiteTrafDuplicadoDto.getTitular().getDireccion().getHm() != null) {
									label = new Label(COL_HM, i, tramiteTrafDuplicadoDto.getTitular().getDireccion().getHm().toString(), formatoDatos);
								} else {
									label = new Label(COL_HM, i, "", formatoDatos);
								}
								sheet.addCell(label);

								label = new Label(COL_LOCALIDAD, i, tramiteTrafDuplicadoDto.getTitular().getDireccion().getPueblo(), formatoDatos); // OJO: ANTONIO TRENADO NO DEJAR EN BLANCO SINO CON EL VALOR DEL PUEBLO!
								sheet.addCell(label);
								label = new Label(COL_CODIGOPOSTAL, i, tramiteTrafDuplicadoDto.getTitular().getDireccion().getCodPostal(), formatoDatos);
								sheet.addCell(label);

//								MunicipioDto municipio = servicioMunicipio.getMunicipioDto(tramiteLista.getTitular().getDireccion().getIdProvincia(), tramiteLista.getTitular().getDireccion()
//											.getIdMunicipio());
								MunicipioSitesVO municipio = servicioMunicipioSites.getMunicipio(tramiteTrafDuplicadoDto.getTitular().getDireccion().getIdMunicipio(), tramiteTrafDuplicadoDto.getTitular().getDireccion().getIdProvincia());
								label = new Label(COL_MUNICIPIO, i, municipio.getNombre(), formatoDatos);
								sheet.addCell(label);

								String nombreProvincia = servicioDireccion.obtenerNombreProvincia(tramiteTrafDuplicadoDto.getTitular().getDireccion().getIdProvincia());
								label = new Label(COL_PROVINCIA, i, nombreProvincia, formatoDatos);
								sheet.addCell(label);
							} else {
								label = new Label(COL_TIPOVIA, i, "", formatoDatos);
								sheet.addCell(label);
								label = new Label(COL_NOMBREVIA, i, "", formatoDatos);
								sheet.addCell(label);
								label = new Label(COL_NUMERO, i, "", formatoDatos);
								sheet.addCell(label);
								label = new Label(COL_BLOQUE, i, "", formatoDatos);
								sheet.addCell(label);
								label = new Label(COL_PORTAL, i, "", formatoDatos);
								sheet.addCell(label);
								label = new Label(COL_ESCALERA, i, "", formatoDatos);
								sheet.addCell(label);
								label = new Label(COL_PLANTA, i, "", formatoDatos);
								sheet.addCell(label);
								label = new Label(COL_PUERTA, i, "", formatoDatos);
								sheet.addCell(label);
								label = new Label(COL_KM, i, "", formatoDatos);
								sheet.addCell(label);
								label = new Label(COL_HM, i, "", formatoDatos);
								sheet.addCell(label);
								label = new Label(COL_LOCALIDAD, i, "", formatoDatos);
								sheet.addCell(label);
								label = new Label(COL_CODIGOPOSTAL, i, "", formatoDatos);
								sheet.addCell(label);
								label = new Label(COL_MUNICIPIO, i, "", formatoDatos);
								sheet.addCell(label);
								label = new Label(COL_PROVINCIA, i, "", formatoDatos);
								sheet.addCell(label);
							}

							if (tramiteTrafDuplicadoDto.getVehiculoDto() != null) {
								label = new Label(COL_MATRICULA, i, tramiteTrafDuplicadoDto.getVehiculoDto().getMatricula(), formatoDatos);
								sheet.addCell(label);
								label = new Label(COL_FECHAMATRICULACION, i, null != tramiteTrafDuplicadoDto.getVehiculoDto().getFechaMatriculacion() ? utilesFecha.formatoFecha(tramiteTrafDuplicadoDto.getVehiculoDto()
										.getFechaMatriculacion()) : "", formatoDatos);
								sheet.addCell(label);
								label = new Label(COL_FECHAITV, i, null != tramiteTrafDuplicadoDto.getVehiculoDto().getFechaItv() ? utilesFecha.formatoFecha(tramiteTrafDuplicadoDto.getVehiculoDto().getFechaItv()) : "",
										formatoDatos);
								sheet.addCell(label);
							} else {
								label = new Label(COL_MATRICULA, i, "", formatoDatos);
								sheet.addCell(label);
								label = new Label(COL_FECHAMATRICULACION, i, "", formatoDatos);
								sheet.addCell(label);
								label = new Label(COL_FECHAITV, i, "", formatoDatos);
								sheet.addCell(label);
							}

							if (tramiteTrafDuplicadoDto.getImprPermisoCirculacion() != null && tramiteTrafDuplicadoDto.getImprPermisoCirculacion()) {
								label = new Label(COL_JUSTIFICANTEAIMPRIMIR, i, VALORSI, formatoDatos);
							} else {
								label = new Label(COL_JUSTIFICANTEAIMPRIMIR, i, VALORNO, formatoDatos);
							}
							sheet.addCell(label);

							if (tramiteTrafDuplicadoDto.getImportacion() != null && tramiteTrafDuplicadoDto.getImportacion()) {
								label = new Label(COL_IMPORTACION, i, VALORSI, formatoDatos);
							} else {
								label = new Label(COL_IMPORTACION, i, VALORNO, formatoDatos);
							}
							sheet.addCell(label);

							label = new Label(COL_TASAIMPORTACION, i, (null != tramiteTrafDuplicadoDto.getTasaImportacion() ? tramiteTrafDuplicadoDto.getTasaImportacion() : ""), formatoDatos);
							sheet.addCell(label);

							label = new Label(COL_OBSERVACIONES, i, (null != tramiteTrafDuplicadoDto.getAnotaciones() ? tramiteTrafDuplicadoDto.getAnotaciones() : ""), formatoDatos);
							sheet.addCell(label);

							label = new Label(COL_FECHA_PRESENTACION, i, utilesFecha.formatoFecha(tramiteTrafDuplicadoDto.getFechaPresentacion()), formatoDatos);
							sheet.addCell(label);
							i++;
							resultadoMetodo.addNumOK();
						}
					} catch (Throwable e) {
						log.error("Ha sucedido un error a la hora de adjuntar el duplicado " + tramiteTrafDuplicadoDto.getNumExpediente() + " al excel, error: ", e);
						resultadoMetodo.addMensajeError("Ha sucedido un error a la hora de adjuntar el duplicado " + tramiteTrafDuplicadoDto.getNumExpediente() + " al excel.");
						resultadoMetodo.addNumError();
						servicioTramiteTrafico.cambiarEstadoConEvolucion(tramiteTrafDuplicadoDto.getNumExpediente(), EstadoTramiteTrafico.Pendiente_Respuesta_Jefatura,
								EstadoTramiteTrafico.Finalizado_Con_Error, true, TextoNotificacion.Cambio_Estado.getNombreEnum(), tramiteTrafDuplicadoDto.getUsuarioDto().getIdUsuario());
					}
				}
			} catch (RowsExceededException e) {
				log.error("Error al imprimir tramites de duplicado para la jefatura " + jefatura + ",error: ", e);
				resultadoMetodo.setError(Boolean.TRUE);
				resultadoMetodo.setMensajeError("Error al imprimir tramites de duplicado para la jefatura: " + jefatura);
			} catch (WriteException e) {
				log.error("Error al imprimir tramites de duplicado para la jefatura " + jefatura + ",error: ", e);
				resultadoMetodo.setError(Boolean.TRUE);
				resultadoMetodo.setMensajeError("Error al imprimir tramites de duplicado para la jefatura: " + jefatura);
			}
			copyWorkbook.write();
		} catch (IOException e) {
			log.error("Error al imprimir tramites de duplicado para la jefatura " + jefatura + ",error: ", e);
			resultadoMetodo.setError(Boolean.TRUE);
			resultadoMetodo.setMensajeError("Error al imprimir tramites de duplicado para la jefatura: " + jefatura);
		} catch (Exception e) {
			log.error("Error al imprimir tramites de duplicado para la jefatura " + jefatura + ",error: ", e);
			resultadoMetodo.setError(Boolean.TRUE);
			resultadoMetodo.setMensajeError("Error al imprimir tramites de duplicado para la jefatura: " + jefatura);
		} finally {
			if (copyWorkbook != null) {
				try {
					copyWorkbook.close();
				} catch (WriteException e) {
					log.error("Error al imprimir tramites de duplicado para la jefatura " + jefatura + ",error: ", e);
					resultadoMetodo.setError(Boolean.TRUE);
					resultadoMetodo.setMensajeError("Error al imprimir tramites de duplicado para la jefatura: " + jefatura);
				} catch (IOException e) {
					log.error("Error al imprimir tramites de duplicado para la jefatura " + jefatura + ",error: ", e);
					resultadoMetodo.setError(Boolean.TRUE);
					resultadoMetodo.setMensajeError("Error al imprimir tramites de duplicado para la jefatura: " + jefatura);
				}
			}
		}

		if (!resultadoMetodo.getError()) {
			resultadoMetodo.setFichero(archivo);
		}
		return resultadoMetodo;
	}

	private String getToCorreo(String jefatura) {
		String to = null;
		if (JefaturasJPTEnum.MADRID.getDescripcion().equals(jefatura)) {
			to = gestorPropiedades.valorPropertie("direccion.excel.duplicados.mail.destinatario");
		} else if (JefaturasJPTEnum.ALCORCON.getDescripcion().equals(jefatura)) {
			to = gestorPropiedades.valorPropertie("direccion.excel.duplicados.mail.destinatario.alcorcon");
		} else if ("ALCALA".equals(jefatura)) {
			to = gestorPropiedades.valorPropertie("direccion.excel.duplicados.mail.destinatario.alcala");
		} else if (JefaturasJPTEnum.AVILA.getDescripcion().equals(jefatura) && "SI".equalsIgnoreCase(gestorPropiedades.valorPropertie("envio.mail.jefaturaAvila"))) {
			to = gestorPropiedades.valorPropertie("direccion.excel.duplicados.mail.destinatario.avila");
		} else if (JefaturasJPTEnum.SEGOVIA.getDescripcion().equals(jefatura) && "SI".equalsIgnoreCase(gestorPropiedades.valorPropertie("envio.mail.jefaturaSegovia"))) {
			to = gestorPropiedades.valorPropertie("direccion.excel.duplicados.mail.destinatario.segovia");
		} else if (JefaturasJPTEnum.CUENCA.getDescripcion().equals(jefatura) && "SI".equalsIgnoreCase(gestorPropiedades.valorPropertie("envio.mail.jefaturaCuenca"))) {
			to = gestorPropiedades.valorPropertie("direccion.excel.duplicados.mail.destinatario.cuenca");
		} else if ("CIUDADREAL".equals(jefatura) && "SI".equalsIgnoreCase(gestorPropiedades.valorPropertie("envio.mail.jefaturaCiudadReal"))) {
			to = gestorPropiedades.valorPropertie("direccion.excel.duplicados.mail.destinatario.ciudadReal");
		} else if (JefaturasJPTEnum.GUADALAJARA.getDescripcion().equals(jefatura) && "SI".equalsIgnoreCase(gestorPropiedades.valorPropertie("envio.mail.jefaturaGuadalajara"))) {
			to = gestorPropiedades.valorPropertie("direccion.excel.duplicados.mail.destinatario.guadalajara");
		}
		return to;
	}

}