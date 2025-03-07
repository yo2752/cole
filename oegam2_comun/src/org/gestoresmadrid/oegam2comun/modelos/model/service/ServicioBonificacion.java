package org.gestoresmadrid.oegam2comun.modelos.model.service;

import java.io.Serializable;

import org.gestoresmadrid.core.modelos.model.vo.BonificacionVO;
import org.gestoresmadrid.oegam2comun.modelos.view.dto.BonificacionDto;

public interface ServicioBonificacion extends Serializable {

	BonificacionDto getBonificacionPorId(String codigo);

	BonificacionVO activarBonificacionPorId(String codigo);
}