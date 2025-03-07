package utilidades.estructuras;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class Fecha implements Serializable {

	private static final ILoggerOegam log = LoggerOegam.getLogger(Fecha.class);

	private static final long serialVersionUID = 1L;
	private int SESENTA = 60;
	private String dia;
	private String mes;
	private String anio;
	private String hora;
	private String minutos;
	private String segundos;

	public Fecha() {
	}

	@SuppressWarnings("deprecation")
	public Fecha(Date date) {
		if (date == null) {
			return;
		}
		this.anio = (1900 + date.getYear()) + "";
		this.mes = (1 + date.getMonth()) + "";
		this.dia = date.getDate() + "";
		this.hora = date.getHours() + "";
		this.minutos = date.getMinutes() + "";
		this.segundos = date.getSeconds() + "";
	}

	public Fecha(String conBarras) {
		if (conBarras != null) {
			String[] trozos = conBarras.split("/");
			if (trozos != null && trozos.length == 3) {
				this.dia = trozos[0];
				this.mes = trozos[1];
				this.anio = trozos[2];
			}
		}
	}

	public String getDia() {
		return dia;
	}

	public void setDia(String dia) {
		this.dia = dia;
	}

	public String getMes() {
		return mes;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}

	public String getAnio() {
		return anio;
	}

	public void setAnio(String anio) {
		this.anio = anio;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public String getMinutos() {
		return minutos;
	}

	public void setMinutos(String minutos) {
		this.minutos = minutos;
	}

	public String getSegundos() {
		return segundos;
	}

	public void setSegundos(String segundos) {
		this.segundos = segundos;
	}

	/**
	 * Devuelve verdadero si algúno de los campos de la fecha es nulo o no esta
	 * lleno
	 * 
	 * @return boolean
	 */
	public boolean isfechaNula() {
		return this.dia == null || "".equals(this.dia) || this.mes == null || "".equals(this.mes) || this.anio == null
				|| "".equals(this.anio);
	}

	/**
	 * Devuelve verdadero si algúno de los campos de la fecha/hora minutos y
	 * segundos es nulo o no esta lleno
	 * 
	 * @return boolean
	 */
	public boolean isfechaHoraMinutosSegundosNula() {
		return this.dia == null || "".equals(this.dia) || this.mes == null || "".equals(this.mes) || this.anio == null
				|| "".equals(this.anio) || this.hora == null || "".equals(this.hora) || this.minutos == null
				|| "".equals(this.minutos) || this.segundos == null || "".equals(this.segundos);
	}

	/**
	 * Devuelve verdadero si algúno de los campos de la fecha/hora y minutos es nulo
	 * o no esta lleno
	 * 
	 * @return boolean
	 */
	public boolean isfechaHoraNula() {
		return this.dia == null || "".equals(this.dia) || this.mes == null || "".equals(this.mes) || this.anio == null
				|| "".equals(this.anio) || this.hora == null || "".equals(this.hora) || this.minutos == null
				|| "".equals(this.minutos) || Integer.parseInt(this.minutos) > SESENTA;
	}

	/**
	 * Método que devuelve un Tipo Date a partir de la fecha seleccionada por el
	 * usuario. Se contruye un util.Date a partir de la fecha fracionada
	 * 
	 * @return java.Util.Date getFecha()
	 */

	public Date getFecha() throws ParseException {
		if (isfechaNula())
			return null;
		try {
			String fechaString = changeSize(this.dia, 2, '0', false) + changeSize(this.mes, 2, '0', false) + this.anio;
			SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
			SimpleDateFormat dateFormat2 = new SimpleDateFormat("ddMMyy");
			Date fechaIni = dateFormat.parse(fechaString);
			if (!dateFormat.format(fechaIni).equals(fechaString)) {
				fechaIni = dateFormat2.parse(fechaString);
				if (!dateFormat2.format(fechaIni).equals(fechaString)) {
					throw new ParseException("La fecha que introdujo es invalida: " + fechaString, 0);
				}
			}
			return fechaIni;
		} catch (ParseException e) {
			log.error("La fecha introducida no tiene un formato valido");
			throw e;
		}
	}

	/**
	 * Método que devuelve un Tipo Date a partir de la fecha seleccionada por el
	 * usuario. Se contruye un util.Date a partir de la fecha fracionada
	 * 
	 * @return java.Util.Date getFechaHora()
	 */

	public Date getFechaHora() throws ParseException {
		if (isfechaHoraMinutosSegundosNula())
			return null;

		try {
			String fechaString = changeSize(this.dia, 2, '0', false) + changeSize(this.mes, 2, '0', false) + this.anio
					+ changeSize(this.hora, 2, '0', false) + ":" + changeSize(this.minutos, 2, '0', false) + ":"
					+ changeSize(this.segundos, 2, '0', false);
			SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyyHH:mm:ss");
			SimpleDateFormat dateFormat2 = new SimpleDateFormat("ddMMyyHH:mm:ss");
			Date fecha = dateFormat.parse(fechaString);
			if (!dateFormat.format(fecha).equals(fechaString)) {
				fecha = dateFormat2.parse(fechaString);
				if (!dateFormat2.format(fecha).equals(fechaString)) {
					throw new ParseException("La fecha que introdujo es invalida: " + fechaString, 0);
				}
			}
			return fecha;
		} catch (ParseException e) {
			log.warn(e);
			throw e;
		}
	}

	/**
	 * Método que devuelve un Tipo Date a partir de la fecha hora y minutos
	 * seleccionada por el usuario. Se contruye un util.Date a partir de la fecha
	 * fracionada
	 * 
	 * @return java.Util.Date getFechaHora()
	 */

	public Date getFechaHoraMinutos() throws ParseException {

		if (isfechaHoraNula())
			return null;

		try {
			String fechaString = changeSize(this.dia, 2, '0', false) + changeSize(this.mes, 2, '0', false) + this.anio
					+ changeSize(this.hora, 2, '0', false) + changeSize(this.minutos, 2, '0', false);
			SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyyHHmm");
			SimpleDateFormat dateFormat2 = new SimpleDateFormat("ddMMyyHHmm");
			Date fecha = dateFormat.parse(fechaString);
			if (!dateFormat.format(fecha).equals(fechaString)) {
				fecha = dateFormat2.parse(fechaString);
				if (!dateFormat2.format(fecha).equals(fechaString)) {
					throw new ParseException("La fecha que introdujo es invalida: " + fechaString, 0);
				}
			}
			return fecha;
		} catch (ParseException e) {
			log.warn(e);
			throw e;
		}
	}

	public Timestamp getTimestamp() throws ParseException {

		if (!isfechaHoraMinutosSegundosNula())
			return new Timestamp(getFechaHora().getTime());

		if (!isfechaNula())
			return new Timestamp(getFecha().getTime());

		return null;
	}

	public java.sql.Date getDate() throws ParseException {
		if (!isfechaHoraMinutosSegundosNula())
			return new java.sql.Date(getFechaHora().getTime());

		if (!isfechaNula())
			return new java.sql.Date(getFecha().getTime());

		return null;
	}

	@Override
	public String toString() {
		if (!isfechaNula())
			return dia + "/" + mes + "/" + anio;
		else
			return "";
	}

	private String changeSize(String cadena, int size, char relleno, boolean detras) {

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

}