package org.gestoresmadrid.oegam2comun.usoRustico.model.service.impl;

import java.util.Collections;
import java.util.List;

import org.gestoresmadrid.core.modelos.model.enumerados.TipoUsoRustico;
import org.gestoresmadrid.core.usoRustico.model.dao.UsoRusticoDao;
import org.gestoresmadrid.core.usoRustico.model.vo.UsoRusticoVO;
import org.gestoresmadrid.oegam2comun.usoRustico.model.service.ServicioUsoRustico;
import org.gestoresmadrid.oegam2comun.usoRustico.view.dto.UsoRusticoDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioUsoRusticoImpl implements ServicioUsoRustico{

	private static final long serialVersionUID = 7866088997278875118L;
	
	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioUsoRusticoImpl.class);
	
	@Autowired
	private UsoRusticoDao usoRusticoDao;
	
	@Autowired
	private Conversor conversor;
	
	@Override
	@Transactional
	public List<UsoRusticoDto> getListaUsoRusticoPorTipo(TipoUsoRustico tipo) {
		try {
			if(tipo != null){
				List<UsoRusticoVO> lista = usoRusticoDao.getListaUsoRusticoPorTipo(tipo.getValorEnum());
				if(lista != null && !lista.isEmpty()){
					return conversor.transform(lista, UsoRusticoDto.class);
				}
			}else{
				log.error("Ha sucedido un error a la hora de obtener el listado de uso rustico por tipo, el tipo uso es nulo");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener el listado de uso rustico por tipo, error: ",e);
		}
		return Collections.emptyList();
	}

}
