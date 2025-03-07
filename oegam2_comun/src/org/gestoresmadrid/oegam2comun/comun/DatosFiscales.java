package org.gestoresmadrid.oegam2comun.comun;

import java.io.Serializable;
import java.math.BigDecimal;

public interface DatosFiscales extends Serializable {
	
	BeanDatosFiscales obtenerDatosFiscales(BigDecimal numExpediente);
	
	BeanDatosFiscales obtenerDatosFiscalesDelColegiado(String numColegiado);
}
