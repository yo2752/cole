package org.gestoresmadrid.oegam2comun.direccion.model.service.impl;

import java.util.Collections;
import java.util.List;

import org.gestoresmadrid.core.direccion.model.dao.ProvinciaCamDao;
import org.gestoresmadrid.core.direccion.model.vo.ProvinciaCamVO;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioProvinciaCam;
import org.gestoresmadrid.oegam2comun.direccion.view.dto.ProvinciaCamDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioProvinciaCamImpl implements ServicioProvinciaCam {

	private static final long serialVersionUID = -1748484621595092235L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioProvinciaCamImpl.class);

	@Autowired
	private Conversor conversor;

	@Autowired
	private ProvinciaCamDao provinciaCamDao;

	@Override
	@Transactional(readOnly = true)
	public List<ProvinciaCamDto> getListaProvinciasCam() {
		try {
			List<ProvinciaCamVO> lista = provinciaCamDao.getLista();
			if (lista != null && !lista.isEmpty()) {
				return conversor.transform(lista, ProvinciaCamDto.class);
			}
		} catch (Exception e) {
			log.error("Error al recuperar la lista de provincias, error: ", e);
		}
		return Collections.emptyList();
	}

	@Override
	@Transactional
	public ProvinciaCamVO getProvincia(String idProvincia) {
		try {
			ProvinciaCamVO provinciaVO = provinciaCamDao.getProvincia(idProvincia);
			if (provinciaVO != null) {
				return provinciaVO;
			}
		} catch (Exception e) {
			log.error("Error al recuperar la provincia", e);
		}
		return null;
	}

	@Override
	@Transactional
	public String getProvinciaNombre(String idProvincia) {
		try {
			ProvinciaCamVO provinciaVO = provinciaCamDao.getProvincia(idProvincia);
			if (provinciaVO != null) {
				return provinciaVO.getNombre();
			}
		} catch (Exception e) {
			log.error("Error al recuperar la provincia", e);
		}
		return null;
	}

}
