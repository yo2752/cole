package org.gestoresmadrid.core.modelo.bien.model.dao;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.modelo.bien.model.vo.ModeloBienVO;

public interface ModeloBienDao extends GenericDao<ModeloBienVO>,Serializable{

	ModeloBienVO getModeloBienPorId(Long idBien, Long idModelo, boolean completo);

	List<ModeloBienVO> getListaPorIdBien(Long idBien);

	List<ModeloBienVO> getModeloBienPorIdModelo(Long idModelo);

}
