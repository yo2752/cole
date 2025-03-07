package trafico.tasas.acciones;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;
import org.gestoresmadrid.core.tasas.model.enumeration.FormatoTasa;
import org.gestoresmadrid.oegam2comun.tasas.model.service.ServicioImportacionTasa;
import org.gestoresmadrid.oegam2comun.tasas.view.beans.ResumenTasas;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;

import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import escrituras.beans.ResultBean;
import general.acciones.ActionBase;
import trafico.modelo.ModeloTasas;
import utilidades.ficheros.BorrarFicherosThread;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

public class ImportarTasasAction extends ActionBase implements SessionAware{

	private static final long serialVersionUID = 1L;
	private Map session;

	// Importacion Etiquetas
	private File ficheroTasas; // Fichero a importar en la JSP
	private String ficheroTasasFileName; //nombre del fichero importado
	private String ficheroTasasContentType; //tipo del fichero importado
	private Boolean menorTamMax; // Indica si se llega al action, ya que si se excede el tamaño máximo, el interceptor devuelve directamente input
	private BigDecimal contratoImportacion; // Contrato seleccionado
	private String formato;

	// Resumen de la importación
	private List<ResumenTasas> listaResumen = new ArrayList<ResumenTasas>();

	// Tipo de tramite recibido del jsp
	private String retorno;

	@Autowired
	UtilesColegiado utilesColegiado;

	//Objeto del modelo
	private ModeloTasas tasasModelo;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ImportarTasasAction.class);

	//Fichero de errores a descargar
	private InputStream ficheroErrores;

	@Autowired
	private GestorDocumentos gestorDocumentos;

	@Autowired
	private ServicioImportacionTasa servicioImportacionTasa;

	@Autowired
	UtilesFecha utilesFecha;

	public List<ResumenTasas> getListaResumen() {
		return listaResumen;
	}

	public InputStream getFicheroErrores() {
		return ficheroErrores;
	}

	public void setFicheroErrores(InputStream ficheroErrores) {
		this.ficheroErrores = ficheroErrores;
	}

	public Map getSession() {
		return session;
	}

	public void setSession(Map session) {
		this.session = session;
	}

	public String getRetorno() {
		return retorno;
	}

	public void setRetorno(String retorno) {
		this.retorno = retorno;
	}	

	public File getFicheroTasas() {
		return ficheroTasas;
	}

	public void setFicheroTasas(File ficheroTasas) {
		this.ficheroTasas = ficheroTasas;
	}

	public String getFicheroTasasFileName() {
		return ficheroTasasFileName;
	}

	public void setFicheroTasasFileName(String ficheroTasasFileName) {
		this.ficheroTasasFileName = ficheroTasasFileName;
	}

	public String getFicheroTasasContentType() {
		return ficheroTasasContentType;
	}

	public void setFicheroTasasContentType(String ficheroTasasContentType) {
		this.ficheroTasasContentType = ficheroTasasContentType;
	}

	public Boolean getMenorTamMax() {
		return menorTamMax;
	}

	public void setMenorTamMax(Boolean menorTamMax) {
		this.menorTamMax = menorTamMax;
	}

	public ModeloTasas getTasasModelo() {
		return tasasModelo;
	}

	public void setTasasModelo(ModeloTasas tasasModelo) {
		this.tasasModelo = tasasModelo;
	}

	public BigDecimal getContratoImportacion() {
		return contratoImportacion;
	}

	public void setContratoImportacion(BigDecimal contratoImportacion) {
		this.contratoImportacion = contratoImportacion;
	}

	public String getFormato() {
		return formato;
	}

	public void setFormato(String formato) {
		this.formato = formato;
	}

	@Override
	public String execute() throws Exception {
		return super.execute();
	}

	public String inicio(){	
		if(getSession().get("subidaFinalizada")!=null){
			getSession().remove("subidaFinalizada");
			getSession().remove("mensajesError");
		}
		if (formato == null || formato.isEmpty()) {
			formato = Integer.toString(FormatoTasa.ELECTRONICO.getCodigo());
		}
		return "importarTasas";
	}

	//Si hay errores habría que devolver un fichero con las lineas que fallaron
	public String importarTasas(){
		if(getSession().get("subidaFinalizada")!=null){
			getSession().remove("subidaFinalizada");
			getSession().remove("mensajesError");
		}
		setMenorTamMax(true);
		if(!(utilesColegiado.tienePermisoAdmin() || utilesColegiado.tienePermisoColegio())){
			contratoImportacion = new BigDecimal(utilesColegiado.getIdContratoSession());
		}
//		usuario = gestorArbol.getUsuario();

		//Tratar fichero
		if(ficheroTasas==null){
			log.info("Error al importar el fichero de tasas");
			addActionError("Error al importar el fichero de tasas");
			return "importarTasas";
		} else {
			String idSession = ServletActionContext.getRequest().getSession().getId();
			ResultBean result = servicioImportacionTasa.importarTasas(ficheroTasas, utilesColegiado.getIdUsuarioSession(), contratoImportacion, idSession, FormatoTasa.convertir(formato), utilesColegiado.getNumColegiadoSession(),utilesColegiado.tienePermisoAdmin(),utilesColegiado.tienePermisoColegio());
			if(result.getError()){
				log.info("Error al importar el fichero de tasas");
				getSession().put("subidaFinalizada", "subidaFinalizada");
				for(String s:result.getListaMensajes()){
					addActionError(s);
				}
				return "importarTasas";
			}else{
				List<String> mensajesError = (List<String>)result.getAttachment("fallos");
				listaResumen = (List<ResumenTasas>)result.getAttachment("resumen");
				try{
					if(!mensajesError.isEmpty()){ //Hay algún fallo...
						addActionError("Fichero importado parcialmente");
						for(String s:mensajesError){
							addActionError(s);
						}
						String mensajeCola = (String) result.getAttachment("mensajeEncoladoOk");
						if(mensajeCola != null && !mensajeCola.isEmpty()){
							addActionMessage(mensajeCola);
						}
					} else {
						addActionMessage("Fichero importado correctamente");
						String ruta = "";

						File file=null;
						try{
							file = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.IMPORTACION, ConstantesGestorFicheros.TASAS, 
									utilesFecha.getFechaActual(), ConstantesGestorFicheros.NOMBRE_ERRORES_TASAS+idSession, ConstantesGestorFicheros.EXTENSION_TXT).getFile();
						} catch (OegamExcepcion e){
							addActionError("Error al obtener la ruta del fichero de errores.");
						}
						if(file!=null){
							BorrarFicherosThread hiloBorrar = new BorrarFicherosThread(file.getAbsolutePath(), 10000);
							hiloBorrar.start();
							log.debug("Se lanza el hilo para borrar el fichero creado, pasado 1 segundo.");
						}
						if(FormatoTasa.PEGATINA.getCodigo() == Integer.parseInt(formato)){
							addActionMessage("La solicitud para generar las tasas de pegatinas se ha encolado correctamente.");
						}
					}
					getSession().put("subidaFinalizada", "subidaFinalizada");
				} catch (Throwable e){
					addActionError("Error en la lectura de las lineas del fichero de errores");
					log.error("Error en la lectura de las lineas del fichero de errores");
					log.error(e);
				}
			}
		}
		return "importarTasas";
	}

//	public String getMensajeResumen(List<TasasGuardadas> resumen) {
//		String mensaje = "Resumen de la importación:";
//		for (int i=0;i<resumen.size();i++){
//			mensaje+="<br>";
//			mensaje+="-Tipo " + resumen.get(i).getTipoTasa() + ": ";
//			mensaje+= "Se han importado correctamente " + resumen.get(i).getGuardadasBien() + " tasas ";
//			mensaje+= " y " + resumen.get(i).getGuardadasMal() + " han dado algun fallo";
//			if (i==resumen.size()-1) mensaje+="<br><br>";
//		}
//		return mensaje;
//	}

	public String descargaFichero() throws Exception {
		String ruta = "";
		String idSession = ServletActionContext.getRequest().getSession().getId();
		File file = null;
		try{
			file = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.IMPORTACION, ConstantesGestorFicheros.TASAS,
					utilesFecha.getFechaActual(), ConstantesGestorFicheros.NOMBRE_ERRORES_TASAS+idSession, ConstantesGestorFicheros.EXTENSION_TXT).getFile();
		} catch (OegamExcepcion e){
			addActionError("Error al obtener la ruta del fichero de errores.");
		}

		ficheroErrores = new FileInputStream(file);
		//Lanzamos un thread para borrar el fichero pasados 5 minutos.
		BorrarFicherosThread hiloBorrar = new BorrarFicherosThread(ruta+"ficheroTasasErrores"+idSession+".txt");
		hiloBorrar.start();
		log.debug("Se lanza el hilo para borrar el fichero creado, pasados 5 minutos.");
		return "ficheroDownload";
	}

	public ServicioImportacionTasa getServicioImportacionTasa() {
		return servicioImportacionTasa;
	}

	public void setServicioImportacionTasa(ServicioImportacionTasa servicioImportacionTasa) {
		this.servicioImportacionTasa = servicioImportacionTasa;
	}

}