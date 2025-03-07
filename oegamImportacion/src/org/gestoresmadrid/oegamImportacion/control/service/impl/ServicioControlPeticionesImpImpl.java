package org.gestoresmadrid.oegamImportacion.control.service.impl;

import org.gestoresmadrid.core.importacionFichero.model.dao.ControlPeticionesImpDao;
import org.gestoresmadrid.core.importacionFichero.model.vo.ControlPeticionesImpVO;
import org.gestoresmadrid.oegamImportacion.control.service.ServicioControlPeticionesImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioControlPeticionesImpImpl implements ServicioControlPeticionesImp {

	private static final long serialVersionUID = -6398258878140103666L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioControlPeticionesImpImpl.class);

	@Autowired
	ControlPeticionesImpDao controlPeticionesImpDao;

	@Override
	@Transactional(readOnly = true)
	public ControlPeticionesImpVO getControlPeticionesImp(String tipo) {
		try {
			ControlPeticionesImpVO controlPeticionesImpVO = controlPeticionesImpDao.getControlPeticionesImp(tipo);
			if (controlPeticionesImpVO != null) {
				return controlPeticionesImpVO;
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener el control de peticiones importación, error: ", e);
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public int numeroPeticiones(String tipo) {
		ControlPeticionesImpVO controlPeticionesImpVO = getControlPeticionesImp(tipo);
		if (controlPeticionesImpVO != null && controlPeticionesImpVO.getNumeroPeticiones() != null) {
			return controlPeticionesImpVO.getNumeroPeticiones().intValue();
		}
		return 0;
	}
}
