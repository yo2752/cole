package org.gestoresmadrid.core.registradores.model.dao;

import java.io.Serializable;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.registradores.model.vo.CuadroAmortizacionVO;




public interface CuadroAmortizacionDao extends GenericDao<CuadroAmortizacionVO>, Serializable {
	
	public CuadroAmortizacionVO getCuadroAmortizacion(String id);
	
}
