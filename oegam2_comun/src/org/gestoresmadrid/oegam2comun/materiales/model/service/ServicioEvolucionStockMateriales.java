package org.gestoresmadrid.oegam2comun.materiales.model.service;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.trafico.materiales.model.vo.EvolucionStockMaterialesVO;
import org.gestoresmadrid.oegam2comun.materiales.view.bean.EvolucionStockMaterialBean;

public interface ServicioEvolucionStockMateriales extends Serializable{
	List<EvolucionStockMaterialBean> convertirEvolucionEnBeanPantalla(List<EvolucionStockMaterialesVO> lista);
	
}
