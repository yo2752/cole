package org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.gestoresmadrid.core.model.beans.ColaBean;
import org.gestoresmadrid.core.trafico.materiales.model.dao.AutorDao;
import org.gestoresmadrid.core.trafico.materiales.model.dao.MaterialDao;
import org.gestoresmadrid.core.trafico.materiales.model.dao.PedidoDao;
import org.gestoresmadrid.core.trafico.materiales.model.enumerados.EstadoPedido;
import org.gestoresmadrid.core.trafico.materiales.model.vo.AutorVO;
import org.gestoresmadrid.core.trafico.materiales.model.vo.MaterialStockVO;
import org.gestoresmadrid.core.trafico.materiales.model.vo.PedidoVO;
import org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.ServicioConsultaAutor;
import org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.ServicioConsultaPedidos;
import org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.ServicioConsultaStock;
import org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.ServicioCrearSolicitudesPedido;
import org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.ServicioGuardarEvolucionPedidos;
import org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.ServicioGuardarPaquetePedido;
import org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.ServicioGuardarPedido;
import org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.ServicioGuardarStock;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.dto.PedidoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioGuardarPedidoImpl implements ServicioGuardarPedido {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1030932679736456599L;

	private static final String MSG_SUCCESS   = "Pedido guardado";
	private static final String MSG_ERROR     = "Pedido no guardado";
	private static final String MSG_ELIMINAR  = "Pedido eliminado";

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioGuardarPedidoImpl.class);
	
	@Resource PedidoDao   pedidoDao;
	@Resource MaterialDao materialDao;
	@Resource AutorDao    autorDao; 

	@Autowired ServicioConsultaPedidos         servicioConsultaPedidos;
	@Autowired ServicioCrearSolicitudesPedido  servicioCrearSolicitudesPedido;
	@Autowired ServicioGuardarEvolucionPedidos servicioGuardarEvolucionPedidos;
	@Autowired ServicioConsultaAutor           servicioConsultaAutor;
	@Autowired ServicioGuardarStock            servicioGuardarStock;
	@Autowired ServicioGuardarPaquetePedido    servicioGuardarPaquetePedido;
	@Autowired ServicioConsultaStock           servicioConsultaStock;
	
	@Override
	@Transactional
	public ResultBean salvarPedido(PedidoDto pedidoDto) {
		ResultBean result = new ResultBean(Boolean.FALSE);
		
		try {
			PedidoVO pedidoVO = servicioConsultaPedidos.getVOFromDto(pedidoDto);
			
			PedidoVO newPedido = pedidoDao.guardarOActualizar(pedidoVO);
			EstadoPedido estadoOld = null;
			
			PedidoDto newPedidoDto = servicioConsultaPedidos.getDtoFromVO(newPedido);
			
			boolean pedidoPermiso = servicioConsultaPedidos.isPedidoPermisos(newPedido);
			boolean pedidoPermisoEntregado = servicioConsultaPedidos.isPedidoPermisosEntregado(newPedido);
			
			pedidoDto.setPedidoPermisosEntregado(pedidoPermisoEntregado);
			pedidoDto.setPedidoPermisos(pedidoPermiso);

			result.setMensaje(MSG_SUCCESS);
			result.addAttachment("pedidoDto", newPedidoDto);
			result.addAttachment("estadoOld", estadoOld);
		} catch (Exception ex) {
			result.setError(Boolean.TRUE);
			log.error(ex.getMessage());
			result.setMensaje(MSG_ERROR);
		}
		return result;
	}

	@Override
	@Transactional
	public ResultBean modifyPedido(PedidoDto pedidoDto) {
		ResultBean result = new ResultBean(Boolean.FALSE);
		
		try {
			PedidoVO oldPedidoVO = pedidoDao.findPedidoByPK(pedidoDto.getPedidoId());
			
			EstadoPedido estadoOrigen = EstadoPedido.convertir(oldPedidoVO.getEstado());
			EstadoPedido nuevoEstado = EstadoPedido.convertir(pedidoDto.getEstado());
			
			if (!estadoOrigen.equals(nuevoEstado)) {
				servicioGuardarEvolucionPedidos.anadirEvolucion(pedidoDto, estadoOrigen);
			}

			PedidoVO pedidoVO = actualizarPedidoVO(pedidoDto);

			//TODO estudiar actualizar stock
			if (EstadoPedido.convertir(pedidoVO.getEstado()) == EstadoPedido.Entregado ) {
				result = servicioGuardarStock.actualizarStockPedido(pedidoVO);
			}
			result.setMensaje(MSG_SUCCESS);
			result.addAttachment("pedidoDto", servicioConsultaPedidos.getDtoFromVO(pedidoVO));
		} catch(Exception ex) {
			result.setError(Boolean.TRUE);
			log.error(ex.getMessage());
			result.setMensaje(MSG_ERROR);
		}
		
		return result;
	}

	@Override
	@Transactional
	public ResultBean salvarPedidoInventario(PedidoDto pedidoDto) {
		ResultBean result = new ResultBean(Boolean.FALSE);
		
		try {
			PedidoVO pedidoVO = servicioConsultaPedidos.getVOFromDto(pedidoDto);
	
			PedidoVO nuevoPedido = pedidoDao.guardarOActualizar(pedidoVO);
			
			PedidoVO newPedido = pedidoDao.actualizar(pedidoVO);
			
			pedidoDto.setPedidoId(newPedido.getPedidoId());
	
			pedidoDto.setPedidoId(newPedido.getPedidoId());
			String nomEstado = EstadoPedido.convertirEstadoLong(newPedido.getEstado());   
			pedidoDto.setNomEstado(nomEstado);
			String nomJefatura = pedidoVO.getJefaturaProvincial().getDescripcion();
			pedidoDto.setJefaturaDescripcion(nomJefatura);
			pedidoDto.setFecha(newPedido.getFecha());
			
			PedidoDto nuevoPedidoDto = servicioConsultaPedidos.getDtoFromVO(nuevoPedido);
			result.setMensaje(MSG_SUCCESS);
			result.addAttachment("pedidoDto", nuevoPedidoDto);
		} catch (Exception ex) {
			result.setError(Boolean.TRUE);
			log.error(ex.getMessage());
			result.setMensaje(MSG_ERROR);
		}
		
		return result;
	}

	@Override
	@Transactional
	public ResultBean confirmarPedido(PedidoDto pedidoDto) {
		ResultBean result = new ResultBean(Boolean.FALSE);
		
		try {
			PedidoVO pedidoVO = actualizarPedidoVO(pedidoDto);
			
			pedidoVO.setEstado(new Long(EstadoPedido.Borrador.getValorEnum()));
			@SuppressWarnings("unused")
			PedidoVO pedidoAct = pedidoDao.guardarOActualizar(pedidoVO);
			
			result = servicioCrearSolicitudesPedido.crearSolicitudCrearPedido(pedidoVO.getPedidoId());

			servicioGuardarEvolucionPedidos.anadirEvolucion(pedidoDto, EstadoPedido.Iniciado);

			result.setMensaje(MSG_SUCCESS);
			pedidoDto.setEstado(new Long(EstadoPedido.Borrador.getValorEnum()));
			result.addAttachment("pedidoDto", servicioConsultaPedidos.getDtoFromVO(pedidoVO));

		} catch(Exception ex) {
			result.setError(Boolean.TRUE);
			log.error(ex.getMessage());
			result.setMensaje(MSG_ERROR);
		}
		
		return result;
		
	}
	
	@Override
	@Transactional
	public ResultBean solicitarPedidos(List<Long> pedidos) {
		ResultBean result = new ResultBean(Boolean.FALSE);
		
		try {
			for(Long item: pedidos){
				PedidoVO pedidoVO = pedidoDao.findPedidoByPK(item);
				pedidoVO.setEstado(new Long(EstadoPedido.Solicitado_Consejo.getValorEnum()));
				
				PedidoVO pedidoAct = pedidoDao.guardarOActualizar(pedidoVO);
				result = servicioCrearSolicitudesPedido.crearSolicitudActualizarPedido(pedidoAct.getPedidoId());
				servicioGuardarEvolucionPedidos.anadirEvolucion(pedidoAct, EstadoPedido.Borrador);

				result.setMensaje(MSG_SUCCESS);
			}
			
		} catch(Exception ex) {
			result.setError(Boolean.TRUE);
			log.error(ex.getMessage());
			result.setMensaje(MSG_ERROR);
		}
		
		return result;
	}

	@Override
	@Transactional
	public ResultBean entregarPedidos(List<Long> listadoPedidos) {
		// TODO Auto-generated method stub
		ResultBean result = new ResultBean(Boolean.FALSE);
		
		try {
			for(Long item: listadoPedidos){
				PedidoVO pedidoVO = pedidoDao.findPedidoByPK(item);
				pedidoVO.setEstado(new Long(EstadoPedido.Entregado.getValorEnum()));
				
				PedidoVO pedidoAct = pedidoDao.guardarOActualizar(pedidoVO);
				result = servicioCrearSolicitudesPedido.crearSolicitudActualizarPedido(pedidoAct.getPedidoId());
				servicioGuardarEvolucionPedidos.anadirEvolucion(pedidoAct, EstadoPedido.Solicitado_Consejo);

				result.setMensaje(MSG_SUCCESS);
			}
			
		} catch(Exception ex) {
			result.setError(Boolean.TRUE);
			log.error(ex.getMessage());
			result.setMensaje(MSG_ERROR);
		}
		
		return result;
	}


	@Override
	@Transactional
	public ResultBean eliminarPedido(PedidoDto pedidoDto) {
		ResultBean result = new ResultBean(Boolean.FALSE);
		PedidoDto pedidoToEliminarDto = null;
		
		try {
		
			PedidoVO pedidoToEliminar = pedidoDao.findPedidoByPK(pedidoDto.getPedidoId());
			pedidoToEliminarDto = servicioConsultaPedidos.getDtoFromVO(pedidoToEliminar);
			
			servicioGuardarEvolucionPedidos.eliminarEvolucion(pedidoDto.getPedidoId());
			
			pedidoDao.borrar(pedidoToEliminar);
			
		} catch(Exception ex) {
			result.setError(Boolean.TRUE);
			log.error(ex.getMessage());
			result.setMensaje(MSG_ERROR);
		}
		
		result.setMensaje(MSG_ELIMINAR);
		result.addAttachment("pedidoDto", pedidoToEliminarDto);
		return result;
	}
	
	private PedidoVO actualizarPedidoVO(PedidoDto pedidoDto) {
		PedidoVO pedidoVO = pedidoDao.findPedidoByPK(pedidoDto.getPedidoId());
		
		pedidoVO.setObservaciones(pedidoDto.getObservaciones());
		pedidoVO.setPedidoInvId(pedidoDto.getPedidoInvId());
		pedidoVO.setCodigoInicial(pedidoDto.getCodigoInicial());
		pedidoVO.setCodigoFinal(pedidoDto.getCodigoFinal());
		pedidoVO.setPedidoDgt(pedidoDto.getPedidoDgt());
		pedidoVO.setEstado(pedidoDto.getEstado());
		pedidoVO.setFecha(new Date());
		
		return pedidoVO;
	}

	@Override
	@Transactional
	public ResultBean salvarPedido(PedidoVO pedidoVO, ColaBean colaBean) {
		ResultBean result = new ResultBean(Boolean.FALSE);
		//System.out.println("Pedido =================================> " + pedidoVO.getPedidoInvId());
		log.info("Pedido =================================> " + pedidoVO.getPedidoInvId());
		try {
			PedidoVO pedidoOld = pedidoDao.findPedidoByInventarioId(pedidoVO.getPedidoInvId(), true);
			if (pedidoOld != null && pedidoOld.equals(pedidoVO)) {
				//System.out.println("Pedido =================================> " + pedidoVO.getPedidoInvId() + " ya registrado.");
				log.info("Pedido =================================> " + pedidoVO.getPedidoInvId() + " ya registrado.");
				
			} else if (pedidoOld == null) {
				//System.out.println("Pedido " + pedidoVO.getPedidoInvId() + " no registrado.");
				log.info("Pedido " + pedidoVO.getPedidoInvId() + " no registrado.");
				AutorVO autorOld = servicioConsultaAutor.getAutorByPrimaryKey(pedidoVO.getAutorVO().getAutorId());
				if (autorOld != null) {
					pedidoVO.setAutorVO(autorOld);
				} else {
					AutorVO autor = new AutorVO();
					autor.setAutorId(pedidoVO.getAutorVO().getAutorId());
					autor.setEmail(pedidoVO.getAutorVO().getEmail());
					autor.setNombre(pedidoVO.getAutorVO().getNombre());
					autor.setRol(pedidoVO.getAutorVO().getRol());
					autor = autorDao.guardarOActualizar(autor);
					pedidoVO.setAutorVO(autor);
				}
				pedidoDao.guardarOActualizar(pedidoVO);
				servicioGuardarEvolucionPedidos.anadirEvolucion(pedidoVO, null);
				
			} else {
				
				//System.out.println("Pedido " + pedidoOld.getPedidoInvId() + " registrado pero cambiado.");
				
				log.info("Pedido " + pedidoOld.getPedidoInvId() + " registrado pero cambiado.");
				EstadoPedido estadoOld = EstadoPedido.convertir(pedidoOld.getEstado());
				EstadoPedido estadoNew = EstadoPedido.convertir(pedidoVO.getEstado());
				
				pedidoOld.setCodigo(pedidoVO.getCodigo());
				//pedidoOld.setCodigoFinal(pedidoVO.getCodigoFinal());
				//pedidoOld.setCodigoInicial(pedidoVO.getCodigoInicial());
				pedidoOld.setFecha(new Date());
				pedidoOld.setObservaciones(pedidoVO.getObservaciones());
				pedidoOld.setPedidoDgt(pedidoVO.getPedidoDgt());
				pedidoOld.setTotal(pedidoVO.getTotal());
				pedidoOld.setUnidades(pedidoVO.getUnidades());
				
				pedidoOld.setEstado(pedidoVO.getEstado());
				pedidoDao.guardarOActualizar(pedidoOld);
				
				if (!estadoOld.equals(estadoNew)) {
					result.addAttachment("estadoOld", estadoOld);
					
					if (estadoNew.equals(EstadoPedido.Entregado) ) {
						MaterialStockVO materialStockVO = servicioConsultaStock.obtenerStock(pedidoVO.getJefaturaProvincial().getJefaturaProvincial(),
								 															 pedidoVO.getMaterialVO().getMaterialId());

						if ( null !=materialStockVO ) {
							List<Long> stocks = new ArrayList<Long>(); 
							stocks.add(materialStockVO.getMaterialStockId());
							result = servicioCrearSolicitudesPedido.crearSolicitudInformacionStock(stocks, colaBean);
						}
					}

				} else {
					log.info("Pedido " + pedidoVO.getPedidoInvId() + " sin cambios de estado.");
				}
				
			}
			
			result.setMensaje(MSG_SUCCESS);
			result.addAttachment("pedidoVO", pedidoVO);
		} catch(Exception ex) {
			result.setError(Boolean.TRUE);
			log.error(ex.getMessage());
			result.setMensaje(MSG_ERROR);
		}
		
		return result;
	}
	
	@Transactional
	public ResultBean actualizarPedidoWithIdInventario(Long pedidoId, Long inventarioId) {
		ResultBean result = new ResultBean(Boolean.FALSE);
		
		try {
			PedidoVO pedido = pedidoDao.findPedidoByPK(pedidoId);
			if (pedido == null) {
				result.setError(Boolean.TRUE);
				log.error("No se ha encontrado el pedido con id ==> " + pedidoId);
				return result;
			} else {
				
				log.info("Pedido =================================> " + pedido.getPedidoInvId());
				
				pedido.setPedidoInvId(inventarioId);
				PedidoVO pedidoNew = pedidoDao.guardarOActualizar(pedido);
				
				result.setMensaje(MSG_SUCCESS);
				result.addAttachment("pedidoVO", pedidoNew);
			}
			
		} catch(Exception ex) {
			result.setError(Boolean.TRUE);
			log.error(ex.getMessage());
			result.setMensaje(MSG_ERROR);
		}
		
		return result;
	}

	@Override
	@Transactional
	public ResultBean actualizarSeriales(Long pedido, String serialIni, String serialFin) {
		ResultBean result = new ResultBean(Boolean.FALSE);
		
		try {
			PedidoVO pedidoVO = pedidoDao.findPedidoByPK(pedido);
			
			if (pedidoVO != null) {
				pedidoVO.setCodigoInicial(serialIni);
				pedidoVO.setCodigoFinal(serialFin);
				
				PedidoVO pedidoNew = pedidoDao.guardarOActualizar(pedidoVO);
				if (!result.getError()) {
					if ("PC".equals(pedidoVO.getMaterialVO().getTipo())) {
						result = servicioGuardarPaquetePedido.paquetizarPedido(pedidoNew);
					}
				}
				
			} else {
				result.setError(Boolean.TRUE);
				log.error("No se ha encontrado el pedido con id ==> " + pedido);
				return result;
			}
		
		} catch(Exception ex) {
			result.setError(Boolean.TRUE);
			log.error(ex.getMessage());
			result.setMensaje(MSG_ERROR);
		}
		
		return result;
	}

}
