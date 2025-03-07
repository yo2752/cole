package org.gestoresmadrid.oegam2comun.impresion.excel.cambioServicio;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.TextoNotificacion;
import org.gestoresmadrid.oegam2comun.correo.model.service.ServicioCorreo;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioDireccion;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioMunicipio;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioMunicipioSites;
import org.gestoresmadrid.oegam2comun.presentacion.jpt.enumerados.JefaturasJPTEnum;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTrafico;
import org.gestoresmadrid.oegam2comun.trafico.view.beans.ResultadoImpresionExcel;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafCambioServicioDto;
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
import trafico.utiles.enumerados.TipoImpreso;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

@Component
public class ImpresionExcelCambioServicio {

	private static ILoggerOegam log = LoggerOegam.getLogger(ImpresionExcelCambioServicio.class);

	// Constantes hoja Excel
	public static final String SOLICITUDCAMBSERVICIOGESTORES = "SolicitudCambioServicioGestores";
	public static final Integer SHETSOLICITUDCAMBSERVGESTORES = 0;

	public static final String MATRICULA = " MATRICULA  ";
	public static final Integer COL_MATRICULA = 0;
	public static final String NREFERENCIA = " Nº Referencia ";
	public static final Integer COL_NREFERENCIA = 1;
	public static final String DNITITULAR = " DNI TITULAR ";
	public static final Integer COL_DNITITULAR = 2;
	public static final String SERVICIOINCORRECTO = " SERVICIO INCORRECTO ";
	public static final Integer COL_SERVICIOINCORRECTO = 3;
	public static final String SERVICIOCORRECTO = " SERVICIO CORRECTO ";
	public static final Integer COL_SERVICIOCORRECTO = 4;
	public static final String NTASACAMBIOSERVICIO= " Nº TASA  ";
	public static final Integer COL_NTASACAMBIOSERVICIO = 5;
	public static final String NGESTORIA = " Nº Gestoria ";
	public static final Integer COL_NGESTORIA = 6;
	public static final String FECHARESOLUCION = " FECHA_RESOLUCION     ";
	public static final Integer COL_FECHARESOLUCION = 7;
	public static final String RESOLUCION = " RESOLUCION  ";
	public static final Integer COL_RESOLUCION = 8;
	public static final String DESCRIPCION = " DESCRIPCION  ";
	public static final Integer COL_DESCRIPCION = 9;
	

	private static final int NUM_CAMBIOSERVICIO_POR_EXCEL = 300;

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

	public void impresionExcelCambioServicio(List<TramiteTrafCambioServicioDto> listaCambServicio, String jefatura, ResultadoImpresionExcel resultado) {
		ResultadoImpresionExcel resultImpresion = new ResultadoImpresionExcel(Boolean.FALSE);
		try {
			resultImpresion = generarExcel(listaCambServicio, jefatura);
			if (!resultImpresion.getError() && "SI".equals(gestorPropiedades.valorPropertie("envio.mail.cambioServicio.excel"))) {
				enviarCorreoExcel(resultImpresion, jefatura);
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un eror a la hora de generar el excel de cambio de servicio para la jefatura: " + jefatura + ", error: ", e);
			resultImpresion.setError(Boolean.TRUE);
			resultImpresion.setMensajeError("Ha sucedido un eror a la hora de generar el excel de cambio de servicio para la jefatura: " + jefatura);
		}
		rellenarResultadoFinal(resultado, resultImpresion, jefatura);
		
	}

	private ResultadoImpresionExcel generarExcel(List<TramiteTrafCambioServicioDto> listaCambServicio,String jefatura) throws OegamExcepcion {
		ResultadoImpresionExcel resultadoMetodo = new ResultadoImpresionExcel(Boolean.FALSE);
		String nombreFichero = "";
		File archivo = null;

		int contadorTramites = 0;
		for (int j = 1; j <= Math.floor(listaCambServicio.size() / NUM_CAMBIOSERVICIO_POR_EXCEL) + 1; j++) {
			FicheroBean fichero = new FicheroBean();
			if (j == 1) {
				nombreFichero = TipoImpreso.CambioServicioExcel.getNombreEnum() + "_ICOGAM_" + jefatura.toString() + "_" + utilesFecha.getCadenaFecha('_');
			} else {
				nombreFichero = TipoImpreso.CambioServicioExcel.getNombreEnum() + "_ICOGAM_" + jefatura.toString() + "_" + utilesFecha.getCadenaFecha('_') + "_" + j;
			}

			fichero.setExtension(".xls");
			fichero.setNombreDocumento(nombreFichero);
			fichero.setTipoDocumento(ConstantesGestorFicheros.EXCELS);
			fichero.setSubTipo(ConstantesGestorFicheros.CAMBIOSERVICIOENVIO);
			fichero.setFecha(utilesFecha.getFechaActual());
			fichero.setSobreescribir(true);
			fichero.setFichero(new File(nombreFichero));

			archivo = gestorDocumentos.guardarFichero(fichero);

			WritableWorkbook copyWorkbook = null;
			WritableSheet sheet;
			try {
				// Creamos la hoja y el fichero Excel
				// copyWorkbook = Workbook.createWorkbook(new File(ruta));
				// Se le pasa el fichero ya inicializado
				copyWorkbook = Workbook.createWorkbook(archivo);
				sheet = copyWorkbook.createSheet(SOLICITUDCAMBSERVICIOGESTORES, 0);

				// Formato para las columnas que ajusten el tamaño al del texto
				CellView vistaCeldas = new CellView();
				vistaCeldas.setAutosize(true);

				for (int i = 0; i < 10; i++) {
					sheet.setColumnView(i, vistaCeldas);
				}

				WritableFont fuente = new WritableFont(WritableFont.createFont("MS Sans Serif"), 10, WritableFont.BOLD, false);
				fuente.setColour(Colour.BLACK);
				WritableCellFormat formato = new WritableCellFormat(fuente);

				formato.setBackground(Colour.GRAY_25);
				formato.setBorder(Border.LEFT, BorderLineStyle.THIN);
				formato.setBorder(Border.RIGHT, BorderLineStyle.THIN);

				vistaCeldas.setFormat(formato);
				for (int i = 7; i < 10; i++) {
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
					label = new Label(COL_MATRICULA, 0, MATRICULA, formatoCabecera);
					sheet.addCell(label);
					label = new Label(COL_NREFERENCIA, 0, NREFERENCIA, formatoCabecera);
					sheet.addCell(label);
					label = new Label(COL_DNITITULAR, 0, DNITITULAR, formatoCabecera);
					sheet.addCell(label);
					label = new Label(COL_SERVICIOINCORRECTO, 0, SERVICIOINCORRECTO, formatoCabecera);
					sheet.addCell(label);
					label = new Label(COL_SERVICIOCORRECTO, 0, SERVICIOCORRECTO, formatoCabecera);
					sheet.addCell(label);
					label = new Label(COL_NTASACAMBIOSERVICIO, 0, NTASACAMBIOSERVICIO, formatoCabecera);
					sheet.addCell(label);
					label = new Label(COL_NGESTORIA, 0, NGESTORIA, formatoCabecera);
					sheet.addCell(label);
					label = new Label(COL_FECHARESOLUCION, 0, FECHARESOLUCION, formatoCabecera);
					sheet.addCell(label);
					label = new Label(COL_RESOLUCION, 0, RESOLUCION, formatoCabecera);
					sheet.addCell(label);
					label = new Label(COL_DESCRIPCION, 0, DESCRIPCION, formatoCabecera);
					sheet.addCell(label);
				

					Integer i = 1;
					for (int k = contadorTramites; k < contadorTramites + NUM_CAMBIOSERVICIO_POR_EXCEL && k < listaCambServicio.size(); k++) {
						TramiteTrafCambioServicioDto tramiteLista = listaCambServicio.get(k);
						try {
							ResultBean resultadoCambio = servicioTramiteTrafico.cambiarEstadoConEvolucion(tramiteLista.getNumExpediente(), EstadoTramiteTrafico.Pendiente_Envio_Excel,
									EstadoTramiteTrafico.Pendiente_Respuesta_Jefatura, true, TextoNotificacion.Cambio_Estado.getNombreEnum(), tramiteLista.getUsuarioDto().getIdUsuario());
							if (resultadoCambio.getError()) {
								resultadoMetodo.addMensajeError("Error al cambiar el estado del trámite " + tramiteLista.getNumExpediente());
								resultadoMetodo.addNumError();
							} else {
								label = new Label(COL_NGESTORIA, i, tramiteLista.getNumColegiado(), formatoDatos);
								sheet.addCell(label);
								label = new Label(COL_NREFERENCIA, i, tramiteLista.getNumExpediente().toString(), formatoDatos);
								sheet.addCell(label);
								if (tramiteLista.getTitular() != null) {
									label = new Label(COL_DNITITULAR, i, tramiteLista.getTitular().getNifInterviniente(), formatoDatos);
									sheet.addCell(label);
								}else {
									label = new Label(COL_DNITITULAR, i, "", formatoDatos);
									sheet.addCell(label);
								}
							
								if (tramiteLista.getTasa() != null) {
									label = new Label(COL_NTASACAMBIOSERVICIO, i, tramiteLista.getTasa().getCodigoTasa(), formatoDatos);
								} else {
									label = new Label(COL_NTASACAMBIOSERVICIO, i, "", formatoDatos);
								}
								sheet.addCell(label);
								if (tramiteLista.getVehiculoDto() != null) {
									label = new Label(COL_MATRICULA, i, tramiteLista.getVehiculoDto().getMatricula(), formatoDatos);
									sheet.addCell(label);
								} else {
									label = new Label(COL_MATRICULA, i, "", formatoDatos);
									sheet.addCell(label);
								}
								label = new Label(COL_SERVICIOINCORRECTO, i, tramiteLista.getVehiculoDto().getServicioTrafico().getIdServicio(), formatoDatos);
								sheet.addCell(label);
								
								label = new Label(COL_SERVICIOCORRECTO, i, tramiteLista.getVehiculoDto().getServicioTraficoAnterior().getIdServicio(), formatoDatos);
								sheet.addCell(label);
								

								i++;
								resultadoMetodo.addNumOK();
							}
						} catch (Throwable e) {
							log.error("Ha sucedido un error a la hora de adjuntar el cambio de servicio " + tramiteLista.getNumExpediente() + " al excel, error: ", e);
							resultadoMetodo.addMensajeError("Ha sucedido un error a la hora de adjuntar el cambio de servicio " + tramiteLista.getNumExpediente() + " al excel.");
							resultadoMetodo.addNumError();
							servicioTramiteTrafico.cambiarEstadoConEvolucion(tramiteLista.getNumExpediente(), EstadoTramiteTrafico.Pendiente_Respuesta_Jefatura,
									EstadoTramiteTrafico.Pendiente_Envio_Excel, true, TextoNotificacion.Cambio_Estado.getNombreEnum(), tramiteLista.getUsuarioDto().getIdUsuario());
						}
					}
					contadorTramites++;
				} catch (RowsExceededException e) {
					log.error("Error al imprimir tramites de cambio de servicio para la jefatura " + jefatura + ",error: ", e);
					resultadoMetodo.setError(Boolean.TRUE);
					resultadoMetodo.setMensajeError("Error al imprimir tramites de cambio de servicio para la jefatura: " + jefatura);
				} catch (WriteException e) {
					log.error("Error al imprimir tramites de cambio de servicio para la jefatura " + jefatura + ",error: ", e);
					resultadoMetodo.setError(Boolean.TRUE);
					resultadoMetodo.setMensajeError("Error al imprimir tramites de cambio de servicio para la jefatura: " + jefatura);
				}
				copyWorkbook.write();
			} catch (IOException e) {
				log.error("Error al imprimir tramites de cambio de servicio para la jefatura " + jefatura + ",error: ", e);
				resultadoMetodo.setError(Boolean.TRUE);
				resultadoMetodo.setMensajeError("Error al imprimir tramites de cambio de servicio para la jefatura: " + jefatura);
			} catch (Exception e) {
				log.error("Error al imprimir tramites de cambio de servicio para la jefatura " + jefatura + ",error: ", e);
				resultadoMetodo.setError(Boolean.TRUE);
				resultadoMetodo.setMensajeError("Error al imprimir tramites de cambio de servicio para la jefatura: " + jefatura);
			} finally {
				if (copyWorkbook != null) {
					try {
						copyWorkbook.close();
					} catch (WriteException e) {
						log.error("Error al imprimir tramites de cambio de servicio para la jefatura " + jefatura + ",error: ", e);
						resultadoMetodo.setError(Boolean.TRUE);
						resultadoMetodo.setMensajeError("Error al imprimir tramites de cambio de servicio para la jefatura: " + jefatura);
					} catch (IOException e) {
						log.error("Error al imprimir tramites de cambio de servicio para la jefatura " + jefatura + ",error: ", e);
						resultadoMetodo.setError(Boolean.TRUE);
						resultadoMetodo.setMensajeError("Error al imprimir tramites de cambio de servicio para la jefatura: " + jefatura);
					}
				}
			}

			if (!resultadoMetodo.getError()) {
				resultadoMetodo.addFicheros(archivo);
			}
		}
		return resultadoMetodo;
	}

}
