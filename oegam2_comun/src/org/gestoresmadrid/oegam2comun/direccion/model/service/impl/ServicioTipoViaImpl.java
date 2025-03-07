package org.gestoresmadrid.oegam2comun.direccion.model.service.impl;

import java.util.Collections;
import java.util.List;

import org.gestoresmadrid.core.direccion.model.dao.TipoViaDao;
import org.gestoresmadrid.core.direccion.model.vo.TipoViaVO;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioTipoVia;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioVia;
import org.gestoresmadrid.oegam2comun.direccion.view.dto.TipoViaDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioTipoViaImpl implements ServicioTipoVia {

	private static final long serialVersionUID = 7599366460145185490L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioTipoViaImpl.class);

	@Autowired
	private ServicioVia servicioVia;

	@Autowired
	private TipoViaDao tipoViaDao;

	@Autowired
	private Conversor conversor;

	@Transactional
	@Override
	public TipoViaVO getTipoVia(String idTipoVia) {
		try {
			TipoViaVO tipoViaVO = tipoViaDao.getTipoVia(idTipoVia);
			if (tipoViaVO != null) {
				return tipoViaVO;
			}
		} catch (Exception e) {
			log.error("Error al recuperar el tipo de via", e);
		}
		return null;
	}

	@Transactional
	@Override
	public TipoViaVO getTipoViaDgt(String idTipoViaDgt) {
		try {
			TipoViaVO tipoViaVO = tipoViaDao.getTipoViaDgt(idTipoViaDgt);
			if (tipoViaVO != null) {
				return tipoViaVO;
			}
		} catch (Exception e) {
			log.error("Error al recuperar el tipo de via", e);
		}
		return null;
	}

	@Transactional
	@Override
	public TipoViaVO getIdTipoViaPorDesc(String descTipoVia) {
		try {
			TipoViaVO tipoViaVO = tipoViaDao.getIdTipoVia(descTipoVia);
			if (tipoViaVO != null) {
				return tipoViaVO;
			}
		} catch (Exception e) {
			log.error("Error al recuperar el tipo de via", e);
		}
		return null;
	}

	@Transactional
	@Override
	public List<TipoViaDto> listadoTipoVias(String idProvincia) {
		try {
			List<String> listaVias = servicioVia.listadoViasUnicasPorTipoVia(idProvincia);
			if (listaVias != null && !listaVias.isEmpty()) {
				List<TipoViaVO> listaTipoVia = tipoViaDao.listadoTipoVias(listaVias);
				if (listaTipoVia != null && !listaTipoVia.isEmpty()) {
					return conversor.transform(listaTipoVia, TipoViaDto.class);
				}
			}
		} catch (Exception e) {
			log.error("Error al recuperar el tipo de via", e);
		}
		return Collections.emptyList();
	}

	public TipoViaDao getTipoViaDao() {
		return tipoViaDao;
	}

	public void setTipoViaDao(TipoViaDao tipoViaDao) {
		this.tipoViaDao = tipoViaDao;
	}

	public ServicioVia getServicioVia() {
		return servicioVia;
	}

	public void setServicioVia(ServicioVia servicioVia) {
		this.servicioVia = servicioVia;
	}
}