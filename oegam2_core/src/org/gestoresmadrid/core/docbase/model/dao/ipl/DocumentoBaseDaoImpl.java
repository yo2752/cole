package org.gestoresmadrid.core.docbase.model.dao.ipl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.docbase.model.dao.DocumentoBaseDao;
import org.gestoresmadrid.core.docbase.model.vo.DocumentoBaseVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.hibernate.Criteria;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class DocumentoBaseDaoImpl extends GenericDaoImplHibernate<DocumentoBaseVO> implements DocumentoBaseDao{

	private static final long serialVersionUID = -6326920188912276787L;

	@Override
	public DocumentoBaseVO getDocBase(Long idDocBase, Boolean docBaseCompleto) {
		Criteria criteria = getCurrentSession().createCriteria(DocumentoBaseVO.class);
		criteria.add(Restrictions.eq("id", idDocBase));
		if(docBaseCompleto){
			criteria.createAlias("tramitesTrafico", "tramitesTrafico",CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("tramitesTrafico.tasa", "tramitesTraficoTasa",CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("tramitesTrafico.intervinienteTraficos", "tramitesTraficoInterv",CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("tramitesTraficoInterv.persona", "tramitesTraficoIntervPersona",CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("tramitesTrafico.vehiculo", "tramitesTraficoVehiculo",CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("contrato", "contrato",CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("contrato.colegiado", "contratoColegiado", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("contrato.jefaturaTrafico", "contratoJefatura", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("contrato.provincia", "contratoProvincia", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("contratoColegiado.usuario", "contratoColegiadoUsuario", CriteriaSpecification.LEFT_JOIN);
		}
		return (DocumentoBaseVO) criteria.uniqueResult();
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<String> obtenerDocIdMax(Date fecha) {
		Criteria criteria = getCurrentSession().createCriteria(DocumentoBaseVO.class);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		int anio = calendar.get(Calendar.YEAR);
		Calendar calendarInicio = Calendar.getInstance();
		calendarInicio.set(anio, 0, 1, 0, 0, 0);
		Calendar calendarFin = Calendar.getInstance();
		calendarFin.set(anio + 1, 0, 1, 0, 0, 0);
		criteria.add(Restrictions.between("fechaCreacion", calendarInicio.getTime(), calendarFin.getTime()));
		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.property("docId"));
		criteria.setProjection(projectionList);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.addOrder(Order.desc("id"));
		return criteria.list();
	}
	
	@Override
	public DocumentoBaseVO docBasePorColegiadoYFechaP(String numColegiado, Date fechaPresentacion) {
		Criteria criteria = getCurrentSession().createCriteria(DocumentoBaseVO.class);
		criteria.add(Restrictions.eq("contratoColegiado.numColegiado", numColegiado));
		Calendar calendar = Calendar.getInstance();
	    calendar.setTime(fechaPresentacion);
	    calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 0, 0, 0);
	    Date fecMin = calendar.getTime();
	    calendar.setTime(fechaPresentacion);
	    calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 23, 59, 59);
	    Date fecMax = calendar.getTime();
	    Conjunction and = Restrictions.conjunction();
	    and.add(Restrictions.between("fechaPresentacion", fecMin,fecMax) );
	    criteria.add(and);
		criteria.createAlias("contrato", "contrato", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.colegiado", "contratoColegiado", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("tramitesTrafico", "tramitesTrafico",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("tramitesTrafico.tasa", "tramitesTraficoTasa",CriteriaSpecification.LEFT_JOIN);
		return (DocumentoBaseVO) criteria.uniqueResult();
	}
	
	@Override
	public DocumentoBaseVO docBasePorIdMacroExpdiente(String idMacroExpediente) {
		Criteria criteria = getCurrentSession().createCriteria(DocumentoBaseVO.class);
		criteria.add(Restrictions.eq("docId", idMacroExpediente));
		criteria.createAlias("tramitesTrafico", "tramitesTrafico",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato", "contrato",CriteriaSpecification.LEFT_JOIN);
		return (DocumentoBaseVO) criteria.uniqueResult();
	}
	
	@Override
	public DocumentoBaseVO docBasePorIdMacroExpdienteYMatricula(String idMacroExpediente,String matricula) {
		Criteria criteria = getCurrentSession().createCriteria(DocumentoBaseVO.class);
		criteria.add(Restrictions.eq("docId", idMacroExpediente));
		criteria.add(Restrictions.eq("tramitesTraficoVehiculo.matricula", matricula));
		criteria.createAlias("tramitesTrafico", "tramitesTrafico",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("tramitesTrafico.vehiculo", "tramitesTraficoVehiculo",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("tramitesTrafico.tasa", "tramitesTraficoTasa",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato", "contrato",CriteriaSpecification.LEFT_JOIN);
		return (DocumentoBaseVO) criteria.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DocumentoBaseVO> getListaDocBaseWS(Long idContrato, Date fechaDocBase, String docId, String carpeta) {
		Criteria criteria = getCurrentSession().createCriteria(DocumentoBaseVO.class);
		criteria.add(Restrictions.eq("contrato.idContrato", idContrato));
		if(fechaDocBase != null) {
			Calendar calendar = Calendar.getInstance();
		    calendar.setTime(fechaDocBase);
		    calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 0, 0, 0);
		    Date fecMin = calendar.getTime();
		    calendar.setTime(fechaDocBase);
		    calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 23, 59, 59);
		    Date fecMax = calendar.getTime();
		    Conjunction and = Restrictions.conjunction();
		    and.add(Restrictions.between("fechaPresentacion", fecMin, fecMax));
		    criteria.add(and);
		}
		if(docId != null && !docId.isEmpty()) {
			criteria.add(Restrictions.eq("docId", docId));
		}
		if(carpeta != null && !carpeta.isEmpty()) {
			criteria.add(Restrictions.eq("carpeta", carpeta));
		}
		criteria.createAlias("contrato", "contrato", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.colegiado", "contratoColegiado", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("tramitesTrafico", "tramitesTrafico", CriteriaSpecification.LEFT_JOIN);
		return criteria.list();
	}
}
