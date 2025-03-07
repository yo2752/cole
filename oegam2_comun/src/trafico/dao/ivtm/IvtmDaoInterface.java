package trafico.dao.ivtm;
//TODO MPC. Cambio IVTM. Clase a�adida.
import java.math.BigDecimal;

public interface IvtmDaoInterface {
	
	/**
	 * Genera el id de una petici�n Ivtm, sacando el m�ximo entre las peticiones de alta, modificaci�n 
	 * y de consulta
	 * @param numColegiado
	 * @return
	 */
	public BigDecimal generarIdPeticion(String numColegiado); 

}
