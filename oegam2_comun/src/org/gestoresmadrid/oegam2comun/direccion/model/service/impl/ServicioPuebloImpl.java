package org.gestoresmadrid.oegam2comun.direccion.model.service.impl;

import java.util.Collections;
import java.util.List;

import org.gestoresmadrid.core.direccion.model.dao.PuebloDao;
import org.gestoresmadrid.core.direccion.model.vo.PuebloVO;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioPueblo;
import org.gestoresmadrid.oegam2comun.direccion.view.dto.PuebloDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioPuebloImpl implements ServicioPueblo {

	private static final long serialVersionUID = 7826807493419299208L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioPuebloImpl.class);

	@Autowired
	private PuebloDao puebloDao;

	@Autowired
	private Conversor conversor;

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

	@Transactional
	@Override
	public List<PuebloDto> listaPueblos(String idProvincia, String idMunicipio) {
		try {
			List<PuebloVO> pueblos = puebloDao.listaPueblos(idProvincia, idMunicipio);
			if (pueblos != null && !pueblos.isEmpty()) {
				return conversor.transform(pueblos, PuebloDto.class);
			}
		} catch (Exception e) {
			log.error("Error al recuperar el listado de pueblos", e);
		}
		return Collections.emptyList();
	}

	public PuebloDao getPuebloDao() {
		return puebloDao;
	}

	public void setPuebloDao(PuebloDao puebloDao) {
		this.puebloDao = puebloDao;
	}
}