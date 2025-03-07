package org.gestoresmadrid.core.participacion.model.dao;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.participacion.model.vo.ParticipacionVO;

public interface ParticipacionDao extends Serializable,GenericDao<ParticipacionVO>{

	List<ParticipacionVO> getListaParticipacionPorIdRemesa(Long idRemesa, boolean completo);

	List<ParticipacionVO> getListaParticipacionPorIdInterviniente(Long idInterviniente);

	List<ParticipacionVO> getlistaParticipacionPorIdBien(Long idBien);

	ParticipacionVO getParticipacionPorRemesaYBien(Long idRemesa, Long idBien);

	ParticipacionVO getParticipacionPorRemesaEInterviniente(Long idRemesa, Long idInterviniente);

	List<ParticipacionVO> getListaParticipacionRemesaBien(Long idRemesa, Long idBien);
	
	List<ParticipacionVO> getListaParticipacionPorRemesaEInterviniente(Long idRemesa, Long idInterviniente);

}
