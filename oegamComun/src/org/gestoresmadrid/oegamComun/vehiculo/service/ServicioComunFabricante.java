package org.gestoresmadrid.oegamComun.vehiculo.service;

import java.io.Serializable;

import org.gestoresmadrid.core.vehiculo.model.vo.FabricanteVO;

public interface ServicioComunFabricante extends Serializable{

	FabricanteVO getFabricante(String fabricante);

}
