package org.gestoresmadrid.core.mensajeErrorServicio.model.dao;

import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.mensajeErrorServicio.model.vo.MensajeErrorServicioVO;
import org.gestoresmadrid.core.model.dao.GenericDao;

import utilidades.estructuras.FechaFraccionada;

public interface MensajeErrorServicioDao extends GenericDao<MensajeErrorServicioVO>{

	List<MensajeErrorServicioVO> getListaMensajeErrorServicio(Date fecha);

	List<MensajeErrorServicioVO> getListaMensajeErrorServicio(FechaFraccionada fecha);

}
