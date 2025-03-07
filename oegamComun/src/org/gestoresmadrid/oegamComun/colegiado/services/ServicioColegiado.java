package org.gestoresmadrid.oegamComun.colegiado.services;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.core.general.model.vo.ColegiadoVO;
import org.gestoresmadrid.oegamComun.colegiado.view.dto.ColegiadoDto;

public interface ServicioColegiado extends Serializable {

	ColegiadoVO getColegiado(String numColegiado);

	ColegiadoDto getColegiadoDto(String numColegiado);

	Long getIdColegiado(String numColegiado);

	ColegiadoVO getColegiadoPorIdUsuario(BigDecimal idUsuario);

}