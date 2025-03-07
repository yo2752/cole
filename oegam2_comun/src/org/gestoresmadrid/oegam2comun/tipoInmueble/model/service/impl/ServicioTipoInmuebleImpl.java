package org.gestoresmadrid.oegam2comun.tipoInmueble.model.service.impl;

import java.util.Collections;
import java.util.List;

import org.gestoresmadrid.core.modelos.model.enumerados.TipoBien;
import org.gestoresmadrid.core.tipoInmueble.model.dao.TipoInmuebleDao;
import org.gestoresmadrid.core.tipoInmueble.model.vo.TipoInmuebleVO;
import org.gestoresmadrid.oegam2comun.tipoInmueble.model.service.ServicioTipoInmueble;
import org.gestoresmadrid.oegam2comun.tipoInmueble.view.dto.TipoInmuebleDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioTipoInmuebleImpl implements ServicioTipoInmueble{

	private static final long serialVersionUID = 7973078541987549111L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioTipoInmuebleImpl.class);
	
	@Autowired
	private TipoInmuebleDao tipoInmuebleDao;
	
	@Autowired
	private Conversor conversor;
	
	@Override
	@Transactional
	public List<TipoInmuebleDto> getListaTiposInmueblesPorTipo(TipoBien tipoBien) {
		try{
			if(tipoBien != null){
				List<TipoInmuebleVO> lista = tipoInmuebleDao.getListaTiposInmueblesPorTipo(tipoBien);
				if(lista != null && !lista.isEmpty()){
					return conversor.transform(lista, TipoInmuebleDto.class);
				}
			}else{
				log.error("Ha sucedido un error a la hora de obtener la lista con los tipos de inmuebles, el tipo bien es nulo");
			}
		}catch(Exception e){
			log.error("Ha sucedido un error a la hora de obtener la lista con los tipos de inmuebles, error: ",e);
		}
		
		return Collections.emptyList();
	}
}
