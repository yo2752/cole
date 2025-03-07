package org.gestoresmadrid.oegam2comun.trafico.materiales.model.service;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;

import org.gestoresmadrid.core.trafico.materiales.model.vo.MaterialVO;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.beans.MaterialesResultadosBean;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.beans.rest.MaterialInfoRest;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.dto.ComboMaterialDto;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.dto.MaterialDto;

public interface ServicioConsultaMateriales extends Serializable {

	List<MaterialesResultadosBean> convertirListaEnBeanPantalla(List<MaterialVO> lista);
	List<ComboMaterialDto> getMateriales(); 
	MaterialVO getMaterialForPrimaryKey(Long materialId);
	MaterialVO getMaterialForTipo(String tipo);
	List<MaterialVO> obtenerTodos();
	String[] obtenerTodosTiposMaterial();
	LinkedHashMap<Long, String> getTipoDistintivo();
	LinkedHashMap<Long, String> getPermisoConducir(); 

	MaterialDto getDtoFromInfoRest(MaterialInfoRest materialInfoRest);
	MaterialDto getDtoFromVO(MaterialVO materialVO);
	MaterialVO  getVOFromDto(MaterialDto materialDto);
	MaterialVO getVOFromInfoRest(MaterialInfoRest materialInfoRest);
	MaterialDto obtenerMaterialForPrimaryKey(Long materialId);
}
