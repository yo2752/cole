package org.oegam.gestor.distintivos.service.impl;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.gestoresmadrid.core.gestion.ficheros.model.dao.OrganizacionFicherosDao;
import org.gestoresmadrid.core.gestion.ficheros.model.vo.OrganizacionFicherosVO;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.oegam.gestor.distintivos.constants.ConstantesGestorFicheros;
import org.oegam.gestor.distintivos.enums.FileResultStatus;
import org.oegam.gestor.distintivos.integracion.bean.FicheroBean;
import org.oegam.gestor.distintivos.integracion.bean.FileResultBean;
import org.oegam.gestor.distintivos.service.BuscadorAlternativo;
import org.oegam.gestor.distintivos.service.GuardarDocumentosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.estructuras.Fecha;
import utilidades.estructuras.FechaFraccionada;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;
import utilidades.web.OegamExcepcion.EnumError;

@Service
public final class GuardarDocumentoServiceImpl implements GuardarDocumentosService {

	private static final String TIPO_DOCUMENTO_DEFECTO ="TIPO_DOCUMENTO_DEFECTO";
	private static final String BARRA ="/";
	private static final ILoggerOegam log = LoggerOegam.getLogger(GuardarDocumentoServiceImpl.class);
	private static final String XML_ENCODING_UTF8 = "UTF-8";

	@Autowired OrganizacionFicherosDao organizacionFicherosDAO;
	@Autowired GestorPropiedades gestorPropiedades;
	@Autowired
	UtilesFecha utilesFecha;
	
	private static ArrayList<File> ficherosDirectorio = new ArrayList<File>();
	
	@Transactional(readOnly = true)
	@Override
	public FileResultBean buscarFicheroPorNombreTipoAM(String tipo, String subTipo, Fecha fecha, String nombre, String extension) throws OegamExcepcion {

		FileResultBean result = new FileResultBean();
		String mes="",anio="",url="";
		
		if(tipo!=null && !tipo.equals("")){
			String obtenerRutaAlmacenamientoAM = null;
			Date fechaActual = new Date();
			if (fechaActual.after(new Date(123, 1,15))){
				obtenerRutaAlmacenamientoAM = gestorPropiedades.valorPropertie("gestion.documentos.nodo5");
			}
			else{
				obtenerRutaAlmacenamientoAM = gestorPropiedades.valorPropertie("obtener.ruta.almacenamientoAM");
			}
			
			url = obtenerRutaAlmacenamientoAM;
			url+=tipo+BARRA;
			if(subTipo!=null && !subTipo.equals("")){
				url+=subTipo+BARRA;
			}
			if(fecha!=null && !fecha.equals("")){
				mes = fecha.getMes();
				anio = fecha.getAnio();
				url +=anio+BARRA+mes+BARRA;
			}
			File directorio = new File (url);
			if(directorio.exists()){
				url+=nombre;
				if(extension!=null && !extension.equals("")){
					url+=extension;
				}
				result.setFile(new File(url));
				if(result.getFile().exists()){
					result.setStatus(FileResultStatus.UNIQUE_FILE_FOUND);
					return result;
				} 
			}
									
		}

		if(result.getFile()==null || !result.getFile().exists()){
			BuscadorAlternativo buscadorAlternativo = getClassBuscador(tipo);
			nombre+=extension;
			if (buscadorAlternativo != null) {
				result.setFile(buscadorAlternativo.buscarNombre(tipo, subTipo, nombre));
			}
		}

		if (result.getFile()!= null && result.getFile().exists()) {
			result.setStatus(FileResultStatus.UNIQUE_FILE_FOUND);
		} else if (result.getStatus() == null){
			result.setStatus(FileResultStatus.FILE_NOT_FOUND);
			result.setFile(null);
		} else {
			result.setFile(null);
		}

		return result;
	}
	
	
	/**
	 * @see GuardarDocumentosInterfaz guardarByte()
	 */
	@Transactional(readOnly = true)
	public File guardarByte(FicheroBean fichero) throws OegamExcepcion{
		
		File archivoGuardar = null;
		try {
//			String ipNodo = getUrlDirectorio(fichero.getTipoDocumento());
			String ipNodo = obtenerRutaAlmacenamiento(fichero.getTipoDocumento(), fichero.getFecha());

			//Se obtiene la ruta de la carpeta
			String url = getDirectorioFichero(ipNodo,fichero);

			int mismoNombre =0;
			if(fichero.getFicheroByte() !=null){

				File directorio = new File(url);

				// Si el directorio existe
				if (!directorio.mkdirs()) {
					if (!fichero.isSobreescribir() && directorio.listFiles() != null) {
						// Busco y cuento en su lista de ficheros nombres parecidos el que voy a crear
						for (File next : directorio.listFiles()) {
							if (next.getName().indexOf(fichero.getNombreDocumento())!=-1) {
								mismoNombre++;
							}
						}
					}
				}
				
				String direccionGuardado = url + fichero.getNombreDocumento()+ fichero.getExtension(); 
				File existe = new File(direccionGuardado);

				
				if (existe.exists()){ 
					if (fichero.isSobreescribir()){
						existe.delete();
					}else{
						direccionGuardado = url + fichero.getNombreDocumento()+"_"+mismoNombre+ fichero.getExtension();
					}
				archivoGuardar = new File(direccionGuardado);
				}
				
				FileOutputStream ficheroSalida = new FileOutputStream(direccionGuardado ,false);
				ficheroSalida.write(fichero.getFicheroByte());
				ficheroSalida.flush(); 
				ficheroSalida.close();

				archivoGuardar = new File(direccionGuardado); 
			}
			else{
				throw new OegamExcepcion(ConstantesGestorFicheros.ERROR_NO_FICHERO);
			}


		} catch (FileNotFoundException e) {
			log.error(e);
			throw new OegamExcepcion(ConstantesGestorFicheros.ERROR_NO_FICHERO);
		} catch (IOException e) {
			log.error(e);
			throw new OegamExcepcion(ConstantesGestorFicheros.ERROR_GUARDAR_ARCHIVO);
		}

		return archivoGuardar;
	}
	
	@Override
	@Transactional(readOnly = true)
	public String guardarFicheroSerializable(FicheroBean fichero, Object serializable) throws OegamExcepcion {
		String nombreFichero = null;
		try {
			String ipNodo = obtenerRutaAlmacenamiento(fichero.getTipoDocumento(), fichero.getFecha());
			//Se obtiene la ruta de la carpeta
			String url = getDirectorioFichero(ipNodo,fichero);
			int mismoNombre =0;
			File directorio = new File(url);

			// Si el directorio existe
			if (!directorio.mkdirs()) {
				if (!fichero.isSobreescribir() && directorio.listFiles() != null) {
					// Busco y cuento en su lista de ficheros nombres parecidos el que voy a crear
					for (File next : directorio.listFiles()) {
						if (next.getName().indexOf(fichero.getNombreDocumento())!=-1) {
							mismoNombre++;
						}
					}
				}
			}
			nombreFichero = fichero.getNombreDocumento();
			String direccionGuardado = url + nombreFichero;
			if(fichero.getExtension() != null){
				direccionGuardado += fichero.getExtension();
			}
			File existe = new File(direccionGuardado);

			if (existe.exists()){ 
				if (fichero.isSobreescribir()){
					existe.delete();
				}else{
					nombreFichero = fichero.getNombreDocumento()+"_"+mismoNombre;
					direccionGuardado = url + nombreFichero;
					if(fichero.getExtension() != null){
						direccionGuardado += fichero.getExtension();
					}
				}
			}
				
			FileOutputStream ficheroSalida = new FileOutputStream(direccionGuardado);
	        ObjectOutputStream out = new ObjectOutputStream(ficheroSalida);
	        out.writeObject(serializable);
	        out.close();
	        ficheroSalida.close();
		} catch (FileNotFoundException e) {
			log.error(e);
			throw new OegamExcepcion(ConstantesGestorFicheros.ERROR_NO_FICHERO);
		} catch (IOException e) {
			log.error(e);
			throw new OegamExcepcion(ConstantesGestorFicheros.ERROR_GUARDAR_ARCHIVO);
		}
		return nombreFichero;
	}
	
	/**
	 *  @see GuardarDocumentosInterfaz guardarFichero()
	 */
	@Transactional(readOnly = true)
	public File guardarFichero(FicheroBean fichero) throws OegamExcepcion{
		
		File archivoGuardar =null;
		try {
//			String ipNodo = getUrlDirectorio(fichero.getTipoDocumento());
			String ipNodo = obtenerRutaAlmacenamiento(fichero.getTipoDocumento(), fichero.getFecha()); 
			String url ="";


			//Se obtiene la ruta de la carpeta
			url = getDirectorioFichero(ipNodo,fichero);

			int mismoNombre =0;
			if(fichero.getFichero()!=null | (fichero.getNombreZip()==null || "".equals(fichero.getNombreZip()))){
					
				File directorio = new File( url );
				
				// Si el directorio existe
				if (!directorio.mkdirs()) {
					if (!fichero.isSobreescribir() && directorio.listFiles() != null) {
						// Busco y cuento en su lista de ficheros nombres parecidos el que voy a crear
						for (File next : directorio.listFiles()) {
							if (next.getName().indexOf(fichero.getNombreDocumento())!=-1) {
								mismoNombre++;
							}
						}
					}
				}
				
				String direccionGuardado = url + fichero.getNombreDocumento()+ fichero.getExtension(); 

				archivoGuardar = new File(direccionGuardado);

				if (archivoGuardar.exists())
				{ if (fichero.isSobreescribir())
				{
					archivoGuardar.delete();
				}else
				{
					direccionGuardado = url + fichero.getNombreDocumento()+"_"+mismoNombre+ fichero.getExtension();
				}
				archivoGuardar = new File(direccionGuardado);
				}
				//Si los ficheros se empaquetan en zip
				if (fichero.getNombreZip()!=null && !"".equals(fichero.getNombreZip()))
				{
					InputStream in = new FileInputStream(fichero.getFichero());
					OutputStream out = new FileOutputStream(archivoGuardar);

					byte[] buf = new byte[1024];
					int len;

					while ((len = in.read(buf)) > 0) {
						out.write(buf, 0, len);
					}				
					in.close();
					out.close();
				}
				//Si los archivos "no" se empaquetan en zip, se crea el archivo
				else if(fichero.getFichero()!=null && !fichero.getFichero().getPath().isEmpty() && fichero.getFichero().exists()){

					InputStream in = new FileInputStream(fichero.getFichero());
					OutputStream out = new FileOutputStream(archivoGuardar);

					byte[] buf = new byte[1024];
					int len;

					while ((len = in.read(buf)) > 0) {
						out.write(buf, 0, len);
					}				
					in.close();
					out.close();	

				}
				else if(fichero.getFicheroByte()!=null){
					
					FileOutputStream ficheroSalida = new FileOutputStream(direccionGuardado ,true);
					ficheroSalida.write(fichero.getFicheroByte());
					ficheroSalida.flush(); 
					ficheroSalida.close();
					
				}
				else{
					archivoGuardar.createNewFile();
				}
			}else{
				throw new OegamExcepcion(ConstantesGestorFicheros.ERROR_NO_FICHERO);
			}


		} catch (FileNotFoundException e) {
			log.error(e);
			throw new OegamExcepcion(ConstantesGestorFicheros.ERROR_NO_FICHERO);
		} catch (IOException e) {
			log.error(e);
			throw new OegamExcepcion(ConstantesGestorFicheros.ERROR_GUARDAR_ARCHIVO);
		}

		return archivoGuardar;
	}

	/**
	 *  @see GuardarDocumentosInterfaz buscarFicheroPorNumExpTipo()
	 */
	@Transactional(readOnly = true)
	public FileResultBean buscarFicheroPorNumExpTipo(String tipo,String subTipo,Fecha fecha,String nombre) throws OegamExcepcion {
		
		FileResultBean result = new FileResultBean();
		result.setFiles(new ArrayList<File>());

		String mes,anio,url="";
		String nom="";
		if(nombre!=null && !nombre.equals("")){
			nom=nombre;
		}

//		String ipNodo = getUrlDirectorio(tipo);
		String ipNodo = obtenerRutaAlmacenamiento(tipo, fecha);
		url = ipNodo;
		if(tipo != null && !tipo.equals("")){
			url +=tipo+BARRA;
			if(subTipo!=null && !subTipo.equals("")){
				url +=subTipo+BARRA;
			}
			if(fecha!=null){
				mes = fecha.getMes();
				anio = fecha.getAnio();
				url +=anio+BARRA+mes+BARRA;
			}

			File directorio = new File (url); 
			if(directorio.exists()){

				for (File next :directorio.listFiles()){

					if(next.getName().indexOf(nom)!=-1){
						result.getFiles().add(next);
					}
				}

			}

			if( result.getFiles().isEmpty()){
				BuscadorAlternativo buscadorAlternativo = getClassBuscador(tipo);
				if (buscadorAlternativo != null) {
					result.setFiles(buscadorAlternativo.buscar(tipo, subTipo, new BigDecimal(nombre)));
				}
			}

		}
		else{
			throw new OegamExcepcion(ConstantesGestorFicheros.ERROR_TIPO_DOCUMENTO);
		}

		if (result.getFiles()!= null && !result.getFiles().isEmpty()) {
			result.setStatus(FileResultStatus.LIST_FILE_FOUND);
		} else {
			result.setStatus(FileResultStatus.FILE_NOT_FOUND);
		}

		return result;
	}

	@Transactional(readOnly = true)
	public FileResultBean buscarFicheroPorNombreTipo(String tipo, String subTipo, Fecha fecha, String nombre, String extension) throws OegamExcepcion {

		FileResultBean result = new FileResultBean();
		String mes="",anio="",url="";
		String ipNodo = "";
		
		if(tipo!=null && !tipo.equals("")){

			ipNodo = obtenerRutaAlmacenamiento(tipo, fecha);
			url = ipNodo;
			url+=tipo+BARRA;
			if(subTipo!=null && !subTipo.equals("")){
				url+=subTipo+BARRA;
			}
			if(fecha != null && !fecha.equals("")){
				mes = fecha.getMes();
				anio = fecha.getAnio();
				url += anio + BARRA + mes + BARRA;
			}
			File directorio = new File (url);
			if(directorio.exists()){
				url += nombre;
				if(extension != null && !extension.equals("")){
					url += extension;
				}
				result.setFile(new File(url));
				
				if(result.getFile().exists()){
					result.setStatus(FileResultStatus.UNIQUE_FILE_FOUND);
					return result;
				} 
			}
			
		}
		
		if (result.getFile()==null || !result.getFile().exists()) {
			String logMessage = String.format("El fichero %s no encontrado", url);
			log.info(logMessage);
			throw new OegamExcepcion(EnumError.error_NoExisteFicheroErrores, logMessage);
		}
		
		return result;
	}
	
	@Transactional(readOnly = true)
	public File buscarFicheroPorNombreTipoYDia(String tipo, String subTipo, Fecha fecha, String nombre, String extension) throws OegamExcepcion {
		
		File resultado = null;
		String mes="",anio="",url="",dia="";
		String ipNodo = "";

		if(tipo!=null && !tipo.equals("")){

//			ipNodo = getUrlDirectorio(tipo);
			ipNodo = obtenerRutaAlmacenamiento(tipo, fecha);
			url = ipNodo;
			url+=tipo+BARRA;
			if(subTipo!=null && !subTipo.equals("")){
				url+=subTipo+BARRA;
			}
			if(fecha!=null && !fecha.equals("")){
				mes = fecha.getMes();
				anio = fecha.getAnio();
				dia = fecha.getDia();
				url +=anio+BARRA+mes+BARRA+dia+BARRA;
			}
			File directorio = new File (url);
			if(directorio.exists()){
				url+=nombre;
				if(extension!=null && !extension.equals("")){
					url+=extension;
				}
				resultado =new File(url);
				if(resultado.exists()){
					return resultado;
				} else {
					resultado=null;
				}
			}
									
		}

		return resultado;
	}

	/**
	 *  @see GuardarDocumentosInterfaz buscarFicheroPorUrl
	 */
	public File buscarFicheroPorUrl(String url) throws OegamExcepcion {
		File fichero = new File(url);

		if (fichero.exists()) {
			return fichero;
		}

		return null;
	}
	
	public static void listarDirectorio(String url, String nombre) {
		if(!url.endsWith(System.getProperty("file.separator"))){
			url += System.getProperty("file.separator");
		}

		File directorio = new File(url);

		if (directorio.exists()) {
			for(File itFile: directorio.listFiles()){
				if(itFile.isDirectory()){
					listarDirectorio(itFile.getAbsolutePath(), nombre);
				} else if (itFile.isFile()) {

					if (nombre != null && itFile.getName().equals(nombre)) {
						ficherosDirectorio.add(itFile);
					} else if (nombre == null || nombre.isEmpty()) {
						ficherosDirectorio.add(itFile);
					}
				}
			}
		}
	}

	/**
	 *  @see GuardarDocumentosInterfaz borraFicheroSiExiste()
	 */
	@Transactional(readOnly = true)
	public void borraFicheroSiExiste(String tipo, String subTipo,Fecha fecha,
			String nombre) throws OegamExcepcion {

		FileResultBean fileResultBean = buscarFicheroPorNumExpTipo(tipo, subTipo, fecha, nombre);
		if(fileResultBean!= null && fileResultBean.getFiles()!=null){
			for(File fichero: fileResultBean.getFiles()){
				if (fichero.exists())
					fichero.delete();
			}
		}
	}

	/**
	 *  @see GuardarDocumentosInterfaz borraFicheroSiExistePorNombreTipo()
	 */
	@Transactional(readOnly = true)
	public void borraFicheroSiExisteConExtension(String tipo, String subTipo, Fecha fecha, String nombre, String extension) throws OegamExcepcion {
		File fichero = buscarFicheroPorNombreTipo(tipo, subTipo, fecha, nombre, extension).getFile();
		if (fichero != null && fichero.exists()) {
			fichero.delete();
			borradoRecursivo(new File(fichero.getParent()));
		}
	}

	public void borradoRecursivo(File fichero) {
		String newPath = fichero.getParent();
		if (fichero.exists()) {
			if (fichero.delete()) {
				borradoRecursivo(new File(newPath));
			}
		}
	}

	/**
	 *  @see GuardarDocumentosInterfaz  empaquetarEnZipFile()
	 */
	@Transactional(readOnly = true)
	public File empaquetarEnZipFile(FicheroBean fichero)
			throws IOException, OegamExcepcion {
		File result = null;
		if(fichero.getListaFicheros()!=null && fichero.getListaFicheros().size()>0){
			if(fichero.getNombreZip() != null && !fichero.getNombreZip().equals("")){

				List<File> ficheros = fichero.getListaFicheros();
				String nombreDelZip = fichero.getNombreZip();
				if(ficheros.size()<1){
					return null;
				}
				File destino = null;

				if(!nombreDelZip.contains(ConstantesGestorFicheros.EXTENSION_ZIP)){
					destino = new File(nombreDelZip + ConstantesGestorFicheros.EXTENSION_ZIP);
				}else{
					destino = new File(nombreDelZip);
				}
				FileOutputStream out = new FileOutputStream(destino);
				ZipOutputStream zip = new ZipOutputStream(out);
				for(File file:ficheros){
					FileInputStream is = new FileInputStream(file);
					ZipEntry zipEntry = new ZipEntry(file.getName());
					zip.putNextEntry(zipEntry);
					
					byte[] buffer = new byte[2048];
					int byteCount;
					while (-1 != (byteCount = is.read(buffer))) {
						zip.write(buffer, 0, byteCount);
					}
					zip.closeEntry();
					is.close();
					if (file.lastModified() > 0) {
						zipEntry.setTime(file.lastModified());
					}
				}
				zip.close();

				if(destino.exists()){
					fichero.setFichero(destino);
					fichero.setExtension(ConstantesGestorFicheros.EXTENSION_ZIP);
					fichero.setNombreDocumento(fichero.getNombreZip());
					result=guardarFichero(fichero);
				}
			}
			else{
				throw new OegamExcepcion(ConstantesGestorFicheros.ERROR_LISTA_FICHEROS);
			}
		}
		else{
			throw new OegamExcepcion(ConstantesGestorFicheros.ERROR_LISTA_FICHEROS);
		}


		return result;
	}

	/**
	 * 
	 *  @see GuardarDocumentosInterfaz  empaquetarEnZipFileCarpetaFecha()
	 */
	@Transactional(readOnly = true)
	public File empaquetarEnZipFileCarpetaFecha(FicheroBean fichero)
			throws IOException, OegamExcepcion {
		File result = null;
		if(fichero.getListaFicheros()!=null && fichero.getListaFicheros().size()>0){
			if(fichero.getNombreZip() != null && !fichero.getNombreZip().equals("")){

				List<File> ficheros = fichero.getListaFicheros();
				String nombreDelZip = fichero.getNombreZip();
				if(ficheros.size()<1){
					return null;
				}
				File destino = null;

				if(!nombreDelZip.contains(ConstantesGestorFicheros.EXTENSION_ZIP)){
					destino = new File(nombreDelZip + ConstantesGestorFicheros.EXTENSION_ZIP);
				}else{
					destino = new File(nombreDelZip);
				}
				FileOutputStream out = new FileOutputStream(destino);
				ZipOutputStream zip = new ZipOutputStream(out);
				for(File file:ficheros){
					FileInputStream is = new FileInputStream(file);
					String mes = file.getName().toString().substring(6, 8);
					String anio = file.getName().toString().substring(8, 10);
					ZipEntry zipEntry = new ZipEntry(anio + "/" + mes + "/" + file.getName());
					zip.putNextEntry(zipEntry);
					
					byte[] buffer = new byte[2048];
					int byteCount;
					while (-1 != (byteCount = is.read(buffer))) {
						zip.write(buffer, 0, byteCount);
					}
					zip.closeEntry();
					is.close();
					if (file.lastModified() > 0) {
						zipEntry.setTime(file.lastModified());
					}
				}
				zip.close();

//				if(destino.exists()){
//					fichero.setFichero(destino);
//					fichero.setExtension(ConstantesGestorFicheros.EXTENSION_ZIP);
//					fichero.setNombreDocumento(fichero.getNombreZip());
//					result=guardarFichero(fichero);
//				}
			}
			else{
				throw new OegamExcepcion(ConstantesGestorFicheros.ERROR_LISTA_FICHEROS);
			}
		}
		else{
			throw new OegamExcepcion(ConstantesGestorFicheros.ERROR_LISTA_FICHEROS);
		}


		return result;
	}
	/**
	 * @see GuardarDocumentosInterfaz empaquetarEnZipByte()
	 */
	@Transactional(readOnly = true)
	public File empaquetarEnZipByte(FicheroBean fichero) throws IOException, OegamExcepcion {
		
		File result = null;
		if(fichero.getListaByte()!=null && fichero.getListaByte().size()>0){
			if(fichero.getNombreZip() != null && !fichero.getNombreZip().equals("")){

				Map<String, byte[]> ficheros = fichero.getListaByte();
				String nombreDelZip = fichero.getNombreZip();

				FileOutputStream out = null;
				ZipOutputStream zip = null;
				try {
					//Se obtiene la ruta de la carpeta
//					String ipNodo = getUrlDirectorio(fichero.getTipoDocumento());
					String ipNodo = obtenerRutaAlmacenamiento(fichero.getTipoDocumento(), fichero.getFecha());
					String url = getDirectorioFichero(ipNodo,fichero);

					File directorio = new File( url );

					if (!directorio.mkdirs()) {
						log.debug("No se ha podido crear la ruta de directorios");
					}

					if(!nombreDelZip.contains(ConstantesGestorFicheros.EXTENSION_ZIP)){
						result = new File(url + nombreDelZip + ConstantesGestorFicheros.EXTENSION_ZIP);
					}else{
						result = new File(url + nombreDelZip);
					}

					// En el caso de que se vaya a sobreescribir, comprobar si existe un zip previo,
					// para recuperar sus zipEntries
					List<String> nombres = new ArrayList<String>();
					Map<ZipEntry, byte[]> existingZips = new HashMap<ZipEntry, byte[]>();
					if (result.exists() && !fichero.isSobreescribir()) {
						ZipFile zipFile = null;
						try {
							zipFile = new ZipFile(result);
							Enumeration<? extends ZipEntry> entries = zipFile.entries();
							while (entries.hasMoreElements()) {
								ZipEntry zipEntry = (ZipEntry) entries.nextElement();
								InputStream in = null;
								ByteArrayOutputStream output = null;
								try {
									in = zipFile.getInputStream(zipEntry);
									output = new ByteArrayOutputStream();
									int data = 0;
									while ((data = in.read()) != -1) {
										output.write(data);
									}

									existingZips.put(new ZipEntry(zipEntry), output.toByteArray());
									nombres.add(zipEntry.getName());

								} catch (Exception e) {
									log.error("Error recuperando entradas antiguas", e);
								} finally {
									if (output != null) {
										try {
											output.close();
										} catch (IOException ioe) {
											log.error("Error cerrando ByteArrayOutputStream", ioe);
										}
									}
									if (in != null) {
										try {
											in.close();
										} catch (IOException ioe) {
											log.error("Error cerrando inputstream", ioe);
										}
									}
								}
							}
						} catch (Exception e) {
							log.error("No se puede mantener las entradas anteriores del zip",e);
						} finally {
							if (zipFile != null) {
								try {
									zipFile.close();
								} catch (IOException e) {
									log.error("Error cerrando zipFile", e);
								}
							}
						}
					}

					out = new FileOutputStream(result);
					zip = new ZipOutputStream(out);

					for (ZipEntry zipEntry: existingZips.keySet()){
						zip.putNextEntry(zipEntry);
						zip.write(existingZips.get(zipEntry));
						zip.closeEntry();
					}

					for (String key: ficheros.keySet()){
						String name = key;

						if (nombres.contains(name)){
							int contador = 1;
							while (nombres.contains(name)) {
								name = key.substring(0, key.lastIndexOf(".")) + " (" + contador + ")" + key.substring(key.lastIndexOf("."));
							}
							nombres.add(name);
						}

						ZipEntry zipEntry = new ZipEntry(name);
						zip.putNextEntry(zipEntry);
						zip.write(ficheros.get(key));
						zip.closeEntry();
					}

				} catch (FileNotFoundException e) {
					log.error(e);
					throw new OegamExcepcion(ConstantesGestorFicheros.ERROR_NO_FICHERO);
				} catch (IOException e) {
					log.error(e);
					throw new OegamExcepcion(ConstantesGestorFicheros.ERROR_GUARDAR_ARCHIVO);
				} finally {
					if (zip != null) {
						try {
							zip.close();
						} catch (IOException ioe) {
							log.error("Error cerrando ZipOutputStream", ioe);
						}
					}
					if (out != null) {
						try {
							out.close();
						} catch (IOException ioe) {
							log.error("Error cerrando FileOutputStream", ioe);
						}
					}
				}
			} else{
				throw new OegamExcepcion(ConstantesGestorFicheros.ERROR_LISTA_FICHEROS);
			}
		} else{
			throw new OegamExcepcion(ConstantesGestorFicheros.ERROR_LISTA_FICHEROS);
		}
		return result;
	}

	/**
	 * @see GuardarDocumentosInterfaz guardarFichero()
	 */
	@Transactional(readOnly = true)
	public File guardarFichero(String tipo, String subTipo,
			Fecha fecha,String nombreFichero, String extension,byte[] fichero) throws OegamExcepcion {

		FicheroBean ficheroBean = new FicheroBean(tipo, fecha, subTipo, nombreFichero, extension, null, fichero, true, null, null, null); 

		File resultado = guardarByte(ficheroBean);

		return resultado;
	}

	/**
	 * @see GuardarDocumentosInterfaz guardarFichero()
	 */
	@Transactional(readOnly = true)
	public File guardarFichero(String tipo, String subTipo,
			Fecha fecha,String nombreFichero, String extension,File fichero) throws OegamExcepcion {

		FicheroBean ficheroBean = new FicheroBean(tipo, fecha, subTipo, nombreFichero, extension, fichero, null, true, null, null, null); 

		File resultado = guardarByte(ficheroBean);

		return resultado;
	}


//	/**
//	 * Obtiene el nodo raiz para guardar los archivos 
//	 * @param nodo
//	 * @return
//	 * @throws OegamExcepcion
//	 */
//	private String getUrlDirectorio(String nodo) throws OegamExcepcion{
//
//		String ipNodo ="";
//
//		String nombreNodo = gestorPropiedades.valorPropertie(nodo);
//		if(nombreNodo==null || nombreNodo.equals("")){
//			nombreNodo = gestorPropiedades.valorPropertie(NODO_DEFECTO);
//		}
//		ipNodo = gestorPropiedades.valorPropertie(nombreNodo);
//		ipNodo += gestorPropiedades.valorPropertie(OEGAM_FICHEROS);
//
//		return ipNodo;
//		
//	}


	/**
	 * Método que obtiene la ruta de almacenamiento de los ficheros para un tipo de documento y una fecha.
	 *  
	 * @param tipoDocumento tipo de documento.
	 * @param fechaConsulta fecha de consulta.
	 * @return String - ruta de almacenamiento de los ficheros para ese tipo de documento y fecha.
	 */
	private String obtenerRutaAlmacenamiento(String tipoDocumento, Fecha fecha) {
	
		String rutaAlmacenamiento = "";
		
		try {
			
			// Si no se recibe un tipo de documento, no se puede buscar su ruta de almacenamiento en la entidad. Establecemos entonces una ruta por defecto
			if (tipoDocumento == null || tipoDocumento.equals("")) {
				tipoDocumento = TIPO_DOCUMENTO_DEFECTO;
			}
			
			// Si no se recibe fecha, se busca para la fecha actual
			Date date = (fecha != null) ? fecha.getDate() : new Date();
			
			// Consultamos la información de almacenamiento por tipo de documento y fecha
			OrganizacionFicherosVO organizacionFicherosVO = organizacionFicherosDAO.consultarInformacionTipoDocumento(tipoDocumento, date);

			if (organizacionFicherosVO != null) {
				// Obtenemos la propiedad 'NODO' para obtener la ruta raíz de acceso
				//TODO
				//String nodo = gestorPropiedades.valorPropertie(organizacionFicherosVO.getNodo());
				String nodo = gestorPropiedades.valorPropertie(organizacionFicherosVO.getNodo());
				if (nodo == null) nodo = "";
				
				// Fomamos la ruta completa
				String carpeta = (organizacionFicherosVO.getCarpeta() != null) ? organizacionFicherosVO.getCarpeta() : "";
				rutaAlmacenamiento = nodo + carpeta;

			} else {
				
				// No hemos encontrado una ruta para el tipo de documento -> Obtenemos la ruta de almacenamiento por defecto
				if (!TIPO_DOCUMENTO_DEFECTO.equals(tipoDocumento)) {
					log.error("GestorDocumentos - consultarOrganizacionFicheros() - ERROR: no se ha encontrado la ruta de organización de los ficheros del tipo '" + tipoDocumento + "' para la fecha '" + fecha + "'.");
					rutaAlmacenamiento = obtenerRutaAlmacenamiento(TIPO_DOCUMENTO_DEFECTO, fecha);
				}
			}
			
		} catch (ParseException e) {
			log.error("GestorDocumentos - consultarOrganizacionFicheros() - ERROR: no se ha podido parsear correctamente la fecha '" + fecha + "': ", e);
			e.printStackTrace();
		} finally {
			log.info("GestorDocumentos - consultarOrganizacionFicheros() - INFO: rutaAlmacenamiento='" + rutaAlmacenamiento + "'.");
		}
		
		return rutaAlmacenamiento;
	}


	/**
	 * 
	 * @param nodo
	 * @return
	 * @throws OegamExcepcion
	 */
	private BuscadorAlternativo getClassBuscador(String nodo) throws OegamExcepcion{
		String nombreNodoMatriculador = gestorPropiedades.valorPropertie(nodo+ConstantesGestorFicheros.BUSCADOR_ALTERNATIVO);
		BuscadorAlternativo buscador = null;
		if(nombreNodoMatriculador!=null && !nombreNodoMatriculador.isEmpty()){
			try {
				buscador =(BuscadorAlternativo) Class.forName(nombreNodoMatriculador).newInstance();
			} catch (InstantiationException e) {
				throw new OegamExcepcion(ConstantesGestorFicheros.ERROR_BUSCADOR_ALTERNATIVO);
			} catch (IllegalAccessException e) {
				throw new OegamExcepcion(ConstantesGestorFicheros.ERROR_BUSCADOR_ALTERNATIVO);
			} catch (ClassNotFoundException e) {
				throw new OegamExcepcion(ConstantesGestorFicheros.ERROR_BUSCADOR_ALTERNATIVO);
			}
		}
		return buscador;
	}




	/**
	 *@throws JAXBException 
	 * @throws Exception 
	 * @see GuardarDocumentosInterfaz crearFicheroXml()
	 */
	@Transactional(readOnly = true)
	public void crearFicheroXml(FicheroBean ficheroBean, String nameContext, Object objetoXml, String xml, String pathXsd) throws OegamExcepcion, Throwable	{	

		String ipNodo;
		String url;
		String rutaFichero;
		File rutaCarpeta= null;

		//Se obtiene el nodo donde se guardan los archivos
//		ipNodo = getUrlDirectorio(ficheroBean.getTipoDocumento());
		ipNodo = obtenerRutaAlmacenamiento(ficheroBean.getTipoDocumento(), ficheroBean.getFecha());

		//Se obtiene la ruta de la carpeta
		url = getDirectorioFichero(ipNodo,ficheroBean);

		//Se obtiene la ruta del fichero
		rutaFichero = url + ficheroBean.getNombreDocumento()+ConstantesGestorFicheros.EXTENSION_XML; 

		//Se crea la carpeta si no existe 
		if (url!=null && !"".equals(url))
		{    rutaCarpeta= new File(url);
		if (!rutaCarpeta.exists())
		{
			rutaCarpeta.mkdirs();
		}			
		}			
		//Se crea o se actualiza el archivo xml
		ficheroBean.setFichero(new File(rutaFichero));		

		//Se guarda el archivo xml con la codificación correspondiente
		try {		
			if (xml!=null && !"".equals(xml))
			{	
				grabarEnFicheroUTF8(xml,ficheroBean.getFichero(), pathXsd);
			}else{
				grabarEnFicheroUTF8(nameContext,objetoXml,true,ficheroBean.getFichero(),XML_ENCODING_UTF8, pathXsd);	
			}
		} catch (JAXBException e) {
			log.error("Error guardando el archivo XML", e);
			throw new OegamExcepcion(ConstantesGestorFicheros.ERROR_CREACION_FICHERO_XML);
		} 	
	}

	/**
	 * 
	 * @param ipNodo
	 * @return
	 * @throws OegamExcepcion 
	 */
	private String getDirectorioFichero(String ipNodo, FicheroBean ficheroBean) throws OegamExcepcion
	{
		String url ="";
		String dia ="";
		String mes ="";
		String anio ="";

		//Se obtiene el nombre del directorio de acuerdo al tipo de Documento
		if(ficheroBean.getTipoDocumento()!=null && !ficheroBean.getTipoDocumento().equals("")){
			url = ficheroBean.getTipoDocumento()+BARRA;
		}
		else{
			throw new OegamExcepcion(ConstantesGestorFicheros.ERROR_TIPO_DOCUMENTO);

		}
		//Se obtiene el nombre del directorio de acuerdo al subtipo 
		if(ficheroBean.getSubTipo()!=null && !ficheroBean.getSubTipo().equals("") ){
			url += ficheroBean.getSubTipo()+BARRA;
		}		
		if(ficheroBean.getFecha()!=null && !ficheroBean.getFecha().equals("")){
			mes = ficheroBean.getFecha().getMes();
			anio = ficheroBean.getFecha().getAnio();
			url +=anio+BARRA+mes+BARRA;
			if (ficheroBean.getSubCarpetaDia() != null && ficheroBean.getSubCarpetaDia() == true) {
				dia = ficheroBean.getFecha().getDia();
				url += dia+BARRA;
			}

		}			
		url = ipNodo+url;

		return url;
	}

	@Transactional(readOnly = true)
	public File guardaFicheroEnviandoStrings(FicheroBean fichero, List<String> lineas) {
		
		String ruta = "";
		File ficheroErrores=null;

		try {

			if(fichero.getTipoDocumento()!=null && !fichero.getTipoDocumento().equals("")){
//				ruta = getUrlDirectorio(fichero.getTipoDocumento());
				ruta = obtenerRutaAlmacenamiento(fichero.getTipoDocumento(), fichero.getFecha());
				ruta += fichero.getTipoDocumento() + BARRA;
			}

			if(fichero.getSubTipo()!=null && !fichero.getSubTipo().equals("")){
				ruta += fichero.getSubTipo() + BARRA;
			}

			if(fichero.getFecha()!=null){
				ruta+=fichero.getFecha().getAnio() + BARRA + fichero.getFecha().getMes() + BARRA;
			}
			else{
				Fecha fecha = utilesFecha.getFechaActual();
				ruta+=fecha.getAnio() + BARRA + fecha.getMes() + BARRA;
			}

			if (!"".equals(ruta) && null!=ruta && ruta.length()>0) {
				String path = ruta.substring(0, ruta.length()-1);
				File carpeta = new File(path);
				if (!carpeta.exists()) carpeta.mkdirs();
			}

			ruta += fichero.getNombreDocumento();
			ruta +=fichero.getExtension();

			ficheroErrores = new File(ruta);
			ficheroErrores.createNewFile();

			ficheroErrores.setReadable(true);
			ficheroErrores.setWritable(true);

			BufferedWriter output = new BufferedWriter(
					new FileWriter(ficheroErrores));

			for (int i=0;i<lineas.size();i++){
				if (i<(lineas.size()-1)) output.write(lineas.get(i)+"\n");
				else output.write(lineas.get(i));
			}
			output.close();
		} catch (IOException e) {
			log.error("Erro al guardar el fichero",e);
//		} catch (OegamExcepcion e) {
//			log.error("Erro al guardar el fichero",e);
		}
		return ficheroErrores;
	}

	
	@Transactional(readOnly = true)
	@Override
	public File guardaFicheroLineas(FicheroBean fichero, List<String> lineas) {
		
		String ruta = "";
		File ficheroErrores=null;

		try {

			if(fichero.getTipoDocumento()!=null && !fichero.getTipoDocumento().equals("")){
//				ruta = getUrlDirectorio(fichero.getTipoDocumento());
				ruta = obtenerRutaAlmacenamiento(fichero.getTipoDocumento(), fichero.getFecha());
				ruta += fichero.getTipoDocumento() + BARRA;
			}

			if(fichero.getSubTipo()!=null && !fichero.getSubTipo().equals("")){
				ruta += fichero.getSubTipo() + BARRA;
			}

			if(fichero.getFecha()!=null){
				ruta+=fichero.getFecha().getAnio() + BARRA + fichero.getFecha().getMes() + BARRA + fichero.getFecha().getDia()+ BARRA;
			}
			else{
				Fecha fecha = utilesFecha.getFechaActual();
				ruta+=fecha.getAnio() + BARRA + fecha.getMes() + BARRA;
			}

			if (!"".equals(ruta) && null!=ruta && ruta.length()>0) {
				String path = ruta.substring(0, ruta.length()-1);
				File carpeta = new File(path);
				if (!carpeta.exists()) carpeta.mkdirs();
			}

			ruta += fichero.getNombreDocumento();
			ruta +=fichero.getExtension();

			ficheroErrores = new File(ruta);
			ficheroErrores.createNewFile();

			ficheroErrores.setReadable(true);
			ficheroErrores.setWritable(true);

			BufferedWriter output = new BufferedWriter(
					new FileWriter(ficheroErrores));

			for (int i=0;i<lineas.size();i++){
				if (i<(lineas.size()-1)) output.write(lineas.get(i)+"\n");
				else output.write(lineas.get(i));
			}
			output.close();
		} catch (IOException e) {
			log.error("Erro al guardar el fichero",e);
//		} catch (OegamExcepcion e) {
//			log.error("Erro al guardar el fichero",e);
		}
		return ficheroErrores;
	}
	/**
	 * 
	 * @param jaxbContext
	 * @param objeto
	 * @param formateado
	 * @param fich
	 * @param encoding
	 * @throws JAXBException 
	 */
	private void grabarEnFicheroUTF8(String nameContext, Object objeto,boolean formateado,File fich,String encoding, String pathXsd) throws JAXBException, Throwable 
	{

		JAXBContext context;
		try {
			context = JAXBContext.newInstance(nameContext);

			Marshaller m;

			m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_ENCODING, encoding);
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, formateado);
			m.marshal(objeto, fich);
			validarXML(fich, null, pathXsd);
		} catch (JAXBException e) {
			fich.delete();
			log.error(e);			
			throw e;
		} 

	}

	/**
	 * 
	 * @param xml
	 * @param fich
	 * @throws OegamExcepcion 
	 * @throws Exception
	 */
	private  void grabarEnFicheroUTF8(String xml, File fich, String pathXsd) throws OegamExcepcion, Throwable {

		validarXML(null, xml, pathXsd);
		FileOutputStream foustr=null;
		try {
			foustr = new FileOutputStream(fich);
			foustr.write(xml.getBytes("UTF-8"));
			foustr.close(); 
		} catch (FileNotFoundException e) {
			throw new OegamExcepcion(ConstantesGestorFicheros.ERROR_CREACION_FICHERO_XML);
		} catch (UnsupportedEncodingException e) {
			throw new OegamExcepcion(ConstantesGestorFicheros.ERROR_CREACION_FICHERO_XML);
		} catch (IOException e) {
			throw new OegamExcepcion(ConstantesGestorFicheros.ERROR_CREACION_FICHERO_XML);
		} 
	}


	/**
	 * Si recibe un fichero xml o una cadena xml y una ruta válida a un esquema, intenta validar
	 * Lanzará excepción si no valida, en otro caso no hará nada
	 * @param fichero
	 * @param cadenaXml
	 * @param rutaEsquema
	 * @throws Throwable
	 */
	private void validarXML(File fichero, String cadenaXml, String rutaEsquema) throws Throwable {

		if(rutaEsquema == null){
			return;
		}
		URL url = GuardarDocumentoServiceImpl.class.getResource(rutaEsquema);
		Schema schema = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema").newSchema(new StreamSource(url.getFile()));
		Validator validator = schema.newValidator();
		if(fichero != null){
			validator.validate(new StreamSource(fichero));
		}else if(cadenaXml != null){
			validator.validate(new StreamSource(cadenaXml));
		}else{
			return;
		}
	}

	@Override
	public byte[] transformFiletoByte(File file) throws IOException {
		byte[] resultado = null;
		FileInputStream docu = null;
		try {
			resultado = new byte[(int)file.length()];
			
			docu = new FileInputStream(file);
			docu.read(resultado);
		} finally {
			if (docu != null) {
				docu.close();
			}
		}
		return resultado;
	}

	public List <String> listarFicherosLogs(String url, String nombreLog, FechaFraccionada fecha) throws OegamExcepcion {                              

        File dir = new File(url);
        File [] files  = dir.listFiles();
       
        List <String> listaNombresLogs = new ArrayList<String>();
        String coincidencia = null;
        
        if (files != null) {
        	
        Arrays.sort(files, new Comparator<Object>(){
            public int compare(Object o1, Object o2) {
            return compare( (File)o1, (File)o2);
           }
           private int compare(File f1, File f2){
               long result = f2.lastModified() - f1.lastModified();
               if( result > 0 ){
                   return 1;
               } else if( result < 0 ){
                   return -1;
               } else {
                   return 0;
               }
           }
       });
       
       Boolean control = false;
       
       while (!control) {
        
         for (int i = 0; i < files.length; i++) {
            
		     long microSeg = files[i].lastModified();
		     Date date = new Date(microSeg);
		     
		     Fecha fecha1 = new Fecha(fecha.getFechaInicio());
		     Fecha fecha2 = new Fecha(date);
		    
		     int ultimaPosicion = files.length - 1;
	    
	     try {
	    	 
	    	 if (files[i] == files[ultimaPosicion] 
		           && utilesFecha.compararFechaMayor(fecha1, fecha2) == 2) {
		           control = true;
		     }
		    
		     else if (nombreLog != null && !nombreLog.isEmpty() && files[i].toString().contains(nombreLog) 
		           && utilesFecha.compararFechaMayor(fecha1, fecha2) == 0) {
		           listaNombresLogs.add(files[i].toString());
		           control = true;
		     } 
		            
		     else if (nombreLog != null && !nombreLog.isEmpty() && files[i].toString().contains(nombreLog) 
		    	   && utilesFecha.compararFechaMayor(fecha1, fecha2) != 0 
				   && fecha1.getAnio().equals(fecha2.getAnio()) && utilesFecha.compararFechaMayor(fecha1, fecha2) == 2) {
		           coincidencia = files[i].toString();
		           control = true;
		     }
		            
		  } catch (ParseException e) {
		       log.error("Error recuperando nombre de fichero de logs", e);
		  }
	   }
	      if (coincidencia != null && listaNombresLogs.isEmpty()) {
			    listaNombresLogs.add(coincidencia);
	      }
	      
		    control = true;
       }
    }	       
	     if (listaNombresLogs.isEmpty()) {
		    	listaNombresLogs=null;
		 }
       
	return listaNombresLogs;                                       

	}

	/**
	 * @see GuardarDocumentosInterfaz#getFicherosLogs(List)
	 */
	public InputStream getFicherosLogs(List<String> listadoNombreLogs) {
		InputStream inputStream = null;

		if (listadoNombreLogs.size() > 0) {
			// zip

			ByteArrayOutputStream baos = null;
			ZipOutputStream zos = null;
			try {
				baos = new ByteArrayOutputStream();
				zos = new ZipOutputStream(baos);
				byte[] buffer = new byte[128];

				for (String s : listadoNombreLogs) {
					try {
						File fileZip = new File(s);
						if (!fileZip.isDirectory() && fileZip.exists()) {
							ZipEntry entry = new ZipEntry(fileZip.getName());
							FileInputStream fis = new FileInputStream(fileZip);
							zos.putNextEntry(entry);
							int read = 0;
							while ((read = fis.read(buffer)) != -1) {
								zos.write(buffer, 0, read);
							}
							zos.closeEntry();
							fis.close();
						}
					} catch (FileNotFoundException e) {
						log.error("Fichero de log no encontrado (" + s +")", e);
					}
				}
			} catch (IOException ioe) {
				log.error("Error zipeando los logs para su descarga", ioe);
			} finally {
				if (zos != null) {
					try {
						zos.close();
					} catch (IOException e) {
						log.error("Error cerrando ZipOutputStream", e);
					}
				}
				if (baos != null) {
					try {
						baos.close();
					} catch (IOException e) {
						log.error("Error cerrando ByteArrayOutputStream", e);
					}
				}
			}
			if (baos != null) {
				inputStream = new ByteArrayInputStream(baos.toByteArray());
			}
		}

		return inputStream;
	}

	public InputStream extraerFicheroZip(File zip, String nombreFicheroBuscar) throws IOException, OegamExcepcion {
		InputStream inputStream = null;
		InputStream fis = null;
		ZipInputStream zis = null;
		try {
			fis = new FileInputStream(zip);
			zis = new ZipInputStream(fis);
			ZipEntry entrada = null;
			
			while (null != (entrada=zis.getNextEntry())) {
				if (entrada.getName().startsWith(nombreFicheroBuscar)) {
					 //File someFile = new File(entrada.getName());
					 ByteArrayOutputStream baos = new ByteArrayOutputStream();
					   int leido;
					   while (0<(leido=zis.read())) {
						   baos.write(leido);
					   }
					   baos.flush();
					   baos.close();
					   zis.closeEntry();
					   inputStream = new ByteArrayInputStream(baos.toByteArray());
					   return inputStream;
				}
			}
		} finally {
			if (zis != null) {
				zis.close();
			}
			if (fis != null) {
				fis.close();
			}
		}
		return inputStream;
	}
	
	public byte[] extraerFicheroZipByte(File zip, String nombreFicheroBuscar) throws IOException, OegamExcepcion {
		@SuppressWarnings("resource")
		ZipInputStream zis = new ZipInputStream(new FileInputStream(zip));
		ZipEntry entrada = null;

		while (null != (entrada = zis.getNextEntry())) {
			if (entrada.getName().startsWith(nombreFicheroBuscar)) {
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				int leido;
				while (0 < (leido = zis.read())) {
					baos.write(leido);
				}
				baos.flush();
				baos.close();
				zis.closeEntry();
				return baos.toByteArray();
			}
		}
		return null;
	}

	public File extraerFicheroZipFile(File zip, String nombreFicheroBuscar) throws IOException, OegamExcepcion {
		@SuppressWarnings("resource")
		ZipInputStream zis = new ZipInputStream(new FileInputStream(zip));
		ZipEntry entrada = null;

		while (null != (entrada = zis.getNextEntry())) {
			if (entrada.getName().startsWith(nombreFicheroBuscar)) {

				File nuevoArchivo = new File("/var/OEGAM_FILES/RECIBIDOS_TEMPORAL/" + entrada.getName());
				FileOutputStream salida = new FileOutputStream(nuevoArchivo);

				byte[] buffer = new byte[1024];
				int leido;

				while ((leido = zis.read(buffer, 0, buffer.length)) != -1) {
					salida.write(buffer, 0, leido);
				}

				salida.flush();
				salida.close();

				zis.closeEntry();
				return nuevoArchivo;
			}
		}
		return null;
	}
	
	@Transactional(readOnly = true)
	public String crearRuta(String tipo, String subTipo, Fecha fecha) {
		String mes, anio, url = "";
		String ipNodo = obtenerRutaAlmacenamiento(tipo, fecha);
		url = ipNodo;
		if (tipo != null && !tipo.equals("")) {
			url += tipo + BARRA;
			if (subTipo != null && !subTipo.equals("")) {
				url += subTipo + BARRA;
			}
			if (fecha != null) {
				mes = fecha.getMes();
				anio = fecha.getAnio();
				url += anio + BARRA + mes + BARRA;
			}
		}
		return url;
	}
}
