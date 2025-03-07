package org.gestoresmadrid.oegamImportacion.direccion.service;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.direccion.model.vo.MunicipioVO;

public interface ServicioMunicipioImportacion extends Serializable {

	MunicipioVO getMunicipio(String idMunicipio, String idProvincia);

	List<MunicipioVO> listadoOficinasLiquidadoras(String idMunicipio);

	MunicipioVO getMunicipioPorNombre(String nombreMunicipio, String idProvincia);

}