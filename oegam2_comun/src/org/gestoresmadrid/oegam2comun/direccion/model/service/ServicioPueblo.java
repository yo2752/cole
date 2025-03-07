package org.gestoresmadrid.oegam2comun.direccion.model.service;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.direccion.model.vo.PuebloVO;
import org.gestoresmadrid.oegam2comun.direccion.view.dto.PuebloDto;

public interface ServicioPueblo extends Serializable {

	PuebloVO getPueblo(String idProvincia, String idMunicipio, String pueblo);

	List<PuebloDto> listaPueblos(String idProvincia, String idMunicipio);
}
