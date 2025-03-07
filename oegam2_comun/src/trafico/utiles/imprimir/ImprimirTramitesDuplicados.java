package trafico.utiles.imprimir;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.springContext.ContextoSpring;
import org.gestoresmadrid.oegam2comun.correo.model.service.ServicioCorreo;
import org.gestoresmadrid.oegam2comun.presentacion.jpt.enumerados.JefaturasJPTEnum;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;

import colas.constantes.ConstantesProcesos;
import colas.daos.EjecucionesProcesosDAO;
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
import oegam.constantes.ConstantesPQ;
import oegam.constantes.ConstantesSession;
import trafico.beans.ExcelDuplicadosCursor;
import trafico.beans.daos.BeanPQCambiarEstadoTramite;
import trafico.modelo.ModeloDuplicado;
import trafico.utiles.UtilesConversiones;
import trafico.utiles.enumerados.MotivoDuplicado;
import trafico.utiles.enumerados.TipoImpreso;
import trafico.utiles.enumerados.TipoProceso;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

public class ImprimirTramitesDuplicados extends ImprimirGeneral {

	private static ILoggerOegam log = LoggerOegam.getLogger(ImprimirTramitesDuplicados.class);
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

	/** **/

	// public static final String DOMICILIO = " DOMICILIO     ";
	// public static final Integer COL_DOMICILIO = 7;

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

	private ModeloDuplicado modeloDuplicado;

	@Autowired
	GestorDocumentos gestorDocumentos;

	@Autowired
	private ServicioCorreo servicioCorreo;

	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	EjecucionesProcesosDAO ejecucionesProcesosDAO;

	@Autowired
	UtilesConversiones utilesConversiones;
	
	public ServicioCorreo getServicioCorreo() {
		if(servicioCorreo == null){
			servicioCorreo = ContextoSpring.getInstance().getBean(ServicioCorreo.class);
		}
		return servicioCorreo;
	}

	/**
	 * Método que generará un fichero Excel con los Duplicados
	 * @param listaTramitesB
	 * @return
	 * @throws Throwable
	 */
	public ResultBean excelDuplicados(ArrayList<ExcelDuplicadosCursor> listaTramitesB, String jefatura) throws Throwable {

		ResultBean resultadoMetodo = new ResultBean();

		String nombreFichero = "";
		File archivo = null;
		FicheroBean fichero = new FicheroBean();

		nombreFichero = TipoImpreso.DuplicadosExcel.getNombreEnum() + "_ICOGAM_" + jefatura.toString() + "_" + utilesFecha.getCadenaFecha('_');

		// Inicializamos el ficheroBean
		fichero.setExtension(".xls");
		fichero.setNombreDocumento(nombreFichero);
		fichero.setTipoDocumento(ConstantesGestorFicheros.EXCELS);
		fichero.setSubTipo(ConstantesGestorFicheros.DUPLICADOSENVIO);
		fichero.setFecha(utilesFecha.getFechaActual());
		fichero.setSobreescribir(true);
		fichero.setFichero(new File(nombreFichero));

		archivo = gestorDocumentos.guardarFichero(fichero);

		// Obtenemos los objetos de la hoja de excel donde insertaremos los trámites.
		WritableWorkbook copyWorkbook = null;

		WritableSheet sheet;
		try {

			// Creamos la hoja y el fichero Excel
			// copyWorkbook = Workbook.createWorkbook(new File(ruta));
			// Se le pasa el fichero ya inicializado
			copyWorkbook = Workbook.createWorkbook(archivo);
			sheet = copyWorkbook.createSheet(SOLICITUDDUPLICADOGESTORES, 0);

			// Formato para las columnas que ajusten el tamaño al del texto
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

			// WritableCellFormat formatoNumeros = new WritableCellFormat(NumberFormats.TEXT);

			// formatoDatos.setBackground(Colour.PALE_BLUE);
			formatoDatos.setAlignment(Alignment.LEFT);
			// formatoDatos.setBackground(Colour.GREY_25_PERCENT);

			Label label = null;
			// Number numero = null;
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

				/* 0002526: Modificación Plantillas SITEX de Bajas y Duplicados. nuevos campos para excel duplicados */
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

				/* ; */
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

				// modificacion sitex 20/11/2012 atc
				label = new Label(COL_FECHA_PRESENTACION, 0, FECHAPRESENTACION, formatoCabecera);
				sheet.addCell(label);
				// fin modificacion

				label = new Label(COL_FECHARESOLUCION, 0, FECHARESOLUCION, formatoCabecera);
				sheet.addCell(label);
				label = new Label(COL_RESOLUCION, 0, RESOLUCION, formatoCabecera);
				sheet.addCell(label);
				label = new Label(COL_DESCRIPCION, 0, DESCRIPCION, formatoCabecera);
				sheet.addCell(label);

				Integer i = 1;
				for (ExcelDuplicadosCursor tramiteLista : listaTramitesB) {

					// cambiamos el estado del trámite
					BeanPQCambiarEstadoTramite beanCambioEstado = new BeanPQCambiarEstadoTramite();

					beanCambioEstado.setP_NUM_EXPEDIENTE(tramiteLista.getNUM_EXPEDIENTE());

					beanCambioEstado.setP_ESTADO(new BigDecimal(EstadoTramiteTrafico.Pendiente_Respuesta_Jefatura.getValorEnum()));
					HashMap<String, Object> resultadoModelo = getModeloTrafico().cambiarEstadoTramite(beanCambioEstado);
					ResultBean resultadoCambio = (ResultBean) resultadoModelo.get(ConstantesPQ.RESULTBEAN);

					if (resultadoCambio.getError()) {
						resultadoMetodo.setMensaje(" Realizando el cambio del estado del trámite impreso " + tramiteLista.getNUM_EXPEDIENTE());
						resultadoMetodo.setError(true);
					}

					label = new Label(COL_NGESTORIA, i, tramiteLista.getNUM_COLEGIADO(), formatoDatos);
					sheet.addCell(label);
					// numero = new Number(COL_NGESTORIA, i, (new Double(utilesColegiado.getContrato().get_num_colegiado())).doubleValue(), formatoNumeros);
					// sheet.addCell(numero);

					label = new Label(COL_NREFERENCIA, i, tramiteLista.getNUM_EXPEDIENTE().toString(), formatoDatos);
					sheet.addCell(label);
					// numero = new Number(COL_NREFERENCIA, i, (null!=tramiteLista.getTramiteTrafico().getNumExpediente()
					// ?(new Double(tramiteLista.getTramiteTrafico().getNumExpediente().toString())).doubleValue():(new Double("0"))), formatoNumeros);
					// sheet.addCell(numero);

					label = new Label(COL_TIPODUPLICADO, i, null != tramiteLista.getMOTIVO_DUPLICADO() ? MotivoDuplicado.convertirTexto(tramiteLista.getMOTIVO_DUPLICADO()).replace(".", "")
							.toUpperCase() : "", formatoDatos);
					sheet.addCell(label);

					label = new Label(COL_NTASADUPLICADO, i, tramiteLista.getCODIGO_TASA(), formatoDatos);
					sheet.addCell(label);

					if (tramiteLista.getTIPO_DOCUMENTO().equals("CIF")) {
						label = new Label(COL_DOCTIPO, i, "2", formatoDatos);

					} else {
						if (tramiteLista.getNif().substring(0, 1).equals("X") || tramiteLista.getNif().substring(0, 1).equals("Y")) {
							label = new Label(COL_DOCTIPO, i, "3", formatoDatos);
						} else {
							label = new Label(COL_DOCTIPO, i, "1", formatoDatos);
						}
					}
					sheet.addCell(label);
					label = new Label(COL_DNITITULAR, i, tramiteLista.getNif(), formatoDatos);
					sheet.addCell(label);
					label = new Label(COL_DNICOTITULAR, i, tramiteLista.getNif_COTITULAR(), formatoDatos);
					sheet.addCell(label);

					/* nuevos campos SITEX */
					label = new Label(COL_TIPOVIA, i, tramiteLista.getId_Tipo_via(), formatoDatos);
					sheet.addCell(label);

					label = new Label(COL_NOMBREVIA, i, tramiteLista.getNombre_via(), formatoDatos);
					sheet.addCell(label);

					label = new Label(COL_NUMERO, i, null != tramiteLista.getNumero() ? tramiteLista.getNumero() : "SN", formatoDatos); // (sitex:si no existe el poner ND)
					sheet.addCell(label);

					label = new Label(COL_BLOQUE, i, tramiteLista.getBloque(), formatoDatos);
					sheet.addCell(label);

					label = new Label(COL_PORTAL, i, tramiteLista.getLetra(), formatoDatos);
					sheet.addCell(label);

					label = new Label(COL_ESCALERA, i, tramiteLista.getEscalera(), formatoDatos);
					sheet.addCell(label);
					label = new Label(COL_PLANTA, i, tramiteLista.getPlanta(), formatoDatos);
					sheet.addCell(label);
					label = new Label(COL_PUERTA, i, tramiteLista.getPuerta(), formatoDatos);
					sheet.addCell(label);
					label = new Label(COL_KM, i, tramiteLista.getKm(), formatoDatos);
					sheet.addCell(label);
					label = new Label(COL_HM, i, tramiteLista.getHm(), formatoDatos);
					sheet.addCell(label);
					label = new Label(COL_LOCALIDAD, i, tramiteLista.getLocalidad(), formatoDatos); // OJO: ANTONIO TRENADO NO DEJAR EN BLANCO SINO CON EL VALOR DEL PUEBLO!!!
					sheet.addCell(label);

					/**/

					label = new Label(COL_CODIGOPOSTAL, i, tramiteLista.getCod_postal(), formatoDatos);
					sheet.addCell(label);

					String municipioAntiguo = utilesConversiones.convertirMunicipioAntigua(tramiteLista.getId_municipio(), tramiteLista.getId_provincia());

					label = new Label(COL_MUNICIPIO, i, utilesConversiones.ajustarCamposIne(municipioAntiguo), formatoDatos);
					sheet.addCell(label);

					label = new Label(COL_PROVINCIA, i, tramiteLista.getProvincia(), formatoDatos);
					sheet.addCell(label);

					label = new Label(COL_MATRICULA, i, tramiteLista.getMatricula(), formatoDatos);
					sheet.addCell(label);

					label = new Label(COL_FECHAMATRICULACION, i, null != tramiteLista.getFecha_matriculacion() ? utilesFecha.formatoFecha(tramiteLista.getFecha_matriculacion()) : "", formatoDatos);
					sheet.addCell(label);

					label = new Label(COL_FECHAITV, i, null != tramiteLista.getFecha_itv() ? utilesFecha.formatoFecha(tramiteLista.getFecha_itv()) : "", formatoDatos);
					sheet.addCell(label);

					label = new Label(COL_JUSTIFICANTEAIMPRIMIR, i, tramiteLista.getIMPR_PERMISO_CIRCU(), formatoDatos);
					sheet.addCell(label);

					label = new Label(COL_IMPORTACION, i, (null != tramiteLista.getIMPORTACION() ? tramiteLista.getIMPORTACION() : ""), formatoDatos);
					sheet.addCell(label);

					label = new Label(COL_TASAIMPORTACION, i, (null != tramiteLista.getTasa_importacion() ? tramiteLista.getTasa_importacion() : ""), formatoDatos);
					sheet.addCell(label);

					label = new Label(COL_OBSERVACIONES, i, (null != tramiteLista.getAnotaciones() ? tramiteLista.getAnotaciones() : ""), formatoDatos);
					sheet.addCell(label);

					// Modificacion sitex atc 20/11/2012
					label = new Label(COL_FECHA_PRESENTACION, i, utilesFecha.formatoFecha(tramiteLista.getFecha_presentacion()), formatoDatos);
					sheet.addCell(label);
					// fin modificacion

					i++;
				}

			} catch (RowsExceededException e) {
				log.error("Error al imprimir tramites de duplicado ", e);
			} catch (WriteException e) {
				log.error("Error al imprimir tramites de duplicado ", e);
			}

			copyWorkbook.write();
		} catch (IOException e) {
			log.error("Error al imprimir tramites de duplicado ", e);
		} catch (Exception e) {
			log.error("Error al imprimir tramites de duplicado ", e);
		} finally {

			if (copyWorkbook != null) {

				try {

					copyWorkbook.close();
				} catch (WriteException e) {
					log.error("Error al imprimir tramites de duplicado ", e);
				} catch (IOException e) {
					log.error("Error al imprimir tramites de duplicado ", e);
				}
			}

		}

		resultadoMetodo.addAttachment(ConstantesSession.FICHERO_EXCEL, archivo);
		resultadoMetodo.addAttachment(ConstantesSession.HAY_FICHERO_EXCEL, true);

		TipoProceso procesoAGuardar = null;
		if (JefaturasJPTEnum.MADRID.getDescripcion().equals(jefatura)) {
			procesoAGuardar = TipoProceso.Excel_Duplicados_Madrid;
		} else if (JefaturasJPTEnum.ALCORCON.getDescripcion().equals(jefatura)) {
			procesoAGuardar = TipoProceso.Excel_Duplicados_Alcorcon;
		}else if ("ALCALA".equals(jefatura)) {
			procesoAGuardar = TipoProceso.Excel_Duplicados_Alcala;
		}else if(JefaturasJPTEnum.AVILA.getDescripcion().equals(jefatura) && "SI".equalsIgnoreCase(gestorPropiedades.valorPropertie("envio.mail.jefaturaAvila"))){
			procesoAGuardar = TipoProceso.Excel_Duplicados_Avila;
		}else if(JefaturasJPTEnum.SEGOVIA.getDescripcion().equals(jefatura) && "SI".equalsIgnoreCase(gestorPropiedades.valorPropertie("envio.mail.jefaturaSegovia"))){
			procesoAGuardar = TipoProceso.Excel_Duplicados_Segovia;
		}else if(JefaturasJPTEnum.CUENCA.getDescripcion().equals(jefatura) && "SI".equalsIgnoreCase(gestorPropiedades.valorPropertie("envio.mail.jefaturaCuenca"))){
			procesoAGuardar = TipoProceso.Excel_Duplicados_Cuenca;
		}else if("CIUDADREAL".equals(jefatura) && "SI".equalsIgnoreCase(gestorPropiedades.valorPropertie("envio.mail.jefaturaCiudadReal"))){
			procesoAGuardar = TipoProceso.Excel_Duplicados_Ciudad_Real;
		}else if(JefaturasJPTEnum.GUADALAJARA.getDescripcion().equals(jefatura) && "SI".equalsIgnoreCase(gestorPropiedades.valorPropertie("envio.mail.jefaturaGuadalajara"))){
			procesoAGuardar = TipoProceso.Excel_Duplicados_Guadalajara;
		}
		

		// Mandamos por correo el excel generado.
		try {
			enviarMailFicheroExcel(resultadoMetodo, jefatura);
		} catch (Exception e) {
			log.info(e);
			// Ricardo Rodriguez. Incidencia 3139. 18/12/2012
			resultadoMetodo.setError(true);
			resultadoMetodo.setMensaje(e.toString());
			// Fin. Ricardo Rodriguez. Incidencia 3139. 18/12/2012
		} catch (OegamExcepcion e) {
			log.info(e);
			// Ricardo Rodriguez. Incidencia 3139. 18/12/2012
			resultadoMetodo.setError(true);
			resultadoMetodo.setMensaje(e.toString());
			// Fin. Ricardo Rodriguez. Incidencia 3139. 18/12/2012
		}

		// Inserción fecha envio excel duplicados siempre que no haya habido error
		String mensaje = resultadoMetodo.getMensaje();
		if (!resultadoMetodo.getError()) {
			try {
				// 01-10-2012. Ricardo Rodriguez. Incidencia : 0000814
				if (mensaje == null || mensaje.equals("")) {
					mensaje = ConstantesProcesos.EJECUCION_CORRECTA_MENSAJE_X_DEFECTO;
				}
				// getModeloTrafico().guardarEnvioData(procesoAGuardar);
				ejecucionesProcesosDAO.actualizarUltimaEjecucion(procesoAGuardar.getValorEnum(), mensaje, ConstantesProcesos.EJECUCION_CORRECTA);
				// FIN : Incidencia : 0000814
				log.info("Se envió el correo y se guardó la información del envío existosamente.");
			} catch (Exception e) {
				log.error("Error al Guardar Envio Data para " + procesoAGuardar.getNombreEnum());
				log.error("Detalle: " + e.getMessage());
			}
		}// 01-10-2012. Ricardo Rodriguez. Incidencia : 0000814
		else {
			if (mensaje == null || mensaje.equals("")) {
				mensaje = ConstantesProcesos.EJECUCION_NO_CORRECTA;
			}
			try {
				ejecucionesProcesosDAO.actualizarUltimaEjecucion(procesoAGuardar.getValorEnum(), mensaje, ConstantesProcesos.EJECUCION_EXCEPCION);
				// Hace 'rollback' del estado de los trámites de la lista:
				getModeloDuplicado().cambiarEstadoListaDuplicados(listaTramitesB, EstadoTramiteTrafico.Pendiente_Envio_Excel);
			} catch (Exception e) {
				log.error("ERROR: Fallo en la actualización de la última ejecución en ENVIO_DATA en " + procesoAGuardar.getValorEnum());
			}
		}
		// FIN : Incidencia : 0000814

		return resultadoMetodo;

	}

	private ResultBean enviarMailFicheroExcel(ResultBean resultadoExportar, String jefatura) throws OegamExcepcion, Exception {

		ResultBean resultadoEnviarFichero = resultadoExportar;
		if (null != resultadoEnviarFichero && null != resultadoEnviarFichero.getAttachment(ConstantesSession.HAY_FICHERO_EXCEL)) {
			Boolean hayFicheroExcel = (Boolean) resultadoEnviarFichero.getAttachment(ConstantesSession.HAY_FICHERO_EXCEL);

			if (hayFicheroExcel != null && hayFicheroExcel) {

				// nueva utilizacion del GestorFicheros para envio de excel de duplicado. Mantis 6798
				File file = (File) resultadoEnviarFichero.getAttachment(ConstantesSession.FICHERO_EXCEL);

				String from = gestorPropiedades.valorPropertie("direccion.excel.duplicados.mail.usuario");
				String to = null;
				if (JefaturasJPTEnum.MADRID.getDescripcion().equals(jefatura)) {
					to = gestorPropiedades.valorPropertie("direccion.excel.duplicados.mail.destinatario");
				} else if (JefaturasJPTEnum.ALCORCON.getDescripcion().equals(jefatura)) {
					to = gestorPropiedades.valorPropertie("direccion.excel.duplicados.mail.destinatario.alcorcon");
				}else if ("ALCALA".equals(jefatura)) {
					to = gestorPropiedades.valorPropertie("direccion.excel.duplicados.mail.destinatario.alcala");
				}else if(JefaturasJPTEnum.AVILA.getDescripcion().equals(jefatura) && "SI".equalsIgnoreCase(gestorPropiedades.valorPropertie("envio.mail.jefaturaAvila"))){
					to = gestorPropiedades.valorPropertie("direccion.excel.duplicados.mail.destinatario.avila");
				}else if(JefaturasJPTEnum.SEGOVIA.getDescripcion().equals(jefatura) && "SI".equalsIgnoreCase(gestorPropiedades.valorPropertie("envio.mail.jefaturaSegovia"))){
					to = gestorPropiedades.valorPropertie("direccion.excel.duplicados.mail.destinatario.segovia");
				}else if(JefaturasJPTEnum.CUENCA.getDescripcion().equals(jefatura) && "SI".equalsIgnoreCase(gestorPropiedades.valorPropertie("envio.mail.jefaturaCuenca"))){
					to = gestorPropiedades.valorPropertie("direccion.excel.duplicados.mail.destinatario.cuenca");
				}else if("CIUDADREAL".equals(jefatura) && "SI".equalsIgnoreCase(gestorPropiedades.valorPropertie("envio.mail.jefaturaCiudadReal"))){
					to = gestorPropiedades.valorPropertie("direccion.excel.duplicados.mail.destinatario.ciudadReal");
				}else if(JefaturasJPTEnum.GUADALAJARA.getDescripcion().equals(jefatura) && "SI".equalsIgnoreCase(gestorPropiedades.valorPropertie("envio.mail.jefaturaGuadalajara"))){
					to = gestorPropiedades.valorPropertie("direccion.excel.duplicados.mail.destinatario.guadalajara");
				}

				String bcc = gestorPropiedades.valorPropertie("direccion.excel.duplicados.mail.destinatario2");
				String subject = gestorPropiedades.valorPropertie("direccion.excel.duplicados.mail.subject");
				String texto = "<br><span style=\"font-size:15pt;font-family:Tunga;margin-left:20px;\"><br>Se solicita desde la Oficina Electrónica de Gestión Administrativa (OEGAM), el envío del siguiente fichero Excel con los duplicados<br></span>";

				FicheroBean fichero = new FicheroBean();
				fichero.setFichero(file);
				fichero.setNombreDocumento(file.getName());

				ResultBean resultEnvio;
				resultEnvio = getServicioCorreo().enviarCorreoDuplicados(texto, null, from, subject, to, bcc, null, "duplicados", fichero);
				
				if (resultEnvio.getError()) {
					resultadoEnviarFichero.setError(true);
					resultadoEnviarFichero.setMensaje(resultEnvio.getMensaje());
				} else {
					resultadoEnviarFichero.setError(false);
				}
			}
		}

		return resultadoEnviarFichero;
	}

	/* *********************************************************************** */
	/* MODELOS *************************************************************** */
	/* *********************************************************************** */

	public ModeloDuplicado getModeloDuplicado() {
		if (modeloDuplicado == null) {
			modeloDuplicado = new ModeloDuplicado();
		}
		return modeloDuplicado;
	}

	public void setModeloDuplicado(ModeloDuplicado modeloDuplicado) {
		this.modeloDuplicado = modeloDuplicado;
	}
}
