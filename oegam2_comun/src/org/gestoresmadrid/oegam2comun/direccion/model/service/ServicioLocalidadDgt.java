package org.gestoresmadrid.oegam2comun.direccion.model.service;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.direccion.model.vo.LocalidadDgtVO;
import org.gestoresmadrid.core.direccion.model.vo.UnidadPoblacionalVO;

import escrituras.beans.ResultBean;

public interface ServicioLocalidadDgt extends Serializable {

	List<LocalidadDgtVO> getLocalidades(String localidad, String municipio);

	List<LocalidadDgtVO> getLocalidadesPorCodigoPostal(String codigoPostal, String localidad);

	List<String> listaLocalidades(String codigoIne);

	ResultBean actualizar(LocalidadDgtVO localidadDgtVO);

	List<UnidadPoblacionalVO> getUnidadesPoblacionales(String idMunicipio, String idProvincia);
}
