package org.gestoresmadrid.core.trafico.eeff.model.dao.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.trafico.eeff.model.dao.ConsultaDao;
import org.gestoresmadrid.core.trafico.eeff.model.enumerados.ConstantesEEFF;
import org.gestoresmadrid.core.trafico.eeff.model.vo.EeffConsultaVO;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

/**
 * 
 * @author Globaltms
 *
 */
@Repository
public class ConsultaDaoImpl extends GenericDaoImplHibernate<EeffConsultaVO> implements ConsultaDao {

	private static final long serialVersionUID = -5539262897552783986L;
	private static final ILoggerOegam log = LoggerOegam.getLogger(ConsultaDaoImpl.class);
	
	@Override
	public BigDecimal guardarConsulta(EeffConsultaVO eeffConsulta) throws HibernateException {
		
		return (BigDecimal) guardar(eeffConsulta);
	}
	
	/**
	 * Genera un numero expediente
	 */
	public BigDecimal generarNumExpediente(String numColegiado) {
		Session session = null;
		try{	
			
			String numExpediente = numColegiado;
			Fecha fecha = new Fecha(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
			numExpediente += fecha.getDia()+fecha.getMes()+fecha.getAnio().substring(2);
			numExpediente += ConstantesEEFF.TIPO_ID_EEFF;
			session = getCurrentSession();
		
			Criteria criteria = session.createCriteria(EeffConsultaVO.class);		
			criteria.add(Restrictions.sqlRestriction( " NUM_EXPEDIENTE like " + " '"+numExpediente+"%'"));
			criteria.setProjection(Projections.max("numExpediente"));
					
			return (BigDecimal) criteria.uniqueResult();
		    
		}catch(HibernateException e){
			 log.error("Un error ha ocurrido al generar un Num de expediente\n" + e.getMessage());		
		}
		return null;
	}

	@Override
	public EeffConsultaVO consultarDesdeExpedienteLiberacion(BigDecimal numExpediente) {
		List<Criterion> criterion = new ArrayList<Criterion>();
		criterion.add(Restrictions.eq("numExpedienteTramite", numExpediente));
		List<String> campoOrden = new ArrayList<String>();
		campoOrden.add("fechaRealizacion");
		List<EeffConsultaVO> lista = buscarPorCriteria(0, 0, criterion, ordenDes, campoOrden);
		if (lista ==null || lista.size()==0){
			return null;
		}
		return lista.get(0);
	}
	
}
