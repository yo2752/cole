package general.utiles;



import java.util.Hashtable;
import java.util.StringTokenizer;

import utilidades.validaciones.NIFValidator;



/**
 * Clase que permite crear el anagrama
 * @author 	TB�Solutions
 * @version	1.0.0
 * @since	
 */
public class Anagrama {

	private static final int _100 = 100;

	private static final int _10000 = 10000;

	private static final int _1000000 = 1000000;

	private static final int _11 = 11;

	private static final int _12 = 12;

	private static final int _13 = 13;

	private static final int _14 = 14;

	private static final int _15 = 15;

	private static final int _16 = 16;

	private static final int _17 = 17;

	private static final int _18 = 18;

	private static final int _19 = 19;

	private static final int _20 = 20;

	private static final int _21 = 21;

	private static final int _22 = 22;

	private static final int _23 = 23;

	private static final int _24 = 24;

	private static final int INDEX = 3;

	private static final int END_INDEX = 3;

	private static final int INT = 3;

	private static final int _3 = 3;

	private static final int _4 = 4;

	private static final int _5 = 5;

	private static final int _6 = 6;

	private static final int _8 = 8;

	private static final int _9 = 9;

	private static final int _7 = 7;

	// variable donde se almacenan valores de conversi�n de letra a n�mero

	private static final int _10 = 10;

	private static Hashtable<String, String> ANAGRAMA_LETRA = null;

	private static String[] LETRAS = null;

	static {

		try {

			ANAGRAMA_LETRA = new Hashtable<String, String>(_100);

			ANAGRAMA_LETRA.put("A", "1");

			ANAGRAMA_LETRA.put("�", "1");

			ANAGRAMA_LETRA.put("�", "1");

			ANAGRAMA_LETRA.put("B", "2");

			ANAGRAMA_LETRA.put("C", "3");

			ANAGRAMA_LETRA.put("�", "3");

			ANAGRAMA_LETRA.put("D", "4");

			ANAGRAMA_LETRA.put("E", "5");

			ANAGRAMA_LETRA.put("�", "5");

			ANAGRAMA_LETRA.put("�", "5");

			ANAGRAMA_LETRA.put("F", "6");

			ANAGRAMA_LETRA.put("G", "7");

			ANAGRAMA_LETRA.put("H", "8");

			ANAGRAMA_LETRA.put("I", "9");

			ANAGRAMA_LETRA.put("�", "9");

			ANAGRAMA_LETRA.put("�", "9");

			ANAGRAMA_LETRA.put("J", "10");

			ANAGRAMA_LETRA.put("K", "11");

			ANAGRAMA_LETRA.put("L", "12");

			ANAGRAMA_LETRA.put("M", "13");

			ANAGRAMA_LETRA.put("N", "14");

			ANAGRAMA_LETRA.put("�", "15");

			ANAGRAMA_LETRA.put("#", "15");

			ANAGRAMA_LETRA.put("@", "15");

			ANAGRAMA_LETRA.put("?", "15");

			ANAGRAMA_LETRA.put("�", "15");

			ANAGRAMA_LETRA.put("/", "15");

			ANAGRAMA_LETRA.put("\\", "15");

			ANAGRAMA_LETRA.put("~", "15");

			ANAGRAMA_LETRA.put("O", "16");

			ANAGRAMA_LETRA.put("�", "16");

			ANAGRAMA_LETRA.put("�", "16");

			ANAGRAMA_LETRA.put("P", "17");

			ANAGRAMA_LETRA.put("Q", "18");

			ANAGRAMA_LETRA.put("R", "19");

			ANAGRAMA_LETRA.put("S", "20");

			ANAGRAMA_LETRA.put("T", "21");

			ANAGRAMA_LETRA.put("U", "22");

			ANAGRAMA_LETRA.put("�", "22");

			ANAGRAMA_LETRA.put("�", "22");

			ANAGRAMA_LETRA.put("V", "23");

			ANAGRAMA_LETRA.put("W", "24");

			ANAGRAMA_LETRA.put("X", "25");

			ANAGRAMA_LETRA.put("Y", "26");

			ANAGRAMA_LETRA.put("Z", "27");

			ANAGRAMA_LETRA.put("a", "1");

			ANAGRAMA_LETRA.put("�", "1");

			ANAGRAMA_LETRA.put("�", "1");

			ANAGRAMA_LETRA.put("�", "1");

			ANAGRAMA_LETRA.put("b", "2");

			ANAGRAMA_LETRA.put("c", "3");

			ANAGRAMA_LETRA.put("�", "3");

			ANAGRAMA_LETRA.put("d", "4");

			ANAGRAMA_LETRA.put("e", "5");

			ANAGRAMA_LETRA.put("�", "5");

			ANAGRAMA_LETRA.put("�", "5");

			ANAGRAMA_LETRA.put("f", "6");

			ANAGRAMA_LETRA.put("g", "7");

			ANAGRAMA_LETRA.put("h", "8");

			ANAGRAMA_LETRA.put("i", "9");

			ANAGRAMA_LETRA.put("�", "9");

			ANAGRAMA_LETRA.put("�", "9");

			ANAGRAMA_LETRA.put("j", "10");

			ANAGRAMA_LETRA.put("k", "11");

			ANAGRAMA_LETRA.put("l", "12");

			ANAGRAMA_LETRA.put("m", "13");

			ANAGRAMA_LETRA.put("n", "14");

			ANAGRAMA_LETRA.put("�", "15");

			ANAGRAMA_LETRA.put("o", "16");

			ANAGRAMA_LETRA.put("�", "16");

			ANAGRAMA_LETRA.put("�", "16");

			ANAGRAMA_LETRA.put("�", "16");

			ANAGRAMA_LETRA.put("p", "17");

			ANAGRAMA_LETRA.put("q", "18");

			ANAGRAMA_LETRA.put("r", "19");

			ANAGRAMA_LETRA.put("s", "20");

			ANAGRAMA_LETRA.put("t", "21");

			ANAGRAMA_LETRA.put("u", "22");

			ANAGRAMA_LETRA.put("�", "22");

			ANAGRAMA_LETRA.put("�", "22");

			ANAGRAMA_LETRA.put("v", "23");

			ANAGRAMA_LETRA.put("w", "24");

			ANAGRAMA_LETRA.put("x", "25");

			ANAGRAMA_LETRA.put("y", "26");

			ANAGRAMA_LETRA.put("z", "27");
			
			LETRAS = new String[_24];

			LETRAS[0] = "T";

			LETRAS[1] = "R";

			LETRAS[2] = "W";

			LETRAS[_3] = "A";

			LETRAS[_4] = "G";

			LETRAS[_5] = "M";

			LETRAS[_6] = "Y";

			LETRAS[_7] = "F";

			LETRAS[_8] = "P";

			LETRAS[_9] = "D";

			LETRAS[_10] = "X";

			LETRAS[_11] = "B";

			LETRAS[_12] = "N";

			LETRAS[_13] = "J";

			LETRAS[_14] = "Z";

			LETRAS[_15] = "S";

			LETRAS[_16] = "Q";

			LETRAS[_17] = "V";

			LETRAS[_18] = "H";

			LETRAS[_19] = "L";

			LETRAS[_20] = "C";

			LETRAS[_21] = "K";

			LETRAS[_22] = "E";

			LETRAS[_23] = "T";

		} catch (Throwable e) {
			// Excepcion
		}

	}

	public Anagrama() {
		
	}
	
	private static String calcularLetra(long numero) {
		long numeroSegundo = numero / _23;
		long numeroTercero = numeroSegundo * _23;
		int letra = (new Long(numero)).intValue()
				- (new Long(numeroTercero)).intValue();
		return LETRAS[letra];
	}

	public static long obtenerLetra(String letra) {
		long letraFinal = 0;
		try {
			letraFinal = Long.valueOf(ANAGRAMA_LETRA.get(letra)).longValue();
		} catch (Throwable ex) {
			letraFinal = 0;
		}
		return letraFinal;

	}

	/**Obiene el anagramaFiscal
	 * @param primerApellido 
	 * @param NIF
	 * @return
	 */
/*	public static String obtenerAnagramaFiscal(String primerApellido, String NIF) {
		try {
			// 1.- Quitamos los espacios en blanco del apellido.
			String apellido = primerApellido.trim();
			
			// 2.- Miramos si el apellido es un apellido compuesto.
			StringTokenizer strTokenizer = new StringTokenizer(apellido, " ");
			
			String palabra = null;
			while (strTokenizer.hasMoreTokens()) {
				palabra = strTokenizer.nextToken();
				palabra = palabra.toUpperCase();
				if ("DE".equalsIgnoreCase(palabra)
						|| "DEL".equalsIgnoreCase(palabra) ||
						"LA".equalsIgnoreCase(palabra)
						|| "LAS".equalsIgnoreCase(palabra) ||
						"LO".equalsIgnoreCase(palabra)
						|| "LOS".equalsIgnoreCase(palabra) ||
						"EL".equalsIgnoreCase(palabra)
						|| "DON".equalsIgnoreCase(palabra) ||
						"AL".equalsIgnoreCase(palabra))      // incidencia 3540: se descarta AL por ejemplo AL SASHIMI
				{
					// nada
				} else {
					apellido = palabra;
					break;
				}
			}
			
			// 3.- Si el apellido es menor que tres caractres, lo ajustamos a 3
			apellido = BasicText.changeSize(apellido, INT);
			String primeraLetraApellido = apellido.substring(0, 1);
			String segundaLetraApellido = apellido.substring(1, 2);
			String terceraLetraApellido = apellido.substring(2, END_INDEX);

			// Quitamos los posibles car�cteres extra�os que sustituyen a la "�"
			if ("�".equals(primeraLetraApellido)
					|| "#".equals(primeraLetraApellido) ||
					"@".equals(primeraLetraApellido)
					|| "?".equals(primeraLetraApellido) ||
					"�".equals(primeraLetraApellido)
					|| "/".equals(primeraLetraApellido) ||
					"\\".equals(primeraLetraApellido)
					|| "~".equals(primeraLetraApellido))
			{
				primeraLetraApellido = "�";
			}

			if ("�".equals(segundaLetraApellido)
					|| "#".equals(segundaLetraApellido) ||
					"@".equals(segundaLetraApellido)
					|| "?".equals(segundaLetraApellido) ||
					"�".equals(segundaLetraApellido)
					|| "/".equals(segundaLetraApellido) ||
					"\\".equals(segundaLetraApellido)
					|| "~".equals(segundaLetraApellido))
			{
				segundaLetraApellido = "�";
			}

			if ("�".equals(terceraLetraApellido)
					|| "#".equals(terceraLetraApellido) ||
					"@".equals(terceraLetraApellido)
					|| "?".equals(terceraLetraApellido) ||
					"�".equals(terceraLetraApellido)
					|| "/".equals(terceraLetraApellido) ||
					"\\".equals(terceraLetraApellido)
					|| "~".equals(terceraLetraApellido))
			{
				terceraLetraApellido = "�";
			}

			// Quitamos los acentos de las letras
			if ("�".equals(primeraLetraApellido)
					|| "�".equals(primeraLetraApellido) ||
					"�".equals(primeraLetraApellido)
					|| "�".equals(primeraLetraApellido) ||
					"�".equals(primeraLetraApellido))
			{
				primeraLetraApellido = "A";
			}

			// Quitamos los acentos de las letras
			if ("�".equals(primeraLetraApellido)
					|| "�".equals(primeraLetraApellido) ||
					"�".equals(primeraLetraApellido)
					|| "�".equals(primeraLetraApellido))
			{
				primeraLetraApellido = "E";
			}

			// Quitamos los acentos de las letras
			if ("�".equals(primeraLetraApellido)
					|| "�".equals(primeraLetraApellido) ||
					"�".equals(primeraLetraApellido)
					|| "�".equals(primeraLetraApellido))
			{
				primeraLetraApellido = "I";
			}

			// Quitamos los acentos de las letras
			if ("�".equals(primeraLetraApellido)
					|| "�".equals(primeraLetraApellido) ||
					"�".equals(primeraLetraApellido)
					|| "�".equals(primeraLetraApellido) ||
					"�".equals(primeraLetraApellido))
			{
				primeraLetraApellido = "O";
			}

			// Quitamos los acentos de las letras
			if ("�".equals(primeraLetraApellido)
					|| "�".equals(primeraLetraApellido) ||
					"�".equals(primeraLetraApellido)
					|| "�".equals(primeraLetraApellido))
			{
				primeraLetraApellido = "U";
			}

			// Quitamos los acentos de las letras
			if ("�".equals(segundaLetraApellido)
					|| "�".equals(segundaLetraApellido) ||
					"�".equals(segundaLetraApellido)
					|| "�".equals(segundaLetraApellido) ||
					"�".equals(segundaLetraApellido))
			{
				segundaLetraApellido = "A";
			}

			// Quitamos los acentos de las letras
			if ("�".equals(segundaLetraApellido)
					|| "�".equals(segundaLetraApellido) ||
					"�".equals(segundaLetraApellido)
					|| "�".equals(segundaLetraApellido))
			{
				segundaLetraApellido = "E";
			}

			// Quitamos los acentos de las letras
			if ("�".equals(segundaLetraApellido)
					|| "�".equals(segundaLetraApellido) ||
					"�".equals(segundaLetraApellido)
					|| "�".equals(segundaLetraApellido))
			{
				segundaLetraApellido = "I";
			}

			// Quitamos los acentos de las letras
			if ("�".equals(segundaLetraApellido)
					|| "�".equals(segundaLetraApellido) ||
					"�".equals(segundaLetraApellido)
					|| "�".equals(segundaLetraApellido) ||
					"�".equals(segundaLetraApellido))
			{
				segundaLetraApellido = "O";
			}

			// Quitamos los acentos de las letras
			if ("�".equals(segundaLetraApellido)
					|| "�".equals(segundaLetraApellido) ||
					"�".equals(segundaLetraApellido)
					|| "�".equals(segundaLetraApellido))
			{
				segundaLetraApellido = "U";
			}

			// Quitamos los acentos de las letras
			if ("�".equals(terceraLetraApellido)
					|| "�".equals(terceraLetraApellido) ||
					"�".equals(terceraLetraApellido)
					|| "�".equals(terceraLetraApellido) ||
					"�".equals(terceraLetraApellido))
			{
				terceraLetraApellido = "A";
			}

			// Quitamos los acentos de las letras
			if ("�".equals(terceraLetraApellido)
					|| "�".equals(terceraLetraApellido) ||
					"�".equals(terceraLetraApellido)
					|| "�".equals(terceraLetraApellido))
			{
				terceraLetraApellido = "E";
			}

			// Quitamos los acentos de las letras
			if ("�".equals(terceraLetraApellido)
					|| "�".equals(terceraLetraApellido) ||
					"�".equals(terceraLetraApellido)
					|| "�".equals(terceraLetraApellido))
			{
				terceraLetraApellido = "I";
			}

			// Quitamos los acentos de las letras
			if ("�".equals(terceraLetraApellido)
					|| "�".equals(terceraLetraApellido) ||
					"�".equals(terceraLetraApellido)
					|| "�".equals(terceraLetraApellido) ||
					"�".equals(terceraLetraApellido))
			{
				terceraLetraApellido = "O";
			}

			// Quitamos los acentos de las letras
			if ("�".equals(terceraLetraApellido)
					|| "�".equals(terceraLetraApellido) ||
					"�".equals(terceraLetraApellido)
					|| "�".equals(terceraLetraApellido))
			{
				terceraLetraApellido = "U";
			}

			long terceraLetra = obtenerLetra(primeraLetraApellido);
			long segundaLetra = obtenerLetra(segundaLetraApellido);
			long primeraLetra = obtenerLetra(terceraLetraApellido);
			long cuartaLetra = 0;
			String primeraLetraNIF = NIF.substring(0, 1);

			if (primeraLetraNIF.equalsIgnoreCase("X")
					|| primeraLetraNIF.equalsIgnoreCase("K") 
					|| primeraLetraNIF.equalsIgnoreCase("L")
					|| primeraLetraNIF.equalsIgnoreCase("M")
					|| primeraLetraNIF.equalsIgnoreCase("Y"))
			{
				cuartaLetra = obtenerLetra(primeraLetraNIF);
			}

			long total = primeraLetra + (segundaLetra * _100)
					+ (terceraLetra * _10000) + (cuartaLetra * _1000000);
			boolean nifNumerico = false;
			try {
				@SuppressWarnings("unused")
				long aux = Long.valueOf(NIF).longValue();
				nifNumerico = true;
			} catch (Throwable ex) {
				nifNumerico = false;
			}

			if (Character.isDigit(primeraLetraNIF.charAt(0))) {
				if (!nifNumerico) {
					total = total
							+ Long.valueOf(NIF.substring(0, _8)).longValue();
				} else {
					total = total
							+ Long.valueOf(NIF.substring(0, _7)).longValue();
				}
			} else {
				if (nifNumerico) {
					total = total
							+ Long.valueOf(NIF.substring(1, _9)).longValue();
				} else {
					total = total
							+ Long.valueOf(NIF.substring(1, _8)).longValue();
				}
			}
			String anagramaFiscal = primeraLetraApellido.toUpperCase() +
			segundaLetraApellido.toUpperCase() +
			terceraLetraApellido.toUpperCase() +
			calcularLetra(total);
			anagramaFiscal = anagramaFiscal.replaceAll(" ", "");
			return anagramaFiscal;
		} catch (Throwable e) {
			try {
				return (primerApellido.substring(0, INDEX).toUpperCase());
			} catch (Exception e1) {
				return "";
			}
		}

	}
	
		
	*/
	
	public static String obtenerAnagramaFiscal(String primerApellido, String NIF) {
		try {
			// 1.- Quitamos los espacios en blanco del apellido.
			String apellido = primerApellido.trim();
			Boolean quitarEspacios = true;
			
			// 2.- Miramos si el apellido es un apellido compuesto.
			StringTokenizer strTokenizer = new StringTokenizer(apellido, " ");
			
			String palabra = null;
			
							
			while (strTokenizer.hasMoreTokens()) {
				palabra = strTokenizer.nextToken(); 
				palabra = palabra.toUpperCase();
				if ("DE".equalsIgnoreCase(palabra)
						|| "DEL".equalsIgnoreCase(palabra) ||
						"LA".equalsIgnoreCase(palabra)
						|| "LAS".equalsIgnoreCase(palabra) ||
						"LO".equalsIgnoreCase(palabra)
						|| "LOS".equalsIgnoreCase(palabra) ||
						"EL".equalsIgnoreCase(palabra)
						|| "DON".equalsIgnoreCase(palabra) ||
						"AL".equalsIgnoreCase(palabra))      // incidencia 3540: se descarta AL por ejemplo AL SASHIMI
				{
					// nada
				} else {
					apellido = palabra;
					break;
				}
			}
			
			// 3.- Si el apellido es menor que tres caractres, lo ajustamos a 3
			apellido = BasicText.changeSize(apellido, INT);
			String primeraLetraApellido = apellido.substring(0, 1);
			String segundaLetraApellido = apellido.substring(1, 2);
			String terceraLetraApellido = apellido.substring(2, END_INDEX);

			// Quitamos los posibles car�cteres extra�os que sustituyen a la "�"
			if ("�".equals(primeraLetraApellido)
					|| "#".equals(primeraLetraApellido) ||
					"@".equals(primeraLetraApellido)
					|| "?".equals(primeraLetraApellido) ||
					"�".equals(primeraLetraApellido)
					|| "/".equals(primeraLetraApellido) ||
					"\\".equals(primeraLetraApellido)
					|| "~".equals(primeraLetraApellido))
			{
				primeraLetraApellido = "�";
			}

			if ("�".equals(segundaLetraApellido)
					|| "#".equals(segundaLetraApellido) ||
					"@".equals(segundaLetraApellido)
					|| "?".equals(segundaLetraApellido) ||
					"�".equals(segundaLetraApellido)
					|| "/".equals(segundaLetraApellido) ||
					"\\".equals(segundaLetraApellido)
					|| "~".equals(segundaLetraApellido))
			{
				segundaLetraApellido = "�";
			}

			if ("�".equals(terceraLetraApellido)
					|| "#".equals(terceraLetraApellido) ||
					"@".equals(terceraLetraApellido)
					|| "?".equals(terceraLetraApellido) ||
					"�".equals(terceraLetraApellido)
					|| "/".equals(terceraLetraApellido) ||
					"\\".equals(terceraLetraApellido)
					|| "~".equals(terceraLetraApellido))
			{
				terceraLetraApellido = "�";
			}
			
			
			

			// Quitamos los acentos de las letras
			if ("�".equals(primeraLetraApellido)
					|| "�".equals(primeraLetraApellido) ||
					"�".equals(primeraLetraApellido)
					)
			{
				primeraLetraApellido = "A";
			}

			// Quitamos los acentos de las letras
			if ("�".equals(primeraLetraApellido)
					|| "�".equals(primeraLetraApellido))
			{
				primeraLetraApellido = "E";
			}

			// Quitamos los acentos de las letras
			if ("�".equals(primeraLetraApellido)
					|| "�".equals(primeraLetraApellido))
			{
				primeraLetraApellido = "I";
			}

			// Quitamos los acentos de las letras
			if ("�".equals(primeraLetraApellido)
					|| "�".equals(primeraLetraApellido) ||
						"�".equals(primeraLetraApellido)
					)
			{
				primeraLetraApellido = "O";
			}

			// Quitamos los acentos de las letras
			if ("�".equals(primeraLetraApellido)
					|| "�".equals(primeraLetraApellido))
			{
				primeraLetraApellido = "U";
			}

			
			
			
			// Quitamos los acentos de las letras
			if ("�".equals(segundaLetraApellido)
					|| "�".equals(segundaLetraApellido)  ||
						"�".equals(segundaLetraApellido)
					)
			{
				segundaLetraApellido = "A";
			}

			// Quitamos los acentos de las letras
			if ("�".equals(segundaLetraApellido)
					|| "�".equals(segundaLetraApellido))
			{
				segundaLetraApellido = "E";
			}

			// Quitamos los acentos de las letras
			if ("�".equals(segundaLetraApellido)
					|| "�".equals(segundaLetraApellido))
			{
				segundaLetraApellido = "I";
			}

			// Quitamos los acentos de las letras
			if ("�".equals(segundaLetraApellido)
					|| "�".equals(segundaLetraApellido)  ||
							"�".equals(segundaLetraApellido)
					)
				
			{
				segundaLetraApellido = "O";
			}

			// Quitamos los acentos de las letras
			if ("�".equals(segundaLetraApellido)
					|| "�".equals(segundaLetraApellido))
			{
				segundaLetraApellido = "U";
				
			}

			// Quitamos los acentos de las letras
			if ("�".equals(terceraLetraApellido)
					|| "�".equals(terceraLetraApellido)  ||
					"�".equals(terceraLetraApellido)
					)
			{
				terceraLetraApellido = "A";
			}

			// Quitamos los acentos de las letras
			if ("�".equals(terceraLetraApellido)
					|| "�".equals(terceraLetraApellido))
			{
				terceraLetraApellido = "E";
			}

			// Quitamos los acentos de las letras
			if ("�".equals(terceraLetraApellido)
					|| "�".equals(terceraLetraApellido))
			{
				terceraLetraApellido = "I";
			}

			// Quitamos los acentos de las letras
			if ("�".equals(terceraLetraApellido)
					|| "�".equals(terceraLetraApellido) ||
							"�".equals(terceraLetraApellido)
					)
			{
				terceraLetraApellido = "O";
			}

			// Quitamos los acentos de las letras
			if ("�".equals(terceraLetraApellido)
					|| "�".equals(terceraLetraApellido))
			{
				terceraLetraApellido = "U";
			}
      
			
			// nuevo calculo del anagrama primeraLetraApellido
			if (	 "�".equals(primeraLetraApellido) ||
				
					 "�".equals(primeraLetraApellido) ||
				
					 "�".equals(primeraLetraApellido) ||
					
					 "�".equals(primeraLetraApellido) ||
					
					 "�".equals(primeraLetraApellido) || 
					 
					 "�".equals(primeraLetraApellido) )
			{
				
				primeraLetraApellido="";
				segundaLetraApellido = "";
				terceraLetraApellido="";
			}
			
			// nuevo calculo del anagrama segundaLetraApellido
			if (	 "�".equals(segundaLetraApellido) ||
					
					 "�".equals(segundaLetraApellido) ||
				
					 "�".equals(segundaLetraApellido) || 
				
					 "�".equals(segundaLetraApellido) ||
					
					 "�".equals(segundaLetraApellido) ||
					 
					 "�".equals(segundaLetraApellido))				
			{
				segundaLetraApellido = "";
				terceraLetraApellido = "";
			}
			
			if( "'".equals(segundaLetraApellido) ||
					 "�".equals(segundaLetraApellido)){
				segundaLetraApellido = " ";
				terceraLetraApellido = "";
				quitarEspacios = false;
			}
			
			// nuevo calculo del anagrama terceraLetraApellido
			if ( "�".equals(terceraLetraApellido) ||
				 "�".equals(terceraLetraApellido) || 
				 "�".equals(terceraLetraApellido) || 
				 "�".equals(terceraLetraApellido) ||
				 "�".equals(terceraLetraApellido) ||
				 "�".equals(terceraLetraApellido))
			{
				
				terceraLetraApellido="";
			}
			
			if( "'".equals(segundaLetraApellido) ||
					 "�".equals(segundaLetraApellido)){
				terceraLetraApellido = " ";
				quitarEspacios = false;
			}
			
			// fin nuevo calculo
			
			
			
			long terceraLetra = obtenerLetra(primeraLetraApellido);
			long segundaLetra = obtenerLetra(segundaLetraApellido);
			long primeraLetra = obtenerLetra(terceraLetraApellido);
			long cuartaLetra = 0;
			String primeraLetraNIF = NIF.substring(0, 1);
      
			if (primeraLetraNIF.equalsIgnoreCase("X")
					|| primeraLetraNIF.equalsIgnoreCase("K") 
					|| primeraLetraNIF.equalsIgnoreCase("L")
					|| primeraLetraNIF.equalsIgnoreCase("M")
					|| primeraLetraNIF.equalsIgnoreCase("Y"))
			{
				cuartaLetra = obtenerLetra(primeraLetraNIF);
			}

			long total = primeraLetra + (segundaLetra * _100)
					+ (terceraLetra * _10000) + (cuartaLetra * _1000000);
			boolean nifNumerico = false;
			try {
				@SuppressWarnings("unused")
				long aux = Long.valueOf(NIF).longValue();
				nifNumerico = true;
			} catch (Throwable ex) {
				nifNumerico = false;
			}

			if (Character.isDigit(primeraLetraNIF.charAt(0))) {
				if (!nifNumerico) {
					total = total
							+ Long.valueOf(NIF.substring(0, _8)).longValue();
				} else {
					total = total
							+ Long.valueOf(NIF.substring(0, _7)).longValue();
				}
			} else {
				if (nifNumerico) {
					total = total
							+ Long.valueOf(NIF.substring(1, _9)).longValue();
				} else {
					total = total
							+ Long.valueOf(NIF.substring(1, _8)).longValue();
				}
			}
			String anagramaFiscal = primeraLetraApellido.toUpperCase() +
			segundaLetraApellido.toUpperCase() +
			terceraLetraApellido.toUpperCase() +
			calcularLetra(total);
			if(quitarEspacios){
				anagramaFiscal = anagramaFiscal.replaceAll(" ", "");
			}
			return anagramaFiscal;
		} catch (Throwable e) {
			try {
				return (primerApellido.substring(0, INDEX).toUpperCase());
			} catch (Exception e1) {
				return "";
			}
		}

	}

	
	
	/**
	 * Main de prueba para verificar la correcta resoluci�n del fallo en obtenci�n del anagrama cuando el 
	 * nif es un nie que empieza por 'y'
	 * @param args
	 */
	public static void main(String[] args){
		System.out.println("Anagrama correcto (ABAF): " + Anagrama.obtenerAnagramaFiscal("Abadia", "03468142H"));
		System.out.println("Anagrama correcto (CORL): " + Anagrama.obtenerAnagramaFiscal("Corroto", "01831009W"));
		System.out.println("Anagrama correcto (FERA): " + Anagrama.obtenerAnagramaFiscal("Fernandez", "51384941C"));
		System.out.println("Anagrama correcto (GILX): " + Anagrama.obtenerAnagramaFiscal("Gilsanz", "01185887F"));
		System.out.println("Anagrama correcto (HERN): " + Anagrama.obtenerAnagramaFiscal("De las heras", "51080647Q"));
		System.out.println("El siguiente deber�a ser 'NIRW' para ser correcto (es nie que empieza por 'Y' antes daba 'NIRF')");
		System.out.println("Anagrama actual: " + Anagrama.obtenerAnagramaFiscal("Nirich", "Y0919204T"));
		System.out.println("El siguiente deber�a ser 'CAND' para ser correcto (es nie que empieza por 'Y' antes daba 'CANZ')");
		System.out.println("Anagrama actual: " + Anagrama.obtenerAnagramaFiscal("Canciello", "Y2252098K"));
		System.out.println("El siguiente deber�a ser 'CUAT' para ser correcto (es nie que empieza por 'Y')");
		System.out.println("Anagrama actual: " + Anagrama.obtenerAnagramaFiscal("Cuartas Betancourth", "Y1148233H"));
		System.out.println("El siguiente deber�a ser 'ZHAB' para ser correcto (es nie que empieza por 'X')");
		System.out.println("Anagrama actual: " + Anagrama.obtenerAnagramaFiscal("Zhan", "X5410352Q"));
		System.out.println("El siguiente deber�a ser 'ISTQ' para ser correcto");
		System.out.println("Anagrama actual: " + Anagrama.obtenerAnagramaFiscal("Istrate", "Y1122722Z"));
		System.out.println("El siguiente deber�a ser 'LUPD' para ser correcto");
		System.out.println("Anagrama actual: " + Anagrama.obtenerAnagramaFiscal("Lupsor", "Y1057812X"));
		System.out.println("Anagrama correcto (HASE): " + Anagrama.obtenerAnagramaFiscal("AL HASHIMI", "47286585B"));
		System.out.println("Anagrama correcto (GP): " + Anagrama.obtenerAnagramaFiscal("G�iZA", "11854289C"));
		System.out.println("Anagrama correcto (AGD): " + Anagrama.obtenerAnagramaFiscal("AG�ERA","46843483G"));	
		System.out.println("Anagrama correcto (DIAM): " + Anagrama.obtenerAnagramaFiscal("D�AZ","05411737K"));
		System.out.println("Anagrama correcto (CIA): " + Anagrama.obtenerAnagramaFiscal("C�A","02522575G"));
		System.out.println("Anagrama correcto (GIL): " + Anagrama.obtenerAnagramaFiscal("CID","01108779H"));
		System.out.println("Anagrama correcto DE AI�AGUER,24528913B (AID): " + Anagrama.obtenerAnagramaFiscal("AI�AGUER","24528913B"));
	}
    
	/**
	 * Valida si un anagrama que se le pasa como par�metro es correcto seg�n el apellido y el nif de los par�metros
	 * @param psAnagrama
	 * @param psApellido1
	 * @param psNIF
	 * @return
	 */
    public static boolean validar(String psAnagrama, String psApellido1, String psNIF){

    	if (!NIFValidator.isValidCIF(psNIF) && psNIF.length()>=8 &&
    			psApellido1 != null && !psApellido1.isEmpty() /*&& psApellido1.length()>=3*/ &&
    			psAnagrama!= null){
    		if (psAnagrama.equals(obtenerAnagramaFiscal(psApellido1,psNIF)))
    			return true;
    	}
    	return false;
    }
    
	
}
