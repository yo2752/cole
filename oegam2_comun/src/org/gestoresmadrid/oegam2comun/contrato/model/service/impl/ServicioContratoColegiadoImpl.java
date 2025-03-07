package org.gestoresmadrid.oegam2comun.contrato.model.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.general.model.dao.ContratoColegiadoDao;
import org.gestoresmadrid.core.general.model.vo.ContratoColegiadoVO;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioContratoColegiado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioContratoColegiadoImpl implements ServicioContratoColegiado {

	private static final long serialVersionUID = 4025581578587617369L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioContratoColegiadoImpl.class);

	@Autowired
	private ContratoColegiadoDao contratoColegiadoDao;

	@Override
	@Transactional
	public String getNumColegiadoPorContrato(Long idContrato) {
		try {
			ContratoColegiadoVO contratoColegiadoVO = contratoColegiadoDao.getColegiadoPorContrato(idContrato);
			if (contratoColegiadoVO != null) {
				return contratoColegiadoVO.getId().getNumColegiado();
			}
		} catch (Exception e) {
			log.error("Error al obtener el contrato colegiado", e);
		}
		return null;
	}
	
	@Override
	@Transactional
	public List<ContratoColegiadoVO> getContratoColegiado(String numColegiado) {
		try {
			
			List<ContratoColegiadoVO> contratos = contratoColegiadoDao.getContratoColegiado(numColegiado);
			if (contratos != null) {
				return contratos;
			}
		} catch (Exception e) {
			log.error("Error al obtener el contrato colegiado", e);
		}
		return null;
	}

	@Override
	@Transactional
	public boolean existeContratoColegiado(Long idContrato, String numColegiado) {
		try {
			ContratoColegiadoVO contratoColegiadoVO = contratoColegiadoDao.getContratoColegiado(idContrato, numColegiado);
			if (contratoColegiadoVO != null) {
				return true;
			} 
		} catch (Exception e) {
			log.error("Error al obtener el contrato colegiado", e);
		}
		return false;
	}

	@Override
	@Transactional
	public boolean esUsuarioDelContrato(BigDecimal idContrato, BigDecimal idUsuario) {
		try {
			ContratoColegiadoVO contratoColegiadoVO = contratoColegiadoDao.getContratoColegiado(idContrato.longValue(), idUsuario.longValue());
			if (contratoColegiadoVO != null) {
				return true;
			} 
		} catch (Exception e) {
			log.error("Error al obtener el contrato colegiado", e);
		}
		return false;
	}

	@Override
	@Transactional
	public ContratoColegiadoVO getColegiadoPorContrato(Long idContrato) {
		return contratoColegiadoDao.getColegiadoPorContrato(idContrato);
	}
}