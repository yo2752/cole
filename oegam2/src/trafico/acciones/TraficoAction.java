package trafico.acciones;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;

import es.globaltms.gestorDocumentos.bean.FileResultBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.enums.FileResultStatus;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import es.globaltms.gestorDocumentos.util.Utilidades;
import escrituras.beans.ResultBean;
import escrituras.modelo.ModeloPersona;
import general.acciones.ActionBase;
import oegam.constantes.ConstantesSession;
import trafico.beans.ConsultarDireccionCursor;
import trafico.beans.ResumenPendienteExcel;
import trafico.beans.TramiteTraficoBean;
import trafico.modelo.ModeloTrafico;
import trafico.utiles.ConstantesPDF;
import trafico.utiles.constantes.ConstantesTrafico;
import trafico.utiles.enumerados.TipoImpreso;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

public class TraficoAction extends ActionBase implements ServletRequestAware, ServletResponseAware {

	/**
	 * Action que gestiona el método Duplicar de todos los tipos de trámites
	 */
	private static final long serialVersionUID = 1L;
	private static final ILoggerOegam log = LoggerOegam.getLogger(TraficoAction.class);
	private TramiteTraficoBean tramiteTraficoBean; 
	private HttpServletRequest request;
	private HttpServletResponse response;
	private Map<String,Object>session;
	private String numExpediente;
	private String numsExpediente;
	private String isMatw = null;

	HashMap<String, Object> parametrosBusqueda;	// Se utiliza para asignar los parámetros de búsqueda al objeto DAO

	private InputStream inputStream;	// Flujo de bytes del fichero a imprimir en PDF del action
	private String fileName;			// Nombre del fichero a imprimir
	private String fileSize;			// Tamaño del fichero a imprimir

	Boolean impresoEspera = false;		// Booleano que nos permite controlar si hay una impresión que realizar
	Boolean impresoFichero = false;		// Booleano que nos indica si la impresión es de ficheros.

	// Resumen
	private List<ResumenPendienteExcel> resumen = new ArrayList<>();
	private Boolean resumenPendienteEnvioExcel = false;

	private String nif;

	private String numColegiado;

	private String pestania;

	private ModeloTrafico modeloTrafico;

	@Autowired
	private GestorDocumentos guardarDocumento;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	private UtilesColegiado utilesColegiado;

	//ActionsMethods para las impresiones de los diferentes tipos de trámites.

	/**
	 * Método que imprimirá los trámites que le pasemos, en el segundo paso de imprimir.
	 * @return
	 * @throws OegamExcepcion 
	 * @throws IOException 
	 */
	public String mostrarDocumento() throws OegamExcepcion, IOException{
		String donde= "mostrarDocumentoImpreso";
		ResultBean resultadoImprimirAction = (ResultBean)
				getSession().get(ConstantesSession.IMPRESION);
		//Exportar XLS
		if (null != resultadoImprimirAction && null!=resultadoImprimirAction.getAttachment(ConstantesSession.HAY_FICHERO_EXCEL)) {
			Boolean hayFicheroExcel = (Boolean) resultadoImprimirAction.getAttachment(ConstantesSession.HAY_FICHERO_EXCEL);

			if (hayFicheroExcel != null && hayFicheroExcel) {
				String ruta = gestorPropiedades.valorPropertie("ficheros.NODO1")+ConstantesPDF.RUTA_EXCEL;

				ruta +=
						(String) resultadoImprimirAction.getAttachment(ConstantesSession.FICHERO_EXCEL) +
						ConstantesPDF.EXTENSION_XLS;

				donde = "descargaXLS";

				inputStream = new FileInputStream(new File(ruta));
				impresoEspera = false;

				setFileName(TipoImpreso.BajasExcel.getNombreEnum());

				log.debug("Exportando XLS");
				getSession().remove(ConstantesSession.IMPRESION);
				return donde;
			}
		}

		// FIN XLS

		ResultBean resultadoImprimirAVPO = (ResultBean)
				getSession().get(ConstantesSession.IMPRESIONAVPO);

		ResultBean resultadoImprimirATEM = (ResultBean)
				getSession().get(ConstantesSession.IMPRESIONATEM);

		// Si imprimir es null puede que venga de consultar, para imprimir XML
		if (null == resultadoImprimirAction && null == resultadoImprimirAVPO && null == resultadoImprimirATEM) {

			//Comprobamos que haya documentos XML
			byte[] ficheroXML = (byte[]) getSession().get(ConstantesTrafico.FICHEROXML);
			String nombreFichero = (String) getSession().get(ConstantesTrafico.NOMBREXML);
			donde="descargarXML";

			if (null != ficheroXML) {
				ByteArrayInputStream stream = new ByteArrayInputStream(ficheroXML);
				setInputStream(stream);
				impresoEspera = false;

				if (nombreFichero.length() > 60) {
					nombreFichero = nombreFichero.substring(0, 58);
					nombreFichero += "__";
				}

				setFileName(nombreFichero + ConstantesPDF.EXTENSION_XML);

				log.debug("Exportando XML");
				getSession().remove(ConstantesSession.IMPRESION);
				getSession().remove(ConstantesTrafico.FICHEROXML);
				getSession().remove(ConstantesTrafico.NOMBREXML);
				return donde;
			}

			// Comprobamos que haya documentos BM
			// Comprobamos que haya documentos XML
			byte[] ficheroBM = (byte[]) getSession().get(ConstantesTrafico.FICHEROBM);
			nombreFichero = (String) getSession().get(ConstantesTrafico.NOMBREBM);
			donde="descargarBM";

			if (null != ficheroBM) {
				ByteArrayInputStream stream = new ByteArrayInputStream(ficheroBM);
				setInputStream(stream);
				impresoEspera = false;

				if (nombreFichero.length() > 60) {
					nombreFichero = nombreFichero.substring(0, 58);
					nombreFichero += "__";
				}

				setFileName(nombreFichero + ConstantesPDF.EXTENSION_BM);

				log.debug("Exportando BM");
				getSession().remove(ConstantesSession.IMPRESION);
				getSession().remove(ConstantesTrafico.FICHEROBM);
				getSession().remove(ConstantesTrafico.NOMBREBM);
				return donde;
			}

			// Comprobamos que haya documentos AEAT
			byte[] ficheroAEAT = (byte[]) getSession().get(ConstantesTrafico.FICHEROAEAT);
			nombreFichero = (String) getSession().get(ConstantesTrafico.NOMBREAEAT);
			donde="descargarAEAT";

			if (null != ficheroAEAT) {
				ByteArrayInputStream stream = new ByteArrayInputStream(ficheroAEAT);
				setInputStream(stream);
				impresoFichero = false;

				if (nombreFichero.length() > 60) {
					nombreFichero = nombreFichero.substring(0, 58);
					nombreFichero += "__";
				}

				setFileName(nombreFichero + ConstantesPDF.EXTENSION_TXT);

				log.debug("Exportando Fichero AEAT");
				getSession().remove(ConstantesSession.IMPRESION);
				getSession().remove(ConstantesTrafico.FICHEROAEAT);
				getSession().remove(ConstantesTrafico.NOMBREAEAT);
				return donde;
			}

		}

		//En el caso de que sea un AVPO miraremos si es un fichero de varios o un fichero solo, o varios ficheros de varios ficheros.
		if (null != resultadoImprimirAVPO) {

			String nombresFicheros = (String) resultadoImprimirAVPO.getAttachment("ZipConsultaAVPO");
			String[] nombreFichero = nombresFicheros.split("-");

			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			File dercargarAvpo = null;
			ZipOutputStream zip = null;
			if (nombreFichero.length > 1) {
				zip = new ZipOutputStream(baos);
			}

			for (String nombre : nombreFichero) {

				//se pone _ el nombre es Consultas_expediente
				String numExpediente = nombre.split("_")[1];

				FileResultBean fileResultBean = guardarDocumento.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.SOLICITUD_INFORMACION, null,
						Utilidades.transformExpedienteFecha(numExpediente), nombre, ConstantesGestorFicheros.EXTENSION_ZIP);
				if (FileResultStatus.ON_DEMAND_FILE.equals(fileResultBean.getStatus())) {
					addActionError(numExpediente + ": " + fileResultBean.getMessage());
				} else {
					dercargarAvpo = fileResultBean.getFile();

					FileInputStream is = new FileInputStream(dercargarAvpo);
					ZipEntry zipEntry = new ZipEntry(dercargarAvpo.getName());
					zip.putNextEntry(zipEntry);
					byte[] buffer = new byte[2048];
					int byteCount;
					while (-1 != (byteCount = is.read(buffer))) {
						zip.write(buffer, 0, byteCount);
					}
					zip.closeEntry();
					is.close();
					if (dercargarAvpo.lastModified() > 0) {
						zipEntry.setTime(dercargarAvpo.lastModified());
					}
				}
			}

			if (nombreFichero.length > 1) {
				zip.close();

				inputStream = new ByteArrayInputStream(baos.toByteArray());
			} else {
				inputStream  = new FileInputStream(dercargarAvpo);
			}
			setFileName("ConsultasAVPO"+ConstantesGestorFicheros.EXTENSION_ZIP);
			getSession().remove(ConstantesSession.IMPRESIONAVPO);
			impresoEspera = false;
			return "descargarZip";
		}

		// En el caso de que sea un ATEM miraremos si es un fichero de varios o un fichero solo, o varios ficheros de varios ficheros.
		if (null != resultadoImprimirATEM) {
			//Datos principales para imprimir después de ejecutar el proceso completo.
			String nombreFicheroaImprimir = null;
			InputStream streamaImprimir = null;

			String nombresFicheros = (String) resultadoImprimirATEM.getAttachment("ZipConsultaATEM");
			String[] nombreFichero = nombresFicheros.split("-");
			int i = 0;
			// Tendrá 3 niveles.
			// Nivel 1: mira cuantos ficheros existen para cada trámite.
			// Nivel 2: en caso de que haya más de uno para cada trámite los mete en un zip.
			// Nivel 3: en caso de que haya varios trámites mete los zips generados en otro zip.
			Boolean variosTramites = false;

			if (nombreFichero.length > 1) {
				variosTramites = true;
			}

			if (variosTramites) {
				//Aquí obtendremos el resto de ficheros para meterlos en el último que será el que imprimimamos.
				ByteArrayOutputStream baosdeZips = new ByteArrayOutputStream();
				ZipOutputStream salidaFinaldeZips = new ZipOutputStream(baosdeZips);
				for (i = 0; i < nombreFichero.length; i++) {
					//Para cada trámite

					File ficheroMeter = new File(nombreFichero[i]);

					ZipEntry entradaZips = new ZipEntry(ficheroMeter.getName());
					salidaFinaldeZips.putNextEntry(entradaZips);

					FileInputStream fis = new FileInputStream(ficheroMeter);
					byte [] buffer = new byte[1024];
					int leido=0;
					while (0 < (leido=fis.read(buffer))){
						salidaFinaldeZips.write(buffer,0,leido);
					}
					fis.close();
					salidaFinaldeZips.closeEntry();
				}

				salidaFinaldeZips.close();

				streamaImprimir = new ByteArrayInputStream(baosdeZips.toByteArray());
				nombreFicheroaImprimir = "VariasSolicitudes.zip";
			} else { // Cuando solo es un trámite
				File ficheroMeter = new File(nombreFichero[0]);
				inputStream = new FileInputStream(ficheroMeter);
				setFileName(ficheroMeter.getName());
				getSession().remove(ConstantesSession.IMPRESIONATEM);
				impresoEspera = false;
				return "descargarZip";
			}

			// Esto va a ser lo que va a generar realmente la impresión.
			setInputStream(streamaImprimir);
			setFileName(nombreFicheroaImprimir);

			getSession().remove(ConstantesSession.IMPRESIONATEM);
			impresoEspera = false;
			return "descargarZip";
		}

		// Si viene de imprimir
		byte[] paraImprimir = null;
		File fileImprimir = null;
		if (resultadoImprimirAction.getAttachment("pdf") instanceof byte[]) {
			paraImprimir = (byte[]) resultadoImprimirAction.getAttachment("pdf");
		} else {
			fileImprimir = (File) resultadoImprimirAction.getAttachment("pdf");
		}
		String numPermiso = (String) resultadoImprimirAction.getAttachment("numPermiso");
		String numCartaIVTM = (String) resultadoImprimirAction.getAttachment("numCartaIVTM");
		Boolean zip = (Boolean) resultadoImprimirAction.getAttachment("zip");
		zip = null != zip?zip:false;

		if (null == paraImprimir && null == fileImprimir) {
			if (null != zip && !zip) {
				if (null != numPermiso) {

					// String ruta = ConstantesPDF.RUTA_PERMISO_TEMPORAL_CIRCULACION;
					try {
						File fichero = null;
						Object o = resultadoImprimirAction.getAttachment("absolutePath");
						if (o != null) {
							Object ruta = ((Map<String, String>) o).get(numPermiso);
							if (ruta!= null){
								fichero = new File(ruta.toString());
								if (!fichero.exists()) {
									fichero = null;
								}
							}
						}
						if (fichero == null) {
							fichero = guardarDocumento.buscarFicheroPorNombreTipo("", "", null, numPermiso, ConstantesGestorFicheros.EXTENSION_PDF).getFile();
						}
						inputStream = new FileInputStream(fichero); //new File(ruta + numPermiso + ConstantesPDF.EXTENSION_PDF));
					} catch (FileNotFoundException e) {
						log.error(e);
					}
				}
				if (null != numCartaIVTM) {
					String ruta = gestorPropiedades.valorPropertie("ficheros.NODO1")+ ConstantesPDF.RUTA_CARTA_PAGO_IVTM;
					try {
						inputStream = new FileInputStream(new File(ruta + numCartaIVTM + ConstantesPDF.EXTENSION_PDF));
						//inputStream = new FileInputStream(new File(ruta + numPermiso + ConstantesPDF.EXTENSION_PDF));//Cambiar cuando funcione el webservice.
					} catch (FileNotFoundException e) {
						log.error(e);
					}
				}
				donde = "descargarPDF";
			} else { // Aquí incluiremos los ficheros en un .zip y lo devolveremos.
				donde="descargarZip";

				String indices = (String) resultadoImprimirAction.getAttachment("nombreFichero");
				String[] codSeleccionados = indices.split("_");

				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ZipOutputStream out = new ZipOutputStream(baos);

				Map<String, String> rutas = (Map<String, String>) resultadoImprimirAction.getAttachment("absolutePath");
				for (int i = 1; i < codSeleccionados.length; i++) {

					String numeroExp = "";
					String nombreFichero = numeroExp;

					if ("i".equals(codSeleccionados[i])) {
						i++;
						numeroExp = "i_" + codSeleccionados[i];
						nombreFichero = "informe_" + codSeleccionados[i];
					} else {
						numeroExp = codSeleccionados[i];
						nombreFichero = "PTC_" + codSeleccionados[i];
					}

					ZipEntry entrada = new ZipEntry(nombreFichero + ConstantesPDF.EXTENSION_PDF);
					out.putNextEntry(entrada);

					File fichero = null;
					if (rutas != null) {
						Object ruta = rutas.get(codSeleccionados[i]);
						if (ruta!= null){
							fichero = new File(ruta.toString());
							if (!fichero.exists()) {
								fichero = null;
							}
						}
					}

					if (fichero == null) {
						fichero = guardarDocumento.buscarFicheroPorNombreTipo("", "", null, numeroExp, ConstantesGestorFicheros.EXTENSION_PDF).getFile();
					}

					FileInputStream fis = new FileInputStream( fichero);
					byte [] buffer = new byte[1024];
					int leido=0;
					while (0 < (leido=fis.read(buffer))){
						out.write(buffer,0,leido);
					}

					fis.close();
					out.closeEntry();
				}

				out.close();

				ByteArrayInputStream stream = new ByteArrayInputStream(baos.toByteArray());
				setInputStream(stream);
				String nombreFichero = (String) resultadoImprimirAction.getAttachment("nombreFichero");

				impresoEspera = false;

				if (nombreFichero.length() > 60) {
					nombreFichero = nombreFichero.substring(0, 58);
					nombreFichero += "__";
				}

				setFileName(nombreFichero + ConstantesPDF.EXTENSION_ZIP);

				log.debug("Imprimiendo");
				getSession().remove(ConstantesSession.IMPRESION);
				return donde;
			}
		} else {
			if (paraImprimir != null) {
				ByteArrayInputStream stream = new ByteArrayInputStream(paraImprimir);
				setInputStream(stream);
			} else {
				inputStream = new FileInputStream(fileImprimir);
			}
		}

		String nombreFichero = (String) resultadoImprimirAction.getAttachment("nombreFichero");

		impresoEspera = false;

		if (nombreFichero.length() > 60) {
			nombreFichero = nombreFichero.substring(0, 58);
			nombreFichero += "__";
		}

		setFileName(nombreFichero + ConstantesPDF.EXTENSION_PDF);

		log.debug("Imprimiendo");
		getSession().remove(ConstantesSession.IMPRESION);
		return donde;
	}

	// MÉTODOS AUXILIARES

	/**
	 * Método que busca direcciones en bbdd por el DNI y devuelve todas las que tenga.
	 * @return
	 * @throws Throwable 
	 */
	public String consultaDireccion() throws Throwable {
		ArrayList<ConsultarDireccionCursor> arrayListDirecciones = new ArrayList<>();
		parametrosBusqueda = new HashMap<>();
		String strNumColegiado = "";
		String strNif=getNif();

		if (getNumColegiado() != null && !getNumColegiado().equals("")) {
			strNumColegiado = getNumColegiado();
		} else {
			strNumColegiado = getNumExpediente() != null && !getNumExpediente().equals("") ? getNumExpediente() : utilesColegiado.getNumColegiadoSession();
		}
		arrayListDirecciones=ModeloPersona.direcciones(strNumColegiado,strNif );
		getSession().put(ConstantesSession.NUM_COLEGIADO,strNumColegiado);
		getSession().put(ConstantesSession.NIF_TITULAR_CONSULTA,strNumColegiado);
		getSession().put(ConstantesSession.LISTA_CONSULTA_DIRECCIONES, arrayListDirecciones);
		log.info("Consulta Dirección.");

		return getIsMatw() != null && !getIsMatw().equals("") ? "consultaDireccionConPuebloCorreos"
				: "consultaDireccion";
	}

	//GETTER & SETTERS

	public TramiteTraficoBean getTramiteTraficoBean() {
		return tramiteTraficoBean;
	}

	public void setTramiteTraficoBean(TramiteTraficoBean tramiteTraficoBean) {
		this.tramiteTraficoBean = tramiteTraficoBean;
	}

	public Boolean getImpresoEspera() {
		return impresoEspera;
	}

	public void setImpresoEspera(Boolean impresoEspera) {
		this.impresoEspera = impresoEspera;
	}

	public List<ResumenPendienteExcel> getResumen() {
		return resumen;
	}

	public void setResumen(List<ResumenPendienteExcel> resumen) {
		this.resumen = resumen;
	}

	@Override
	public void setServletResponse(HttpServletResponse arg0) {
		this.response=arg0;
	}

	public Map<String, Object> getSession() {
		return session;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public HttpServletRequest getServletRequest() {
		return request;
	}

	public void setServletRequest(HttpServletRequest servletRequest) {
		this.request = servletRequest;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	public String getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(String numExpediente) {
		this.numExpediente = numExpediente;
	}

	public HashMap<String, Object> getParametrosBusqueda() {
		return parametrosBusqueda;
	}

	public void setParametrosBusqueda(HashMap<String, Object> parametrosBusqueda) {
		this.parametrosBusqueda = parametrosBusqueda;
	}

	public String getNumsExpediente() {
		return numsExpediente;
	}

	public void setNumsExpediente(String numsExpediente) {
		this.numsExpediente = numsExpediente;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public Boolean getImpresoFichero() {
		return impresoFichero;
	}

	public void setImpresoFichero(Boolean impresoFichero) {
		this.impresoFichero = impresoFichero;
	}

	public Boolean getResumenPendienteEnvioExcel() {
		return resumenPendienteEnvioExcel;
	}

	public void setResumenPendienteEnvioExcel(Boolean resumenPendienteEnvioExcel) {
		this.resumenPendienteEnvioExcel = resumenPendienteEnvioExcel;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public String getPestania() {
		return pestania;
	}

	public void setPestania(String pestania) {
		this.pestania = pestania;
	}

	public String getIsMatw() {
		return isMatw;
	}

	public void setIsMatw(String isMatw) {
		this.isMatw = isMatw;
	}

	/* ******************************************************* */
	/* MODELOS *********************************************** */
	/* ******************************************************* */

	public ModeloTrafico getModeloTrafico() {
		if (modeloTrafico == null) {
			modeloTrafico = new ModeloTrafico();
		}
		return modeloTrafico;
	}

	public void setModeloTrafico(ModeloTrafico modeloTrafico) {
		this.modeloTrafico = modeloTrafico;
	}

}