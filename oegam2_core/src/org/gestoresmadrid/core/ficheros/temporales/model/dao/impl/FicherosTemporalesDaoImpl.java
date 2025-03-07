package org.gestoresmadrid.core.ficheros.temporales.model.dao.impl;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.ficheros.temporales.model.dao.FicherosTemporalesDao;
import org.gestoresmadrid.core.ficheros.temporales.model.vo.FicherosTemporalesVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;


@Repository
public class FicherosTemporalesDaoImpl extends GenericDaoImplHibernate<FicherosTemporalesVO> implements FicherosTemporalesDao{

	private static final long serialVersionUID = 5357471677287695702L;
	
	
	@SuppressWarnings("unchecked")
	public List<FicherosTemporalesVO> getFicherosTemporales(Long idFichero, String nombre, String extension, Date fecha, String tipoDoc, String subTipoDoc,
			String numColegiado, Long idContrato, Long idUsuario){
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(FicherosTemporalesVO.class);
		
		if(idFichero != null){
			criteria.add(Restrictions.eq("idFicheroTemporal", idFichero));
		}
		
		if(nombre != null){
			criteria.add(Restrictions.eq("nombre", nombre));
		}
		
		if(extension != null){
			criteria.add(Restrictions.eq("extension", extension));
		}
		
		if(fecha != null){
			criteria.add(Restrictions.ge("fecha", fecha));
		}
		
		if(tipoDoc != null){
			criteria.add(Restrictions.eq("tipoDocumento", tipoDoc));
		}
		
		if(subTipoDoc != null){
			criteria.add(Restrictions.eq("subTipoDocumento", subTipoDoc));
		}
		
		if(numColegiado != null){
			criteria.add(Restrictions.eq("numColegiado", numColegiado));
		}
		
		if(idContrato != null){
			criteria.add(Restrictions.eq("idContrato", idContrato));
		}
		
		if(idUsuario != null){
			criteria.add(Restrictions.eq("idUsuario", idUsuario));
		}
		
		return criteria.list();
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<FicherosTemporalesVO> getFicheroPorId(FicherosTemporalesVO ficherosTemporalesVO) {
		if(ficherosTemporalesVO != null && ficherosTemporalesVO.getIdFicheroTemporal() != null){
			return getFicherosTemporales(ficherosTemporalesVO.getIdFicheroTemporal(),null,null,null,null,null,null,null,null);
		}else{
			return Collections.EMPTY_LIST;
		}
	}

}
