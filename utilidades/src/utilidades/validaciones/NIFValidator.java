package utilidades.validaciones;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NIFValidator {
	public static final int NO_VALIDO = -1;
	public static final int ES_DNI = 0;
	public static final int ES_CIF = 1;
	public static final int ES_NIE = 2;
	public static final int ES_MENOR_DE_14 = 3;
	public static final int ES_ESPANOL_NO_RESIDENTE = 4;
	public static final int ES_EXTRANJERO_SIN_NIE = 5;
	private static final String[] tablaLetras = { "T", "R", "W", "A", "G", "M", "Y", "F", "P", "D", "X", "B", "N", "J", "Z", "S", "Q", "V", "H", "L", "C", "K", "E" };
	private static final String ORIGEN_NIF_ESPA = "NIF Español";
	private static final String ORIGEN_NIF_MENOR14 = "NIF Menor de 14 años";
	private static final String ORIGEN_NIF_ESPA_NO_RES = "NIF Español No Residente";
	private static final String ORIGEN_NIF_EXT_CON_NIE = "NIF Extranjero con NIE";
	private static final String ORIGEN_NIF_EXT_SIN_NIE = "NIF Extranjero sin NIE";
	private static final String ORIGEN_CIF_NACIONAL = "CIF Nacional";
	private static final String ORIGEN_CIF_EXT = "CIF Extranjero";
	private static final String ORIGEN_CIF_ADM_EST = "CIF Organismo Administracion Estado";
	private static final String ORIGEN_CIF_ADM_AUT = "CIF Organismo Adiministracion Local o Autonómica";
	private static final String ORIGEN_CIF_FIC = "CIF Ficticio";
	private static final String ORIGEN_CIF_No_RES = "CIF No Residente";
	private static String _origen = null;
	private static int _type;
	public static final int FISICA = 0;
	public static final int JURIDICA_PUBLICA = 1;
	public static final int JURIDICA_PRIVADA = 2;

	public static boolean isValidNIF(String nif) {
		return isValidDNI(nif) || isValidCIF(nif) || isValidNIE(nif) || isValidMenor14(nif) || isValidEspanolNoResidente(nif) || isValidExtranjeroSinNIE(nif);
	}

	public static int isValidDniNieCif(String nif) {
		if (isValidDNI(nif) || isValidNIE(nif) || isValidMenor14(nif)) {
			return FISICA;
		} else if (isValidCIF(nif)) {
			String letraCIF = nif.substring(0, 1);
			String numeroCIF = nif.substring(1, nif.length() - 1);
			String controlCIF = nif.substring(nif.length() - 1, nif.length());
			_isValidCIFNoNacional(letraCIF, numeroCIF, controlCIF);
			if (_origen.equals(ORIGEN_CIF_ADM_AUT) || _origen.equals(ORIGEN_CIF_ADM_EST)) {
				return JURIDICA_PUBLICA;
			} else {
				return JURIDICA_PRIVADA;
			}
		}
		return -1;
	}

	public static boolean isValidDNI(String dni) {
		if ((longitudOK(dni)) && (_isNumber(dni.substring(0, 8)))) {
			int numero = Integer.parseInt(dni.substring(0, 8));
			int indiceTabla = numero % 23;
			boolean ret = tablaLetras[indiceTabla].endsWith(dni.substring(8, 9));
			if (ret) {
				_origen = ORIGEN_NIF_ESPA;
				_type = ES_DNI;
			}
			return ret;
		}
		return false;
	}

	public static boolean isValidCIF(String cif) {
		boolean esCIFValido = false;
		String letraCIF = null;
		String numeroCIF = null;
		String controlCIF = null;

		if (!longitudOK(cif)) {
			return false;
		}

		letraCIF = cif.substring(0, 1);
		numeroCIF = cif.substring(1, cif.length() - 1);
		controlCIF = cif.substring(cif.length() - 1, cif.length());
		esCIFValido = _isValidCIFNacional(letraCIF, numeroCIF, controlCIF);
		if (esCIFValido) {
			_origen = ORIGEN_CIF_NACIONAL;
			_type = ES_CIF;
		} else {
			esCIFValido = _isValidCIFNoNacional(letraCIF, numeroCIF, controlCIF);
		}
		return esCIFValido;
	}

	public static boolean isValidNIE(String nie) {
		String numeroNIE = null;
		String letraNIE = null;
		boolean esNIEValido = false;

		if (!longitudOK(nie)) {
			return false;
		}

		numeroNIE = nie.substring(0, nie.length() - 1);
		char primerCaracterNumeroNIE = numeroNIE.charAt(0);
		String restoNumeroNIE = numeroNIE.substring(1);
		letraNIE = nie.substring(nie.length() - 1);
		if ((Character.isLetterOrDigit(primerCaracterNumeroNIE)) && (_isNumber(restoNumeroNIE)) && (Character.isLetter(letraNIE.charAt(0)))) {
			if (primerCaracterNumeroNIE == 'X') {
				esNIEValido = _compruebaLetraNIF(restoNumeroNIE, letraNIE);
			} else if (primerCaracterNumeroNIE == 'Y') {
				esNIEValido = _compruebaLetraNIF("1" + restoNumeroNIE, letraNIE);
			} else if (primerCaracterNumeroNIE == 'Z') {
				esNIEValido = _compruebaLetraNIF("2" + restoNumeroNIE, letraNIE);
			} else if (primerCaracterNumeroNIE == 'L') {
				// esNIEValido = _compruebaLetraNIF("2" + restoNumeroNIE, letraNIE);
				esNIEValido = true;
			}
			if (esNIEValido) {
				_origen = ORIGEN_NIF_EXT_CON_NIE;
				_type = ES_NIE;
			}
		}
		return esNIEValido;
	}
	
	public static boolean isValidMenor14(String nif) {
		String numeroNIF = null;
		String letraNIF = null;
		boolean esNIFValido = false;

		if (!longitudOK(nif)) {
			return false;
		}

		numeroNIF = nif.substring(0, nif.length() - 1);
		char primerCaracterNumeroNIF = numeroNIF.charAt(0);
		String restoNumeroNIF = numeroNIF.substring(1);
		letraNIF = nif.substring(nif.length() - 1);
		if ((Character.isLetterOrDigit(primerCaracterNumeroNIF)) && (_isNumber(restoNumeroNIF)) && (Character.isLetter(letraNIF.charAt(0))) && (primerCaracterNumeroNIF == 'K')) {
			esNIFValido = _compruebaLetraNIF(restoNumeroNIF, letraNIF);
			if (esNIFValido) {
				_origen = ORIGEN_NIF_MENOR14;
				_type = ES_MENOR_DE_14;
			}
			return esNIFValido;
		}

		return esNIFValido;
	}

	public static boolean isValidEspanolNoResidente(String nif) {
		String numeroNIF = null;
		String letraNIF = null;
		boolean esNIFValido = false;

		if (!longitudOK(nif)) {
			return false;
		}

		numeroNIF = nif.substring(0, nif.length() - 1);
		char primerCaracterNumeroNIF = numeroNIF.charAt(0);
		String restoNumeroNIF = numeroNIF.substring(1);
		letraNIF = nif.substring(nif.length() - 1);
		if ((Character.isLetterOrDigit(primerCaracterNumeroNIF)) && (_isNumber(restoNumeroNIF)) && (Character.isLetter(letraNIF.charAt(0))) && (primerCaracterNumeroNIF == 'L')) {
			esNIFValido = _compruebaLetraNIF(restoNumeroNIF, letraNIF);
			if (esNIFValido) {
				_origen = ORIGEN_NIF_ESPA_NO_RES;
				_type = ES_ESPANOL_NO_RESIDENTE;
			}
			return esNIFValido;
		}

		return esNIFValido;
	}

	public static boolean isValidExtranjeroSinNIE(String nif) {
		String numeroNIF = null;
		String letraNIF = null;
		boolean esNIFValido = false;

		if (!longitudOK(nif)) {
			return false;
		}

		numeroNIF = nif.substring(0, nif.length() - 1);
		char primerCaracterNumeroNIF = numeroNIF.charAt(0);
		String restoNumeroNIF = numeroNIF.substring(1);
		letraNIF = nif.substring(nif.length() - 1);
		if ((Character.isLetterOrDigit(primerCaracterNumeroNIF)) && (_isNumber(restoNumeroNIF)) && (Character.isLetter(letraNIF.charAt(0))) && (primerCaracterNumeroNIF == 'M')) {
			esNIFValido = _compruebaLetraNIF(restoNumeroNIF, letraNIF);
			if (esNIFValido) {
				_origen = ORIGEN_NIF_EXT_SIN_NIE;
				_type = ES_EXTRANJERO_SIN_NIE;
			}
			return esNIFValido;
		}

		return esNIFValido;
	}

	public static String getLetter(String str) {
		try {
			if ((str.length() == 8) || (str.length() == 7)) {
				int numero = Integer.parseInt(str);
				int indiceTabla = numero % 23;
				return tablaLetras[indiceTabla];
			}
			return null;
		} catch (Exception ex) {}
		return null;
	}

	public static int getType(String nif) {
		int i = -1;
		Object o = new Object();
		synchronized (o) {
			if (isValidNIF(nif)) {
				i = _type;
			}
		}
		return i;
	}

	public static String getOrigen(String nif) {
		String s = null;
		Object o = new Object();
		synchronized (o) {
			if (isValidNIF(nif)) {
				s = _origen;
			}
		}
		return s;
	}

	private static boolean longitudOK(String nif) {
		return nif.length() == 9;
	}

	private static boolean _isValidCIFNacional(String letraCIF, String numeroCIF, String controlCIF) {
		boolean resultadoComprobacion = false;
		boolean esCIFNacional = true;
		char letra = letraCIF.charAt(0);
		char digitoControl = controlCIF.charAt(0);

		if (!Character.isDigit(digitoControl)) {
			resultadoComprobacion = false;
			return resultadoComprobacion;
		}

		if (((letra >= 'A') && (letra <= 'J')) || (((letra == 'U') || (letra == 'V')) && (controlCIF.equals(_dameControlCIF(numeroCIF, esCIFNacional)))))
			resultadoComprobacion = true;
		else {
			resultadoComprobacion = false;
		}

		return resultadoComprobacion;
	}

	private static boolean _isValidCIFNoNacional(String letraCIF, String numeroCIF, String controlCIF) {
		boolean resultadoComprobacion = false;
		boolean esCIFNacional = false;
		char letra = letraCIF.charAt(0);

		if (controlCIF.equals(_dameControlCIF(numeroCIF, esCIFNacional))) {
			if ((letra >= 'A') && (letra <= 'J')) {
				_origen = ORIGEN_CIF_EXT;
				resultadoComprobacion = true;
			} else if (letra == 'S') {
				_origen = ORIGEN_CIF_ADM_EST;
				resultadoComprobacion = true;
			} else if ((letra == 'P') || (letra == 'Q') || (letra == 'R')) {
				_origen = ORIGEN_CIF_ADM_AUT;
				resultadoComprobacion = true;
			} else if (letra == 'U') {
				_origen = ORIGEN_CIF_FIC;
				resultadoComprobacion = true;
			} else if ((letra == 'N') || (letra == 'W')) {
				_origen = ORIGEN_CIF_No_RES;
				resultadoComprobacion = true;
			}

		} else {
			resultadoComprobacion = false;
		}
		if (resultadoComprobacion) {
			_type = ES_CIF;
		}
		return resultadoComprobacion;
	}

	private static String _dameControlCIF(String numeroCIF, boolean esCIFNacional) {
		String codigoControlResultante = null;
		int codigoControl;
		try {
			int numero1 = Integer.parseInt(numeroCIF.substring(0, 1)) * 2;
			int numero2 = Integer.parseInt(numeroCIF.substring(1, 2));
			int numero3 = Integer.parseInt(numeroCIF.substring(2, 3)) * 2;
			int numero4 = Integer.parseInt(numeroCIF.substring(3, 4));
			int numero5 = Integer.parseInt(numeroCIF.substring(4, 5)) * 2;
			int numero6 = Integer.parseInt(numeroCIF.substring(5, 6));
			int numero7 = Integer.parseInt(numeroCIF.substring(6)) * 2;
			int numero1transformado = _transformaAMenorDe10(numero1);
			int numero3transformado = _transformaAMenorDe10(numero3);
			int numero5transformado = _transformaAMenorDe10(numero5);
			int numero7transformado = _transformaAMenorDe10(numero7);
			int sumaTotal = numero1transformado + numero2 + numero3transformado + numero4 + numero5transformado + numero6 + numero7transformado;
			int moduloResultadoTotal = sumaTotal % 10;
			codigoControl = 10 - moduloResultadoTotal;
		} catch (NumberFormatException nfe) {
			return codigoControlResultante;
		}
		if (codigoControl == 10)
			codigoControl = 0;
		if (esCIFNacional)
			codigoControlResultante = String.valueOf(codigoControl);
		else
			switch (codigoControl) {
				case 1: // '\001'
					codigoControlResultante = "A";
					break;

				case 2: // '\002'
					codigoControlResultante = "B";
					break;

				case 3: // '\003'
					codigoControlResultante = "C";
					break;

				case 4: // '\004'
					codigoControlResultante = "D";
					break;

				case 5: // '\005'
					codigoControlResultante = "E";
					break;

				case 6: // '\006'
					codigoControlResultante = "F";
					break;

				case 7: // '\007'
					codigoControlResultante = "G";
					break;

				case 8: // '\b'
					codigoControlResultante = "H";
					break;

				case 9: // '\t'
					codigoControlResultante = "I";
					break;

				case 0: // '\0'
					codigoControlResultante = "J";
					break;

				default:
					codigoControlResultante = "*";
					break;
			}
		return codigoControlResultante;
	}

	private static int _transformaAMenorDe10(int numero) {
		int numeroTransformado = 0;
		if (numero < 10) {
			return numero;
		}
		numeroTransformado = numero % 10 + numero / 10;

		return numeroTransformado;
	}

	private static boolean _isNumber(String str) {
		try {
			Long.parseLong(str);
			return true;
		} catch (Exception e) {}
		return false;
	}

	private static boolean _compruebaLetraNIF(String numeroNIF, String letraNIF) {
		return letraNIF.equalsIgnoreCase(getLetter(numeroNIF));
	}

	public static boolean isNifNie(String nif) {
		boolean fisica = false;
		if (nif != null && !nif.isEmpty()) {
			nif = nif.toUpperCase();
			// si es NIE, eliminar la x,y,z inicial para tratarlo como nif
			if (nif.startsWith("X") || nif.startsWith("Y") || nif.startsWith("Z")) {
				nif = nif.substring(1);
			}

			Pattern nifPattern = Pattern.compile("(\\d{1,8})([TRWAGMYFPDXBNJZSQVHLCKE])");
			Matcher m = nifPattern.matcher(nif);
			if (m.matches()) {
				String letra = m.group(2);
				// Extraer letra del NIF
				String letras = "TRWAGMYFPDXBNJZSQVHLCKE";
				int dni = Integer.parseInt(m.group(1));
				dni = dni % 23;
				String reference = letras.substring(dni, dni + 1);

				if (reference.equals(letra)) {
					fisica = true;
				}
			}

		}
		return fisica;
	}

	public static void main(String[] args) {
		String[] pruebas = { "B11854072", "00000000T", "11111111H", "677545cx", "22222222k", "fghgyyasd", "b82917220", "X0347769D", "00347769D", "0347769D", "347769D", "K0347769D", "L0347769D",
				"M0347769D", "M03477691" };
		for (int i = 0; i < pruebas.length; i++) {
			String cifNif = pruebas[i];
			System.err.println("###INICIO PRUEBA validate NIF " + cifNif + "###");
			boolean isValidNIF = isValidNIF(cifNif);
			System.err.println("isValidNIF=" + isValidNIF);
			boolean isValidDNI = isValidDNI(cifNif);
			System.err.println("isValidDNI=" + isValidDNI);
			boolean isValidCIF = isValidCIF(cifNif);
			System.err.println("isValidCIF=" + isValidCIF);
			boolean isValidNIE = isValidNIE(cifNif);
			System.err.println("isValidNIE=" + isValidNIE);
			boolean isValidMenor14 = isValidMenor14(cifNif);
			System.err.println("isValidMenor14=" + isValidMenor14);
			boolean isValidEspanolNoResidente = isValidEspanolNoResidente(cifNif);
			System.err.println("isValidEspanolNoResidente=" + isValidEspanolNoResidente);
			boolean isValidExtranjeroSinNIE = isValidExtranjeroSinNIE(cifNif);
			System.err.println("isValidExtranjeroSinNIE=" + isValidExtranjeroSinNIE);
			int type = getType(cifNif);
			System.err.println("type=" + type);
			String origen = getOrigen(cifNif);
			System.err.println("origen=" + origen);
			System.err.println("###FIN PRUEBA validate NIF " + cifNif + "###");
		}
	}
	
	public static BigDecimal validarNif(String nif) {
		int resultado = isValidDniNieCif(nif);
		if (resultado == FISICA) {
			return new BigDecimal("1");
		}else if(resultado > 0) {
			return new BigDecimal("2");
		}else {
			return new BigDecimal("-1");
		}
	}

}