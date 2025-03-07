package org.gestoresmadrid.oegam2comun.vehiculo.model.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.core.vehiculo.model.vo.MarcaDgtVO;
import org.gestoresmadrid.oegam2comun.vehiculo.view.dto.MarcaDgtDto;

public interface ServicioMarcaDgt extends Serializable {

	MarcaDgtVO getMarcaDgt(String codigoMarca, String marca, Boolean versionMatw);

	List<MarcaDgtDto> listaMarcas(String nombreMarca, Boolean versionMatw);

	MarcaDgtDto getMarcaDgtDto(String codigoMarca, String marca, Boolean versionMatw);
	
	MarcaDgtVO addMarca(String marca, Long version);

	MarcaDgtVO getMarcaDgt(String codigoMarca, String marca, ArrayList<Integer> version);

	MarcaDgtVO updateMarca(MarcaDgtVO marcaVO);

	Long getCodigoFromMarca(String marca);

	List<MarcaDgtVO> getMarca(String marca);

	MarcaDgtVO getMarcaDgtCodigo(Long codigoMarca);

	MarcaDgtVO saveOrUpdateMarca(MarcaDgtVO marcaVO);

}
