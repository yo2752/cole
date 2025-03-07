package org.gestoresmadrid.oegamComun.direccion.service;

import java.io.Serializable;

import org.gestoresmadrid.core.direccion.model.vo.DireccionVO;
import org.gestoresmadrid.core.direccion.model.vo.MunicipioSitesVO;
import org.gestoresmadrid.core.direccion.model.vo.MunicipioVO;
import org.gestoresmadrid.core.direccion.model.vo.ProvinciaVO;
import org.gestoresmadrid.core.direccion.model.vo.TipoViaVO;

public interface ServicioPersistenciaDireccion extends Serializable{

	DireccionVO getDireccion(Long idDireccion);

	DireccionVO guardarOActualizar(DireccionVO direccion);

	Long guardar(DireccionVO direccionVO);

	void evict(DireccionVO direccionVO);

	TipoViaVO getTipoVia(String idTipoVia);

	ProvinciaVO getProvincia(String idProvincia);

	MunicipioVO getMunicipio(String idMunicipio, String idProvincia);

	MunicipioSitesVO getMunicipioSites(String idMunicipio, String idProvincia);

}
