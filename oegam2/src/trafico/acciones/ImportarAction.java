package trafico.acciones;

import static trafico.utiles.constantes.ConstantesDGT.BEAN_PDF;
import static trafico.utiles.constantes.ConstantesDGT.ERROR_FICHERO_SIN_SOLICUTUD;
import static trafico.utiles.constantes.ConstantesDGT.ERROR_FICHERO_SIN_TRAMITES;
import static trafico.utiles.constantes.ConstantesDGT.ERROR_FORMATO_FICHERO;
import static trafico.utiles.constantes.ConstantesDGT.ERROR_IMPORTAR_FICHERO;
import static trafico.utiles.constantes.ConstantesDGT.REGISTRO_BAJA;
import static trafico.utiles.constantes.ConstantesDGT.REGISTRO_MATRICULACION;
import static trafico.utiles.constantes.ConstantesDGT.REGISTRO_PDF;
import static trafico.utiles.constantes.ConstantesDGT.REGISTRO_TRANSMISION;
import static trafico.utiles.constantes.ConstantesDGT.TAM_REG_TRAMITE;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;
import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.enumerados.FicherosImportacion;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.trafico.eeff.model.enumerados.ConstantesEEFF;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTraficoVO;
import org.gestoresmadrid.oegam2comun.importacion.importadores.ImportadorDGTBaja;
import org.gestoresmadrid.oegam2comun.importacion.model.service.ServicioImportar;
import org.gestoresmadrid.oegam2comun.importacion.utiles.FORMATOGABajaDtoConversion;
import org.gestoresmadrid.oegam2comun.importacion.utiles.FORMATOGADuplicadoDtoConversion;
import org.gestoresmadrid.oegam2comun.importacion.utiles.FORMATOGAMatwDtoConversion;
import org.gestoresmadrid.oegam2comun.ivtmMatriculacion.model.service.ServicioIvtmMatriculacion;
import org.gestoresmadrid.oegam2comun.model.service.ServicioPermisos;
import org.gestoresmadrid.oegam2comun.presentacion.jpt.enumerados.JefaturasJPTEnum;
import org.gestoresmadrid.oegam2comun.tasas.view.beans.Tasa;
import org.gestoresmadrid.oegam2comun.trafico.eeff.model.service.ServicioEEFFNuevo;
import org.gestoresmadrid.oegam2comun.trafico.inteve.model.service.ServicioInteve;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTrafico;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTraficoMatriculacion;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafBajaDto;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafDto;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafDuplicadoDto;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafMatrDto;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import com.opensymphony.xwork2.Action;

import es.globaltms.gestorDocumentos.bean.FicheroBean;
import es.globaltms.gestorDocumentos.bean.FileResultBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.enums.FileResultStatus;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import escrituras.beans.CamposRespuestaPLSQL;
import escrituras.beans.Persona;
import escrituras.beans.ResultBean;
import general.acciones.ActionBase;
import general.utiles.UtilesXML;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import oegam.constantes.ConstantesPQ;
import oegam.constantes.ConstantesSession;
import trafico.beans.ImportacionDGTPDFBean;
import trafico.beans.IntervinienteTrafico;
import trafico.beans.ResumenImportacion;
import trafico.beans.SolicitudDatosBean;
import trafico.beans.SolicitudVehiculoBean;
import trafico.beans.TramiteTraficoBean;
import trafico.beans.VehiculoBean;
import trafico.beans.daos.BeanPQAltaMatriculacionImport;
import trafico.beans.daos.BeanPQCambiarEstadoTramite;
import trafico.beans.daos.BeanPQTramiteTransmisionImport;
import trafico.beans.jaxb.baja.FORMATOOEGAM2BAJA;
import trafico.beans.jaxb.duplicados.FORMATOOEGAM2DUPLICADO;
import trafico.beans.jaxb.matw.FORMATOGA.MATRICULACION.DATOSIMPUESTOS.DATOSIMVTM;
import trafico.beans.jaxb.solicitud.FORMATOOEGAM2SOLICITUD;
import trafico.beans.utiles.FORMATOGAtransmisionPQConversion;
import trafico.beans.utiles.FORMATOOEGAM2SOLICITUDConversion;
import trafico.beans.utiles.FormatoGaMatriculacionMatwPqConversion;
import trafico.beans.utiles.ParametrosPegatinaMatriculacion;
import trafico.modelo.ModeloImportacionDGT;
import trafico.modelo.ModeloSolicitudDatos;
import trafico.modelo.ModeloTrafico;
import trafico.modelo.impl.ModeloImportacionMasivaImpl;
import trafico.modelo.ivtm.IVTMModeloMatriculacionInterface;
import trafico.modelo.ivtm.impl.IVTMModeloMatriculacionImpl;
import trafico.utiles.UtilesVistaTrafico;
import trafico.utiles.XMLBajaFactory;
import trafico.utiles.XMLDuplicadoFactory;
import trafico.utiles.XMLGAFactory;
import trafico.utiles.XMLManejadorErrores;
import trafico.utiles.XMLSolicitudFactory;
import trafico.utiles.XMLTransmisionFactory;
import trafico.utiles.constantes.Constantes;
import trafico.utiles.constantes.ConstantesMensajes;
import trafico.utiles.constantes.ConstantesTrafico;
import trafico.utiles.enumerados.TipoCreacion;
import trafico.utiles.importacion.importadores.ImportadorDGTMatriculacion;
import trafico.utiles.importacion.importadores.ImportadorDGTTransmision;
import trafico.utiles.importacion.importadores.ImportadorPDF;
import trafico.utiles.importacion.interfaces.IImportadorDGT;
import trafico.utiles.imprimir.ImprimirGeneral;
import trafico.utiles.imprimir.PreferenciasEtiquetas;
import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.mensajes.Claves;
import utilidades.propiedades.PropertiesConstantes;
import utilidades.validaciones.NIFValidator;
import utilidades.web.OegamExcepcion;
import utilidades.web.excepciones.XmlNoValidoExcepcion;

public class ImportarAction extends ActionBase implements SessionAware {

	private static final long serialVersionUID = 7624082117509905834L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ImportarAction.class);

	private static final String XML_VALIDO = "CORRECTO";
	private static final int BAJA_REALIZADA_CORRECTAMENTE = 0;

	private static final String MENSAJE_AVISO_IMPORTAR_MATE = "Sólo es posible importar un XML de MATW";

	// Importacion desde JSP
	private File fichero; // Fichero a importar desde la pagina JSP
	private String ficheroFileName; // nombre del fichero importado
	private String ficheroContentType; // tipo del fichero importado
	private Boolean menorTamMax; // Indica si se llega al action, ya que si se excede el tamaño máximo, el interceptor devuelve directamente input
	private String tipoFichero; // {DGT, XML, XML_MATE}
	private BigDecimal contratoImportacion = null; // contrato para importar

	// Resultado
	private ResultBean resultBean;
	private List<TramiteTraficoBean> listaInsertados;

	// Resumen
	private List<ResumenImportacion> resumen = new ArrayList<ResumenImportacion>();

	// Fichero de errores a descargar
	private InputStream ficheroErrores;

	private ParametrosPegatinaMatriculacion etiquetaParametros;

	private ModeloTrafico modeloTrafico;
	private ModeloSolicitudDatos modeloSolicitudDatos;
	private ModeloImportacionDGT modeloImportacionDGT;

	@Autowired
	private ServicioPermisos servicioPermisos;

	@Autowired
	private ServicioEEFFNuevo servicioEEFF;

	@Autowired
	private ServicioImportar servicioImportacion;

	@Autowired
	private ServicioIvtmMatriculacion servicioIvtmMatriculacion;

	@Autowired
	private GestorDocumentos gestorDocumentos;

	@Autowired
	private ServicioTramiteTrafico servicioTramiteTrafico;

	@Autowired
	private ServicioInteve servicioInteve;

	@Autowired
	private FORMATOGAMatwDtoConversion formatoMatwDtoConversion;

	@Autowired
	private FORMATOGABajaDtoConversion formatoBajaDtoConversion;

	@Autowired
	private FORMATOGADuplicadoDtoConversion formatoDuplicadoDtoConversion;

	@Autowired
	private ImportadorDGTBaja importadorDGTBaja;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	FormatoGaMatriculacionMatwPqConversion formatoGaMatriculacionMatwPqConversion;

	@Autowired
	FORMATOOEGAM2SOLICITUDConversion fORMATOOEGAM2SOLICITUDConversion;

	@Autowired
	private Utiles utiles;

	@Autowired
	private UtilesColegiado utilesColegiado;

	@Override
	public String execute() throws Exception {
		return super.execute();
	}

	public String inicio() throws OegamExcepcion {
		log.info("Cargamos vista de importarDGT");
		// Si el usuario no tiene permiso de Importar DGT, se devuelve el error

		ResultBean resultadoPreferencias = new ResultBean();
		ParametrosPegatinaMatriculacion preferenciasEtiquetas = new ParametrosPegatinaMatriculacion();

		resultadoPreferencias = PreferenciasEtiquetas.cargarPreferencias(utilesColegiado.getNumColegiadoSession());

		if (resultadoPreferencias.getError()) {
			setEtiquetaParametros(preferenciasEtiquetas);
		} else {
			etiquetaParametros = (ParametrosPegatinaMatriculacion) resultadoPreferencias.getAttachment(ConstantesTrafico.PREFERENCIAS);
		}
		return "importarDGT";
	}

	public String descargarFichero() {
		String idSession = ServletActionContext.getRequest().getSession().getId();
		try {
			FileResultBean fileResultBean = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.IMPORTACION, ConstantesGestorFicheros.DGT, utilesFecha.getFechaActual(), idSession
					+ ConstantesGestorFicheros.NOMBRE_ERRORES_DGT, ConstantesGestorFicheros.EXTENSION_DGT);
			if (FileResultStatus.ON_DEMAND_FILE.equals(fileResultBean.getStatus())) {
				addActionError(fileResultBean.getMessage());
				return "importarDGT";
			} else {
				ficheroErrores = new FileInputStream(fileResultBean.getFile());
			}
		} catch (FileNotFoundException e) {
			log.error(e);
			return "importarDGT";
		} catch (OegamExcepcion e) {
			log.error("Error al recuperar el archivo", e);
			return "importarDGT";
		}
		return "ficheroDownload";
	}

	/**
	 * Método para actualizar el resumen de la importación de trámites
	 */
	private List<ResumenImportacion> actualizaResumen(List<ResumenImportacion> resumen, int tipoTramiteIndex, int total, Boolean correcto) {
		if (correcto) {
			resumen.get(tipoTramiteIndex).addCorrecto();
			if (total != 0 && tipoTramiteIndex != total)
				resumen.get(total).addCorrecto();
		} else {
			resumen.get(tipoTramiteIndex).addIncorrecto();
			if (total != 0 && tipoTramiteIndex != total)
				resumen.get(total).addIncorrecto();
		}
		return resumen;
	}

	/**
	 * Método que recibe el fichero DGT para importarlo y gestionar sus trámites
	 * @return
	 */
	public String importarDGT() {
		setMenorTamMax(true);
		String accionRetorno = "importarDGT";
		int matriculacion_index = 0;
		int baja_index = 1;
		int transmision_index = 2;
		int otros_index = 3;
		int total_index = 4;
		boolean custodiarArchivo = true;
		Boolean tienePermisosBTV = utilesColegiado.tienePermisoBTV();
		// Contrato y usuario del que está haciendo la importación
		BigDecimal idContrato = utilesColegiado.getIdContratoSessionBigDecimal();
		BigDecimal idUsuario = utilesColegiado.getIdUsuarioSessionBigDecimal();

		resumen.add(matriculacion_index, new ResumenImportacion("Matriculación"));
		resumen.add(baja_index, new ResumenImportacion("Baja"));
		resumen.add(transmision_index, new ResumenImportacion("Transmisión"));
		resumen.add(otros_index, new ResumenImportacion("Otros"));
		resumen.add(total_index, new ResumenImportacion("TOTAL"));

		List<String> lineasError = new ArrayList<>();

		// Si no se rellena el input file se direcciona de nuevo a la pagina de
		// cargar fichero
		if (getFichero() == null || !utiles.esNombreFicheroValido(getFicheroFileName())) {
			log.error("Error al importar el fichero");
			setResultBean(new ResultBean());
			getResultBean().setError(true);
			getResultBean().setMensaje(ERROR_IMPORTAR_FICHERO);
			addActionError(ERROR_IMPORTAR_FICHERO);
			custodiarArchivo = false;
			resumen = actualizaResumen(resumen, otros_index, total_index, false);
			return "importarDGT";
		}

		// Incidencia : Mantis 1951 (Ricardo Rodriguez 15/06/2012)
		// Descripcion : Verificar permiso de importación de todo tipo de
		// ficheros
		try {
			String resultado = utilesColegiado.tienePermisoImportacion(FicherosImportacion.IMPORTAR_DGT);
			if (resultado != null && !resultado.equalsIgnoreCase(Constantes.TRUE)) {
				// El usuario no tiene permiso para importar este trámite.
				// Recupera de un properties el mensaje al usuario
				String mensajeError = FicherosImportacion.IMPORTAR_DGT.getNombreEnum();
				setResultBean(new ResultBean());
				getResultBean().setError(true);
				getResultBean().setMensaje(mensajeError);
				addActionError(mensajeError);
				custodiarArchivo = false;
				resumen = actualizaResumen(resumen, otros_index, total_index, false);
				return "importarDGT";
			}
		} catch (Exception ex) {
			log.error(ex);
			addActionError(ex.toString());
			custodiarArchivo = false;
			return Action.ERROR;
		} catch (OegamExcepcion ex) {
			log.error(ex);
			addActionError(ex.toString());
			custodiarArchivo = false;
			return Action.ERROR;
		}
		// Fin incidencia Mantis 1951

		// Leemos las lineas del fichero
		List<String> lineas;
		try {
			lineas = utiles.obtenerLineasFicheroTexto(getFichero());
		} catch (Throwable e) {
			log.error("Error al importar el fichero:" + e);
			setResultBean(new ResultBean());
			getResultBean().setError(true);
			getResultBean().setMensaje(ERROR_IMPORTAR_FICHERO);
			// e.printStackTrace();
			addActionError(ERROR_IMPORTAR_FICHERO);
			custodiarArchivo = false;
			resumen = actualizaResumen(resumen, otros_index, total_index, false);
			return "importarDGT";
		}

		// El fichero no tiene lineas de trámites
		if (!(lineas.size() > 1)) {
			log.error("El fichero no tiene líneas de trámites");
			setResultBean(new ResultBean());
			getResultBean().setError(true);
			getResultBean().setMensaje(ERROR_FICHERO_SIN_TRAMITES);
			addActionError(ERROR_FICHERO_SIN_TRAMITES);
			custodiarArchivo = false;
			resumen = actualizaResumen(resumen, otros_index, total_index, false);
			return "importarDGT";
		}

		// Recorro las lineas del fichero, obteniendo el bean que corresponda a
		// su trámite, o un bean con el error
		// que corresponda al intentar su importación o tramitación.
		// numLinea indica el numero de registro (empieza por 0). Cuidado al
		// usarlo para referirse a la linea del fichero (empieza en 1)
		int numLinea;
		String regTramite = "";
		IImportadorDGT importador;
		List<Object> beansLinea = new ArrayList<>();
		// resultados de validar las lineas
		ResultBean validaLinea;
		// resultados al generar los beans
		ResultBean tramiteBean = new ResultBean();

		// colegiado de la cabecera del fichero
		String colegiadoCabecera = "";
		for (numLinea = 0; numLinea < lineas.size(); numLinea++) {
			// Leemos de la linea el tipo de tramite (los tres primeros
			// caracteres)
			if (lineas.get(numLinea).trim().length() >= 3)
				regTramite = utiles.getSubcadena(lineas.get(numLinea), 0, TAM_REG_TRAMITE);
			else if (lineas.get(numLinea).trim().length() == 0)
				regTramite = "Linea vacia";
			else
				regTramite = "Inválido";
			// Cogemos el importador adecuado para cada tipo de trámite
			importador = getImportador(regTramite);
			// Si ha encontrado un importador, el regTramite es válido
			if (importador != null) {
				// Validamos la línea del trámite
				validaLinea = importador.validaFormato(lineas.get(numLinea), numLinea + 1);
				// Si no pasa la primera validación, devolvemos el error
				if (validaLinea.getError())
					beansLinea.add(validaLinea);
				// Si valida, obtenemos el bean del trámite y lo añadimos a la
				// lista de beans
				else {
					tramiteBean = importador.importaValores(lineas.get(numLinea), colegiadoCabecera);
					// Si el importador era de PDF (primera linea), guardamos el
					// colegiado que tiene, para hacer comprobaciones en el
					// resto de trámites.
					if (importador instanceof ImportadorPDF) {
						colegiadoCabecera = ((ImportacionDGTPDFBean) tramiteBean.getAttachment(BEAN_PDF)).getNumColegiado();
						lineasError.add(lineas.get(numLinea));
					}
					// Si ha dado error...
					if (tramiteBean.getError()) {
						beansLinea.add(tramiteBean);
					} else { // Si se ha importado bien, recogemos el bean del resultado
						beansLinea.add(tramiteBean.getAttachment(importador.getKeyBean()));
					}
				}
			} else { // Si no hay importador para el trámite
				tramiteBean = new ResultBean();
				tramiteBean.setError(true);
				String mensaje = "El tipo de registro del trámite (" + regTramite + "), no es procesable";
				tramiteBean.setMensaje(mensaje);
				beansLinea.add(tramiteBean);
			}
		}
		boolean lineaTMatriculacion = true;
		List<ResultBean> resultadoProcesar = new ArrayList<>();
		// Recorremos los beans obtenidos de las lineas. Si no son un
		// resultBean, los tramitamos al paquete
		for (numLinea = 0; numLinea < beansLinea.size(); numLinea++) {
			// Si es un resultBean, ha habido algún error, y lo guardamos
			// directamente
			if (beansLinea.get(numLinea) instanceof ResultBean) {
				resultadoProcesar.add((ResultBean) beansLinea.get(numLinea));
				if (((ResultBean) beansLinea.get(numLinea)).getError()) {
					String reg = utiles.getSubcadena(lineas.get(numLinea), 0, TAM_REG_TRAMITE);
					addActionError("-Línea " + (numLinea + 1) + " (" + dimeTramite(reg) + "): " + ((ResultBean) beansLinea.get(numLinea)).getMensaje());
					custodiarArchivo = false;
					lineasError.add(lineas.get(numLinea));
					if ("020".equals(reg))
						resumen = actualizaResumen(resumen, baja_index, total_index, false);
					else if ("030".equals(reg))
						resumen = actualizaResumen(resumen, transmision_index, total_index, false);
					else if ("040".equals(reg))
						resumen = actualizaResumen(resumen, matriculacion_index, total_index, false);
					else
						resumen = actualizaResumen(resumen, otros_index, total_index, false);
				}
			}
			// Si no es un ResultBean, es un bean importado. Puede ser de varios
			// tipos según el trámite gestionado (matriculación, baja...)
			else {
				// Bean de Matriculación
				if (beansLinea.get(numLinea) instanceof BeanPQAltaMatriculacionImport) {
					log.error("Desde la actualización a MateW se deja de dar soporte a los ficheros .dgt de matriculación.");
					setResultBean(new ResultBean());
					getResultBean().setError(true);
					resumen = actualizaResumen(resumen, matriculacion_index, total_index, false);
					if (lineaTMatriculacion) {
						lineaTMatriculacion = false;
						getResultBean().setMensaje("Desda la actualización del proceso de matriculación por parte de la DGT en Octubre de 2013 no está permitida la "
								+ "importación de trámites de matriculación en ficheros del tipo .dgt.");
						addActionError("Desda la actualización del proceso de matriculación por parte de la DGT en Octubre de 2013 no está permitida la "
								+ "importación de trámites de matriculación en ficheros del tipo .dgt.");
						custodiarArchivo = false;
					}
				}
				// Bean de Baja
				else if (beansLinea.get(numLinea) instanceof TramiteTrafBajaDto) {
					TramiteTrafBajaDto beanAlta = (TramiteTrafBajaDto) beansLinea.get(numLinea);
					beanAlta.setIdTipoCreacion(new BigDecimal(TipoCreacion.DGT.getValorEnum()));
					ResultBean resultado = servicioImportacion.importarBaja(beanAlta, idUsuario, tienePermisosBTV, Boolean.FALSE);
					if (resultado != null && resultado.getError()) {
						String mensajes = "";
						for (String mensaje : resultado.getListaMensajes()) {
							mensajes = mensajes + mensaje + " ";
						}
						addActionError("-Línea " + (numLinea + 1) + " (Baja): " + mensajes);
						custodiarArchivo = false;
						lineasError.add(lineas.get(numLinea));
						resumen = actualizaResumen(resumen, baja_index, total_index, false);
					} else {
						addActionMessage("-Línea " + (numLinea + 1) + " (" + dimeTramite(utiles.getSubcadena(lineas.get(numLinea), 0, TAM_REG_TRAMITE)) + "): " + resultado.getMensaje());
						custodiarArchivo = false;
						resumen = actualizaResumen(resumen, baja_index, total_index, true);
					}
				}
				// Bean de Transmision
				else if (beansLinea.get(numLinea) instanceof BeanPQTramiteTransmisionImport) {
					String resultadoPermisoT2 = Constantes.TRUE;
					try {
						resultadoPermisoT2 = utilesColegiado.tienePermisoImportacion(FicherosImportacion.IMPORTAR_DGT_TRANSMISION_ACTUAL);
						if (resultadoPermisoT2 != null && !resultadoPermisoT2.equalsIgnoreCase(Constantes.TRUE)) {
							// El usuario no tiene permiso para importar este
							// trámite.
							// Recupera de un properties el mensaje al usuario
							String mensajeError = FicherosImportacion.IMPORTAR_DGT_TRANSMISION_ACTUAL.getNombreEnum();
							setResultBean(new ResultBean());
							getResultBean().setError(true);
							getResultBean().setMensaje(mensajeError);
							String matricula = ((BeanPQTramiteTransmisionImport) beansLinea.get(numLinea)).getBeanGuardarVehiculo().getP_MATRICULA();
							addActionError("-Línea " + (numLinea + 1) + " (Transmision): " + mensajeError + " para el vehiculo con matricula: " + matricula);
							custodiarArchivo = false;
							lineasError.add(lineas.get(numLinea));
							resumen = actualizaResumen(resumen, otros_index, total_index, false);
						}
					} catch (Exception ex) {
						log.error(ex);
						addActionError(ex.toString());
						custodiarArchivo = false;
						return Action.ERROR;
					} catch (OegamExcepcion ex) {
						log.error(ex);
						addActionError(ex.toString());
						custodiarArchivo = false;
						return Action.ERROR;
					}
					// Arriba se comprueba el permiso. Si no tiene se inserta en
					// la lista de errores. Pero aquí hay que volver a
					// comprobarlo para saber si hay que guardar
					// el trámite o no.
					if (resultadoPermisoT2 != null && resultadoPermisoT2.equalsIgnoreCase(Constantes.TRUE)) {
						HashMap<String, Object> resultado;
						try {
							resultado = getModeloImportacionDGT().guardarTransmisionImport((BeanPQTramiteTransmisionImport) beansLinea.get(numLinea), getContratoImportacion(), idUsuario, idContrato,
									TipoCreacion.DGT);
							if (((ResultBean) resultado.get(ConstantesPQ.RESULTBEAN)).getError()) {
								addActionError("-Línea " + (numLinea + 1) + " (Transmision): " + ((ResultBean) resultado.get("ResultBean")).getMensaje());
								custodiarArchivo = false;
								lineasError.add(lineas.get(numLinea));
								resumen = actualizaResumen(resumen, transmision_index, total_index, false);
							} else {
								addActionMessage("-Línea " + (numLinea + 1) + " (" + dimeTramite(utiles.getSubcadena(lineas.get(numLinea), 0, TAM_REG_TRAMITE)) + "): "
										+ ((ResultBean) resultado.get("ResultBean")).getMensaje());
								custodiarArchivo = false;
								resumen = actualizaResumen(resumen, transmision_index, total_index, true);
							}
						} catch (OegamExcepcion e) {
							log.error(e);
							addActionError("Ha habido un error al guardar en BBDD");
							custodiarArchivo = false;
							resumen = actualizaResumen(resumen, otros_index, total_index, false);
						}
					}
				}
				// Otros
				// beans.....*****************************************************
			}
		}
		//String idSession = ServletActionContext.getRequest().getSession().getId();

		if (custodiarArchivo) {
			// Gestor de ficheros
			// Mantis 24181 Custodiar los documentos .DGT
			FicheroBean fichero = new FicheroBean();
			fichero.setTipoDocumento(ConstantesGestorFicheros.IMPORTACION);
			fichero.setSubTipo(ConstantesGestorFicheros.DGT);
			fichero.setExtension(ConstantesGestorFicheros.EXTENSION_DGT);
			Fecha f = new Fecha(new Date());
			fichero.setFecha(f);

			String fecha = f.getDia() + "_" + f.getHora() + f.getMinutos() + f.getSegundos();
			fichero.setNombreDocumento(idUsuario + "_" + idContrato + "_" + fecha);
			fichero.setSobreescribir(true);

			gestorDocumentos.guardaFicheroEnviandoStrings(fichero, lineas);
			log.info("Fichero .DGT guardado en TDOC");
		}

		return accionRetorno;
	}

	private IImportadorDGT getImportador(String regTramite) {
		if (REGISTRO_MATRICULACION.equals(regTramite))
			return new ImportadorDGTMatriculacion();
		else if (REGISTRO_PDF.equals(regTramite))
			return new ImportadorPDF();
		else if (REGISTRO_BAJA.equals(regTramite))
			return importadorDGTBaja;
		else if (REGISTRO_TRANSMISION.equals(regTramite))
			return new ImportadorDGTTransmision();
		else
			return null;
	}

	/**
	 * Metodo que recibe el fichero DGT para importarlo y gestionar sus trámites
	 * @return
	 * @throws OegamExcepcion
	 */
	public String importarPegatinas() throws OegamExcepcion {
		int matriculacion_index = 0;
		int baja_index = 1;
		int transmision_index = 2;
		int otros_index = 3;
		int total_index = 4;

		resumen.add(matriculacion_index, new ResumenImportacion("Matriculación"));
		resumen.add(baja_index, new ResumenImportacion("Baja"));
		resumen.add(transmision_index, new ResumenImportacion("Transmisión"));
		resumen.add(otros_index, new ResumenImportacion("Otros"));
		resumen.add(total_index, new ResumenImportacion("TOTAL"));

		String accionRetorno = "ficheroPegatinasDownload";
		PreferenciasEtiquetas.guardarPreferencias(etiquetaParametros, utilesColegiado.getNumColegiadoSession());

		// Si no se rellena el input file se direcciona de nuevo a la pagina de
		// cargar fichero
		if (getFichero() == null || !utiles.esNombreFicheroValido(getFicheroFileName())) {
			log.error("Error al importar el fichero");
			setResultBean(new ResultBean());
			getResultBean().setError(true);
			getResultBean().setMensaje(ERROR_IMPORTAR_FICHERO);
			addActionError(ERROR_IMPORTAR_FICHERO);

			resumen = actualizaResumen(resumen, otros_index, total_index, false);
			return "importarDGT";
		}

		// Incidencia : Mantis 1951 (Ricardo Rodriguez 15/06/2012)
		// Descripcion : Verificar permiso de importación de todo tipo de
		// ficheros
		try {
			String resultado = utilesColegiado.tienePermisoImportacion(FicherosImportacion.IMPORTAR_PEGATINAS);
			if (resultado != null && !resultado.equalsIgnoreCase(Constantes.TRUE)) {
				// El usuario no tiene permiso para importar este trámite.
				// Recupera de un properties el mensaje al usuario
				String mensajeError = FicherosImportacion.IMPORTAR_PEGATINAS.getNombreEnum();
				setResultBean(new ResultBean());
				getResultBean().setError(true);
				getResultBean().setMensaje(mensajeError);
				addActionError(mensajeError);
				resumen = actualizaResumen(resumen, otros_index, total_index, false);
				return "importarDGT";
			}
		} catch (Exception ex) {
			log.error(ex);
			addActionError(ex.toString());
			return Action.ERROR;
		} catch (OegamExcepcion ex) {
			log.error(ex);
			addActionError(ex.toString());
			return Action.ERROR;
		}
		// Fin incidencia Mantis 1951

		// Leemos las lineas del fichero
		List<String> lineas;
		try {
			lineas = utiles.obtenerLineasFicheroTexto(getFichero());
		} catch (Throwable e) {
			log.error("Error al importar el fichero:" + e);
			setResultBean(new ResultBean());
			getResultBean().setError(true);
			getResultBean().setMensaje(ERROR_IMPORTAR_FICHERO);
			addActionError(ERROR_IMPORTAR_FICHERO);
			resumen = actualizaResumen(resumen, otros_index, total_index, false);
			return "importarDGT";
		}

		// numLinea indica el numero de registro (empieza por 0). Cuidado al
		// usarlo para referirse a la linea del fichero (empieza en 1)
		int numLinea;

		String[] listaMatriculaBastidor = new String[lineas.size()];

		for (numLinea = 0; numLinea < lineas.size(); numLinea++) {
			// Leemos todas las líneas que serían MatriculaBastidor
			String linea = lineas.get(numLinea).trim();
			if (!linea.contains(";")) {
				log.error("Error generando el PDF de Etiquetas");
				setResultBean(new ResultBean());
				getResultBean().setError(true);
				getResultBean().setMensaje(ERROR_FORMATO_FICHERO);
				addActionError(ERROR_FORMATO_FICHERO);
				resumen = actualizaResumen(resumen, otros_index, total_index, false);
				return "importarDGT";
			}
			listaMatriculaBastidor[numLinea] = linea;
		}

		try {
			ImprimirGeneral imprimirGeneral = new ImprimirGeneral();
			ficheroErrores = new ByteArrayInputStream(imprimirGeneral.generarPdfEtiquetas(etiquetaParametros, listaMatriculaBastidor));
		} catch (Exception e) {
			log.error("Error generando el PDF de Etiquetas");
			setResultBean(new ResultBean());
			getResultBean().setError(true);
			getResultBean().setMensaje(ERROR_FORMATO_FICHERO);
			addActionError(ERROR_FORMATO_FICHERO);
			resumen = actualizaResumen(resumen, otros_index, total_index, false);
			return "importarDGT";
		}

		return accionRetorno;
	}

	/**
	 * Método que valida un XML contra MATE (ga.xsd)
	 * @param fichero xml a validar
	 * @return Devolverá XML_CORRECTO si es válido, en cualquier otro caso devolverá el string de la excepción de validación
	 */
	public String validarXMLMATE(File fich) {
		// Constantes para validación de Schemas
		final String W3C_XML_SCHEMA = "http://www.w3.org/2001/XMLSchema";
		URL esquema = this.getClass().getResource("/trafico/schemas/ga.xsd");
		final String MY_SCHEMA = esquema.getFile();

		try {
			// get validation driver:
			SchemaFactory factory = SchemaFactory.newInstance(W3C_XML_SCHEMA);
			// Creamos el schema leyéndolo desde el XSD
			Schema schema = factory.newSchema(new StreamSource(MY_SCHEMA));
			Validator validator = schema.newValidator();
			XMLManejadorErrores errores = new XMLManejadorErrores();
			validator.setErrorHandler(errores);
			// validamos el XML, si no lo valida devolverá el string de la
			// excepción, si lo valida devolverá 'CORRECTO'
			validator.validate(new StreamSource(fich));
			if (!errores.getListaErrores().isEmpty()) {
				String mensaje = "";
				for (int i = 0; i < errores.getListaErrores().size(); i++) {
					if (i < errores.getListaErrores().size() - 1)
						mensaje += errores.getListaErrores().get(i) + ". ";
					else
						mensaje += errores.getListaErrores().get(i);
				}
				return mensaje;
			}
		} catch (SAXParseException e) {
			log.error(e);
			String error = "";
			error += "Error en la validación del archivo XML:";
			error += " -Linea: " + e.getLineNumber();
			error += " -Columna: " + e.getColumnNumber();
			error += " -Mensaje: " + e.getMessage();
			return error;
		} catch (SAXException saxEx) {
			log.error(saxEx);
			return saxEx.toString();
		} catch (Exception ex) {
			log.error(ex);
			return ex.toString();
		}
		return XML_VALIDO;
	}

	/**
	 * Importación de fichero DGT con formato XML para MATEGE
	 * @return
	 */
	public String importarMATEGEMatw() throws OegamExcepcion {
		String accionRetorno = "importarDGT";
		int matriculacion_index = 0;
		int otros_index = 1;
		int total_index = 2;

		BigDecimal idContrato = utilesColegiado.getIdContratoSessionBigDecimal();
		BigDecimal idUsuario = utilesColegiado.getIdUsuarioSessionBigDecimal();

		resumen.add(matriculacion_index, new ResumenImportacion("Matriculacion"));
		resumen.add(otros_index, new ResumenImportacion("Otros"));
		resumen.add(total_index, new ResumenImportacion("TOTAL"));

		// Si no se rellena el input file se direcciona de nuevo a la página de
		// cargar fichero
		if (getFichero() == null || !utiles.esNombreFicheroValido(getFicheroFileName())) {
			log.error("Error al importar el fichero");
			setResultBean(new ResultBean());
			getResultBean().setError(true);
			getResultBean().setMensaje(ERROR_IMPORTAR_FICHERO);
			addActionError(ERROR_IMPORTAR_FICHERO);
			resumen = actualizaResumen(resumen, otros_index, total_index, false);
			return "importarDGT";
		}

		// Incidencia : Mantis 1951 (Ricardo Rodriguez 15/06/2012)
		// Descripcion : Verificar permiso de importación de todo tipo de
		// ficheros
		try {
			String resultado = utilesColegiado.tienePermisoImportacion(FicherosImportacion.IMPORTAR_XML_MATRICULACION_MATW);
			if (resultado != null && !resultado.equalsIgnoreCase(Constantes.TRUE)) {
				// El usuario no tiene permiso para importar este trámite.
				// Recupera de un properties el mensaje al usuario
				String mensajeError = FicherosImportacion.IMPORTAR_XML_MATRICULACION_MATW.getNombreEnum();
				setResultBean(new ResultBean());
				getResultBean().setError(true);
				getResultBean().setMensaje(mensajeError);
				addActionError(mensajeError);
				resumen = actualizaResumen(resumen, otros_index, total_index, false);
				return "importarDGT";
			}
		} catch (Exception ex) {
			log.error(ex.toString());
			addActionError(ex.toString());
			return Action.ERROR;
		} catch (OegamExcepcion ex) {
			log.error(ex.toString());
			addActionError(ex.toString());
			return Action.ERROR;
		}
		// Fin incidencia Mantis 1951

		File ficheroMatriculacion = getFichero();

		ResultBean resultadoImportacion = new ResultBean();

		// String habilitarMatriculacion = gestorPropiedades.valorPropertie("habilitar.matriculacion.nueva");
		String habilitarImportacionMatriculacion = gestorPropiedades.valorPropertie("habilitar.importacion.matriculacion.nueva");

		if (habilitarImportacionMatriculacion != null && "SI".equals(habilitarImportacionMatriculacion)) {
			resultadoImportacion = importarXMLMategeMatriculacionMatwNuevo(idUsuario, idContrato, ficheroMatriculacion);
		} else {
			resultadoImportacion = importarXMLMategeMatriculacionMatw(idUsuario, idContrato, ficheroMatriculacion);
		}

		if (!resultadoImportacion.getError()) {
			// Gestor de Ficheros
			FicheroBean fichero = new FicheroBean();
			fichero.setFichero(ficheroMatriculacion);
			fichero.setTipoDocumento(ConstantesGestorFicheros.IMPORTACION);
			fichero.setSubTipo(ConstantesGestorFicheros.SUBTIPO_DOC_MATRICULACION);
			fichero.setExtension(ConstantesGestorFicheros.EXTENSION_XML);
			Fecha f = new Fecha(new Date());
			fichero.setFecha(f);
			String fecha = f.getDia() + "_" + f.getHora() + f.getMinutos() + f.getSegundos();
			fichero.setNombreDocumento(idUsuario + "_" + idContrato + "_" + fecha);
			try {
				gestorDocumentos.guardarFichero(fichero);
				log.info("Fichero guardado en TDOC");
			} catch (OegamExcepcion e) {
				log.error("Error al custodiar el importado XML de Matw: " + idUsuario + "_" + idContrato + "_" + fecha);
			}
		}

		return accionRetorno;
	}

	/**
	 * Método que procesa un XML (validado de MATEGE)
	 * @return
	 */
	public ResultBean importarXMLMategeMatriculacionMatw(BigDecimal idUsuario, BigDecimal idContrato, File ficheroMatriculacion) {
		// String accionRetorno = "importarDGT";
		ResultBean resultadoImportacion = new ResultBean();
		int matriculacion_index = 0;
		int otros_index = 1;
		int total_index = 2;
		String mensajeErrorEstandar = null;

		try {
			mensajeErrorEstandar = ConstantesMensajes.MENSAJE_ERROR_IMPORTACION_MATRICULACION_ESTANDAR;

			// Valida el fichero contra el esquema:
			UtilesXML.validarXML(ficheroMatriculacion, gestorPropiedades.valorPropertie(Constantes.PROPERTIES_RUTA_ESQUEMA_MATRICULACION_MATW));

			Unmarshaller unmarshaller = new XMLGAFactory().getMatriculacionMatWContext().createUnmarshaller();
			// Parseamos el fichero XML al bean raiz generado por xjc.exe
			trafico.beans.jaxb.matw.FORMATOGA ga = (trafico.beans.jaxb.matw.FORMATOGA) unmarshaller.unmarshal(ficheroMatriculacion);

			// Mantis 13118. David Sierra: Mensaje de aviso para importacion de XML de Matriculacion antigua
			for (trafico.beans.jaxb.matw.FORMATOGA.MATRICULACION tramite : ga.getMATRICULACION()) {
				if ((tramite.getVERSIONMATE() == null) || tramite.getVERSIONMATE() != null && !tramite.getVERSIONMATE().equals("MATW")) {
					addActionError(MENSAJE_AVISO_IMPORTAR_MATE);
					resultadoImportacion.setError(true);
					resumen = actualizaResumen(resumen, otros_index, total_index, false);
					return resultadoImportacion;
				}
			}
			if (ga.getMATRICULACION() == null || ga.getMATRICULACION().isEmpty()) {
				addActionError(mensajeErrorEstandar);
				resultadoImportacion.setError(true);
				addActionError("El xml no tiene trámites de matriculacion");
				resumen = actualizaResumen(resumen, otros_index, total_index, false);
				return resultadoImportacion;
			}

			// Si el campo OEGAM_DGT tiene valor NO deja importar el trámite:
			if (ga.getCABECERA() != null && ga.getCABECERA().getDATOSGESTORIA() != null) {
				if (ga.getCABECERA().getDATOSGESTORIA().getOEGAMDGT() != null && !ga.getCABECERA().getDATOSGESTORIA().getOEGAMDGT().equals("")) {
					addActionError(mensajeErrorEstandar + ". La tag OEGAMDGT se encuentra en el XML.");
					resultadoImportacion.setError(true);
					resumen = actualizaResumen(resumen, otros_index, total_index, false);
					return resultadoImportacion;
				}
			}

			// Elimino los campos que no se van a importar
			for (trafico.beans.jaxb.matw.FORMATOGA.MATRICULACION tramite : ga.getMATRICULACION()) {
				if (tramite.getDATOSIMPUESTOS() != null && tramite.getDATOSIMPUESTOS().getDATOSIMVTM() != null) {
					DATOSIMVTM datos = tramite.getDATOSIMPUESTOS().getDATOSIMVTM();
					datos.setCODIGOGESTOR(null);
					datos.setDIGITOCONTROL(null);
					datos.setEMISOR(null);
					datos.setIMPORTEANUAL(null);
					datos.setCODIGORESPUESTAPAGO(null);
				}
			}

			// List<BeanPQAltaMatriculacionImport> beansAlta =
			// FORMATOGAlistaPQConversion.convertirFORMATOGAtoPQ(ga, idUsuario,
			// idContrato);
			List<BeanPQAltaMatriculacionImport> beansAlta = formatoGaMatriculacionMatwPqConversion.convertirFORMATOGAtoPQ(ga, idUsuario, idContrato);

			// Si no hay beans de alta. Error
			if (beansAlta == null || (beansAlta != null && beansAlta.isEmpty())) {
				addActionError(mensajeErrorEstandar);
				resultadoImportacion.setError(true);
				resumen = actualizaResumen(resumen, otros_index, total_index, false);
				return resultadoImportacion;
			} else if (utilesColegiado.tienePermisoImportacionMasiva() && importacionMasiva(beansAlta.size())) {
				importacionEnProceso(ficheroMatriculacion, utilesColegiado.getNumColegiadoSession(), idContrato, contratoImportacion, ConstantesSession.XML_MATRICULACION_MATW);
				addActionError("Su solicitud se está procesando. Estará disponible en unos minutos. Recibirá una notificación de la misma.");
				resultadoImportacion.setError(true);
				resumen = actualizaResumen(resumen, otros_index, total_index, false);
				return resultadoImportacion;
			}

			// Si el colegiado de sesión tiene permiso de administración:
			// ---> se procesa el trámite
			if (utilesColegiado.tienePermisoAdmin()) {
				for (int i = 0; i < beansAlta.size(); i++) {
					if (beansAlta.get(i) != null && beansAlta.get(i).getBeanGuardarVehiculo() != null && beansAlta.get(i).getBeanGuardarVehiculo().getP_IMPORTADO() != null && beansAlta.get(i)
							.getBeanGuardarVehiculo().getP_IMPORTADO().equalsIgnoreCase("S") && (beansAlta.get(i).getBeanGuardarVehiculo().getP_VEHI_USADO() == null || beansAlta.get(i)
									.getBeanGuardarVehiculo().getP_VEHI_USADO() != null && beansAlta.get(i).getBeanGuardarVehiculo().getP_VEHI_USADO().equalsIgnoreCase("N"))) {
						addActionError(mensajeErrorEstandar);
						resultadoImportacion.setError(true);
						addActionError("Cuando un vehículo se marca como importado obligatoriamente tiene que ser marcado como usado.");
						resumen = actualizaResumen(resumen, matriculacion_index, total_index, false);
					} else {
						procesarGA(ga, beansAlta.get(i), mensajeErrorEstandar, matriculacion_index, total_index, i, idUsuario, idContrato);
					}
				}
			}
			// Si el colegiado de sesión tiene permiso de colegiado:
			// ---> Se comprueba que el número de colegiado del archivo
			// pertenece al contrato del colegio del colegiado de sesión
			// ---> se procesa en caso afirmativo
			else if (utilesColegiado.tienePermisoColegio()) {
				List<String> listaColegiados = utilesColegiado.getNumColegiadosDelContrato();
				Boolean puede = false;
				for (int i = 0; i < beansAlta.size(); i++) {
					int j = 0;
					while ((!puede) && (listaColegiados.size() > j)) {
						puede = puede || beansAlta.get(i).getBeanGuardarMatriculacion().getP_NUM_COLEGIADO().equals(listaColegiados.get(j));
						j++;
					}
					if (puede) {
						procesarGA(ga, beansAlta.get(i), mensajeErrorEstandar, matriculacion_index, total_index, i, idUsuario, idContrato);
					} else {
						addActionError(mensajeErrorEstandar);
						resultadoImportacion.setError(true);
						String mensajeError = "-Tramite " + i + " : El usuario (" + utilesColegiado.getNumColegiadoSession()
								+ ") no tiene permisos para realizar la importación para el colegiado del fichero (" + beansAlta.get(i).getBeanGuardarMatriculacion().getP_NUM_COLEGIADO() + ")";
						addActionError(mensajeError);
						resumen = actualizaResumen(resumen, matriculacion_index, total_index, false);
						return resultadoImportacion;
					}
					puede = false;
				}
			}
			// Si el usuario de sesión es un colegiado cualquiera
			else {
				// Si es el mismo el de sesión que el de cabecera
				for (int i = 0; i < beansAlta.size(); i++) {
					if (beansAlta.get(i).getBeanGuardarMatriculacion().getP_NUM_COLEGIADO().equals(utilesColegiado.getNumColegiadoSession())) {
						procesarGA(ga, beansAlta.get(i), mensajeErrorEstandar, matriculacion_index, total_index, i, idUsuario, idContrato);
					} else {
						addActionError(mensajeErrorEstandar);
						resultadoImportacion.setError(true);
						String mensajeError = "-Tramite " + i + " : El usuario (" + utilesColegiado.getNumColegiadoSession() + ") no es el mismo colegiado que el del trámite del fichero (" + beansAlta.get(i)
								.getBeanGuardarMatriculacion().getP_NUM_COLEGIADO() + ")";
						resumen = actualizaResumen(resumen, matriculacion_index, total_index, false);
						addActionError(mensajeError);
					}
				}
			}
		} catch (JAXBException e) {
			log.error(e);
			resumen = actualizaResumen(resumen, otros_index, total_index, false);
			addActionError(mensajeErrorEstandar);
			resultadoImportacion.setError(true);
			addActionError(e.toString());
		} catch (Exception e) {
			log.error("Ha habido un error " + e.toString());
			resumen = actualizaResumen(resumen, otros_index, total_index, false);
			addActionError(mensajeErrorEstandar);
			resultadoImportacion.setError(true);
			addActionError(e.toString());
		} catch (XmlNoValidoExcepcion e) {
			log.error("Ha habido un error al parsear el XML de Matw " + e.toString().substring(49));
			resumen = actualizaResumen(resumen, otros_index, total_index, false);
			addActionError(mensajeErrorEstandar);
			resultadoImportacion.setError(true);
			addActionError(e.toString());
		}
		return resultadoImportacion;
	}

	/**
	 * Método que procesa un XML (validado de MATEGE)
	 * @return
	 */
	public ResultBean importarXMLMategeMatriculacionMatwNuevo(BigDecimal idUsuario, BigDecimal idContrato, File ficheroMatriculacion) throws OegamExcepcion {
		ResultBean resultadoImportacion = new ResultBean();
		int matriculacion_index = 0;
		int otros_index = 1;
		int total_index = 2;
		String mensajeErrorEstandar = ConstantesMensajes.MENSAJE_ERROR_IMPORTACION_MATRICULACION_ESTANDAR;
		Boolean esAdmin = utilesColegiado.tienePermisoAdmin();
		try {
			UtilesXML.validarXML(ficheroMatriculacion, gestorPropiedades.valorPropertie(Constantes.PROPERTIES_RUTA_ESQUEMA_MATRICULACION_MATW));
			// Lectura del XML. Carga los datos en ga.
			Unmarshaller unmarshaller = new XMLGAFactory().getMatriculacionMatWContext().createUnmarshaller();
			trafico.beans.jaxb.matw.FORMATOGA ga = (trafico.beans.jaxb.matw.FORMATOGA) unmarshaller.unmarshal(ficheroMatriculacion);

			// Mantis 13118. David Sierra: Mensaje de aviso para importacion de XML de Matriculacion antigua
			for (trafico.beans.jaxb.matw.FORMATOGA.MATRICULACION tramite : ga.getMATRICULACION()) {
				if ((tramite.getVERSIONMATE() == null) || tramite.getVERSIONMATE() != null && !tramite.getVERSIONMATE().equals("MATW")) {
					addActionError(MENSAJE_AVISO_IMPORTAR_MATE);
					resultadoImportacion.setError(true);
					resumen = actualizaResumen(resumen, otros_index, total_index, false);
					return resultadoImportacion;
				}
			}

			if (ga.getMATRICULACION() == null || ga.getMATRICULACION().isEmpty()) {
				addActionError(mensajeErrorEstandar);
				resultadoImportacion.setError(true);
				addActionError("El xml no tiene trámites de matriculacion");
				resumen = actualizaResumen(resumen, otros_index, total_index, false);
				return resultadoImportacion;
			}

			// Si el campo OEGAM_DGT tiene valor NO deja importar el trámite:
			if (ga.getCABECERA() != null && ga.getCABECERA().getDATOSGESTORIA() != null) {
				if (ga.getCABECERA().getDATOSGESTORIA().getOEGAMDGT() != null && !ga.getCABECERA().getDATOSGESTORIA().getOEGAMDGT().equals("")) {
					addActionError(mensajeErrorEstandar + ". La tag OEGAMDGT se encuentra en el XML.");
					resultadoImportacion.setError(true);
					resumen = actualizaResumen(resumen, otros_index, total_index, false);
					return resultadoImportacion;
				}
			}

			// Elimino los campos que no se van a importar
			for (trafico.beans.jaxb.matw.FORMATOGA.MATRICULACION tramite : ga.getMATRICULACION()) {
				if (tramite.getDATOSIMPUESTOS() != null && tramite.getDATOSIMPUESTOS().getDATOSIMVTM() != null) {
					DATOSIMVTM datos = tramite.getDATOSIMPUESTOS().getDATOSIMVTM();
					datos.setCODIGOGESTOR(null);
					datos.setDIGITOCONTROL(null);
					datos.setEMISOR(null);
					datos.setIMPORTEANUAL(null);
					datos.setCODIGORESPUESTAPAGO(null);
				}
			}

			Boolean tienePermisoLiberarEEFF = Boolean.FALSE;
			if (utilesColegiado.tienePermisoLiberarEEFF()) {
				tienePermisoLiberarEEFF = servicioPermisos.tienePermisoElContrato(getContratoImportacion() != null ? getContratoImportacion().longValue() : idContrato.longValue(),
						ConstantesEEFF.CODIGO_PERMISO_BBDD_LIBERAR_EEFF, UtilesColegiado.APLICACION_OEGAM_TRAF);
			}

			// Coge los datos de titular, representanteTitular, conductorHabitual, arrendatario, representanteArrendatario
			// Los mueve de ga a listaMatriculacion
			List<TramiteTrafMatrDto> listaMatriculacion = formatoMatwDtoConversion.convertirFORMATOGAtoPQ(ga, idContrato, contratoImportacion, tienePermisoLiberarEEFF);

			// Si no hay beans de alta. Error
			if (listaMatriculacion == null || listaMatriculacion.isEmpty()) {
				addActionError(mensajeErrorEstandar);
				resultadoImportacion.setError(true);
				resumen = actualizaResumen(resumen, otros_index, total_index, false);
				return resultadoImportacion;
			} else if (utilesColegiado.tienePermisoImportacionMasiva() && importacionMasiva(listaMatriculacion.size())) {
				importacionEnProceso(ficheroMatriculacion, utilesColegiado.getNumColegiadoSession(), idContrato, contratoImportacion, ConstantesSession.XML_MATRICULACION_MATW);
				addActionError("Su solicitud se está procesando. Estará disponible en unos minutos. Recibirá una notificación de la misma.");
				resultadoImportacion.setError(true);
				resumen = actualizaResumen(resumen, otros_index, total_index, false);
				return resultadoImportacion;
			}

			// Si el colegiado de sesión tiene permiso de administración: se procesa el trámite
			if (esAdmin) {
				for (int i = 0; i < listaMatriculacion.size(); i++) {
					if (listaMatriculacion.get(i) != null && listaMatriculacion.get(i).getVehiculoDto() != null && listaMatriculacion.get(i).getVehiculoDto().getImportado() != null
							&& listaMatriculacion.get(i).getVehiculoDto().getImportado() && (listaMatriculacion.get(i).getVehiculoDto().getVehiUsado() == null || !listaMatriculacion.get(i)
									.getVehiculoDto().getVehiUsado())) {
						addActionError(mensajeErrorEstandar);
						resultadoImportacion.setError(true);
						addActionError("Cuando un vehículo se marca como importado obligatoriamente tiene que ser marcado como usado.");
						resumen = actualizaResumen(resumen, matriculacion_index, total_index, false);
					} else {
						resultadoImportacion = procesarGANuevo(ga, listaMatriculacion.get(i), mensajeErrorEstandar, matriculacion_index, total_index, i, idUsuario, idContrato, esAdmin,
								tienePermisoLiberarEEFF);
					}
				}
			}
			// Si el colegiado de sesión tiene permiso de colegiado: Se comprueba que el número de colegiado del archivo
			// pertenece al contrato del colegio del colegiado de sesión
			else if (utilesColegiado.tienePermisoColegio()) {
				List<String> listaColegiados = utilesColegiado.getNumColegiadosDelContrato();
				Boolean puede = false;
				for (int i = 0; i < listaMatriculacion.size(); i++) {
					int j = 0;
					while ((!puede) && (listaColegiados.size() > j)) {
						puede = puede || listaMatriculacion.get(i).getNumColegiado().equals(listaColegiados.get(j));
						j++;
					}
					if (puede) {
						resultadoImportacion = procesarGANuevo(ga, listaMatriculacion.get(i), mensajeErrorEstandar, matriculacion_index, total_index, i, idUsuario, idContrato, esAdmin,
								tienePermisoLiberarEEFF);
					} else {
						addActionError(mensajeErrorEstandar);
						resultadoImportacion.setError(true);
						String mensajeError = "-Tramite " + i + " : El usuario (" + utilesColegiado.getNumColegiadoSession()
								+ ") no tiene permisos para realizar la importación para el colegiado del fichero (" + listaMatriculacion.get(i).getNumColegiado() + ")";
						addActionError(mensajeError);
						resumen = actualizaResumen(resumen, matriculacion_index, total_index, false);
						return resultadoImportacion;
					}
					puede = false;
				}
			}
			// Si el usuario de sesión es un colegiado cualquiera
			else {
				// Si es el mismo el de sesión que el de cabecera
				for (int i = 0; i < listaMatriculacion.size(); i++) {
					if (listaMatriculacion.get(i).getNumColegiado().equals(utilesColegiado.getNumColegiadoSession())) {
						resultadoImportacion = procesarGANuevo(ga, listaMatriculacion.get(i), mensajeErrorEstandar, matriculacion_index, total_index, i, idUsuario, idContrato, esAdmin,
								tienePermisoLiberarEEFF);
					} else {
						addActionError(mensajeErrorEstandar);
						resultadoImportacion.setError(true);
						String mensajeError = "-Tramite " + i + " : El usuario (" + utilesColegiado.getNumColegiadoSession() + ") no es el mismo colegiado que el del trámite del fichero ("
								+ listaMatriculacion.get(i).getNumColegiado() + ")";
						resumen = actualizaResumen(resumen, matriculacion_index, total_index, false);
						addActionError(mensajeError);
					}
				}
			}
		} catch (JAXBException e) {
			log.error(e);
			resumen = actualizaResumen(resumen, otros_index, total_index, false);
			addActionError(mensajeErrorEstandar);
			resultadoImportacion.setError(true);
			addActionError(e.toString());
		} catch (Exception e) {
			log.error("Ha habido un error " + e.toString());
			resumen = actualizaResumen(resumen, otros_index, total_index, false);
			addActionError(mensajeErrorEstandar);
			resultadoImportacion.setError(true);
			addActionError(e.toString());
		} catch (XmlNoValidoExcepcion e) {
			log.error("Ha habido un error al parsear el XML de Matw " + e.toString().substring(49));
			resumen = actualizaResumen(resumen, otros_index, total_index, false);
			addActionError(mensajeErrorEstandar);
			resultadoImportacion.setError(true);
			addActionError(e.toString());
		}
		return resultadoImportacion;
	}

	// TODO MPC. Cambio IVTM.
	private void procesarGA(trafico.beans.jaxb.matw.FORMATOGA formatoGA, BeanPQAltaMatriculacionImport beanAlta, String mensajeErrorEstandar, int matriculacion_index, int total_index, int i,
			BigDecimal idUsuario, BigDecimal idContrato) {

		if (formatoGA == null || formatoGA.getMATRICULACION() == null || formatoGA.getMATRICULACION().size() < i) {
			addActionMessage("No se puede recuperar el trámite");
			return;
		}

		trafico.beans.jaxb.matw.FORMATOGA.MATRICULACION matriculacionGA = formatoGA.getMATRICULACION().get(i);
		IVTMModeloMatriculacionInterface modeloIVTM = new IVTMModeloMatriculacionImpl();
		// Para importar los datos del IVTM, tanto el usuario que lo solicita, como el colegiado del trámite, deben tener permisos del IVTM.
		boolean tienePermisoIVTM = false;
		if (utilesColegiado.tienePermisoAutoliquidarIvtm()) {
			tienePermisoIVTM = servicioPermisos.tienePermisoElContrato(getContratoImportacion() != null ? getContratoImportacion().longValue() : idContrato.longValue(),
					UtilesColegiado.PERMISO_AUTOLIQUIDAR_IVTM, UtilesColegiado.APLICACION_OEGAM_TRAF);
		}
		boolean tienePermisoLiberarEEFF = false;
		if (utilesColegiado.tienePermisoLiberarEEFF()) {
			tienePermisoLiberarEEFF = servicioPermisos.tienePermisoElContrato(getContratoImportacion() != null ? getContratoImportacion().longValue() : idContrato.longValue(),
					ConstantesEEFF.CODIGO_PERMISO_BBDD_LIBERAR_EEFF, UtilesColegiado.APLICACION_OEGAM_TRAF);
		}
		ResultBean resultadoValidar = modeloIVTM.validarFORMATOIVTMGA(formatoGA, tienePermisoIVTM);

		if (resultadoValidar != null && !resultadoValidar.getError()) {
			if (utilesColegiado.tienePermisoLiberarEEFF()) {
				resultadoValidar = servicioEEFF.validarEeffLibMatwFORMATOGA(matriculacionGA);
			} else {
				resultadoValidar = new ResultBean();
			}
			if (resultadoValidar != null && !resultadoValidar.getError()) {
				ResultBean resultadoGuardarMatw = getModeloImportacionDGT().guardarAltaMatriculacionMatwImport(beanAlta, false, getContratoImportacion(), idUsuario, idContrato, TipoCreacion.XML);
				BigDecimal numExpediente = (BigDecimal) resultadoGuardarMatw.getAttachment("numExpediente");

				if (resultadoGuardarMatw.getError()) {
					if (mensajeErrorEstandar != null && !"".equals(mensajeErrorEstandar)) {
						addActionError(mensajeErrorEstandar);
					}
					addActionError("Tramite " + i + ": " + resultadoGuardarMatw.getMensaje());
					resumen = actualizaResumen(resumen, matriculacion_index, total_index, false);
				} else {
					// Entidades financieras
					for (String mensaje : resultadoValidar.getListaMensajes()) {
						addActionMessage("Tramite " + i + ": " + mensaje);
					}
					ResultBean resultadoGuardar = null;
					if (tienePermisoLiberarEEFF) {
						resultadoGuardar = servicioEEFF.guardarDatosImportacion(matriculacionGA, numExpediente);
						if (null != resultadoGuardar) {
							for (String mensaje : resultadoGuardar.getListaMensajes()) {
								addActionMessage("Tramite " + i + ": " + mensaje);
							}
						}
					}
					// TODO MPC. Cambios IVTM
					resultadoGuardar = modeloIVTM.guardarDatosImportados(matriculacionGA, numExpediente, tienePermisoIVTM);
					if (resultadoGuardar != null && resultadoGuardar.getError()) {
						addActionMessage("- IVTM  -" + resultadoGuardar.getMensaje() + " trámite: " + numExpediente);
					}
					addActionMessage("Tramite " + i + ": " + resultadoGuardarMatw.getMensaje());
					resumen = actualizaResumen(resumen, matriculacion_index, total_index, true);
				}
			} else {
				if (null != resultadoValidar) {
					for (String mensaje : resultadoValidar.getListaMensajes()) {
						addActionError("Tramite " + i + ": " + mensaje);
					}
				}
				resumen = actualizaResumen(resumen, matriculacion_index, total_index, false);
			}
		} else {
			for (String mensaje : resultadoValidar.getListaMensajes()) {
				addActionError("Tramite " + i + ": " + mensaje);
			}
			resumen = actualizaResumen(resumen, matriculacion_index, total_index, false);
		}
	}

	private ResultBean procesarGANuevo(trafico.beans.jaxb.matw.FORMATOGA formatoGA, TramiteTrafMatrDto tramite, String mensajeErrorEstandar, int matriculacion_index, int total_index, int i,
			BigDecimal idUsuario, BigDecimal idContrato, Boolean esAdmin, Boolean tienePermisoLiberarEEFF) throws OegamExcepcion {
		ResultBean resultadoImportacion = new ResultBean();

		trafico.beans.jaxb.matw.FORMATOGA.MATRICULACION matriculacionGA = formatoGA.getMATRICULACION().get(i);
		// Para importar los datos del IVTM, tanto el usuario que lo solicita, como el colegiado del trámite, deben tener permisos del IVTM.
		boolean tienePermisoIVTM = false;
		if (utilesColegiado.tienePermisoAutoliquidarIvtm()) {
			tienePermisoIVTM = servicioPermisos.tienePermisoElContrato(getContratoImportacion() != null ? getContratoImportacion().longValue() : idContrato.longValue(),
					UtilesColegiado.PERMISO_AUTOLIQUIDAR_IVTM, UtilesColegiado.APLICACION_OEGAM_TRAF);
		}

		ResultBean resultadoValidar = servicioIvtmMatriculacion.validarFORMATOIVTMGA(formatoGA, tienePermisoIVTM);

		if (resultadoValidar != null && !resultadoValidar.getError()) {
			if (utilesColegiado.tienePermisoLiberarEEFF()) {
				resultadoValidar = servicioEEFF.validarEeffLibMatwFORMATOGA(matriculacionGA);
			} else {
				resultadoValidar = new ResultBean();
			}
			if (resultadoValidar != null && !resultadoValidar.getError()) {
				tramite.setIdTipoCreacion(new BigDecimal(TipoCreacion.XML.getValorEnum()));
				ResultBean result = servicioImportacion.importarMatriculacion(tramite, idUsuario, tienePermisoLiberarEEFF, esAdmin);

				if (result.getError()) {
					String mensajes = "";
					for (String mensaje : result.getListaMensajes()) {
						mensajes = mensajes + mensaje + " ";
					}
					resultadoImportacion.setError(true);
					addActionError("Tramite " + i + ": " + mensajes);
					resumen = actualizaResumen(resumen, matriculacion_index, total_index, false);
				} else {
					// Entidades financieras
					for (String mensaje : resultadoValidar.getListaMensajes()) {
						addActionMessage("Tramite " + i + ": " + mensaje);
					}
					ResultBean resultadoGuardar = null;
					BigDecimal numExpediente = (BigDecimal) result.getAttachment(ServicioTramiteTraficoMatriculacion.NUMEXPEDIENTE);
					if (tienePermisoIVTM) {
						resultadoGuardar = servicioIvtmMatriculacion.guardarDatosImportados(matriculacionGA, numExpediente);
						if (resultadoGuardar != null && resultadoGuardar.getError()) {
							addActionMessage("- IVTM  -" + resultadoGuardar.getMensaje() + " trámite: " + numExpediente);
						}
					}
					addActionMessage("Tramite " + i + ": " + result.getMensaje());
					resumen = actualizaResumen(resumen, matriculacion_index, total_index, true);
				}
			} else {
				if (resultadoValidar != null) {
					for (String mensaje : resultadoValidar.getListaMensajes()) {
						addActionError("Tramite " + i + ": " + mensaje);
					}
				}
				resumen = actualizaResumen(resumen, matriculacion_index, total_index, false);
				resultadoImportacion.setError(true);
			}
		} else {
			for (String mensaje : resultadoValidar.getListaMensajes()) {
				addActionError("Tramite " + i + ": " + mensaje);
			}
			resumen = actualizaResumen(resumen, matriculacion_index, total_index, false);
			resultadoImportacion.setError(true);
		}
		return resultadoImportacion;
	}

	/**
	 * Provisional mientras sea válido el antiguo xsd
	 */

	/**
	 * Método para improtar un XML de transmisión electrónica
	 * @return
	 */
	public String importarTransmisionElectronica() {
		return importarTransmision(true);
	}

	/**
	 * Método para improtar un XML de transmisión actual
	 * @return
	 */
	public String importarTransmisionActual() {
		return importarTransmision(false);
	}

	public String importarTransmision(Boolean electronica) {
		String accionRetorno = "importarDGT";

		int transmision_index = 0;
		int otros_index = 1;
		int total_index = 2;

		// Contrato y usuario del que está haciendo la importación
		BigDecimal idContrato = utilesColegiado.getIdContratoSessionBigDecimal();
		BigDecimal idUsuario = utilesColegiado.getIdUsuarioSessionBigDecimal();

		resumen.add(transmision_index, new ResumenImportacion("Transmision"));
		resumen.add(otros_index, new ResumenImportacion("Otros"));
		resumen.add(total_index, new ResumenImportacion("TOTAL"));
		// Si no se rellena el input file se direcciona de nuevo a la página de
		// cargar fichero
		if (getFichero() == null || !utiles.esNombreFicheroValido(getFicheroFileName())) {
			log.error("Error al importar el fichero");
			setResultBean(new ResultBean());
			getResultBean().setError(true);
			getResultBean().setMensaje(ERROR_IMPORTAR_FICHERO);
			addActionError(ERROR_IMPORTAR_FICHERO);
			resumen = actualizaResumen(resumen, otros_index, total_index, false);
			return accionRetorno;
		}

		// Incidencia : Mantis 1951 (Ricardo Rodriguez 15/06/2012)
		// Descripcion : Verificar permiso de importación de todo tipo de
		// ficheros
		FicherosImportacion ficheroImportacion = null;
		if (electronica) {
			ficheroImportacion = FicherosImportacion.IMPORTAR_XML_TRANSMISION_ELECTRONICA;
		} else {
			ficheroImportacion = FicherosImportacion.IMPORTAR_XML_TRANSMISION;
		}
		try {
			String resultado = utilesColegiado.tienePermisoImportacion(ficheroImportacion);
			if (resultado != null && !resultado.equalsIgnoreCase(Constantes.TRUE)) {
				// El usuario no tiene permiso para importar este trámite.
				// Recupera de un properties el mensaje al usuario
				String mensajeError = ficheroImportacion.getNombreEnum();
				setResultBean(new ResultBean());
				getResultBean().setError(true);
				getResultBean().setMensaje(mensajeError);
				addActionError(mensajeError);
				resumen = actualizaResumen(resumen, otros_index, total_index, false);
				return "importarDGT";
			}
		} catch (Exception ex) {
			log.error(ex);
			addActionError(ex.toString());
			return Action.ERROR;
		} catch (OegamExcepcion ex) {
			log.error(ex);
			addActionError(ex.toString());
			return Action.ERROR;
		}
		// Fin incidencia Mantis 1951

		File ficheroTransmision = getFichero();
		ResultBean resultadoImportacion = importarXMLTransmision(electronica, idUsuario, idContrato, ficheroTransmision);

		if (!resultadoImportacion.getError()) {
			// Gestor de Ficheros
			FicheroBean fichero = new FicheroBean();
			fichero.setFichero(ficheroTransmision);
			fichero.setTipoDocumento(ConstantesGestorFicheros.IMPORTACION);
			if (electronica)
				fichero.setSubTipo(ConstantesGestorFicheros.NOMBRE_TRANS_ELECTRONICA);
			else
				fichero.setSubTipo(ConstantesGestorFicheros.SUBTIPO_DOC_TRANSMISION);
			fichero.setExtension(ConstantesGestorFicheros.EXTENSION_XML);
			Fecha f = new Fecha(new Date());
			fichero.setFecha(f);
			String fecha = f.getDia() + "_" + f.getHora() + f.getMinutos() + f.getSegundos();
			fichero.setNombreDocumento(idUsuario + "_" + idContrato + "_" + fecha);
			try {
				gestorDocumentos.guardarFichero(fichero);
			} catch (OegamExcepcion e) {
				String tipo = "";
				if (electronica)
					tipo += "electronica";
				else
					tipo += "antigua";
				log.error("Error al custodiar el importado XML de Transmision " + tipo + ": " + idUsuario + "_" + idContrato + "_" + fecha);
			}
		}
		return accionRetorno;
	}

	/**
	 * Método que procesa un XML (validado de MATE, o directamente de MATEGE)
	 * @return
	 */
	public ResultBean importarXMLTransmision(Boolean electronica, BigDecimal idUsuario, BigDecimal idContrato, File ficheroTransmision) {
		// String accionRetorno = "importarDGT";
		ResultBean resultadoImportacion = new ResultBean();
		int transmision_index = 0;
		int otros_index = 1;
		int total_index = 2;
		String token = null;

		// Si está validado, continuamos parseando el XML
		try {
			// metemos el paquete donde están las clases que creamos a partir de
			// ga.xsd mediante xjc.exe
			XMLTransmisionFactory xMLTransmisionFactory = new XMLTransmisionFactory();
			xMLTransmisionFactory.validarXMLTRANSMISION(ficheroTransmision, gestorPropiedades.valorPropertie(Constantes.PROPERTIES_RUTA_ESQUEMA_TRANSMISION));

			// Construyo un Objeto de tipo Transmision
			trafico.beans.jaxb.transmision.FORMATOGA ga = xMLTransmisionFactory.getFormatoOegam2Transmision(ficheroTransmision);

			// Mantis 17066: David Sierra: Se comprueba si el usuario ha introducido el numero de CET y la Factura a la vez
			for (int i = 0; i < ga.getTRANSMISION().size(); i++) {
				if ((ga.getTRANSMISION().get(i).getDATOSPRESENTACION().getCODIGOELECTRONICOTRANSFERENCIA() != null && !ga.getTRANSMISION().get(i).getDATOSPRESENTACION()
						.getCODIGOELECTRONICOTRANSFERENCIA().isEmpty()) && (ga.getTRANSMISION().get(i).getDATOSPRESENTACION().getNUMEROFACTURA() != null && !ga.getTRANSMISION().get(i)
								.getDATOSPRESENTACION().getNUMEROFACTURA().isEmpty())) {

					ga.getTRANSMISION().get(i).getDATOSPRESENTACION().setCODIGOELECTRONICOTRANSFERENCIA("");
					addActionMessage("No se ha importado el CET porque ya se ha introducido el número de factura");
				}
			}

			List<BeanPQTramiteTransmisionImport> beansAlta = FORMATOGAtransmisionPQConversion.convertirFORMATOGAtoPQ(ga, electronica, idUsuario, idContrato);
			token = ga.getCABECERA().getDATOSGESTORIA().getTIPODGT();

			log.debug("probando conversion a los beans de pantalla");

			try {
				if (ga.getCABECERA() == null || ga.getCABECERA().getDATOSGESTORIA() == null || ga.getCABECERA().getDATOSGESTORIA().getPROFESIONAL() == null || "".equals(ga.getCABECERA()
						.getDATOSGESTORIA().getPROFESIONAL())) {
					addActionError("El fichero no tiene número de profesional, no se podrán importar sus trámites");
					resultadoImportacion.setError(false);
					resumen = actualizaResumen(resumen, otros_index, total_index, false);
					return resultadoImportacion;
				}

				// Si es trasmisión electrónica se tiene que validar que venga
				// informado tipoDGT
				if (electronica && token == null) {
					addActionError("Para la importación de trámites de transmisión electrónica es obligatorio informar el campo 'Tipo DGT'");
					resultadoImportacion.setError(false);
					resumen = actualizaResumen(resumen, otros_index, total_index, false);
					return resultadoImportacion;
				}

			} catch (Exception e) {
				log.error(e);
				addActionError("Error en la cabecera del fichero");
				resultadoImportacion.setError(false);
				resumen = actualizaResumen(resumen, otros_index, total_index, false);
			}

			if (utilesColegiado.tienePermisoImportacionMasiva() && importacionMasiva(beansAlta.size())) {
				if (electronica) {
					importacionEnProceso(ficheroTransmision, utilesColegiado.getNumColegiadoSession(), idContrato, contratoImportacion, ConstantesSession.XML_TRANSMISION_ELECTRONICA);
				} else {
					importacionEnProceso(ficheroTransmision, utilesColegiado.getNumColegiadoCabecera(), idContrato, contratoImportacion, ConstantesSession.XML_TRANSMISION);
				}
				addActionError("Su solicitud se está procesando. Estará disponible en unos minutos. Recibirá una notificación de la misma.");
				resultadoImportacion.setError(true);
				resumen = actualizaResumen(resumen, otros_index, total_index, false);
				return resultadoImportacion;
			}

			// Si el colegiado de sesión tiene permiso de administración:
			// ---> se procesa el trámite
			if (utilesColegiado.tienePermisoAdmin()) {
				for (int i = 0; i < beansAlta.size(); i++) {
					// Mantis 25415
					if (beansAlta.get(i).getBeanGuardarTransmision().getP_VALOR_REAL() != null && !validarFormatoValorReal(beansAlta.get(i).getBeanGuardarTransmision().getP_VALOR_REAL().toString())
							&& (beansAlta.get(i).getBeanGuardarTransmision().getP_FACTURA() == null || beansAlta.get(i).getBeanGuardarTransmision().getP_FACTURA() == "")) {
						addActionError("-Tramite " + i + ": " + " El valor real debe contener como máximo 6 enteros y 2 decimales. Además debe ser superior a cero.");
						resultadoImportacion.setError(false);
						resumen = actualizaResumen(resumen, otros_index, total_index, false);
					} else {
						HashMap<String, Object> resultado = getModeloImportacionDGT().guardarTransmisionImport((BeanPQTramiteTransmisionImport) beansAlta.get(i), getContratoImportacion(), idUsuario,
								idContrato, TipoCreacion.XML);
						if (((ResultBean) resultado.get(ConstantesPQ.RESULTBEAN)).getError()) {
							addActionError("-Tramite " + i + ": " + ((ResultBean) resultado.get("ResultBean")).getMensaje());
							resultadoImportacion.setError(false);
							resumen = actualizaResumen(resumen, otros_index, total_index, false);
						} else {
							// Control de importación
							if (electronica) {
								getModeloImportacionDGT().controlImportacion((BigDecimal) resultado.get(ConstantesPQ.NUM_EXPEDIENTE), token);
							}

							addActionMessage("-Tramite " + i + ": " + ((ResultBean) resultado.get("ResultBean")).getMensaje());
							resumen = actualizaResumen(resumen, otros_index, total_index, true);
						}
					}
				}
			}
			// Si el colegiado de sesión tiene permiso de colegiado:
			// ---> Se comprueba que el número de colegiado del archivo
			// pertenece al contrato del colegio del colegiado de sesión
			// ---> se procesa en caso afirmativo
			else if (utilesColegiado.tienePermisoColegio()) {
				List<String> listaColegiados = utilesColegiado.getNumColegiadosDelContrato();
				Boolean puede = false;
				for (int i = 0; i < beansAlta.size(); i++) {
					int j = 0;
					while ((!puede) && (listaColegiados.size() > j)) {
						puede = puede || beansAlta.get(i).getBeanGuardarTransmision().getP_NUM_COLEGIADO().equals(listaColegiados.get(j));
						j++;
					}
					if (puede) {
						// Mantis 25415
						if (beansAlta.get(i).getBeanGuardarTransmision().getP_VALOR_REAL() != null && !validarFormatoValorReal(beansAlta.get(i).getBeanGuardarTransmision().getP_VALOR_REAL()
								.toString()) && (beansAlta.get(i).getBeanGuardarTransmision().getP_FACTURA() == null || beansAlta.get(i).getBeanGuardarTransmision().getP_FACTURA() == "")) {
							addActionError("-Tramite " + i + ": " + " El valor real debe contener como máximo 6 enteros y 2 decimales. Además debe ser superior a cero.");
							resultadoImportacion.setError(false);
							resumen = actualizaResumen(resumen, otros_index, total_index, false);
						} else {
							HashMap<String, Object> resultado = getModeloImportacionDGT().guardarTransmisionImport((BeanPQTramiteTransmisionImport) beansAlta.get(i), getContratoImportacion(),
									idUsuario, idContrato, TipoCreacion.XML);
							if (((ResultBean) resultado.get(ConstantesPQ.RESULTBEAN)).getError()) {
								addActionError("-Tramite " + i + ": " + ((ResultBean) resultado.get("ResultBean")).getMensaje());
								resultadoImportacion.setError(false);
								resumen = actualizaResumen(resumen, transmision_index, total_index, false);
							} else {
								// Control de importación
								if (electronica) {
									getModeloImportacionDGT().controlImportacion((BigDecimal) resultado.get(ConstantesPQ.NUM_EXPEDIENTE), token);
								}

								addActionMessage("-Tramite " + i + ": " + ((ResultBean) resultado.get("ResultBean")).getMensaje());
								resumen = actualizaResumen(resumen, transmision_index, total_index, true);
							}

						}
					} else {
						String mensajeError = "-Tramite " + i + " : El usuario (" + utilesColegiado.getNumColegiadoSession()
								+ ") no tiene permisos para realizar la importación para el colegiado del fichero (" + beansAlta.get(i).getBeanGuardarTransmision().getP_NUM_COLEGIADO() + ")";
						addActionError(mensajeError);
						resultadoImportacion.setError(false);
						resumen = actualizaResumen(resumen, otros_index, total_index, false);
						return resultadoImportacion;
					}
					puede = false;
				}
			} else { // Si el usuario de sesión es un colegiado cualquiera
				// Si es el mismo el de sesión que el de cabecera
				for (int i = 0; i < beansAlta.size(); i++) {
					if (utilesColegiado.getNumColegiadoSession().equals(beansAlta.get(i).getBeanGuardarTransmision().getP_NUM_COLEGIADO())) {
						HashMap<String, Object> resultado = getModeloImportacionDGT().guardarTransmisionImport((BeanPQTramiteTransmisionImport) beansAlta.get(i), getContratoImportacion(), idUsuario,
								idContrato, TipoCreacion.XML);
						if (((ResultBean) resultado.get(ConstantesPQ.RESULTBEAN)).getError()) {
							addActionError("-Tramite " + i + ": " + ((ResultBean) resultado.get("ResultBean")).getMensaje());
							resultadoImportacion.setError(false);
							resumen = actualizaResumen(resumen, transmision_index, total_index, false);
						} else {
							// Control de importación
							if (electronica) {
								getModeloImportacionDGT().controlImportacion((BigDecimal) resultado.get(ConstantesPQ.NUM_EXPEDIENTE), token);
							}

							addActionMessage("-Tramite " + i + ": " + ((ResultBean) resultado.get("ResultBean")).getMensaje());
							resumen = actualizaResumen(resumen, transmision_index, total_index, true);
						}
					} else {
						String mensajeError = "-Tramite " + i + " : El usuario (" + utilesColegiado.getNumColegiadoSession() + ") no es el mismo colegiado que el del trámite del fichero (" + beansAlta.get(i)
								.getBeanGuardarTransmision().getP_NUM_COLEGIADO() + ")";
						resumen = actualizaResumen(resumen, otros_index, total_index, false);
						addActionError(mensajeError);
						resultadoImportacion.setError(false);
					}
				}
			}
		} catch (Exception e) {
			log.error("Ha habido un error " + e.toString(), e);
			addActionError("Ha habido un error " + e.toString());
			resultadoImportacion.setError(false);
			resumen = actualizaResumen(resumen, otros_index, total_index, false);
		} catch (XmlNoValidoExcepcion e) {
			log.error("Ha habido un error al parsear el XML " + e.toString().substring(49));
			addActionError("Ha habido un error al parsear el XML de Transmision Electronica " + e.toString().substring(49));
			resultadoImportacion.setError(false);
			resumen = actualizaResumen(resumen, otros_index, total_index, false);
		} catch (OegamExcepcion e) {
			log.error("Ha habido un error al guardar en BBDD " + e.toString());
			addActionError("Ha habido un error al guardar en BBDD " + e.toString());
			resultadoImportacion.setError(false);
			resumen = actualizaResumen(resumen, otros_index, total_index, false);
		}

		return resultadoImportacion;
	}

	/**
	 * Importar xml BAJA
	 */
	public String importarBaja() {
		String accionRetorno = "importarDGT";

		int baja_index = 0;
		int otros_index = 1;
		int total_index = 2;

		// Contrato y usuario del que está haciendo la importación
		BigDecimal idContrato = utilesColegiado.getIdContratoSessionBigDecimal();
		BigDecimal idUsuario = utilesColegiado.getIdUsuarioSessionBigDecimal();

		resumen.add(baja_index, new ResumenImportacion("Baja"));
		resumen.add(otros_index, new ResumenImportacion("Otros"));
		resumen.add(total_index, new ResumenImportacion("TOTAL"));
		// Si no se rellena el input file se direcciona de nuevo a la pagina de
		// cargar fichero
		if (getFichero() == null || !utiles.esNombreFicheroValido(getFicheroFileName())) {
			log.error("Error al importar el fichero");
			setResultBean(new ResultBean());
			getResultBean().setError(true);
			getResultBean().setMensaje(ERROR_IMPORTAR_FICHERO);
			addActionError(ERROR_IMPORTAR_FICHERO);
			resumen = actualizaResumen(resumen, otros_index, total_index, false);
			return accionRetorno;
		}

		try {
			String resultado = utilesColegiado.tienePermisoImportacion(FicherosImportacion.IMPORTAR_XML_BAJA);
			if (resultado != null && !resultado.equalsIgnoreCase(Constantes.TRUE)) {
				// El usuario no tiene permiso para importar este trámite.
				// Recupera de un properties el mensaje al usuario
				String mensajeError = FicherosImportacion.IMPORTAR_XML_BAJA.getNombreEnum();
				setResultBean(new ResultBean());
				getResultBean().setError(true);
				getResultBean().setMensaje(mensajeError);
				addActionError(mensajeError);
				resumen = actualizaResumen(resumen, otros_index, total_index, false);
				return "importarDGT";
			}
		} catch (Exception ex) {
			log.error(ex.toString());
			addActionError(ex.toString());
			return Action.ERROR;
		} catch (OegamExcepcion ex) {
			log.error(ex.toString());
			addActionError(ex.toString());
			return Action.ERROR;
		}
		// Fin incidencia Mantis 1951

		File ficheroBaja = getFichero();
		ResultBean resultadoImportacion = importarXMLBaja(idUsuario, idContrato, ficheroBaja, contratoImportacion);

		if (!resultadoImportacion.getError()) {
			// Gestor de Ficheros
			FicheroBean fichero = new FicheroBean();
			fichero.setFichero(ficheroBaja);
			fichero.setTipoDocumento(ConstantesGestorFicheros.IMPORTACION);
			fichero.setSubTipo(ConstantesGestorFicheros.SUBTIPO_DOC_BAJA);
			fichero.setExtension(ConstantesGestorFicheros.EXTENSION_XML);
			Fecha f = new Fecha(new Date());
			fichero.setFecha(f);
			String fecha = f.getDia() + "_" + f.getHora() + f.getMinutos() + f.getSegundos();
			fichero.setNombreDocumento(idUsuario + "_" + idContrato + "_" + fecha);
			try {
				gestorDocumentos.guardarFichero(fichero);
			} catch (OegamExcepcion e) {
				log.error("Error al custodiar el importado XML de Baja: " + idUsuario + "_" + idContrato + "_" + fecha);
			}
		}
		return accionRetorno;
	}

	/**
	 * Importar XML Baja Método que procesa un XML (validado de MATE, o directamente de MATEGE)
	 * @return
	 */
	public ResultBean importarXMLBaja(BigDecimal idUsuario, BigDecimal idContrato, File ficheroBaja, BigDecimal contratoImportacion) {
		ResultBean resultadoImportacion = new ResultBean();
		int baja_index = 0;
		int otros_index = 1;
		int total_index = 2;
		Boolean tienePermisoBtv = utilesColegiado.tienePermisoBTV();

		try {
			XMLBajaFactory xMLBajaFactory = new XMLBajaFactory();
			xMLBajaFactory.validarXMLFORMATOOEGAM2BAJA(ficheroBaja, gestorPropiedades.valorPropertie(Constantes.PROPERTIES_RUTA_ESQUEMA_BAJA));
			FORMATOOEGAM2BAJA ga = xMLBajaFactory.getFormatoOegam2Baja(ficheroBaja);

			List<TramiteTrafBajaDto> listaBaja = formatoBajaDtoConversion.convertirFORMATOGAtoDto(ga, idContrato, contratoImportacion);
			log.debug("probando conversion a los beans de pantalla");

			try {
				if (ga.getCABECERA() == null || ga.getCABECERA().getDATOSGESTORIA() == null || ga.getCABECERA().getDATOSGESTORIA().getPROFESIONAL() == null || "".equals(ga.getCABECERA()
						.getDATOSGESTORIA().getPROFESIONAL())) {
					addActionError("El fichero no tiene número de profesional, no se podrán importar sus trámites");
					resultadoImportacion.setError(true);
					resumen = actualizaResumen(resumen, otros_index, total_index, false);
					return resultadoImportacion;
				}
			} catch (Exception e) {
				log.error(e);
				addActionError("Error en la cabecera del fichero");
				resultadoImportacion.setError(true);
				resumen = actualizaResumen(resumen, otros_index, total_index, false);
			}

			if (utilesColegiado.tienePermisoImportacionMasiva() && importacionMasiva(listaBaja.size())) {
				importacionEnProceso(ficheroBaja, utilesColegiado.getNumColegiadoSession(), idContrato, contratoImportacion, ConstantesSession.XML_BAJA);
				addActionError("Su solicitud se está procesando. Estará disponible en unos minutos. Recibirá una notificación de la misma.");
				resultadoImportacion.setError(true);
				resumen = actualizaResumen(resumen, otros_index, total_index, false);
				return resultadoImportacion;
			}

			// Si el colegiado de sesión tiene permiso de administración: se procesa el trámite
			if (utilesColegiado.tienePermisoAdmin()) {
				for (int i = 0; i < listaBaja.size(); i++) {
					listaBaja.get(i).setIdTipoCreacion(new BigDecimal(TipoCreacion.XML.getValorEnum()));
					ResultBean result = servicioImportacion.importarBaja(listaBaja.get(i), idUsuario, tienePermisoBtv, Boolean.FALSE);

					if (result.getError()) {
						String mensajes = "";
						for (String mensaje : result.getListaMensajes()) {
							mensajes = mensajes + mensaje + " ";
						}
						addActionError("-Tramite " + i + ": " + mensajes);
						resultadoImportacion.setError(true);
						resumen = actualizaResumen(resumen, baja_index, total_index, false);
					} else {
						addActionMessage("-Tramite " + i + ": " + result.getMensaje());
						resumen = actualizaResumen(resumen, baja_index, total_index, true);
					}
				}
			}
			// Si el colegiado de sesión tiene permiso de colegiado: Se comprueba que el número de colegiado del archivo pertenece al contrato del colegio del colegiado de sesión
			else if (utilesColegiado.tienePermisoColegio()) {
				List<String> listaColegiados = utilesColegiado.getNumColegiadosDelContrato();
				Boolean puede = false;
				for (int i = 0; i < listaBaja.size(); i++) {
					int j = 0;
					while ((!puede) && (listaColegiados.size() > j)) {
						puede = puede || listaBaja.get(i).getNumColegiado().equals(listaColegiados.get(j));
						j++;
					}
					if (puede) {
						ResultBean result = servicioImportacion.importarBaja(listaBaja.get(i), idUsuario, tienePermisoBtv, Boolean.FALSE);

						if (result.getError()) {
							addActionError("-Tramite " + i + ": " + result.getMensaje());
							resultadoImportacion.setError(true);
							resumen = actualizaResumen(resumen, baja_index, total_index, false);
						} else {
							addActionMessage("-Tramite " + i + ": " + result.getMensaje());
							resumen = actualizaResumen(resumen, baja_index, total_index, true);
						}
					} else {
						String mensajeError = "-Tramite " + i + " : El usuario (" + utilesColegiado.getNumColegiadoSession()
								+ ") no tiene permisos para realizar la importación para el colegiado del fichero (" + listaBaja.get(i).getNumColegiado() + ")";
						addActionError(mensajeError);
						resultadoImportacion.setError(true);
						resumen = actualizaResumen(resumen, otros_index, total_index, false);
						return resultadoImportacion;
					}
					puede = false;
				}
			}
			// Si el usuario de sesión es un colegiado cualquiera
			else {
				// Si es el mismo el de sesión que el de cabecera
				for (int i = 0; i < listaBaja.size(); i++) {
					if (utilesColegiado.getNumColegiadoSession().equals(listaBaja.get(i).getNumColegiado())) {
						ResultBean result = servicioImportacion.importarBaja(listaBaja.get(i), idUsuario, tienePermisoBtv, Boolean.FALSE);

						if (result.getError()) {
							addActionError("-Tramite " + i + ": " + result.getMensaje());
							resultadoImportacion.setError(true);
							resumen = actualizaResumen(resumen, baja_index, total_index, false);
						} else {
							addActionMessage("-Tramite " + i + ": " + result.getMensaje());
							resumen = actualizaResumen(resumen, baja_index, total_index, true);
						}
					} else {
						String mensajeError = "-Tramite " + i + " : El usuario (" + utilesColegiado.getNumColegiadoSession() + ") no es el mismo colegiado que el del trámite del fichero (" + listaBaja.get(i)
								.getNumColegiado() + ")";
						resumen = actualizaResumen(resumen, otros_index, total_index, false);
						addActionError(mensajeError);
						resultadoImportacion.setError(true);
					}
				}
			}
		} catch (Exception e) {
			log.error("Ha habido un error: " + e.toString());
			addActionError("Ha habido un error al importar el fichero de baja.");
			resultadoImportacion.setError(true);
			resumen = actualizaResumen(resumen, otros_index, total_index, false);
		} catch (XmlNoValidoExcepcion e) {
			log.error("Ha habido un error al parsear el XML de Baja: " + e.toString().substring(49));
			addActionError("Ha habido un error al parsear el XML de Baja:" + e.toString().substring(49));
			resultadoImportacion.setError(true);
			resumen = actualizaResumen(resumen, otros_index, total_index, false);
		}
		return resultadoImportacion;
	}

	/**
	 * Importar XML Duplicado
	 */
	public String importarDuplicado() {

		String accionRetorno = "importarDGT";

		int duplicado_index = 0;
		int otros_index = 1;
		int total_index = 2;

		// Usuario que está haciendo la importación
		BigDecimal idUsuario = utilesColegiado.getIdUsuarioSessionBigDecimal();
		BigDecimal idContrato = utilesColegiado.getIdContratoSessionBigDecimal();

		resumen.add(duplicado_index, new ResumenImportacion("Duplicado"));
		resumen.add(otros_index, new ResumenImportacion("Otros"));
		resumen.add(total_index, new ResumenImportacion("TOTAL"));

		// Si no se rellena el input file se direcciona de nuevo a la página de cargar fichero
		if (getFichero() == null || !utiles.esNombreFicheroValido(getFicheroFileName())) {
			log.error("Error al importar el fichero");
			setResultBean(new ResultBean());
			getResultBean().setError(true);
			getResultBean().setMensaje(ERROR_IMPORTAR_FICHERO);
			addActionError(ERROR_IMPORTAR_FICHERO);
			resumen = actualizaResumen(resumen, otros_index, total_index, false);
			return accionRetorno;
		}

		// Incidencia : Mantis 1951 (Ricardo Rodriguez 15/06/2012)
		// Descripcion : Verificar permiso de importación de todo tipo de ficheros
		try {
			String resultado = utilesColegiado.tienePermisoImportacion(FicherosImportacion.IMPORTAR_XML_DUPLICADO);
			if (resultado != null && !resultado.equalsIgnoreCase(Constantes.TRUE)) {
				// El usuario no tiene permiso para importar este trámite.
				// Recupera de un properties el mensaje al usuario
				String mensajeError = FicherosImportacion.IMPORTAR_XML_DUPLICADO.getNombreEnum();
				setResultBean(new ResultBean());
				getResultBean().setError(true);
				getResultBean().setMensaje(mensajeError);
				addActionError(mensajeError);
				resumen = actualizaResumen(resumen, otros_index, total_index, false);
				return "importarDGT";
			}
		} catch (Exception ex) {
			log.error(ex);
			addActionError(ex.toString());
			return Action.ERROR;

		} catch (OegamExcepcion ex) {
			log.error(ex);
			addActionError(ex.toString());
			return Action.ERROR;
		}
		// Fin incidencia Mantis 1951

		File ficheroDuplicado = getFichero();
		ResultBean resultadoImportacion = importarXMLDuplicado(idUsuario, idContrato, ficheroDuplicado, contratoImportacion);

		if (!resultadoImportacion.getError()) {
			// Gestor de Ficheros
			FicheroBean fichero = new FicheroBean();
			fichero.setFichero(ficheroDuplicado);
			fichero.setTipoDocumento(ConstantesGestorFicheros.IMPORTACION);
			fichero.setSubTipo(ConstantesGestorFicheros.SUBTIPO_DOC_DUPLICADO);
			fichero.setExtension(ConstantesGestorFicheros.EXTENSION_XML);
			Fecha f = new Fecha(new Date());
			fichero.setFecha(f);
			String fecha = f.getDia() + "_" + f.getHora() + f.getMinutos() + f.getSegundos();
			fichero.setNombreDocumento(idUsuario + "_" + idContrato + "_" + fecha);
			try {
				gestorDocumentos.guardarFichero(fichero);
				log.info("Fichero guardado en TDOC");
			} catch (OegamExcepcion e) {
				log.error("Error al custodiar el importado XML de Duplicado: " + idUsuario + "_" + idContrato + "_" + fecha);
			}
		}
		return accionRetorno;
	}

	/**
	 * Método que importa un XML de duplicados de trámites.
	 * @param idUsuario idUsuario.
	 * @param ficheroDuplicado fichero con los duplicados a importar.
	 * @param numColegiado número de colegiado.
	 * @param idContrato id de contrato.
	 * @return String - accion.
	 */
	public ResultBean importarXMLDuplicado(BigDecimal idUsuario, BigDecimal idContrato, File ficheroDuplicado, BigDecimal contratoImportacion) {
		ResultBean resultadoImportacion = new ResultBean();
		int duplicado_index = 0;
		int otros_index = 1;
		int total_index = 2;

		try {
			XMLDuplicadoFactory xMLDuplicadoFactory = new XMLDuplicadoFactory();
			xMLDuplicadoFactory.validarXMLFORMATOOEGAM2DUPLICADO(ficheroDuplicado, gestorPropiedades.valorPropertie(Constantes.PROPERTIES_RUTA_ESQUEMA_DUPLICADO));
			FORMATOOEGAM2DUPLICADO ga = xMLDuplicadoFactory.getFormatoOegam2Duplicado(ficheroDuplicado);

			List<TramiteTrafDuplicadoDto> listaDuplicado = formatoDuplicadoDtoConversion.convertirFORMATOGAtoDto(ga, idContrato, contratoImportacion);

			try {
				if (ga.getCABECERA() == null || ga.getCABECERA().getDATOSGESTORIA() == null || ga.getCABECERA().getDATOSGESTORIA().getPROFESIONAL() == null || "".equals(ga.getCABECERA()
						.getDATOSGESTORIA().getPROFESIONAL())) {
					addActionError("El fichero no tiene número de profesional, no se importaron sus trámites");
					resultadoImportacion.setError(true);
					resumen = actualizaResumen(resumen, otros_index, total_index, false);
					return resultadoImportacion;
				}
			} catch (Exception e) {
				log.error(e);
				addActionError("Error en la cabecera del fichero");
				resultadoImportacion.setError(true);
				resumen = actualizaResumen(resumen, otros_index, total_index, false);
			}

			// Si el colegiado de sesión tiene permiso de administración: se procesa el trámite
			if (utilesColegiado.tienePermisoAdmin()) {
				for (int i = 0; i < listaDuplicado.size(); i++) {

					listaDuplicado.get(i).setIdTipoCreacion(new BigDecimal(TipoCreacion.XML.getValorEnum()));
					ResultBean result = servicioImportacion.importarDuplicado(listaDuplicado.get(i), idUsuario);

					if (result.getError()) {
						String mensajes = "";
						for (String mensaje : result.getListaMensajes()) {
							mensajes = mensajes + mensaje + " ";
						}
						addActionError("-Tramite " + i + ": " + mensajes);
						resultadoImportacion.setError(true);
						resumen = actualizaResumen(resumen, duplicado_index, total_index, false);
					} else {
						addActionMessage("-Tramite " + i + ": " + result.getMensaje());
						resumen = actualizaResumen(resumen, duplicado_index, total_index, true);
					}
				}
			}
			// Si el colegiado de sesión tiene permiso de colegiado:
			// ---> Se comprueba que el número de colegiado del archivo pertenece al contrato del colegio del colegiado de sesión (se procesa en caso afirmativo)
			else if (utilesColegiado.tienePermisoColegio()) {
				List<String> listaColegiados = utilesColegiado.getNumColegiadosDelContrato();
				Boolean puede = false;
				for (int i = 0; i < listaDuplicado.size(); i++) {
					int j = 0;
					while ((!puede) && (listaColegiados.size() > j)) {
						puede = puede || listaDuplicado.get(i).getNumColegiado().equals(listaColegiados.get(j));
						j++;
					}
					if (puede) {
						ResultBean result = servicioImportacion.importarDuplicado(listaDuplicado.get(i), idUsuario);

						if (result.getError()) {
							addActionError("-Tramite " + i + ": " + result.getMensaje());
							resultadoImportacion.setError(true);
							resumen = actualizaResumen(resumen, duplicado_index, total_index, false);
						} else {
							addActionMessage("-Tramite " + i + ": " + result.getMensaje());
							resumen = actualizaResumen(resumen, duplicado_index, total_index, true);
						}
					} else {
						String mensajeError = "-Tramite " + i + " : El usuario (" + utilesColegiado.getNumColegiadoSession()
								+ ") no tiene permisos para realizar la importación para el colegiado del fichero (" + listaDuplicado.get(i).getNumColegiado() + ")";
						addActionError(mensajeError);
						resultadoImportacion.setError(true);
						resumen = actualizaResumen(resumen, otros_index, total_index, false);
						return resultadoImportacion;
					}
					puede = false;

				}
			}
			// Si el usuario de sesión es un colegiado cualquiera
			else {
				for (int i = 0; i < listaDuplicado.size(); i++) {
					if (utilesColegiado.getNumColegiadoSession().equals(listaDuplicado.get(i).getNumColegiado())) {
						ResultBean result = servicioImportacion.importarDuplicado(listaDuplicado.get(i), idUsuario);

						if (result.getError()) {
							addActionError("-Tramite " + i + ": " + result.getMensaje());
							resultadoImportacion.setError(true);
							resumen = actualizaResumen(resumen, duplicado_index, total_index, false);
						} else {
							addActionMessage("-Tramite " + i + ": " + result.getMensaje());
							resumen = actualizaResumen(resumen, duplicado_index, total_index, true);
						}
					} else {
						String mensajeError = "-Tramite " + i + " : El usuario (" + utilesColegiado.getNumColegiadoSession() + ") no es el mismo colegiado que el del trámite del fichero (" + listaDuplicado
								.get(i).getNumColegiado() + ")";
						resumen = actualizaResumen(resumen, otros_index, total_index, false);
						addActionError(mensajeError);
						resultadoImportacion.setError(true);
					}
				}
			}
		} catch (Exception e) {
			log.error(e);
			addActionError("Ha habido un error " + e.toString());
			resultadoImportacion.setError(true);
			resumen = actualizaResumen(resumen, otros_index, total_index, false);

		} catch (XmlNoValidoExcepcion e) {
			log.error(e);
			addActionError("Ha habido un error al parsear el XML " + e.toString().substring(49));
			resultadoImportacion.setError(true);
			resumen = actualizaResumen(resumen, otros_index, total_index, false);
		}
		return resultadoImportacion;
	}

	/**
	 * Importar XML Solicitud
	 */
	public String importarSolicitud() {
		String accionRetorno = "importarDGT";

		int solicitud_index = 0;
		int otros_index = 1;
		int total_index = 2;

		// Usuario que esta haciendo la importación
		BigDecimal idUsuario = utilesColegiado.getIdUsuarioSessionBigDecimal();

		// Contrato seleccionado (admin) o contrato del que está haciendo la importacion
		BigDecimal idContrato = (getContratoImportacion() != null) ? getContratoImportacion() : utilesColegiado.getIdContratoSessionBigDecimal();

		// Colegiado del contrato
		ContratoVO contrato = utilesColegiado.getContratoDelColegiado(idContrato);
		String numColegiado = (contrato != null && contrato.getColegiado() != null) ? contrato.getColegiado().getNumColegiado() : utilesColegiado.getNumColegiadoSession();

		resumen.add(solicitud_index, new ResumenImportacion("Solicitud"));
		resumen.add(otros_index, new ResumenImportacion("Otros"));
		resumen.add(total_index, new ResumenImportacion("TOTAL"));

		// Si no se rellena el input file se direcciona de nuevo a la pagina de cargar fichero
		if (getFichero() == null || !utiles.esNombreFicheroValido(getFicheroFileName())) {
			log.error("Error al importar el fichero");
			setResultBean(new ResultBean());
			getResultBean().setError(true);
			getResultBean().setMensaje(ERROR_IMPORTAR_FICHERO);
			addActionError(ERROR_IMPORTAR_FICHERO);
			resumen = actualizaResumen(resumen, otros_index, total_index, false);
			return accionRetorno;
		}

		// Descripcion : Verificar permiso de importación de todo tipo de ficheros
		try {
			String resultado = utilesColegiado.tienePermisoImportacion(FicherosImportacion.IMPORTAR_SOLICITUD);
			if (resultado != null && !resultado.equalsIgnoreCase(Constantes.TRUE)) {
				// El usuario no tiene permiso para importar este tramite.
				// Recupera de un properties el mensaje al usuario
				String mensajeError = FicherosImportacion.IMPORTAR_SOLICITUD.getNombreEnum();
				setResultBean(new ResultBean());
				getResultBean().setError(true);
				getResultBean().setMensaje(mensajeError);
				addActionError(mensajeError);
				resumen = actualizaResumen(resumen, otros_index, total_index, false);
				return "importarDGT";
			}
		} catch (Exception ex) {
			log.error(ex);
			addActionError(ex.toString());
			return Action.ERROR;

		} catch (OegamExcepcion ex) {
			log.error(ex);
			addActionError(ex.toString());
			return Action.ERROR;
		}

		File ficheroSolicitud = getFichero();
		ResultBean resultadoImportacion = importarXMLSolicitud(idUsuario, ficheroSolicitud, numColegiado, idContrato);

		// Gestor de Ficheros
		if (!resultadoImportacion.getError()) {
			FicheroBean fichero = new FicheroBean();
			fichero.setFichero(getFichero());
			fichero.setTipoDocumento(ConstantesGestorFicheros.IMPORTACION);
			fichero.setSubTipo(ConstantesGestorFicheros.SUBTIPO_DOC_SOLICITUD);
			fichero.setExtension(ConstantesGestorFicheros.EXTENSION_XML);
			Fecha f = new Fecha(new Date());
			fichero.setFecha(f);
			String fecha = f.getDia() + "_" + f.getHora() + f.getMinutos() + f.getSegundos();
			fichero.setNombreDocumento(idUsuario + "_" + idContrato + "_" + fecha);
			try {
				gestorDocumentos.guardarFichero(fichero);
			} catch (OegamExcepcion e) {
				log.error("Error al custodiar el importado XML de Solicitud: " + idUsuario + "_" + idContrato + "_" + fecha);
			}
		}

		return accionRetorno;
	}

	/**
	 * Método que importa un XML de solicitudes de tramites.
	 * @param idUsuario idUsuario.
	 * @param ficheroDuplicado fichero con las solicitudes a importar.
	 * @param numColegiado numero de colegiado.
	 * @param idContrato id de contrato.
	 * @return String - accion.
	 */
	public ResultBean importarXMLSolicitud(BigDecimal idUsuario, File ficheroSolicitado, String numColegiado, BigDecimal idContrato) {
		// String accionRetorno = "importarDGT";
		ResultBean resultadoImportacion = new ResultBean();
		int solicitud_index = 0;
		int otros_index = 1;
		int total_index = 2;

		// Si esta validado, continuamos parseando el xml
		try {
			XMLSolicitudFactory xMLSolicitudFactory = new XMLSolicitudFactory();
			xMLSolicitudFactory.validarXMLFORMATOOEGAM2SOLICITUD(ficheroSolicitado, gestorPropiedades.valorPropertie(PropertiesConstantes.RUTA_DIRECTORIO_DATOS) + gestorPropiedades.valorPropertie(
					Constantes.PROPERTIES_RUTA_ESQUEMA_SOLICITUD));

			// Construyo un objeto de tipo Solicitud
			FORMATOOEGAM2SOLICITUD solicitud = xMLSolicitudFactory.getFormatoOegam2Solicitud(ficheroSolicitado);

			try {
				if (solicitud.getCABECERA() == null || solicitud.getCABECERA().getDATOSGESTORIA() == null || solicitud.getCABECERA().getDATOSGESTORIA().getPROFESIONAL() == null || "".equals(solicitud
						.getCABECERA().getDATOSGESTORIA().getPROFESIONAL())) {
					addActionError("El fichero no tiene número de profesional, no se importaron sus trámites");
					resultadoImportacion.setError(true);
					resumen = actualizaResumen(resumen, otros_index, total_index, false);
					return resultadoImportacion;
				}

			} catch (Exception e) {
				log.error(e);
				addActionError("Error en la cabecera del fichero");
				resultadoImportacion.setError(true);
				resumen = actualizaResumen(resumen, otros_index, total_index, false);
			}

			// Numero de profesional del fichero y lista de tramites
			String numColegiadoXML = solicitud.getCABECERA().getDATOSGESTORIA().getPROFESIONAL();
			List<SolicitudDatosBean> listaTramiteTraficoSolicitud = fORMATOOEGAM2SOLICITUDConversion.convertirFORMATOOEGAM2SOLICITUDtoListaSolicitudDatosBean(solicitud, numColegiado, idContrato);

			/* INICIO PROCESAMIENTO DE SOLICITUDES */

			// Si el colegiado de sesion tiene permiso de administracion: se procesa el tramite
			if (utilesColegiado.tienePermisoAdmin()) {
				// Se comprueba antes si el numero de profesional del fichero coincide con el contrato seleccionado por el administrador
				if (!numColegiadoXML.equals(numColegiado)) {
					String mensajeError = "El colegiado (" + numColegiado + ") no es el mismo que el número de profesional del fichero (" + numColegiadoXML + ")";
					addActionError(mensajeError);
					resultadoImportacion.setError(true);
					resumen = actualizaResumen(resumen, otros_index, total_index, false);
					return resultadoImportacion;
				}

				// Guardamos las solicitudes de tramites
				importarListaSolicitudesTramites(listaTramiteTraficoSolicitud, solicitud_index, total_index);
			}

			// Si el colegiado de sesion tiene permiso de colegiado:
			// ---> Se comprueba que el numero de colegiado del archivo pertenece al contrato del colegio del colegiado de sesión (se procesa en caso afirmativo)
			else if (utilesColegiado.tienePermisoColegio()) {
				// Se comprueba antes si el numero de profesional del fichero coincide con alguno de los colegiados
				List<String> listaColegiados = utilesColegiado.getNumColegiadosDelContrato();
				Boolean puede = false;
				int j = 0;
				while ((!puede) && (listaColegiados.size() > j)) {
					puede = puede || numColegiadoXML.equals(listaColegiados.get(j));
					j++;
				}
				if (!puede) {
					String mensajeError = "El número de profesional del fichero (" + numColegiadoXML + ") no coincide con ninguno de los colegiados del colegio";
					addActionError(mensajeError);
					resultadoImportacion.setError(true);
					resumen = actualizaResumen(resumen, otros_index, total_index, false);
					return resultadoImportacion;
				}

				// Guardamos las solicitudes de trámites
				importarListaSolicitudesTramites(listaTramiteTraficoSolicitud, solicitud_index, total_index);
			} else { // Si el usuario de sesion es un colegiado cualquiera
				// Se comprueba antes si el número de profesional del fichero coincide con el contrato de sesión
				if (!numColegiadoXML.equals(numColegiado)) {
					String mensajeError = "El colegiado (" + numColegiado + ") no es el mismo que el número de profesional del fichero (" + numColegiadoXML + ")";
					addActionError(mensajeError);
					resultadoImportacion.setError(true);
					resumen = actualizaResumen(resumen, otros_index, total_index, false);
					return resultadoImportacion;
				}

				// Guardamos las solicitudes de tramites
				importarListaSolicitudesTramites(listaTramiteTraficoSolicitud, solicitud_index, total_index);
			}

			/* FIN PROCESAMIENTO DE SOLICITUDES */
		} catch (Exception e) {
			log.error(e);
			addActionError("Ha habido un error " + e.toString());
			resultadoImportacion.setError(true);
			resumen = actualizaResumen(resumen, otros_index, total_index, false);

		} catch (XmlNoValidoExcepcion e) {
			log.error(e);
			addActionError("Ha habido un error al parsear el XML " + e.toString().substring(49));
			resultadoImportacion.setError(true);
			resumen = actualizaResumen(resumen, otros_index, total_index, false);
		}

		return resultadoImportacion;
	}

	/**
	 * Metodo que importa en el sistema una lista de solicitudes de tramites.
	 * @param listaTramiteTraficoSolicitud lista a importar.
	 * @param solicitud_index.
	 * @param total_index total_index.
	 */
	private void importarListaSolicitudesTramites(List<SolicitudDatosBean> listaTramiteTraficoSolicitud, int solicitud_index, int total_index) {
		int i = 0;
		for (SolicitudDatosBean tramiteTraficoSolicitado : listaTramiteTraficoSolicitud) {

			HashMap<String, Object> resultado = getModeloSolicitudDatos().guardarSolicitudDatos(tramiteTraficoSolicitado);

			if (((ResultBean) resultado.get(ConstantesPQ.RESULTBEAN)).getError()) {
				addActionError("-Tramite " + i + ": " + ((ResultBean) resultado.get("ResultBean")).getMensaje());
				resumen = actualizaResumen(resumen, solicitud_index, total_index, false);

			} else {
				// addActionMessage("-Tramite " + i + ": " + ((ResultBean) resultado.get("ResultBean")) .getMensaje());
				String numExpediente = "";
				SolicitudDatosBean tramiteResultado = ((SolicitudDatosBean) resultado.get(ConstantesPQ.BEANPANTALLA));
				if (tramiteResultado != null && tramiteResultado.getTramiteTrafico() != null && tramiteResultado.getTramiteTrafico().getNumExpediente() != null) {
					numExpediente = tramiteResultado.getTramiteTrafico().getNumExpediente().toString();
				}
				addActionMessage("-Tramite " + i + ": importado correctamente (numExpediente '" + numExpediente + "').");
				resumen = actualizaResumen(resumen, solicitud_index, total_index, true);
			}
			i++;
		}
	}

	public String importarAVPO() {
		int avpo_index = 0;
		int otros_index = 1;
		int total_index = 2;
		boolean custodiarArchivo = true;

		resumen.add(avpo_index, new ResumenImportacion("Solicitud de datos"));
		resumen.add(otros_index, new ResumenImportacion("Otros"));
		resumen.add(total_index, new ResumenImportacion("TOTAL"));

		// Si no se rellena el input file se direcciona de nuevo a la pagina de
		// cargar fichero
		if (getFichero() == null || !utiles.esNombreFicheroValido(getFicheroFileName())) {
			log.error("Error al importar el fichero");
			setResultBean(new ResultBean());
			getResultBean().setError(true);
			getResultBean().setMensaje(ERROR_IMPORTAR_FICHERO);
			addActionError(ERROR_IMPORTAR_FICHERO);
			custodiarArchivo = false;
			resumen = actualizaResumen(resumen, otros_index, total_index, false);
			return "importarDGT";
		}

		// Incidencia : Mantis 1951 (Ricardo Rodriguez 15/06/2012)
		// Descripcion : Verificar permiso de importación de todo tipo de
		// ficheros
		try {
			String resultado = utilesColegiado.tienePermisoImportacion(FicherosImportacion.IMPORTAR_SOLICITUD);
			if (resultado != null && !resultado.equalsIgnoreCase(Constantes.TRUE)) {
				// El usuario no tiene permiso para importar este trámite.
				// Recupera de un properties el mensaje al usuario
				String mensajeError = FicherosImportacion.IMPORTAR_SOLICITUD.getNombreEnum();
				setResultBean(new ResultBean());
				getResultBean().setError(true);
				getResultBean().setMensaje(mensajeError);
				addActionError(mensajeError);
				custodiarArchivo = false;
				resumen = actualizaResumen(resumen, otros_index, total_index, false);
				return "importarDGT";
			}
		} catch (Exception ex) {
			log.error(ex);
			addActionError(ex.toString());
			custodiarArchivo = false;
			return Action.ERROR;
		} catch (OegamExcepcion ex) {
			log.error(ex);
			addActionError(ex.toString());
			custodiarArchivo = false;
			return Action.ERROR;
		}
		// Fin incidencia Mantis 1951

		if (UtilesVistaTrafico.getInstance().nuevoInteve()) {
			ResultBean resultado = servicioInteve.importarInteve(getFichero(), getContratoImportacion(), utilesColegiado.getIdUsuarioSessionBigDecimal());
			if (!resultado.getError()) {
				int lineasError = (Integer) resultado.getAttachment("lineasError");
				int lineasOk = (Integer) resultado.getAttachment("lineasOk");
				if (lineasError > 0) {
					for (int i = 0; i < lineasError; i++) {
						resumen.get(total_index).addIncorrecto();
					}
					for (String mensaje : resultado.getListaMensajes()) {
						addActionError(mensaje);
					}
				}
				if (lineasOk > 0) {
					for (int i = 0; i < lineasOk; i++) {
						resumen.get(total_index).addCorrecto();
					}
					addActionMessage("La solicitud con numero de expediente " + resultado.getAttachment("numExpediente") + " se ha generado correctamente.");
				}
			} else {
				addActionError(resultado.getMensaje());
				resumen = actualizaResumen(resumen, otros_index, total_index, false);
			}

		} else {
			// Leemos las líneas del fichero
			List<String> lineas;
			try {
				lineas = utiles.obtenerLineasFicheroTexto(getFichero());
			} catch (Throwable e) {
				log.error("Error al importar el fichero: " + e);
				setResultBean(new ResultBean());
				getResultBean().setError(true);
				getResultBean().setMensaje(ERROR_IMPORTAR_FICHERO);

				addActionError(ERROR_IMPORTAR_FICHERO);
				custodiarArchivo = false;
				resumen = actualizaResumen(resumen, otros_index, total_index, false);
				return "importarDGT";
			}

			// El fichero no tiene líneas de trámites
			if (!(!lineas.isEmpty())) {
				log.error("El fichero no tiene líneas de solicitud");
				setResultBean(new ResultBean());
				getResultBean().setError(true);
				getResultBean().setMensaje(ERROR_FICHERO_SIN_SOLICUTUD);
				addActionError(ERROR_FICHERO_SIN_SOLICUTUD);
				custodiarArchivo = false;
				resumen = actualizaResumen(resumen, otros_index, total_index, false);
				return "importarDGT";
			}

			boolean errorFormato = false;
			int lineaTipo = 0;
			IntervinienteTrafico solicitante = null;

			// La cabecera del fichero no es ni por matrícula ni por bastidor
			if (!"MATRICULA".equalsIgnoreCase(lineas.get(0)) && !"BASTIDOR".equalsIgnoreCase(lineas.get(0))) {
				// Comprobar si la primera linea viene informada con el solicitante
				solicitante = obtenerSolicitante(lineas.get(0).toUpperCase());
				if (solicitante != null) {
					lineaTipo = 1;
					if (!"MATRICULA".equalsIgnoreCase(lineas.get(lineaTipo)) && !"BASTIDOR".equalsIgnoreCase(lineas.get(lineaTipo))) {
						errorFormato = true;
					}
				} else {
					errorFormato = true;
				}
			}

			if (errorFormato) {
				log.error("El fichero no tiene líneas de solicitud");
				setResultBean(new ResultBean());
				getResultBean().setError(true);
				getResultBean().setMensaje(ERROR_FICHERO_SIN_SOLICUTUD);
				addActionError(ERROR_FICHERO_SIN_SOLICUTUD);
				custodiarArchivo = false;
				resumen = actualizaResumen(resumen, otros_index, total_index, false);
				return "importarDGT";
			}

			boolean matricula = false;
			boolean bastidor = false;

			if ("MATRICULA".equalsIgnoreCase(lineas.get(lineaTipo)))
				matricula = true;
			else if ("BASTIDOR".equalsIgnoreCase(lineas.get(lineaTipo)))
				bastidor = true;

			SolicitudDatosBean bean = new SolicitudDatosBean();
			List<SolicitudVehiculoBean> solicitudesVehiculos = new ArrayList<>();

			TramiteTraficoBean tramiteTrafico = new TramiteTraficoBean();
			tramiteTrafico.setIdContrato(getContratoImportacion());
			tramiteTrafico.setIdUsuario(utilesColegiado.getIdUsuarioSessionBigDecimal());
			bean.setTramiteTrafico(tramiteTrafico);
			bean.setSolicitante(solicitante);

			List<Integer> lineasErrores = new ArrayList<>();
			// FOR CON LAS LINEAS DEL FICHERO
			for (int i = lineaTipo + 1; i < lineas.size(); i++) {
				String[] separado = lineas.get(i).split(";");
				if (separado.length == 2) {
					SolicitudVehiculoBean solicitud = new SolicitudVehiculoBean();
					Tasa tasa = new Tasa();
					tasa.setCodigoTasa(separado[1]);
					solicitud.setTasa(tasa);
					VehiculoBean vehiculo = new VehiculoBean();
					if (matricula)
						vehiculo.setMatricula(separado[0]);
					else if (bastidor)
						vehiculo.setBastidor(separado[0]);
					solicitud.setVehiculo(vehiculo);
					solicitudesVehiculos.add(solicitud);
				} else {
					addActionError("La linea " + i + " del fichero no contiene los datos esperados");
					resumen = actualizaResumen(resumen, avpo_index, total_index, false);
					custodiarArchivo = false;
					lineasErrores.add(i);
				}
			}

			bean.setSolicitudesVehiculos(solicitudesVehiculos);

			/* INICIO MANTIS 0012591 */
			/* INICIO VALIDACION MÁXIMO SOLICITUDES POR FICHERO */

			// Si está configurado AVPO o si el usuario NO es usuario de pruebas INTEVE
			// limitación de N solicitudes por trámite:
			boolean errorNumSolicitudes = false;
			int maximoNumSolicitudes = ConstantesTrafico.NUM_MAXIMO_SOLICITUDES_POR_DEFECTO;
			try {
				maximoNumSolicitudes = Integer.parseInt(gestorPropiedades.valorPropertie(ConstantesTrafico.LIMITACION_NUMERO_SOLICITUDES));
			} catch (Exception e) {
				log.error("No se puede recuperar el número máximo de solicitudes", e);
			}

			String servidor = UtilesVistaTrafico.getInstance().servidorInformesTecnicos();
			String usuarioPruebasInteve = UtilesVistaTrafico.getInstance().sesionUsuarioPruebasInteve();
			if ((servidor == null || !servidor.equalsIgnoreCase("INTEVE")) && (usuarioPruebasInteve == null || usuarioPruebasInteve.equalsIgnoreCase("NO"))) {
				if (bean.getSolicitudesVehiculos() != null && bean.getSolicitudesVehiculos().size() >= maximoNumSolicitudes) {
					errorNumSolicitudes = true;
				}
			}

			if (errorNumSolicitudes) {
				String mensajeError = "No puede guardar más de " + maximoNumSolicitudes + " solicitudes en un mismo fichero. Número de solicitudes del fichero utilizado: " + bean
						.getSolicitudesVehiculos().size();
				log.error(mensajeError);
				setResultBean(new ResultBean());
				getResultBean().setError(true);
				getResultBean().setMensaje(mensajeError);
				addActionError(mensajeError);
				custodiarArchivo = false;
				resumen = actualizaResumen(resumen, otros_index, total_index, false);
				return "importarDGT";
			}
			/* FIN VALIDACIÓN MÁXIMO SOLICITUDES POR FICHERO */
			/* INICIO MANTIS 0012591 */

			HashMap<String, Object> resultado = getModeloSolicitudDatos().guardarSolicitudDatos(bean);
			ResultBean resultBean = (ResultBean) resultado.get(ConstantesPQ.RESULTBEAN);

			if (resultBean.getError()) {
				// Si hay error, es que hay alguna solicitud fallida, pero no todas
				for (int j = 0; j < resultBean.getListaMensajes().size(); j++) {
					// Si es de error
					if (esSolicitudDeError(resultBean.getListaMensajes().get(j))) {
						addActionError("Error en la solicitud " + lineaCorregida(lineasErrores, j + 1) + ": " + resultBean.getListaMensajes().get(j));
						resumen = actualizaResumen(resumen, avpo_index, total_index, false);
						custodiarArchivo = false;
					} else {
						addActionMessage("Solicitud " + lineaCorregida(lineasErrores, j + 1) + ": " + resultBean.getListaMensajes().get(j));
						resumen = actualizaResumen(resumen, avpo_index, total_index, true);
					}
				}
			} else {
				addActionMessage("Se han importado " + bean.getSolicitudesVehiculos().size() + " solicitudes correctamente");
				resumen = actualizaResumen(resumen, avpo_index, total_index, true);
			}

			if (custodiarArchivo) {
				BigDecimal idContrato = utilesColegiado.getIdContratoSessionBigDecimal();
				BigDecimal idUsuario = utilesColegiado.getIdUsuarioSessionBigDecimal();

				// Gestor de Ficheros
				FicheroBean fichero = new FicheroBean();
				fichero.setFichero(getFichero());
				fichero.setTipoDocumento(ConstantesGestorFicheros.IMPORTACION);
				fichero.setSubTipo(ConstantesGestorFicheros.SUBTIPO_DOC_SOLICITUD);
				fichero.setExtension(ConstantesGestorFicheros.EXTENSION_TXT);
				Fecha f = new Fecha(new Date());
				fichero.setFecha(f);
				String fecha = f.getDia() + "_" + f.getHora() + f.getMinutos() + f.getSegundos();
				fichero.setNombreDocumento(idUsuario + "_" + idContrato + "_" + fecha);
				try {
					gestorDocumentos.guardarFichero(fichero);
				} catch (OegamExcepcion e) {
					log.error("Error al custodiar el importado TXT de Solicitud: " + idUsuario + "_" + idContrato + "_" + fecha);
				}
			}
		}
		return "importarDGT";
	}

	/**
	 * Trata de obtener el solicitante desde el fichero pasado, con la siguiente estructura [<NIF/CIF>;<APELLIDO1/RAZONSOCIAL>[;<APELLIDO2>[;<NOMBRE>]]]
	 * @param upperCase
	 * @return
	 */
	private IntervinienteTrafico obtenerSolicitante(String linea) {
		if (linea != null) {
			try {
				// Si viene en ISO, tratar de convertir a UTF-8
				if (new String(linea.getBytes(Claves.ENCODING_ISO88591), Claves.ENCODING_ISO88591).equals(linea)) {
					linea = new String(linea.getBytes(Claves.ENCODING_ISO88591), Claves.ENCODING_UTF8);
				}
			} catch (UnsupportedEncodingException e) {
				log.error("Error tratando de convertir a UTF-8", e);
			}
			String[] token = linea.split(";");
			if (token.length >= 2 && token.length <= 4) {
				String nif = token[0];
				// Si NIF no valido return null
				BigDecimal resultValida = NIFValidator.validarNif(nif);
				if (resultValida == null || resultValida.intValue() <= 0) {
					return null;
				}
				String apellido1RazonSocial = token[1];
				String apellido2 = null;
				String nombre = null;
				if (token.length > 2) {
					apellido2 = token[2];
					if (token.length > 3) {
						nombre = token[3];
					}
				}
				IntervinienteTrafico intervinienteTrafico = new IntervinienteTrafico();
				intervinienteTrafico.setPersona(new Persona());
				intervinienteTrafico.getPersona().setNif(nif);
				intervinienteTrafico.getPersona().setApellido1RazonSocial(apellido1RazonSocial);
				intervinienteTrafico.getPersona().setApellido2(apellido2);
				intervinienteTrafico.getPersona().setNombre(nombre);
				return intervinienteTrafico;
			}
		}
		return null;
	}

	private int lineaCorregida(List<Integer> lineasErrores, int i) {
		int suma = 0;
		for (int j = 0; j < lineasErrores.size(); j++) {
			if (lineasErrores.get(j) <= i) {
				suma++;
			}
		}
		return i + suma;
	}

	/**
	 * Devuelve si el mensaje del ModeloSolicitudDatos es un mensaje de error o de solicitud correcta
	 * @param mensaje
	 * @return
	 */
	public boolean esSolicitudDeError(String mensaje) {
		if (mensaje.startsWith("- Error al guardar"))
			return true;
		else
			return false;
	}

	public File getFichero() {
		return fichero;
	}

	public void setFichero(File fichero) {
		this.fichero = fichero;
	}

	public String getFicheroFileName() {
		return ficheroFileName;
	}

	public void setFicheroFileName(String ficheroFileName) {
		this.ficheroFileName = ficheroFileName;
	}

	public String getFicheroContentType() {
		return ficheroContentType;
	}

	public void setFicheroContentType(String ficheroContentType) {
		this.ficheroContentType = ficheroContentType;
	}

	public Boolean getMenorTamMax() {
		return menorTamMax;
	}

	public void setMenorTamMax(Boolean menorTamMax) {
		this.menorTamMax = menorTamMax;
	}

	public String getTipoFichero() {
		return tipoFichero;
	}

	public void setTipoFichero(String tipoFichero) {
		this.tipoFichero = tipoFichero;
	}

	public ResultBean getResultBean() {
		return resultBean;
	}

	public void setResultBean(ResultBean resultBean) {
		this.resultBean = resultBean;
	}

	public List<TramiteTraficoBean> getListaInsertados() {
		return listaInsertados;
	}

	public void setListaInsertados(List<TramiteTraficoBean> listaInsertados) {
		this.listaInsertados = listaInsertados;
	}

	public List<ResumenImportacion> getResumen() {
		return resumen;
	}

	public BigDecimal getContratoImportacion() {
		return contratoImportacion;
	}

	public void setContratoImportacion(BigDecimal contratoImportacion) {
		this.contratoImportacion = contratoImportacion;
	}

	public String dimeTramite(String reg) {
		if ("010".equals(reg))
			return "Solicitud";
		if ("020".equals(reg))
			return "Baja";
		if ("030".equals(reg))
			return "Transmisión";
		if ("040".equals(reg))
			return "Matriculación";
		return "desconocido";
	}

	public void setFicheroErrores(InputStream ficheroErrores) {
		this.ficheroErrores = ficheroErrores;
	}

	public InputStream getFicheroErrores() {
		return ficheroErrores;
	}

	/**
	 * Método que valida un XML de importación CET (cet.xsd)
	 * @param fichero xml a validar
	 * @return Devolverá XML_CORRECTO si es válido, en cualquier otro caso devolverá el string de la excepción de validación
	 */
	public String validarXMLCET(File fich) {
		// Constantes para validacion de Schemas
		final String W3C_XML_SCHEMA = "http://www.w3.org/2001/XMLSchema";
		URL esquema = this.getClass().getResource("/trafico/schemas/cet.xsd");
		final String MY_SCHEMA = esquema.getFile();

		try {
			// get validation driver:
			SchemaFactory factory = SchemaFactory.newInstance(W3C_XML_SCHEMA);
			// creamos el schema leyéndolo desde el XSD
			Schema schema = factory.newSchema(new StreamSource(MY_SCHEMA));
			Validator validator = schema.newValidator();
			XMLManejadorErrores errores = new XMLManejadorErrores();
			validator.setErrorHandler(errores);
			// Validamos el XML, si no lo valida devolverá el string de la
			// excepción, si lo valida devolverá 'CORRECTO'
			validator.validate(new StreamSource(fich));
			if (!errores.getListaErrores().isEmpty()) {
				String mensaje = "";
				for (int i = 0; i < errores.getListaErrores().size(); i++) {
					if (i < errores.getListaErrores().size() - 1)
						mensaje += errores.getListaErrores().get(i) + ". ";
					else
						mensaje += errores.getListaErrores().get(i);
				}
				return mensaje;
			}
		} catch (SAXParseException e) {
			log.error(e);
			String error = "";
			error += "Error en la validación del archivo XML:";
			error += " -Linea: " + e.getLineNumber();
			error += " -Columna: " + e.getColumnNumber();
			error += " -Mensaje: " + e.getMessage();
			return error;

		} catch (SAXException saxEx) {
			log.error(saxEx);
			return saxEx.toString();
		} catch (Exception ex) {
			log.error(ex);
			return ex.toString();
		}
		return XML_VALIDO;
	}

	/*
	 * Método para importación, xml valido CET
	 */
	public String importarCET() {
		String accionRetorno = "importarDGT";
		int matriculacion_index = 0;
		int baja_index = 1;
		int transmision_index = 2;
		int cet_index = 3;
		int otros_index = 4;
		int total_index = 5;
		boolean custodiarArchivo = true;

		resumen.add(matriculacion_index, new ResumenImportacion("Matriculación"));
		resumen.add(baja_index, new ResumenImportacion("Baja"));
		resumen.add(transmision_index, new ResumenImportacion("Transmisión"));
		resumen.add(cet_index, new ResumenImportacion("CET"));
		resumen.add(otros_index, new ResumenImportacion("Otros"));
		resumen.add(total_index, new ResumenImportacion("TOTAL"));
		CamposRespuestaPLSQL respuestaPlsql = new CamposRespuestaPLSQL();

		// Si no se rellena el input file se direcciona de nuevo a la pagina de
		// cargar fichero
		if (getFichero() == null || !utiles.esNombreFicheroValido(getFicheroFileName())) {
			log.error("Error al importar el fichero");
			setResultBean(new ResultBean());
			getResultBean().setError(true);
			getResultBean().setMensaje(ERROR_IMPORTAR_FICHERO);
			addActionError(ERROR_IMPORTAR_FICHERO);
			custodiarArchivo = false;
			resumen = actualizaResumen(resumen, otros_index, total_index, false);
			return "importarDGT";
		}

		// Incidencia : Mantis 1951 (Ricardo Rodriguez 15/06/2012)
		// Descripcion : Verificar permiso de importación de todo tipo de
		// ficheros
		try {
			String resultado = utilesColegiado.tienePermisoImportacion(FicherosImportacion.IMPORTAR_XML_CET);
			if (resultado != null && !resultado.equalsIgnoreCase(Constantes.TRUE)) {
				// El usuario no tiene permiso para importar este trámite.
				// Recupera de un properties el mensaje al usuario
				String mensajeError = FicherosImportacion.IMPORTAR_XML_CET.getNombreEnum();
				setResultBean(new ResultBean());
				getResultBean().setError(true);
				getResultBean().setMensaje(mensajeError);
				addActionError(mensajeError);
				custodiarArchivo = false;
				resumen = actualizaResumen(resumen, otros_index, total_index, false);
				return "importarDGT";
			}
		} catch (Exception ex) {
			log.error(ex);
			addActionError(ex.toString());
			custodiarArchivo = false;
			return Action.ERROR;
		} catch (OegamExcepcion ex) {
			log.error(ex);
			addActionError(ex.toString());
			custodiarArchivo = false;
			return Action.ERROR;
		}
		// Fin incidencia Mantis 1951

		// Si está validado, continuamos parseando el xml
		try {

			// Validamos el fichero seleccionado.

			String resultadoValidar = validarXMLCET(getFichero());
			if (!XML_VALIDO.equals(resultadoValidar)) {
				addActionError(resultadoValidar);
				custodiarArchivo = false;
				resumen = actualizaResumen(resumen, cet_index, total_index, false);
				return "importarDGT";
			} else {
				// Metemos el paquete donde están las clases que creamos a
				// partir de ga.xsd mediante xjc.exe
				Unmarshaller unmarshaller = new XMLGAFactory().getCETImportacionContext().createUnmarshaller();
				trafico.beans.jaxb.cet_import.OptiCET620 ga = (trafico.beans.jaxb.cet_import.OptiCET620) unmarshaller.unmarshal(getFichero());

				// Una vez obtenida la lista de autoquidaciones, llamaremos al
				// método para la inserción en la base de datos.
				for (int i = 0; i < ga.getAutoliquidacion().size(); i++) {
					// DRC@28-02-2013 Incidencia Mantis: 3730
					if (getContratoImportacion() == null)
						setContratoImportacion(utilesColegiado.getIdContratoSessionBigDecimal());

					respuestaPlsql = getModeloImportacionDGT().importarCET(ga.getAutoliquidacion().get(i), utilesColegiado.getIdUsuarioSessionBigDecimal(), getContratoImportacion(), utilesColegiado
							.getIdContratoSessionBigDecimal());

					if (respuestaPlsql.getRespuesta() == null) {
						if (respuestaPlsql.getPSqlErrm().equals("Correcto")) {
							addActionMessage(respuestaPlsql.getPSqlErrm());
							resumen = actualizaResumen(resumen, cet_index, total_index, true);
						} else {
							addActionError(respuestaPlsql.getPSqlErrm());
							custodiarArchivo = false;
							resumen = actualizaResumen(resumen, cet_index, total_index, false);
						}
					}
				}
			}

		} catch (JAXBException e) {
			log.error("Ha habido un error al parsear el XML", e);
			addActionError("Ha habido un error al parsear el XML");
			custodiarArchivo = false;
			resumen = actualizaResumen(resumen, cet_index, total_index, false);
		}

		if (custodiarArchivo) {
			BigDecimal idContrato = utilesColegiado.getIdContratoSessionBigDecimal();
			BigDecimal idUsuario = utilesColegiado.getIdUsuarioSessionBigDecimal();

			// Gestor de Ficheros
			FicheroBean fichero = new FicheroBean();
			fichero.setFichero(getFichero());
			fichero.setTipoDocumento(ConstantesGestorFicheros.IMPORTACION);
			fichero.setSubTipo(ConstantesGestorFicheros.SUBTIPO_DOC_CET);
			fichero.setExtension(ConstantesGestorFicheros.EXTENSION_XML);
			Fecha f = new Fecha(new Date());
			fichero.setFecha(f);
			String fecha = f.getDia() + "_" + f.getHora() + f.getMinutos() + f.getSegundos();
			fichero.setNombreDocumento(idUsuario + "_" + idContrato + "_" + fecha);
			try {
				gestorDocumentos.guardarFichero(fichero);
			} catch (OegamExcepcion e) {
				log.error("Error al custodiar el importado XML de CET: " + idUsuario + "_" + idContrato + "_" + fecha);
			}
		}

		return accionRetorno;
	}

	/*
	 * Método que importará los trámite de baja, incluidos en la excel, procedente de la DGT
	 */
	public String importarExcelBajas() throws IOException {
		String jefatura = null;
		String accionRetorno = "importarDGT";
		int matriculacion_index = 0;
		int baja_index = 1;
		int transmision_index = 2;
		int cet_index = 3;
		int otros_index = 4;
		int total_index = 5;
		boolean custodiarArchivo = true;

		resumen.add(matriculacion_index, new ResumenImportacion("Matriculación"));
		resumen.add(baja_index, new ResumenImportacion("Baja"));
		resumen.add(transmision_index, new ResumenImportacion("Transmisión"));
		resumen.add(cet_index, new ResumenImportacion("CET"));
		resumen.add(otros_index, new ResumenImportacion("Otros"));
		resumen.add(total_index, new ResumenImportacion("TOTAL"));

		// Si no se rellena el input file se direcciona de nuevo a la pagina de
		// cargar fichero
		if (getFichero() == null || !utiles.esNombreFicheroValido(getFicheroFileName())) {
			log.error("Error al importar el fichero");
			setResultBean(new ResultBean());
			getResultBean().setError(true);
			getResultBean().setMensaje(ERROR_IMPORTAR_FICHERO);
			addActionError(ERROR_IMPORTAR_FICHERO);
			custodiarArchivo = false;
			resumen = actualizaResumen(resumen, otros_index, total_index, false);
			return "importarDGT";
		}

		// Incidencia : Mantis 1951 (Ricardo Rodriguez 15/06/2012)
		// Descripcion : Verificar permiso de importación de todo tipo de
		// ficheros
		try {
			String resultado = utilesColegiado.tienePermisoImportacion(FicherosImportacion.IMPORTAR_XLS_BAJA);
			if (resultado != null && !resultado.equalsIgnoreCase(Constantes.TRUE)) {
				// El usuario no tiene permiso para importar este trámite.
				// Recupera de un properties el mensaje al usuario
				String mensajeError = FicherosImportacion.IMPORTAR_XLS_BAJA.getNombreEnum();
				setResultBean(new ResultBean());
				getResultBean().setError(true);
				getResultBean().setMensaje(mensajeError);
				addActionError(mensajeError);
				custodiarArchivo = false;
				resumen = actualizaResumen(resumen, otros_index, total_index, false);
				return "importarDGT";
			}
		} catch (Exception ex) {
			log.error(ex);
			addActionError(ex.toString());
			custodiarArchivo = false;
			return Action.ERROR;
		} catch (OegamExcepcion ex) {
			log.error(ex);
			addActionError(ex.toString());
			custodiarArchivo = false;
			return Action.ERROR;
		}
		// Fin incidencia Mantis 1951

		try {
			Workbook archivoExcel = Workbook.getWorkbook(getFichero());

			for (int sheetNo = 0; sheetNo < archivoExcel.getNumberOfSheets(); sheetNo++) {
				Sheet hoja = archivoExcel.getSheet(sheetNo);
				int numFilas = hoja.getRows();
				String numeroExpediente = "";
				String causaNoResolucion = "";
				int resolucion;
				// int num_col_resol=17; // 2984 modificacion sitex se ha
				// incrementado nueva columna FECHA_PRESENTACION
				// int num_col_resol=19; // JMC - Modificacion Nueva Plantilla
				// Bajas 22/11/2013 por la nueva Baja Motivo Carta Dgt
				// Numero de la columna resolucion
				int numColResol = 22; // ATC - Modificacion Nueva Plantilla
									  // Bajas 28/11/2014 por nuevo campo
									  // CERTIFICADO_MEDIOAMBIENTAL
				// int caus_no_resol=18; // 2984 modificacion sitex se ha
				// incrementado nueva columna FECHA_PRESENTACION
				// int caus_no_resol=20; // JMC - Modificacion Nueva Plantilla
				// Bajas 22/11/2013 por la nueva Baja Motivo Carta Dgt
				// Numero de la columna descripcion resolución
				int causNoResol = 23; // ATC - Modificacion Nueva Plantilla
									  // Bajas 28/11/2014 por nuevo campo
									  // CERTIFICADO_MEDIOAMBIENTAL
				// Una vez recuperada la hoja, comenzamos a leer para
				for (int fila = 1; fila < numFilas; fila++) { // Recorre cada
															  // fila de la
															  // hoja
					try {
						// Obtenemos el número de expediente de cada uno de los trámites
						
						if (hoja.getCell(1, fila) == null || hoja.getCell(1, fila).getContents() == null) {
							numeroExpediente = "";
						} else {
							numeroExpediente = hoja.getCell(1, fila).getContents();
							if (jefatura == null) {
								jefatura = getJefaturaTramite(numeroExpediente);
							}
						}
						if (hoja.getCell(numColResol, fila) == null || hoja.getCell(numColResol, fila).getContents() == null || "".equals(hoja.getCell(numColResol, fila).getContents())) {
							addActionError("Trámite con número expediente: " + numeroExpediente + ": " + " No contiene respuesta");
							resumen = actualizaResumen(resumen, baja_index, total_index, false);
							custodiarArchivo = false;
						} else {
							resolucion = new Integer(hoja.getCell(numColResol, fila).getContents()).intValue();
							causaNoResolucion = hoja.getCell(causNoResol, fila).getContents();
							BeanPQCambiarEstadoTramite beanCambioEstado = new BeanPQCambiarEstadoTramite();
							beanCambioEstado.setP_NUM_EXPEDIENTE(new BigDecimal(numeroExpediente));
							// Si la resolución es 0, cambiamos de estado.
							if (resolucion == BAJA_REALIZADA_CORRECTAMENTE) {
								beanCambioEstado.setP_RESPUESTA("Trámite realizado correctamente");
								beanCambioEstado.setP_ESTADO(new BigDecimal(EstadoTramiteTrafico.Finalizado_Excel.getValorEnum()));
							} else {
								beanCambioEstado.setP_RESPUESTA(causaNoResolucion);
								beanCambioEstado.setP_ESTADO(new BigDecimal(EstadoTramiteTrafico.Finalizado_Excel_Incidencia.getValorEnum()));
							}
							HashMap<String, Object> resultadoModelo = getModeloTrafico().cambiarEstadoTramite(beanCambioEstado);
							ResultBean resultadoCambio = (ResultBean) resultadoModelo.get(ConstantesPQ.RESULTBEAN);
							BeanPQCambiarEstadoTramite beanPq = (BeanPQCambiarEstadoTramite) resultadoModelo.get(ConstantesPQ.BEANPQRESULTADO);
							if (resultadoCambio.getError()) {
								addActionError("Trámite con número expediente: " + numeroExpediente + ": " + resultadoCambio.getMensaje());
								// log.error("Trámite con número expediente: " + numeroExpediente + ": " + resultadoCambio.getMensaje());
								// log.error("P_CODE del PQ: " + beanPq.getP_CODE());
								// log.error("Mensaje de respuesta: " + beanPq.getP_RESPUESTA());
								// log.error("Codigo resulocion: " + resolucion);
								resumen = actualizaResumen(resumen, baja_index, total_index, false);
								custodiarArchivo = false;
							} else {
								if (resolucion == BAJA_REALIZADA_CORRECTAMENTE) {

									addActionMessage("El trámite con número: " + numeroExpediente + " ha sido importado correctamente");
									resumen = actualizaResumen(resumen, baja_index, total_index, true);
								} else {
									addActionError("El trámite con número: " + numeroExpediente + " no se ha dado de baja por: " + causaNoResolucion);
									custodiarArchivo = false;
									resumen = actualizaResumen(resumen, baja_index, total_index, false);
								}
							}
						}
					} catch (NumberFormatException e) {
						// log.error("Ha habido un error haciendo el parseo de datos");
						// log.error("El número de expediente es: " + numeroExpediente);
						log.error(e.toString());
						addActionError("Trámite con número expediente: " + numeroExpediente + ": " + " Error de datos");
						custodiarArchivo = false;
						resumen = actualizaResumen(resumen, baja_index, total_index, false);
					} catch (Exception e) {
						// log.error("Ha habido un error actualizando el resumen del expediente");
						// log.error("El número de expediente es: " + numeroExpediente);
						log.error(e.getMessage(), e);
						throw e;
					}
				}
			}
		} catch (BiffException ex) {
			log.error(ex);
			addActionError("Ha habido un error al leer el XLS");
			custodiarArchivo = false;
			resumen = actualizaResumen(resumen, baja_index, total_index, false);
			return accionRetorno;
		} catch (Exception e) {
			log.error(e);
			addActionError("Se ha producido una excepción general en el proceso");
			custodiarArchivo = false;
			resumen = actualizaResumen(resumen, baja_index, total_index, false);
			return accionRetorno;

		}

		if (custodiarArchivo) {
			// Mantis 19464. David Sierra: Custodia de Ficheros de Jefatura con la Respuesta de Bajas
			try {
				FicheroBean fichero = new FicheroBean();
				fichero.setTipoDocumento(ConstantesGestorFicheros.EXCELS);
				fichero.setSubTipo(ConstantesGestorFicheros.BAJAS_RESPUESTA_JEFATURA);
				fichero.setSobreescribir(true);
				fichero.setExtension(ConstantesGestorFicheros.EXTENSION_XLS);
				fichero.setFecha(utilesFecha.getFechaHoraActualLEG());
				Date fechaFichero = new Date();
				String nombreFichero = utiles.componerNombreFichero(ConstantesGestorFicheros.RESPUESTA_BAJAS + jefatura, fechaFichero);
				fichero.setNombreDocumento(nombreFichero);
				fichero.setFichero(getFichero());
				File ficheroResult = gestorDocumentos.guardarFichero(fichero);
				if (ficheroResult == null) {
					log.error("Ha sucedido un error a la hora de guardar el fichero xls de RespuestaBajas");
				}
			} catch (OegamExcepcion e) {
				log.error("Ha sucedido un error a la hora de guardar el fichero fichero xls de RespuestaBajas, error: ", e);
			}
		}
		return accionRetorno;
	}

	/*
	 * Método que importará los trámite de baja, incluidos en la excel, procedente de la DGT
	 */
	public String importarExcelDuplicados() throws IOException {
		String jefatura = null;
		String accionRetorno = "importarDGT";
		int matriculacion_index = 0;
		int baja_index = 1;
		int transmision_index = 2;
		int cet_index = 3;
		int dupl_index = 4;
		int otros_index = 5;
		int total_index = 6;
		int num_col_resol = 30; // 2984 modificacion sitex se ha incrementado
								// nueva columna FECHA_PRESENTACION.
		int caus_no_resol = 31; // 2984 modificacion sitex se ha incrementado
								// nueva columna FECHA_PRESENTACION.

		boolean custodiarArchivo = true;
		resumen.add(matriculacion_index, new ResumenImportacion("Matriculación"));
		resumen.add(baja_index, new ResumenImportacion("Baja"));
		resumen.add(transmision_index, new ResumenImportacion("Transmisión"));
		resumen.add(cet_index, new ResumenImportacion("CET"));
		resumen.add(dupl_index, new ResumenImportacion("Duplicados"));
		resumen.add(otros_index, new ResumenImportacion("Otros"));
		resumen.add(total_index, new ResumenImportacion("TOTAL"));

		// Si no se rellena el input file se direcciona de nuevo a la pagina de
		// cargar fichero
		if (getFichero() == null || !utiles.esNombreFicheroValido(getFicheroFileName())) {
			log.error("Error al importar el fichero");
			setResultBean(new ResultBean());
			getResultBean().setError(true);
			getResultBean().setMensaje(ERROR_IMPORTAR_FICHERO);
			addActionError(ERROR_IMPORTAR_FICHERO);
			custodiarArchivo = false;
			resumen = actualizaResumen(resumen, otros_index, total_index, false);
			return "importarDGT";
		}

		// Incidencia : Mantis 1951 (Ricardo Rodriguez 15/06/2012)
		// Descripcion : Verificar permiso de importación de todo tipo de
		// ficheros
		try {
			String resultado = utilesColegiado.tienePermisoImportacion(FicherosImportacion.IMPORTAR_XSL_DUPLICADO);
			if (resultado != null && !resultado.equalsIgnoreCase(Constantes.TRUE)) {
				// El usuario no tiene permiso para importar este trámite.
				// Recupera de un properties el mensaje al usuario
				String mensajeError = FicherosImportacion.IMPORTAR_XSL_DUPLICADO.getNombreEnum();
				setResultBean(new ResultBean());
				getResultBean().setError(true);
				getResultBean().setMensaje(mensajeError);
				addActionError(mensajeError);
				custodiarArchivo = false;
				resumen = actualizaResumen(resumen, otros_index, total_index, false);
				return "importarDGT";
			}
		} catch (Exception ex) {
			log.error(ex);
			addActionError(ex.toString());
			custodiarArchivo = false;
			return Action.ERROR;
		} catch (OegamExcepcion ex) {
			log.error(ex);
			addActionError(ex.toString());
			custodiarArchivo = false;
			return Action.ERROR;
		}
		// Fin incidencia Mantis 1951

		try {
			Workbook archivoExcel = Workbook.getWorkbook(getFichero());
			for (int sheetNo = 0; sheetNo < archivoExcel.getNumberOfSheets(); sheetNo++) {
				Sheet hoja = archivoExcel.getSheet(sheetNo);
				int numFilas = hoja.getRows();
				String numeroExpediente = "";
				String causaNoResolucion = "";
				int resolucion;
				// Una vez recuperada la hoja, comenzamos a leer para
				for (int fila = 1; fila < numFilas; fila++) { // Recorre cada
																// fila de la
																// hoja
					try {
						// Obtenemos el número de expediente de cada uno de los
						// trámites
						if (hoja.getCell(1, fila) == null || hoja.getCell(1, fila).getContents() == null) {
							numeroExpediente = "";
						} else {
							numeroExpediente = hoja.getCell(1, fila).getContents();
							if (jefatura == null) {
								jefatura = getJefaturaTramite(numeroExpediente);
							}
						}
						if (hoja.getCell(num_col_resol, fila) == null || hoja.getCell(num_col_resol, fila).getContents() == null || "".equals(hoja.getCell(num_col_resol, fila).getContents())) {
							addActionError("Trámite con número expediente: " + numeroExpediente + ": " + " No contiene respuesta");
							custodiarArchivo = false;
							resumen = actualizaResumen(resumen, dupl_index, total_index, false);
						} else {
							resolucion = new Integer(hoja.getCell(num_col_resol, fila).getContents()).intValue();
							causaNoResolucion = hoja.getCell(caus_no_resol, fila).getContents();
							BeanPQCambiarEstadoTramite beanCambioEstado = new BeanPQCambiarEstadoTramite();
							beanCambioEstado.setP_NUM_EXPEDIENTE(new BigDecimal(numeroExpediente));
							// Si la resolución es 0, cambiamos de estado.
							if (resolucion == 0) {
								beanCambioEstado.setP_ESTADO(new BigDecimal(EstadoTramiteTrafico.Finalizado_Excel.getValorEnum()));
								beanCambioEstado.setP_RESPUESTA("Trámite realizado correctamente");
								HashMap<String, Object> resultadoModelo = getModeloTrafico().cambiarEstadoTramite(beanCambioEstado);
								ResultBean resultadoCambio = (ResultBean) resultadoModelo.get(ConstantesPQ.RESULTBEAN);
								if (resultadoCambio.getError()) {
									addActionError("Trámite con número expediente: " + numeroExpediente + ": " + resultadoCambio.getMensaje());
									resumen = actualizaResumen(resumen, dupl_index, total_index, false);
									custodiarArchivo = false;
								} else {
									addActionMessage("El trámite con número: " + numeroExpediente + " ha sido importado correctamente");
									resumen = actualizaResumen(resumen, dupl_index, total_index, true);
									// Lo tendremos que añadir en el campo
									// anotaciones del trámite.
								}
							} else {
								beanCambioEstado.setP_ESTADO(new BigDecimal(EstadoTramiteTrafico.Finalizado_Excel_Incidencia.getValorEnum()));
								beanCambioEstado.setP_RESPUESTA(causaNoResolucion);
								getModeloTrafico().cambiarEstadoTramite(beanCambioEstado);
								addActionError("El trámite con número: " + numeroExpediente + " no se ha realizado por: " + causaNoResolucion);
								custodiarArchivo = false;
								resumen = actualizaResumen(resumen, dupl_index, total_index, false);
							}
						}
					} catch (NumberFormatException e) {
						// log.error("Ha habido un error haciendo el parseo de datos");
						// log.error("El número de expediente es: " + numeroExpediente);
						log.error(e.toString());
						addActionError("Trámite con número expediente: " + numeroExpediente + ": " + " Error de datos");
						custodiarArchivo = false;
						resumen = actualizaResumen(resumen, dupl_index, total_index, false);
					}
				}
			}

		} catch (BiffException ex) {
			log.error(ex);
			addActionError("Ha habido un error al leer el XLS");
			custodiarArchivo = false;
			resumen = actualizaResumen(resumen, dupl_index, total_index, false);
			return accionRetorno;
		} catch (Exception e) {
			log.error(e);
			addActionError("Se ha producido una excepción general en el proceso");
			custodiarArchivo = false;
			resumen = actualizaResumen(resumen, dupl_index, total_index, false);
			return accionRetorno;
		}

		if (custodiarArchivo) {
			// Mantis 19464. David Sierra: Custodia de Ficheros de Jefatura con la Respuesta de Duplicados
			try {
				FicheroBean fichero = new FicheroBean();
				fichero.setTipoDocumento(ConstantesGestorFicheros.EXCELS);
				fichero.setSubTipo(ConstantesGestorFicheros.DUPLICADOS_RESPUESTA_JEFATURA);
				fichero.setSobreescribir(true);
				fichero.setExtension(ConstantesGestorFicheros.EXTENSION_XLS);
				fichero.setFecha(utilesFecha.getFechaHoraActualLEG());
				Date fechaFichero = new Date();
				String nombreFichero = utiles.componerNombreFichero(ConstantesGestorFicheros.RESPUESTA_DUPLICADOS + jefatura, fechaFichero);
				fichero.setNombreDocumento(nombreFichero);
				fichero.setFichero(getFichero());
				File ficheroResult = gestorDocumentos.guardarFichero(fichero);
				if (ficheroResult == null) {
					log.error("Ha sucedido un error a la hora de guardar el fichero xls de RespuestaDuplicados");
				}
			} catch (OegamExcepcion e) {
				log.error("Ha sucedido un error a la hora de guardar el fichero xls de RespuestaDuplicados, error: ", e);
			}
		}
		return accionRetorno;
	}

	//importacion cambio servicio
	public String importarExcelCambioServicio() throws IOException {
		String jefatura = null;
		String accionRetorno = "importarDGT";
		int matriculacion_index = 0;
		int baja_index = 1;
		int transmision_index = 2;
		int cet_index = 3;
		int dupl_index = 4;
		int cambServ_index = 5;
		int otros_index = 6;
		int total_index = 7;
		int num_col_resol = 8;

		int caus_no_resol = 9;
		boolean custodiarArchivo = true;
		resumen.add(matriculacion_index, new ResumenImportacion("Matriculación"));
		resumen.add(baja_index, new ResumenImportacion("Baja"));
		resumen.add(transmision_index, new ResumenImportacion("Transmisión"));
		resumen.add(cet_index, new ResumenImportacion("CET"));
		resumen.add(dupl_index, new ResumenImportacion("Duplicados"));
		resumen.add(cambServ_index, new ResumenImportacion("CambioServicio"));
		resumen.add(otros_index, new ResumenImportacion("Otros"));
		resumen.add(total_index, new ResumenImportacion("TOTAL"));

		if (getFichero() == null || !utiles.esNombreFicheroValido(getFicheroFileName())) {
			log.error("Error al importar el fichero");
			setResultBean(new ResultBean());
			getResultBean().setError(true);
			getResultBean().setMensaje(ERROR_IMPORTAR_FICHERO);
			addActionError(ERROR_IMPORTAR_FICHERO);
			custodiarArchivo = false;
			resumen = actualizaResumen(resumen, otros_index, total_index, false);
			return "importarDGT";
		}
		try {
			String resultado = utilesColegiado.tienePermisoImportacion(FicherosImportacion.IMPORTAR_XSL_CAMBIOSERVICIO);
			if (resultado != null && !resultado.equalsIgnoreCase(Constantes.TRUE)) {
				String mensajeError = FicherosImportacion.IMPORTAR_XSL_CAMBIOSERVICIO.getNombreEnum();
				setResultBean(new ResultBean());
				getResultBean().setError(true);
				getResultBean().setMensaje(mensajeError);
				addActionError(mensajeError);
				custodiarArchivo = false;
				resumen = actualizaResumen(resumen, otros_index, total_index, false);
				return "importarDGT";
			}
		} catch (Exception ex) {
			log.error(ex);
			addActionError(ex.toString());
			custodiarArchivo = false;
			return Action.ERROR;
		} catch (OegamExcepcion ex) {
			log.error(ex);
			addActionError(ex.toString());
			custodiarArchivo = false;
			return Action.ERROR;
		}
		try {
			Workbook archivoExcel = Workbook.getWorkbook(getFichero());
			for (int sheetNo = 0; sheetNo < archivoExcel.getNumberOfSheets(); sheetNo++) {
				Sheet hoja = archivoExcel.getSheet(sheetNo);
				int numFilas = hoja.getRows();
				String numeroExpediente = "";
				String causaNoResolucion = "";
				int resolucion;
				for (int fila = 1; fila < numFilas; fila++) { 
					try {
						if (hoja.getCell(1, fila) == null || hoja.getCell(1, fila).getContents() == null) {
							numeroExpediente = "";
						} else {
							numeroExpediente = hoja.getCell(1, fila).getContents();
							if (jefatura == null) {
								jefatura = getJefaturaTramite(numeroExpediente);
							}
						}
						if (hoja.getCell(num_col_resol, fila) == null || hoja.getCell(num_col_resol, fila).getContents() == null || "".equals(hoja.getCell(num_col_resol, fila).getContents())) {
							addActionError("Trámite con número expediente: " + numeroExpediente + ": " + " No contiene respuesta");
							custodiarArchivo = false;
							resumen = actualizaResumen(resumen, cambServ_index, total_index, false);
						} else {
							resolucion = new Integer(hoja.getCell(num_col_resol, fila).getContents()).intValue();
							causaNoResolucion = hoja.getCell(caus_no_resol, fila).getContents();

							TramiteTrafDto tramiteTraficoDto = new TramiteTrafDto();
							tramiteTraficoDto = servicioTramiteTrafico.getTramiteDto(new BigDecimal(numeroExpediente), Boolean.TRUE);
							if (tramiteTraficoDto != null) {
								if (resolucion == 0) {
									tramiteTraficoDto.setEstado(EstadoTramiteTrafico.Finalizado_Excel.getValorEnum());
									ResultBean resultadoCambio = servicioTramiteTrafico.guardarOActualizarCS(tramiteTraficoDto);
									if (resultadoCambio.getError()) {
										addActionError("Trámite con número expediente: " + numeroExpediente + ": " + resultadoCambio.getMensaje());
										resumen = actualizaResumen(resumen, cambServ_index, total_index, false);
										custodiarArchivo = false;
									} else {
										addActionMessage("El trámite con número: " + numeroExpediente + " ha sido importado correctamente");
										resumen = actualizaResumen(resumen, cambServ_index, total_index, true);
									}
								} else {
									tramiteTraficoDto.setEstado(EstadoTramiteTrafico.Finalizado_Excel_Incidencia.getValorEnum());
									ResultBean resultadoCambio = servicioTramiteTrafico.guardarOActualizarCS(tramiteTraficoDto);
									if (!resultadoCambio.getError()) {
										addActionError("El trámite con número: " + numeroExpediente + " no se ha realizado por: " + causaNoResolucion);
										custodiarArchivo = false;
										resumen = actualizaResumen(resumen, cambServ_index, total_index, false);
									}
								}
							}else {
								addActionError("Trámite con número expediente: " + numeroExpediente + " no se ha podido importar correctamente. Revise los datos del excel ");
								custodiarArchivo = false;
								resumen = actualizaResumen(resumen, cambServ_index, total_index, false);
							}
						}
					} catch (NumberFormatException e) {
						log.error(e.toString());
						addActionError("Trámite con número expediente: " + numeroExpediente + ": " + " Error de datos");
						custodiarArchivo = false;
						resumen = actualizaResumen(resumen, cambServ_index, total_index, false);
					}

				}
			}

		} catch (BiffException ex) {
			log.error(ex);
			addActionError("Ha habido un error al leer el XLS");
			custodiarArchivo = false;
			resumen = actualizaResumen(resumen, cambServ_index, total_index, false);
			return accionRetorno;

		} catch (Exception e) {
			log.error(e);
			addActionError("Se ha producido una excepción general en el proceso");
			custodiarArchivo = false;
			resumen = actualizaResumen(resumen, cambServ_index, total_index, false);
			return accionRetorno;
		}
		if (custodiarArchivo) {
			try {
				FicheroBean fichero = new FicheroBean();
				fichero.setTipoDocumento(ConstantesGestorFicheros.EXCELS);
				fichero.setSubTipo(ConstantesGestorFicheros.CAMBIOSERVICIO_RESPUESTA_JEFATURA);
				fichero.setSobreescribir(true);
				fichero.setExtension(ConstantesGestorFicheros.EXTENSION_XLS);
				fichero.setFecha(utilesFecha.getFechaHoraActualLEG());
				Date fechaFichero = new Date();
				String nombreFichero = utiles.componerNombreFichero(ConstantesGestorFicheros.RESPUESTA_CAMBIOSERVICIO + jefatura, fechaFichero);
				fichero.setNombreDocumento(nombreFichero);
				fichero.setFichero(getFichero());
				File ficheroResult = gestorDocumentos.guardarFichero(fichero);
				if (ficheroResult == null) {
					log.error("Ha sucedido un error a la hora de guardar el fichero xls de RespuestaCambioServicio");
				}
			} catch (OegamExcepcion e) {
				log.error("Ha sucedido un error a la hora de guardar el fichero xls de RespuestaCambioServicio, error: ", e);
			}
		}
		return accionRetorno;
	}
	//----------------------------------------------

	private String getJefaturaTramite(String numeroExpediente) {
		String jefatura = null;
		TramiteTraficoVO tramiteTraficoVO = servicioTramiteTrafico.getTramite(new BigDecimal(numeroExpediente), true);
		if (tramiteTraficoVO != null && tramiteTraficoVO.getJefaturaTrafico() != null) {
			if (JefaturasJPTEnum.ALCALADEHENARES.getJefatura().equals(tramiteTraficoVO.getJefaturaTrafico().getJefatura())) {
				jefatura = "ALCALA";
			} else if (JefaturasJPTEnum.CIUDADREAL.getJefatura().equals(tramiteTraficoVO.getJefaturaTrafico().getJefatura())) {
				jefatura = "CIUDADREAL";
			} else {
				jefatura = tramiteTraficoVO.getJefaturaTrafico().getDescripcion();
			}
		}
		return jefatura;
	}

	public ParametrosPegatinaMatriculacion getEtiquetaParametros() {
		return etiquetaParametros;
	}

	public void setEtiquetaParametros(ParametrosPegatinaMatriculacion etiquetaParametros) {
		this.etiquetaParametros = etiquetaParametros;
	}

	private boolean importacionMasiva(int numeroImportaciones) {
		try {
			String habilitado = gestorPropiedades.valorPropertie("importacionMasiva.habilitado");
			String maxTramites = gestorPropiedades.valorPropertie(PropertiesConstantes.NUMERO_MINIMO_TRAMITES_PARA_IMPORTACION_MASIVA);

			if (habilitado != null && !"".equals(habilitado) && habilitado.equals("SI") && numeroImportaciones >= Integer.valueOf(maxTramites)) {
				return true;
			}
		} catch (NumberFormatException e) {
			log.error("Error recuperando parámetros en properties de importación masiva");
		}
		return false;
	}

	private ResultBean importacionEnProceso(File ficheroXml, String numColegiado, BigDecimal idContrato, BigDecimal contratoImportacion, String tipoImportacion) {
		ResultBean result = new ResultBean();
		String nombreFichero = crearFicheroProceso(ficheroXml, numColegiado, idContrato, contratoImportacion, tipoImportacion);
		if (!crearSolicitudProceso(nombreFichero)) {
			result.setError(true);
			result.addMensajeALista("No se ha podido crear la llamada al proceso");
		}
		return result;
	}

	private boolean crearSolicitudProceso(String nombreFicheroAEnviar) {
		ModeloImportacionMasivaImpl modeloImportacionMasivaImpl = new ModeloImportacionMasivaImpl();
		ResultBean resultBean;
		// modificada
		try {
			String fecha = utilesFecha.formatoFecha("ddMMyyHHmmss", new Date());
			String idTramite = utilesColegiado.getNumColegiadoSession() + fecha;
			resultBean = modeloImportacionMasivaImpl.crearSolicitud(nombreFicheroAEnviar, ConstantesSession.TIPO_TRAMITE_IMPORTACION_MASIVA, idTramite);
			if (resultBean != null && !resultBean.getError()) {
				return true;
			}
		} catch (OegamExcepcion e) {
			log.error("Error creando la solicitud Importación Masiva");
			log.error(e);
		}
		return false;
	}

	private String crearFicheroProceso(File ficheroXml, String numColegiado, BigDecimal idContrato, BigDecimal contratoImportacion, String tipoImportacion) {
		String fecha = utilesFecha.formatoFecha("ddMMyyyyHHmmss", new Date());
		String nombreFicheroAEnviar = numColegiado + "-" + idContrato + "-" + contratoImportacion + "-" + tipoImportacion + "-" + fecha;

		try {
			guardarFicheroProceso(ficheroXml, nombreFicheroAEnviar, ConstantesGestorFicheros.IMPORTACION_MASIVA, ConstantesGestorFicheros.PETICIONES, ConstantesGestorFicheros.EXTENSION_XML);
			return nombreFicheroAEnviar;
		} catch (IOException e) {
			log.error("Error generando documento de importación masivo por proceso");
			log.error(e);
		} catch (Exception e) {
			log.error("Error generando documento de importación masivo por proceso");
			log.error(e);
		} catch (OegamExcepcion e) {
			log.error("Error generando documento de importación masivo por proceso");
			log.error(e);
		}
		return null;
	}

	private File guardarFicheroProceso(File ficheroXml, String nombreFichero, String tipoDocumento, String subTipo, String extension) throws Exception, IOException, OegamExcepcion {
		log.info(" Guardar Documentos del Proceso Importación masiva");
		FicheroBean fichero = new FicheroBean();
		fichero.setTipoDocumento(tipoDocumento);
		fichero.setSubTipo(subTipo);
		fichero.setFichero(ficheroXml);
		fichero.setSobreescribir(true);
		fichero.setSubCarpetaDia(true);

		fichero.setFecha(utilesFecha.getFechaActual());
		fichero.setNombreDocumento(nombreFichero);

		fichero.setExtension(extension);
		return gestorDocumentos.guardarFichero(fichero);
	}

	// Mantis 25415
	private boolean validarFormatoValorReal(String cadena) {
		String prueba1 = "\\d{1,6}.\\d{0,2}";
		String prueba2 = "\\d{1,6},\\d{0,2}";
		String valorCero = "0.00";

		return ((cadena).matches(prueba1) || (cadena).matches(prueba2)) && !cadena.equals(valorCero);
	}

	/* *********************************************************************** */
	/* MODELOS *************************************************************** */
	/* *********************************************************************** */

	public ModeloTrafico getModeloTrafico() {
		if (modeloTrafico == null) {
			modeloTrafico = new ModeloTrafico();
		}
		return modeloTrafico;
	}

	public void setModeloTrafico(ModeloTrafico modeloTrafico) {
		this.modeloTrafico = modeloTrafico;
	}

	public ModeloSolicitudDatos getModeloSolicitudDatos() {
		if (modeloSolicitudDatos == null) {
			modeloSolicitudDatos = new ModeloSolicitudDatos();
		}
		return modeloSolicitudDatos;
	}

	public void setModeloSolicitudDatos(ModeloSolicitudDatos modeloSolicitudDatos) {
		this.modeloSolicitudDatos = modeloSolicitudDatos;
	}

	public ModeloImportacionDGT getModeloImportacionDGT() {
		if (modeloImportacionDGT == null) {
			modeloImportacionDGT = new ModeloImportacionDGT();
		}
		return modeloImportacionDGT;
	}

	public void setModeloImportacionDGT(ModeloImportacionDGT modeloImportacionDGT) {
		this.modeloImportacionDGT = modeloImportacionDGT;
	}

	public ServicioEEFFNuevo getServicioEEFF() {
		return servicioEEFF;
	}

	public void setServicioEEFF(ServicioEEFFNuevo servicioEEFF) {
		this.servicioEEFF = servicioEEFF;
	}

	public ServicioPermisos getServicioPermisos() {
		return servicioPermisos;
	}

	public void setServicioPermisos(ServicioPermisos servicioPermisos) {
		this.servicioPermisos = servicioPermisos;
	}

	public ServicioImportar getServicioImportacion() {
		return servicioImportacion;
	}

	public void setServicioImportacion(ServicioImportar servicioImportacion) {
		this.servicioImportacion = servicioImportacion;
	}
}