package org.gestoresmadrid.oegam2comun.vehiculo.model.service;

import java.io.Serializable;

import org.gestoresmadrid.oegam2comun.vehiculo.view.dto.MarcaFabricanteDto;

import escrituras.beans.ResultBean;

public interface ServicioMarcaFabricante extends Serializable {

	MarcaFabricanteDto getMarcaFabricante(String codigoMarca, Long codFabricante);
	
	ResultBean[] addMarcaFabricante(String marca, String fabricante, Long version);
}
