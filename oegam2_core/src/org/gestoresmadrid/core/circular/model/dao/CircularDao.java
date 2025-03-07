package org.gestoresmadrid.core.circular.model.dao;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.circular.model.vo.CircularVO;
import org.gestoresmadrid.core.model.dao.GenericDao;

public interface CircularDao extends GenericDao<CircularVO>, Serializable{

	CircularVO getCircular(Long id);

	List<CircularVO> getListaCircularesActivas();

	List<CircularVO> getListaCircularesCaducadas();

	List<CircularVO> getListaCircularesRepeticion();

	List<CircularVO> getListaCircularesActivasFecha();

}
