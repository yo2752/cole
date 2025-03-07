package org.gestoresmadrid.oegam2comun.modelos.model.service.impl;

import java.util.Collections;
import java.util.List;

import org.gestoresmadrid.core.modelos.model.dao.OficinaLiquidadoraDao;
import org.gestoresmadrid.core.modelos.model.vo.OficinaLiquidadoraVO;
import org.gestoresmadrid.oegam2comun.modelos.model.service.ServicioOficinaLiquidadora;
import org.gestoresmadrid.oegam2comun.modelos.view.dto.OficinaLiquidadoraDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioOficinaLiquidadoraImpl implements ServicioOficinaLiquidadora{

	private static final long serialVersionUID = -7577201649762559068L;
	
	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioOficinaLiquidadoraImpl.class);
	
	@Autowired
	private OficinaLiquidadoraDao oficinaLiquidadoraDao;
	
	@Autowired
	private Conversor conversor;
	
	@Override
	@Transactional
	public List<OficinaLiquidadoraDto> getListaOficinasLiquidadoras(String idProvinciaOficinaLiquid) {
		try {
			List<OficinaLiquidadoraVO> lista = oficinaLiquidadoraDao.getListaOficinas(idProvinciaOficinaLiquid);
			if(lista != null && !lista.isEmpty()){
				return conversor.transform(lista, OficinaLiquidadoraDto.class);
			}
		} catch (Exception e) {
			log.error("Error al obtener la lista de oficinas liquidadoras, error: ",e);
		}
		return Collections.emptyList();
	}

}
