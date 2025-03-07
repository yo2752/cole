package org.gestoresmadrid.oegam2comun.grupo.model.service.impl;

import java.util.List;

import org.gestoresmadrid.core.general.model.dao.GrupoDao;
import org.gestoresmadrid.core.general.model.vo.GrupoVO;
import org.gestoresmadrid.core.model.beans.DatoMaestroBean;
import org.gestoresmadrid.oegam2comun.grupo.model.service.ServicioGrupo;
import org.gestoresmadrid.oegam2comun.view.dto.GruposDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioGrupoImpl implements ServicioGrupo {

	private static final long serialVersionUID = 7846046390702698237L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioGrupoImpl.class);

	@Autowired
	private GrupoDao grupoDao;

	@Autowired
	private Conversor conversor;

	@Override
	@Transactional
	public GruposDto getGrupo(String idGrupo) {
		GrupoVO grupo = grupoDao.getGrupo(idGrupo);
		if (grupo != null) {
			return conversor.transform(grupo, GruposDto.class);
		}
		return null;
	}

	@Override
	@Transactional
	public List<GruposDto> getGrupos() {
		try {
			List<GrupoVO> lista = grupoDao.getGrupos();
			if (lista != null && !lista.isEmpty()) {
				return conversor.transform(lista, GruposDto.class);
			}
		} catch (Exception e) {
			log.error("Error al recuperar los grupos", e);
		}
		return null;
	}

	@Override
	@Transactional
	public List<DatoMaestroBean> getComboGrupos() {
		return grupoDao.getComboGrupos();
	}

	@Override
	@Transactional
	public String getDescripcionGrupo(String idGrupo) {
		List<String> lDescGrupo = grupoDao.getDescripcionGrupo(idGrupo);
		if (lDescGrupo != null && !lDescGrupo.isEmpty()) {
			return lDescGrupo.get(0);
		}
		return null;
	}
}
