package org.gestoresmadrid.oegamImpresion.utilidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Component;

import sun.misc.BASE64Encoder;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Component
public class UtilidadesConversiones implements Serializable {

	private static final long serialVersionUID = 3973907114834287780L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(UtilidadesConversiones.class);

	Map<String, String> provinciaTablatoDGT = new HashMap<String, String>();

	List<String> tipoVehiculo = Arrays.asList("50", "51", "52", "53", "54", "90", "91", "92", "R0", "R1", "R2", "R3", "R4", "R5", "R6", "R7", "R8", "R9", "RA", "RB", "RC", "RD", "RE", "RF", "RH",
			"S0", "S1", "S2", "S3", "S4", "S5", "S6", "S7", "S8", "S9", "SA", "SB", "SC", "SD", "SE", "SF", "SH");

	private static final String TIPO_MATRICULA_ORDINARIA = "ordinaria";
	private static final String TIPO_MATRICULA_TRACTOR = "tractor";
	private static final String TIPO_MATRICULA_CICLOMOTOR = "ciclomotor";

	public UtilidadesConversiones() {
		inicializarTablaProvincias();
	}

	public boolean containsTipoVehiculoDGT(String tipo) {
		return tipoVehiculo.contains(tipo);
	}

	public void inicializarTablaProvincias() {
		provinciaTablatoDGT.put("01", "VI");
		provinciaTablatoDGT.put("02", "AB");
		provinciaTablatoDGT.put("03", "A");
		provinciaTablatoDGT.put("04", "AL");
		provinciaTablatoDGT.put("05", "AV");
		provinciaTablatoDGT.put("06", "BA");
		provinciaTablatoDGT.put("07", "IB");

		provinciaTablatoDGT.put("08", "B");
		provinciaTablatoDGT.put("09", "BU");
		provinciaTablatoDGT.put("10", "CC");
		provinciaTablatoDGT.put("11", "CA");
		provinciaTablatoDGT.put("12", "CS");
		provinciaTablatoDGT.put("13", "CR");
		provinciaTablatoDGT.put("14", "CO");
		provinciaTablatoDGT.put("15", "C");
		provinciaTablatoDGT.put("16", "CU");
		provinciaTablatoDGT.put("17", "GI");

		provinciaTablatoDGT.put("18", "GR");
		provinciaTablatoDGT.put("19", "GU");
		provinciaTablatoDGT.put("20", "SS");
		provinciaTablatoDGT.put("21", "H");
		provinciaTablatoDGT.put("22", "HU");
		provinciaTablatoDGT.put("23", "J");
		provinciaTablatoDGT.put("24", "LE");
		provinciaTablatoDGT.put("25", "L");
		provinciaTablatoDGT.put("26", "LO");
		provinciaTablatoDGT.put("27", "LU");
		provinciaTablatoDGT.put("28", "M");
		provinciaTablatoDGT.put("29", "MA");
		provinciaTablatoDGT.put("30", "MU");
		provinciaTablatoDGT.put("31", "NA");
		provinciaTablatoDGT.put("32", "OU");

		provinciaTablatoDGT.put("33", "O");
		provinciaTablatoDGT.put("34", "P");
		provinciaTablatoDGT.put("35", "GC");
		provinciaTablatoDGT.put("36", "PO");
		provinciaTablatoDGT.put("37", "SA");
		provinciaTablatoDGT.put("38", "TF");
		provinciaTablatoDGT.put("39", "S");
		provinciaTablatoDGT.put("40", "SG");
		provinciaTablatoDGT.put("41", "SE");
		provinciaTablatoDGT.put("42", "SO");
		provinciaTablatoDGT.put("43", "T");
		provinciaTablatoDGT.put("44", "TE");
		provinciaTablatoDGT.put("45", "TO");
		provinciaTablatoDGT.put("46", "V");
		provinciaTablatoDGT.put("47", "VA");
		provinciaTablatoDGT.put("48", "BI");
		provinciaTablatoDGT.put("49", "ZA");
		provinciaTablatoDGT.put("50", "Z");
		provinciaTablatoDGT.put("51", "CE");
		provinciaTablatoDGT.put("52", "ML");
	}

	public String bigDecimalToStringDosDecimales(BigDecimal numero, Integer longitud) {
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

	public String bigDecimalToStringTresDecimales(BigDecimal numero, Integer longitud) {
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

	public String ajustarCamposIne(String campo) {
		String cadena = campo;
		if (null != campo && !campo.equals("")) {
			if (campo.endsWith(" (LA)")) {
				cadena = "LA " + campo.substring(0, campo.length() - 5);
			} else if (campo.endsWith(" (EL)")) {
				cadena = "EL " + campo.substring(0, campo.length() - 5);
			} else if (campo.endsWith(" (LAS)")) {
				cadena = "LAS " + campo.substring(0, campo.length() - 6);
			} else if (campo.endsWith(" (LOS)")) {
				cadena = "LOS " + campo.substring(0, campo.length() - 6);
			} else if (campo.endsWith(" (DE LOS)")) {
				cadena = "DE LOS " + campo.substring(0, campo.length() - 9);
			} else if (campo.endsWith(" (DE LAS)")) {
				cadena = "DE LAS " + campo.substring(0, campo.length() - 9);
			} else if (campo.endsWith(" (DEL)")) {
				cadena = "DEL " + campo.substring(0, campo.length() - 6);
			} else if (campo.endsWith(" (DE LA)")) {
				cadena = "DE LA " + campo.substring(0, campo.length() - 8);
			} else if (campo.endsWith(" (DE)")) {
				cadena = "DE " + campo.substring(0, campo.length() - 5);
			}
			return cadena;
		}
		return campo;
	}

	public String cifrarHMACSHA1(String cadena, String pass) {
		String sNameHashAlgorithm = "HmacSHA1";
		String sLastHMAC = "";

		byte[] cadenaACifrar = cadena.getBytes();
		byte[] abKeyBytes = pass.getBytes();
		SecretKeySpec signingKey = new SecretKeySpec(abKeyBytes, sNameHashAlgorithm);

		try {
			Mac objMac = Mac.getInstance(sNameHashAlgorithm);
			objMac.init(signingKey);
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

	public String getSiglasFromIdProvincia(String idProvincia) {
		if (idProvincia == null)
			return "";
		return provinciaTablatoDGT.get(idProvincia.trim());
	}

	public String cambiaFormatoMatricula(String matricula) {
		String matriculaFormateada = matricula;
		int longMatricula = matricula.length();
		if (longMatricula >= 3) {
			if (Character.isDigit(matricula.charAt(longMatricula - 3))) {
				if (Character.isLetter(matricula.charAt(0)) && Character.isDigit(matricula.charAt(1))) {
					String provincia = matricula.substring(0, 1);
					matriculaFormateada = matricula.replaceFirst(provincia, provincia + " ");
				}
				if (matriculaFormateada.length() == 7 && Character.isDigit(matriculaFormateada.charAt(5)) && Character.isLetter(matriculaFormateada.charAt(6))) {
					matriculaFormateada = matriculaFormateada.substring(0, 6) + " " + matriculaFormateada.substring(6);
				}
				String cifras = matriculaFormateada.substring(2); // Le quitamos los dos primeros caracteres de la provincia
				if (cifras.length() == 7 && Character.isDigit(cifras.charAt(6)) && cifras.charAt(0) == '0') {
					matriculaFormateada = matriculaFormateada.replaceFirst("0", "");
				}
			}
		}
		return matriculaFormateada;
	}

	public String quitarCaracteresExtranios(String texto) {
		texto = texto.replace("º", "");
		texto = texto.replace("ª", "");
		texto = texto.replace("\\", "");
		texto = texto.replace("!", "");
		texto = texto.replace("|", "");
		texto = texto.replace("\"", "");
		texto = texto.replace("·", "");
		texto = texto.replace("#", "");
		texto = texto.replace("$", "");
		texto = texto.replace("~", "");
		texto = texto.replace("%", "");
		texto = texto.replace("&", "");
		texto = texto.replace("/", "");
		texto = texto.replace("(", "");
		texto = texto.replace(")", "");
		texto = texto.replace("=", "");
		texto = texto.replace("'", "");
		texto = texto.replace("?", "");
		texto = texto.replace("¡", "");
		texto = texto.replace("`", "");
		texto = texto.replace("^", "");
		texto = texto.replace("[", "");
		texto = texto.replace("+", "");
		texto = texto.replace("*", "");
		texto = texto.replace("]", "");
		texto = texto.replace("´", "");
		texto = texto.replace("¨", "");
		texto = texto.replace("{", "");
		texto = texto.replace("ç", "");
		texto = texto.replace("}", "");
		texto = texto.replace("<", "");
		texto = texto.replace(">", "");
		texto = texto.replace(",", "");
		texto = texto.replace(";", "");
		texto = texto.replace(".", "");
		texto = texto.replace(":", "");
		texto = texto.replace("-", "");
		texto = texto.replace("_", "");
		return texto;
	}

	public String valorEnumTipoTransferenciaNotEnt(String valorEnum) {
		String valorAntiguo = null;
		if (valorEnum.equalsIgnoreCase("4")) {
			valorAntiguo = "2";
		} else if (valorEnum.equalsIgnoreCase("5")) {
			valorAntiguo = "3";
		}
		if (valorAntiguo == null) {
			return valorEnum;
		}
		return valorAntiguo;
	}

	public String analizarMatriculaVehiculo(String matricula) {
		String trozo1;
		String trozo2;
		String trozo3;
		String tipoMatricula = TIPO_MATRICULA_ORDINARIA;

		if (matricula.length() == 7) {
			trozo1 = matricula.substring(0, 4);
			trozo2 = matricula.substring(4, 7);
			if (comprobarNumero(trozo1) && !comprobarNumero(trozo2)) {
				tipoMatricula = TIPO_MATRICULA_ORDINARIA;
				return tipoMatricula;
			}
		}

		if (matricula.length() == 8) {
			trozo1 = matricula.substring(0, 1);
			trozo2 = matricula.substring(1, 5);
			trozo3 = matricula.substring(5, 8);
			if (!comprobarNumero(trozo1) && trozo1.equals("E") && comprobarNumero(trozo2) && !comprobarNumero(trozo3)) {
				tipoMatricula = TIPO_MATRICULA_TRACTOR;
				return tipoMatricula;
			}

			trozo1 = matricula.substring(0, 2);
			trozo2 = matricula.substring(2, 5);
			trozo3 = matricula.substring(5, 8);
			if (!comprobarNumero(trozo1) && trozo1.substring(0, 1).equals("C") && comprobarNumero(trozo2) && !comprobarNumero(trozo3)) {
				tipoMatricula = TIPO_MATRICULA_CICLOMOTOR;
				return tipoMatricula;
			}
		}

		return tipoMatricula;
	}

	public boolean comprobarNumero(String cadena) {
		try {
			Integer.parseInt(cadena);
		} catch (Exception e) {
			if ("SN".equals(cadena))
				return true;
			else
				return false;
		}
		return true;
	}
}
