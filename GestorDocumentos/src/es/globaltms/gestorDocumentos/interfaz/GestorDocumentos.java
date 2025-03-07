package es.globaltms.gestorDocumentos.interfaz;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;

import utilidades.estructuras.Fecha;
import utilidades.estructuras.FechaFraccionada;
import utilidades.web.OegamExcepcion;
import es.globaltms.gestorDocumentos.bean.FicheroBean;
import es.globaltms.gestorDocumentos.bean.FileResultBean;

public interface GestorDocumentos extends Serializable{


	/**
	 * Guarda un documento, se debe de rellenar el tipo de fichero con el tipo Byte no el array de File.
	 * Como tipo de documento usar la constante.
	 * @param fichero
	 */
	public File guardarByte(FicheroBean fichero) throws OegamExcepcion;
	
	/**
	 * Guarda un documento, se debe de rellenar el tipo de fichero con el tipo File no el array de Byte.
	 * @param fichero
	 */
	public File guardarFichero(FicheroBean fichero) throws OegamExcepcion;
	
	/**
	 * Busca los ficheros guardados con uno parametros de busqueda.
	 * @param tipo tipo de fichero ejemplo eeff 
	 * @param subTipo subTipo de fichero ejemplo liberacion
	 * @param numExpediente 
	 * @return Retorna una lista con los archivos que cumple la busqueda
	 * @throws OegamExcepcion
	 */
	public FileResultBean buscarFicheroPorNumExpTipo(String tipo,String subTipo,Fecha fecha,String nombre)throws OegamExcepcion;
	
	/**
	 * Busqueda de archivo por nombre. 
	 * Si no vienen subtipo buscara por todos todo el directorio de carpetas. 
	 * Si viene el expediente buscará por año, mes, para afinar mas la busqueda.
	 * @param tipo Tipo de archivo de busqueda es obligatorio
	 * @param subTipo SubTipo no es obligatorio.
	 * @param nombre nombre del archivo sin extension.
	 * @param extension 
	 * @return
	 * @throws OegamExcepcion
	 */
	public FileResultBean buscarFicheroPorNombreTipo(String tipo, String subTipo,Fecha fecha, String nombre, String extension) throws OegamExcepcion;
	
	/**
	 * Busqueda de archivo por nombre. 
	 * Si no vienen subtipo buscara por todos todo el directorio de carpetas. 
	 * Si viene el expediente buscará por año, mes, para afinar mas la busqueda.
	 * @param tipo Tipo de archivo de busqueda es obligatorio
	 * @param subTipo SubTipo no es obligatorio.
	 * @param nombre nombre del archivo sin extension.
	 * @param extension 
	 * @return
	 * @throws OegamExcepcion
	 */
	public File buscarFicheroPorNombreTipoYDia(String tipo, String subTipo,	Fecha fecha, String nombre, String extension) throws OegamExcepcion;
	
	/**
	 * Busqueda de una lista de archivos
	 * @param url Url del fcihero a buscar
	 * @return
	 * @throws OegamExcepcion
	 */
	public File buscarFicheroPorUrl(String url) throws OegamExcepcion;
	
	/**
	 * Busca un fichero por el numero de expediente siendo el numero de expediente parte del nombre.
	 * @param tipo
	 * @param subTipo
	 * @param fecha
	 * @param nombre
	 * @throws OegamExcepcion
	 */
	public void borraFicheroSiExiste(String tipo,String subTipo,Fecha fecha,String nombre)throws OegamExcepcion;
	
	/**
	 * Busca un fichero por el numero de expediente siendo el numero de expediente parte del nombre.
	 * @param tipo
	 * @param subTipo
	 * @param fecha
	 * @param nombre
	 * @param extension
	 * @throws OegamExcepcion
	 */
	public void borraFicheroSiExisteConExtension(String tipo, String subTipo, Fecha fecha, String nombre, String extension) throws OegamExcepcion;
	
	/**
	 * Borrar fichero y posibles directorios vacíos
	 * @param fichero
	 * @throws OegamExcepcion
	 */
	public void borradoRecursivo(File fichero);
	
	/**
	 * Empaqueta una lista de ficheros, enviando los ficheros como File
	 * @param fichero
	 * @return
	 * @throws IOException
	 * @throws OegamExcepcion
	 */
	public File empaquetarEnZipFile(FicheroBean fichero) throws IOException, OegamExcepcion;
	
	/**
	 * Empaqueta una lista de ficheros, en concreto fichas tecnicas, enviando los ficheros como File y guardándolos en
	 * carpeta en función del año/mes del numero de expediente
	 * @param fichero
	 * @return
	 * @throws IOException
	 * @throws OegamExcepcion
	 */
	public File empaquetarEnZipFileCarpetaFecha (FicheroBean fichero) throws IOException, OegamExcepcion;
	
	/**
	 * Empaqueta una lista de ficheros, enviando los ficheros como array de Byte
	 * @param fichero
	 * @return
	 * @throws IOException
	 * @throws OegamExcepcion
	 */
	public File empaquetarEnZipByte(FicheroBean fichero) throws IOException, OegamExcepcion;
	
	/**
	 * 
	 * @param tipo utilizar las constantes
	 * @param subTipo
	 * @param numExpediente
	 * @param nombreFichero
	 * @param extension
	 * @param fichero
	 * @return
	 */
	public File guardarFichero(String tipo,String subTipo,Fecha fecha,String nombreFichero, String extension,byte[] fichero) throws OegamExcepcion;

	
	/**
	 * Crea un fichero en el directorio que se envia en tipo sub tipo con la historificación.
	 * Se envia un lista de String
	 * @param fichero Datos para guardar el archivo
	 * @param lineas listado de String para crear el fichero
	 * @return Fichero guardado
	 */
	public File guardaFicheroEnviandoStrings(FicheroBean fichero, List<String> lineas);
	
	/**
	 * 
	 * @param tipo utilizar las constantes
	 * @param subTipo
	 * @param numExpediente
	 * @param nombreFichero
	 * @param extension
	 * @param fichero
	 * @return
	 */
	public File guardarFichero(String tipo,String subTipo,Fecha fecha,String nombreFichero, String extension,File fichero) throws OegamExcepcion;

	
	/**
	 * 
	 * @param ficheroBean
	 * @param nameContext
	 * @param objetoXml
	 * @throws OegamExcepcion
	 */
	public void  crearFicheroXml(FicheroBean ficheroBean, String nameContext, Object objetoXml, String xml, String pathXsd) throws OegamExcepcion, Throwable;
	
	/**
	 * Transforma un File en un array de bytes.
	 * @param file
	 * @return
	 * @throws FileNotFoundException 
	 * @throws IOException 
	 */
	public byte[] transformFiletoByte(File file) throws IOException;
	
	/**
	 * Comprueba si un fichero existe y devuelve una lista con los ficheros de logs
	 * @param url
	 * @param nombreLog
	 * @param fecha
	 * @return listaNombresLogs
	 * @throws OegamExcepcion
	 */
	
	public List <String> listarFicherosLogs(String url, String nombreLog, FechaFraccionada fecha) throws OegamExcepcion;
	
	/**
	 * Devuelve un zip con los ficheros de log o si es un solo fichero lo abre directamente con Notepad
	 * @param listadoNombreLogs
	 * @return inputStream
	 * @throws OegamExcepcion
	 * @throws IOException 
	 */
	
	public InputStream getFicherosLogs(List<String> listadoNombreLogs) throws OegamExcepcion, IOException;
	
	/**
	 * Devuelve el fichero a buscar en el zip
	 * @param zip
	 * @param nombreFicheroBuscar
	 * @return InputStream
	 * @throws IOException 
	 */
	public InputStream extraerFicheroZip(File zip, String nombreFicheroBuscar) throws IOException, OegamExcepcion;

	public String guardarFicheroSerializable(FicheroBean fichero, Object serializable) throws OegamExcepcion;

	public Object obtenerCollectionSerializable(File file);
	
	public byte[] extraerFicheroZipByte(File zip, String nombreFicheroBuscar) throws IOException, OegamExcepcion;
	
	public File extraerFicheroZipFile(File zip, String nombreFicheroBuscar) throws IOException, OegamExcepcion;
	
	public String crearRuta(String tipo, String subTipo, Fecha fecha);

	File guardaFicheroLineas(FicheroBean fichero, List<String> lineas);

	public Boolean renombrarFichero(String tipo, String subTipo, Fecha fecha, String nombreFichero,
			String extension, File fichero);

	public String obtenerRutaFichero(String tipo, String subTipo,Boolean subCarpetaDia, Fecha fecha);

	File obtenerFileToByte(String path, byte[] fichero);
	
	List <String> listarFicherosSantander(String url) throws IOException;
	
	public void borrarFicheroSantander(String url);

	public FileResultBean buscarFicheroPorNombreTipoAM(String mate, String permisosDefinitivo,Fecha transformExpedienteFecha, String string, String extensionPdf) throws OegamExcepcion;

}
