package trafico.utiles.exportacion;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import escrituras.beans.Persona;
import escrituras.modelo.ModeloColegiado;
import trafico.beans.daos.BeanPQTasasDetalle;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class TramaTasa {
	private static final ILoggerOegam log = LoggerOegam.getLogger(TramaTasa.class);
	static int tamanioTasa = 12;
	static int tamanioGrupoP = 2;
	static int tamanioTipoP = 2;
	static int tamanioGrupoA = 2;
	static int tamanioTipoA = 2;
	static int tamanioPrecio = 9;
	static int enterosPrecio = 6;

	public static ArrayList<String> getLineas(List<BeanPQTasasDetalle> lista) {
		ArrayList<String> lineas = new ArrayList<>();
		lineas.add(getCabecera(lista));
		for (int i = 0; i < lista.size(); i++) {
			lineas.add(getLinea(lista.get(i)));
		}
		return lineas;
	}

	public static String getCabecera(List<BeanPQTasasDetalle> lista) {
		BigDecimal idContrato = lista.get(0).getP_ID_CONTRATO();
		Persona gestoria;
		String linea = "";
		gestoria = new ModeloColegiado().obtenerColegiadoCompleto(idContrato);

		// Añadimos los espacios del número de autoliquidación
		linea += "            "; // 12 espacios
		// Añadimos los espacios de la fecha de venta
		linea += "        ";
		// Añadimos el NIF de la gestoria
		linea += gestoria.getNif();
		// Añadimos la cantidad
		BigDecimal precioTotal = new BigDecimal(0);
		for (int i = 0; i < lista.size(); i++) {
			precioTotal = precioTotal.add(lista.get(i).getP_PRECIO());
		}
		linea += aniadirCerosIzquierda(precioTotal, 10);
		// Añadimos la cantidad
		if (lista.size() < 10)
			linea += "00" + lista.size();
		else if (lista.size() < 100)
			linea += "0" + lista.size();
		else
			linea += lista.size();

		return linea;
	}

	public static String getLinea(BeanPQTasasDetalle tasa) {
		int posicion = 0;
		String linea = "";
		linea += tasa.getP_CODIGO_TASA();
		posicion += tamanioTasa;
		for (int i = linea.length(); i < 12; i++) {
			linea = linea + " ";
		}

		String tipo = tasa.getP_TIPO_TASA();
		String[] tipoSeparado = tipo.split("\\.");
		if (tipoSeparado.length == 2) {
			linea += tipoSeparado[0] + " " + tipoSeparado[1] + " ";
			linea += "    "; // Para los asociados;
		} else {
			linea += "        ";
		}
		posicion += tamanioGrupoP + tamanioTipoP + tamanioGrupoA + tamanioTipoA;

		linea += aniadirCerosIzquierda(tasa.getP_PRECIO(), tamanioPrecio);

		return linea;
	}

	public static String aniadirCerosIzquierda(BigDecimal cifra, int tamanioEntero) {
		String valor = "";
		valor = String.format("%" + tamanioEntero + ".2f", cifra);
		valor = valor.replaceAll(" ", "0");
		return valor;
	}

}