package org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.trafico.materiales.model.dao.PedPaqueteDao;
import org.gestoresmadrid.core.trafico.materiales.model.dao.PedidoDao;
import org.gestoresmadrid.core.trafico.materiales.model.enumerados.EstadoPedPaquete;
import org.gestoresmadrid.core.trafico.materiales.model.vo.PedPaqueteVO;
import org.gestoresmadrid.core.trafico.materiales.model.vo.PedidoVO;
import org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.ServicioConsultaPaquetePedido;
import org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.ServicioConsultaPedidos;
import org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.ServicioGuardarPaquetePedido;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioGuardarPaquetePedidoImpl implements ServicioGuardarPaquetePedido {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5100755881215012699L;

	private static final String CANTIDAD_POR_PAQUETE = "num.inicial.paquete";
	private static final String MSG_ERROR     = "Pedido no paquetizado";
	private static final String MSG_SUCCESS   = "Pedido paquetizado correctamente";
	
	private static final String TIPO_PERMISO  = "PC";
	
	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioGuardarPaquetePedidoImpl.class);

	@Resource PedPaqueteDao pedPaqueteDao;
	@Resource PedidoDao     pedidoDao;
	
	@Autowired ServicioConsultaPaquetePedido servicioConsultaPaquetePedido;
	@Autowired ServicioConsultaPedidos       servicioConsultaPedidos;
	
	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	@Override
	@Transactional
	public ResultBean paquetizarPedido(PedidoVO pedidoVO) {
		ResultBean result = new ResultBean(Boolean.FALSE);

		try {
			Long numInicialPaquete = Long.valueOf(gestorPropiedades.valorPropertie(CANTIDAD_POR_PAQUETE));
			
			Long numPaquetes = servicioConsultaPaquetePedido.obtenerNumeroPaquetesPedido(pedidoVO.getPedidoId());
			if (numPaquetes == 0) {
				//PedidoVO pedidoVO = servicioConsultaPedidos.obtenerPedidoCompleto(pedidoId);
				
				if ( TIPO_PERMISO.equals(pedidoVO.getMaterialVO().getTipo()) ) {
					long serialIni = obtenerNumSerie(pedidoVO.getCodigoInicial());
					long serialFin = obtenerNumSerie(pedidoVO.getCodigoFinal());
					
					int serialLen = obtenerNumSerieLength(pedidoVO.getCodigoInicial());
					String prefijo = obtenerPrefijo(pedidoVO.getCodigoInicial());
					
					long serialInd = serialIni;
					String serialCalIni = null;
					String serialCalFin = null;
					
					List<PedPaqueteVO> paquetes = new ArrayList<PedPaqueteVO>();
					while(serialInd < serialFin) {
						serialCalIni = prefijo + StringUtils.leftPad((String.valueOf(serialInd)), serialLen, "0");
						serialInd += numInicialPaquete;
						if (serialInd >= serialFin) {
							serialInd = serialFin + 1;
						}
						
						serialCalFin = prefijo + StringUtils.leftPad((String.valueOf(serialInd - 1)), serialLen, "0");
						
						PedPaqueteVO paquete = new PedPaqueteVO();
						paquete.setPedidoVO(pedidoVO);
						paquete.setNumSerieIncial(serialCalIni);
						paquete.setNumSerieFin(serialCalFin);
						paquete.setNumSerieActual(serialCalIni);
						paquete.setEstado(EstadoPedPaquete.Disponible.getValorEnum());
						
						System.out.println("=====================> " + paquete.toString());
						
						PedPaqueteVO paqueteNew = pedPaqueteDao.guardarOActualizar(paquete);
						paquetes.add(paqueteNew);

					}
					
					pedidoVO.setPaquetes(paquetes);
					pedidoDao.guardarOActualizar(pedidoVO);
				}
				
				result.setMensaje(MSG_SUCCESS);
				//result.addAttachment("pedidoDto", pedidoDto);

			}
		} catch(Exception ex) {
			result.setError(Boolean.TRUE);
			log.error(ex.getMessage());
			result.setMensaje(MSG_ERROR);
		}
		
		return result;
	}
	
	private long obtenerNumSerie(String numSerie) {
		String numSerieStr = numSerie.substring(numSerie.indexOf('-') + 1);
		long numSerieLong = Long.parseLong(numSerieStr);
		return numSerieLong;
	}

	private int obtenerNumSerieLength(String numSerie) {
		String numSerieStr = numSerie.substring(numSerie.indexOf('-') + 1);
		return numSerieStr.length();
	}

	private String obtenerPrefijo(String numSerie) {
		String prefijo = numSerie.substring(0, numSerie.indexOf('-') + 1);
		return prefijo;
	}

}
