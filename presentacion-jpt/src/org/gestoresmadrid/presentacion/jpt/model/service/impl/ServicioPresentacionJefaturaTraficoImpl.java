package org.gestoresmadrid.presentacion.jpt.model.service.impl;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.core.general.model.vo.UsuarioVO;
import org.gestoresmadrid.core.model.enumerados.EstadoPresentacionJPT;
import org.gestoresmadrid.core.presentacion.jpt.model.beans.BeanPresentadoJpt;
import org.gestoresmadrid.core.presentacion.jpt.model.beans.ResumenJPTBean;
import org.gestoresmadrid.core.trafico.model.vo.EvolucionPresentacionJptPK;
import org.gestoresmadrid.core.trafico.model.vo.EvolucionPresentacionJptVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTraficoVO;
import org.gestoresmadrid.oegam2comun.correo.model.service.ServicioCorreo;
import org.gestoresmadrid.oegam2comun.presentacion.jpt.model.service.ServicioEvolucionPresentacionJpt;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTrafico;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.oegamComun.usuario.view.dto.UsuarioDto;
import org.gestoresmadrid.presentacion.jpt.model.service.ServicioPresentacionJefaturaTrafico;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

@Service
public class ServicioPresentacionJefaturaTraficoImpl implements ServicioPresentacionJefaturaTrafico {

	private static ILoggerOegam log = LoggerOegam.getLogger(ServicioPresentacionJefaturaTraficoImpl.class);
	private static final String FORMATO_FECHA = "dd/MM/yyyy";
	private static final String FONT = "MS Sans Serif";

	@Autowired
	ServicioTramiteTrafico servicioTramiteTrafico;

	@Autowired
	ServicioEvolucionPresentacionJpt servicioEvolucionPresentacionJpt;

	@Autowired
	private ServicioCorreo servicioCorreo;

	@Autowired
	private GestorDocumentos gestorDocumentos;

	@Autowired
	private Conversor conversor;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	private UtilesFecha utilesFecha;

	@Override
	public ResumenJPTBean getResumen(String docId) {
		return servicioTramiteTrafico.getTramitesDocumentoBase(docId);
	}

	@Override
	public List<ResumenJPTBean> getResumenMultiple(List<String> listadoDocId) {
		List<ResumenJPTBean> listaMaestra = new ArrayList<>();

		for (String docId : listadoDocId) {
			ResumenJPTBean resumen = getResumen(docId);
			listaMaestra.add(resumen);
		}

		return listaMaestra;
	}

	@Override
	public List<ResumenJPTBean> presentarTramitesMultiple(List<String> listadoDocId, UsuarioDto usuario) {
		List<ResumenJPTBean> listaResultado = new ArrayList<>();
		for (String docId : listadoDocId) {
			listaResultado.add(presentarTramites(docId, usuario));
		}
		return listaResultado;
	}

	@Override
	public ResumenJPTBean presentarTramites(String docId, UsuarioDto usuario) {
		ResumenJPTBean presentados = null;
		try {
			presentados = getResumen(docId);
			presentados.getNumeroDeExpedientesPresentados();
			int numTramites = servicioTramiteTrafico.presentarJPT(docId);

			EvolucionPresentacionJptVO evolucion = new EvolucionPresentacionJptVO();
			evolucion.setUsuario(new UsuarioVO());
			evolucion.getUsuario().setIdUsuario(usuario.getIdUsuario().longValue());
			evolucion.setJefatura(usuario.getJefaturaJPT());
			evolucion.setTramiteTrafico(new TramiteTraficoVO());
			evolucion.setId(new EvolucionPresentacionJptPK());
			evolucion.getId().setEstadoNuevo(EstadoPresentacionJPT.Presentado.getValorEnum().longValue());
			evolucion.getId().setFechaCambio(utilesFecha.getFechaActualDesfaseBBDD());
			for (BeanPresentadoJpt presentado : presentados.getExpedientes()) {
				// Solo se han actualizado los no presentados
				if (!EstadoPresentacionJPT.Presentado.equals(presentado.getPresentado())) {
					evolucion.getId().setEstadoAnterior(EstadoPresentacionJPT.No_Presentado.getValorEnum().longValue());
					evolucion.getTramiteTrafico().setNumExpediente(new BigDecimal(presentado.getNumExpediente()));
					evolucion.getId().setNumExpediente(evolucion.getTramiteTrafico().getNumExpediente().longValue());
					servicioEvolucionPresentacionJpt.guardarEvolucion(evolucion);
				}
			}

			presentados.setResultadoPresentacion("Actualizados " + numTramites + " tramites");
		} catch (HibernateException e) {
			log.error("Error en la presentacionJPT con los tramites del documento base " + docId, e);
			log.error("Error al guardar en la presentacionJPT con Usuario: " + usuario.getIdUsuario().longValue() + " Jefatura: " + usuario.getJefaturaJPT());
			presentados.setResultadoPresentacion("Error en la presentacionJPT con los tramites del documento base " + docId);
		}
		return presentados;
	}

	@Override
	public ResultBean obtenerNoPresentados() {
		ResultBean resultado = new ResultBean();
		try {
			List<TramiteTraficoVO> lista = servicioTramiteTrafico.obtenerNoPresentados();
			if (lista != null) {
				resultado.setError(false);
				List<TramiteTrafDto> listaDtos = conversor.transform(lista, TramiteTrafDto.class);
				resultado.addAttachment("NOPRESENTADOS", listaDtos);
			} else {
				resultado.setError(true);
			}
		} catch (Exception e) {
			log.error("Error al guardar la evolución");
			resultado.setError(true);
		}
		return resultado;
	}

	@Override
	public void enviarCorreo(List<TramiteTrafDto> noPresentados) throws ParseException, Exception {
		String recipent = null;
		String direccionesOcultas = null;
		try {

			StringBuffer texto = new StringBuffer("<span style=\"font-size:15pt;font-family:Tunga;margin-left:20px;\">");
			// Mantis 20929. David Sierra: Modificacion Correo Presentacion JPT
			texto.append("La hoja de excel con los datos de los expedientes no presentados se ha generado correctamente.");
			texto.append(" Esta se puede descargar en: Administración ---> Presentacion JPT ----> Consulta Expedientes No Presentados JPT, " + "filtrando por la fecha correspondiente.<br>");

			texto.append("</span>");

			recipent = gestorPropiedades.valorPropertie(RECIPIENT);
			direccionesOcultas = gestorPropiedades.valorPropertie(DIRECCIONES_OCULTAS);

			// Mantis 19399. David Sierra. Yo no se envia la excel como archivo adjunto en el correo.
			// Se descarga a traves de la plataforma
			crearExcel(noPresentados);

			String subject = SUBJECT;
			String entorno = gestorPropiedades.valorPropertie("Entorno");
			if (!"PRODUCCION".equals(entorno)) {
				subject = entorno + ": " + subject;
			}

			ResultBean resultado;
			resultado = servicioCorreo.enviarCorreo(texto.toString(), null, null, subject, recipent, null, direccionesOcultas, null, null);

			if (resultado.getError()) {
				log.error("No se ha enviado el correo para las impresiones masivas. Error: " + resultado.getMensaje());
			}
		} catch (OegamExcepcion e) {
			log.error("No se enviaron correos de la impresión masiva", e);
		}
	}

	private FicheroBean crearExcel(List<TramiteTrafDto> noPresentados) throws Exception, OegamExcepcion {
		String nombreFichero = "";
		File archivo = null;
		FicheroBean fichero = new FicheroBean();

		nombreFichero = NOPRESENTADOSJPT + "_" + utilesFecha.getCadenaFecha('_');

		// Inicializamos el ficheroBean
		fichero.setExtension(".xls");
		fichero.setNombreDocumento(nombreFichero);
		fichero.setTipoDocumento(ConstantesGestorFicheros.NOPRESENTADOSJEFATURA);
		fichero.setSubTipo(null);
		fichero.setFecha(utilesFecha.getFechaActual());
		fichero.setSobreescribir(true);
		fichero.setFichero(new File(nombreFichero));

		archivo = gestorDocumentos.guardarFichero(fichero);

		WritableWorkbook copyWorkbook = null;

		// Mantis 21046. David Sierra: Reajuste del fichero Excel de Presentados JPT
		WritableSheet sheetTrans;
		WritableSheet sheetMatr2015;
		WritableSheet sheetMatr2016;

		try {
			CellView vistaCeldas = new CellView();
			vistaCeldas.setAutosize(true);

			copyWorkbook = Workbook.createWorkbook(archivo);

			sheetTrans = copyWorkbook.createSheet(TRANSMISION, 0);
			sheetMatr2015 = copyWorkbook.createSheet(MATRICULACION_2015, 1);
			sheetMatr2016 = copyWorkbook.createSheet(MATRICULACION_2016, 2);

			for (int i = 0; i < 6; i++) {
				sheetTrans.setColumnView(i, vistaCeldas);
				sheetMatr2015.setColumnView(i, vistaCeldas);
				sheetMatr2016.setColumnView(i, vistaCeldas);
			}

			WritableFont fuente = new WritableFont(WritableFont.createFont(FONT), 10, WritableFont.BOLD, false);
			fuente.setColour(Colour.BLACK);
			WritableCellFormat formato = new WritableCellFormat(fuente);

			vistaCeldas.setFormat(formato);
			for (int i = 0; i < 6; i++) {
				vistaCeldas.setAutosize(true);
				sheetTrans.setColumnView(i, vistaCeldas);
				sheetMatr2015.setColumnView(i, vistaCeldas);
				sheetMatr2016.setColumnView(i, vistaCeldas);
			}

			// Generamos las cabeceras de la hoja Excel con el formato indicado

			// Formato de las celdas de cabecera
			WritableFont fuenteCabecera = new WritableFont(WritableFont.createFont(FONT), 10, WritableFont.BOLD, false);
			fuenteCabecera.setColour(Colour.BLACK);
			WritableCellFormat formatoCabecera = new WritableCellFormat(fuenteCabecera);

			formatoCabecera.setBackground(Colour.PALE_BLUE);
			formatoCabecera.setAlignment(Alignment.CENTRE);
			formatoCabecera.setBorder(Border.ALL, BorderLineStyle.THIN);

			// Formato de las celdas de datos
			WritableFont fuenteDatos = new WritableFont(WritableFont.createFont(FONT), 10, WritableFont.NO_BOLD, false);
			fuenteDatos.setColour(Colour.BLACK);
			WritableCellFormat formatoDatos = new WritableCellFormat(fuenteDatos);

			formatoDatos.setAlignment(Alignment.CENTRE);

			Label labelTrans = null;
			Label labelMatr2015 = null;
			Label labelMatr2016 = null;

			try {
				// Mantis 21046. David Sierra: Reajuste del fichero Excel de Presentados JPT. Ahora se divide en Transmisión, Matriculación 2015 y Matriculación 2016
				labelTrans = new Label(0, 0, "Número de colegiado", formatoCabecera);
				sheetTrans.addCell(labelTrans);
				labelMatr2015 = new Label(0, 0, "Número de colegiado", formatoCabecera);
				sheetMatr2015.addCell(labelMatr2015);
				labelMatr2016 = new Label(0, 0, "Número de colegiado", formatoCabecera);
				sheetMatr2016.addCell(labelMatr2016);
				labelTrans = new Label(1, 0, "Número Expediente", formatoCabecera);
				sheetTrans.addCell(labelTrans);
				labelMatr2015 = new Label(1, 0, "Número Expediente", formatoCabecera);
				sheetMatr2015.addCell(labelMatr2015);
				labelMatr2016 = new Label(1, 0, "Número Expediente", formatoCabecera);
				sheetMatr2016.addCell(labelMatr2016);
				labelTrans = new Label(2, 0, "Matrícula", formatoCabecera);
				sheetTrans.addCell(labelTrans);
				labelMatr2015 = new Label(2, 0, "Matrícula", formatoCabecera);
				sheetMatr2015.addCell(labelMatr2015);
				labelMatr2016 = new Label(2, 0, "Matrícula", formatoCabecera);
				sheetMatr2016.addCell(labelMatr2016);
				labelTrans = new Label(3, 0, "Fecha Presentación", formatoCabecera);
				sheetTrans.addCell(labelTrans);
				labelMatr2015 = new Label(3, 0, "Fecha Presentación", formatoCabecera);
				sheetMatr2015.addCell(labelMatr2015);
				labelMatr2016 = new Label(3, 0, "Fecha Presentación", formatoCabecera);
				sheetMatr2016.addCell(labelMatr2016);
				labelTrans = new Label(4, 0, "Jefatura Provincial", formatoCabecera);
				sheetTrans.addCell(labelTrans);
				labelMatr2015 = new Label(4, 0, "Jefatura Provincial", formatoCabecera);
				sheetMatr2015.addCell(labelMatr2015);
				labelMatr2016 = new Label(4, 0, "Jefatura Provincial", formatoCabecera);
				sheetMatr2016.addCell(labelMatr2016);
				labelTrans = new Label(5, 0, "Número de documento Base", formatoCabecera);
				sheetTrans.addCell(labelTrans);
				labelMatr2015 = new Label(5, 0, "Número de documento Base", formatoCabecera);
				sheetMatr2015.addCell(labelMatr2015);
				labelMatr2016 = new Label(5, 0, "Número de documento Base", formatoCabecera);
				sheetMatr2016.addCell(labelMatr2016);

				int contador = 0;
				int contador2 = 0;
				int contador3 = 0;

				for (TramiteTrafDto tramiteTrafDto : noPresentados) {
					// Mantis 21046. David Sierra: Reajuste del fichero Excel de Presentados JPT
					// Transmision
					if (TIPO_TRANSMISION.equals(tramiteTrafDto.getTipoTramite())) {
						labelTrans = new Label(0, contador + 1, tramiteTrafDto.getNumColegiado(), formatoDatos);
						sheetTrans.addCell(labelTrans);

						labelTrans = new Label(1, contador + 1, tramiteTrafDto.getNumExpediente().toString(), formatoDatos);
						sheetTrans.addCell(labelTrans);

						if (tramiteTrafDto.getVehiculoDto() != null && tramiteTrafDto.getVehiculoDto().getMatricula() != null) {
							labelTrans = new Label(2, contador + 1, tramiteTrafDto.getVehiculoDto().getMatricula(), formatoDatos);
							sheetTrans.addCell(labelTrans);
						} else {
							labelTrans = new Label(2, contador + 1, "", formatoDatos);
							sheetTrans.addCell(labelTrans);
						}

						labelTrans = new Label(3, contador + 1, utilesFecha.formatoFecha(FORMATO_FECHA, tramiteTrafDto.getFechaPresentacion().getDate()), formatoDatos);
						sheetTrans.addCell(labelTrans);

						if (tramiteTrafDto.getJefaturaTraficoDto() != null && tramiteTrafDto.getJefaturaTraficoDto().getJefatura() != null) {
							labelTrans = new Label(4, contador + 1, tramiteTrafDto.getJefaturaTraficoDto().getDescripcion(), formatoDatos);
							sheetTrans.addCell(labelTrans);
						} else {
							labelTrans = new Label(4, contador + 1, "", formatoDatos);
							sheetTrans.addCell(labelTrans);
						}

						if (tramiteTrafDto.getDocumentoBase() != null && tramiteTrafDto.getDocumentoBase().getDocId() != null && !tramiteTrafDto.getDocumentoBase().getDocId().isEmpty()) {
							labelTrans = new Label(5, contador + 1, tramiteTrafDto.getDocumentoBase().getDocId(), formatoDatos);
							sheetTrans.addCell(labelTrans);
						} else {
							labelTrans = new Label(5, contador + 1, tramiteTrafDto.getIdYbpdf(), formatoDatos);
							sheetTrans.addCell(labelTrans);
						}
						contador++;
					}
					// Matriculación 2015
					else if (TIPO_MATRICULACION.equals(tramiteTrafDto.getTipoTramite())) {
						if (ANIO_2015.equals(tramiteTrafDto.getFechaPresentacion().getAnio())) {
							labelMatr2015 = new Label(0, contador2 + 1, tramiteTrafDto.getNumColegiado(), formatoDatos);
							sheetMatr2015.addCell(labelMatr2015);

							labelMatr2015 = new Label(1, contador2 + 1, tramiteTrafDto.getNumExpediente().toString(), formatoDatos);
							sheetMatr2015.addCell(labelMatr2015);

							if (tramiteTrafDto.getVehiculoDto() != null && tramiteTrafDto.getVehiculoDto().getMatricula() != null) {
								labelMatr2015 = new Label(2, contador2 + 1, tramiteTrafDto.getVehiculoDto().getMatricula(), formatoDatos);
								sheetMatr2015.addCell(labelMatr2015);
							} else {
								labelMatr2015 = new Label(2, contador2 + 1, "", formatoDatos);
								sheetMatr2015.addCell(labelMatr2015);
							}

							labelMatr2015 = new Label(3, contador2 + 1, utilesFecha.formatoFecha(FORMATO_FECHA, tramiteTrafDto.getFechaPresentacion().getDate()), formatoDatos);
							sheetMatr2015.addCell(labelMatr2015);

							if (tramiteTrafDto.getJefaturaTraficoDto() != null && tramiteTrafDto.getJefaturaTraficoDto().getJefatura() != null) {
								labelMatr2015 = new Label(4, contador2 + 1, tramiteTrafDto.getJefaturaTraficoDto().getDescripcion(), formatoDatos);
								sheetMatr2015.addCell(labelMatr2015);
							} else {
								labelMatr2015 = new Label(4, contador2 + 1, tramiteTrafDto.getJefaturaTraficoDto().getDescripcion(), formatoDatos);
								sheetMatr2015.addCell(labelMatr2015);
							}
							if (tramiteTrafDto.getDocumentoBase() != null && tramiteTrafDto.getDocumentoBase().getDocId() != null && !tramiteTrafDto.getDocumentoBase().getDocId().isEmpty()) {
								labelMatr2015 = new Label(5, contador2 + 1, tramiteTrafDto.getDocumentoBase().getDocId(), formatoDatos);
								sheetMatr2015.addCell(labelMatr2015);
							} else {
								labelMatr2015 = new Label(5, contador2 + 1, tramiteTrafDto.getIdYbpdf(), formatoDatos);
								sheetMatr2015.addCell(labelMatr2015);
							}
							contador2++;

							// Matriculación 2016
						} else if (ANIO_2016.equals(tramiteTrafDto.getFechaPresentacion().getAnio())) {
							labelMatr2016 = new Label(0, contador3 + 1, tramiteTrafDto.getNumColegiado(), formatoDatos);
							sheetMatr2016.addCell(labelMatr2016);

							labelMatr2016 = new Label(1, contador3 + 1, tramiteTrafDto.getNumExpediente().toString(), formatoDatos);
							sheetMatr2016.addCell(labelMatr2016);

							if (tramiteTrafDto.getVehiculoDto() != null && tramiteTrafDto.getVehiculoDto().getMatricula() != null) {
								labelMatr2016 = new Label(2, contador3 + 1, tramiteTrafDto.getVehiculoDto().getMatricula(), formatoDatos);
								sheetMatr2016.addCell(labelMatr2016);
							} else {
								labelMatr2016 = new Label(2, contador3 + 1, "", formatoDatos);
								sheetMatr2016.addCell(labelMatr2016);
							}

							labelMatr2016 = new Label(3, contador3 + 1, utilesFecha.formatoFecha(FORMATO_FECHA, tramiteTrafDto.getFechaPresentacion().getDate()), formatoDatos);
							sheetMatr2016.addCell(labelMatr2016);

							if (tramiteTrafDto.getJefaturaTraficoDto() != null && tramiteTrafDto.getJefaturaTraficoDto().getJefatura() != null) {
								labelMatr2016 = new Label(4, contador3 + 1, tramiteTrafDto.getJefaturaTraficoDto().getDescripcion(), formatoDatos);
								sheetMatr2016.addCell(labelMatr2016);
							} else {
								labelMatr2016 = new Label(4, contador3 + 1, tramiteTrafDto.getJefaturaTraficoDto().getDescripcion(), formatoDatos);
								sheetMatr2016.addCell(labelMatr2016);
							}
							if (tramiteTrafDto.getDocumentoBase() != null && tramiteTrafDto.getDocumentoBase().getDocId() != null && !tramiteTrafDto.getDocumentoBase().getDocId().isEmpty()) {
								labelMatr2016 = new Label(5, contador3 + 1, tramiteTrafDto.getDocumentoBase().getDocId(), formatoDatos);
								sheetMatr2016.addCell(labelMatr2016);
							} else {
								labelTrans = new Label(5, contador + 1, tramiteTrafDto.getIdYbpdf(), formatoDatos);
								labelMatr2016 = new Label(5, contador3 + 1, tramiteTrafDto.getIdYbpdf(), formatoDatos);
								sheetMatr2016.addCell(labelMatr2016);
							}
							contador3++;
						}
					}
				}

			} catch (RowsExceededException e) {
				log.error("Error al obtener las estadisticas agrupadas para excel.", e);
			} catch (WriteException e) {
				log.error("Error al obtener las estadisticas agrupadas para excel.", e);
			}

			copyWorkbook.write();

		} catch (IOException e) {
			throw new Exception(e);
		} catch (Exception e) {
			throw new Exception(e);
		}

		copyWorkbook.close();

		FicheroBean ficheroAdjunto = new FicheroBean();
		ficheroAdjunto.setFichero(archivo);
		ficheroAdjunto.setNombreDocumento(nombreFichero + ".xls");

		return ficheroAdjunto;
	}
}