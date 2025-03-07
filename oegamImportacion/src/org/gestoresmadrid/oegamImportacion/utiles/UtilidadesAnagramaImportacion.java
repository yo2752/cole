package org.gestoresmadrid.oegamImportacion.utiles;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UtilidadesAnagramaImportacion implements Serializable{

	private static final long serialVersionUID = -5973535514751709601L;

	@Autowired
	UtilidadesNIFValidatorImportacion utilidadesNIFValidator; 
	
	private static final int _100 = 100;
	private static final int _10000 = 10000;
	private static final int _1000000 = 1000000;
	private static final int INDEX = 3;
	private static final int END_INDEX = 3;
	private static final int INT = 3;
	private static Hashtable<String, String> ANAGRAMA_LETRA = null;
	private static String[] LETRAS = null;
	
	public UtilidadesAnagramaImportacion() {
		super();
		cargarAnagrama();
		cargarLetras();
	}

	private void cargarLetras() {
		LETRAS = new String[24];
		LETRAS[0] = "T";
		LETRAS[1] = "R";
		LETRAS[2] = "W";
		LETRAS[3] = "A";
		LETRAS[4] = "G";
		LETRAS[5] = "M";
		LETRAS[6] = "Y";
		LETRAS[7] = "F";
		LETRAS[8] = "P";
		LETRAS[9] = "D";
		LETRAS[10] = "X";
		LETRAS[11] = "B";
		LETRAS[12] = "N";
		LETRAS[13] = "J";
		LETRAS[14] = "Z";
		LETRAS[15] = "S";
		LETRAS[16] = "Q";
		LETRAS[17] = "V";
		LETRAS[18] = "H";
		LETRAS[19] = "L";
		LETRAS[20] = "C";
		LETRAS[21] = "K";
		LETRAS[22] = "E";
		LETRAS[23] = "T";
	}

	private void cargarAnagrama() {
		ANAGRAMA_LETRA = new Hashtable<String, String>(_100);
		ANAGRAMA_LETRA.put("A", "1");
		ANAGRAMA_LETRA.put("Á", "1");
		ANAGRAMA_LETRA.put("Ä", "1");
		ANAGRAMA_LETRA.put("B", "2");
		ANAGRAMA_LETRA.put("C", "3");
		ANAGRAMA_LETRA.put("Ç", "3");
		ANAGRAMA_LETRA.put("D", "4");
		ANAGRAMA_LETRA.put("E", "5");
		ANAGRAMA_LETRA.put("É", "5");
		ANAGRAMA_LETRA.put("Ë", "5");
		ANAGRAMA_LETRA.put("F", "6");
		ANAGRAMA_LETRA.put("G", "7");
		ANAGRAMA_LETRA.put("H", "8");
		ANAGRAMA_LETRA.put("I", "9");
		ANAGRAMA_LETRA.put("Í", "9");
		ANAGRAMA_LETRA.put("Ï", "9");
		ANAGRAMA_LETRA.put("J", "10");
		ANAGRAMA_LETRA.put("K", "11");
		ANAGRAMA_LETRA.put("L", "12");
		ANAGRAMA_LETRA.put("M", "13");
		ANAGRAMA_LETRA.put("N", "14");
		ANAGRAMA_LETRA.put("Ñ", "15");
		ANAGRAMA_LETRA.put("#", "15");
		ANAGRAMA_LETRA.put("@", "15");
		ANAGRAMA_LETRA.put("?", "15");
		ANAGRAMA_LETRA.put("¿", "15");
		ANAGRAMA_LETRA.put("/", "15");
		ANAGRAMA_LETRA.put("\\", "15");
		ANAGRAMA_LETRA.put("~", "15");
		ANAGRAMA_LETRA.put("O", "16");
		ANAGRAMA_LETRA.put("Ó", "16");
		ANAGRAMA_LETRA.put("Ö", "16");
		ANAGRAMA_LETRA.put("P", "17");
		ANAGRAMA_LETRA.put("Q", "18");
		ANAGRAMA_LETRA.put("R", "19");
		ANAGRAMA_LETRA.put("S", "20");
		ANAGRAMA_LETRA.put("T", "21");
		ANAGRAMA_LETRA.put("U", "22");
		ANAGRAMA_LETRA.put("Ú", "22");
		ANAGRAMA_LETRA.put("Ü", "22");
		ANAGRAMA_LETRA.put("V", "23");
		ANAGRAMA_LETRA.put("W", "24");
		ANAGRAMA_LETRA.put("X", "25");
		ANAGRAMA_LETRA.put("Y", "26");
		ANAGRAMA_LETRA.put("Z", "27");
		ANAGRAMA_LETRA.put("a", "1");
		ANAGRAMA_LETRA.put("á", "1");
		ANAGRAMA_LETRA.put("ä", "1");
		ANAGRAMA_LETRA.put("ª", "1");
		ANAGRAMA_LETRA.put("b", "2");
		ANAGRAMA_LETRA.put("c", "3");
		ANAGRAMA_LETRA.put("ç", "3");
		ANAGRAMA_LETRA.put("d", "4");
		ANAGRAMA_LETRA.put("e", "5");
		ANAGRAMA_LETRA.put("é", "5");
		ANAGRAMA_LETRA.put("ë", "5");
		ANAGRAMA_LETRA.put("f", "6");
		ANAGRAMA_LETRA.put("g", "7");
		ANAGRAMA_LETRA.put("h", "8");
		ANAGRAMA_LETRA.put("i", "9");
		ANAGRAMA_LETRA.put("í", "9");
		ANAGRAMA_LETRA.put("ï", "9");
		ANAGRAMA_LETRA.put("j", "10");
		ANAGRAMA_LETRA.put("k", "11");
		ANAGRAMA_LETRA.put("l", "12");
		ANAGRAMA_LETRA.put("m", "13");
		ANAGRAMA_LETRA.put("n", "14");
		ANAGRAMA_LETRA.put("ñ", "15");
		ANAGRAMA_LETRA.put("o", "16");
		ANAGRAMA_LETRA.put("ó", "16");
		ANAGRAMA_LETRA.put("ö", "16");
		ANAGRAMA_LETRA.put("º", "16");
		ANAGRAMA_LETRA.put("p", "17");
		ANAGRAMA_LETRA.put("q", "18");
		ANAGRAMA_LETRA.put("r", "19");
		ANAGRAMA_LETRA.put("s", "20");
		ANAGRAMA_LETRA.put("t", "21");
		ANAGRAMA_LETRA.put("u", "22");
		ANAGRAMA_LETRA.put("ú", "22");
		ANAGRAMA_LETRA.put("ü", "22");
		ANAGRAMA_LETRA.put("v", "23");
		ANAGRAMA_LETRA.put("w", "24");
		ANAGRAMA_LETRA.put("x", "25");
		ANAGRAMA_LETRA.put("y", "26");
		ANAGRAMA_LETRA.put("z", "27");
	}
	
	public String calcularLetra(long numero) {
		long numeroSegundo = numero / 23;
		long numeroTercero = numeroSegundo * 23;
		int letra = (new Long(numero)).intValue() - (new Long(numeroTercero)).intValue();
		return LETRAS[letra];
	}

	public long obtenerLetra(String letra) {
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
	public String obtenerAnagramaFiscal(String primerApellido, String NIF) {
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
			// Quitamos los posibles carácteres extraños que sustituyen a la "ñ"
			if ("ñ".equals(primeraLetraApellido)
					|| "#".equals(primeraLetraApellido) ||
					"@".equals(primeraLetraApellido)
					|| "?".equals(primeraLetraApellido) ||
					"¿".equals(primeraLetraApellido)
					|| "/".equals(primeraLetraApellido) ||
					"\\".equals(primeraLetraApellido)
					|| "~".equals(primeraLetraApellido))
			{
				primeraLetraApellido = "Ñ";
			}
			if ("ñ".equals(segundaLetraApellido)
					|| "#".equals(segundaLetraApellido) ||
					"@".equals(segundaLetraApellido)
					|| "?".equals(segundaLetraApellido) ||
					"¿".equals(segundaLetraApellido)
					|| "/".equals(segundaLetraApellido) ||
					"\\".equals(segundaLetraApellido)
					|| "~".equals(segundaLetraApellido))
			{
				segundaLetraApellido = "Ñ";
			}
			if ("ñ".equals(terceraLetraApellido)
					|| "#".equals(terceraLetraApellido) ||
					"@".equals(terceraLetraApellido)
					|| "?".equals(terceraLetraApellido) ||
					"¿".equals(terceraLetraApellido)
					|| "/".equals(terceraLetraApellido) ||
					"\\".equals(terceraLetraApellido)
					|| "~".equals(terceraLetraApellido))
			{
				terceraLetraApellido = "Ñ";
			}
			// Quitamos los acentos de las letras
			if ("á".equals(primeraLetraApellido)
					|| "Á".equals(primeraLetraApellido) ||
					"ª".equals(primeraLetraApellido)
					)
			{
				primeraLetraApellido = "A";
			}
			// Quitamos los acentos de las letras
			if ("é".equals(primeraLetraApellido)
					|| "É".equals(primeraLetraApellido))
			{
				primeraLetraApellido = "E";
			}
			// Quitamos los acentos de las letras
			if ("í".equals(primeraLetraApellido)
					|| "Í".equals(primeraLetraApellido))
			{
				primeraLetraApellido = "I";
			}
			// Quitamos los acentos de las letras
			if ("ó".equals(primeraLetraApellido)
					|| "Ó".equals(primeraLetraApellido) ||
						"º".equals(primeraLetraApellido)
					)
			{
				primeraLetraApellido = "O";
			}
			// Quitamos los acentos de las letras
			if ("ú".equals(primeraLetraApellido)
					|| "Ú".equals(primeraLetraApellido))
			{
				primeraLetraApellido = "U";
			}
			// Quitamos los acentos de las letras
			if ("á".equals(segundaLetraApellido)
					|| "Á".equals(segundaLetraApellido)  ||
						"ª".equals(segundaLetraApellido)
					)
			{
				segundaLetraApellido = "A";
			}
			// Quitamos los acentos de las letras
			if ("é".equals(segundaLetraApellido)
					|| "É".equals(segundaLetraApellido))
			{
				segundaLetraApellido = "E";
			}
			// Quitamos los acentos de las letras
			if ("í".equals(segundaLetraApellido)
					|| "Í".equals(segundaLetraApellido))
			{
				segundaLetraApellido = "I";
			}
			// Quitamos los acentos de las letras
			if ("ó".equals(segundaLetraApellido)
					|| "Ó".equals(segundaLetraApellido)  ||
							"º".equals(segundaLetraApellido)
					)
				
			{
				segundaLetraApellido = "O";
			}
			// Quitamos los acentos de las letras
			if ("ú".equals(segundaLetraApellido)
					|| "Ú".equals(segundaLetraApellido))
			{
				segundaLetraApellido = "U";
				
			}
			// Quitamos los acentos de las letras
			if ("á".equals(terceraLetraApellido)
					|| "Á".equals(terceraLetraApellido)  ||
					"ª".equals(terceraLetraApellido)
					)
			{
				terceraLetraApellido = "A";
			}
			// Quitamos los acentos de las letras
			if ("é".equals(terceraLetraApellido)
					|| "É".equals(terceraLetraApellido))
			{
				terceraLetraApellido = "E";
			}
			// Quitamos los acentos de las letras
			if ("í".equals(terceraLetraApellido)
					|| "Í".equals(terceraLetraApellido))
			{
				terceraLetraApellido = "I";
			}
			// Quitamos los acentos de las letras
			if ("ó".equals(terceraLetraApellido)
					|| "Ó".equals(terceraLetraApellido) ||
							"º".equals(terceraLetraApellido)
					)
			{
				terceraLetraApellido = "O";
			}
			// Quitamos los acentos de las letras
			if ("ú".equals(terceraLetraApellido)
					|| "Ú".equals(terceraLetraApellido))
			{
				terceraLetraApellido = "U";
			}
			// nuevo calculo del anagrama primeraLetraApellido
			if (	 "Ä".equals(primeraLetraApellido) ||
				
					 "Ë".equals(primeraLetraApellido) ||
				
					 "Ï".equals(primeraLetraApellido) ||
					
					 "Ö".equals(primeraLetraApellido) ||
					
					 "Ü".equals(primeraLetraApellido) || 
					 
					 "Ç".equals(primeraLetraApellido) )
			{
				
				primeraLetraApellido="";
				segundaLetraApellido = "";
				terceraLetraApellido="";
			}
			// nuevo calculo del anagrama segundaLetraApellido
			if (	 "Ä".equals(segundaLetraApellido) ||
					
					 "Ë".equals(segundaLetraApellido) ||
				
					 "Ï".equals(segundaLetraApellido) || 
				
					 "Ö".equals(segundaLetraApellido) ||
					
					 "Ü".equals(segundaLetraApellido) ||
					 
					 "Ç".equals(segundaLetraApellido))				
			{
				segundaLetraApellido = "";
				terceraLetraApellido = "";
			}
			if( "'".equals(segundaLetraApellido) ||
					 "´".equals(segundaLetraApellido)){
				segundaLetraApellido = " ";
				terceraLetraApellido = "";
				quitarEspacios = false;
			}
			// nuevo calculo del anagrama terceraLetraApellido
			if ( "Ä".equals(terceraLetraApellido) ||
				 "Ë".equals(terceraLetraApellido) || 
				 "Ï".equals(terceraLetraApellido) || 
				 "Ö".equals(terceraLetraApellido) ||
				 "Ü".equals(terceraLetraApellido) ||
				 "Ç".equals(terceraLetraApellido))
			{
				
				terceraLetraApellido="";
			}
			if( "'".equals(segundaLetraApellido) ||
					 "´".equals(segundaLetraApellido)){
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
							+ Long.valueOf(NIF.substring(0, 8)).longValue();
				} else {
					total = total
							+ Long.valueOf(NIF.substring(0, 7)).longValue();
				}
			} else {
				if (nifNumerico) {
					total = total
							+ Long.valueOf(NIF.substring(1, 9)).longValue();
				} else {
					total = total
							+ Long.valueOf(NIF.substring(1, 8)).longValue();
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
	 * Valida si un anagrama que se le pasa como parámetro es correcto según el apellido y el nif de los parámetros
	 * @param psAnagrama
	 * @param psApellido1
	 * @param psNIF
	 * @return
	 */
    public Boolean validar(String psAnagrama, String psApellido1, String psNIF){

    	if (!utilidadesNIFValidator.isValidCIF(psNIF) && psNIF.length()>=8 &&
    			psApellido1 != null && !psApellido1.isEmpty() /*&& psApellido1.length()>=3*/ &&
    			psAnagrama!= null){
    		if (psAnagrama.equals(obtenerAnagramaFiscal(psApellido1,psNIF)))
    			return Boolean.TRUE;
    	}
    	return Boolean.FALSE;
    }
	
}
