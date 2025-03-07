package org.gestoresmadrid.core.trafico.canjes.model.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.trafico.canjes.model.vo.CanjesVO;
import org.gestoresmadrid.core.trafico.duplicado.permiso.conducir.model.vo.DuplicadoPermisoConducirVO;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.hibernate.Criteria;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.hibernate.transform.Transformers;
import org.hibernate.type.IntegerType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CanjesDaoImpl extends GenericDaoImplHibernate<CanjesVO> implements CanjesDao{

	private static final long serialVersionUID = -2133367867840028269L;
	
	
	@Autowired
	UtilesFecha utilesFecha;


	@SuppressWarnings("unchecked")
	@Override
	public String generarIdCanje() throws Exception {
	Criteria criteria = getCurrentSession().createCriteria(CanjesVO.class);

	  criteria.setProjection(Projections.sqlProjection(
		        "MAX(TO_NUMBER(DOC_CANJE_ID)) as maxDocIdCanje",
		        new String[]{"maxDocIdCanje"},
		        new IntegerType[]{new IntegerType()}
		    ));
	  criteria.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
	    Map<String, Object> result = (Map<String, Object>) criteria.uniqueResult();
	    Integer maxId = (Integer) result.get("maxDocIdCanje");
	    if (maxId == null) {
	        maxId = 1;
	    } else {
	        maxId++;
	    }
	    return maxId.toString();
	}


	@Override
	public CanjesVO buscarCanjePorId(String dninie, String numColegiado) {
		Criteria criteria = getCurrentSession().createCriteria(CanjesVO.class);
		criteria.add(Restrictions.eq("dniNie", dninie));
		criteria.add(Restrictions.eq("numColegiado", numColegiado));
		Calendar calendar = Calendar.getInstance();
		Conjunction and = Restrictions.conjunction();	
		calendar.setTime(new Date());
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 0, 0, 0);
		Date fecMin = calendar.getTime();
		and.add(Restrictions.ge("fechaAlta", fecMin));
		calendar.setTime(new Date());
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 23, 59, 59);
		Date fecMax = calendar.getTime();
		and.add(Restrictions.lt("fechaAlta", fecMax));
		criteria.add(and);
		return (CanjesVO) criteria.uniqueResult();
	}

}
