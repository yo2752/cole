package org.gestoresmadrid.oegam2comun.modelos.model.service.impl;

import org.gestoresmadrid.core.modelos.model.dao.ModeloDao;
import org.gestoresmadrid.core.modelos.model.enumerados.Modelo;
import org.gestoresmadrid.core.modelos.model.vo.ModeloVO;
import org.gestoresmadrid.oegam2comun.modelos.model.service.ServicioModelos;
import org.gestoresmadrid.oegam2comun.modelos.view.dto.ModeloDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServicioModelosImpl implements ServicioModelos{

	private static final long serialVersionUID = 7694429213748641882L;
	
	@Autowired
	private Conversor conversor;
	
	@Autowired
	private ModeloDao modeloDao;
	
	@Override
	@Transactional
	public ModeloDto getModeloDtoPorModelo(Modelo modelo) {
		if(modelo != null){
			ModeloVO modeloVO = modeloDao.getModeloPorId(modelo.getValorEnum(),modelo.getVersion());
			if(modeloVO != null){
				return conversor.transform(modeloVO, ModeloDto.class);
			}
		}
		return null;
	}

}
