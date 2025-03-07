package org.gestoresmadrid.oegam2comun.paises.model.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.oegam2comun.paises.view.dto.PaisDto;

public interface ServicioPais extends Serializable {

	PaisDto getPais(String idPais);

	PaisDto getIdPaisPorSigla(String sigla);

	List<PaisDto> listaPaises(BigDecimal tipoPais);

	PaisDto getPaisPorSiglasImportDgt(String siglasPais);

	List<PaisDto> paises();

	List<PaisDto> paisesPorNombre(String pais);
}
