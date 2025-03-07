package org.gestoresmadrid.core.general.model.dao;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.general.model.vo.AplicacionVO;
import org.gestoresmadrid.core.model.dao.GenericDao;

public interface AplicacionDao extends GenericDao<AplicacionVO>, Serializable {

	boolean tieneAplicacion(Long idContrato, String codigoAplicacion);

	List<AplicacionVO> getAplicacionByCodigo(String codigoAplicacion);

	List<AplicacionVO> getAplicaciones();

	AplicacionVO getAplicacionPorCodigo(String codAplicacion);
}
