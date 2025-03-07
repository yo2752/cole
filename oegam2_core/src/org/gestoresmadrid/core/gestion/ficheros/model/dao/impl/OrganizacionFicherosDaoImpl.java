package org.gestoresmadrid.core.gestion.ficheros.model.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.gestion.ficheros.model.dao.OrganizacionFicherosDao;
import org.gestoresmadrid.core.gestion.ficheros.model.vo.OrganizacionFicherosVO;
import org.gestoresmadrid.core.model.beans.AliasQueryBean;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

/**
 * Implementaci�n del DAO de documentos base.
 * 
 * @author ext_ihgl
 *
 */

@Repository
public class OrganizacionFicherosDaoImpl extends GenericDaoImplHibernate<OrganizacionFicherosVO> implements OrganizacionFicherosDao {
		

	/* INICIO DECLARACI�N DE CONSTANTES */
	
	private static final ILoggerOegam log = LoggerOegam.getLogger(OrganizacionFicherosDaoImpl.class);

	/* FIN DECLARACI�N DE CONSTANTES */
	
	
	/**************************************************************** INICIO M�TODOS ************************************************************************/

	/**
	 * @see org.gestoresmadrid.core.gestion.ficheros.model.dao.OrganizacionFicherosDao#consultarInformacionTipoDocumento(java.lang.String, java.util.Date)
	 */
	@Override
	public OrganizacionFicherosVO consultarInformacionTipoDocumento(String tipoDocumento, Date fechaConsulta) {

		OrganizacionFicherosVO organizacionFicherosVO = null;
		
        /* INICIO PROYECTIONS */

		ProjectionList listaProyection = null;
		
        /* FIN PROYECTIONS */


        /* INICIO ALIAS */
		
		List<AliasQueryBean> listaAlias = null;
    	
		/* FIN ALIAS */


		/* INICIO RESTRICTIONS */

		List<Criterion> listaCriterion = new ArrayList<Criterion>();
		listaCriterion.add(Restrictions.eq("tipoDocumento", tipoDocumento));
		listaCriterion.add(Restrictions.le("fechaInicio", fechaConsulta));
		listaCriterion.add(Restrictions.disjunction()
							.add(Restrictions.isNull("fechaFin"))
							.add(Restrictions.gt("fechaFin", fechaConsulta)));
		
		/* FIN RESTRICTIONS */			


		// Consultamos la informaci�n de almacenamiento
		List<?> result = buscarPorCriteria(listaCriterion, listaAlias, listaProyection);
		if (result != null && !result.isEmpty()) {
			organizacionFicherosVO = (OrganizacionFicherosVO) result.get(0);
		}

		return organizacionFicherosVO;

	}

	/**************************************************************** FIN M�TODOS ************************************************************************/
	
	
	/*********************************************************** INICIO M�TODOS PRIVADOS **************************************************************/
	
	
	/*********************************************************** FIN M�TODOS PRIVADOS **************************************************************/

}