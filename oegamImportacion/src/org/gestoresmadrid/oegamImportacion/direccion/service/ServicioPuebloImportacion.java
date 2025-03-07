package org.gestoresmadrid.oegamImportacion.direccion.service;

import java.io.Serializable;

import org.gestoresmadrid.core.direccion.model.vo.PuebloVO;

public interface ServicioPuebloImportacion extends Serializable {

	PuebloVO getPueblo(String idProvincia, String idMunicipio, String pueblo);

}
