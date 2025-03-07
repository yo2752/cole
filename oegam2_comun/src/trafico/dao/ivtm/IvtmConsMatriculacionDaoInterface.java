package trafico.dao.ivtm;
//TODO MPC. Cambio IVTM. Clase añadida.
import hibernate.entities.trafico.IvtmConsultaMatriculacion;

import java.math.BigDecimal;

import trafico.dao.GenericDao;

public interface IvtmConsMatriculacionDaoInterface extends GenericDao<IvtmConsultaMatriculacion> {
	

	/**
	 * 
	 * @param ivtmConsulta
	 * @return
	 */
	public BigDecimal guardarConsulta(IvtmConsultaMatriculacion  ivtmConsulta);
	
}
