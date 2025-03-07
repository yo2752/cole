package org.gestoresmadrid.oegam2comun.materiales.model.service;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.trafico.materiales.model.vo.StockMaterialVO;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean.StockMaterialBean;

public interface ServicioStockMateriales extends Serializable{
	List<StockMaterialBean> convertirListaEnBeanPantalla(List<StockMaterialVO> listaVo);
	boolean actualizarUnidades(StockMaterialBean stockMaterialBean);
	boolean crearElemento(StockMaterialBean stockMaterialBean);
	void eliminar(Long id);
	Long cantidadStockPorTipo(String jefatura, String tipo);
	void descontarStock(String jefatura, String tipo, Long cantidadExpediente);
	List<StockMaterialBean> findAll();
	String[] obtenerTodosTiposMaterial();
}
