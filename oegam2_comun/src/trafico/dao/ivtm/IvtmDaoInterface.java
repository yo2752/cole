package trafico.dao.ivtm;
//TODO MPC. Cambio IVTM. Clase añadida.
import java.math.BigDecimal;

public interface IvtmDaoInterface {
	
	/**
	 * Genera el id de una petición Ivtm, sacando el máximo entre las peticiones de alta, modificación 
	 * y de consulta
	 * @param numColegiado
	 * @return
	 */
	public BigDecimal generarIdPeticion(String numColegiado); 

}
