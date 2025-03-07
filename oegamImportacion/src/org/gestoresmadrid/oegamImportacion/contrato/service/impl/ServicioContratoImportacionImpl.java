package org.gestoresmadrid.oegamImportacion.contrato.service.impl;

import java.math.BigDecimal;

import org.gestoresmadrid.core.contrato.model.dao.ContratoDao;
import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.general.model.dao.ContratoColegiadoDao;
import org.gestoresmadrid.core.general.model.dao.ContratoUsuarioDao;
import org.gestoresmadrid.oegamImportacion.contrato.service.ServicioContratoImportacion;
import org.gestoresmadrid.oegamImportacion.direccion.service.ServicioMunicipioImportacion;
import org.gestoresmadrid.oegamImportacion.direccion.service.ServicioProvinciaImportacion;
import org.gestoresmadrid.oegamImportacion.persona.service.ServicioPersonaImportacion;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioContratoImportacionImpl implements ServicioContratoImportacion {

	private static final long serialVersionUID = -8366257641662706305L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioContratoImportacionImpl.class);

	@Autowired
	ServicioPersonaImportacion servicioPersona;

	@Autowired
	ContratoDao contratoDao;

	@Autowired
	ContratoColegiadoDao contratoColegiadoDao;

	@Autowired
	ContratoUsuarioDao contratoUsuarioDao;
	
	@Autowired
	ServicioProvinciaImportacion servicioProvincia;

	@Autowired
	ServicioMunicipioImportacion servicioMunicipio;
	
	@Autowired
	GestorPropiedades gestorPropiedades;
	
	@Override
	@Transactional(readOnly=true)
	public ContratoVO getContrato(BigDecimal idContrato) {
		try {
			return getContratoVO(idContrato.longValue());
		} catch (Exception e) {
			log.error("Error al obtener el contrato: " + idContrato, e);
		}
		return null;
	}
	
	@Override
	@Transactional(readOnly=true)
	public ContratoVO getContratoVO(Long idContrato) {
		try {
			return contratoDao.getContrato(idContrato, false);
		} catch (Exception e) {
			log.error("Error al obtener el contrato: " + idContrato, e);
		}
		return null;
	}


}
