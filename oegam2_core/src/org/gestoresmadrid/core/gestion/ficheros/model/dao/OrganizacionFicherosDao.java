package org.gestoresmadrid.core.gestion.ficheros.model.dao;

import java.util.Date;

import org.gestoresmadrid.core.gestion.ficheros.model.vo.OrganizacionFicherosVO;
import org.gestoresmadrid.core.model.dao.GenericDao;


/**
 * Interfaz del DAO de gestion de ficheros.
 * 
 * @author ext_ihgl
 *
 */
public interface OrganizacionFicherosDao extends GenericDao<OrganizacionFicherosVO> {
	

	/* INICIO M�TODOS */
	
	/**
	 * M�todo que consulta la informaci�n de almacenamiento de los ficheros para un tipo de documento y una fecha.
	 * 
	 * @param tipoDocumento tipo de documento.
	 * @param fechaConsulta fecha de consulta.
	 * @return OrganizacionFicherosVO - informaci�n de almacenamiento de los ficheros para ese tipo de documento y fecha.
	 */
	public OrganizacionFicherosVO consultarInformacionTipoDocumento(String tipoDocumento, Date fechaConsulta);
	
	/* FIN M�TODOS */

}
