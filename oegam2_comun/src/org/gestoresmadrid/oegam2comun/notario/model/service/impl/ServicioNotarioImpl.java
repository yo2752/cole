package org.gestoresmadrid.oegam2comun.notario.model.service.impl;

import org.gestoresmadrid.core.notario.model.dao.NotarioDao;
import org.gestoresmadrid.core.notario.model.vo.NotarioVO;
import org.gestoresmadrid.oegam2comun.notario.model.service.ServicioNotario;
import org.gestoresmadrid.oegam2comun.notario.view.dto.NotarioDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioNotarioImpl implements ServicioNotario{

	private static final long serialVersionUID = -4792517761940552060L;
	
	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioNotarioImpl.class);
	
	@Autowired
	private NotarioDao notarioDao;
	
	@Autowired
	private Conversor conversor;
	
	@Autowired
	Utiles utiles;

	@Override
	@Transactional
	public NotarioDto getNotarioPorId(String codigo) {
		try{
			if(codigo != null && !codigo.isEmpty()){
				codigo = utiles.rellenarCeros(codigo, 7);
				NotarioVO notarioVO = notarioDao.getNotarioPorID(codigo);
				if(notarioVO != null){
					return conversor.transform(notarioVO, NotarioDto.class); 
				}
			}
		}catch(Exception e){
			log.error("Ha sucedido un error a la hora de obtener el notario por su ID, error: ",e);
		}
		return null;
	}

	@Override
	@Transactional
	public NotarioDto guardarNotario(NotarioDto notario) {
		try{
			notario.setCodigoNotaria(utiles.rellenarCeros(notario.getCodigoNotaria(), 9));
			notario.setCodigoNotario(utiles.rellenarCeros(notario.getCodigoNotario(), 7));
			NotarioVO notarioVO = conversor.transform(notario, NotarioVO.class);		
			notarioVO = notarioDao.guardarOActualizar(notarioVO);
			
			return conversor.transform(notarioVO, NotarioDto.class); 
			
		}
		catch(Exception e){
			log.error("Ha sucedido al dar de alta un notario en el sistema, error: ",e);
		}
		
		return null;
	}
	
}
