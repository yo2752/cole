package org.gestoresmadrid.oegam2.view.decorator;

import org.displaytag.decorator.TableDecorator;
import org.gestoresmadrid.core.legalizacion.model.enumerados.EstadoPeticion;
import org.gestoresmadrid.core.legalizacion.model.enumerados.TipoDocumento;
import org.gestoresmadrid.core.legalizacion.model.vo.LegalizacionCitaVO;
import org.gestoresmadrid.core.springContext.ContextoSpring;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;

/**
 * @author ext_jmbc Decorator de la tabla de Legalización. Para pasar por el decorator hay que tener get de las propiedades que tiene que entrar el decorador.
 */
public class DecoratorLegalizacion extends TableDecorator {

	public String getReferencia() {

		LegalizacionCitaVO fila = (LegalizacionCitaVO) getCurrentRowObject();

		if (getUtilesColegiado().tienePermisoMinisterio() && !getUtilesColegiado().tienePermisoAdmin()) {
			return fila.getReferencia();
		} else {
			return "<a href=\"cargarPeticionAltaLegalizacionNuevo.action?IdPeticion=" + fila.getIdPeticion() + "\">" + fila.getReferencia() + "</a>";
		}
	}

	/**
	 * Convierte el valor del Tipo Documento en el nombre del enumerado
	 * @return Valor en String del Enumerado
	 */
	public String getTipoDocumento() {
		LegalizacionCitaVO fila = (LegalizacionCitaVO) getCurrentRowObject();

		if (fila.getTipoDocumento() != null) {
			return TipoDocumento.convertir(fila.getTipoDocumento()).getNombreEnum();
		}
		return "";
	}

	public String getSolicitado() {
		LegalizacionCitaVO fila = (LegalizacionCitaVO) getCurrentRowObject();
		if (getUtilesColegiado().tienePermisoMinisterio() || getUtilesColegiado().tienePermisoAdmin()) {
			return fila.getSolicitado() == 1 ? "Si" : "No";
		}
		return null;
	}

	public String getFicheroAdjunto() {
		LegalizacionCitaVO fila = (LegalizacionCitaVO) getCurrentRowObject();
		String resultado = null;

		if (fila.getFicheroAdjunto() == 1) {
			resultado = "<img class=\"PopcalTrigger\"" + "onclick=\"descargarFicheroLegalizacion('" + fila.getIdPeticion() + "'); \""
					+ "align=\"left\" src=\"img/documents.png\" style=\"cursor: pointer\"" + "width=\"15\" height=\"16\" border=\"0\" alt=\"Calendario\" />";
		}
		return resultado;
	}

	/**
	 * Convierte el valor del Estado en el nombre del enumerado
	 * @return Valor en String del Enumerado
	 */
	public String getEstado() {
		LegalizacionCitaVO fila = (LegalizacionCitaVO) getCurrentRowObject();
		return EstadoPeticion.convertir(fila.getEstadoPeticion()).getNombreEnum();
	}

	public String getAnulado() {
		LegalizacionCitaVO fila = (LegalizacionCitaVO) getCurrentRowObject();

		if (getUtilesColegiado().tienePermisoMinisterio() || getUtilesColegiado().tienePermisoAdmin()) {
			return fila.getEstado() == 1 ? "No" : "Si";
		}
		return null;
	}

	public String getNombreContrato() {
		LegalizacionCitaVO fila = (LegalizacionCitaVO) getCurrentRowObject();

		StringBuffer nombreContrato = new StringBuffer("");

		if (fila.getContrato() != null) {
			if (fila.getContrato().getColegiado() != null) {
				nombreContrato.append(fila.getContrato().getColegiado().getNumColegiado());
			}
			if (fila.getContrato().getProvincia() != null) {
				nombreContrato.append("-");
				nombreContrato.append(fila.getContrato().getProvincia().getNombre());
			}
			if (fila.getContrato().getVia() != null) {
				nombreContrato.append("-");
				nombreContrato.append(fila.getContrato().getVia());
			}
		}
		return nombreContrato.toString();
	}

	private UtilesColegiado getUtilesColegiado() {
		return ContextoSpring.getInstance().getBean(UtilesColegiado.class);
	}

}