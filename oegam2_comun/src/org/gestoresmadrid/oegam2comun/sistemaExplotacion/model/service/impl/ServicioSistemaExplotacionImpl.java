package org.gestoresmadrid.oegam2comun.sistemaExplotacion.model.service.impl;

import java.util.Collections;
import java.util.List;

import org.gestoresmadrid.core.sistemaExplotacion.model.dao.SistemaExplotacionDao;
import org.gestoresmadrid.core.sistemaExplotacion.model.vo.SistemaExplotacionVO;
import org.gestoresmadrid.oegam2comun.sistemaExplotacion.model.service.ServicioSistemaExplotacion;
import org.gestoresmadrid.oegam2comun.sistemaExplotacion.view.dto.SistemaExplotacionDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioSistemaExplotacionImpl implements ServicioSistemaExplotacion{

	private static final long serialVersionUID = -5689276001838430782L;
	
	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioSistemaExplotacionImpl.class);
	
	@Autowired
	private SistemaExplotacionDao sistemaExplotacionDao;
	
	@Autowired
	private Conversor conversor;
	
	@Override
	@Transactional
	public List<SistemaExplotacionDto> getListaSistemasExplotacion() {
		try {
			List<SistemaExplotacionVO> lista = sistemaExplotacionDao.getListaSistemaExplotacion(); 
			if(lista != null && !lista.isEmpty()){
				return conversor.transform(lista, SistemaExplotacionDto.class);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener la lista de sistemas de explotacion, erro: ",e);
		}
		return Collections.emptyList();
	}

}
