package org.gestoresmadrid.oegam2comun.modelos.model.service;

import java.io.Serializable;

import org.gestoresmadrid.oegam2comun.remesa.view.dto.RemesaDto;

import escrituras.beans.ResultBean;

public interface ServicioCoefParticipacion extends Serializable{

	ResultBean getListasCoefParticipacion(RemesaDto remesa);

}
