package org.gestoresmadrid.core.atex5.model.dao;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.core.atex5.model.vo.ConsultaPersonaAtex5VO;
import org.gestoresmadrid.core.model.dao.GenericDao;

public interface ConsultaPersonaAtex5Dao  extends Serializable, GenericDao<ConsultaPersonaAtex5VO>{
	
	public static final String horaFInDia = "23:59";
	public static final int N_SEGUNDOS = 59;

	ConsultaPersonaAtex5VO getConsultaPersonaAtex5PorId(Long idConsultaPersonaAtex5, Boolean tramiteCompleto, Boolean filtrarPorTasa);

	BigDecimal generarNumExpediente(String numColegiado) throws Exception;

	ConsultaPersonaAtex5VO getConsultaPersonaAtex5PorExpediente(BigDecimal numExpediente, Boolean tramiteCompleto, Boolean filtrarPorTasa);

}
