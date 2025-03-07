package org.gestoresmadrid.oegamComun.acceso.service.impl;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import org.gestoresmadrid.core.general.model.dao.UrlDao;
import org.gestoresmadrid.oegamComun.acceso.model.service.ServicioUrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioUrlImpl implements ServicioUrl{

	private static final long serialVersionUID = 9210050622902108953L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioUrlImpl.class);

	@Autowired
	UrlDao urlDao;

	@Override
	@Transactional(readOnly=true)
	public List<String> getListaUrlsSecuralized() {
		try {
			return urlDao.listPatronUrlsSecured();
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener la lista de URLs que se debe de comprobar el acceso, error: ", e);
		}
		return Collections.emptyList();
	}

	@Override
	@Transactional(readOnly=true)
	public List<String> getListaPatronUrlsContrato(BigDecimal idContrato, BigDecimal idUsuario) {
		try {
			return urlDao.listPatronUrlsContrato(idContrato, idUsuario);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener la lista de URLs por contrato, error: ", e);
		}
		return Collections.emptyList();
	}

	@Override
	@Transactional(readOnly=true)
	public List<String> getListaPatronUrlsAplicacion(String codigoAplicacion) {
		try {
			return urlDao.listPatronUrlsAplicacion(codigoAplicacion);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener la lista de URLs por aplicación, error: ", e);
		}
		return Collections.emptyList();
	}

}