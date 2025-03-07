package org.gestoresmadrid.oegamComun.pais.service;

import java.io.Serializable;

import org.gestoresmadrid.core.paises.model.vo.PaisVO;

public interface ServicioComunPais extends Serializable {

	public PaisVO getPais(String idPais);
}
