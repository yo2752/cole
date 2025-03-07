package org.gestoresmadrid.oegamComun.pais.service.impl;

import org.gestoresmadrid.core.paises.model.dao.PaisDao;
import org.gestoresmadrid.core.paises.model.vo.PaisVO;
import org.gestoresmadrid.oegamComun.pais.service.ServicioComunPais;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioComunPaisImpl implements ServicioComunPais {

	private static final long serialVersionUID = 1318445443215979716L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioComunPaisImpl.class);

	@Autowired
	private PaisDao paisDao;

	@Override
	@Transactional
	public PaisVO getPais(String codigo) {
		try {
			return paisDao.getPaisPorSigla(codigo);
		} catch (Exception e) {
			log.error("Error al recuperar el pais", e);
		}
		return null;
	}
}
