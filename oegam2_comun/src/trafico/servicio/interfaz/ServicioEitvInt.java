package trafico.servicio.interfaz;

import java.io.FileNotFoundException;
import java.math.BigDecimal;

import org.gestoresmadrid.core.model.beans.ColaBean;


import com.gescogroup.blackbox.EitvQueryWSResponse;

public interface ServicioEitvInt {

	void generarSolicitudXmlEitv(BigDecimal numExpediente);

	void guardarXmlRespuesta(EitvQueryWSResponse respuestaWS, String numExpediente);
	
	String recogerXmlEitv(ColaBean solicitud) throws FileNotFoundException;

}
