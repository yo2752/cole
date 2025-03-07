package colas.decorators;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;

import org.displaytag.decorator.TableDecorator;

import procesos.beans.ProcesoBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

/**
 * Formatea diferentes columnas que se presentan en la monitorización online
 * de procesos.
 * 
 * @author 
 *
 */
public class GestorProcesosDecorator extends TableDecorator {

	private static SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	private static final ILoggerOegam log = LoggerOegam.getLogger(GestorProcesosDecorator.class);

	public String getNumeroProcesos() {
		log.debug("getNumeroProcesos");
		if (((ProcesoBean)this.getCurrentRowObject()).getNumero()==null) {
			((ProcesoBean)this.getCurrentRowObject()).setNumero(0);
		}
		return "<input type=\"text\" size=\"2\" maxlength=\"2\"id=\"numero" +
				((ProcesoBean)this.getCurrentRowObject()).getOrden() +
				"\" value=\"" +
				((ProcesoBean)this.getCurrentRowObject()).getNumero() +
				"\" onchange=\"activarCambio(" + 
				((ProcesoBean)this.getCurrentRowObject()).getOrden() +
				")\"/>";
	}

	public String getIntentosMax() {
		log.debug("getNumMax");
		if (((ProcesoBean)this.getCurrentRowObject()).getIntentosMax()==null) {
			((ProcesoBean)this.getCurrentRowObject()).setIntentosMax(new BigDecimal(0));
		}

		return "<input type=\"text\" size=\"2\" maxlength=\"2\"id=\"intentos" +
				((ProcesoBean)this.getCurrentRowObject()).getOrden() +
				"\" value=\"" +
				((ProcesoBean)this.getCurrentRowObject()).getIntentosMax() +
				"\" onchange=\"activarCambio(" + 
				((ProcesoBean)this.getCurrentRowObject()).getOrden() +
				")\"/>";
	}

	public String getNumeroActivos() {
		log.debug("getNumActivos");

		return "<span style=\"display:none;\" id=\"proceso" +
				(this.getListIndex() + 1) + "\"> " +
				((ProcesoBean)this.getCurrentRowObject()).getEstadoInstancias() + "</span><span style=\"font-weight:bold;padding-right:3px;vertical-align:top;\">" + 
				((ProcesoBean)this.getCurrentRowObject()).getNumActivos() +
				"</span><img src=\"img/mostrar.gif\" style=\"vertical-align:bottom;\" border=\"0\" height=\"16\" width=\"16\" " +
				"\" onmouseout=\"ocultarVentana()\" onmouseover=\"mostrarVentana(event, "
				+ (this.getListIndex() + 1) + "," + ((ProcesoBean)this.getCurrentRowObject()).getNumero() + ")\" />";
	}

	public String getEstado() {
		return (((ProcesoBean)this.getCurrentRowObject()).isActivo() ? "<span size='5' style=\"background-color:green;color:white\" >ACTIVO</span>" :"<span style=\"background-color:red;color:white\">PARADO</span>");		 
	}

	public String getUltimaEjecucion() {
		return ((ProcesoBean)this.getCurrentRowObject()).getUltimaEjecucion();
	}

	public String getIntervalo(){
		log.debug("getIntervalo");

		String intervalo = ((ProcesoBean)this.getCurrentRowObject()).getIntervalo();
		if (intervalo==null)
			intervalo="";

		return "<input type=\"text\" size=\"5\" maxlength=\"5\"id=\"intervalo" +
		((ProcesoBean)this.getCurrentRowObject()).getOrden() +
		"\" value=\"" +
		intervalo +
		"\" onchange=\"activarCambio(" +
		((ProcesoBean)this.getCurrentRowObject()).getOrden() +
		")\"/>";
	}

	public String getHoraInicio(){
		log.debug("getHoraInicio");

		String horaInicio = ((ProcesoBean)this.getCurrentRowObject()).getHoraInicio();
		if (horaInicio==null)
			horaInicio=""; 

		return "<input type=\"text\" size=\"4\" maxlength=\"4\"id=\"horaInicio" +
		((ProcesoBean)this.getCurrentRowObject()).getOrden() +
		"\" value=\"" +
		horaInicio +
		"\" onchange=\"activarCambio(" + 
		((ProcesoBean)this.getCurrentRowObject()).getOrden() +
		")\"/>";
	}

	public String getHoraFin(){
		log.debug("getHoraFin");

		String horaFin = ((ProcesoBean)this.getCurrentRowObject()).getHoraFin();
		if (horaFin==null)
			horaFin=""; 

		return "<input type=\"text\" size=\"4\" maxlength=\"4\"id=\"horaFin" +
		((ProcesoBean)this.getCurrentRowObject()).getOrden() +
		"\" value=\"" +
		horaFin +
		"\" onchange=\"activarCambio(" +
		((ProcesoBean)this.getCurrentRowObject()).getOrden() +
		")\"/>";
	}

	public String getModificar() {
		return "<input type=\"button\" class=\"botonpeque\" id=\"btnModificar" +
		((ProcesoBean)this.getCurrentRowObject()).getOrden() +
		"\" onclick=\"modificar(" + 
		((ProcesoBean)this.getCurrentRowObject()).getOrden() +
		")\" title=\"Modificar el número de procesos\" value=\"Modificar\" disabled=\"disabled\"/>";
	}

	public String getAccion() {
		return "<input type=\"button\" class=\"botonpeque\" id=\"btnAccion" +
		((ProcesoBean)this.getCurrentRowObject()).getOrden() +
		"\" value=\"" +
		(((ProcesoBean)this.getCurrentRowObject()).isActivo() ? "Detener" :"Activar") +
		"\" onclick=\"" + 
		(((ProcesoBean)this.getCurrentRowObject()).isActivo() ? "parar(" :"activar(") +
		((ProcesoBean)this.getCurrentRowObject()).getOrden() +
		")\" />";
	}

	public String getTiempoCorrecto() {
		log.debug("getTiempoCorrecto");
		if (((ProcesoBean)this.getCurrentRowObject()).getTiempoCorrecto()==null) {
			((ProcesoBean)this.getCurrentRowObject()).setTiempoCorrecto(new BigDecimal(0));
		}

		return "<input type=\"text\" size=\"2\" maxlength=\"5\"id=\"tiempoCorrecto" +
				((ProcesoBean)this.getCurrentRowObject()).getOrden() +
				"\" value=\"" +
				((ProcesoBean)this.getCurrentRowObject()).getTiempoCorrecto() +
				"\" onchange=\"activarCambio(" + 
				((ProcesoBean)this.getCurrentRowObject()).getOrden() +
				")\"/>";
	}

	public String getTiempoRecuperable() {
		log.debug("getTiempoRecuperable");
		if (((ProcesoBean)this.getCurrentRowObject()).getTiempoRecuperable()==null) {
			((ProcesoBean)this.getCurrentRowObject()).setTiempoRecuperable(new BigDecimal(0));
		}

		return "<input type=\"text\" size=\"4\" maxlength=\"4\"id=\"tiempoRecuperable" +
				((ProcesoBean)this.getCurrentRowObject()).getOrden() +
				"\" value=\"" +
				((ProcesoBean)this.getCurrentRowObject()).getTiempoRecuperable() +
				"\" onchange=\"activarCambio(" + 
				((ProcesoBean)this.getCurrentRowObject()).getOrden() +
				")\"/>";
	}

	public String getTiempoNoRecuperable() {
		log.debug("getTiempoNoRecuperable");
		if (((ProcesoBean)this.getCurrentRowObject()).getTiempoNoRecuperable()==null) {
			((ProcesoBean)this.getCurrentRowObject()).setTiempoNoRecuperable(new BigDecimal(0));
		}

		return "<input type=\"text\" size=\"4\" maxlength=\"4\"id=\"tiempoNoRecuperable" +
				((ProcesoBean)this.getCurrentRowObject()).getOrden() +
				"\" value=\"" +
				((ProcesoBean)this.getCurrentRowObject()).getTiempoNoRecuperable() +
				"\" onchange=\"activarCambio(" +
				((ProcesoBean)this.getCurrentRowObject()).getOrden() +
				")\"/>";
	}

	public String getTiempoSinDatos() {
		log.debug("getTiempoSinDatos");
		if (((ProcesoBean)this.getCurrentRowObject()).getTiempoSinDatos()==null) {
			((ProcesoBean)this.getCurrentRowObject()).setTiempoSinDatos(new BigDecimal(0));
		}

		return "<input type=\"text\" size=\"4\" maxlength=\"4\"id=\"tiempoSinDatos" +
				((ProcesoBean)this.getCurrentRowObject()).getOrden() +
				"\" value=\"" +
				((ProcesoBean)this.getCurrentRowObject()).getTiempoSinDatos() +
				"\" onchange=\"activarCambio(" + 
				((ProcesoBean)this.getCurrentRowObject()).getOrden() +
				")\"/>";
	}

}