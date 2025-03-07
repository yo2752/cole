package org.gestoresmadrid.oegam2comun.presentacion.jpt.model.service.impl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.core.trafico.model.dao.EvolucionPresentacionJptDao;
import org.gestoresmadrid.core.trafico.model.vo.EvolucionPresentacionJptPK;
import org.gestoresmadrid.core.trafico.model.vo.EvolucionPresentacionJptVO;
import org.gestoresmadrid.oegam2comun.evolucionJPT.EvolucionPresentacionJptDTO;
import org.gestoresmadrid.oegam2comun.presentacion.jpt.model.service.ServicioEvolucionPresentacionJpt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ServicioEvolucionPresentacionJptImpl implements
		ServicioEvolucionPresentacionJpt, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1906860920136915800L;

	@Autowired
	private EvolucionPresentacionJptDao evolucionPresentacionJptDao;

	public ServicioEvolucionPresentacionJptImpl() {
		super();
	}

	@Override
	public EvolucionPresentacionJptPK guardarEvolucion(EvolucionPresentacionJptVO evolucion) {
		return (EvolucionPresentacionJptPK) evolucionPresentacionJptDao.guardar(evolucion);
	}

	public EvolucionPresentacionJptDao getEvolucionPresentacionJptDao() {
		return evolucionPresentacionJptDao;
	}

	public void setEvolucionPresentacionJptDao(EvolucionPresentacionJptDao evolucionPresentacionJptDao) {
		this.evolucionPresentacionJptDao = evolucionPresentacionJptDao;
	}

	@Override
	public List<EvolucionPresentacionJptDTO> mostrarEvolucionExpediente(String numExpediente) {
		List<EvolucionPresentacionJptVO> listaInicial = evolucionPresentacionJptDao.obtenerEvolucion(new BigDecimal(numExpediente)); 
		List<EvolucionPresentacionJptDTO> listaFinal = null;
		if(listaInicial != null && listaInicial.size() > 0){
			listaFinal = new ArrayList<EvolucionPresentacionJptDTO>();
			for(EvolucionPresentacionJptVO evolucionAux : listaInicial){
				EvolucionPresentacionJptDTO dtoAux = new EvolucionPresentacionJptDTO();
				dtoAux.setUsuario(evolucionAux.getUsuario().getApellidosNombre());
				dtoAux.setJefatura(evolucionAux.getJefatura());
				dtoAux.setFechaCambio(evolucionAux.getId().getFechaCambio());
				dtoAux.setEstadoAnterior(evolucionAux.getId().getEstadoAnterior().toString());
				dtoAux.setEstadoNuevo(evolucionAux.getId().getEstadoNuevo().toString());
				listaFinal.add(dtoAux);
			}
		}
			
		
		return listaFinal;
	}
	
	

}
