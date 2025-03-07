package org.gestoresmadrid.core.intervinienteModelos.model.dao;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.intervinienteModelos.model.vo.IntervinienteModelosVO;
import org.gestoresmadrid.core.model.dao.GenericDao;

public interface IntervinienteModelosDao extends GenericDao<IntervinienteModelosVO>,Serializable{

	IntervinienteModelosVO getIntervinientePorNifYNumColegiado(String nif,	String numColegiado);

	IntervinienteModelosVO getIntervinientePorNifYIdRemesaYTipoInterviniente(String nif, Long idRemesa, String tipoInterviniente);

	List<IntervinienteModelosVO> getIntervinientesPorRemesa(Long idRemesa);

	IntervinienteModelosVO getIntervinientePorId(Long idInterviniente);

	IntervinienteModelosVO getIntervinientePorNifYIdModeloYTipoInterviniente(String nif, Long idModelo, String tipoInterviniente);

	List<IntervinienteModelosVO> getListaIntervinientesPorModelo(Long idModelo);

}
