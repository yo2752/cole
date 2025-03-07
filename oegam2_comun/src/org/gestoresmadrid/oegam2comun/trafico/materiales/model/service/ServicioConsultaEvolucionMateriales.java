package org.gestoresmadrid.oegam2comun.trafico.materiales.model.service;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.trafico.materiales.model.enumerados.EstadoMaterial;
import org.gestoresmadrid.core.trafico.materiales.model.vo.EvolucionMaterialVO;
import org.gestoresmadrid.core.trafico.materiales.model.vo.MaterialVO;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.beans.EvolucionMaterialesResultadosBean;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.dto.EvolucionMaterialDto;

public interface ServicioConsultaEvolucionMateriales extends Serializable {

	List<EvolucionMaterialesResultadosBean> convertirListaEnBeanPantalla(List<EvolucionMaterialVO> lista);
	EvolucionMaterialVO getEvolucionMaterialFromMaterial(MaterialVO materialVO, EstadoMaterial estado);
	EvolucionMaterialDto obtenerMaterialForPrimaryKey(Long evolucionMaterialId);
}
