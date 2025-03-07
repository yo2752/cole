package utilidades.validaciones;

import java.math.BigInteger;

public class UtilesCuentaBancaria {

	private UtilesCuentaBancaria() {
		super();
	}

	/**
	 * Comprueba si una cuenta bancaria es correcta
	 * @param entidad
	 * @param oficina
	 * @param dc
	 * @param numeroCuenta
	 * @return
	 */
	public static boolean esValidoCCC(String entidad, String oficina, String dc, String numeroCuenta) {
		return dc != null && dc.equals(calcularDC(entidad, oficina, numeroCuenta));
	}

	/**
	 * Obtiene el digito control asociado a un numero de cuenta
	 * @param entidad
	 * @param oficina
	 * @param numeroCuenta
	 * @return
	 */
	public static String calcularDC(String entidad, String oficina, String numeroCuenta) {
		String dc = null;
		if (entidad != null && entidad.length() == 4 && oficina != null && oficina.length() == 4 && numeroCuenta != null && numeroCuenta.length() == 10) {

			int dc1 = 0;

			dc1 = dc1 + Integer.parseInt(entidad.substring(0, 1)) * 4;
			dc1 = dc1 + Integer.parseInt(entidad.substring(1, 2)) * 8;
			dc1 = dc1 + Integer.parseInt(entidad.substring(2, 3)) * 5;
			dc1 = dc1 + Integer.parseInt(entidad.substring(3, 4)) * 10;
			dc1 = dc1 + Integer.parseInt(oficina.substring(0, 1)) * 9;
			dc1 = dc1 + Integer.parseInt(oficina.substring(1, 2)) * 7;
			dc1 = dc1 + Integer.parseInt(oficina.substring(2, 3)) * 3;
			dc1 = dc1 + Integer.parseInt(oficina.substring(3, 4)) * 6;

			dc1 = dc1 % 11;
			dc1 = 11 - dc1;
			if (dc1 == 10) {
				dc1 = 1;
			}
			if (dc1 == 11) {
				dc1 = 0;
			}

			int dc2 = 0;

			dc2 = dc2 + Integer.parseInt(numeroCuenta.substring(0, 1)) * 1;
			dc2 = dc2 + Integer.parseInt(numeroCuenta.substring(1, 2)) * 2;
			dc2 = dc2 + Integer.parseInt(numeroCuenta.substring(2, 3)) * 4;
			dc2 = dc2 + Integer.parseInt(numeroCuenta.substring(3, 4)) * 8;
			dc2 = dc2 + Integer.parseInt(numeroCuenta.substring(4, 5)) * 5;
			dc2 = dc2 + Integer.parseInt(numeroCuenta.substring(5, 6)) * 10;
			dc2 = dc2 + Integer.parseInt(numeroCuenta.substring(6, 7)) * 9;
			dc2 = dc2 + Integer.parseInt(numeroCuenta.substring(7, 8)) * 7;
			dc2 = dc2 + Integer.parseInt(numeroCuenta.substring(8, 9)) * 3;
			dc2 = dc2 + Integer.parseInt(numeroCuenta.substring(9, 10)) * 6;

			dc2 = dc2 % 11;
			dc2 = 11 - dc2;
			if (dc2 == 10) {
				dc2 = 1;
			}
			if (dc2 == 11) {
				dc2 = 0;
			}
			dc = String.valueOf(dc1) + String.valueOf(dc2);
		}

		return dc;
	}

	/**
	 * Comprueba si una cuenta IBAN es correcta
	 * @param codigoPais
	 * @param dcIban
	 * @param entidad
	 * @param oficina
	 * @param dc
	 * @param numeroCuenta
	 * @return
	 */
	public static boolean esValidoIBAN(String codigoPais, String dcIban, String entidad, String oficina, String dc, String numeroCuenta) {
		if (dcIban != null && esValidoCCC(entidad, oficina, dc, numeroCuenta)) {
			return dcIban.equals(calculaDcIban(codigoPais, entidad, oficina, dc, numeroCuenta));
		} else {
			return false;
		}
	}

	/**
	 * Obtiene el digito control asociado a una cuenta IBAN
	 * @param codigoPais
	 * @param entidad
	 * @param oficina
	 * @param dc
	 * @param numeroCuenta
	 * @return
	 */
	public static String calculaDcIban(String codigoPais, String entidad, String oficina, String dc, String numeroCuenta) {
		String dcIban;
		try {
			String preIban = entidad + oficina + dc + numeroCuenta + damePesoIBAN(codigoPais.charAt(0)) + damePesoIBAN(codigoPais.charAt(1)) + "00";
			int dcIb = 98 - new BigInteger(preIban).mod(new BigInteger("97")).intValue();
			dcIban = dcIb < 10 ? "0" + dcIb : "" + dcIb;
		} catch (NumberFormatException e) {
			// Alguno de los valores no es numerico
			dcIban = null;
		}
		return dcIban;
	}

	/**
	 * Realiza la conversión letra-numero para el calculo del digito control del IBAN
	 * @param letra
	 * @return
	 */
	private static String damePesoIBAN(char letra) {
		String peso;
		letra = Character.toUpperCase(letra);
		switch (letra) {
			case 'A':
				peso = "10";
				break;
			case 'B':
				peso = "11";
				break;
			case 'C':
				peso = "12";
				break;
			case 'D':
				peso = "13";
				break;
			case 'E':
				peso = "14";
				break;
			case 'F':
				peso = "15";
				break;
			case 'G':
				peso = "16";
				break;
			case 'H':
				peso = "17";
				break;
			case 'I':
				peso = "18";
				break;
			case 'J':
				peso = "19";
				break;
			case 'K':
				peso = "20";
				break;
			case 'L':
				peso = "21";
				break;
			case 'M':
				peso = "22";
				break;
			case 'N':
				peso = "23";
				break;
			case 'O':
				peso = "24";
				break;
			case 'P':
				peso = "25";
				break;
			case 'Q':
				peso = "26";
				break;
			case 'R':
				peso = "27";
				break;
			case 'S':
				peso = "28";
				break;
			case 'T':
				peso = "29";
				break;
			case 'U':
				peso = "30";
				break;
			case 'V':
				peso = "31";
				break;
			case 'W':
				peso = "32";
				break;
			case 'X':
				peso = "33";
				break;
			case 'Y':
				peso = "34";
				break;
			case 'Z':
				peso = "35";
				break;
			default:
				peso = "";
		}
		return peso;
	}

}
