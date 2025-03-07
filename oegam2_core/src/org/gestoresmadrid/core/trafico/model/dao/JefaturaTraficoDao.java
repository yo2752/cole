package org.gestoresmadrid.core.trafico.model.dao;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.trafico.model.vo.JefaturaTraficoVO;

public interface JefaturaTraficoDao extends GenericDao<JefaturaTraficoVO>, Serializable {

	JefaturaTraficoVO getJefatura(String jefaturaProvincial);

	JefaturaTraficoVO getJefaturaPorDescripcion(String descripcion);

	List<JefaturaTraficoVO> listadoJefaturas(String idProvincia);

	List<JefaturaTraficoVO> getJefaturasPorContrato(Long idContrato);
}
