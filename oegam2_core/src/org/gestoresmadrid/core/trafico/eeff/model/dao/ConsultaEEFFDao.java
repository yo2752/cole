package org.gestoresmadrid.core.trafico.eeff.model.dao;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.trafico.eeff.model.vo.ConsultaEEFFVO;

public interface ConsultaEEFFDao extends GenericDao<ConsultaEEFFVO>, Serializable{

	public static final String horaFInDia = "23:59";
	public static final int N_SEGUNDOS = 59;
	
	ConsultaEEFFVO getConsultaEEFFPorNumExpediente(BigDecimal numExpediente, Boolean consultaCompleta);

	String generarNumExpediente(BigDecimal numColegiado) throws Exception;

}
