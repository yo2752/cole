package org.gestoresmadrid.oegam2comun.colegio.model.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.core.general.model.dao.ColegioProvinciaDao;
import org.gestoresmadrid.core.general.model.vo.ColegioProvinciaVO;
import org.gestoresmadrid.oegam2comun.colegio.model.service.ServicioColegioProvincia;
import org.gestoresmadrid.oegamComun.colegio.view.dto.ColegioDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioColegioProvinciaImpl implements ServicioColegioProvincia {

	private static final long serialVersionUID = -2392926056984887161L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioColegioProvinciaImpl.class);

	@Autowired
	private ColegioProvinciaDao colegioProvinciaDao;

	@Autowired
	private Conversor conversor;

	@Override
	@Transactional
	public List<ColegioDto> listadoColegio(String idProvincia) {
		List<ColegioDto> listadoColegio = new ArrayList<ColegioDto>();
		try {
			List<ColegioProvinciaVO> lista = colegioProvinciaDao.listadoColegioProvincia(idProvincia);
			if (lista != null && lista.size() > 0) {
				for (ColegioProvinciaVO colegioProvincia : lista) {
					if (colegioProvincia.getColegio() != null) {
						listadoColegio.add(conversor.transform(colegioProvincia.getColegio(), ColegioDto.class));
					}
				}
			}
		} catch (Exception e) {
			log.error("Error al recuperar el listado de colegios por provincia", e);
		}
		return listadoColegio;
	}

	public ColegioProvinciaDao getColegioProvinciaDao() {
		return colegioProvinciaDao;
	}

	public void setColegioProvinciaDao(ColegioProvinciaDao colegioProvinciaDao) {
		this.colegioProvinciaDao = colegioProvinciaDao;
	}
}
