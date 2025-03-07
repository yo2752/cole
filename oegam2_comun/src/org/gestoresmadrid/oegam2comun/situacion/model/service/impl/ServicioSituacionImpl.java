package org.gestoresmadrid.oegam2comun.situacion.model.service.impl;

import java.util.Collections;
import java.util.List;

import org.gestoresmadrid.core.situacion.model.dao.SituacionDao;
import org.gestoresmadrid.core.situacion.model.vo.SituacionVO;
import org.gestoresmadrid.oegam2comun.situacion.model.service.ServicioSituacion;
import org.gestoresmadrid.oegam2comun.situacion.view.dto.SituacionDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioSituacionImpl implements ServicioSituacion{

	private static final long serialVersionUID = -3380583253149295391L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioSituacionImpl.class);
	
	@Autowired	
	private SituacionDao situacionDao;
	
	@Autowired
	private Conversor conversor;
	
	@Override
	@Transactional
	public List<SituacionDto> getListaSituacion() {
		try {
			List<SituacionVO> lista = situacionDao.getListaSituacion();
			if(lista != null && !lista.isEmpty()){
				return conversor.transform(lista, SituacionDto.class);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener la lista con las situaciones, error: ",e);
		}
		return Collections.emptyList();
	}
}
