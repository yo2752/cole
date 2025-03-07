package org.gestoresmadrid.core.trafico.testra.model.dao;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.trafico.testra.model.vo.ConsultaTestraVO;

public interface ConsultaTestraDao extends GenericDao<ConsultaTestraVO>, Serializable {

	List<ConsultaTestraVO> getConsultas(String dato, String numColegiado);

	List<String> getNumerosColegiados();

	List<ConsultaTestraVO> getConsultasByColegiado(String numColegiado);

	ConsultaTestraVO getConsultaById(Long id);

	ConsultaTestraVO getConsultaTestraPorTipoDatoNumColegiado(String tipo, String dato, String numColegiado);
}
