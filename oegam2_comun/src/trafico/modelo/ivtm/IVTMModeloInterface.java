package trafico.modelo.ivtm;
//TODO MPC. Cambio IVTM. Clase nueva.
import java.math.BigDecimal;

public interface IVTMModeloInterface {

	/**
	 * Obtiene el id Petici�n IVTM
	 * @param numColegiado
	 * @return
	 */
	public BigDecimal generarIdPeticion(String numColegiado);

}