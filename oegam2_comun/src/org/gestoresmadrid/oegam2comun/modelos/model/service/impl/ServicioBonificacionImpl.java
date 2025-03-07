package org.gestoresmadrid.oegam2comun.modelos.model.service.impl;

import java.math.BigDecimal;

import org.gestoresmadrid.core.modelos.model.dao.BonificacionDao;
import org.gestoresmadrid.core.modelos.model.vo.BonificacionVO;
import org.gestoresmadrid.oegam2comun.modelos.model.service.ServicioBonificacion;
import org.gestoresmadrid.oegam2comun.modelos.view.dto.BonificacionDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServicioBonificacionImpl implements ServicioBonificacion{

	private static final long serialVersionUID = -7034907917838990662L;

	@Autowired
	private Conversor conversor;
	
	@Autowired
	private BonificacionDao bonificacionDao;
	
	@Override
	@Transactional
	public BonificacionDto getBonificacionPorId(String codigo) {
		BonificacionVO bonificacionVO = bonificacionDao.getBonificacionPorId(codigo);
		if(bonificacionVO != null){
			return conversor.transform(bonificacionVO, BonificacionDto.class);
		}
		return null;
	}

	@Override
	@Transactional
	public BonificacionVO activarBonificacionPorId(String codigo) {
		BonificacionVO bonificacionVO = bonificacionDao.getBonificacionPorId(codigo);
		if(bonificacionVO != null) {
			bonificacionVO.setEstado(BigDecimal.ONE);
			return bonificacionVO;
		}
		return null;
	}
}
