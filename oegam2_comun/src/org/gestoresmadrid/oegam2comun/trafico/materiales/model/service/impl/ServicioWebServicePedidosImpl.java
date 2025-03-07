package org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.gestoresmadrid.core.model.beans.ColaBean;
import org.gestoresmadrid.core.trafico.materiales.model.enumerados.ComandosMaterial;
import org.gestoresmadrid.core.trafico.materiales.model.enumerados.Delegacion;
import org.gestoresmadrid.core.trafico.materiales.model.enumerados.EstadoIncidencia;
import org.gestoresmadrid.core.trafico.materiales.model.enumerados.EstadoPedido;
import org.gestoresmadrid.core.trafico.materiales.model.vo.IncidenciaVO;
import org.gestoresmadrid.core.trafico.materiales.model.vo.MaterialStockVO;
import org.gestoresmadrid.core.trafico.materiales.model.vo.MaterialVO;
import org.gestoresmadrid.core.trafico.materiales.model.vo.PedidoVO;
import org.gestoresmadrid.core.trafico.materiales.model.vo.UsuarioColegioVO;
import org.gestoresmadrid.core.trafico.model.vo.JefaturaTraficoVO;
import org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.ServicioCallToRestfullMateriales;
import org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.ServicioConsultaIncidencias;
import org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.ServicioConsultaMateriales;
import org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.ServicioConsultaPedidos;
import org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.ServicioConsultaStock;
import org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.ServicioConsultaUsuarioConsejo;
import org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.ServicioGuardarEvolucionPedidos;
import org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.ServicioGuardarIncidencia;
import org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.ServicioGuardarMateriales;
import org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.ServicioGuardarPedido;
import org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.ServicioGuardarStock;
import org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.ServicioGuardarUsuarioConsejo;
import org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.ServicioWebServicePedidos;
import org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.exceptions.NotificationException;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.beans.rest.AppException;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.beans.rest.IncidenciaInfoRest;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.beans.rest.IncidenciasInfoRest;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.beans.rest.MaterialInfoRest;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.beans.rest.MaterialesInfoRest;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.beans.rest.PedidoInfoRest;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.beans.rest.PedidosInfoRest;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.beans.rest.Response;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.beans.rest.StockInfoRest;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.beans.rest.StocksInfoRest;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.beans.rest.UsuarioInfoRest;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.dto.IncidenciaDto;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.dto.MaterialStockDto;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.dto.PedidoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

@Service
public class ServicioWebServicePedidosImpl implements ServicioWebServicePedidos {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2490881405414225485L;
	
	private ILoggerOegam log = LoggerOegam.getLogger(ServicioWebServicePedidosImpl.class);
	
	private static final String MSG_NO_HAY_DATOS = "No existen datos de la consulta para poder realizar la petición.";

	@Autowired ServicioConsultaPedidos          servicioConsultaPedidos;
	@Autowired ServicioConsultaIncidencias      servicioConsultaIncidencias;
	@Autowired ServicioConsultaMateriales       servicioConsultaMateriales;
	@Autowired ServicioGuardarMateriales        servicioGuardarMateriales;
	@Autowired ServicioConsultaStock            servicioConsultaStock;
	@Autowired ServicioGuardarStock             servicioGuardarStock;
	@Autowired ServicioGuardarPedido            servicioGuardarPedido;
	@Autowired ServicioGuardarEvolucionPedidos  servicioGuardarEvolucionPedidos;
	@Autowired ServicioCallToRestfullMateriales servicioCallToRestfullMateriales;
	@Autowired ServicioGuardarIncidencia        servicioGuardarIncidencia;
	@Autowired ServicioConsultaUsuarioConsejo   servicioConsultaUsuarioConsejo;
	@Autowired ServicioGuardarUsuarioConsejo    servicioGuardarUsuarioConsejo;

	@Override
	public ResultBean executeNotificacines() {
		
		ResultBean resultBean = new ResultBean(false);
		
		try {
			//resultBean = executeActualizarStock();
			resultBean = executeInformacionUsuario();
			resultBean = executeInformacionMateriales();
			resultBean = executeInformacionPedidos();
			resultBean = executeInformacionStocks();
			resultBean = executeActualizarStock();
			resultBean = executeInformacionIncidencias();
		} catch (NotificationException ex) {
			resultBean.setError(true);
			resultBean.setMensaje(ex.getMessage());
		}
		
		return resultBean;
	}

	@Override
	public ResultBean generarCrearPedido(ColaBean colaBean) {
		
		ResultBean result = new ResultBean(false);
		Response response = null;
		
		try {
			if (colaBean != null) {
				
				String comando = ComandosMaterial.PEDIDOCREAR.getNombreEnum();
				List<Long> data = procesarXmlToSend(comando, colaBean.getXmlEnviar() );
				PedidoDto pedidoDto = servicioConsultaPedidos.getPedidoDto(data.get(0));
				
				Date ahora = new Date();
				pedidoDto.setFecha(ahora);
				
				response = servicioCallToRestfullMateriales.llamadaWSCrearPedido(pedidoDto);
				if ("error".equals(response.getMessage())) {
					LinkedHashMap<String, List<String>> errors = response.getErrors();
					for( Map.Entry<String,List<String>> entry : errors.entrySet()) {
						  String key = entry.getKey();
						  log.error("Error en ==> " + key);
						  List<String> value = entry.getValue();
						  for(String item:  value) {
							  log.error("\t\t " + item);
						  }
						  
					result.setError(true);
					return result;
					}
				}
				
				if ("ok".equals(response.getMessage())) {
					Long inventarioId = new Long(response.getId());
					result = servicioGuardarPedido.actualizarPedidoWithIdInventario(pedidoDto.getPedidoId(), inventarioId);
				}
				
				
			} else {
				ponerError(MSG_NO_HAY_DATOS);
			}

		} catch (AppException e) {
			ponerError(e.getName(), e.getMessage(), e.getCode(), e.getStatus(), e.getType() );
		} catch (OegamExcepcion e) {
			log.error("Ha ocurrido un error " + e.getMessage());
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public ResultBean generarModificarPedido(ColaBean colaBean) {
		ResultBean result = new ResultBean(false);
		Response response = null;
		
		try {
			if (colaBean != null) {
				
				String comando = ComandosMaterial.PEDIDODETALLE.getNombreEnum();
				List<Long> data = procesarXmlToSend(comando, colaBean.getXmlEnviar() );
				PedidoDto pedidoDto = servicioConsultaPedidos.getPedidoDto(data.get(0));
				
				Date ahora = new Date();
				pedidoDto.setFecha(ahora);
				
				response = servicioCallToRestfullMateriales.llamadaWSModificarPedido(pedidoDto);
				if ("error".equals(response.getMessage())) {
					LinkedHashMap<String, List<String>> errors = response.getErrors();
					for( Map.Entry<String,List<String>> entry : errors.entrySet()) {
						  String key = entry.getKey();
						  log.error("Error en ==> " + key);
						  List<String> value = entry.getValue();
						  for(String item:  value) {
							  log.error("\t\t " + item);
						  }
						  
					result.setError(true);
					return result;
					}
				}
				
				if ("ok".equals(response.getMessage())) {
					if ( null != response.getId() ) {
						Long inventarioId = new Long(response.getId());
						result = servicioGuardarPedido.actualizarPedidoWithIdInventario(pedidoDto.getPedidoId(), inventarioId);
					}
				}
				
				
			} else {
				ponerError(MSG_NO_HAY_DATOS);
			}

		} catch (AppException e) {
			ponerError(e.getName(), e.getMessage(), e.getCode(), e.getStatus(), e.getType() );
		} catch (OegamExcepcion e) {
			log.error("Ha ocurrido un error " + e.getMessage());
			e.printStackTrace();
		}
		
		return result;
	}


	@Override
	public ResultBean generarCrearIncidencia(ColaBean colaBean) {

		ResultBean result = new ResultBean(false);
		Response response = null;
		
		try {
			if (colaBean != null) {
				String comando = ComandosMaterial.INCIDENCIACREAR.getNombreEnum();
				List<Long> data = procesarXmlToSend(comando, colaBean.getXmlEnviar() );
				
				IncidenciaDto incidenciaDto = servicioConsultaIncidencias.getIncidencia(data.get(0));

				response = servicioCallToRestfullMateriales.llamadaWSCrearIncidencia(incidenciaDto);
				if ("error".equals(response.getMessage())) {
					LinkedHashMap<String, List<String>> errors = response.getErrors();
					for( Map.Entry<String,List<String>> entry : errors.entrySet()) {
						  String key = entry.getKey();
						  log.error("Error en ==> " + key);
						  List<String> value = entry.getValue();
						  for(String item:  value) {
							  log.error("\t\t " + item);
						  }
						  
					result.setError(true);
					return result;
					}
				}
				
				if ("ok".equals(response.getMessage())) {
					Long inventarioId = new Long(response.getId());
					result = servicioGuardarIncidencia.actualizarIncidenciaWithIdInventario(incidenciaDto.getIncidenciaId(), inventarioId);
				}
			
			} else {
				ponerError(MSG_NO_HAY_DATOS);
			}
	
		} catch (AppException e) {
			ponerError(e.getName(), e.getMessage(), e.getCode(), e.getStatus(), e.getType() );
		} catch (OegamExcepcion e) {
			log.error("Ha ocurrido un error " + e.getMessage());
			e.printStackTrace();
		}
	
		return result;
	}

	@Override
	public ResultBean generarUpdateIncidencia(ColaBean colaBean) {
		
		ResultBean result = new ResultBean(false);
		
		Response response = null;

		try {
			if (colaBean != null) {
				String comando = ComandosMaterial.INCIDENCIAUPDATE.getNombreEnum();
				List<Long> data = procesarXmlToSend(comando, colaBean.getXmlEnviar() );
				IncidenciaDto incidenciaDto = servicioConsultaIncidencias.getIncidencia(data.get(0));
				
				response = servicioCallToRestfullMateriales.llamadaWSUpdateIncidencia(incidenciaDto);
				if ("error".equals(response.getMessage())) {
					LinkedHashMap<String, List<String>> errors = response.getErrors();
					for( Map.Entry<String,List<String>> entry : errors.entrySet()) {
						  String key = entry.getKey();
						  log.error("Error en ==> " + key);
						  List<String> value = entry.getValue();
						  for(String item:  value) {
							  log.error("\t\t " + item);
						  }
						  
					result.setError(true);
					return result;
					}
				}

				if ("ok".equals(response.getMessage())) {
					log.info("Incidencia modificada en Consejo correctamente");
				}
				
			} else {
				ponerError(MSG_NO_HAY_DATOS);
			}
		} catch (AppException e) {
			ponerError(e.getName(), e.getMessage(), e.getCode(), e.getStatus(), e.getType() );
		} catch (OegamExcepcion e) {
			log.error("Ha ocurrido un error " + e.getMessage());
			e.printStackTrace();
		}
	
		return result;
	}

	@Override
	public ResultBean generarActualizarStock(ColaBean colaBean) {
		// TODO Auto-generated method stub
		ResultBean result = new ResultBean(false);
		
		Response response = null;

		try {
			if (colaBean != null) {
				String comando = ComandosMaterial.UPDATESTOCK.getNombreEnum();
				List<Long> data = procesarXmlToSend(comando, colaBean.getXmlEnviar() );
				MaterialStockVO materialStockVO = servicioConsultaStock.obtenerStockByPrimaryKey(data.get(0));
				
				response = servicioCallToRestfullMateriales.llamadaWSActualizarStock(materialStockVO);
				if ("error".equals(response.getMessage())) {
					LinkedHashMap<String, List<String>> errors = response.getErrors();
					for( Map.Entry<String,List<String>> entry : errors.entrySet()) {
						  String key = entry.getKey();
						  log.error("Error en ==> " + key);
						  List<String> value = entry.getValue();
						  for(String item:  value) {
							  log.error("\t\t " + item);
						  }
						  
					result.setError(true);
					return result;
					}
				}

				if ("ok".equals(response.getMessage())) {
					log.info("Stock actualizado en Consejo correctamente");
				}
				
			} else {
				ponerError(MSG_NO_HAY_DATOS);
			}
		} catch (AppException e) {
			ponerError(e.getName(), e.getMessage(), e.getCode(), e.getStatus(), e.getType() );
		} catch (OegamExcepcion e) {
			log.error("Ha ocurrido un error " + e.getMessage());
			e.printStackTrace();
		}
	
		return result;
	}


	@Transactional
	private ResultBean executeInformacionUsuario() throws NotificationException {
		
		ResultBean resultBean = new ResultBean(false);
		
		@SuppressWarnings("unused")
		Response response = null;
		
		try {
			UsuarioInfoRest usuario = servicioCallToRestfullMateriales.llamadaWSInfoUsuario();
			resultBean = addUsuario(usuario);

		} catch (AppException e) {
			ponerError(e.getName(), e.getMessage(), e.getCode(), e.getStatus(), e.getType() );
			throw new NotificationException(resultBean.getMensaje());
		} catch (OegamExcepcion e) {
			log.error("Ha ocurrido un error " + e.getMessage());
			e.printStackTrace();
			throw new NotificationException("Ha ocurrido un error " + e.getMessage());
		}
		
		return resultBean;
	}
	
	private ResultBean addUsuario(UsuarioInfoRest usuario) {
		ResultBean resultBean = new ResultBean(false);
		
		UsuarioColegioVO usuarioColegioVO = servicioConsultaUsuarioConsejo.getDtoFromInfoRest(usuario);
		
		String jpt = Delegacion.convertirTexto(usuarioColegioVO.getDelegacionVO().getDelegacionId());
		JefaturaTraficoVO jefaturaTraficoVO = servicioConsultaUsuarioConsejo.obtenerJefaturaProvincial(jpt);
		usuarioColegioVO.getDelegacionVO().setJefaturaProvincial(jefaturaTraficoVO);
		usuarioColegioVO.setFecha(new Date());
		resultBean = servicioGuardarUsuarioConsejo.guardarUsuarioConsejo(usuarioColegioVO);
		
		return resultBean;
	}

	@Transactional
	private ResultBean executeInformacionMateriales() throws NotificationException {
		ResultBean resultBean = new ResultBean(false);
		
		try {
			MaterialesInfoRest materiales = servicioCallToRestfullMateriales.llamadaWSInfoMaterial();

			resultBean = addMateriales(materiales);
			
		} catch (AppException e) {
			ponerError(e.getName(), e.getMessage(), e.getCode(), e.getStatus(), e.getType() );
			throw new NotificationException(resultBean.getMensaje()); 
		} catch (OegamExcepcion e) {
			ponerError("Ha ocurrido un error " + e.getMessage());
			e.printStackTrace();
			throw new NotificationException("Ha ocurrido un error " + e.getMessage()); 
		}
		return resultBean;
				
	}

	@Override
	@Transactional
	public ResultBean generarInfoPedido(ColaBean colaBean) {
		ResultBean resultBean = new ResultBean(false);
		
		String comando = ComandosMaterial.INFOPEDIDO.getNombreEnum();
		List<Long> data = procesarXmlToSend(comando, colaBean.getXmlEnviar() );
		PedidoDto pedidoDto = servicioConsultaPedidos.getPedidoDto(data.get(0));
		
		try {
			PedidoInfoRest pedido = servicioCallToRestfullMateriales.llamadaWSInfoPedido(pedidoDto.getPedidoInvId());
			resultBean = sincronizarPedido(pedido, colaBean);
		} catch (AppException e) {
			ponerError(e.getName(), e.getMessage(), e.getCode(), e.getStatus(), e.getType() );
			throw new NotificationException(resultBean.getMensaje()); 
		} catch (OegamExcepcion e) {
			ponerError("Ha ocurrido un error " + e.getMessage());
			e.printStackTrace();
			throw new NotificationException("Ha ocurrido un error " + e.getMessage()); 
		}
		return resultBean;
	}

	@Override
	@Transactional
	public ResultBean generarInfoStock(ColaBean colaBean) {
		ResultBean resultBean = new ResultBean(false);
		
		String comando = ComandosMaterial.INFOSTOCK.getNombreEnum();
		List<Long> data = procesarXmlToSend(comando, colaBean.getXmlEnviar() );
		
		MaterialStockDto materialStockDto = servicioConsultaStock.getStockDto(data.get(0));
		
		try {
			StockInfoRest stock = servicioCallToRestfullMateriales.llamadaWSInfoStock(materialStockDto.getStockInvId());
			resultBean = sincronizarStock(stock);
		} catch (AppException e) {
			ponerError(e.getName(), e.getMessage(), e.getCode(), e.getStatus(), e.getType() );
			throw new NotificationException(resultBean.getMensaje()); 
		} catch (OegamExcepcion e) {
			ponerError("Ha ocurrido un error " + e.getMessage());
			e.printStackTrace();
			throw new NotificationException("Ha ocurrido un error " + e.getMessage()); 
		}
		return resultBean;
	}


	@Transactional
	private ResultBean executeInformacionStocks() throws NotificationException {
		ResultBean resultBean = new ResultBean(false);
		
		try {
			StocksInfoRest stock = servicioCallToRestfullMateriales.llamadaWSInfoStocks();
					
			resultBean = addStock(stock);
			
		} catch (AppException e) {
			ponerError(e.getName(), e.getMessage(), e.getCode(), e.getStatus(), e.getType() );
			throw new NotificationException(resultBean.getMensaje()); 
		} catch (OegamExcepcion e) {
			ponerError("Ha ocurrido un error " + e.getMessage());
			e.printStackTrace();
			throw new NotificationException("Ha ocurrido un error " + e.getMessage()); 
		}
		
		return resultBean;
	}
	
	@Transactional
	private ResultBean executeInformacionPedidos() throws NotificationException {
		ResultBean resultBean = new ResultBean(false);
		
		try {
			PedidosInfoRest pedidos = servicioCallToRestfullMateriales.llamadaWSInfoPedidos();
			resultBean = procesarPedidos(pedidos);
			
		} catch (AppException e) {
			ponerError(e.getName(), e.getMessage(), e.getCode(), e.getStatus(), e.getType() );
			throw new NotificationException(resultBean.getMensaje()); 
		} catch (OegamExcepcion e) {
			ponerError("Ha ocurrido un error " + e.getMessage());
			e.printStackTrace();
			throw new NotificationException(resultBean.getMensaje()); 
		}
		
		return resultBean;
	}

	@Transactional
	private ResultBean executeInformacionIncidencias() throws NotificationException {
		ResultBean resultBean = new ResultBean(false);
		
		try {
			IncidenciasInfoRest incidencias = servicioCallToRestfullMateriales.llamadaWSInfoIncidencias();
			
			IncidenciaVO incidenciaVO = null;
			for (IncidenciaInfoRest item : incidencias.getRecords()) {
				incidenciaVO = servicioConsultaIncidencias.getVOFromInfoRest(item);
				EstadoIncidencia estado = EstadoIncidencia.Activo;
				incidenciaVO.setEstado(Long.valueOf(estado.getValorEnum()));
				resultBean = servicioGuardarIncidencia.salvarIncidencia(incidenciaVO);
			}

		} catch (AppException e) {
			ponerError(e.getName(), e.getMessage(), e.getCode(), e.getStatus(), e.getType() );
			throw new NotificationException(resultBean.getMensaje()); 
		} catch (OegamExcepcion e) {
			ponerError("Ha ocurrido un error " + e.getMessage());
			e.printStackTrace();
			throw new NotificationException(resultBean.getMensaje()); 
		}
		
		return resultBean;
	}

	@SuppressWarnings("unused")
	private ResultBean procesarIncidencia(IncidenciaDto incidenciaDto) {
		ResultBean resultBean = null;
		
		String jefatura = incidenciaDto.getJefaturaProvincial();
		Long materialId = incidenciaDto.getMateriaId();
		//String serial   = incidenciaDto.getNumSerie();
		
		resultBean = servicioGuardarIncidencia.salvarIncidencia(incidenciaDto);
		return resultBean;
	}

	@Transactional
	private ResultBean executeActualizarStock() throws NotificationException  {
		ResultBean resultBean = new ResultBean(false);
		@SuppressWarnings("unused")
		Response response = null;
		
		try {
			List<MaterialStockVO> stockMateriales = servicioConsultaStock.obtenerStockToUpdate();
			
			for(MaterialStockVO item: stockMateriales) {
				response = servicioCallToRestfullMateriales.llamadaWSActualizarStock(item);
			}
			
		} catch (AppException e) {
			ponerError(e.getName(), e.getMessage(), e.getCode(), e.getStatus(), e.getType() );
			throw new NotificationException(resultBean.getMensaje()); 
		} catch (OegamExcepcion e) {
			ponerError("Ha ocurrido un error " + e.getMessage());
			e.printStackTrace();
			throw new NotificationException(resultBean.getMensaje()); 
		}
		
		return resultBean;
	}

	private ResultBean ponerError(String name, String message, int code, Integer status, String type) {
		ResultBean result = new ResultBean(true);
		
		log.error("Ha sucedido un error al invocar el servicio remoto");
		StringBuffer cadenaError = new StringBuffer();
		cadenaError.append("name: " + name + " ");
		cadenaError.append("message: " + message + " ");
		cadenaError.append("code: " + code + " ");
		cadenaError.append("status: " + status + " ");
		cadenaError.append("type: " + type + " ");
		
		log.error("Error: ", cadenaError.toString());
		
		result.setMensaje("error: " + cadenaError.toString());
		
		return result;
	}

	private ResultBean ponerError(String Mensaje) {
		ResultBean result = new ResultBean(true);
		
		log.error("Ha sucedido un error al invocar el servicio remoto");
		StringBuffer cadenaError = new StringBuffer();
		cadenaError.append("message: " + Mensaje + " ");
		
		log.error("Error: ", cadenaError.toString());
		
		result.setMensaje("error: " + cadenaError.toString());
		
		return result;
	}


	private List<Long> procesarXmlToSend(String comando, String xmlToSend) {
		String data = xmlToSend.substring(comando.length() + 1);
		List<Long> datosComando = new ArrayList<Long>();
		
		String[] arrayData = data.split(",");
		for (int i = 0; i < arrayData.length; i++) {
			datosComando.add(new Long(arrayData[i]));
		}
		return datosComando;
	}

	private ResultBean addMateriales(MaterialesInfoRest materiales) {
		ResultBean resultBean = new ResultBean(Boolean.FALSE);
		List<MaterialInfoRest> materialRecibido = materiales.getRecords();
		
		for(MaterialInfoRest item: materialRecibido) {
			MaterialVO materialVO = servicioConsultaMateriales.getVOFromInfoRest(item);
			MaterialVO materialVODB = servicioConsultaMateriales.getMaterialForPrimaryKey(materialVO.getMaterialId());
			if (materialVODB != null && materialVO.equals(materialVODB)) {
				log.info("Material " + materialVO.getMaterialId() + " [" + materialVO.getName() + "] ya registrado en base de datos");
			} else {
				resultBean = servicioGuardarMateriales.salvarMaterial(materialVO);
				if (resultBean.getError()) { break;	}
			}
			
		}
		
		return resultBean;
	}

	private ResultBean addStock(StocksInfoRest stocks) {
		ResultBean resultBean = null;
		List<StockInfoRest> stocksInfoRest = stocks.getRecords();
		
		for(StockInfoRest item: stocksInfoRest) {
			sincronizarStock(item);
		}
		
		return resultBean;		
	}
	
	private ResultBean sincronizarStock(StockInfoRest stockInfoRest) {
		MaterialStockVO materialStockVO = servicioConsultaStock.getVOFromInfoRest(stockInfoRest);
		log.info(materialStockVO.toString());
		return servicioGuardarStock.salvarStock(materialStockVO);		
	}

	@Transactional
	private ResultBean procesarPedidos(PedidosInfoRest pedidos) {
		ResultBean resultBean = null;

		for (PedidoInfoRest item: pedidos.getRecords()) {
			resultBean = sincronizarPedido(item, null);
			if (resultBean.getError()) {
				break;
			}
		}
		
		return resultBean;		
	}
	
	
	private ResultBean sincronizarPedido(PedidoInfoRest pedidoInfoRest, ColaBean colaBean) {
		ResultBean resultBean = null;
		
		log.info("Procesando pedido " + pedidoInfoRest.getId());
		PedidoVO pedidoVO = servicioConsultaPedidos.getVOFromInfoRest(pedidoInfoRest);
		resultBean = servicioGuardarPedido.salvarPedido(pedidoVO, colaBean);
		if (!resultBean.getError()) {
			EstadoPedido estadoOld = (EstadoPedido) resultBean.getAttachment("estadoOld");
			if (estadoOld != null) {
				Long newEvolucion = servicioGuardarEvolucionPedidos.anadirEvolucion(pedidoVO, estadoOld);
				if (newEvolucion == null) {
					resultBean.setError(true);
				}
			} else {
				log.info("Pedido " + pedidoVO.getPedidoInvId() + " sin cambios de estado.");
			}
		}

		return resultBean;
	}


	@SuppressWarnings("unused")
	private PedidoVO compararPedidoColegioConsejo(PedidoVO origenCompleto, PedidoDto pedidoDtoInventario) {
		if (!(origenCompleto.getCodigoInicial().equals(pedidoDtoInventario.getCodigoInicial()))) {
			origenCompleto.setCodigoInicial(pedidoDtoInventario.getCodigoInicial());
		}
		
		if (!(origenCompleto.getCodigoFinal().equals(pedidoDtoInventario.getCodigoFinal()))) {
			origenCompleto.setCodigoFinal(pedidoDtoInventario.getCodigoFinal());
		}
		
		if (!(origenCompleto.getObservaciones().equals(pedidoDtoInventario.getObservaciones()))) {
			origenCompleto.setObservaciones(pedidoDtoInventario.getObservaciones());
		}
		
		if (!(origenCompleto.getEstado().equals(pedidoDtoInventario.getEstado()))) {
			origenCompleto.setEstado(pedidoDtoInventario.getEstado());
		}
		
		if (!(origenCompleto.getPedidoDgt().equals(pedidoDtoInventario.getPedidoDgt()))) {
			origenCompleto.setPedidoDgt(pedidoDtoInventario.getPedidoDgt());
		}
		
		return origenCompleto;
	}


}
