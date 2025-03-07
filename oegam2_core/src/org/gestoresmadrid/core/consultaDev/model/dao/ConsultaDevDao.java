package org.gestoresmadrid.core.consultaDev.model.dao;

import java.io.Serializable;

import org.gestoresmadrid.core.consultaDev.model.vo.ConsultaDevVO;
import org.gestoresmadrid.core.model.dao.GenericDao;

public interface ConsultaDevDao extends Serializable, GenericDao<ConsultaDevVO>{

	ConsultaDevVO getConsultaDevPorId(Long idConsultaDev, Boolean completo);

}
