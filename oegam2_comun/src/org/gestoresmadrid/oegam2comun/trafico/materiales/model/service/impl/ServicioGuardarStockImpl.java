package org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.gestoresmadrid.core.trafico.materiales.model.dao.MaterialStockDao;
import org.gestoresmadrid.core.trafico.materiales.model.vo.MaterialStockVO;
import org.gestoresmadrid.core.trafico.materiales.model.vo.MaterialVO;
import org.gestoresmadrid.core.trafico.materiales.model.vo.PedidoVO;
import org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.ServicioConsultaMateriales;
import org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.ServicioConsultaPedidos;
import org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.ServicioConsultaStock;
import org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.ServicioGuardarStock;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.dto.MaterialStockDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioGuardarStockImpl implements ServicioGuardarStock {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9046767810147928187L;
	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioGuardarStockImpl.class);

	private static final String MSG_ERROR     = "Stock no actualizado";
	private static final String MSG_SUCCESS   = "Stock actualizado correctamente";

	@Resource  MaterialStockDao materialStockDao;
	
	@Autowired ServicioConsultaStock      servicioConsultaStock;
	@Autowired ServicioConsultaMateriales servicioConsultaMateriales;
	@Autowired ServicioConsultaPedidos    servicioConsultaPedidos;
	
	@Override
	@Transactional
	public ResultBean salvarStock(MaterialStockDto materialStockDto) {
		ResultBean result = new ResultBean(Boolean.FALSE);
		
		try {
			MaterialStockVO origen = materialStockDao.findStockBystockInvId(materialStockDto.getStockInvId());
			
			boolean tengoStock = false;
			if (origen != null) {
				tengoStock = origen.getJefaturaProvincial().equals(materialStockDto.getJefaturaProvincial()) && 
							 origen.getMaterialVO().getMaterialId().equals(materialStockDto.getMaterialId());
				
				if (tengoStock) {
					if (origen.getUnidades().equals(materialStockDto.getStock())) {
						log.info("Stock ya procesado");
					} else {
						origen.setUnidades(materialStockDto.getStock());
						origen.setFecUltRecarga(new Date());
						@SuppressWarnings("unused")
						MaterialStockVO  materialStockVO = materialStockDao.guardarOActualizar(origen);
						
						log.info("Stock guardado correctamente");
					} 
				} 
			} else {
				MaterialStockVO materialStockVO = servicioConsultaStock.getVOFromDto(materialStockDto);
				materialStockVO = materialStockDao.guardarOActualizar(materialStockVO);
				log.info("Stock guardado correctamente");
			}
			
		} catch (Exception ex) {
			result.setError(Boolean.TRUE);
			log.error(ex.getMessage());
		}
		
		return result;
	}

	@Override
	@Transactional
	public ResultBean salvarStock(MaterialStockVO materialStocVO) {
		ResultBean result = new ResultBean(Boolean.FALSE);
		
		try {
			log.info("Salvando Stock....");
			log.info("Salvando Stock ==================================> " + materialStocVO.getStockInvId());
			MaterialVO materialVO = servicioConsultaMateriales.getMaterialForPrimaryKey(materialStocVO.getMaterialVO().getMaterialId());
			materialStocVO.setMaterialVO(materialVO);
			
			MaterialStockVO materialStocVOOld = materialStockDao.findStockBystockInvId(materialStocVO.getStockInvId());
			if (materialStocVOOld != null && materialStocVOOld.equals(materialStocVO)) {
				log.info("Stock " + materialStocVO.getStockInvId() + " ya registrado.");
			} else {
				if (materialStocVOOld == null) {
					materialStocVO = materialStockDao.guardarOActualizar(materialStocVO);
				} else if (!materialStocVOOld.equals(materialStocVO)) {
					log.info("Stock " + materialStocVO.getStockInvId() + " Recarga de material.");
					
					Long unidades = materialStocVO.getUnidades();
					materialStocVOOld.setUnidades(unidades);
					materialStocVOOld.setFecUltRecarga(materialStocVO.getFecUltRecarga());
					materialStocVOOld.setObservaciones(materialStocVO.getObservaciones());
					materialStocVO = materialStockDao.guardarOActualizar(materialStocVOOld);
				} 
			}
			
		} catch (Exception ex) {
			result.setError(Boolean.TRUE);
			log.error(ex.getMessage());
		}
		
		return result;
	}

	@Override
	public ResultBean actualizarStockPedido(PedidoVO pedidoVO) {
		ResultBean result = new ResultBean(Boolean.FALSE);

		
		try {
		
			if (servicioConsultaPedidos.isPedidoPermisosEntregado(pedidoVO)) {
				
				MaterialStockVO stock = servicioConsultaStock.obtenerStock(pedidoVO.getJefaturaProvincial().getJefatura(), 
						pedidoVO.getMaterialVO().getMaterialId());
	
				if (stock != null) {
					Long stockIncial = stock.getUnidades();
					stock.setUnidades(stockIncial + pedidoVO.getUnidades());
					stock.setFecUltRecarga(new Date());
					materialStockDao.guardarOActualizar(stock);
					
					log.info("Stock guardado correctamente");
					
				} else {
					MaterialStockVO newStock = new MaterialStockVO();
					
					
					newStock.setMaterialVO(pedidoVO.getMaterialVO());
					newStock.setJefaturaProvincial(pedidoVO.getJefaturaProvincial());
					newStock.setTipo(pedidoVO.getMaterialVO().getTipo());
					newStock.setFecUltRecarga(new Date());
					newStock.setUnidades(pedidoVO.getUnidades());
					
					materialStockDao.guardarOActualizar(newStock);
					log.info("Stock guardado correctamente");
					
				}
				
			}
			
			result.setMensaje(MSG_SUCCESS);
			
		} catch(Exception ex) {
			result.setError(Boolean.TRUE);
			log.error(ex.getMessage());
			result.setMensaje(MSG_ERROR);
		}
		
		return result;
	}

}
