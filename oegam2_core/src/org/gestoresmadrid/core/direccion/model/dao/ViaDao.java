package org.gestoresmadrid.core.direccion.model.dao;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.direccion.model.vo.ViaVO;
import org.gestoresmadrid.core.model.dao.GenericDao;

public interface ViaDao extends GenericDao<ViaVO>, Serializable {

	ViaVO getVia(String idProvincia, String idMunicipio, String idTipoVia, String via);

	List<String> listadoViasUnicasPorTipoVia(String idProvincia);

	List<ViaVO> listadoVias(String idProvincia, String idMunicipio, String idTipoVia);
}
