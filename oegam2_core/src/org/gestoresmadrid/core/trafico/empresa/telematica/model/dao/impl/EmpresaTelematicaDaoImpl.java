package org.gestoresmadrid.core.trafico.empresa.telematica.model.dao.impl;

import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.trafico.empresa.telematica.model.dao.EmpresaTelematicaDao;
import org.gestoresmadrid.core.trafico.empresa.telematica.model.enumerado.EstadoEmpresaTelematica;
import org.gestoresmadrid.core.trafico.empresa.telematica.model.vo.EmpresaTelematicaVO;
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;


@Repository
public class EmpresaTelematicaDaoImpl extends GenericDaoImplHibernate<EmpresaTelematicaVO> implements EmpresaTelematicaDao {

	private static final long serialVersionUID = -6131081540900385397L;

	private static final ILoggerOegam LOG = LoggerOegam.getLogger(EmpresaTelematicaDaoImpl.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public List<EmpresaTelematicaVO> getListaEmpresas(String nombreEmpresa, String cifEmpresa, String codPostal,
			String numColegiado, Long idContrato, String idMunicipio, String idProvincia) {
		
		Criteria criteria = getCurrentSession().createCriteria(EmpresaTelematicaVO.class);
		try{
			if(nombreEmpresa!=null && !nombreEmpresa.equals("") && !nombreEmpresa.isEmpty()){
				criteria.add(Restrictions.eq("empresa", nombreEmpresa));
			}
			if(cifEmpresa !=null && !cifEmpresa.equals("") && !cifEmpresa.isEmpty()){
				criteria.add(Restrictions.eq("cifEmpresa", cifEmpresa));
			}
			if(codPostal !=null && !codPostal.equals("") && !codPostal.isEmpty()){
				criteria.add(Restrictions.eq("codigoPostal", codPostal));
			}
			if(nombreEmpresa!=null && !nombreEmpresa.equals("") && !nombreEmpresa.isEmpty()){
				criteria.add(Restrictions.eq("empresa", nombreEmpresa.toUpperCase()));
			}
			if(numColegiado!=null && !numColegiado.equals("") && !numColegiado.isEmpty()){
				criteria.add(Restrictions.eq("numColegiado", numColegiado));
			}
			if(idContrato!=null){
				criteria.add(Restrictions.eq("idContrato", idContrato));
			}
			if(idMunicipio!=null && !idMunicipio.equals("") && !idMunicipio.isEmpty()){
				criteria.add(Restrictions.eq("municipio", idMunicipio));
			}
			if(idProvincia != null && !idProvincia.isEmpty()){
				criteria.add(Restrictions.eq("provincia", idProvincia));
			}
			
			criteria.add(Restrictions.eq("estado", new Long(EstadoEmpresaTelematica.Activo.getValorEnum())));

		
			return (List<EmpresaTelematicaVO>) criteria.list();	
		}catch(Exception e){
			LOG.error("Ha sucedido un error al consultar empresas telemáticas");
		}
		return null;
	}

	@Override
	public EmpresaTelematicaVO getEmpresa(String id) {
		Criteria criteria =  getCurrentSession().createCriteria(EmpresaTelematicaVO.class);
		if (id != null && !id.isEmpty()) {
			criteria.add(Restrictions.eq("id", new Long(id)));
		}
		criteria.createAlias("contratoVO", "contrato", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("municipioVO", "municipio", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("provinciaVO", "provincia", CriteriaSpecification.LEFT_JOIN);
		return (EmpresaTelematicaVO) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EmpresaTelematicaVO> getListaEmpresasContrato(Long idContrato) {
		Criteria criteria = getCurrentSession().createCriteria(EmpresaTelematicaVO.class);
		criteria.add(Restrictions.eq("idContrato", idContrato));
		criteria.add(Restrictions.eq("estado", BigDecimal.ONE.longValue()));
		return criteria.list();
	}
	
}
