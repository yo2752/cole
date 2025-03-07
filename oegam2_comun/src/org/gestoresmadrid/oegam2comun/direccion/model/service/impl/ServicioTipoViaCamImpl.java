package org.gestoresmadrid.oegam2comun.direccion.model.service.impl;

import java.util.Collections;
import java.util.List;

import org.gestoresmadrid.core.direccion.model.dao.TipoViaCamDao;
import org.gestoresmadrid.core.direccion.model.vo.TipoViaCamVO;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioTipoViaCam;
import org.gestoresmadrid.oegam2comun.direccion.view.dto.TipoViaCamDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioTipoViaCamImpl implements ServicioTipoViaCam{

	private static final long serialVersionUID = 182426689351436488L;
	
	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioTipoViaCamImpl.class);
	
	@Autowired
	private Conversor conversor;
	
	@Autowired
	private TipoViaCamDao tipoViaCamDao;
	
	@Override
	@Transactional(readOnly=true)
	public List<TipoViaCamDto> getListaTipoVias() {
		try {
			List<TipoViaCamVO> lista = tipoViaCamDao.getListaTipoVias();
			if(lista != null && !lista.isEmpty()){
				return conversor.transform(lista, TipoViaCamDto.class);
			}
		} catch (Exception e) {
			log.error("Error al recuperar la lista con los tipos de vias", e);
		}
		return Collections.emptyList();
	}
	
	@Override
	@Transactional(readOnly=true)
	public TipoViaCamVO getTipoVia(String idTipoVia) {
		try {
			TipoViaCamVO tipoViaCamVO = tipoViaCamDao.getTipoVia(idTipoVia);
			if (tipoViaCamVO != null) {
				return tipoViaCamVO;
			}
		} catch (Exception e) {
			log.error("Error al recuperar el tipo de via", e);
		}
		return null;
	}

}
