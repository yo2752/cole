package org.gestoresmadrid.oegam2comun.contrato.model.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.general.model.vo.ContratoColegiadoVO;

public interface ServicioContratoColegiado extends Serializable {

	String getNumColegiadoPorContrato(Long idContrato);

	boolean existeContratoColegiado(Long idContrato, String numColegiado);

	boolean esUsuarioDelContrato(BigDecimal idContrato, BigDecimal idUsuario);

	List<ContratoColegiadoVO> getContratoColegiado(String numColegiado);
	
	ContratoColegiadoVO getColegiadoPorContrato(Long idContrato);
}
