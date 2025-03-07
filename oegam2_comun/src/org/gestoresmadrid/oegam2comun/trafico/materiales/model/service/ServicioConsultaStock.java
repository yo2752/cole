package org.gestoresmadrid.oegam2comun.trafico.materiales.model.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.core.trafico.materiales.model.vo.MaterialStockVO;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean.ResultadoPermisoDistintivoItvBean;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.beans.StockResultadosBean;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.beans.rest.StockInfoRest;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.dto.MaterialStockDto;

import escrituras.beans.ResultBean;

public interface ServicioConsultaStock extends Serializable {

	List<StockResultadosBean> convertirListaEnBeanPantalla(List<MaterialStockVO> lista);
	void gestionarMaterialStock(String jefatura, Long material);
	MaterialStockVO obtenerStockInventario(Long stockInventario);
	List<StockResultadosBean> obtenerStock();
	boolean hayStock(String jefatura, String tipo, Long unidadesDeseadas);
	MaterialStockVO obtenerStockByPrimaryKey(Long materialStockId);
	
	MaterialStockVO obtenerStock(String jefatura, Long materialId);
	List<MaterialStockVO> obtenerStockToUpdate();
	Long cantidadStock(String jefatura, Long materialId);
	Long cantidadStockPorTipo(String jefatura, String tipo);
	void descontarStock(String jefatura, String tipo, Long cantidadExpediente);
	MaterialStockDto getStockDto(Long stockId);
	MaterialStockDto getDtoFromInfoRest(StockInfoRest stockInfoRest);
	MaterialStockVO getVOFromDto(MaterialStockDto materialStockDto);
	MaterialStockVO getVOFromInfoRest(StockInfoRest stockInfoRest);
	MaterialStockDto getDtoFromVO(MaterialStockVO materialStockVO);
	ResultBean enviarCorreoEnvioStock(ArrayList<String> listaAvisoStockEnviar);
	ResultadoPermisoDistintivoItvBean aniadirStock(String jefatura, String tipo, Long cantidad);

}
