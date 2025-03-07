package org.gestoresmadrid.core.conductor.model.dao;

import java.io.Serializable;

import org.gestoresmadrid.core.conductor.model.vo.ConsultaConductorVO;
import org.gestoresmadrid.core.model.dao.GenericDao;

public interface ConsultaConductorDao  extends Serializable, GenericDao<ConsultaConductorVO>{
	
	public static final String horaFInDia = "23:59";
	public static final int N_SEGUNDOS = 59;



	ConsultaConductorVO getConsultaConductor();



}
