package org.gestoresmadrid.core.mensajes.procesos.model.dao;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.mensajes.procesos.model.vo.MensajesProcesosVO;
import org.gestoresmadrid.core.model.dao.GenericDao;

public interface MensajesProcesosDao extends GenericDao<MensajesProcesosVO>, Serializable {

	List<MensajesProcesosVO> getListaMensajesProcesos();

	MensajesProcesosVO getMensajeByCodigo(String codigo);
}
