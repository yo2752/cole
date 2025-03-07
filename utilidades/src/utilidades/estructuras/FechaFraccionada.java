package utilidades.estructuras;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.gestoresmadrid.utilidades.components.Utiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class FechaFraccionada {

	private static final ILoggerOegam log = LoggerOegam.getLogger(FechaFraccionada.class);

	private String diaInicio;
	private String mesInicio;
	private String anioInicio;
	private String diaFin;
	private String mesFin;
	private String anioFin;

	@Autowired
	Utiles utiles;

	public FechaFraccionada() {
		super();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	public String getAnioFin() {
		return anioFin;
	}

	public void setAnioFin(String anioFin) {
		this.anioFin = anioFin;
	}

	public String getAnioInicio() {
		return anioInicio;
	}

	public void setAnioInicio(String anioInicio) {
		this.anioInicio = anioInicio;
	}

	public String getDiaFin() {
		return diaFin;
	}

	public void setDiaFin(String diaFin) {
		this.diaFin = diaFin;
	}

	public String getDiaInicio() {
		return diaInicio;
	}

	public void setDiaInicio(String diaInicio) {
		this.diaInicio = diaInicio;
	}

	public String getMesFin() {
		return mesFin;
	}

	public void setMesFin(String mesFin) {
		this.mesFin = mesFin;
	}

	public String getMesInicio() {
		return mesInicio;
	}

	public void setMesInicio(String mesInicio) {
		this.mesInicio = mesInicio;
	}

	/**
	 * Métodos UTILES para las fechas fraccionadas
	 */

	/**
	 * Devuelve verdadero si los campos de la fecha de inicio y fecha de fin son
	 * nulos o no estan llenos
	 * 
	 * @return boolean
	 */
	public boolean isfechaNula() {
		return this.isfechaInicioNula() && isfechaFinNula();
	}

	/**
	 * Devuelve verdadero si algúno de los campos de la fecha de inicio es nulo o no
	 * esta lleno
	 * 
	 * @return boolean
	 */
	public boolean isfechaInicioNula() {
		return this.diaInicio == null || "".equals(this.diaInicio) || this.mesInicio == null
				|| "".equals(this.mesInicio) || this.anioInicio == null || "".equals(this.anioInicio);
	}

	/**
	 * Devuelve verdadero si algúno de los campos de la fecha de fin es nulo o no
	 * esta lleno
	 * 
	 * @return boolean
	 */

	public boolean isfechaFinNula() {
		return this.diaFin == null || "".equals(this.diaFin) || this.mesFin == null || "".equals(this.mesFin)
				|| this.anioFin == null || "".equals(this.anioFin);
	}

	/**
	 * Método que devuelve un Tipo Date a partir de la fecha de Inicio seleccionada
	 * por el usuario. Se contruye un util.Date a partir de la fecha fracionada
	 * 
	 * @return java.Util.Date Fecha de Inicio
	 */

	public Date getFechaInicio() {
		if (isfechaInicioNula())
			return null;

		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
			Date fechaIni = dateFormat.parse(utiles.changeSize(this.diaInicio, 2, '0', false)
					+ utiles.changeSize(this.mesInicio, 2, '0', false) + this.anioInicio);
			return fechaIni;
		} catch (Exception e) {
			log.error("Error recuperando fecha de inicio", e);
			return new Date();
		}

	}

	public Date getFechaMinInicio() {
		Date result = null;
		if (anioInicio != null && !anioInicio.isEmpty()) {
			int year = Integer.parseInt(anioInicio);
			int month = 0;
			int date = 1;
			if (mesInicio != null && !mesInicio.isEmpty()) {
				month = Integer.parseInt(mesInicio) - 1;
				if (diaInicio != null && !diaInicio.isEmpty()) {
					date = Integer.parseInt(diaInicio);
				}
			}
			Calendar cal = Calendar.getInstance();
			cal.set(year, month, date, 0, 0, 0);
			cal.set(Calendar.MILLISECOND, 0);
			result = cal.getTime();
		}
		return result;
	}

	public Date getFechaMaxFin() {
		Date result = null;
		if (anioFin != null && !anioFin.isEmpty()) {
			int year = Integer.parseInt(anioFin);
			int month = 11;
			int date = 0;
			if (mesFin != null && !mesFin.isEmpty()) {
				month = Integer.parseInt(mesFin) - 1;
				if (diaFin != null && !diaFin.isEmpty()) {
					date = Integer.parseInt(diaFin);
				}
			}
			Calendar cal = Calendar.getInstance();
			cal.set(year, month, date > 0 ? date : 1, 23, 59, 59);
			cal.set(Calendar.MILLISECOND, 999);
			if (date == 0) {
				date = cal.getActualMaximum(Calendar.DATE);
			}
			cal.set(Calendar.DATE, date);
			result = cal.getTime();
		}
		return result;
	}

	/**
	 * Método que devuelve un Tipo Date a partir de la fecha de Fin seleccionada por
	 * el usuario. Se contruye un util.Date a partir de la fecha fracionada
	 * 
	 * @return java.Util.Date Fecha de Fin
	 */

	public Date getFechaFin() {

		if (isfechaFinNula())
			return null;

		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
			Date fechaFin = dateFormat.parse(utiles.changeSize(this.diaFin, 2, '0', false)
					+ utiles.changeSize(this.mesFin, 2, '0', false) + this.anioFin);
			return fechaFin;
		} catch (Exception e) {
			log.error("Error recuperando fecha de fin", e);
			return new Date();
		}

	}

}