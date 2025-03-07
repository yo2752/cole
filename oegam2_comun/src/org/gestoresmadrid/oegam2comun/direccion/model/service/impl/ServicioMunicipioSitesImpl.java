package org.gestoresmadrid.oegam2comun.direccion.model.service.impl;

import org.gestoresmadrid.core.direccion.model.dao.MunicipioSitesDao;
import org.gestoresmadrid.core.direccion.model.vo.MunicipioSitesVO;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioMunicipioSites;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioMunicipioSitesImpl implements ServicioMunicipioSites {

	private static final long serialVersionUID = -3756265412344454039L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioMunicipioSitesImpl.class);

	@Autowired
	private MunicipioSitesDao municipioSitesDao;

	@Autowired
	private Conversor conversor;
	
	@Override
	@Transactional(readOnly=true)
	public MunicipioSitesVO getMunicipio(String idMunicipio, String idProvincia) {
		try {
			MunicipioSitesVO municipioSitesVO = municipioSitesDao.getMunicipioSites(idMunicipio, idProvincia);
			if (municipioSitesVO != null) {
				return municipioSitesVO;
			}
		} catch (Exception e) {
			log.error("Error al recuperar el municipio", e);
		}
		return null;
	}

	
	public Conversor getConversor() {
		return conversor;
	}

	public void setConversor(Conversor conversor) {
		this.conversor = conversor;
	}


	public MunicipioSitesDao getMunicipioSitesDao() {
		return municipioSitesDao;
	}


	public void setMunicipioSitesDao(MunicipioSitesDao municipioSitesDao) {
		this.municipioSitesDao = municipioSitesDao;
	}

	
	
}
