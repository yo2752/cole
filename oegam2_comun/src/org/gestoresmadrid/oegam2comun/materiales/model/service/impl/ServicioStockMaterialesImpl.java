package org.gestoresmadrid.oegam2comun.materiales.model.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.docPermDistItv.model.enumerados.TipoDistintivo;
import org.gestoresmadrid.core.model.enumerados.TipoMaterial;
import org.gestoresmadrid.core.trafico.materiales.model.dao.EvolucionStockMaterialDao;
import org.gestoresmadrid.core.trafico.materiales.model.dao.StockMaterialDao;
import org.gestoresmadrid.core.trafico.materiales.model.vo.EvolucionStockMaterialesVO;
import org.gestoresmadrid.core.trafico.materiales.model.vo.StockMaterialVO;
import org.gestoresmadrid.oegam2comun.materiales.model.service.ServicioStockMateriales;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean.StockMaterialBean;
import org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.ServicioConsultaStock;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class ServicioStockMaterialesImpl implements ServicioStockMateriales{

	private static final long serialVersionUID = -1577002620590816320L;
	
	@Autowired
	Conversor conversor;
	
	@Autowired
	StockMaterialDao stockMaterialDao;
	
	@Autowired
	ServicioConsultaStock servicioConsultaStock;
	
	@Autowired
	EvolucionStockMaterialDao evolucionStockMaterialDao;

	@Override
	public List<StockMaterialBean> convertirListaEnBeanPantalla(List<StockMaterialVO> lista) {
		List<StockMaterialBean> listaBean = null;
		if(lista != null && !lista.isEmpty()){
			listaBean = new ArrayList<StockMaterialBean>();
			for(StockMaterialVO stockMaterialVO : lista){
				StockMaterialBean stockMaterialBean= conversor.transform(stockMaterialVO, StockMaterialBean.class);
				stockMaterialBean.setJefatura(stockMaterialVO.getJefaturaProvincial().getDescripcion());
				stockMaterialBean.setUnidades(stockMaterialVO.getUnidades().toString());
				stockMaterialBean.setTipoMaterial(TipoMaterial.convertirTexto(stockMaterialVO.getTipoMaterial()));
				listaBean.add(stockMaterialBean);
			}
		}else {
			listaBean = Collections.emptyList();
		}
		return listaBean;
	}
	
	@Transactional
	@Override
	public boolean actualizarUnidades(StockMaterialBean stockMaterialBean) {
		if(stockMaterialBean!= null) {
			StockMaterialVO vo = stockMaterialDao.getElementById(stockMaterialBean.getId());
			EvolucionStockMaterialesVO evolucionVO = new EvolucionStockMaterialesVO();
			BigDecimal sumaUnidades = vo.getUnidades().add(new BigDecimal(stockMaterialBean.getUnidades()));
			//Insertamos las unidades anteriores y actuales en Evolucion Stock Materiales
			evolucionVO.setIdStock(stockMaterialBean.getId());
			evolucionVO.setUnidadesAnteriores(vo.getUnidades());
			evolucionVO.setUsuario(stockMaterialBean.getIdUsuario());
			evolucionVO.setUnidadesActuales(sumaUnidades);
			evolucionVO.setFecha_recarga(new Date());
			evolucionVO.setOperacion("RC");
			//Seteamos las unidades actuales al Stock
			vo.setUnidades(sumaUnidades);
			evolucionStockMaterialDao.guardar(evolucionVO);
			stockMaterialDao.actualizar(vo);
			return true;
		}
		return false;
	}
	
	private StockMaterialVO convertirBeanToVo(StockMaterialBean bean) {
		StockMaterialVO vo = new StockMaterialVO();
		if(bean.getJefatura() != null) {
			vo.setJefatura(bean.getJefatura());
		}
		if(bean.getTipoMaterial() != null) {
			vo.setTipoMaterial(bean.getTipoMaterial());	
		}
		if(bean.getUnidades()!= null) {
			vo.setUnidades(new BigDecimal(bean.getUnidades()));
		}
		return vo;
	}
	
	private EvolucionStockMaterialesVO convertirStockToEvolucionStock(StockMaterialBean stockMaterialBean) {
		EvolucionStockMaterialesVO eVo = new EvolucionStockMaterialesVO();
		eVo.setIdStock(stockMaterialBean.getId());
		//Cuando se crea un Material, se Asigna por defecto 0
		eVo.setUnidadesAnteriores(BigDecimal.ZERO);
		eVo.setUnidadesActuales(new BigDecimal(stockMaterialBean.getUnidades()));
		eVo.setUsuario(stockMaterialBean.getIdUsuario());
		eVo.setFecha_recarga(new Date());
		eVo.setOperacion("CR");
		return eVo;
	}
	
	@Transactional
	@Override
	public boolean crearElemento(StockMaterialBean stockMaterialBean) {
		if(stockMaterialBean != null) {
			StockMaterialVO vo = convertirBeanToVo(stockMaterialBean);
			if(stockMaterialDao.comprobarExistente(vo) == null){
				stockMaterialDao.guardar(vo);
				stockMaterialBean.setId(vo.getId());
				EvolucionStockMaterialesVO eVo= convertirStockToEvolucionStock(stockMaterialBean);
				evolucionStockMaterialDao.guardar(eVo);
				return true;
			}
		}
		return false;
	}

	@Transactional
	@Override
	public void eliminar(Long id) {
		StockMaterialVO vo = stockMaterialDao.getElementById(id);
		if(vo != null) {
			stockMaterialDao.borrar(vo);
			evolucionStockMaterialDao.eliminarByIdStock(id);
		}
	}

	@Override
	public Long cantidadStockPorTipo(String jefatura, String tipo) {
		StockMaterialVO vo = stockMaterialDao.findByJefaturaTipo(jefatura, tipo);
		if(vo != null) {
			Long unidades = Long.parseLong(vo.getUnidades().toString());
			if(unidades != 0 || unidades != null) {
				return unidades;
			}
		}
		return null;
	}

	@Override
	@Transactional
	public void descontarStock(String jefatura, String tipo, Long cantidadExpediente) {
		StockMaterialVO vo = stockMaterialDao.findByJefaturaTipo(jefatura, tipo);
		if(vo!=null) {
			vo.setUnidades(vo.getUnidades().subtract(BigDecimal.valueOf(cantidadExpediente)));
		}
		
	}

	@Override
	@Transactional
	public List<StockMaterialBean> findAll() {
		List<StockMaterialBean> lista = convertirListaEnBeanPantalla(stockMaterialDao.buscar(null));
		return lista;
	}
	
	
	@Override
	@Transactional(readOnly=true)
	public String[] obtenerTodosTiposMaterial() {
		List<StockMaterialVO> materiales = stockMaterialDao.buscar(null);
		String[] tipos = new String[materiales.size()];
		int index = 0;
		for(StockMaterialVO item: materiales) {
			if ("PC".equals(item.getTipoMaterial())) {
				tipos[index++] = item.getTipoMaterial();
			} else {
				TipoDistintivo tipoDistintivo = TipoDistintivo.convertir(item.getTipoMaterial());
				tipos[index++] = tipoDistintivo.getNombreEnum();
			}
		}
		return tipos;
	}
	

}
