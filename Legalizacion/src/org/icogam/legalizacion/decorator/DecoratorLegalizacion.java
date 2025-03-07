package org.icogam.legalizacion.decorator;

import org.displaytag.decorator.TableDecorator;
import org.gestoresmadrid.core.springContext.ContextoSpring;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.icogam.legalizacion.beans.LegalizacionCita;
import org.icogam.legalizacion.utiles.ClaseDocumento;
import org.icogam.legalizacion.utiles.EstadoPeticion;
import org.icogam.legalizacion.utiles.TipoDocumento;

/**
 * @author ext_jmbc
 *
 *
 *         Decorator de la tabla de Legalizacion.
 * 
 *         Para pasar por el decorator hay que tener get de las propiedades que
 *         tiene que entrar el decorador.
 */
public class DecoratorLegalizacion extends TableDecorator {

	public String getReferencia() {

		LegalizacionCita fila = (LegalizacionCita) getCurrentRowObject();

		if (getUtilesColegiado().tienePermisoMinisterio() && !getUtilesColegiado().tienePermisoAdmin()) {
			return fila.getReferencia();
		} else {
			return "<a href=\"cargarPeticionAltaLegalizacion.action?IdPeticion=" + fila.getIdPeticion() + "\">"
					+ fila.getReferencia() + "</a>";
		}
	}

	/**
	 * Convierte el valor del Tipo Documento en el nombre del enumerado
	 * 
	 * @return Valor en String del Enumerado
	 */
	public String getTipoDocumento() {
		LegalizacionCita fila = (LegalizacionCita) getCurrentRowObject();

		return TipoDocumento.convertir(fila.getTipoDocumento()).getNombreEnum();
	}

	public String getClaseDocumento() {
		LegalizacionCita fila = (LegalizacionCita) getCurrentRowObject();
		if (fila.getClaseDocumento() != null) {
			return ClaseDocumento.convertir(fila.getClaseDocumento()).getNombreEnum();
		}
		return null;
	}

	public String getSolicitado() {
		LegalizacionCita fila = (LegalizacionCita) getCurrentRowObject();

		if (getUtilesColegiado().tienePermisoMinisterio() || getUtilesColegiado().tienePermisoAdmin()) {
			return fila.getSolicitado() == 1 ? "Si" : "No";
		}
		return null;
	}

	public String getFicheroAdjunto() {
		LegalizacionCita fila = (LegalizacionCita) getCurrentRowObject();
		String resultado = null;

		if (fila.getFicheroAdjunto() == 1) {
			resultado = "<img class=\"PopcalTrigger\"" + "onclick=\"descargarFicheroLegalizacion('"
					+ fila.getIdPeticion() + "'); \""
					+ "align=\"left\" src=\"img/documents.png\" style=\"cursor: pointer\""
					+ "width=\"15\" height=\"16\" border=\"0\" alt=\"Calendario\" />";
		}

		return resultado;
	}

	/**
	 * Convierte el valor del Estado en el nombre del enumerado
	 * 
	 * @return Valor en String del Enumerado
	 */
	public String getEstado() {
		LegalizacionCita fila = (LegalizacionCita) getCurrentRowObject();

		return EstadoPeticion.convertir(fila.getEstadoPeticion()).getNombreEnum();
	}

	/**
	 * CheckBox para seleccionar la peticion.
	 * 
	 * @return
	 */
	public String getCheckbox() {
		LegalizacionCita fila = (LegalizacionCita) getCurrentRowObject();

		String resultado = "<input type=\"checkbox\" " + "name=\"listaChecksPeticiones\" id=\"idPeticion\"" + "value='"
				+ fila.getIdPeticion() + "' />";

		return resultado;
	}

	private UtilesColegiado getUtilesColegiado() {
		return ContextoSpring.getInstance().getBean(UtilesColegiado.class);
	}

}
