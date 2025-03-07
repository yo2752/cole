package org.gestoresmadrid.core.consultaDev.model.dao;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.consultaDev.model.vo.SuscripcionDevVO;
import org.gestoresmadrid.core.model.dao.GenericDao;

public interface SuscripcionDevDao extends Serializable, GenericDao<SuscripcionDevVO>{

	SuscripcionDevVO getSuscripcionDevPorId(Long idSuscripcion);

	//SuscripcionDevVO getTodasSuscripcionesDevPorId(Long idSuscripcion);

	List<SuscripcionDevVO> getTodasSuscripcionesDevPorIdConsultaDev(Long idConsultaDev);

}
