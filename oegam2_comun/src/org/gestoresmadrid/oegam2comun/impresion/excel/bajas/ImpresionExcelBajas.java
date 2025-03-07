package org.gestoresmadrid.oegam2comun.impresion.excel.bajas;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.MotivoBaja;
import org.gestoresmadrid.core.model.enumerados.TextoNotificacion;
import org.gestoresmadrid.core.model.enumerados.TipoImpreso;
import org.gestoresmadrid.oegam2comun.correo.model.service.ServicioCorreo;
import org.gestoresmadrid.oegam2comun.presentacion.jpt.enumerados.JefaturasJPTEnum;
import org.gestoresmadrid.oegam2comun.proceso.model.service.ServicioProcesos;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTrafico;
import org.gestoresmadrid.oegam2comun.trafico.view.beans.ResultadoImpresionExcel;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafBajaDto;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

@Component
public class ImpresionExcelBajas {

	private static ILoggerOegam log = LoggerOegam.getLogger(ImpresionExcelBajas.class);

	private static final String SOLICITUDBAJAGESTORES = "SolicitudBajasGestores";

	private static final String NGESTORIA = " Nº Gestoria ";
	private static final Integer COL_NGESTORIA = 0;
	private static final String NREFERENCIA = " Nº Referencia ";
	private static final Integer COL_NREFERENCIA = 1;
	private static final String TIPOBAJA = " Tipo Baja ";
	private static final Integer COL_TIPOBAJA = 2;
	private static final String NTASABAJA = " Nº TASA Baja ";
	private static final Integer COL_NTASABAJA = 3;
	private static final String DOCTIPO = " DOC_TIPO ";
	private static final Integer COL_DOCTIPO = 4;
	private static final String DNITITULAR = " DNI TITULAR ";
	private static final Integer COL_DNITITULAR = 5;
	private static final String DNICOTITULARES = " DNI_COTITULARES ";
	private static final Integer COL_DNICOTITULARES = 6;
	private static final String DNIACREDITAPROPIEDAD = " DNI ACREDITA PROPIEDAD     ";
	private static final Integer COL_DNIACREDITAPROPIEDAD = 7;
	private static final String MATRICULA = " MATRICULA  ";
	private static final Integer COL_MATRICULA = 8;
	private static final String FECHAMATRICULACION = " FECHA_MATRICULACION     ";
	private static final Integer COL_FECHAMATRICULACION = 9;
	private static final String FECHAITV = " FECHA ITV  ";
	private static final Integer COL_FECHAITV = 10;
	private static final String JUSTIFICANTEAIMPRIMIR = " JUSTIFICANTE A IMPRIMIR       ";
	private static final Integer COL_JUSTIFICANTEAIMPRIMIR = 11;
	private static final String TASAIMPRESIONPERMISO = " Tasa Impresión Permiso ";
	private static final Integer COL_TASAIMPRESIONPERMISO = 12;
	private static final String DECLARACIONRESIDUOSOLIDO = " Declaración NO es Residuo Sólido "; // SOLO PARA BAJAS DE EXPORTACION
	private static final Integer COL_DECLARACIONRESIDUOSOLIDO = 13;
	private static final String DECLARACIONENTREGACATV = "Declaración NO Entrega CATV";
	private static final Integer COL_DECLARACION_ENTREGA_CATV = 14;
	private static final String CARTADGT10ANIOS = "Carta DGT Vehículo más de 10 años";
	private static final Integer COL_CARTADGT10ANIOS = 15;
	private static final String CERTIFICADOMEDIOAMBIENTAL = "Certificado Medioambiental";
	private static final Integer COL_CERTIFICADOMEDIOAMBIENTAL = 16;
	private static final String OBSERVACIONES = " OBSERVACIONES     ";
	private static final Integer COL_OBSERVACIONES = 17;
	private static final String FECHAPRESENTACION = " FECHA_PRESENTACION     ";
	private static final Integer COL_FECHA_PRESENTACION = 18;
	private static final String FECHARESOLUCION = " FECHA_RESOLUCION     ";
	private static final Integer COL_FECHARESOLUCION = 19;
	private static final String RESOLUCION = " RESOLUCION  ";
	private static final Integer COL_RESOLUCION = 20;
	private static final String DESCRIPCION = " DESCRIPCION  ";
	private static final Integer COL_DESCRIPCION = 21;

	private static final String VALORSI = "SI";
	private static final String VALORNO = "NO";

	private static final int NUM_BAJAS_POR_EXCEL = 4000;

	@Autowired
	ServicioTramiteTrafico servicioTramiteTrafico;

	@Autowired
	ServicioProcesos servicioProcesos;

	@Autowired
	GestorDocumentos gestorDocumentos;

	@Autowired
	ServicioCorreo servicioCorreo;

	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	@Autowired
	UtilesFecha utilesFecha;

	public void impresionExcelBajas(List<TramiteTrafBajaDto> listaBajas, String jefatura, ResultadoImpresionExcel resultado) {
		ResultadoImpresionExcel resultImpresion = new ResultadoImpresionExcel(Boolean.FALSE);
		try {
			resultImpresion = generarExcel(listaBajas, jefatura);
			if (!resultImpresion.getError() && "SI".equals(gestorPropiedades.valorPropertie("envio.mail.bajas.excel"))) {
				enviarCorreoExcel(resultImpresion, jefatura);
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un eror a la hora de generar el excel de bajas para la jefatura: " + jefatura + ", error: ", e);
			resultImpresion.setError(Boolean.TRUE);
			resultImpresion.setMensajeError("Ha sucedido un eror a la hora de generar el excel de bajas para la jefatura: " + jefatura);
		}
		rellenarResultadoFinal(resultado, resultImpresion, jefatura);
	}

	private void enviarCorreoExcel(ResultadoImpresionExcel resultImpresion, String jefatura) {
		try {
			if (resultImpresion.getFicheros() != null && !resultImpresion.getFicheros().isEmpty()) {
				String to = getToCorreo(jefatura);
				String bcc = gestorPropiedades.valorPropertie("direccion.excel.bajas.mail.destinatario2");
				String from = gestorPropiedades.valorPropertie("direccion.excel.bajas.mail.usuario");
				String subject = gestorPropiedades.valorPropertie("direccion.excel.bajas.mail.subject");

				String texto = "<br><span style=\"font-size:15pt;font-family:Tunga;margin-left:20px;\"><br>Se solicita desde la Oficina Electrónica de Gestión Administrativa (OEGAM), el envío del siguiente fichero Excel con las bajas definitivas del tipo:<br>Definitiva Tránsito Comunitario,Definitiva Exportación y Temporal Voluntaria.<br><br></span>";

				FicheroBean[] adjuntos = new FicheroBean[resultImpresion.getFicheros().size()];
				for (int i = 0; i < resultImpresion.getFicheros().size(); i++) {
					FicheroBean ficheroBean = new FicheroBean();
					ficheroBean.setFichero(resultImpresion.getFicheros().get(i));
					ficheroBean.setNombreDocumento(resultImpresion.getFicheros().get(i).getName());
					adjuntos[i] = ficheroBean;
				}

				ResultBean resultEnvio;

				resultEnvio = servicioCorreo.enviarCorreoBajas(texto, Boolean.FALSE, from, subject, to, null, bcc, "bajas", adjuntos);

				if (resultEnvio.getError()) {
					resultImpresion.setError(Boolean.TRUE);
					resultImpresion.setMensajeError(resultEnvio.getMensaje());
				}
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de enviar el correo de bajas a la jefatura: " + jefatura + ",error: ", e);
			resultImpresion.setError(Boolean.TRUE);
			resultImpresion.setMensajeError("Ha sucedido un error a la hora de enviar el correo de bajas a la jefatura: " + jefatura);
		}
	}

	private String getToCorreo(String jefatura) {
		String to = null;
		if (JefaturasJPTEnum.MADRID.getDescripcion().equals(jefatura)) {
			to = gestorPropiedades.valorPropertie("direccion.excel.bajas.mail.destinatario");
		} else if (JefaturasJPTEnum.ALCORCON.getDescripcion().equals(jefatura)) {
			to = gestorPropiedades.valorPropertie("direccion.excel.bajas.mail.destinatario.alcorcon");
		} else if ("ALCALA".equals(jefatura)) {
			to = gestorPropiedades.valorPropertie("direccion.excel.bajas.mail.destinatario.alcala");
		} else if (JefaturasJPTEnum.AVILA.getDescripcion().equals(jefatura) && "SI".equalsIgnoreCase(gestorPropiedades.valorPropertie("envio.mail.jefaturaAvila"))) {
			to = gestorPropiedades.valorPropertie("direccion.excel.bajas.mail.destinatario.avila");
		} else if (JefaturasJPTEnum.SEGOVIA.getDescripcion().equals(jefatura) && "SI".equalsIgnoreCase(gestorPropiedades.valorPropertie("envio.mail.jefaturaSegovia"))) {
			to = gestorPropiedades.valorPropertie("direccion.excel.bajas.mail.destinatario.segovia");
		} else if (JefaturasJPTEnum.CUENCA.getDescripcion().equals(jefatura) && "SI".equalsIgnoreCase(gestorPropiedades.valorPropertie("envio.mail.jefaturaCuenca"))) {
			to = gestorPropiedades.valorPropertie("direccion.excel.bajas.mail.destinatario.cuenca");
		} else if ("CIUDADREAL".equals(jefatura) && "SI".equalsIgnoreCase(gestorPropiedades.valorPropertie("envio.mail.jefaturaCiudadReal"))) {
			to = gestorPropiedades.valorPropertie("direccion.excel.bajas.mail.destinatario.ciudadReal");
		} else if (JefaturasJPTEnum.GUADALAJARA.getDescripcion().equals(jefatura) && "SI".equalsIgnoreCase(gestorPropiedades.valorPropertie("envio.mail.jefaturaGuadalajara"))) {
			to = gestorPropiedades.valorPropertie("direccion.excel.bajas.mail.destinatario.guadalajara");
		}
		return to;
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

	private ResultadoImpresionExcel generarExcel(List<TramiteTrafBajaDto> listaBajas, String jefatura) throws OegamExcepcion {
		ResultadoImpresionExcel resultado = new ResultadoImpresionExcel(Boolean.FALSE);
		String nombreFichero = "";
		File archivo = null;
		// --------------------------------------------------------------------------------

		int contadorTramites = 0;
		for (int j = 1; j <= Math.floor(listaBajas.size() / NUM_BAJAS_POR_EXCEL) + 1; j++) {
			FicheroBean fichero = new FicheroBean();
			if (j == 1) {
				nombreFichero = TipoImpreso.BajasExcel.getNombreEnum() + "_ICOGAM_" + jefatura.toString() + "_" + utilesFecha.getCadenaFecha('_');
			} else {
				nombreFichero = TipoImpreso.BajasExcel.getNombreEnum() + "_ICOGAM_" + jefatura.toString() + "_" + utilesFecha.getCadenaFecha('_') + "_" + j;
			}

			fichero.setExtension(".xls");
			fichero.setNombreDocumento(nombreFichero);
			fichero.setTipoDocumento(ConstantesGestorFicheros.EXCELS);
			fichero.setSubTipo(ConstantesGestorFicheros.BAJASENVIO);
			fichero.setFecha(utilesFecha.getFechaActual());
			fichero.setSobreescribir(true);
			fichero.setFichero(new File(nombreFichero));

			archivo = gestorDocumentos.guardarFichero(fichero);

			// Obtenemos los objetos de la hoja de excel donde insertaremos los trámites.
			WritableWorkbook copyWorkbook = null;
			WritableSheet sheet;
			try {
				// Creamos la hoja y el fichero Excel
				copyWorkbook = Workbook.createWorkbook(archivo);

				sheet = copyWorkbook.createSheet(SOLICITUDBAJAGESTORES, 0);

				// Formato para las columnas que ajusten el tamaño al del texto
				CellView vistaCeldas = new CellView();
				vistaCeldas.setAutosize(true);

				for (int i = 0; i <= COL_DESCRIPCION; i++) {
					sheet.setColumnView(i, vistaCeldas);
				}

				WritableFont fuente = new WritableFont(WritableFont.createFont("MS Sans Serif"), 10, WritableFont.BOLD, false);
				fuente.setColour(Colour.BLACK);
				WritableCellFormat formato = new WritableCellFormat(fuente);

				formato.setBackground(Colour.GRAY_25);
				formato.setBorder(Border.LEFT, BorderLineStyle.THIN);
				formato.setBorder(Border.RIGHT, BorderLineStyle.THIN);

				vistaCeldas.setFormat(formato);
				for (int i = COL_FECHARESOLUCION; i <= COL_DESCRIPCION; i++) {
					sheet.setColumnView(i, vistaCeldas);
				}

				// Generamos las cabeceras de la hoja Excel con el formato indicado
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
					label = new Label(COL_TIPOBAJA, 0, TIPOBAJA, formatoCabecera);
					sheet.addCell(label);
					label = new Label(COL_NTASABAJA, 0, NTASABAJA, formatoCabecera);
					sheet.addCell(label);
					label = new Label(COL_DOCTIPO, 0, DOCTIPO, formatoCabecera);
					sheet.addCell(label);
					label = new Label(COL_DNITITULAR, 0, DNITITULAR, formatoCabecera);
					sheet.addCell(label);
					label = new Label(COL_DNICOTITULARES, 0, DNICOTITULARES, formatoCabecera);
					sheet.addCell(label);
					label = new Label(COL_DNIACREDITAPROPIEDAD, 0, DNIACREDITAPROPIEDAD, formatoCabecera);
					sheet.addCell(label);
					label = new Label(COL_MATRICULA, 0, MATRICULA, formatoCabecera);
					sheet.addCell(label);
					label = new Label(COL_FECHAMATRICULACION, 0, FECHAMATRICULACION, formatoCabecera);
					sheet.addCell(label);
					label = new Label(COL_FECHAITV, 0, FECHAITV, formatoCabecera);
					sheet.addCell(label);
					label = new Label(COL_JUSTIFICANTEAIMPRIMIR, 0, JUSTIFICANTEAIMPRIMIR, formatoCabecera);
					sheet.addCell(label);
					label = new Label(COL_TASAIMPRESIONPERMISO, 0, TASAIMPRESIONPERMISO, formatoCabecera);
					sheet.addCell(label);
					label = new Label(COL_DECLARACIONRESIDUOSOLIDO, 0, DECLARACIONRESIDUOSOLIDO, formatoCabecera);
					sheet.addCell(label);
					label = new Label(COL_DECLARACION_ENTREGA_CATV, 0, DECLARACIONENTREGACATV, formatoCabecera);
					sheet.addCell(label);
					label = new Label(COL_CARTADGT10ANIOS, 0, CARTADGT10ANIOS, formatoCabecera);
					sheet.addCell(label);
					label = new Label(COL_CERTIFICADOMEDIOAMBIENTAL, 0, CERTIFICADOMEDIOAMBIENTAL, formatoCabecera);
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
					for (int k = contadorTramites; k < contadorTramites + NUM_BAJAS_POR_EXCEL && k < listaBajas.size(); k++) {
						TramiteTrafBajaDto tramite = listaBajas.get(k);
						try {
							ResultBean resultadoCambio = servicioTramiteTrafico.cambiarEstadoConEvolucion(tramite.getNumExpediente(), EstadoTramiteTrafico.Pendiente_Envio_Excel,
									EstadoTramiteTrafico.Pendiente_Respuesta_Jefatura, true, TextoNotificacion.Cambio_Estado.getNombreEnum(), tramite.getUsuarioDto().getIdUsuario());

							if (resultadoCambio.getError()) {
								resultado.addMensajeError("Error al cambiar el estado del tramite:" + tramite.getNumExpediente());
								resultado.addNumError();
							} else {
								label = new Label(COL_NGESTORIA, i, tramite.getNumColegiado(), formatoDatos);
								sheet.addCell(label);

								label = new Label(COL_NREFERENCIA, i, tramite.getNumExpediente().toString(), formatoDatos);
								sheet.addCell(label);

								label = new Label(COL_TIPOBAJA, i, MotivoBaja.getDescBajaPorTipo(tramite.getMotivoBaja()), formatoDatos);
								sheet.addCell(label);

								if (tramite.getTasa() != null) {
									label = new Label(COL_NTASABAJA, i, tramite.getTasa().getCodigoTasa(), formatoDatos);
								} else {
									label = new Label(COL_NTASABAJA, i, "", formatoDatos);
								}
								sheet.addCell(label);

								if (tramite.getTitular() != null) {
									if (tramite.getTitular() != null && tramite.getTitular().getPersona().getTipoPersona().equals("JURIDICA")) {
										label = new Label(COL_DOCTIPO, i, "2", formatoDatos);
									} else {
										if (tramite.getTitular().getNifInterviniente().substring(0, 1).equals("X") || tramite.getTitular().getNifInterviniente().substring(0, 1).equals("Y")) {
											label = new Label(COL_DOCTIPO, i, "3", formatoDatos);
										} else {
											label = new Label(COL_DOCTIPO, i, "1", formatoDatos);
										}
									}
									sheet.addCell(label);
									label = new Label(COL_DNITITULAR, i, tramite.getTitular().getNifInterviniente(), formatoDatos);
									sheet.addCell(label);
								} else {
									label = new Label(COL_DOCTIPO, i, "", formatoDatos);
									sheet.addCell(label);
									label = new Label(COL_DNITITULAR, i, "", formatoDatos);
									sheet.addCell(label);
								}

								label = new Label(COL_DNICOTITULARES, i, tramite.getDniCotitulares(), formatoDatos);
								sheet.addCell(label);
								if (tramite.getAdquiriente() != null) {
									label = new Label(COL_DNIACREDITAPROPIEDAD, i, tramite.getAdquiriente().getNifInterviniente(), formatoDatos);
								} else {
									label = new Label(COL_DNIACREDITAPROPIEDAD, i, "", formatoDatos);
								}
								sheet.addCell(label);

								if (tramite.getVehiculoDto() != null) {
									label = new Label(COL_MATRICULA, i, tramite.getVehiculoDto().getMatricula(), formatoDatos);
									sheet.addCell(label);
									label = new Label(COL_FECHAMATRICULACION, i, utilesFecha.formatoFecha(tramite.getVehiculoDto().getFechaMatriculacion()), formatoDatos);
									sheet.addCell(label);
									label = new Label(COL_FECHAITV, i, null != tramite.getVehiculoDto().getFechaItv() ? utilesFecha.formatoFecha(tramite.getVehiculoDto().getFechaItv()) : "",
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

								if (tramite.getImprPermisoCircu() != null && tramite.getImprPermisoCircu()) {
									label = new Label(COL_JUSTIFICANTEAIMPRIMIR, i, "PERMISO CIRCULACIÓN", formatoDatos);
								} else {
									label = new Label(COL_JUSTIFICANTEAIMPRIMIR, i, "AVIO", formatoDatos);
								}
								sheet.addCell(label);

								label = new Label(COL_TASAIMPRESIONPERMISO, i, (null != tramite.getTasaDuplicado() ? tramite.getTasaDuplicado() : ""), formatoDatos);
								sheet.addCell(label);

								if (tramite.getDeclaracionResiduo() != null && tramite.getDeclaracionResiduo()) {
									label = new Label(COL_DECLARACIONRESIDUOSOLIDO, i, VALORSI, formatoDatos);
								} else {
									label = new Label(COL_DECLARACIONRESIDUOSOLIDO, i, VALORNO, formatoDatos);
								}
								sheet.addCell(label);

								if (tramite.getDeclaracionNoEntregaCatV() != null && tramite.getDeclaracionNoEntregaCatV()) {
									label = new Label(COL_DECLARACION_ENTREGA_CATV, i, VALORSI, formatoDatos);
								} else {
									label = new Label(COL_DECLARACION_ENTREGA_CATV, i, VALORNO, formatoDatos);
								}
								sheet.addCell(label);

								if (tramite.getCartaDGTVehiculoMasDiezAn() != null && tramite.getCartaDGTVehiculoMasDiezAn()) {
									label = new Label(COL_CARTADGT10ANIOS, i, VALORSI, formatoDatos);
								} else {
									label = new Label(COL_CARTADGT10ANIOS, i, VALORNO, formatoDatos);
								}
								sheet.addCell(label);

								if (tramite.getCertificadoMedioAmbiental() != null && tramite.getCertificadoMedioAmbiental()) {
									label = new Label(COL_CERTIFICADOMEDIOAMBIENTAL, i, VALORSI, formatoDatos);
								} else {
									label = new Label(COL_CERTIFICADOMEDIOAMBIENTAL, i, VALORNO, formatoDatos);
								}
								sheet.addCell(label);
								label = new Label(COL_OBSERVACIONES, i, (null != tramite.getAnotaciones() ? tramite.getAnotaciones() : ""), formatoDatos);
								sheet.addCell(label);
								label = new Label(COL_FECHA_PRESENTACION, i, utilesFecha.formatoFecha(tramite.getFechaPresentacion()), formatoDatos);
								sheet.addCell(label);
								i++;
								resultado.addNumOK();
							}
						} catch (Throwable e) {
							log.error("Ha sucedido un error a la hora de adjuntar la baja " + tramite.getNumExpediente() + " al excel, error: ", e);
							resultado.addMensajeError("Ha sucedido un error a la hora de adjuntar la baja " + tramite.getNumExpediente() + " al excel.");
							resultado.addNumError();
							servicioTramiteTrafico.cambiarEstadoConEvolucion(tramite.getNumExpediente(), EstadoTramiteTrafico.Pendiente_Respuesta_Jefatura, EstadoTramiteTrafico.Finalizado_Con_Error,
									true, TextoNotificacion.Cambio_Estado.getNombreEnum(), tramite.getUsuarioDto().getIdUsuario());
						}
						contadorTramites++;
					}
				} catch (RowsExceededException e) {
					log.error("Error al imprimir tramites de baja para la jefatura: " + jefatura + ",error: ", e);
					resultado.setError(Boolean.TRUE);
					resultado.setMensajeError("Error al imprimir tramites de baja para la jefatura: " + jefatura);
				} catch (WriteException e) {
					log.error("Error al imprimir tramites de baja para la jefatura: " + jefatura + ",error: ", e);
					resultado.setError(Boolean.TRUE);
					resultado.setMensajeError("Error al imprimir tramites de baja para la jefatura: " + jefatura);
				}
				copyWorkbook.write();
			} catch (IOException e) {
				log.error("Error al imprimir tramites de baja para la jefatura: " + jefatura + ",error: ", e);
				resultado.setError(Boolean.TRUE);
				resultado.setMensajeError("Error al imprimir tramites de baja para la jefatura: " + jefatura);
			} catch (Exception e) {
				log.error("Error al imprimir tramites de baja para la jefatura: " + jefatura + ",error: ", e);
				resultado.setError(Boolean.TRUE);
				resultado.setMensajeError("Error al imprimir tramites de baja para la jefatura: " + jefatura);
			} finally {
				if (copyWorkbook != null) {
					try {
						copyWorkbook.close();
					} catch (WriteException e) {
						log.error("Error al imprimir tramites de baja para la jefatura: " + jefatura + ",error: ", e);
						resultado.setError(Boolean.TRUE);
						resultado.setMensajeError("Error al imprimir tramites de baja para la jefatura: " + jefatura);
					} catch (IOException e) {
						log.error("Error al imprimir tramites de baja para la jefatura: " + jefatura + ",error: ", e);
						resultado.setError(Boolean.TRUE);
						resultado.setMensajeError("Error al imprimir tramites de baja para la jefatura: " + jefatura);
					}
				}
			}
			if (!resultado.getError()) {
				resultado.addFicheros(archivo);
			}
		}
		return resultado;
	}

}