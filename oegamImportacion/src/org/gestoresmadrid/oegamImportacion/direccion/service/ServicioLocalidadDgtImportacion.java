package org.gestoresmadrid.oegamImportacion.direccion.service;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.direccion.model.vo.LocalidadDgtVO;

public interface ServicioLocalidadDgtImportacion extends Serializable {

	List<LocalidadDgtVO> getLocalidades(String localidad, String municipio);

	List<LocalidadDgtVO> getLocalidadesPorCodigoPostal(String codigoPostal, String localidad);

	List<String> listaLocalidades(String codigoIne);
}
