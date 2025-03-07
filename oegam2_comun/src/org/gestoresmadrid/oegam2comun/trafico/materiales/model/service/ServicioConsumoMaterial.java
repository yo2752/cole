package org.gestoresmadrid.oegam2comun.trafico.materiales.model.service;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.gestoresmadrid.core.trafico.materiales.model.values.ConsumoMaterialValue;
import org.gestoresmadrid.core.trafico.model.vo.JefaturaTraficoVO;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.dto.ConsumoMaterialDto;

public interface ServicioConsumoMaterial extends Serializable {
	HashMap<String, List<ConsumoMaterialDto>> obtenerConsumoMaterial(Date fecha);
	ConsumoMaterialDto getDtoFromValue(ConsumoMaterialValue consumoMaterialValue);
	JefaturaTraficoVO getNombreJefaturaForJefatura(String jefatura);
}
