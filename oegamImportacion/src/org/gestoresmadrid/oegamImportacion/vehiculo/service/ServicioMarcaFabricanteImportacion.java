package org.gestoresmadrid.oegamImportacion.vehiculo.service;

import java.io.Serializable;

import org.gestoresmadrid.core.vehiculo.model.vo.MarcaFabricanteVO;

public interface ServicioMarcaFabricanteImportacion extends Serializable {

	MarcaFabricanteVO getMarcaFabricante(String codigoMarca, Long codFabricante);
}
