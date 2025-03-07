package org.gestoresmadrid.oegamImportacion.utiles;



import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.StringTokenizer;

/**
*
* Colección <b>básica</b> de tratamiento de cadenas.
* Ver la clase Text para más utilidades de cadenas.
*
*
*
* <dl><dt>
* <b>Historia BasicText</b>:
* <dd>		Versión 1.0.0 - Versión inicial
* 
* @version	1.0.0, 26/09/2001
* @author	Jesús Viñuales Bueno
* 
* <dl><dt>
* <b>Historia Text </b>:
* <dd>		Versión 1.0.0 - Versión inicial
* <dd>		Versión 2.0.0 - Mejora de velocidad en replaceAllSubstring.
* <dd>		Versión 2.0.1 - Eliminación del método indexOf(). Es muy costoso.
* <dd>		Versión 2.0.2 - Versiones de changeSize() para StringBuffers
* <dd>		Versión 2.0.3 - Optimización de velocidad en los métodos changeSize()
* <dd>		Versión 2.0.4 - Optimización de velocidad en el método setZeros()
* <dd>		Versión 3.0.0 - Extiende de BasicText
* </dl>
*
*
* @version	3.0.0, 26/09/2001
* @author	Jesús Viñuales Bueno
* </dl>
* 
* 
* <dd> 	Version 4.0.0 - Se juntan ambas clases en una unica y se eliminan 
* algunas utilidades que son provistas directamente por el JDK.  
* @version	4.0.0, 07/11/2006
* @author Jose Manuel Infante
*
*/
public abstract class BasicText {

	public static final boolean DETRAS = true;
	public static final boolean DELANTE = false;

	public static final String CHARACTER_ENCODING = "iso-8859-1";

	/**
	 * Caracter espacio en blanco.
	 */
	public static final char SPACE = ' ';
	
	/**
	 * Caracter cero.
	 */
	public static final char ZERO = '0';
	
	/**
	 * Caracter Comilla doble.
	 */
	public static final char QUOTE = '"';
	
	/**
	 * Caracter Subrayado bajo
	 */
	public static final char UNDERSCORE = '_';
	
	/** 
	 * Caracter tabulador.
	 */
	public static final char TAB = '\t';
	
	private static final int ARRAYS_LENGTH = 200;

	/** 200 espacios en blanco. */
	private static final char[] SPACES_ARRAY = new char[ARRAYS_LENGTH];

	/** 200 ceros. */
	private static final char[] ZEROS_ARRAY = new char[ARRAYS_LENGTH];

	/**
	 * Inicializa los arrays de caracteres.
	 */
	static {
		// crea los arrays
		for (int i = 0; i < ARRAYS_LENGTH; i++) {
			SPACES_ARRAY[i] = SPACE;
			ZEROS_ARRAY[i] = ZERO;
		}
	}

	/**
	 * Reemplaza en una cadena un texto por otro.
	 * La comparación no es sensitiva a mayúsculas y minúsculas
	 * (case insensitive).
	 *
	 * @param cadena		la cadena original en la que va a buscarse
	 * @param subcadena1	la subcadena que se buscará (case insensitive) en la cadena
	 * @param subcadena2	la subcadena por la que se reemplazará la subcadena1
	 *
	 * @return 				la cadena modificada
	 *
	 */
	public static final String replaceSubstring(String cadena, String subcadena1, String subcadena2) {

		return replaceSubstring(cadena, subcadena1, subcadena2, true, 0);
	}

	/**
	 * Reemplaza en una cadena un texto por otro.
	 * La comparación puede ser sensitiva o no sensitiva a
	 * mayúsculas y minúsculas.
	 *
	 * @param cadena		la cadena original en la que va a buscarse
	 * @param subcadena1	la subcadena que se buscará en la cadena
	 * @param subcadena2	la subcadena por la que se reemplazará la subcadena1
	 * @param ignoreCase	si la búsqueda de subcadena1 en cadena debe ignorar
	 *						si está en mayúsculas y minúsculas (true) o no (false).
	 * @param indexInicio	posición de inicio de búsqueda
	 *
	 * @return 				la cadena modificada
	 *
	 */
	public static final String replaceSubstring(String cadena, String subcadena1, String subcadena2, boolean ignoreCase, int indexInicio) {

		if (cadena == null || subcadena1 == null || subcadena2 == null)
			return null;

		int index;

		if (!ignoreCase)
			index = cadena.indexOf(subcadena1, indexInicio);
		else
			index = cadena.toUpperCase().indexOf(subcadena1.toUpperCase(), indexInicio);

		if (index < 0)
			return cadena;
		else {
			int len = subcadena1.length();
			return (cadena.substring(0, index) + subcadena2 + cadena.substring(index + len));
		}
	}

	/**
	 * Reemplaza en una cadena un texto por otro.
	 * La comparación puede ser sensitiva o no sensitiva a
	 * mayúsculas y minúsculas.
	 *
	 * @param cadena		la cadena original en la que va a buscarse
	 * @param subcadena1	la subcadena que se buscará en la cadena
	 * @param subcadena2	la subcadena por la que se reemplazará la subcadena1
	 * @param ignoreCase	si la búsqueda de subcadena1 en cadena debe ignorar
	 *						si está en mayúsculas y minúsculas (true) o no (false).
	 * @param indexInicio	posición de inicio de búsqueda
	 *
	 * @return 				la cadena modificada
	 *
	 */
	public static final String replaceAllSubstring(String cadena, String subcadena1, String subcadena2, boolean ignoreCase, int indexInicio) {

		if (cadena == null || subcadena1 == null || subcadena2 == null)
			return null;

		int index = 0;
		int lastIndex = cadena.lastIndexOf(subcadena1);
		if (lastIndex < 0)
			return cadena;

		int len = cadena.length();
		int lenSubcadena1 = subcadena1.length();
		StringBuffer cadenaBuffer = new StringBuffer(len + 16);

		while (index < lastIndex) {
			if (!ignoreCase) {
				index = cadena.indexOf(subcadena1, indexInicio);
			} else {
				index = cadena.toLowerCase().indexOf(subcadena1.toLowerCase(), indexInicio);
			}
			if (index < 0) {
				break;
			}

			cadenaBuffer.append(cadena.substring(indexInicio, index)).append(subcadena2);
			indexInicio = index + lenSubcadena1;//+1;
		}

		// El resto del cuaderno (si no lleva salto de línea al final)
		int aux = lastIndex + lenSubcadena1; //+1;
		if (aux < len) {
			cadenaBuffer.append(cadena.substring(aux));
		}
		return cadenaBuffer.toString();
	}

	/**
	 * Crea una copia de cadena y con el tamaño especificado;
	 * si la cadena es mayor que el tamaño especificado, devuelve los
	 * <code>size</code> primeros caracteres; en caso contrario, completa
	 * con espacios en blanco al final de la cadena hasta llegar al tamaño
	 * especificado.
	 *
	 * @param	cadena	la cadena original
	 * @param	size	el tamaño en caracteres de la copia
	 * @return	la copia con el nuevo tamaño
	 */
	public static final String changeSize(String cadena, int size) {

		return changeSize(cadena, size, SPACE, DETRAS);
	}

	/**
	 * Crea una copia de cadena y con el tamaño especificado;
	 * si la cadena es mayor que el tamaño especificado, devuelve los
	 * <code>size</code> primeros caracteres; en caso contrario, completa
	 * con el caracter especificado al final de la cadena hasta llegar al tamaño
	 * especificado.
	 *
	 * @param	cadena	la cadena original
	 * @param	size	el tamaño en caracteres de la copia
	 * @param	relleno el caracter de relleno para completar el tamaño
	 * @return	la copia con el nuevo tamaño
	 */
	public static final String changeSize(String cadena, int size, char relleno) {

		return changeSize(cadena, size, relleno, DETRAS);
	}

	/**
	 * Crea una copia de cadena y con el tamaño especificado;
	 * si la cadena es mayor que el tamaño especificado, devuelve los
	 * <code>size</code> primeros caracteres; en caso contrario, completa
	 * con el caracter especificado al final de la cadena hasta llegar al tamaño
	 * especificado.
	 *
	 * @param	cadena	la cadena original
	 * @param	size	el tamaño en caracteres de la copia
	 * @param	relleno el caracter de relleno para completar el tamaño
	 * @param	detras  si hay que completar con " " detrás (true)
	 *					o delante (false) de la cadena original
	 * @return	la copia con el nuevo tamaño
	 */
	public static final String changeSize(String cadena, int size, char relleno, boolean detras) {

		String res = null;
		if (cadena == null)
			return null;

		int tam = cadena.length();

		if (size == tam) {
			return cadena;
		} else if (size > tam) {
			StringBuffer buffer = new StringBuffer(size);
			int dif = size - tam;

			char[] blancos;
			boolean isLessThan = dif < ARRAYS_LENGTH;

			if (isLessThan && relleno == SPACE) {
				blancos = SPACES_ARRAY;
			} else if (isLessThan && relleno == ZERO) {
				blancos = ZEROS_ARRAY;
			} else {
				blancos = new char[dif];
				for (int i = 0; i < dif; i++) {
					blancos[i] = relleno;
				}
			}

			if (detras)
				buffer.append(cadena).append(blancos, 0, dif);
			else
				buffer.append(blancos, 0, dif).append(cadena);
			return buffer.toString();
		} else {
			if (detras)
				res = cadena.substring(0, size);
			else
				res = cadena.substring(tam - size);
		}

		return res;
	}

	/**
	 * Crea una copia de cadena y con el tamaño especificado;
	 * si la cadena es mayor que el tamaño especificado, devuelve los
	 * <code>size</code> primeros caracteres; en caso contrario, completa
	 * con espacios en blanco al final de la cadena hasta llegar al tamaño
	 * especificado.
	 *
	 * @param	buffer	la cadena original
	 * @param	size	el tamaño en caracteres de la copia
	 * @return	la copia con el nuevo tamaño
	 */
	public static final String changeSize(StringBuffer buffer, int size) {

		return changeSize(buffer, size, SPACE, DETRAS);
	}

	/**
	 * Crea una copia de cadena y con el tamaño especificado;
	 * si la cadena es mayor que el tamaño especificado, devuelve los
	 * <code>size</code> primeros caracteres; en caso contrario, completa
	 * con el caracter especificado al final de la cadena hasta llegar al tamaño
	 * especificado.
	 *
	 * @param	buffer	la cadena original
	 * @param	size	el tamaño en caracteres de la copia
	 * @param	relleno el caracter de relleno para completar el tamaño
	 * @return	la copia con el nuevo tamaño
	 */
	public static final String changeSize(StringBuffer buffer, int size, char relleno) {

		return changeSize(buffer, size, relleno, DETRAS);
	}

	/**
	 * Crea una copia de cadena y con el tamaño especificado;
	 * si la cadena es mayor que el tamaño especificado, devuelve los
	 * <code>size</code> primeros caracteres; en caso contrario, completa
	 * con el caracter especificado al final de la cadena hasta llegar al tamaño
	 * especificado.
	 *
	 * @param	buffer	la cadena original
	 * @param	size	el tamaño en caracteres de la copia
	 * @param	relleno el caracter de relleno para completar el tamaño
	 * @param	detras  si hay que completar con " " detrás (true)
	 *					o delante (false) de la cadena original
	 * @return	la copia con el nuevo tamaño
	 */
	public static final String changeSize(StringBuffer buffer, int size, char relleno, boolean detras) {

		if (buffer == null)
			return null;

		int tam = buffer.length();

		if (size == tam) {
			return buffer.toString();
		} else if (size > tam) {
			buffer.ensureCapacity(size);
			int dif = size - tam;

			char[] blancos;
			boolean isLessThan = dif < ARRAYS_LENGTH;

			if (isLessThan && relleno == SPACE) {
				blancos = SPACES_ARRAY;
			} else if (isLessThan && relleno == ZERO) {
				blancos = ZEROS_ARRAY;
			} else {
				blancos = new char[dif];
				for (int i = 0; i < dif; i++) {
					blancos[i] = relleno;
				}
			}

			if (detras) {
				buffer.append(blancos, 0, dif);
			} else {
				// En JDK1.1 no existe el método
				// StringBuffer.insert(int index,char str[],int offset,int len);
				// Apareció en JDK1.2 :-(
				buffer.insert(0, new String(blancos, 0, dif));
			}

			return buffer.toString();
		} else {
			if (detras) {
				buffer.setLength(size);
				return buffer.toString();
			} else
				return buffer.toString().substring(tam - size);
		}
	}

	/**
	 * Pasa una cadena a modo oración (primera letra de cada palabra a mayúsculas)
	 *
	 * @param	cadena	la cadena original
	 * @return	la cadena con el nuevo formato
	 */
	public static final String capitalize(String cadena) {

		try {
			StringBuffer cadenaOracion = new StringBuffer();
			String palabra = "";

			// Pasamos primero todo a minúsculas.
			cadena = cadena.toLowerCase();

			StringTokenizer st = new StringTokenizer(cadena);
			while (st.hasMoreTokens()) {
				palabra = (String) st.nextElement();
				if ((palabra != null) && (palabra.length() > 0)) {
					if (palabra.length() == 1)
						palabra = palabra.substring(0, 1).toUpperCase();
					else
						palabra = palabra.substring(0, 1).toUpperCase() + palabra.substring(1);
				}
				cadenaOracion.append(palabra).append(" ");
			}

			return (cadenaOracion.toString().trim());
		} catch (Exception ex) {
			return cadena;
		}
	}

	/**
	 * Reemplaza los caracteres con acento por la cadena html que representa el caracter
	 * 		á --> &aacute;
	 *
	 * @param cadena		la cadena original en la que va a buscarse
	 *
	 * @return 				la cadena modificada
	 *
	 */
	public static final String replaceAccents(String cadena) {

		if (cadena == null)
			return null;

		String[] subcadenas1 = { "á", "é", "í", "ó", "ú", "Á", "É", "Í", "Ó", "Ú" };
		String[] subcadenas2 = { "&aacute;", "&eacute;", "&iacute;", "&oacute;", "&uacute;", "&Aacute;", "&Eacute;", "&Iacute;", "&Oacute;", "&Uacute;" };

		if (subcadenas1.length != subcadenas2.length)
			return cadena;
		else {
			String subcadena1 = "";
			String subcadena2 = "";
			for (int array_index = 0; array_index < subcadenas1.length; array_index++) {
				subcadena1 = subcadenas1[array_index];
				subcadena2 = subcadenas2[array_index];
				cadena = BasicText.replaceAllSubstring(cadena, subcadena1, subcadena2, false, 0);
			}
		}
		return cadena;
	}

	/**
	 *  Método estático que vuelca la traza de una excepcion a un String
	 */
	public static final String getStackTrace(Throwable t) {

		java.io.StringWriter s = new java.io.StringWriter();
		t.printStackTrace(new java.io.PrintWriter(s));
		return s.toString();
	}

	/**
	 * If the given parameter has a value starting and ending by '"',
	 * removes those leading and trailing characters;
	 * this method "dequotes" a string, DOESN'T remove any inner
	 * quote nor remove leading or trailing quotes if
	 * both characters doesn't match (i.e. only appears a leading
	 * quote or only a trailing quote).
	 */
	public static final String removeQuotes(String original) {

		String res = original;
		int size = original.length();
		if (size >= 2) {
			int last = size - 1;
			if (res.charAt(last) == QUOTE && res.charAt(0) == QUOTE) {
				res = res.substring(1, last); // Elimina el primer y último carácter
			}
		}
		return res;
	}

	/**
	 * Operacion XOr entre Strings
	 */
	public static final String xor(String c1, String c2) {

		try {
			byte[] b1 = c1.getBytes(CHARACTER_ENCODING);
			int len1 = b1.length;

			byte[] b2 = c2.getBytes(CHARACTER_ENCODING);
			int len2 = b2.length;

			int maxLen = java.lang.Math.max(len1, len2);
			int minLen = java.lang.Math.min(len1, len2);
			byte[] xor = new byte[maxLen];
			for (int i = 0; i < minLen; i++) {
				xor[i] = (byte) (b1[i] ^ b2[i]);
			}
			if (len1 == maxLen) {
				for (int i = minLen; i < maxLen; i++) {
					xor[i] = b1[i];
				}
			} else {
				for (int i = minLen; i < maxLen; i++) {
					xor[i] = b2[i];
				}
			}

			return new String(xor, CHARACTER_ENCODING);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Returns a string representation of this message digest object.
	 */
	public static String digestToString(byte[] digestBits) {

		ByteArrayOutputStream ou = new ByteArrayOutputStream(2 * digestBits.length);
		PrintStream p = new PrintStream(ou);

		//if (digestBits != null) {
			for (int i = 0; i < digestBits.length; i++)
				hexDigit(p, digestBits[i]);
//		} else {
//			return null;
//		}
		return ou.toString();
	}

	/**
	 * Returns a string representation of this message digest object.
	 */
	public static byte[] stringToDigest(String digestString) {

		int size = digestString.length();
		int byteArraySize = size / 2;
		byte result[] = new byte[byteArraySize];
		int j = 0;
		int i = 0;
		for (; i < size;) {
			String auxStr = digestString.substring(i, i + 2);
			result[j] = (byte) Integer.parseInt(auxStr, 16);
			i += 2;
			j++;
		}
		return result;
	}

	/**
	 * This method makes a String with all the characters to zero.
	 * @param	len				Length of the string.
	 * @return	A String with all the characters to zero.
	 */
	public static final String setZeros(int len) {

		char[] zeros;

		if (len < ARRAYS_LENGTH) {
			zeros = ZEROS_ARRAY;
		} else {
			zeros = new char[len];
			for (int i = 0; i < len; i++) {
				zeros[i] = ZERO;
			}
		}
		return new String(zeros, 0, len);
	}

	/**
	 * Parses a String as a double. Both comma (",") and dot (".") are
	 * decimal separator characters.
	 *
	 * @throws NullPointerException if the argument is null.
	 * @throws ParseException if the argument does not match the double pattern.
	 */
	//	public static final double parseDouble(String text) throws ParseException {
	//
	//		if (text == null)
	//			throw new NullPointerException();
	//		else {
	//			text = text.replace(',', '.');
	//			try {
	//				return Double.valueOf(text).doubleValue();
	//			} catch (Exception e) {
	//				throw new ParseException(e.getMessage(), 0);
	//			}
	//		}
	//	}
	// fin de setZeros
	//	public static final String format(long l) throws Exception {
	//
	//		long l2 = l * (long) Math.pow(10D, 1);
	//		String s = basicFormat(l2);
	//		return s.substring(0, s.length() - 1);
	//	}
	//
	//	public static final String format(double d) throws Exception {
	//
	//		int decimalPlaces = 0;
	//		long l1 = (long) (d * Math.pow(10D, decimalPlaces + 1));
	//		String s = basicFormat(l1);
	//		return s.substring(0, s.length() - 1);
	//	}
	/**
	 * Returns a string representation of this message digest object.
	 */
	//	public static String digestToString(byte[] digestBits) {
	//
	//		ByteArrayOutputStream ou = new ByteArrayOutputStream(2 * digestBits.length);
	//		PrintStream p = new PrintStream(ou);
	//
	//		if (digestBits != null) {
	//			for (int i = 0; i < digestBits.length; i++)
	//				hexDigit(p, digestBits[i]);
	//		} else {
	//			return null;
	//		}
	//		return ou.toString();
	//	}
	/**
	 * Returns a string representation of this message digest object.
	 */
	//	public static byte[] stringToDigest(String digestString) {
	//
	//		int size = digestString.length();
	//		int byteArraySize = size / 2;
	//		byte result[] = new byte[byteArraySize];
	//		int j = 0;
	//		int i = 0;
	//		for (; i < size;) {
	//			String auxStr = digestString.substring(i, i + 2);
	//			result[j] = (byte) Integer.parseInt(auxStr, 16);
	//			i += 2;
	//			j++;
	//		}
	//		return result;
	//	}
	/**
	 * Helper function that prints unsigned two character hex digits.
	 */
	private static void hexDigit(PrintStream p, byte x) {

		char c;

		c = (char) ((x >> 4) & 0xf);
		if (c > 9) {
			c = (char) ((c - 10) + 'A');
		} else {
			c = (char) (c + '0');
		}
		p.write(c);

		c = (char) (x & 0xf);
		if (c > 9) {
			c = (char) ((c - 10) + 'A');
		} else {
			c = (char) (c + '0');
		}
		p.write(c);
	}
	//	private static final String basicFormat(long l) throws Exception {
	//
	//		String s = "";
	//		char c = '\0';
	//		if (l < 0L)
	//			c = '\r';
	//		else
	//			c = '\f';
	//		l = Math.abs(l);
	//		boolean flag = true;
	//		for (; l != 0L; l /= 10L) {
	//			int i = (int) remainder(l, 10L);
	//			if (flag) {
	//				c |= i << 4;
	//				s = c + s;
	//				flag = false;
	//			} else {
	//				c = (char) i;
	//				flag = true;
	//			}
	//		}
	//
	//		if (flag)
	//			s = c + s;
	//		if (s.length() > 16)
	//			throw new Exception();
	//		else
	//			return s;
	//	}
	//	private static final long remainder(long l, long l1) {
	//
	//		long l2 = l / l1;
	//		return l - l1 * l2;
	//	}
}
