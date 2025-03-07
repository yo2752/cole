package org.gestoresmadrid.core.consultaKo.model.dao;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.consultaKo.model.vo.ConsultaKoVO;
import org.gestoresmadrid.core.model.dao.GenericDao;

public interface ConsultaKoDao extends GenericDao<ConsultaKoVO>, Serializable{

	List<ConsultaKoVO> getMatriculaKo();

	List<ConsultaKoVO> getListaConsultasKOPorIds(Long[] idsConsultaKO);

	ConsultaKoVO getConsultaKo(Long id);

	List<ConsultaKoVO> getListaPorEstadoDoc(String estadoDocKO);

	List<ConsultaKoVO> getListaPorContrato(Long idContrato, String estadoDoc);

	List<ConsultaKoVO> getListaKoPorEstado(String estadoKo);

}
