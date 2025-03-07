package org.gestoresmadrid.core.trafico.eeff.model.dao;

import java.math.BigDecimal;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.trafico.eeff.model.vo.EeffConsultaVO;
import org.hibernate.HibernateException;



/**
 * 
 * @author Globaltms
 *
 */
public interface ConsultaDao extends GenericDao<EeffConsultaVO> {
	
	

	/**
	 * 
	 * @param eeffConsulta
	 */
	BigDecimal guardarConsulta(EeffConsultaVO  eeffConsulta) throws HibernateException;
	
	
	/**
	 * 
	 * @param numColegiado
	 * @return
	 * @throws HibernateException
	 */
	BigDecimal generarNumExpediente(String numColegiado);


	EeffConsultaVO consultarDesdeExpedienteLiberacion(BigDecimal numExpediente);
	

}
