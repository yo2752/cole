package org.gestoresmadrid.oegamImportacion.tasa.service;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.core.tasas.model.vo.TasaPegatinaVO;
import org.gestoresmadrid.core.tasas.model.vo.TasaVO;

public interface ServicioTasaImportacion extends Serializable {

	TasaVO getTasa(String codigoTasa, String tipoTasa);

	TasaVO esTasaValida(String codigoTasa, String tipoTasa);

	String asignarTasa(TasaVO tasa, BigDecimal numExpediente, Long idUsuario);
	
	TasaPegatinaVO getTasaPegatina(String codigoTasa, String tipoTasa);
}
