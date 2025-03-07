package org.gestoresmadrid.oegam2comun.colegio.model.service.impl;

import org.gestoresmadrid.core.colegio.model.dao.ColegioDao;
import org.gestoresmadrid.core.general.model.vo.ColegioVO;
import org.gestoresmadrid.oegam2comun.colegio.model.service.ServicioColegio;
import org.gestoresmadrid.oegamComun.colegio.view.dto.ColegioDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioColegioImpl implements ServicioColegio{

	private static final long serialVersionUID = -2429488148256687780L;
	
	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioColegioImpl.class);
	
	@Autowired
	Conversor conversor;
	
	@Autowired
	ColegioDao colegioDao;

	@Override
	@Transactional
	public ColegioDto getColegioDto(String colegio) {
		try{
			if(colegio != null && !colegio.isEmpty()){
				ColegioVO colegioVO = colegioDao.getColegio(colegio);
				if(colegioVO != null){
					return conversor.transform(colegioVO, ColegioDto.class);
				}
			}
		}catch(Exception e){
			log.error("Ha sucedido un error a la hora de obtener el colegio seleccionado, error: ",e);
		}
		return null;
	}

}
