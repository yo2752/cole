package org.gestoresmadrid.oegam2comun.direccion.model.service.impl;

import java.util.Collections;
import java.util.List;

import org.gestoresmadrid.core.direccion.model.dao.MunicipioCamDao;
import org.gestoresmadrid.core.direccion.model.vo.MunicipioCamVO;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioMunicipioCam;
import org.gestoresmadrid.oegam2comun.direccion.view.dto.MunicipioCamDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioMunicipioCamImpl implements ServicioMunicipioCam {

	private static final long serialVersionUID = -222560114177029188L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioMunicipioCamImpl.class);

	@Autowired
	private MunicipioCamDao municipioCamDao;

	@Autowired
	private Conversor conversor;

	@Override
	@Transactional(readOnly = true)
	public List<MunicipioCamDto> getListaMunicipiosPorProvincia(String idProvincia) {
		if (idProvincia != null && !idProvincia.isEmpty()) {
			List<MunicipioCamVO> lista = municipioCamDao.getListaMunicipiosPorProvincia(idProvincia);
			if (lista != null && !lista.isEmpty()) {
				return conversor.transform(lista, MunicipioCamDto.class);
			}
		}
		return Collections.emptyList();
	}

	@Override
	@Transactional(readOnly = true)
	public MunicipioCamVO getMunicipio(String idMunicipio, String idProvincia) {
		try {
			MunicipioCamVO municipioVO = municipioCamDao.getMunicipio(idMunicipio, idProvincia);
			if (municipioVO != null) {
				return municipioVO;
			}
		} catch (Exception e) {
			log.error("Error al recuperar el municipio", e);
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public String getMunicipioNombre(String idMunicipio, String idProvincia) {
		try {
			MunicipioCamVO municipioVO = municipioCamDao.getMunicipio(idMunicipio, idProvincia);
			if (municipioVO != null) {
				return municipioVO.getNombre();
			}
		} catch (Exception e) {
			log.error("Error al recuperar el municipio", e);
		}
		return null;
	}

	public MunicipioCamDao getMunicipioCamDao() {
		return municipioCamDao;
	}

	public void setMunicipioCamDao(MunicipioCamDao municipioCamDao) {
		this.municipioCamDao = municipioCamDao;
	}

}
