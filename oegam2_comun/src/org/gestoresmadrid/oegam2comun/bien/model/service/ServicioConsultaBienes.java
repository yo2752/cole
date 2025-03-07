package org.gestoresmadrid.oegam2comun.bien.model.service;

import java.io.Serializable;

import escrituras.beans.ResultBean;

public interface ServicioConsultaBienes extends Serializable{

	ResultBean eliminarBloque(String codSeleccionados);

}
