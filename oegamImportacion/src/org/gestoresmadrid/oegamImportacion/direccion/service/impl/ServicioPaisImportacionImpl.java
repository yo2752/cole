package org.gestoresmadrid.oegamImportacion.direccion.service.impl;

import org.gestoresmadrid.core.paises.model.dao.PaisDao;
import org.gestoresmadrid.core.paises.model.vo.PaisVO;
import org.gestoresmadrid.oegamImportacion.direccion.service.ServicioPaisImportacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioPaisImportacionImpl implements ServicioPaisImportacion {

	private static final long serialVersionUID = 6445284383359262478L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioPaisImportacionImpl.class);

	@Autowired
	PaisDao paisDao;

	@Override
	@Transactional(readOnly = true)
	public PaisVO getIdPaisPorSigla(String sigla) {
		try {
			return paisDao.getPaisPorSigla(sigla);
		} catch (Exception e) {

			log.error("Error al recuperar el pais, error: ", e);
		}
		return null;
	}
}
