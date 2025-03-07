package org.gestoresmadrid.oegamImportacion.direccion.service;
        
import java.io.Serializable;

import org.gestoresmadrid.core.paises.model.vo.PaisVO;

public interface ServicioPaisImportacion extends Serializable {

	PaisVO getIdPaisPorSigla(String paisbaja);
	

}
