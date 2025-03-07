package org.gestoresmadrid.oegam2.trafico.mf.controller.action;

import java.io.File;

import org.gestoresmadrid.oegam2comun.actualizacionMF.model.service.ServicioActualizacionMF;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.springframework.beans.factory.annotation.Autowired;

import escrituras.beans.ResultBean;
import general.acciones.ActionBase;

public class ActualizacionMFAction extends ActionBase {

	private static final long serialVersionUID = -4331122698430664724L;

	private static final String ACTUALIZACION = "actualizacion";

	private File fichero;
	private String ficheroFileName; // Nombre del fichero importado

	@Autowired
	ServicioActualizacionMF servicioActualizacion;

	@Autowired
	private Utiles utiles;

	@Autowired
	private UtilesColegiado utilesColegiado;

	public String inicio() {
		return ACTUALIZACION;
	}

	public String subir() {
		ResultBean salida;

		if (!utiles.esNombreFicheroValido(getFicheroFileName())) {
			addActionError("El fichero añadido debe tener un nombre correcto");
			return ACTUALIZACION;
		}

		salida = servicioActualizacion.encolarActualizacion(fichero, utilesColegiado.getIdContratoSessionBigDecimal(),
				utilesColegiado.getIdUsuarioSessionBigDecimal());

		if (salida.getError()) {
			addActionError(salida.getMensaje());
		} else {
			addActionMessage(
					"Se ha generado correctamente la peticion de actualización. Revise el estado en la consulta de solicitudes");
		}

		return ACTUALIZACION;
	}

	public File getFichero() {
		return fichero;
	}

	public void setFichero(File fichero) {
		this.fichero = fichero;
	}

	public String getFicheroFileName() {
		return ficheroFileName;
	}

	public void setFicheroFileName(String ficheroFileName) {
		this.ficheroFileName = ficheroFileName;
	}

}