package org.gestoresmadrid.utilidades.components;

import java.io.Serializable;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import utilidades.estructuras.Fecha;
import utilidades.estructuras.FechaFraccionada;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

@Component
public class UtilesFecha implements Serializable{

	private static final long serialVersionUID = 1287060324941242797L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(UtilesFecha.class);

	private static UtilesFecha utilesFecha;

	private static final String FORMATO_FECHA = "dd/MM/yyyy";

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	Utiles utiles;

	public static UtilesFecha getInstance() {
		if (utilesFecha == null) {
			utilesFecha = new UtilesFecha();
			SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(utilesFecha);
		}
		return utilesFecha;
	}

	/**
	 * Te devuelve un Boolean.TRUE si la diferencia de dias es menor o igual al rango indicado
	 * @param fechaInicio
	 * @param fechaFin
	 * @param valorRango
	 * @return
	 */
	public Boolean comprobarRangoFechas(Date fechaInicio, Date fechaFin, int valorRango) {
		try {
			if (fechaInicio != null && fechaFin != null) {
				long numDias = diferenciaFechaEnDias(fechaInicio, fechaFin);
				if (numDias <= valorRango) {
					return Boolean.TRUE;
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de comprobar el rango de fechas, error: ", e);
		}
		return Boolean.FALSE;
	}

	/**
	 * Devuelve un tipo Fecha a partir de un tipo Date.
	 * @return escrituras.utiles.FechaFracionada
	 */
	public Fecha getFechaFracionada(Date date) {
		return getFechaFracionada(date, null);
	}

	/**
	 * Devuelve un tipo Fecha a partir de un tipo Date.
	 * @return escrituras.utiles.FechaFracionada
	 */
	public Fecha getFechaFracionada(Date date, Time time) {
		if (date == null)
			return null;

		Fecha fecha = new Fecha();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		Time time0 = Time.valueOf("00:00:00");
		if (time != null && !time.equals(time0)) {
			try {
				calendar.setTimeInMillis(calendar.getTimeInMillis() + time.getTime());
				fecha.setHora(utiles.changeSize(String.valueOf(calendar.get(Calendar.HOUR_OF_DAY) + 1), 2, '0', false));
				fecha.setMinutos(utiles.changeSize(String.valueOf(calendar.get(Calendar.MINUTE)), 2, '0', false));
				fecha.setSegundos(utiles.changeSize(String.valueOf(calendar.get(Calendar.SECOND)), 2, '0', false));
			} catch (Exception e) {
				log.error("Error al obtener la hora:minutos:segundos de una fecha");
			}
		}

		fecha.setDia(utiles.changeSize(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)), 2, '0', false));
		fecha.setMes(utiles.changeSize(String.valueOf(calendar.get(Calendar.MONTH) + 1), 2, '0', false));
		fecha.setAnio(String.valueOf(calendar.get(Calendar.YEAR)));

		return fecha;
	}

	/**
	 * Devuelve un tipo Fecha a partir de un tipo Timestamp.
	 * @return escrituras.utiles.FechaFracionada
	 */
	public Fecha getFechaFracionada(Timestamp timestamp) {
		if (timestamp == null)
			return null;
		return getFechaFracionada(getDate(timestamp));
	}

	/**
	 * Asigna la fecha actual a una fecha fracionada y la devuelve.
	 * @return escrituras.utiles.FechaFracionada
	 */
	public FechaFraccionada getFechaFracionadaActual() {
		Calendar calendar = Calendar.getInstance();
		FechaFraccionada fechaFraccionada = new FechaFraccionada();
		fechaFraccionada.setDiaInicio(utiles.changeSize(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)), 2, '0', false));
		fechaFraccionada.setMesInicio(utiles.changeSize(String.valueOf(calendar.get(Calendar.MONTH) + 1), 2, '0', false));
		fechaFraccionada.setAnioInicio(String.valueOf(calendar.get(Calendar.YEAR)));
		fechaFraccionada.setDiaFin(utiles.changeSize(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)), 2, '0', false));
		fechaFraccionada.setMesFin(utiles.changeSize(String.valueOf(calendar.get(Calendar.MONTH) + 1), 2, '0', false));
		fechaFraccionada.setAnioFin(String.valueOf(calendar.get(Calendar.YEAR)));
		return fechaFraccionada;
	}

	public FechaFraccionada rellenarFechaFraccionada(Date fechaInicial, Date fechaFinal) {
		Calendar calendarIni = Calendar.getInstance();
		calendarIni.setTime(fechaInicial);
		FechaFraccionada fechaFraccionada = new FechaFraccionada();
		fechaFraccionada.setDiaInicio(utiles.changeSize(String.valueOf(calendarIni.get(Calendar.DAY_OF_MONTH)), 2, '0', false));
		fechaFraccionada.setMesInicio(utiles.changeSize(String.valueOf(calendarIni.get(Calendar.MONTH) + 1), 2, '0', false));
		fechaFraccionada.setAnioInicio(String.valueOf(calendarIni.get(Calendar.YEAR)));
		Calendar calendarFin = Calendar.getInstance();
		calendarFin.setTime(fechaInicial);
		fechaFraccionada.setDiaFin(utiles.changeSize(String.valueOf(calendarFin.get(Calendar.DAY_OF_MONTH)), 2, '0', false));
		fechaFraccionada.setMesFin(utiles.changeSize(String.valueOf(calendarFin.get(Calendar.MONTH) + 1), 2, '0', false));
		fechaFraccionada.setAnioFin(String.valueOf(calendarFin.get(Calendar.YEAR)));
		return fechaFraccionada;
	}

	/**
	 * Asigna la fecha actual a una fecha y la devuelve.
	 * @return escrituras.utiles.Fecha
	 */
	public Fecha getFechaActual() {
		Calendar calendar = Calendar.getInstance();
		Fecha fecha = new Fecha();
		fecha.setDia(utiles.changeSize(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)), 2, '0', false));
		fecha.setMes(utiles.changeSize(String.valueOf(calendar.get(Calendar.MONTH) + 1), 2, '0', false));
		fecha.setAnio(String.valueOf(calendar.get(Calendar.YEAR)));

		return fecha;
	}

	/**
	 * Asigna la fecha / hora actual y la devuelve.
	 * @return escrituras.utiles.Fecha
	 */
	public Fecha getFechaHoraActual() {
		Calendar calendar = Calendar.getInstance();
		Fecha fecha = new Fecha();
		fecha.setDia(utiles.changeSize(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)), 2, '0', false));
		fecha.setMes(utiles.changeSize(String.valueOf(calendar.get(Calendar.MONTH) + 1), 2, '0', false));
		fecha.setAnio(String.valueOf(calendar.get(Calendar.YEAR)));
		fecha.setHora(utiles.changeSize(String.valueOf(calendar.get(Calendar.HOUR) + 1), 2, '0', false));
		fecha.setMinutos(utiles.changeSize(String.valueOf(calendar.get(Calendar.MINUTE)), 2, '0', false));
		fecha.setSegundos(utiles.changeSize(String.valueOf(calendar.get(Calendar.SECOND)), 2, '0', false));

		return fecha;
	}

	/**
	 * Asigna la fecha / hora actual y la devuelve.
	 * @return escrituras.utiles.Fecha
	 */
	public Fecha getFechaHoraActualLEG() {
		Calendar calendar = Calendar.getInstance();
		Fecha fecha = new Fecha();
		fecha.setDia(utiles.changeSize(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)), 2, '0', false));
		fecha.setMes(utiles.changeSize(String.valueOf(calendar.get(Calendar.MONTH) + 1), 2, '0', false));
		fecha.setAnio(String.valueOf(calendar.get(Calendar.YEAR)));
		fecha.setHora(utiles.changeSize(String.valueOf(calendar.get(Calendar.HOUR_OF_DAY)), 2, '0', false));
		fecha.setMinutos(utiles.changeSize(String.valueOf(calendar.get(Calendar.MINUTE)), 2, '0', false));
		fecha.setSegundos(utiles.changeSize(String.valueOf(calendar.get(Calendar.SECOND)), 2, '0', false));

		return fecha;
	}

	public String getFechaHoy() {
		java.util.Date date = new java.util.Date();
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(FORMATO_FECHA);
		return sdf.format(date);
	}

	/**
	 * Asigna la fecha actual a un timestamp y la devuelve.
	 * @return escrituras.utiles.Fecha
	 */
	public Timestamp getTimestampActual() {
		return new Timestamp((new Date()).getTime());
	}

	/**
	 * Retorna un timestamp equivalente a la fecha pasada por parámetro.
	 * @param java.util.Date
	 * @return java.sql.Timestamp
	 */
	public Timestamp getTimestamp(Date date) {
		return new Timestamp(date.getTime());
	}

	/**
	 * Retorna un date equivalente al date pasada por parámetro.
	 * @param java.sql.Timestamp
	 * @return java.util.Date
	 */
	public Date getDate(Timestamp timestamp) {
		return new Date(timestamp.getTime());
	}

	/**
	 * Compara los valores dos Fechas Fraccionadas devuelve cero (0) si sin iguales, menos uno (-1) si la fecha inicio es distinta, uno (1) si la fecha de fin es distinta y dos (2) si ambas fechas son distintas
	 * @return int
	 */
	public int compararFechasFraccionadas(FechaFraccionada fechaFraccionada1, FechaFraccionada fechaFraccionada2) {
		int iguales = 0;

		if (!fechaFraccionada1.getFechaInicio().equals(fechaFraccionada2.getFechaInicio()))
			iguales = -1;

		if (!fechaFraccionada1.getFechaFin().equals(fechaFraccionada2.getFechaFin())) {
			iguales = iguales != -1 ? 1 : 2;
		}

		return iguales;
	}

	/**
	 * Compara los valores dos Fechas devuelve cero (0) si sin iguales o uno (1) son distintas
	 * @param fecha1
	 * @param fecha2
	 * @return int
	 * @throws ParseException
	 */
	public int compararFechas(Fecha fecha1, Fecha fecha2) throws ParseException {
		if (fecha1 == null && fecha2 == null) {
			return 1;
		}

		if (fecha1 == null || fecha2 == null) {
			return 0;
		}

		int iguales = 1;

		if (!fecha1.getFecha().equals(fecha2.getFecha()))
			iguales = 0;

		return iguales;
	}

	// INICIO - 0006958: Error al validar una Baja con fecha de matriculación
	// superior a la fecha de la baja
	/**
	 * Compara los valores dos Fechas, devuelve uno (1) si la fecha1 es mayor, cero (0) si son iguales o dos (2) si la fecha2 es mayor, y menos uno (-1) si hay un error.
	 * @param fecha1
	 * @param fecha2
	 * @return int
	 * @throws ParseException
	 */
	public int compararFechaMayor(Fecha fecha1, Fecha fecha2) throws ParseException {
		if (fecha1 == null && fecha2 == null) {
			return -1;
		}

		if (fecha1 == null || fecha2 == null) {
			return -1;
		}

		int res = 0;

		Date dFecha1 = fecha1.getFecha();
		Date dFecha2 = fecha2.getFecha();

		if (dFecha1 == null || dFecha2 == null) {
			return -1;
		}

		if (dFecha1.compareTo(dFecha2) == 0) {
			res = 0;
		} else if (dFecha1.compareTo(dFecha2) > 0) {
			res = 1;
		} else {
			res = 2;
		}

		return res;
	}
	// FIN - 0006958: Error al validar una Baja con fecha de matriculación
	// superior a la fecha de la baja

	public Date getFechaDate(String fecha) {
		Date dFecha = null;
		if (fecha != null && !("").equals(fecha)) {
			int anno = Integer.parseInt(fecha.substring(0, 4));
			int mes = Integer.parseInt(fecha.substring(4, 6)) - 1;
			int dia = Integer.parseInt(fecha.substring(6, 8));

			Calendar cal = new GregorianCalendar(anno, mes, dia);

			dFecha = cal.getTime();
		}
		return dFecha;
	}

	public Date formatoFecha(String formato, String fecha) {
		Date dFecha = null;
		try {
			dFecha = new SimpleDateFormat(formato).parse(fecha);
		} catch (ParseException e) {
			log.error("Error a la hora de formatear la Fecha: " + e);
		}

		return dFecha;
	}

	public Date getFechaHoraDate(String fecha) {
		Date dFecha = null;

		int dia = Integer.parseInt(fecha.substring(0, 2));
		int mes = Integer.parseInt(fecha.substring(2, 4)) - 1;
		int anno = Integer.parseInt(fecha.substring(4, 8));
		int hora = Integer.parseInt(fecha.substring(8, 10));
		int minuto = Integer.parseInt(fecha.substring(10, 12));
		int segundo = Integer.parseInt(fecha.substring(12, 14));
		Calendar cal = new GregorianCalendar(anno, mes, dia, hora, minuto, segundo);

		dFecha = cal.getTime();

		return dFecha;
	}

	/**
	 * Actualiza la fechafraccionada que se pasa por parámetro, es decir que si alguna de las fechas (Inicio o Fin) tiene algún campo nulo, le asigna la fecha actual
	 * @param FechaFracionada a Actualizar
	 * @return escrituras.utiles.FechaFracionada
	 */
	public void actualizarFechaFracionada(FechaFraccionada fechaFraccionadaActualizar) {
		boolean fechaInicioNula = fechaFraccionadaActualizar.isfechaInicioNula();
		boolean fechaFinNula = fechaFraccionadaActualizar.isfechaFinNula();
		if ((!fechaInicioNula && !fechaFinNula) || (fechaInicioNula && fechaFinNula)) {
			return;
		} else {
			FechaFraccionada fechaHoy = getFechaFracionadaActual();
			if (fechaInicioNula) {
				fechaFraccionadaActualizar.setDiaInicio(fechaHoy.getDiaInicio());
				fechaFraccionadaActualizar.setMesInicio(fechaHoy.getMesInicio());
				fechaFraccionadaActualizar.setAnioInicio(fechaHoy.getAnioInicio());
			}
			if (fechaFinNula) {
				fechaFraccionadaActualizar.setDiaFin(fechaHoy.getDiaFin());
				fechaFraccionadaActualizar.setMesFin(fechaHoy.getMesFin());
				fechaFraccionadaActualizar.setAnioFin(fechaHoy.getAnioFin());
			}
		}
	}

	public String formatoFecha(Timestamp fecha) {
		if (fecha == null)
			return null;
		Date date = new Date(fecha.getTime());
		return formatoFecha(date);
	}

	public String formatoFecha(String formato, Timestamp fecha) {
		if (fecha == null)
			return null;
		Date date = new Date(fecha.getTime());
		return formatoFecha(formato, date);
	}

	public String formatoFecha(Date fecha) {
		return formatoFecha(FORMATO_FECHA, fecha);
	}

	public String formatoFecha(Fecha fecha) {
		try {
			return fecha != null ? formatoFecha(FORMATO_FECHA, fecha.getFecha()) : "";
		} catch (ParseException e) {
			return "";
		}
	}

	public String formatoFechaHorasMinutos(Date fecha) {
		return formatoFecha("dd/MM/yyyy HH:mm:ss", fecha);
	}

	public String formatoFecha(String formato, Date fecha) {
		if (fecha == null)
			return null;
		SimpleDateFormat dateFormat = new SimpleDateFormat(formato);
		return dateFormat.format(fecha);
	}

	/***
	 * Recibe una fecha como string con el formato dd/mm/yyyy y la invierte al formato yyyy-mm-dd hh:mm:ss[.fffffffff] .Utilizado para obtener un objeto Timestamp con el método estático valueOf(String). No hace comprobaciones con lo que la cadena recibida como parámetro debe tener el formato
	 * requerido.
	 */
	public String invertirCadenaFecha(String fecha) {
		String[] trozos = fecha.split("/");
		StringBuilder invertida = new StringBuilder();
		invertida.append(trozos[2] + "-" + trozos[1] + "-" + trozos[0] + " 00:00:00.0");
		return invertida.toString();
	}

	public String invertirCadenaFechaSinSeparador(String fecha) {
		if (!StringUtils.isBlank(fecha) && !fecha.equals("//")) {
			String[] trozos = fecha.split("/");
			StringBuilder invertida = new StringBuilder();
			invertida.append(trozos[2] + trozos[1] + trozos[0]);
			return invertida.toString();
		} else
			return null;
	}

	public String invertirFecha(Fecha fecha) {
		if (fecha.getAnio() == null || fecha.getDia() == null || fecha.getMes() == null)
			return invertirCadenaFechaSinSeparador(getFechaActual().toString());
		if (fecha != null) {
			return fecha.getAnio() + fecha.getMes() + fecha.getDia();
		}
		return null;
	}

	/**
	 * @param hora formato hhmm
	 * @return objeto date de java.util
	 */
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

	public java.sql.Date convertirStringtoDate (String fecha) {
		java.util.Date date;
		try {
			date = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss")
				.parse(fecha);
		} catch (ParseException e) {
			return null;
		}
		return new java.sql.Date(date.getTime());
	}

	public Timestamp sumaDias(Fecha fechaInicio, String dias) {
		Calendar greg = Calendar.getInstance();
		Fecha fechaactual = getFechaActual();
		greg.set(Integer.parseInt(fechaactual.getAnio()), Integer.parseInt(fechaactual.getMes()) - 1, Integer.parseInt(fechaactual.getDia()));

		greg.add(Calendar.DATE, Integer.parseInt(dias));
		return new Timestamp(greg.getTimeInMillis());
	}

	public Fecha sumaAnio(Fecha fechaInicio, int anioMod) {
		Fecha fechaMod = new Fecha();
		int anioNuevo = Integer.parseInt(fechaInicio.getAnio());
		anioNuevo = anioNuevo + anioMod;
		String anioNuevoMod = String.valueOf(anioNuevo);
		fechaMod.setAnio(anioNuevoMod);
		fechaMod.setMes(fechaInicio.getMes());
		fechaMod.setDia(fechaInicio.getDia());
		return fechaMod;
	}

	public String getTextoFechaFormato(Date fecha, SimpleDateFormat formatoFecha) {
		String cadFecha = "";
		try {
			if (null != fecha) {
				cadFecha = formatoFecha.format(fecha);
			}
		} catch (Throwable ex) {
		}
		return cadFecha;
	}

	public boolean esLunes() {
		Calendar cal = Calendar.getInstance();
		return (cal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY);
	}

	public boolean esFechaLaborable() {
		Calendar cal = Calendar.getInstance();
		return ((cal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) && (cal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY));
	}

	public boolean esDomingo() {
		Calendar cal = Calendar.getInstance();
		return (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY);
	}

	public boolean esViernes(boolean considerarFestivos) throws OegamExcepcion {
		return considerarFestivos ? esViernesSinConsiderarFestivo() : esViernesConsiderandoFestivo();
	}

	private boolean esViernesConsiderandoFestivo() throws OegamExcepcion {
		Calendar cal = Calendar.getInstance();
		return ((cal.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) && !esFestivoNacional(getFechaActual()));
	}

	private boolean esViernesSinConsiderarFestivo() {
		Calendar cal = Calendar.getInstance();
		return (cal.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY);
	}

	/**
	 * Determina si la fecha actual es día laborable
	 * @param considerarFestivos - si el parámetro es true se tienen en cuenta los festivos
	 * @return
	 * @throws Throwable
	 */
	public boolean esFechaLaborable(boolean considerarFestivos) throws OegamExcepcion {
		return considerarFestivos ? esFechaLaborableConsiderandoFestivos() : esFechaLaborableSinConsiderarFestivos();
	}

	/**
	 * Método que determina si la fecha actual es laborable SIN tener en cuenta los festivos nacionales
	 * @return
	 */
	public boolean esFechaLaborableSinConsiderarFestivos() {
		Calendar cal = Calendar.getInstance();
		return ((cal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) && (cal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY));
	}

	/**
	 * Método que determina si la fecha actual es laborable teniendo en cuenta fines de semana y festivos nacionales
	 * @return
	 * @throws Throwable
	 */
	public boolean esFechaLaborableConsiderandoFestivos() throws OegamExcepcion {
		Calendar cal = Calendar.getInstance();
		return ((cal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) && (cal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY) && !esFestivoNacional(getFechaActual()));
	}

	/**
	 * Método que determina si la fecha actual es laborable teniendo en cuenta fines de semana y festivos Madrid
	 * @return
	 * @throws Throwable
	 */
	public boolean esFechaLaborableConsiderandoFestivosMadrid() throws OegamExcepcion {
		Calendar cal = Calendar.getInstance();
		return ((cal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) && (cal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY) && !esFestivoMadrid(getFechaActual()));
	}

	/**
	 * Método que identifica si una fecha es laborable teniendo en cuenta fines de semana y festivos nacionales
	 * @param fecha
	 * @return
	 * @throws OegamExcepcion
	 * @author alfredo.delallave
	 */
	public boolean esFechaLaborableConsiderandoFestivos(Fecha fecha) throws OegamExcepcion {
		Calendar cal = Calendar.getInstance();
		try {
			cal.setTime(fecha.getFecha());
		} catch (ParseException e) {
			throw (new OegamExcepcion("Error al obtener fecha - " + e.getMessage()));
		}
		return ((cal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) && (cal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY) && !esFestivoNacional(fecha));
	}

	/**
	 * Método que identifica si una fecha es festivo nacional
	 * @param fecha
	 * @return devuelve true si es festivo nacional
	 * @throws OegamExcepcion
	 */
	public boolean esFestivoNacional(Fecha fecha) throws OegamExcepcion {
		String[] fechasFestivos;

		try {
			// Se obtiene la lista de días festivos para el año correspondiente
			// a nuestra fecha
			fechasFestivos = gestorPropiedades.valorPropertie("dias.festivos." + fecha.getAnio()).split("\\,");
		} catch (Exception e) {
			// log.error("Error al obtener el listado de los festivos nacionales del año "
			// + fecha.getAnio());
			throw (new OegamExcepcion("Error al obtener listado de festivos nacionales del anio " + fecha.getAnio()));
		}

		// Se recorre la lista de festivos y si se encuentra una coincidencia
		// con nuestra fecha se devuelve true
		for (int i = 0; i < fechasFestivos.length; i++)
			if (fechasFestivos[i].equals(fecha.getDia() + fecha.getMes()))
				return true;

		return false;
	}

	/**
	 * Método que identifica si una fecha es festivo en Madrid
	 * @param fecha
	 * @return devuelve true si es festivo Madrid
	 * @throws OegamExcepcion
	 */
	public boolean esFestivoMadrid(Fecha fecha) throws OegamExcepcion {
		String[] fechasFestivos;
		try {
			fechasFestivos = gestorPropiedades.valorPropertie("dias.festivos.madrid." + fecha.getAnio()).split("\\,");
		} catch (Exception e) {
			throw (new OegamExcepcion("Error al obtener listado de festivos Madrid del anio " + fecha.getAnio()));
		}
		// Se recorre la lista de festivos y si se encuentra una coincidencia
		// con nuestra fecha se devuelve true
		for (int i = 0; i < fechasFestivos.length; i++)
			if (fechasFestivos[i].equals(fecha.getDia() + fecha.getMes()))
				return true;
		return false;
	}

	/**
	 * Método que nos dice si existen festivos para el a&ntilde;o indicado.
	 * @param anio
	 * @return devuelve true si es festivo nacional
	 * @throws OegamExcepcion
	 */
	public boolean existenFestivos(String anio) throws OegamExcepcion {
		String res = "";
		try {
			// Se obtiene la lista de días festivos para el año correspondiente
			// a nuestra fecha
			res = gestorPropiedades.valorPropertie("dias.festivos." + anio);
		} catch (Exception e) {
			// log.error("Error al obtener el listado de los festivos nacionales del año "
			// + fecha.getAnio());
			throw (new OegamExcepcion("Error al obtener listado de festivos nacionales del año " + anio));
		}

		return (res != null && !res.equalsIgnoreCase(""));
	}

	/**
	 * Convierte una fecha String DD/MM/AAAA en un Timestamp. Ponemos manualmente la hora a 00:00:00
	 * @param valor
	 */
	public Timestamp DDMMAAAAToTimestamp(String valor) {
		if (valor != null && (!"".equals(valor.trim()))) {
			String[] fecha = valor.split("/");
			String anyo = fecha[2];
			String mes = fecha[1].length() == 2 ? fecha[1] : ("0" + fecha[1]);
			String dia = fecha[0].length() == 2 ? fecha[0] : ("0" + fecha[0]);
			String valorToTimestamp = anyo + "-" + mes + "-" + dia + " 00:00:00";
			return Timestamp.valueOf(valorToTimestamp);
		}
		return null;
	}

	/**
	 * Setea la hora de un Date con la cadena que se le pasa que debe tener el formato (hh:mm)
	 * @param fecha : Date al que se le quiere establecer la hora
	 * @param hora : String del cuál se extraerá la hora
	 * @throws Exception
	 */
	public void setHoraEnDate(Date fecha, String hora) throws Exception {
		// Verifica la longitud de la cadena:
		if (hora.length() != 5) {
			throw new Exception("El formato de la hora no es correcto. Deber ser: hh:mm");
		}
		String[] trozos = hora.split(":");
		// Verifica que se han pasado horas y minutos separados por ':'
		if (trozos.length != 2) {
			throw new Exception("El formato de la hora no es correcto. Deber ser: hh:mm");
		}
		Integer horas = null;
		Integer minutos = null;
		try {
			// Comprueba que las horas y los minutos son números:
			horas = Integer.parseInt(trozos[0]);
			minutos = Integer.parseInt(trozos[1]);
		} catch (NumberFormatException e) {
			throw new Exception("El formato de la hora no es correcto. Deber ser: hh:mm");
		}
		// Comprueba que las horas y los minutos están en su rango posible:
		if (horas > 24 || horas < 0 || minutos > 59 || minutos < 0) {
			throw new Exception("El formato de la hora no es correcto. Debe ser: hh:mm");
		}
		// Crea un calendar con la fecha y le establece la hora
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha);
		calendar.set(Calendar.HOUR_OF_DAY, horas);
		calendar.set(Calendar.MINUTE, minutos);
		calendar.set(Calendar.SECOND, 0);
		// Establece la fecha con el calendar auxiliar:
		fecha.setTime(calendar.getTimeInMillis());
	}

	public Date setHorasMinutosSegundosEnDate(Date fecha, String hora) throws Exception {
		if (hora.length() != 8) {
			throw new Exception("El formato de la hora no es correcto. Deber ser: hh:mm:ss");
		}
		String[] trozos = hora.split(":");
		// Verifica que se han pasado horas y minutos separados por ':'
		if (trozos.length != 3) {
			throw new Exception("El formato de la hora no es correcto. Deber ser: hh:mm:ss");
		}
		Integer horas = null;
		Integer minutos = null;
		Integer segundos = null;
		try {
			// Comprueba que las horas y los minutos son números:
			horas = Integer.parseInt(trozos[0]);
			minutos = Integer.parseInt(trozos[1]);
			segundos = Integer.parseInt(trozos[2]);
		} catch (NumberFormatException e) {
			throw new Exception("El formato de la hora no es correcto. Deber ser: hh:mm:ss");
		}
		// Comprueba que las horas y los minutos están en su rango posible:
		if (horas > 24 || horas < 0 || minutos > 59 || minutos < 0 || segundos > 59 || segundos < 0) {
			throw new Exception("El formato de la hora no es correcto. Debe ser: hh:mm:ss");
		}
		// Crea un calendar con la fecha y le establece la hora
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha);
		calendar.set(Calendar.HOUR_OF_DAY, horas);
		calendar.set(Calendar.MINUTE, minutos);
		calendar.set(Calendar.SECOND, segundos);
		// Establece la fecha con el calendar auxiliar:
		fecha.setTime(calendar.getTimeInMillis());
		return fecha;
	}

	public void setSegundosEnDate(Date fecha, int segundos) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha);
		calendar.set(Calendar.SECOND, segundos);
		fecha.setTime(calendar.getTimeInMillis());
	}

	public void setMilisegundosEnDate(Date fecha, int milisegundos) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha);
		calendar.set(Calendar.MILLISECOND, milisegundos);
		fecha.setTime(calendar.getTimeInMillis());
	}

	/**
	 * Devuelve el primer laborable anterior a la fecha pasada como parámetro Tiene en consideración festivos nacionales y fines de semana
	 * @return
	 * @throws ParseException
	 * @throws OegamExcepcion
	 * @throws Throwable
	 */
	public Fecha getPrimerLaborableAnterior(Fecha fecha) throws ParseException, OegamExcepcion {
		Fecha laborableAnterior;

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha.getFecha());

		do {
			calendar.add(Calendar.DAY_OF_MONTH, -1);
			laborableAnterior = getFechaFracionada(calendar.getTime());
		}
		while (!esFechaLaborableConsiderandoFestivos(laborableAnterior));

		return laborableAnterior;
	}

	/**
	 * Devuelve el primer laborable posterior a la fecha pasada como parámetro. Tiene en consideración festivos nacionales y fines de semana
	 * @param fecha
	 * @return
	 * @throws ParseException
	 * @throws OegamExcepcion
	 * @throws Throwable
	 */
	public Fecha getPrimerLaborablePosterior(Fecha fecha) throws ParseException, OegamExcepcion {
		Fecha laborableAnterior;

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha.getFecha());

		do {
			calendar.add(Calendar.DAY_OF_MONTH, +1);
			laborableAnterior = getFechaFracionada(calendar.getTime());
		}
		while (!esFechaLaborableConsiderandoFestivos(laborableAnterior));

		return laborableAnterior;
	}

	/**
	 * Devuelve el primer laborable anterior a la fecha actual Tiene en consideración festivos nacionales y fines de semana
	 * @return
	 * @throws OegamExcepcion
	 * @throws ParseException
	 * @throws Throwable
	 */
	public Fecha getPrimerLaborableAnterior() throws ParseException, OegamExcepcion {
		return getPrimerLaborableAnterior(getFechaActual());
	}

	/*
	 * Este método devolverá la fecha de ayer. Se ha creado para Legalización. El usuario con permiso ministerio sólo puede ver peticiones hasta (fecha hasta) el día anterior al que está consultando.
	 */
	public Fecha getDiaMesAnterior() throws ParseException {
		Fecha laborableAnterior;
		Fecha fecha = getFechaActual();

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha.getFecha());

		calendar.add(Calendar.DAY_OF_MONTH, -1);
		laborableAnterior = getFechaFracionada(calendar.getTime());

		return laborableAnterior;
	}

	/**
	 * Asigna la fecha actual a una fecha fracionada y la devuelve.
	 * @param fechaDate
	 * @return escrituras.utiles.FechaFracionada
	 */
	public Fecha getFechaConDate(Date fechaDate) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fechaDate);
		Fecha fechaConDate = new Fecha();
		fechaConDate.setDia(utiles.changeSize(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)), 2, '0', false));
		fechaConDate.setMes(utiles.changeSize(String.valueOf(calendar.get(Calendar.MONTH) + 1), 2, '0', false));
		fechaConDate.setAnio(String.valueOf(calendar.get(Calendar.YEAR)));
		return fechaConDate;
	}

	/**
	 * Devuelve un tipo Fecha a partir de un tipo Date teniendo en cuenta Time.
	 * @param date
	 * @return escrituras.utiles.FechaFracionada
	 */
	public Fecha getFechaTimeStampConDate(Date date) {
		if (date == null) {
			return null;
		}

		Fecha fecha = new Fecha();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		fecha.setDia(utiles.changeSize(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)), 2, '0', false));
		fecha.setMes(utiles.changeSize(String.valueOf(calendar.get(Calendar.MONTH) + 1), 2, '0', false));
		fecha.setAnio(String.valueOf(calendar.get(Calendar.YEAR)));

		fecha.setHora(utiles.changeSize(String.valueOf(calendar.get(Calendar.HOUR_OF_DAY)), 2, '0', false));
		fecha.setMinutos(utiles.changeSize(String.valueOf(calendar.get(Calendar.MINUTE)), 2, '0', false));
		fecha.setSegundos(utiles.changeSize(String.valueOf(calendar.get(Calendar.SECOND)), 2, '0', false));

		return fecha;
	}

	/**
	 * Devuelve una cadena con la fecha de hoy separada por el carácter del parámetro
	 * @param separador . Carácter con el se separará el dia del mes y del año. Si vale null se separará con barra ascendente.
	 * @return Una cadena con la fecha de hoy
	 */
	public String getCadenaFecha(Character param) {
		char separador;
		separador = param == null ? '/' : param;

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		Fecha fecha = new Fecha();
		fecha.setDia(utiles.changeSize(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)), 2, '0', false));
		fecha.setMes(utiles.changeSize(String.valueOf(calendar.get(Calendar.MONTH) + 1), 2, '0', false));
		fecha.setAnio(String.valueOf(calendar.get(Calendar.YEAR)));
		String cadenaRetorno = fecha.getAnio() + separador;
		cadenaRetorno += fecha.getMes() + separador;
		cadenaRetorno += fecha.getDia();
		return cadenaRetorno;
	}

	public Integer restarAnyos(String fechaIni, String fechaFin) {
		String[] dat1fin = fechaFin.split("/");
		String[] dat2ini = fechaIni.split("/");
		int anos = Integer.parseInt(dat2ini[2]) - Integer.parseInt(dat1fin[2]);
		int mes = Integer.parseInt(dat2ini[1]) - Integer.parseInt(dat1fin[1]);
		if (mes < 0) {
			anos = anos - 1;
		} else if (mes == 0) {
			int dia = Integer.parseInt(dat2ini[0]) - Integer.parseInt(dat1fin[0]);
			if (dia < 0) {
				anos = anos - 1;
			}
		}
		return anos;
	}

	/**
	 * Método que realmente devuelve un objeto de tipo FechaFraccionada a partir de un Date.
	 * @param date
	 */
	public FechaFraccionada getFechaFracionadaReal(Date date) {
		Fecha fecha = getFechaFracionada(date, null);
		FechaFraccionada fechaFraccionada = new FechaFraccionada();
		fechaFraccionada.setDiaInicio(fecha.getDia());
		fechaFraccionada.setMesInicio(fecha.getMes());
		fechaFraccionada.setAnioInicio(fecha.getAnio());

		return fechaFraccionada;
	}

	/**
	 * Método que devuelve una suma como resultado de sumar los dias a la fecha pasada como parámetro.
	 */
	public Timestamp sumaDiasAFecha(Fecha fechaInicio, String dias) {
		Calendar greg = Calendar.getInstance();
		greg.set(Integer.parseInt(fechaInicio.getAnio()), Integer.parseInt(fechaInicio.getMes()) - 1, Integer.parseInt(fechaInicio.getDia()));

		greg.add(Calendar.DATE, Integer.parseInt(dias));
		return new Timestamp(greg.getTimeInMillis());
	}

	/**
	 * Calcula en dias la diferencia entre dos fechas de tipo Date
	 * @param fecha1
	 * @param fecha2
	 * @return La diferencia de dias entre las dos fechas en un long
	 * @throws Exception
	 */
	public long diferenciaFechaEnDias(Date fecha1, Date fecha2) throws Exception {
		Calendar calendarDesde = Calendar.getInstance();
		calendarDesde.setTime(fecha1);
		long milisDesde = calendarDesde.getTimeInMillis();
		Calendar calendarHasta = Calendar.getInstance();
		calendarHasta.setTime(fecha2);
		long milisHasta = calendarHasta.getTimeInMillis();
		long milisDiferencia = milisHasta - milisDesde;
		return milisDiferencia / (24 * 60 * 60 * 1000);
	}

	/**
	 * Compara los valores dos Fechas devuelve uno(1) si la fecha1 es mayor, cero (0) si son iguales o dos (2) si la fecha2 es mayor, y menos uno (-1) si hay un error.
	 * @param pantalla
	 * @param bbdd
	 * @return int
	 * @throws ParseException
	 */
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

	/**
	 * Devuelve en minutos el tiempo que ha transcurrido entre dos fechas
	 * @param f1
	 * @param f2
	 * @return minutos (long) entre dos fechas
	 */
	public long diferenciaMinutosEntreFechas(Date f1, Date f2) {
		long diferencia = f2.getTime() - f1.getTime();
		return (diferencia / (60 * 1000));
	}

	public Date sumarRestarDiasFecha(Date fecha, int dias) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha);
		calendar.add(Calendar.DAY_OF_YEAR, dias);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		return calendar.getTime();
	}

	/**
	 * Metodo que recibe una Fecha y devuelve un Date
	 * @param fecha
	 * @return
	 */
	public Date convertirFecha(Fecha fecha) {
		Calendar cal = new GregorianCalendar();
		cal.set(Integer.parseInt(fecha.getAnio()), Integer.parseInt(fecha.getMes())-1, Integer.parseInt(fecha.getDia()), Integer.parseInt(fecha.getHora()), Integer.parseInt(fecha.getMinutos()), Integer
				.parseInt(fecha.getSegundos()));
		return cal.getTime();
	}

	/**
	 * Metodo que recibe una fecha fraccionada y te devuelve un Date
	 * @param fecha
	 * @return
	 */
	public Date convertirFechaFraccionadaEnDate(FechaFraccionada fecha) {
		String sFecha = fecha.getDiaInicio() + "/" + fecha.getMesInicio() + "/" + fecha.getAnioInicio();
		String formato = FORMATO_FECHA;
		return formatoFecha(formato, sFecha);
	}

	public Date convertirFechaEnDate(Fecha fecha) {
		String sFecha = fecha.getDia() + "/" + fecha.getMes() + "/" + fecha.getAnio();
		String formato = FORMATO_FECHA;
		return formatoFecha(formato, sFecha);
	}

	public Date sumaDiasFecha(Fecha fecha, String dias) {
		Calendar greg = Calendar.getInstance();
		greg.set(Integer.parseInt(fecha.getAnio()), Integer.parseInt(fecha.getMes()) - 1, Integer.parseInt(fecha.getDia()));
		greg.add(Calendar.DATE, Integer.parseInt(dias));
		return greg.getTime();
	}

	public boolean comprobarFecha(String fechaEnv) {
		Calendar cal = Calendar.getInstance();
		String anio = fechaEnv.substring(0, 4);
		String mes = fechaEnv.substring(4, 6);
		String dia = fechaEnv.substring(6, 8);
		try {
			cal.set(Integer.parseInt(anio), Integer.parseInt(mes), Integer.parseInt(dia));
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public Date restarDias(Date fecha, int contDiasRest, Integer horas, Integer minutos, Integer segundos) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha);
		calendar.add(Calendar.DAY_OF_YEAR, contDiasRest);
		if (horas != null) {
			calendar.add(Calendar.HOUR_OF_DAY, horas);
		}
		if (minutos != null) {
			calendar.add(Calendar.MINUTE, minutos);
		}
		if (segundos!= null) {
			calendar.add(Calendar.SECOND, segundos);
		}
		return calendar.getTime();
	}

	public Date sumarDias(Date fecha, int contDiasSumar, Integer horas, Integer minutos, Integer segundos) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha);
		calendar.add(Calendar.DAY_OF_YEAR, contDiasSumar);
		if (horas != null) {
			calendar.add(Calendar.HOUR_OF_DAY, horas);
		}
		if (minutos != null) {
			calendar.add(Calendar.MINUTE, minutos);
		}
		if (segundos!= null) {
			calendar.add(Calendar.SECOND, segundos);
		}
		return calendar.getTime();
	}

	public Date restarMeses(Date fecha, int contMesesRest, Integer horas, Integer minutos, Integer segundos) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha);
		calendar.add(Calendar.MONTH, -contMesesRest);
		if (horas != null) {
			calendar.add(Calendar.HOUR_OF_DAY, horas);
		}
		if (minutos != null) {
			calendar.add(Calendar.MINUTE, minutos);
		}
		if (segundos != null) {
			calendar.add(Calendar.SECOND, segundos);
		}
		return calendar.getTime();
	}

	public int getNumDiasLaborableEntreFechas(Date startDate, Date endDate) throws OegamExcepcion {
		Calendar startCal = Calendar.getInstance();
		startCal.setTime(startDate);

		Calendar endCal = Calendar.getInstance();
		endCal.setTime(endDate);

		int workDays = 0;

		// Return 0 if start and end are the same
		if (startCal.getTimeInMillis() == endCal.getTimeInMillis()) {
			return 0;
		}

		if (startCal.getTimeInMillis() > endCal.getTimeInMillis()) {
			startCal.setTime(endDate);
			endCal.setTime(startDate);
		}

		do {
			// Excluding start date
			startCal.add(Calendar.DAY_OF_MONTH, 1);
			if (startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY
					&& startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY
					&& !esFestivoNacional(getFechaConDate(startCal.getTime()))) {
				++workDays;
			}
		} while (startCal.getTimeInMillis() < endCal.getTimeInMillis()); // Excluding end date

		return workDays;
	}
	// Mantis 19262. David Sierra. Validación del formato de la fecha pasada por parametro
	public boolean validarFormatoFecha(Fecha fecha) {
		try {
			fecha.getTimestamp();
		} catch (ParseException e) {
			log.error(e.getMessage());
			return false;
		}
		return true;
	}

	public Date getFechaActualDesfaseBBDD() {
		Calendar fechaActual = Calendar.getInstance();

		String segundosDesfase = gestorPropiedades.valorPropertie("desfase.fecha.bbdd");

		if (segundosDesfase != null && !segundosDesfase.isEmpty()) {
			fechaActual.add(Calendar.SECOND, Integer.valueOf(segundosDesfase));
		}

		return fechaActual.getTime();
	}

	public Boolean comprobarFormatoFecha(String formato, String fecha) {
		Boolean formatoOk = true;
		try {
			new SimpleDateFormat(formato).parse(fecha);
		} catch (ParseException e) {
			formatoOk = false;
		}
		return formatoOk;
	}

	public String convertirMesNumberToDesc(Integer intMes) {
		String descMes = "";
		if (intMes != null) {
			String mes = String.valueOf(intMes);
			switch (mes) {
				case "1":
					descMes = "Enero";
					break;
				case "2":
					descMes = "Febrero";
					break;
				case "3":
					descMes = "Marzo";
					break;
				case "4":
					descMes = "Abril";
					break;
				case "5":
					descMes = "Mayo";
					break;
				case "6":
					descMes = "Junio";
					break;
				case "7":
					descMes = "Julio";
					break;
				case "8":
					descMes = "Agosto";
					break;
				case "9":
					descMes = "Septiembre";
					break;
				case "10":
					descMes = "Octubre";
					break;
				case "11":
					descMes = "Noviembre";
					break;
				case "12":
					descMes = "Diciembre";
					break;
				default:
					break;
			}
		}
		return descMes;
	}

	public Date getPrimerLaborableAnterior(Date fecha) {
		Date fechaLaborable = null;
		Fecha laborableAnterior = null;
		try {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(fecha);
			do {
				calendar.add(Calendar.DAY_OF_MONTH, -1);
				laborableAnterior = getFechaFracionada(calendar.getTime(), null);
				fechaLaborable = laborableAnterior.getFecha();
			}
			while (!esFechaLaborableConsiderandoFestivos(laborableAnterior));
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener el primer laborable anterior a la fecha indicada, error: ", e);
		} catch (OegamExcepcion e) {
			log.error("Ha sucedido un error a la hora de obtener el primer laborable anterior a la fecha indicada, error: ", e);
		}
		return fechaLaborable;
	}

	public boolean esPrimerDiaMes() {
		Calendar cal = Calendar.getInstance();
		return (cal.get(Calendar.DAY_OF_MONTH) == 1);
	}

	public Date formatoFecha(String formato, Fecha fecha) {
		Date dFecha = null;
		try {
			dFecha = new SimpleDateFormat(formato).parse(fecha.toString());
		} catch (ParseException e) {
			log.error("Error a la hora de formatear la Fecha: " + e);
		}
		return dFecha;
	}

	public Date sumaRestaAnios(Date fecha, int anios) {
		Calendar calendario = Calendar.getInstance();
		calendario.setTime(fecha);
		calendario.add(Calendar.YEAR, anios);
		return calendario.getTime();
	}

	public int diferenciaFechaEnAnios(Date fecha) {
		LocalDate fechaNac = fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		Period periodo = Period.between(fechaNac, LocalDate.now());
		return periodo.getYears();
	}

	//TODO: Función para cálculo de Prescrita 620
//	public Date calcularPrescrita620(Date fecha) {
//	}
}