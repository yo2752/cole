package org.gestoresmadrid.oegamImportacion.utiles;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.gestoresmadrid.core.personas.model.vo.PersonaVO;
import org.gestoresmadrid.oegamConversiones.jaxb.matw.TipoTarjetaItv;
import org.springframework.stereotype.Component;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Component
public class UtilidadesConversionesImportacion implements Serializable {

	private static final long serialVersionUID = -4725136754782392131L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(UtilidadesConversionesImportacion.class);

	Map<String, String> provinciaDGTtoTabla = new HashMap<String, String>();

	public UtilidadesConversionesImportacion() {
		inicializarProvincia();
	}

	public void inicializarProvincia() {
		provinciaDGTtoTabla.put("VI", "01");
		provinciaDGTtoTabla.put("AB", "02");
		provinciaDGTtoTabla.put("A", "03");
		provinciaDGTtoTabla.put("AL", "04");
		provinciaDGTtoTabla.put("AV", "05");
		provinciaDGTtoTabla.put("BA", "06");
		provinciaDGTtoTabla.put("PM", "07");
		provinciaDGTtoTabla.put("B", "08");
		provinciaDGTtoTabla.put("BU", "09");
		provinciaDGTtoTabla.put("CC", "10");
		provinciaDGTtoTabla.put("CA", "11");
		provinciaDGTtoTabla.put("CS", "12");
		provinciaDGTtoTabla.put("CR", "13");
		provinciaDGTtoTabla.put("CO", "14");
		provinciaDGTtoTabla.put("C", "15");
		provinciaDGTtoTabla.put("CU", "16");
		provinciaDGTtoTabla.put("GE", "17");
		provinciaDGTtoTabla.put("GR", "18");
		provinciaDGTtoTabla.put("GU", "19");
		provinciaDGTtoTabla.put("SS", "20");
		provinciaDGTtoTabla.put("H", "21");
		provinciaDGTtoTabla.put("HU", "22");
		provinciaDGTtoTabla.put("J", "23");
		provinciaDGTtoTabla.put("LE", "24");
		provinciaDGTtoTabla.put("L", "25");
		provinciaDGTtoTabla.put("LO", "26");
		provinciaDGTtoTabla.put("LU", "27");
		provinciaDGTtoTabla.put("M", "28");
		provinciaDGTtoTabla.put("MA", "29");
		provinciaDGTtoTabla.put("MU", "30");
		provinciaDGTtoTabla.put("NA", "31");
		provinciaDGTtoTabla.put("OR", "32");
		provinciaDGTtoTabla.put("O", "33");
		provinciaDGTtoTabla.put("P", "34");
		provinciaDGTtoTabla.put("GC", "35");
		provinciaDGTtoTabla.put("PO", "36");
		provinciaDGTtoTabla.put("SA", "37");
		provinciaDGTtoTabla.put("TF", "38");
		provinciaDGTtoTabla.put("S", "39");
		provinciaDGTtoTabla.put("SG", "40");
		provinciaDGTtoTabla.put("SE", "41");
		provinciaDGTtoTabla.put("SO", "42");
		provinciaDGTtoTabla.put("T", "43");
		provinciaDGTtoTabla.put("TE", "44");
		provinciaDGTtoTabla.put("TO", "45");
		provinciaDGTtoTabla.put("V", "46");
		provinciaDGTtoTabla.put("VA", "47");
		provinciaDGTtoTabla.put("BI", "48");
		provinciaDGTtoTabla.put("ZA", "49");
		provinciaDGTtoTabla.put("Z", "50");
		provinciaDGTtoTabla.put("CE", "51");
		provinciaDGTtoTabla.put("ML", "52");
	}

	public String getIdProvinciaFromSiglas(String sigla) {
		if (sigla == null)
			return "";
		return provinciaDGTtoTabla.get(sigla.trim());
	}

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

	public String quitarDecimales(String valor) {
		if (valor == null || valor.equals(""))
			return "";
		valor = valor.replaceAll("\\.", "");
		valor = valor.replaceAll(",", ".");
		BigDecimal valorDecimal;
		try {
			valorDecimal = new BigDecimal(valor);
		} catch (Exception e) {
			return "";
		}
		return valorDecimal.toBigInteger().toString();
	}

	public String tipVehiNuevo(String tipVehiAntiguo) {
		if ("T".equals(tipVehiAntiguo))
			return "A";
		else if ("X".equals(tipVehiAntiguo))
			return "B";
		else if ("M".equals(tipVehiAntiguo))
			return "C";
		else if ("S".equals(tipVehiAntiguo))
			return "D";
		else if ("E".equals(tipVehiAntiguo))
			return "E";
		else if ("F".equals(tipVehiAntiguo))
			return "F";
		else
			return "";
	}

	public String tipoVia(String tipoVia) {
		if (tipoVia == null)
			tipoVia = "";
		if ("6".equals(tipoVia.trim()))
			tipoVia = "41"; // calle
		if ("44".equals(tipoVia.trim()))
			tipoVia = "5";
		if ("24".equals(tipoVia.trim()))
			tipoVia = "29";
		if ("34".equals(tipoVia.trim()))
			tipoVia = "29";
		return tipoVia;
	}

	public String numRegistroLimitacionToMotivo(String valor, String tipo) {
		String respuesta = "";
		if ("05".equals(tipo)) {
			if ("ALQU".equals(valor))
				respuesta = "ER3";
			if ("FAMN".equals(valor))
				respuesta = "RE1";
			if ("MINU".equals(valor))
				respuesta = "ER4";
			if ("AESC".equals(valor))
				respuesta = "ER2";
		} else if ("06".equals(tipo)) {
			if ("RESI".equals(valor))
				respuesta = "ET4";
		}
		return respuesta;
	}

	public String tipoTarjetaItv(TipoTarjetaItv tipoTarjetaItv) {
		if (tipoTarjetaItv.equals(TipoTarjetaItv.A)) {
			return TipoTarjetaItv.A.value();
		}
		if (tipoTarjetaItv.equals(TipoTarjetaItv.B)) {
			return TipoTarjetaItv.B.value();
		}
		if (tipoTarjetaItv.equals(TipoTarjetaItv.BL)) {
			return TipoTarjetaItv.BL.value();
		}
		if (tipoTarjetaItv.equals(TipoTarjetaItv.BR)) {
			return TipoTarjetaItv.BR.value();
		}
		if (tipoTarjetaItv.equals(TipoTarjetaItv.BT)) {
			return TipoTarjetaItv.BT.value();
		}
		if (tipoTarjetaItv.equals(TipoTarjetaItv.C)) {
			return TipoTarjetaItv.C.value();
		}
		if (tipoTarjetaItv.equals(TipoTarjetaItv.D)) {
			return TipoTarjetaItv.D.value();
		}
		if (tipoTarjetaItv.equals(TipoTarjetaItv.AL)) {
			return TipoTarjetaItv.AL.value();
		}
		if (tipoTarjetaItv.equals(TipoTarjetaItv.AT)) {
			return TipoTarjetaItv.AT.value();
		}
		if (tipoTarjetaItv.equals(TipoTarjetaItv.AR)) {
			return TipoTarjetaItv.AR.value();
		}
		if (tipoTarjetaItv.equals(TipoTarjetaItv.DL)) {
			return TipoTarjetaItv.DL.value();
		}
		if (tipoTarjetaItv.equals(TipoTarjetaItv.DR)) {
			return TipoTarjetaItv.DR.value();
		}
		if (tipoTarjetaItv.equals(TipoTarjetaItv.DT)) {
			return TipoTarjetaItv.DT.value();
		}
		return null;
	}

	public BigDecimal stringDecimalToBigDecimal(String valor) {
		try {
			if (valor == null || valor.equals(""))
				return null;
			Float num = null;
			valor = valor.replace(".", "");
			valor = valor.replace(",", "");
			num = new Float(valor);
			num = num / 100;
			return new BigDecimal(num.toString());
		} catch (Exception e) {
			return null;
		}
	}

	public String bigDecimalToStringDosDecimalesPuntosComas(BigDecimal numero, Integer longitud) {
		String numSinComa = quitarCerosIzquierda(bigDecimalToStringDosDecimales(numero, longitud));
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

		String numConComa = parteEnteraPuntos + "," + parteDecimal;

		return numConComa;
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

	public void separarNombreConComas(PersonaVO persona, String nombreEntero) {
		String[] separado = nombreEntero.split("\\,");
		if (separado.length == 3) {
			persona.setApellido1RazonSocial(separado[0].trim());
			persona.setApellido2(separado[1].trim());
			persona.setNombre(separado[2].trim());
		}
		if (separado.length == 2) {
			persona.setApellido1RazonSocial(separado[0].trim());
			persona.setApellido2("");
			persona.setNombre(separado[1].trim());
		}
		if (separado.length == 1) {
			persona.setApellido1RazonSocial(separado[0].trim());
			persona.setApellido2("");
			persona.setNombre("");
		}
	}

	public String convertirEpigrafe(String valor) {
		if (valor != null) {
			if ("01".equals(valor))
				return "1";
			if ("02".equals(valor))
				return "2";
			if ("03".equals(valor))
				return "3";
			if ("04".equals(valor))
				return "4";
			if ("05".equals(valor))
				return "5";
			if ("06".equals(valor))
				return "6";
			if ("07".equals(valor))
				return "7";
			if ("08".equals(valor))
				return "8";
			if ("09".equals(valor))
				return "9";
		}
		return valor;
	}

	public String getViaSinEditar(String nombreVia) {
		if (StringUtils.isNotBlank(nombreVia)) {
			nombreVia = nombreVia.toUpperCase();

			nombreVia = nombreVia.replace("(", " ");
			nombreVia = nombreVia.replace(")", " ");
			nombreVia = nombreVia.replace("-", " ");
			nombreVia = nombreVia.replace("/", " ");
			nombreVia = nombreVia.replace("_", " ");
			nombreVia = nombreVia.replace(",", " ");
			nombreVia = nombreVia.replace(" DE ", "");
			nombreVia = nombreVia.replace(" LA ", "");
			nombreVia = nombreVia.replace(" LAS ", "");
			nombreVia = nombreVia.replace(" LOS ", "");
			nombreVia = nombreVia.replace(" EL ", "");
			nombreVia = nombreVia.replace(" DEL ", "");
			nombreVia = nombreVia.replace(" UN ", "");
			nombreVia = nombreVia.replace(" UNOS ", "");
			nombreVia = nombreVia.replace(" ", "");
			nombreVia = nombreVia.replace("ª", "");
			nombreVia = nombreVia.replace("º", "");
			nombreVia = nombreVia.replace("\\", "");
			nombreVia = nombreVia.replace(";", "");
			nombreVia = nombreVia.replace(".", "");
			nombreVia = nombreVia.replace(":", "");
			nombreVia = nombreVia.replace("Á", "A");
			nombreVia = nombreVia.replace("É", "A");
			nombreVia = nombreVia.replace("Í", "A");
			nombreVia = nombreVia.replace("Ó", "A");
			nombreVia = nombreVia.replace("Ú", "A");
		}

		return nombreVia;
	}
}
