package org.gestoresmadrid.oegamImportacion.control.service;

import java.io.Serializable;

import org.gestoresmadrid.core.importacionFichero.model.vo.ControlPeticionesImpVO;

public interface ServicioControlPeticionesImp extends Serializable {

	int numeroPeticiones(String tipo);

	ControlPeticionesImpVO getControlPeticionesImp(String tipo);
}
