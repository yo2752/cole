package org.gestoresmadrid.oegamImportacion.control.service.impl;

import org.gestoresmadrid.core.importacionFichero.model.dao.ControlContratoImpDao;
import org.gestoresmadrid.core.importacionFichero.model.enumerados.EstadosImpContrato;
import org.gestoresmadrid.core.importacionFichero.model.vo.ControlContratoImpVO;
import org.gestoresmadrid.oegamImportacion.control.service.ServicioControlContratoImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioControlContratoImpImpl implements ServicioControlContratoImp {

	private static final long serialVersionUID = -8202894884167353429L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioControlContratoImpImpl.class);

	@Autowired
	ControlContratoImpDao contratoImpDao;

	@Override
	@Transactional(readOnly = true)
	public ControlContratoImpVO getControlContratoImp(Long idContrato, String tipo) {
		try {
			ControlContratoImpVO contratoImpVO = contratoImpDao.getControlContratoImp(idContrato, tipo);
			if (contratoImpVO != null) {
				return contratoImpVO;
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener el control de contrato importación, error: ", e);
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public boolean esPermitido(Long idContrato, String tipo) {
		ControlContratoImpVO contratoImpVO = getControlContratoImp(idContrato, tipo);
		if (contratoImpVO != null) {
			if (EstadosImpContrato.Con_Permiso.getValorEnum().equals(contratoImpVO.getEstado())) {
				return true;
			}
		} else {
			return true;
		}
		return false;
	}
}
