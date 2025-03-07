package org.gestoresmadrid.oegamImportacion.direccion.service;

import java.io.Serializable;

import org.gestoresmadrid.core.direccion.model.vo.ProvinciaVO;

public interface ServicioProvinciaImportacion extends Serializable {

	ProvinciaVO getProvincia(String idProvincia);
	
	ProvinciaVO getProvinciaPorNombre(String nombre);

}
