package org.gestoresmadrid.oegamImportacion.contrato.service;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;

public interface ServicioContratoImportacion extends Serializable {

	public static String ID_CONTRATO = "idContrato";
	public static String NUEVO_CONTRATO = "nuevoContrato";

	ContratoVO getContrato(BigDecimal idContrato);

	ContratoVO getContratoVO(Long idContrato);

}