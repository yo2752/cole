package org.gestoresmadrid.oegam2comun.evolucionModelo600601.model.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.gestoresmadrid.core.evolucionModelo600601.model.dao.EvolucionModelo600601Dao;
import org.gestoresmadrid.core.evolucionModelo600601.model.vo.EvolucionModelo600_601PK;
import org.gestoresmadrid.core.evolucionModelo600601.model.vo.EvolucionModelo600_601VO;
import org.gestoresmadrid.core.modelos.model.enumerados.EstadoModelos;
import org.gestoresmadrid.oegam2comun.evolucionModelo600601.model.service.ServicioEvolucionModelo600_601;
import org.gestoresmadrid.oegam2comun.evolucionModelo600601.view.bean.EvolucionModelo600_601Bean;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioEvolucionModelo600_601Impl implements ServicioEvolucionModelo600_601{

	private static final long serialVersionUID = 7015514442388215205L;
	
	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioEvolucionModelo600_601Impl.class);
	
	@Autowired
	private Conversor conversor;
	
	@Autowired
	private EvolucionModelo600601Dao evolucionModelo600601Dao;
	
	@Autowired
	UtilesFecha utilesFecha;

	@Override
	public List<EvolucionModelo600_601Bean> convertirListaEnBeanPantalla(List<EvolucionModelo600_601VO> lista) {
		try {
			if(lista != null && !lista.isEmpty()){
				List<EvolucionModelo600_601Bean> listaBeanPantalla = new ArrayList<EvolucionModelo600_601Bean>();
				for(EvolucionModelo600_601VO evolucionModelo600_601VO: lista){
					EvolucionModelo600_601Bean evolucionModelo600_601Bean = conversor.transform(evolucionModelo600_601VO, EvolucionModelo600_601Bean.class, "evolModelo600601BeanPantalla");
					evolucionModelo600_601Bean.setEstadoAnterior(EstadoModelos.convertirTexto(evolucionModelo600_601VO.getId().getEstadoAnterior().toString()));
					evolucionModelo600_601Bean.setEstadoNuevo(EstadoModelos.convertirTexto(evolucionModelo600_601VO.getId().getEstadoNuevo().toString()));
					listaBeanPantalla.add(evolucionModelo600_601Bean);
				}
				return listaBeanPantalla;
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de convertir el objeto EvolucionModelo600_601VO to EvolucionModelo600_601Bean, error: ",e);
		}
		return Collections.emptyList();
	}
	
	@Override
	@Transactional
	public ResultBean eliminarEvolucionModelo(BigDecimal numExpediente) {
		ResultBean resultado = new ResultBean(false);
		try {
			if(numExpediente != null){
				List<EvolucionModelo600_601VO> listaEvolucion = evolucionModelo600601Dao.getListaEvolucionPorNumExpediente(numExpediente);
				if(listaEvolucion != null && !listaEvolucion.isEmpty()){
					for (EvolucionModelo600_601VO evolucionModelo600_601VO : listaEvolucion) {
						evolucionModelo600601Dao.borrar(evolucionModelo600_601VO);
					}
				}
			}else{
				resultado.setError(true);
				resultado.addMensajeALista("No se encuentran datos del modelo para eliminar su evolución.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de eliminar la evolucion del modelo con expediente: " + numExpediente + ", error: ",e);
			resultado.setError(true);
			resultado.addMensajeALista("Ha sucedido un error a la hora de eliminar la evolucion del modelo con expediente: " + numExpediente);
		}
		return resultado;
	}
	
	@Override
	@Transactional
	public EvolucionModelo600_601VO guardarEvolucionModelo(BigDecimal numExpediente, BigDecimal estadoAnt, BigDecimal estadoNuevo, BigDecimal idUsuario) {
		try {
			EvolucionModelo600_601VO evolucionModelo600_601VO = new EvolucionModelo600_601VO();
			EvolucionModelo600_601PK id = new EvolucionModelo600_601PK();
			id.setNumExpediente(numExpediente);
			if(estadoAnt != null){
				id.setEstadoAnterior(estadoAnt);
			}else{
				id.setEstadoAnterior(BigDecimal.ZERO);
			}
			id.setEstadoNuevo(estadoNuevo);
			id.setFechaCambio(utilesFecha.getFechaActualDesfaseBBDD());
			evolucionModelo600_601VO.setId(id);
			evolucionModelo600_601VO.setIdUsuario(idUsuario);
			evolucionModelo600601Dao.guardarOActualizar(evolucionModelo600_601VO);
			return evolucionModelo600_601VO;
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de guardar la evolucion del modelo, error: ",e);
		}
		return null;
	}

}
