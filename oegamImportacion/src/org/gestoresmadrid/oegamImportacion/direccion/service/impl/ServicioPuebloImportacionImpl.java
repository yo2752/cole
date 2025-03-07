package org.gestoresmadrid.oegamImportacion.direccion.service.impl;

import org.gestoresmadrid.core.direccion.model.dao.PuebloDao;
import org.gestoresmadrid.core.direccion.model.vo.PuebloVO;
import org.gestoresmadrid.oegamImportacion.direccion.service.ServicioPuebloImportacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioPuebloImportacionImpl implements ServicioPuebloImportacion {

	private static final long serialVersionUID = 4650022973959681091L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioPuebloImportacionImpl.class);

	@Autowired
	private PuebloDao puebloDao;

	@Transactional
	@Override
	public PuebloVO getPueblo(String idProvincia, String idMunicipio, String pueblo) {
		try {
			PuebloVO puebloVO = puebloDao.getPueblo(idProvincia, idMunicipio, pueblo);
			if (puebloVO != null) {
				return puebloVO;
			}
		} catch (Exception e) {
			log.error("Error al recuperar el pueblo", e);
		}
		return null;
	}

}
