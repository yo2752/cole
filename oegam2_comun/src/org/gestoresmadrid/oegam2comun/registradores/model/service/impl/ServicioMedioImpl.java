package org.gestoresmadrid.oegam2comun.registradores.model.service.impl;

import org.gestoresmadrid.core.registradores.model.dao.MedioDao;
import org.gestoresmadrid.core.registradores.model.vo.MedioVO;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioMedio;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.MedioDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import escrituras.beans.ResultBean;

@Service
public class ServicioMedioImpl implements ServicioMedio {

	private static final long serialVersionUID = -8693721296719581896L;

	private static ILoggerOegam log = LoggerOegam.getLogger(ServicioMedioImpl.class);

	@Autowired
	private MedioDao medioDao;
	
	@Autowired
	private Conversor conversor;
	
	@Override
	@Transactional
	public ResultBean guardarMedio(MedioDto medio) {
		ResultBean result = new ResultBean();
		try {
			if (medio.getDescMedio() != null && !medio.getDescMedio().isEmpty()) {
				medio.setDescMedio(medio.getDescMedio().toUpperCase());
			}
			MedioVO medioVO = conversor.transform(medio, MedioVO.class);
			medioVO = medioDao.guardarOActualizar(medioVO);
			result.addAttachment(ID_MEDIO, medioVO.getIdMedio());
		} catch (Exception e) {
			result.setError(Boolean.TRUE);
			result.addMensajeALista("Error al guardar el medio: " + e.getMessage());
			log.error("Error al guardar el medio", e.getMessage());
		}
		return result;
	}
}
