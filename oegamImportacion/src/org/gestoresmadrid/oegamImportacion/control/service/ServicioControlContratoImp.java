package org.gestoresmadrid.oegamImportacion.control.service;

import java.io.Serializable;

import org.gestoresmadrid.core.importacionFichero.model.vo.ControlContratoImpVO;

public interface ServicioControlContratoImp extends Serializable {

	ControlContratoImpVO getControlContratoImp(Long idContrato, String tipo);

	boolean esPermitido(Long idContrato, String tipo);
}
