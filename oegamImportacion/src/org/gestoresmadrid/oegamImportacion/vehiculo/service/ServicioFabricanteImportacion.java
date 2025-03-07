package org.gestoresmadrid.oegamImportacion.vehiculo.service;

import java.io.Serializable;

import org.gestoresmadrid.core.vehiculo.model.vo.FabricanteVO;

public interface ServicioFabricanteImportacion extends Serializable {

	FabricanteVO getFabricante(String fabricante);
}
