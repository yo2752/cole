package trafico.dao.ivtm;
//TODO MPC. Cambio IVTM. Clase añadida.
import java.math.BigDecimal;

import org.gestoresmadrid.oegam2comun.trafico.dto.ivtm.IvtmDatosEntradaMatriculasWS;
import org.gestoresmadrid.oegam2comun.trafico.dto.ivtm.IvtmResultadoWSMatriculasWS;

import hibernate.entities.trafico.IvtmMatriculacion;
import trafico.dao.GenericDao;

public interface IvtmMatriculacionDaoInterface extends GenericDao<IvtmMatriculacion>{

	public BigDecimal recuperarIdPeticion (BigDecimal numExpediente);

	/** 
	 * Método utilizado para MatriculasWS
	 */
	public IvtmResultadoWSMatriculasWS[] recuperarMatriculas (IvtmDatosEntradaMatriculasWS datosEntrada);
	
}
