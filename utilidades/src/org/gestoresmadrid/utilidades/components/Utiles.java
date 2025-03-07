package org.gestoresmadrid.utilidades.components;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URL;
import java.security.MessageDigest;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import sun.misc.BASE64Encoder;
import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.mensajes.Claves;

@Component
public class Utiles implements Serializable{

	private static final long serialVersionUID = 3885178121952627911L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(Utiles.class);

	// Constantes para analizar una matrícula
	/* Tipos de matrícula */
	public static final String TIPO_MATRICULA_ORDINARIA		= "ordinaria";
	public static final String TIPO_MATRICULA_TRACTOR		= "tractor";
	public static final String TIPO_MATRICULA_CICLOMOTOR	= "ciclomotor";
	/* Enteros */
	public static final Integer CERO =		0;
	public static final Integer UNO =		1;
	public static final Integer DOS =		2;
	public static final Integer TRES =		3;
	public static final Integer CUATRO =	4;
	public static final Integer CINCO =		5;
	public static final Integer SEIS =		6;
	public static final Integer SIETE =		7;
	public static final Integer OCHO =		8;
	public static final Integer NUEVE =		9;
	public static final Integer DIEZ =		10;

	private static final SimpleDateFormat SDF_FECHA_FICHERO = new SimpleDateFormat("ddMMyyyyHHmmss");
	private static final char[] ILLEGAL_CHARACTERS = { '/', '\n', '\r', '\t', '\0', '\f', '`', '?', '*', '\\', '<', '>', '|', '\"', ':' };

	@Autowired
	GestorPropiedades gestorPropiedades;

	private static Utiles utiles;

	public static Utiles getInstance() {
		if (utiles == null) {
			utiles = new Utiles();
			SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(utiles);
		}
		return utiles;
	}

	public String analizarMatriculaVehiculo(String matricula) {
		String trozo1;
		String trozo2;
		String trozo3;
		String tipoMatricula = TIPO_MATRICULA_ORDINARIA;

		// Análisis matrícula ordinaria
		if (matricula.length() == SIETE.intValue()) {
			trozo1 = matricula.substring(0, 4);
			trozo2 = matricula.substring(4, 7);

			if (comprobarNumero(trozo1) && !comprobarNumero(trozo2)) {
				return tipoMatricula;
			}
		}

		if (matricula.length() == OCHO.intValue()) {
			// Análisis tipo matrícula antigua

			// Análisis matrícula tractor y agrícolas
			trozo1 = matricula.substring(0,1);
			trozo2 = matricula.substring(1,5);
			trozo3 = matricula.substring(5,8);

			if (!comprobarNumero(trozo1) && trozo1.equals("E") && comprobarNumero(trozo2) && !comprobarNumero(trozo3)) {
				tipoMatricula = TIPO_MATRICULA_TRACTOR;
				return tipoMatricula;
			}

			// Análisis matrícula ciclomotores
			trozo1 = matricula.substring(0,2);
			trozo2 = matricula.substring(2,5);
			trozo3 = matricula.substring(5,8);

			if (!comprobarNumero(trozo1) && trozo1.substring(0, 1).equals("C") && comprobarNumero(trozo2)
					&& !comprobarNumero(trozo3)) {
				tipoMatricula = TIPO_MATRICULA_CICLOMOTOR;
				return tipoMatricula;
			}
		}
		return tipoMatricula;
	}

	public Long[] convertirStringArrayToLongArray(String[] elementos) {
		if (elementos == null) {
			return null;
		}
		List<Long> convertidos = new ArrayList<>();
		for (int i = 0; i < elementos.length; i++) {
			Long l = stringToLong(elementos[i]);
			if (l != null) {
				convertidos.add(l);
			}
		}
		Long[] result = new Long[convertidos.size()];
		return convertidos.toArray(result);
	}

	public Long stringToLong(String elemento) {
		try {
			return new Long(elemento);
		} catch (NumberFormatException e) {}
		return null;
	}

	public Fecha transformExpedienteFecha(String numExpediente) {
		Fecha fecha = null;
		String dia;
		String mes;
		String anio;

		while (numExpediente.length() < 15) {
			numExpediente = "0" + numExpediente;
		}
		dia = numExpediente.substring(4,6);
		mes = numExpediente.toString().substring(6, 8);
		anio = "20"+numExpediente.toString().substring(8, 10);
		StringBuffer fechaBarras= new StringBuffer();
		fechaBarras.append(dia);
		fechaBarras.append("/");
		fechaBarras.append(mes);
		fechaBarras.append("/");
		fechaBarras.append(anio);

		fecha = new Fecha(fechaBarras.toString());
		return fecha;
	}

	public Fecha transformExpedienteInterga(String numExpediente) {
		Fecha fecha = null;
		String dia;
		String mes;
		String anio;

		while (numExpediente.length() < 13) {
			numExpediente="0"+numExpediente;
		}
		dia = numExpediente.substring(4,6);
		mes = numExpediente.toString().substring(6, 8);
		anio = "20"+numExpediente.toString().substring(8, 10);
		StringBuffer fechaBarras= new StringBuffer();
		fechaBarras.append(dia);
		fechaBarras.append("/");
		fechaBarras.append(mes);
		fechaBarras.append("/");
		fechaBarras.append(anio);

		fecha = new Fecha(fechaBarras.toString());
		return fecha;
	}

	public String convertirCombo(String elemento) {
		if ("".equals(elemento) || "-1".equals(elemento) || "-".equals(elemento)) {
			return null;
		}
		return elemento;
	}

	public String dameExtension(String nombreFichero, Boolean conPunto) {
		String minusculas = nombreFichero.toLowerCase();
		String extension = null;
		int indiceExtension = minusculas.lastIndexOf(".");
		if (indiceExtension == -1) {
			return null;
		}
		extension = minusculas.substring(conPunto ? indiceExtension : indiceExtension + 1, nombreFichero.length());

		return extension;
	}

	//Métodos movidos de UtilesFicheros
	/**
	 * Obtiene las líneas de texto del fichero que recibe
	 * 
	 * @param ficheroTexto Fichero del que se quieren leer sus líneas
	 * @return Devuelve una lista con las líneas del fichero
	 * @throws Throwable
	 */
	public List<String> obtenerLineasFicheroTexto(File ficheroTexto) throws Throwable {
		List<String> list = new ArrayList<String>();
		//BufferedReader input = new BufferedReader(new FileReader(ficheroTexto));
		//Especificamos la codificación con la que queremos que se lea el fichero
		BufferedReader input = null;
		try {
			input = new BufferedReader(
					new InputStreamReader(
						new FileInputStream(ficheroTexto), Claves.ENCODING_ISO88591)
					);

			String line = null;
			//log.trace("Leyendo lineas del fichero");
			while (( line = input.readLine()) != null){
				//jesus.campos: control de cross-site scripting
	//			if(FiltroJavascript.findScriptCodeWhitelistPlus(line)){
	//				throw new Throwable("Contenido de caracteres no válidos");
	//			}
				list.add(line);
			}
			return list;
		} finally {
			if (input != null) {
				input.close();
			}
		}
	}

	/**
	 * Escribe las líneas de texto que recibe en una lista en el fichero que también recibe
	 * 
	 * @param fichero
	 * @param list
	 * @return Devuelve el mismo fichero pero modificado
	 * @throws Throwable
	 */
	public File escribirLineasFicheroTexto(File fichero, List<String> list) throws Throwable {
		String line = null;
		BufferedWriter output = null;

		try {
			output = new BufferedWriter(new FileWriter(fichero));
			Iterator<String> it = list.iterator();

			//log.trace("Escribiendo lineas en el fichero");
			while(it.hasNext()){
				line = (String) it.next();
				output.write(line);
				output.newLine();
			}
		} catch (Throwable e) {
			//log.error(e);
			throw e;
		} finally {
			if (null != output) {
				output.close();
			}
		}
		return fichero;
	}

	/**
	 * Método que obtiene el nombre que se le va a poner al fichero a exportar a partir de la fecha actual y
	 * del tipo de fichero que se está exportando.
	 * 
	 * @param tipoFichero Tipo de fichero que se quiere exportar
	 * @param extensionFichero Extensión del fichero a exportar
	 * @return Devuelve el nombre del fichero, incluida extensión
	 */
	public String obtenerNombreFichero(String tipoFichero, String extensionFichero) {
		StringBuffer nombre = new StringBuffer();

		Date fechaActual = new Date();

		try {
			nombre.append(SDF_FECHA_FICHERO.format(fechaActual));
		} catch (Throwable e) {
			log.error(e);
		}

		nombre.append("-");
		nombre.append(tipoFichero);
		nombre.append(".");
		nombre.append(extensionFichero);

		return nombre.toString();
	}

	public String getSubcadena(String cadena, int inicio, int fin) {
		try {
			return cadena.substring(inicio,fin);
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * Crea un fichero físico con las lineas que se le pasan como parámetro
	 * @return nombre de fichero físico creado
	 */
	public String crearFicheroFisicoConStrings(String nombreFichero, String extension, String ruta,
			List<String> lineas) {

		String nombre = "";
		if (!"".equals(extension) && null != extension) {
			nombre = nombreFichero + "." + extension;
		} else
			nombre = nombreFichero;
		if (!"".equals(ruta) && null != ruta) {
			nombre = ruta + nombre;
		}

		try {
			if (!"".equals(ruta) && null != ruta && ruta.length() > 0) {
				String path = ruta.substring(0, ruta.length() - 1);
				File carpeta = new File(path);
				if (!carpeta.exists())
					carpeta.mkdirs();
			}

			File ficheroErrores = new File(nombre);
			ficheroErrores.createNewFile();

			ficheroErrores.setReadable(true);
			ficheroErrores.setWritable(true);

			BufferedWriter output = new BufferedWriter(new FileWriter(ficheroErrores));

			for (int i = 0; i < lineas.size(); i++) {
				if (i < (lineas.size() - 1))
					output.write(lineas.get(i) + "\n");
				else
					output.write(lineas.get(i));
			}
			output.close();
		} catch (IOException e) {
			log.error(e);
		}
		return nombre;
	}

	/*
	 * Recupera la extensión del nombre de fichero que recibe como parámetro.
	 * El segundo parámetro es un boolean que indica si la extensión será
	 * devuelta con punto o sin punto.
	 */
	public String dameExtension(String nombreFichero, boolean conPunto) {
		String minusculas = nombreFichero.toLowerCase();
		String extension = null;
		int indiceExtension = minusculas.lastIndexOf(".");
		if (indiceExtension == -1) {
			return null;
		}
		if (conPunto) {
			extension = minusculas.substring(indiceExtension, nombreFichero.length());
		} else {
			extension = minusculas.substring(indiceExtension + 1 , nombreFichero.length());
		}
		return extension;
	}

	public void creaCarpetaSiNoExiste(String ruta){
		File carpeta = new File(ruta.substring(0, ruta.length()-1));
		if (!carpeta.exists()) carpeta.mkdirs();
	}

	public void creaFicheroConBytes(String ruta,byte[] ptcBytes ) throws IOException{
		FileOutputStream ficheroSalida = new FileOutputStream(ruta ,true);
		ficheroSalida.write(ptcBytes);
		ficheroSalida.flush();
		ficheroSalida.close();
	}

	// Returns the contents of the file in a byte array.
	public byte[] getBytesFromFile(File file) throws IOException {
		InputStream is = null;
		try {
			is = new FileInputStream(file);

			// Get the size of the file
			long length = file.length();

			// You cannot create an array using a long type.
			// It needs to be an int type.
			// Before converting to an int type, check
			// to ensure that file is not larger than Integer.MAX_VALUE.
			if (length > Integer.MAX_VALUE) {
				// File is too large
			}

			// Create the byte array to hold the data
			byte[] bytes = new byte[(int) length];

			// Read in the bytes
			int offset = 0;
			int numRead = 0;
			while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
				offset += numRead;
			}

			// Ensure all the bytes have been read in
			if (offset < bytes.length) {
				throw new IOException("Could not completely read file " + file.getName());
			}

			// Close the input stream and return bytes
			return bytes;
		} finally {
			if (is != null) {
				is.close();
			}
		}
	}

	/**
	 * Devuelve el string que representa el contenido del archivo que está en la ruta pasada como parámetro
	 * @param ruta
	 * @return
	 */
	public String getStringFromFileRoute(String ruta) {
		try {
			StringBuffer fileData = new StringBuffer(1000);
			BufferedReader reader = new BufferedReader(new FileReader(ruta));
			char[] buf = new char[1024];
			int numRead = 0;
			while ((numRead = reader.read(buf)) != -1) {
				String readData = String.valueOf(buf, 0, numRead);
				fileData.append(readData);
				buf = new char[1024];
			}
			reader.close();
			return fileData.toString();
		} catch (Exception e) {
			log.error(e);
			return "";
		}
	}

	public void borraFicheroSiExiste(String rutaFichero){
		File fichero = new File(rutaFichero);
		if (fichero.exists())
			fichero.delete();
	}

	public File empaquetarEnZip(String nombreDelZip, ArrayList<File> ficheros) throws IOException {
		if (ficheros.size() <= 1) {
			return null;
		}
		File destino = null;
		if (!nombreDelZip.contains(".zip")) {
			destino = new File(nombreDelZip + ".zip");
		} else {
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
		}
		zip.close();
		return destino;
	}

	/**
	 * Sobrescrito que devuelve el nombre del fichero creado
	 * @param nombreDelZip
	 * @param ficheros
	 * @param sobrescrito
	 * @return
	 * @throws IOException
	 */
	public String empaquetarEnZip(String nombreDelZip, ArrayList<File> ficheros, Boolean sobrescrito, int tamanyoMinimo) throws IOException{
		if (ficheros.size() <= tamanyoMinimo) {
			return null;
		}
		File destino = null;
		if (!nombreDelZip.contains(".zip")) {
			destino = new File(nombreDelZip + ".zip");
		} else {
			destino = new File(nombreDelZip);
		}
		FileOutputStream out = new FileOutputStream(destino);
		ZipOutputStream zip = new ZipOutputStream(out);
		for (File file : ficheros) {
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
		}
		zip.close();
		return destino.getAbsolutePath();
	}

	public String crearFicheroFisicoConBytes(String nombreFichero, String extension,
			String ruta, byte[] bytes) throws Exception {

		String nombre = "";
		if (!"".equals(extension) && null != extension) {
			nombre = nombreFichero+"."+extension;
		} else
			nombre = nombreFichero;
		if (!"".equals(ruta) && null != ruta) {
			nombre = ruta+nombre;
		}

		if (!"".equals(ruta) && null != ruta && ruta.length() > 0) {
			String path = ruta.substring(0, ruta.length()-1);
			File carpeta = new File(path);
			if (!carpeta.exists()) carpeta.mkdirs();
		}

		FileOutputStream ficheroSalida = new FileOutputStream(nombre, true);
		ficheroSalida.write(bytes);
		ficheroSalida.flush();
		ficheroSalida.close();

		return nombre;
	}

	public String componerNombreFichero(String nombre, Date fecha) {
		String nombreFichero = emptyString(nombre) + "_" + new Timestamp((fecha).getTime()).toString();
		nombreFichero = nombreFichero.replace(':', '_');
		nombreFichero = nombreFichero.replace('-', '_');
		nombreFichero = nombreFichero.replace(' ', '_');
		nombreFichero = nombreFichero.replace('.', '_');
		return nombreFichero;
	}

	public String emptyString(String cadena) {
		return (cadena == null) ? "" : cadena;
	}

	//Métodos movidos de utilidades.Utiles
	/**
	 * Crea una copia de cadena y con el tamaño especificado; si la cadena es mayor que el tamaño especificado, devuelve los <code>size</code> primeros caracteres; en caso contrario, completa con el caracter especificado al final de la cadena hasta llegar al tamaño especificado.
	 * @param cadena la cadena original
	 * @param size el tamaño en caracteres de la copia
	 * @param relleno el caracter de relleno para completar el tamaño
	 * @param detras si hay que completar con el caracter relleno detrás (true) o delante (false) de la cadena original
	 * @return la copia con el nuevo tamaño
	 */
	public String changeSize(String cadena, int size, char relleno, boolean detras) {
		if (cadena == null || "".equals(cadena) || cadena.length() == size)
			return cadena;

		String res = null;

		int tam = cadena.length();

		if (size > tam) {
			int dif = size - tam;
			char[] caracterRelleno = new char[dif];
			for (int i = 0; i < dif; i++) {
				caracterRelleno[i] = relleno;
			}

			StringBuffer buffer = new StringBuffer(size);
			if (detras)
				buffer.append(cadena).append(caracterRelleno);
			else
				buffer.append(caracterRelleno).append(cadena);
			return buffer.toString();
		} else {
			res = cadena.substring(0, size);
		}

		return res;
	}

	public BigDecimal convertirIntegerABigDecimal(Integer valor) {
		if (valor == null)
			return null;
		else
			return new BigDecimal(valor);
	}

	public BigDecimal convertirFloatABigDecimal(Float valor) {
		return valor == null ? null : new BigDecimal(valor);
	}

	public BigDecimal convertirLongABigDecimal(Long valor) {
		return valor == null ? null : new BigDecimal(valor);
	}

	public Integer convertirBigDecimalAInteger(BigDecimal valor) {
		return valor == null ? null : valor.intValue();
	}

	public Float convertirBigDecimalAFloat(BigDecimal valor) {
		return valor == null ? null : valor.floatValue();
	}

	public Long convertirBigDecimalALong(BigDecimal valor) {
		return valor == null ? null : valor.longValue();
	}

	public String formatoFloat(String formato, Float valor) {
		if (valor == null)
			return null;
		DecimalFormat df = new DecimalFormat(formato);
		return df.format(valor);
	}

	public String formatoFloat(Float valor) {
		return formatoFloat(valor >= 1000 ? "0,000.00" : "0.00", valor);
	}

	public Boolean convertirIntegerABoolean(Integer valor) {
		if (valor == null)
			return null;
		return valor == 1;
	}

	public Integer convertirBooleanAInteger(Boolean valor) {
		if (valor == null)
			return null;
		return valor ? new Integer(1) : new Integer(0);
	}

	/*
	 * Devuelve un mapa con día, mes y año de una cadena. No comprueba con lo que el parámetro debe contener una fecha válida en formato: dd/mm/yyyy
	 */
	public HashMap<String, String> formatoFecha(String cadFecha) {
		HashMap<String, String> respuestas = new HashMap<>();
		String[] trozos = cadFecha.split("/");
		respuestas.put("dia", trozos[0]);
		respuestas.put("mes", trozos[1]);
		respuestas.put("anio", trozos[2]);
		return respuestas;
	}

	/**
	 * Cambia el formato de las matrículas antiguas para la generación de la nube de puntos, aquellas que comenzaban por el identificador de la provincia y contiene un solo carácter (p.e. M1234ZZ), a las que se le añade un espacio entra la provincia y los números (p.e. M 1234ZZ). Si acaba también en
	 * un solo carácter, se le añade un espacio en blanco antes (p.e. M1234Z --> M 1234 Z)
	 * @param matricula
	 * @return
	 */
	public String cambiaFormatoMatricula(String matricula) {
		String matriculaFormateada = matricula;
		int longMatricula = matricula.length();

		if (longMatricula >= 3) {
			// Si la matrícula es de un ciclomotor o especial (p.e. C1234BBB) no debe añadir el espacio después
			// de la primera letra, por ello se comprueba que el antepenúltimo carácter no sea un letra
			if (Character.isDigit(matricula.charAt(longMatricula - 3))) {
				// Si el primer carácter es una letra y el segundo un número, se trata de una matrícula a modificar
				if (Character.isLetter(matricula.charAt(0)) && Character.isDigit(matricula.charAt(1))) {
					String provincia = matricula.substring(0, 1);
					matriculaFormateada = matricula.replaceFirst(provincia, provincia + " ");
				}
				// Si es matrícula antigua y acaba en una única letra se añade un espacio en blanco
				if (matriculaFormateada.length() == 7 && Character.isDigit(matriculaFormateada.charAt(5)) && Character.isLetter(matriculaFormateada.charAt(6))) {
					matriculaFormateada = matriculaFormateada.substring(0, 6) + " " + matriculaFormateada.substring(6);
				}

				// Si es una matrícula con 6 dígitos y se le ha metido un cero, se lo quitamos...
				String cifras = matriculaFormateada.substring(2); // Le quitamos los dos primeros caracteres de la provincia
				if (cifras.length() == 7 && Character.isDigit(cifras.charAt(6)) && cifras.charAt(0) == '0') {
					matriculaFormateada = matriculaFormateada.replaceFirst("0", "");
				}
			}
		}
		return matriculaFormateada;
	}

	@SuppressWarnings("restriction")
	public String cifrarHMACSHA1(String cadena, String pass) {

		String sNameHashAlgorithm = "HmacSHA1";
		String sLastHMAC = "";

		byte[] cadenaACifrar = cadena.getBytes();
		// Para triple des la clave debe ser de 24 bytes
		byte[] abKeyBytes = pass.getBytes();
		// Inicializas la clave con la tuya
		SecretKeySpec signingKey = new SecretKeySpec(abKeyBytes, sNameHashAlgorithm);

		// Obtenemos la instancia de la Mac para construir la HMAC del algoritmo de hash indicado.
		try {
			Mac objMac = Mac.getInstance(sNameHashAlgorithm);
			objMac.init(signingKey);
			// abAudit son los datos
			byte[] abHMAC = objMac.doFinal(cadenaACifrar);
			BASE64Encoder encoder = new BASE64Encoder();
			sLastHMAC = encoder.encode(abHMAC);
		} catch (Throwable e) {
			log.error("Excepción al calcular la firma: " + e);
		}
		return sLastHMAC;
	}

	public String stringToHex(String base) {
		StringBuffer buffer = new StringBuffer();
		int intValue;
		for (int x = 0; x < base.length(); x++) {
			int cursor = 0;
			intValue = base.charAt(x);
			String binaryChar = new String(Integer.toBinaryString(base.charAt(x)));
			for (int i = 0; i < binaryChar.length(); i++) {
				if (binaryChar.charAt(i) == '1') {
					cursor += 1;
				}
			}
			if ((cursor % 2) > 0) {
				intValue += 128;
			}
			buffer.append(Integer.toHexString(intValue));
		}
		return buffer.toString();
	}

	public String cambiarFormatoCO2(String numeroCadena) {
		return cambiarFormatoCO2(numeroCadena, false);
	}

	// TODO: Estandarizar esta función para que cualquiera pueda cambiar de formato (no solo a 000.000)
	/**
	 * @author Carlos García Fecha: 08/06/2012 Método que recoge un número en tipo cadena, lo pasa a float, lo adapta al formato 000.000, sustituye el "," por "." y si es para la nube de puntos los "." por "". Devuelve una cadena con 6 posiciones, teniendo en las 3 primeras la parte entera y las dos
	 *         últimas la parte decimal.
	 * @param numeroCadena, nubePuntos
	 * @return String
	 */
	public String cambiarFormatoCO2(String numeroCadena, boolean nubePuntos) throws NumberFormatException {
		String cadena = "";
		if (null == numeroCadena || numeroCadena.equals("")) {
			return null;
		}

		if (nubePuntos) {
			DecimalFormat df = new DecimalFormat("000.000");
			cadena = df.format(Float.parseFloat(numeroCadena));
			cadena = cadena.replaceAll(",", "");
			cadena = cadena.replaceAll("\\.", "");
		} else {
			DecimalFormat df = new DecimalFormat("0.000");
			cadena = df.format(Float.parseFloat(numeroCadena));
			cadena = cadena.replaceAll(",", ".");
		}

		return cadena;
	}

	// TODO: Estandarizar esta función para que cualquiera pueda cambiar de formato (no solo a 000.000)
	/**
	 * @author Fecha: Método que recoge un numero en tipo cadena si tiene decimales pues devuelve tal cual con las dos posiciones decimamles y si no tiene decimales le añade .00 para que se envíen decimales. Devuelve una cadena con 8 posiciones, teniendo en las 5 primeras la parte entera y las dos
	 *         últimas la parte decimal.
	 * @param numeroCadena
	 * @return String
	 */
	public String cambiarFormatoCilindrada(String numeroCadena) throws NumberFormatException {

		String cadena = "";

		if ("0".equals(numeroCadena))
			return "0.00";
		numeroCadena = numeroCadena.trim();
		try {
			if (numeroCadena.contains(".")) {
				cadena = numeroCadena;
			} else {
				cadena = numeroCadena.substring(0, numeroCadena.length()) + ".00";
			}
		} catch (Exception e) {
			log.error("Error en Utiles.cambiarFormatoCilindrada. " + e);
			return "";
		}

		return cadena;
	}

	/**
	 * Cambia un número Float a un String con el formato 0000000
	 * @param numero
	 * @return
	 */
	public String cambiarDecimalFloatFormatoMancha(Float numero) {
		DecimalFormat df = new DecimalFormat("00000.00");

		if (null == numero) {
			return null;
		}

		return df.format(numero).replaceAll(",", "");
	}

	/**
	 * Un número que representa un número entero, te lo devuelve en un string con decimales a 0 y ceros a la izquierda
	 */
	public String StringEnteroToStringDecimal(String valor, int longitud) {
		return rellenarCeros(valor + "00", longitud);
	}

	/**
	 * Devuelve un String de la longitud que le indiquemos con con el formato indicado, si error devuelve null.
	 */
	public String BigDecimalToStringDosDecimales(BigDecimal numero, Integer longitud) {
		try {
			String numeroString = "";
			String parteEntera = "";
			String parteDecimal = "";

			String paso1 = "";
			if (null != numero) {
				paso1 = numero.toString().replace(".", ",");
			} else {
				return null;
			}
			String[] partes = paso1.split(",");
			String baseEntera = "00000000000000000000000000000000000000000000";

			parteEntera = partes[0];
			parteDecimal = partes.length == 2 ? (partes[1].length() == 2 ? partes[1] : partes[1] + "0") : "00";

			parteDecimal = parteDecimal.length() > 2 ? parteDecimal.substring(0, 2) : parteDecimal;

			if (parteEntera.length() > longitud - 2) {
				return null;
			}

			if (parteEntera.length() < longitud - 2) {
				numeroString = baseEntera.substring(0, longitud - (parteEntera.length() + 2)).concat(parteEntera);
			} else {
				numeroString = parteEntera;
			}

			if (null != parteDecimal && parteDecimal.length() < 2) {
				parteDecimal += "0";
			}

			numeroString += parteDecimal;

			return longitud == numeroString.length() ? numeroString : null;
		} catch (Exception e) {
			log.error("Error en Utiles.BigDecimalToStringDosDecimales. " + e);
			return "";
		}
	}

	/**
	 * Devuelve un String de la longitud que le indiquemos con con el formato indicado, si error devuelve null.
	 */
	public String BigDecimalToStringTresDecimales(BigDecimal numero, Integer longitud) {
		try {
			String numeroString = "";
			String parteEntera = "";
			String parteDecimal = "";

			String paso1 = "";
			if (null != numero) {
				paso1 = numero.toString().replace(".", ",");
			} else {
				return null;
			}
			String[] partes = paso1.split(",");
			String baseEntera = "00000000000000000000000000000000000000000000";

			parteEntera = partes[0];
			parteDecimal = partes.length == 2 ? (partes[1].length() == 3 ? partes[1] : partes[1] + "0") : "000";

			parteDecimal = parteDecimal.length() > 3 ? parteDecimal.substring(0, 3) : parteDecimal;

			if (parteEntera.length() > longitud - 3) {
				return null;
			}

			if (parteEntera.length() < longitud - 3) {
				numeroString = baseEntera.substring(0, longitud - (parteEntera.length() + 3)).concat(parteEntera);
			} else {
				numeroString = parteEntera;
			}

			if (null != parteDecimal && parteDecimal.length() < 3) {
				parteDecimal += "0";
			}

			numeroString += parteDecimal;

			return longitud == numeroString.length() ? numeroString : null;
		} catch (Exception e) {
			log.error("Error en Utiles.BigDecimalToStringDosDecimales. " + e);
			return "";
		}
	}

	/**
	 * Devuelve un String de la longitud que le indiquemos con el formato indicado, si error devuelve null. pone coma al anterior
	 */
	public String bigDecimalToStringDosDecimalesPuntosComas(BigDecimal numero, Integer longitud) {
		String numSinComa = quitarCerosIzquierda(BigDecimalToStringDosDecimales(numero, longitud));
		if (numSinComa.equals("0")) {
			numSinComa = numSinComa + "00";
		}

		String parteEntera = numSinComa.length() > 2 ? numSinComa.substring(0, numSinComa.length() - 2) : "0";
		String parteDecimal = numSinComa.length() > 2 ? numSinComa.substring(numSinComa.length() - 2) : "00";
		String parteEnteraPuntos = parteEntera;

		if (parteEntera.length() > 3) {
			parteEnteraPuntos = parteEntera.substring(0, parteEntera.length() - 3) + "." + parteEntera.substring(parteEntera.length() - 3);
			if (parteEnteraPuntos.length() > 7) {
				parteEnteraPuntos = parteEnteraPuntos.substring(0, parteEntera.length() - 7) + "." + parteEntera.substring(parteEntera.length() - 7);
				if (parteEnteraPuntos.length() > 11) {
					parteEnteraPuntos = parteEnteraPuntos.substring(0, parteEntera.length() - 11) + "." + parteEntera.substring(parteEntera.length() - 11);
					if (parteEnteraPuntos.length() > 15) {
						parteEnteraPuntos = parteEnteraPuntos.substring(0, parteEntera.length() - 15) + "." + parteEntera.substring(parteEntera.length() - 15);
					}
				}
			}
		}

		return parteEnteraPuntos + "," + parteDecimal;
	}

	/**
	 * Devuelve un BigDecimal con dos decimales a partir de un String plano Si hay algún error, devolverá null
	 * @param valor
	 * @return
	 */
	public BigDecimal stringToBigDecimalDosDecimales(String valor) {
		BigDecimal num = null;
		if (valor == null || valor.equals("")) {
			return null;
		}
		if ("0".equals(valor))
			return BigDecimal.ZERO;
		valor = valor.trim();
		try {
			if (valor.contains(",")) {
				valor = valor.replace(".", "");
				String[] separo = valor.split(",");
				num = new BigDecimal(separo[0] + "." + separo[1]);
			} else {
				valor = valor.substring(0, valor.length() - 2) + "." + valor.substring(valor.length() - 2);
				num = new BigDecimal(valor);
			}
		} catch (Exception e) {
			return null;
		}
		return num;
	}

	public BigDecimal stringToBigDecimal(String cadena) {
		if (cadena == null || cadena.trim().isEmpty()) {
			return BigDecimal.ZERO;
		}
		DecimalFormat formateador = new DecimalFormat("#,##0.00");
		try {
			return new BigDecimal(formateador.parse(cadena).toString());
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * Devuelve un BigDecimal con dos decimales a partir de un String plano Si hay algún error, devolverá null
	 * @param valor
	 * @return
	 */
	public BigDecimal stringToBigDecimalTresDecimales(String valor) {
		BigDecimal num = null;
		if (valor == null || valor.equals("")) {
			return null;
		}
		if ("0".equals(valor))
			return BigDecimal.ZERO;
		valor = valor.trim();
		try {
			if (valor.contains(",")) {
				valor = valor.replace(".", "");
				String[] separo = valor.split(",");
				num = new BigDecimal(separo[0] + "." + separo[1]);
			} else {
				valor = valor.substring(0, valor.length() - 3) + "." + valor.substring(valor.length() - 3);
				num = new BigDecimal(valor);
			}
		} catch (Exception e) {
			return null;
		}
		return num;
	}

	/**
	 * Devuelve un BigDecimal con 4 decimales a partir de un String plano Si hay algún error, devolverá null
	 * @param valor
	 * @return
	 */
	public BigDecimal stringToBigDecimalCuatroDecimales(String valor) {
		BigDecimal num = null;
		if (valor == null || valor.equals("")) {
			return null;
		}
		if ("0".equals(valor))
			return BigDecimal.ZERO;
		valor = valor.trim();
		try {
			if (valor.contains(",")) {
				valor = valor.replace(".", "");
				String[] separo = valor.split(",");
				num = new BigDecimal(separo[0] + "." + separo[1]);
			} else {
				valor = valor.substring(0, valor.length() - 4) + "." + valor.substring(valor.length() - 4);
				num = new BigDecimal(valor);
			}
		} catch (Exception e) {
			return null;
		}
		return num;
	}

	// public String convertirAdecimales(String num, int posDecimales, int tam){
	// if(num == null){
	// return "";
	// }
	// String cambiado = num;
	//
	// if(num.contains(".")){
	// return cambiado;
	// }else if(num.contains(",")){
	// return cambiado;
	// }else{
	// if(num.length() < tam){
	// for(int i=0; i < posDecimales; i++){
	// if(i==0) num = num+".0";
	// else num = num+0;
	// }
	// cambiado = num;
	// }
	// }
	//
	// return cambiado;
	// }

	/**
	 * Divide dos BigDecimals que le pasemos como parámetros divisor/dividendo
	 * @param valor
	 * @return
	 */
	public BigDecimal divisionBigDecimals(BigDecimal divisor, BigDecimal dividendo) {
		return dividendo.divide(divisor);
	}

	/**
	 * Elimina los ceros por la izquierda de un String que indica un numérico
	 * @param valor
	 * @return
	 */
	public String quitarCerosIzquierda(String valor) {
		if (valor == null || "".equals(valor)) {
			return "";
		}
		try {
			BigDecimal num = new BigDecimal(valor);
			return num.toString();
		} catch (Exception e) {
			log.error(e);
		}
		return "";
	}

	/**
	 * Devuelve un string con ceros a la izquierda hasta completar la longitud requerida
	 * @param valor
	 * @param longitud
	 * @return
	 */
	public String rellenarCeros(String valor, int longitud) {
		String aux = valor;
		if (null == valor) {
			aux = "";
		}
		for (int i = aux.length(); i < longitud; i++) {
			aux = "0" + aux;
		}
		return aux;
	}

	/**
	 * Quita duplicados de un array de Strings
	 * @param array
	 * @return
	 */
	public String[] quitarDuplicados(String[] array) {
		if (array != null) {
			String[] aux = new String[array.length];
			int i = 0;
			int cont = 0;
			for (String actual : array) {
				if (actual != null) {
					aux[cont] = actual;
					cont++;
				}

				for (int j = i + 1; j < array.length; j++) {
					if (actual != null && actual.equals(array[j]))
						array[j] = null;
				}
				i++;
			}

			String[] noDups = new String[cont];

			if (cont < array.length) {
				for (int j = 0; j < cont; j++) {
					noDups[j] = aux[j];
				}
			} else {
				noDups = aux;
			}

			return noDups;
		}
		return array;
	}

	/**
	 * Devuelve el número recibido como parámetro con un número determinado de decimales
	 * @param numeroDecimales
	 * @param numero
	 * @return el número
	 */
	public BigDecimal getDecimales(int numeroDecimales, BigDecimal numero) {

		NumberFormat nf = NumberFormat.getInstance();
		// Establece el número de decimales (máximo y mínimo):
		nf.setMaximumFractionDigits(numeroDecimales);
		nf.setMinimumFractionDigits(numeroDecimales);
		String st = nf.format(numero);
		// Si hay punto es en la parte entera. Lo quita:
		String st2 = st.replace(".", "");
		// Cambia la coma decimal por punto:
		String st3 = st2.replace(",", ".");
		return new BigDecimal(st3);
	}

	/**
	 * Convierte la marca 'editada' del vehículo en marca 'sin editar'
	 * @param marcaEditada
	 * @return marcaSinEditar
	 * @throws Exception
	 */
	public String getMarcaSinEditar(String marca) {
		if (marca == null || marca.equals("")) {
			return marca;
		}
		marca = marca.toUpperCase();
		String[] caracteres = { " ", ".", "-", ",", "Ñ", "'", "(", ")", "&", "/", "+", ";", "=", "Ä", "Ë", "Ï", "Ö", "Ü" };
		for (int i = 0; i < caracteres.length; i++) {
			if (marca.contains(caracteres[i])) {
				marca = marca.replace(caracteres[i], "");
			}
		}
		return marca;
	}

	public BigDecimal[] convertirStringArrayToBigDecimalArray(String[] elementos) {
		if (elementos == null) {
			return null;
		}
		List<BigDecimal> convertidos = new ArrayList<BigDecimal>();
		for (int i = 0; i < elementos.length; i++) {
			if (elementos[i] != null) {
				BigDecimal l = stringToBigDecimal(elementos[i]);
				if (l != null) {
					convertidos.add(l);
				}
			}
		}
		BigDecimal[] result = new BigDecimal[convertidos.size()];
		return convertidos.toArray(result);
	}

	public BigDecimal[] convertirLongArrayToBigDecimalArray(Long[] elementos) {
		if (elementos == null) {
			return null;
		}
		BigDecimal[] convertidos = new BigDecimal[elementos.length];
		for (int i = 0; i < elementos.length; i++) {
			convertidos[i] = convertirLongABigDecimal(elementos[i]);
		}
		return convertidos;
	}

	public List<String> convertirListLongToListString(List<Long> elementos) {
		if (elementos == null) {
			return null;
		}
		List<String> lista = new ArrayList<>();
		for (Long elemento : elementos) {
			lista.add(elemento.toString());
		}
		return lista;
	}

	public Long[] convertirBigDecimalArrayToLongArray(BigDecimal[] elementos) {
		if (elementos == null) {
			return null;
		}
		Long[] convertidos = new Long[elementos.length];
		for (int i = 0; i < elementos.length; i++) {
			convertidos[i] = convertirBigDecimalALong(elementos[i]);
		}
		return convertidos;
	}

	public Long[] convertirLongListToLongArray(List<Long> elementos) {
		if (elementos == null) {
			return null;
		}
		Long[] convertidos = new Long[elementos.size()];
		for (int i = 0; i < elementos.size(); i++) {
			convertidos[i] = elementos.get(i);
		}
		return convertidos;
	}

	public String convertirNulltoString(String elemento) {
		if ("".equals(elemento)) {
			return null;
		}
		return elemento;
	}

	public BigDecimal[] convertirBigDecimalListToBigDecimalArray(List<BigDecimal> elementos) {
		if (elementos == null) {
			return null;
		}
		BigDecimal[] convertidos = new BigDecimal[elementos.size()];
		for (int i = 0; i < elementos.size(); i++) {
			convertidos[i] = elementos.get(i);
		}
		return convertidos;
	}

	/**
	 * Si son vacíos (null, '') o iguales los dos elementos devuelvo true, sino false
	 * @param pantalla
	 * @param bbdd
	 * @return
	 */
	public boolean sonIgualesString(String pantalla, String bbdd) {
		String pant = convertirNulltoString(pantalla);
		String bbDD = convertirNulltoString(bbdd);
		if (pant == null && bbDD == null) {
			return true;
		} else if (pant != null && bbDD == null) {
			return false;
		} else if (pant == null && bbDD != null) {
			return false;
		} else if (pant.equals(bbDD)) {
			return true;
		}
		return false;
	}

	/**
	 * Si son vacíos (null, '') o iguales los dos elementos devuelvo true, sino false
	 * @param pantalla
	 * @param bbdd
	 * @return
	 */
	public boolean sonIgualesBigDecimal(BigDecimal pantalla, BigDecimal bbdd) {
		if (pantalla == null && bbdd == null) {
			return true;
		} else if (pantalla != null && bbdd == null) {
			return false;
		} else if (pantalla == null && bbdd != null) {
			return false;
		} else if (pantalla.floatValue() == bbdd.floatValue()) {
			return true;
		}
		return false;
	}

	/**
	 * Si son vacíos (null, '', -1) o iguales los dos elementos devuelvo true, sino false
	 * @param pantalla
	 * @param bbdd
	 * @return
	 */
	public boolean sonIgualesCombo(String pantalla, String bbdd) {
		String pant = convertirCombo(pantalla);
		String bd = convertirCombo(bbdd);
		if (pant == null && bd == null) {
			return true;
		} else if (pant != null && bd == null) {
			return false;
		} else if (pant == null && bd != null) {
			return false;
		} else if (pant.equals(bd)) {
			return true;
		}
		return false;
	}

	/**
	 * Si son iguales los dos elementos devuelvo true, sino false
	 * @param pantalla
	 * @param bbdd
	 * @return
	 */
	public boolean sonIgualesObjetos(Object bbdd, Object pantalla) {
		if (pantalla == null && bbdd == null) {
			return true;
		} else if (pantalla != null && bbdd == null) {
			return false;
		} else if (pantalla == null && bbdd != null) {
			return false;
		} else if (pantalla.equals(bbdd)) {
			return true;
		}
		return false;
	}

	/**
	 * En BBDD puede que sea null y de pantalla venga NO
	 * @param pantalla
	 * @param bbdd
	 * @return
	 */
	public boolean sonIgualesCheckBox(String pantalla, String bbdd) {
		if ("NO".equals(pantalla) && bbdd == null) {
			return true;
		} else if ("false".equals(pantalla) && bbdd == null) {
			return true;
		} else if ("N".equals(pantalla) && bbdd == null) {
			return true;
		} else if ((pantalla == null || "NO".equals(pantalla)) && "N".equals(bbdd)) {
			return true;
		} else if ("SI".equals(pantalla) && "S".equals(bbdd)) {
			return true;
		}
		return sonIgualesString(pantalla, bbdd);
	}

	/**
	 * En BBDD puede que sea null y de pantalla venga NO
	 * @param pantalla
	 * @param bbdd
	 * @return
	 */
	public boolean sonIgualesCheckBox(Boolean pantalla, Boolean bbdd) {
		if ((pantalla == null || !pantalla) && bbdd == null) {
			return true;
		}
		return sonIgualesObjetos(pantalla, bbdd);
	}

	/**
	 * Si los objetos son diferentes por ser uno de ellos nulo
	 * @param elemento1
	 * @param elemento2
	 * @return
	 */
	public boolean diferenciaNulls(Object elemento1, Object elemento2) {
		if (elemento1 == null && elemento2 != null) {
			return false;
		} else if (elemento1 != null && elemento2 == null) {
			return false;
		}
		return true;
	}

	/**
	 * Se aplica el algoritmo MD5 a la password que se le pasa por parámetro
	 * @param password
	 * @return hashword
	 */
	public String hashPassword(String password) throws Exception {
		String hashword = null;
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		md5.update(password.getBytes());
		BigInteger hash = new BigInteger(1, md5.digest());
		hashword = hash.toString(16);
		return hashword;
	}

	/**
	 * Entorno estático para la decodificación de una cadena en base 64
	 * @param bas64
	 * @param codificacion
	 * @return
	 */
	public byte[] doBase64Decode(String bas64, String codificacion) throws Exception {
		if (codificacion != null) {
			return Base64.decodeBase64(bas64.getBytes());
		} else {
			return Base64.decodeBase64(bas64.getBytes(codificacion));
		}
	}

	/**
	 * Entorno estático para la codificación de un array de bytes en base 64
	 * @param array
	 * @return
	 */
	public String doBase64Encode(byte[] array) {
		return new String(Base64.encodeBase64(array));
	}

	// INICIO MANTIS 0011927: impresión de justificantes profesionales

	/**
	 * Transforma una lista de String en una cadena separada por comas y ''
	 * @param lista lista
	 * @param strInicio (opcional) cadena de texto para añadir al inicio.
	 * @param strFin (opcional) cadena de texto para añadir al final.
	 * @param delimitadorElementos (opcional) cadena de texto para delimitar cada elemento.
	 * @return String - cadena resultado
	 */
	public String transformListaToString(List<String> lista, String strInicio, String strFin, String delimitadorElementos) {
		if (lista == null) {
			return "";
		}
		return transformListaToString(lista.toArray(new String[0]), strInicio, strFin, delimitadorElementos);
	}

	/**
	 * Transforma una lista de String en una cadena separada por comas y ''
	 * @param lista lista
	 * @param strInicio (opcional) cadena de texto para añadir al inicio.
	 * @param strFin (opcional) cadena de texto para añadir al final.
	 * @param delimitadorElementos (opcional) cadena de texto para delimitar cada elemento.
	 * @return String - cadena resultado
	 */
	public String transformListaToString(String[] lista, String strInicio, String strFin, String delimitadorElementos) {

		StringBuffer cadenaResultado = new StringBuffer();

		// Delimitador inicial
		if (strInicio != null) {
			cadenaResultado.append(strInicio);
		}

		if (lista != null && lista.length > 0) {
			int i = 1;

			for (String elemento : lista) {
				// Añadimos delimitador inicial
				if (delimitadorElementos != null) {
					cadenaResultado.append(delimitadorElementos);
				}

				// Añadimos elemento
				cadenaResultado.append(elemento);

				// Vemos si hay que introducir separador
				if (i < lista.length) {
					cadenaResultado.append(", ");
				}

				// Añadimos delimitador final
				if (delimitadorElementos != null) {
					cadenaResultado.append(delimitadorElementos);
				}

				i++;
			}
		}

		// Delimitador final
		if (strFin != null) {
			cadenaResultado.append(strFin);
		}

		return cadenaResultado.toString();
	}

	// FIN MANTIS 0011927

	public BigDecimal[] convertirStringArrayToBigDecimal(String[] elementos) {
		if (elementos == null) {
			return null;
		}
		List<BigDecimal> convertidos = new ArrayList<>();
		for (int i = 0; i < elementos.length; i++) {
			BigDecimal l = stringToBigDecimal(elementos[i]);
			if (l != null) {
				convertidos.add(l);
			}
		}
		BigDecimal[] result = new BigDecimal[convertidos.size()];
		return convertidos.toArray(result);
	}

	public String[] convertirBigDecimalArrayToStringArray(BigDecimal[] elementos) {
		if (elementos == null) {
			return null;
		}
		String[] convertidos = new String[elementos.length];
		for (int i = 0; i < elementos.length; i++) {
			convertidos[i] = convertirBigDecimalAString(elementos[i]);
		}
		return convertidos;
	}

	private String convertirBigDecimalAString(BigDecimal valor) {
		if (valor == null)
			return null;
		else
			return valor.toString();
	}

	// Mantis 16330
	public String getIpMaquina() {
		try {
			String ipDefinitiva = "?";
			Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
			for (NetworkInterface netint : Collections.list(nets)) {
				Enumeration<InetAddress> inetAddresses = netint.getInetAddresses();
				for (InetAddress inetAddress : Collections.list(inetAddresses)) {
					String ip = inetAddress.toString();
					if (ip != null && !"".equals(ip)) {
						if (ip.contains("192.168.50")) {
							ipDefinitiva = ip.split("\\.")[3];
						}
					}
				}
			}
			return ipDefinitiva;
		} catch (Exception e) {
			log.error("Ha ocurrido un error al recuperar la IP del servidor", e);
			return "?";
		}
	}

	public String[] transformListaToArrayString(List<String> lista) {
		String[] arrayString = new String[lista.size()];
		for (int i = 0; i < lista.size(); i++) {
			arrayString[i] = lista.get(i);
		}
		return arrayString;
	}

	public String formatDoubleToString(String format, Double valor) {
		DecimalFormatSymbols dfs = new DecimalFormatSymbols();
		dfs.setDecimalSeparator(',');
		dfs.setGroupingSeparator('.');
		DecimalFormat df = new DecimalFormat(format, dfs);
		df.setGroupingSize(3);
		df.setGroupingUsed(true);
		return df.format(valor);
	}

	//Métodos movidos de UtilesNuevo
	public Fecha transformExpedienteFecha(BigDecimal numExpediente){
		Fecha fecha=null;
		fecha = transformExpedienteFecha(numExpediente.toString());
		return fecha;
	}

	public String getFilePath(String src) {
		URL ruta = this.getClass().getResource(src);
		return ruta.getFile();
	}

	public File getFileFromSrc(String src) {
		if (null == src || src.equals("")) {
			return null;
		}

		String myFile = getFilePath(src);
		return new File(myFile);
	}

	public BigDecimal[] convertirBigDecimalArrayToStringArray(String[] elementos) {
		if (elementos == null || elementos.length == 0) {
			return null;
		}
		Boolean relleno = Boolean.FALSE;
		BigDecimal[] convertidos = new BigDecimal[elementos.length];
		for (int i = 0; i < elementos.length; i++) {
			if(elementos[i] != null && !elementos[i].isEmpty()) {
				convertidos[i] = convertirStringToBigDecimal(elementos[i]);
				relleno = Boolean.TRUE;
			}
		}
		if (!relleno) {
			convertidos = null;
		}
		return convertidos;
	}

	public BigDecimal convertirStringToBigDecimal(String valor) {
		if (valor == null || valor.isEmpty()) {
			return null;
		} else {
			return new BigDecimal(valor);
		}
	}

	public Date horaStringToDate(String hora) {
		Date startTime = null;
		if (hora != null && !hora.equals("")) {
			SimpleDateFormat formatAnno = new SimpleDateFormat("yyyyMMdd");
			String fechaHoy = formatAnno.format(new Date());
			int anno = Integer.parseInt(fechaHoy.substring(0, 4));
			int mes = Integer.parseInt(fechaHoy.substring(4, 6)) - 1;
			int dia = Integer.parseInt(fechaHoy.substring(6, 8));
			Calendar cal = new GregorianCalendar(anno, mes, dia);
			cal.set(Calendar.HOUR, Integer.parseInt(hora.substring(0, 2)));
			cal.set(Calendar.MINUTE, Integer.parseInt(hora.substring(2, 4)));
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);

			startTime = cal.getTime();
		}
		return startTime;
	}

	public boolean comprobarNumero(String cadena) {
		try {
			Integer.parseInt(cadena);
		} catch (Exception e) {
			if ("SN".equals(cadena)) return true;
			else return false;
		}
		return true;
	}

	public String quitarCaracteresExtranios(String texto) {
		texto = texto.replace("ƒÂ³", "O");
		texto = texto.replace("ƒÃ­", "I");
		texto = texto.replace("Ã¡", "A");
		texto = texto.replace("Ã©", "E");
		texto = texto.replace("Ãº", "U");

		texto = texto.replace("ÃƒÂ³", "O");
		texto = texto.replace("ƒÃÃ­", "I");
		texto = texto.replace("ÃÃ¡", "A");
		texto = texto.replace("ÃÃ©", "E");
		texto = texto.replace("ÃÃº", "U");

		texto = texto.replace("ÃÃ±", "ñ");
		texto = texto.replace("Ã±", "ñ");

		texto = texto.replace("ƒÂ", "");

		texto = texto.replace("'", "");
		texto = texto.replace("/", "");
		return texto;
	}

	public int compararFechaDate(Date pantalla, Date bbdd) throws ParseException {
		int res = 0;
		if (pantalla == null && bbdd == null) {
			return 0;
		} else if (pantalla == null || bbdd == null) {
			return -1;
		} else if (pantalla.compareTo(bbdd) == 0) {
			res = 0;
		} else if (pantalla.compareTo(bbdd) > 0) {
			res = 1;
		} else {
			res = 2;
		}
		return res;
	}

	public boolean esNombreFicheroValido(String nombreFichero) {
		if (StringUtils.isNotBlank(nombreFichero)) {
			for (int i = 0; i < ILLEGAL_CHARACTERS.length; i++) {
				if (nombreFichero.contains(String.valueOf(ILLEGAL_CHARACTERS[i]))) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Devuelve un HasMap con (importeReduccion, baseImponible y cuotaTributaria)
	 * @param valorDeclarado
	 * @param reduccion
	 * @return HashMap<String,BigDecimal>
	 */
	public HashMap<String,BigDecimal> calculoCuotaTributaria(BigDecimal valorDeclarado, BigDecimal reduccion){
		HashMap<String,BigDecimal> mapaValores = new HashMap<>();
		if(null != valorDeclarado && null != reduccion) {
			mapaValores.put("importeReduccion", valorDeclarado.multiply(reduccion).setScale(2, RoundingMode.HALF_UP));
			mapaValores.put("baseImponible", valorDeclarado.subtract( mapaValores.get("importeReduccion")).setScale(2, RoundingMode.HALF_UP));
			mapaValores.put("cuotaTributaria", mapaValores.get("baseImponible").multiply(new BigDecimal("0.04")).setScale(2, RoundingMode.HALF_UP));
		}
		return mapaValores;
	}
	public Boolean esUsuarioMatw(String colegiado) {
		String forzarMatw = gestorPropiedades.valorPropertie("forzar.matw");
		if ("SI".equalsIgnoreCase(forzarMatw) || esAutorizadoMatw(colegiado)) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	private boolean esAutorizadoMatw(String colegiado) {
		String[] usuariosMatw = gestorPropiedades.valorPropertie("usuarios.matw").split(",");
		for (String elemento : usuariosMatw) {
			if (elemento.replaceAll("\\n","").equalsIgnoreCase(colegiado)) {
				return Boolean.FALSE;
			}
		}
		return Boolean.TRUE;
	}

	public Boolean esUsuarioSive(String colegiado) {
		String forzarSive = gestorPropiedades.valorPropertie("forzar.sive");
		if ("SI".equalsIgnoreCase(forzarSive) || esAutorizadoSive(colegiado)) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	private boolean esAutorizadoSive(String colegiado) {
		String[] usuariosSive = gestorPropiedades.valorPropertie("usuarios.sive").split(",");
		for (String elemento : usuariosSive) {
			if (elemento.equals(colegiado)) {
				return Boolean.TRUE;
			}
		}
		return Boolean.FALSE;
	}

	public Boolean esUsuarioBajaSive(String colegiado) {
		String forzarSive = gestorPropiedades.valorPropertie("forzar.baja.sive");
		if ("SI".equalsIgnoreCase(forzarSive) || esAutorizadoBajaSive(colegiado)) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	private boolean esAutorizadoBajaSive(String colegiado) {
		String[] usuariosSive = gestorPropiedades.valorPropertie("usuarios.baja.sive").split(",");
		for (String elemento : usuariosSive) {
			if (elemento.equals(colegiado)) {
				return Boolean.TRUE;
			}
		}
		return Boolean.FALSE;
	}

	public Boolean esUsuarioIvtm(String colegiado) {
		String forzarIvtm = gestorPropiedades.valorPropertie("forzar.ivtm");
		if ("SI".equalsIgnoreCase(forzarIvtm) || esAutorizadoIvtm(colegiado)) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	private boolean esAutorizadoIvtm(String colegiado) {
		String[] usuariosMatw = gestorPropiedades.valorPropertie("usuarios.ivtm").split(",");
		for (String elemento : usuariosMatw) {
			if (elemento.replaceAll("\\n","").equalsIgnoreCase(colegiado)) {
				return Boolean.TRUE;
			}
		}
		return Boolean.FALSE;
	}

	public Boolean esUsuarioExentoCtr(String colegiado) {
		String forzarExentoCtr = gestorPropiedades.valorPropertie("forzar.exento.ctr");
		if ("SI".equalsIgnoreCase(forzarExentoCtr) || esAutorizadoExentoCtr(colegiado)) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	private boolean esAutorizadoExentoCtr(String colegiado) {
		String[] usuariosMatw = gestorPropiedades.valorPropertie("usuarios.exento.ctr").split(",");
		for (String elemento : usuariosMatw) {
			if (elemento.replaceAll("\\n","").equalsIgnoreCase(colegiado)) {
				return Boolean.TRUE;
			}
		}
		return Boolean.FALSE;
	}

	public Boolean esUsuarioOegam3(String colegiado) {
		String forzarMatw = gestorPropiedades.valorPropertie("forzar.oegam3");
		if ("SI".equalsIgnoreCase(forzarMatw) || esAutorizadoOegam3(colegiado)) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	private boolean esAutorizadoOegam3(String colegiado) {
		String[] usuariosMatw = gestorPropiedades.valorPropertie("usuarios.oegam3").split(",");
		for (String elemento : usuariosMatw) {
			if (elemento.replaceAll("\\n","").equalsIgnoreCase(colegiado)) {
				return Boolean.TRUE;
			}
		}
		return Boolean.FALSE;
	}

}